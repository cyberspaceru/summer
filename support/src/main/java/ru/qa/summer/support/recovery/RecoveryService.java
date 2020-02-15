package ru.qa.summer.support.recovery;

import org.reflections.Reflections;
import ru.qa.summer.support.annotations.RecoveryRule;
import ru.qa.summer.support.exceptions.RecoveryRuleInstantiateException;

import java.util.*;
import java.util.stream.Collectors;

import static ru.qa.summer.support.SupportConfiguration.SUPPORT_CONFIGURATION;

public class RecoveryService {
    private static List<Class<? extends BaseRecoveryRule>> cache;
    private final List<Class<? extends BaseRecoveryRule>> rules;
    private int commonAttempts;
    private Map<Class<? extends BaseRecoveryRule>, Integer> ownAttemptsMap = new HashMap<>();

    public RecoveryService(List<Class<? extends BaseRecoveryRule>> rules) {
        this.rules = rules;
    }

    public List<Class<? extends BaseRecoveryRule>> getRules() {
        return rules;
    }

    public Class<? extends BaseRecoveryRule> canBeRecoveredBy(Throwable throwable) {
        Class<? extends BaseRecoveryRule> result = null;
        for (Class<? extends BaseRecoveryRule> ruleClass : rules) {
            if (!ownAttemptsMap.containsKey(ruleClass)) {
                ownAttemptsMap.put(ruleClass, 0);
            }
            int ownAttempts = ownAttemptsMap.get(ruleClass);
            BaseRecoveryRule instance;
            try {
                instance = ruleClass.newInstance();
            } catch (Exception e) {
                throw new RecoveryRuleInstantiateException(e);
            }
            if (!instance.isExpired(commonAttempts, ownAttempts)) {
                RecoveryInfo info = new RecoveryInfo();
                info.setCommonAttempts(commonAttempts);
                info.setOwnAttempts(ownAttempts);
                info.setThrowable(throwable);
                if (instance.test(info)) {
                    result = ruleClass;
                }
                ownAttemptsMap.put(ruleClass, ownAttempts + 1);
            }
            if (result != null) {
                break;
            }
        }
        commonAttempts++;
        return result;
    }

    public static RecoveryService create() {
        String[] packages = SUPPORT_CONFIGURATION.getLoadableRecoveryPackages().split(";");
        return byPackages(Arrays.stream(packages).filter(x -> !x.isEmpty()).collect(Collectors.toList()));
    }

    public static RecoveryService byPackages(List<String> packages) {
        List<Class<? extends BaseRecoveryRule>> rules = new ArrayList<>();
        if (cache == null) {
            for (String p : packages) {
                List<Class<? extends BaseRecoveryRule>> byPackage = new ArrayList<>();
                new Reflections(p).getTypesAnnotatedWith(RecoveryRule.class).forEach(x -> {
                    if (BaseRecoveryRule.class.isAssignableFrom(x)) {
                        byPackage.add((Class<? extends BaseRecoveryRule>) x);
                    }
                });
                if (!byPackage.isEmpty()) {
                    rules.addAll(byPackage);
                }
            }
            cache = Collections.unmodifiableList(rules);
        }
        return new RecoveryService(cache);
    }
}

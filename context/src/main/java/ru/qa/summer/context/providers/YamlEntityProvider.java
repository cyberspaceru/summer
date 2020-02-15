package ru.qa.summer.context.providers;

import org.apache.commons.io.FileUtils;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.emitter.Emitter;
import org.yaml.snakeyaml.nodes.MappingNode;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.NodeTuple;
import org.yaml.snakeyaml.nodes.ScalarNode;
import org.yaml.snakeyaml.resolver.Resolver;
import org.yaml.snakeyaml.serializer.Serializer;
import ru.qa.summer.context.ContextEntity;
import ru.qa.summer.context.EntityProvider;
import ru.qa.summer.context.ValueWrapper;
import ru.qa.summer.context.exceptions.ContextParseException;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import static java.text.MessageFormat.format;
import static ru.qa.summer.support.util.NamingUtil.*;

/**
 * @author Viktor Matskevich
 */
public class YamlEntityProvider implements EntityProvider {
    private final String yaml;

    public YamlEntityProvider(String yaml) {
        this.yaml = yaml;
    }

    @Override
    public List<Object> provide() {
        Node rootNode = new Yaml().compose(new StringReader(yaml));
        ContextEntity root = readNode("root", rootNode);
        List<Object> result = new ArrayList<>();
        root.forEach((k, v) -> {
            if (v instanceof ContextEntity) {
                result.add(v);
            } else if (v instanceof ArrayField) {
                result.add(((ArrayField) v).transform());
            } else {
                throw new ContextParseException("Root object can be an instance of '" + ContextEntity.class + "' or '"
                        + ArrayField.class + "' , but '" + k + "' doesn't meet this requirement. Actually it's " + v.getClass());
            }
        });
        return result;
    }

    private ContextEntity readNode(String entityName, Node valueNode) {
        ContextEntity entity = ContextEntity.parse(entityName);
        for (NodeTuple n : ((MappingNode) valueNode).getValue()) {
            String name = ((ScalarNode) n.getKeyNode()).getValue();
            if (entity.containsKey(name)) {
                String message = format("Found a duplicate value: {0}", name);
                throw new ContextParseException(message);
            }
            resolve(entity, name, n.getValueNode());
        }
        return entity;
    }

    private String nodeToString(Node node) {
        StringWriter buffer = new StringWriter();
        DumperOptions dumperOptions = new DumperOptions();
        Serializer serializer = new Serializer(new Emitter(buffer, dumperOptions), new Resolver(), dumperOptions, null);
        try {
            serializer.open();
            serializer.serialize(node);
            serializer.close();
        } catch (IOException e) {
            throw new ContextParseException(e);
        }
        return buffer.toString().trim();
    }

    private void resolve(ContextEntity entity, String name, Node node) {
        Object value = getNodeValue(name, node);
        if (containsIndexer(name)) {
            resolveIndexer(entity, name, value);
        } else {
            entity.put(name, value);
        }
    }

    private void resolveIndexer(ContextEntity entity, String name, Object value) {
        String key = removeIndexer(name);
        if (entity.get(key) == null || !(entity.get(key) instanceof ArrayField)) {
            entity.put(key, new ArrayField(key));
        }
        ArrayField array = (ArrayField) entity.get(key);
        array.add(extractIndex(name), value);
    }

    private Object getNodeValue(String name, Node node) {
        if (node instanceof MappingNode) {
            return readNode(name, node);
        } else {
            return new ValueWrapper(nodeToString(node));
        }
    }

    public static YamlEntityProvider fromFile(File file) {
        try {
            return new YamlEntityProvider(FileUtils.readFileToString(file, "UTF-8"));
        } catch (IOException e) {
            throw new ContextParseException(e);
        }
    }
}

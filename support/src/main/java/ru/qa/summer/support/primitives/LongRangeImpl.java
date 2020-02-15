package ru.qa.summer.support.primitives;

public class LongRangeImpl implements LongRange {
    private final Long from;
    private final Long to;

    public LongRangeImpl(String source, String divider) {
        String[] parts = source.split(divider);
        if (parts.length != 2) {
            throw new IllegalArgumentException("Source('" + source + "') for Range contains more than 1 Divider('" + divider + "')");
        }
        this.from = Long.parseLong(parts[0].trim());
        this.to = Long.parseLong(parts[1].trim());
    }

    @Override
    public Long from() {
        return from;
    }

    @Override
    public Long to() {
        return to;
    }

    public boolean contains(Long l) {
        return from >= l && to <= l;
    }

    public boolean contains(long l) {
        return from >= l && to <= l;
    }
}
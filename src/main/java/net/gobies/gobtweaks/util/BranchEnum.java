package net.gobies.gobtweaks.util;

public enum BranchEnum {
    ONLY_BRANCHES(3),
    WHOLE_TREE(8);

    private final int value;

    BranchEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
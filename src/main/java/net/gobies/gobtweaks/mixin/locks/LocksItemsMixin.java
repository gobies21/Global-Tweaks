package net.gobies.gobtweaks.mixin.locks;

import melonslise.locks.common.init.LocksItems;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(LocksItems.class)
public class LocksItemsMixin {

    @ModifyConstant(
            method = "lambda$static$8",
            constant = @Constant(intValue = 5)
    )
    private static int woodLockRebalance(int constant) {
        return 3;
    }

    @ModifyConstant(
            method = "lambda$static$9",
            constant = @Constant(intValue = 7)
    )
    private static int ironLockRebalance(int constant) {
        return 6;
    }

    @ModifyConstant(
            method = "lambda$static$10",
            constant = @Constant(intValue = 9)
    )
    private static int steelLockRebalance(int constant) {
        return 7;
    }

    @ModifyConstant(
            method = "lambda$static$11",
            constant = @Constant(intValue = 6)
    )
    private static int goldLockRebalance(int constant) {
        return 5;
    }

    @ModifyConstant(
            method = "lambda$static$12",
            constant = @Constant(intValue = 11)
    )
    private static int diamondLockRebalance(int constant) {
        return 9;
    }

    @ModifyConstant(
            method = "lambda$static$17",
            constant = @Constant(floatValue = 0.35F)
    )
    private static float ironLockPickRebalance(float constant) {
        return 0.45F;
    }

    @ModifyConstant(
            method = "lambda$static$19",
            constant = @Constant(floatValue = 0.25F)
    )
    private static float goldLockPickRebalance(float constant) {
        return 0.30F;
    }
}
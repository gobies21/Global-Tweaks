package net.gobies.gobtweaks.mixin.blessfulled;

import org.aqutheseal.blessfulled.triggers.SpecialSoundsTriggers;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(SpecialSoundsTriggers.class)
public class SpecialSoundsTriggersMixin {
    @ModifyConstant(
            method = "playSwingSound",
            constant = @Constant(floatValue = 0.5F),
            remap = false
    )
    private static float playSwingSound(float constant) {
        return 0.8F;
    }
}

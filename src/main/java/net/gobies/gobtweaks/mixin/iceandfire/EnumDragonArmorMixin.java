package net.gobies.gobtweaks.mixin.iceandfire;

import com.github.alexthe666.iceandfire.enums.EnumDragonArmor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(EnumDragonArmor.class)
public class EnumDragonArmorMixin {
    @ModifyConstant(
            method = "initArmors",
            constant = @Constant(intValue = 7),
            remap = false
    )
    // Fixes armor values being mixed up
    private static int modifyChestArmor(int original) {
        return 9;
    }

    @ModifyConstant(
            method = "initArmors",
            constant = @Constant(intValue = 9),
            remap = false
    )

    private static int modifyLegArmor(int original) {
        return 7;
    }
}

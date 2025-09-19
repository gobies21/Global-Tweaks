package net.gobies.gobtweaks.mixin.iceandfire;

import com.github.alexthe666.iceandfire.item.DragonSteelTier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(DragonSteelTier.class)
public class DragonSteelTierMixin {
    @ModifyConstant(
            method = "createTierWithRepairItem",
            constant = @Constant(floatValue = 21.0F),
            remap = false
    )
    // Major nerf to dragon steel equipment from spartanfire weapons
    private static float nerfDragonSteel(float constant) {
        return 7.0F;
    }
}

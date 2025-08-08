package net.gobies.gobtweaks.mixin.jlme;

import com.kettle.jlme.enchantment.SupremeSharpnessEnchantment;
import net.gobies.gobtweaks.config.CommonConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(SupremeSharpnessEnchantment.class)
public class SupremeSharpnessEnchantmentMixin {
    @ModifyConstant(
            method = "getDamageBonus",
            constant = @Constant(floatValue = 7)
    )
    // Makes the damage values of supreme sharpness configurable
    public float setBaseDamage(float base) {
        return CommonConfig.SUPREME_SHARPNESS_BASE_DAMAGE.get().floatValue();
    }

    @ModifyConstant(
            method = "getDamageBonus",
            constant = @Constant(floatValue = 1.25F)
    )

    public float setPerLevelDamage(float perLevel) {
        return CommonConfig.SUPREME_SHARPNESS_LEVEL_DAMAGE.get().floatValue();
    }
}

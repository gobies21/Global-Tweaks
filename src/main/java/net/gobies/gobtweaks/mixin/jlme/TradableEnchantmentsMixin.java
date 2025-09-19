package net.gobies.gobtweaks.mixin.jlme;

import com.kettle.jlme.enchantment.*;
import net.gobies.gobtweaks.config.CommonConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({
        AdvancedBlastProtectionEnchantment.class,
        AdvancedFeatherFallingEnchantment.class,
        AdvancedFireProtectionEnchantment.class,
        AdvancedProjectileProtectionEnchantment.class,
        AdvancedFortuneEnchantment.class,
        AdvancedPowerEnchantment.class,
        AdvancedProtectionEnchantment.class,
        SupremeColdAspectEnchantment.class,
        SupremeFireAspectEnchantment.class,
        SupremeSharpnessEnchantment.class}
)
public class TradableEnchantmentsMixin {
    @Inject(
            method = "isTradeable",
            at = @At("HEAD"),
            cancellable = true
    )
    private void isTradeable(CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(CommonConfig.TRADABLE_GLOBAL_ENCHANTMENTS.get());
    }
}

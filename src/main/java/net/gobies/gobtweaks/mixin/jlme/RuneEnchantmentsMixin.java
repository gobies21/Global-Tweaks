package net.gobies.gobtweaks.mixin.jlme;

import com.kettle.jlme.enchantment.*;
import net.gobies.gobtweaks.config.CommonConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RuneEnchantments.class)
public class RuneEnchantmentsMixin {
    @Inject(
            method = "isTradeable",
            at = @At("HEAD"),
            cancellable = true
    )
    private void isTradeable(CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(CommonConfig.TRADABLE_RUNE_ENCHANTMENTS.get());
    }
}

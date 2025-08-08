package net.gobies.gobtweaks.mixin.vanilla;

import net.gobies.gobtweaks.config.CommonConfig;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(Enchantment.class)
public class EnchantmentMixin {
    @Inject(
            method = "canEnchant",
            at = @At("HEAD"),
            remap = false,
            cancellable = true
    )
    private void canEnchantCrossbow(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        if (CommonConfig.ALLOW_POWER_CROSSBOWS.get()) {
            Enchantment enchantment = (Enchantment) (Object) this;
            if (stack.getItem() instanceof CrossbowItem && enchantment.equals(Enchantments.POWER_ARROWS)) {
                cir.setReturnValue(true);
            }
        }
    }
}
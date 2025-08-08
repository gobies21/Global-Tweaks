package net.gobies.gobtweaks.mixin.vanilla;

import net.gobies.gobtweaks.config.CommonConfig;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CrossbowItem.class)
public class CrossbowItemMixin {
    @Inject(
            method = "getShootingPower",
            at = @At("TAIL"),
            cancellable = true
    )
    private static void power(ItemStack pCrossbowStack, CallbackInfoReturnable<Float> cir) {
        int powerLevel = pCrossbowStack.getEnchantmentLevel(Enchantments.POWER_ARROWS);
        if (powerLevel > 0 && CommonConfig.ALLOW_POWER_CROSSBOWS.get()) {
            float newShootingPower = powerLevel * 1.0F + 0.5F;
            cir.setReturnValue(newShootingPower);
        }
    }
}
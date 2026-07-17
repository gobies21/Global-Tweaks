package net.gobies.gobtweaks.mixin.jlme;

import com.kettle.jlme.configuration.JLMEConfiguration;
import com.kettle.jlme.enchantment.LethalCadenceEnchantment;
import com.kettle.jlme.enchantment.LifestealEnchantment;
import com.kettle.jlme.enchantment.LuckyOddsEnchantment;
import net.gobies.gobtweaks.config.CommonConfig;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({
        LifestealEnchantment.class,
        LethalCadenceEnchantment.class,
        LuckyOddsEnchantment.class
})
public class BowEnchantmentMixin {
    @Inject(
            method = "canEnchant",
            at = @At("HEAD"),
            cancellable = true
    )
    private void canEnchant(ItemStack p_44642_, CallbackInfoReturnable<Boolean> cir) {
        if (CommonConfig.SPECIAL_FEATURES.get() && JLMEConfiguration.lifesteal) {
            if (p_44642_.getItem() instanceof BowItem || p_44642_.getItem() instanceof CrossbowItem) {
                cir.setReturnValue(true);
            }
        }
    }
}

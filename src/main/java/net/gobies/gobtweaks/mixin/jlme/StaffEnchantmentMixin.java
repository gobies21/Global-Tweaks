package net.gobies.gobtweaks.mixin.jlme;

import com.kettle.jlme.configuration.JLMEConfiguration;
import com.kettle.jlme.enchantment.*;
import net.gobies.gobtweaks.config.CommonConfig;
import net.gobies.gobtweaks.util.GTUtils;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({
        AdeptEnchantment.class,
        RepairableEnchantment.class,
        CurseOfPossessionEnchantment.class,
        LethalCadenceEnchantment.class,
        ArmorPiercingEnchantment.class,
        MagicPiercingEnchantment.class,
        LifestealEnchantment.class,
        LuckyOddsEnchantment.class
})
public class StaffEnchantmentMixin {
    @Inject(
            method = "canEnchant",
            at = @At("HEAD"),
            cancellable = true
    )
    private void canEnchant(ItemStack p_44642_, CallbackInfoReturnable<Boolean> cir) {
        if (CommonConfig.SPECIAL_FEATURES.get() && JLMEConfiguration.lifesteal) {
            if (GTUtils.isStaff(p_44642_.getItem())) {
                cir.setReturnValue(true);
            }
        }
    }
}

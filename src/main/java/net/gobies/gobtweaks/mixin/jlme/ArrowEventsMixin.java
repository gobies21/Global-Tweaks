package net.gobies.gobtweaks.mixin.jlme;

import com.kettle.jlme.events.ArrowEvents;
import com.kettle.jlme.init.JlmeModEnchantments;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ArrowEvents.class)
public class ArrowEventsMixin {
    // Rework advanced power to use normal damage formula
    @Redirect(
            method = "onEntityJoin",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/projectile/AbstractArrow;setBaseDamage(D)V"
            )
    )
    private static void modifyAdvancedPowerDamage(AbstractArrow arrow, double pBaseDamage) {
        if (arrow.getOwner() instanceof LivingEntity owner) {
            float level = (float) owner.getMainHandItem().getEnchantmentLevel(JlmeModEnchantments.ADVANCED_POWER.get());
            double baseDamage = arrow.getBaseDamage();
            double advancedPowerDamage = (double) level + 1.00D;
            arrow.setBaseDamage(baseDamage + advancedPowerDamage);
        }
    }
}
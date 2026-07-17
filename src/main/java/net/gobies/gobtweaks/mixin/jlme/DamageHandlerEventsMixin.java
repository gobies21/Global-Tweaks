package net.gobies.gobtweaks.mixin.jlme;

import com.kettle.jlme.configuration.JLMEConfiguration;
import com.kettle.jlme.events.DamageHandlerEvents;
import net.gobies.gobtweaks.config.CommonConfig;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.CrossbowItem;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(DamageHandlerEvents.class)
public class DamageHandlerEventsMixin {

    // Nerfs piercing enchantments against players for pvp purposes
    @ModifyVariable(
            method = "DefenseBypass(Lnet/minecraftforge/event/entity/living/LivingDamageEvent;)V",
            at = @At(value = "STORE"),
            remap = false,
            name = "pclvl"
    )
    private static float halfPcLevel(float pclvl, LivingDamageEvent event) {
        if (!CommonConfig.RUNE_BALANCE.get()) return pclvl;
        if (event != null && event.getEntity() instanceof Player) {
            return pclvl * 0.5F;
        }
        return pclvl;
    }

    @ModifyVariable(
            method = "DefenseBypass(Lnet/minecraftforge/event/entity/living/LivingDamageEvent;)V",
            at = @At(value = "STORE"),
            remap = false,
            name = "arrow_piercing"
    )
    private static float halfArrowPiercing(float arrow_piercing, LivingDamageEvent event) {
        if (!CommonConfig.RUNE_BALANCE.get()) return arrow_piercing;
        if (event != null && event.getEntity() instanceof Player) {
            return arrow_piercing * 0.25F;
        }
        return arrow_piercing;
    }

    // Nerfs lifesteal on bows and crossbows
    @ModifyVariable(
            method = "LastModification",
            at = @At(value = "STORE"),
            remap = false,
            name = "amount"
    )
    private static float halfLifesteal(float amount, LivingDamageEvent event) {
        if (CommonConfig.SPECIAL_FEATURES.get() && JLMEConfiguration.lifesteal) {
            if (event != null && event.getSource().getEntity() instanceof LivingEntity livingEntity) {
                if (livingEntity.getMainHandItem().getItem() instanceof BowItem || livingEntity.getMainHandItem().getItem() instanceof CrossbowItem) {
                    return amount * 0.5F;
                }
            }
        }
        return amount;
    }
}

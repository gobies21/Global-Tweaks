package net.gobies.gobtweaks.handlers.enhancedvisuals;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import team.creative.enhancedvisuals.EnhancedVisuals;

public class EHDamageHandler {

    // For first aid
    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent event) {
        LivingEntity entity = event.getEntity();
        DamageSource damage = event.getSource();
        if (entity instanceof Player player) {
            float amount = event.getAmount();
            EnhancedVisuals.EVENTS.damage(player, damage, amount);
        }
    }
}

package net.gobies.gobtweaks.handlers.firstaid;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import team.creative.enhancedvisuals.EnhancedVisuals;

public class EHDamageHandler {

    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        LivingEntity entity = event.getEntity();
        DamageSource damage = event.getSource();
        if (entity instanceof Player player) {
            EnhancedVisuals.EVENTS.damage(player, damage, event.getAmount());
        }
    }
}

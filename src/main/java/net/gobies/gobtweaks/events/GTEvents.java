package net.gobies.gobtweaks.events;

import net.gobies.gobtweaks.config.CommonConfig;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class GTEvents {

    @SubscribeEvent
    public static void friendlyFire(LivingAttackEvent event) {
        if (CommonConfig.FRIENDLY_FIRE.get()) {
            Entity target = event.getEntity();
            Entity source = event.getSource().getEntity();
            if (target != null && source != null) {
                if (target instanceof TamableAnimal pet && pet.isTame()) {
                    if (pet.getOwner() instanceof Player player && player.equals(source)) {
                        event.setCanceled(true);
                    }
                }
            }
        }
    }
}
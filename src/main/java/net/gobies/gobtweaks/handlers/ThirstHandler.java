package net.gobies.gobtweaks.handlers;

import dev.ghen.thirst.foundation.common.capability.ModCapabilities;
import net.gobies.gobtweaks.config.CommonConfig;
import net.gobies.gobtweaks.util.ModLoadedUtil;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ThirstHandler {

    @SubscribeEvent
    public static void onAttack(AttackEntityEvent event) {
        if (ModLoadedUtil.isThirstLoaded() && CommonConfig.THIRST_ATTACK_EXHAUSTION.get()) {
            try {
                Player player = event.getEntity();
                player.getCapability(ModCapabilities.PLAYER_THIRST, null).ifPresent(cap -> {
                    if (cap.getExhaustion() > 0) {
                        cap.setExhaustion(cap.getExhaustion() + 0.2F);
                    }
                });

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}

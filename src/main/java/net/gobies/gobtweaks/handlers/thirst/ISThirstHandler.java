package net.gobies.gobtweaks.handlers.thirst;

import dev.ghen.thirst.foundation.common.capability.ModCapabilities;
import io.redspace.ironsspellbooks.api.events.SpellOnCastEvent;
import net.gobies.gobtweaks.config.CommonConfig;
import net.gobies.gobtweaks.util.ModLoadedUtil;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ISThirstHandler {


    @SubscribeEvent
    public static void onCast(SpellOnCastEvent event) {
        if (ModLoadedUtil.isThirstLoaded() && ModLoadedUtil.isIronsspellbooksLoaded() && CommonConfig.THIRST_ATTACK_EXHAUSTION.get()) {
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

package net.gobies.gobtweaks.handlers.firstaid;

import ichttt.mods.firstaid.FirstAid;
import ichttt.mods.firstaid.api.damagesystem.AbstractDamageablePart;
import ichttt.mods.firstaid.common.network.MessageUpdatePart;
import ichttt.mods.firstaid.common.util.CommonUtils;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.network.PacketDistributor;

public class LoginHandler {
    // Fix rejoining world at wrong visual health values and body overlay
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer serverPlayer)) return;
        resyncBody(serverPlayer);
    }
    // Fix respawning at wrong visual wrong health values and body overlay
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer serverPlayer)) return;
        resyncBody(serverPlayer);
    }

    private static void resyncBody(ServerPlayer serverPlayer){
        CommonUtils.getOptionalDamageModel(serverPlayer).ifPresent(damageModel -> {
            for (AbstractDamageablePart part : damageModel) {
                MessageUpdatePart updatePacket = new MessageUpdatePart(part);
                damageModel.scheduleResync();
                FirstAid.NETWORKING.send(PacketDistributor.PLAYER.with(() -> serverPlayer), updatePacket);
            }
        });
    }
}

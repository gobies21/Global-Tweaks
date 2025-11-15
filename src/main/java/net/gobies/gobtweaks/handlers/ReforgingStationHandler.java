package net.gobies.gobtweaks.handlers;

import com.dplayend.reforgingstation.registry.RegistryAttributes;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ReforgingStationHandler {

    public static void loadClass() {
        MinecraftForge.EVENT_BUS.register(new ReforgingStationHandler());
    }

    @SubscribeEvent
    public void onLivingFall(LivingFallEvent event) {
        if (!event.isCanceled() && event.getEntity() instanceof Player player) {
            float fallDistance = event.getDistance();
            double jumpHeight = player.getAttributeValue(RegistryAttributes.JUMP_HEIGHT.get());
            event.setDistance((float) (fallDistance - (jumpHeight * 2)));
        }
    }

    @SubscribeEvent
    public void onLivingHurt(LivingHurtEvent event) {
        if (!event.isCanceled() && event.getEntity() instanceof Player player) {
            float amount = event.getAmount();
            double damageResistance = player.getAttributeValue(RegistryAttributes.DAMAGE_RESISTANCE.get());
            event.setAmount((float) (amount * (1 - damageResistance)));
        }
    }
}

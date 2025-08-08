package net.gobies.gobtweaks.handlers;

import com.github.alexthe666.iceandfire.misc.IafDamageRegistry;
import com.momosoftworks.coldsweat.core.init.EffectInit;
import net.gobies.gobtweaks.config.CommonConfig;
import net.gobies.gobtweaks.util.ModLoadedUtil;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class DragonDamageHandler {

    @SubscribeEvent
    public static void onEntityAttacked(LivingAttackEvent event) {
        if (ModLoadedUtil.isIceandFireLoaded()) {
            LivingEntity entity = event.getEntity();
            DamageSource source = event.getSource();
            if (CommonConfig.DRAGON_FIRE_IMMUNE.get()) {
                if (entity.hasEffect(MobEffects.FIRE_RESISTANCE) && source.is(IafDamageRegistry.DRAGON_FIRE_TYPE)) {
                    event.setCanceled(true);
                }
            }
            if (ModLoadedUtil.isColdSweatLoaded() && CommonConfig.DRAGON_ICE_IMMUNE.get()) {
                if (entity.hasEffect(EffectInit.ICE_RESISTANCE.get()) && source.is(IafDamageRegistry.DRAGON_ICE_TYPE)) {
                    event.setCanceled(true);
                }
            }
        }
    }
}
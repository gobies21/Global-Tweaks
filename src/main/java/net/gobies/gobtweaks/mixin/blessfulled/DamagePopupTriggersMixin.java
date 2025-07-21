package net.gobies.gobtweaks.mixin.blessfulled;

import com.kettle.jlme.init.JlmeModEnchantments;
import com.kettle.pml.core.PMLDamageTypes;
import net.gobies.gobtweaks.util.ModLoadedUtil;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.aqutheseal.blessfulled.triggers.DamagePopupTriggers;
import org.aqutheseal.blessfulled.util.mixinhelper.CritTracker;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DamagePopupTriggers.class)
public class DamagePopupTriggersMixin {
    @Inject(
            method = "hurtTrigger",
            at = @At("HEAD"),
            remap = false,
            cancellable = true
    )
    private static void onHurt(LivingEntity target, DamageSource source, float damage, CallbackInfo ci) {
        Entity attacker = source.getEntity();
        if (ModLoadedUtil.isPMLLoaded()) {
            if (source.is(PMLDamageTypes.PLAYER_CRITICAL_KEY)) {
                DamagePopupTriggers.triggerEffect(attacker, target, damage, ((CritTracker) target).blessfulled$hasCritted() ? 1 : 0);
                ci.cancel();
            }
        }
        if (ModLoadedUtil.isJLMELoaded() && attacker instanceof LivingEntity livingAttacker) {
            if (livingAttacker.getMainHandItem().getEnchantmentLevel(JlmeModEnchantments.critical_strike) > 0) {
                DamagePopupTriggers.triggerEffect(attacker, target, damage, 0);
                ci.cancel();
            }
            if (livingAttacker.getOffhandItem().getEnchantmentLevel(JlmeModEnchantments.critical_strike) > 0) {
                DamagePopupTriggers.triggerEffect(attacker, target, damage, 0);
                ci.cancel();
            }
        }
    }
}
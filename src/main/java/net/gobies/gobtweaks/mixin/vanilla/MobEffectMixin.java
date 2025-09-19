package net.gobies.gobtweaks.mixin.vanilla;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;

@Mixin(MobEffect.class)
public class MobEffectMixin {
    @Inject(
            method = "applyInstantenousEffect",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;heal(F)V")
    )
    // Makes instant health potions heal additional health on par with instant damage potions
    private void modifyHealAmount(@Nullable Entity pSource, @Nullable Entity pIndirectSource, LivingEntity pLivingEntity, int pAmplifier, double pHealth, CallbackInfo ci) {
        MobEffect effect = (MobEffect) (Object) this;
        if (effect == MobEffects.HEAL && !pLivingEntity.isInvertedHealAndHarm()) {
            double additionalHealth = 1.5D;

            int i = (int)(pHealth * (double)(4 << pAmplifier) + additionalHealth);

            pLivingEntity.heal(i);
        }
    }
}
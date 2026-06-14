package net.gobies.gobtweaks.mixin.vanilla;

import net.gobies.gobtweaks.config.CommonConfig;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.item.ItemEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemEntity.class)
public abstract class ItemEntityMixin {
    @Inject(
            method = "hurt",
            at = @At("HEAD"),
            cancellable = true
    )
    public void preventExplosionDamage(DamageSource pSource, float pAmount, CallbackInfoReturnable<Boolean> cir) {
        if (CommonConfig.EXPLOSION_DESTROY_ITEM.get()) {
            if (pSource.is(DamageTypeTags.IS_EXPLOSION)) {
                cir.cancel();
            }
        }
    }
}
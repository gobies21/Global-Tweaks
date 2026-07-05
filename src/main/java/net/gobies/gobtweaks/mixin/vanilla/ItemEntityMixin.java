package net.gobies.gobtweaks.mixin.vanilla;

import net.gobies.gobtweaks.config.CommonConfig;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
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

    @Shadow
    public int lifespan;

    @Inject(
            method = "<init>(Lnet/minecraft/world/level/Level;DDDLnet/minecraft/world/item/ItemStack;DDD)V",
            at = @At("TAIL")
    )
    // Makes despawn timer configurable
    private void changeBaseLifespan(Level pLevel, double pPosX, double pPosY, double pPosZ, ItemStack pItemStack, double pDeltaX, double pDeltaY, double pDeltaZ, CallbackInfo ci) {
        if (this.lifespan == 6000) {
            this.lifespan = CommonConfig.DESPAWN_LIFESPAN.get();
        }
    }
}
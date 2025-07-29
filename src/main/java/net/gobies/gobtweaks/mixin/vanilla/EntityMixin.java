package net.gobies.gobtweaks.mixin.vanilla;

import net.gobies.gobtweaks.CommonConfig;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public abstract class EntityMixin {

    @Shadow
    public abstract Level level();

    @Shadow
    public abstract AABB getBoundingBox();

    @Shadow
    private int remainingFireTicks;

    @Inject(
            method = "setRemainingFireTicks",
            at = @At("TAIL")
    )
    // Clears all fire from entities if they have fire resistance
    private void setFireTicks(int ticks, CallbackInfo ci) {
        gobtweaks$clearFire();
    }

    @Inject(
            method = "baseTick",
            at = @At("TAIL")
    )
    private void baseTick(CallbackInfo ci) {
        if (((Entity) (Object) this).isOnFire()) {
            gobtweaks$clearFire();
        }
    }

    @Unique
    public void gobtweaks$clearFire() {
        if (remainingFireTicks > 0) {
            Entity entity = (Entity) (Object) this;
            if (entity instanceof LivingEntity livingEntity) {
                if (gobTweaks$shouldExtinguish(livingEntity)) {
                    this.remainingFireTicks = 0;
                }
            }
        }
    }

    @Unique
    private boolean gobTweaks$shouldExtinguish(LivingEntity livingEntity) {
        if (CommonConfig.NO_FIRE_OVERLAY.get()) {
            if (livingEntity instanceof Player player && player.isCreative()) {
                return true;
            }
            return livingEntity.hasEffect(MobEffects.FIRE_RESISTANCE) && !gobTweaks$isInFireOrLava();
        }
        return false;
    }

    @Unique
    private boolean gobTweaks$isInFireOrLava() {
        return level().getBlockStatesIfLoaded(getBoundingBox().contract(0.001D, 0.001D, 0.001D))
                .allMatch(blockState -> blockState.is(BlockTags.FIRE) || blockState.is(Blocks.LAVA));
    }
}
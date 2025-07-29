package net.gobies.gobtweaks.mixin.iceandfire;

import com.github.alexthe666.iceandfire.entity.props.FrozenData;
import com.momosoftworks.coldsweat.core.init.EffectInit;
import net.gobies.gobtweaks.CommonConfig;
import net.gobies.gobtweaks.util.ModLoadedUtil;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FrozenData.class)
public abstract class FrozenDataMixin {

    @Shadow(remap = false)
    protected abstract void clearFrozen(LivingEntity entity);

    @Shadow(remap = false)
    public boolean isFrozen;

    @Inject(
            method = "tickFrozen",
            at = @At("HEAD"),
            remap = false
    )
    // Having ice resistance makes the entity immune the being frozen
    private void clearFrozen(LivingEntity entity, CallbackInfo ci) {
        if (ModLoadedUtil.isColdSweatLoaded() && CommonConfig.DRAGON_ICE_IMMUNE.get()) {
            if (entity.hasEffect(EffectInit.ICE_RESISTANCE.get()) && this.isFrozen) {
                this.clearFrozen(entity);
            }
        }
    }
}

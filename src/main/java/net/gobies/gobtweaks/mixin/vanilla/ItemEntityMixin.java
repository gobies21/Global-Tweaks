package net.gobies.gobtweaks.mixin.vanilla;

import net.gobies.gobtweaks.Config;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.item.ItemEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public abstract class ItemEntityMixin {
    @Inject(
            method = "thunderHit",
            at = @At("HEAD"),
            cancellable = true
    )
    //Prevent lightning from destroying items
    public void thunderHit(ServerLevel pLevel, LightningBolt pLightning, CallbackInfo ci) {
        if (Config.LIGHTNING_DESTROY_ITEM.get()) {
            Entity entity = (Entity) ((Object) this);
            if (entity instanceof ItemEntity) {
                ci.cancel();
            }
        }
    }
}
package net.gobies.gobtweaks.mixin.vanilla;

import com.mojang.blaze3d.vertex.PoseStack;
import net.gobies.gobtweaks.config.CommonConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ScreenEffectRenderer;
import net.minecraft.world.effect.MobEffects;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ScreenEffectRenderer.class)
public class ScreenEffectRendererMixin {
    @Inject(
            method = "renderScreenEffect",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ScreenEffectRenderer;renderFire(Lnet/minecraft/client/Minecraft;Lcom/mojang/blaze3d/vertex/PoseStack;)V"),
            cancellable = true
    )
    // Cancels the fire overlay if the player is in creative or has fire resistance
    private static void noFireOverlay(Minecraft pMinecraft, PoseStack pPoseStack, CallbackInfo ci) {
        if (CommonConfig.NO_FIRE_OVERLAY.get() && pMinecraft.player != null && pMinecraft.player.isOnFire()) {
            if (pMinecraft.player.hasEffect(MobEffects.FIRE_RESISTANCE) || pMinecraft.player.isCreative()) {
                ci.cancel();
            }
        }
    }
}
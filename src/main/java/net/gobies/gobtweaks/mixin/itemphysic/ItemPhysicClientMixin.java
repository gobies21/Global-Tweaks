package net.gobies.gobtweaks.mixin.itemphysic;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import team.creative.itemphysic.ItemPhysic;
import team.creative.itemphysic.client.ItemPhysicClient;

@Mixin(ItemPhysicClient.class)
public class ItemPhysicClientMixin {

    @Final
    @Shadow(remap = false)
    public static Minecraft mc;

    @Inject(
            method = "renderTooltip",
            at = @At("HEAD"),
            cancellable = true,
            remap = false
    )
    private static void renderTooltip(GuiGraphics graphics, CallbackInfo ci) {
        if (ItemPhysic.CONFIG.pickup.customPickup && mc.player != null) {
            HitResult result = ItemPhysicClient.getEntityItem(mc.player);
            if (result instanceof EntityHitResult entityHitResult) {
                ItemEntity itemEntity = (ItemEntity) entityHitResult.getEntity();
                if (itemEntity.getItem().getItem() == Items.AIR) {
                    ci.cancel();
                }
            }
        }
    }
}
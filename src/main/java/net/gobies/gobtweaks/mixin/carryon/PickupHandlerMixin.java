package net.gobies.gobtweaks.mixin.carryon;

import melonslise.locks.common.util.LocksUtil;
import net.gobies.gobtweaks.util.ModLoadedUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import tschipp.carryon.common.carry.PickupHandler;

@Mixin(PickupHandler.class)
public class PickupHandlerMixin {
    @Inject(
            method = "canCarryGeneral",
            at = @At("TAIL"),
            remap = false,
            cancellable = true
    )
    // Prevent picking up locked chests through carry-on
    private static void denyLocked(ServerPlayer player, Vec3 pos, CallbackInfoReturnable<Boolean> cir) {
        if (ModLoadedUtil.isLocksLoaded() && cir.getReturnValue()) {
            BlockPos blockPos = BlockPos.containing(pos);
            BlockEntity blockEntity = player.level().getBlockEntity(blockPos);
            if (blockEntity instanceof BaseContainerBlockEntity && LocksUtil.locked(player.level(), blockPos)) {
                cir.setReturnValue(false);
            }
        }
    }
}
package net.gobies.gobtweaks.mixin.carryon;

import melonslise.locks.common.util.LocksUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import tschipp.carryon.common.carry.PickupHandler;

@Mixin(PickupHandler.class)
public abstract class PickupHandlerMixin {
    @Inject(
            method = "canCarryGeneral",
            at = @At("TAIL"),
            remap = false,
            cancellable = true
    )
    //Does not work in game for some reason...
    //Prevent picking up locked chests through carry-on
    private static void denyLocked(ServerPlayer player, Vec3 pos, CallbackInfoReturnable<Boolean> cir) {
        BlockPos blockPos = new BlockPos((int) pos.x, (int) pos.y, (int) pos.z);
        BaseContainerBlockEntity blockEntity = (BaseContainerBlockEntity) player.level().getBlockEntity(blockPos);
        if (blockEntity != null) {
            Level level = player.level();
            if (LocksUtil.locked(level, BlockPos.containing(pos))) {
                cir.setReturnValue(false);
                cir.cancel();
            }
        }
    }
}
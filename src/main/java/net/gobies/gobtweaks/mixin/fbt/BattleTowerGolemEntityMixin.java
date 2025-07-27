package net.gobies.gobtweaks.mixin.fbt;

import com.kettle.fbt.ForgottenBattleTowersChestEvent;
import melonslise.locks.common.util.LocksUtil;
import net.gobies.gobtweaks.util.ModLoadedUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ForgottenBattleTowersChestEvent.class)
public class BattleTowerGolemEntityMixin {

    @Inject(
            method = "notifyEntitiesNearby",
            at = @At("HEAD"),
            cancellable = true,
            remap = false
    )
    // Allow interacting with locked battle tower chests without the golem getting aggressive
    private static void targetChest(Level level, BlockPos chestPos, Player player, CallbackInfoReturnable<Boolean> cir) {
        if (ModLoadedUtil.isLocksLoaded()) {
            BlockState state = level.getBlockState(chestPos);
            Block var6 = state.getBlock();
            if (var6 instanceof ChestBlock && LocksUtil.locked(level, chestPos)) {
                cir.setReturnValue(false);
            }
        }
    }
}
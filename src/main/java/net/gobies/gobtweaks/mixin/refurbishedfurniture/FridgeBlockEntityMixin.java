package net.gobies.gobtweaks.mixin.refurbishedfurniture;

import com.mrcrayfish.furniture.refurbished.blockentity.FridgeBlockEntity;
import net.gobies.gobtweaks.Config;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(FridgeBlockEntity.class)
public class FridgeBlockEntityMixin {
    @ModifyConstant(
            method = "<init>(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;)V",
            constant = @Constant(intValue = 3)
    )
    // Change the default value of rows to 6
    private static int modifyRows(int original) {
        if (Config.FRIDGE_INCREASE_ROWS.get()) {
            return 6;
        }
        return original;
    }
}
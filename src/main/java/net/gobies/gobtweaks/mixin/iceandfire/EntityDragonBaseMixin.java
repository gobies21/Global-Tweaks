package net.gobies.gobtweaks.mixin.iceandfire;

import com.ferreusveritas.dynamictrees.block.branch.BasicBranchBlock;
import com.ferreusveritas.dynamictrees.data.DTBlockTags;
import com.github.alexthe666.iceandfire.entity.EntityDragonBase;
import net.gobies.gobtweaks.config.CommonConfig;
import net.gobies.gobtweaks.util.ModLoadedUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityDragonBase.class)
public class EntityDragonBaseMixin {
    @Inject(
            method = "isBreakable",
            at = @At("HEAD"),
            cancellable = true,
            remap = false
    )
    // Allows dragons to break dynamic trees branches
    private void canDestroyBlock(BlockPos pos, BlockState state, float hardness, EntityDragonBase entity, CallbackInfoReturnable<Boolean> cir) {
        if (ModLoadedUtil.isDynamicTreesLoaded()) {
            if (state.is(DTBlockTags.BRANCHES)) {
                Block block = state.getBlock();
                if (block instanceof BasicBranchBlock branchBlock) {
                    int radius = branchBlock.getRadius(state);
                    if (radius <= CommonConfig.BREAK_BRANCH_TYPE.get().getValue()) {
                        cir.setReturnValue(true);
                    }
                }
            }
        }
    }
}


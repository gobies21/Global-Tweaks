package net.gobies.gobtweaks.mixin.iceandfire;

import com.github.alexthe666.iceandfire.entity.util.DragonUtils;
import net.gobies.gobtweaks.util.ModLoadedUtil;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DragonUtils.class)
public class DragonUtilsMixin {
    @Inject(
            method = "canDragonBreak",
            at = @At("HEAD"),
            cancellable = true,
            remap = false
    )
    // Adds branches to the allowed blocks that dragons can break
    private static void canDragonBreak(BlockState state, Entity entity, CallbackInfoReturnable<Boolean> cir) {
        if (ModLoadedUtil.isDynamicTreesLoaded()) {
            if (state.is(com.ferreusveritas.dynamictrees.data.DTBlockTags.BRANCHES)) {
                cir.setReturnValue(true);
            }
        }
    }
}
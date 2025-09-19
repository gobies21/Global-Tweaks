package net.gobies.gobtweaks.mixin.iceandfire;

import com.github.alexthe666.iceandfire.item.ItemAlchemySword;
import net.gobies.gobtweaks.config.CommonConfig;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemAlchemySword.class)
public class ItemAlchemySwordMixin {
    @Inject(
            method = "isFoil",
            at = @At("HEAD"),
            cancellable = true
    )
    public void isFoil(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(CommonConfig.DRAGON_ENCHANTED_GLOW.get());
    }
}

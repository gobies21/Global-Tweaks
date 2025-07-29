package net.gobies.gobtweaks.mixin.spartanweaponry;

import com.oblivioussp.spartanweaponry.api.tags.ModItemTags;
import com.oblivioussp.spartanweaponry.capability.IOilHandler;
import com.oblivioussp.spartanweaponry.event.ClientEventHandler;
import com.oblivioussp.spartanweaponry.init.ModCapabilities;
import net.gobies.gobtweaks.CommonConfig;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.common.util.LazyOptional;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientEventHandler.class)
public class ClientEventHandlerMixin {
    @Inject(
            method = "onRenderTooltip",
            at = @At("HEAD"),
            remap = false,
            cancellable = true
    )
    private static void removeTooltip(RenderTooltipEvent.GatherComponents ev, CallbackInfo ci) {
        if (CommonConfig.DISABLE_OIL_TOOLTIP.get()) {
            ItemStack stack = ev.getItemStack();
            if (!stack.is(ModItemTags.OILABLE_WEAPONS)) {
                return;
            }

            LazyOptional<IOilHandler> handler = stack.getCapability(ModCapabilities.OIL_CAPABILITY);
            if (!handler.isPresent()) {
                return;
            }

            IOilHandler oilHandler = handler.orElseThrow(() -> new IllegalStateException("OilHandler capability is not present"));
            if (!oilHandler.isOiled()) {
                ci.cancel();
            }
        }
    }
}
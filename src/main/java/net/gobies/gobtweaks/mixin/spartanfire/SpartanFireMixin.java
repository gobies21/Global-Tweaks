package net.gobies.gobtweaks.mixin.spartanfire;

import krelox.spartanfire.SpartanFire;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SpartanFire.class)
public class SpartanFireMixin {

    /**
     * @author gobies
     * @reason remove legendary weapon tooltip
     */
    @SubscribeEvent
    @Overwrite(remap = false)
    public void gatherTooltipComponents(RenderTooltipEvent.GatherComponents event) {
        // do nothing
    }
}

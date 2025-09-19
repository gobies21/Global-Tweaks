package net.gobies.gobtweaks.mixin.vanilla;

import com.google.gson.JsonElement;
import net.gobies.gobtweaks.config.CommonConfig;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.ServerAdvancementManager;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(ServerAdvancementManager.class)
public class ServerAdvancementManagerMixin {
    @Inject(
            method = "apply*",
            at = @At("HEAD")
    )
    // Disables all advancements
    protected void apply(Map<ResourceLocation, JsonElement> pObject, ResourceManager pResourceManager, ProfilerFiller pProfiler, CallbackInfo ci) {
        if (CommonConfig.DISABLE_ADVANCEMENTS.get()) {
            pObject.clear();
        }
    }
}

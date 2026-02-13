package net.gobies.gobtweaks.mixin.combatnouveau;

import fuzs.combatnouveau.config.ServerConfig;
import fuzs.combatnouveau.handler.CombatTestHandler;
import fuzs.combatnouveau.mixin.accessor.ItemAccessor;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CombatTestHandler.class)
public class CombatTestHandlerMixin {
    @Inject(
            method = "setMaxStackSize",
            at = @At("HEAD"),
            remap = false
    )
    private static void setMaxStackSize(ServerConfig serverConfig, CallbackInfo ci) {
        if (serverConfig.increaseStackSize) {
            ((ItemAccessor) Items.SPLASH_POTION).goldenagecombat$setMaxStackSize(16);
            ((ItemAccessor) Items.LINGERING_POTION).goldenagecombat$setMaxStackSize(16);
        }
    }
}

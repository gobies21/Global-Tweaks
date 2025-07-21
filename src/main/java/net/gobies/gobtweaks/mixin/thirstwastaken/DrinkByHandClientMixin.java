package net.gobies.gobtweaks.mixin.thirstwastaken;

import com.github.alexmodguy.alexscaves.server.block.fluid.ACFluidRegistry;
import dev.ghen.thirst.content.thirst.DrinkByHandClient;
import dev.ghen.thirst.foundation.config.ClientConfig;
import dev.ghen.thirst.foundation.network.ThirstModPacketHandler;
import dev.ghen.thirst.foundation.network.message.DrinkByHandMessage;
import dev.ghen.thirst.foundation.util.MathHelper;
import net.gobies.gobtweaks.util.ModLoadedUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

@Mixin(DrinkByHandClient.class)
public class DrinkByHandClientMixin {
    @Inject(
            method = "drinkByHand",
            at = @At("HEAD"),
            remap = false
    )
    // Adds alexs caves compat to thirst was taken for soda fluid
    private static void drinkByHand(CallbackInfo ci) {
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;
        Level level = mc.level;
        BlockPos blockPos = MathHelper.getPlayerPOVHitResult(Objects.requireNonNull(level), Objects.requireNonNull(player), ClipContext.Fluid.ANY).getBlockPos();
        if (ModLoadedUtil.isAlexsCavesLoaded()) {
            if (level.getFluidState(blockPos).is(ACFluidRegistry.PURPLE_SODA_FLUID_SOURCE.get()) && player.isCrouching() && !player.isInvulnerable()) {
                boolean HandAvailable;
                if (!(Boolean) ClientConfig.DRINK_BOTH_HAND_NEEDED.get()) {
                    HandAvailable = player.getItemInHand(InteractionHand.MAIN_HAND).isEmpty();
                } else {
                    HandAvailable = player.getItemInHand(InteractionHand.MAIN_HAND).isEmpty() && player.getItemInHand(InteractionHand.OFF_HAND).isEmpty();
                }

                if (HandAvailable) {
                    level.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.GENERIC_DRINK, SoundSource.NEUTRAL, 1.0F, 1.0F);
                    ThirstModPacketHandler.INSTANCE.sendToServer(new DrinkByHandMessage(blockPos));
                }
            }
        }
    }
}

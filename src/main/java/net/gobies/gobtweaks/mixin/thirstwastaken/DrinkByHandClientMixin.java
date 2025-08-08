package net.gobies.gobtweaks.mixin.thirstwastaken;

import dev.ghen.thirst.content.thirst.DrinkByHandClient;
import dev.ghen.thirst.foundation.config.ClientConfig;
import dev.ghen.thirst.foundation.network.ThirstModPacketHandler;
import dev.ghen.thirst.foundation.network.message.DrinkByHandMessage;
import dev.ghen.thirst.foundation.util.MathHelper;
import net.gobies.gobtweaks.util.ModLoadedUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.FluidState;
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DrinkByHandClient.class)
public class DrinkByHandClientMixin {
    @Inject(
            method = "drinkByHand",
            at = @At("HEAD"),
            remap = false
    )
    // Adds alexs caves compatibility to thirst was taken for soda fluid
    private static void drinkByHand(CallbackInfo ci) {
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;
        Level level = mc.level;
        if (ModLoadedUtil.isAlexsCavesLoaded() && level != null && player!= null) {
        BlockPos blockPos = MathHelper.getPlayerPOVHitResult((level), (player), ClipContext.Fluid.ANY).getBlockPos();
            FluidState fluidState = level.getFluidState(blockPos);
            ResourceLocation fluidLocation = ForgeRegistries.FLUIDS.getKey(fluidState.getType());
            if (fluidLocation != null && fluidLocation.equals(new ResourceLocation("alexscaves", "purple_soda")) && player.isCrouching() && !player.isInvulnerable()) {
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

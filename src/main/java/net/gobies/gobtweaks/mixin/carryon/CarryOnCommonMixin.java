package net.gobies.gobtweaks.mixin.carryon;

import net.gobies.gobtweaks.config.CommonConfig;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import tschipp.carryon.CarryOnCommon;
import tschipp.carryon.common.carry.CarryOnData;
import tschipp.carryon.common.carry.CarryOnDataManager;

@Mixin(CarryOnCommon.class)
public class CarryOnCommonMixin {

    @Invoker(value = "potionLevel", remap = false)
    static int invokePotionLevel(CarryOnData carry, Level level) {
        throw new AssertionError();
    }

    // Custom carry on effect
    @Redirect(
            method = "onCarryTick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/level/ServerPlayer;addEffect(Lnet/minecraft/world/effect/MobEffectInstance;)Z"
            )
    )
    private static boolean configEffect(ServerPlayer serverPlayer, MobEffectInstance mobEffectInstance) {
        CarryOnData carry = CarryOnDataManager.getCarryData(serverPlayer);
        @Nullable MobEffect effectName = ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation(CommonConfig.CARRY_ON_EFFECT.get()));
        if (effectName != null) {
            return serverPlayer.addEffect(new MobEffectInstance(effectName, 2, invokePotionLevel(carry, serverPlayer.level()), false, false));
        } else {
            return false;
        }
    }
}
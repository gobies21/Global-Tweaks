package net.gobies.gobtweaks.mixin.vanilla;

import net.gobies.gobtweaks.config.CommonConfig;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CrossbowItem.class)
public class CrossbowItemMixin {
    @Inject(
            method = "getShootingPower",
            at = @At("TAIL"),
            cancellable = true
    )
    // Crossbow power enchant scaling
    private static void power(ItemStack pCrossbowStack, CallbackInfoReturnable<Float> cir) {
        int powerLevel = pCrossbowStack.getEnchantmentLevel(Enchantments.POWER_ARROWS);
        if (powerLevel > 0 && CommonConfig.EXTRA_CROSSBOWS_ENCHANTS.get()) {
            float newShootingPower = 0.5F * powerLevel + 0.5F;
            cir.setReturnValue(newShootingPower);
        }
    }

    @Inject(
            method = "loadProjectile",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;split(I)Lnet/minecraft/world/item/ItemStack;"),
            cancellable = true
    )
    private static void preventArrowConsumption(LivingEntity pShooter, ItemStack pCrossbowStack, ItemStack pAmmoStack, boolean pHasAmmo, boolean pIsCreative, CallbackInfoReturnable<Boolean> cir) {
        int infinityLevel = pCrossbowStack.getEnchantmentLevel(Enchantments.INFINITY_ARROWS);
        if (infinityLevel > 0 && pAmmoStack.getItem() instanceof ArrowItem && CommonConfig.EXTRA_CROSSBOWS_ENCHANTS.get()) {
            ItemStack itemstack = pAmmoStack.copy();
            gobTweaks$addChargedProjectile(pCrossbowStack, itemstack);
            cir.setReturnValue(true);
        }
    }

    @Inject(
            method = "getArrow",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ArrowItem;createArrow(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/entity/LivingEntity;)Lnet/minecraft/world/entity/projectile/AbstractArrow;"),
            cancellable = true
    )
    private static void shootProjectile(Level pLevel, LivingEntity pLivingEntity, ItemStack pCrossbowStack, ItemStack pAmmoStack, CallbackInfoReturnable<AbstractArrow> cir) {
        int flame = pCrossbowStack.getEnchantmentLevel(Enchantments.FLAMING_ARROWS);
        int punch = pCrossbowStack.getEnchantmentLevel(Enchantments.PUNCH_ARROWS);
        ArrowItem arrowitem = (ArrowItem)(pAmmoStack.getItem() instanceof ArrowItem ? pAmmoStack.getItem() : Items.ARROW);
        AbstractArrow abstractarrow = arrowitem.createArrow(pLevel, pAmmoStack, pLivingEntity);
        if (flame > 0 && CommonConfig.EXTRA_CROSSBOWS_ENCHANTS.get()) {
            abstractarrow.setSecondsOnFire(100);
            cir.setReturnValue(abstractarrow);
        }
        if (punch > 0 && CommonConfig.EXTRA_CROSSBOWS_ENCHANTS.get()) {
            abstractarrow.setKnockback(punch);
            cir.setReturnValue(abstractarrow);
        }
    }

        @Unique
    private static void gobTweaks$addChargedProjectile(ItemStack pCrossbowStack, ItemStack pAmmoStack) {
        CompoundTag compoundtag = pCrossbowStack.getOrCreateTag();
        ListTag listtag;
        if (compoundtag.contains("ChargedProjectiles", 9)) {
            listtag = compoundtag.getList("ChargedProjectiles", 10);
        } else {
            listtag = new ListTag();
        }

        CompoundTag compoundtag1 = new CompoundTag();
        pAmmoStack.save(compoundtag1);
        listtag.add(compoundtag1);
        compoundtag.put("ChargedProjectiles", listtag);
    }
}
package net.gobies.gobtweaks.mixin.vanilla;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import net.gobies.gobtweaks.config.CommonConfig;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraftforge.common.ForgeMod;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.*;
import java.util.function.Consumer;

@Mixin(value = ItemStack.class, priority = 900)
public abstract class ItemStackMixin {

    /**
     * Reorders attribute modifiers in tooltips
     */

    @Redirect(
            method = "getTooltipLines",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/item/ItemStack;getAttributeModifiers(Lnet/minecraft/world/entity/EquipmentSlot;)Lcom/google/common/collect/Multimap;"
            )
    )
    private Multimap<Attribute, AttributeModifier> reorderAttributes(ItemStack stack, EquipmentSlot slot) {
        Multimap<Attribute, AttributeModifier> original = stack.getAttributeModifiers(slot);

        if (original.isEmpty()) return original;
        LinkedHashMultimap<Attribute, AttributeModifier> sorted = LinkedHashMultimap.create();

        if (!(stack.getItem() instanceof ArmorItem)) {
            for (Map.Entry<Attribute, AttributeModifier> entry : original.entries()) {
                if (entry.getKey() == Attributes.ATTACK_DAMAGE) {
                    sorted.put(entry.getKey(), entry.getValue());
                }
            }

            for (Map.Entry<Attribute, AttributeModifier> entry : original.entries()) {
                if (entry.getKey() == Attributes.ATTACK_SPEED) {
                    sorted.put(entry.getKey(), entry.getValue());
                }
            }

            for (Map.Entry<Attribute, AttributeModifier> entry : original.entries()) {
                if (entry.getKey() == ForgeMod.ENTITY_REACH.get()) {
                    sorted.put(entry.getKey(), entry.getValue());
                }
            }

            for (Map.Entry<Attribute, AttributeModifier> entry : original.entries()) {
                if (entry.getKey() != Attributes.ATTACK_DAMAGE && entry.getKey() != Attributes.ATTACK_SPEED && entry.getKey() != ForgeMod.ENTITY_REACH.get()) {
                    sorted.put(entry.getKey(), entry.getValue());
                }
            }

        } else if (stack.getItem() instanceof ArmorItem) {
            for (Map.Entry<Attribute, AttributeModifier> entry : original.entries()) {
                if (entry.getKey() == Attributes.ARMOR) {
                    sorted.put(entry.getKey(), entry.getValue());
                }
            }

            for (Map.Entry<Attribute, AttributeModifier> entry : original.entries()) {
                if (entry.getKey() == Attributes.ARMOR_TOUGHNESS) {
                    sorted.put(entry.getKey(), entry.getValue());
                }
            }

            for (Map.Entry<Attribute, AttributeModifier> entry : original.entries()) {
                if (entry.getKey() == Attributes.KNOCKBACK_RESISTANCE) {
                    sorted.put(entry.getKey(), entry.getValue());
                }
            }

            for (Map.Entry<Attribute, AttributeModifier> entry : original.entries()) {
                if (entry.getKey() != Attributes.ARMOR && entry.getKey() != Attributes.ARMOR_TOUGHNESS && entry.getKey() != Attributes.KNOCKBACK_RESISTANCE) {
                    sorted.put(entry.getKey(), entry.getValue());
                }
            }
        }

        return sorted;
    }

    // Cap max durability damage for armors
    @ModifyVariable(
            method = "hurtAndBreak(ILnet/minecraft/world/entity/LivingEntity;Ljava/util/function/Consumer;)V",
            at = @At("HEAD"),
            argsOnly = true
    )
    private int capDurabilityDamage(int amount, int originalAmount, LivingEntity entity, Consumer<?> onBroken) {
        ItemStack stack = (ItemStack) (Object) this;
        if (!(stack.getItem() instanceof ArmorItem)) {
            return amount;
        }
        int level = EnchantmentHelper.getTagEnchantmentLevel(Enchantments.UNBREAKING, stack);
        int maxArmorDamage = CommonConfig.MAX_ARMOR_DAMAGE.get() - (level * CommonConfig.UNBREAKING_ARMOR_DAMAGE.get());
        int damageCap = Math.max(1, maxArmorDamage);

        return Math.min(amount, damageCap);
    }
}
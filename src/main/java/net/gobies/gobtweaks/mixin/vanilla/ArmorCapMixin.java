package net.gobies.gobtweaks.mixin.vanilla;

import net.gobies.gobtweaks.config.CommonConfig;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.function.Consumer;

@Mixin(value = ItemStack.class, priority = 1000)
public class ArmorCapMixin {

    // Cap max durability damage for armors
    @ModifyVariable(
            method = "hurtAndBreak(ILnet/minecraft/world/entity/LivingEntity;Ljava/util/function/Consumer;)V",
            at = @At("HEAD"),
            argsOnly = true
    )
    private int capDurabilityDamage(int amount, int originalAmount, LivingEntity entity, Consumer<?> onBroken) {
        ItemStack stack = (ItemStack) (Object) this;
        if (!(stack.getItem() instanceof ArmorItem || stack.getItem() instanceof ShieldItem)) {
            return amount;
        }
        int level = EnchantmentHelper.getTagEnchantmentLevel(Enchantments.UNBREAKING, stack);
        int maxArmorDamage = CommonConfig.MAX_ARMOR_DAMAGE.get() - (level * CommonConfig.UNBREAKING_ARMOR_DAMAGE.get());
        int damageCap = Math.max(1, maxArmorDamage);

        return Math.min(amount, damageCap);
    }
}

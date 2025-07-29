package net.gobies.gobtweaks.mixin.dungeoncrawl;

import net.gobies.gobtweaks.CommonConfig;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xiroc.dungeoncrawl.dungeon.monster.RandomEquipment;

@Mixin(RandomEquipment.class)
public class RandomEquipmentMixin {
    @Inject(
            method = "createItemStack",
            at = @At("RETURN"),
            cancellable = true,
            remap = false
    )
    // Adds a chance to give items from dungeon crawl mobs curse of vanishing
    private static void createItemStack(RandomSource rand, Item item, int stage, CallbackInfoReturnable<ItemStack> cir) {
        ItemStack itemStack = cir.getReturnValue();
        if (itemStack != null) {
            if (rand.nextFloat() < CommonConfig.DUNGEON_VANISH_CHANCE.get()) {
                itemStack.enchant(Enchantments.VANISHING_CURSE, 1);
                cir.setReturnValue(itemStack);
            }
        }
    }
}

package net.gobies.gobtweaks.mixin.vanilla;

import java.util.Collection;
import java.util.Objects;
import java.util.Set;

import net.gobies.gobtweaks.config.CommonConfig;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

@Mixin(value = CreativeModeTab.class, priority = 10000)
public abstract class CreativeModeTabMixin {

    @Shadow
    private Collection<ItemStack> displayItems;

    @Shadow
    private Set<ItemStack> displayItemsSearchTab;

    @Inject(
            method = "buildContents",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/CreativeModeTab;rebuildSearchTree()V")
    )
    private void buildContents(CreativeModeTab.ItemDisplayParameters displayContext, CallbackInfo ci) {
        String[] itemsToRemove = CommonConfig.BLACKLIST_CREATIVE_TAB.get().toArray(new String[0]);

        displayItems.removeIf(stack -> gobTweaks$shouldRemove(stack, itemsToRemove));
        displayItemsSearchTab.removeIf(stack -> gobTweaks$shouldRemove(stack, itemsToRemove));
    }

    @Unique
    private boolean gobTweaks$shouldRemove(ItemStack stack, String[] itemsToRemove) {
        if (stack.isEmpty()) return false;
        Item item = stack.getItem();

        // Items
        if (ForgeRegistries.ITEMS.getKey(item) != null) {
            String id = Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(item)).toString();
            for (String removeId : itemsToRemove) {
                if (id.equals(removeId)) return true;
            }
        }

        // Potions
        if (item == Items.POTION || item == Items.SPLASH_POTION || item == Items.LINGERING_POTION || item == Items.TIPPED_ARROW) {
            Potion potion = PotionUtils.getPotion(stack);
            String potionId = Objects.requireNonNull(ForgeRegistries.POTIONS.getKey(potion)).toString();
            for (String removeId : itemsToRemove) {
                if (potionId.equals(removeId)) return true;
            }
        }
        return false;
    }
}
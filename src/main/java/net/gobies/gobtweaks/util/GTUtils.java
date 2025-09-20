package net.gobies.gobtweaks.util;

import com.github.alexthe666.iceandfire.item.IafItemRegistry;
import com.github.alexthe666.iceandfire.item.ItemDragonArmor;
import com.oblivioussp.spartanweaponry.item.ThrowingWeaponItem;
import fuzs.mutantmonsters.world.item.ArmorBlockItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class GTUtils {

    public static final Set<Item> ADDITIONAL_PET_ITEMS = new HashSet<>();
    public static final Set<Item> ADDITIONAL_TOOL_ITEMS = new HashSet<>();


    public static Set<Item> createItemSet(List<? extends String> itemIds) {
        return itemIds.stream()
                .map(ResourceLocation::new)
                .map(ForgeRegistries.ITEMS::getValue)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }

    static {
        if (ModLoadedUtil.isIceandFireLoaded()) {
            ADDITIONAL_PET_ITEMS.add(IafItemRegistry.IRON_HIPPOGRYPH_ARMOR.get());
            ADDITIONAL_PET_ITEMS.add(IafItemRegistry.GOLD_HIPPOGRYPH_ARMOR.get());
            ADDITIONAL_PET_ITEMS.add(IafItemRegistry.DIAMOND_HIPPOGRYPH_ARMOR.get());

            Set<Item> dragonArmorItems = new HashSet<>();
            for (Item item : ForgeRegistries.ITEMS.getValues()) {
                if (item instanceof ItemDragonArmor) {
                    dragonArmorItems.add(item);
                }
            }
            ADDITIONAL_PET_ITEMS.addAll(dragonArmorItems);
        }
        if (ModLoadedUtil.isSpartanWeaponryLoaded()) {
            for (Item item : ForgeRegistries.ITEMS.getValues()) {
                if (item instanceof ThrowingWeaponItem) {
                    ADDITIONAL_TOOL_ITEMS.add(item);
                }
            }
        }
    }

    public static boolean isSkullHelmet(ItemStack stack) {
        Item armor = stack.getItem();
        if (ModLoadedUtil.isMutantMonstersLoaded()) {
            return armor instanceof ArmorBlockItem;
        }
        return false;
    }
}

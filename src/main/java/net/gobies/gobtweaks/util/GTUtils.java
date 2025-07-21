package net.gobies.gobtweaks.util;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class GTUtils {

    public static Set<Item> createItemSet(List<? extends String> itemIds) {
        return itemIds.stream()
                .map(ResourceLocation::new)
                .map(ForgeRegistries.ITEMS::getValue)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }
}

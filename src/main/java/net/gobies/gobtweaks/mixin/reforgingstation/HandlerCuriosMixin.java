package net.gobies.gobtweaks.mixin.reforgingstation;

import com.dplayend.reforgingstation.common.quality.Quality;
import com.dplayend.reforgingstation.handler.HandlerCurios;
import net.gobies.gobtweaks.config.CommonConfig;
import net.gobies.gobtweaks.util.GTUtils;
import net.gobies.gobtweaks.util.QualityHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.ModList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import java.util.*;

@Mixin(HandlerCurios.class)
public class HandlerCuriosMixin {

    @Unique
    private static final Map<Item, Boolean> CURIO_CACHE = new WeakHashMap<>();

    @Unique
    private static final Set<Item> BLACKLIST_CURIO_ITEMS = GTUtils.createItemSet(CommonConfig.BLACKLIST_CURIO_QUALITIES.get());

    @Shadow
    public static boolean isModLoaded() {
        return ModList.get().isLoaded("curios");
    }


    /**
     * @author gobies
     * @reason Curios support to previous changes
     */
    @Overwrite(remap = false)
    public static void randomizerAccessoryQuality(ItemStack stack) {
        stack.getOrCreateTag().putString("quality", QualityHelper.getReforgeQuality(Quality.accessoryQualityList()).quality());
    }

    /**
     * @author gobies
     * @reason Cache curios so curio items don't get constantly ticked
     */
    @Overwrite(remap = false)
    public static boolean accessoryQuality(ItemStack stack) {
        if (stack.isEmpty() || !isModLoaded()) return false;
        Item item = stack.getItem();
        if (BLACKLIST_CURIO_ITEMS.contains(item)) return false;

        return CURIO_CACHE.computeIfAbsent(item, i -> stack.getTags().anyMatch(itemTagKey -> itemTagKey.location().getNamespace().equals("curios")));
    }
}

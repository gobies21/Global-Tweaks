package net.gobies.gobtweaks.mixin.reforgingstation;

import com.dplayend.reforgingstation.common.quality.QualityUtil;
import com.github.alexthe666.iceandfire.item.IafItemRegistry;
import com.github.alexthe666.iceandfire.item.ItemDragonArmor;
import com.oblivioussp.spartanweaponry.item.ThrowingWeaponItem;
import net.gobies.gobtweaks.Config;
import net.gobies.gobtweaks.util.ModLoadedUtil;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Mixin(QualityUtil.class)
public abstract class QualityUtilMixin {

    @Unique
    private static final Set<Item> TOOL_ITEMS = Config.ADD_TOOL_QUALITIES.get().stream()
            .map(ResourceLocation::new)
            .map(ForgeRegistries.ITEMS::getValue)
            .filter(Objects::nonNull)
            .collect(Collectors.toSet());

    @Unique
    private static final Set<Item> SHIELD_ITEMS = Config.ADD_SHIELD_QUALITIES.get().stream()
            .map(ResourceLocation::new)
            .map(ForgeRegistries.ITEMS::getValue)
            .filter(Objects::nonNull)
            .collect(Collectors.toSet());

    @Unique
    private static final Set<Item> BOW_ITEMS = Config.ADD_BOW_QUALITIES.get().stream()
            .map(ResourceLocation::new)
            .map(ForgeRegistries.ITEMS::getValue)
            .filter(Objects::nonNull)
            .collect(Collectors.toSet());

    @Unique
    private static final Set<Item> PET_ARMOR_ITEMS = new HashSet<>();

    static {
        PET_ARMOR_ITEMS.add(IafItemRegistry.IRON_HIPPOGRYPH_ARMOR.get());
        PET_ARMOR_ITEMS.add(IafItemRegistry.GOLD_HIPPOGRYPH_ARMOR.get());
        PET_ARMOR_ITEMS.add(IafItemRegistry.DIAMOND_HIPPOGRYPH_ARMOR.get());

        for (Item item : ForgeRegistries.ITEMS.getValues()) {
            if (item instanceof ItemDragonArmor) {
                PET_ARMOR_ITEMS.add(item);
            }
        }
    }

    @Inject(
            method = "toolQuality",
            at = @At("HEAD"),
            remap = false,
            cancellable = true
    )
    private static void addToolQuality(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        Item tool = stack.getItem();
        if (TOOL_ITEMS.contains(tool)) {
            cir.setReturnValue(true);
            return;
        }
        if (ModLoadedUtil.isSpartanWeaponryLoaded() && tool instanceof ThrowingWeaponItem) {
            cir.setReturnValue(true);
        }
    }

    @Inject(
            method = "shieldQuality",
            at = @At("HEAD"),
            remap = false,
            cancellable = true
    )
    private static void addShieldQuality(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        Item shield = stack.getItem();
        if (SHIELD_ITEMS.contains(shield)) {
            cir.setReturnValue(true);
        }
    }

    @Inject(
            method = "bowQuality",
            at = @At("HEAD"),
            remap = false,
            cancellable = true
    )
    private static void addBowQuality(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        Item bow = stack.getItem();
        if (BOW_ITEMS.contains(bow)) {
            cir.setReturnValue(true);
        }
    }

    @Inject(
            method = "petQuality",
            at = @At("HEAD"),
            remap = false,
            cancellable = true
    )
    private static void addPetQuality(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        Item petArmor = stack.getItem();
        if (ModLoadedUtil.isIceandFireLoaded() && PET_ARMOR_ITEMS.contains(petArmor)) {
            cir.setReturnValue(true);
        }
    }
}
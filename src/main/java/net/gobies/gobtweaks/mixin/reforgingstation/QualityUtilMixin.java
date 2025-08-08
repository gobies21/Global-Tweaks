package net.gobies.gobtweaks.mixin.reforgingstation;

import com.dplayend.reforgingstation.common.quality.QualityUtil;
import com.github.alexthe666.iceandfire.item.IafItemRegistry;
import com.github.alexthe666.iceandfire.item.ItemDragonArmor;
import com.oblivioussp.spartanweaponry.item.ThrowingWeaponItem;
import fuzs.mutantmonsters.world.item.ArmorBlockItem;
import net.gobies.gobtweaks.config.CommonConfig;
import net.gobies.gobtweaks.util.GTUtils;
import net.gobies.gobtweaks.util.ModLoadedUtil;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.HashSet;
import java.util.Set;

@Mixin(QualityUtil.class)
public abstract class QualityUtilMixin {

    @Unique
    private static final Set<Item> TOOL_ITEMS = GTUtils.createItemSet(CommonConfig.ADD_TOOL_QUALITIES.get());

    @Unique
    private static final Set<Item> SHIELD_ITEMS = GTUtils.createItemSet(CommonConfig.ADD_SHIELD_QUALITIES.get());

    @Unique
    private static final Set<Item> BOW_ITEMS = GTUtils.createItemSet(CommonConfig.ADD_BOW_QUALITIES.get());

    @Unique
    private static final Set<Item> PET_ARMOR_ITEMS = new HashSet<>();

    static {
        if (ModLoadedUtil.isIceandFireLoaded()) {
            PET_ARMOR_ITEMS.add(IafItemRegistry.IRON_HIPPOGRYPH_ARMOR.get());
            PET_ARMOR_ITEMS.add(IafItemRegistry.GOLD_HIPPOGRYPH_ARMOR.get());
            PET_ARMOR_ITEMS.add(IafItemRegistry.DIAMOND_HIPPOGRYPH_ARMOR.get());

            Set<Item> dragonArmorItems = new HashSet<>();
            for (Item item : ForgeRegistries.ITEMS.getValues()) {
                if (item instanceof ItemDragonArmor) {
                    dragonArmorItems.add(item);
                }
            }
            PET_ARMOR_ITEMS.addAll(dragonArmorItems);
        }
    }

    // Adds a config for custom weapons to be able to receive qualities
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

    @Inject(
            method = "helmetQuality",
            at = @At("HEAD"),
            remap = false,
            cancellable = true
    )
    private static void addHelmetQuality(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        Item armor = stack.getItem();
        if (ModLoadedUtil.isMutantMonstersLoaded() && armor instanceof ArmorBlockItem) {
            cir.setReturnValue(true);
        }
    }
}
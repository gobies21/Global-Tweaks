package net.gobies.gobtweaks.mixin.reforgingstation;

import com.dplayend.reforgingstation.common.quality.QualityUtil;
import net.gobies.gobtweaks.config.CommonConfig;
import net.gobies.gobtweaks.util.GTUtils;
import net.gobies.gobtweaks.util.ModLoadedUtil;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
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
        PET_ARMOR_ITEMS.addAll(GTUtils.ADDITIONAL_PET_ITEMS);
        TOOL_ITEMS.addAll(GTUtils.ADDITIONAL_TOOL_ITEMS);
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
        if (GTUtils.isSkullHelmet(armor.getDefaultInstance())) {
            cir.setReturnValue(true);
        }
    }
}
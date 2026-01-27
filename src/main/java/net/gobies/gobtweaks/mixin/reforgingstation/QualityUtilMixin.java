package net.gobies.gobtweaks.mixin.reforgingstation;

import com.dplayend.reforgingstation.common.quality.Quality;
import com.dplayend.reforgingstation.common.quality.QualityUtil;
import com.dplayend.reforgingstation.handler.HandlerCurios;
import net.gobies.gobtweaks.config.CommonConfig;
import net.gobies.gobtweaks.util.GTUtils;
import net.gobies.gobtweaks.util.ModLoadedUtil;
import net.gobies.gobtweaks.util.QualityHelper;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.Set;

@Mixin(QualityUtil.class)
public abstract class QualityUtilMixin {

    @Unique
    private static final Set<Item> ADDITIONAL_TOOL_ITEMS = GTUtils.createItemSet(CommonConfig.ADD_TOOL_QUALITIES.get());

    @Unique
    private static final Set<Item> BLACKLIST_TOOL_ITEMS = GTUtils.createItemSet(CommonConfig.BLACKLIST_TOOL_QUALITIES.get());

    @Unique
    private static final Set<Item> ADDITIONAL_SHIELD_ITEMS = GTUtils.createItemSet(CommonConfig.ADD_SHIELD_QUALITIES.get());

    @Unique
    private static final Set<Item> BLACKLIST_SHIELD_ITEMS = GTUtils.createItemSet(CommonConfig.BLACKLIST_SHIELD_QUALITIES.get());

    @Unique
    private static final Set<Item> ADDITIONAL_BOW_ITEMS = GTUtils.createItemSet(CommonConfig.ADD_BOW_QUALITIES.get());

    @Unique
    private static final Set<Item> BLACKLIST_BOW_ITEMS = GTUtils.createItemSet(CommonConfig.BLACKLIST_BOW_QUALITIES.get());

    @Unique
    private static final Set<Item> ADDITIONAL_PET_ARMOR_ITEMS = new HashSet<>();

    static {
        ADDITIONAL_PET_ARMOR_ITEMS.addAll(GTUtils.ADDITIONAL_PET_ITEMS);
        ADDITIONAL_TOOL_ITEMS.addAll(GTUtils.ADDITIONAL_TOOL_ITEMS);
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
        if (ADDITIONAL_TOOL_ITEMS.contains(tool)) {
            cir.setReturnValue(true);
        }
        if (BLACKLIST_TOOL_ITEMS.contains(tool)) {
            cir.setReturnValue(false);
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
        if (ADDITIONAL_SHIELD_ITEMS.contains(shield)) {
            cir.setReturnValue(true);
        }
        if (BLACKLIST_SHIELD_ITEMS.contains(shield)) {
            cir.setReturnValue(false);
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
        if (ADDITIONAL_BOW_ITEMS.contains(bow)) {
            cir.setReturnValue(true);
        }
        if (BLACKLIST_BOW_ITEMS.contains(bow)) {
            cir.setReturnValue(false);
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
        if (ModLoadedUtil.isIceandFireLoaded() && ADDITIONAL_PET_ARMOR_ITEMS.contains(petArmor)) {
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

    @Unique
    private static QualityHelper.Category gobTweaks$resolve(ItemStack stack, @Nullable LivingEntity entity) {
        if (QualityUtil.helmetQuality(stack)) return QualityHelper.Category.HELMET;
        if (QualityUtil.chestPlateQuality(stack)) return QualityHelper.Category.CHESTPLATE;
        if (QualityUtil.leggingsQuality(stack)) return QualityHelper.Category.LEGGINGS;
        if (QualityUtil.bootsQuality(stack)) return QualityHelper.Category.BOOTS;
        if (QualityUtil.petQuality(stack)) return QualityHelper.Category.PET;
        if (QualityUtil.shieldQuality(stack)) return QualityHelper.Category.SHIELD;
        if (QualityUtil.bowQuality(stack)) return QualityHelper.Category.BOW;
        if (QualityUtil.fishingRodQuality(stack)) return QualityHelper.Category.ROD;
        if (QualityUtil.toolQuality(stack)) return QualityHelper.Category.TOOL;

        if (HandlerCurios.accessoryQuality(stack)
                && (entity == null || entity instanceof ServerPlayer)) {
            return QualityHelper.Category.ACCESSORY;
        }

        return QualityHelper.Category.NONE;
    }

    /**
     * @author gobies
     * @reason Performance improvements
     */
    @Overwrite(remap = false)
    public static Quality.QualityType getQualityType(ItemStack stack, LivingEntity entity) {
        QualityHelper.Category category = gobTweaks$resolve(stack, entity);
        return switch (category) {
            case HELMET -> Quality.getQualityType(stack, Quality.helmetQualityList());
            case CHESTPLATE -> Quality.getQualityType(stack, Quality.chestplateQualityList());
            case LEGGINGS -> Quality.getQualityType(stack, Quality.leggingsQualityList());
            case BOOTS -> Quality.getQualityType(stack, Quality.bootsQualityList());
            case PET -> Quality.getQualityType(stack, Quality.petQualityList());
            case SHIELD -> Quality.getQualityType(stack, Quality.shieldQualityList());
            case BOW -> Quality.getQualityType(stack, Quality.bowQualityList());
            case ROD -> Quality.getQualityType(stack, Quality.rodQualityList());
            case TOOL -> Quality.getQualityType(stack, Quality.toolQualityList());
            case ACCESSORY -> Quality.getQualityType(stack, Quality.accessoryQualityList());
            default -> null;
        };
    }

    /**
     * @author gobies
     * @reason Performance improvements
     */
    @Overwrite(remap = false)
    public static String getQuality(ItemStack stack) {
        QualityHelper.Category category = gobTweaks$resolve(stack, null);
        return switch (category) {
            case HELMET -> "helmet";
            case CHESTPLATE -> "chestplate";
            case LEGGINGS -> "leggings";
            case BOOTS -> "boots";
            case PET -> "pet";
            case SHIELD -> "shield";
            case BOW -> "bow";
            case ROD -> "rod";
            case TOOL -> "tool";
            case ACCESSORY -> "accessory";
            default -> "";
        };
    }

    /**
     * @author gobies
     * @reason Performance improvements
     */
    @Overwrite(remap = false)
    public static void createQuality(ItemStack stack) {
        QualityHelper.Category category = gobTweaks$resolve(stack, null);

        switch (category) {
            case HELMET ->
                    Quality.createQualities(stack, Quality.getRandomQuality(Quality.helmetQualityList()).quality());
            case CHESTPLATE ->
                    Quality.createQualities(stack, Quality.getRandomQuality(Quality.chestplateQualityList()).quality());
            case LEGGINGS ->
                    Quality.createQualities(stack, Quality.getRandomQuality(Quality.leggingsQualityList()).quality());
            case BOOTS ->
                    Quality.createQualities(stack, Quality.getRandomQuality(Quality.bootsQualityList()).quality());
            case SHIELD ->
                    Quality.createQualities(stack, Quality.getRandomQuality(Quality.shieldQualityList()).quality());
            case PET ->
                    Quality.createQualities(stack, Quality.getRandomQuality(Quality.petQualityList()).quality());
            case BOW ->
                    Quality.createQualities(stack, Quality.getRandomQuality(Quality.bowQualityList()).quality());
            case ROD ->
                    Quality.createQualities(stack, Quality.getRandomQuality(Quality.rodQualityList()).quality());
            case TOOL ->
                    Quality.createQualities(stack, Quality.getRandomQuality(Quality.toolQualityList()).quality());
            case ACCESSORY ->
                    HandlerCurios.createAccessoryQuality(stack);
            default -> {
                // do nothing
            }
        }
    }
}
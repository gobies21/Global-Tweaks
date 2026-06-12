package net.gobies.gobtweaks.mixin.reforgingstation;

import com.dplayend.reforgingstation.common.quality.Quality;
import net.gobies.gobtweaks.config.reforgingstation.QualityConfig;
import net.gobies.gobtweaks.helper.QualityConfigHelper;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.util.List;

@Mixin(Quality.class)
public class QualityConfigMixin {


    // This code WORKS but is to performance heavy for how this mod is structured
    /**
     * @author gobies
     * @reason Makes operations configurable
     */
    /*
    @Overwrite(remap = false)
    public static AttributeModifier.Operation attributeOperation(Attribute attribute) {
        return QualityConfigHelper.attributeOperation(attribute);
    }

    @Overwrite(remap = false)
    public static List<Quality.QualityType> helmetQualityList() {
        return QualityConfigHelper.parseQualities(QualityConfig.HELMET_QUALITIES);
    }

    @Overwrite(remap = false)
    public static List<Quality.QualityType> chestplateQualityList() {
        return QualityConfigHelper.parseQualities(QualityConfig.CHESTPLATE_QUALITIES);
    }

    @Overwrite(remap = false)
    public static List<Quality.QualityType> leggingsQualityList() {
        return QualityConfigHelper.parseQualities(QualityConfig.LEGGINGS_QUALITIES);
    }

    @Overwrite(remap = false)
    public static List<Quality.QualityType> bootsQualityList() {
        return QualityConfigHelper.parseQualities(QualityConfig.BOOTS_QUALITIES);
    }

    @Overwrite(remap = false)
    public static List<Quality.QualityType> shieldQualityList() {
        return QualityConfigHelper.parseQualities(QualityConfig.SHIELD_QUALITIES);
    }

    @Overwrite(remap = false)
    public static List<Quality.QualityType> petQualityList() {
        return QualityConfigHelper.parseQualities(QualityConfig.PET_QUALITIES);
    }

    @Overwrite(remap = false)
    public static List<Quality.QualityType> bowQualityList() {
        return QualityConfigHelper.parseQualities(QualityConfig.BOW_QUALITIES);
    }

    @Overwrite(remap = false)
    public static List<Quality.QualityType> rodQualityList() {
        return QualityConfigHelper.parseQualities(QualityConfig.ROD_QUALITIES);
    }

    @Overwrite(remap = false)
    public static List<Quality.QualityType> toolQualityList() {
        return QualityConfigHelper.parseQualities(QualityConfig.TOOL_QUALITIES);
    }

    @Overwrite(remap = false)
    public static List<Quality.QualityType> accessoryQualityList() {
        return QualityConfigHelper.parseQualities(QualityConfig.ACCESSORY_QUALITIES);
    }
    */
}
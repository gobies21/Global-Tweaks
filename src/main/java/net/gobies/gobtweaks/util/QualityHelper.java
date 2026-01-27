package net.gobies.gobtweaks.util;

import com.dplayend.reforgingstation.common.quality.Quality;

import java.util.ArrayList;
import java.util.List;

public class QualityHelper {

    public enum Category {
        HELMET,
        CHESTPLATE,
        LEGGINGS,
        BOOTS,
        PET,
        SHIELD,
        BOW,
        ROD,
        TOOL,
        ACCESSORY,
        NONE
    }

    public static Quality.QualityType getReforgeQuality(List<Quality.QualityType> list) {
        List<Quality.QualityType> nonNormalList = new ArrayList<>();
        Quality.QualityType normalQuality = null;

        for (Quality.QualityType qualityType : list) {
            if (qualityType.attributes().length == 0) {
                normalQuality = qualityType;
            } else {
                nonNormalList.add(qualityType);
            }
        }
        if (!nonNormalList.isEmpty()) {
            return nonNormalList.get((int) Math.floor(Math.random() * nonNormalList.size()));
        }

        return normalQuality;
    }
}

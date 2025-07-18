package net.gobies.gobtweaks.util;

import net.minecraftforge.fml.ModList;

public class ModLoadedUtil {

    public static boolean isCuriosLoaded() {
        return ModList.get().isLoaded("curios");
    }

    public static boolean isIronsspellbooksLoaded() {
        return ModList.get().isLoaded("irons_spellbooks");
    }

    public static boolean isReforgingStationLoaded() {
        return ModList.get().isLoaded("reforgingstation");
    }

    public static boolean isCataclysmLoaded() {
        return ModList.get().isLoaded("cataclysm");
    }

    public static boolean isSpartanWeaponryLoaded() {
        return ModList.get().isLoaded("cataclysm");
    }

    public static boolean isIceandFireLoaded() {
        return ModList.get().isLoaded("cataclysm");
    }
}

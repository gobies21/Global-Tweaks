package net.gobies.gobtweaks;

import com.mojang.logging.LogUtils;
import net.gobies.gobtweaks.util.ModLoadedUtil;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(GobTweaks.MOD_ID)
public class GobTweaks {
    public static final String MOD_ID = "gobtweaks";
    public static final Logger LOGGER = LogUtils.getLogger();

    public GobTweaks() {
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();

        MinecraftForge.EVENT_BUS.register(this);

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);

        modBus.addListener(this::commonSetup);
        modBus.addListener(this::clientSetup);

    }
    public void commonSetup(final FMLCommonSetupEvent event) {
        LOGGER.info("Vanilla Mixins loaded to prevent items from being destroyed by lightning");
        LOGGER.info("Swing through grass has been loaded to allow hitting entities through grass without destroying the grass");
        if (ModLoadedUtil.isBlessfulledLoaded() && ModLoadedUtil.isPMLLoaded()) LOGGER.info("Blessfulled compatibility for PML has been loaded to allow critical hits numbers to be displayed");
        if (ModLoadedUtil.isCarryOnLoaded() && ModLoadedUtil.isLocksLoaded()) LOGGER.info("Locks compatibility for carry-on has been loaded to not allow picking up blocks that are locked");
        if (ModLoadedUtil.isFBTLoaded() && ModLoadedUtil.isLocksLoaded()) LOGGER.info("Forgotten Battle Towers compatibility for locks has been loaded to allow using locked chests until they are unlocked in battle towers");
        if (ModLoadedUtil.isJLMELoaded()) LOGGER.info("JLME loaded new config options to change non configurable settings");
        if (ModLoadedUtil.isReforgingStationLoaded()) LOGGER.info("Reforging Station loaded any item with repair material will work as their respective reforge item and option to add any tool to be allowed to receive qualities");
        if (ModLoadedUtil.isReforgingStationLoaded() && ModLoadedUtil.isIronsspellbooksLoaded()) LOGGER.info("Reforging Station loaded new qualities for irons spellbooks and option to add any tool to be allowed to receive qualities");
        if (ModLoadedUtil.isReforgingStationLoaded() && ModLoadedUtil.isCataclysmLoaded()) LOGGER.info("Reforging station compatibility for Cataclysm has been loaded to allow all weapons from cataclysm to receive qualities");
        if (ModLoadedUtil.isRefurbishedFurnitureLoaded()) LOGGER.info("Refurbished Furniture loaded configs for fridges and crates to increase rows");
        if (ModLoadedUtil.isIceandFireLoaded()) LOGGER.info("Ice and Fire loaded fixed wrong armor values for dragon scale armor");
        if (ModLoadedUtil.isIceandFireLoaded() && ModLoadedUtil.isDynamicTreesLoaded()) LOGGER.info("Dynamic Trees compatibility for ice and fire has been loaded to allow dragons to destroy dynamic trees");
        if (ModLoadedUtil.isDungeonCrawlLoaded()) LOGGER.info("Dungeon Crawl loaded new config option to set a chance for geared mobs to spawn with curse of vanishing on their gear");
    }

    public void clientSetup(final FMLClientSetupEvent event) {
        if (ModLoadedUtil.isThirstLoaded() && ModLoadedUtil.isAlexsCavesLoaded()) LOGGER.info("Thirst was taken compatibility for Alex's Caves has been loaded to allow hand drinking from soda fluids");
    }
}
package net.gobies.gobtweaks.config;

import net.gobies.gobtweaks.GobTweaks;
import net.gobies.gobtweaks.util.BranchEnum;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

import java.util.List;

@Mod.EventBusSubscriber(modid = GobTweaks.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CommonConfig {
    private static final String FILENAME = "gobtweaks-common.toml";

    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static ForgeConfigSpec.ConfigValue<Boolean> LIGHTNING_DESTROY_ITEM;
    public static boolean lightning_destroy_item;
    public static ForgeConfigSpec.ConfigValue<Boolean> GRASS_ATTACK;
    public static boolean grass_attack;
    public static ForgeConfigSpec.ConfigValue<Boolean> NO_FIRE_OVERLAY;
    public static boolean no_fire_overlay;
    public static ForgeConfigSpec.ConfigValue<Boolean> EXTRA_CROSSBOWS_ENCHANTS;
    public static boolean extra_crossbows_enchants;
    public static ForgeConfigSpec.ConfigValue<Boolean> DISABLE_ADVANCEMENTS;
    public static boolean disable_advancements;
    public static ForgeConfigSpec.ConfigValue<Boolean> ALWAYS_PICKUP;
    public static boolean always_pickup;
    public static ForgeConfigSpec.ConfigValue<Boolean> FRIENDLY_FIRE;
    public static boolean friendly_fire;
    public static ForgeConfigSpec.ConfigValue<Boolean> CRATE_INCREASE_ROWS;
    public static boolean crates_increase_rows;
    public static ForgeConfigSpec.ConfigValue<Double> SUPREME_SHARPNESS_BASE_DAMAGE;
    public static float supreme_sharpness_base_damage;
    public static ForgeConfigSpec.ConfigValue<Double> SUPREME_SHARPNESS_LEVEL_DAMAGE;
    public static float supreme_sharpness_level_damage;
    public static ForgeConfigSpec.ConfigValue<Boolean> TRADABLE_GLOBAL_ENCHANTMENTS;
    public static boolean tradable_global_enchantments;
    public static ForgeConfigSpec.ConfigValue<Boolean> TRADABLE_RUNE_ENCHANTMENTS;
    public static boolean tradable_rune_enchantments;
    public static ForgeConfigSpec.ConfigValue<Boolean> MAGE_QUALITIES;
    public static boolean mage_qualities;
    public static ForgeConfigSpec.ConfigValue<List<? extends String>> ADD_TOOL_QUALITIES;
    public static List<? extends String> add_tool_qualities;
    public static ForgeConfigSpec.ConfigValue<List<? extends String>> ADD_SHIELD_QUALITIES;
    public static List<? extends String> add_shield_qualities;
    public static ForgeConfigSpec.ConfigValue<List<? extends String>> ADD_BOW_QUALITIES;
    public static List<? extends String> add_bow_qualities;
    public static ForgeConfigSpec.ConfigValue<List<? extends String>> BLACKLIST_TOOL_QUALITIES;
    public static List<? extends String> blacklist_tool_qualities;
    public static ForgeConfigSpec.ConfigValue<List<? extends String>> BLACKLIST_SHIELD_QUALITIES;
    public static List<? extends String> blacklist_shield_qualities;
    public static ForgeConfigSpec.ConfigValue<List<? extends String>> BLACKLIST_BOW_QUALITIES;
    public static List<? extends String> blacklist_bow_qualities;
    public static ForgeConfigSpec.ConfigValue<List<? extends String>> ENTITY_QUALITIES;
    public static List<? extends String> entity_qualities;
    public static ForgeConfigSpec.ConfigValue<Boolean> VALID_REPAIR_MATERIALS;
    public static boolean valid_repair_materials;
    public static ForgeConfigSpec.ConfigValue<Double> NO_QUALITY_CHANCE;
    public static float no_quality_chance;
    public static ForgeConfigSpec.ConfigValue<Double> DUNGEON_VANISH_CHANCE;
    public static float dungeon_vanish_chance;
    public static ForgeConfigSpec.ConfigValue<BranchEnum> BREAK_BRANCH_TYPE;
    public static BranchEnum break_branch_type;
    public static ForgeConfigSpec.ConfigValue<Boolean> DRAGON_FIRE_IMMUNE;
    public static boolean dragon_fire_immune;
    public static ForgeConfigSpec.ConfigValue<Boolean> DRAGON_ICE_IMMUNE;
    public static boolean dragon_ice_immune;
    public static ForgeConfigSpec.ConfigValue<Boolean> DRAGON_ENCHANTED_GLOW;
    public static boolean dragon_enchanted_glow;
    public static ForgeConfigSpec.ConfigValue<Double> ADDITIONAL_STICK_CHANCE;
    public static float additional_stick_chance;
    public static ForgeConfigSpec.ConfigValue<Boolean> DISABLE_OIL_TOOLTIP;
    public static boolean disable_oil_tooltip;
    public static ForgeConfigSpec.ConfigValue<Boolean> THIRST_ATTACK_EXHAUSTION;
    public static boolean thirst_attack_exhaustion;
    public static ForgeConfigSpec.ConfigValue<List<? extends String>> BLACKLIST_CREATIVE_TAB;
    public static List<? extends String> blacklist_creative_tab;

    public static ForgeConfigSpec.ConfigValue<Boolean> DEBUG;
    public static boolean debug;


    public CommonConfig() {
    }

    @SubscribeEvent
    static void onLoad(ModConfigEvent.Loading configEvent) {
        if (configEvent.getConfig().getFileName().equals(FILENAME)) {
            lightning_destroy_item = LIGHTNING_DESTROY_ITEM.get();
            grass_attack = GRASS_ATTACK.get();
            no_fire_overlay = NO_FIRE_OVERLAY.get();
            extra_crossbows_enchants = EXTRA_CROSSBOWS_ENCHANTS.get();
            disable_advancements = DISABLE_ADVANCEMENTS.get();
            always_pickup = ALWAYS_PICKUP.get();
            friendly_fire = FRIENDLY_FIRE.get();
            crates_increase_rows = CRATE_INCREASE_ROWS.get();
            supreme_sharpness_base_damage = SUPREME_SHARPNESS_BASE_DAMAGE.get().floatValue();
            supreme_sharpness_level_damage = SUPREME_SHARPNESS_LEVEL_DAMAGE.get().floatValue();
            tradable_global_enchantments = TRADABLE_GLOBAL_ENCHANTMENTS.get();
            tradable_rune_enchantments = TRADABLE_RUNE_ENCHANTMENTS.get();
            mage_qualities = MAGE_QUALITIES.get();
            add_tool_qualities = ADD_TOOL_QUALITIES.get();
            add_shield_qualities = ADD_SHIELD_QUALITIES.get();
            add_bow_qualities = ADD_BOW_QUALITIES.get();
            blacklist_tool_qualities = BLACKLIST_TOOL_QUALITIES.get();
            blacklist_shield_qualities = BLACKLIST_SHIELD_QUALITIES.get();
            blacklist_bow_qualities = BLACKLIST_BOW_QUALITIES.get();
            entity_qualities = ENTITY_QUALITIES.get();
            valid_repair_materials = VALID_REPAIR_MATERIALS.get();
            no_quality_chance = NO_QUALITY_CHANCE.get().floatValue();
            dungeon_vanish_chance = DUNGEON_VANISH_CHANCE.get().floatValue();
            break_branch_type = BREAK_BRANCH_TYPE.get();
            dragon_fire_immune = DRAGON_FIRE_IMMUNE.get();
            dragon_ice_immune = DRAGON_ICE_IMMUNE.get();
            dragon_enchanted_glow = DRAGON_ENCHANTED_GLOW.get();
            additional_stick_chance = ADDITIONAL_STICK_CHANCE.get().floatValue();
            disable_oil_tooltip = DISABLE_OIL_TOOLTIP.get();
            thirst_attack_exhaustion = THIRST_ATTACK_EXHAUSTION.get();
            blacklist_creative_tab = BLACKLIST_CREATIVE_TAB.get();
            debug = DEBUG.get();
        }
    }

    static {
        BUILDER.push("General");
        BLACKLIST_CREATIVE_TAB = BUILDER.comment("List items to blacklist from the creative tab").defineList("Creative_Blacklist", List.of(), o -> true);
        DEBUG = BUILDER.comment("Enable debug mode (experimental features)").define("Debug", false);
        BUILDER.pop();

        BUILDER.push("Vanilla");
        LIGHTNING_DESTROY_ITEM = BUILDER.comment("Prevents lightning from destroying items").define("Lightning_Prevention", true);
        GRASS_ATTACK = BUILDER.comment("Allows attacking through grass without breaking it").define("Attack_Through_Grass", true);
        NO_FIRE_OVERLAY = BUILDER.comment("Makes having fire resistance or being in creative not allow entities to get set on fire").define("No_Fire_Overlay", true);
        EXTRA_CROSSBOWS_ENCHANTS = BUILDER.comment("Allows crossbows to be enchanted with normal bow enchantments").define("Extra_Crossbows_Enchants", false);
        DISABLE_ADVANCEMENTS = BUILDER.comment("Disables all advancements from the game").define("Disable_Advancements", false);
        ALWAYS_PICKUP = BUILDER.comment("Allows mobs to always pickup dropped items").define("Always_Pickup", false);
        FRIENDLY_FIRE = BUILDER.comment("Disables being able to attack your own tames").define("Friendly_Fire", true);
        BUILDER.pop();

        BUILDER.push("Refurbished_Furniture");
        CRATE_INCREASE_ROWS = BUILDER.comment("Increase crate maximum rows from 3 to 6").define("Increase_Crate_Rows", true);
        BUILDER.pop();

        BUILDER.push("JLME");
        SUPREME_SHARPNESS_BASE_DAMAGE = BUILDER.comment("Supreme sharpness base damage").define("Supreme_Sharpness_Base_Damage", 4.0);
        SUPREME_SHARPNESS_LEVEL_DAMAGE = BUILDER.comment("Supreme sharpness damage per level").define("Supreme_Sharpness_Level_Damage", 2.0);
        TRADABLE_GLOBAL_ENCHANTMENTS = BUILDER.comment("Makes all non rune enchantments tradable").define("Tradable_Enchantments", false);
        TRADABLE_RUNE_ENCHANTMENTS = BUILDER.comment("Makes all rune enchantments tradable").define("Tradable_Runes", false);
        BUILDER.pop();

        BUILDER.push("Reforging_Station");
        NO_QUALITY_CHANCE = BUILDER.comment("Chance that items do not receive a quality, higher values makes qualities rarer").defineInRange("Normal_Chance", 0.5, 0.0, 1.0);
        MAGE_QUALITIES = BUILDER.comment("Adds new qualities for irons spellbooks").define("Irons_Spellbooks_Mage_Qualities", true);
        ADD_TOOL_QUALITIES = BUILDER.comment("List of tools/weapons to be able to receive qualities").defineList("Tool_Qualities", List.of("cataclysm:the_incinerator", "cataclysm:gauntlet_of_guard", "cataclysm:gauntlet_of_bulwark", "cataclysm:gauntlet_of_maelstrom", "cataclysm:soul_render", "cataclysm:the_annihilator", "cataclysm:the_immolator", "cataclysm:tidal_claws", "cataclysm:coral_spear", "cataclysm:coral_bardiche", "cataclysm:meat_shredder", "cataclysm:astrape", "cataclysm:ceraunus", "cataclysm:ancient_spear"), o -> true);
        ADD_SHIELD_QUALITIES = BUILDER.comment("List of shields to be able to receive qualities").defineList("Shield_Qualities", List.of("cataclysm:bulwark_of_the_flame"), o -> true);
        ADD_BOW_QUALITIES = BUILDER.comment("List of bows to be able to receive qualities").defineList("Bow_Qualities", List.of(), o -> true);
        BLACKLIST_TOOL_QUALITIES = BUILDER.comment("List of tools/weapons to be unable to receive qualities").defineList("Blacklisted_Tool_Qualities", List.of(), o -> true);
        BLACKLIST_BOW_QUALITIES = BUILDER.comment("List of bows to be unable to receive qualities").defineList("Blacklisted_Bow_Qualities", List.of(), o -> true);
        BLACKLIST_SHIELD_QUALITIES = BUILDER.comment("List of shields to be unable to receive qualities").defineList("Blacklisted_Shield_Qualities", List.of(), o -> true);
        ENTITY_QUALITIES = BUILDER.comment("List of entities to be able to receive qualities").defineList("Entity_Qualities", List.of("minecraft:horse"), o -> true);
        VALID_REPAIR_MATERIALS = BUILDER.comment("Allows all reforge-able gear to pull from their repair materials for reforging").define("Valid_Repair_Materials", true);
        BUILDER.pop();

        BUILDER.push("Dungeon_Crawl");
        DUNGEON_VANISH_CHANCE = BUILDER.comment("Chance for gear to spawn with curse of vanishing in dungeon crawl dungeons").defineInRange("Vanish_Chance", 0.1, 0.0, 1.0);
        BUILDER.pop();

        BUILDER.push("Ice_and_Fire");
        BREAK_BRANCH_TYPE = BUILDER.comment("Size of the branches that ice and fire dragons are allowed to destroy").defineEnum("Dynamic_Tree_Branch", BranchEnum.ONLY_BRANCHES, BranchEnum.values());
        DRAGON_FIRE_IMMUNE = BUILDER.comment("Makes fire resistance give immunity to fire dragon breath damage").define("Dragon_Fire_Immune", true);
        DRAGON_ICE_IMMUNE = BUILDER.comment("Makes ice resistance from cold sweat give immunity to ice dragon breath damage").define("Dragon_Ice_Immune", true);
        DRAGON_ENCHANTED_GLOW = BUILDER.comment("Makes blooded dragon bone weapons have enchanted glow").define("Dragon_Enchanted_Glow", false);
        BUILDER.pop();

        BUILDER.push("Dynamic_Trees");
        ADDITIONAL_STICK_CHANCE = BUILDER.comment("Chance for additional sticks to drop from dynamic tree leaves").defineInRange("Additional_Stick_Chance", 0.1, 0.0, 1.0);
        BUILDER.pop();

        BUILDER.push("Spartan_Weaponry");
        DISABLE_OIL_TOOLTIP = BUILDER.comment("Disable the oil tooltip that displays on weapons without weapon oil applied").define("Disable_Oil_Tooltip", false);
        BUILDER.pop();

        BUILDER.push("Thirst_was_Taken");
        THIRST_ATTACK_EXHAUSTION = BUILDER.comment("Makes attacking entities slightly exhaust thirst").define("Thirst_Attack_Exhaustion", true);
        BUILDER.pop();

        SPEC = BUILDER.build();
    }
}
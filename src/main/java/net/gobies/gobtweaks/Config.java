package net.gobies.gobtweaks;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

import java.util.List;

@Mod.EventBusSubscriber(modid = GobTweaks.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static ForgeConfigSpec.ConfigValue<Boolean> CRATE_INCREASE_ROWS;
    public static boolean crates_increase_rows;
    public static ForgeConfigSpec.ConfigValue<Boolean> FRIDGE_INCREASE_ROWS;
    public static boolean fridge_increase_rows;
    public static ForgeConfigSpec.ConfigValue<Boolean> LIGHTNING_DESTROY_ITEM;
    public static boolean lightning_destroy_item;
    public static ForgeConfigSpec.ConfigValue<Boolean> GRASS_ATTACK;
    public static boolean grass_attack;
    public static ForgeConfigSpec.ConfigValue<Double> SUPREME_SHARPNESS_BASE_DAMAGE;
    public static float supreme_sharpness_base_damage;
    public static ForgeConfigSpec.ConfigValue<Double> SUPREME_SHARPNESS_LEVEL_DAMAGE;
    public static float supreme_sharpness_level_damage;
    public static ForgeConfigSpec.ConfigValue<Boolean> MAGE_QUALITIES;
    public static boolean mage_qualities;
    public static ForgeConfigSpec.ConfigValue<List<? extends String>> ADD_TOOL_QUALITIES;
    public static List<? extends String> add_tool_qualities;
    public static ForgeConfigSpec.ConfigValue<List<? extends String>> ADD_SHIELD_QUALITIES;
    public static List<? extends String> add_shield_qualities;
    public static ForgeConfigSpec.ConfigValue<List<? extends String>> ADD_BOW_QUALITIES;
    public static List<? extends String> add_bow_qualities;
    public static ForgeConfigSpec.ConfigValue<Double> DUNGEON_VANISH_CHANCE;
    public static float dungeon_vanish_chance;


    public Config() {
    }

    @SubscribeEvent
    static void onLoad(ModConfigEvent.Loading configEvent) {
        crates_increase_rows = CRATE_INCREASE_ROWS.get();
        fridge_increase_rows = FRIDGE_INCREASE_ROWS.get();
        lightning_destroy_item = LIGHTNING_DESTROY_ITEM.get();
        grass_attack = GRASS_ATTACK.get();
        supreme_sharpness_base_damage = SUPREME_SHARPNESS_BASE_DAMAGE.get().floatValue();
        supreme_sharpness_level_damage = SUPREME_SHARPNESS_LEVEL_DAMAGE.get().floatValue();
        mage_qualities = MAGE_QUALITIES.get();
        add_tool_qualities = ADD_TOOL_QUALITIES.get();
        add_shield_qualities = ADD_SHIELD_QUALITIES.get();
        add_bow_qualities = ADD_BOW_QUALITIES.get();
        dungeon_vanish_chance = DUNGEON_VANISH_CHANCE.get().floatValue();
    }

    static {
        BUILDER.push("Vanilla");
        LIGHTNING_DESTROY_ITEM = BUILDER.comment("Prevents lightning from destroying items").define("Prevention", true);
        GRASS_ATTACK = BUILDER.comment("Allows attacking through grass without breaking it").define("Attack_Through_Grass", true);
        BUILDER.pop();

        BUILDER.push("Refurbished_Furniture");
        CRATE_INCREASE_ROWS = BUILDER.comment("Increase crate maximum rows from 3 to 6").define("Increase_Crate_Rows", true);
        FRIDGE_INCREASE_ROWS = BUILDER.comment("Increase fridge maximum rows from 3 to 6").define("Increase_Fridge_Rows", false);
        BUILDER.pop();

        BUILDER.push("JLME");
        SUPREME_SHARPNESS_BASE_DAMAGE = BUILDER.comment("Supreme sharpness base damage").define("Base_Damage", 4.0);
        SUPREME_SHARPNESS_LEVEL_DAMAGE = BUILDER.comment("Supreme sharpness damage per level").define("Level_Damage", 2.0);
        BUILDER.pop();

        BUILDER.push("Reforging_Station");
        MAGE_QUALITIES = BUILDER.comment("Adds new qualities for irons spellbooks").define("Mage_Qualities", true);
        ADD_TOOL_QUALITIES = BUILDER.comment("List of tools/weapons to be able to receive qualities").defineList("Tool_Qualities", List.of("cataclysm:the_incinerator", "cataclysm:gauntlet_of_guard", "cataclysm:gauntlet_of_bulwark", "cataclysm:gauntlet_of_maelstrom", "cataclysm:soul_render", "cataclysm:the_annihilator", "cataclysm:the_immolator", "cataclysm:tidal_claws", "cataclysm:coral_spear", "cataclysm:coral_bardiche", "cataclysm:meat_shredder", "cataclysm:astrape", "cataclysm:ceraunus", "cataclysm:ancient_spear"), o -> true);
        ADD_SHIELD_QUALITIES = BUILDER.comment("List of shields to be able to receive qualities").defineList("Shield_Qualities", List.of("cataclysm:bulwark_of_the_flame"), o -> true);
        ADD_BOW_QUALITIES = BUILDER.comment("List of bows to be able to receive qualities").defineList("Bow_Qualities", List.of(), o -> true);
        BUILDER.pop();

        BUILDER.push("Dungeon_Crawl");
        DUNGEON_VANISH_CHANCE = BUILDER.comment("Chance for gear to spawn with curse of vanishing in dungeon crawl dungeons").defineInRange("Chance", 0.1, 0.0, 1.0);
        BUILDER.pop();

        SPEC = BUILDER.build();
    }
}
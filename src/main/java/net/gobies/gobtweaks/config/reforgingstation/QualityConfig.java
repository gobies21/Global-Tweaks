package net.gobies.gobtweaks.config.reforgingstation;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.gobies.gobtweaks.GobTweaks;
import net.minecraftforge.fml.loading.FMLPaths;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class QualityConfig {

    // Unfortunately this does work perfectly fine but with how this mod is structured its way to performance heavy to implement a config like this into it,
    // On the bright side ive learned how to create json configs!
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
    private static final File JSON_FILE = new File(FMLPaths.CONFIGDIR.get().toFile(), "reforgingstation/modifiers.json");

    public static List<String> HELMET_QUALITIES = new ArrayList<>();
    public static List<String> CHESTPLATE_QUALITIES = new ArrayList<>();
    public static List<String> LEGGINGS_QUALITIES = new ArrayList<>();
    public static List<String> BOOTS_QUALITIES = new ArrayList<>();
    public static List<String> SHIELD_QUALITIES = new ArrayList<>();
    public static List<String> PET_QUALITIES = new ArrayList<>();
    public static List<String> BOW_QUALITIES = new ArrayList<>();
    public static List<String> ROD_QUALITIES = new ArrayList<>();
    public static List<String> TOOL_QUALITIES = new ArrayList<>();
    public static List<String> ACCESSORY_QUALITIES = new ArrayList<>();

    public static List<String> ATTRIBUTE_OPERATIONS = new ArrayList<>();

    public static void loadJsonConfig() {
        if (!JSON_FILE.getParentFile().exists()) {
            boolean directoryCreated = JSON_FILE.getParentFile().mkdirs();
            if (!directoryCreated && !JSON_FILE.getParentFile().exists()) {
                GobTweaks.LOGGER.error("Failed to create configuration file");
                return;
            }
        }

        if (!JSON_FILE.exists()) {
            createDefaultJson();
        }

        try (FileReader reader = new FileReader(JSON_FILE)) {
            JsonObject json = GSON.fromJson(reader, JsonObject.class);
            if (json != null) {
                HELMET_QUALITIES = getList(json, "helmet_qualities");
                CHESTPLATE_QUALITIES = getList(json, "chestplate_qualities");
                LEGGINGS_QUALITIES = getList(json, "leggings_qualities");
                BOOTS_QUALITIES = getList(json, "boots_qualities");
                SHIELD_QUALITIES = getList(json, "shield_qualities");
                PET_QUALITIES = getList(json, "pet_qualities");
                BOW_QUALITIES = getList(json, "bow_qualities");
                ROD_QUALITIES = getList(json, "rod_qualities");
                TOOL_QUALITIES = getList(json, "tool_qualities");
                ACCESSORY_QUALITIES = getList(json, "accessory_qualities");
                ATTRIBUTE_OPERATIONS = getList(json, "attribute_operations");
            }
        } catch (IOException e) {
            GobTweaks.LOGGER.error("Error parsing JSON file");
        }
    }

    private static void createDefaultJson() {
        JsonObject json = new JsonObject();
        json.add("helmet_qualities", toJsonArray(DEFAULTS_HELMET));
        json.add("chestplate_qualities", toJsonArray(DEFAULTS_CHESTPLATE));
        json.add("leggings_qualities", toJsonArray(DEFAULTS_LEGGINGS));
        json.add("boots_qualities", toJsonArray(DEFAULTS_BOOTS));
        json.add("shield_qualities", toJsonArray(DEFAULTS_SHIELD));
        json.add("pet_qualities", toJsonArray(DEFAULTS_PET));
        json.add("bow_qualities", toJsonArray(DEFAULTS_BOW));
        json.add("rod_qualities", toJsonArray(DEFAULTS_ROD));
        json.add("tool_qualities", toJsonArray(DEFAULTS_TOOL));
        json.add("accessory_qualities", toJsonArray(DEFAULTS_ACCESSORY));

        String[] defaultAttributeOps = {
                "minecraft:generic.armor;ADDITION",
                "minecraft:generic.armor_toughness;ADDITION",
                "minecraft:generic.luck;ADDITION",
                "minecraft:generic.max_health;ADDITION",
                "reforgingstation:magic_resist;ADDITION",
                "reforgingstation:jump_height;ADDITION",
                "reforgingstation:step_height;ADDITION",
                "reforgingstation:block_reach;ADDITION",
                "reforgingstation:entity_reach;ADDITION"
        };
        json.add("attribute_operations", toJsonArray(defaultAttributeOps));

        try (FileWriter writer = new FileWriter(JSON_FILE)) {
            GSON.toJson(json, writer);
        } catch (IOException e) {
            GobTweaks.LOGGER.error("Failed to write JSON file");
        }

        HELMET_QUALITIES = jsonArrayToList(json.getAsJsonArray("helmet_qualities"));
        CHESTPLATE_QUALITIES = jsonArrayToList(json.getAsJsonArray("chestplate_qualities"));
        LEGGINGS_QUALITIES = jsonArrayToList(json.getAsJsonArray("leggings_qualities"));
        BOOTS_QUALITIES = jsonArrayToList(json.getAsJsonArray("boots_qualities"));
        SHIELD_QUALITIES = jsonArrayToList(json.getAsJsonArray("shield_qualities"));
        PET_QUALITIES = jsonArrayToList(json.getAsJsonArray("pet_qualities"));
        BOW_QUALITIES = jsonArrayToList(json.getAsJsonArray("bow_qualities"));
        ROD_QUALITIES = jsonArrayToList(json.getAsJsonArray("rod_qualities"));
        TOOL_QUALITIES = jsonArrayToList(json.getAsJsonArray("tool_qualities"));
        ACCESSORY_QUALITIES = jsonArrayToList(json.getAsJsonArray("accessory_qualities"));
        ATTRIBUTE_OPERATIONS = jsonArrayToList(json.getAsJsonArray("attribute_operations"));
    }

    private static List<String> getList(JsonObject json, String key) {
        return json.has(key) ? jsonArrayToList(json.getAsJsonArray(key)) : new ArrayList<>();
    }

    private static JsonArray toJsonArray(String[] array) {
        JsonArray jsonArray = new JsonArray();
        for (String element : array) jsonArray.add(element);
        return jsonArray;
    }

    private static List<String> jsonArrayToList(JsonArray array) {
        List<String> list = new ArrayList<>();
        if (array != null) array.forEach(element -> list.add(element.getAsString()));
        return list;
    }

    private static final String[] DEFAULTS_HELMET = {
            "crumbled;DARK_RED;minecraft:generic.armor=-1.0,reforgingstation:magic_resist=-1.5,minecraft:generic.armor_toughness=-1.0",
            "dented;DARK_GRAY;minecraft:generic.armor=-1.0",
            "heavy;RED;minecraft:generic.movement_speed=-0.1",
            "normal;GRAY;",
            "thick;YELLOW;minecraft:generic.armor=1.0,minecraft:generic.movement_speed=-0.05",
            "tough;BLUE;minecraft:generic.armor_toughness=1.0",
            "protective;BLUE;minecraft:generic.armor=2.0",
            "arcane;BLUE;reforgingstation:magic_resist=1.0",
            "lucky;AQUA;minecraft:generic.luck=0.5",
            "masterful;LIGHT_PURPLE;minecraft:generic.armor=1.0,minecraft:generic.armor_toughness=1.0,minecraft:generic.luck=0.5"
    };

    private static final String[] DEFAULTS_CHESTPLATE = {
            "crumbled;DARK_RED;minecraft:generic.armor=-2.0,reforgingstation:magic_resist=-1.0,minecraft:generic.armor_toughness=-1.0",
            "cumbersome;DARK_GRAY;reforgingstation:dig_speed=-0.1,minecraft:generic.attack_speed=-0.1",
            "dented;DARK_GRAY;minecraft:generic.armor=-2.0",
            "heavy;RED;minecraft:generic.movement_speed=-0.1",
            "normal;GRAY;",
            "thick;YELLOW;minecraft:generic.armor=1.0,minecraft:generic.movement_speed=-0.05",
            "tough;BLUE;minecraft:generic.armor_toughness=1.0",
            "protective;BLUE;minecraft:generic.armor=1.0",
            "arcane;BLUE;reforgingstation:magic_resist=1.0",
            "solid;BLUE;minecraft:generic.knockback_resistance=0.5",
            "masterful;LIGHT_PURPLE;minecraft:generic.armor=2.0,minecraft:generic.armor_toughness=1.0,minecraft:generic.knockback_resistance=0.5"
    };

    private static final String[] DEFAULTS_LEGGINGS = {
            "crumbled;DARK_RED;minecraft:generic.armor=-1.5,minecraft:generic.armor_toughness=-1.0,reforgingstation:magic_resist=-1.0",
            "dented;DARK_GRAY;minecraft:generic.armor=-1.5",
            "heavy;RED;minecraft:generic.movement_speed=-0.1",
            "normal;GRAY;",
            "thick;YELLOW;minecraft:generic.armor=1.0,minecraft:generic.movement_speed=-0.05",
            "tough;BLUE;minecraft:generic.armor_toughness=1.0",
            "protective;BLUE;minecraft:generic.armor=1.0",
            "arcane;BLUE;reforgingstation:magic_resist=1.0",
            "springy;BLUE;reforgingstation:jump_height=0.5",
            "masterful;LIGHT_PURPLE;minecraft:generic.armor=1.5,minecraft:generic.armor_toughness=1.0,reforgingstation:jump_height=0.5"
    };

    private static final String[] DEFAULTS_BOOTS = {
            "crumbled;DARK_RED;minecraft:generic.armor=-1.0,minecraft:generic.armor_toughness=-0.5,reforgingstation:magic_resist=-0.5",
            "dented;DARK_GRAY;minecraft:generic.armor=-1.0",
            "heavy;RED;minecraft:generic.movement_speed=-0.1",
            "normal;GRAY;",
            "thick;YELLOW;minecraft:generic.armor=1.0,minecraft:generic.movement_speed=-0.05",
            "tough;BLUE;minecraft:generic.armor_toughness=-1.0",
            "protective;BLUE;minecraft:generic.armor=1.0",
            "arcane;BLUE;reforgingstation:magic_resist=1.0",
            "tall;BLUE;reforgingstation:step_height=0.5",
            "speedy;BLUE;minecraft:generic.movement_speed=0.1",
            "masterful;LIGHT_PURPLE;minecraft:generic.armor=1.0,minecraft:generic.armor_toughness=1.0,minecraft:generic.movement_speed=0.1,reforgingstation:step_height=0.5"
    };

    private static final String[] DEFAULTS_SHIELD = {
            "worthless;DARK_RED;minecraft:generic.armor=-1.0,minecraft:generic.movement_speed=-0.1",
            "heavy;RED;minecraft:generic.movement_speed=-0.1",
            "damaged;RED;minecraft:generic.armor=-1.5",
            "normal;GRAY;",
            "thick;YELLOW;minecraft:generic.armor=0.5,minecraft:generic.movement_speed=-0.05",
            "protective;BLUE;minecraft:generic.armor=1.5",
            "arcane;BLUE;reforgingstation:magic_resist=1.0",
            "solid;BLUE;minecraft:generic.knockback_resistance=1.0",
            "light;AQUA;minecraft:generic.movement_speed=0.1",
            "legendary;LIGHT_PURPLE;minecraft:generic.armor=1.5,minecraft:knockback_resistance=0.5,reforgingstation:magic_resist=0.5"
    };

    private static final String[] DEFAULTS_PET = {
            "crumbled;DARK_RED;minecraft:generic.armor=-2.0,reforgingstation:magic_resist=-1.0",
            "dented;DARK_GRAY;minecraft:generic.armor=-1.0",
            "heavy;RED;minecraft:generic.movement_speed=-0.1",
            "normal;GRAY;",
            "thick;YELLOW;minecraft:generic.armor=1.0,minecraft:generic.movement_speed=-0.1",
            "protective;BLUE;minecraft:generic.armor=1.5",
            "arcane;BLUE;reforgingstation:magic_resist=1.0",
            "speedy;BLUE;minecraft:generic.movement_speed=0.3",
            "masterful;LIGHT_PURPLE;minecraft:generic.armor=2.0,minecraft:generic.movement_speed=0.15"
    };


    private static final String[] DEFAULTS_BOW = {
            "awful;DARK_RED;reforgingstation:projectile_damage=-0.15",
            "awkward;RED;reforgingstation:projectile_damage=-0.05",
            "normal;GRAY;",
            "deadly;BLUE;reforgingstation:projectile_damage=0.05",
            "powerful;AQUA;reforgingstation:projectile_damage=0.1",
            "unreal;GOLD;reforgingstation:projectile_damage=0.15"
    };

    private static final String[] DEFAULTS_ROD = {
            "unlucky;RED;minecraft:generic.luck=-0.5",
            "normal;GRAY;",
            "lucky;AQUA;minecraft:generic.luck=0.5"
    };

    private static final String[] DEFAULTS_TOOL = {
            "worthless;DARK_RED;minecraft:generic.attack_damage=-0.1,minecraft:generic.attack_speed=-0.1,reforgingstation:entity_reach=-1.0",
            "terrible;DARK_RED;reforgingstation:dig_speed=-0.1,reforgingstation:block_reach=-1.0",
            "broken;DARK_GRAY;reforgingstation:dig_speed=-0.05,reforgingstation:block_reach=-0.5",
            "bulky;DARK_GRAY;minecraft:generic.attack_damage=-0.05,minecraft:generic.attack_speed=-0.05",
            "rusted;RED;minecraft:generic.attack_damage=-0.1",
            "clumsy;RED;minecraft:generic.attack_speed=-0.1",
            "chipped;RED;reforgingstation:dig_speed=-0.1",
            "short;RED;reforgingstation:entity_reach=-1.0",
            "small;RED;reforgingstation:block_reach=-1.0",
            "normal;GRAY;",
            "broad;YELLOW;minecraft:generic.attack_damage=-0.1,reforgingstation:entity_reach=0.5",
            "thin;YELLOW;minecraft:generic.attack_damage=0.1,reforgingstation:entity_reach=-0.5",
            "pokey;YELLOW;minecraft:generic.attack_damage=0.1,minecraft:generic.attack_speed=-0.1",
            "vicious;YELLOW;minecraft:generic.attack_damage=0.15,reforgingstation:entity_reach=-0.5",
            "long;BLUE;reforgingstation:entity_reach=1.0",
            "massive;BLUE;reforgingstation:block_reach=1.5",
            "sharp;BLUE;minecraft:generic.attack_damage=0.15",
            "keen;BLUE;minecraft:generic.attack_speed=0.1",
            "energetic;BLUE;reforgingstation:critical_damage=0.1",
            "graceful;AQUA;minecraft:generic.attack_speed=0.1,reforgingstation:dig_speed=0.1,reforgingstation:block_reach=0.5",
            "sweeping;AQUA;minecraft:generic.attack_speed=0.2,reforgingstation:entity_reach=0.5",
            "strong;AQUA;minecraft:generic.attack_damage=0.15,reforgingstation:critical_damage=0.05",
            "agile;AQUA;minecraft:generic.attack_speed=0.15,reforgingstation:critical_damage=0.05",
            "legendary;LIGHT_PURPLE;reforgingstation:dig_speed=0.15,reforgingstation:block_reach=1.0",
            "mythical;GREEN;minecraft:generic.attack_damage=0.15,minecraft:generic.attack_speed=0.1,reforgingstation:critical_damage=0.05,reforgingstation:entity_reach=0.5"
    };

    private static final String[] DEFAULTS_ACCESSORY = {
            "horrible;DARK_RED;minecraft:generic.attack_damage=-0.05,reforgingstation:critical_damage=-0.05,reforgingstation:projectile_damage=-0.05",
            "defective;DARK_RED;minecraft:generic.attack_speed=-0.05,reforgingstation:dig_speed=-0.05,minecraft:generic.movement_speed=-0.05",
            "unlucky;RED;minecraft:generic.luck=-0.2",
            "normal;GRAY;",
            "healthy;BLUE;minecraft:generic.max_health=2.0",
            "armored;BLUE;reforgingstation:damage_resistance=0.03",
            "speedy;BLUE;minecraft:generic.movement_speed=0.03",
            "springy;BLUE;reforgingstation:jump_height=0.5",
            "prospecting;BLUE;reforgingstation:dig_speed=0.03",
            "flailing;BLUE;minecraft:generic.attack_speed=0.03",
            "arcane;BLUE;reforgingstation:magic_resist=1.0",
            "aiming;BLUE;reforgingstation:projectile_damage=0.03",
            "strengthening;BLUE;minecraft:generic.attack_damage=0.03",
            "precise;BLUE;reforgingstation:critical_damage=0.03",
            "lucky;AQUA;minecraft:generic.luck=0.2",
            "graceful;AQUA;minecraft:generic.attack_speed=0.03,reforgingstation:dig_speed=0.03",
            "athletic;AQUA;minecraft:generic.movement_speed=0.05,reforgingstation:jump_height=0.5",
            "versatile;LIGHT_PURPLE;minecraft:generic.attack_speed=0.03,reforgingstation:dig_speed=0.03,minecraft:generic.movement_speed=0.03",
            "punishing;LIGHT_PURPLE;minecraft:generic.attack_damage=0.03,reforgingstation:critical_damage=0.03,reforgingstation:projectile_damage=0.03",
            "undying;LIGHT_PURPLE;minecraft:generic.max_health=2.0,reforgingstation:damage_resistance=0.03,reforgingstation:magic_resist=1.0"
    };
}
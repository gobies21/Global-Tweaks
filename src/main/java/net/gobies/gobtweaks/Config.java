package net.gobies.gobtweaks;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

@Mod.EventBusSubscriber(modid = GobTweaks.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static ForgeConfigSpec.ConfigValue<Boolean> CRATE_INCREASE_ROWS;
    public static boolean crates_increase_rows;
    public static ForgeConfigSpec.ConfigValue<Boolean> LIGHTNING_DESTROY_ITEM;
    public static boolean lightning_destroy_item;


    public Config() {
    }

    @SubscribeEvent
    static void onLoad(ModConfigEvent.Loading configEvent) {
        crates_increase_rows = CRATE_INCREASE_ROWS.get();
        lightning_destroy_item = LIGHTNING_DESTROY_ITEM.get();
    }

    static {
        BUILDER.push("Vanilla");
        LIGHTNING_DESTROY_ITEM = BUILDER.comment("Prevents lightning from destroying items").define("Prevention", true);
        BUILDER.pop();

        BUILDER.push("Refurbished Furniture");
        CRATE_INCREASE_ROWS = BUILDER.comment("Increase crate maximum rows from 3 to 6").define("Increase Rows", true);
        BUILDER.pop();

        SPEC = BUILDER.build();
    }
}
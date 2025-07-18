package net.gobies.gobtweaks.handlers;

import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import net.gobies.gobtweaks.util.ModLoadedUtil;
import net.minecraft.world.entity.ai.attributes.Attribute;

public class AttributeHandler {
    public static Attribute SPELL_POWER;
    public static Attribute MANA_REGEN;
    public static Attribute MAX_MANA;

    static {
        if (ModLoadedUtil.isIronsspellbooksLoaded()) {
            SPELL_POWER = AttributeRegistry.SPELL_POWER.get();
            MANA_REGEN = AttributeRegistry.MANA_REGEN.get();
            MAX_MANA = AttributeRegistry.MAX_MANA.get();
        }
    }
}

package net.gobies.gobtweaks.mixin.reforgingstation;

import com.dplayend.reforgingstation.common.quality.Quality;
import com.dplayend.reforgingstation.registry.RegistryAttributes;
import net.gobies.gobtweaks.config.CommonConfig;
import net.gobies.gobtweaks.handlers.AttributeHandler;
import net.minecraft.ChatFormatting;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.Map;

@Mixin(Quality.class)
public class QualityMixin2 {

    // Adds new qualities based off of irons spellbooks
    @Inject(
            method = "attributeOperation",
            at = @At("HEAD"),
            cancellable = true,
            remap = false
    )
    private static void attributeOperation(Attribute attribute, CallbackInfoReturnable<AttributeModifier.Operation> cir) {
        if (attribute == AttributeHandler.MAX_MANA) {
            cir.setReturnValue(AttributeModifier.Operation.ADDITION);
        }
    }

    @Inject(
            method = "attributeMap",
            at = @At("TAIL"),
            remap = false
    )
    private static void attributeMap(CallbackInfoReturnable<Map<Attribute, String>> cir) {
        Map<Attribute, String> map = cir.getReturnValue();
        map.put(AttributeHandler.SPELL_POWER, "3176cb2e-5a6f-4f19-a2dc-f86982c98350");
        map.put(AttributeHandler.MANA_REGEN, "3176cb2e-5a6f-4f19-a2dc-f86982c98351");
        map.put(AttributeHandler.MAX_MANA, "3176cb2e-5a6f-4f19-a2dc-f86982c98352");
    }

    @Inject(
            method = "accessoryQualityList",
            at = @At("TAIL"),
            remap = false
    )
    private static void addAccessoryQualities(CallbackInfoReturnable<List<Quality.QualityType>> cir) {
        if (CommonConfig.MAGE_QUALITIES.get()) {
            List<Quality.QualityType> list = cir.getReturnValue();
            list.add(new Quality.QualityType("inept", ChatFormatting.RED, new Quality.Modifier(AttributeHandler.MAX_MANA, -10.0F)));
            list.add(new Quality.QualityType("adept", ChatFormatting.BLUE, new Quality.Modifier(AttributeHandler.MAX_MANA, 10.0F)));
            list.add(new Quality.QualityType("mystic", ChatFormatting.AQUA, new Quality.Modifier(AttributeHandler.SPELL_POWER, 0.03F), new Quality.Modifier(RegistryAttributes.MAGIC_RESIST.get(), 1.0F)));
            list.add(new Quality.QualityType("celestial", ChatFormatting.LIGHT_PURPLE, new Quality.Modifier(AttributeHandler.SPELL_POWER, 0.03F), new Quality.Modifier(AttributeHandler.MANA_REGEN, 0.03F), new Quality.Modifier(AttributeHandler.MAX_MANA, 10.0F)));
        }
    }
}
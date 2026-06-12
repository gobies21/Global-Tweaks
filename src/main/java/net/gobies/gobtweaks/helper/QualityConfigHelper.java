package net.gobies.gobtweaks.helper;

import com.dplayend.reforgingstation.common.quality.Quality;
import net.gobies.gobtweaks.GobTweaks;
import net.gobies.gobtweaks.config.reforgingstation.QualityConfig;
import net.minecraft.ChatFormatting;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;

public class QualityConfigHelper {

    public static AttributeModifier.Operation attributeOperation(Attribute attribute) {
        if (attribute == null) return AttributeModifier.Operation.MULTIPLY_TOTAL;

        ResourceLocation registryName = ForgeRegistries.ATTRIBUTES.getKey(attribute);
        if (registryName == null) return AttributeModifier.Operation.MULTIPLY_TOTAL;

        String attributeKey = registryName.toString().toLowerCase();
        if (QualityConfig.ATTRIBUTE_OPERATIONS != null) {
            for (String mapping : QualityConfig.ATTRIBUTE_OPERATIONS) {
                if (mapping == null || !mapping.contains(";")) continue;
                String cleanLine = mapping.replace("\"", "");
                String[] parts = cleanLine.split(";");
                if (parts.length < 2) continue;

                String configAttribute = parts[0].trim().toLowerCase();

                if (configAttribute.equals(attributeKey)) {
                    String opType = parts[1].trim().toUpperCase();
                    switch (opType) {
                        case "ADDITION":       return AttributeModifier.Operation.ADDITION;
                        case "MULTIPLY_BASE":  return AttributeModifier.Operation.MULTIPLY_BASE;
                        case "MULTIPLY_TOTAL": return AttributeModifier.Operation.MULTIPLY_TOTAL;
                    }
                }
            }
        }

        return AttributeModifier.Operation.MULTIPLY_BASE;
    }

    public static List<Quality.QualityType> parseQualities(List<? extends String> configStrings) {
        List<Quality.QualityType> parsedList = new ArrayList<>();

        for (String entry : configStrings) {
            if (entry == null || entry.isBlank()) continue;

            try {
                String[] segments = entry.split(";");
                String name = segments[0].trim();

                ChatFormatting color = ChatFormatting.getByName(segments[1].trim().toUpperCase());
                if (color == null) color = ChatFormatting.GRAY;

                List<Quality.Modifier> modifiersList = new ArrayList<>();
                if (segments.length > 2 && !segments[2].isBlank()) {

                    for (String token : segments[2].split(",")) {
                        String[] pair = token.split("=");
                        if (pair.length != 2) continue;

                        String attributeId = pair[0].trim().toLowerCase();
                        double value = Double.parseDouble(pair[1].trim());

                        Attribute attribute = ForgeRegistries.ATTRIBUTES.getValue(new ResourceLocation(attributeId));
                        if (attribute != null) {
                            modifiersList.add(new Quality.Modifier(attribute, value));
                        } else {
                            GobTweaks.LOGGER.warn("Configuration skipped unregistered attribute: {}", attributeId);
                        }
                    }
                }

                Quality.Modifier[] modifiersArray = modifiersList.toArray(new Quality.Modifier[0]);
                parsedList.add(new Quality.QualityType(name, color, modifiersArray));

            } catch (Exception e) {
                GobTweaks.LOGGER.error("Error processing configuration line entry: {}", entry, e);
            }
        }
        return parsedList;
    }
}
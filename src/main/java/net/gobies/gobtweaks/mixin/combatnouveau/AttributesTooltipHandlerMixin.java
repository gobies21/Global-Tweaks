package net.gobies.gobtweaks.mixin.combatnouveau;

import fuzs.combatnouveau.client.handler.AttributesTooltipHandler;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.List;

@Mixin(AttributesTooltipHandler.class)
public class AttributesTooltipHandlerMixin {

    @Redirect(
            method = "convertToDefaultAttribute",
            remap = false,
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/util/List;add(ILjava/lang/Object;)V"
            )
    )
    private static void reorderAttributeInsertion(List<Component> lines, int index, Object element) {
        Component newLine = (Component) element;
        String lineText = newLine.getString().toLowerCase();
        if (lineText.contains("entity reach")) {
            int lastTooltip = -1;

            for (int i = 0; i < lines.size(); i++) {
                String existingLineText = lines.get(i).getString().toLowerCase();
                if (existingLineText.contains("attack damage") || existingLineText.contains("attack speed")) {
                    lastTooltip = i;
                }
            }

            int insertIndex = (lastTooltip != -1) ? lastTooltip + 1 : index;
            lines.add(insertIndex, newLine);

        } else {
            lines.add(index, newLine);
        }
    }
}
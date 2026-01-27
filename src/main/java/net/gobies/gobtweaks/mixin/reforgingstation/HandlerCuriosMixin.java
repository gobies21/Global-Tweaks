package net.gobies.gobtweaks.mixin.reforgingstation;

import com.dplayend.reforgingstation.common.quality.Quality;
import com.dplayend.reforgingstation.handler.HandlerCurios;
import net.gobies.gobtweaks.util.QualityHelper;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(HandlerCurios.class)
public class HandlerCuriosMixin {

    /**
     * @author gobies
     * @reason Curios support to previous changes
     */
    @Overwrite(remap = false)
    public static void randomizerAccessoryQuality(ItemStack stack) {
        stack.getOrCreateTag().putString("quality", QualityHelper.getReforgeQuality(Quality.accessoryQualityList()).quality());
    }
}

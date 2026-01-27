package net.gobies.gobtweaks.mixin.reforgingstation;

import com.dplayend.reforgingstation.common.quality.Quality;
import com.dplayend.reforgingstation.common.quality.QualityUtil;
import com.dplayend.reforgingstation.handler.HandlerCurios;
import com.dplayend.reforgingstation.network.common.ChangeQualitySP;
import net.gobies.gobtweaks.util.QualityHelper;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import java.util.function.Supplier;

@Mixin(ChangeQualitySP.class)
public class ChangeQualitySPMixin {

    /**
     * @author gobies
     * @reason Making reforging table skip normal qualities and better work with the less rare quality chances
     */
    @Overwrite(remap = false)
    public void handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            if (player != null) {
                ItemStack stack = player.containerMenu.getSlot(36).getItem();
                if (stack.hasTag() && stack.getTag() != null && !stack.getTag().getString("quality").isEmpty()) {
                    switch (QualityUtil.getQuality(stack)) {
                        case "helmet" ->
                                stack.getTag().putString("quality", QualityHelper.getReforgeQuality(Quality.helmetQualityList()).quality());
                        case "chestplate" ->
                                stack.getTag().putString("quality", QualityHelper.getReforgeQuality(Quality.chestplateQualityList()).quality());
                        case "leggings" ->
                                stack.getTag().putString("quality", QualityHelper.getReforgeQuality(Quality.leggingsQualityList()).quality());
                        case "boots" ->
                                stack.getTag().putString("quality", QualityHelper.getReforgeQuality(Quality.bootsQualityList()).quality());
                        case "shield" ->
                                stack.getTag().putString("quality", QualityHelper.getReforgeQuality(Quality.shieldQualityList()).quality());
                        case "pet" ->
                                stack.getTag().putString("quality", QualityHelper.getReforgeQuality(Quality.petQualityList()).quality());
                        case "bow" ->
                                stack.getTag().putString("quality", QualityHelper.getReforgeQuality(Quality.bowQualityList()).quality());
                        case "rod" ->
                                stack.getTag().putString("quality", QualityHelper.getReforgeQuality(Quality.rodQualityList()).quality());
                        case "tool" ->
                                stack.getTag().putString("quality", QualityHelper.getReforgeQuality(Quality.toolQualityList()).quality());
                        case "accessory" -> HandlerCurios.randomizerAccessoryQuality(stack);
                    }

                    player.containerMenu.getSlot(37).getItem().setCount(player.containerMenu.getSlot(37).getItem().getCount() - 1);
                    player.containerMenu.getSlot(36).set(stack);
                }
            }

        });
        context.setPacketHandled(true);
    }
}
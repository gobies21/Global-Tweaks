package net.gobies.gobtweaks.mixin.notreepunching;

import com.alcatrazescapee.notreepunching.util.Helpers;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(Helpers.class)
public class HelpersMixin {

    /**
     * @author gobies
     * @reason fix picking up loose rocks not going directly into inventory when using item-physics custom pickup
     * code referenced from #337
     */
    @Overwrite(remap = false)
    public static void giveItemToPlayer(Level level, Player player, ItemStack stack) {
        if (!stack.isEmpty() && !level.isClientSide) {
            final ItemEntity entity = new ItemEntity(level, player.getX(), player.getY() + 0.5, player.getZ(), stack);
            entity.setPickUpDelay(0);
            level.addFreshEntity(entity);
            if (!player.getInventory().add(stack)) {
                player.drop(stack, false);
            }
        }
    }
}

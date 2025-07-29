package net.gobies.gobtweaks.handlers;

import com.ferreusveritas.dynamictrees.data.DTBlockTags;
import net.gobies.gobtweaks.CommonConfig;
import net.gobies.gobtweaks.util.ModLoadedUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.Tags;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class DynamicSticksHandler {

    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        if (ModLoadedUtil.isDynamicTreesLoaded()) {
            BlockState state = event.getState();
            Level world = (Level) event.getLevel();
            BlockPos pos = event.getPos();
            Player player = event.getPlayer();
            if (state.is(DTBlockTags.LEAVES) && !player.isCreative() && !player.isSpectator()) {
                boolean hasShears = player.getItemInHand(player.getUsedItemHand()).is(Tags.Items.SHEARS);
                boolean hasSilkTouch = EnchantmentHelper.getEnchantmentLevel(Enchantments.SILK_TOUCH, player) > 0;
                if (!hasShears && !hasSilkTouch && world.getRandom().nextFloat() < CommonConfig.ADDITIONAL_STICK_CHANCE.get()) {
                    Block.popResource(world, pos, new ItemStack(Items.STICK));
                }
            }
        }
    }
}
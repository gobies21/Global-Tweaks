package net.gobies.gobtweaks.handlers.locks;

import melonslise.locks.common.config.LocksCommonConfig;
import melonslise.locks.common.init.LocksCapabilities;
import melonslise.locks.common.init.LocksNetwork;
import melonslise.locks.common.network.toclient.AddLockableToChunkPacket;
import melonslise.locks.common.network.toclient.RemoveLockablePacket;
import melonslise.locks.common.util.Cuboid6i;
import melonslise.locks.common.util.Lock;
import melonslise.locks.common.util.Lockable;
import melonslise.locks.common.util.Transform;
import net.gobies.gobtweaks.config.CommonConfig;
import net.gobies.gobtweaks.mixin.accessor.RandomizableContainerBlockEntityAccessor;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.event.level.ChunkEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.registries.ForgeRegistries;

public class WorldGenLockHandler {

    // Random lock config value
    private static final String RANDOM = "random";

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onChunkLoad(ChunkEvent.Load event) {
        if (!(event.getLevel() instanceof ServerLevel level)) return;
        LevelChunk chunk = (LevelChunk) event.getChunk();
        if (chunk.isOldNoiseGeneration() || chunk.getBlockEntities().isEmpty()) return;

        chunk.getCapability(LocksCapabilities.LOCKABLE_STORAGE).ifPresent(storage -> level.getCapability(LocksCapabilities.LOCKABLE_HANDLER).ifPresent(handler -> {
            var blockEntities = chunk.getBlockEntities();
            if (blockEntities.isEmpty()) return;

            boolean chunkChanged = false;
            var storageMap = storage.get();
            if (storageMap == null) return;

            for (BlockEntity block : blockEntities.values()) {
                if (!(block instanceof RandomizableContainerBlockEntity container)) continue;
                BlockState state = block.getBlockState();
                BlockPos pos = block.getBlockPos();
                BlockPos pos1 = pos;

                if (state.hasProperty(BlockStateProperties.CHEST_TYPE)) {
                    var type = state.getValue(BlockStateProperties.CHEST_TYPE);
                    if (type == net.minecraft.world.level.block.state.properties.ChestType.RIGHT) continue;
                    if (type == net.minecraft.world.level.block.state.properties.ChestType.LEFT) {
                        pos1 = pos.relative(net.minecraft.world.level.block.ChestBlock.getConnectedDirection(state));
                    }
                }

                if (!(container instanceof RandomizableContainerBlockEntityAccessor accessor)) continue;
                ResourceLocation loot = accessor.gobtweaks$getLootTable();
                if (loot == null) continue;

                ResourceLocation lockId = CommonConfig.always_lock.get(loot);
                if (lockId == null) continue;

                int persistentId = pos.hashCode();
                if (storageMap.containsKey(persistentId)) continue;

                for (Lockable existing : storageMap.values()) {
                    if (existing != null && existing.bb.intersects(pos)) {
                        handler.getLoaded().remove(existing.id);
                        existing.deleteObserver(handler);
                        storage.remove(existing.id);
                        LocksNetwork.MAIN.send(PacketDistributor.TRACKING_CHUNK.with(() -> chunk), new RemoveLockablePacket(existing.id));
                        break;
                    }
                }

                ItemStack stack;
                if (RANDOM.equals(lockId.getPath())) {
                    stack = LocksCommonConfig.getRandomLock(level.random);
                } else {
                    var item = ForgeRegistries.ITEMS.getValue(lockId);
                    stack = (item == null) ? LocksCommonConfig.getRandomLock(level.random) : new ItemStack(item);
                }

                if (stack.isEmpty()) continue;

                Direction dir = getFacing(state);
                Transform transform = Transform.fromDirection(dir, dir);
                if (transform == null) transform = Transform.NORTH_UP;

                Lockable lockable = new Lockable(new Cuboid6i(pos, pos1), Lock.from(stack), transform, stack, persistentId);

                lockable.addObserver(handler);
                handler.getLoaded().put(lockable.id, lockable);
                storage.add(lockable);

                LocksNetwork.MAIN.send(PacketDistributor.TRACKING_CHUNK.with(() -> chunk), new AddLockableToChunkPacket(lockable, chunk));
                chunkChanged = true;
            }

            if (chunkChanged) {
                chunk.setUnsaved(true);
            }
        }));
    }

    private static Direction getFacing(BlockState state) {
        if (state.hasProperty(BlockStateProperties.FACING)) return state.getValue(BlockStateProperties.FACING);
        if (state.hasProperty(BlockStateProperties.HORIZONTAL_FACING)) return state.getValue(BlockStateProperties.HORIZONTAL_FACING);
        return Direction.DOWN;
    }
}
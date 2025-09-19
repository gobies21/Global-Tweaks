package net.gobies.gobtweaks.mixin.dungeonsmobs;

import net.firefoxsalesman.dungeonsmobs.lib.client.CuriosKeyBindings;
import net.firefoxsalesman.dungeonsmobs.lib.client.message.CuriosArtifactStartMessage;
import net.firefoxsalesman.dungeonsmobs.lib.items.artifacts.ArtifactItem;
import net.firefoxsalesman.dungeonsmobs.lib.items.artifacts.ArtifactUseContext;
import net.firefoxsalesman.dungeonsmobs.network.NetworkHandler;
import net.gobies.gobtweaks.config.CommonConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import top.theillusivec4.curios.api.CuriosApi;

@Mixin(CuriosKeyBindings.class)
public class CuriosKeyBindingsMixin {

    /**
     * @author gobies
     * @reason fixes a crash when artifacts slots are disabled and the artifact keybinds are pressed
     */
    @Overwrite(remap = false)
    private static void curiosStartMessage(int slot, BlockHitResult blockHitResult, LocalPlayer player) {
        if (CommonConfig.DEBUG.get()) {
            NetworkHandler.INSTANCE.sendToServer(new CuriosArtifactStartMessage(slot, blockHitResult));
            CuriosApi.getCuriosInventory(player).ifPresent((iCuriosItemHandler) -> {
                iCuriosItemHandler.getCurios().forEach((identifier, curioInventory) -> {
                    for (int i = 0; i < curioInventory.getSlots(); i++) {
                        ItemStack curioItem = curioInventory.getStacks().getStackInSlot(i);
                        if (!curioItem.isEmpty() && curioItem.getItem() instanceof ArtifactItem) {
                            if (i == slot) {
                                ArtifactUseContext iuc = new ArtifactUseContext(player.level(), player, curioItem, blockHitResult);
                                ((ArtifactItem) curioItem.getItem()).activateArtifact(iuc);
                            }
                        }
                    }
                });
            });
        }
    }

    /**
     * @author gobies
     * @reason attempt to fix artifacts only being usable when equipped in the artifact slot
     */
    @Overwrite(remap = false)
    private static void sendCuriosStartMessageToServer(int slot) {
        if (CommonConfig.DEBUG.get()) {
            HitResult hitResult = Minecraft.getInstance().hitResult;
            LocalPlayer player = Minecraft.getInstance().player;
            if (player != null) {
                if (hitResult != null && hitResult.getType() != HitResult.Type.MISS) {
                    BlockHitResult blockHitResult;
                    if (hitResult.getType() == HitResult.Type.BLOCK) {
                        blockHitResult = (BlockHitResult) hitResult;
                    } else if (hitResult.getType() == HitResult.Type.ENTITY) {
                        EntityHitResult entityHitResult = (EntityHitResult) hitResult;
                        blockHitResult = new BlockHitResult(entityHitResult.getEntity().position(), Direction.UP, entityHitResult.getEntity().blockPosition(), false);
                    } else {
                        blockHitResult = new BlockHitResult(player.position(), Direction.UP, player.blockPosition(), false);
                    }

                    CuriosApi.getCuriosInventory(player).ifPresent(curiosItemHandler -> {
                        for (String identifier : curiosItemHandler.getCurios().keySet()) {
                            curiosItemHandler.getStacksHandler(identifier).ifPresent(curioStacksHandler -> {
                                for (int i = 0; i < curioStacksHandler.getSlots(); i++) {
                                    ItemStack artifact = curioStacksHandler.getStacks().getStackInSlot(i);
                                    if (!artifact.isEmpty() && artifact.getItem() instanceof ArtifactItem) {
                                        curiosStartMessage(i, blockHitResult, player);
                                    }
                                }
                            });
                        }
                    });
                }
            }
        }
    }
}
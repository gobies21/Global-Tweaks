package net.gobies.gobtweaks.mixin.reforgingstation;

import com.dplayend.reforgingstation.common.quality.Quality;
import com.dplayend.reforgingstation.common.quality.QualityUtil;
import com.dplayend.reforgingstation.handler.HandlerCurios;
import net.gobies.gobtweaks.config.CommonConfig;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@Mixin(Quality.class)
public class QualityMixin {

    @Inject(
            method = "matchingMaterial",
            at = @At("TAIL"),
            remap = false,
            cancellable = true
    )
    // Makes sure to check for the items repair material if it has one for reforging
    private static void reforgeMaterial(ItemStack stack, ItemStack materialStack, CallbackInfoReturnable<Boolean> cir) {
        if (stack.getItem().isValidRepairItem(stack, materialStack) && CommonConfig.VALID_REPAIR_MATERIALS.get()) {
            cir.setReturnValue(true);
        }
    }

    /**
     * @author gobies
     * @reason attempt to fix qualities ticking on all entities causing performance issues
     */
    @Overwrite(remap = false)
    public static void addModifiers(LivingEntity livingEntity) {
        ResourceLocation entityName = ForgeRegistries.ENTITY_TYPES.getKey(livingEntity.getType());
        if (livingEntity instanceof Player || entityName != null && CommonConfig.ENTITY_QUALITIES.get().contains(entityName.toString())) {
            List<Quality.SimpleModifier> map = new ArrayList<>();
            map.add(new Quality.SimpleModifier(livingEntity.getMainHandItem(), QualityUtil.toolQuality(livingEntity.getMainHandItem())));
            map.add(new Quality.SimpleModifier(livingEntity.getMainHandItem(), QualityUtil.bowQuality(livingEntity.getMainHandItem())));
            map.add(new Quality.SimpleModifier(livingEntity.getMainHandItem(), QualityUtil.fishingRodQuality(livingEntity.getMainHandItem())));
            map.add(new Quality.SimpleModifier(livingEntity.getOffhandItem(), QualityUtil.shieldQuality(livingEntity.getOffhandItem())));
            livingEntity.getArmorSlots().forEach((stack) -> map.add(new Quality.SimpleModifier(stack, QualityUtil.armorQuality(stack))));
            if (HandlerCurios.isModLoaded()) {
                HandlerCurios.accessoryStackList(livingEntity).forEach((stack) -> map.add(new Quality.SimpleModifier(stack, HandlerCurios.accessoryQuality(livingEntity))));
            }

            Quality.attributeMap().forEach((attribute, uuid) -> {
                AtomicReference<Double> value = new AtomicReference<>((double) 0.0F);
                AtomicReference<AttributeModifier.Operation> operation = new AtomicReference<>(AttributeModifier.Operation.ADDITION);
                map.forEach((simpleModifier) -> {
                    ItemStack stack = simpleModifier.stack();
                    boolean quality = simpleModifier.qualityType();
                    Quality.QualityType type = QualityUtil.getQualityType(stack, livingEntity);
                    if (type != null && !stack.isEmpty() && stack.getTag() != null && !stack.getTag().getString("quality").isEmpty() && quality) {
                        for (Quality.Modifier provider : type.attributes()) {
                            if (provider.attribute() != null && provider.attribute().equals(attribute)) {
                                value.updateAndGet((aDouble) -> aDouble + provider.getAmount());
                                operation.set(Quality.attributeOperation(provider.attribute()));
                            }
                        }
                    }

                });
                UUID var10002 = UUID.fromString(uuid);
                Objects.requireNonNull(attribute);
                AttributeModifier attackModifier = new AttributeModifier(var10002, attribute::getDescriptionId, value.get(), operation.get());
                AttributeInstance attributeInstance = livingEntity.getAttribute(attribute);
                if (attributeInstance != null) {
                    attributeInstance.removePermanentModifier(attackModifier.getId());
                    if (!attributeInstance.hasModifier(attackModifier)) {
                        attributeInstance.addPermanentModifier(attackModifier);
                    }
                }

            });
        }
    }

    /**
     * @author gobies
     * @reason Make it more rare to get qualities
     */
    @Overwrite(remap = false)
    public static Quality.QualityType getRandomQuality(List<Quality.QualityType> list) {
        Quality.QualityType normalQuality = null;
        for (Quality.QualityType qualityType : list) {
            if (qualityType.attributes().length == 0) {
                normalQuality = qualityType;
                break;
            }
        }
        if (normalQuality != null && Math.random() < CommonConfig.NO_QUALITY_CHANCE.get()) {
            return normalQuality;
        } else {
            return list.get((int) Math.floor(Math.random() * list.size()));
        }
    }
}
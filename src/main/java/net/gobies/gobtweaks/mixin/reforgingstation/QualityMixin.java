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
        if (livingEntity instanceof Player || (entityName != null && CommonConfig.ENTITY_QUALITIES.get().contains(entityName.toString()))) {
            List<Quality.SimpleModifier> map = new ArrayList<>(12);

            ItemStack mainHand = livingEntity.getMainHandItem();
            ItemStack offHand = livingEntity.getOffhandItem();

            map.add(new Quality.SimpleModifier(mainHand, QualityUtil.toolQuality(mainHand)));
            map.add(new Quality.SimpleModifier(mainHand, QualityUtil.bowQuality(mainHand)));
            map.add(new Quality.SimpleModifier(mainHand, QualityUtil.fishingRodQuality(mainHand)));
            map.add(new Quality.SimpleModifier(offHand, QualityUtil.shieldQuality(offHand)));

            for (ItemStack stack : livingEntity.getArmorSlots()) {
                map.add(new Quality.SimpleModifier(stack, QualityUtil.armorQuality(stack)));
            }

            if (HandlerCurios.isModLoaded()) {
                for (ItemStack stack : HandlerCurios.accessoryStackList(livingEntity)) {
                    map.add(new Quality.SimpleModifier(stack, HandlerCurios.accessoryQuality(livingEntity)));
                }
            }
            Quality.attributeMap().forEach((attribute, uuidStr) -> {
                if (attribute == null) return;

                double totalValue = 0.0;
                AttributeModifier.Operation operation = AttributeModifier.Operation.ADDITION;

                for (Quality.SimpleModifier simpleModifier : map) {
                    if (!simpleModifier.qualityType()) continue;

                    ItemStack stack = simpleModifier.stack();
                    if (stack != null && !stack.isEmpty() && stack.hasTag()) {
                        String qualityStr = Objects.requireNonNull(stack.getTag()).getString("quality");

                        if (!qualityStr.isEmpty()) {
                            Quality.QualityType type = QualityUtil.getQualityType(stack, livingEntity);
                            if (type != null && type.attributes() != null) {
                                for (Quality.Modifier provider : type.attributes()) {
                                    if (provider.attribute() != null && provider.attribute().equals(attribute)) {
                                        totalValue += provider.getAmount();
                                        operation = Quality.attributeOperation(provider.attribute());
                                    }
                                }
                            }
                        }
                    }
                }

                UUID uuid = UUID.fromString(uuidStr);
                AttributeInstance attributeInstance = livingEntity.getAttribute(attribute);

                if (attributeInstance != null) {
                    AttributeModifier attackModifier = new AttributeModifier(uuid, attribute::getDescriptionId, totalValue, operation);
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
        Quality.QualityType normal = null;
        int qualityCount = 0;

        for (Quality.QualityType quality : list) {
            if (quality.attributes().length == 0) {
                normal = quality;
            } else {
                qualityCount++;
            }
        }
        if (normal == null || qualityCount == 0) return list.get((int) (Math.random() * list.size()));
        if (Math.random() < CommonConfig.NO_QUALITY_CHANCE.get()) return normal;

        int pick = (int) (Math.random() * qualityCount);
        for (Quality.QualityType quality : list) {
            if (quality.attributes().length != 0 && pick-- == 0) {
                return quality;
            }
        }
        // fallback (should not occur)
        return normal;
    }

    // This code WORKS but is to performance heavy for how this mod is structured
    /*
    @Overwrite(remap = false)
    public static void addModifiers(LivingEntity livingEntity) {
        if (livingEntity == null || livingEntity.level().isClientSide()) return;

        ResourceLocation entityName = ForgeRegistries.ENTITY_TYPES.getKey(livingEntity.getType());
        if (livingEntity instanceof Player || (entityName != null && CommonConfig.ENTITY_QUALITIES.get().contains(entityName.toString()))) {

            List<Quality.SimpleModifier> map = new ArrayList<>();
            map.add(new Quality.SimpleModifier(livingEntity.getMainHandItem(), QualityUtil.toolQuality(livingEntity.getMainHandItem())));
            map.add(new Quality.SimpleModifier(livingEntity.getMainHandItem(), QualityUtil.bowQuality(livingEntity.getMainHandItem())));
            map.add(new Quality.SimpleModifier(livingEntity.getMainHandItem(), QualityUtil.fishingRodQuality(livingEntity.getMainHandItem())));
            map.add(new Quality.SimpleModifier(livingEntity.getOffhandItem(), QualityUtil.shieldQuality(livingEntity.getOffhandItem())));
            livingEntity.getArmorSlots().forEach((stack) -> map.add(new Quality.SimpleModifier(stack, QualityUtil.armorQuality(stack))));

            if (HandlerCurios.isModLoaded()) {
                HandlerCurios.accessoryStackList(livingEntity).forEach((stack) -> map.add(new Quality.SimpleModifier(stack, HandlerCurios.accessoryQuality(livingEntity))));
            }

            Map<Attribute, String> legacyUuidMap = Quality.attributeMap();

            for (Attribute attribute : ForgeRegistries.ATTRIBUTES.getValues()) {
                if (attribute == null) continue;
                double totalValue = 0.0;
                AttributeModifier.Operation operation = AttributeModifier.Operation.ADDITION;
                for (Quality.SimpleModifier simpleModifier : map) {
                    ItemStack stack = simpleModifier.stack();
                    boolean quality = simpleModifier.qualityType();
                    Quality.QualityType type = QualityUtil.getQualityType(stack, livingEntity);

                    if (type != null && !stack.isEmpty() && stack.hasTag() && quality) {
                        String qualityName = Objects.requireNonNull(stack.getTag()).getString("quality");
                        if (!qualityName.isEmpty()) {
                            for (Quality.Modifier provider : type.attributes()) {
                                if (provider.attribute() != null && provider.attribute().equals(attribute)) {
                                    totalValue += provider.getAmount();
                                    operation = Quality.attributeOperation(provider.attribute());
                                }
                            }
                        }
                    }
                }

                String uuidStr = legacyUuidMap.get(attribute);
                if (uuidStr == null) {
                    ResourceLocation loc = ForgeRegistries.ATTRIBUTES.getKey(attribute);
                    if (loc == null) continue;
                    uuidStr = UUID.nameUUIDFromBytes(loc.toString().getBytes(java.nio.charset.StandardCharsets.UTF_8)).toString();
                }
                UUID modifierId = UUID.fromString(uuidStr);

                AttributeInstance attributeInstance = livingEntity.getAttribute(attribute);
                if (attributeInstance == null && totalValue != 0.0) {
                    try {
                        attributeInstance = livingEntity.getAttributes().getInstance(attribute);
                    } catch (Exception ignored) {}
                }

                if (attributeInstance != null) {attributeInstance.removePermanentModifier(modifierId);
                    if (totalValue != 0.0) {
                        AttributeModifier attackModifier = new AttributeModifier(modifierId, attribute::getDescriptionId, totalValue, operation);
                        if (!attributeInstance.hasModifier(attackModifier)) {
                            attributeInstance.addPermanentModifier(attackModifier);
                        }
                    }
                }
            }
        }
    }

     */
}
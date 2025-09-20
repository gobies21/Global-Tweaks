package net.gobies.gobtweaks.mixin.spartanfire;

import com.oblivioussp.spartanweaponry.api.WeaponMaterial;
import krelox.spartanfire.SpartanFire;
import krelox.spartantoolkit.WeaponItem;
import net.gobies.gobtweaks.config.CommonConfig;
import net.gobies.gobtweaks.util.ModLoadedUtil;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = Item.class, priority = 500)
public class ItemMixin {
    @Inject(
            method = "isFoil",
            at = @At("RETURN"),
            cancellable = true
    )
    private void isFoil(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        Item var4 = stack.getItem();
        if (var4 instanceof WeaponItem weapon && !stack.isEnchanted() && ModLoadedUtil.isSpartanFireLoaded()) {
            WeaponMaterial material = weapon.getMaterial();
            if (cir.getReturnValue() || material.equals(SpartanFire.FLAMED_DRAGON_BONE) || material.equals(SpartanFire.ICED_DRAGON_BONE) || material.equals(SpartanFire.LIGHTNING_DRAGON_BONE)) {
                cir.setReturnValue(CommonConfig.DRAGON_ENCHANTED_GLOW.get());
            }
        }
    }
}
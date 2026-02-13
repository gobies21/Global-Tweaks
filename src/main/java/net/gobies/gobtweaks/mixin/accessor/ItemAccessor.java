package net.gobies.gobtweaks.mixin.accessor;

import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Item.class)
public interface ItemAccessor {

    @Accessor("maxDamage")
    @Mutable
    void gobTweaks$setMaxDamage(int maxDamage);
}

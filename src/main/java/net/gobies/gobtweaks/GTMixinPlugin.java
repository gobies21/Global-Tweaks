package net.gobies.gobtweaks;

import com.google.common.collect.ImmutableMap;
import net.minecraftforge.fml.loading.LoadingModList;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

public final class GTMixinPlugin implements IMixinConfigPlugin {
    private static final Supplier<Boolean> TRUE = () -> true;
    private static final Map<String, Supplier<Boolean>> CONDITIONS = ImmutableMap.of(
            "net.gobies.gobtweaks.mixin.blessfulled.DamagePopupTriggersMixin", () -> LoadingModList.get().getModFileById("pml") != null,
            "net.gobies.gobtweaks.mixin.carryon.PickupHandlerMixin", () -> LoadingModList.get().getModFileById("locks") != null,
            "net.gobies.gobtweaks.mixin.fbt.BattleTowerGolemEntityMixin", () -> LoadingModList.get().getModFileById("locks") != null,
            "net.gobies.gobtweaks.mixin.iceandfire.EntityDragonBaseMixin", () -> LoadingModList.get().getModFileById("dynamictrees") != null,
            "net.gobies.gobtweaks.mixin.iceandfire.DragonUtilsMixin", () -> LoadingModList.get().getModFileById("dynamictrees") != null,
            "net.gobies.gobtweaks.mixin.iceandfire.FrozenDataMixin", () -> LoadingModList.get().getModFileById("cold_sweat") != null,
            "net.gobies.gobtweaks.mixin.reforgingstation.QualityMixin2", () -> LoadingModList.get().getModFileById("irons_spellbooks") != null,
            "net.gobies.gobtweaks.mixin.reforgingstation.HandlerCuriosMixin", () -> LoadingModList.get().getModFileById("curios") != null,
            "net.gobies.gobtweaks.mixin.spartanfire.ItemMixin", () -> LoadingModList.get().getModFileById("spartanfire") != null,
            "net.gobies.gobtweaks.mixin.thirstwastaken.DrinkByHandClientMixin", () -> LoadingModList.get().getModFileById("alexscaves") != null
    );

    @Override
    public void onLoad(String mixinPackage) {
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        return CONDITIONS.getOrDefault(mixinClassName, TRUE).get();
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {
    }

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public List<String> getMixins() {
        return null;
    }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
    }

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
    }
}
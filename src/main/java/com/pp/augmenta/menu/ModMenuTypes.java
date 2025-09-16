package com.pp.augmenta.menu;

import com.pp.augmenta.Augmenta;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ModMenuTypes {
    private ModMenuTypes() {}

    private static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(net.minecraft.core.registries.Registries.MENU, Augmenta.MODID);

    public static final DeferredHolder<MenuType<?>, MenuType<AugmenterMenu>> AUGMENTER =
            MENUS.register("augmenter", () -> IMenuTypeExtension.create(AugmenterMenu::new));

    public static void register(IEventBus eventBus) {
        MENUS.register(eventBus);
    }
}

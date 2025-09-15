package com.pp.augmenta.item;

import com.pp.augmenta.Augmenta;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Augmenta.MODID);

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}

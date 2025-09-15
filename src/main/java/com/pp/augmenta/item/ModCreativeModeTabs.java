package com.pp.augmenta.item;

import com.pp.augmenta.Augmenta;
import com.pp.augmenta.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Augmenta.MODID);

    public static final Supplier<CreativeModeTab> AUGMENTA_ITEMS_TAB = CREATIVE_MODE_TAB.register("augmenta_items_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModBlocks.AUGMENTER.get()))
                    .title(Component.translatable("Augmenta Items"))
                    .displayItems((itemDisplayParameters, output) -> {

//                        output.accept(ModItems.GECKO_SPAWN_EGG); // Placeholder

                    }).build());

    public static final Supplier<CreativeModeTab> AUGMENTA_BLOCK_TAB = CREATIVE_MODE_TAB.register("augmenta_blocks_tab",
        () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModBlocks.AUGMENTER.get()))
            .withTabsBefore(ResourceLocation.fromNamespaceAndPath(Augmenta.MODID, "augmenta_items_tab"))
            .title(Component.translatable("Augmenta Blocks"))
            .displayItems((itemDisplayParameters, output) -> {
                output.accept(ModBlocks.AUGMENTER.get());

            }).build());


    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TAB.register(eventBus);
    }
}

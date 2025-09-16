package com.pp.augmenta;

import com.mojang.logging.LogUtils;
import com.pp.augmenta.attachment.ModAttachments;
import com.pp.augmenta.block.ModBlocks;
import com.pp.augmenta.command.AugmentCommands;
import com.pp.augmenta.item.ModCreativeModeTabs;
import com.pp.augmenta.item.ModItems;
import com.pp.augmenta.menu.ModMenuTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import org.slf4j.Logger;

@Mod(Augmenta.MODID)
public class Augmenta {
    public static final String MODID = "augmenta";
    public static final Logger LOGGER = LogUtils.getLogger();

    // First code that is run when mod is loaded.
    public Augmenta(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::commonSetup);

        // Finalize Registries
        ModCreativeModeTabs.register(modEventBus);

        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModMenuTypes.register(modEventBus);
        ModAttachments.register(modEventBus);

        // Register ourselves for server and other game events we are interested in.
        NeoForge.EVENT_BUS.register(this);
        NeoForge.EVENT_BUS.addListener(AugmentCommands::register);
        modEventBus.addListener(this::addCreative);
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        LOGGER.info("HELLO FROM COMMON SETUP 1");

        if (Config.LOG_DIRT_BLOCK.getAsBoolean()) {
            LOGGER.info("DIRT BLOCK >> {}", BuiltInRegistries.BLOCK.getKey(Blocks.DIRT));
        }
    }

    // Add items to the creative tab group
    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if(event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) {
//            event.accept(ModBlocks.BISMUTH_BLOCK);
//            event.accept(ModBlocks.BISMUTH_ORE);
        }
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // Do something when the server starts
        LOGGER.info("HELLO from Server Starting.");
    }
}

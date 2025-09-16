package com.pp.augmenta;

import com.pp.augmenta.menu.ModMenuTypes;
import com.pp.augmenta.menu.client.AugmenterScreen;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@Mod(value = Augmenta.MODID, dist = Dist.CLIENT)

@EventBusSubscriber(modid = Augmenta.MODID, value = Dist.CLIENT)
public class AugmentaClient {
    public AugmentaClient(ModContainer container) {
        // Do not forget to add translations for your config options to the en_us.json file.
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }

    @SubscribeEvent
    static void onClientSetup(FMLClientSetupEvent event) {
        Augmenta.LOGGER.info("HELLO FROM CLIENT SETUP");
        Augmenta.LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
    }

    @SubscribeEvent
    static void onRegisterScreens(RegisterMenuScreensEvent event) {
        event.register(ModMenuTypes.AUGMENTER.get(), AugmenterScreen::new);
    }
}

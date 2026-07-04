package com.example.customzombies;

import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

@Mod(value = Customzombies.MODID, dist = Dist.CLIENT)
@EventBusSubscriber(modid = Customzombies.MODID, value = Dist.CLIENT)
public class CustomzombiesClient {
    public CustomzombiesClient(IEventBus modEventBus, ModContainer container) {
    }

    @SubscribeEvent
    static void onClientSetup(FMLClientSetupEvent event) {
        // Some client setup code
        Customzombies.LOGGER.info("HELLO FROM CLIENT SETUP");
        Customzombies.LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
    }
}

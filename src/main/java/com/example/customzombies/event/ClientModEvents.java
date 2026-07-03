package com.example.customzombies.event;

import com.example.customzombies.init.ModEntities;
import com.example.customzombies.renderer.HomelanderZombieRenderer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@EventBusSubscriber(modid = "customzombies", value = Dist.CLIENT)
public class ClientModEvents {
    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntities.HOMELANDER_ZOMBIE.get(), HomelanderZombieRenderer::new);
    }
}

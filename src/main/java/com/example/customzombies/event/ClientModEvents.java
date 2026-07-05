package com.example.customzombies.event;

import com.example.customzombies.zombie.ModZombies;
import net.minecraft.client.renderer.entity.ZombieRenderer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@EventBusSubscriber(modid = "customzombies", value = Dist.CLIENT)
public class ClientModEvents {
//    @SubscribeEvent
//    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
//        event.registerLayerDefinition(
//                CustomZombieModel.LAYER_LOCATION,
//                CustomZombieModel::createBodyLayer
//        );
//    }

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        ModZombies.all().forEach(entry -> event.registerEntityRenderer(entry.entityType().get(), ZombieRenderer::new));
    }
}

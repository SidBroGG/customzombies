package com.example.customzombies.renderer;

import com.example.customzombies.entity.CustomZombieEntity;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class CustomZombieRenderer extends HumanoidMobRenderer<CustomZombieEntity, PlayerModel<CustomZombieEntity>> {
    public CustomZombieRenderer(EntityRendererProvider.Context context) {
        super(context, new PlayerModel<>(context.bakeLayer(ModelLayers.PLAYER), false), 0.5F);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(CustomZombieEntity entity) {
        return entity.getDefinition().texture();
    }
}

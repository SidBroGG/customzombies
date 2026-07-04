package com.example.customzombies.renderer;

import com.example.customzombies.client.model.CustomZombieModel;
import com.example.customzombies.entity.CustomZombieEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class CustomZombieRenderer extends MobRenderer<CustomZombieEntity, CustomZombieModel> {
    public CustomZombieRenderer(EntityRendererProvider.Context context) {
        super(context, new CustomZombieModel(context.bakeLayer(CustomZombieModel.LAYER_LOCATION)), 0.5F);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(CustomZombieEntity entity) {
        return entity.getDefinition().texture();
    }
}

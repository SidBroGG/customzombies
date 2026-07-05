package com.example.customzombies.renderer;

import com.example.customzombies.client.model.CustomZombieModel;
import com.example.customzombies.entity.CustomZombieEntity;
import com.mojang.blaze3d.vertex.PoseStack;
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

    @Override
    protected void scale(CustomZombieEntity entity, @NotNull PoseStack poseStack, float partialTick) {
        if (entity.isBaby()) {
            poseStack.scale(0.5F, 0.5F, 0.5F);
        }
    }
}

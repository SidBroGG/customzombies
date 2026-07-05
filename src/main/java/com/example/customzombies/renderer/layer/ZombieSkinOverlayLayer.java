package com.example.customzombies.renderer.layer;

import com.example.customzombies.entity.CustomZombieEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.ZombieModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.entity.monster.Zombie;

public final class ZombieSkinOverlayLayer extends RenderLayer<Zombie, ZombieModel<Zombie>> {
    private final PlayerModel<Zombie> overlayModel;

    public ZombieSkinOverlayLayer(RenderLayerParent<Zombie, ZombieModel<Zombie>> renderer, EntityRendererProvider.Context context) {
        super(renderer);
        this.overlayModel = new PlayerModel<>(context.bakeLayer(ModelLayers.PLAYER), false);
    }

    @Override
    public void render(
            PoseStack poseStack,
            MultiBufferSource buffer,
            int packedLight,
            Zombie zombie,
            float limbSwing,
            float limbSwingAmount,
            float partinalTicks,
            float ageInTicks,
            float netHeadYaw,
            float headPitch
    ) {
        if (!(zombie instanceof CustomZombieEntity customZombie) || zombie.isInvisible()) {
            return;
        }

        this.getParentModel().copyPropertiesTo(this.overlayModel);
        this.copyOverlayPartRotations();
        this.showOnlyOverlayParts();

        var vertexConsumer = buffer.getBuffer(
                RenderType.entityTranslucent(customZombie.getDefinition().texture())
        );

        this.overlayModel.renderToBuffer(poseStack, vertexConsumer, packedLight, LivingEntityRenderer.getOverlayCoords(zombie, 0.0F));
    }

    private void copyOverlayPartRotations() {
        this.overlayModel.hat.copyFrom(this.overlayModel.head);
        this.overlayModel.jacket.copyFrom(this.overlayModel.body);
        this.overlayModel.leftSleeve.copyFrom(this.overlayModel.leftArm);
        this.overlayModel.rightSleeve.copyFrom(this.overlayModel.rightArm);
        this.overlayModel.leftPants.copyFrom(this.overlayModel.leftLeg);
        this.overlayModel.rightPants.copyFrom(this.overlayModel.rightLeg);
    }

    private void showOnlyOverlayParts() {
        this.overlayModel.head.visible = false;
        this.overlayModel.body.visible = false;
        this.overlayModel.leftArm.visible = false;
        this.overlayModel.rightArm.visible = false;
        this.overlayModel.leftLeg.visible = false;
        this.overlayModel.rightLeg.visible = false;

        this.overlayModel.hat.visible = true;
        this.overlayModel.jacket.visible = true;
        this.overlayModel.leftSleeve.visible = true;
        this.overlayModel.rightSleeve.visible = true;
        this.overlayModel.leftPants.visible = true;
        this.overlayModel.rightPants.visible = true;
    }
}

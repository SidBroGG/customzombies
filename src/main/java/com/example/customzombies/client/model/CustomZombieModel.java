package com.example.customzombies.client.model;

import com.example.customzombies.Customzombies;
import com.example.customzombies.client.animation.CustomZombieAnimations;
import com.example.customzombies.entity.CustomZombieEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public final class CustomZombieModel extends HierarchicalModel<CustomZombieEntity> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(Customzombies.MODID, "custom_zombie"), "main");

    private final ModelPart root;
    private final ModelPart head;
    private final ModelPart body;
    private final ModelPart rightLeg;
    private final ModelPart leftLeg;
    private boolean baby;

    public CustomZombieModel(ModelPart root) {
        this.root = root;
        this.head = root.getChild("head");
        this.body = root.getChild("body");
        this.rightLeg = root.getChild("right_leg");
        this.leftLeg = root.getChild("left_leg");
    }

    @Override
    public @NotNull ModelPart root() {
        return this.root;
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();

        PartDefinition body = root.addOrReplaceChild(
                "body",
                CubeListBuilder.create()
                        .texOffs(16, 16)
                        .addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F)
        );

        root.addOrReplaceChild(
                "head",
                CubeListBuilder.create()
                        .texOffs(0, 0)
                        .addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F)
        );

        body.addOrReplaceChild(
                "right_arm",
                CubeListBuilder.create()
                        .texOffs(40, 16)
                        .addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F),
                PartPose.offset(-5.0F, 2.0F, 0.0F)
        );

        body.addOrReplaceChild(
                "left_arm",
                CubeListBuilder.create()
                        .texOffs(40, 16)
                        .mirror()
                        .addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F)
                        .mirror(false),
                PartPose.offset(5.0F, 2.0F, 0.0F)
        );

        root.addOrReplaceChild(
                "right_leg",
                CubeListBuilder.create()
                        .texOffs(0, 16)
                        .addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F),
                PartPose.offset(-1.9F, 12.0F, 0.0F)
        );

        root.addOrReplaceChild(
                "left_leg",
                CubeListBuilder.create()
                        .texOffs(0, 16)
                        .mirror()
                        .addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F)
                        .mirror(false),
                PartPose.offset(1.9F, 12.0F, 0.0F)
        );

        return LayerDefinition.create(mesh, 64, 64);
    }

    @Override
    public void setupAnim(@NotNull CustomZombieEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.baby = entity.isBaby();
        this.root().getAllParts().forEach(ModelPart::resetPose);

        this.animateWalk(CustomZombieAnimations.WALK, limbSwing, limbSwingAmount, 2.0F, 2.5F);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
        if (!this.baby) {
            this.root.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
            return;
        }

        poseStack.pushPose();
        poseStack.translate(0.0D, 0.75D, 0.0D);
        this.head.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
        poseStack.popPose();

        poseStack.pushPose();
        poseStack.translate(0.0D, 0.75D, 0.0D);
        poseStack.scale(0.5F, 0.5F, 0.5F);

        this.body.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
        this.rightLeg.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
        this.leftLeg.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);

        poseStack.popPose();
    }
}

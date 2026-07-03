package com.example.customzombies.renderer;

import com.example.customzombies.Customzombies;
import com.example.customzombies.entity.HomelanderZombieEntity;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class HomelanderZombieRenderer extends HumanoidMobRenderer<HomelanderZombieEntity, PlayerModel<HomelanderZombieEntity>> {
    private static final ResourceLocation ZOMBIE_SKIN = ResourceLocation.fromNamespaceAndPath(Customzombies.MODID, "textures/entity/homelander_zombie.png");

    public HomelanderZombieRenderer(EntityRendererProvider.Context context) {
        super(context, new PlayerModel<>(context.bakeLayer(ModelLayers.PLAYER), false), 0.5F);
    }
//
//    @Override
//    protected void setupModelDirs(HomelanderZombieEntity entity, float f, float f1, float f2) {
//        super.setupModelDirs(entity, f, f1, f2);
//
//        // Принудительно включаем видимость всех внешних элементов модели (outer layers)
//        PlayerModel<HomelanderZombieEntity> playermodel = this.getModel();
//        playermodel.hat.visible = true;
//        playermodel.jacket.visible = true;
//        playermodel.leftPants.visible = true;
//        playermodel.rightPants.visible = true;
//        playermodel.leftSleeve.visible = true;
//        playermodel.rightSleeve.visible = true;
//    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull HomelanderZombieEntity entity) {
        return ZOMBIE_SKIN;
    }
}

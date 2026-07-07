package com.example.customzombies.renderer;

import com.example.customzombies.entity.CustomZombieEntity;
import com.example.customzombies.renderer.layer.ZombieSkinOverlayLayer;
import com.example.customzombies.zombie.definitions.RealZombie;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ZombieRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Zombie;
import org.jetbrains.annotations.NotNull;

public class CustomZombieRenderer extends ZombieRenderer {
    public CustomZombieRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.addLayer(new ZombieSkinOverlayLayer(this, context));
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull Zombie zombie) {
        if (zombie instanceof CustomZombieEntity customZombie) {
            if (customZombie.isRealZombie()) {
                return RealZombie.getSkin(customZombie.getSkinVariant());
            }

            return customZombie.getDefinition().texture();
        }

        return super.getTextureLocation(zombie);
    }
}

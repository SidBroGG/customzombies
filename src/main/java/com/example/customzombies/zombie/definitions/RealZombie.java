package com.example.customzombies.zombie.definitions;

import com.example.customzombies.Customzombies;
import com.example.customzombies.entity.CustomZombieEntity;
import com.example.customzombies.zombie.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.List;

public class RealZombie {
    public static final int SKIN_COUNT = 20;

    public static ResourceLocation getSkin(int variant) {
        int index = Math.floorMod(variant, SKIN_COUNT);
        String fileName = index == 0 ? "zombie" : "zombie" + index;

        return ResourceLocation.fromNamespaceAndPath(
                Customzombies.MODID,
                "textures/entity/real_zombie/" + fileName + ".png"
        );
    }

    private RealZombie() {
    }

    private static final ZombieDefinition DEFINITION = new ZombieDefinition(
            "real_zombie",
            new ZombieStats()
                    .with(Attributes.MOVEMENT_SPEED, 0.3D),
            false,
            20,
            1D,
            1,
            List.of(
                    LootDrop.flesh(1, 3, 1)
            ),
            getSkin(0),
            0x542623,
            0xff1100
    );

    public static final DeferredHolder<EntityType<?>, EntityType<CustomZombieEntity>> ENTITY =
            ModZombies.ENTITIES.register(
                    DEFINITION.id(),
                    () -> EntityType.Builder
                            .of(CustomZombieEntity::new, MobCategory.MONSTER)
                            .sized(0.6F, 1.95F)
                            .build(DEFINITION.id())
            );

    public static final DeferredHolder<Item, DeferredSpawnEggItem> SPAWN_EGG =
            ModZombies.ITEMS.register(
                    DEFINITION.id() + "_spawn_egg",
                    () -> new DeferredSpawnEggItem(
                            ENTITY,
                            DEFINITION.eggPrimaryColor(),
                            DEFINITION.eggSecondaryColor(),
                            new Item.Properties()
                    )
            );

    public static final ZombieEntry ENTRY = new ZombieEntry(
            DEFINITION.id(),
            ENTITY,
            SPAWN_EGG,
            DEFINITION
    );
}

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

public final class MaxZombie {
    private MaxZombie() {
    }

    private static final ZombieDefinition DEFINITION = new ZombieDefinition(
            "max_zombie",
            new ZombieStats()
                    .with(Attributes.MAX_HEALTH, 500.0D)
                    .with(Attributes.ATTACK_DAMAGE, 4.0D)
                    .with(Attributes.MOVEMENT_SPEED, 0.3D)
                    .with(Attributes.SCALE, 3.0D),
            false,
            20,
            1.5D,
            10,
            List.of(
                    LootDrop.flesh(1, 5, 15),
                    LootDrop.ammo("tacz:9mm", 2, 4, 10),
                    LootDrop.ammo("tacz:45acp", 2, 3, 10),
                    LootDrop.ammo("tacz:556x45", 2, 4, 10),
                    LootDrop.ammo("tacz:308", 2, 4, 10),
                    LootDrop.ammo("tacz:12g", 1, 2, 10),
                    LootDrop.ammo("tacz:rpg_rocket", 1, 1, 10)
            ),
            ResourceLocation.fromNamespaceAndPath(Customzombies.MODID, "textures/entity/max_zombie.png"),
            0xff00f7,
            0xbaa880
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

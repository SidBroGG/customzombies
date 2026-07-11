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

public final class SkufZombie {
    private static final ZombieDefinition DEFINITION = new ZombieDefinition(
            "skuf_zombie",
            new ZombieStats()
                    .with(Attributes.MAX_HEALTH, 40.0D)
                    .with(Attributes.MOVEMENT_SPEED, 0.34D)
                    .with(Attributes.SCALE, 1.1D)
                    .with(Attributes.ATTACK_DAMAGE, 2D),
            false,
            5,
            1.5D,
            2,
            List.of(
                    LootDrop.ammo("tacz:9mm", 2, 4, 100),
                    LootDrop.ammo("tacz:45acp", 2, 3, 100),
                    LootDrop.ammo("tacz:556x45", 2, 4, 100),
                    LootDrop.ammo("tacz:308", 2, 4, 100),
                    LootDrop.ammo("tacz:12g", 1, 2, 10),
                    LootDrop.ammo("tacz:rpg_rocket", 1, 1, 1)
            ),
            ResourceLocation.fromNamespaceAndPath(
                    Customzombies.MODID,
                    "textures/entity/skuf_zombie.png"
            ),
            0xd1ba73,
            0x360e11
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

    private SkufZombie() {
    }
}
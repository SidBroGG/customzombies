package com.example.customzombies.zombie.definitions;

import com.example.customzombies.Customzombies;
import com.example.customzombies.entity.CustomZombieEntity;
import com.example.customzombies.zombie.*;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.List;

public final class AlexZombie {
    private AlexZombie() {
    }

    private static final ZombieDefinition DEFINITION = new ZombieDefinition(
            "alex_zombie",
            new ZombieStats()
                    .with(Attributes.MAX_HEALTH, 30.0D)
                    .with(Attributes.MOVEMENT_SPEED, 0.4D)
                    .with(Attributes.ATTACK_DAMAGE, 2D),
            true,
            20,
            0.8D,
            1,
            List.of(
                    new LootDrop(
                            ResourceLocation.fromNamespaceAndPath(
                                    "minecraft", "rotten_flesh"
                            ), new CompoundTag(), 2, 6, 100
                    )
            ),
            ResourceLocation.fromNamespaceAndPath(Customzombies.MODID, "textures/entity/alex_zombie.png"),
            0xffee00,
            0x027a00
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

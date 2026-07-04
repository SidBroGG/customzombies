package com.example.customzombies.zombie;

import com.example.customzombies.Customzombies;
import com.example.customzombies.entity.CustomZombieEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;

public final class ModZombies {
    private ModZombies() {
    }

    public static final DeferredRegister<EntityType<?>> ENTITIES =
            DeferredRegister.create(Registries.ENTITY_TYPE, Customzombies.MODID);

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(Registries.ITEM, Customzombies.MODID);

    private static final ZombieDefinition HOMELANDER_DEFINITION = new ZombieDefinition(
            "homelander_zombie",
            new ZombieStats()
                    .with(Attributes.MAX_HEALTH, 50.0D)
                    .with(Attributes.MOVEMENT_SPEED, 0.33D)
                    .with(Attributes.SCALE, 1.2D)
                    .with(Attributes.ATTACK_DAMAGE, 1D),
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
                    "textures/entity/homelander_zombie.png"
            ),
            0x0D08A3,
            0xD43B3B
    );

    public static final DeferredHolder<EntityType<?>, EntityType<CustomZombieEntity>> HOMELANDER_ENTITY =
            ENTITIES.register(
                    HOMELANDER_DEFINITION.id(),
                    () -> EntityType.Builder
                            .of(CustomZombieEntity::new, MobCategory.MONSTER)
                            .sized(0.6F, 1.95F)
                            .build(HOMELANDER_DEFINITION.id())
            );

    public static final DeferredHolder<Item, DeferredSpawnEggItem> HOMELANDER_SPAWN_EGG =
            ITEMS.register(
                    HOMELANDER_DEFINITION.id() + "_spawn_egg",
                    () -> new DeferredSpawnEggItem(
                            HOMELANDER_ENTITY,
                            HOMELANDER_DEFINITION.eggPrimaryColor(),
                            HOMELANDER_DEFINITION.eggSecondaryColor(),
                            new Item.Properties()
                    )
            );

    public static final ZombieEntry HOMELANDER_ZOMBIE = new ZombieEntry(
            HOMELANDER_DEFINITION.id(),
            HOMELANDER_ENTITY,
            HOMELANDER_SPAWN_EGG,
            HOMELANDER_DEFINITION
    );

    private static final List<ZombieEntry> ALL = List.of(
            HOMELANDER_ZOMBIE
    );

    public static List<ZombieEntry> all() {
        return ALL;
    }

    public static ZombieEntry getByEntityType(EntityType<?> entityType) {
        return ALL.stream()
                .filter(entry -> entry.entityType().get().equals(entityType))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("no zombie definition for entity type: " + entityType));
    }


}

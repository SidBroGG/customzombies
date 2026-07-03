package com.example.customzombies.init;

import com.example.customzombies.entity.HomelanderZombieEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(Registries.ENTITY_TYPE, "customzombies");

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, "customzombies");

    public static final DeferredHolder<EntityType<?>, EntityType<HomelanderZombieEntity>> HOMELANDER_ZOMBIE =
            ENTITIES.register("homelander_zombie", () -> EntityType.Builder.of(HomelanderZombieEntity::new, MobCategory.MONSTER).sized(0.6F, 1.95F).build("homelander_zombie"));

    public static final DeferredHolder<Item, DeferredSpawnEggItem> HOMELANDER_ZOMBIE_SPAWN_EGG =
            ITEMS.register("homelander_zombie_spawn_egg", () -> new DeferredSpawnEggItem(HOMELANDER_ZOMBIE, 0x0d08a3, 0xd43b3b, new Item.Properties()));
}

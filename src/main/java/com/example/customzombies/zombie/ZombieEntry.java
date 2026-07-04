package com.example.customzombies.zombie;

import com.example.customzombies.entity.CustomZombieEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.registries.DeferredHolder;

public record ZombieEntry(
        String id,
        DeferredHolder<EntityType<?>, EntityType<CustomZombieEntity>> entityType,
        DeferredHolder<Item, DeferredSpawnEggItem> spawnEgg,
        ZombieDefinition definition
) {
}

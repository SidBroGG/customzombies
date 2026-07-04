package com.example.customzombies.zombie;

import net.minecraft.world.entity.EntityType;
import net.neoforged.neoforge.registries.DeferredHolder;

public record ZombieEntry(
        String id,
        DeferredHolder<EntityType<?>, EntityType<CustomZombieEntity>> entityType,
        ZombieDefinition definition
) {
}

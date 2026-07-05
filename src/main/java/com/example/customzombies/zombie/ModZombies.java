package com.example.customzombies.zombie;

import com.example.customzombies.Customzombies;
import com.example.customzombies.zombie.definitions.AlexZombie;
import com.example.customzombies.zombie.definitions.HomelanderZombie;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;

public final class ModZombies {
    private ModZombies() {
    }

    public static final DeferredRegister<EntityType<?>> ENTITIES =
            DeferredRegister.create(Registries.ENTITY_TYPE, Customzombies.MODID);

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(Registries.ITEM, Customzombies.MODID);

    // All zombie classes here
    private static final List<ZombieEntry> ALL = List.of(
            HomelanderZombie.ENTRY,
            AlexZombie.ENTRY
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

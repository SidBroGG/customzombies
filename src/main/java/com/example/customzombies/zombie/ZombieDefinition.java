package com.example.customzombies.zombie;

import net.minecraft.resources.ResourceLocation;

import java.util.List;

public record ZombieDefinition(
        String id,
        ZombieStats stats,
        boolean isBaby,
        int attackCooldownTicks,
        double attackReach,
        int lootRolls,
        List<LootDrop> loot,
        ResourceLocation texture,
        int eggPrimaryColor,
        int eggSecondaryColor
) {
    public ZombieDefinition {
        loot = List.copyOf(loot);
    }
}

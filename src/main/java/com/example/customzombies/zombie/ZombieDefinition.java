package com.example.customzombies.zombie;

import net.minecraft.resources.ResourceLocation;

import java.util.List;

public record ZombieDefinition(
        String id,
        ZombieStats stats,
        int attackCooldownTicks,
        ResourceLocation texture,
        List<LootDrop> loot,
        int eggPrimaryColor,
        int eggSecondaryColor
) {
}

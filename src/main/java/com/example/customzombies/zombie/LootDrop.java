package com.example.customzombies.zombie;

import net.minecraft.world.item.ItemStack;

import java.util.Objects;

public record LootDrop(
        ItemStack item,
        int minCount,
        int maxCount,
        float chance
) {
    public LootDrop {
        Objects.requireNonNull(item, "item");

        if (minCount < 1) {
            throw new IllegalArgumentException("minCount must be at least 1");
        }

        if (maxCount < minCount) {
            throw new IllegalArgumentException("maxCount must be greater than or equal to minCount");
        }

        if (chance <= 0.0F || chance >= 1.0F) {
            throw new IllegalArgumentException("chance must be between 0.0F and 1.0F");
        }
    }
}

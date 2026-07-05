package com.example.customzombies.zombie;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;

import java.util.Objects;

public record LootDrop(
        ResourceLocation itemId,
        CompoundTag customData,
        int minCount,
        int maxCount,
        int weight
) {
    public LootDrop {
        Objects.requireNonNull(itemId, "itemId");

        Objects.requireNonNull(customData, "customData");
        customData = customData.copy();

        if (minCount < 1) {
            throw new IllegalArgumentException("minCount must be at least 1");
        }

        if (maxCount < minCount) {
            throw new IllegalArgumentException("maxCount must be greater than or equal to minCount");
        }

        if (weight < 1) {
            throw new IllegalArgumentException("weight must be at least 1");
        }
    }

    public static LootDrop ammo(String ammoId, int minCount, int maxCount, int weight) {
        CompoundTag data = new CompoundTag();
        data.putString("AmmoId", ammoId);

        return new LootDrop(
                ResourceLocation.fromNamespaceAndPath("tacz", "ammo"),
                data,
                minCount,
                maxCount,
                weight
        );
    }

    public static LootDrop flesh(int minCount, int maxCount, int weight) {
        return new LootDrop(
                ResourceLocation.fromNamespaceAndPath("minecraft", "rotten_flesh"),
                new CompoundTag(),
                minCount,
                maxCount,
                weight
        );
    }
}

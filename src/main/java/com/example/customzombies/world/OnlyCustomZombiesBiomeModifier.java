package com.example.customzombies.world;

import com.example.customzombies.zombie.ModZombies;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.ModifiableBiomeInfo;
import org.jetbrains.annotations.NotNull;

public record OnlyCustomZombiesBiomeModifier(HolderSet<Biome> biomes) implements BiomeModifier {
    public static final MapCodec<OnlyCustomZombiesBiomeModifier> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Biome.LIST_CODEC.fieldOf("biomes").forGetter(OnlyCustomZombiesBiomeModifier::biomes)
    ).apply(instance, OnlyCustomZombiesBiomeModifier::new));

    @Override
    public void modify(@NotNull Holder<Biome> biome, @NotNull Phase phase, ModifiableBiomeInfo.BiomeInfo.@NotNull Builder builder) {
        if (phase != Phase.REMOVE || !this.biomes.contains(biome)) {
            return;
        }

        var spawns = builder.getMobSpawnSettings();

        for (var category : MobCategory.values()) {
            spawns.getSpawner(category).removeIf(spawner -> !isCustomZombie(spawner.type));
        }
    }

    @Override
    public @NotNull MapCodec<? extends BiomeModifier> codec() {
        return ModBiomeModifierSerializers.ONLY_CUSTOM_ZOMBIES.get();
    }

    private static boolean isCustomZombie(EntityType<?> type) {
        return ModZombies.all().stream().anyMatch(entry -> entry.entityType().get().equals(type));
    }
}

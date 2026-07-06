package com.example.customzombies.data;

import com.example.customzombies.Customzombies;
import com.example.customzombies.world.OnlyCustomZombiesBiomeModifier;
import com.example.customzombies.zombie.definitions.AlexZombie;
import com.example.customzombies.zombie.definitions.HomelanderZombie;
import com.example.customzombies.zombie.definitions.MaxZombie;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.List;

public final class ModBiomeModifiers {
    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(NeoForgeRegistries.Keys.BIOME_MODIFIERS, ModBiomeModifiers::bootstrap);

    private static void bootstrap(BootstrapContext<BiomeModifier> context) {
        var overworldBiomes = context.lookup(Registries.BIOME).getOrThrow(BiomeTags.IS_OVERWORLD);

        var spawners = List.of(
                new MobSpawnSettings.SpawnerData(
                        EntityType.ZOMBIE,
                        100,
                        1,
                        8
                ),
                new MobSpawnSettings.SpawnerData(
                        HomelanderZombie.ENTITY.get(),
                        10,
                        1,
                        3
                ),
                new MobSpawnSettings.SpawnerData(
                        AlexZombie.ENTITY.get(),
                        10,
                        1,
                        4
                ),
                new MobSpawnSettings.SpawnerData(
                        MaxZombie.ENTITY.get(),
                        1,
                        1,
                        1
                )
        );

        context.register(
                key("overworld_custom_zombies"),
                new BiomeModifiers.AddSpawnsBiomeModifier(overworldBiomes, spawners)
        );

        context.register(
                key("overworld_custom_overworld_spawns"),
                new OnlyCustomZombiesBiomeModifier(overworldBiomes)
        );
    }

    private static ResourceKey<BiomeModifier> key(String name) {
        return ResourceKey.create(
                NeoForgeRegistries.Keys.BIOME_MODIFIERS,
                ResourceLocation.fromNamespaceAndPath(Customzombies.MODID, name)
        );
    }
}

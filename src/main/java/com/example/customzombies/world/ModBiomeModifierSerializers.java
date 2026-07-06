package com.example.customzombies.world;

import com.example.customzombies.Customzombies;
import com.mojang.serialization.MapCodec;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public final class ModBiomeModifierSerializers {
    private ModBiomeModifierSerializers() {
    }

    public static final DeferredRegister<MapCodec<? extends BiomeModifier>> BIOME_MODIFIER_SERIALIZERS =
            DeferredRegister.create(NeoForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS, Customzombies.MODID);

    public static final DeferredHolder<MapCodec<? extends BiomeModifier>, MapCodec<OnlyCustomZombiesBiomeModifier>> ONLY_CUSTOM_ZOMBIES =
            BIOME_MODIFIER_SERIALIZERS.register("only_custom_zombies", () -> OnlyCustomZombiesBiomeModifier.CODEC);
}

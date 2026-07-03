package com.example.customzombies.loot;

import com.example.customzombies.init.ModEntities;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.loot.EntityLootSubProvider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetComponentsFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import org.jetbrains.annotations.NotNull;

import java.util.stream.Stream;

public class ModEntityLoot extends EntityLootSubProvider {
    public ModEntityLoot(HolderLookup.Provider registries) {
        super(FeatureFlags.REGISTRY.allFlags(), registries);
    }

    @Override
    public void generate() {
        final var ammoItem = BuiltInRegistries.ITEM.get(ResourceLocation.fromNamespaceAndPath("tacz", "ammo"));

        final var ammo9mmTag = new CompoundTag();
        ammo9mmTag.putString("AmmoId", "tacz:9mm");

        final var ammo45AcpTag = new CompoundTag();
        ammo45AcpTag.putString("AmmoId", "tacz:45acp");

        final var ammo556X45Tag = new CompoundTag();
        ammo556X45Tag.putString("AmmoId", "tacz:556x45");

        final var ammo308Tag = new CompoundTag();
        ammo308Tag.putString("AmmoId", "tacz:308");

        final var ammoRpgRocketTag = new CompoundTag();
        ammoRpgRocketTag.putString("AmmoId", "tacz:rpg_rocket");

        final var ammo12GTag = new CompoundTag();
        ammo12GTag.putString("AmmoId", "tacz:12g");

        final var AMMO_9MM = LootItem.lootTableItem(ammoItem).apply(SetComponentsFunction.setComponent(DataComponents.CUSTOM_DATA, CustomData.of(ammo9mmTag)));
        final var AMMO_45ACP = LootItem.lootTableItem(ammoItem).apply(SetComponentsFunction.setComponent(DataComponents.CUSTOM_DATA, CustomData.of(ammo45AcpTag)));
        final var AMMO_556X45 = LootItem.lootTableItem(ammoItem).apply(SetComponentsFunction.setComponent(DataComponents.CUSTOM_DATA, CustomData.of(ammo556X45Tag)));
        final var AMMO_308 = LootItem.lootTableItem(ammoItem).apply(SetComponentsFunction.setComponent(DataComponents.CUSTOM_DATA, CustomData.of(ammo308Tag)));
        final var AMMO_RPG_ROCKET = LootItem.lootTableItem(ammoItem).apply(SetComponentsFunction.setComponent(DataComponents.CUSTOM_DATA, CustomData.of(ammoRpgRocketTag)));
        final var AMMO_12G = LootItem.lootTableItem(ammoItem).apply(SetComponentsFunction.setComponent(DataComponents.CUSTOM_DATA, CustomData.of(ammo12GTag)));

        this.add(ModEntities.HOMELANDER_ZOMBIE.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(2))
                        .add(AMMO_9MM.setWeight(10).apply(SetItemCountFunction.setCount(UniformGenerator.between(5.0f, 7.0f))))
                        .add(AMMO_45ACP.setWeight(10).apply(SetItemCountFunction.setCount(UniformGenerator.between(4.0f, 6.0f))))
                        .add(AMMO_556X45.setWeight(10).apply(SetItemCountFunction.setCount(UniformGenerator.between(5.0f, 7.0f))))
                        .add(AMMO_308.setWeight(10).apply(SetItemCountFunction.setCount(UniformGenerator.between(5.0f, 7.0f))))
                        .add(AMMO_12G.setWeight(10).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0f, 3.0f))))
                        .add(AMMO_RPG_ROCKET.setWeight(1).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1))))
                )
        );
    }

    @Override
    protected @NotNull Stream<EntityType<?>> getKnownEntityTypes() {
        return Stream.of(ModEntities.HOMELANDER_ZOMBIE.get());
    }
}

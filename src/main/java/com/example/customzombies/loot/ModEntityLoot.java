package com.example.customzombies.loot;

import com.example.customzombies.zombie.LootDrop;
import com.example.customzombies.zombie.ModZombies;
import com.example.customzombies.zombie.ZombieEntry;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.loot.EntityLootSubProvider;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
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
        ModZombies.all().forEach(this::addZombieLoot);
    }

    private void addZombieLoot(ZombieEntry zombie) {
        var pool = LootPool.lootPool()
                .setRolls(ConstantValue.exactly(zombie.definition().lootRolls()));

        zombie.definition().loot().forEach(drop -> pool.add(createDrop(drop)));


        this.add(
                zombie.entityType().get(),
                LootTable.lootTable().withPool(pool)
        );
    }

    private LootPoolEntryContainer.Builder<?> createDrop(LootDrop drop) {
        var item = BuiltInRegistries.ITEM.get(drop.itemId());

        var entry = LootItem.lootTableItem(item)
                .setWeight(drop.weight())
                .apply(SetItemCountFunction.setCount(
                        drop.minCount() == drop.maxCount() ? ConstantValue.exactly(drop.minCount()) : UniformGenerator.between(drop.minCount(), drop.maxCount())
                ));

        entry.apply(SetComponentsFunction.setComponent(DataComponents.CUSTOM_DATA, CustomData.of(drop.customData().copy())));

        return entry;
    }

    @Override
    protected @NotNull Stream<EntityType<?>> getKnownEntityTypes() {
        return ModZombies.all().stream().map(entry -> entry.entityType().get());
    }
}

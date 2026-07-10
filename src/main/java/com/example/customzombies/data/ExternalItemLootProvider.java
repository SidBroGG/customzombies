package com.example.customzombies.data;

import com.example.customzombies.zombie.LootDrop;
import com.example.customzombies.zombie.ModZombies;
import com.example.customzombies.zombie.ZombieEntry;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;

import java.util.concurrent.CompletableFuture;

public final class ExternalItemLootProvider implements DataProvider {
    private final PackOutput.PathProvider pathProvider;

    public ExternalItemLootProvider(PackOutput output) {
        this.pathProvider = output.createRegistryElementsPathProvider(Registries.LOOT_TABLE);
    }

    private static JsonObject createLootTable(ZombieEntry zombie) {
        JsonObject root = new JsonObject();
        root.addProperty("type", "minecraft:entity");

        JsonArray pools = new JsonArray();
        JsonObject pool = new JsonObject();

        pool.addProperty("rolls", zombie.definition().lootRolls());

        JsonArray entries = new JsonArray();
        zombie.definition().loot().forEach(drop -> entries.add(createDrop(drop)));

        pool.add("entries", entries);
        pools.add(pool);
        root.add("pools", pools);

        return root;
    }

    private static JsonObject createDrop(LootDrop drop) {
        JsonObject entry = new JsonObject();

        entry.addProperty("type", "minecraft:item");
        entry.addProperty("name", drop.itemId().toString());
        entry.addProperty("weight", drop.weight());

        JsonArray functions = new JsonArray();
        functions.add(createCountFunction(drop));

        if (!drop.customData().isEmpty()) {
            functions.add(createCustomDataFunction(drop));
        }

        entry.add("functions", functions);

        return entry;
    }

    private static JsonObject createCountFunction(LootDrop drop) {
        JsonObject function = new JsonObject();

        function.addProperty("function", "minecraft:set_count");

        if (drop.minCount() == drop.maxCount()) {
            function.addProperty("count", drop.minCount());
        } else {
            JsonObject count = new JsonObject();
            count.addProperty("min", drop.minCount());
            count.addProperty("max", drop.maxCount());
            function.add("count", count);
        }

        return function;
    }

    private static JsonObject createCustomDataFunction(LootDrop drop) {
        JsonObject function = new JsonObject();
        function.addProperty("function", "minecraft:set_components");

        JsonObject components = new JsonObject();
        components.addProperty("minecraft:custom_data", drop.customData().toString());

        function.add("components", components);

        return function;
    }

    @Override
    public String getName() {
        return "Custom Zombies External Item Loot Tables";
    }

    @Override
    public CompletableFuture<?> run(CachedOutput output) {
        return CompletableFuture.allOf(
                ModZombies.all().stream()
                        .map(zombie -> DataProvider.saveStable(
                                output,
                                createLootTable(zombie),
                                this.pathProvider.json(zombie.entityType().get().getDefaultLootTable().location())
                        ))
                        .toArray(CompletableFuture[]::new)
        );
    }

}

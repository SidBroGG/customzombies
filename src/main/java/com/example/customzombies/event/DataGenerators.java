package com.example.customzombies.event;

import com.example.customzombies.Customzombies;
import com.example.customzombies.data.ExternalItemLootProvider;
import com.example.customzombies.data.ModBiomeModifiers;
import com.example.customzombies.data.ModItemModelProvider;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.Set;

@EventBusSubscriber(modid = "customzombies")
public class DataGenerators {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        var generator = event.getGenerator();
        var packOutput = generator.getPackOutput();
        var lookupProvider = event.getLookupProvider();

        generator.addProvider(event.includeServer(), new ExternalItemLootProvider(packOutput));
        generator.addProvider(event.includeClient(), new ModItemModelProvider(packOutput, event.getExistingFileHelper()));
        generator.addProvider(event.includeServer(), new DatapackBuiltinEntriesProvider(packOutput, lookupProvider, ModBiomeModifiers.BUILDER, Set.of(Customzombies.MODID)));
    }
}

package com.example.customzombies.data;

import com.example.customzombies.Customzombies;
import com.example.customzombies.zombie.ModZombies;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public final class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, Customzombies.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        ModZombies.all().forEach(entry ->
                withExistingParent(entry.id() + "_spawn_egg", ResourceLocation.fromNamespaceAndPath("minecraft", "item/template_spawn_egg"))
        );
    }
}

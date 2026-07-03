package com.example.customzombies.data;

import com.example.customzombies.init.ModEntities;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, "customzombies", existingFileHelper);
    }

    @Override
    protected void registerModels() {
        withExistingParent(ModEntities.HOMELANDER_ZOMBIE_SPAWN_EGG.getId().getPath(), "minecraft:item/template_spawn_egg");
    }
}

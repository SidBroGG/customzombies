package com.example.customzombies;

import com.example.customzombies.zombie.ModZombies;
import com.mojang.logging.LogUtils;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(Customzombies.MODID)
public class Customzombies {
    // Define mod id in a common place for everything to reference
    public static final String MODID = "customzombies";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();

    public Customzombies(IEventBus modEventBus, ModContainer modContainer) {
        ModZombies.ENTITIES.register(modEventBus);
        ModZombies.ITEMS.register(modEventBus);
    }

}

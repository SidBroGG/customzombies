package com.example.customzombies.event;

import com.example.customzombies.Customzombies;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;

@EventBusSubscriber(modid = Customzombies.MODID)
public final class ModEvents {
    private ModEvents() {
    }

    @SubscribeEvent
    public static void onCreateAttributes(EntityAttributeCreationEvent event) {
        ModZombies.all().forEach(entry -> {
            var builder = Zombie.createAttributes();

            entry.definition().stats().applyTo(builder);

            event.put(entry.entityType().get(), builder.build());
        });
    }

    @SubscribeEvent
    public static void addCreativeTabs(BuildCreativeModeTabContentsEvent event) {
        if (!event.getTabKey().equals(CreativeModeTabs.SPAWN_EGGS)) {
            return;
        }

        ModZombies.all().forEach(entry -> event.accept(entry.spawnEgg().get()));
    }
}

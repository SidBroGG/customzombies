package com.example.customzombies.event;

import com.example.customzombies.Customzombies;
import com.example.customzombies.entity.CustomZombieEntity;
import com.example.customzombies.zombie.ModZombies;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;

@EventBusSubscriber(modid = Customzombies.MODID)
public final class CommonModEvents {
    private CommonModEvents() {
    }

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        ModZombies.all().forEach(entry -> event.put(entry.entityType().get(), CustomZombieEntity.createAttributes(entry.definition()).build()));
    }

    @SubscribeEvent
    public static void addSpawnEggsToCreativeTab(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey().equals(CreativeModeTabs.SPAWN_EGGS)) {
            ModZombies.all().forEach(entry -> event.accept(entry.spawnEgg().get()));
        }
    }

    @SubscribeEvent
    public static void registerSpawnPlacements(RegisterSpawnPlacementsEvent event) {
        ModZombies.all().forEach(entry -> event.register(
                entry.entityType().get(),
                SpawnPlacementTypes.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Monster::checkMonsterSpawnRules,
                RegisterSpawnPlacementsEvent.Operation.OR
        ));
    }
}

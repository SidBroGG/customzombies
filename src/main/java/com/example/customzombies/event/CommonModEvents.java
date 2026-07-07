package com.example.customzombies.event;

import com.example.customzombies.Customzombies;
import com.example.customzombies.entity.CustomZombieEntity;
import com.example.customzombies.zombie.ModZombies;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;

@EventBusSubscriber(modid = Customzombies.MODID)
public final class CommonModEvents {
    private final static float DAY_SPAWN_CHANCE = 0.4F;

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
                CommonModEvents::checkCustomZombieSpawnRules,
                RegisterSpawnPlacementsEvent.Operation.OR
        ));
    }

    private static boolean checkCustomZombieSpawnRules(
            EntityType<CustomZombieEntity> type,
            ServerLevelAccessor level,
            MobSpawnType spawnType,
            BlockPos pos,
            RandomSource random
    ) {
        return level.getDifficulty() != Difficulty.PEACEFUL
                && Mob.checkMobSpawnRules(type, level, spawnType, pos, random)
                && passesDayNightSpawnChance(type, level, random);
    }

    private static boolean passesDayNightSpawnChance(EntityType<CustomZombieEntity> type, ServerLevelAccessor level, RandomSource random) {
        if (isDay(level)) {
            return random.nextFloat() < DAY_SPAWN_CHANCE;
        }

        return true;
    }

    private static boolean isDay(ServerLevelAccessor level) {
        long dayTime = level.getLevel().getDayTime() % 24000L;
        return dayTime >= 0L && dayTime < 13000L;
    }
}

package com.example.customzombies.entity;

import com.example.customzombies.entity.ai.CustomMeleeAttackGoal;
import com.example.customzombies.zombie.ModZombies;
import com.example.customzombies.zombie.ZombieDefinition;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.goal.ZombieAttackGoal;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class CustomZombieEntity extends Zombie {
    public CustomZombieEntity(EntityType<? extends Zombie> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier.Builder createAttributes(ZombieDefinition definition) {
        var builder = Zombie.createAttributes();
        definition.stats().applyTo(builder);
        return builder;
    }

    public ZombieDefinition getDefinition() {
        return ModZombies.getByEntityType(this.getType()).definition();
    }

    @Override
    public void setBaby(boolean baby) {
        super.setBaby(this.getDefinition().isBaby());
    }

    @Override
    public boolean isBaby() {
        return this.getDefinition().isBaby();
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.removeAllGoals(goal -> goal instanceof ZombieAttackGoal);

        this.goalSelector.addGoal(2, new CustomMeleeAttackGoal(this, 1.0D, this.getDefinition().attackCooldownTicks(), this.getDefinition().attackReach(), false));
    }

    @Override
    public @Nullable SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor level, @NotNull DifficultyInstance difficulty, @NotNull MobSpawnType spawnType, @Nullable SpawnGroupData spawnGroupData) {
        var data = super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData);

        this.setCanPickUpLoot(false);

        for (var slot : EquipmentSlot.values()) {
            this.setItemSlot(slot, ItemStack.EMPTY);
            this.setDropChance(slot, 0.0F);
        }

        return data;
    }
}

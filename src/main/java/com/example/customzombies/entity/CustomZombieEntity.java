package com.example.customzombies.entity;

import com.example.customzombies.entity.ai.CustomMeleeAttackGoal;
import com.example.customzombies.zombie.ZombieDefinition;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.ZombieAttackGoal;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.level.Level;

public final class CustomZombieEntity extends Zombie {
    public CustomZombieEntity(EntityType<? extends Zombie> entityType, Level level) {
        super(entityType, level);
    }

    public final ZombieDefinition getDefinition() {
        return ModZombies.getByEntityType(this.getType()).definition();
    }

    @Override
    public void setBaby(boolean baby) {
        super.setBaby(false);
    }

    @Override
    public boolean isBaby() {
        return false;
    }

    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.removeAllGoals(goal -> goal instanceof ZombieAttackGoal);

        this.goalSelector.addGoal(2, new CustomMeleeAttackGoal(this, 1.0D, this.getDefinition().attackCooldownTicks(), false));
    }
}

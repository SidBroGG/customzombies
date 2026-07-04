package com.example.customzombies.entity;

import com.example.customzombies.entity.ai.CustomMeleeAttackGoal;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.level.Level;

public class HomelanderZombieEntity extends Zombie {
    public HomelanderZombieEntity(EntityType<? extends Zombie> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public void setBaby(boolean baby) {
        super.setBaby(false);
    }

    @Override
    public boolean isBaby() {
        return false;
    }

    // Modify attack speed
    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new CustomMeleeAttackGoal(this, 1.2D, 10, false));
    }

}

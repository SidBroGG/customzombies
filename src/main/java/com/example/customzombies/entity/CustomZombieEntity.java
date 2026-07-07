package com.example.customzombies.entity;

import com.example.customzombies.entity.ai.CustomMeleeAttackGoal;
import com.example.customzombies.zombie.ModZombies;
import com.example.customzombies.zombie.ZombieDefinition;
import com.example.customzombies.zombie.definitions.RealZombie;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.ZombieAttackGoal;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class CustomZombieEntity extends Zombie {
    private static final String SKIN_VARIANT_TAG = "RealZombieSkinVariant";

    private static final EntityDataAccessor<Integer> DATA_SKIN_VARIANT =
            SynchedEntityData.defineId(CustomZombieEntity.class, EntityDataSerializers.INT);

    public CustomZombieEntity(EntityType<? extends Zombie> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier.Builder createAttributes(ZombieDefinition definition) {
        var builder = Zombie.createAttributes()
                .add(Attributes.FOLLOW_RANGE, 48.0D);
        definition.stats().applyTo(builder);
        return builder;
    }

    @Override
    public boolean checkSpawnRules(LevelAccessor level, @NotNull MobSpawnType spawnReason) {
        return level.getDifficulty() != Difficulty.PEACEFUL;
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

        if (this.isRealZombie()) {
            this.setSkinVariant(this.getRandom().nextInt(RealZombie.SKIN_COUNT));
        }

        return data;
    }

    @Override
    protected boolean isSunSensitive() {
        return false;
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.@NotNull Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_SKIN_VARIANT, 0);
    }

    public int getSkinVariant() {
        return this.entityData.get(DATA_SKIN_VARIANT);
    }

    private void setSkinVariant(int skinVariant) {
        this.entityData.set(DATA_SKIN_VARIANT, Math.floorMod(skinVariant, RealZombie.SKIN_COUNT));
    }

    public boolean isRealZombie() {
        return this.getType() == RealZombie.ENTITY.get();
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag tag) {
        super.readAdditionalSaveData(tag);

        if (tag.contains(SKIN_VARIANT_TAG)) {
            this.setSkinVariant(tag.getInt(SKIN_VARIANT_TAG));
        }
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt(SKIN_VARIANT_TAG, this.getSkinVariant());
    }
}

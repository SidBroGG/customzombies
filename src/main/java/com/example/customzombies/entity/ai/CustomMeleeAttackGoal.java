package com.example.customzombies.entity.ai;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.pathfinder.Path;

import java.util.EnumSet;

public final class CustomMeleeAttackGoal extends Goal {
    private static final long CAN_USE_CHECK_COOLDOWN = 20L;

    private final PathfinderMob mob;
    private final double speedModifier;
    private final int attackCooldown;
    private final boolean followingTargetEvenIfNotSeen;

    private Path path;
    private double pathedTargetX;
    private double pathedTargetY;
    private double pathedTargetZ;
    private int ticksUntilNextPathRecalculation;
    private int ticksUntilNextAttack;
    private long lastCanUseCheck;

    private final double attackReach;

    public CustomMeleeAttackGoal(
            PathfinderMob mob,
            double speedModifier,
            int attackCooldown,
            double attackReach,
            boolean followingTargetEvenIfNotSeen
    ) {
        this.mob = mob;
        this.speedModifier = speedModifier;
        this.attackCooldown = Math.max(1, attackCooldown);
        this.attackReach = attackReach;
        this.followingTargetEvenIfNotSeen = followingTargetEvenIfNotSeen;

        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        long gameTime = this.mob.level().getGameTime();

        if (gameTime - this.lastCanUseCheck < CAN_USE_CHECK_COOLDOWN) {
            return false;
        }

        this.lastCanUseCheck = gameTime;

        LivingEntity target = this.mob.getTarget();
        if (target == null || !target.isAlive()) {
            return false;
        }

        this.path = this.mob.getNavigation().createPath(target, 0);
        return this.path != null || this.mob.isWithinMeleeAttackRange(target);
    }

    @Override
    public boolean canContinueToUse() {
        LivingEntity target = this.mob.getTarget();
        return target != null && target.isAlive();
    }

    @Override
    public void start() {
        this.mob.getNavigation().moveTo(this.path, this.speedModifier);
        this.mob.setAggressive(true);
        this.ticksUntilNextPathRecalculation = 0;
        this.ticksUntilNextAttack = 0;
    }

    @Override
    public void stop() {
        LivingEntity target = this.mob.getTarget();

        if (!EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(target)) {
            this.mob.setTarget(null);
        }

        this.mob.setAggressive(false);
        this.mob.getNavigation().stop();
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    @Override
    public void tick() {
        LivingEntity target = this.mob.getTarget();
        if (target == null) {
            return;
        }

        this.mob.getLookControl().setLookAt(target, 30.0F, 30.0F);
        this.ticksUntilNextPathRecalculation = Math.max(this.ticksUntilNextPathRecalculation - 1, 0);

        boolean shouldRecalculatePath =
                (this.followingTargetEvenIfNotSeen || this.mob.getSensing().hasLineOfSight(target))
                        && this.ticksUntilNextPathRecalculation <= 0
                        && (
                        this.pathedTargetX == 0.0D
                                && this.pathedTargetY == 0.0D
                                && this.pathedTargetZ == 0.0D
                                || target.distanceToSqr(
                                this.pathedTargetX,
                                this.pathedTargetY,
                                this.pathedTargetZ
                        ) >= 1.0D
                                || this.mob.getRandom().nextFloat() < 0.05F
                );

        if (shouldRecalculatePath) {
            this.pathedTargetX = target.getX();
            this.pathedTargetY = target.getY();
            this.pathedTargetZ = target.getZ();

            this.ticksUntilNextPathRecalculation = 4 + this.mob.getRandom().nextInt(7);

            double distanceSqr = this.mob.distanceToSqr(target);

            if (distanceSqr > 1024.0D) {
                this.ticksUntilNextPathRecalculation += 10;
            } else if (distanceSqr > 256.0D) {
                this.ticksUntilNextPathRecalculation += 5;
            }

            if (!this.mob.getNavigation().moveTo(target, this.speedModifier)) {
                this.ticksUntilNextPathRecalculation += 15;
            }

            this.ticksUntilNextPathRecalculation =
                    this.adjustedTickDelay(this.ticksUntilNextPathRecalculation);
        }

        this.ticksUntilNextAttack = Math.max(this.ticksUntilNextAttack - 1, 0);
        this.checkAndPerformAttack(target);
    }

    private void checkAndPerformAttack(LivingEntity target) {
        if (!this.canPerformAttack(target)) {
            return;
        }

        this.ticksUntilNextAttack = this.adjustedTickDelay(this.attackCooldown);
        this.mob.swing(InteractionHand.MAIN_HAND);
        this.mob.doHurtTarget(target);
    }

    private boolean canPerformAttack(LivingEntity target) {
        return this.ticksUntilNextAttack <= 0
                && this.mob.isWithinMeleeAttackRange(target)
                && this.mob.getSensing().hasLineOfSight(target);
    }


}
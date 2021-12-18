package com.traverse.tuffdepth.data;

import net.minecraft.world.effect.MobEffectInstance;

import java.util.Optional;

public class EntityCondition {

    private final int minYLevel;
    private final int maxYLevel;
    private final double damageMultiplier;
    private final double healthMultiplier;
    private final Optional<MobEffectInstance> potionEffect;

    public EntityCondition(int minYLevel, int maxYLevel, double damageMultiplier, double healthMultiplier, Optional<MobEffectInstance> potionEffect) {
        this.minYLevel = minYLevel;
        this.maxYLevel = maxYLevel;
        this.damageMultiplier = damageMultiplier;
        this.healthMultiplier = healthMultiplier;
        this.potionEffect = potionEffect;
    }

    public int getMinYLevel() {
        return minYLevel;
    }

    public int getMaxYLevel() {
        return maxYLevel;
    }

    public double getDamageMultiplier() {
        return damageMultiplier;
    }

    public double getHealthMultiplier() {
        return healthMultiplier;
    }

    public Optional<MobEffectInstance> getPotionEffect() {
        return potionEffect;
    }
}
package com.example.customzombies.zombie;

import net.minecraft.core.Holder;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;

import java.util.LinkedHashMap;
import java.util.Map;

public final class ZombieStats {
    private final Map<Holder<Attribute>, Double> overrides = new LinkedHashMap<>();

    public ZombieStats with(Holder<Attribute> attribute, double value) {
        overrides.put(attribute, value);
        return this;
    }

    public void applyTo(AttributeSupplier.Builder builder) {
        overrides.forEach(builder::add);
    }
}

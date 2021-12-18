package com.traverse.tuffdepth.data;

import net.minecraft.world.entity.EntityType;

import java.util.List;

public class EntityModifier {

    private final List<EntityCondition> conditions;
    private final EntityType<?> entityType;

    public EntityModifier(EntityType<?> entityType, List<EntityCondition> conditions) {
        this.entityType = entityType;
        this.conditions = conditions;
    }

    public List<EntityCondition> getConditions() {
        return conditions;
    }

    public EntityType<?> getEntityType() {
        return entityType;
    }
}

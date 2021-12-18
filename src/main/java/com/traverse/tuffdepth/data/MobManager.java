package com.traverse.tuffdepth.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.traverse.tuffdepth.TuffDepthConfig;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.*;

public class MobManager {

    private static final MobManager INSTANCE = new MobManager();
    private static final Codec<MobEffect> MOB_EFFECT_CODEC = ResourceLocation.CODEC.xmap(ForgeRegistries.MOB_EFFECTS::getValue, ForgeRegistries.MOB_EFFECTS::getKey);
    private static final Codec<EntityType<?>> ENTITY_TYPE_CODEC = ResourceLocation.CODEC.xmap(ForgeRegistries.ENTITIES::getValue, ForgeRegistries.ENTITIES::getKey);

    private static final Codec<MobEffectInstance> MOB_EFFECT_INSTANCE_CODEC = RecordCodecBuilder.create(instance -> instance.group(
            MOB_EFFECT_CODEC.fieldOf("potion").forGetter(MobEffectInstance::getEffect),
            Codec.INT.fieldOf("duration").forGetter(MobEffectInstance::getDuration),
            Codec.INT.fieldOf("amplifier").forGetter(MobEffectInstance::getAmplifier)
    ).apply(instance, MobEffectInstance::new));

    private static final Codec<EntityCondition> CONDITION_CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.fieldOf("minYLevel").forGetter(EntityCondition::getMinYLevel),
            Codec.INT.fieldOf("maxYLevel").forGetter(EntityCondition::getMaxYLevel),
            Codec.DOUBLE.fieldOf("damageMultiplier").forGetter(EntityCondition::getHealthMultiplier),
            Codec.DOUBLE.fieldOf("healthMultiplier").forGetter(EntityCondition::getDamageMultiplier),
            MOB_EFFECT_INSTANCE_CODEC.optionalFieldOf("potionEffect").forGetter(EntityCondition::getPotionEffect)
    ).apply(instance, EntityCondition::new));

    public static final Codec<EntityModifier> ENTITY_MODIFIER_CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ENTITY_TYPE_CODEC.fieldOf("entity").forGetter(EntityModifier::getEntityType),
            CONDITION_CODEC.listOf().fieldOf("conditions").forGetter(EntityModifier::getConditions)
    ).apply(instance, EntityModifier::new));

    private final Map<EntityType<?>, EntityModifier> modifiers = new HashMap<>();

    public void addModifier(EntityType<?> entityType, EntityModifier modifier) {
        modifiers.put(entityType, modifier);
    }

    public EntityModifier getModifier(Entity entity) {
        return getModifier(entity.getType());
    }

    public EntityModifier getModifier(EntityType<?> entityType) {
        return modifiers.compute(entityType, (type, modifier) -> {
            if (modifier != null) return modifier;
            EntityCondition condition = new EntityCondition(
                    TuffDepthConfig.general.minYLevel.get(),
                    TuffDepthConfig.general.maxYLevel.get(),
                    TuffDepthConfig.general.damageMultiplier.get(),
                    TuffDepthConfig.general.healthMultiplier.get(),
                    Optional.empty()
            );

            return new EntityModifier(type, List.of(condition));
        });
    }

    public static MobManager getInstance() {
        return INSTANCE;
    }
}

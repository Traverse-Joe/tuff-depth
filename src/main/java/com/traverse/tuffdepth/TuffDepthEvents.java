package com.traverse.tuffdepth;

import com.traverse.tuffdepth.data.EntityCondition;
import com.traverse.tuffdepth.data.EntityModifier;
import com.traverse.tuffdepth.data.MobDataReloadListener;
import com.traverse.tuffdepth.data.MobManager;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;

public class TuffDepthEvents {

    public static void datapackRegistry(AddReloadListenerEvent evt) {
        evt.addListener(new MobDataReloadListener());
    }

    public static void onMobSpawned(EntityJoinWorldEvent evt) {
        if (evt.getWorld().dimension() == Level.OVERWORLD && !evt.getWorld().isClientSide()) {
            changeEntityStats(evt.getEntity());
        }
    }

    public static void onEntityAttacked(LivingAttackEvent evt) {
        if (evt.getEntityLiving() instanceof Player player && evt.getSource().getEntity() instanceof Monster monster) {
            EntityModifier modifier = MobManager.getInstance().getModifier(monster);
            for (EntityCondition condition : modifier.getConditions()) {
                if (condition.getPotionEffect().isEmpty()) continue;
                if (monster.getY() >= condition.getMinYLevel() && monster.getY() <= condition.getMaxYLevel()) {
                    MobEffectInstance effect = new MobEffectInstance(
                            condition.getPotionEffect().get().getEffect(),
                            condition.getPotionEffect().get().getDuration(),
                            condition.getPotionEffect().get().getAmplifier()
                    );
                    player.addEffect(effect);
                    break;
                }
            }
        }
    }

    private static void changeEntityStats(Entity entity) {
        if (entity instanceof Monster target) {
            EntityModifier modifier = MobManager.getInstance().getModifier(entity);
            for (EntityCondition condition : modifier.getConditions()) {
                if (target.getY() >= condition.getMinYLevel() && target.getY() <= condition.getMaxYLevel()) {
                    float targetHealth = (float) (target.getAttributeBaseValue(Attributes.MAX_HEALTH) * condition.getHealthMultiplier());
                    target.getAttribute(Attributes.MAX_HEALTH).setBaseValue(targetHealth);
                    target.setHealth(targetHealth);
                    float targetDamage = (float) (target.getAttributeBaseValue(Attributes.ATTACK_DAMAGE) * condition.getDamageMultiplier());
                    target.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(targetDamage);
                    break;
                }
            }
        }
    }
}



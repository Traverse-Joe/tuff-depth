package com.traverse.tuffdepth.mixin;

import com.traverse.tuffdepth.TuffDepth;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerLevel.class)
public class MixinServerLevel {

    @Inject(method = "addFreshEntity", at = @At(value = "HEAD"), cancellable = true)
    private void onMobSpawned(Entity entity, CallbackInfoReturnable<Boolean> cir) {
        Level level = entity.getLevel();
        if (entity.getY() < TuffDepth.CONFIG.maxYLevel &&
                entity.getY() > TuffDepth.CONFIG.minYLevel
                && level.dimension() == Level.OVERWORLD
                && !level.isClientSide()) {
            changeEntityStats(entity);
        }
    }

    private void changeEntityStats(Entity entity) {
        if (entity instanceof Monster target) {
            float targetHealth = (float) (target.getAttributeBaseValue(Attributes.MAX_HEALTH) * TuffDepth.CONFIG.healthMultiplier);
            target.getAttribute(Attributes.MAX_HEALTH).setBaseValue(targetHealth);
            target.setHealth(targetHealth);
        }

        if (entity instanceof Monster target) {
            float targetDamage = (float) (target.getAttributeBaseValue(Attributes.ATTACK_DAMAGE) * TuffDepth.CONFIG.damageMultiplier);
            target.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(targetDamage);
        }
    }

}

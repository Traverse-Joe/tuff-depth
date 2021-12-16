package com.traverse.tuffdepth;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("tuffdepth")
public class TuffDepth {

    public TuffDepth() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, TuffDepthConfig.configSpec);

        MinecraftForge.EVENT_BUS.addListener(this::onMobSpawned);
        MinecraftForge.EVENT_BUS.register(this);
    }

    public void onMobSpawned(EntityJoinWorldEvent evt) {
        if (evt.getEntity().getY() < 0 && evt.getWorld().dimension() == Level.OVERWORLD && !evt.getWorld().isClientSide()) {
            changeEntityStats(evt.getEntity());
        }
    }

    private void changeEntityStats(Entity entity) {
        if (entity instanceof Mob target) {
            float targetHealth = (float) (target.getAttributeBaseValue(Attributes.MAX_HEALTH) * TuffDepthConfig.general.healthMultiplier.get());
            target.getAttribute(Attributes.MAX_HEALTH).setBaseValue(targetHealth);
            target.setHealth(targetHealth);
        }
        if (entity instanceof Monster target) {
            float targetDamage = (float) (target.getAttributeBaseValue(Attributes.ATTACK_DAMAGE) * TuffDepthConfig.general.damageMultiplier.get());
            target.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(targetDamage);
        }
    }


    private void setup(final FMLCommonSetupEvent event) {

    }

    private void enqueueIMC(final InterModEnqueueEvent event) {

    }

    private void processIMC(final InterModProcessEvent event) {

    }

}

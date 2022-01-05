package com.traverse.tuffdepth;

import com.traverse.tuffdepth.config.ConfigHandler;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import net.fabricmc.api.ModInitializer;

public class TuffDepth implements ModInitializer {

    public static ConfigHandler CONFIG = new ConfigHandler();

    @Override
    public void onInitialize() {
        AutoConfig.register(ConfigHandler.class, JanksonConfigSerializer::new);
        CONFIG =AutoConfig.getConfigHolder(ConfigHandler.class).getConfig();
    }

}

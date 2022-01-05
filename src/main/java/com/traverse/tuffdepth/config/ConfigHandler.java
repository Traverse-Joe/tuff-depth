package com.traverse.tuffdepth.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

@Config(name = "tuffdepth")
public class ConfigHandler implements ConfigData {
    @Comment("Minimum Y-Level Tuff Depth Mobs can spawn")
    public int minYLevel = -64;

    @Comment("Maximum Y-Level Tuff Depth Mobs can spawn")
    public int maxYLevel = 0;

    @Comment("Health Multiplier for Tuff Depth Mobs")
    public double healthMultiplier = 1.5;

    @Comment("Damage Multiplier for Tuff Depth Mobs")
    public double damageMultiplier = 2.0;
}

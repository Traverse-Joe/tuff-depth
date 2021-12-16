package com.traverse.tuffdepth;

import net.minecraftforge.common.ForgeConfigSpec;

public class TuffDepthConfig {

    public static class General {
        public final ForgeConfigSpec.ConfigValue<Double> healthMultiplier;
        public final ForgeConfigSpec.ConfigValue<Double> damageMultiplier;

        General(ForgeConfigSpec.Builder builder) {
            builder.push("General");
            healthMultiplier = builder
                    .comment("Health Multiplier for Tuff Depth Spawned Mobs")
                    .define("healthMultiplier", 1.5);
            damageMultiplier = builder
                    .comment("Damage Multiplier for Tuff Depth Spawned Mobs")
                    .define("depthMultiplier", 2.0);
            builder.pop();
        }
    }

    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final General general = new General(BUILDER);
    public static final ForgeConfigSpec configSpec = BUILDER.build();
}

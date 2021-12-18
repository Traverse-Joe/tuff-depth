package com.traverse.tuffdepth;

import net.minecraftforge.common.ForgeConfigSpec;

public class TuffDepthConfig {

    public static class General {
        public final ForgeConfigSpec.ConfigValue<Integer> minYLevel;
        public final ForgeConfigSpec.ConfigValue<Integer> maxYLevel;
        public final ForgeConfigSpec.ConfigValue<Double> healthMultiplier;
        public final ForgeConfigSpec.ConfigValue<Double> damageMultiplier;

        General(ForgeConfigSpec.Builder builder) {
            builder.push("General");
            minYLevel = builder
                    .comment("The minimum Y Level the Tuff Spawn Mobs are Effected")
                    .define("minYLevel", -64);
            maxYLevel = builder
                    .comment("The maximum Y Level the Tuff Spawn Mobs are Effected")
                    .define("maxYLevel", 0);
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

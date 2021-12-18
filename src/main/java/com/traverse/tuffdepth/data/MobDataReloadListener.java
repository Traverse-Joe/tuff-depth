package com.traverse.tuffdepth.data;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.mojang.serialization.JsonOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;

public class MobDataReloadListener implements ResourceManagerReloadListener {

    @Override
    public void onResourceManagerReload(ResourceManager manager) {
        Collection<ResourceLocation> resourceIds = manager.listResources("modifiers", s -> s.endsWith(".json"));
        for (ResourceLocation resourceLocation : resourceIds) {
            if(!resourceLocation.getNamespace().equals("tuffdepth")) continue;
            try {
                Resource resource = manager.getResource(resourceLocation);
                try (InputStreamReader reader = new InputStreamReader(resource.getInputStream())) {
                    JsonElement element = JsonParser.parseReader(reader);
                    EntityModifier modifier = MobManager.ENTITY_MODIFIER_CODEC.parse(JsonOps.INSTANCE, element.getAsJsonObject()).getOrThrow(false, System.out::println);
                    MobManager.getInstance().addModifier(modifier.getEntityType(), modifier);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

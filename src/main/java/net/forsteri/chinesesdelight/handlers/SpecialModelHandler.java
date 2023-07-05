package net.forsteri.chinesesdelight.handlers;

import net.forsteri.chinesesdelight.contents.foods.fillable.AbstractFillableProcessingItem;
import net.forsteri.chinesesdelight.registries.ModFoodItems;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ForgeModelBakery;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SpecialModelHandler {

    public static Map<ResourceLocation, BakedModel> modelLocation = new HashMap<>();

    public static void onSpecialModelRegistry(ModelRegistryEvent ignoredEvent) {
        for(RegistryObject<Item> itemRegistryObject :ModFoodItems.ITEMS.getEntries())
            if(itemRegistryObject.get() instanceof AbstractFillableProcessingItem item)
                ForgeModelBakery.addSpecialModel(
                        new ModelResourceLocation(Objects.requireNonNull(item.getRegistryName()).getNamespace()+":" + item.getRegistryName().getPath() + "_base", "inventory")
                );
    }

    public static void onModelBakedEvent(ModelBakeEvent event){
        Map<ResourceLocation, BakedModel> modelRegistry = event.getModelRegistry();
        for(RegistryObject<Item> itemRegistryObject :ModFoodItems.ITEMS.getEntries())
            if(itemRegistryObject.get() instanceof AbstractFillableProcessingItem item){
                ModelResourceLocation modelResourceLocation = new ModelResourceLocation(Objects.requireNonNull(item.getRegistryName()).getNamespace()+":" + item.getRegistryName().getPath() + "_base", "inventory");

                modelLocation.put(
                        modelResourceLocation,
                        modelRegistry.get(modelResourceLocation)
                );
            }
    }
}

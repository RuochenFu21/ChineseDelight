package net.forsteri.chinesesdelight.registries;

import net.forsteri.chinesesdelight.ChinesesDelight;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;


public class ModFoodItems {
    public static final DeferredRegister<Item> ITEMS
            = DeferredRegister.create(ForgeRegistries.ITEMS, ChinesesDelight.MOD_ID);


    @SuppressWarnings("SameParameterValue")
    private static RegistryObject<Item> registerFood(String name, FoodProperties foodValues) {
        return ModFoodItems.ITEMS.register(name, () -> new Item(new Item.Properties().tab(ModItemGroup.MOD_ITEM_GROUP).food(foodValues)));
    }

    public static final RegistryObject<Item> SMALL_BOWL_OF_EGG_AND_SEAWEED_SOUP = registerFood("small_bowl_of_egg_and_seaweed_soup", ModFoodValues.BOWL_OF_EGG_AND_SEAWEED_SOUP);
}

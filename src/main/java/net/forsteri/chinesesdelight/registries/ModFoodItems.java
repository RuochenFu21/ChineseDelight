package net.forsteri.chinesesdelight.registries;

import net.forsteri.chinesesdelight.ChinesesDelight;
import net.forsteri.chinesesdelight.contents.abstracts.customizable.AbstractCustomizableProductItem;
import net.forsteri.chinesesdelight.contents.foods.customizable.dumplings.RawDumplingProduct;
import net.forsteri.chinesesdelight.contents.foods.customizable.dumplings.ProcessingDumplingItem;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.function.Function;


@SuppressWarnings("unused")
public class ModFoodItems {
    public static final DeferredRegister<Item> ITEMS
            = DeferredRegister.create(ForgeRegistries.ITEMS, ChinesesDelight.MOD_ID);


    @SuppressWarnings("SameParameterValue")
    private static RegistryObject<Item> registerFood(String name, @Nullable FoodProperties foodValues) {
        //noinspection DataFlowIssue
        return ModFoodItems.ITEMS.register(name, () -> new Item(new Item.Properties().tab(ModItemGroup.MOD_ITEM_GROUP).food(foodValues)));
    }

    private static <T extends Item> RegistryObject<T> registerCustom(String name, Function<Item.Properties, T> item) {
        return ModFoodItems.ITEMS.register(name,
                () -> item.apply(new Item.Properties().tab(ModItemGroup.MOD_ITEM_GROUP)));
    }

    public static final RegistryObject<Item> SMALL_BOWL_OF_EGG_AND_SEAWEED_SOUP = registerFood("small_bowl_of_egg_and_seaweed_soup", ModFoodValues.BOWL_OF_EGG_AND_SEAWEED_SOUP);
    public static final RegistryObject<Item> PROCESSING_DUMPLING = registerCustom("processing_dumpling", ProcessingDumplingItem::new);

    public static final RegistryObject<AbstractCustomizableProductItem> RAW_DUMPLING = registerCustom("raw_dumpling", RawDumplingProduct::new);
}

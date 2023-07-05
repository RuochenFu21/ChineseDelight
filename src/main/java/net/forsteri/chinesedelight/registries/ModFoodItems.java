package net.forsteri.chinesedelight.registries;

import net.forsteri.chinesedelight.ChineseDelight;
import net.forsteri.chinesedelight.contents.foods.fillable.AbstractFillableProductItem;
import net.forsteri.chinesedelight.contents.foods.fillable.dumplings.items.DumplingSoupItem;
import net.forsteri.chinesedelight.contents.foods.fillable.dumplings.items.ProcessingDumplingItem;
import net.forsteri.chinesedelight.contents.foods.fillable.dumplings.items.RawDumplingItem;
import net.forsteri.chinesedelight.contents.steamer.SteamerLidItem;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.checkerframework.checker.nullness.qual.Nullable;
import vectorwing.farmersdelight.common.FoodValues;

import java.util.function.Function;


@SuppressWarnings("unused")
public class ModFoodItems {
    public static final DeferredRegister<Item> ITEMS
            = DeferredRegister.create(ForgeRegistries.ITEMS, ChineseDelight.MOD_ID);


    @SuppressWarnings("SameParameterValue")
    private static RegistryObject<Item> registerFood(String name, @Nullable FoodProperties foodValues) {
        //noinspection DataFlowIssue
        return ModFoodItems.ITEMS.register(name, () -> new Item(new Item.Properties().tab(ModItemGroup.MOD_ITEM_GROUP).food(foodValues)));
    }

    @SuppressWarnings("SameParameterValue")
    private static <T extends Item> RegistryObject<T> registerCustomWithTab(String name, Function<Item.Properties, T> item, CreativeModeTab tab) {
        return ModFoodItems.ITEMS.register(name,
                () -> item.apply(new Item.Properties().tab(tab)));
    }

    private static <T extends Item> RegistryObject<T> registerCustom(String name, Function<Item.Properties, T> item) {
        return registerCustomWithTab(name, item, ModItemGroup.MOD_ITEM_GROUP);
    }

    public static final RegistryObject<Item> SMALL_BOWL_OF_EGG_AND_SEAWEED_SOUP = registerFood("small_bowl_of_egg_and_seaweed_soup", ModFoodValues.BOWL_OF_EGG_AND_SEAWEED_SOUP);
    public static final RegistryObject<Item> PROCESSING_WHITE_DUMPLING = registerCustom("processing_white_dumpling", ProcessingDumplingItem::new);
    public static final RegistryObject<AbstractFillableProductItem> RAW_WHITE_DUMPLING = registerCustom("raw_white_dumpling", RawDumplingItem::new);
    public static final RegistryObject<DumplingSoupItem> DUMPLING_SOUP = registerCustom("dumpling_soup", DumplingSoupItem::new);
    public static final RegistryObject<Item> FLAT_DOUGH = registerFood("flat_dough", FoodValues.WHEAT_DOUGH);
    public static final RegistryObject<Item> ROLLING_PIN = registerCustom("rolling_pin", Item::new);
    public static final RegistryObject<Item> CIRCULAR_CUTTER = registerCustom("circular_cutter", Item::new);
    public static final RegistryObject<Item> STEAMER_LID = registerCustom("steamer_lid", SteamerLidItem::new);
}

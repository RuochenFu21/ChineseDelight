package net.forsteri.chinesesdelight.handlers;

import net.forsteri.chinesesdelight.contents.abstracts.customizable.AbstractCustomizableProcessingItem;
import net.forsteri.chinesesdelight.registries.OtherRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import vectorwing.farmersdelight.common.registry.ModItems;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomRecipeHandler extends CustomRecipe implements CraftingRecipe
{


    public CustomRecipeHandler(ResourceLocation p_43833_) {
        super(p_43833_);
    }

    @Override
    public boolean matches(CraftingContainer p_44002_, @NotNull Level p_44003_) {
        int bases = 0;
        int fillings = 0;

        ItemStack baseItem = null;

        for (int i = 0; i < p_44002_.getContainerSize(); i++) {
            if (!p_44002_.getItem(i).isEmpty()){
                if (
                        !(p_44002_.getItem(i).getItem() instanceof AbstractCustomizableProcessingItem
                        || rawFillingList().contains(p_44002_.getItem(i).getItem())))
                    return false;
                else
                    if (p_44002_.getItem(i).getItem() instanceof AbstractCustomizableProcessingItem){
                        bases++;
                    baseItem = p_44002_.getItem(i);}
                    else
                        fillings++;
            }
        }
        return bases == 1 && baseItem.getOrCreateTag().getIntArray("fillings").length+fillings <= ((AbstractCustomizableProcessingItem) baseItem.getItem()).maxFillingSize();
    }

    @Override
    public @NotNull ItemStack assemble(CraftingContainer p_44001_) {
        List<ItemLike> addedFillings = new ArrayList<>();

        ItemStack baseItem = null;

        for (int i = 0; i < p_44001_.getContainerSize(); i++) {
            if (!p_44001_.getItem(i).isEmpty()){
                if (p_44001_.getItem(i).getItem() instanceof AbstractCustomizableProcessingItem){
                    baseItem = p_44001_.getItem(i);
                } else if (rawFillingList().contains(p_44001_.getItem(i).getItem())){
                    addedFillings.add(p_44001_.getItem(i).getItem());
                }
            }
        }


        ItemStack result;

        if(addedFillings.size() > 0){
            List<Integer> integerList = new ArrayList<>();

            assert baseItem != null;
            if(baseItem.getTag() != null && baseItem.getTag().contains("fillings"))
                for (Integer integer : baseItem.getTag().getIntArray("fillings"))
                    integerList.add(integer);

            for (ItemLike itemLike : addedFillings)
                integerList.add(rawFillingList().indexOf(itemLike));

            result = new ItemStack(baseItem.getItem());
            result.getOrCreateTag().putIntArray("fillings",
                    integerList);
        }else{
            assert baseItem != null;
            result = new ItemStack(((AbstractCustomizableProcessingItem) baseItem.copy().getItem()).getProductItem());
            result.getOrCreateTag().putIntArray("fillings",
                    baseItem.getOrCreateTag().getIntArray("fillings"));
        }

        return result;
    }

    @Override
    public boolean canCraftInDimensions(int p_43999_, int p_44000_) {
        return p_43999_ * p_44000_ > 1;
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return OtherRegistries.CUSTOMIZED_COOKING.get();
    }

    public static Map<ItemLike, ItemLike> fillingMaps() {
        HashMap<ItemLike, ItemLike> ret = new HashMap<>();
        ret.put(ModItems.CHICKEN_CUTS.get(), ModItems.COOKED_CHICKEN_CUTS.get());
        ret.put(ModItems.BACON.get(), ModItems.COOKED_BACON.get());
        ret.put(ModItems.MINCED_BEEF.get(), ModItems.BEEF_PATTY.get());
        ret.put(ModItems.SALMON_SLICE.get(), ModItems.COOKED_SALMON_SLICE.get());
        ret.put(ModItems.COD_SLICE.get(), ModItems.COOKED_COD_SLICE.get());
        ret.put(Items.CARROT, Items.CARROT);
        ret.put(Items.POTATO, Items.BAKED_POTATO);
        ret.put(Items.EGG, ModItems.FRIED_EGG.get());
        ret.put(Items.CHICKEN, Items.COOKED_CHICKEN);
        ret.put(Items.BEEF, Items.COOKED_BEEF);
        ret.put(Items.PORKCHOP, Items.COOKED_PORKCHOP);
        ret.put(Items.MUTTON, Items.COOKED_MUTTON);
        ret.put(Items.RABBIT, Items.COOKED_RABBIT);
        ret.put(Items.SALMON, Items.COOKED_SALMON);
        ret.put(Items.COD, Items.COOKED_COD);
        ret.put(Items.APPLE, Items.APPLE);
        ret.put(Items.GOLDEN_APPLE, Items.GOLDEN_APPLE);
        ret.put(Items.ENCHANTED_GOLDEN_APPLE, Items.ENCHANTED_GOLDEN_APPLE);

        return ret;
    }

    public static List<ItemLike> rawFillingList() {
        return new ArrayList<>(fillingMaps().keySet());
    }
}

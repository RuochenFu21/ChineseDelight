package net.forsteri.chinesesdelight.contents.foods.customizable;

import net.forsteri.chinesesdelight.contents.abstracts.customizable.AbstractCustomizableBaseItem;
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
import java.util.List;

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
                        !(p_44002_.getItem(i).getItem() instanceof AbstractCustomizableBaseItem
                        ||supportedFillings().contains(p_44002_.getItem(i).getItem())))
                    return false;
                else
                    if (p_44002_.getItem(i).getItem() instanceof AbstractCustomizableBaseItem){
                        bases++;
                    baseItem = p_44002_.getItem(i);}
                    else
                        fillings++;
            }
        }
        return bases == 1 && baseItem.getOrCreateTag().getIntArray("fillings").length+fillings <= ((AbstractCustomizableBaseItem) baseItem.getItem()).maxFillingSize();
    }

    @Override
    public @NotNull ItemStack assemble(CraftingContainer p_44001_) {
        List<ItemLike> addedFillings = new ArrayList<>();

        ItemStack baseItem = null;

        for (int i = 0; i < p_44001_.getContainerSize(); i++) {
            if (!p_44001_.getItem(i).isEmpty()){
                if (p_44001_.getItem(i).getItem() instanceof AbstractCustomizableBaseItem){
                    baseItem = p_44001_.getItem(i);
                } else if (supportedFillings().contains(p_44001_.getItem(i).getItem())){
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
                integerList.add(supportedFillings().indexOf(itemLike));

            result = new ItemStack(baseItem.getItem());
            result.getOrCreateTag().putIntArray("fillings",
                    integerList);
        }else{
            assert baseItem != null;
            result = new ItemStack(((AbstractCustomizableBaseItem) baseItem.copy().getItem()).getProductItem());
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

    public static List<ItemLike> supportedFillings() {
        return new ArrayList<>(List.of(
                ModItems.CHICKEN_CUTS.get(),
                ModItems.BACON.get(),
                ModItems.BEEF_PATTY.get(),
                ModItems.SALMON_SLICE.get(),
                ModItems.COD_SLICE.get(),
                Items.CARROT,
                Items.POTATO,
                Items.EGG
        )); //TODO: Reference of cooked fillings
    }
}

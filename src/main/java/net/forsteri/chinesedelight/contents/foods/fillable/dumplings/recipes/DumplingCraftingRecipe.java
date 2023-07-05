package net.forsteri.chinesedelight.contents.foods.fillable.dumplings.recipes;

import net.forsteri.chinesedelight.contents.foods.fillable.AbstractFillable;
import net.forsteri.chinesedelight.contents.foods.fillable.AbstractFillableProcessingItem;
import net.forsteri.chinesedelight.contents.foods.fillable.FillingHandler;
import net.forsteri.chinesedelight.contents.foods.fillable.dumplings.stuffing.DumplingStuffingMap;
import net.forsteri.chinesedelight.registries.OtherRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class DumplingCraftingRecipe extends CustomRecipe implements CraftingRecipe
{
    public DumplingCraftingRecipe(ResourceLocation p_43833_) {
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
                        !(p_44002_.getItem(i).getItem() instanceof AbstractFillableProcessingItem
                        || DumplingStuffingMap.rawToCookedMap.containsKey(p_44002_.getItem(i).getItem())))
                    return false;
                else
                    if (p_44002_.getItem(i).getItem() instanceof AbstractFillableProcessingItem){
                        bases++;
                        baseItem = p_44002_.getItem(i);
                    } else
                        fillings++;
            }
        }
        return bases == 1
                && (AbstractFillable.hasFillings(baseItem) || fillings > 0)
                && new FillingHandler(baseItem.getOrCreateTag().getCompound("fillings")).getAllStuffings().size() + fillings <= ((AbstractFillableProcessingItem) baseItem.getItem()).maxFillingSize();
    }

    @Override
    public @NotNull ItemStack assemble(CraftingContainer p_44001_) {
        List<ItemLike> addedFillings = new ArrayList<>();

        ItemStack baseItem = null;

        for (int i = 0; i < p_44001_.getContainerSize(); i++) {
            if (!p_44001_.getItem(i).isEmpty()){
                if (p_44001_.getItem(i).getItem() instanceof AbstractFillableProcessingItem){
                    baseItem = p_44001_.getItem(i);
                } else if (DumplingStuffingMap.rawToCookedMap.containsKey(p_44001_.getItem(i).getItem())){
                    addedFillings.add(p_44001_.getItem(i).getItem());
                }
            }
        }

        assert baseItem != null;


        ItemStack result;

        if(addedFillings.size() > 0){
            result = new ItemStack(baseItem.getItem());
            result.getOrCreateTag().put("fillings", new CompoundTag());
            (new FillingHandler(result.getOrCreateTag().getCompound("fillings"))).addStuffings(addedFillings.stream().map(ItemLike::asItem).toList());

            (new FillingHandler(result.getOrCreateTag().getCompound("fillings"))).addStuffings(
                    new FillingHandler(baseItem.getOrCreateTag().getCompound("fillings")).getAllStuffings()
            );
        }else{
            result = new ItemStack(((AbstractFillableProcessingItem) baseItem.copy().getItem()).getProductItem());

            FillingHandler stuffingHandler = new FillingHandler(result.getOrCreateTag().getCompound("fillings"));

            new FillingHandler(baseItem.getOrCreateTag().getCompound("fillings")).getAllStuffings().forEach(
                    stuffingHandler::addStuffing);

            result.getOrCreateTag().put("fillings", stuffingHandler.nbt);
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
}

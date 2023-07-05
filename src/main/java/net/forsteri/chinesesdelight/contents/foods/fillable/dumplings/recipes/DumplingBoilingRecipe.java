package net.forsteri.chinesesdelight.contents.foods.fillable.dumplings.recipes;

import net.forsteri.chinesesdelight.contents.foods.fillable.dumplings.stuffing.DumplingStuffingMap;
import net.forsteri.chinesesdelight.contents.foods.fillable.dumplings.items.RawDumplingItem;
import net.forsteri.chinesesdelight.contents.foods.fillable.FillingHandler;
import net.forsteri.chinesesdelight.registries.ModFoodItems;
import net.forsteri.chinesesdelight.registries.OtherRegistries;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.wrapper.RecipeWrapper;
import org.jetbrains.annotations.NotNull;
import vectorwing.farmersdelight.client.recipebook.CookingPotRecipeBookTab;
import vectorwing.farmersdelight.common.crafting.CookingPotRecipe;
import vectorwing.farmersdelight.common.registry.ModRecipeTypes;

import java.util.ArrayList;
import java.util.List;

public class DumplingBoilingRecipe extends CookingPotRecipe {
    private final ResourceLocation id;

    public DumplingBoilingRecipe(ResourceLocation pId) {
        super(pId, "", CookingPotRecipeBookTab.findByName("meals"), NonNullList.of(Ingredient.of(ModFoodItems.RAW_WHITE_DUMPLING.get())), ModFoodItems.DUMPLING_SOUP.get().getDefaultInstance(), Items.BOWL.getDefaultInstance(), 0, 200);
        this.id = pId;
    }

    public @NotNull ResourceLocation getId() {
        return this.id;
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return OtherRegistries.DUMPLING_BOILING.get();
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return ModRecipeTypes.COOKING.get();
    }

    /**
     * If true, this recipe does not appear in the recipe book and does not respect recipe unlocking (and the
     * doLimitedCrafting gamerule)
     */
    public boolean isSpecial() {
        return true;
    }

    @Override
    public boolean matches(RecipeWrapper pContainer, @NotNull Level pLevel) {

        List<ItemStack> includedItems = new ArrayList<>();
        for(int i=0; i<pContainer.getContainerSize(); i++){
            includedItems.add(pContainer.getItem(i));
        }

        List<ItemStack> dumplings = includedItems.subList(0, 7);

        dumplings.removeIf(ItemStack::isEmpty);

        if(dumplings.stream().filter(itemStack -> itemStack.getItem() instanceof RawDumplingItem).count() < 2)
            return false;

        if(dumplings.stream().anyMatch(itemStack ->
                !(itemStack.getItem() instanceof RawDumplingItem)))
            return false;

        return (pContainer.getItem(7).getItem() == Items.BOWL || pContainer.getItem(7).getItem() == Items.AIR)
                && (pContainer.getItem(8).getItem() == ModFoodItems.DUMPLING_SOUP.get()
                || pContainer.getItem(8).getItem() == Items.AIR);
    }

    @Override
    public @NotNull ItemStack assemble(RecipeWrapper pContainer) {
        ItemStack ret = ModFoodItems.DUMPLING_SOUP.get().getDefaultInstance();

        ret.getOrCreateTag().put("dumplings", new CompoundTag());

        for(int i=0; i<pContainer.getContainerSize(); i++){
            if (pContainer.getItem(i).getItem() instanceof RawDumplingItem){

                ret.getOrCreateTag().getCompound("dumplings").put("dumpling" + i, new CompoundTag());

                FillingHandler handle = new FillingHandler(ret.getOrCreateTag().getCompound("dumplings").getCompound("dumpling" + i));

                new FillingHandler(pContainer.getItem(i).getOrCreateTag().getCompound("fillings")).getAllStuffings().forEach(
                        stuffing -> handle.addStuffing(DumplingStuffingMap.rawToCookedMap.get(stuffing))
                );
            }
        }

        return ret;
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return pHeight * pWidth >= 1;
    }

    /**
     * Get the result of this recipe, usually for display purposes (e.g. recipe book). If your recipe has more than one
     * possible result (e.g. it's dynamic and depends on its inputs), then return an empty stack.
     */
    @Override
    public @NotNull ItemStack getResultItem() {
        return new ItemStack(
                ModFoodItems.DUMPLING_SOUP.get()
        ).copy();
    }
}

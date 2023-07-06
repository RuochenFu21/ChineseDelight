package net.forsteri.chinesedelight.compact.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.ModIds;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.registration.IRecipeRegistration;
import net.forsteri.chinesedelight.ChineseDelight;
import net.forsteri.chinesedelight.contents.foods.fillable.dumplings.stuffing.DumplingStuffingMap;
import net.forsteri.chinesedelight.registries.ModFoodItems;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.ShapelessRecipe;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;
import vectorwing.farmersdelight.client.recipebook.CookingPotRecipeBookTab;
import vectorwing.farmersdelight.common.crafting.CookingPotRecipe;
import vectorwing.farmersdelight.common.crafting.CuttingBoardRecipe;
import vectorwing.farmersdelight.common.crafting.ingredient.ChanceResult;
import vectorwing.farmersdelight.common.registry.ModBlocks;
import vectorwing.farmersdelight.common.registry.ModItems;
import vectorwing.farmersdelight.integration.jei.category.CookingRecipeCategory;
import vectorwing.farmersdelight.integration.jei.category.CuttingRecipeCategory;

import java.util.ArrayList;
import java.util.List;

@JeiPlugin
public class ChineseDelightJEIPlugin implements IModPlugin {
    @Override
    public @NotNull ResourceLocation getPluginUid() {
        return new ResourceLocation(ChineseDelight.MOD_ID, "jei_plugin");
    }

    @SuppressWarnings("removal")
    @Override
    public void registerRecipes(@NotNull IRecipeRegistration registration) {
        IModPlugin.super.registerRecipes(registration);

        registration.addRecipes(
                List.of(
                        new CuttingBoardRecipe(
                                new ResourceLocation(ChineseDelight.MOD_ID, "flattening_dough"),
                                "",
                                Ingredient.of(ModItems.WHEAT_DOUGH.get()),
                                Ingredient.of(ModFoodItems.ROLLING_PIN.get()),
                                NonNullList.of(new ChanceResult(ItemStack.EMPTY, 1.0f), new ChanceResult(ModFoodItems.FLAT_DOUGH.get().getDefaultInstance(), 1.0f)),
                                ""
                        ),
                        new CuttingBoardRecipe(
                                new ResourceLocation(ChineseDelight.MOD_ID, "cut_dough_into_circle"),
                                "",
                                Ingredient.of(ModFoodItems.FLAT_DOUGH.get()),
                                Ingredient.of(ModFoodItems.CIRCULAR_CUTTER.get()),
                                NonNullList.of(
                                        new ChanceResult(ItemStack.EMPTY, 1.0f),
                                        new ChanceResult(ModFoodItems.PROCESSING_WHITE_DUMPLING.get().getDefaultInstance(), 1.0f)
                                ),
                                ""
                        ),
                        new CuttingBoardRecipe(
                                new ResourceLocation(ChineseDelight.MOD_ID, "assemble_dumpling_board"),
                                "",
                                Ingredient.of(ModFoodItems.PROCESSING_WHITE_DUMPLING.get()),
                                Ingredient.of(ItemStack.EMPTY),
                                NonNullList.of(
                                        new ChanceResult(ItemStack.EMPTY, 1.0f),
                                        new ChanceResult(ModFoodItems.RAW_WHITE_DUMPLING.get().getDefaultInstance(), 1.0f)
                                ),
                                ""
                        )
                ), CuttingRecipeCategory.UID
        );

        registration.addRecipes(
                List.of(
                        new ShapelessRecipe(
                                new ResourceLocation(ChineseDelight.MOD_ID, "assemble_dumpling_crafting"),
                                "",
                                ModFoodItems.RAW_WHITE_DUMPLING.get().getDefaultInstance(),
                                NonNullList.of(
                                        Ingredient.of(ItemStack.EMPTY),
                                        Ingredient.of(ModFoodItems.PROCESSING_WHITE_DUMPLING.get())
                                )
                        )
                ),
                new ResourceLocation(ModIds.MINECRAFT_ID, "crafting")
        );

        List<CookingPotRecipe> potRecipes = new ArrayList<>();

        for (int i = 3; i <= 6; i++) {
            potRecipes.add(
                    new CookingPotRecipe(
                            new ResourceLocation(ChineseDelight.MOD_ID, i + "_dumplings_soup"),
                            "",
                            CookingPotRecipeBookTab.MEALS,
                            NonNullList.withSize(i, Ingredient.of(ModFoodItems.RAW_WHITE_DUMPLING.get())),
                            ModFoodItems.DUMPLING_SOUP.get().getDefaultInstance(),
                            ItemStack.EMPTY,
                            0,
                            200
                    )
            );
        }

        registration.addRecipes(
                potRecipes, CookingRecipeCategory.UID
        );

        registration.addIngredientInfo(
                new ItemStack(ModFoodItems.PROCESSING_WHITE_DUMPLING.get()), VanillaTypes.ITEM,
                new TranslatableComponent(
                        "jei.tooltip.processing_white_dumpling",
                        I18n.get(Blocks.CRAFTING_TABLE.getDescriptionId()),
                        I18n.get(ModBlocks.CUTTING_BOARD.get().getDescriptionId()) // Add fillings to the dumpling with a crafting table or a Cutting board, same with assembling
                )
        );

        registration.addIngredientInfo(
                DumplingStuffingMap.rawToCookedMap.keySet().stream().map(Item::getDefaultInstance).toList(), VanillaTypes.ITEM,
                new TranslatableComponent(
                        "jei.tooltip.dumpling_filling"
                )
        );

    }
}

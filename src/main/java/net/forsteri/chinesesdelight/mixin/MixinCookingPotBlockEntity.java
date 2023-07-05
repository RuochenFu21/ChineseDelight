package net.forsteri.chinesesdelight.mixin;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.RecipeWrapper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vectorwing.farmersdelight.common.block.entity.CookingPotBlockEntity;
import vectorwing.farmersdelight.common.crafting.CookingPotRecipe;

@Mixin(value = CookingPotBlockEntity.class, remap = false)
public class MixinCookingPotBlockEntity {
    @Shadow @Final private ItemStackHandler inventory;
    private CookingPotRecipe recipe;

    @ModifyVariable(method = "processCooking", at = @At("STORE"), ordinal = 0)
    private ItemStack modifyCookingResult(ItemStack result) {
        return recipe.assemble(new RecipeWrapper(inventory));
    }

    @Inject(method = "processCooking", at = @At("HEAD"))
    private void getRecipe(CookingPotRecipe recipe, CookingPotBlockEntity cookingPot, CallbackInfoReturnable<Boolean> cir) {
        this.recipe = recipe;
    }
}

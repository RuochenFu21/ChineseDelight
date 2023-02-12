package net.forsteri.chinesesdelight.registries;

import net.forsteri.chinesesdelight.ChinesesDelight;
import net.forsteri.chinesesdelight.contents.foods.customizable.CustomRecipeHandler;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleRecipeSerializer;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@SuppressWarnings("SameParameterValue")
public class OtherRegistries {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZER =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, ChinesesDelight.MOD_ID);
    public static RegistryObject<SimpleRecipeSerializer<CustomRecipeHandler>> CUSTOMIZED_COOKING = register("customize_cooking", new SimpleRecipeSerializer<>(CustomRecipeHandler::new));

    static <S extends RecipeSerializer<?>> RegistryObject<S> register(String p_44099_, S p_44100_) {
        return SERIALIZER.register(p_44099_, () -> p_44100_);
    }

}

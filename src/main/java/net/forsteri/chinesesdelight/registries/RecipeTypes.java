package net.forsteri.chinesesdelight.registries;

import net.forsteri.chinesesdelight.ChinesesDelight;
import net.forsteri.chinesesdelight.contents.foods.customizable.dumplings.recipes.DumplingBoilingRecipe;
import net.forsteri.chinesesdelight.contents.foods.customizable.dumplings.recipes.DumplingCraftingRecipe;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;
import vectorwing.farmersdelight.common.registry.ModRecipeTypes;

import java.util.Locale;
import java.util.function.Supplier;

@SuppressWarnings("unused")
public enum RecipeTypes {
    CUSTOMIZED(() -> new SimpleRecipeSerializer<>(DumplingCraftingRecipe::new), () -> RecipeType.CRAFTING, false),

    DUMPLING(() -> new SimpleRecipeSerializer<>(DumplingBoilingRecipe::new), ModRecipeTypes.COOKING::get, false);


    private final ResourceLocation id;
    private final RegistryObject<RecipeSerializer<?>> serializerObject;
    @Nullable
    private final RegistryObject<RecipeType<?>> typeObject;
    private final Supplier<RecipeType<?>> type;

    RecipeTypes(Supplier<RecipeSerializer<?>> serializerSupplier, Supplier<RecipeType<?>> typeSupplier, boolean registerType) {
        String name = name().toLowerCase(Locale.ROOT);
        id = new ResourceLocation(ChinesesDelight.MOD_ID, name);
        serializerObject = RecipeTypes.Registers.SERIALIZER_REGISTER.register(name, serializerSupplier);
        if (registerType) {
            typeObject = RecipeTypes.Registers.TYPE_REGISTER.register(name, typeSupplier);
            type = typeObject;
        } else {
            typeObject = null;
            type = typeSupplier;
        }
    }

    RecipeTypes(Supplier<RecipeSerializer<?>> serializerSupplier) {
        String name = name().toLowerCase(Locale.ROOT);
        id = new ResourceLocation(ChinesesDelight.MOD_ID, name);
        serializerObject = RecipeTypes.Registers.SERIALIZER_REGISTER.register(name, serializerSupplier);
        typeObject = RecipeTypes.Registers.TYPE_REGISTER.register(name, () -> simpleType(id));
        type = typeObject;
    }

    public ResourceLocation getId() {
        return id;
    }

    @SuppressWarnings("unchecked")
    public <T extends RecipeSerializer<?>> T getSerializer() {
        return (T) serializerObject.get();
    }

    @SuppressWarnings("unchecked")
    public <T extends RecipeType<?>> T getType() {
        return (T) type.get();
    }

    private static class Registers {
        private static final DeferredRegister<RecipeSerializer<?>> SERIALIZER_REGISTER = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, ChinesesDelight.MOD_ID);
        private static final DeferredRegister<RecipeType<?>> TYPE_REGISTER = DeferredRegister.create(Registry.RECIPE_TYPE_REGISTRY, ChinesesDelight.MOD_ID);
    }

    public static <T extends Recipe<?>> RecipeType<T> simpleType(ResourceLocation id) {
        String stringId = id.toString();
        return new RecipeType<>() {
            @Override
            public String toString() {
                return stringId;
            }
        };
    }

    public static void register(IEventBus modEventBus) {
        ShapedRecipe.setCraftingSize(9, 9);
        RecipeTypes.Registers.SERIALIZER_REGISTER.register(modEventBus);
        RecipeTypes.Registers.TYPE_REGISTER.register(modEventBus);
    }
}

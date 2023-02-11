package net.forsteri.chinesesdelight.registries;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.food.FoodProperties;
import vectorwing.farmersdelight.common.registry.ModEffects;

@SuppressWarnings("unused")
public class ModFoodValues {
    public static final int BRIEF_DURATION = 600;
    public static final int SHORT_DURATION = 1200;
    public static final int MEDIUM_DURATION = 3600;
    public static final int LONG_DURATION = 6000;

    public static final FoodProperties BOWL_OF_EGG_AND_SEAWEED_SOUP = (new FoodProperties.Builder()).nutrition(3).saturationMod(0.75F).effect(() -> new MobEffectInstance(ModEffects.COMFORT.get(), 1500, 0), 1.0F).build();
}

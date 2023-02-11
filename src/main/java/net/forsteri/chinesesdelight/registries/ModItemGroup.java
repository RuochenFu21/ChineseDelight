package net.forsteri.chinesesdelight.registries;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ModItemGroup {
    public static final CreativeModeTab MOD_ITEM_GROUP = new CreativeModeTab("chinesesdelight") {
        @Override
        public @NotNull ItemStack makeIcon() {
            return new ItemStack(ModFoodBlocks.EGG_AND_SEAWEED_SOUP.get());
        }
    };
}

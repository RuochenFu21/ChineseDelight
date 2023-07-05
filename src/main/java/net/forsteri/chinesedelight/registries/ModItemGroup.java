package net.forsteri.chinesedelight.registries;

import net.forsteri.chinesedelight.ChineseDelight;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ModItemGroup {
    public static final CreativeModeTab MOD_ITEM_GROUP = new CreativeModeTab(ChineseDelight.MOD_ID) {
        @Override
        public @NotNull ItemStack makeIcon() {
            return new ItemStack(ModFoodBlocks.EGG_AND_SEAWEED_SOUP.get());
        }
    };
}

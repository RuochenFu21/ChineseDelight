package net.forsteri.chinesesdelight.registries;

import net.forsteri.chinesesdelight.ChinesesDelight;
import net.forsteri.chinesesdelight.contents.EmptyBowlBlock;
import net.forsteri.chinesesdelight.contents.foods.EggAndSeaweedSoup;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Function;

@SuppressWarnings("SameParameterValue")
public class ModFoodBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, ChinesesDelight.MOD_ID);
    public static final RegistryObject<Block> EGG_AND_SEAWEED_SOUP = registerBlockWithItem("egg_and_seaweed_soup", EggAndSeaweedSoup::new);
    public static final RegistryObject<Block> BOWL_BLOCK = registerBlock("bowl", EmptyBowlBlock::new);

    private static <T extends Block> RegistryObject<T> registerBlockWithItem(String name, Function<BlockBehaviour.Properties, T> block) {
        RegistryObject<T> toReturn = registerBlock(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Function<BlockBehaviour.Properties, T> block) {
        return BLOCKS.register(name, () -> block.apply(BlockBehaviour.Properties.copy(Blocks.CAKE)));
    }

    @SuppressWarnings("UnusedReturnValue")
    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {
        return ModFoodItems.ITEMS.register(name, () -> new BlockItem(block.get(),
                new Item.Properties().tab(ModItemGroup.MOD_ITEM_GROUP)));
    }
}

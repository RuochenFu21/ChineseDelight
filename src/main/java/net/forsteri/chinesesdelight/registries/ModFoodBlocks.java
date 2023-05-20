package net.forsteri.chinesesdelight.registries;

import net.forsteri.chinesesdelight.ChinesesDelight;
import net.forsteri.chinesesdelight.contents.EmptyBowlBlock;
import net.forsteri.chinesesdelight.contents.foods.EggAndSeaweedSoup;
import net.forsteri.chinesesdelight.contents.steamer.SteamerBlock;
import net.forsteri.chinesesdelight.contents.steamer.SteamerBlockEntity;
import net.forsteri.chinesesdelight.contents.steamer.SteamerItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Function;

@SuppressWarnings("SameParameterValue")
public class ModFoodBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, ChinesesDelight.MOD_ID);

    public static final DeferredRegister<BlockEntityType<?>> TILE_ENTITY =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, ChinesesDelight.MOD_ID);

    public static final RegistryObject<Block> STEAMER = registerBlockWithItem("steamer", SteamerBlock::new, SteamerItem::new);

    @SuppressWarnings("DataFlowIssue")
    public static final RegistryObject<BlockEntityType<SteamerBlockEntity>> STEAMER_BLOCK_ENTITY =
            TILE_ENTITY.register("steamer", () ->
                    BlockEntityType.Builder.of(SteamerBlockEntity::new, ModFoodBlocks.STEAMER.get()).build(null));
    public static final RegistryObject<Block> EGG_AND_SEAWEED_SOUP = registerBlockWithItem("egg_and_seaweed_soup", EggAndSeaweedSoup::new);
    public static final RegistryObject<Block> BOWL_BLOCK = registerBlock("bowl", EmptyBowlBlock::new);

    private static <T extends Block> RegistryObject<T> registerBlockWithItem(String name, Function<BlockBehaviour.Properties, T> block) {
        return registerBlockWithItem(name, block, BlockItem::new);
    }

    private static <T extends Block> RegistryObject<T> registerBlockWithItem(String name, Function<BlockBehaviour.Properties, T> block, ItemConstructor item) {
        RegistryObject<T> toReturn = registerBlock(name, block);
        registerBlockItem(name, toReturn, item);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Function<BlockBehaviour.Properties, T> block) {
        return BLOCKS.register(name, () -> block.apply(BlockBehaviour.Properties.copy(Blocks.CAKE).noCollission()));
    }

    @SuppressWarnings("UnusedReturnValue")
    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block, ItemConstructor item) {
        return ModFoodItems.ITEMS.register(name, () -> item.get(block.get(),
                new Item.Properties().tab(ModItemGroup.MOD_ITEM_GROUP)));
    }

    @FunctionalInterface
    private interface ItemConstructor {
        Item get(Block pBlock, Item.Properties pProperties);
    }
}

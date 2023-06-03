package net.forsteri.chinesesdelight.contents.steamer;

import net.forsteri.chinesesdelight.registries.ModFoodBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import vectorwing.farmersdelight.common.block.CookingPotBlock;

import java.util.Objects;

import static net.forsteri.chinesesdelight.contents.steamer.SteamerBlock.CONNECTED;
import static net.forsteri.chinesesdelight.contents.steamer.SteamerBlock.HAVE_LID;

public class SteamerItem extends BlockItem {
    public SteamerItem(Block pBlock, Properties pProperties) {
        super(pBlock, pProperties);
    }

    @Override
    public @NotNull InteractionResult place(BlockPlaceContext pContext) {
        if (!pContext.canPlace())
            return InteractionResult.FAIL;

        BlockPos pos = pContext.getClickedPos().relative(pContext.getClickedFace().getOpposite());
        Level level = pContext.getLevel();

        if (level.getBlockState(pos).getBlock() instanceof SteamerBlock) {
            if (level.getBlockState(pos).getValue(SteamerBlock.SIZE) < 4) {
                level.setBlock(pos, level.getBlockState(pos).setValue(SteamerBlock.SIZE, level.getBlockState(pos).getValue(SteamerBlock.SIZE) + 1), 11);
                pContext.getItemInHand().shrink(1);
                return InteractionResult.SUCCESS;
            }

            int above = 1;

            while (true) {
                if (level.getBlockState(pos.above(above)).getBlock() instanceof SteamerBlock) {
                    if (level.getBlockState(pos.above(above)).getValue(SteamerBlock.SIZE) < 4) {
                        level.setBlock(pos.above(above), level.getBlockState(pos.above(above)).setValue(SteamerBlock.SIZE, level.getBlockState(pos.above(above)).getValue(SteamerBlock.SIZE) + 1), 11);
                        pContext.getItemInHand().shrink(1);
                        return InteractionResult.SUCCESS;
                    }

                    above++;
                } else
                    break;
            }

            if (level.getBlockState(pos.above(above-1)).getValue(SteamerBlock.HAVE_LID)) {
                var ret = new InnerBlockPlaceContext(pContext) {
                    public int _above = 0;

                    @Override
                    public @NotNull BlockPos getClickedPos() {
                        return pos.above(_above);
                    }


                };

                ret._above = above;

                if (ret.canPlace()) {
                    level.setBlock(pos.above(above-1), level.getBlockState(pos.above(above-1)).setValue(SteamerBlock.HAVE_LID, false), 11);

                    return super.place(ret);
                }
            } else {
                var ret = new BlockPlaceContext(pContext) {
                    public int _above = 0;

                    @Override
                    public @NotNull BlockPos getClickedPos() {
                        return pos.above(_above);
                    }


                };

                ret._above = above;

                return super.place(ret);
            }

        }

        if (level.getBlockState(pos).getBlock() instanceof CookingPotBlock) {
            if (level.getBlockState(pos.above()).isAir()) {
                level.setBlock(pos.above(), ModFoodBlocks.STEAMER.get().defaultBlockState().setValue(CONNECTED, true), 11);
                pContext.getItemInHand().shrink(1);
                return InteractionResult.SUCCESS;
            }

            int above = 1;

            while (true) {

                if (level.getBlockState(pos.above(above)).isAir()) {
                    level.setBlock(pos.above(above), ModFoodBlocks.STEAMER.get().defaultBlockState().setValue(HAVE_LID, level.getBlockState(pos.above(above-1)).getValue(HAVE_LID)), 11);
                    pContext.getItemInHand().shrink(1);
                    return InteractionResult.SUCCESS;
                }

                if (level.getBlockState(pos.above(above)).getBlock() instanceof SteamerBlock) {
                    if (level.getBlockState(pos.above(above)).getValue(SteamerBlock.SIZE) < 4) {
                        level.setBlock(pos.above(above), level.getBlockState(pos.above(above)).setValue(SteamerBlock.SIZE, level.getBlockState(pos.above(above)).getValue(SteamerBlock.SIZE) + 1), 11);
                        pContext.getItemInHand().shrink(1);
                        return InteractionResult.SUCCESS;
                    } else {
                        above++;
                    }
                } else
                    break;
            }
        }


        return super.place(pContext);
    }

    @Nullable
    @Override
    protected BlockState getPlacementState(@NotNull BlockPlaceContext pContext) {
        if (pContext instanceof InnerBlockPlaceContext)
            return Objects.requireNonNull(super.getPlacementState(pContext)).setValue(SteamerBlock.HAVE_LID, true);
        return super.getPlacementState(pContext);
    }

    private static class InnerBlockPlaceContext extends BlockPlaceContext {

        public InnerBlockPlaceContext(UseOnContext pContext) {
            super(pContext);
        }

    }
}

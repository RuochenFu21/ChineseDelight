package net.forsteri.chinesesdelight.contents.steamer;

import net.forsteri.chinesesdelight.registries.ModFoodBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import vectorwing.farmersdelight.common.block.CookingPotBlock;

import static net.forsteri.chinesesdelight.contents.steamer.SteamerBlock.CONNECTED;
import static net.forsteri.chinesesdelight.contents.steamer.SteamerBlock.HAVE_LID;

public class SteamerLidItem extends Item {
    public SteamerLidItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext pContext) {
        Level level = pContext.getLevel();
        BlockPos pos = pContext.getClickedPos();

        if (level.getBlockState(pos).getBlock() instanceof SteamerBlock
        && !level.getBlockState(pos).getValue(SteamerBlock.HAVE_LID)) {
            int above = 0;
            while (true) {
                if (level.getBlockState(pos.above(above)).getBlock() instanceof SteamerBlock) {
                    if (level.getBlockState(pos.above(above)).getValue(SteamerBlock.SIZE) < 4) {
                        if (level.getBlockState(pos.above(above)).getValue(SteamerBlock.HAVE_LID))
                            return InteractionResult.FAIL;
                        level.setBlock(pos.above(above), level.getBlockState(pos.above(above)).setValue(SteamerBlock.SIZE, level.getBlockState(pos.above(above)).getValue(SteamerBlock.SIZE) + 1), 11);
                        level.setBlock(pos.above(above), level.getBlockState(pos.above(above)).setValue(SteamerBlock.HAVE_LID, true), 11);
                        pContext.getItemInHand().shrink(1);
                        return InteractionResult.SUCCESS;
                    }

                    above++;
                } else
                    break;
            }
            var context = new BlockPlaceContext(pContext) {
                @SuppressWarnings("ProtectedMemberInFinalClass")
                protected int _above;

                @Override
                public @NotNull BlockPos getClickedPos() {
                    return pos.above(_above);
                }
            };

            context._above = above;


            if (level.getBlockState(pos.above(above)).canBeReplaced(context)) {
                level.setBlock(pos.above(above), ModFoodBlocks.STEAMER.get().defaultBlockState().setValue(SteamerBlock.HAVE_LID, true), 11);

                pContext.getItemInHand().shrink(1);
                return InteractionResult.SUCCESS;
            } else
                return InteractionResult.FAIL;
        }

        if (level.getBlockState(pos).getBlock() instanceof CookingPotBlock) {
            if (level.getBlockState(pos.above()).isAir()) {
                level.setBlock(pos.above(), ModFoodBlocks.STEAMER.get().defaultBlockState().setValue(CONNECTED, true).setValue(HAVE_LID, true), 11);
                pContext.getItemInHand().shrink(1);
                return InteractionResult.SUCCESS;
            }

            int above = 1;

            while (true) {

                if (level.getBlockState(pos.above(above)).isAir()) {
                    level.setBlock(pos.above(above), ModFoodBlocks.STEAMER.get().defaultBlockState().setValue(HAVE_LID, true), 11);
                    pContext.getItemInHand().shrink(1);
                    return InteractionResult.SUCCESS;
                }

                if (level.getBlockState(pos.above(above)).getBlock() instanceof SteamerBlock && !level.getBlockState(pos.above(above)).getValue(HAVE_LID)) {
                    if (level.getBlockState(pos.above(above)).getValue(SteamerBlock.SIZE) < 4) {
                        level.setBlock(pos.above(above), level.getBlockState(pos.above(above)).setValue(SteamerBlock.SIZE, level.getBlockState(pos.above(above)).getValue(SteamerBlock.SIZE) + 1).setValue(HAVE_LID, true), 11);
                        pContext.getItemInHand().shrink(1);
                        return InteractionResult.SUCCESS;
                    } else {
                        above++;
                    }
                } else
                    break;
            }
        }

        return super.useOn(pContext);
    }
}

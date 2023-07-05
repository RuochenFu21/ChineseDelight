package net.forsteri.chinesedelight.contents.steamer;

import net.forsteri.chinesedelight.registries.ModFoodBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import vectorwing.farmersdelight.common.block.CookingPotBlock;

import java.util.Objects;

@SuppressWarnings("deprecation")
public class SteamerBlock extends BaseEntityBlock {
    public static final IntegerProperty SIZE = IntegerProperty.create("size", 1, 4);
    public static final BooleanProperty HAVE_LID = BooleanProperty.create("have_lid");
    public static final BooleanProperty CONNECTED = BooleanProperty.create("connected");
    public SteamerBlock(Properties p_49795_) {
        super(p_49795_);
        this.registerDefaultState(this.defaultBlockState().setValue(SIZE, 1).setValue(HAVE_LID, false).setValue(CONNECTED, false));
    }

    @Override
    public boolean canSurvive(@NotNull BlockState pState, LevelReader pLevel, BlockPos pPos) {
        return (pLevel.getBlockState(pPos.below()).getBlock() instanceof SteamerBlock && pLevel.getBlockState(pPos.below()).getValue(SIZE) == 4) || pLevel.getBlockState(pPos.below()).getBlock() instanceof CookingPotBlock;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pPos, @NotNull BlockState pState) {
        return new SteamerBlockEntity(pPos, pState);
    }

    @Override
    public @NotNull RenderShape getRenderShape(@NotNull BlockState pState) {
        return RenderShape.MODEL;
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(SIZE, HAVE_LID, CONNECTED);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level pLevel, @NotNull BlockState pState, @NotNull BlockEntityType<T> pBlockEntityType) {
        return createTickerHelper(pBlockEntityType, ModFoodBlocks.STEAMER_BLOCK_ENTITY.get(), SteamerBlockEntity::tick);
    }

    @Override
    public @NotNull BlockState updateShape(BlockState pState, @NotNull Direction pDirection, @NotNull BlockState pNeighborState, @NotNull LevelAccessor pLevel, @NotNull BlockPos pCurrentPos, @NotNull BlockPos pNeighborPos) {
        return pState.canSurvive(pLevel, pCurrentPos) ?
                super.updateShape(pState, pDirection, pNeighborState, pLevel, pCurrentPos, pNeighborPos).setValue(CONNECTED, pLevel.getBlockState(pCurrentPos.below()).getBlock() instanceof CookingPotBlock)
                : Blocks.AIR.defaultBlockState();
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState pState, @NotNull BlockGetter pLevel, @NotNull BlockPos pPos, @NotNull CollisionContext pContext) {
        return Block.box(2.0D, 0.0D, 2.0D, 14.0D, pState.getValue(SIZE) * 4.0D, 14.0D);
    }

    @Override
    public @NotNull VoxelShape getCollisionShape(@NotNull BlockState pState, @NotNull BlockGetter pLevel, @NotNull BlockPos pPos, @NotNull CollisionContext pContext) {
        return getShape(pState, pLevel, pPos, pContext);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return pContext.getLevel().getBlockState(pContext.getClickedPos().below()).getBlock() instanceof CookingPotBlock ? (super.getStateForPlacement(pContext) == null ? null : Objects.requireNonNull(super.getStateForPlacement(pContext)).setValue(CONNECTED, true)) : super.getStateForPlacement(pContext);
    }
}

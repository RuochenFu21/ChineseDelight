package net.forsteri.chinesesdelight.contents.steamer;

import net.forsteri.chinesesdelight.registries.ModFoodBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

public class SteamerBlockEntity extends BlockEntity {
    public SteamerBlockEntity(BlockPos pWorldPosition, BlockState pBlockState) {
        super(ModFoodBlocks.STEAMER_BLOCK_ENTITY.get(), pWorldPosition, pBlockState);
    }

    private @Nullable SteamerBlockEntity owner = null;

    protected void refreshOwner() {
        assert level != null;
        if (level.getBlockState(getBlockPos().below()).getBlock() instanceof SteamerBlock)
            owner = (SteamerBlockEntity) level.getBlockEntity(getBlockPos().below());
        else
            owner = null;
    }

    @Override
    public void onLoad() {
        super.onLoad();
    }

    @SuppressWarnings("unused")
    protected static <T extends BlockEntity> void tick(Level pLevel, BlockPos pPos, BlockState pState, T pBlockEntity) {
        assert pBlockEntity instanceof SteamerBlockEntity;
        SteamerBlockEntity steamerBlockEntity = ((SteamerBlockEntity) pBlockEntity);
        steamerBlockEntity.refreshOwner();
        if (steamerBlockEntity.owner != null) {
            boolean a; // Placeholder
        }

    }
}

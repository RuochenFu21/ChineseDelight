package net.forsteri.chinesesdelight.contents.abstracts;

import net.forsteri.chinesesdelight.registries.ModFoodBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@SuppressWarnings("deprecation")
public abstract class AbstractBowlWithSoupBlock extends Block {

    public static final IntegerProperty BOWLS_LEFT = IntegerProperty.create("bowl", 0, 4);
    public AbstractBowlWithSoupBlock(Properties p_49795_) {
        super(p_49795_);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(BOWLS_LEFT, 4)
        );
    }

    @Override
    public @NotNull InteractionResult use(@NotNull BlockState p_60503_, Level p_60504_, BlockPos p_60505_, Player p_60506_, InteractionHand p_60507_, BlockHitResult p_60508_) {
        ItemStack itemstack = p_60506_.getItemInHand(p_60507_);

        if (p_60504_.isClientSide) {
            if (eat(p_60504_, p_60505_, p_60503_, p_60506_).consumesAction()) {
                return InteractionResult.SUCCESS;
            }

            if (itemstack.isEmpty()) {
                return InteractionResult.CONSUME;
            }
        }

        return eat(p_60504_, p_60505_, p_60503_, p_60506_);
    }

    protected InteractionResult eat(LevelAccessor p_51186_, BlockPos p_51187_, BlockState p_51188_, Player p_51189_) {
        if (!p_51189_.canEat(false)) {
            return InteractionResult.PASS;
        } else {
            p_51189_.eat(((Level) p_51186_), getSmallBowlOfThis().asItem().getDefaultInstance());

            if(p_51188_.getValue(BOWLS_LEFT) -1 != 0)
                p_51186_.setBlock(p_51187_, p_51188_.setValue(BOWLS_LEFT, p_51188_.getValue(BOWLS_LEFT) -1), 3);
            else
                p_51186_.setBlock(p_51187_, ModFoodBlocks.BOWL_BLOCK.get().defaultBlockState(), 3);

            return InteractionResult.SUCCESS;
        }
    }

    protected abstract ItemLike getSmallBowlOfThis();

    @Override
    public @NotNull VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        return Block.box(2.0D, 0.0D, 2.0D, 14.0D, 4.0D, 14.0D);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_49915_) {
        p_49915_.add(BOWLS_LEFT);
    }
}

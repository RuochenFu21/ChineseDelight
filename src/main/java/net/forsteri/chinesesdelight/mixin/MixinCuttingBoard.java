package net.forsteri.chinesesdelight.mixin;

import net.forsteri.chinesesdelight.contents.abstracts.customizable.AbstractCustomizableBaseItem;
import net.forsteri.chinesesdelight.contents.foods.customizable.CustomRecipeHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.apache.commons.lang3.ArrayUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vectorwing.farmersdelight.common.block.CuttingBoardBlock;
import vectorwing.farmersdelight.common.block.entity.CuttingBoardBlockEntity;

@Mixin(value = CuttingBoardBlock.class, remap = false)
public abstract class MixinCuttingBoard extends BaseEntityBlock implements SimpleWaterloggedBlock {
    protected MixinCuttingBoard(Properties p_49224_) {
        super(p_49224_);
    }

    @Inject(at = @At("HEAD"), method = "use", cancellable = true)
    private void useMixin(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit, CallbackInfoReturnable<InteractionResult> cir) {
        BlockEntity tileEntity = level.getBlockEntity(pos);
        if (tileEntity instanceof CuttingBoardBlockEntity cuttingBoardEntity) {
            ItemStack heldStack = player.getItemInHand(hand);
            if (!cuttingBoardEntity.isEmpty()) {
                if(cuttingBoardEntity.getStoredItem().getItem() instanceof AbstractCustomizableBaseItem storedItem) {
                    if (CustomRecipeHandler.supportedFillings().contains(heldStack.getItem())
                            && cuttingBoardEntity.getStoredItem().getOrCreateTag().getIntArray("fillings").length < storedItem.maxFillingSize()){
                        var inserted = new ItemStack(storedItem);
                        inserted.getOrCreateTag().putIntArray("fillings",

                                ArrayUtils.addAll(cuttingBoardEntity.getInventory().extractItem(0, 1, false).getOrCreateTag().getIntArray("fillings"),
                                        CustomRecipeHandler.supportedFillings().indexOf(heldStack.getItem()))
                        );

                        cuttingBoardEntity.getInventory().insertItem(0,
                                inserted, false);
                        heldStack.shrink(1);
                    }
                    else {
                        var inserted = new ItemStack(storedItem.getProductItem());
                        inserted.getOrCreateTag().putIntArray("fillings",
                                cuttingBoardEntity.getInventory().extractItem(0, 1, false).getOrCreateTag().getIntArray("fillings"));

                        cuttingBoardEntity.getInventory().insertItem(0,
                                inserted, false);
                    }
                    cir.setReturnValue(InteractionResult.SUCCESS);
                    cir.cancel();
                }
            }
        }
    }
}

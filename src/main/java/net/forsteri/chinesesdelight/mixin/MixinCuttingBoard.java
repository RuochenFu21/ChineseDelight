package net.forsteri.chinesesdelight.mixin;

import net.forsteri.chinesesdelight.contents.abstracts.customizable.AbstractCustomizableProcessingItem;
import net.forsteri.chinesesdelight.handlers.CustomRecipeHandler;
import net.forsteri.chinesesdelight.registries.ModFoodItems;
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
import vectorwing.farmersdelight.common.registry.ModItems;

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
                if(cuttingBoardEntity.getStoredItem().getItem() instanceof AbstractCustomizableProcessingItem storedItem) {
                    if (CustomRecipeHandler.rawFillingList().contains(heldStack.getItem())
                            && cuttingBoardEntity.getStoredItem().getOrCreateTag().getIntArray("fillings").length < storedItem.maxFillingSize()){
                        var inserted = new ItemStack(storedItem);
                        inserted.getOrCreateTag().putIntArray("fillings",

                                ArrayUtils.addAll(cuttingBoardEntity.getInventory().extractItem(0, 1, false).getOrCreateTag().getIntArray("fillings"),
                                        CustomRecipeHandler.rawFillingList().indexOf(heldStack.getItem()))
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
                if(cuttingBoardEntity.getStoredItem().is(ModItems.WHEAT_DOUGH.get())) {
                    cuttingBoardEntity.getInventory().insertItem(0,
                            new ItemStack(ModFoodItems.DOUGH_SKIN.get(), cuttingBoardEntity.getInventory().extractItem(0, cuttingBoardEntity.getInventory().getStackInSlot(0).getCount(), false).getCount()), false);
                    cir.setReturnValue(InteractionResult.SUCCESS);
                    cir.cancel();
                }
                if(cuttingBoardEntity.getStoredItem().is(ModFoodItems.DOUGH_SKIN.get())) {
                    if (player.getItemInHand(hand).is(ModFoodItems.CIRCULAR_CUTTER.get())) {
                        cuttingBoardEntity.getInventory().insertItem(0,
                                new ItemStack(ModFoodItems.PROCESSING_WHITE_DUMPLING.get(), cuttingBoardEntity.getInventory().extractItem(0, cuttingBoardEntity.getInventory().getStackInSlot(0).getCount(), false).getCount()), false);
                        cir.setReturnValue(InteractionResult.SUCCESS);
                        cir.cancel();
                    }
                }
            }
        }
    }
}

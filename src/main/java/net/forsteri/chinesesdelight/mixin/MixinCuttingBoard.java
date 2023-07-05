package net.forsteri.chinesesdelight.mixin;

import net.forsteri.chinesesdelight.contents.foods.fillable.AbstractFillable;
import net.forsteri.chinesesdelight.contents.foods.fillable.AbstractFillableProcessingItem;
import net.forsteri.chinesesdelight.contents.foods.fillable.FillingHandler;
import net.forsteri.chinesesdelight.contents.foods.fillable.dumplings.stuffing.DumplingStuffingMap;
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
                if(cuttingBoardEntity.getStoredItem().getItem() instanceof AbstractFillableProcessingItem storedItem) {
                    if (DumplingStuffingMap.rawToCookedMap.containsKey(heldStack.getItem())
                            && new FillingHandler(cuttingBoardEntity.getStoredItem().getOrCreateTag().getCompound("fillings")).getAllStuffings().size() < storedItem.maxFillingSize()){
                        var inserted = new ItemStack(storedItem);

                        var tag = new FillingHandler(cuttingBoardEntity.getInventory().extractItem(0, 1, false).getOrCreateTag().getCompound("fillings"));
                        tag.addStuffing(heldStack.getItem());

                        inserted.getOrCreateTag().put("fillings", tag.nbt);

                        cuttingBoardEntity.getInventory().insertItem(0,
                                inserted, false);

                        heldStack.shrink(1);

                        cir.setReturnValue(InteractionResult.SUCCESS);
                        cir.cancel();
                    } else if (AbstractFillable.hasFillings(cuttingBoardEntity.getStoredItem())){
                        var inserted = new ItemStack(storedItem.getProductItem());
                        var tag =  new FillingHandler(inserted.getOrCreateTag().getCompound("fillings"));
                        tag.addStuffings(
                                new FillingHandler(cuttingBoardEntity.getInventory().extractItem(0, 1, false).getOrCreateTag().getCompound("fillings")).getAllStuffings()
                        );

                        inserted.getOrCreateTag().put("fillings", tag.nbt);

                        cuttingBoardEntity.getInventory().insertItem(0,
                                inserted, false);

                        cir.setReturnValue(InteractionResult.SUCCESS);
                        cir.cancel();
                    }
                }
                if(cuttingBoardEntity.getStoredItem().is(ModItems.WHEAT_DOUGH.get()) && player.getItemInHand(hand).is(ModFoodItems.ROLLING_PIN.get())) {
                    cuttingBoardEntity.getInventory().insertItem(0,
                            new ItemStack(ModFoodItems.FLAT_DOUGH.get(), cuttingBoardEntity.getInventory().extractItem(0, cuttingBoardEntity.getInventory().getStackInSlot(0).getCount(), false).getCount()), false);
                    cir.setReturnValue(InteractionResult.SUCCESS);
                    cir.cancel();
                }
                if(cuttingBoardEntity.getStoredItem().is(ModFoodItems.FLAT_DOUGH.get())) {
                    if (player.getItemInHand(hand).is(ModFoodItems.CIRCULAR_CUTTER.get())) {
                        cuttingBoardEntity.getInventory().insertItem(0,
                                new ItemStack(ModFoodItems.PROCESSING_WHITE_DUMPLING.get(), cuttingBoardEntity.getInventory().extractItem(0, cuttingBoardEntity.getInventory().getStackInSlot(0).getCount(), false).getCount()), false);
                        cir.setReturnValue(InteractionResult.SUCCESS);
                        cir.cancel();
                    }
                }

                // We used mixin to make the crafting more immersive
            }
        }
    }
}

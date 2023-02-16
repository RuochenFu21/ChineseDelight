package net.forsteri.chinesesdelight.contents.foods.customizable.dumplings;

import net.forsteri.chinesesdelight.handlers.CustomRecipeHandler;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import vectorwing.farmersdelight.common.item.ConsumableItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DumplingSoup extends ConsumableItem {
    public DumplingSoup(Properties properties) {
        super(properties.craftRemainder(Items.BOWL));
    }

    @Override
    public boolean isEdible() {
        return true;
    }

    @Override
    public @NotNull ItemStack finishUsingItem(@NotNull ItemStack stack, @NotNull Level level, @NotNull LivingEntity consumer) {
        for(List<ItemLike> fillings : getDumplingFillings(stack)){
            for(ItemLike filling : fillings){
                consumer.eat(level, filling.asItem().getDefaultInstance());
            }
        }

        return stack;
    }

    protected List<List<ItemLike>> getDumplingFillings(ItemStack stack){
        List<List<ItemLike>> ret = new ArrayList<>();

        int i = 0;
        while (stack.getOrCreateTag().contains("dumpling" + i)) {
            //noinspection SuspiciousMethodCalls
            ret.add(stack.getOrCreateTag().getList("dumpling" + i, 8).stream().map(x -> CustomRecipeHandler.fillingMaps().get(x)).toList());
            i++;
        }

        return ret;
    }

    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level pLevel, Player pPlayer, @NotNull InteractionHand pUsedHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pUsedHand);
        boolean canEat = true;

        for(List<ItemLike> fillings : getDumplingFillings(itemstack)){
            for(ItemLike filling : fillings){
                //noinspection deprecation
                if (!pPlayer.canEat(Objects.requireNonNull(filling.asItem().getFoodProperties()).canAlwaysEat())) {
                    canEat = false;
                    break;
                }
            }
        }

        if(getDumplingFillings(itemstack).size() == 0)
            canEat = false;

        if (canEat) {
            pPlayer.startUsingItem(pUsedHand);
            return InteractionResultHolder.consume(itemstack);
        }

        return InteractionResultHolder.pass(pPlayer.getItemInHand(pUsedHand));
    }
}

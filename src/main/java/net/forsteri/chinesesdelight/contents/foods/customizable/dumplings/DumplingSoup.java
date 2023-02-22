package net.forsteri.chinesesdelight.contents.foods.customizable.dumplings;

import net.forsteri.chinesesdelight.handlers.CustomRecipeHandler;
import net.forsteri.chinesesdelight.registries.ModFoodItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import vectorwing.farmersdelight.common.item.ConsumableItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class DumplingSoup extends ConsumableItem {
    public DumplingSoup(Properties properties) {
        super(properties.craftRemainder(Items.BOWL).stacksTo(1));
    }

    @Override
    public boolean isEdible() {
        return true;
    }

    @Override
    public @NotNull ItemStack finishUsingItem(@NotNull ItemStack stack, @NotNull Level level, @NotNull LivingEntity consumer) {
        for(ItemLike filling :
                getDumplingFillings(stack).get(getDumplingFillings(stack).size()-1)) {
            consumer.eat(level, filling.asItem().getDefaultInstance());
        }

        ItemStack ret = ModFoodItems.DUMPLING_SOUP.get().getDefaultInstance();

        for(int i=0; i<getDumplingFillings(stack).size()-1; i++)
            ret.getOrCreateTag().putIntArray("dumpling" + i, stack.getOrCreateTag().getIntArray("dumpling" + i));

        if(getDumplingFillings(ret).size() == 0) {
            ret = new ItemStack(Items.BOWL);
        }

        return ret;
    }

    protected static List<List<ItemLike>> getDumplingFillings(ItemStack stack){
        List<List<ItemLike>> ret = new ArrayList<>();

        int i = 0;
        while (stack.getOrCreateTag().contains("dumpling" + i)) {
            ret.add(
                    Arrays.stream(stack.getOrCreateTag().getIntArray("dumpling" + i)).mapToObj(x -> CustomRecipeHandler.cookedFillingList().get(x)).toList());
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

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, @NotNull List<Component> tooltip, @NotNull TooltipFlag isAdvanced) {
        int i = 0;
        while (stack.getOrCreateTag().contains("dumpling" + i)) {
            tooltip.add(new TextComponent(new TranslatableComponent("item.chinesesdelight.dumpling").getString() + " " + (i + 1))
                    .withStyle(new ChatFormatting[]{ChatFormatting.GRAY}));
            tooltip.addAll(Arrays.stream(stack.getOrCreateTag().getIntArray("dumpling" + i))
                    .mapToObj(j ->
                            new TranslatableComponent(
                                    CustomRecipeHandler.cookedFillingList().get(j).asItem().getDescriptionId()
                            )
                                    .withStyle(new ChatFormatting[]{ChatFormatting.DARK_GRAY})
                    ).toList());
            i++;
        }

        super.appendHoverText(stack, level, tooltip, isAdvanced);
    }

    @Override
    public int getUseDuration(@NotNull ItemStack pStack) {
        return 10;
    }
}

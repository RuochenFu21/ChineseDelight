package net.forsteri.chinesesdelight.contents.foods.customizable.dumplings;

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
import java.util.List;
import java.util.Objects;

@SuppressWarnings("deprecation")
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
            if (filling.asItem().isEdible())
                consumer.eat(level, filling.asItem().getDefaultInstance());
            else
                if (consumer instanceof Player) {
                    if (!((Player) consumer).getInventory().add(new ItemStack(filling)))
                        consumer.spawnAtLocation(new ItemStack(filling));
                } else
                    consumer.spawnAtLocation(new ItemStack(filling));
        }

        ItemStack ret = stack;

        ret.getOrCreateTag().getCompound("dumplings").remove("dumpling" + (getDumplingFillings(ret).size() - 1));

        if(getDumplingFillings(ret).size() == 0) {
            ret = new ItemStack(Items.BOWL);
        }

        return ret;
    }

    protected static List<List<ItemLike>> getDumplingFillings(ItemStack stack){
        List<List<ItemLike>> ret = new ArrayList<>();

        int i = 0;
        while (stack.getOrCreateTag().getCompound("dumplings").contains("dumpling" + i)) {
            ret.add(
                    new DumplingFillingHandler(stack.getOrCreateTag().getCompound("dumplings").getCompound("dumpling" + i)).getAllStuffings().stream().map(x -> ((ItemLike) x)).toList());
            i++;
        }

        return ret;
    }

    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level pLevel, Player pPlayer, @NotNull InteractionHand pUsedHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pUsedHand);
        boolean canEat = true;

        for(List<ItemLike> fillings : getDumplingFillings(itemstack)){
            for(ItemLike filling : fillings){
                if (!filling.asItem().isEdible())
                    continue;
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
        while (stack.getOrCreateTag().getCompound("dumplings").contains("dumpling" + i)) {
            tooltip.add(new TextComponent(new TranslatableComponent("item.chinesesdelight.dumpling").getString() + " " + (i + 1))
                    .withStyle(new ChatFormatting[]{ChatFormatting.GRAY}));
            tooltip.addAll(new DumplingFillingHandler(stack.getOrCreateTag().getCompound("dumplings").getCompound("dumpling" + i)).getAllStuffings().stream()
                    .map(j ->
                            new TranslatableComponent(
                                    j.getDescriptionId()
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

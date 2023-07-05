package net.forsteri.chinesesdelight.contents.foods.customizable;

import net.forsteri.chinesesdelight.contents.foods.customizable.dumplings.DumplingFillingHandler;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class AbstractCustomizable extends Item {
    public AbstractCustomizable(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void appendHoverText(ItemStack p_41421_, @Nullable Level p_41422_, List<Component> p_41423_, @NotNull TooltipFlag p_41424_) {
        p_41423_.addAll(
                new DumplingFillingHandler(p_41421_.getOrCreateTag().getCompound("fillings")).getAllStuffings().stream()
                .map(i ->
                        new TranslatableComponent(
                                i.getDescriptionId()
                        ).withStyle(new ChatFormatting[]{ChatFormatting.GRAY})
                ).toList());
        super.appendHoverText(p_41421_, p_41422_, p_41423_, p_41424_);
    }

    @Override
    public boolean isFoil(ItemStack pStack) {
        return new DumplingFillingHandler(pStack.getOrCreateTag().getCompound("fillings")).isFoil();
    }

    public static boolean hasFillings(ItemStack pStack) {
        return !new DumplingFillingHandler(pStack.getOrCreateTag().getCompound("fillings")).isEmpty();
    }

    @Override
    public @NotNull Component getName(@NotNull ItemStack pStack) {
        return new TranslatableComponent(
                hasFillings(pStack) ? getDescriptionId(pStack) + ".filled" : getDescriptionId(pStack) + ".empty"
        );
    }
}

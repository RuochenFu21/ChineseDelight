package net.forsteri.chinesesdelight.contents.foods.fillable;

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

public class AbstractFillable extends Item {
    public AbstractFillable(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void appendHoverText(ItemStack p_41421_, @Nullable Level p_41422_, @NotNull List<Component> p_41423_, @NotNull TooltipFlag p_41424_) {
        FillingUtil.appendHoverText(p_41421_.getOrCreateTag().getCompound("fillings"), p_41423_, ChatFormatting.GRAY);

        super.appendHoverText(p_41421_, p_41422_, p_41423_, p_41424_);
    }

    @Override
    public boolean isFoil(ItemStack pStack) {
        return new FillingHandler(pStack.getOrCreateTag().getCompound("fillings")).isFoil();
    }

    public static boolean hasFillings(ItemStack pStack) {
        return !new FillingHandler(pStack.getOrCreateTag().getCompound("fillings")).isEmpty();
    }

    @Override
    public @NotNull Component getName(@NotNull ItemStack pStack) {
        return new TranslatableComponent(
                hasFillings(pStack) ? getDescriptionId(pStack) + ".filled" : getDescriptionId(pStack) + ".empty"
        );
    }
}

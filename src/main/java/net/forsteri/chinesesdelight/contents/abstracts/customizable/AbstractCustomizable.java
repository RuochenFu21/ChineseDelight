package net.forsteri.chinesesdelight.contents.abstracts.customizable;

import net.forsteri.chinesesdelight.handlers.CustomRecipeHandler;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

public class AbstractCustomizable extends Item {
    public AbstractCustomizable(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void appendHoverText(ItemStack p_41421_, @Nullable Level p_41422_, List<Component> p_41423_, @NotNull TooltipFlag p_41424_) {
        p_41423_.addAll(Arrays.stream(p_41421_.getOrCreateTag().getIntArray("fillings"))
                .mapToObj(i ->
                        new TranslatableComponent(CustomRecipeHandler.rawFillingList().get(i).asItem().getDescriptionId())
                                .withStyle(new ChatFormatting[]{ChatFormatting.GRAY})
                ).toList());
        super.appendHoverText(p_41421_, p_41422_, p_41423_, p_41424_);
    }

    @Override
    public boolean isFoil(ItemStack pStack) {
        return Arrays.stream(pStack.getOrCreateTag().getIntArray("fillings")).anyMatch(i -> CustomRecipeHandler.rawFillingList().get(i).asItem().isFoil(pStack));
    }

    @Override
    public @NotNull Component getName(ItemStack pStack) {
        return new TranslatableComponent(
                pStack.getOrCreateTag().contains("fillings") ?  getDescriptionId(pStack) + ".filled" : getDescriptionId(pStack) + ".empty"
        );
    }
}

package net.forsteri.chinesesdelight.contents.abstracts.customizable;

import net.forsteri.chinesesdelight.handlers.CustomRecipeHandler;
import net.forsteri.chinesesdelight.handlers.CustomizableItemRenderer;
import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.IItemRenderProperties;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public abstract class AbstractCustomizableProcessingItem extends Item {
    public AbstractCustomizableProcessingItem(Properties p_41383_) {
        super(p_41383_);
    }

    public abstract int maxFillingSize();

    @Override
    public void appendHoverText(ItemStack p_41421_, @Nullable Level p_41422_, List<Component> p_41423_, @NotNull TooltipFlag p_41424_) {
        p_41423_.addAll(Arrays.stream(p_41421_.getOrCreateTag().getIntArray("fillings"))
                .mapToObj(i ->
                        new TranslatableComponent(CustomRecipeHandler.rawFillingList().get(i).asItem().getDescriptionId())
                                .withStyle(new ChatFormatting[]{ChatFormatting.GRAY})
                ).toList());
        super.appendHoverText(p_41421_, p_41422_, p_41423_, p_41424_);
    }

    public abstract AbstractCustomizableProductItem getProductItem();

    @Override
    public void initializeClient(Consumer<IItemRenderProperties> consumer) {
        consumer.accept(new IItemRenderProperties() {

            private final BlockEntityWithoutLevelRenderer renderer = new CustomizableItemRenderer();

            @Override
            public BlockEntityWithoutLevelRenderer getItemStackRenderer() {
                return renderer;
            }
        });
    }
}

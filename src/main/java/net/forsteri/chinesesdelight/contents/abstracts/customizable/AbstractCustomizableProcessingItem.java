package net.forsteri.chinesesdelight.contents.abstracts.customizable;

import net.forsteri.chinesesdelight.handlers.DumplingItemRenderer;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraftforge.client.IItemRenderProperties;

import java.util.function.Consumer;

public abstract class AbstractCustomizableProcessingItem extends AbstractCustomizable {
    public AbstractCustomizableProcessingItem(Properties p_41383_) {
        super(p_41383_);
    }

    public abstract int maxFillingSize();

    public abstract AbstractCustomizableProductItem getProductItem();

    @Override
    public void initializeClient(Consumer<IItemRenderProperties> consumer) {
        consumer.accept(new IItemRenderProperties() {

            private final BlockEntityWithoutLevelRenderer renderer = new DumplingItemRenderer();

            @Override
            public BlockEntityWithoutLevelRenderer getItemStackRenderer() {
                return renderer;
            }
        });
    }
}

package net.forsteri.chinesedelight.contents.foods.fillable;

import net.forsteri.chinesedelight.contents.foods.fillable.dumplings.DumplingItemRenderer;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraftforge.client.IItemRenderProperties;

import java.util.function.Consumer;

public abstract class AbstractFillableProcessingItem extends AbstractFillable {
    public AbstractFillableProcessingItem(Properties p_41383_) {
        super(p_41383_);
    }

    public abstract int maxFillingSize();

    public abstract AbstractFillableProductItem getProductItem();

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

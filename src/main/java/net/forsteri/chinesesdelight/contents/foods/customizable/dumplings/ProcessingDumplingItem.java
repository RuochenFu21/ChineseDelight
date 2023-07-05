package net.forsteri.chinesesdelight.contents.foods.customizable.dumplings;

import net.forsteri.chinesesdelight.contents.foods.customizable.AbstractCustomizableProcessingItem;
import net.forsteri.chinesesdelight.contents.foods.customizable.AbstractCustomizableProductItem;
import net.forsteri.chinesesdelight.registries.ModFoodItems;

public class ProcessingDumplingItem extends AbstractCustomizableProcessingItem {
    public ProcessingDumplingItem(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public int maxFillingSize() {
        return 3;
    }

    @Override
    public AbstractCustomizableProductItem getProductItem() {
        return ModFoodItems.RAW_WHITE_DUMPLING.get();
    }
}

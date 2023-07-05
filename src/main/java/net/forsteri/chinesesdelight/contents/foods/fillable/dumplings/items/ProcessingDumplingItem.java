package net.forsteri.chinesesdelight.contents.foods.fillable.dumplings.items;

import net.forsteri.chinesesdelight.contents.foods.fillable.AbstractFillableProcessingItem;
import net.forsteri.chinesesdelight.contents.foods.fillable.AbstractFillableProductItem;
import net.forsteri.chinesesdelight.registries.ModFoodItems;

public class ProcessingDumplingItem extends AbstractFillableProcessingItem {
    public ProcessingDumplingItem(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public int maxFillingSize() {
        return 3;
    }

    @Override
    public AbstractFillableProductItem getProductItem() {
        return ModFoodItems.RAW_WHITE_DUMPLING.get();
    }
}

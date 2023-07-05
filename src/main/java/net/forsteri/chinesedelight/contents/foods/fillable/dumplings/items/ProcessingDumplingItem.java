package net.forsteri.chinesedelight.contents.foods.fillable.dumplings.items;

import net.forsteri.chinesedelight.contents.foods.fillable.AbstractFillableProcessingItem;
import net.forsteri.chinesedelight.contents.foods.fillable.AbstractFillableProductItem;
import net.forsteri.chinesedelight.registries.ModFoodItems;

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

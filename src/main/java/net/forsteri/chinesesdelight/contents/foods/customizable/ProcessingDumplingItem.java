package net.forsteri.chinesesdelight.contents.foods.customizable;

import net.forsteri.chinesesdelight.contents.abstracts.customizable.AbstractCustomizableBaseItem;
import net.forsteri.chinesesdelight.registries.ModFoodItems;
import net.minecraft.world.item.Item;

public class ProcessingDumplingItem extends AbstractCustomizableBaseItem {
    public ProcessingDumplingItem(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public int maxFillingSize() {
        return 3;
    }

    @Override
    public Item getProductItem() {
        return ModFoodItems.RAW_DUMPLING.get();
    }
}

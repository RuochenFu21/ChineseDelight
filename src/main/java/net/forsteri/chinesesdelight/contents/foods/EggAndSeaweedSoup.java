package net.forsteri.chinesesdelight.contents.foods;

import net.forsteri.chinesesdelight.contents.abstracts.AbstractBowlWithSoupBlock;
import net.forsteri.chinesesdelight.registries.ModFoodItems;
import net.minecraft.world.level.ItemLike;

public class EggAndSeaweedSoup extends AbstractBowlWithSoupBlock {
    public EggAndSeaweedSoup(Properties p_49795_) {
        super(p_49795_);
    }

    @Override
    protected ItemLike getSmallBowlOfThis() {
        return ModFoodItems.SMALL_BOWL_OF_EGG_AND_SEAWEED_SOUP.get();
    }
}

package net.forsteri.chinesedelight.contents.foods;

import net.forsteri.chinesedelight.contents.foods.abstracts.AbstractBowlWithSoupBlock;
import net.forsteri.chinesedelight.registries.ModFoodItems;
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

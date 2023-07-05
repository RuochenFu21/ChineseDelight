package net.forsteri.chinesesdelight.contents.foods.fillable;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.*;

public class FillingHandler {
    public CompoundTag nbt;
    private Integer stuffingCount = 0;

    public FillingHandler(CompoundTag pTag) {
        this.nbt = pTag;
        this.stuffingCount += pTag.size();
    }

    public void addStuffing(Item pRaw) {
        nbt.putString(
                "fillings" + stuffingCount++,
                Objects.requireNonNull(pRaw.getRegistryName()).toString()
        );
    }

    public void addStuffings(List<Item> itemList){
        for (Item item : itemList) {
            addStuffing(item);
        }
    }

    public List<Item> getAllStuffings() {
        List<Item> ret = new ArrayList<>();

        int i = 0;

        while (nbt.contains("fillings" + i)) {
            ret.add(ForgeRegistries.ITEMS.getValue(
                    new ResourceLocation(nbt.getString("fillings" + i)))
            );
            i++;
        }

        return ret;
    }

    public boolean isFoil() {
        return getAllStuffings().stream().anyMatch(
                i -> i.getDefaultInstance().hasFoil()
        );
    }

    public boolean isEmpty() {
        return getAllStuffings().isEmpty();
    }
}

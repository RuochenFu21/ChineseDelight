package net.forsteri.chinesesdelight.contents.foods.fillable;

import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.Item;

import java.util.List;

public class FillingUtil {
    public static void appendCompressedHoverText(CompoundTag tag, List<Component> p_41423_, ChatFormatting color) {
        List<Item> stuffings = new FillingHandler(tag).getAllStuffings();

        if (stuffings.isEmpty()) return;

        MutableComponent component = new TranslatableComponent(
                stuffings.get(0).getDescriptionId()
        ).withStyle(new ChatFormatting[]{color});

        stuffings.subList(1, stuffings.size()).forEach(item -> {
            component.append(new TextComponent(", ").withStyle(new ChatFormatting[]{color}));

            component.append(new TranslatableComponent(
                    item.getDescriptionId()
            ).withStyle(new ChatFormatting[]{color}));
        });

        p_41423_.add(component);
    }

    public static void appendHoverText(CompoundTag tag, List<Component> p_41423_, ChatFormatting color) {
        p_41423_.addAll(
                new FillingHandler(tag).getAllStuffings().stream()
                        .map(i ->
                                new TranslatableComponent(
                                        i.getDescriptionId()
                                ).withStyle(new ChatFormatting[]{color})
                        ).toList()
        );
    }
}

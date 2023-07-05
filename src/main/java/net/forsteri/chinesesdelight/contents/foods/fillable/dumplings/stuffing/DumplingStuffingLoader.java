package net.forsteri.chinesesdelight.contents.foods.fillable.dumplings.stuffing;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.item.Items;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class DumplingStuffingLoader extends SimpleJsonResourceReloadListener {

    private static final Gson GSON = new Gson();

    public static final DumplingStuffingLoader INSTANCE = new DumplingStuffingLoader();

    public DumplingStuffingLoader() {
        super(GSON, "dumpling_filling");
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> pObject, @NotNull ResourceManager pResourceManager, @NotNull ProfilerFiller pProfiler) {
        for (Map.Entry<ResourceLocation, JsonElement> entry : pObject.entrySet()) {
            JsonElement element = entry.getValue();
            if (element.isJsonObject()) {
                ResourceLocation id = entry.getKey();
                JsonObject object = element.getAsJsonObject();
                JsonElement rawElement = object.get("raw");
                JsonElement cookedElement = object.get("cooked");

                if (rawElement == null)
                    throw new RuntimeException("No raw item specified for dumpling stuffing: " + id);

                if (cookedElement == null)
                    cookedElement = rawElement;

                if (ForgeRegistries.ITEMS.getValue(new ResourceLocation(rawElement.getAsString())) == Items.AIR) {
                    throw new RuntimeException("Invalid raw stuffing specified for dumpling stuffing data: " + id);
                }

                if (ForgeRegistries.ITEMS.getValue(new ResourceLocation(cookedElement.getAsString())) == Items.AIR) {
                    throw new RuntimeException("Invalid cooked stuffing specified for dumpling stuffing data: " + id);
                }

                DumplingStuffingMap.rawToCookedMap.put(ForgeRegistries.ITEMS.getValue(new ResourceLocation(rawElement.getAsString())), ForgeRegistries.ITEMS.getValue(new ResourceLocation((cookedElement.getAsString()))));
            }
        }
    }
}

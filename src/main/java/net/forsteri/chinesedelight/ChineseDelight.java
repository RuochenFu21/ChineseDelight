package net.forsteri.chinesedelight;

import net.forsteri.chinesedelight.contents.foods.fillable.dumplings.stuffing.DumplingStuffingLoader;
import net.forsteri.chinesedelight.contents.steamer.SteamerBlockRenderer;
import net.forsteri.chinesedelight.handlers.SpecialModelHandler;
import net.forsteri.chinesedelight.registries.ModFoodBlocks;
import net.forsteri.chinesedelight.registries.ModFoodItems;
import net.forsteri.chinesedelight.registries.RecipeTypes;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(ChineseDelight.MOD_ID)
public class ChineseDelight {

    public static final String MOD_ID = "chinesedelight";

    // Directly reference a slf4j logger

    public ChineseDelight() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModFoodItems.ITEMS.register(eventBus);
        ModFoodBlocks.BLOCKS.register(eventBus);
        ModFoodBlocks.TILE_ENTITY.register(eventBus);
    //    OtherRegistries.register();
        RecipeTypes.register(eventBus);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        IEventBus forgeEventBus = MinecraftForge.EVENT_BUS;
        IEventBus modEventBus = FMLJavaModLoadingContext.get()
                .getModEventBus();

        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> clientSetup(forgeEventBus, modEventBus));
    }

    private void clientSetup(IEventBus ignoredForgeEventBus, IEventBus modEventBus) {
        modEventBus.addListener(SpecialModelHandler::onSpecialModelRegistry);
        modEventBus.addListener(SpecialModelHandler::onModelBakedEvent);
    }

    // You can use EventBusSubscriber to automatically subscribe events on the contained class (this is subscribing to the MOD
    // Event bus for receiving Registry Events)
    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        @SubscribeEvent
        public static void onClientSetupEvent(final FMLCommonSetupEvent event) {
            // Register a new block here
            event.enqueueWork(() -> BlockEntityRenderers.register(ModFoodBlocks.STEAMER_BLOCK_ENTITY.get(), SteamerBlockRenderer::new));
        }
    }

    @Mod.EventBusSubscriber
    public static class ForgeEvents {
        @SubscribeEvent
        public static void addReloadListeners(AddReloadListenerEvent event) {
            event.addListener(DumplingStuffingLoader.INSTANCE);
        }
    }
}

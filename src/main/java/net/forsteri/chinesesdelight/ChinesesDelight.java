package net.forsteri.chinesesdelight;

import com.mojang.logging.LogUtils;
import net.forsteri.chinesesdelight.contents.steamer.SteamerBlockRenderer;
import net.forsteri.chinesesdelight.handlers.DumplingStuffingLoader;
import net.forsteri.chinesesdelight.handlers.SpecialModelHandler;
import net.forsteri.chinesesdelight.registries.ModFoodBlocks;
import net.forsteri.chinesesdelight.registries.ModFoodItems;
import net.forsteri.chinesesdelight.registries.RecipeTypes;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

import java.util.stream.Collectors;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(ChinesesDelight.MOD_ID)
public class ChinesesDelight {

    public static final String MOD_ID = "chinesesdelight";

    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    public ChinesesDelight() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModFoodItems.ITEMS.register(eventBus);
        ModFoodBlocks.BLOCKS.register(eventBus);
        ModFoodBlocks.TILE_ENTITY.register(eventBus);
    //    OtherRegistries.register();
        RecipeTypes.register(eventBus);
        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        // Register the enqueueIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        // Register the processIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);

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

    private void setup(final FMLCommonSetupEvent event) {
        // Some preinit code
        LOGGER.info("HELLO FROM PREINIT");
        LOGGER.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
    }

    private void enqueueIMC(final InterModEnqueueEvent event) {
        // Some example code to dispatch IMC to another mod
        InterModComms.sendTo("chinesesdelight", "helloworld", () -> { LOGGER.info("Hello world from the MDK"); return "Hello world";});
    }

    private void processIMC(final InterModProcessEvent event) {
        // Some example code to receive and process InterModComms from other mods
        LOGGER.info("Got IMC {}", event.getIMCStream().
                map(m->m.messageSupplier().get()).
                collect(Collectors.toList()));
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
    }

    // You can use EventBusSubscriber to automatically subscribe events on the contained class (this is subscribing to the MOD
    // Event bus for receiving Registry Events)
    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        @SubscribeEvent
        public static void onBlocksRegistry(final RegistryEvent.Register<Block> blockRegistryEvent) {
            // Register a new block here
            LOGGER.info("HELLO from Register Block");
        }

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

package net.forsteri.chinesedelight.contents.steamer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import org.jetbrains.annotations.NotNull;

public class SteamerBlockRenderer implements BlockEntityRenderer<SteamerBlockEntity> {
    public SteamerBlockRenderer(@SuppressWarnings("unused") BlockEntityRendererProvider.Context pContext) {
    }

    @Override
    public void render(@NotNull SteamerBlockEntity pBlockEntity, float pPartialTick, @NotNull PoseStack pPoseStack, @NotNull MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay) {

    }
}

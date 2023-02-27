package net.forsteri.chinesesdelight.handlers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.Random;

public class CustomizableItemRenderer extends BlockEntityWithoutLevelRenderer {
    public CustomizableItemRenderer() {
        //noinspection DataFlowIssue
        super(null, null);
    }

    private static final Random random = new Random();

    protected ResourceLocation loc = null;

    @Override
    public void renderByItem(@NotNull ItemStack pStack, ItemTransforms.@NotNull TransformType pTransformType, @NotNull PoseStack pPoseStack, @NotNull MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {

        pPoseStack.popPose();
        pPoseStack.pushPose();

        if (pTransformType == ItemTransforms.TransformType.GUI) {
            pPoseStack.mulPose(Vector3f.XP.rotationDegrees(90F));
        }

        pPoseStack.translate(0, -3 / 16D, 0);

        int size = pStack.getOrCreateTag().getIntArray("fillings").length;


        if (pTransformType.firstPerson() || pTransformType == ItemTransforms.TransformType.GUI) {
            pPoseStack.translate(0, -size / 32D, 0);
        }

        pPoseStack.pushPose();
        pPoseStack.mulPose(Vector3f.XP.rotationDegrees(90));
        pPoseStack.mulPose(Vector3f.ZP.rotationDegrees(180));

        if(loc == null)
            loc = new ModelResourceLocation(Objects.requireNonNull(pStack.getItem().getRegistryName()).getNamespace() + ":"+ pStack.getItem().getRegistryName().getPath() + "_base", "inventory");

        pPoseStack.pushPose();

        BakedModel model =
                SpecialModelHandler.modelLocation.get(loc)
        //        Minecraft.getInstance().getModelManager().getModel(loc)

                ;

        pPoseStack.translate(-1/2D, -1/2D, -7 / 16D);

        Minecraft.getInstance().getItemRenderer().renderModelLists(
                model,
                pStack,
                pPackedLight,
                pPackedOverlay,
                pPoseStack,
                pBuffer.getBuffer(RenderType.cutout())
        );

        pPoseStack.popPose();


        random.setSeed(0);
        for (int slot = 0; slot < pStack.getOrCreateTag().getIntArray("fillings").length; slot++) {
            pPoseStack.pushPose();
            pPoseStack.scale(0.5F, 0.5F, 0.5F);
            ItemStack stack =
                    new ItemStack(CustomRecipeHandler.rawFillingList().get(pStack.getOrCreateTag().getIntArray("fillings")[slot]));
            pPoseStack.mulPose(Vector3f.ZP.rotationDegrees(random.nextFloat() * 20 - 10));
            Minecraft.getInstance().getItemRenderer().renderStatic(stack, ItemTransforms.TransformType.FIXED, pPackedLight, pPackedOverlay, pPoseStack, pBuffer, slot);
            pPoseStack.popPose();
            pPoseStack.translate(0, 0, -1 / 16D - 0.001);
        }
        pPoseStack.popPose();
    }


}

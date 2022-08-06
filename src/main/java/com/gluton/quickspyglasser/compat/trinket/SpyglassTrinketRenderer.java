package com.gluton.quickspyglasser.compat.trinket;

import com.gluton.quickspyglasser.QuickSpyglasser;
import com.gluton.quickspyglasser.render.SpyglassBeltModel;
import com.gluton.quickspyglasser.render.SpyglassStubModel;
import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.client.TrinketRenderer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3f;

@Environment(EnvType.CLIENT)
public class SpyglassTrinketRenderer implements TrinketRenderer {
	private final SpyglassBeltModel spyglassBeltModel;
	private final SpyglassStubModel spyglassStubModel;

	public SpyglassTrinketRenderer() {
		this.spyglassBeltModel = new SpyglassBeltModel();
		this.spyglassStubModel = new SpyglassStubModel();
	}

	@Override
	public void render(ItemStack itemStack, SlotReference slotReference, EntityModel<? extends LivingEntity> entityModel,
			MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int light, LivingEntity entity,
			float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
		if (!(entityModel instanceof BipedEntityModel bipedEntityModel)) return;

		// spyglass rendering
		if (entity.getDataTracker().get(QuickSpyglasser.USING_SPYGLASS) == 0) {
			matrixStack.push();
			matrixStack.scale(0.75f, 0.75f, 0.75f);
			if (entity.isInSneakingPose()) {
				matrixStack.translate(0.0d, 0.2575d, 0.0d);
				matrixStack.multiply(Vec3f.POSITIVE_X.getRadialQuaternion(bipedEntityModel.body.pitch));
			}
			matrixStack.translate(-0.425f, 1.1f, -0.04f);
			this.spyglassStubModel.render(matrixStack, vertexConsumerProvider, light);
//			this.spyglassStubModel.render(matrixStack, vertexConsumerProvider.getBuffer(this.spyglassStubModel.getLayer(STUB_TEXTURE)), light,OverlayTexture.DEFAULT_UV, 1.0f, 1.0f, 1.0f, 1.0f);
			matrixStack.pop();
		}
		// belt rendering
		matrixStack.push();
		matrixStack.scale(1.01f, 1.01f, 1.01f);
		if (entity.isInSneakingPose()) {
			matrixStack.translate(0.0d, 0.195d, 0.0d);
			matrixStack.multiply(Vec3f.POSITIVE_X.getRadialQuaternion(bipedEntityModel.body.pitch));
		}
		matrixStack.translate(0.0d, 0.625d, 0.0d);
		this.spyglassBeltModel.render(matrixStack, vertexConsumerProvider, light);
//		this.spyglassBeltModel.render(matrixStack, vertexConsumerProvider.getBuffer(this.spyglassBeltModel.getLayer(BELT_TEXTURE)), light, OverlayTexture.DEFAULT_UV, 1.0f, 1.0f, 1.0f, 1.0f);
		matrixStack.pop();
	}
}

package com.gluton.quickspyglasser.render;

import net.minecraft.client.model.Model;
import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class SpyglassStubModel extends Model {
	private static final Identifier TEXTURE = new Identifier("textures/item/spyglass_model.png");

	private static final float TO_RADIANS = (float) Math.PI / 180.0f;

	private ModelPart spyglassStub;

	public SpyglassStubModel() {
		super(RenderLayer::getEntityCutout);
		this.spyglassStub = createData().createModel().getChild("spyglass_stub");
	}

	private TexturedModelData createData() {
		ModelData data = new ModelData();
		ModelPartBuilder stubBuilder = ModelPartBuilder.create();
		stubBuilder.cuboid("top", 0.0f, 0.0f, 0.0f, 2, 0, 2, -4, 13);
		stubBuilder.cuboid("side", 0.0f, -5.0f, 0.0f, 0, 5, 2, 0, 6);
		stubBuilder.cuboid("left", 0.0f, -5.0f, 2.0f, 2, 5, 0, -2, 8);
		stubBuilder.cuboid("right", 0.0f, -5.0f, 0.0f, 2, 5, 0, 0, 8);
		stubBuilder.cuboid("inside", 2.0f, -5.0f, 0.0f, 0, 5, 2, -2, 6);
		data.getRoot().addChild("spyglass_stub", stubBuilder, ModelTransform.of(-1.0f, -5.0f, 1.0f, 180.0f * TO_RADIANS, 0.0f, 0.0f));
		return TexturedModelData.of(data, 16, 16);
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
		this.spyglassStub.render(matrices, vertices, light, overlay, red, green, blue, alpha);
	}

	public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int light) {
		this.render(matrixStack, vertexConsumerProvider.getBuffer(this.getLayer(TEXTURE)), light, OverlayTexture.DEFAULT_UV, 1.0f, 1.0f, 1.0f, 1.0f);
	}
}

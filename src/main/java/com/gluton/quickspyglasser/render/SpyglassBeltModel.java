package com.gluton.quickspyglasser.render;

import com.gluton.quickspyglasser.QuickSpyglasser;
import com.gluton.quickspyglasser.QuickSpyglasserClient;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class SpyglassBeltModel extends Model {
	private static final Identifier TEXTURE = new Identifier(QuickSpyglasser.MOD_ID, "textures/spyglass_belt.png");

	private static final float TO_RADIANS = (float) Math.PI / 180.0f;

	private final ModelPart spyglassBelt;
	private final ModelPart spyglassBeltOpened;

	public SpyglassBeltModel() {
		super(RenderLayer::getEntityCutout);
		this.spyglassBelt = createData(false).createModel().getChild("spyglass_belt");
		this.spyglassBeltOpened = createData(true).createModel().getChild("spyglass_belt_opened");
	}

	private TexturedModelData createData(boolean opened) {
		ModelData data = new ModelData();
//		ModelPartBuilder stubBuilder = ModelPartBuilder.create();
//		stubBuilder.cuboid("top", 0.0f, 0.0f, 0.0f, 2, 0, 2, -4, 13);
//		stubBuilder.cuboid("side", 0.0f, -5.0f, 0.0f, 0, 5, 2, 0, 6);
//		stubBuilder.cuboid("left", 0.0f, -5.0f, 2.0f, 2, 5, 0, -2, 8);
//		stubBuilder.cuboid("right", 0.0f, -5.0f, 0.0f, 2, 5, 0, 0, 8);
//		stubBuilder.cuboid("inside", 2.0f, -5.0f, 0.0f, 0, 5, 2, -2, 6);
//		data.getRoot().addChild("spyglass_stub", stubBuilder, ModelTransform.of(-1.0f, -5.0f, 1.0f, 180.0f * TO_RADIANS, 0.0f, 0.0f));

		ModelPartBuilder beltBuilder = ModelPartBuilder.create();
		beltBuilder.cuboid("front", -4.0f, 0.0f, -1.595f, 8, 2, 0, 0, 0);
		beltBuilder.cuboid("back", -4.0f, 0.0f, 2.405f, 8, 2, 0, 0, 2);
		beltBuilder.cuboid("left", -4.0f, 0.0f, -1.595f, 0, 2, 4, 0, 0);
		beltBuilder.cuboid("right", 4.0f, 0.0f, -1.595f, 0, 2, 4, 8, 0);
		beltBuilder.cuboid("buckle", -1.0f, 0.5f, -1.645f, 2, 1, 0, 0, 6);
		ModelPartData belt = data.getRoot().addChild(opened ? "spyglass_belt_opened" : "spyglass_belt", beltBuilder, ModelTransform.pivot(0.0f, 0.0f, -0.405f));

		ModelPartBuilder holsterBuilder = ModelPartBuilder.create();
		holsterBuilder.cuboid("left", -6.1f, 0.0f, 1.0f, 2, 4, 0, 4, 6);
		holsterBuilder.cuboid("right", -6.1f, 0.0f, -1.0f, 2, 4, 0, 12, 6);
		holsterBuilder.cuboid("front", -6.1f, 0.0f, -1.0f, 0, 4, 2, 8, 4);
		holsterBuilder.cuboid("back", -4.1f, -1.0f, -1.0f, 0, 5, 2, 0, 5);
		holsterBuilder.cuboid("bottom", -6.1f, 4.0f, -1.0f, 2, 0, 2, 2, 10);
		ModelPartData holster = belt.addChild("holster", holsterBuilder, ModelTransform.pivot(0.0f, 0.0f, 0.0f));
		{
			buildHolsterFlap(holster, opened);

			ModelPartBuilder bandBuilder = ModelPartBuilder.create();
			bandBuilder.cuboid("front", -6.175f, 1.0f, -1.0f, 0, 1, 2, 6, 10);
			ModelPartData band = holster.addChild("band", bandBuilder, ModelTransform.pivot(0.0f, 0.0f, 0.0f));
			{
				ModelPartBuilder bandLeftBackBuilder = ModelPartBuilder.create();
				bandLeftBackBuilder.cuboid("cube", -0.5f, -0.5f, 0.0f, 1, 1, 0, 2, 12);
				band.addChild("left_back", bandLeftBackBuilder, ModelTransform.of(-4.675f, 1.5f, 1.025f, 0.0f, 0.0436f, 0.0f));

				ModelPartBuilder bandRightBackBuilder = ModelPartBuilder.create();
				bandRightBackBuilder.cuboid("cube", -0.5f, -0.5f, 0.0f, 1, 1, 0, 12, 12);
				band.addChild("right_back", bandRightBackBuilder, ModelTransform.of(-4.675f, 1.5f, -1.025f, 0.0f, -0.0436f, 0.0f));

				ModelPartBuilder bandLeftFrontBuilder = ModelPartBuilder.create();
				bandLeftFrontBuilder.cuboid("cube", -0.5f, -0.5f, 0.0f, 1, 1, 0, 4, 12);
				band.addChild("left_front", bandLeftFrontBuilder, ModelTransform.of(-5.675f, 1.5f, 1.025f, 0.0f, -0.048f, 0.0f));

				ModelPartBuilder bandRightFrontBuilder = ModelPartBuilder.create();
				bandRightFrontBuilder.cuboid("cube", -0.5f, -0.5f, 0.0f, 1, 1, 0, 10, 12);
				band.addChild("right_front", bandRightFrontBuilder, ModelTransform.of(-5.675f, 1.5f, -1.025f, 0.0f, 0.048f, 0.0f));
			}
		}
		return TexturedModelData.of(data, 16, 16);
	}

	private void buildHolsterFlap(ModelPartData holsterData, boolean opened) {
		ModelPartBuilder flapBuilder = ModelPartBuilder.create();
		flapBuilder.cuboid("top", -2.0F, 0.0F, -1.0F, 2, 0, 2, 6, 10);
		ModelPartData flap = holsterData.addChild(opened ? "flap_opened" : "flap", flapBuilder, ModelTransform.of(-4.1F, -1.0F, 0.0F, 0.0F, 0.0F, opened ? 0.9163F : 0.1309F));
		{
			ModelPartBuilder flapSideBuilder = ModelPartBuilder.create();
			ModelPartData flapSide = flap.addChild("side", flapSideBuilder, ModelTransform.of(-2.0F, 0.0F, 0.0F,  0.0F, 0.0F, opened ? 1.0952F : -0.1265F));
			{
				ModelPartBuilder flapSideSideBuilder = ModelPartBuilder.create();
				flapSideSideBuilder.cuboid("side", 0.0F, -1.0F, -1.0F, 0, 2, 2, 12, 8);
				flapSide.addChild("side", flapSideSideBuilder, ModelTransform.of(-0.0761F, 0.9989F, 0.0F, 0.0F, 0.0F, 0.0916F));

				ModelPartBuilder flapSideTongueBuilder = ModelPartBuilder.create();
				flapSideTongueBuilder.cuboid("tongue", 0.0F, -1.5F, -0.5F, 0, 3, 1, 0, 11);
				flapSide.addChild("tongue", flapSideTongueBuilder, ModelTransform.of(-0.0759F, 2.2613F, 0.0F, 0.0F, 0.0F, -0.0436F));
			}
		}
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
		getModel().render(matrices, vertices, light, overlay, red, green, blue, alpha);
	}

	public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int light) {
		this.render(matrixStack, vertexConsumerProvider.getBuffer(this.getLayer(TEXTURE)), light, OverlayTexture.DEFAULT_UV, 1.0f, 1.0f, 1.0f, 1.0f);
	}

	private ModelPart getModel() {
		return QuickSpyglasserClient.getInstance().isUsingSpyglass() ? this.spyglassBeltOpened : this.spyglassBelt;
	}
}

package com.ferreusveritas.tapestry.client.renderer;

import java.util.HashMap;
import java.util.Map;

import com.ferreusveritas.tapestry.ModBlocks;
import com.ferreusveritas.tapestry.ModConstants;
import com.ferreusveritas.tapestry.block.BlockTapestry;
import com.ferreusveritas.tapestry.client.model.ModelTapestry;
import com.ferreusveritas.tapestry.tileentity.TapestryShape;
import com.ferreusveritas.tapestry.tileentity.TileEntityTapestry;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class TileEntityTapestryRenderer extends TileEntitySpecialRenderer<TileEntityTapestry> {

	public static Map<Integer, ModelTapestry> map = new HashMap<>();

	protected static ResourceLocation[] clothResources = new ResourceLocation[16];

	static {
		for(EnumDyeColor color : EnumDyeColor.values()) {
			clothResources[color.getMetadata()] = new ResourceLocation(ModConstants.MODID, "textures/models/banner_cloth_" + color.getName() + ".png");
		}
	}

	@Override
	public void render(TileEntityTapestry te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		//super.render(te, x, y, z, partialTicks, destroyStage, alpha);

		int length = te.getLength();
		int width = te.getWidth();
		TapestryShape shape = te.getShape();

		float rotation = 0.0f;
		float wallTranslate = 0.0f;

		if(te.hasWorld()) {
			IBlockState state = te.getWorld().getBlockState(te.getPos());
			if(state.getBlock() == ModBlocks.blockTapestry) { //Sometimes if we are far far away the block is air since the chunk is unloaded
				EnumFacing facing = state.getValue(BlockTapestry.FACING);
				switch(facing) {
					case NORTH: rotation = 0.0f; break;
					case EAST: rotation = 90.0f; break;
					case SOUTH: rotation = 180.0f; break;
					case WEST: rotation = 270.0f; break;
					default: break;
				}

				wallTranslate = state.getValue(BlockTapestry.WALL) ? 7 / 16f : 0.0f;
			}
		}

		ModelTapestry tapestryModel = getModel(length, width, shape);

		GlStateManager.pushMatrix();
		GlStateManager.translate((float)x + 0.5F, (float)y + 1.0f, (float)z + 0.5F);

		GlStateManager.disableCull();//Somebody out there is misbehaving by calling GL11 states directly from overridden skull renderer code.  So let's fix the state here.
		GlStateManager.enableCull();

		//Turn model upside down
		GlStateManager.rotate(180.0f, 0.0f, 0.0f, 1.0f);

		GlStateManager.rotate(rotation, 0.0f, 1.0f, 0.0f);

		GlStateManager.translate(0.0f, 0.0f, wallTranslate);

		GlStateManager.enableRescaleNormal();

		EnumDyeColor bgColor = te.getBgColor();
		ResourceLocation resourcelocation = clothResources[bgColor.getMetadata()];
		bindTexture(resourcelocation);

		GlStateManager.disableBlend();

		tapestryModel.renderTapestry();

		EnumDyeColor fgColor = te.getFgColor();
		int color = fgColor.getColorValue();
		GlStateManager.color((color >> 16 & 0xff) / 255.0f, (color >> 8 & 0xff) / 255.0f, (color >> 0 & 0xff) / 255.0f);

		resourcelocation = new ResourceLocation(ModConstants.MODID, "textures/models/banner_trim.png");

		GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
		GlStateManager.enableBlend();

		if(resourcelocation != null) {
			bindTexture(resourcelocation);
			tapestryModel.renderTrim();
		}

		resourcelocation = new ResourceLocation(ModConstants.MODID, "textures/models/banner_symbol.png");

		if(resourcelocation != null) {
			bindTexture(resourcelocation);
			tapestryModel.renderSymbol();
		}

		GlStateManager.disableBlend();

		GlStateManager.popMatrix();
	}

	public static ModelTapestry getModel(int length, int width, TapestryShape shape) {
		return map.computeIfAbsent(calcTapestryKey(length, width, shape), i -> new ModelTapestry(length, width, shape));
		//return new ModelTapestry(length, width, shape);
	}

	public static int calcTapestryKey(int length, int width, TapestryShape shape) {
		return length << 6 | width << 4 | shape.ordinal();
	}

	@Override
	public boolean isGlobalRenderer(TileEntityTapestry te) {
		return true;
	}

}

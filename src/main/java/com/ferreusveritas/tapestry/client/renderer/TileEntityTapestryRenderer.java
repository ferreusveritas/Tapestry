package com.ferreusveritas.tapestry.client.renderer;

import java.util.HashMap;
import java.util.Map;

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
		
		ModelTapestry tapestryModel = getModel(length, width, shape);
		
		GlStateManager.pushMatrix();
		GlStateManager.translate((float)x + 0.5F, (float)y + 1.0f, (float)z + 0.5F);

		//Turn model upside down
		GlStateManager.rotate(180.0f, 0.0f, 0.0f, 1.0f);
		
		GlStateManager.rotate(rotation, 0.0f, 1.0f, 0.0f);
		
		GlStateManager.translate(0.0f, 0.0f, wallTranslate);
		
		GlStateManager.enableRescaleNormal();
		
		setLightmapDisabled(true);
		
		GlStateManager.disableLighting();
		
		EnumDyeColor bgColor = te.getBgColor();
		ResourceLocation resourcelocation = clothResources[bgColor.getMetadata()];
		bindTexture(resourcelocation);
		tapestryModel.renderTapestry();

		EnumDyeColor fgColor = te.getFgColor();
		int color = fgColor.getColorValue();
		GlStateManager.color((color >> 16 & 0xff) / 255.0f, (color >> 8 & 0xff) / 255.0f, (color >> 0 & 0xff) / 255.0f);
		
		resourcelocation = new ResourceLocation(ModConstants.MODID, "textures/models/banner_trim.png");
		
		if(resourcelocation != null) {
			GlStateManager.enableBlend();
			
			bindTexture(resourcelocation);
			tapestryModel.renderTrim();
			
			GlStateManager.disableBlend();
		}

		resourcelocation = new ResourceLocation(ModConstants.MODID, "textures/models/banner_symbol.png");
		
		if(resourcelocation != null) {
			GlStateManager.enableBlend();
			
			bindTexture(resourcelocation);
			tapestryModel.renderSymbol();
			
			GlStateManager.disableBlend();
		}
		
		setLightmapDisabled(false);

		GlStateManager.popMatrix();
	}

	public static ModelTapestry getModel(int length, int width, TapestryShape shape) {
		return map.computeIfAbsent(calcTapestryKey(length, width, shape), i -> new ModelTapestry(length, width, shape));
		//return new ModelTapestry(length, width, shape);
	}

	public static int calcTapestryKey(int length, int width, TapestryShape shape) {
		return length << 6 | width << 4 | shape.ordinal();
	}

}

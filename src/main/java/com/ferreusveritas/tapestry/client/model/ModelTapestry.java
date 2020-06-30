package com.ferreusveritas.tapestry.client.model;

import com.ferreusveritas.tapestry.tileentity.TapestryShape;
import com.ferreusveritas.tapestry.tileentity.TileEntityTapestry;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.MathHelper;

public class ModelTapestry extends ModelBase {

	public ModelRenderer bannerSlate;
	public ModelRenderer bannerTrim;
	public ModelRenderer bannerSymbol;
	
	public ModelTapestry(int len, int width, TapestryShape shape) {
		textureWidth = 64;
		textureHeight = 32;
		bannerSlate = new ModelRenderer(this);
		
		len = MathHelper.clamp(len, TileEntityTapestry.MIN_LENGTH, TileEntityTapestry.MAX_LENGTH);
		
		//TapestryGrids.rebuild();
		
		TapestryGrid grid = TapestryGrids.getStyle(width, shape);
		int gridWidth = grid.getWidth();

		//Build the fabric loopy thing at the top of the banner that is used to hang it from a bar
		for(int x = 0; x < gridWidth; x++) {
			int ObjOffX = ((gridWidth * -16) / 2) + (x * 16);
			bannerSlate.cubeList.add( new ModelBoxFace(bannerSlate, 48,  4, ObjOffX, 7.0f, -1.0f, 16, 2, 0, 0.0f).setFace(EnumFacing.NORTH).scale(1.0f, 1.0625f, 1.0625f).offset(0.0f, -0.5f, 0.0f) );
			bannerSlate.cubeList.add( new ModelBoxFace(bannerSlate, 48,  4, ObjOffX, 7.0f,  1.0f, 16, 2, 0, 0.0f).setFace(EnumFacing.NORTH).scale(1.0f, 1.0625f, 1.0625f).offset(0.0f, -0.5f, 0.0f) );
			bannerSlate.cubeList.add( new ModelBoxFace(bannerSlate, 32,  8, ObjOffX, 7.0f,  1.0f, 16, 2, 0, 0.0f).setFace(EnumFacing.SOUTH).scale(1.0f, 1.0625f, 1.0625f).offset(0.0f, -0.5f, 0.0f) );
			bannerSlate.cubeList.add( new ModelBoxFace(bannerSlate, 32,  8, ObjOffX, 7.0f, -1.0f, 16, 2, 0, 0.0f).setFace(EnumFacing.SOUTH).scale(1.0f, 1.0625f, 1.0625f).offset(0.0f, -0.5f, 0.0f) );
			bannerSlate.cubeList.add( new ModelBoxFace(bannerSlate, 46,  6, ObjOffX, 7.0f, -1.0f, 16, 0, 2, 0.0f).setFace(EnumFacing.DOWN ).scale(1.0f, 1.0625f, 1.0625f).offset(0.0f, -0.5f, 0.0f) );
			bannerSlate.cubeList.add( new ModelBoxFace(bannerSlate, 46,  6, ObjOffX, 9.0f, -1.0f, 16, 0, 2, 0.0f).setFace(EnumFacing.DOWN ).scale(1.0f, 1.0625f, 1.0625f).offset(0.0f, -0.5f, 0.0f) );
			bannerSlate.cubeList.add( new ModelBoxFace(bannerSlate, 30, 10, ObjOffX, 9.0f, -1.0f, 16, 0, 2, 0.0f).setFace(EnumFacing.UP   ).scale(1.0f, 1.0625f, 1.0625f).offset(0.0f, -0.5f, 0.0f) );
			bannerSlate.cubeList.add( new ModelBoxFace(bannerSlate, 30, 10, ObjOffX, 7.0f, -1.0f, 16, 0, 2, 0.0f).setFace(EnumFacing.UP   ).scale(1.0f, 1.0625f, 1.0625f).offset(0.0f, -0.5f, 0.0f) );
		}
		
		//Build the base fabric surface of the banner
		for(int y = 0; y < len; y++) {
			for(int x = 0; x < gridWidth; x++) {
				int piece = grid.getPiece(x, y, len);
				int texOffX = (piece & 3) << 4;
				int texOffY = (piece & 4) << 2;
				int ObjOffX = ((gridWidth * -16) / 2) + (x * 16);
				int ObjOffY = y * 16;
				bannerSlate.cubeList.add( new ModelBoxFace(bannerSlate, texOffX, texOffY, ObjOffX, ObjOffY, 0.0f, 16, 16, 0, 0.0F).setFace(EnumFacing.NORTH) );
				bannerSlate.cubeList.add( new ModelBoxFace(bannerSlate, texOffX - 16, texOffY, -16 - ObjOffX, ObjOffY, 0.0f, 16, 16, 0, 0.0F).setFace(EnumFacing.SOUTH) );
			}
		}

		//The trim pieces use a different texture and thus must employ a separate renderer.
		bannerTrim = new ModelRenderer(this);

		int vlen = len * 2;//Trim pieces use twice the geometry

		grid = TapestryGrids.getTrim(width, shape);
		gridWidth = grid.getWidth();
		
		for(int y = 0; y < vlen; y++) {
			for(int x = 0; x < gridWidth; x++) {
				int piece = grid.getPiece(x, y, vlen);
				if(piece > 0) {
					int texOffX = (piece & 7) << 3;
					int texOffY = (piece & 24);
					int ObjOffX = ((gridWidth * -8) / 2) + (x * 8);
					int ObjOffY = y * 8;
					bannerTrim.cubeList.add( new ModelBoxFace(bannerTrim, texOffX, texOffY, ObjOffX, ObjOffY, -0.0625f, 8, 8, 0, 0.0F).setFace(EnumFacing.NORTH) );
					bannerTrim.cubeList.add( new ModelBoxFace(bannerTrim, texOffX - 8, texOffY, -8 - ObjOffX, ObjOffY, 0.0625f, 8, 8, 0, 0.0F).setFace(EnumFacing.SOUTH) );
				}
			}
		}
		
		//The symbol is yet another texture
		textureWidth = 64;
		textureHeight = 64;
		
		bannerSymbol = new ModelRenderer(this);
		TapestrySymbolModel symbol = TapestrySymbolModel.getForWidth(width);
		symbol.buildModel(bannerSymbol, len, shape);
		
	}

	public void renderTapestry() {
		bannerSlate.rotationPointY = 0.0F;
		bannerSlate.render(0.0625F);//Scale by 1/16
	}

	public void renderTrim() {
		bannerTrim.rotationPointY = 0.0F;
		bannerTrim.render(0.0625F);//Scale by 1/16
	}
		
	public void renderSymbol() {
		bannerSymbol.rotationPointY = 0.0F;
		bannerSymbol.render(0.0625F);//Scale by 1/16
	}
}

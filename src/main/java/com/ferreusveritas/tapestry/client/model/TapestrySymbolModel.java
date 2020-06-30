package com.ferreusveritas.tapestry.client.model;

import com.ferreusveritas.tapestry.tileentity.TapestryShape;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.MathHelper;

public class TapestrySymbolModel {

	private static TapestrySymbolModel symbol1 = new TapestrySymbolModel(10,  28, 36, 36) {
		@Override
		protected int getBottomOff(TapestryShape shape) {
			switch(shape) {
				default:
				case Rectangular: return 3;
				case Pointed: return 6;
				case SwallowTail: return 19;
			}
		}
		
		@Override
		public void buildModel(ModelRenderer modelRenderer, int len, TapestryShape shape) {
			int topOff = 14;
			int bottomOff = getBottomOff(shape);
			int ObjOffX = -texW / 2;
			int realLen = (len * 16) - topOff - bottomOff;

			int sections = realLen / texH;
			int ObjOffY = topOff + (realLen - (texH * sections)) / 2;
			
			if(sections == 1) {
				modelRenderer.cubeList.add( new ModelBoxFace(modelRenderer, texOffX,        texOffY, ObjOffX, ObjOffY,-0.0625f, texW, texH, 0, 0.0F).setFace(EnumFacing.NORTH) );
				modelRenderer.cubeList.add( new ModelBoxFace(modelRenderer, texOffX - texW, texOffY, ObjOffX, ObjOffY, 0.0625f, texW, texH, 0, 0.0F).setFace(EnumFacing.SOUTH) );
			}
			else {
				int offY = ObjOffY;
				modelRenderer.cubeList.add( new ModelBoxFace(modelRenderer, texOffX,        texOffY, ObjOffX, offY, -0.0625f, texW, texH / 2, 0, 0.0F).setFace(EnumFacing.NORTH) );
				modelRenderer.cubeList.add( new ModelBoxFace(modelRenderer, texOffX - texW, texOffY, ObjOffX, offY,  0.0625f, texW, texH / 2, 0, 0.0F).setFace(EnumFacing.SOUTH) );
				offY += texH / 2;
				for(int i = 0; i < sections - 1; i++) {
					modelRenderer.cubeList.add( new ModelBoxFace(modelRenderer, texOffX + texW, texOffY,         ObjOffX, offY,-0.0625f, texW, texH, 0, 0.0F).setFace(EnumFacing.NORTH) );
					modelRenderer.cubeList.add( new ModelBoxFace(modelRenderer, texOffX,        texOffY, -texW - ObjOffX, offY, 0.0625f, texW, texH, 0, 0.0F).setFace(EnumFacing.SOUTH) );
					offY += texH;
				}
				modelRenderer.cubeList.add( new ModelBoxFace(modelRenderer, texOffX,        texOffY + texH / 2, ObjOffX, offY,-0.0625f, texW, texH / 2, 0, 0.0F).setFace(EnumFacing.NORTH) );
				modelRenderer.cubeList.add( new ModelBoxFace(modelRenderer, texOffX - texW, texOffY + texH / 2, ObjOffX, offY, 0.0625f, texW, texH / 2, 0, 0.0F).setFace(EnumFacing.SOUTH) );
			}
		}
		
	};
	private static TapestrySymbolModel symbol2 = new TapestrySymbolModel(26,  36, 36, 0) {
		@Override
		protected int getBottomOff(TapestryShape shape) {
			switch(shape) {
				default:
				case Rectangular: return 3;
				case Pointed: return 14;
				case SwallowTail: return 15;
			}
		}
	};
	
	private static TapestrySymbolModel symbol3 = new TapestrySymbolModel(36,  48,  0,  0) {
		@Override
		protected int getBottomOff(TapestryShape shape) {
			switch(shape) {
				default:
				case Rectangular: return 3;
				case Pointed: return 14;
				case SwallowTail: return 19;
			}
		}
	};
	
	private static TapestrySymbolModel[] symbols = { symbol1, symbol2, symbol3 };
	
	public static TapestrySymbolModel getForWidth(int width) {
		return symbols[MathHelper.clamp(width, 1, 3) - 1];
	}
	
	protected int texW;
	protected int texH;
	protected int texOffX;
	protected int texOffY;
	
	public TapestrySymbolModel(int texW, int texH, int texOffX, int texOffY) {
		this.texW = texW;
		this.texH = texH;
		this.texOffX = texOffX;
		this.texOffY = texOffY;
	}
	
	public void buildModel(ModelRenderer modelRenderer, int len, TapestryShape shape) {
		int topOff = 14;
		int bottomOff = getBottomOff(shape);
		int ObjOffX = -texW / 2;
		int realLen = (len * 16) - topOff - bottomOff;

		int ObjOffY = topOff + (realLen - texH) / 2;
		
		modelRenderer.cubeList.add( new ModelBoxFace(modelRenderer, texOffX, texOffY, ObjOffX, ObjOffY, -0.0625f, texW, texH, 0, 0.0F).setFace(EnumFacing.NORTH) );
		modelRenderer.cubeList.add( new ModelBoxFace(modelRenderer, texOffX - texW, texOffY, -texW - ObjOffX, ObjOffY, 0.0625f, texW, texH, 0, 0.0F).setFace(EnumFacing.SOUTH) );
	}
	
	protected int getBottomOff(TapestryShape shape) {
		return 0;
	}
	
}

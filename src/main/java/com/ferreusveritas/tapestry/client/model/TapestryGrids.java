package com.ferreusveritas.tapestry.client.model;

import com.ferreusveritas.tapestry.tileentity.TapestryShape;
import com.ferreusveritas.tapestry.tileentity.TileEntityTapestry;

import net.minecraft.util.math.MathHelper;

public class TapestryGrids {

	private final static TapestryGrid[][] styles;
	private final static TapestryGrid[][] trim;
	
	static {

		TapestryGrid style1r = new TapestryGrid( new int[][]{ {1}, {0}, {0} }, 1);
		TapestryGrid style1p = new TapestryGrid( new int[][]{ {1}, {0}, {2} }, 1);
		TapestryGrid style1s = new TapestryGrid( new int[][]{ {1}, {0}, {5} }, 1);

		TapestryGrid style2r = new TapestryGrid( new int[][]{ {1, 1}, {0, 0}, {0, 0} }, 1);
		TapestryGrid style2p = new TapestryGrid( new int[][]{ {1, 1}, {0, 0}, {6, 4} }, 1);
		TapestryGrid style2s = new TapestryGrid( new int[][]{ {1, 1}, {0, 0}, {4, 6} }, 1);

		TapestryGrid style3r = new TapestryGrid( new int[][]{ {1, 1, 1}, {0, 0, 0}, {0, 0, 0} }, 1);
		TapestryGrid style3p = new TapestryGrid( new int[][]{ {1, 1, 1}, {0, 0, 0}, {6, 2, 4} }, 1);
		TapestryGrid style3s = new TapestryGrid( new int[][]{ {1, 1, 1}, {0, 0, 0}, {4, 5, 6} }, 1);

		TapestryGrid[] style1 = {style1r, style1p, style1s};
		TapestryGrid[] style2 = {style2r, style2p, style2s};
		TapestryGrid[] style3 = {style3r, style3p, style3s};

		styles = new TapestryGrid[][]{ style1, style2, style3 };
		
		TapestryGrid trim1r = new TapestryGrid( new int[][] {
			{  0,  0 },
			{  1,  3 },
			{ 22, 23 },
			{ 9,  11 },
		}, 1);
		
		TapestryGrid trim1p = new TapestryGrid( new int[][] {
			{  0,  0 },
			{  1,  3 },
			{ 22, 23 },
			{ 30, 31 }
		}, 1);
		
		TapestryGrid trim1s = new TapestryGrid( new int[][] {
			{  0,  0 },
			{  1,  3 },
			{ 22, 23 },
			{ 18, 19 },
			{ 26, 27 },
			{  0,  0 }
		}, 3);
				
		TapestryGrid trim2r = new TapestryGrid( new int[][] {
			{  0,  0,  0,  0 },
			{  1,  2,  2,  3 },
			{ 22,  0,  0, 23 },
			{ 9,  10, 10, 11 },
		}, 1);
				
		TapestryGrid trim2p = new TapestryGrid( new int[][] {
			{  0,  0,  0,  0 },
			{  1,  2,  2,  3 },
			{ 22,  0,  0, 23 },
			{ 30, 20, 21, 31 },
			{  0, 28, 29,  0 },
		}, 2);
		
		TapestryGrid trim2s = new TapestryGrid( new int[][] {
			{  0,  0,  0,  0 },
			{  1,  2,  2,  3 },
			{ 22,  0,  0, 23 },
			{ 18, 17, 16, 19 },
			{ 26,  0,  0, 27 },
		}, 2);
		
		TapestryGrid trim3r = new TapestryGrid( new int[][] {
			{  0,  0,  0,  0,  0,  0 },
			{  1,  2,  2,  2,  2,  3 },
			{ 22,  0,  0,  0,  0, 23 },
			{ 9,  10, 10, 10, 10, 11 },
		}, 1);
		
		TapestryGrid trim3p = new TapestryGrid( new int[][] {
			{  0,  0,  0,  0,  0,  0 },
			{  1,  2,  2,  2,  2,  3 },
			{ 22,  0,  0,  0,  0, 23 },
			{ 30, 20,  0,  0, 21, 31 },
			{  0, 28, 16, 17, 29,  0 },
		}, 2);
		
		TapestryGrid trim3s = new TapestryGrid( new int[][] {
			{  0,  0,  0,  0,  0,  0 },
			{  1,  2,  2,  2,  2,  3 },
			{ 22,  0,  0,  0,  0, 23 },
			{ 22,  0, 21, 20,  0, 23 },
			{ 18, 17, 29, 28, 16, 19 },
			{ 26,  0,  0,  0,  0, 27 },
		}, 3);
			
		TapestryGrid[] trim1 = {trim1r, trim1p, trim1s};
		TapestryGrid[] trim2 = {trim2r, trim2p, trim2s};
		TapestryGrid[] trim3 = {trim3r, trim3p, trim3s};
		
		trim = new TapestryGrid[][] { trim1, trim2, trim3 };
		
	}
	
	public static TapestryGrid getStyle(int width, TapestryShape shape) {
		return styles[MathHelper.clamp(width, TileEntityTapestry.MIN_WIDTH, TileEntityTapestry.MAX_WIDTH) - 1][MathHelper.clamp(shape.ordinal(), 0, TapestryShape.values().length - 1)];
	}
	
	public static TapestryGrid getTrim(int width, TapestryShape shape) {
		return trim[MathHelper.clamp(width, TileEntityTapestry.MIN_WIDTH, TileEntityTapestry.MAX_WIDTH) - 1][MathHelper.clamp(shape.ordinal(), 0, TapestryShape.values().length - 1)];
	}
	
	/*public static void rebuild() {
		trim[0][2] = new TapestryGrid( new int[][] {
			{  0,  0 },
			{  1,  3 },
			{ 22, 23 },
			{ 18, 19 },
			{ 26, 27 },
			{  0,  0 }
		}, 3);
	}*/
	
}

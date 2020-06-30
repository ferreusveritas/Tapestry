package com.ferreusveritas.tapestry.client.model;

import net.minecraft.util.math.MathHelper;

public class TapestryGrid {

	private int[][] data;
	private int topLen;
	private int bottomLen;
	private int height;
	private int width;
	
	public TapestryGrid(int[][] gridData, int bottomLen) {
		this.data = gridData;
		this.bottomLen = bottomLen;
		this.height = gridData.length;
		this.width = gridData[0].length;
		this.topLen = height - bottomLen;
	}
	
	public int getPiece(int x, int y, int len) {
		int virtTopLen = len - bottomLen;
		
		if(y < virtTopLen) {
			y = MathHelper.clamp(y, 0, topLen - 1);
		} else {
			y = topLen + (y - virtTopLen);
		}
		
		return data[y][x];
	}
	
	public int getWidth() {
		return width;
	}
	
}

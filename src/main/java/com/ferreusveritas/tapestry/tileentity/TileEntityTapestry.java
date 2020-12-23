package com.ferreusveritas.tapestry.tileentity;

import net.minecraft.item.EnumDyeColor;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.common.util.Constants.NBT;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityTapestry extends TileEntity {

	public static final String NBT_TAPESTRY_LENGTH = "length";
	public static final String NBT_TAPESTRY_WIDTH = "width";
	public static final String NBT_TAPESTRY_SHAPE = "shape";
	public static final String NBT_TAPESTRY_BGCOLOR = "bgcolor";
	public static final String NBT_TAPESTRY_FGCOLOR = "fgcolor";
	
	public static final int MIN_WIDTH = 1;
	public static final int MAX_WIDTH = 3;

	public static final int MIN_LENGTH = 4;
	public static final int MAX_LENGTH = 12;
	
	protected int length;
	protected int width;
	protected TapestryShape shape;
	protected EnumDyeColor bgColor;
	protected EnumDyeColor fgColor;

	public TileEntityTapestry() {
		length = 5;
		width = 1;
		shape = TapestryShape.SwallowTail;
		bgColor = EnumDyeColor.BLUE;
		fgColor = EnumDyeColor.YELLOW;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		
		float midX = pos.getX() + 0.5f;
		float midY = pos.getY() + 0.5f;
		float midZ = pos.getZ() + 0.5f;
		
		//return INFINITE_EXTENT_AABB;
		
		return new AxisAlignedBB(
				midX - (width / 2.0f),
				midY - length,
				midZ - 0.5f,
				midX + (width / 2.0f),
				midY + 0.5f,
				midZ + 0.5f);
	}
	
    @SideOnly(Side.CLIENT)
	@Override
	public double getMaxRenderDistanceSquared() {
		//return super.getMaxRenderDistanceSquared();
		return 65536;//256 Blocks or 16 chunks
	}
    
	public int getLength() {
		return length;
	}
	
	public void setLength(int length) {
		if(this.length != length) {
			this.length = MathHelper.clamp(length, MIN_LENGTH, MAX_LENGTH);
			markDirty();
		}
	}
	
	public int getWidth() {
		return width;
	}
	
	public void setWidth(int width) {
		if(this.width != width) {
			this.width = MathHelper.clamp(width, MIN_WIDTH, MAX_WIDTH);
			markDirty();
			if(world != null) {
				world.markBlockRangeForRenderUpdate(getPos(), getPos());
			}
		}
	}

	public TapestryShape getShape() {
		return shape;
	}
	
	public void setShape(TapestryShape shape) {
		if(this.shape != shape) {
			this.shape = shape;
			markDirty();
		}
	}

	public EnumDyeColor getBgColor() {
		return bgColor;
	}

	public void setBgColor(EnumDyeColor color) {
		if(this.bgColor != color) {
			bgColor = color;
			markDirty();
		}
	}
	
	public EnumDyeColor getFgColor() {
		return fgColor;
	}

	public void setFgColor(EnumDyeColor color) {
		if(this.fgColor != color) {
			fgColor = color;
			markDirty();
		}
	}
	
	public void readSyncableDataFromNBT(NBTTagCompound pNbt) {
		if(pNbt.hasKey(NBT_TAPESTRY_LENGTH, NBT.TAG_BYTE)) {
			setLength(pNbt.getByte(NBT_TAPESTRY_LENGTH));
		}
		if(pNbt.hasKey(NBT_TAPESTRY_WIDTH, NBT.TAG_BYTE)) {
			setWidth(pNbt.getByte(NBT_TAPESTRY_WIDTH));
		}
		if(pNbt.hasKey(NBT_TAPESTRY_SHAPE, NBT.TAG_BYTE)) {
			setShape(TapestryShape.fromId(pNbt.getByte(NBT_TAPESTRY_SHAPE)));
		}
		if(pNbt.hasKey(NBT_TAPESTRY_BGCOLOR, NBT.TAG_BYTE)) {
			setBgColor(EnumDyeColor.byMetadata(pNbt.getByte(NBT_TAPESTRY_BGCOLOR)));
		}
		if(pNbt.hasKey(NBT_TAPESTRY_FGCOLOR, NBT.TAG_BYTE)) {
			setFgColor(EnumDyeColor.byMetadata(pNbt.getByte(NBT_TAPESTRY_FGCOLOR)));
		}
	}
	
	public NBTTagCompound writeSyncableDataToNBT(NBTTagCompound pNbt) {
		pNbt.setByte(NBT_TAPESTRY_LENGTH, (byte) getLength());
		pNbt.setByte(NBT_TAPESTRY_WIDTH, (byte) getWidth());
		pNbt.setByte(NBT_TAPESTRY_SHAPE, (byte) getShape().ordinal());
		pNbt.setByte(NBT_TAPESTRY_BGCOLOR, (byte) getBgColor().getMetadata());
		pNbt.setByte(NBT_TAPESTRY_FGCOLOR, (byte) getFgColor().getMetadata());
		return pNbt;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound pNbt) {
		super.readFromNBT(pNbt);
		readSyncableDataFromNBT(pNbt);
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		writeSyncableDataToNBT(nbt);
		return nbt;
	}
	
	@Override
	public NBTTagCompound getUpdateTag() {
		return writeSyncableDataToNBT(super.getUpdateTag());
	}
	
	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		return new SPacketUpdateTileEntity(pos, 0, writeToNBT(new NBTTagCompound()));
	}
	
	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		readSyncableDataFromNBT(pkt.getNbtCompound());
	}
	
}

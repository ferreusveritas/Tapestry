package com.ferreusveritas.tapestry.block;

import java.util.List;

import javax.annotation.Nullable;

import com.ferreusveritas.tapestry.ModConstants;
import com.ferreusveritas.tapestry.tileentity.TapestryShape;
import com.ferreusveritas.tapestry.tileentity.TileEntityTapestry;
import com.ferreusveritas.tapestry.util.OreColor;

import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemShears;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants.NBT;

public class BlockTapestry extends Block implements ITileEntityProvider {

	public static final String Name = "tapestry";

	public static final String BlockEntityTag = "BlockEntityTag";
	
	public static final PropertyDirection FACING = BlockHorizontal.FACING;
	public static final PropertyInteger WIDTH = PropertyInteger.create("width", 1, 3);
	public static final PropertyBool WALL = PropertyBool.create("wall");

	//order nswe
	public static AxisAlignedBB[] wallsBB = { 
			new AxisAlignedBB(0, 6 / 16d, 13 / 16d, 1, 10 / 16d, 17 / 16d), //1n
			new AxisAlignedBB(0, 6 / 16d, -1 / 16d, 1, 10 / 16d, 3 / 16d ), //1s
			new AxisAlignedBB(13 / 16d, 6 / 16d, 0, 17 / 16d, 10 / 16d, 1), //1w
			new AxisAlignedBB(-1 / 16d, 6 / 16d, 0, 3 / 16d, 10 / 16d, 1 ), //1e
			new AxisAlignedBB(-0.5, 6 / 16d, 13 / 16d, 1.5, 10 / 16d, 17 / 16d),//2n 
			new AxisAlignedBB(-0.5, 6 / 16d, -1 / 16d, 1.5, 10 / 16d, 3 / 16d ),//2s 
			new AxisAlignedBB(13 / 16d, 6 / 16d, -0.5, 17 / 16d, 10 / 16d, 1.5),//2w 
			new AxisAlignedBB(-1 / 16d, 6 / 16d, -0.5, 3 / 16d, 10 / 16d, 1.5 ),//2e 
			new AxisAlignedBB(-1, 6 / 16d, 13 / 16d, 2, 10 / 16d, 17 / 16d),//3n
			new AxisAlignedBB(-1, 6 / 16d, -1 / 16d, 2, 10 / 16d, 3 / 16d ),//3s
			new AxisAlignedBB(13 / 16d, 6 / 16d, -1, 17 / 16d, 10 / 16d, 2),//3w
			new AxisAlignedBB(-1 / 16d, 6 / 16d, -1, 3 / 16d, 10 / 16d, 2 ) //3e
	};

	//order xy
	public static AxisAlignedBB[] barsBB = {
			new AxisAlignedBB(6 / 16d, 6 / 16d, 0, 10 / 16d, 10 / 16d, 1), //1x
			new AxisAlignedBB(0, 6 / 16d, 6 / 16d, 1, 10 / 16d, 10 / 16d), //1y
			new AxisAlignedBB(6 / 16d, 6 / 16d, -0.5, 10 / 16d, 10 / 16d, 1.5), //2x 
			new AxisAlignedBB(-0.5, 6 / 16d, 6 / 16d, 1.5, 10 / 16d, 10 / 16d), //2y
			new AxisAlignedBB(6 / 16d, 6 / 16d, -1, 10 / 16d, 10 / 16d, 2),  //3x
			new AxisAlignedBB(-1, 6 / 16d, 6 / 16d, 2.0, 10 / 16d, 10 / 16d) //3y
	};

	public BlockTapestry() {
		super(Material.CLOTH);
		setRegistryName(new ResourceLocation(ModConstants.MODID, Name));
		setUnlocalizedName(Name);
		setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
		setHardness(1.5f);
		setResistance(3.0f);
		setCreativeTab(CreativeTabs.DECORATIONS);
		hasTileEntity = true;
		fullBlock = false;
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { FACING, WIDTH, WALL });
	}

	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos) {
		TileEntity te = world.getTileEntity(pos);
		if(te instanceof TileEntityTapestry) {
			TileEntityTapestry tet = (TileEntityTapestry) te;
			state = state.withProperty(WIDTH, tet.getWidth());
		}

		return state;
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(meta & 3)).withProperty(WALL, (meta & 4) != 0);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return ((EnumFacing)state.getValue(FACING)).getHorizontalIndex() + (state.getValue(WALL) ? 4 : 0);
	}

	public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
		EnumFacing enumfacing = placer.getHorizontalFacing().getOpposite();

		return getDefaultState().withProperty(FACING, enumfacing).withProperty(WALL, facing.getAxis() != Axis.Y);
	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) { }
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {

		TileEntity te = worldIn.getTileEntity(pos);

		if(te instanceof TileEntityTapestry) {
			TileEntityTapestry tet = (TileEntityTapestry) te;
			int length = tet.getLength();
			int width = tet.getWidth();
			ItemStack stack = playerIn.getHeldItem(hand);

			//Attempt to color the banner
			EnumDyeColor color = OreColor.getDyeColor(stack);
			if(color != null) {
				int dyeOwned = stack.getCount();
				int dyeUsed = 0;

				if(facing.getAxis() != Axis.Y) { //When the player clicks on the front face of the tapestry rod
					if(tet.getFgColor() != color) {
						tet.setFgColor(color);
						dyeUsed = 1;
					}
				} else if(facing == EnumFacing.DOWN) { //When the player clicks on the bottom of the tapestry rod
					int dyeNeeded = length * width / 2;
					if(tet.getBgColor() != color && dyeOwned >= dyeNeeded) {
						tet.setBgColor(color);
						dyeUsed = dyeNeeded;
					}
				}

				if(dyeUsed > 0) {
					if(!playerIn.isCreative()) { //Don't consume/damage items or produce drops in creative mode
						stack.shrink(dyeUsed); //consume the amount of dye used for the operation
					}
					return true;
				}

			}

			//Attempt to extend the banner dimensions
			if(stack.getItem() == ItemBlock.getItemFromBlock(Blocks.CARPET) && stack.getItemDamage() == tet.getBgColor().getMetadata()) { //Ensure it's carpet and the color matches
				int carpetsOwned = stack.getCount();
				int carpetsUsed = 0;

				//Attempt to extend the banner length
				if(facing == EnumFacing.DOWN) { //When the player clicks on the bottom of the tapestry rod
					if(carpetsOwned >= width) { //Ensure we have enough carpet to get the job done
						tet.setLength(length + 1); //Try setting the tapestry to one block longer
						if(tet.getLength() > length) { //See if the length actually changed
							carpetsUsed = width;
						}
					}
				}
				//Attempt to extend the banner width
				else if(facing.getAxis() != Axis.Y) { //When the player clicks on the front face of the tapestry rod
					if(carpetsOwned >= length) { //Make sure we have enough carpet in the stack
						tet.setWidth(width + 1); //Try setting the tapestry to one block wider
						if(tet.getWidth() > width) { //See if the width actually changed
							carpetsUsed = length;
						}
					}
				}

				//Consume carpet if applicable
				if(carpetsUsed > 0) {
					if(!playerIn.isCreative()) {
						stack.shrink(carpetsUsed);
					}
					return true;
				}
			}


			//Attempt to shorten banner length
			if(stack.getItem() instanceof ItemShears) {
				int carpetDropped = 0;
				boolean shearsUsed = false;

				if(facing == EnumFacing.DOWN) {
					TapestryShape shape = tet.getShape();
					switch(shape) {
						case Rectangular:
							tet.setShape(TapestryShape.Pointed);
							shearsUsed = true;
							break;
						case Pointed:
							tet.setShape(TapestryShape.SwallowTail);
							shearsUsed = true;
							break;
						case SwallowTail:
							tet.setLength(length - 1);
							if(tet.getLength() < length) { //See if the length actually changed
								tet.setShape(TapestryShape.Rectangular);
								shearsUsed = true;
								carpetDropped = width;//We cut off the bottom of the tapestry
							}
							break;
					}
				} else if(facing.getAxis() != Axis.Y) {
					tet.setWidth(width - 1);
					if(tet.getWidth() < width) { //See if the width actually changed
						shearsUsed = true;
						carpetDropped = length; //We cut off the side of the tapestry
					}
				}

				if(shearsUsed) {
					if(!playerIn.isCreative()) {//Don't consume/damage items or produce drops in creative mode
						stack.damageItem(1, playerIn);//damage shears with normal wear
						ItemStack carpetToDrop = new ItemStack(Blocks.CARPET, carpetDropped, tet.getBgColor().getMetadata());//colored carpet
						Block.spawnAsEntity(worldIn, pos.down(length - 1), carpetToDrop);//drop the cut off carpet into the world
					}

					return true;
				}
			}

		}

		return false;
	}

	public ItemStack getTaggedItemStackFromWorld(IBlockAccess world, BlockPos pos) {
		ItemStack stack = new ItemStack(Item.getItemFromBlock(this));
		TileEntity te = world.getTileEntity(pos);

		if(te instanceof TileEntityTapestry) {
			//System.out.println("getTaggedItemStackFromWorld");
			((TileEntityTapestry) te).writeSyncableDataToNBT(stack.getOrCreateSubCompound(BlockEntityTag));
		}

		return stack;
	}

    @Override
    public void getDrops(net.minecraft.util.NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
		drops.add(getTaggedItemStackFromWorld(world, pos));
    }
    
    @Override
    public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest) {
        if (willHarvest) return true; //If it will harvest, delay deletion of the block until after getDrops
        return super.removedByPlayer(state, world, pos, player, willHarvest);
    }
    
    @Override
    public void harvestBlock(World world, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, ItemStack tool) {
        super.harvestBlock(world, player, pos, state, te, tool);
        world.setBlockToAir(pos);//Here we actually destroy the block
    }
	
	@Override
	public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player) {
		// TODO Auto-generated method stub
		super.onBlockHarvested(worldIn, pos, state, player);
	}

	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
		return getTaggedItemStackFromWorld(world, pos);
	}
	
	@Override
	public void addInformation(ItemStack stack, World player, List<String> tooltip, ITooltipFlag advanced) {
		super.addInformation(stack, player, tooltip, advanced);
		
		if(stack.hasTagCompound()) {
			NBTTagCompound tag = stack.getTagCompound();
			if(tag.hasKey(BlockEntityTag, NBT.TAG_COMPOUND)) {
				NBTTagCompound entityTag = tag.getCompoundTag(BlockEntityTag);
				int width = entityTag.getByte(TileEntityTapestry.NBT_TAPESTRY_WIDTH);
				int length = entityTag.getByte(TileEntityTapestry.NBT_TAPESTRY_LENGTH);
				EnumDyeColor bgColor = EnumDyeColor.byMetadata(entityTag.getByte(TileEntityTapestry.NBT_TAPESTRY_BGCOLOR));
				EnumDyeColor fgColor = EnumDyeColor.byMetadata(entityTag.getByte(TileEntityTapestry.NBT_TAPESTRY_FGCOLOR));
				TapestryShape shape = TapestryShape.fromId(entityTag.getByte(TileEntityTapestry.NBT_TAPESTRY_SHAPE));
				tooltip.add(bgColor.getName() + " " + width + " x " + length + " " + shape.name().toLowerCase() + " banner with " + fgColor + " stitching");
			}
		}
	}
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityTapestry();
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {

		boolean onWall = state.getValue(WALL);
		EnumFacing facing = state.getValue(FACING);

		int width = 0;

		TileEntity te = source.getTileEntity(pos);
		if(te instanceof TileEntityTapestry) {
			TileEntityTapestry tet = (TileEntityTapestry) te;
			width = tet.getWidth() - 1;
		}

		if(onWall) {
			return wallsBB[(width * 4) + (facing.ordinal() - 2)];
		} else {
			return barsBB[(width * 2) + (facing.getAxis() == Axis.X ? 0 : 1)];
		}

	}

	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}

	@Override
	public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
		boolean onWall = state.getValue(WALL);
		EnumFacing facing = state.getValue(FACING);
		return onWall || (face.getAxis() != facing.rotateY().getAxis()) ? BlockFaceShape.UNDEFINED : BlockFaceShape.CENTER_SMALL;
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	public IBlockState withRotation(IBlockState state, Rotation rot) {
		return state.getBlock() != this ? state : state.withProperty(FACING, rot.rotate((EnumFacing)state.getValue(FACING)));
	}

}

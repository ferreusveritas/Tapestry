package com.ferreusveritas.tapestry;

import com.ferreusveritas.tapestry.block.BlockTapestry;
import com.ferreusveritas.tapestry.tileentity.TileEntityTapestry;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.oredict.ShapedOreRecipe;

@Mod.EventBusSubscriber(modid = ModConstants.MODID)
public class ModRecipes {

	@SubscribeEvent
	public static void register(RegistryEvent.Register<IRecipe> event) {
		
		ItemStack tapestryStack = new ItemStack(ModBlocks.blockTapestry);
		NBTTagCompound nbt = tapestryStack.getOrCreateSubCompound(BlockTapestry.BlockEntityTag);
		nbt.setByte(TileEntityTapestry.NBT_TAPESTRY_BGCOLOR, (byte) EnumDyeColor.BLUE.getMetadata());
		nbt.setByte(TileEntityTapestry.NBT_TAPESTRY_FGCOLOR, (byte) EnumDyeColor.YELLOW.getMetadata());
		
		event.getRegistry().register(new ShapedOreRecipe(null, tapestryStack, 
			"ii",
			"cc",
			"cc",
			'i', Items.IRON_INGOT,
			'c', Blocks.CARPET).setRegistryName(new ResourceLocation(ModConstants.MODID, "tapestry")));
	}
	
}

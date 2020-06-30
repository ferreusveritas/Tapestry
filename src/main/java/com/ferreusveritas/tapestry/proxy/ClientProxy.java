package com.ferreusveritas.tapestry.proxy;

import com.ferreusveritas.tapestry.ModBlocks;
import com.ferreusveritas.tapestry.block.BlockTapestry;
import com.ferreusveritas.tapestry.client.renderer.TileEntityTapestryRenderer;
import com.ferreusveritas.tapestry.tileentity.TileEntityTapestry;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.Constants.NBT;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

public class ClientProxy extends CommonProxy {


	@Override
	public void init(FMLInitializationEvent event) {
		super.init(event);
		registerColorHandlers();
	}

	public void registerColorHandlers() {
		Minecraft.getMinecraft().getItemColors().registerItemColorHandler((stack, tint) -> getTapestryColor(stack, tint), new Item[] { Item.getItemFromBlock(ModBlocks.blockTapestry)});
	}

	public int getTapestryColor(ItemStack stack, int tint) {
		if(stack.hasTagCompound()) {
			NBTTagCompound tag = stack.getTagCompound();
			if(tag.hasKey(BlockTapestry.BlockEntityTag, NBT.TAG_COMPOUND)) {
				NBTTagCompound entityTag = tag.getCompoundTag(BlockTapestry.BlockEntityTag);
				int colorMeta = entityTag.getInteger(tint == 0 ? TileEntityTapestry.NBT_TAPESTRY_BGCOLOR : TileEntityTapestry.NBT_TAPESTRY_FGCOLOR);
				EnumDyeColor dyeColor = EnumDyeColor.byMetadata(colorMeta);
				return dyeColor.getColorValue();
			}
		} else {
			EnumDyeColor dyeColor = tint == 0 ? EnumDyeColor.BLUE : EnumDyeColor.YELLOW;
			return dyeColor.getColorValue();
		}

		return 0;
	}

	
	
	@Override
	public void registerTileEntities() {
		super.registerTileEntities();
		TileEntitySpecialRenderer tapestryRenderer = new TileEntityTapestryRenderer();
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTapestry.class, tapestryRenderer);
	}

}

package com.ferreusveritas.tapestry;

import com.ferreusveritas.tapestry.tileentity.TileEntityTapestry;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModTileEntities {

	public static void preInit() {
		GameRegistry.registerTileEntity(TileEntityTapestry.class, new ResourceLocation(ModConstants.MODID, "tapestry"));
	}
	
}

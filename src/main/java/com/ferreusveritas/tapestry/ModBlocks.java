package com.ferreusveritas.tapestry;

import com.ferreusveritas.tapestry.block.BlockTapestry;

import net.minecraft.block.Block;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = ModConstants.MODID)
public class ModBlocks {

	public static Block blockTapestry;
	
	public static void preInit(FMLPreInitializationEvent event) {
		blockTapestry = new BlockTapestry();
	}
	
	@SubscribeEvent
	public static void register(RegistryEvent.Register<Block> event) {
		event.getRegistry().register(blockTapestry);
	}
	
}

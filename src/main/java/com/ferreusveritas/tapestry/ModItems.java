package com.ferreusveritas.tapestry;

import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = ModConstants.MODID)
public class ModItems {

	@SubscribeEvent
	public static void register(RegistryEvent.Register<Item> event) {
		ItemBlock itemBlock = new ItemBlock(ModBlocks.blockTapestry);
		itemBlock.setRegistryName(ModBlocks.blockTapestry.getRegistryName());
		event.getRegistry().register(itemBlock);
	}
	
}

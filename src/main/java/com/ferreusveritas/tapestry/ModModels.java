package com.ferreusveritas.tapestry;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = ModConstants.MODID)
public class ModModels {

	@SubscribeEvent
	public static void register(ModelRegistryEvent event) {
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(ModBlocks.blockTapestry), 0, new ModelResourceLocation(ModBlocks.blockTapestry.getRegistryName(), "inventory"));
	}
	
}

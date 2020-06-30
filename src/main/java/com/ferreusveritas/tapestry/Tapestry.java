package com.ferreusveritas.tapestry;

import com.ferreusveritas.tapestry.proxy.CommonProxy;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

/**
* <p><pre><tt><b>
*  ╭─────────────────╮
*  │                 │
*  │                 │
*  │                 │
*  │                 │
*  │                 │
*  │                 │
*  │                 │
*  │                 │
*  │                 │
*  │                 │
*  │                 │
*  ╞═════════════════╡
*  │     TAPESTRY    │
*  ╰─────────────────╯
* </b></tt></pre></p>
* 
* <p>
* 2020 Ferreusveritas
* </p>
*
*/
@Mod(modid = ModConstants.MODID, name = ModConstants.NAME, version=ModConstants.VERSION, dependencies=ModConstants.DEPENDENCIES)
public class Tapestry {
	
	@Mod.Instance(ModConstants.MODID)
	public static Tapestry instance;
	
	@SidedProxy(clientSide = "com.ferreusveritas.tapestry.proxy.ClientProxy", serverSide = "com.ferreusveritas.tapestry.proxy.CommonProxy")
	public static CommonProxy proxy;
	
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		
		ModTileEntities.preInit();
		
		ModBlocks.preInit(event);
		proxy.preInit(event);
	}
	
	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {
		proxy.init(event);
		proxy.registerTileEntities();
	}
	
	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		proxy.postInit();
	}
	
}
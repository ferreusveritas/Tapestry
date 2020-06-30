package com.ferreusveritas.tapestry.util;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class OreColor {

	private static Map<Integer, EnumDyeColor> colorMap = null;
	
	private static void initOreColorMapping() {
		
		colorMap = new HashMap<>();
		
        // Register dyes
        String[] dyes = {
            "Black",
            "Red",
            "Green",
            "Brown",
            "Blue",
            "Purple",
            "Cyan",
            "LightGray",
            "Gray",
            "Pink",
            "Lime",
            "Yellow",
            "LightBlue",
            "Magenta",
            "Orange",
            "White"
        };
		
        for(int c = 0; c < dyes.length; c++) {
        	String colorName = dyes[c];
        	int id = OreDictionary.getOreID("dye" + colorName);
        	colorMap.put(id, EnumDyeColor.byDyeDamage(c));
        }
	}
	
	public static EnumDyeColor getDyeColor(ItemStack stack) {

		if(colorMap == null) {
			initOreColorMapping();
		}
        
		if(!stack.isEmpty()) {
			int[] ores = OreDictionary.getOreIDs(stack);
			
			for(int id : ores) {
				if(colorMap.containsKey(id)) {
					return colorMap.get(id);
				}
			}
		}

		return null;
	}
	
}

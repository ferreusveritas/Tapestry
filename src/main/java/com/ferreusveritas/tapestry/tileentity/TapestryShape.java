package com.ferreusveritas.tapestry.tileentity;

public enum TapestryShape {
	Rectangular,
	Pointed,
	SwallowTail;
	
	public static TapestryShape fromId(int id) {
		switch(id) {
			case 0: return Rectangular;
			case 1: return Pointed;
			case 2: return SwallowTail;
			default: return Pointed;
		}
	}
	
}

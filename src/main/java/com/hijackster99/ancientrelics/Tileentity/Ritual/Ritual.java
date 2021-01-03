package com.hijackster99.ancientrelics.Tileentity.Ritual;

import java.util.HashMap;
import java.util.Map;

import com.hijackster99.ancientrelics.core.classloader.RitualClassManager;
import com.hijackster99.ancientrelics.core.classloader.RitualJsonManager;

import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;

public class Ritual {
	
	public TileEntityWrapper wrapper;
	public Map<Block, BlockPos> ritualBlocks;
	public int tier;
	
	public static Map<ResourceLocation, Ritual> loadedRituals = new HashMap<ResourceLocation, Ritual>();
	
	public static boolean classLoad = false;
	public static boolean jsonLoad = false;
	
	public Ritual(TileEntityWrapper wrapper, Map<Block, BlockPos> ritualBlocks, int tier) {
		this.wrapper = wrapper;
		this.ritualBlocks = ritualBlocks;
		this.tier = tier;
	}
	
	public static void CompileRituals() {
		loadedRituals.clear();
		loadedRituals.put(new ResourceLocation("ancientrelics:default_ritual"), new Ritual(new TileEntityWrapper(DefaultWrapper.class), null, -1));
		for(ResourceLocation loc : RitualJsonManager.ritualBlocks.keySet()) {
			if(RitualJsonManager.ritualTiers.containsKey(loc) && RitualClassManager.ritualClasses.containsKey(loc)) {
				loadedRituals.put(loc, new Ritual(new TileEntityWrapper(RitualClassManager.ritualClasses.get(loc)), RitualJsonManager.ritualBlocks.get(loc), RitualJsonManager.ritualTiers.get(loc)));
			}else {
				System.err.println("Error: Missing Ritual data! Skipping!");
			}
		}
	}
	
	@Override
	public String toString() {
		return "Class: " + wrapper.te.toString() + ", Ritual Blocks: " + ritualBlocks + ", Tier: " + tier;
	}

}

package com.hijackster99.ancientrelics.Tileentity.Ritual;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hijackster99.ancientrelics.core.classloader.RitualClassManager;
import com.hijackster99.ancientrelics.core.classloader.RitualJsonManager;

import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;

public class Ritual {
	
	public TileEntityWrapper wrapper;
	public Map<Block, BlockPos> ritualBlocks;
	
	public static Map<ResourceLocation, Ritual> loadedRituals = new HashMap<ResourceLocation, Ritual>();
	
	public static boolean classLoad = false;
	public static boolean jsonLoad = false;
	
	public Ritual(TileEntityWrapper wrapper, Map<Block, BlockPos> ritualBlocks) {
		this.wrapper = wrapper;
		this.ritualBlocks = ritualBlocks;
	}
	
	public static void CompileRituals() {
		loadedRituals.clear();
		loadedRituals.put(new ResourceLocation("ancientrelics:default_ritual"), new Ritual(new TileEntityWrapper(DefaultWrapper.class), null));
		for(ResourceLocation loc : RitualJsonManager.ritualBlocks.keySet()) {
			if(RitualClassManager.ritualClasses.containsKey(loc)) {
				loadedRituals.put(loc, new Ritual(new TileEntityWrapper(RitualClassManager.ritualClasses.get(loc)), RitualJsonManager.ritualBlocks.get(loc)));
			}else {
				System.err.println("Error: Json has no matching Class! Skipping!");
			}
		}
	}

}

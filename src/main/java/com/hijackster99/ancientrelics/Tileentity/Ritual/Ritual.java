package com.hijackster99.ancientrelics.Tileentity.Ritual;

import java.util.List;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.registries.ForgeRegistryEntry;

public class Ritual extends ForgeRegistryEntry<Ritual>{
	
	public Class<? extends TileEntityWrapper> wrapper;
	public Map<Block, List<BlockPos>> ritualBlocks;
	public int tier;
	
	public static boolean classLoad = false;
	public static boolean jsonLoad = false;
	
	public Ritual(String name, Class<? extends TileEntityWrapper> wrapper, Map<Block, List<BlockPos>> ritualBlocks, int tier) {
		this.wrapper = wrapper;
		this.ritualBlocks = ritualBlocks;
		this.tier = tier;
		this.setRegistryName(name);
	}
	
	public Ritual(String name, Class<? extends TileEntityWrapper> wrapper) {
		this(name, wrapper, null, 0);
	}
	
	public boolean isValid() {
		return tier > 0 && tier < 7 && !ritualBlocks.isEmpty();
	}

}

package com.hijackster99.ancientrelics.tileentity.ritual;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.registries.ForgeRegistryEntry;

public class Ritual extends ForgeRegistryEntry<Ritual>{
	
	public Class<? extends TileEntityWrapper> wrapper;
	private Map<Block, List<BlockPos>> ritualBlocks;
	private Map<BlockPos, Block> ritualBlocksIter;
	private int tier;
	
	public static int blocksChecked = 4;
	
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
	
	public Map<Block, List<BlockPos>> getRitualBlocks() {
		return ritualBlocks;
	}

	public void setRitualBlocks(Map<Block, List<BlockPos>> ritualBlocks) {
		Map<BlockPos, Block> m = new HashMap<BlockPos, Block>();
		for(Entry<Block, List<BlockPos>> e : ritualBlocks.entrySet()) {
			for(BlockPos pos : e.getValue()) {
				m.put(pos, e.getKey());
			}
		}
		this.ritualBlocks = ritualBlocks;
		this.ritualBlocksIter = m;
	}

	public Map<BlockPos, Block> getRitualBlocksIter() {
		return ritualBlocksIter;
	}

	public int getTier() {
		return tier;
	}

	public void setTier(int tier) {
		this.tier = tier;
	}
	
	@Override
	public String toString() {
		String result = getRegistryName().toString() + ": " + tier;
		if(ritualBlocks != null) result += ", " + ritualBlocks.toString();
		else result += ", " + "null";
		if(wrapper != null) result += ", " + wrapper.toString();
		else result += ", " + "null";
		return result;
	}

}

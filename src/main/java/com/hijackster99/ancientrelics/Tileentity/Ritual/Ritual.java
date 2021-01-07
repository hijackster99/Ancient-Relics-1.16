package com.hijackster99.ancientrelics.tileentity.ritual;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.hijackster99.ancientrelics.core.classloader.RitualJsonManager.Option;

import net.minecraft.util.math.BlockPos;
import net.minecraftforge.registries.ForgeRegistryEntry;

public class Ritual extends ForgeRegistryEntry<Ritual>{
	
	public Class<? extends TileEntityWrapper> wrapper;
	private Map<Option, List<BlockPos>> ritualBlocks;
	private Map<BlockPos, Option> ritualBlocksIter;
	private int tier;
	
	public static int blocksChecked = 4;
	
	public Ritual(String name, Class<? extends TileEntityWrapper> wrapper, Map<Option, List<BlockPos>> ritualBlocks, int tier) {
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
	
	public Map<Option, List<BlockPos>> getRitualBlocks() {
		return ritualBlocks;
	}

	public void setRitualBlocks(Map<Option, List<BlockPos>> ritualBlocks) {
		Map<BlockPos, Option> m = new HashMap<BlockPos, Option>();
		for(Entry<Option, List<BlockPos>> e : ritualBlocks.entrySet()) {
			for(BlockPos pos : e.getValue()) {
				m.put(pos, e.getKey());
			}
		}
		this.ritualBlocks = ritualBlocks;
		this.ritualBlocksIter = m;
	}

	public Map<BlockPos, Option> getRitualBlocksIter() {
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

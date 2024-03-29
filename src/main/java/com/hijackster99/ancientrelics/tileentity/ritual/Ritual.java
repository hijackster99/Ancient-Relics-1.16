package com.hijackster99.ancientrelics.tileentity.ritual;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.hijackster99.ancientrelics.core.classloader.RitualJsonManager.Option;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistryEntry;

public class Ritual extends ForgeRegistryEntry<Ritual>{
	
	public Class<? extends TileEntityWrapper> wrapper;
	private Map<BlockPos, Option> ritualBlocksIter;
	
	private int tier;
	private Map<Option, List<BlockPos>> ritualBlocks;
	private Map<BlockPos, String> blockDefs;
	private Map<Block, List<String>> typeDefs;
	
	public static int blocksChecked = 4;
	
	public Ritual(String name, Class<? extends TileEntityWrapper> wrapper, Map<Option, List<BlockPos>> ritualBlocks, int tier) {
		this.wrapper = wrapper;
		this.ritualBlocks = ritualBlocks;
		this.tier = tier;
		this.blockDefs = new HashMap<BlockPos, String>();
		this.typeDefs = new HashMap<Block, List<String>>();
		this.setRegistryName(name);
	}
	
	public Ritual(String name, Class<? extends TileEntityWrapper> wrapper) {
		this(name, wrapper, null, -1);
	}
	
	public boolean isValid() {
		return tier != -1 && !ritualBlocks.isEmpty();
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
	
	public Map<BlockPos, String> getBlockDefs() {
		return blockDefs;
	}
	
	public void putBlockDefs(BlockPos pos, String str) {
		blockDefs.put(pos, str);
	}
	
	public Map<Block, List<String>> getTypeDefs() {
		return typeDefs;
	}
	
	public void putTypeDefs(Block loc, List<String> cap) {
		typeDefs.put(loc, cap);
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

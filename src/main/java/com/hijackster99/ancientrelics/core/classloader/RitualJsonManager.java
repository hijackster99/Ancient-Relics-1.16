package com.hijackster99.ancientrelics.core.classloader;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.hijackster99.ancientrelics.Tileentity.Ritual.Ritual;

import net.minecraft.block.Block;
import net.minecraft.client.resources.JsonReloadListener;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class RitualJsonManager extends JsonReloadListener{
	
	private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().disableHtmlEscaping().create();
	
	public static Map<ResourceLocation, Map<Block, BlockPos>> ritualBlocks;
	public static Map<ResourceLocation, Integer> ritualTiers;

	public RitualJsonManager() {
		super(GSON, "ritual");
		ritualBlocks = new HashMap<ResourceLocation, Map<Block, BlockPos>>();
	}

	@Override
	protected void apply(Map<ResourceLocation, JsonElement> objectIn, IResourceManager resourceManagerIn, IProfiler profilerIn) {
		for(Entry<ResourceLocation, JsonElement> entry : objectIn.entrySet()) {
	         ResourceLocation resourcelocation = entry.getKey();
	         if (resourcelocation.getPath().startsWith("_")) continue; //Forge: filter anything beginning with "_" as it's used for metadata.
	         if(entry.getValue().isJsonArray()) {
        		 Map<Block, BlockPos> blocks = new HashMap<Block, BlockPos>();
	        	 JsonArray array = entry.getValue().getAsJsonArray();
	        	 for(JsonElement e : array) {
	        		 if(e.isJsonArray()) {
	        			 JsonArray arr = (JsonArray) e;
			        	 if(arr.size() > 1 && (arr.size() - 1) % 3 == 0) {
		        			 String loc = arr.get(0).getAsString();
		    				 Block block = GameRegistry.findRegistry(Block.class).getValue(new ResourceLocation(loc));
		    				 if(block != null) {
			        			 for(int i = 1; i < arr.size(); i += 3) {
			        				 int x = arr.get(i).getAsInt();
			        				 int y = arr.get(i + 1).getAsInt();
			        				 int z = arr.get(+ 2).getAsInt();
			        				 blocks.put(block, new BlockPos(x, y, z));
			        			 }
		    				 }else {
		    					 System.err.println("Error: Block not registered! Skipping!");
		    				 }
			        	 }
	        		 }else {
	        			 ritualTiers.put(resourcelocation, e.getAsInt());
	        		 }
	        	 }
    			 ritualBlocks.put(resourcelocation, blocks);
	         }
		}
		Ritual.jsonLoad = true;
		if(Ritual.jsonLoad && Ritual.classLoad) {
			Ritual.CompileRituals();
			Ritual.jsonLoad = false;
			Ritual.classLoad = false;
		}
	}

}

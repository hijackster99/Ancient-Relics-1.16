package com.hijackster99.ancientrelics.core.classloader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.hijackster99.ancientrelics.tileentity.ritual.Ritual;
import com.hijackster99.ancientrelics.tileentity.ritual.RitualBuilder;

import net.minecraft.block.Block;
import net.minecraft.client.resources.JsonReloadListener;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class RitualJsonManager extends JsonReloadListener{
	
	private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().disableHtmlEscaping().create();
	private static final Logger LOGGER = LogManager.getLogger();

	public RitualJsonManager() {
		super(GSON, "ritual");
	}

	@Override
	protected void apply(Map<ResourceLocation, JsonElement> objectIn, IResourceManager resourceManagerIn, IProfiler profilerIn) {
		for(Entry<ResourceLocation, JsonElement> entry : objectIn.entrySet()) {
	         ResourceLocation resourcelocation = entry.getKey();
	         if (resourcelocation.getPath().startsWith("_")) continue; //Forge: filter anything beginning with "_" as it's used for metadata.
			 if(GameRegistry.findRegistry(Ritual.class).containsKey(resourcelocation)) {
				 JsonElement tierElement = entry.getValue().getAsJsonObject().get("tier");
				 GameRegistry.findRegistry(Ritual.class).getValue(resourcelocation).setTier(tierElement.getAsInt());
        		 Map<Block, List<BlockPos>> blocks = new HashMap<Block, List<BlockPos>>();
	        	 JsonObject object = entry.getValue().getAsJsonObject().get("blocks").getAsJsonObject();
	        	 Set<Map.Entry<String, JsonElement>> names = object.entrySet();
	        	 for(Map.Entry<String, JsonElement> e : names) {
	        		 if(e.getValue().isJsonArray()) {
	        			 JsonArray arr = e.getValue().getAsJsonArray();
			        	 if(arr.size() > 1 && arr.size() % 3 == 0) {
		        			 String loc = e.getKey();
		    				 Block block = GameRegistry.findRegistry(Block.class).getValue(new ResourceLocation(loc));
		    				 if(block != null) {
		    					 List<BlockPos> posList = new ArrayList<BlockPos>();
			        			 for(int i = 0; i < arr.size(); i += 3) {
			        				 int x = arr.get(i).getAsInt();
			        				 int y = arr.get(i + 1).getAsInt();
			        				 int z = arr.get(i + 2).getAsInt();
			        				 if(!(x == 0 && y == 0 && z == 0))
			        					 posList.add(new BlockPos(x, y, z));
			        				 else
			        					 LOGGER.error("Error: Block specified at ritual center! Skipping!");
			        			 }
			        			 blocks.put(block, posList);
		    				 }else {
		    					 LOGGER.error("Error: Block: {} not registered! Skipping!", loc);
		    				 }
			        	 }
	        		 }
		         }
    			 GameRegistry.findRegistry(Ritual.class).getValue(resourcelocation).setRitualBlocks(blocks);
			 }else {
				 LOGGER.error("Error: Ritual: {} not registered! Skipping!", resourcelocation);
			 }
		}
		RitualBuilder.rituals = null;
		printInvalidRituals();
	}
	
	private void printInvalidRituals() {
		for(Ritual r : GameRegistry.findRegistry(Ritual.class).getValues()) {
			if(!r.isValid()) LOGGER.error("Warning! Ritual: {} Had a problem loading and will be ignored! Be sure to check the json format!", r.getRegistryName());
		}
	}

}

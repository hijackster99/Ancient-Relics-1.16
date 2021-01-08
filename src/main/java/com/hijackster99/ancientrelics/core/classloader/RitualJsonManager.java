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
import net.minecraft.tags.Tag;
import net.minecraft.tags.TagCollectionManager;
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
				 if(TagCollectionManager.getManager().getBlockTags().get(new ResourceLocation("ancientrelics:ritual_tier_" + tierElement.getAsInt())) != null) GameRegistry.findRegistry(Ritual.class).getValue(resourcelocation).setTier((Tag<Block>) TagCollectionManager.getManager().getBlockTags().get(new ResourceLocation("ancientrelics:ritual_tier_" + tierElement.getAsInt())));
        		 Map<Option, List<BlockPos>> blocks = new HashMap<Option, List<BlockPos>>();
	        	 JsonObject object = entry.getValue().getAsJsonObject().get("blocks").getAsJsonObject();
	        	 Set<Map.Entry<String, JsonElement>> names = object.entrySet();
	        	 for(Map.Entry<String, JsonElement> e : names) {
	        		 if(e.getValue().isJsonArray()) {
	        			 JsonArray arr = e.getValue().getAsJsonArray();
			        	 if(arr.size() > 1 && arr.size() % 3 == 0) {
		        			 String name = e.getKey();
		        			 Option opt = new Option();
		        			 if(name.startsWith("#")) {
		        				 name = name.substring(1);
		        				 Tag<Block> tag = (Tag<Block>) TagCollectionManager.getManager().getBlockTags().get(new ResourceLocation(name));
		        				 if(tag != null) opt.set(tag);
		        			 }else {
		        				 if(GameRegistry.findRegistry(Block.class).containsKey(new ResourceLocation(name))) opt.set(GameRegistry.findRegistry(Block.class).getValue(new ResourceLocation(name)));
		        			 }
		    				 if(opt.getType() != null) {
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
			        			 blocks.put(opt, posList);
		    				 }else {
		    					 LOGGER.error("Error: Block/Tag: {} not registered! Skipping!", name);
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
	
	public static class Option {
		
		private Block block;
		private Tag<Block> tag;
		
		public Option() {
			block = null;
			tag = null;
		}
		
		public Option(Block block) {
			this.block = block;
		}
		
		public Option(Tag<Block> tag) {
			this.tag = tag;
		}
		
		public boolean set(Block block) {
			if(tag == null) {
				this.block = block;
				return true;
			}
			return false;
		}
		
		public boolean set(Tag<Block> tag) {
			if(block == null) {
				this.tag = tag;
				return true;
			}
			return false;
		}
		
		public Class<?> getType(){
			if(block != null) {
				return Block.class;
			}else if(tag != null) {
				return Tag.class;
			}
			return null;
		}
		
		public Object get() {
			return block != null ? block : tag != null ? tag : null;
		}
		
	}

}

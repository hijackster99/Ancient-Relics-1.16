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

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryManager;

public class RitualJsonManager extends SimpleJsonResourceReloadListener{
	
	private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().disableHtmlEscaping().create();
	private static final Logger LOGGER = LogManager.getLogger();

	public RitualJsonManager() {
		super(GSON, "ritual");
	}

	
	
	@Override
	protected void apply(Map<ResourceLocation, JsonElement> objectIn, ResourceManager resourceManagerIn, ProfilerFiller profilerIn) {
		for(Entry<ResourceLocation, JsonElement> entry : objectIn.entrySet()) {
	         ResourceLocation resourcelocation = entry.getKey();
	         if (resourcelocation.getPath().startsWith("_")) continue; //Forge: filter anything beginning with "_" as it's used for metadata.
			 if(RegistryManager.ACTIVE.getRegistry(Ritual.class).containsKey(resourcelocation)) {
				 JsonElement tierElement = entry.getValue().getAsJsonObject().get("tier");
				 RegistryManager.ACTIVE.getRegistry(Ritual.class).getValue(resourcelocation).setTier(tierElement.getAsInt());
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
								 ResourceLocation tag = new ResourceLocation(name);
		        				 if(tag != null) opt.set(tag);
		        			 }else {
		        				 if(RegistryManager.ACTIVE.getRegistry(Block.class).containsKey(new ResourceLocation(name))) opt.set(RegistryManager.ACTIVE.getRegistry(Block.class).getValue(new ResourceLocation(name)));
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
	        	 if(entry.getValue().getAsJsonObject().has("block_definition")) {
		        	 JsonObject blockDef = entry.getValue().getAsJsonObject().get("block_definition").getAsJsonObject();
		        	 Set<Map.Entry<String, JsonElement>> blockEntrySet = blockDef.entrySet();
		        	 for(Map.Entry<String, JsonElement> e : blockEntrySet) {
		        		 if(e.getValue().isJsonArray()) {
		        			 JsonArray array = e.getValue().getAsJsonArray();
		        			 if(array.size() > 1 && array.size() % 3 == 0) {
		        				 for(int i = 0; i < array.size(); i += 3) {
			        				 int x = array.get(i).getAsInt();
			        				 int y = array.get(i + 1).getAsInt();
			        				 int z = array.get(i + 2).getAsInt();
			        				 if(!(x == 0 && y == 0 && z == 0))
			        					 RegistryManager.ACTIVE.getRegistry(Ritual.class).getValue(resourcelocation).putBlockDefs(new BlockPos(x, y, z), e.getKey());
			        				 else
			        					 LOGGER.error("Error: Block specified at ritual center! Skipping!");
			        			 }
		        			 }
		        		 }
		        	 }
	        	 }
	        	 if(entry.getValue().getAsJsonObject().has("type_definition")) {
		        	 JsonObject typeDef = entry.getValue().getAsJsonObject().get("type_definition").getAsJsonObject();
		        	 Set<Map.Entry<String, JsonElement>> typeEntrySet = typeDef.entrySet();
		        	 for(Map.Entry<String, JsonElement> e : typeEntrySet) {
		        		 if(e.getValue().isJsonArray()) {
		        			 JsonArray array = e.getValue().getAsJsonArray();
		        			 List<String> capList = new ArrayList<String>();
		        			 for(JsonElement arrEl : array) {
		        				 String str = arrEl.getAsString();
		        				 capList.add(str);
		        			 }
		        			 RegistryManager.ACTIVE.getRegistry(Ritual.class).getValue(resourcelocation).putTypeDefs(RegistryManager.ACTIVE.getRegistry(Block.class).getValue(new ResourceLocation(e.getKey())), capList);
		        		 }
		        	 }
	        	 }
	        	 RegistryManager.ACTIVE.getRegistry(Ritual.class).getValue(resourcelocation).setRitualBlocks(blocks);
			 }else {
				 LOGGER.error("Error: Ritual: {} not registered! Skipping!", resourcelocation);
			 }
		}
		RitualBuilder.rituals = null;
		printInvalidRituals();
	}
	
	private void printInvalidRituals() {
		for(Ritual r : RegistryManager.ACTIVE.getRegistry(Ritual.class).getValues()) {
			if(!r.isValid()) LOGGER.error("Warning! Ritual: {} Had a problem loading and will be ignored! Be sure to check the json format!", r.getRegistryName());
		}
	}
	
	public static class Option {
		
		private Block block;
		private ResourceLocation tag;
		
		public Option() {
			block = null;
			tag = null;
		}
		
		public Option(Block block) {
			this.block = block;
		}
		
		public Option(ResourceLocation tag) {
			this.tag = tag;
		}
		
		public boolean set(Block block) {
			if(tag == null) {
				this.block = block;
				return true;
			}
			return false;
		}
		
		public boolean set(ResourceLocation tag) {
			if(block == null) {
				this.tag = tag;
				return true;
			}
			return false;
		}
		
		public String getType(){
			if(block != null) {
				return "block";
			}else if(tag != null) {
				return "tag";
			}
			return null;
		}
		
		public Object get() {
			return block != null ? block : tag != null ? tag : null;
		}
		
		public boolean isBlock()
		{
			return getType().equals("block");
		}
		
		@Override
		public String toString() {
			if(getType().equals("block"))
				return "Option: " + block.toString();
			else return "Option: #" + tag.toString();
		}
		
	}

}

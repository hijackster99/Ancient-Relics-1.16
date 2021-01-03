package com.hijackster99.ancientrelics.core.classloader;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.hijackster99.ancientrelics.Tileentity.Ritual.Ritual;

import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;

public class RitualClassManager extends ClassReloadListener{

	public static Map<ResourceLocation, Class<?>> ritualClasses;
	private final static FileClassLoader LOADER = new FileClassLoader();
	
	public RitualClassManager() {
		super(LOADER, "ritual");
		ritualClasses = new HashMap<ResourceLocation, Class<?>>();
	}

	@Override
	protected void apply(Map<ResourceLocation, Class<?>> objectIn, IResourceManager resourceManagerIn, IProfiler profilerIn) {
		for(Entry<ResourceLocation, Class<?>> entry : objectIn.entrySet()) {
	         ResourceLocation resourcelocation = entry.getKey();
	         Class<?> ritualClass = entry.getValue();
	         if (resourcelocation.getPath().startsWith("_")) continue; //Forge: filter anything beginning with "_" as it's used for metadata.
	         ritualClasses.clear();
	         ritualClasses.put(resourcelocation, ritualClass);
		}
		Ritual.classLoad = true;
		if(Ritual.classLoad && Ritual.jsonLoad) {
			Ritual.CompileRituals();
			Ritual.classLoad = false;
			Ritual.jsonLoad = false;
		}
	}

}

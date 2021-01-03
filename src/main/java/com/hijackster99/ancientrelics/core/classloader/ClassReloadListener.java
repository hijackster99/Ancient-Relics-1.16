package com.hijackster99.ancientrelics.core.classloader;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.collect.Maps;

import net.minecraft.client.resources.ReloadListener;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IResource;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;

public abstract class ClassReloadListener extends ReloadListener<Map<ResourceLocation, Class<?>>>{

	private static final Logger LOGGER = LogManager.getLogger();
   private static final int CLASS_EXTENSION_LENGTH = ".class".length();
   private final FileClassLoader loader;
   private final String folder;
   
   public ClassReloadListener(FileClassLoader loader, String folder) {
	   this.loader = loader;
	   this.folder = folder;
   }
	
	@Override
	protected Map<ResourceLocation, Class<?>> prepare(IResourceManager resourceManagerIn, IProfiler profilerIn) {
		Map<ResourceLocation, Class<?>> map = Maps.newHashMap();
	      int i = this.folder.length() + 1;

	      for(ResourceLocation resourcelocation : resourceManagerIn.getAllResourceLocations(this.folder, (p_223379_0_) -> {
	         return p_223379_0_.endsWith(".class");
	      })) {
	         String s = resourcelocation.getPath();
	         ResourceLocation resourcelocation1 = new ResourceLocation(resourcelocation.getNamespace(), s.substring(i, s.length() - CLASS_EXTENSION_LENGTH));

	         
	            IResource iresource;
				try {
					iresource = resourceManagerIn.getResource(resourcelocation);
		            InputStream inputstream = iresource.getInputStream();
		            Class<?> classElement = loader.getClassFromName(resourcelocation1.getPath(), inputstream);
		            if (classElement != null) {
		               Class<?> classElement1 = map.put(resourcelocation1, classElement);
		               if (classElement1 != null) {
		                  throw new IllegalStateException("Duplicate data file ignored with ID " + resourcelocation1);
		               }
		            } else {
		               LOGGER.error("Couldn't load data file {} from {} as it's null or empty", resourcelocation1, resourcelocation);
		            }
				} catch (IOException e) {
					LOGGER.error("Couldn't parse data file {} from {}", resourcelocation1, resourcelocation, e);
				}
	            
	      }

	      return map;
	}

}

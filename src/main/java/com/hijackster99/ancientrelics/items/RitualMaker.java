package com.hijackster99.ancientrelics.items;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.storage.FolderName;

public class RitualMaker extends ARItem {
	
	int clicked = 0;
	BlockPos pos1;
	BlockPos pos2;
	Block ignore;
	Block air;
	BlockPos pos3;

	public RitualMaker(String registryName, int maxStack, ItemGroup tab) {
		super(registryName, maxStack, tab);
	}
	
	@Override
	public ActionResultType onItemUse(ItemUseContext context) {
		if(!context.getWorld().isRemote()) {
			if(clicked == 0) {
				pos1 = context.getPos();
				clicked++;
				return ActionResultType.SUCCESS;
			}else if(clicked == 1) {
				pos2 = context.getPos();
				clicked++;
				return ActionResultType.SUCCESS;
			}else if(clicked == 2) {
				ignore = context.getWorld().getBlockState(context.getPos()).getBlock();
				clicked++;
				return ActionResultType.SUCCESS;
			}else if(clicked == 3) {
				air = context.getWorld().getBlockState(context.getPos()).getBlock();
				clicked++;
				return ActionResultType.SUCCESS;
			}else if(clicked == 4){
				pos3 = context.getPos();
				createFile(context.getWorld());
				return ActionResultType.SUCCESS;
			}
		}
		return ActionResultType.PASS;
	}
	
	private void createFile(World world) {
		clicked = 0;
		JsonObject main = new JsonObject();
		JsonObject blocks = new JsonObject();
		Map<Block, List<BlockPos>> blocksMap = new HashMap<Block, List<BlockPos>>();
		for(int x = (pos1.getX() < pos2.getX() ? pos1.getX() : pos2.getX()); x <= (pos1.getX() >= pos2.getX() ? pos1.getX() : pos2.getX()); x++) {
			for(int y = (pos1.getY() < pos2.getY() ? pos1.getY() : pos2.getY()); y <= (pos1.getY() >= pos2.getY() ? pos1.getY() : pos2.getY()); y++) {
				for(int z = (pos1.getZ() < pos2.getZ() ? pos1.getZ() : pos2.getZ()); z <= (pos1.getZ() >= pos2.getZ() ? pos1.getZ() : pos2.getZ()); z++) {
					BlockPos pos = new BlockPos(x, y, z);
					Block block = world.getBlockState(pos).getBlock();
					if(block == Blocks.AIR || block == ignore || (x == 0 && y == 0 && z == 0)) block = null;
					if(block == air) block = Blocks.AIR;
					if(!blocksMap.containsKey(block)) {
						if(block != null) blocksMap.put(block, new ArrayList<BlockPos>());
					}
					if(block != null) blocksMap.get(block).add(pos);
				}
			}
		}
		for(Entry<Block, List<BlockPos>> entry : blocksMap.entrySet()) {
			JsonArray arr = new JsonArray();
			for(BlockPos pos : entry.getValue()) {
				arr.add(pos.subtract(pos3).getX());
				arr.add(pos.subtract(pos3).getY());
				arr.add(pos.subtract(pos3).getZ());
			}
			blocks.add(entry.getKey().getRegistryName().toString(), arr);
		}
		main.add("blocks", blocks);
		try {
			File file = Minecraft.getInstance().getIntegratedServer().func_240776_a_(new FolderName("Rituals")).toFile();
			file.mkdir();
			File file2 = Minecraft.getInstance().getIntegratedServer().func_240776_a_(new FolderName("Rituals")).resolve(world.getBlockState(pos3).getBlock().getRegistryName().getPath() + ".json").toFile();
			FileOutputStream str = new FileOutputStream(file2);
			str.write(new GsonBuilder().create().toJson(main).getBytes());
			str.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

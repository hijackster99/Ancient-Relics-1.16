package com.hijackster99.ancientrelics.tileentity.ritual;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.hijackster99.ancientrelics.blocks.ARBlock;
import com.hijackster99.ancientrelics.core.References;
import com.hijackster99.ancientrelics.core.classloader.RitualJsonManager.Option;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.event.TickEvent.WorldTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.registry.GameRegistry;

@EventBusSubscriber(modid = References.MODID)
public class RitualBuilder {

	public static Map<BlockPos, Ritual> activeRituals = new HashMap<BlockPos, Ritual>();
	public static List<Checker> ritualCheckers = new ArrayList<Checker>();
	public static List<Ritual> rituals = null;
	
	@SuppressWarnings("unchecked")
	@SubscribeEvent
	public static void worldTick(WorldTickEvent event) {
		if(event.side == LogicalSide.SERVER) {
			if(rituals == null) getRituals();
			for(Iterator<Checker> c = ritualCheckers.iterator(); c.hasNext(); ) {
				Minecraft.getInstance().getIntegratedServer().sendMessage(new StringTextComponent("Checking Rituals..."), null);
				Checker check = c.next();
				if(check.dimension.equals(event.world.getDimensionKey().getLocation().toString())) {
					if(check.iter == null) getNextIter(check, event);
					if(check.valid) {
						if(check.iter != null && !check.iter.hasNext()) {
							Minecraft.getInstance().getIntegratedServer().sendMessage(new StringTextComponent("Found Valid Ritual!"), null);
							if(BlockTags.getCollection().get(new ResourceLocation("ancientrelics:ritual_type_inactive")).contains(event.world.getBlockState(check.pos).getBlock())) {
								activeRituals.put(check.pos, rituals.get(check.counter));
								event.world.setBlockState(check.pos, getActiveBlock(event.world.getBlockState(check.pos).getBlock()).getDefaultState());
							}
							c.remove();
						}else if(check.iter != null){
							for(int i = 0; i < Ritual.blocksChecked; i++) {
								if(check.iter.hasNext()) {
									Entry<BlockPos, Option> entry = check.iter.next();
									if(entry.getValue().getType().equals(Block.class)) {
										if(event.world.getBlockState(check.pos.add(entry.getKey())).getBlock() != (Block) entry.getValue().get()) { 
											check.valid = false;
											Minecraft.getInstance().getIntegratedServer().sendMessage(new StringTextComponent("Check Failed! Bad Block at " + check.pos.add(entry.getKey()) + "! " + event.world.getBlockState(check.pos.add(entry.getKey())).getBlock().getRegistryName().toString() + " instead of " + ((Block) entry.getValue().get()).getRegistryName().toString()), null);
										}
									}else if(entry.getValue().getType().equals(Tag.class)) {
										if(!((Tag<Block>) entry.getValue().get()).contains(event.world.getBlockState(check.pos.add(entry.getKey())).getBlock())) { 
											check.valid = false;
											Minecraft.getInstance().getIntegratedServer().sendMessage(new StringTextComponent("Check Failed! Bad Block at " + check.pos.add(entry.getKey()) + "! " + event.world.getBlockState(check.pos.add(entry.getKey())).getBlock().getRegistryName().toString() + " is not in specified tag collection!"), null);
										}
									}
								}
							}
						}else {
							Minecraft.getInstance().getIntegratedServer().sendMessage(new StringTextComponent("No Valid Ritual Found!"), null);
							c.remove();
						}
					}else {
						check.valid = true;
						getNextIter(check, event);
					}
				}
			}
		}
	}
	
	public static void getNextIter(Checker check, WorldTickEvent event) {
		check.iter = null;
		check.counter++;
		for( ;check.counter < rituals.size(); check.counter++){
			Ritual rit = rituals.get(check.counter);
			Minecraft.getInstance().getIntegratedServer().sendMessage(new StringTextComponent("Checking Ritual Tier..."), null);
			if(rit.getTier().contains(event.world.getBlockState(check.pos).getBlock())) {
				Minecraft.getInstance().getIntegratedServer().sendMessage(new StringTextComponent("Tiers Match! Checking: " + rituals.get(check.counter).getRegistryName().toString()), null);
				check.iter = rituals.get(check.counter).getRitualBlocksIter().entrySet().iterator();
				break;
			}
			Minecraft.getInstance().getIntegratedServer().sendMessage(new StringTextComponent("Check Failed!"), null);
		}
	}
	
	public static void getRituals() {
		Minecraft.getInstance().getIntegratedServer().sendMessage(new StringTextComponent("Loading Rituals..."), null);
		List<Ritual> rituals = new ArrayList<Ritual>(GameRegistry.findRegistry(Ritual.class).getValues());
		for(Iterator<Ritual> iter = rituals.iterator(); iter.hasNext(); ) {
			Ritual r = iter.next();
			if(!r.isValid()) iter.remove();
		}
		Minecraft.getInstance().getIntegratedServer().sendMessage(new StringTextComponent("Succesfully Loaded " + rituals.size() + " Rituals!"), null);
		RitualBuilder.rituals = rituals;
	}
	
	public static Block getActiveBlock(Block block) {
		if(block == ARBlock.RITUAL_STONE_1_RUBY) return ARBlock.RITUAL_STONE_1_RUBY_ACTIVE;
		if(block == ARBlock.RITUAL_STONE_2_RUBY) return ARBlock.RITUAL_STONE_2_RUBY_ACTIVE;
		if(block == ARBlock.RITUAL_STONE_3_RUBY) return ARBlock.RITUAL_STONE_3_RUBY_ACTIVE;
		if(block == ARBlock.RITUAL_STONE_4_RUBY) return ARBlock.RITUAL_STONE_4_RUBY_ACTIVE;
		if(block == ARBlock.RITUAL_STONE_5_RUBY) return ARBlock.RITUAL_STONE_5_RUBY_ACTIVE;
		if(block == ARBlock.RITUAL_STONE_1_PERIDOT) return ARBlock.RITUAL_STONE_1_PERIDOT_ACTIVE;
		if(block == ARBlock.RITUAL_STONE_2_PERIDOT) return ARBlock.RITUAL_STONE_2_PERIDOT_ACTIVE;
		if(block == ARBlock.RITUAL_STONE_3_PERIDOT) return ARBlock.RITUAL_STONE_3_PERIDOT_ACTIVE;
		if(block == ARBlock.RITUAL_STONE_4_PERIDOT) return ARBlock.RITUAL_STONE_4_PERIDOT_ACTIVE;
		if(block == ARBlock.RITUAL_STONE_5_PERIDOT) return ARBlock.RITUAL_STONE_5_PERIDOT_ACTIVE;
		if(block == ARBlock.RITUAL_STONE_1_SAPPHIRE) return ARBlock.RITUAL_STONE_1_SAPPHIRE_ACTIVE;
		if(block == ARBlock.RITUAL_STONE_2_SAPPHIRE) return ARBlock.RITUAL_STONE_2_SAPPHIRE_ACTIVE;
		if(block == ARBlock.RITUAL_STONE_3_SAPPHIRE) return ARBlock.RITUAL_STONE_3_SAPPHIRE_ACTIVE;
		if(block == ARBlock.RITUAL_STONE_4_SAPPHIRE) return ARBlock.RITUAL_STONE_4_SAPPHIRE_ACTIVE;
		if(block == ARBlock.RITUAL_STONE_5_SAPPHIRE) return ARBlock.RITUAL_STONE_5_SAPPHIRE_ACTIVE;
		else return ARBlock.RITUAL_STONE_6_ACTIVE;
	}
	
	public static class Checker {
		
		public Checker(BlockPos pos, String dimension) {
			this.pos = pos;
			this.dimension = dimension;
		}
		
		BlockPos pos;
		String dimension;
		int counter = -1;
		boolean valid = true;
		Iterator<Entry<BlockPos, Option>> iter;
		
	}
	
}

package com.hijackster99.ancientrelics.tileentity.ritual;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.hijackster99.ancientrelics.blocks.ARBlock;
import com.hijackster99.ancientrelics.core.References;
import com.hijackster99.ancientrelics.core.classloader.RitualJsonManager.Option;

import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
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
				Checker check = c.next();
				check.player.displayClientMessage(new StringTextComponent("Checking Rituals..."), false);
				if(check.dimension.equals(event.world.dimension().location().toString())) {
					if(check.iter == null) getNextIter(check, event);
					if(check.valid) {
						if(check.iter != null && !check.iter.hasNext()) {
							check.player.displayClientMessage(new StringTextComponent("Found Valid Ritual!"), false);
							if(BlockTags.getAllTags().getTag(new ResourceLocation("ancientrelics:ritual_type_inactive")).contains(event.world.getBlockState(check.pos).getBlock())) {
								activeRituals.put(check.pos, rituals.get(check.counter));
								event.world.setBlockAndUpdate(check.pos, getActiveBlock(event.world.getBlockState(check.pos).getBlock()).defaultBlockState());
							}
							c.remove();
						}else if(check.iter != null){
							for(int i = 0; i < Ritual.blocksChecked; i++) {
								if(check.iter.hasNext()) {
									Entry<BlockPos, Option> entry = check.iter.next();
									if(entry.getValue().getType().equals(Block.class)) {
										if(event.world.getBlockState(check.pos.offset(entry.getKey())).getBlock() != (Block) entry.getValue().get()) { 
											check.valid = false;
											check.player.displayClientMessage(new StringTextComponent("Check Failed! Bad Block at " + check.pos.offset(entry.getKey()) + "! " + event.world.getBlockState(check.pos.offset(entry.getKey())).getBlock().getRegistryName().toString() + " instead of " + ((Block) entry.getValue().get()).getRegistryName().toString()), false);
										}
									}else if(entry.getValue().getType().equals(Tag.class)) {
										if(!((Tag<Block>) entry.getValue().get()).contains(event.world.getBlockState(check.pos.offset(entry.getKey())).getBlock())) { 
											check.valid = false;
											check.player.displayClientMessage(new StringTextComponent("Check Failed! Bad Block at " + check.pos.offset(entry.getKey()) + "! " + event.world.getBlockState(check.pos.offset(entry.getKey())).getBlock().getRegistryName().toString() + " is not in specified tag collection!"), false);
										}
									}
								}
							}
						}else {
							check.player.displayClientMessage(new StringTextComponent("No Valid Ritual Found!"), false);
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
			check.player.displayClientMessage(new StringTextComponent("Checking Ritual Tier..."), false);
			if(rit.getTier().contains(event.world.getBlockState(check.pos).getBlock())) {
				check.player.displayClientMessage(new StringTextComponent("Tiers Match! Checking: " + rituals.get(check.counter).getRegistryName().toString()), false);
				check.iter = rituals.get(check.counter).getRitualBlocksIter().entrySet().iterator();
				break;
			}
			check.player.displayClientMessage(new StringTextComponent("Check Failed!"), false);
		}
	}
	
	public static void getRituals() {
		List<Ritual> rituals = new ArrayList<Ritual>(GameRegistry.findRegistry(Ritual.class).getValues());
		for(Iterator<Ritual> iter = rituals.iterator(); iter.hasNext(); ) {
			Ritual r = iter.next();
			if(!r.isValid()) iter.remove();
		}
		RitualBuilder.rituals = rituals;
	}
	
	public static Block getActiveBlock(Block block) {
		Set<ResourceLocation> tags = block.getTags();
		Tag<Block> tier = null;
		Tag<Block> type = null;
		Tag<Block> active = (Tag<Block>) BlockTags.getAllTags().getTag(new ResourceLocation("ancientrelics:ritual_type_active"));
		for(ResourceLocation loc : tags) {
			if(loc.getPath().endsWith("active"));
			else if(loc.getPath().startsWith("ritual_type_")) {
				type = (Tag<Block>) BlockTags.getAllTags().getTag(loc);
			}else if(loc.getPath().startsWith("ritual_tier_")) {
				tier = (Tag<Block>) BlockTags.getAllTags().getTag(loc);
			}
		}
		if(tier != null && type != null) {
			for(Block b : active.getValues()) {
				if(tier.contains(b) && type.contains(b)) {
					return b;
				}
			}
		}
		return ARBlock.RITUAL_STONE_1_RUBY_ACTIVE;
	}
	
	public static class Checker {
		
		public Checker(BlockPos pos, String dimension, PlayerEntity player) {
			this.pos = pos;
			this.dimension = dimension;
			this.player = player;
		}
		
		BlockPos pos;
		String dimension;
		PlayerEntity player;
		int counter = -1;
		boolean valid = true;
		Iterator<Entry<BlockPos, Option>> iter;
		
	}
	
}

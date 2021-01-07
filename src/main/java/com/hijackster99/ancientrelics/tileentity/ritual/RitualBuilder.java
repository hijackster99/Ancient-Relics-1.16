package com.hijackster99.ancientrelics.tileentity.ritual;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.hijackster99.ancientrelics.blocks.ARBlock;
import com.hijackster99.ancientrelics.blocks.RitualBlock;
import com.hijackster99.ancientrelics.core.References;

import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.TickEvent.WorldTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.registry.GameRegistry;

@EventBusSubscriber(modid = References.MODID)
public class RitualBuilder {

	public static Map<BlockPos, Ritual> activeRituals = new HashMap<BlockPos, Ritual>();
	public static List<Checker> ritualCheckers = new ArrayList<Checker>();
	public static List<Ritual> rituals = null;
	
	@SubscribeEvent
	public static void worldTick(WorldTickEvent event) {
		if(rituals == null) getRituals();
		for(Iterator<Checker> c = ritualCheckers.iterator(); c.hasNext(); ) {
			Checker check = c.next();
			if(check.dimension.equals(event.world.getDimensionKey().getLocation().toString())) {
				if(check.iter == null) getNextIter(check, event);
				if(check.iter != null && !check.iter.hasNext()) {
					if(check.valid) {
						if(!((RitualBlock) event.world.getBlockState(check.pos).getBlock()).isActive()) {
							activeRituals.put(check.pos, rituals.get(check.counter));
							System.out.println(!((RitualBlock) event.world.getBlockState(check.pos).getBlock()).isActive());
							event.world.setBlockState(check.pos, getActiveBlock(event.world.getBlockState(check.pos).getBlock()).getDefaultState());
							c.remove();
						}
					}
					getNextIter(check, event);
				}else if(check.iter != null){
					for(int i = 0; i < Ritual.blocksChecked; i++) {
						if(check.iter.hasNext()) {
							Entry<BlockPos, Block> entry = check.iter.next();
							if(event.world.getBlockState(check.pos.add(entry.getKey())).getBlock() != entry.getValue()) check.valid = false;
						}
					}
				}else {
					c.remove();
				}
			}
		}
	}
	
	public static void getNextIter(Checker check, WorldTickEvent event) {
		check.iter = null;
		check.counter++;
		for( ;check.counter < rituals.size(); check.counter++){
			Ritual rit = rituals.get(check.counter);
			if(rit.getTier() == ((RitualBlock) event.world.getBlockState(check.pos).getBlock()).getTier()) {
				check.iter = rituals.get(check.counter).getRitualBlocksIter().entrySet().iterator();
				break;
			}
			check.counter++;
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
		Iterator<Entry<BlockPos, Block>> iter;
		
	}
	
}

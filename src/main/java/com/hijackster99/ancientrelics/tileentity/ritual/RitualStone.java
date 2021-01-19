package com.hijackster99.ancientrelics.tileentity.ritual;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Random;

import com.hijackster99.ancientrelics.blocks.ARBlock;
import com.hijackster99.ancientrelics.core.IInteractable;
import com.hijackster99.ancientrelics.core.IRandomUpdate;
import com.hijackster99.ancientrelics.core.classloader.RitualJsonManager.Option;
import com.hijackster99.ancientrelics.tileentity.ARTileEntity;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tags.Tag;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class RitualStone extends ARTileEntity implements ITickableTileEntity, IInteractable, IRandomUpdate {

	private Tag<Block> tier;
	private Tag<Block> type;
	private Ritual ritual = null;
	private Iterator<Entry<BlockPos, Option>> iter;
	private TileEntityWrapper wrapper = new TileEntityWrapper();
	
	public RitualStone() {
		this(null, null);
	}
	
	public RitualStone(Tag<Block> tier, Tag<Block> type) {
		super(ARTileEntity.RITUAL_STONE);
		this.tier = tier;
		this.type = type;
	}

	public Tag<Block> getRitualType() {
		return type;
	}

	public void setRitualType(Tag<Block> type) {
		this.type = type;
	}

	public Tag<Block> getTier() {
		return tier;
	}

	public void setTier(Tag<Block> tier) {
		this.tier = tier;
	}

	public Ritual getRitual() {
		return ritual;
	}

	public void setRitual(Ritual ritual) {
		if(ritual != null) {
			this.ritual = ritual;
			try {
				this.wrapper = this.ritual.wrapper.newInstance();
			} catch (InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			}
			iter =  ritual.getRitualBlocksIter().entrySet().iterator();
		}
	}

	@Override
	public void tick() {
		if(!world.isRemote()) {
			if(ritual == null) {
				setRitual(RitualBuilder.activeRituals.get(pos));
				RitualBuilder.activeRituals.remove(pos);
			}
			if(ritual == null || !checkRitual()) {
				getWorld().removeBlock(pos, false);
				getWorld().setBlockState(pos, getInactiveBlock().getDefaultState());
			}
		}
		wrapper.tick(getWorld(), pos);
	}

	@Override
	public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
		return wrapper.onBlockActivated(state, worldIn, pos, player, handIn, hit);
	}

	@Override
	public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
		wrapper.randomTick(state, worldIn, pos, random);
	}
	
	@SuppressWarnings("unchecked")
	private boolean checkRitual() {
		if(ritual.getTier() != tier) return false;
		for(int i = 0; i < Ritual.blocksChecked; i++) {
			if(iter.hasNext()) {
				Entry<BlockPos, Option> e = iter.next();
				if(e.getValue().getType().equals(Block.class)) {
					if(getWorld().getBlockState(e.getKey().add(pos)).getBlock() != (Block) e.getValue().get()) {
						return false;
					}
				}else if(e.getValue().getType().equals(Tag.class)) {
					if(!((Tag<Block>) e.getValue().get()).contains(getWorld().getBlockState(e.getKey().add(pos)).getBlock())) {
						return false;
					}
				}
			}else {
				iter = ritual.getRitualBlocksIter().entrySet().iterator();
			}
		}
		return true;
	}
	
	private Block getInactiveBlock() {
		return tier != null && type != null ? tier.contains(ARBlock.RITUAL_STONE_1_RUBY) && type.contains(ARBlock.RITUAL_STONE_1_RUBY) ? ARBlock.RITUAL_STONE_1_RUBY : tier.contains(ARBlock.RITUAL_STONE_2_RUBY) && type.contains(ARBlock.RITUAL_STONE_2_RUBY) ? ARBlock.RITUAL_STONE_2_RUBY : tier.contains(ARBlock.RITUAL_STONE_3_RUBY) && type.contains(ARBlock.RITUAL_STONE_3_RUBY) ? ARBlock.RITUAL_STONE_3_RUBY : tier.contains(ARBlock.RITUAL_STONE_4_RUBY) && type.contains(ARBlock.RITUAL_STONE_4_RUBY) ? ARBlock.RITUAL_STONE_4_RUBY : tier.contains(ARBlock.RITUAL_STONE_5_RUBY) && type.contains(ARBlock.RITUAL_STONE_5_RUBY) ? ARBlock.RITUAL_STONE_5_RUBY : tier.contains(ARBlock.RITUAL_STONE_1_PERIDOT) && type.contains(ARBlock.RITUAL_STONE_1_PERIDOT) ? ARBlock.RITUAL_STONE_1_PERIDOT : tier.contains(ARBlock.RITUAL_STONE_2_PERIDOT) && type.contains(ARBlock.RITUAL_STONE_2_PERIDOT) ? ARBlock.RITUAL_STONE_2_PERIDOT : tier.contains(ARBlock.RITUAL_STONE_3_PERIDOT) && type.contains(ARBlock.RITUAL_STONE_3_PERIDOT) ? ARBlock.RITUAL_STONE_3_PERIDOT : tier.contains(ARBlock.RITUAL_STONE_4_PERIDOT) && type.contains(ARBlock.RITUAL_STONE_4_PERIDOT) ? ARBlock.RITUAL_STONE_4_PERIDOT : tier.contains(ARBlock.RITUAL_STONE_5_PERIDOT) && type.contains(ARBlock.RITUAL_STONE_5_PERIDOT) ? ARBlock.RITUAL_STONE_5_PERIDOT : tier.contains(ARBlock.RITUAL_STONE_1_SAPPHIRE) && type.contains(ARBlock.RITUAL_STONE_1_SAPPHIRE) ? ARBlock.RITUAL_STONE_1_SAPPHIRE : tier.contains(ARBlock.RITUAL_STONE_2_SAPPHIRE) && type.contains(ARBlock.RITUAL_STONE_2_SAPPHIRE) ? ARBlock.RITUAL_STONE_2_SAPPHIRE : tier.contains(ARBlock.RITUAL_STONE_3_SAPPHIRE) && type.contains(ARBlock.RITUAL_STONE_3_SAPPHIRE) ? ARBlock.RITUAL_STONE_3_SAPPHIRE : tier.contains(ARBlock.RITUAL_STONE_4_SAPPHIRE) && type.contains(ARBlock.RITUAL_STONE_4_SAPPHIRE) ? ARBlock.RITUAL_STONE_4_SAPPHIRE : tier.contains(ARBlock.RITUAL_STONE_5_SAPPHIRE) && type.contains(ARBlock.RITUAL_STONE_5_SAPPHIRE) ? ARBlock.RITUAL_STONE_5_SAPPHIRE : tier.contains(ARBlock.RITUAL_STONE_6) && type.contains(ARBlock.RITUAL_STONE_6) ? ARBlock.RITUAL_STONE_6 : ARBlock.RITUAL_STONE_1_RUBY : ARBlock.RITUAL_STONE_1_RUBY;
	}

}

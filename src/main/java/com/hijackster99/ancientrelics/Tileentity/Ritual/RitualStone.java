package com.hijackster99.ancientrelics.tileentity.ritual;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Random;

import com.hijackster99.ancientrelics.blocks.ARBlock;
import com.hijackster99.ancientrelics.core.EnumRitualType;
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

	private int tier;
	private EnumRitualType type;
	private Ritual ritual = null;
	private Iterator<Entry<BlockPos, Option>> iter;
	private TileEntityWrapper wrapper = new TileEntityWrapper();
	
	public RitualStone() {
		this(0, EnumRitualType.RUBY);
	}
	
	public RitualStone(int tier, EnumRitualType type) {
		super(ARTileEntity.RITUAL_STONE);
		this.tier = tier;
		this.type = type;
	}

	public EnumRitualType getRitualType() {
		return type;
	}

	public void setRitualType(EnumRitualType type) {
		this.type = type;
	}

	public int getTier() {
		return tier;
	}

	public void setTier(int tier) {
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
		switch(type.ordinal()) {
		case 0:
			switch(tier) {
			case 1:
				return ARBlock.RITUAL_STONE_1_RUBY;
			case 2:
				return ARBlock.RITUAL_STONE_2_RUBY;
			case 3:
				return ARBlock.RITUAL_STONE_3_RUBY;
			case 4:
				return ARBlock.RITUAL_STONE_4_RUBY;
			case 5:
				return ARBlock.RITUAL_STONE_5_RUBY;
			}
			break;
		case 1:
			switch(tier) {
			case 1:
				return ARBlock.RITUAL_STONE_1_PERIDOT;
			case 2:
				return ARBlock.RITUAL_STONE_2_PERIDOT;
			case 3:
				return ARBlock.RITUAL_STONE_3_PERIDOT;
			case 4:
				return ARBlock.RITUAL_STONE_4_PERIDOT;
			case 5:
				return ARBlock.RITUAL_STONE_5_PERIDOT;
			}
			break;
		case 2:
			switch(tier) {
			case 1:
				return ARBlock.RITUAL_STONE_1_SAPPHIRE;
			case 2:
				return ARBlock.RITUAL_STONE_2_SAPPHIRE;
			case 3:
				return ARBlock.RITUAL_STONE_3_SAPPHIRE;
			case 4:
				return ARBlock.RITUAL_STONE_4_SAPPHIRE;
			case 5:
				return ARBlock.RITUAL_STONE_5_SAPPHIRE;
			}
			break;
		case 3:
			switch(tier) {
			case 6:
				return ARBlock.RITUAL_STONE_6;
			}
			break;
		}
		return ARBlock.RITUAL_STONE_1_RUBY;
	}

}

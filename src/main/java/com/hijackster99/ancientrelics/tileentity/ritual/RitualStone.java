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
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.Tag;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class RitualStone extends ARTileEntity implements ITickableTileEntity, IInteractable, IRandomUpdate, ICapabilityProvider {

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
			iter = ritual.getRitualBlocksIter().entrySet().iterator();
		}
	}
	
	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
		return wrapper.getCapability(cap, side);
	}
	
	@Override
	public void load(BlockState state, CompoundNBT nbt) {
		super.load(state, nbt);
		String ritual = nbt.getString("ritual");
		String tier = nbt.getString("tier");
		String type = nbt.getString("type");
		setRitual(!ritual.equals("null") ? GameRegistry.findRegistry(Ritual.class).getValue(new ResourceLocation(ritual)) : null);
		this.type = (Tag<Block>) BlockTags.getAllTags().getTag(new ResourceLocation(type));
		this.tier = (Tag<Block>) BlockTags.getAllTags().getTag(new ResourceLocation(tier));
		wrapper.read(state, nbt);
	}
	
	@Override
	public CompoundNBT save(CompoundNBT compound) {
		compound = wrapper.write(compound);
		String tier = "";
		String type = "";
		for(ResourceLocation loc : this.getBlockState().getBlock().getTags()) {
			String name = loc.toString();
			if(name.endsWith("active"));
			else if(name.startsWith(":ritual_type_", name.indexOf(':'))) type = name;
			else if(name.startsWith(":ritual_tier_", name.indexOf(':'))) tier = name;
		}
		compound.putString("ritual", ritual != null ? ritual.getRegistryName().toString() : "null");
		compound.putString("tier", tier);
		compound.putString("type", type);
		return super.save(compound);
	}

	@Override
	public void tick() {
		if(level.isClientSide()) {
			if(ritual == null) {
				setRitual(RitualBuilder.activeRituals.get(worldPosition));
				RitualBuilder.activeRituals.remove(worldPosition);
			}
			if(ritual == null || !checkRitual()) {
				getLevel().removeBlock(worldPosition, false);
				getLevel().setBlockAndUpdate(worldPosition, getInactiveBlock().defaultBlockState());
			}
		}
		wrapper.tick(getLevel(), worldPosition);
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
					if(getLevel().getBlockState(e.getKey().offset(worldPosition)).getBlock() != (Block) e.getValue().get()) {
						return false;
					}
				}else if(e.getValue().getType().equals(Tag.class)) {
					if(!((Tag<Block>) e.getValue().get()).contains(getLevel().getBlockState(e.getKey().offset(worldPosition)).getBlock())) {
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
		if(tier != null && type != null) {
			Tag<Block> inactive = (Tag<Block>) BlockTags.getAllTags().getTag(new ResourceLocation("ancientrelics:ritual_type_inactive"));
			for(Block b : inactive.getValues()) {
				System.out.println(type.getValues());
				if(tier.contains(b) && type.contains(b)) {
					return b;
				}
			}
		}
		return ARBlock.RITUAL_STONE_1_RUBY;
	}

}

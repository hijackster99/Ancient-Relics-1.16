package com.hijackster99.ancientrelics.tileentity.ritual;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Random;

import com.hijackster99.ancientrelics.blocks.ARBlock;
import com.hijackster99.ancientrelics.core.IInteractable;
import com.hijackster99.ancientrelics.core.IRandomUpdate;
import com.hijackster99.ancientrelics.core.classloader.RitualJsonManager.Option;
import com.hijackster99.ancientrelics.tileentity.ARTileEntity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.Tag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.registries.RegistryManager;

public class RitualStone extends ARTileEntity implements IInteractable, IRandomUpdate, ICapabilityProvider, BlockEntityTicker<RitualStone> {

	private int tier;
	private Tag<Block> type;
	private Ritual ritual = null;
	private Iterator<Entry<BlockPos, Option>> iter;
	private TileEntityWrapper wrapper = new TileEntityWrapper();
	
	public RitualStone(BlockPos pos, BlockState state)
	{
		this(-1, null, pos, state);
	}
	
	public RitualStone(int tier, Tag<Block> type, BlockPos pos, BlockState state) {
		super(ARTileEntity.RITUAL_STONE, pos, state);
		this.tier = tier;
		this.type = type;
	}

	public Tag<Block> getRitualType() {
		return type;
	}

	public void setRitualType(Tag<Block> type) {
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

	@SuppressWarnings("deprecation")
	public void setRitual(Ritual ritual) {
		if(ritual != null) {
			this.ritual = ritual;
			try {
				this.wrapper = this.ritual.wrapper.newInstance();
				this.wrapper.ritual = ritual;
				this.wrapper.type = type;
				this.wrapper.init(level, worldPosition);
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
	public void load(CompoundTag nbt) {
		super.load(nbt);
		String ritual = nbt.getString("ritual");
		this.tier = nbt.getInt("tier");
		String type = nbt.getString("type");
		setRitual(!ritual.equals("null") ? RegistryManager.ACTIVE.getRegistry(Ritual.class).getValue(new ResourceLocation(ritual)) : null);
		this.type = (Tag<Block>) BlockTags.getAllTags().getTag(new ResourceLocation(type));
		wrapper.read(nbt);
	}
	
	@Override
	public CompoundTag save(CompoundTag compound) {
		compound = wrapper.write(compound);
		int tier = -1;
		String type = "";
		for(ResourceLocation loc : this.getBlockState().getBlock().getTags()) {
			String name = loc.toString();
			if(name.endsWith("active"));
			else if(name.startsWith(":ritual_type_", name.indexOf(':'))) type = name;
			else if(name.startsWith(":ritual_tier_", name.indexOf(':'))) tier = Integer.valueOf(name.substring(name.length() - 1));
		}
		
		compound.putString("ritual", ritual != null ? ritual.getRegistryName().toString() : "null");
		compound.putInt("tier", tier);
		compound.putString("type", type);
		return super.save(compound);
	}

	@Override
	public void tick(Level worldIn, BlockPos pos, BlockState stateIn, RitualStone te) {
		if(!worldIn.isClientSide()) {
			if(ritual == null) {
				setRitual(RitualBuilder.activeRituals.get(pos));
				RitualBuilder.activeRituals.remove(pos);
			}
			if(ritual == null || !checkRitual()) {
				worldIn.removeBlock(pos, false);
				worldIn.setBlockAndUpdate(pos, getInactiveBlock().defaultBlockState());
			}
		}
		wrapper.tick(worldIn, pos);
	}

	@Override
	public InteractionResult onBlockActivated(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
		return wrapper.onBlockActivated(state, worldIn, pos, player, handIn, hit);
	}

	@Override
	public void randomTick(BlockState state, ServerLevel worldIn, BlockPos pos, Random random) {
		wrapper.randomTick(state, worldIn, pos, random);
	}
	
	@SuppressWarnings("unchecked")
	private boolean checkRitual() {
		if(ritual.getTier() != tier) return false;
		for(int i = 0; i < Ritual.blocksChecked; i++) {
			if(iter.hasNext()) {
				Entry<BlockPos, Option> e = iter.next();
				if(e.getValue().isBlock()) {
					if(getLevel().getBlockState(e.getKey().offset(worldPosition)).getBlock() != (Block) e.getValue().get()) {
						return false;
					}
				}else if(!e.getValue().isBlock()) {
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
		if(tier != -1 && type != null) {
			Tag<Block> inactive = (Tag<Block>) BlockTags.getAllTags().getTag(new ResourceLocation("ancientrelics:ritual_type_inactive"));
			for(Block b : inactive.getValues()) {
				System.out.println(type.getValues());
				if(BlockTags.getAllTags().getTag(new ResourceLocation("ancientrelics:ritual_tier_" + tier)).contains(b) && type.contains(b)) {
					return b;
				}
			}
		}
		return ARBlock.RITUAL_STONE_1_RUBY;
	}

	@Override
	public BlockEntityType<?> getType() {
		return ARTileEntity.RITUAL_STONE;
	}

}

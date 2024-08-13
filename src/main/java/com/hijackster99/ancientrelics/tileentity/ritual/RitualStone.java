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
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.registries.RegistryManager;

public class RitualStone extends ARTileEntity implements IInteractable, IRandomUpdate, ICapabilityProvider {

	private int tier;
	private Tag<Block> type;
	private Ritual ritual = null;
	private Iterator<Entry<BlockPos, Option>> iter;
	private TileEntityWrapper wrapper = new TileEntityWrapper();
	private boolean changed = true;
	
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
	public void setRitual() {
		if(ritual != null) {
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
		setChanged();
	}
	
	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
		return wrapper.getCapability(cap, side);
	}
	
	@Override
	public void load(CompoundTag nbt) {
		super.load(nbt);
		String ritual = nbt.getString("ritual");
		this.ritual = !ritual.equals("null") ? RegistryManager.ACTIVE.getRegistry(Ritual.class).getValue(new ResourceLocation(ritual)) : null;
		wrapper.read(nbt);
	}
	
	@Override
	protected void saveAdditional(CompoundTag compound) {
		super.saveAdditional(compound);

		compound = wrapper.write(compound);
		compound.putString("ritual", ritual != null ? ritual.getRegistryName().toString() : "null");
	}

	public static <T extends BlockEntity> void tick(Level worldIn, BlockPos pos, BlockState stateIn, T te) {
		if(te instanceof RitualStone && worldIn.getBlockState(pos).getBlock() != Blocks.AIR)
		{
			RitualStone rs = (RitualStone) te;
			if(rs.changed && rs.level != null) {
				for(ResourceLocation rl : stateIn.getBlock().getTags()) {
					if(rl.getPath().startsWith("ritual_tier")) rs.tier = Integer.valueOf(rl.getPath().substring(rl.getPath().length() - 1));
					else if(rl.getPath().startsWith("ritual_type")) rs.type = (Tag<Block>) BlockTags.getAllTags().getTag(rl);
				}
				rs.setRitual();
				rs.setChanged();
				rs.changed = false;
			}
			if(!worldIn.isClientSide()) {
				if(rs.ritual == null) {
					rs.ritual = RitualBuilder.activeRituals.get(pos);
					rs.setRitual();
					RitualBuilder.activeRituals.remove(pos);
				}
				if(rs.ritual == null || !rs.checkRitual()) {
					worldIn.removeBlock(pos, false);
					worldIn.setBlockAndUpdate(pos, rs.getInactiveBlock().defaultBlockState());
				}
			}
			rs.wrapper.tick(worldIn, pos);
		}
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
		if(tier == 0 || type == null) getRitualStats();
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
	
	private void getRitualStats()
	{
		for(ResourceLocation rl : this.getLevel().getBlockState(this.worldPosition).getBlock().getTags()) {
			if(rl.getPath().startsWith("ritual_tier")) tier = Integer.valueOf(rl.getPath().substring(rl.getPath().length() - 1));
			else if(rl.getPath().endsWith("active"));
			else if(rl.getPath().startsWith("ritual_type")) type = (Tag<Block>) BlockTags.getAllTags().getTag(rl);
		}
	}
	
	private Block getInactiveBlock() {
		System.out.println(worldPosition);
		System.out.println(this.level.getBlockState(worldPosition).getBlock());
		Tag<Block> tier = BlockTags.getAllTags().getTag(new ResourceLocation("ancientrelics:ritual_tier_1"));
		Tag<Block> type = BlockTags.getAllTags().getTag(new ResourceLocation("ancientrelics:ritual_type_ruby"));
		for(ResourceLocation rl : this.getLevel().getBlockState(this.worldPosition).getBlock().getTags()) {
			if(rl.getPath().startsWith("ritual_tier")) tier = (Tag<Block>) BlockTags.getAllTags().getTag(rl);
			else if(rl.getPath().endsWith("active"));
			else if(rl.getPath().startsWith("ritual_type")) type = (Tag<Block>) BlockTags.getAllTags().getTag(rl);
		}
		Tag<Block> inactive = (Tag<Block>) BlockTags.getAllTags().getTag(new ResourceLocation("ancientrelics:ritual_type_inactive"));
		for(Block b : inactive.getValues()) {
			if(tier.contains(b) && type.contains(b)) {
				return b;
			}
		}
		return ARBlock.RITUAL_STONE_1_RUBY;
	}

	@Override
	public BlockEntityType<?> getType() {
		return ARTileEntity.RITUAL_STONE;
	}

}

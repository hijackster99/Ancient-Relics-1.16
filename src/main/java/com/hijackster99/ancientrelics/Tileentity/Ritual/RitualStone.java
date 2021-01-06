package com.hijackster99.ancientrelics.Tileentity.Ritual;

import java.util.Random;

import com.hijackster99.ancientrelics.Tileentity.ARTileEntity;
import com.hijackster99.ancientrelics.core.EnumRitualType;
import com.hijackster99.ancientrelics.core.IInteractable;
import com.hijackster99.ancientrelics.core.IRandomUpdate;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
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
	private TileEntityWrapper wrapper = new TileEntityWrapper();
	
	public RitualStone() {
		this(0, null);
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
		this.ritual = ritual;
		try {
			this.wrapper = this.ritual.wrapper.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void tick() {
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

}

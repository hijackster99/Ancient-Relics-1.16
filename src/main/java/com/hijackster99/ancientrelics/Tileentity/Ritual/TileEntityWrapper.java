package com.hijackster99.ancientrelics.Tileentity.Ritual;

import com.hijackster99.ancientrelics.core.IInteractable;
import com.hijackster99.ancientrelics.core.IRandomUpdate;
import com.hijackster99.ancientrelics.core.IUpdate;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;

public class TileEntityWrapper {

	Class<?> te;
	
	public TileEntityWrapper(Class<?> te) {
		this.te = te;
	}
	
	public void tick(World worldObj, BlockPos pos) {
		if(te.isInstance(IUpdate.class)) {
			IUpdate update = (IUpdate) te.cast(IUpdate.class);
			update.tick(worldObj, pos);
		}
	}
	
	public void randomTick(World worldObj, BlockPos pos) {
		if(te.isInstance(IRandomUpdate.class)) {
			IRandomUpdate update = (IRandomUpdate) te.cast(IRandomUpdate.class);
			update.randomTick(worldObj, pos);
		}
	}
	
	public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
		if(te.isInstance(IInteractable.class)) {
			IInteractable interact = (IInteractable) te.cast(IInteractable.class);
			return interact.onBlockActivated(state, worldIn, pos, player, handIn, hit);
		}
		return ActionResultType.PASS;
	}
	
}

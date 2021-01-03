package com.hijackster99.ancientrelics.Tileentity.Ritual;

import java.util.Random;

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
import net.minecraft.world.server.ServerWorld;

public class TileEntityWrapper {

	Class<?> te;
	
	public TileEntityWrapper(Class<?> te) {
		this.te = te;
	}
	
	public void tick(World worldObj, BlockPos pos) {
		if(contains(te.getInterfaces(), IUpdate.class)) {
			IUpdate update;
			try {
				update = (IUpdate) te.newInstance();
				update.tick(worldObj, pos);
			} catch (InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
		if(contains(te.getInterfaces(), IRandomUpdate.class)) {
			IRandomUpdate update;
			try {
				update = (IRandomUpdate) te.newInstance();
				update.randomTick(state, worldIn, pos, random);
			} catch (InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}
	
	public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
		if(contains(te.getInterfaces(), IInteractable.class)) {
			try {
				IInteractable interact = (IInteractable) te.newInstance();
				interact.onBlockActivated(state, worldIn, pos, player, handIn, hit);
			} catch (InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return ActionResultType.PASS;
	}
	
	public boolean contains(Class<?>[] classes, Class<?> c){
		for(Class<?> cl : classes) {
			if(cl.equals(c)) return true;
		}
		return false;
	}
	
}

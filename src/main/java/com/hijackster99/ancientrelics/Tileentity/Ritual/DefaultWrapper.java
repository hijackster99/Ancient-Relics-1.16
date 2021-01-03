package com.hijackster99.ancientrelics.Tileentity.Ritual;

import com.hijackster99.ancientrelics.Items.ARItem;
import com.hijackster99.ancientrelics.core.IInteractable;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;

public class DefaultWrapper implements IInteractable {

	@Override
	public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
		if(player.getHeldItem(handIn).getItem() == ARItem.RUBY_STAFF) {
			return ActionResultType.SUCCESS;
		}
		return ActionResultType.PASS;
	}

}

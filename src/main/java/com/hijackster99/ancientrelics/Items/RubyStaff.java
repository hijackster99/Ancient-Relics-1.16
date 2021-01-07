package com.hijackster99.ancientrelics.items;

import com.hijackster99.ancientrelics.blocks.RitualBlock;
import com.hijackster99.ancientrelics.tileentity.ritual.RitualBuilder;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;

public class RubyStaff extends ARItem{

	public RubyStaff(String registryName, int maxStack, ItemGroup tab) {
		super(registryName, maxStack, tab);
	}
	
	@Override
	public ActionResultType onItemUse(ItemUseContext context) {
		if(context.getWorld().getBlockState(context.getPos()).getBlock() instanceof RitualBlock && !((RitualBlock) context.getWorld().getBlockState(context.getPos()).getBlock()).isActive()) {
			RitualBuilder.ritualCheckers.add(new RitualBuilder.Checker(context.getPos(), context.getWorld().getDimensionKey().getLocation().toString()));
			return ActionResultType.SUCCESS;
		}
		return ActionResultType.PASS;
	}

}

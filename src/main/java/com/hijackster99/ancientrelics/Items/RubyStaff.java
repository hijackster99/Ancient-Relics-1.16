package com.hijackster99.ancientrelics.items;

import com.hijackster99.ancientrelics.tileentity.ritual.RitualBuilder;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemUseContext;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.ResourceLocation;

public class RubyStaff extends ARItem{

	public RubyStaff(String registryName, int maxStack, ItemGroup tab) {
		super(registryName, maxStack, tab);
	}
	
	@Override
	public ActionResultType onItemUse(ItemUseContext context) {
		if(BlockTags.getCollection().get(new ResourceLocation("ancientrelics:ritual_type_inactive")) != null && BlockTags.getCollection().get(new ResourceLocation("ancientrelics:ritual_type_inactive")).contains(context.getWorld().getBlockState(context.getPos()).getBlock())) {
			RitualBuilder.ritualCheckers.add(new RitualBuilder.Checker(context.getPos(), context.getWorld().getDimensionKey().getLocation().toString()));
			return ActionResultType.SUCCESS;
		}
		return super.onItemUse(context);
	}

}

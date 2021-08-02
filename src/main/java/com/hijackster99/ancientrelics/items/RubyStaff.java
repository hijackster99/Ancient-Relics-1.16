package com.hijackster99.ancientrelics.items;

import com.hijackster99.ancientrelics.tileentity.ritual.RitualBuilder;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class RubyStaff extends ARItem{

	public RubyStaff(String registryName, int maxStack, ItemGroup tab) {
		super(registryName, maxStack, tab);
	}
	
	@Override
	public ActionResultType useOn(ItemUseContext context) {
		if(BlockTags.getAllTags().getTag(new ResourceLocation("ancientrelics:ritual_type_inactive")) != null && BlockTags.getAllTags().getTag(new ResourceLocation("ancientrelics:ritual_type_inactive")).contains(context.getLevel().getBlockState(context.getClickedPos()).getBlock())) {
			RitualBuilder.ritualCheckers.add(new RitualBuilder.Checker(context.getClickedPos(), context.getLevel().dimension().getRegistryName().toString()));
			return ActionResultType.SUCCESS;
		}
		return super.useOn(context);
	}
	
	@Override
	public ActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn) {
		if(playerIn.isCrouching()) {
			if(handIn == Hand.MAIN_HAND) {
				playerIn.inventory.setItem(playerIn.inventory.selected, new ItemStack(ARItem.SAPPHIRE_STAFF, 1));
			}else {
				playerIn.inventory.setItem(45, new ItemStack(ARItem.SAPPHIRE_STAFF, 1));
			}
		}
		return super.use(worldIn, playerIn, handIn);
	}

}

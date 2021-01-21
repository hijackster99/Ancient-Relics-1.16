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
	public ActionResultType onItemUse(ItemUseContext context) {
		if(BlockTags.getCollection().get(new ResourceLocation("ancientrelics:ritual_type_inactive")) != null && BlockTags.getCollection().get(new ResourceLocation("ancientrelics:ritual_type_inactive")).contains(context.getWorld().getBlockState(context.getPos()).getBlock())) {
			RitualBuilder.ritualCheckers.add(new RitualBuilder.Checker(context.getPos(), context.getWorld().getDimensionKey().getLocation().toString()));
			return ActionResultType.SUCCESS;
		}
		return super.onItemUse(context);
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
		if(playerIn.isSneaking()) {
			if(handIn == Hand.MAIN_HAND) {
				playerIn.inventory.setInventorySlotContents(playerIn.inventory.currentItem, new ItemStack(ARItem.SAPPHIRE_STAFF, 1));
			}else {
				playerIn.inventory.setInventorySlotContents(45, new ItemStack(ARItem.SAPPHIRE_STAFF, 1));
			}
		}
		return super.onItemRightClick(worldIn, playerIn, handIn);
	}

}

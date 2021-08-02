package com.hijackster99.ancientrelics.items;

import com.hijackster99.ancientrelics.blocks.VoidGas;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class PeridotStaff extends ARItem{

	public PeridotStaff(String registryName, int maxStack, ItemGroup tab) {
		super(registryName, maxStack, tab);
	}
	
	@Override
	public ActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn) {
		if(playerIn.isCrouching()) {
			if(handIn == Hand.MAIN_HAND) {
				playerIn.inventory.setItem(playerIn.inventory.selected, new ItemStack(ARItem.RUBY_STAFF, 1));
			}else {
				playerIn.inventory.setItem(45, new ItemStack(ARItem.RUBY_STAFF, 1));
			}
		}
		return super.use(worldIn, playerIn, handIn);
	}
	
	@Override
	public ActionResultType useOn(ItemUseContext context) {
		System.out.println(VoidGas.VOID_GAS_FLOWING);
		
		return super.useOn(context);
	}

}

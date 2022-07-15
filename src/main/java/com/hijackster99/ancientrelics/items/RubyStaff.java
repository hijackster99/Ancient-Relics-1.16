package com.hijackster99.ancientrelics.items;

import com.hijackster99.ancientrelics.tileentity.ritual.RitualBuilder;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

public class RubyStaff extends ARItem{

	public RubyStaff(String registryName, int maxStack, CreativeModeTab tab) {
		super(registryName, maxStack, tab);
	}
	
	@Override
	public InteractionResult useOn(UseOnContext context) {
		if(BlockTags.getAllTags().getTag(new ResourceLocation("ancientrelics:ritual_type_inactive")) != null && BlockTags.getAllTags().getTag(new ResourceLocation("ancientrelics:ritual_type_inactive")).contains(context.getLevel().getBlockState(context.getClickedPos()).getBlock())) {
			RitualBuilder.ritualCheckers.add(new RitualBuilder.Checker(context.getClickedPos(), context.getLevel().dimension().location().toString(), context.getPlayer()));
			return InteractionResult.SUCCESS;
		}
		return super.useOn(context);
	}
	
	@Override
	public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
		if(playerIn.isCrouching()) {
			if(handIn == InteractionHand.MAIN_HAND) {
				playerIn.getInventory().setItem(playerIn.getInventory().selected, new ItemStack(ARItem.SAPPHIRE_STAFF, 1));
			}else {
				playerIn.getInventory().setItem(45, new ItemStack(ARItem.SAPPHIRE_STAFF, 1));
			}
		}
		return super.use(worldIn, playerIn, handIn);
	}

}

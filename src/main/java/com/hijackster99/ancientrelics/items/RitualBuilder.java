package com.hijackster99.ancientrelics.items;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.hijackster99.ancientrelics.core.classloader.RitualJsonManager.Option;

import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

public class RitualBuilder extends ARItem{

	Map<BlockPos, Iterator<Entry<BlockPos, Option>>> map;
	
	public RitualBuilder(String registryName, int maxStack, ItemGroup tab) {
		super(registryName, maxStack, tab);
		map = new HashMap<BlockPos, Iterator<Entry<BlockPos, Option>>>();
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
		if(!worldIn.isRemote()) {
			if(com.hijackster99.ancientrelics.tileentity.ritual.RitualBuilder.rituals == null) com.hijackster99.ancientrelics.tileentity.ritual.RitualBuilder.getRituals();
			if(!playerIn.getHeldItem(handIn).hasTag()) playerIn.getHeldItem(handIn).setTag(new CompoundNBT());
			if(!playerIn.getHeldItem(handIn).getTag().contains("ritual")) playerIn.getHeldItem(handIn).getTag().putInt("ritual", 0);
			int ritual = playerIn.getHeldItem(handIn).getTag().getInt("ritual");
			if(playerIn.isSneaking()) {
				if(ritual == 0) {
					ritual = com.hijackster99.ancientrelics.tileentity.ritual.RitualBuilder.rituals.size() - 1;
				}else {
					ritual--;
				}
			}else {
				if(ritual == com.hijackster99.ancientrelics.tileentity.ritual.RitualBuilder.rituals.size() - 1) {
					ritual = 0;
				}else {
					ritual++;
				}
			}
			playerIn.getHeldItem(handIn).getTag().putInt("ritual", ritual);
			playerIn.sendStatusMessage(new StringTextComponent(com.hijackster99.ancientrelics.tileentity.ritual.RitualBuilder.rituals.get(ritual).getRegistryName().toString()), true);
		}
		return super.onItemRightClick(worldIn, playerIn, handIn);
	}
	
	@SuppressWarnings({ "unchecked" })
	@Override
	public ActionResultType onItemUse(ItemUseContext context) {
		if(!context.getWorld().isRemote()) {
			if(com.hijackster99.ancientrelics.tileentity.ritual.RitualBuilder.rituals == null) com.hijackster99.ancientrelics.tileentity.ritual.RitualBuilder.getRituals();
			if(!context.getItem().hasTag()) context.getItem().setTag(new CompoundNBT());
			if(!context.getItem().getTag().contains("ritual")) context.getItem().getTag().putInt("ritual", 0);
			int ritual = context.getItem().getTag().getInt("ritual");
			if(map.containsKey(context.getPos())) {
				if(!context.getPlayer().isSneaking()) {
					Iterator<Entry<BlockPos, Option>> iter = map.get(context.getPos());
					if(iter.hasNext()) {
						Entry<BlockPos, Option> entry = iter.next();
						System.out.println(1);
						System.out.println(context.getWorld().getBlockState(entry.getKey()));
						if(context.getWorld().getBlockState(context.getPos().add(entry.getKey())).getMaterial().isReplaceable()) {
							System.out.println(2);
							if(context.getPlayer().isCreative()) {
								System.out.println(3);
								if(entry.getValue().getType().equals(Block.class)) {
									System.out.println(4);
									context.getWorld().setBlockState(context.getPos().add(entry.getKey()), ((Block) entry.getValue().get()).getDefaultState());
								}else {
									System.out.println(5);
									context.getWorld().setBlockState(context.getPos().add(entry.getKey()), ((Tag<Block>) entry.getValue().get()).getAllElements().get(0).getDefaultState());
								}
							}else {
								if(entry.getValue().getType().equals(Block.class)) {
									Block block = (Block) entry.getValue().get();
									int slot = context.getPlayer().inventory.getSlotFor(new ItemStack(block));
									if(slot != -1) {
										context.getWorld().setBlockState(context.getPos().add(entry.getKey()), block.getDefaultState());
										context.getPlayer().inventory.decrStackSize(slot, 1);
									}
								}else {
									Tag<Block> tag = (Tag<Block>) entry.getValue().get();
									for(Block block : tag.getAllElements()) {
										int slot = context.getPlayer().inventory.getSlotFor(new ItemStack(block));
										if(slot != -1) {
											context.getWorld().setBlockState(context.getPos().add(entry.getKey()), block.getDefaultState());
											context.getPlayer().inventory.decrStackSize(slot, 1);
											break;
										}
									}
								}
							}
						}
					}else {
						map.remove(context.getPos());
					}
				}
				return ActionResultType.SUCCESS;
			}else {
				if(BlockTags.getCollection().get(new ResourceLocation("ancientrelics:ritual_type_inactive")).contains(context.getWorld().getBlockState(context.getPos()).getBlock())) {
					if(com.hijackster99.ancientrelics.tileentity.ritual.RitualBuilder.rituals.get(ritual).getTier().contains(context.getWorld().getBlockState(context.getPos()).getBlock())){
						map.put(context.getPos(), com.hijackster99.ancientrelics.tileentity.ritual.RitualBuilder.rituals.get(ritual).getRitualBlocksIter().entrySet().iterator());
					}
					return ActionResultType.SUCCESS;
				}
			}
		}else {
			if(BlockTags.getCollection().get(new ResourceLocation("ancientrelics:ritual_type_inactive")).contains(context.getWorld().getBlockState(context.getPos()).getBlock())) {
				return ActionResultType.SUCCESS;
			}
		}
		return super.onItemUse(context);
	}

}

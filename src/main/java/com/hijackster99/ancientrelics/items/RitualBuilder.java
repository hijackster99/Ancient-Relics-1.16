package com.hijackster99.ancientrelics.items;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.hijackster99.ancientrelics.core.classloader.RitualJsonManager.Option;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.Tag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

public class RitualBuilder extends ARItem{

	Map<BlockPos, Iterator<Entry<BlockPos, Option>>> map;
	
	public RitualBuilder(String registryName, int maxStack, CreativeModeTab tab) {
		super(registryName, maxStack, tab);
		map = new HashMap<BlockPos, Iterator<Entry<BlockPos, Option>>>();
	}
	
	@Override
	public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
		if(!worldIn.isClientSide()) {
			if(com.hijackster99.ancientrelics.tileentity.ritual.RitualBuilder.rituals == null) com.hijackster99.ancientrelics.tileentity.ritual.RitualBuilder.getRituals();
			if(!playerIn.getItemInHand(handIn).hasTag()) playerIn.getItemInHand(handIn).setTag(new CompoundTag());
			if(!playerIn.getItemInHand(handIn).getTag().contains("ritual")) playerIn.getItemInHand(handIn).getTag().putInt("ritual", 0);
			int ritual = playerIn.getItemInHand(handIn).getTag().getInt("ritual");
			if(playerIn.isCrouching()) {
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
			playerIn.getItemInHand(handIn).getTag().putInt("ritual", ritual);
			playerIn.displayClientMessage(new TextComponent(com.hijackster99.ancientrelics.tileentity.ritual.RitualBuilder.rituals.get(ritual).getRegistryName().toString()), true);
		}
		return super.use(worldIn, playerIn, handIn);
	}
	
	@SuppressWarnings({ "unchecked" })
	@Override
	public InteractionResult useOn(UseOnContext context) {
		if(!context.getLevel().isClientSide()) {
			if(com.hijackster99.ancientrelics.tileentity.ritual.RitualBuilder.rituals == null) com.hijackster99.ancientrelics.tileentity.ritual.RitualBuilder.getRituals();
			if(!context.getItemInHand().hasTag()) context.getItemInHand().setTag(new CompoundTag());
			if(!context.getItemInHand().getTag().contains("ritual")) context.getItemInHand().getTag().putInt("ritual", 0);
			int ritual = context.getItemInHand().getTag().getInt("ritual");
			if(map.containsKey(context.getClickedPos())) {
				if(!context.getPlayer().isCrouching()) {
					Iterator<Entry<BlockPos, Option>> iter = map.get(context.getClickedPos());
					if(iter.hasNext()) {
						Entry<BlockPos, Option> entry = iter.next();
						System.out.println(1);
						System.out.println(context.getLevel().getBlockState(entry.getKey()));
						if(context.getLevel().getBlockState(context.getClickedPos().offset(entry.getKey())).getMaterial().isReplaceable()) {
							System.out.println(2);
							if(context.getPlayer().isCreative()) {
								System.out.println(3);
								if(entry.getValue().isBlock()) {
									System.out.println(4);
									context.getLevel().setBlockAndUpdate(context.getClickedPos().offset(entry.getKey()), ((Block) entry.getValue().get()).defaultBlockState());
								}else {
									System.out.println(5);
									context.getLevel().setBlockAndUpdate(context.getClickedPos().offset(entry.getKey()), ((Tag<Block>) entry.getValue().get()).getValues().get(0).defaultBlockState());
								}
							}else {
								if(entry.getValue().isBlock()) {
									Block block = (Block) entry.getValue().get();
									HashSet<Item> items = new HashSet<Item>();
									items.add(block.asItem());
									if(context.getPlayer().getInventory().hasAnyOf(items)) {
										context.getLevel().setBlockAndUpdate(context.getClickedPos().offset(entry.getKey()), block.defaultBlockState());
										context.getPlayer().getInventory().removeItem(new ItemStack(block, 1));
									}
								}else {
									Tag<Block> tag = (Tag<Block>) entry.getValue().get();
									for(Block block : tag.getValues()) {
										HashSet<Item> items = new HashSet<Item>();
										items.add(block.asItem());
										if(context.getPlayer().getInventory().hasAnyOf(items)) {
											context.getLevel().setBlockAndUpdate(context.getClickedPos().offset(entry.getKey()), block.defaultBlockState());
											context.getPlayer().getInventory().removeItem(new ItemStack(block, 1));
											break;
										}
									}
								}
							}
						}
					}else {
						map.remove(context.getClickedPos());
					}
				}
				return InteractionResult.SUCCESS;
			}else {
				if(BlockTags.getAllTags().getTag(new ResourceLocation("ancientrelics:ritual_type_inactive")).contains(context.getLevel().getBlockState(context.getClickedPos()).getBlock())) {
					if(BlockTags.getAllTags().getTag(new ResourceLocation("ancientrelics:ritual_tier_" + com.hijackster99.ancientrelics.tileentity.ritual.RitualBuilder.rituals.get(ritual).getTier())).contains(context.getLevel().getBlockState(context.getClickedPos()).getBlock())){
						map.put(context.getClickedPos(), com.hijackster99.ancientrelics.tileentity.ritual.RitualBuilder.rituals.get(ritual).getRitualBlocksIter().entrySet().iterator());
					}
					return InteractionResult.SUCCESS;
				}
			}
		}else {
			if(BlockTags.getAllTags().getTag(new ResourceLocation("ancientrelics:ritual_type_inactive")).contains(context.getLevel().getBlockState(context.getClickedPos()).getBlock())) {
				return InteractionResult.SUCCESS;
			}
		}
		return super.useOn(context);
	}

}

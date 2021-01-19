package com.hijackster99.ancientrelics.items;

import com.hijackster99.ancientrelics.core.References;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.registries.ObjectHolder;

public class ARItem extends Item{

	public ARItem(String registryName, int maxStack, ItemGroup tab) {
		super(new Item.Properties().maxStackSize(maxStack).group(tab));
		setRegistryName(registryName);
	}
	//Items
	@ObjectHolder(References.MODID + ":ruby_gem")
	public static ARItem RUBY;
	
	@ObjectHolder(References.MODID + ":peridot_gem")
	public static ARItem PERIDOT;
	
	@ObjectHolder(References.MODID + ":sapphire_gem")
	public static ARItem SAPPHIRE;
	
	@ObjectHolder(References.MODID + ":ruby_staff")
	public static ARItem RUBY_STAFF;
	
	@ObjectHolder(References.MODID + ":peridot_staff")
	public static ARItem PERIDOT_STAFF;
	
	@ObjectHolder(References.MODID + ":sapphire_staff")
	public static ARItem SAPPHIRE_STAFF;
	
	@ObjectHolder(References.MODID + ":void_coal")
	public static ARItem VOID_COAL;
	
	@ObjectHolder(References.MODID + ":void_charcoal")
	public static ARItem VOID_CHARCOAL;
	
	@ObjectHolder(References.MODID + ":filter")
	public static ARItem FILTER;
	
	@ObjectHolder(References.MODID + ":ritual_builder")
	public static ARItem RITUAL_BUILDER;
	
	@ObjectHolder(References.MODID + ":ritual_maker")
	public static ARItem RITUAL_MAKER;

	//BlockItems
	@ObjectHolder(References.MODID + ":arcane_stone_1")
	public static ARBlockItem ARCANE_STONE_1;
	
	@ObjectHolder(References.MODID + ":arcane_stone_2")
	public static ARBlockItem ARCANE_STONE_2;
	
	@ObjectHolder(References.MODID + ":arcane_stone_3")
	public static ARBlockItem ARCANE_STONE_3;
	
	@ObjectHolder(References.MODID + ":arcane_stone_4")
	public static ARBlockItem ARCANE_STONE_4;
	
	@ObjectHolder(References.MODID + ":arcane_stone_5")
	public static ARBlockItem ARCANE_STONE_5;
	
	@ObjectHolder(References.MODID + ":arcane_stone_6")
	public static ARBlockItem ARCANE_STONE_6;
	
	@ObjectHolder(References.MODID + ":peridot_ore")
	public static ARBlockItem PERIDOT_ORE;
	
	@ObjectHolder(References.MODID + ":ruby_ore")
	public static ARBlockItem RUBY_ORE;
	
	@ObjectHolder(References.MODID + ":sapphire_ore")
	public static ARBlockItem SAPPHIRE_ORE;
	
	@ObjectHolder(References.MODID + ":peridot_shard")
	public static ARBlockItem PERIDOT_SHARD;
	
	@ObjectHolder(References.MODID + ":ruby_shard")
	public static ARBlockItem RUBY_SHARD;
	
	@ObjectHolder(References.MODID + ":sapphire_shard")
	public static ARBlockItem SAPPHIRE_SHARD;

	@ObjectHolder(References.MODID + ":ritual_stone_1_ruby")
	public static ARBlockItem RITUAL_STONE_1_RUBY;
	
	@ObjectHolder(References.MODID + ":ritual_stone_2_ruby")
	public static ARBlockItem RITUAL_STONE_2_RUBY;
	
	@ObjectHolder(References.MODID + ":ritual_stone_3_ruby")
	public static ARBlockItem RITUAL_STONE_3_RUBY;
	
	@ObjectHolder(References.MODID + ":ritual_stone_4_ruby")
	public static ARBlockItem RITUAL_STONE_4_RUBY;
	
	@ObjectHolder(References.MODID + ":ritual_stone_5_ruby")
	public static ARBlockItem RITUAL_STONE_5_RUBY;
	
	@ObjectHolder(References.MODID + ":ritual_stone_1_peridot")
	public static ARBlockItem RITUAL_STONE_1_PERIDOT;
	
	@ObjectHolder(References.MODID + ":ritual_stone_2_peridot")
	public static ARBlockItem RITUAL_STONE_2_PERIDOT;
	
	@ObjectHolder(References.MODID + ":ritual_stone_3_peridot")
	public static ARBlockItem RITUAL_STONE_3_PERIDOT;
	
	@ObjectHolder(References.MODID + ":ritual_stone_4_peridot")
	public static ARBlockItem RITUAL_STONE_4_PERIDOT;
	
	@ObjectHolder(References.MODID + ":ritual_stone_5_peridot")
	public static ARBlockItem RITUAL_STONE_5_PERIDOT;
	
	@ObjectHolder(References.MODID + ":ritual_stone_1_sapphire")
	public static ARBlockItem RITUAL_STONE_1_SAPPHIRE;
	
	@ObjectHolder(References.MODID + ":ritual_stone_2_sapphire")
	public static ARBlockItem RITUAL_STONE_2_SAPPHIRE;
	
	@ObjectHolder(References.MODID + ":ritual_stone_3_sapphire")
	public static ARBlockItem RITUAL_STONE_3_SAPPHIRE;
	
	@ObjectHolder(References.MODID + ":ritual_stone_4_sapphire")
	public static ARBlockItem RITUAL_STONE_4_SAPPHIRE;
	
	@ObjectHolder(References.MODID + ":ritual_stone_5_sapphire")
	public static ARBlockItem RITUAL_STONE_5_SAPPHIRE;
	
	@ObjectHolder(References.MODID + ":ritual_stone_6")
	public static ARBlockItem RITUAL_STONE_6;

	@ObjectHolder(References.MODID + ":ritual_stone_1_ruby_active")
	public static ARBlockItem RITUAL_STONE_1_RUBY_ACTIVE;
	
	@ObjectHolder(References.MODID + ":ritual_stone_2_ruby_active")
	public static ARBlockItem RITUAL_STONE_2_RUBY_ACTIVE;
	
	@ObjectHolder(References.MODID + ":ritual_stone_3_ruby_active")
	public static ARBlockItem RITUAL_STONE_3_RUBY_ACTIVE;
	
	@ObjectHolder(References.MODID + ":ritual_stone_4_ruby_active")
	public static ARBlockItem RITUAL_STONE_4_RUBY_ACTIVE;
	
	@ObjectHolder(References.MODID + ":ritual_stone_5_ruby_active")
	public static ARBlockItem RITUAL_STONE_5_RUBY_ACTIVE;
	
	@ObjectHolder(References.MODID + ":ritual_stone_1_peridot_active")
	public static ARBlockItem RITUAL_STONE_1_PERIDOT_ACTIVE;
	
	@ObjectHolder(References.MODID + ":ritual_stone_2_peridot_active")
	public static ARBlockItem RITUAL_STONE_2_PERIDOT_ACTIVE;
	
	@ObjectHolder(References.MODID + ":ritual_stone_3_peridot_active")
	public static ARBlockItem RITUAL_STONE_3_PERIDOT_ACTIVE;
	
	@ObjectHolder(References.MODID + ":ritual_stone_4_peridot_active")
	public static ARBlockItem RITUAL_STONE_4_PERIDOT_ACTIVE;
	
	@ObjectHolder(References.MODID + ":ritual_stone_5_peridot_active")
	public static ARBlockItem RITUAL_STONE_5_PERIDOT_ACTIVE;
	
	@ObjectHolder(References.MODID + ":ritual_stone_1_sapphire_active")
	public static ARBlockItem RITUAL_STONE_1_SAPPHIRE_ACTIVE;
	
	@ObjectHolder(References.MODID + ":ritual_stone_2_sapphire_active")
	public static ARBlockItem RITUAL_STONE_2_SAPPHIRE_ACTIVE;
	
	@ObjectHolder(References.MODID + ":ritual_stone_3_sapphire_active")
	public static ARBlockItem RITUAL_STONE_3_SAPPHIRE_ACTIVE;
	
	@ObjectHolder(References.MODID + ":ritual_stone_4_sapphire_active")
	public static ARBlockItem RITUAL_STONE_4_SAPPHIRE_ACTIVE;
	
	@ObjectHolder(References.MODID + ":ritual_stone_5_sapphire_active")
	public static ARBlockItem RITUAL_STONE_5_SAPPHIRE_ACTIVE;
	
	@ObjectHolder(References.MODID + ":ritual_stone_6_active")
	public static ARBlockItem RITUAL_STONE_6_ACTIVE;

	@ObjectHolder(References.MODID + ":pedestal")
	public static ARBlockItem PEDESTAL;

	@ObjectHolder(References.MODID + ":charger")
	public static ARBlockItem CHARGER;

	@ObjectHolder(References.MODID + ":relic_anvil")
	public static ARBlockItem RELIC_ANVIL;

	@ObjectHolder(References.MODID + ":void_relay")
	public static ARBlockItem VOID_RELAY;
	
	@ObjectHolder(References.MODID + ":peridot_relic_1")
	public static ARBlockItem PERIDOT_RELIC_1;
	
	@ObjectHolder(References.MODID + ":peridot_relic_2")
	public static ARBlockItem PERIDOT_RELIC_2;
	
	@ObjectHolder(References.MODID + ":peridot_relic_3")
	public static ARBlockItem PERIDOT_RELIC_3;
	
	@ObjectHolder(References.MODID + ":peridot_relic_4")
	public static ARBlockItem PERIDOT_RELIC_4;
	
	@ObjectHolder(References.MODID + ":peridot_relic_5")
	public static ARBlockItem PERIDOT_RELIC_5;
	
	@ObjectHolder(References.MODID + ":ruby_relic_1")
	public static ARBlockItem RUBY_RELIC_1;
	
	@ObjectHolder(References.MODID + ":ruby_relic_2")
	public static ARBlockItem RUBY_RELIC_2;
	
	@ObjectHolder(References.MODID + ":ruby_relic_3")
	public static ARBlockItem RUBY_RELIC_3;
	
	@ObjectHolder(References.MODID + ":ruby_relic_4")
	public static ARBlockItem RUBY_RELIC_4;
	
	@ObjectHolder(References.MODID + ":ruby_relic_5")
	public static ARBlockItem RUBY_RELIC_5;
	
	@ObjectHolder(References.MODID + ":sapphire_relic_1")
	public static ARBlockItem SAPPHIRE_RELIC_1;
	
	@ObjectHolder(References.MODID + ":sapphire_relic_2")
	public static ARBlockItem SAPPHIRE_RELIC_2;
	
	@ObjectHolder(References.MODID + ":sapphire_relic_3")
	public static ARBlockItem SAPPHIRE_RELIC_3;
	
	@ObjectHolder(References.MODID + ":sapphire_relic_4")
	public static ARBlockItem SAPPHIRE_RELIC_4;
	
	@ObjectHolder(References.MODID + ":sapphire_relic_5")
	public static ARBlockItem SAPPHIRE_RELIC_5;
	
	@ObjectHolder(References.MODID + ":master_relic")
	public static ARBlockItem MASTER_RELIC;
	
	@ObjectHolder(References.MODID + ":block_charcoal")
	public static ARBlockItem BLOCK_CHARCOAL;
	
	@ObjectHolder(References.MODID + ":block_void_charcoal")
	public static ARBlockItem BLOCK_VOID_CHARCOAL;
	
	@ObjectHolder(References.MODID + ":block_void_coal")
	public static ARBlockItem BLOCK_VOID_COAL;
	
	@ObjectHolder(References.MODID + ":void_stone")
	public static ARBlockItem VOID_STONE;
	
	@ObjectHolder(References.MODID + ":soul_heart")
	public static ARBlockItem SOUL_HEART;
	
}

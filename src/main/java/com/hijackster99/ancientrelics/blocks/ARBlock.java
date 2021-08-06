package com.hijackster99.ancientrelics.blocks;

import com.hijackster99.ancientrelics.core.References;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.registries.ObjectHolder;

public class ARBlock extends Block{

	public ARBlock(String registryName, Material materialIn, float hardnessIn, float resistanceIn, ToolType harvestTool, int miningLevel, boolean requiresTool) {
		super(Properties.of(materialIn).strength(hardnessIn, resistanceIn).harvestTool(harvestTool).harvestLevel(miningLevel).requiresCorrectToolForDrops());
		setRegistryName(References.MODID, registryName);
	}
	
	public ARBlock(String registryName, Material materialIn, float hardnessIn, float resistanceIn, ToolType harvestTool, int miningLevel) {
		super(Properties.of(materialIn).strength(hardnessIn, resistanceIn).harvestTool(harvestTool).harvestLevel(miningLevel));
		setRegistryName(References.MODID, registryName);
	}
	
	@ObjectHolder(References.MODID + ":arcane_stone_1")
	public static ARBlock ARCANE_STONE_1;
	
	@ObjectHolder(References.MODID + ":arcane_stone_2")
	public static ARBlock ARCANE_STONE_2;
	
	@ObjectHolder(References.MODID + ":arcane_stone_3")
	public static ARBlock ARCANE_STONE_3;
	
	@ObjectHolder(References.MODID + ":arcane_stone_4")
	public static ARBlock ARCANE_STONE_4;
	
	@ObjectHolder(References.MODID + ":arcane_stone_5")
	public static ARBlock ARCANE_STONE_5;
	
	@ObjectHolder(References.MODID + ":arcane_stone_6")
	public static ARBlock ARCANE_STONE_6;
	
	@ObjectHolder(References.MODID + ":peridot_ore")
	public static ARBlock PERIDOT_ORE;
	
	@ObjectHolder(References.MODID + ":ruby_ore")
	public static ARBlock RUBY_ORE;
	
	@ObjectHolder(References.MODID + ":sapphire_ore")
	public static ARBlock SAPPHIRE_ORE;
	
	@ObjectHolder(References.MODID + ":ruby_shard")
	public static ARBlock RUBY_SHARD;
	
	@ObjectHolder(References.MODID + ":peridot_shard")
	public static ARBlock PERIDOT_SHARD;
	
	@ObjectHolder(References.MODID + ":sapphire_shard")
	public static ARBlock SAPPHIRE_SHARD;

	@ObjectHolder(References.MODID + ":ritual_stone_1_ruby")
	public static ARBlock RITUAL_STONE_1_RUBY;
	
	@ObjectHolder(References.MODID + ":ritual_stone_2_ruby")
	public static ARBlock RITUAL_STONE_2_RUBY;
	
	@ObjectHolder(References.MODID + ":ritual_stone_3_ruby")
	public static ARBlock RITUAL_STONE_3_RUBY;
	
	@ObjectHolder(References.MODID + ":ritual_stone_4_ruby")
	public static ARBlock RITUAL_STONE_4_RUBY;
	
	@ObjectHolder(References.MODID + ":ritual_stone_5_ruby")
	public static ARBlock RITUAL_STONE_5_RUBY;
	
	@ObjectHolder(References.MODID + ":ritual_stone_1_peridot")
	public static ARBlock RITUAL_STONE_1_PERIDOT;
	
	@ObjectHolder(References.MODID + ":ritual_stone_2_peridot")
	public static ARBlock RITUAL_STONE_2_PERIDOT;
	
	@ObjectHolder(References.MODID + ":ritual_stone_3_peridot")
	public static ARBlock RITUAL_STONE_3_PERIDOT;
	
	@ObjectHolder(References.MODID + ":ritual_stone_4_peridot")
	public static ARBlock RITUAL_STONE_4_PERIDOT;
	
	@ObjectHolder(References.MODID + ":ritual_stone_5_peridot")
	public static ARBlock RITUAL_STONE_5_PERIDOT;
	
	@ObjectHolder(References.MODID + ":ritual_stone_1_sapphire")
	public static ARBlock RITUAL_STONE_1_SAPPHIRE;
	
	@ObjectHolder(References.MODID + ":ritual_stone_2_sapphire")
	public static ARBlock RITUAL_STONE_2_SAPPHIRE;
	
	@ObjectHolder(References.MODID + ":ritual_stone_3_sapphire")
	public static ARBlock RITUAL_STONE_3_SAPPHIRE;
	
	@ObjectHolder(References.MODID + ":ritual_stone_4_sapphire")
	public static ARBlock RITUAL_STONE_4_SAPPHIRE;
	
	@ObjectHolder(References.MODID + ":ritual_stone_5_sapphire")
	public static ARBlock RITUAL_STONE_5_SAPPHIRE;
	
	@ObjectHolder(References.MODID + ":ritual_stone_6")
	public static ARBlock RITUAL_STONE_6;

	@ObjectHolder(References.MODID + ":ritual_stone_1_ruby_active")
	public static ARBlock RITUAL_STONE_1_RUBY_ACTIVE;
	
	@ObjectHolder(References.MODID + ":ritual_stone_2_ruby_active")
	public static ARBlock RITUAL_STONE_2_RUBY_ACTIVE;
	
	@ObjectHolder(References.MODID + ":ritual_stone_3_ruby_active")
	public static ARBlock RITUAL_STONE_3_RUBY_ACTIVE;
	
	@ObjectHolder(References.MODID + ":ritual_stone_4_ruby_active")
	public static ARBlock RITUAL_STONE_4_RUBY_ACTIVE;
	
	@ObjectHolder(References.MODID + ":ritual_stone_5_ruby_active")
	public static ARBlock RITUAL_STONE_5_RUBY_ACTIVE;
	
	@ObjectHolder(References.MODID + ":ritual_stone_1_peridot_active")
	public static ARBlock RITUAL_STONE_1_PERIDOT_ACTIVE;
	
	@ObjectHolder(References.MODID + ":ritual_stone_2_peridot_active")
	public static ARBlock RITUAL_STONE_2_PERIDOT_ACTIVE;
	
	@ObjectHolder(References.MODID + ":ritual_stone_3_peridot_active")
	public static ARBlock RITUAL_STONE_3_PERIDOT_ACTIVE;
	
	@ObjectHolder(References.MODID + ":ritual_stone_4_peridot_active")
	public static ARBlock RITUAL_STONE_4_PERIDOT_ACTIVE;
	
	@ObjectHolder(References.MODID + ":ritual_stone_5_peridot_active")
	public static ARBlock RITUAL_STONE_5_PERIDOT_ACTIVE;
	
	@ObjectHolder(References.MODID + ":ritual_stone_1_sapphire_active")
	public static ARBlock RITUAL_STONE_1_SAPPHIRE_ACTIVE;
	
	@ObjectHolder(References.MODID + ":ritual_stone_2_sapphire_active")
	public static ARBlock RITUAL_STONE_2_SAPPHIRE_ACTIVE;
	
	@ObjectHolder(References.MODID + ":ritual_stone_3_sapphire_active")
	public static ARBlock RITUAL_STONE_3_SAPPHIRE_ACTIVE;
	
	@ObjectHolder(References.MODID + ":ritual_stone_4_sapphire_active")
	public static ARBlock RITUAL_STONE_4_SAPPHIRE_ACTIVE;
	
	@ObjectHolder(References.MODID + ":ritual_stone_5_sapphire_active")
	public static ARBlock RITUAL_STONE_5_SAPPHIRE_ACTIVE;
	
	@ObjectHolder(References.MODID + ":ritual_stone_6_active")
	public static ARBlock RITUAL_STONE_6_ACTIVE;

	@ObjectHolder(References.MODID + ":pedestal")
	public static ARBlock PEDESTAL;

	@ObjectHolder(References.MODID + ":charger")
	public static ARBlock CHARGER;

	@ObjectHolder(References.MODID + ":relic_anvil")
	public static ARBlock RELIC_ANVIL;

	@ObjectHolder(References.MODID + ":void_relay")
	public static ARBlock VOID_RELAY;
	
	@ObjectHolder(References.MODID + ":peridot_relic_1")
	public static ARBlock PERIDOT_RELIC_1;
	
	@ObjectHolder(References.MODID + ":peridot_relic_2")
	public static ARBlock PERIDOT_RELIC_2;
	
	@ObjectHolder(References.MODID + ":peridot_relic_3")
	public static ARBlock PERIDOT_RELIC_3;
	
	@ObjectHolder(References.MODID + ":peridot_relic_4")
	public static ARBlock PERIDOT_RELIC_4;
	
	@ObjectHolder(References.MODID + ":peridot_relic_5")
	public static ARBlock PERIDOT_RELIC_5;
	
	@ObjectHolder(References.MODID + ":ruby_relic_1")
	public static ARBlock RUBY_RELIC_1;
	
	@ObjectHolder(References.MODID + ":ruby_relic_2")
	public static ARBlock RUBY_RELIC_2;
	
	@ObjectHolder(References.MODID + ":ruby_relic_3")
	public static ARBlock RUBY_RELIC_3;
	
	@ObjectHolder(References.MODID + ":ruby_relic_4")
	public static ARBlock RUBY_RELIC_4;
	
	@ObjectHolder(References.MODID + ":ruby_relic_5")
	public static ARBlock RUBY_RELIC_5;
	
	@ObjectHolder(References.MODID + ":sapphire_relic_1")
	public static ARBlock SAPPHIRE_RELIC_1;
	
	@ObjectHolder(References.MODID + ":sapphire_relic_2")
	public static ARBlock SAPPHIRE_RELIC_2;
	
	@ObjectHolder(References.MODID + ":sapphire_relic_3")
	public static ARBlock SAPPHIRE_RELIC_3;
	
	@ObjectHolder(References.MODID + ":sapphire_relic_4")
	public static ARBlock SAPPHIRE_RELIC_4;
	
	@ObjectHolder(References.MODID + ":sapphire_relic_5")
	public static ARBlock SAPPHIRE_RELIC_5;
	
	@ObjectHolder(References.MODID + ":master_relic")
	public static ARBlock MASTER_RELIC;
	
	@ObjectHolder(References.MODID + ":block_charcoal")
	public static ARBlock BLOCK_CHARCOAL;
	
	@ObjectHolder(References.MODID + ":block_void_charcoal")
	public static ARBlock BLOCK_VOID_CHARCOAL;
	
	@ObjectHolder(References.MODID + ":block_void_coal")
	public static ARBlock BLOCK_VOID_COAL;
	
	@ObjectHolder(References.MODID + ":void_stone")
	public static ARBlock VOID_STONE;
	
	@ObjectHolder(References.MODID + ":soul_heart")
	public static ARBlock SOUL_HEART;
	
	@ObjectHolder(References.MODID + ":void_gas_block")
	public static VoidGasBlock VOID_GAS_BLOCK;
	
	@ObjectHolder(References.MODID + ":void_liquid_block")
	public static VoidLiquidBlock VOID_LIQUID_BLOCK;

}

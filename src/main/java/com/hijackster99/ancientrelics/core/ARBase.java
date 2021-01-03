package com.hijackster99.ancientrelics.core;

import com.hijackster99.ancientrelics.Blocks.ARBlock;
import com.hijackster99.ancientrelics.Blocks.ChargerBlock;
import com.hijackster99.ancientrelics.Blocks.PedestalBlock;
import com.hijackster99.ancientrelics.Blocks.RelicAnvilBlock;
import com.hijackster99.ancientrelics.Blocks.RelicBlock;
import com.hijackster99.ancientrelics.Blocks.RitualBlock;
import com.hijackster99.ancientrelics.Blocks.ShardBlock;
import com.hijackster99.ancientrelics.Blocks.VoidRelayBlock;
import com.hijackster99.ancientrelics.Items.ARBlockItem;
import com.hijackster99.ancientrelics.Items.ARItem;
import com.hijackster99.ancientrelics.Items.Burnable;
import com.hijackster99.ancientrelics.Items.BurnableBlock;
import com.hijackster99.ancientrelics.Items.ChargerBlockItem;
import com.hijackster99.ancientrelics.Items.Filter;
import com.hijackster99.ancientrelics.Items.PeridotStaff;
import com.hijackster99.ancientrelics.Items.RubyStaff;
import com.hijackster99.ancientrelics.Items.SapphireStaff;
import com.hijackster99.ancientrelics.Items.ShardBlockItem;
import com.hijackster99.ancientrelics.Tileentity.Ritual.RitualStone;
import com.hijackster99.ancientrelics.core.classloader.RitualClassManager;
import com.hijackster99.ancientrelics.core.classloader.RitualJsonManager;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(References.MODID)
@Mod.EventBusSubscriber(modid = References.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ARBase {
	
	public ARBase() {
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::commonSetup);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);
		
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	public static ItemGroup ARGroup = new ItemGroup("ancientrelics") {

		@Override
		public ItemStack createIcon() {
			return new ItemStack(ARItem.MASTER_RELIC);
		}
		
	};
	
	private void commonSetup(FMLCommonSetupEvent event) {
		
	}
	
	private void clientSetup(FMLClientSetupEvent event) {
		
	}
	
	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event) {
		//Items
		Item rubyGem = new ARItem("ruby_gem", 64, ARGroup);
		event.getRegistry().register(rubyGem);
		Item peridotGem = new ARItem("peridot_gem", 64, ARGroup);
		event.getRegistry().register(peridotGem);
		Item sapphireGem = new ARItem("sapphire_gem", 64, ARGroup);
		event.getRegistry().register(sapphireGem);
		Item rubyStaff = new RubyStaff("ruby_staff", 1, ARGroup);
		event.getRegistry().register(rubyStaff);
		Item peridotStaff = new PeridotStaff("peridot_staff", 1, ARGroup);
		event.getRegistry().register(peridotStaff);
		Item sapphireStaff = new SapphireStaff("sapphire_staff", 1, ARGroup);
		event.getRegistry().register(sapphireStaff);
		Item voidCoal = new Burnable("void_coal", 1, ARGroup, 2600);
		event.getRegistry().register(voidCoal);
		Item voidCharcoal = new Burnable("void_charcoal", 1, ARGroup, 2600);
		event.getRegistry().register(voidCharcoal);
		Item filter = new Filter("filter", 1, ARGroup);
		event.getRegistry().register(filter);
		//BlockItems
		BlockItem arcaneStone1 = new ARBlockItem(ARBlock.ARCANE_STONE_1, 64, ARGroup);
		event.getRegistry().register(arcaneStone1);
		BlockItem arcaneStone2 = new ARBlockItem(ARBlock.ARCANE_STONE_2, 64, ARGroup);
		event.getRegistry().register(arcaneStone2);
		BlockItem arcaneStone3 = new ARBlockItem(ARBlock.ARCANE_STONE_3, 64, ARGroup);
		event.getRegistry().register(arcaneStone3);
		BlockItem arcaneStone4 = new ARBlockItem(ARBlock.ARCANE_STONE_4, 64, ARGroup);
		event.getRegistry().register(arcaneStone4);
		BlockItem arcaneStone5 = new ARBlockItem(ARBlock.ARCANE_STONE_5, 64, ARGroup);
		event.getRegistry().register(arcaneStone5);
		BlockItem arcaneStone6 = new ARBlockItem(ARBlock.ARCANE_STONE_6, 64, ARGroup);
		event.getRegistry().register(arcaneStone6);
		BlockItem peridotOre = new ARBlockItem(ARBlock.PERIDOT_ORE, 64, ARGroup);
		event.getRegistry().register(peridotOre);
		BlockItem rubyOre = new ARBlockItem(ARBlock.RUBY_ORE, 64, ARGroup);
		event.getRegistry().register(rubyOre);
		BlockItem sapphireOre = new ARBlockItem(ARBlock.SAPPHIRE_ORE, 64, ARGroup);
		event.getRegistry().register(sapphireOre);
		BlockItem peridotShard = new ShardBlockItem(ARBlock.PERIDOT_SHARD, 64, ARGroup);
		event.getRegistry().register(peridotShard);
		BlockItem rubyShard = new ShardBlockItem(ARBlock.RUBY_SHARD, 64, ARGroup);
		event.getRegistry().register(rubyShard);
		BlockItem sapphireShard = new ShardBlockItem(ARBlock.SAPPHIRE_SHARD, 64, ARGroup);
		event.getRegistry().register(sapphireShard);
		BlockItem ritualStone1Ruby = new ARBlockItem(ARBlock.RITUAL_STONE_1_RUBY, 64, ARGroup);
		event.getRegistry().register(ritualStone1Ruby);
		BlockItem ritualStone2Ruby = new ARBlockItem(ARBlock.RITUAL_STONE_2_RUBY, 64, ARGroup);
		event.getRegistry().register(ritualStone2Ruby);
		BlockItem ritualStone3Ruby = new ARBlockItem(ARBlock.RITUAL_STONE_3_RUBY, 64, ARGroup);
		event.getRegistry().register(ritualStone3Ruby);
		BlockItem ritualStone4Ruby = new ARBlockItem(ARBlock.RITUAL_STONE_4_RUBY, 64, ARGroup);
		event.getRegistry().register(ritualStone4Ruby);
		BlockItem ritualStone5Ruby = new ARBlockItem(ARBlock.RITUAL_STONE_5_RUBY, 64, ARGroup);
		event.getRegistry().register(ritualStone5Ruby);
		BlockItem ritualStone1Peridot = new ARBlockItem(ARBlock.RITUAL_STONE_1_PERIDOT, 64, ARGroup);
		event.getRegistry().register(ritualStone1Peridot);
		BlockItem ritualStone2Peridot = new ARBlockItem(ARBlock.RITUAL_STONE_2_PERIDOT, 64, ARGroup);
		event.getRegistry().register(ritualStone2Peridot);
		BlockItem ritualStone3Peridot = new ARBlockItem(ARBlock.RITUAL_STONE_3_PERIDOT, 64, ARGroup);
		event.getRegistry().register(ritualStone3Peridot);
		BlockItem ritualStone4Peridot = new ARBlockItem(ARBlock.RITUAL_STONE_4_PERIDOT, 64, ARGroup);
		event.getRegistry().register(ritualStone4Peridot);
		BlockItem ritualStone5Peridot = new ARBlockItem(ARBlock.RITUAL_STONE_5_PERIDOT, 64, ARGroup);
		event.getRegistry().register(ritualStone5Peridot);
		BlockItem ritualStone1Sapphire = new ARBlockItem(ARBlock.RITUAL_STONE_1_SAPPHIRE, 64, ARGroup);
		event.getRegistry().register(ritualStone1Sapphire);
		BlockItem ritualStone2Sapphire = new ARBlockItem(ARBlock.RITUAL_STONE_2_SAPPHIRE, 64, ARGroup);
		event.getRegistry().register(ritualStone2Sapphire);
		BlockItem ritualStone3Sapphire = new ARBlockItem(ARBlock.RITUAL_STONE_3_SAPPHIRE, 64, ARGroup);
		event.getRegistry().register(ritualStone3Sapphire);
		BlockItem ritualStone4Sapphire = new ARBlockItem(ARBlock.RITUAL_STONE_4_SAPPHIRE, 64, ARGroup);
		event.getRegistry().register(ritualStone4Sapphire);
		BlockItem ritualStone5Sapphire = new ARBlockItem(ARBlock.RITUAL_STONE_5_SAPPHIRE, 64, ARGroup);
		event.getRegistry().register(ritualStone5Sapphire);
		BlockItem ritualStone6 = new ARBlockItem(ARBlock.RITUAL_STONE_6, 64, ARGroup);
		event.getRegistry().register(ritualStone6);
		BlockItem pedestal = new ARBlockItem(ARBlock.PEDESTAL, 64, ARGroup);
		event.getRegistry().register(pedestal);
		BlockItem charger = new ChargerBlockItem(ARBlock.CHARGER, 64, ARGroup);
		event.getRegistry().register(charger);
		BlockItem relicAnvil = new ARBlockItem(ARBlock.RELIC_ANVIL, 64, ARGroup);
		event.getRegistry().register(relicAnvil);
		BlockItem voidRelay = new ARBlockItem(ARBlock.VOID_RELAY, 64, ARGroup);
		event.getRegistry().register(voidRelay);
		BlockItem peridotRelic1 = new ShardBlockItem(ARBlock.PERIDOT_RELIC_1, 64, ARGroup);
		event.getRegistry().register(peridotRelic1);
		BlockItem peridotRelic2 = new ShardBlockItem(ARBlock.PERIDOT_RELIC_2, 64, ARGroup);
		event.getRegistry().register(peridotRelic2);
		BlockItem peridotRelic3 = new ShardBlockItem(ARBlock.PERIDOT_RELIC_3, 64, ARGroup);
		event.getRegistry().register(peridotRelic3);
		BlockItem peridotRelic4 = new ShardBlockItem(ARBlock.PERIDOT_RELIC_4, 64, ARGroup);
		event.getRegistry().register(peridotRelic4);
		BlockItem peridotRelic5 = new ShardBlockItem(ARBlock.PERIDOT_RELIC_5, 64, ARGroup);
		event.getRegistry().register(peridotRelic5);
		BlockItem rubyRelic1 = new ShardBlockItem(ARBlock.RUBY_RELIC_1, 64, ARGroup);
		event.getRegistry().register(rubyRelic1);
		BlockItem rubyRelic2 = new ShardBlockItem(ARBlock.RUBY_RELIC_2, 64, ARGroup);
		event.getRegistry().register(rubyRelic2);
		BlockItem rubyRelic3 = new ShardBlockItem(ARBlock.RUBY_RELIC_3, 64, ARGroup);
		event.getRegistry().register(rubyRelic3);
		BlockItem rubyRelic4 = new ShardBlockItem(ARBlock.RUBY_RELIC_4, 64, ARGroup);
		event.getRegistry().register(rubyRelic4);
		BlockItem rubyRelic5 = new ShardBlockItem(ARBlock.RUBY_RELIC_5, 64, ARGroup);
		event.getRegistry().register(rubyRelic5);
		BlockItem sapphireRelic1 = new ShardBlockItem(ARBlock.SAPPHIRE_RELIC_1, 64, ARGroup);
		event.getRegistry().register(sapphireRelic1);
		BlockItem sapphireRelic2 = new ShardBlockItem(ARBlock.SAPPHIRE_RELIC_2, 64, ARGroup);
		event.getRegistry().register(sapphireRelic2);
		BlockItem sapphireRelic3 = new ShardBlockItem(ARBlock.SAPPHIRE_RELIC_3, 64, ARGroup);
		event.getRegistry().register(sapphireRelic3);
		BlockItem sapphireRelic4 = new ShardBlockItem(ARBlock.SAPPHIRE_RELIC_4, 64, ARGroup);
		event.getRegistry().register(sapphireRelic4);
		BlockItem sapphireRelic5 = new ShardBlockItem(ARBlock.SAPPHIRE_RELIC_5, 64, ARGroup);
		event.getRegistry().register(sapphireRelic5);
		BlockItem masterRelic = new ShardBlockItem(ARBlock.MASTER_RELIC, 64, ARGroup);
		event.getRegistry().register(masterRelic);
		BlockItem blockCharcoal = new BurnableBlock(ARBlock.BLOCK_CHARCOAL, 64, ARGroup, 16000);
		event.getRegistry().register(blockCharcoal);
		BlockItem blockVoidCoal = new BurnableBlock(ARBlock.BLOCK_VOID_COAL, 64, ARGroup, 25000);
		event.getRegistry().register(blockVoidCoal);
		BlockItem blockVoidCharcoal = new BurnableBlock(ARBlock.BLOCK_VOID_CHARCOAL, 64, ARGroup, 25000);
		event.getRegistry().register(blockVoidCharcoal);
		BlockItem voidStone = new ARBlockItem(ARBlock.VOID_STONE, 64, ARGroup);
		event.getRegistry().register(voidStone);
		BlockItem soulHeart = new ARBlockItem(ARBlock.SOUL_HEART, 64, ARGroup);
		event.getRegistry().register(soulHeart);
	}
	
	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event) {
		//Blocks
		Block arcaneStone1 = new ARBlock("arcane_stone_1", Material.ROCK, 2f, 7f, ToolType.PICKAXE, 0, true);
		event.getRegistry().register(arcaneStone1);
		Block arcaneStone2 = new ARBlock("arcane_stone_2", Material.ROCK, 10f, 10f, ToolType.PICKAXE, 0, true);
		event.getRegistry().register(arcaneStone2);
		Block arcaneStone3 = new ARBlock("arcane_stone_3", Material.ROCK, 20f, 13f, ToolType.PICKAXE, 1, true);
		event.getRegistry().register(arcaneStone3);
		Block arcaneStone4 = new ARBlock("arcane_stone_4", Material.ROCK, 30f, 16f, ToolType.PICKAXE, 2, true);
		event.getRegistry().register(arcaneStone4);
		Block arcaneStone5 = new ARBlock("arcane_stone_5", Material.ROCK, 40f, 20f, ToolType.PICKAXE, 3, true);
		event.getRegistry().register(arcaneStone5);
		Block arcaneStone6 = new ARBlock("arcane_stone_6", Material.ROCK, 50f, 1200f, ToolType.PICKAXE, 4, true);
		event.getRegistry().register(arcaneStone6);
		Block ritualStone1Ruby = new RitualBlock("ritual_stone_1_ruby", Material.ROCK, 2f, 7f, ToolType.PICKAXE, 0, true, 1, EnumRitualType.RUBY);
		event.getRegistry().register(ritualStone1Ruby);
		Block ritualStone2Ruby = new RitualBlock("ritual_stone_2_ruby", Material.ROCK, 10f, 10f, ToolType.PICKAXE, 0, true, 2, EnumRitualType.RUBY);
		event.getRegistry().register(ritualStone2Ruby);
		Block ritualStone3Ruby = new RitualBlock("ritual_stone_3_ruby", Material.ROCK, 20f, 13f, ToolType.PICKAXE, 1, true, 3, EnumRitualType.RUBY);
		event.getRegistry().register(ritualStone3Ruby);
		Block ritualStone4Ruby = new RitualBlock("ritual_stone_4_ruby", Material.ROCK, 30f, 16f, ToolType.PICKAXE, 2, true, 4, EnumRitualType.RUBY);
		event.getRegistry().register(ritualStone4Ruby);
		Block ritualStone5Ruby = new RitualBlock("ritual_stone_5_ruby", Material.ROCK, 40f, 20f, ToolType.PICKAXE, 3, true, 5, EnumRitualType.RUBY);
		event.getRegistry().register(ritualStone5Ruby);
		Block ritualStone1Peridot = new RitualBlock("ritual_stone_1_peridot", Material.ROCK, 2f, 7f, ToolType.PICKAXE, 0, true, 1, EnumRitualType.PERIDOT);
		event.getRegistry().register(ritualStone1Peridot);
		Block ritualStone2Peridot = new RitualBlock("ritual_stone_2_peridot", Material.ROCK, 10f, 10f, ToolType.PICKAXE, 0, true, 2, EnumRitualType.PERIDOT);
		event.getRegistry().register(ritualStone2Peridot);
		Block ritualStone3Peridot = new RitualBlock("ritual_stone_3_peridot", Material.ROCK, 20f, 13f, ToolType.PICKAXE, 1, true, 3, EnumRitualType.PERIDOT);
		event.getRegistry().register(ritualStone3Peridot);
		Block ritualStone4Peridot = new RitualBlock("ritual_stone_4_peridot", Material.ROCK, 30f, 16f, ToolType.PICKAXE, 2, true, 4, EnumRitualType.PERIDOT);
		event.getRegistry().register(ritualStone4Peridot);
		Block ritualStone5Peridot = new RitualBlock("ritual_stone_5_peridot", Material.ROCK, 40f, 20f, ToolType.PICKAXE, 3, true, 5, EnumRitualType.PERIDOT);
		event.getRegistry().register(ritualStone5Peridot);
		Block ritualStone1Sapphire = new RitualBlock("ritual_stone_1_sapphire", Material.ROCK, 2f, 7f, ToolType.PICKAXE, 0, true, 1, EnumRitualType.SAPPHIRE);
		event.getRegistry().register(ritualStone1Sapphire);
		Block ritualStone2Sapphire = new RitualBlock("ritual_stone_2_sapphire", Material.ROCK, 10f, 10f, ToolType.PICKAXE, 0, true, 2, EnumRitualType.SAPPHIRE);
		event.getRegistry().register(ritualStone2Sapphire);
		Block ritualStone3Sapphire = new RitualBlock("ritual_stone_3_sapphire", Material.ROCK, 20f, 13f, ToolType.PICKAXE, 1, true, 3, EnumRitualType.SAPPHIRE);
		event.getRegistry().register(ritualStone3Sapphire);
		Block ritualStone4Sapphire = new RitualBlock("ritual_stone_4_sapphire", Material.ROCK, 30f, 16f, ToolType.PICKAXE, 2, true, 4, EnumRitualType.SAPPHIRE);
		event.getRegistry().register(ritualStone4Sapphire);
		Block ritualStone5Sapphire = new RitualBlock("ritual_stone_5_sapphire", Material.ROCK, 40f, 20f, ToolType.PICKAXE, 3, true, 5, EnumRitualType.SAPPHIRE);
		event.getRegistry().register(ritualStone5Sapphire);
		Block ritualStone6 = new RitualBlock("ritual_stone_6", Material.ROCK, 50f, 1200f, ToolType.PICKAXE, 4, true, 6, EnumRitualType.MASTER);
		event.getRegistry().register(ritualStone6);
		Block peridotOre = new ARBlock("peridot_ore", Material.ROCK, 3f, 3f, ToolType.PICKAXE, 2, true);
		event.getRegistry().register(peridotOre);
		Block rubyOre = new ARBlock("ruby_ore", Material.ROCK, 3f, 3f, ToolType.PICKAXE, 2, true);
		event.getRegistry().register(rubyOre);
		Block sapphireOre = new ARBlock("sapphire_ore", Material.ROCK, 3f, 3f, ToolType.PICKAXE, 2, true);
		event.getRegistry().register(sapphireOre);
		Block rubyShard = new ShardBlock("ruby_shard", Material.GLASS, 0.5f, 1f, ToolType.PICKAXE, 0);
		event.getRegistry().register(rubyShard);
		Block peridotShard = new ShardBlock("peridot_shard", Material.GLASS, 0.5f, 1f, ToolType.PICKAXE, 0);
		event.getRegistry().register(peridotShard);
		Block sapphireShard = new ShardBlock("sapphire_shard", Material.GLASS, 0.5f, 1f, ToolType.PICKAXE, 0);
		event.getRegistry().register(sapphireShard);
		Block pedestal = new PedestalBlock("pedestal", Material.ROCK, 2f, 7f, ToolType.PICKAXE, 0, true);
		event.getRegistry().register(pedestal);
		Block charger = new ChargerBlock("charger", Material.ROCK, 2f, 7f, ToolType.PICKAXE, 0, true);
		event.getRegistry().register(charger);
		Block relicAnvil = new RelicAnvilBlock("relic_anvil", Material.ROCK, 2f, 7f, ToolType.PICKAXE, 0, true);
		event.getRegistry().register(relicAnvil);
		Block voidRelay = new VoidRelayBlock("void_relay", Material.ROCK, 2f, 7f, ToolType.PICKAXE, 0, true);
		event.getRegistry().register(voidRelay);
		Block peridotRelic1 = new RelicBlock("peridot_relic_1", Material.GLASS, 0.5f, 1f, ToolType.PICKAXE, 0);
		event.getRegistry().register(peridotRelic1);
		Block peridotRelic2 = new RelicBlock("peridot_relic_2", Material.GLASS, 0.5f, 1f, ToolType.PICKAXE, 0);
		event.getRegistry().register(peridotRelic2);
		Block peridotRelic3 = new RelicBlock("peridot_relic_3", Material.GLASS, 0.5f, 1f, ToolType.PICKAXE, 0);
		event.getRegistry().register(peridotRelic3);
		Block peridotRelic4 = new RelicBlock("peridot_relic_4", Material.GLASS, 0.5f, 1f, ToolType.PICKAXE, 0);
		event.getRegistry().register(peridotRelic4);
		Block peridotRelic5 = new RelicBlock("peridot_relic_5", Material.GLASS, 0.5f, 1f, ToolType.PICKAXE, 0);
		event.getRegistry().register(peridotRelic5);
		Block rubyRelic1 = new RelicBlock("ruby_relic_1", Material.GLASS, 0.5f, 1f, ToolType.PICKAXE, 0);
		event.getRegistry().register(rubyRelic1);
		Block rubyRelic2 = new RelicBlock("ruby_relic_2", Material.GLASS, 0.5f, 1f, ToolType.PICKAXE, 0);
		event.getRegistry().register(rubyRelic2);
		Block rubyRelic3 = new RelicBlock("ruby_relic_3", Material.GLASS, 0.5f, 1f, ToolType.PICKAXE, 0);
		event.getRegistry().register(rubyRelic3);
		Block rubyRelic4 = new RelicBlock("ruby_relic_4", Material.GLASS, 0.5f, 1f, ToolType.PICKAXE, 0);
		event.getRegistry().register(rubyRelic4);
		Block rubyRelic5 = new RelicBlock("ruby_relic_5", Material.GLASS, 0.5f, 1f, ToolType.PICKAXE, 0);
		event.getRegistry().register(rubyRelic5);
		Block sapphireRelic1 = new RelicBlock("sapphire_relic_1", Material.GLASS, 0.5f, 1f, ToolType.PICKAXE, 0);
		event.getRegistry().register(sapphireRelic1);
		Block sapphireRelic2 = new RelicBlock("sapphire_relic_2", Material.GLASS, 0.5f, 1f, ToolType.PICKAXE, 0);
		event.getRegistry().register(sapphireRelic2);
		Block sapphireRelic3 = new RelicBlock("sapphire_relic_3", Material.GLASS, 0.5f, 1f, ToolType.PICKAXE, 0);
		event.getRegistry().register(sapphireRelic3);
		Block sapphireRelic4 = new RelicBlock("sapphire_relic_4", Material.GLASS, 0.5f, 1f, ToolType.PICKAXE, 0);
		event.getRegistry().register(sapphireRelic4);
		Block sapphireRelic5 = new RelicBlock("sapphire_relic_5", Material.GLASS, 0.5f, 1f, ToolType.PICKAXE, 0);
		event.getRegistry().register(sapphireRelic5);
		Block masterRelic = new RelicBlock("master_relic", Material.GLASS, 0.5f, 1f, ToolType.PICKAXE, 0);
		event.getRegistry().register(masterRelic);
		Block blockCharcoal = new ARBlock("block_charcoal", Material.ROCK, 3f, 7f, ToolType.PICKAXE, 0, true);
		event.getRegistry().register(blockCharcoal);
		Block blockVoidCoal = new ARBlock("block_void_coal", Material.ROCK, 3f, 7f, ToolType.PICKAXE, 0, true);
		event.getRegistry().register(blockVoidCoal);
		Block blockVoidCharcoal = new ARBlock("block_void_charcoal", Material.ROCK, 3f, 7f, ToolType.PICKAXE, 0, true);
		event.getRegistry().register(blockVoidCharcoal);
		Block voidStone = new ARBlock("void_stone", Material.ROCK, 30f, 1200f, ToolType.PICKAXE, 3, true);
		event.getRegistry().register(voidStone);
		Block soulHeart = new ARBlock("soul_heart", Material.ROCK, 50f, 1200f, ToolType.PICKAXE, 4, true);
		event.getRegistry().register(soulHeart);
	}
	
	@SubscribeEvent
	public static void registerTileEntities(RegistryEvent.Register<TileEntityType<?>> event) {
		TileEntityType<RitualStone> ritualStone = TileEntityType.Builder.create(RitualStone::new, ARBlock.RITUAL_STONE_1_PERIDOT, ARBlock.RITUAL_STONE_1_RUBY, ARBlock.RITUAL_STONE_1_SAPPHIRE, ARBlock.RITUAL_STONE_2_PERIDOT, ARBlock.RITUAL_STONE_2_RUBY, ARBlock.RITUAL_STONE_2_SAPPHIRE, ARBlock.RITUAL_STONE_3_PERIDOT, ARBlock.RITUAL_STONE_3_RUBY, ARBlock.RITUAL_STONE_3_SAPPHIRE, ARBlock.RITUAL_STONE_4_PERIDOT, ARBlock.RITUAL_STONE_4_RUBY, ARBlock.RITUAL_STONE_4_SAPPHIRE, ARBlock.RITUAL_STONE_5_PERIDOT, ARBlock.RITUAL_STONE_5_RUBY, ARBlock.RITUAL_STONE_5_SAPPHIRE, ARBlock.RITUAL_STONE_6).build(null);
		ritualStone.setRegistryName(References.MODID, "ritual_stone");
		event.getRegistry().register(ritualStone);
	}
	
	@EventBusSubscriber(modid = References.MODID, bus = EventBusSubscriber.Bus.FORGE)
	public static class eventHandler{
		
		@SubscribeEvent
		public static void registerReloadListener(AddReloadListenerEvent event) {
			System.out.println("hello");
			event.addListener(new RitualJsonManager());
			event.addListener(new RitualClassManager());
		}
		
	}
	
}

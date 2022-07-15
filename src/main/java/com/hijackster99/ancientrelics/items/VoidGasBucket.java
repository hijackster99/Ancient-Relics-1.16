package com.hijackster99.ancientrelics.items;

import java.util.function.Supplier;

import com.hijackster99.ancientrelics.core.ARBase;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.capability.wrappers.FluidBucketWrapper;

public class VoidGasBucket extends BucketItem {

	public VoidGasBucket(Supplier<? extends Fluid> supplier) {
		super(supplier, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1).tab(ARBase.ARGroup));
		setRegistryName("void_gas_bucket");
	}

	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, CompoundTag nbt) {
		return new FluidBucketWrapper(stack);
	}
}

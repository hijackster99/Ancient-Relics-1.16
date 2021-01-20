package com.hijackster99.ancientrelics.items;

import java.util.function.Supplier;

import com.hijackster99.ancientrelics.core.ARBase;

import net.minecraft.fluid.Fluid;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.capability.wrappers.FluidBucketWrapper;

public class VoidGasBucket extends BucketItem {

	public VoidGasBucket(Supplier<? extends Fluid> supplier) {
		super(supplier, new Item.Properties().containerItem(Items.BUCKET).maxStackSize(1).group(ARBase.ARGroup));
		setRegistryName("void_gas_bucket");
	}

	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, CompoundNBT nbt) {
		return new FluidBucketWrapper(stack);
	}
}

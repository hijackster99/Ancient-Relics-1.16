package com.hijackster99.ancientrelics.core;

import java.util.function.Predicate;

import com.hijackster99.ancientrelics.blocks.VoidGas;

import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidTank;

public class VoidGasTank extends FluidTank {

	public VoidGasTank(int capacity) {
		super(capacity, new Predicate<FluidStack>() {

			@Override
			public boolean test(FluidStack t) {
				return t.getFluid().equals(VoidGas.VOID_GAS_STILL);
			}
			
		});
	}

}

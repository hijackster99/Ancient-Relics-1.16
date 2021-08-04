package com.hijackster99.ancientrelics.tileentity.voidrelay;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.hijackster99.ancientrelics.blocks.VoidGas;
import com.hijackster99.ancientrelics.core.VoidGasTank;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;

public class Network {

	private List<VoidRelay> relays;
	
	private BiMap<VoidRelay, ICapabilityProvider> voidIns;
	private BiMap<VoidRelay, ICapabilityProvider> voidOuts;
	
	private Iterator<ICapabilityProvider> voidInIter;
	
	public Network(VoidRelay source) {
		relays = new ArrayList<VoidRelay>();
		voidIns = HashBiMap.<VoidRelay, ICapabilityProvider>create();
		voidOuts = HashBiMap.<VoidRelay, ICapabilityProvider>create();
		relays.add(source);
		NetworkManager.INSTANCE.addNetwork(this);
	}
	
	public void tick() {
		if(voidInIter == null || !voidInIter.hasNext()) {
			voidInIter = voidIns.values().iterator();
		}
		BiMap<ICapabilityProvider, VoidRelay> inverseIn = voidIns.inverse();
		BiMap<ICapabilityProvider, VoidRelay> inverseOut = voidOuts.inverse();
		if(voidInIter.hasNext()) {
			ICapabilityProvider te = voidInIter.next();
			IFluidHandler tank = te.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY).orElse(null);
			if(tank != null && tank instanceof VoidGasTank) {
				if(tank.getFluidInTank(0).getAmount() > 0) {
					for(ICapabilityProvider te2 : voidOuts.values()) {
						IFluidHandler tank2 = te2.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY).orElse(null);
						if(tank2 != null && tank2 instanceof VoidGasTank) {
							int maxTransfer = Math.min(inverseIn.get(te).maxTransfer, inverseOut.get(te2).maxTransfer);
							int transfer = Math.min(Math.min(tank.getFluidInTank(0).getAmount(), tank2.getTankCapacity(0) - tank2.getFluidInTank(0).getAmount()), maxTransfer);
							tank.drain(transfer, FluidAction.EXECUTE);
							tank2.fill(new FluidStack(VoidGas.VOID_GAS_STILL, transfer), FluidAction.EXECUTE);
						}
					}
				}
			}
		}
	}
	
	public void addVoidIn(VoidRelay relay, ICapabilityProvider te) {
		if(!voidIns.containsValue(te)) {
			voidIns.put(relay, te);
			relay.setVoidIn(te);
		}
	}
	
	public void addVoidOut(VoidRelay relay, ICapabilityProvider te) {
		if(!voidOuts.containsValue(te)) {
			voidOuts.put(relay, te);
			relay.setVoidOut(te);
		}
	}
	
	public boolean addRelay(VoidRelay relay1, VoidRelay relay2, PlayerEntity playerIn) {
		if(relay2.getNetwork() == this) {
			if(playerIn != null) playerIn.displayClientMessage(new StringTextComponent("Connection Failed! Relay already in network!"), false);
			return false;
		}
		else {
			mergeNetwork(relay2.getNetwork());
			relay1.addConnection(relay2);
			relay2.addConnection(relay1);
			return true;
		}
	}
	
	public void removeRelay(VoidRelay relay) {
		if(size() == 1) 
			NetworkManager.INSTANCE.removeNetwork(this);
		else {
			for(int i = 1; i < relay.getConnections().size(); i++) {
				VoidRelay r = relay.getRelayConnection(i);
				r.removeConnection(relay);
				Network net = new Network(r);
				r.setNetwork(net);
				net.addAllConnections(r);
			}
			VoidRelay source = relay.getRelayConnection(0);
			source.setNetwork(this);
			this.clear();
			this.addAllConnections(source);
		}
	}
	
	public void mergeNetwork(Network other) {
		if(size() >= other.size()) {
			for(VoidRelay relay : other.relays) {
				relay.setNetwork(this);
				checkVoids(relay, this);
				relays.add(relay);
			}
			NetworkManager.INSTANCE.removeNetwork(other);
		}else {
			for(VoidRelay relay : relays) {
				relay.setNetwork(other);
				checkVoids(relay, other);
				other.relays.add(relay);
			}
			NetworkManager.INSTANCE.removeNetwork(this);
		}
	}
	
	public void checkVoids(VoidRelay relay, Network net) {
		if(relay.getVoidIn() != null) {
			if(net.voidIns.containsValue(relay.getVoidIn())) 
				relay.setVoidIn(null);
			else 
				net.voidIns.put(relay, relay.getVoidIn());
		}
		if(relay.getVoidOut() != null) {
			if(net.voidOuts.containsValue(relay.getVoidOut())) 
				relay.setVoidOut(null);
			else 
				net.voidOuts.put(relay, relay.getVoidOut());
		}
	}
	
	public void addAllConnections(VoidRelay relay) {
		for(int i = 0; i < relay.getConnections().size(); i++) {
			if(addRelay(relay, relay.getRelayConnection(i), null)) {
				addAllConnections(relay.getRelayConnection(i));
			}
		}
	}
	
	public void clear() {
		relays.clear();
		voidIns.clear();
		voidOuts.clear();
	}
	
	public int size() {
		return relays.size();
	}
	
}

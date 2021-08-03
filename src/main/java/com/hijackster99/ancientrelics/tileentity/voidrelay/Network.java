package com.hijackster99.ancientrelics.tileentity.voidrelay;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.hijackster99.ancientrelics.blocks.VoidGas;
import com.hijackster99.ancientrelics.core.Util;

import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;

public class Network {

	private List<ICapabilityProvider> requesters;
	private List<ICapabilityProvider> senders;
	private List<ICapabilityProvider> storages;
	private List<VoidRelay> relays;
	
	private Checker checker = null;
	
	public int MAX_NETWORK = 20;
	public int MAX_TRANSFER = 200;
	
	public Network(VoidRelay source) {
		requesters = new ArrayList<ICapabilityProvider>();
		senders = new ArrayList<ICapabilityProvider>();
		storages = new ArrayList<ICapabilityProvider>();
		relays = new ArrayList<VoidRelay>();
		relays.add(source);
	}
	
	public void tick() {
		if(checker != null) {
			
		}else {
			List<Request> requests = new ArrayList<Request>();
			List<Request> send = new ArrayList<Request>();
			int transfered = 0;
			if(!storages.isEmpty() || !senders.isEmpty()) {
				for(ICapabilityProvider prov : requesters) {
					IFluidHandler tank = prov.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, Direction.UP).orElse(null);
					if(tank != null) {
						int amount = tank.getTankCapacity(0) - tank.getFluidInTank(0).getAmount();
						requests.add(new Request(tank, amount));
					}
				}
			}
			int transferPer = MAX_TRANSFER / requests.size();
			for(int i = 0; i < requests.size(); i++) {
				int t = 0;
				checks: {
					for(ICapabilityProvider prov : storages) {
						IFluidHandler tank = prov.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, Direction.UP).orElse(null);
						if(tank != null) {
							int amount = tank.getTankCapacity(0);
							if(amount >= transferPer) {
								tank.drain(new FluidStack(VoidGas.VOID_GAS_STILL,  transferPer), FluidAction.EXECUTE);
								requests.get(i).tank.fill(new FluidStack(VoidGas.VOID_GAS_STILL, transferPer), FluidAction.EXECUTE);
								transfered += transferPer;
								requests.remove(i);
								i--;
								break checks;
							}else {
								
							}
						}
					}
					for(ICapabilityProvider prov : senders) {
						
					}
				}
			}
			if(!storages.isEmpty()) {
				for(ICapabilityProvider prov : senders) {
					IFluidHandler tank = prov.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, Direction.UP).orElse(null);
					if(tank != null) {
						int amount = tank.getFluidInTank(0).getAmount();
						send.add(new Request(tank, amount));
					}
				}
			}
		}
	}
	
	public boolean addRelay(VoidRelay relay) {
		return mergeNetworks(relay.getNetwork());
	}
	
	private boolean mergeNetworks(Network net) {
		if(Util.containsAny(relays, net.relays)) return false;
		else if(getSize() + net.getSize() <= MAX_NETWORK) {
			relays.addAll(net.relays);
			senders.addAll(net.senders);
			requesters.addAll(net.requesters);
			storages.addAll(net.storages);
			return true;
		}
		return false;
	}
	
	public boolean removeRelay(VoidRelay relay) {
		List<VoidRelay> connections = relay.getConnections();
		for(VoidRelay r : connections) {
			r.removeConnection(relay);
		}
		if(relays.contains(relay)) {
			if(relays.size() == 1) {
				NetworkManager.INSTANCE.removeNetwork(this);
				return false;
			}else {
				relays.remove(relay);
				if(checker == null) {
					checker = new Checker();
				}else {
					checker.reset();
				}
			}
		}
		
		return true;
	}
	
	public int getSize() {
		return requesters.size() + senders.size() + storages.size() + relays.size();
	}
	
//	public List<VoidRelay> getAllConnections(VoidRelay relay) {
//		
//	}
	
	private class Checker {
		List<VoidRelay> relaysToCheck;
		int counter;
		
		private Checker() {
			relaysToCheck = new ArrayList<VoidRelay>();
			Collections.copy(relaysToCheck, relays);
			counter = 0;
		}
		
		private void reset() {
			relaysToCheck.clear();
			Collections.copy(relaysToCheck, relays);
			counter = 0;
		}
	}
	
	private static class Request {
		IFluidHandler tank;
		int amount;
		
		private Request(IFluidHandler tank, int amount) {
			this.tank = tank;
			this.amount = amount;
		}
	}
	
}

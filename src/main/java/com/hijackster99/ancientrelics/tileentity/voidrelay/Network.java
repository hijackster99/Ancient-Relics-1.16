package com.hijackster99.ancientrelics.tileentity.voidrelay;

import java.util.ArrayList;
import java.util.List;

import com.hijackster99.ancientrelics.core.Util;
import com.hijackster99.ancientrelics.tileentity.VoidRelay;

import net.minecraft.tileentity.TileEntity;

public class Network {

	private List<TileEntity> requesters;
	private List<TileEntity> senders;
	private List<TileEntity> storages;
	private List<VoidRelay> relays;
	
	private Checker checker = null;
	
	public int MAX_NETWORK = 20;
	
	public Network(VoidRelay source) {
		requesters = new ArrayList<TileEntity>();
		senders = new ArrayList<TileEntity>();
		storages = new ArrayList<TileEntity>();
		relays = new ArrayList<VoidRelay>();
		relays.add(source);
	}
	
	public void tick() {
		
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
		if(relays.contains(relay) && relays.size() == 1) {
			NetworkManager.INSTANCE.removeNetwork(this);
			return false;
		}
		
		return true;
	}
	
	public int getSize() {
		return requesters.size() + senders.size() + storages.size() + relays.size();
	}
	
//	public List<VoidRelay> getAllConnections(VoidRelay relay) {
//		
//	}
	
	public class Checker {
		
	}
	
}

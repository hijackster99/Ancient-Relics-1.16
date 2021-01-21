package com.hijackster99.ancientrelics.tileentity;

import java.util.ArrayList;
import java.util.List;

import com.hijackster99.ancientrelics.tileentity.voidrelay.Network;

public class VoidRelay extends ARTileEntity {

	private Network net;
	
	private List<VoidRelay> connections;
	
	public VoidRelay() {
		super(VOID_RELAY);
		net = new Network(this);
		connections = new ArrayList<VoidRelay>();
	}
	
	public Network getNetwork() {
		return net;
	}
	
	@Override
	public void remove() {
		super.remove();
		net.removeRelay(this);
	}

	public List<VoidRelay> getConnections() {
		return connections;
	}
	
	public void addConnection(VoidRelay relay) {
		connections.add(relay);
	}
	
	public void removeConnection(VoidRelay relay) {
		connections.remove(relay);
	}

}

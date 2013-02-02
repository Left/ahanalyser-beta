package com.areshaev.ahanalyser.auction.json;

import java.util.Set;

import com.google.common.collect.Sets;

public class AuctionJson {
	private RealmInfo realm;
	private AuctionList alliance;
	private AuctionList horde;
	private AuctionList neutral;

	public RealmInfo getRealm() {
		return realm;
	}
	
	public AuctionList getAlliance() {
		return alliance;
	}
	
	public AuctionList getHorde() {
		return horde;
	}
	
	public AuctionList getNeutral() {
		return neutral;
	}
	
	public Set<AuctionItem> forHorde() {
		return Sets.union(horde.getAuctions(), neutral.getAuctions());
	}
	
	public Set<AuctionItem> forAlliance() {
		return Sets.union(alliance.getAuctions(), neutral.getAuctions());
	}
}

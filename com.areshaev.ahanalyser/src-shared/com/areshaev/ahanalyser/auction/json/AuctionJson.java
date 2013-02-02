package com.areshaev.ahanalyser.auction.json;

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
}

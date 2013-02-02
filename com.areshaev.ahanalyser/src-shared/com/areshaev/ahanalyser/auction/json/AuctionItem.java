package com.areshaev.ahanalyser.auction.json;

import com.google.common.base.Objects;
import com.google.common.collect.Ordering;
import com.google.common.primitives.Longs;


/**
 * Single auction item
 */
public class AuctionItem implements Comparable<AuctionItem> {
	public static Ordering<AuctionItem> BY_BID = new Ordering<AuctionItem>() {
		@Override
		public int compare(AuctionItem left, AuctionItem right) {
			return Longs.compare(left.bid, right.bid);
		}		
	};

	public static Ordering<AuctionItem> BY_BUYOUT = new Ordering<AuctionItem>() {
		@Override
		public int compare(AuctionItem left, AuctionItem right) {
			return Longs.compare(left.buyout, right.buyout);
		}		
	};

	private long auc;
	private int item;
	private String owner;
	private long bid;
	private long buyout;
	private int quantity;
	// private String timeLeft;

	public long getAuc() {
		return auc;
	}

	public long getItem() {
		return item;
	}
	
	public String getOwner() {
		return owner;
	}
	
	public long getBid() {
		return bid;
	}
	
	public long getBuyout() {
		return buyout;
	}
	
	public long getQuantity() {
		return quantity;
	}
	/*
	public String getTimeLeft() {
		return timeLeft;
	}
	*/

	@Override
	public int compareTo(AuctionItem arg0) {
		return Longs.compare(auc, arg0.auc);
	}

	@Override
	public String toString() {
		return Objects.toStringHelper("")
				.addValue("" + auc + "")
				.add("Item", item)
				.add("Bid", bid)
				.add("Buyout", buyout)
				.add("Qty", quantity)
				.toString();
	}
	
	
}

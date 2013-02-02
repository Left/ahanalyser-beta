package com.areshaev.ahanalyser.auction.json;

import com.google.common.base.Function;
import com.google.common.base.Objects;
import com.google.common.primitives.Longs;


/**
 * Single auction item
 */
public class AuctionItem implements Comparable<AuctionItem> {
	
	public enum GetPrices implements Function<AuctionItem, Long> {
		BID {
			@Override
			public Long apply(AuctionItem input) {
				return input.getBid();
			}			
		},
		BUYOUT {
			@Override
			public Long apply(AuctionItem input) {
				return input.getBuyout();
			}
		};
	}

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

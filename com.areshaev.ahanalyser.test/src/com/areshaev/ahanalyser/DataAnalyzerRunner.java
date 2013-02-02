package com.areshaev.ahanalyser;

import java.io.InputStreamReader;
import java.util.Collection;
import java.util.Map.Entry;
import java.util.NavigableSet;

import com.areshaev.ahanalyser.auction.json.AuctionItem;
import com.areshaev.ahanalyser.auction.json.AuctionJson;
import com.google.common.base.Charsets;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Ordering;
import com.google.common.collect.TreeMultimap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class DataAnalyzerRunner {
	public static void main(String[] args) {
		long currentTimeMillis = System.currentTimeMillis();
		
		Gson create = (new GsonBuilder()).create();
		AuctionJson auction = create.fromJson(
				new InputStreamReader(DataAnalyzerRunner.class.getResourceAsStream("auctions.json"), Charsets.UTF_8), 
				AuctionJson.class);
		
		System.out.println(
				auction.getHorde().getAuctions().size() + "," +
				auction.getAlliance().getAuctions().size() + "," +
				auction.getNeutral().getAuctions().size()
				);
		
		System.out.println("Parsing:" + (System.currentTimeMillis() - currentTimeMillis));

		Multimap<String, AuctionItem> allByOwner = ArrayListMultimap.create();
		TreeMultimap<Long, AuctionItem> allItemsByType = TreeMultimap.create(
				Ordering.natural(),
				AuctionItem.BY_BID);

		for (AuctionItem auc : auction.getAlliance().getAuctions()) {
			allItemsByType.put(auc.getItem(), auc);
			allByOwner.put(auc.getOwner(), auc);
		}

		System.out.println(allByOwner.size());
		for (Entry<String, Collection<AuctionItem>> e : allByOwner.asMap().entrySet()) {
			System.out.println(e.getKey() + "->" + e.getValue().size());
			for (AuctionItem aui : e.getValue()) {
				// Find all items
				NavigableSet<AuctionItem> allOther = allItemsByType.get(aui.getItem());

				AuctionItem min = allOther.iterator().next();
				AuctionItem max = allOther.descendingIterator().next();
				/*
				int betterThenThis = 0;
				int worseThenThis = 0;
				
				// allOther.
				for (AuctionItem it : allOther) {
					if (it != aui) {
						int compare = AuctionItem.BY_BID.compare(it, aui);
						if (compare < 0) {
							betterThenThis += it.getQuantity();
						} else if (compare > 0) {
							worseThenThis += it.getQuantity();
						}
					}
				}
				*/
				
				if (min.getBid() == aui.getBid()) {
					// Best seller for this item 
					System.out.println(e.getKey() + " is a best seller for " + aui + " " + 
							min.getBid() + " " + 
							max.getBid());
				}
			}
		}
		/*
		for (Entry<Long, Collection<AuctionItem>> e : allItemsByType.asMap().entrySet()) {
			System.out.println(e.getKey() + "->" + e.getValue().size());	
		}
		*/

		System.out.println(System.currentTimeMillis() - currentTimeMillis);
	}
}

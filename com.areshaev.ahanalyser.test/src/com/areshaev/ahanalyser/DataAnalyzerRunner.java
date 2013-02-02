package com.areshaev.ahanalyser;

import java.io.InputStreamReader;
import java.util.Collection;
import java.util.Map.Entry;
import java.util.NavigableSet;

import com.areshaev.ahanalyser.auction.json.AuctionItem;
import com.areshaev.ahanalyser.auction.json.AuctionItem.GetPrices;
import com.areshaev.ahanalyser.auction.json.AuctionJson;
import com.google.common.base.Charsets;
import com.google.common.base.Predicate;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Iterables;
import com.google.common.collect.Multimap;
import com.google.common.collect.Ordering;
import com.google.common.collect.Sets;
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
		for (AuctionItem auc : Sets.union(
				auction.forAlliance(),
				auction.forHorde())) {
			allByOwner.put(auc.getOwner(), auc);
		}
		
		for (GetPrices getPricesMethod : AuctionItem.GetPrices.values()) {
			TreeMultimap<Long, AuctionItem> allItemsByType = TreeMultimap.create(
					Ordering.natural(),
					Ordering.natural().onResultOf(getPricesMethod));
	
			for (AuctionItem auc : Sets.union(
					auction.forAlliance(),
					auction.forHorde())) {
				allItemsByType.put(auc.getItem(), auc);
			}
	
			for (Entry<String, Collection<AuctionItem>> e : allByOwner.asMap().entrySet()) {
				// System.out.println(e.getKey() + "->" + e.getValue().size());

				for (final AuctionItem aui : e.getValue()) {
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
					
					if (getPricesMethod.apply(min).equals(getPricesMethod.apply(aui))) {
						// Best seller for this item
						/*
						System.out.println(getPricesMethod.name() + ":" + e.getKey() + " is a best seller for " + aui + " " + 
								min.getBid() + " " + 
								max.getBid());
						*/
					}
					boolean isUniqueSeller = Iterables.all(allOther, new Predicate<AuctionItem>() {
						@Override
						public boolean apply(AuctionItem input) {
							return input.getOwner().equals(aui.getOwner());
						}						
					});
					if (isUniqueSeller) {
						System.out.println(getPricesMethod.name() + ":" + e.getKey() + " is a unique seller for " + aui);
					}
				}
			}
			/*
			for (Entry<Long, Collection<AuctionItem>> e : allItemsByType.asMap().entrySet()) {
				System.out.println(e.getKey() + "->" + e.getValue().size());	
			}
			*/
		}

		System.out.println(System.currentTimeMillis() - currentTimeMillis);
	}
}

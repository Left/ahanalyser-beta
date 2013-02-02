package com.areshaev.ahanalyser.data;

import java.util.List;

import javax.persistence.Id;

public class Auctioner {
	@Id String name;
	List<Bid> bids;
}

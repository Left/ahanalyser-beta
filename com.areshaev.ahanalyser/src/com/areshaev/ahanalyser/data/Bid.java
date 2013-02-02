package com.areshaev.ahanalyser.data;

import javax.persistence.Id;

public class Bid {
	@Id Long id;
	long item;
	long bid;
	long buyout;
	int quantity;
}

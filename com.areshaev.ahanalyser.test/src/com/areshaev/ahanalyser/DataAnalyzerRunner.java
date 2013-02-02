package com.areshaev.ahanalyser;

import java.io.InputStreamReader;
import java.io.Reader;

import com.google.common.base.Charsets;

public class DataAnalyzerRunner {
	public static void main(String[] args) {
		Reader json = new InputStreamReader(
				DataAnalyzerRunner.class.getResourceAsStream("auctions.json"), Charsets.UTF_8);
		
		DataAnalyzer.processJSON(json);
	}
}

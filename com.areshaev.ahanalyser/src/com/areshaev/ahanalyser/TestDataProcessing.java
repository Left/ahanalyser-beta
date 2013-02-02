package com.areshaev.ahanalyser;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.areshaev.ahanalyser.data.Personality;
import com.google.common.base.Charsets;
import com.googlecode.objectify.ObjectifyService;

@SuppressWarnings("serial")
public class TestDataProcessing extends HttpServlet {
	static {
		ObjectifyService.register(Personality.class);
	}

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		long currentTimeMillis = System.currentTimeMillis();
		
		resp.setContentType("text/plain; charset=\"utf-8\"");
		
		Reader json = new InputStreamReader(
				DataAnalyzer.class.getResourceAsStream("auctions.json"), Charsets.UTF_8);
		
		DataAnalyzer.processJSON(json);

		resp.getWriter().println("" + (System.currentTimeMillis() - currentTimeMillis));		
	}
}

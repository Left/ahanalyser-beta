package com.areshaev.ahanalyser;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.areshaev.ahanalyser.data.Personality;
import com.google.common.base.Strings;
import com.google.common.io.ByteStreams;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;

@SuppressWarnings("serial")
public class GetData extends HttpServlet {
	static {
		ObjectifyService.register(Personality.class);
	}

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		Objectify ofy = ObjectifyService.begin();
				
		List<String> split = Arrays.asList(Strings.nullToEmpty(req.getPathInfo()).split("/", -1));

		Personality personality = ofy.get(Personality.class, split.get(1) + ".csv");

		resp.setContentType("application/octet-stream");
		ServletOutputStream outputStream = resp.getOutputStream();
		ByteStreams.copy(new ByteArrayInputStream(personality.getData()), outputStream);
		outputStream.close();
	}
}

package com.areshaev.ahanalyser;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.areshaev.ahanalyser.data.Personality;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.io.ByteStreams;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;

@SuppressWarnings("serial")
public class PostData extends HttpServlet {
	static {
		ObjectifyService.register(Personality.class);
	}

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setStatus(405);
		resp.setContentType("text/plain");
		resp.getWriter().println("Method is not allowed dude");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		ServletInputStream inputStream = req.getInputStream();
		ZipInputStream zipped = new ZipInputStream(inputStream);

		Objectify ofy = ObjectifyService.begin();
		List<Personality> allPassed = Lists.newArrayList();

		Map<String, String> content = Maps.newLinkedHashMap();
		for (ZipEntry entry = zipped.getNextEntry();
			entry != null; 
			entry = zipped.getNextEntry()) {

			if (entry.isDirectory()) {
				// ??
			} else {
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				ByteStreams.copy(zipped, out);
				
				// HashCode hash = ByteStreams.hash(ByteStreams.newInputStreamSupplier(out.toByteArray()), Hashing.sha1());
				Personality pers = new Personality(entry.getName(), out.toByteArray());
				allPassed.add(pers);
				/*
				// Get a file service
				FileService fileService = FileServiceFactory.getFileService();

				// Create a new Blob file with mime-type "text/plain"
				AppEngineFile file = fileService.createNewBlobFile("application/json");
				FileWriteChannel writeChannel = fileService.openWriteChannel(file, true);

				writeChannel.write(ByteBuffer.wrap(out.toByteArray()));

				// Now finalize
				writeChannel.closeFinally();

				String path = file.getFullPath();
				*/
				
				content.put(entry.getName(), "");
			}
	    }
		ofy.put(allPassed);

		resp.setContentType("text/plain; charset=\"utf-8\"");
		for (Entry<String, String> e : content.entrySet()) {
			resp.getWriter().println(e.getKey() + " " + e.getValue());
		}
		resp.getWriter().println("OK");
	}
}

package com.areshaev.ahanalyser;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import com.google.common.base.Strings;
import com.google.common.io.ByteStreams;

public class PureHttpTest {
	public static void main(String[] args) {
		try {
			URL url = new URL("https://ahanalyser.appspot.com/post");
			URLConnection openConnection = url.openConnection();
			openConnection.setDoOutput(true);
			OutputStream outputStream = openConnection.getOutputStream();
			ByteStreams.copy(PureHttpTest.class.getResourceAsStream("ah.zip"), outputStream);
			outputStream.close();
			
			InputStream inputStream = openConnection.getInputStream();
			ByteArrayOutputStream res = new ByteArrayOutputStream();
			ByteStreams.copy(inputStream, res);
			
			String contentEncoding = openConnection.getContentEncoding();
			if (Strings.isNullOrEmpty(contentEncoding)) {
				contentEncoding = "utf-8";
			}
			
			System.out.println(new String(res.toByteArray(), contentEncoding));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

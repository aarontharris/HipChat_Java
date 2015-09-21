package com.leap12.hipj.util;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import com.leap12.common.StrUtl;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.OkUrlFactory;

public class Fetcher {
	private final OkUrlFactory mFactory;

	public Fetcher() {
		OkHttpClient client = new OkHttpClient();
		mFactory = new OkUrlFactory(client);
	}

	public String fetch(String urlString) throws Exception {
		InputStream iStream = null;
		try {
			URL url = URI.create(urlString).toURL();
			HttpURLConnection conn = mFactory.open(url);
			iStream = conn.getInputStream();
			String in = StrUtl.toString(iStream);
			return in;
		} finally {
			if (iStream != null) {
				iStream.close();
			}
		}
	}

}

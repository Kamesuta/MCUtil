package com.kamesuta.mc.mcutil.screenshot;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.JsonParseException;
import com.kamesuta.mc.mcutil.Reference;

public class Debug {

	public Debug() {
		// TODO 自動生成されたコンストラクター・スタブ
	}

	public void up(final Logger logger) throws SynchronizeException {
		try {
			final String host = "https://upload.gyazo.com/api/upload/easy_auth";
			final String clientId = "df9edab530e84b4c56f9fcfa209aff1131c7d358a91d85cc20b9229e515d67dd";
			final URI uprepository = new URI(host + "?client_id=" + clientId);

			final MultipartEntityBuilder entitybuilder = MultipartEntityBuilder.create();

			final File file = new File("C:/Users/b7n/git/MCUtil/screenshots/2016-05-29_21.18.35.png");
			entitybuilder.addBinaryBody("image_url", file, ContentType.DEFAULT_BINARY, file.getName());

			final HttpEntity entity = entitybuilder.build();

			final HttpPost httpPost = new HttpPost(uprepository);
			httpPost.setEntity(entity);
			final HttpResponse response = getHttpClient().execute(httpPost);

			final BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			final StringBuilder sb = new StringBuilder();
			String line;
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}

			logger.info(sb.toString());
			br.close();

		} catch (final ClientProtocolException e) {
			throw new SynchronizeException(e);
		} catch (final IllegalStateException e) {
			throw new SynchronizeException(e);
		} catch (final JsonParseException e) {
			throw new SynchronizeException(e);
		} catch (final IOException e) {
			throw new SynchronizeException(e);
		} catch (final URISyntaxException e) {
			throw new SynchronizeException(e);
		}
	}

	public static HttpClient getHttpClient() {
		// request configuration
		final RequestConfig requestConfig = RequestConfig.custom()
				.setConnectTimeout(5000)
				.setSocketTimeout(5000)
				.setCircularRedirectsAllowed(false)
				.build();

		// headers
		final List<Header> headers = new ArrayList<Header>();
		headers.add(new BasicHeader("Accept-Charset", "utf-8"));
		headers.add(new BasicHeader("Accept-Language", "ja, en;q=0.8"));
		headers.add(new BasicHeader("User-Agent", Reference.MODID));
		// create client
		return HttpClientBuilder.create().setDefaultRequestConfig(requestConfig).setDefaultHeaders(headers).build();
	}

	public static void main(final String[] args) throws SynchronizeException {
		final Logger logger = LogManager.getLogger("Debug");
		final Debug debug = new Debug();
		debug.up(logger);
	}

}

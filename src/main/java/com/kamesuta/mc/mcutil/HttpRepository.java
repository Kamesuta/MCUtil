package com.kamesuta.mc.mcutil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.compress.utils.IOUtils;
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

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.stream.JsonReader;
import com.kamesuta.mc.mcutil.screenshot.SynchronizeException;

public class HttpRepository {
	public static void download(final String uri, final File file) throws SynchronizeException {
		try {
			final URI downrepository = new URI(uri);

			HttpEntity entity = MultipartEntityBuilder.create().build();

			final HttpPost httpPost = new HttpPost(downrepository);
			httpPost.setEntity(entity);
			final HttpResponse response = getHttpClient().execute(httpPost);
			entity = response.getEntity();

			IOUtils.copy(response.getEntity().getContent(), new FileOutputStream(file));
		} catch (final ClientProtocolException e) {
			throw new SynchronizeException(e);
		} catch (final IllegalStateException e) {
			throw new SynchronizeException(e);
		} catch (final IOException e) {
			throw new SynchronizeException(e);
		} catch (final URISyntaxException e) {
			throw new SynchronizeException(e);
		}
	}

	public static List<HttpRepositoryResult> upload(final String uri, final File... files) throws SynchronizeException {
		try {
			final URI uprepository = new URI(uri);

			final MultipartEntityBuilder entitybuilder = MultipartEntityBuilder.create();

			for (final File file : files) {
				entitybuilder.addBinaryBody("upfile[]", file, ContentType.DEFAULT_BINARY, file.getName());
			}

			final HttpEntity entity = entitybuilder.build();

			final HttpPost httpPost = new HttpPost(uprepository);
			httpPost.setEntity(entity);
			final HttpResponse response = getHttpClient().execute(httpPost);

			final HttpRepositoryResult[] resultarray = new Gson()
					.fromJson(new JsonReader(new InputStreamReader(response.getEntity().getContent())),
							HttpRepositoryResult[].class);
			return Lists.newArrayList(resultarray);

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

	public static class HttpRepositoryResult {
		public Result results;
		public String status;
		@Override
		public String toString() {
			return String.format("HttpRepositoryResult [results=%s, status=%s]", this.results, this.status);
		}
	}

	public static class Result {
		public String name;
		public int size;
		public String type;
		@Override
		public String toString() {
			return String.format("Result [name=%s, size=%d, type=%s]", this.name, this.size, this.type);
		}
	}
}

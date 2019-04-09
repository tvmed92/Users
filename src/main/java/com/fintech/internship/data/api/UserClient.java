package com.fintech.internship.data.api;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

public class UserClient {


    private OkHttpClient userAPI = new OkHttpClient();

    public String doGetRequest(String urlFormat, int upperLimit) throws IOException {
        int limit = ThreadLocalRandom.current().nextInt(1, upperLimit);
        String url = String.format(urlFormat, limit);
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = userAPI.newCall(request).execute();
        return Optional.of(response.body().string())
                .orElseThrow(() -> new RuntimeException("Empty response body"));
    }
}
package com.tanovait.sunnyapp.api;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class HeaderInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request()
                .newBuilder()
                .addHeader("x-rapidapi-key", "cd17f9763dmsh7305da9366d42cbp14d1cejsna849fbc95db0")
                .addHeader("x-rapidapi-host", "community-open-weather-map.p.rapidapi.com")
                .build();
        Response response = chain.proceed(request);
        return response;
    }
}
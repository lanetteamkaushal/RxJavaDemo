package com.lanet.rxjavademo.apis;

/**
 * Created For LaNet Team
 * by lcom75 on 22/6/16.
 */

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;

public class ServiceFactory {

    /**
     * Creates a retrofit service from an arbitrary class (clazz)
     *
     * @param clazz    Java interface of the retrofit service
     * @param endPoint REST endpoint url
     * @return retrofit service with defined endpoint
     */
    public static <T> T createRetrofitService(final Class<T> clazz, final String endPoint) {
        final RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(endPoint)
                .setRequestInterceptor(new RequestInterceptor() {
                    @Override
                    public void intercept(RequestFacade request) {
                        request.addHeader("Authorization", "token bad9f1669b782222327c8f163500e260e929ce31");
                    }
                })
                .build();
        T service = restAdapter.create(clazz);

        return service;
    }
}
package com.lanet.rxjavademo.apis;

import retrofit.http.GET;
import retrofit.http.Path;
import rx.Observable;

/**
 * Created For LaNet Team
 * by lcom75 on 22/6/16.
 */
public interface GithubService {
    String SERVICE_ENDPOINT = "https://api.github.com";

    @GET("/users/{login}")
    Observable<Github> getUser(@Path("login") String login);
}

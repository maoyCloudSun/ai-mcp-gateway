package com.duncan.ai.proxy.api;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface IOpenAiApiProxy {
    /**
     * 这个是请求,定义规范
     * @param request
     * @return
     */
    @POST("v1/chat/completions")
    Single<Object> completions(@Body Object request);
}

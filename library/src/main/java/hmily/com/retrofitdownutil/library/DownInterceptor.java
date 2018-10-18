package hmily.com.retrofitdownutil.library;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * time 2018/10/17.
 * author hmily
 * description:下载拦截器
 */
public final class DownInterceptor<T extends DownListener> implements Interceptor {
    private T downListener;

    public DownInterceptor(T downListener) {
        this.downListener = downListener;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response response = chain.proceed(chain.request());

        return response.newBuilder().body(new DownResponseBody<>(response.body(), downListener)).build();
    }
}

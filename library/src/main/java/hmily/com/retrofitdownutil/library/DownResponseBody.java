package hmily.com.retrofitdownutil.library;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Source;

import static okio.Okio.buffer;

/**
 * time 2018/10/17.
 * author hmily
 * description:下载响应体
 */
public final class DownResponseBody<T extends DownListener> extends ResponseBody {

    private ResponseBody responseBody;
    //相当于inputStream
    private BufferedSource bufferedSource;
    //下载监听
    private T downListener;

    public DownResponseBody(ResponseBody responseBody, T downListener) {
        this.responseBody = responseBody;
        this.downListener = downListener;
        this.downListener.onStart(contentLength());
    }

    @Override
    public MediaType contentType() {
        return responseBody.contentType();
    }

    @Override
    public long contentLength() {
        return responseBody.contentLength();
    }

    @Override
    public BufferedSource source() {
        if (bufferedSource == null) {
            bufferedSource = buffer(source(responseBody.source()));
        }
        return bufferedSource;
    }

    private Source source(Source source) {
        return new ForwardingSource(source) {
            long totalBytesRead = 0L;

            @Override
            public long read(Buffer sink, long byteCount) throws IOException {
                long bytesRead = super.read(sink, byteCount);
                totalBytesRead += bytesRead != -1 ? bytesRead : 0;
                if (null != downListener) {
                    if (bytesRead != -1) {
                        downListener.onProgress(totalBytesRead * 1.0f / contentLength());
                    }
                }
                return bytesRead;
            }
        };
    }
}

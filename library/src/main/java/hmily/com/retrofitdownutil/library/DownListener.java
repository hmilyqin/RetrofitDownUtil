package hmily.com.retrofitdownutil.library;

/**
 * time 2018/10/17.
 * author hmily
 * description:下载监听，可自行扩展
 */
public interface DownListener {
    /**
     * 开始下载
     *
     * @param allLength 文件总长度
     */
    void onStart(long allLength);

    /**
     * 进度回调
     *
     * @param progress 当前进度
     */
    void onProgress(float progress);

    /**
     * 下载错误回调
     *
     * @param t 错误信息
     */
    void onError(Throwable t);
}

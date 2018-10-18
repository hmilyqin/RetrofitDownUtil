package hmily.com.retrofitdownutil.library;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * time 2018/10/17.
 * author hmily
 * description:文件下载工具
 */
public final class DownUtils {
    private Observable<ResponseBody> observable;
    private File file;
    private DownHandleSubscriber observer;

    public static DownUtils getInstance() {
        return new DownUtils();
    }

    public <T extends DownListener> void downLoad(final T downLoadListener) {
        if (null == observable) {
            throw new NullPointerException("the param observable(Observable<ResponseBody>) is NullPointer");
        }

        if (null == file) {
            throw new NullPointerException("the param file is NullPointer");
        }

        if (null == observer) {
            throw new NullPointerException("the param observer is NullPointer");
        }

        observable.subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io())
                .map(ResponseBody::byteStream)
                .observeOn(Schedulers.computation())
                .doOnNext(inputStream -> writeFile(inputStream, file, downLoadListener))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public DownUtils observable(Observable<ResponseBody> observable) {
        this.observable = observable;
        return this;
    }

    public DownUtils file(File file) {
        this.file = file;
        return this;
    }

    public DownUtils observer(DownHandleSubscriber observer) {
        this.observer = observer;
        return this;
    }

    /**
     * 将输入流写入文件
     *
     * @param inputString
     * @param file
     */
    private static <T extends DownListener> void writeFile(InputStream inputString, File file, T downLoadListener) {
        if (file.exists()) {
            file.delete();
        } else if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
            file.mkdir();
        } else {
            file.mkdir();
        }
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(file);

            byte[] b = new byte[1024];

            int len;
            while ((len = inputString.read(b)) != -1) {
                fos.write(b, 0, len);
            }
            inputString.close();
            fos.close();

        } catch (IOException e) {
            downLoadListener.onError(e);
        }
    }
}

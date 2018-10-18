package hmily.com.retrofitdownutil.library;

import java.io.InputStream;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * time 2018/10/17.
 * author hmily
 * description:
 */
public abstract class DownHandleSubscriber implements Observer<InputStream> {
    @Override
    public void onSubscribe(@NonNull Disposable d) {

    }

    @Override
    public void onNext(InputStream o) {

    }

    @Override
    public void onError(@NonNull Throwable t) {
        t.printStackTrace();
    }
}

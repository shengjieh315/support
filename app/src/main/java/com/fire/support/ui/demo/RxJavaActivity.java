package com.fire.support.ui.demo;

import android.os.Bundle;
import android.widget.TextView;

import com.fire.support.R;
import com.fire.support.base.BaseActivity;
import com.fire.support.utils.ThreadUtils;
import com.socks.library.KLog;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class RxJavaActivity extends BaseActivity {

    @BindView(R.id.tv_content)
    TextView tvContent;

    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_rx_java);
        ButterKnife.bind(this);

    }

    @Override
    public void initListener(Bundle savedInstanceState) {

    }

    @Override
    public void initData(Bundle savedInstanceState) {
        getData();
    }


    private void getData() {
        Observable.mergeArray(getObservableInfo(1), getObservableInfo(2), getObservableInfo(3), getObservableInfo(4), getObservableInfo(5))
                .subscribe(new Observer<Bundle>() {
                    StringBuffer stringBuffer = new StringBuffer();

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Bundle bundle) {
                        if (context == null || context.isFinishing()) {
                            return;
                        }
                        int result = bundle.getInt("result");
                        stringBuffer.append(result);
                        if (result == 5) {
                            onComplete();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        ThreadUtils.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tvContent.setText(stringBuffer.toString());
                            }
                        });
                    }
                });

    }

    private Observable<Bundle> getObservableInfo(int i) {
        return Observable.create(new ObservableOnSubscribe<Bundle>() {
            @Override
            public void subscribe(ObservableEmitter<Bundle> emitter) throws Exception {
                Bundle bundle = new Bundle();
                bundle.putInt("result", i);
                emitter.onNext(bundle);
            }
        });
    }

}

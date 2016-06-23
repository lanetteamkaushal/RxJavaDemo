package com.lanet.rxjavademo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;

import com.lanet.rxjavademo.adapters.CardAdapter;
import com.lanet.rxjavademo.apis.Github;
import com.lanet.rxjavademo.apis.GithubService;
import com.lanet.rxjavademo.apis.ServiceFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements CardAdapter.OnStartDragListener {
    CardAdapter mCardAdapter;
    RecyclerView rlList;
    private static final String TAG = "MainActivity";
    private ItemTouchHelper mItemTouchHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rlList = (RecyclerView) findViewById(R.id.rlList);
        mCardAdapter = new CardAdapter(this);
        rlList.setAdapter(mCardAdapter);
        rlList.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false));
        android.support.v7.widget.helper.ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(mCardAdapter, this);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(rlList);
        GithubService service = ServiceFactory.createRetrofitService(GithubService.class, GithubService.SERVICE_ENDPOINT);
        for (String login : Data.githubList) {
            service.getUser(login)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<Github>() {
                        @Override
                        public final void onCompleted() {
                            // do nothing
                        }

                        @Override
                        public final void onError(Throwable e) {
                            Log.e(TAG, e.getMessage());
                            e.printStackTrace();
                        }

                        @Override
                        public final void onNext(Github response) {
                            mCardAdapter.addData(response);
                        }
                    });
        }

//        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.START) {
////            @Override
////            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
////                Log.d(TAG, "getMovementFlags() called with: " + "recyclerView = [" + recyclerView + "], viewHolder = [" + viewHolder + "]");
////                return 0;
////            }
//
//            @Override
//            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
//                Log.d(TAG, "onMove() called with: " + "recyclerView = [" + recyclerView + "], viewHolder = [" + viewHolder + "], target = [" + target + "]");
//                return false;
//            }
//
//            @Override
//            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
//                Log.d(TAG, "onSwiped() called with: " + "viewHolder = [" + viewHolder + "], direction = [" + direction + "]");
//                if (mCardAdapter.removeItemOnSwipe(viewHolder.getAdapterPosition())) {
//                    mCardAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
//                }
//            }
//
//            @Override
//            public int getSwipeDirs(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
//                // can only swipe-dismiss certain sources
//                Log.d(TAG, "getSwipeDirs() called with: " + "recyclerView = [" + recyclerView + "], viewHolder = [" + viewHolder + "]");
//                return makeMovementFlags(0, (ItemTouchHelper.START));
//            }
//        });
//        itemTouchHelper.attachToRecyclerView(rlList);

//        Observable<String> myObservable = Observable.create(
//                new Observable.OnSubscribe<String>() {
//                    @Override
//                    public void call(Subscriber<? super String> sub) {
//                        sub.onNext("Hello, world!");
//                        sub.onCompleted();
//                    }
//                }
//        );
//
//        Subscriber<String> mySubscriber = new Subscriber<String>() {
//            @Override
//            public void onNext(String s) {
//                Log.d(TAG, "onNext: " + s);
//            }
//
//            @Override
//            public void onCompleted() {
//            }
//
//            @Override
//            public void onError(Throwable e) {
//            }
//        };
//
//        myObservable.subscribe(mySubscriber);

//        Observable<String> myObservable =
//                Observable.just("Hello, world!");
//        Action1<String> onNextAction = new Action1<String>() {
//            @Override
//            public void call(String s) {
//                Log.d(TAG, "call: " + s);
//            }
//        };
//        myObservable.subscribe(onNextAction);

//        Observable.just("Hello, world!")
//                .subscribe(new Action1<String>() {
//                    @Override
//                    public void call(String s) {
//                        Log.d(TAG, "call: " + s);
//                    }
//                });
//        Observable.just("Hello World!").subscribe(s -> Log.d(TAG, "onCreate: "+s));
//        Observable.just("Hello World!").map(new Func1<String, Object>() {
//            @Override
//            public Object call(String s) {
//                return s+" -Bhavin";
//            }
//        }).subscribe(s -> Log.d(TAG, "onCreate: "+s));
//        Observable.just("Hello World!").map(s -> s + " -Bhavin").subscribe(s -> Log.d(TAG, "onCreate: " + s));
//        Observable.just("Hello World").map(new Func1<String, Integer>() {
//            @Override
//            public Integer call(String s) {
//                return s.hashCode();
//            }
//        }).subscribe(s -> Log.d(TAG, "onCreate: " + Integer.toString(s)));
//        Observable.just("Hello, world!")
//                .map(s -> s.hashCode())
//                .subscribe(i -> Log.d(TAG, "onCreate: " + Integer.toString(i)));
//        query("Hello, world!")
//                .flatMap(new Func1<List<String>, Observable<String>>() {
//                    @Override
//                    public Observable<String> call(List<String> urls) {
//                        return Observable.from(urls);
//                    }
//                })
//                .subscribe(url -> System.out.println(url));

    }

//    public Observable<List<String>> query(String text) {
//        String[] urls = text.split(",");
//        List<String> strings = new ArrayList<>();
//        strings.addAll(Arrays.asList(urls));
//        return (Observable<List<String>>) strings;
//    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);
    }
}

package com.fire.okhttp.threadpool;

public interface SingleJob<O,T> {

    T run(O o);
}

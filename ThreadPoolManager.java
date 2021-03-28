package com.example.threadmanager;


import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolManager {
    private static  ThreadPool threadPool;
    public static ThreadPoolExecutor executor;
    public static ThreadPool getThreadPool(){
        if (threadPool==null){
            synchronized (ThreadPoolManager.class){
                if(threadPool==null){
                    //核心线程数等于处理器个数*2
                    int corePoolSize = Runtime.getRuntime().availableProcessors()*2;
                    int maximumPoolSize = 20;
                    long keepAILVETIME = 200L;
                    threadPool = new ThreadPool(corePoolSize,maximumPoolSize,keepAILVETIME);
                }
            }

        }
        return threadPool;
    }
    public static class ThreadPool{
//        public static ThreadPoolExecutor executor = null;
        private int corePoolSize;
        private int maximumPoolSize;
        private long keepAliveTime = 0;
        public ThreadPool(int corePoolSize,int maximumPoolSize,long keepAliveTime){
            super();
            this.corePoolSize=corePoolSize;//核心线程数
            this.maximumPoolSize=maximumPoolSize;//最大线程数
            this.keepAliveTime=keepAliveTime;//线程排队时间
        }
        public void execute(Runnable runnable){
            if (runnable==null){
                return;
            }
            if (executor==null){
                executor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime,
                        TimeUnit.MILLISECONDS,// 时间单位
                        new LinkedBlockingQueue<Runnable>(),// 线程队列
                        Executors.defaultThreadFactory(),//线程工厂
                        new ThreadPoolExecutor.AbortPolicy());
            }
            //给线程池添加一个线程
            executor.execute(runnable);
        }
    }
    public void stopThreadPool() {
        if ( executor!= null) {
            executor.shutdown();
            executor = null;
        }
    }



}
//ThreadPoolManager.getThreadPool().execute(new Runnable() {
//@Override
//public void run() {
//           SHDHADHASDSA
//        }
//        });
//使用例子
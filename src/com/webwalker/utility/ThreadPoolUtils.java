package com.webwalker.utility;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ThreadPoolUtils {

    private static ThreadPoolUtils threadPoolUtils;
    
    private static ExecutorService executor;
    
    private ThreadPoolUtils()
    {
        executor =  Executors.newCachedThreadPool();
    }
    
    /**
     * 获得线程池实例
     * @return
     */
    public static ThreadPoolUtils getInstance()
    {
        if(threadPoolUtils==null)
        {
            threadPoolUtils = new ThreadPoolUtils();
        }
        return threadPoolUtils;
    }
    
    /**
     * 执行任务
     * @param task
     */
    public void execute(Runnable task)
    {
        executor.execute(task);
    }
    
    /**
     * 提交任务
     * @param task
     * @return
     */
    public Future submit(Runnable task)
    {
        return executor.submit(task);
    }
    
    
}

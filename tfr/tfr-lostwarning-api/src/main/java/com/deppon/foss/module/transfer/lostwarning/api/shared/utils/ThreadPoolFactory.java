package com.deppon.foss.module.transfer.lostwarning.api.shared.utils;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;


/**
 * 线程池工厂
 * 
 * 项目名称：tfr-lostwarning-api
 * 
 * 类名称：ThreadPoolFactory
 * 
 * 创建人：lyz 263072
 * 
 * 创建时间：2015-6-11 下午7:09:30
 * 
 * 版权所有：上海德邦物流有限公司
 */
public class ThreadPoolFactory {
	
	
	private final static int CoreThreadCounts = 200;//池中所保存的线程数，包括空闲线程。
	private final static int maxThreadCounts = 400;//线程池维护线程的最大数量
	private final static long spanTime = 100L;  //当线程数大于核心时，此为终止前多余的空闲线程等待新任务的最长时间
	private final static int queueLength = 2000; //队列长度
	
	private ThreadPool  pool;  //线程池
	
	private static ThreadPoolFactory factory;
	
	public static ThreadPoolFactory getInstance(){
		factory = new ThreadPoolFactory();
		factory.initThreadPool();
		return factory;
    }
	
	/**
	 * @Description: 初始化线程池
	 * @date 2015-6-12 上午10:41:44   
	 * @author 263072
	 */
	public void initThreadPool(){
		BlockingQueue<Runnable> blockingQueue = new ArrayBlockingQueue<Runnable>(queueLength);
        pool = new ThreadPool(CoreThreadCounts, maxThreadCounts, spanTime, TimeUnit.MILLISECONDS, blockingQueue);
        pool.setRejectedExecutionHandler(new ThreadRejectedExecutionHandler());
	}
	
	
	/**
	 * @Description: 新增线程池任务
	 * @date 2015-6-11 下午7:42:28   
	 * @author 263072 
	 * @param run
	 */
    public synchronized void addTask(Runnable run){
    	
        pool.execute(run);
    }
     
    /**
	 * @Description: 新增线程池任务
	 * @date 2015-6-11 下午7:42:28   
	 * @author 263072 
	 * @param run
	 */
    public synchronized void addTask(List<Runnable> runs){
        if(runs != null){
            for(Runnable r:runs){
                this.addTask(r);
            }
        }
    }
     
   /**
    * @Description: 关闭线程池
    * @date 2015-6-11 下午7:42:51   
    * @author 263072
    */
    public void closePool(){
        pool.shutdown();
    }
    
    /**
     * @Description: 打开线程池
     * @date 2015-6-11 下午7:43:50   
     * @author 263072
     */
    public void openPool(){
    	initThreadPool();
    }
    
    /**
     * @Description: 判断线程池中所有任务是否完成
     * @date 2015-7-18 下午3:00:44   
     * @author 263072 
     * @return
     */
    public boolean getTerminatedStatus(){
    	return pool.isTerminated();
    }


	public  ThreadPool getPool() {
		return pool;
	}

    
}

package frame.base.core.util.thread;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;



/**
 *  线程管理池
 *  @author:    
 */

public class ThreadPoolManager implements IThreadPoolManager{
	private int corePoolSize=5;
	private int maximumPoolSize=10;
	private long keepAliveTime=100;
	private int workQueueSize=0;
	private TimeUnit timeUnit=TimeUnit.MILLISECONDS;
	private BlockingQueue<Runnable> workQueue=null;
	private RejectedExecutionHandler handler=new ThreadPoolExecutor.CallerRunsPolicy();
	
	public ThreadPoolManager(){
		
	}
	
    public int getWorkQueueSize() {
		return workQueueSize;
	}

	public void setWorkQueueSize(int workQueueSize) {
		this.workQueueSize = workQueueSize;
	}

	/**
	 * 获取池中所保存的线程数，包括空闲线程
	 * @return int
	 */
	public int getCorePoolSize() {
		return corePoolSize;
	}

	/**
	 * 设置池中所保存的线程数，包括空闲线程
	 * @param corePoolSize
	 */
	public void setCorePoolSize(int corePoolSize) {
		this.corePoolSize = corePoolSize;
	}

	/**
	 * 获取当线程数大于核心时，此为终止前多余的空闲线程等待新任务的最长时间
	 * @return long
	 */
	public long getKeepAliveTime() {
		return keepAliveTime;
	}

	/**
	 * 设置当线程数大于核心时，此为终止前多余的空闲线程等待新任务的最长时间
	 * @param keepAliveTime
	 */
	public void setKeepAliveTime(long keepAliveTime) {
		this.keepAliveTime = keepAliveTime;
	}

	/**
	 * 获取池中允许的最大线程数
	 * @return int
	 */
	public int getMaximumPoolSize() {
		return maximumPoolSize;
	}

	/**
	 * 设置池中允许的最大线程数
	 * @param maximumPoolSize
	 */
	public void setMaximumPoolSize(int maximumPoolSize) {
		this.maximumPoolSize = maximumPoolSize;
	}

	/**
	 * 获取keepAliveTime 参数的时间单位
	 * @return TimeUnit
	 */
	public TimeUnit getTimeUnit() {
		return timeUnit;
	}

	/**
	 * 设置keepAliveTime 参数的时间单位
	 * @param timeUnit
	 */
	public void setTimeUnit(TimeUnit timeUnit) {
		this.timeUnit = timeUnit;
	}

	/**
	 * 获取执行前用于保持任务的队列。此队列仅由保持 execute 方法提交的 Runnable 任务
	 * @return BlockingQueue<Runnable>
	 */
	public BlockingQueue<Runnable> getWorkQueue() {
		return workQueue;
	}

	/**
	 * 设置执行前用于保持任务的队列。此队列仅由保持 execute 方法提交的 Runnable 任务
	 * @param workQueue
	 */
	public void setWorkQueue(BlockingQueue<Runnable> workQueue) {
		this.workQueue = workQueue;
	}
	
	/**
	 * 初始化条件
	 */
	public ThreadPoolExecutor createThreadPool(){
		ThreadPoolExecutor producerPool = null;
		if(maximumPoolSize<=0){
			maximumPoolSize=Integer.MAX_VALUE;
		}
		if(workQueue==null){
			if(workQueueSize<=0){
				workQueue=new LinkedBlockingQueue<Runnable>();
				workQueueSize=Integer.MAX_VALUE;
			}else{
				workQueue=new LinkedBlockingQueue<Runnable>(workQueueSize);
			}
		}
		producerPool = new ThreadPoolExecutor(corePoolSize, 
	                                          maximumPoolSize, 
	                                          keepAliveTime,
	                                          timeUnit, 
	                                          workQueue,
	                                          handler);
		return producerPool;
	}
}


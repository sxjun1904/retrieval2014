package framework.retrieval.task;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public abstract class AbstractTaskPool {
	public abstract List deal(List<Future> rl);
	
	public List exeFuture(List<ITask> tasklist){
		ExecutorService executorService = Executors.newCachedThreadPool();
		List<Future> rl = new ArrayList<Future>();
		for(ITask t : tasklist){
			// 使用ExecutorService执行Callable类型的任务，并将结果保存在future变量中   
			Future future = executorService.submit(t);  
			// 将任务执行结果存储到List中   
            rl.add(future); 
		}
		executorService.shutdown(); 
		return deal(rl);
	}
}  


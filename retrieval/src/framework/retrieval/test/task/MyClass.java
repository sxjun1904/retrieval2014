package framework.retrieval.test.task;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import framework.retrieval.task.AbstractTaskPool;
import framework.retrieval.task.ITask;

public class MyClass extends AbstractTaskPool{

	@Override
	public List deal(List<Future> rl) {
		for(Future f:rl){
			try {
				System.out.println(f.get());
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
public static void main(String[] args) {
	while(true){
		
		ITask t = new MyTask();
		List<ITask> tl = new ArrayList<ITask>();
		tl.add(t);
		new MyClass().exeFuture(tl);
	}
}
}

package frame.retrieval.test.task;

import frame.retrieval.task.ITask;

public class MyTask implements ITask{

	@Override
	public String call() throws Exception {
		System.out.println("成功调用");
		return "1";
	}

}

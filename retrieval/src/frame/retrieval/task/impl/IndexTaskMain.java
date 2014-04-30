package frame.retrieval.task.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import frame.retrieval.task.AbstractTaskPool;
import frame.retrieval.engine.index.create.impl.RIndexWriterWrap;

public class IndexTaskMain extends AbstractTaskPool {
	@Override
	public List deal(List<Future> rl) {
		List<RIndexWriterWrap>  wraplist = new ArrayList<RIndexWriterWrap>();
		try {
			for(Future f : rl){
				wraplist.add((RIndexWriterWrap) f.get());
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		return wraplist;
	}
}

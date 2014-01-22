package framework.retrieval.helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RetrievalPages {
	private List<RetrievalPage> retrievalPageList = new ArrayList<RetrievalPage>();
	private int count = 0;
	private String time;
	private Map<String,Integer> group = new HashMap<String,Integer>();
	
	public List<RetrievalPage> getRetrievalPageList() {
		return retrievalPageList;
	}
	public void setRetrievalPageList(List<RetrievalPage> retrievalPageList) {
		this.retrievalPageList = retrievalPageList;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public Map<String, Integer> getGroup() {
		return group;
	}
	public void setGroup(Map<String, Integer> group) {
		this.group = group;
	}
	
}

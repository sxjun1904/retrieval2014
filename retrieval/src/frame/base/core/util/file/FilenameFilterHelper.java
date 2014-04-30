package frame.base.core.util.file;

import java.io.File;
import java.io.FilenameFilter;

public class FilenameFilterHelper implements FilenameFilter{
	private String[] filters = null;
	public FilenameFilterHelper(){
	}
	
	public FilenameFilterHelper(String[] filters){
		this.filters = filters;
	}
	
	public boolean fileterStarts(String name){
		boolean isfind = false;
		for(String f : filters){
			if (name.toUpperCase().startsWith(f.toUpperCase())){    
				isfind = true;  
				break;
			}    
		}
		return isfind;
	}
	
	@Override
	public boolean accept(File dir, String name) {
		return fileterStarts(name);
	}

}

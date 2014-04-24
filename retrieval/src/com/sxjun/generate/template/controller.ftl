/**
 * code generation
 */
package ${packageName}.${moduleName}${subModuleName}.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.apache.commons.lang.StringUtils;
import com.jfinal.core.Controller;
import ${packageName}.${moduleName}.pojo${subModuleName}.${ClassName};

/**
 * ${functionName}Controller
 * @author ${classAuthor}
 * @version ${classVersion}
 */
public class ${ClassName}Controller extends BaseController<${ClassName}> {
	private final static String cachename = ${ClassName}.class.getSimpleName();

	public void list() {
		list(cachename);
	}
	
	public void form(){
		form(cachename);
	}
	
	public void save(){
		save(getModel(${ClassName}.class));
	}
	
	public void delete(){
		delete(cachename);
	}

	public List<${ClassName}> get${ClassName}List(){
		return null;
	}
}

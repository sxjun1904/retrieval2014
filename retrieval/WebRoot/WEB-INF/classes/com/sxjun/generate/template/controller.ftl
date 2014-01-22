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
public class ${ClassName}Controller extends Controller {

	public void index() {
	}
	
	public void list() {
		List<${ClassName}> ${className}List = get${ClassName}List(); 
		setAttr("${className}",${className}List);
		render("${className}List.jsp");
	}
	
	public void form(){
		String id = getPara();
		if(StringUtils.isNotBlank(id))
			setAttr("${className}",get${ClassName}List().get(0));
		render("${className}Form.jsp");
	}
	
	public void save(){
		${ClassName} ${className} = getModel(${ClassName}.class);
		render("${className}Form.jsp");
	}
	
	public void delete(){
		String id=getPara();
		list();
	}

	public List<${ClassName}> get${ClassName}List(){
		return null;
	}
}

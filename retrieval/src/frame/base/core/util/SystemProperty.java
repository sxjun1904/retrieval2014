package frame.base.core.util;
/**
 * 获取软件版本
 * @author 
 *
 */
public class SystemProperty {
	/**
	 * 获取当前使用的JDK版本
	 */
	public static final String JAVA_VERSION = System.getProperty("java.version");

	public static final boolean JAVA_1_1 = JAVA_VERSION.startsWith("1.1.");

    public static final boolean JAVA_1_2 = JAVA_VERSION.startsWith("1.2.");

	public static final boolean JAVA_1_3 = JAVA_VERSION.startsWith("1.3.");
	
	public static final boolean JAVA_1_4 = JAVA_VERSION.startsWith("1.4.");
	
	/**
	 * 获取当前使用的操作系统类型
	 */
	public static final String OS_NAME = System.getProperty("os.name");

	public static final boolean LINUX = OS_NAME.startsWith("Linux");

	public static final boolean WINDOWS = OS_NAME.startsWith("Windows");

	public static final boolean SUN_OS = OS_NAME.startsWith("SunOS");
}

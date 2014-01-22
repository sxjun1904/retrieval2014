/**
 * Copyright 2010 
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package framework.base.snoic.base.util;
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

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
package framework.base.snoic.system.interfaces;

/**
 * 初始化系统
 * @author 
 *
 */
public interface IInit {
	/**
	 * 系统初始化,在系统已经被初始化过的情况下，不会再被初始化
	 */
	public void init();
	
	/**
	 * 强制系统重新初始化
	 */
	public void reInit();
	
	/**
	 * 强制摧毁系统，将会摧毁整个系统管理的所有的对象以及所有的初始化信息
	 *
	 */
	public void destroySystem();
}

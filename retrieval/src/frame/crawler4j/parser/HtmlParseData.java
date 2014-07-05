/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package frame.crawler4j.parser;

import java.util.List;

import frame.crawler4j.url.WebURL;

public class HtmlParseData implements ParseData
{

	private String html;
	private String text;
	private String title;

	private List<WebURL> outgoingUrls;

	public String getHtml()
	{
		return html;
	}

	public void setHtml(String html)
	{
		this.html = html;
	}

	public String getText()
	{
		return text;
	}

	public void setText(String text)
	{
		this.text = text;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public List<WebURL> getOutgoingUrls()
	{
		return outgoingUrls;
	}

	public void setOutgoingUrls(List<WebURL> outgoingUrls)
	{
		this.outgoingUrls = outgoingUrls;
	}

	@Override
	public String toString()
	{
		return text;
	}

}
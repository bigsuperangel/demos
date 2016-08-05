/*
 * ====================================================================
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 *
 */
package org.apache.http.client.fluent;

import org.apache.http.HeaderIterator;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

public class FluentQuickStart {

    public static void main(String[] args) throws Exception {
        // The fluent API relieves the user from having to deal with manual
        // deallocation of system resources at the cost of having to buffer
        // response content in memory in some cases.

//        Request.Get("http://targethost/homepage")
//            .execute().returnContent();
//        Request.Post("http://targethost/login")
//            .bodyForm(Form.form().add("username",  "vip").add("password",  "secret").build())
//            .execute().returnContent();
        
//        Content content = Request.Get("http://www.baidu.com").execute().returnContent();
//        System.out.println(content.toString());
        HttpResponse response = Request.Get("http://www.baidu.com").execute().returnResponse();
        printResponse(response);
        
    }
    
	private static String printResponse(HttpResponse httpResponse) throws Exception {
		// 获取响应消息实体
		HttpEntity entity = httpResponse.getEntity();
		// 响应状态
		System.out.println("status:" + httpResponse.getStatusLine());
		System.out.println("headers:");
		HeaderIterator iterator = httpResponse.headerIterator();
		while (iterator.hasNext()) {
			System.out.println("\t" + iterator.next());
		}
		// 判断响应实体是否为空
		if (entity != null) {
			String responseString = EntityUtils.toString(entity);
			System.out.println("response length:" + responseString.length());
			System.out.println("response content:" + responseString);
//			System.out.println("response content:"
//					+ responseString.replace("\r\n", ""));
			return responseString;
		}
		return null;
	}
}

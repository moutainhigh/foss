/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 PKP
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * PROJECT NAME	: pkp-gui
 * 
 * FILE PATH        	: appHome/src/main/java/org/limewire/cef/MainV8Handler.java
 * 
 * FILE NAME        	: MainV8Handler.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package org.limewire.cef;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/**
 * 调用CEF jni接口参考列子程序
 * @author niujian
 */
public class MainV8Handler implements CefV8Handler {
	private MainFrame frame;
	
	MainV8Handler(MainFrame frame)
	{
		this.frame = frame;
	}
	
	class ShowMessage implements Runnable
	{
		private String message;
		
		ShowMessage(String message) {
			this.message = message;
		}
		
		@Override
		public void run() {
			JOptionPane.showMessageDialog(frame, message);
		}
	}
	
	@Override
	public boolean executeFunction(String name, CefV8FunctionParams params) {
		if(name.equals("showMessage") && params.hasArguments())
		{
			//frame.getCefBrowser()
			// Retrieve the first argument as a string.
			String message = params.getArguments()[0].getStringValue();
			
			// Show the message box using the event dispatching thread.
			SwingUtilities.invokeLater(new ShowMessage(message));
			
			// Return a string value.
			params.setRetVal(CefContext.createV8String("Got the message."));
			
			return true;
		}
		return false;
	}
}
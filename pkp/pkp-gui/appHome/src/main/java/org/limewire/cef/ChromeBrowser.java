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
 * FILE PATH        	: appHome/src/main/java/org/limewire/cef/ChromeBrowser.java
 * 
 * FILE NAME        	: ChromeBrowser.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package org.limewire.cef;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.lang.reflect.InvocationTargetException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * 借助CEF 框架提供的jni接口, chrome browser 调用专门的类
 * @author niujian
 *
 */
public class ChromeBrowser extends JPanel {
	
	private static final Log log = LogFactory.getLog(ChromeBrowser.class);
	private JPanel mainpanel = null;
	private JPanel controlpanel = null;
	private static final long serialVersionUID = 1899887682284080412L;
	private CefBrowser browserComponent;
	private String url = null;
	private CefCookie cefCookie;
	private static ChromeBrowser instance = null;
	private JDialog parent = null;
	public void setParent(JDialog parent){
		this.parent = parent;
	}
	
/*	public static ChromeBrowser getInstance(String pUrl,boolean displayControl) throws Exception {
		if(instance==null){
			instance = new ChromeBrowser(pUrl);
			instance.setForseToNPaint(true);
		}
		else {
			instance.setForseToNPaint(true);
			instance.loadUrl(pUrl);
		}
		instance.setControlVisible(displayControl);
		return instance;
	}*/
	
	public void setForseToNPaint(boolean forse){
		if(browserComponent!=null){
			browserComponent.setForseToNPaint(forse);
		}
	}
	
	public ChromeBrowser(String url) throws Exception {
		init(url,null);
	}
	
	public ChromeBrowser(String url, CefCookie cefCookie) throws Exception {
		init(url,cefCookie);
	}
	
	public void init(String url, CefCookie cefCookie) throws Exception {
		// Initialize the CEF context.
		//CefContext.initialize(null);
		this.url = url;
		if(cefCookie!=null){
			this.cefCookie = cefCookie;
		}
		mainpanel = new JPanel();
		mainpanel.setLayout(new BorderLayout());
		mainpanel.add(createContentPanel(),BorderLayout.CENTER);
		
		controlpanel = createControlPanel();
		mainpanel.add(controlpanel,BorderLayout.NORTH);
		this.setLayout(new BorderLayout());
		this.add(mainpanel,BorderLayout.CENTER);
		
	}
	
	public void displayOrHideControlPanel(){
		if(this.controlpanel!=null){
			this.controlpanel.setVisible(!this.controlpanel.isVisible());
		}
	}
	
	public void setControlVisible(boolean displayControl){
		controlpanel.setVisible(displayControl);
	}
	
	private JPanel createControlPanel(){
		
		final JTextField txtURL = new JTextField();
		txtURL.setPreferredSize(new Dimension(500,25));
		//txtfield.setText("gis.deppon.com/gis-biz/biz-destination/index.action");
		txtURL.setText(this.url);
		
		final JTextField txtCookieToken = new JTextField();
		txtCookieToken.setPreferredSize(new Dimension(200,25));
		if(this.cefCookie!=null){
			txtCookieToken.setText(this.cefCookie.getCookievalue());
		}
		txtURL.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				
			}
			
			@Override
			public void keyPressed(KeyEvent e) {

				try {
					if (e.getKeyCode() == 10) {
						resetBrowser(txtURL.getText(),txtCookieToken.getText());
					}
				} catch (Exception exp) { }
			}
		});		
		
		JButton btnGo = new JButton();
		btnGo.setPreferredSize(new Dimension(25,25));
		btnGo.setToolTipText("Play");
		ImageIcon iconplay = new ImageIcon(this.getClass().getResource("/org/limewire/cef/images/play.png")); 
		btnGo.setIcon(iconplay);
		btnGo.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try{
					resetBrowser(txtURL.getText(),txtCookieToken.getText());
				}catch(Exception exp){ }
			}
		});
		
		JButton btnstop = new JButton();
		btnstop.setPreferredSize(new Dimension(25,25));
		btnstop.setToolTipText("Stop");
		ImageIcon iconstop = new ImageIcon(this.getClass().getResource("/org/limewire/cef/images/stop.png")); 
		btnstop.setIcon(iconstop);
		btnstop.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try{
					Stop();
				}catch(Exception exp){ }
			}
		});
		
		JButton btnRefresh = new JButton();
		btnRefresh.setPreferredSize(new Dimension(25,25));
		btnRefresh.setToolTipText("Refresh");
		ImageIcon iconrefresh = new ImageIcon(this.getClass().getResource("/org/limewire/cef/images/refresh.png")); 
		btnRefresh.setIcon(iconrefresh);
		btnRefresh.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try{
					resetBrowser(txtURL.getText(),null);
				}catch(Exception exp){ }
			}
		});
		
		JButton btnbackward = new JButton();
		btnbackward.setPreferredSize(new Dimension(25,25));
		btnRefresh.setToolTipText("Backward");
		ImageIcon iconbackward = new ImageIcon(this.getClass().getResource("/org/limewire/cef/images/backward.png")); 
		btnbackward.setIcon(iconbackward);
		btnbackward.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try{
					GoBack();
				}catch(Exception exp){ }
			}
		});
		
		JButton btnforward = new JButton();
		btnforward.setPreferredSize(new Dimension(25,25));
		btnforward.setToolTipText("Forward");
		ImageIcon iconforward = new ImageIcon(this.getClass().getResource("/org/limewire/cef/images/forward.png")); 
		btnforward.setIcon(iconforward);
		btnforward.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try{
					GoForward();
				}catch(Exception exp){ }
			}
		});
		
		JButton btnDevTools = new JButton();
		btnDevTools.setPreferredSize(new Dimension(25,25));
		btnDevTools.setToolTipText("Dev Tools");
		ImageIcon icontool = new ImageIcon(this.getClass().getResource("/org/limewire/cef/images/tool.png")); 
		btnDevTools.setIcon(icontool);
		btnDevTools.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try{
					ShowDevTools();
				}catch(Exception exp){ }
			}
		});
		
		JPanel jpanel = new JPanel();
		jpanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		jpanel.add(new JLabel("URL:"));
		jpanel.add(txtURL);
		jpanel.add(new JLabel("Cookie:"));
		jpanel.add(txtCookieToken);
		jpanel.add(btnGo);
		jpanel.add(btnstop);
		jpanel.add(btnRefresh);		
		jpanel.add(btnbackward);
		jpanel.add(btnforward);
		jpanel.add(btnDevTools);
		return jpanel;
	}
	
	protected void finalize() throws Throwable
    {
    	// Shut down the CEF context.
		// CefContext.shutdown();
		log.warn(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>do finalize...");
    	super.finalize();
    }
	
	private IChromeBrowserCallBack callBack = null;
	public void setCallBack(IChromeBrowserCallBack pCallBack){
		callBack = pCallBack;
	}
	
	public void doCallBack(String[] params) throws Exception {
		if(callBack!=null){
			boolean ret = callBack.doCallBack(params);
			if(ret && this.parent!=null){
//				this.parent.setVisible(false);
				log.warn(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>do dispatchEvent...");
				this.parent.dispatchEvent(new WindowEvent(this.parent, WindowEvent.WINDOW_CLOSING));
			}
		}
	}
	
	class myCefV8Handler implements CefV8Handler {

		private ChromeBrowser frame;
    	private String v8objname;
    	private String v8objfuncname;
		
		myCefV8Handler(ChromeBrowser frame, String v8objname,String v8objfuncname )
		{
			this.frame = frame;
			this.v8objname = v8objname;
    		this.v8objfuncname = v8objfuncname;
		}
		
		// Class that will be used to show the message box on the event-dispatching thread.
		class returnResults implements Runnable
		{
			private String[] paramString;
			
			returnResults(String[] paramString) {
				this.paramString = paramString;
			}
			
			@Override
			public void run() {
				try{
					frame.doCallBack(paramString);
				}catch(Exception e){ log.error("myCefV8Handler returnDeptNo error ",e); }
			}
		}
		
		@Override
		public boolean executeFunction(String name, CefV8FunctionParams params) {
			if(name.equals("returnResults") && params.hasArguments())
			{
				// Retrieve the first argument as a string.
				String paramString = params.getArguments()[0].getStringValue();
				if(paramString!=null ){
					String[] paramarr = paramString.split("&");
					// Show the message box using the event dispatching thread.
					SwingUtilities.invokeLater(new returnResults(paramarr));
					
					// Return a string value.
					//params.setRetVal(CefContext.createV8String("Got the message."));
				}
				return true;
			}
			return false;
		}
	}
	
	private JPanel createContentPanel() {
		JPanel contentPanel = new JPanel(new BorderLayout());
		contentPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		myCefV8Handler _myCefV8Handler = new myCefV8Handler(this,"GIS_Results","returnResults");
		if(this.cefCookie!=null ){
			browserComponent = CefContext.createBrowser(this.url,_myCefV8Handler,this.cefCookie);
		}
		else {
			browserComponent = CefContext.createBrowser(this.url,_myCefV8Handler);
		}
		
		contentPanel.add(browserComponent, BorderLayout.CENTER);
		
		return contentPanel;
	}
	
	public void resetBrowser(String url ,String cookiestr) throws InterruptedException, InvocationTargetException{
		if(browserComponent!=null && url!=null && url.length()>0 ){
			if(cookiestr!=null && cookiestr.length()>0 ){
				
				CefCookie _cookie = new CefCookie();
				_cookie.setCookiepath("/");
				_cookie.setCookieurl(url);
				_cookie.setCookievalue("_TOKEN="+cookiestr);
				this.cefCookie = _cookie;
				
			}
			if(this.cefCookie!=null ){
				browserComponent.setCefCookie(this.cefCookie);
			}
			browserComponent.loadByUrl(url);
		}
	}
	
    public void setAcceptLanguage(String acceptlanguage) {
    	if(browserComponent!=null && acceptlanguage!=null){
			browserComponent.setAcceptLanguage(acceptlanguage);
		}
    }
    
    public void GoForward(){
    	if(browserComponent!=null ){
			browserComponent.goForward();
		}
    }
    
    public void GoBack(){
    	if(browserComponent!=null ){
			browserComponent.goBack();
		}
    }
    
    public void ShowDevTools(){
    	if(browserComponent!=null ){
			browserComponent.showDevTools();
		}
    }    
    
    public void Stop(){
    	if(browserComponent!=null ){
			browserComponent.stop();
		}    	
    }

	public void setCefV8Handler(CefV8Handler cefV8Handler) {
		browserComponent.setCefV8Handler(cefV8Handler);
	}
}
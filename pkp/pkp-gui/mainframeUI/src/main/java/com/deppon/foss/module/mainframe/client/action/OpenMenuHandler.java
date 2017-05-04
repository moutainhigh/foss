package com.deppon.foss.module.mainframe.client.action;

import java.util.Locale;

import javax.swing.SwingUtilities;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.limewire.cef.CefChromeBrowserManager;
import org.limewire.cef.CefCookie;
import org.limewire.cef.CefV8FunctionParams;
import org.limewire.cef.CefV8Handler;
import org.limewire.cef.CefV8Value;
import org.limewire.cef.ChromeBrowser;

import com.deppon.foss.framework.client.core.application.IApplication;
import com.deppon.foss.framework.client.core.context.SessionContext;
import com.deppon.foss.framework.client.core.workbench.EditorConfig;
import com.deppon.foss.framework.client.widget.msgbox.MsgBox;
import com.deppon.foss.framework.define.LocaleConst;
import com.deppon.foss.module.mainframe.client.utills.RemotingInfoUtils;

/**
 * 嵌web页面打开二级菜单处理类
 * @author 102246-foss-shaohongliang
 *
 */
public class OpenMenuHandler implements CefV8Handler {
	
	private static final Log log = LogFactory.getLog(OpenMenuHandler.class);

	private IApplication application;
    private ChromeBrowser frame;
    private String v8objname;
    private String v8objfuncname;
	/**
	 * 编辑配置
	 */
	private EditorConfig editConfig;
	
	OpenMenuHandler(IApplication application, ChromeBrowser frame, String v8objname,String v8objfuncname )
	{
		this.application = application;
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
		    	/**
			 * 获取本地默认的语言
			 */
			String localLanguage = Locale.getDefault().getLanguage();
			/**
			 * 获取本地默认的国家
			 */
			String localCountry = Locale.getDefault().getCountry();
			
			String ip=RemotingInfoUtils.getRemotingInfo().getHostName()+":"+RemotingInfoUtils.getRemotingInfo().getPort();
			
			/**
			 * 获取FOSS URI 地址并追加语言和国家信息，将最终的字符串赋给urlValue
			 */
			//Tab页签名字
			if(paramString.length>2){
			String tabName = paramString[1];
			//弹出地址
			String url = paramString[2];
		
			if(!url.contains("?")){
				url = url +"?";
			}else{
				url = url +"&";
			}
			String urlValue = "http://"+ip+url
					+LocaleConst.KEY_LOCALE_LANGUAGE+"="+localLanguage+"&"+LocaleConst.KEY_LOCALE_COUNTRY+"="+localCountry;
		
			try {

				/**
				 * 创建CefCookie对象
				 */
				CefCookie cookie = new CefCookie();
				/**
				 * 设置cookie的path
				 */
				cookie.setCookiepath("/");
				/**
				 * 设置cookie的url
				 */
				cookie.setCookieurl(urlValue);
				/**
				 * 设置cookie的value
				 */
				cookie.setCookievalue(SessionContext.KEY_TOKEN+"="+(String)SessionContext.get(SessionContext.KEY_TOKEN));

				/**
				 * 创建ChromeBrowser对象并获取ChromeBrowser的实例
				 */
				ChromeBrowser browser = CefChromeBrowserManager.getChromeBrowser(urlValue,false,cookie);
				/**
				 * 修改默认JS处理行为
				 */
				browser.setCefV8Handler(new OpenMenuHandler(application, browser, "GUI_Proxy", "addTab"));
				/**
				 * 创建EditorConfig对象editConfig
				 */
				editConfig = new EditorConfig();
				/**
				 * 设置editConfig标题
				 */
				editConfig.setTitle(tabName);
				/**
				 * 设置editConfig插件ID
				 */
				editConfig.setPluginId("com.deppon.foss.module.pkp-common");
				/**
				 * 调用打开界面方法openUI
				 */
				application.openUI(editConfig, browser);
			} catch (Exception e) {
				if(log.isErrorEnabled()){
					log.error(e);
				}
				MsgBox.showError("未找到相应页面");
			}	
		}
		}
	}
	
	@Override
	public boolean executeFunction(String name, CefV8FunctionParams params) {
		if(name.equals("addTab") && params.hasArguments())
		{
			// Retrieve the first argument as a string.
			CefV8Value[] paramValues = params.getArguments();
			String[] paramarr = new String[paramValues.length];
			for(int i=0 ; i<paramValues.length ; i++){
				paramarr[i] = paramValues[i].getStringValue();
			}
			// Show the message box using the event dispatching thread.
			SwingUtilities.invokeLater(new returnResults(paramarr));
			
			// Return a string value.
			//params.setRetVal(CefContext.createV8String("Got the message."));
			return true;
		}
		return false;
	}
}
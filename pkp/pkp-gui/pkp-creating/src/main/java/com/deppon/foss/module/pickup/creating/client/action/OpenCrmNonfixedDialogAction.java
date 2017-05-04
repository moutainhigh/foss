/*
 * PROJECT NAME: pkp-common
 * PACKAGE NAME: com.deppon.foss.module.pickup.common.client.action
 * FILE    NAME: OpenCrmNonfixedDialogAction.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.pickup.creating.client.action;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;

import org.limewire.cef.CefChromeBrowserManager;
import org.limewire.cef.IChromeBrowserCallBack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.component.buttonaction.IButtonActionListener;
import com.deppon.foss.framework.client.component.remote.DefaultRemoteServiceFactory;
import com.deppon.foss.framework.client.widget.msgbox.MsgBox;
import com.deppon.foss.module.pickup.creating.client.ui.WaybillEditUI;
import com.deppon.foss.module.pickup.waybill.shared.exception.OpenCrmUIException;
import com.deppon.foss.module.pickup.waybill.shared.hessian.IFossSSOLoginRemoting;


/**
 * 新增CRM散客的UI界面
 * @author 026123-foss-lifengteng
 * @date 2013-3-18 上午11:03:44
 */
public class OpenCrmNonfixedDialogAction implements IButtonActionListener<WaybillEditUI>{
	/**
	 * 定义日志静态类 
	 * 通过日志工厂类获得该类的日志对象
	 * 使用该日志类的静态方法记录日志
	 */
	protected final static Logger LOGGER = LoggerFactory.getLogger(OpenCrmNonfixedDialogAction.class.getName());
	
	/**
	 * 登录服务
	 * 提供FOSS单点登陆CRM界面的服务接口
	 */
	private IFossSSOLoginRemoting service;
	
	/**
	 * 国际化
	 */
	private static final II18n i18n = I18nManager.getI18n(LoadPickupStationAction.class);
	
	
	/** 
	 * 执行方法，即入口方法
	 * @author 026123-foss-lifengteng
	 * @date 2013-3-18 上午11:28:04
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// 捕捉异常信息
		try {
			// 得到service
			service = DefaultRemoteServiceFactory.getService(IFossSSOLoginRemoting.class);

			// 得到url
			String urlValue;
			try {
				urlValue = service.getUrl("crm", "customer/scattercustIndex.action");
			} catch (Exception ee) {
				throw new OpenCrmUIException(i18n.get("foss.gui.creating.OpenCrmNonfixedDialogAction.exception.connectCRMError") + ee.getMessage(), ee.getMessage());
			}

			/**
			 * 定义chromeBrowser的回调函数
			 * 该方法不做任何业务操作，只为定义后面方法的参数值
			 */
			IChromeBrowserCallBack myCallBack = new IChromeBrowserCallBack() {
				/** 
				 * 回调函数，暂不做回调的业务操作
				 * @author 026123-foss-lifengteng
				 * @date 2013-3-18 下午8:08:54
				 * @see org.limewire.cef.IChromeBrowserCallBack#doCallBack(java.lang.String[])
				 */
				@Override
				public boolean doCallBack(String[] params) throws Exception {
					return false;
				}
				
			};
			
			//获得屏幕宽度
			double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
			//获得屏幕高度
			double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
			
			
			
			//在弹出框中打开浏览器内容
			CefChromeBrowserManager.PopChromeBrowserAsDialog(urlValue, (int)width- NumberConstants.NUMBER_20, (int)height- NumberConstants.NUMBER_50, myCallBack,false);
		} catch (Exception ex) {
			// 添加异常日志
			LOGGER.error("打开CRM的新增散客界面失败！");
			// 弹出“打开CRM的新增散客界面失败”的异常信息，并中止程序继续运行
			MsgBox.showError(i18n.get("foss.gui.creating.OpenCrmNonfixedDialogAction.msgBox.openCRMError")+ex.getMessage());
		}

	}

	
	/**
	 * @return the service .
	 */
	public IFossSSOLoginRemoting getService() {
		return service;
	}

	/** 
	 * 给运单界面对象赋值
	 * @author 026123-foss-lifengteng
	 * @date 2013-3-18 上午11:52:35
	 * @see com.deppon.foss.framework.client.component.buttonaction.IButtonActionListener#setInjectUI(java.awt.Container)
	 */
	@Override
	public void setInjectUI(WaybillEditUI ui) {
		//不做业务处理
	}
}

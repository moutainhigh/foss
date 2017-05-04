/**
 *  initial comments.
 */
/*
 * Copyright by Deppon and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Deppon from
 *
 *      http://www.deppon.com
 *
 */
package com.deppon.foss.module.mainframe.client.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.WindowConstants;

import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.commons.util.WindowUtil;
import com.deppon.foss.framework.client.core.context.ApplicationContext;
import com.deppon.foss.framework.client.core.context.SessionContext;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.frameworkimpl.server.util.CryptoUtil;
import com.deppon.foss.module.login.shared.domain.LoginInfo;
import com.deppon.foss.module.mainframe.client.ui.PwdDialog;


/**
 * MainFrame的锁屏事件
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:dp-yangtong,date:2012-10-12 上午11:46:51, </p>
 * @author dp-yangtong
 * @date 2012-10-12 上午11:46:51
 * @since
 * @version
 */
public class LockScreenActionListener implements ActionListener {

	/**
	 * 110 size
	 */
	private static final int HUNDREDTEN = 110;
	/**
	 * 300 size
	 */
	private static final int THREEHUNDRED = 300;
	
	/**
	 * PANEL 
	 */
	private JPanel jpanel;
	
	/**
	 * 国际化对象
	 */
	private II18n i18n = I18nManager.getI18n(LockScreenActionListener.class); 

	
	/**
	 * 构造方法
	 * @param jpanel
	 */
	public LockScreenActionListener(JPanel jpanel) {
		this.jpanel = jpanel;
	}
	
	/**
	 * 
	 * 密码校验
	 * @author 026113-foss-linwensheng
	 * @date 2012-12-18 上午10:03:50
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		
		//密码输入Dialog
		PwdDialog pwdDialog = new PwdDialog(ApplicationContext.getApplication().getWorkbench().getFrame(),true);
		//设置pwdDialog对象的标题
		pwdDialog.setTitle(i18n.get("lockscreen.title"));
		//设置pwdDialog对象的大小
		pwdDialog.setSize(THREEHUNDRED, HUNDREDTEN);
		//将pwdDialog置于屏幕中间
		WindowUtil.center(pwdDialog);
		//设置pwdDialog关闭程序属性setDefaultCloseOperation
		pwdDialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		
		//this is invisible panel 
		/**
		 * 设置父窗体为不可见的
		 */
		jpanel.getParent().setVisible(false);
		//声明布尔型变量isContinue，默认为true
		boolean isContinue = true;
		
		//WAIT FOR USE TO INPUT 当isContinue为true时一直循环
		while (isContinue) {
		    	//pwdDialog
		    	/**
			 * 设置为可见的
			 */
			pwdDialog.setVisible(true);
			//password 获取窗体的密码
			String password = pwdDialog.getPassword();
			/**
			 * 判断密码是否为空
			 */
			if(!StringUtil.isEmpty(password)){
				// 应用OA的加密方式
				password = CryptoUtil.digestByMD5(password);
			}
			/*//get use from session 从session中获取当前用户
			IUser iuser = SessionContext.getCurrentUser();
			//use is null or not 判断iuser是否为空，如果不为空，则user设置为(UserEntity)iuser，否则user设置为null
			UserEntity user = iuser!=null ? (UserEntity)iuser : null;
			//use userentity to login 判断user是否为空，如果为空，则loginInfo设置null，否则，根据username获取user并赋给loginInfo
			LoginInfo loginInfo = user==null ? null : UserServiceFactory.getUserService().queryUserByName(user.getUserName());*/
			
			LoginInfo	loginInfo =(LoginInfo)SessionContext.get("login_info");
			/**
			 * 判断loginInfo是否为空并且password是否为空并且当前登录用户的密码是否等于password
			 */
			if(loginInfo!=null && StringUtil.isNotEmpty(password) && loginInfo.getUser().getPassword().equals(password)){
			    	//调用窗体的dispose()方法释放资源
				pwdDialog.dispose();
				/**
				 * 设置为不可见的
				 */
				pwdDialog.setVisible(false);
				//login successfully isContinue设置为false即跳出循环
				isContinue = false;
			}else{
				//TODO 提示密码不正确
			}
		}
		//this is visible panel final
		/**
		 * 设置父窗体为可见的
		 */
		jpanel.getParent().setVisible(true);
	}
}
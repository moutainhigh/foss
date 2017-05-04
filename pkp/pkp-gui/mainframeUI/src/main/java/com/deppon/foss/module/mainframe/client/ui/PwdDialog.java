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
package com.deppon.foss.module.mainframe.client.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.border.EmptyBorder;

import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.core.context.ApplicationContext;


/**
 * 
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:dp-yangtong,date:2012-10-12 上午11:50:34,content:
 * @author dp-yangtong
 * @date 2012-10-12 上午11:50:34
 * @since
 * @version
 */
public class PwdDialog extends javax.swing.JDialog  implements ActionListener  {
	/**
	 * OK
	 */
	private static final String OK = "ok";

	/**
	 * 20
	 */
	private static final int TWENTY = 20;

	/**
	 * 5
	 */
	private static final int FIVE = 5;

	/**
	 * 序列化版本号
	 */
	private static final long serialVersionUID = -2754017723256203234L;
	
	/**
	 * 国际化对象
	 */
	private transient II18n i18n = I18nManager.getI18n(PwdDialog.class);
	
	/**
	 * PASSWORD FIELD 创建JPasswordField对象，用来处理密码输入
	 */
	private javax.swing.JPasswordField pwdField;
	
	
	/**
	 * 构造方法
	 */
	public PwdDialog() {
	/**
	 * 继承父类构造方法
	 */
        super();
        /**
         * 加载组件
         */
        initComponents();
    }
	
	/**
	 * 构造方法
	 * @param parent
	 * @param modal
	 */
	public PwdDialog(java.awt.Frame parent, boolean modal) {
	/**
	 * 继承父类构造方法
	 */
        super(parent, modal);
        /**
         * 加载组件
         */
        initComponents();
    }
	
	/**
	 * get password
	 * @return
	 */
	public String getPassword(){
	    	//返回密码
		return String.valueOf(pwdField.getPassword());
	}
	
	/**
	 * 初始化界面信息
	 */
    private void initComponents() {
    	//create northpanel
	/**
	 * 创建JPanel对象northPanel
	 */
    	javax.swing.JPanel northPanel = new javax.swing.JPanel();
    	//create text
    	/**
	 * 创建JLabel对象pwdText
	 */
    	javax.swing.JLabel pwdText = new javax.swing.JLabel();
    	//create password field
    	/**
	 * 创建JPasswordField对象pwdField
	 */
        pwdField = new javax.swing.JPasswordField();
        //create center panel
        /**
	 * 创建JPanel对象centerPanel
	 */
        javax.swing.JPanel centerPanel = new javax.swing.JPanel();
        //create ok button
        /**
	 * 创建JButton对象okButton
	 */
        javax.swing.JButton okButton = new javax.swing.JButton();
        //create exit button
        /**
	 * 创建JButton对象exitButton
	 */
        javax.swing.JButton exitButton = new javax.swing.JButton();
        //this panel name
        /**
	 * 设置名称
	 */
        setName("Form");
        /**
	 * 设置布局
	 */
        getContentPane().setLayout(new java.awt.BorderLayout(FIVE, FIVE));
        /**
	 * 为northPanel设置名称
	 */
        northPanel.setName("northPanel");
        //border
        /**
	 * 为northPanel设置边框
	 */
        northPanel.setBorder(new EmptyBorder(FIVE, FIVE, FIVE, FIVE));
        
        //layout
        /**
	 * 为northPanel设置布局
	 */
        northPanel.setLayout(new java.awt.BorderLayout(FIVE, FIVE));
        //text
        /**
	 * 为pwdText设置文本
	 */
        pwdText.setText(i18n.get("pwdText.text")); 
        //name
        /**
	 * 为pwdText设置名称
	 */
        pwdText.setName("pwdText"); 
        //name
        /**
	 * 为pwdField设置名称
	 */
        pwdField.setName("pwdField"); 
        //action command
        /**
	 * 为pwdField设置执行命令
	 */
        pwdField.setActionCommand(OK);
        //listener
        /**
	 * 为pwdField设置监听器
	 */
        pwdField.addActionListener(this);
        //text
        /**
	 * 为northPanel添加属性
	 */
        northPanel.add(pwdText, java.awt.BorderLayout.WEST);
        //password field
        /**
	 * 为northPanel添加属性
	 */
        northPanel.add(pwdField, java.awt.BorderLayout.CENTER);
        //north panel
        /**
	 * 为面板添加元素
	 */
        getContentPane().add(northPanel, java.awt.BorderLayout.NORTH);
        //center panel
        /**
	 * 为centerPanel设置名称
	 */
        centerPanel.setName("centerPanel"); 	
        //layout
        /**
	 * 为centerPanel设置布局
	 */
        centerPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, TWENTY, FIVE));
        //ok text
        /**
	 * 为okButton设置文本
	 */
        okButton.setText(i18n.get("okButton.text"));
        //ok name
        /**
       	 * 为okButton设置名称
       	 */
        okButton.setName("okButton"); 
      	// ok action
        /**
       	 * 为okButton设置执行命令
       	 */
        okButton.setActionCommand(OK);
        //ok button
        /**
       	 * 为okButton添加监听器
       	 */
        okButton.addActionListener(this);
        //center panel
        /**
       	 * 为centerPanel添加元素okButton
       	 */
        centerPanel.add(okButton);
        //button text
        /**
       	 * 为exitButton设置文本
       	 */
        exitButton.setText(i18n.get("exitButton.text")); 
        //button name
        /**
       	 * 为exitButton设置名称
       	 */
        exitButton.setName("exitButton"); 
        
        //Exit
        /**
       	 * 为exitButton添加监听器
       	 */
        exitButton.addActionListener(new ExitButton());
        //exit button
        /**
       	 * 为centerPanel添加元素
       	 */
        centerPanel.add(exitButton);

        //panel center
        /**
       	 * 获取面板并添加元素
       	 */
        getContentPane().add(centerPanel, java.awt.BorderLayout.CENTER);
        
        //resizable
        /**
       	 * 设置对话框不能改变大小
       	 */
        this.setResizable(false);
        /**
       	 * 调用pack()方法封装
       	 */
        pack();
    }

    /**
     * ExitButton for action listener  which will be register to exit button
     * @author admin
     *
     */
    private static class ExitButton implements ActionListener{
    	/**
    	 * 添加事件
    	 */
    	@Override
		public void actionPerformed(ActionEvent e) {
                	/**
                	 * 退出
                	 */
			ApplicationContext.getApplication().exit();
		}
    }
    
    /**
     * 添加事件
     */
	@Override
	public void actionPerformed(ActionEvent e) {
	    	//获取执行命令
		String ok = e.getActionCommand();
		/**
		 * 判断执行命令是否是"ok"，如果是，则设置为不可见的
		 */
		if(ok.equals(OK)){
			this.setVisible(false);
		}
	}
}
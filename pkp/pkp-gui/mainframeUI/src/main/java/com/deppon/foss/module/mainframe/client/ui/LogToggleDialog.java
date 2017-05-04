package com.deppon.foss.module.mainframe.client.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import org.apache.commons.lang.BooleanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.module.mainframe.client.common.CommonContents;
import com.deppon.foss.module.mainframe.client.common.PropertiesUtil;

/**
 * 日志开关对话框
 * @author 272311
 *
 */
public class LogToggleDialog extends JDialog{
	//日志
	private Logger logger = LoggerFactory.getLogger(LogToggleDialog.class);
	private JCheckBox jcb ;
	
	//读写配置文件
	PropertiesUtil propertiesUtil = PropertiesUtil.getPropertiesUtil();
	//配置文件路径
	String path = "conf/logToggle.properties" ;
	
	public LogToggleDialog(Frame frame,String title,boolean model) {
		super(frame, title, model);
		init();
	}
	
	public void init(){
		JLabel jLabel = new JLabel("是否打开日志：");
		jcb = new JCheckBox();
		jcb.setToolTipText("勾选为打开日志，不勾选为关闭日志");
		
		//读取配置文件
		String value = propertiesUtil.getProperties(path, "logToggle", "true");
		
		jcb.setSelected(BooleanUtils.toBoolean(value));
		
		jcb.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				logger.info("jcb.isSelected():"+jcb.isSelected());
				if(jcb.isSelected()){
					try {
						// 关闭日志
//						Properties properties = new Properties();
//						// 文件输出流
//						FileOutputStream os = new FileOutputStream(
//								"conf/logToggle.properties");
//						properties.setProperty("logToggle", "true");
//						// 将Properties集合保存到流中
//						properties.store(os, "Copyright (c) deppon");
//						os.close();// 关闭流
						//设置配置文件
						propertiesUtil.setProperties(path, "logToggle", "true");
						CommonContents.logToggle = true ;
						jcb.setSelected(true);
						logger.info("日志开关打开成功");
					} catch (Exception e2) {
						logger.error("日志开关打开失败："+e2.getMessage(),e2);
					}
				}else {
					//打开日志
					try {
						logger.info("jcb.isSelected():"+jcb.isSelected());
						// 关闭日志
//						Properties properties = new Properties();
//						// 文件输出流
//						FileOutputStream os = new FileOutputStream(
//								"conf/logToggle.properties");// "src/temp.properties"
//						properties.setProperty("logToggle", "false");
//						// 将Properties集合保存到流中
//						properties.store(os, "Copyright (c) deppon");
//						os.close();// 关闭流
						propertiesUtil.setProperties(path, "logToggle", "false");
						CommonContents.logToggle = false ;
						jcb.setSelected(false);
						logger.info("日志开关关闭成功");
					} catch (Exception e2) {
						logger.error("日志开关关闭失败："+e2.getMessage(),e2);
					}
				}
				
			}
		});
		
		JPanel jPanel = new JPanel();
		jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.LINE_AXIS));
		jPanel.add(jLabel);
		jPanel.add(jcb);
		jPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder()
				,"日志开关", TitledBorder.LEFT, TitledBorder.DEFAULT_JUSTIFICATION
				,new Font("华文楷体",Font.PLAIN,NumberConstants.NUMBER_20)
				,Color.red));
		
		this.add(jPanel);
	}
	
	

}

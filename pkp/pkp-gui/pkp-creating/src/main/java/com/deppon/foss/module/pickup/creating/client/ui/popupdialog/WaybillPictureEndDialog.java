package com.deppon.foss.module.pickup.creating.client.ui.popupdialog;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.deppon.foss.base.util.define.NumberConstants;
public class WaybillPictureEndDialog extends JDialog implements ActionListener{
	
	private static final long serialVersionUID = 7261592763093891412L;
	
	private static final int NUM_75 = 75;

	private static final int NUM_80 = 80;
	
	private static final int NUM_140 = 140;
	
	private static final int NUM_260 = 260;
	
	private Log logger = LogFactory.getLog(WaybillPictureEndDialog.class);
	private JButton jbSure  ;
	private JLabel jlabel ;
	private JTextField jTextField ;
	private String context = "" ;
	
	public WaybillPictureEndDialog() {
		logger.info("进入 WaybillPictureEndDialog 对话框");
		init();
	}
	
	public void init(){
		jbSure = new JButton("确定");
		jlabel = new JLabel("请输入中止原因：");
		jTextField = new JTextField(NumberConstants.NUMBER_25);
		
		jbSure.addActionListener(this) ;
		this.setLayout(null);
		//
		jlabel.setBounds(NumberConstants.NUMBER_25, NumberConstants.NUMBER_10, NumberConstants.NUMBER_100, NumberConstants.NUMBER_20);
		jTextField.setBounds(NumberConstants.NUMBER_25, NumberConstants.NUMBER_35, NumberConstants.NUMBER_200, NumberConstants.NUMBER_25);
		jbSure.setBounds(NUM_80, NumberConstants.NUMBER_70, NUM_75, NumberConstants.NUMBER_30);
		
		this.add(jlabel);
		this.add(jTextField);
		this.add(jbSure);
		
		//
		this.setTitle("图片运单中止");
		this.setModal(true);
		this.setLocation(NumberConstants.NUMBER_400, NumberConstants.NUMBER_300) ;
//		this.setIconImage(image);
		this.setSize(NUM_260, NUM_140);
		this.setResizable(false);
		this.setVisible(true);
	}
	
	public String getContext(){
		logger.debug("返回内容：context:"+context);
		return context ;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		context = jTextField.getText() ;
		logger.debug("输入内容：context："+context) ;
		if(StringUtils.isBlank(context)){
			jTextField.setBorder(BorderFactory.createLineBorder(Color.RED)) ;
			return ;
		}
		this.dispose();
	
	}
}


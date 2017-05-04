package com.deppon.foss.print.labelprint.gui;

import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.deppon.foss.print.labelprint.service.LblPrtServiceConst;

public class LblPrtSetupWindow extends LblPrtForm {
	
	private static final long serialVersionUID = 3562530459609716597L;
	private static LblPrtSetupWindow instance;
	
	private LblPrtSetupWindow(){
		super();
	}
	
	public static LblPrtSetupWindow getInstance(){
		if(instance==null){
			instance = new LblPrtSetupWindow();
		}
		return instance;
	}
	
	@Override
	public JPanel makeBottomControlPanel() {
		JPanel _panel = new JPanel();
		_panel.setSize(new Dimension(400,40));
		
		JButton btnSaveSetting = null;
		btnSaveSetting = new JButton();
		btnSaveSetting.setText(getUIResource(LblPrtServiceConst.key_ui_button_save_setting));
		btnSaveSetting.addActionListener(new SaveSettingActionListener());
		_panel.add(btnSaveSetting);
		
		JButton btnHide = null;
		btnHide = new JButton();
		btnHide.setText(getUIResource(LblPrtServiceConst.key_ui_button_hide));
		btnHide.addActionListener(new HideConsoleActionListener());
		_panel.add(btnHide);
		
		return _panel;
	}
	
}

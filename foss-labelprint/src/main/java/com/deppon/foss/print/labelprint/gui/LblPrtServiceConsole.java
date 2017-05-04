package com.deppon.foss.print.labelprint.gui;

import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.deppon.foss.print.labelprint.service.LblPrtServiceConst;

/**
 * Label Print Serivce Console 
 * 1. detect all printer from local PC, user can set one as default.
 * 2. xml config for label printer's special print program (java), user can set one as default.
 * 3. test print button
 * 4. jetty service turn on/off
 * 5. print job list, job status, job control(stop , delete), max print jobs control.
 * 
 * @author niujian
 *
 */
public class LblPrtServiceConsole extends LblPrtForm {
	
	private static final long serialVersionUID = 8142420472447684293L;
	private static LblPrtServiceConsole instance;
	
	private LblPrtServiceConsole(){
		super();	
	}
	
	public static LblPrtServiceConsole getInstance(){
		if(instance==null){
			instance = new LblPrtServiceConsole();
		}
		return instance;
	}
	
	public void openConsole(){
		openWindow();
	}
	
	public void hideConsole(){
		hideWindow();
	}
	
	@Override
	public JPanel makeBottomControlPanel() {
		JPanel _panel = new JPanel();
		_panel.setSize(new Dimension(400,30));
		
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

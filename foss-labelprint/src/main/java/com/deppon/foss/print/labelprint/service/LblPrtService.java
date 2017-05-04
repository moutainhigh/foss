package com.deppon.foss.print.labelprint.service;

import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;

import javax.swing.ImageIcon;

import org.mortbay.jetty.Server;

import com.deppon.foss.print.labelprint.gui.LblPrtServiceConsole;
import com.deppon.foss.print.labelprint.util.Log;
import com.deppon.foss.print.labelprint.util.PropertiesUtil;

public class LblPrtService {

	public static void main(String[] args){
		LblPrtService ser = new LblPrtService();
	}
	
	public LblPrtService() {
		

		try{
			PropertiesUtil.initProperties();
//			System.loadLibrary("termbjava"); 
			initSystemTray();
			startLblPrtService();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void showLblPrtServiceConsole(){
		LblPrtServiceConsole console = LblPrtServiceConsole.getInstance();
		console.setLblService(this);
		console.openConsole();
	}
	
	private Server jettyserver = null;
	private void startLblPrtService() throws Exception {
		int port = Integer.parseInt(PropertiesUtil.get("lblprt.service.port"));
		Log.info(" Starting Label Print Service at localhost:"+port);
		jettyserver = new Server(port);
		jettyserver.setHandler(new DefaultRequestHandler());
				
		jettyserver.start();
		jettyserver.join();
		Log.info(" Started success " );
	}
	
	public int getLblServiceStatus(){
		/*
		 * 0 - failed 1 - running 2 - started 3 - starting 4 - stopped 5 - stopping
		 */
		if (jettyserver != null) {
			if (jettyserver.isFailed()) {
				return 0;
			} else if (jettyserver.isRunning()) {
				return 1;
			} else if (jettyserver.isStarted()) {
				return 2;
			} else if (jettyserver.isStarting()) {
				return 3;
			} else if (jettyserver.isStopped()) {
				return 4;
			} else if (jettyserver.isStopping()) {
				return 5;
			} else {
				return -1;
			}
		} else {
			return -1;
		}
	}
	
	private String getUIResource(String key ){
		return (String)PropertiesUtil.get(key);
	}
	
	TrayIcon trayIcon = null;
	private void initSystemTray() throws Exception {
		if (SystemTray.isSupported()) {
			
			URL url = this.getClass().getResource("/com/deppon/foss/print/labelprint/service/images/printer16.png");
			ImageIcon icon = new ImageIcon(url); 
			Image image = icon.getImage();
			trayIcon = new TrayIcon(image);
			trayIcon.addMouseListener(new MouseAdapter() { 
				public void mouseClicked(MouseEvent e) { 
					if (e.getClickCount() == 2) {
						showLblPrtServiceConsole();
					}
				}
			});
			trayIcon.setToolTip(getUIResource(LblPrtServiceConst.key_ui_tray_icon_tooltip));
			
			PopupMenu popupMenu = new PopupMenu();
			MenuItem consoleitem = new MenuItem(getUIResource(LblPrtServiceConst.key_ui_menu_console));
			consoleitem.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					showLblPrtServiceConsole();
				}
			});
			
			MenuItem exititem = new MenuItem(getUIResource(LblPrtServiceConst.key_ui_menu_quit));
			exititem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					SystemTray.getSystemTray().remove(trayIcon); 
					System.exit(0);
				}
			});
			popupMenu.add(consoleitem); 
			popupMenu.add(exititem); 
			
			trayIcon.setPopupMenu(popupMenu);
			SystemTray systemTray = SystemTray.getSystemTray();
			systemTray.add(trayIcon); 
		}
	}
}

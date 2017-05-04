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
 * FILE PATH        	: foss-webstart/src/main/java/com/deppon/foss/client/ClientApp.java
 * 
 * FILE NAME        	: ClientApp.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.client;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.UIManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.deppon.foss.util.ClassPathResourceUtil;
import com.deppon.foss.util.Md5;
import com.jgoodies.looks.plastic.PlasticLookAndFeel;
import com.jgoodies.looks.plastic.theme.LightGray;

public class ClientApp extends JFrame {
	
	private static final Log log = LogFactory.getLog(ClientApp.class);
	private static final long serialVersionUID = -4117347642193675467L;
	private static final int width = 600;
	private static final int height = 250;
	public static File currWorkDir = new File(".");
	public static String downloadserver = "192.168.20.21";
	public static String httpDownloadserver = "192.168.20.21";
	public static String httpDownloadPort = "192.168.20.21";
	public static String downloadtype = ClientAppConstants.key_download_type_ftp;
	public static String downloadftpport = "21";
	public static String downloadftpmode = ClientAppConstants.key_ftp_mode_Passive;
	public static String downloadftpusername = "dpfoss";
	public static String downloadftppassword = "foss*ftp";
	public static String downloadpath = null;
	public static String dbOutputFloder = "database";
	public static String dbFileName = dbOutputFloder+File.separator+"fossdb.zip";
	
	public static String fossguistartvalue = "deppon1foss2gui3start4";

	public static String VERSION ="2.0";
	
	public static String env = "test";
	private static final int NUM_8 = 8 ;
	private static final int NUM_6 = 6 ;
	private static final int NUM_7 = 7 ;
	private static final int NUM_3 = 3 ;
	private static final int NUM_4 = 4 ;
	private static final int NUM_5 = 5 ;
	
	public static void main(String[] args) {
		//file.deppon.com.cn
		args = new String[]{"192.168.20.21","http", "appHome/", "dpfoss", "foss*ftp","21", "192.168.20.21","80","test"};
		String paramfoss = System.getProperty("foss");
		log.info("input param foss : " + paramfoss);
		if(!fossguistartvalue.equals(paramfoss)){
			ClientAppAlertUtil util = ClientAppAlertUtil.getInstance(null);
			util.showError("", ClientAppMessages.getString("ui.error.msg.000004"));
			System.exit(0);
		}
		
		if(args!=null && args.length>0){
			downloadserver = args[0];
			try{
				downloadtype = args[1];
			}catch (Exception e) {
				downloadtype = ClientAppConstants.key_download_type_http;
			}
			env = args[NUM_8 ];
			if(ClientAppConstants.key_download_type_http.equals(downloadtype)){
				try{
					downloadpath = args[2];
				}catch (Exception e) {
					downloadpath = null;
				}
//				downloadftpmode = "";
//				downloadftpusername = "";
//				downloadftppassword = "";
				httpDownloadserver = args[NUM_6];
				httpDownloadPort= args[NUM_7];
			}
			else if(ClientAppConstants.key_download_type_ftp.equals(downloadtype)){
				downloadpath = null;
				 
//				try{
//					downloadftpmode = args[2];
//				}catch (Exception e) {
//					downloadftpmode = ClientAppConstants.key_ftp_mode_Active;
//				}
				
//				try{
//					downloadftpusername = args[3];
//				}catch (Exception e) {
//					downloadftpusername = "anonymous";
//				}
//				
//				try{
//					downloadftppassword = args[4];
//				}catch (Exception e) {
//					downloadftppassword = "";
//				}
//				
//				try{
//					downloadftpport = args[5];
//				}catch (Exception e) {
//					downloadftpport = "";
//				}
			}
		}
//		try{
//			downloadftpmode = args[2];
//		}catch (Exception e) {
//			downloadftpmode = null;
//		}
		try{
			downloadftpusername = args[NUM_3];
		}catch (Exception e) {
			downloadftpusername = "anonymous";
		}
		
		try{
			downloadftppassword = args[NUM_4];
		}catch (Exception e) {
			downloadftppassword = "";
		}
		
		try{
			downloadftpport = args[NUM_5];
		}catch (Exception e) {
			downloadftpport = "";
		}
		ClientApp client = null;
		try{
			
//			DefaultClientSetupUtil setupUtil = DefaultClientSetupUtil.getInstance();
//			String _downloadserver = setupUtil.loadSetDefaultValue(DefaultClientSetupUtil.key_download_server);
//			String _downloadtype = setupUtil.loadSetDefaultValue(DefaultClientSetupUtil.key_download_type);
//			String _downloadftpport = setupUtil.loadSetDefaultValue(DefaultClientSetupUtil.key_ftp_port);
//			String _downloadftpmode = setupUtil.loadSetDefaultValue(DefaultClientSetupUtil.key_ftp_mode);
//			String _downloadftpusername = setupUtil.loadSetDefaultValue(DefaultClientSetupUtil.key_ftp_username);
//			String _downloadftppassword = setupUtil.loadSetDefaultValue(DefaultClientSetupUtil.key_ftp_password);
//			
//			if(_downloadserver!=null ){
//				downloadserver = _downloadserver;
//			}
//			if(_downloadtype!=null && 
//					(ClientAppConstants.key_download_type_ftp.equals(_downloadtype)) || ClientAppConstants.key_download_type_http.equals(_downloadtype)){
//				downloadtype = _downloadtype;
//			}
//			
//			if(ClientAppConstants.key_download_type_ftp.equals(_downloadtype)) {
//				downloadftpport = _downloadftpport;
//			}
//			
//			if(_downloadftpmode!=null ){
//				downloadftpmode = _downloadftpmode;
//			}
//			
//			if(_downloadftpusername!=null ){
//				downloadftpusername = _downloadftpusername;
//			}
//			
//			if(_downloadftppassword!=null ){
//				downloadftppassword = DefaultClientSetupUtil.getEncrypt  (_downloadftppassword);
//			}
			
			log.info("启动Foss 客户端下载环境");
			client = new ClientApp();
			ProcesserKiller.killOtherTasks(client);
			
			client.startClient();
			client.forceToAutoUpdate();
		}catch (Exception e) {
			String errtitle = ClientAppMessages.getString("ui.error.msg.000001");
			log.error(errtitle,e);
			ClientAppAlertUtil util = ClientAppAlertUtil.getInstance(client);
			util.showError(errtitle, ClientAppMessages.getString("ui.error.msg.000003"));
		}
	}

	public ClientApp() throws Exception {
		initUILooksAndFeel();
		initDefaultAppAction();
		initUIComponents();
		
	}
	
	private void initDefaultAppAction() {
		//this.setUndecorated(true);
		//this.getRootPane().setWindowDecorationStyle(JRootPane.FRAME);
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				doSystemExit();
			}
		});
	}
	
	private void initUILooksAndFeel() {
		PlasticLookAndFeel.setPlasticTheme(new LightGray());
		try {
			UIManager.setLookAndFeel("com.jgoodies.looks.windows.WindowsLookAndFeel");
		} catch (Exception e) {
			
		}
	}
	
	private JProgressBar progressBar = null;
	private JLabel progressLabel = null;
	private JButton olrecoverbtn = null;
	private static final int NUM_8096 = 8096 ;
	private static final int NUM_17 = 17 ;
	private static final int NUM_599 = 599 ;
	private static final int NUM_80 = 80 ;
	private static final int NUM_170 = 170 ;
	private static final int NUM_200 = 200 ;
	private static final int NUM_30 = 30 ;
	private static final int NUM_24 = 24 ;
	private static final int NUM_40 = 40 ;
	private static final int NUM_1024 = 1024 ;
	private static final long NUM_2000 = 2000 ;

	private void initUIComponents() throws Exception {
		this.setTitle(ClientAppMessages.getString(ClientAppConstants.key_ui_app_title));
		ClassPathResourceUtil loadutil = new ClassPathResourceUtil();
		ImageIcon icon = new ImageIcon(this.getClass().getResource("/com/deppon/foss/client/img/foss.png"));
		this.setIconImage(icon.getImage());
		// create logo panel 
		BufferedImage image = ImageIO.read(loadutil.getInputStream("com/deppon/foss/client/img/dp-foss.jpg"));
		ClientImagePane logoPanel = new ClientImagePane(image, ClientImagePane.CENTRE);
		logoPanel.setPreferredSize(new Dimension(NUM_599,NUM_80));
		logoPanel.setBorder(BorderFactory.createEmptyBorder());

		// create console panel
		JPanel consolePanel = new JPanel();
		consolePanel.setBorder(BorderFactory.createEtchedBorder());
		consolePanel.setLayout(new BorderLayout());
		consolePanel.setPreferredSize(new Dimension(NUM_599,NUM_170));

		/*
		ConsoleTextArea outputarea = ConsoleTextArea.getInstance();
		outputarea.setBackground(Color.WHITE);
		outputarea.setText("");
		outputarea.setEditable(false);
		outputarea.setRows(1000);
		outputarea.setColumns(50);
		consolePanel.add(new JScrollPane(outputarea),BorderLayout.CENTER);*/
		
		String startmessage = ClientAppMessages.getString("ui.launch.msg.000001" ) +"   : [" + VERSION+"]";
		JLabel processtitlelbl = new JLabel("<html><div style='margin:10px;font-size:12pt;font-family:微软雅黑;'>"+startmessage+"</div></html>");
		consolePanel.add(processtitlelbl,BorderLayout.NORTH);
		
		progressLabel = new JLabel("<html><div style='margin:10px;'></div></html>");
		progressLabel.setPreferredSize(new Dimension(NUM_200, NUM_30));
		consolePanel.add(progressLabel,BorderLayout.CENTER);
		
		progressBar = new JProgressBar();
        progressBar.setSize(NUM_599, NUM_24);
        consolePanel.add(progressBar,BorderLayout.SOUTH);
		
		// create control button panel
		JPanel controlBtnPanel = new JPanel();
		controlBtnPanel.setBorder(BorderFactory.createEtchedBorder());
		controlBtnPanel.setPreferredSize(new Dimension(NUM_599,NUM_40));
		controlBtnPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		olrecoverbtn = new JButton(ClientAppMessages.getString(ClientAppConstants.key_ui_btn_onlinerecover));
		olrecoverbtn.addActionListener(new OnlineRecoverAction());
		//JButton stopdbtn = new JButton(ClientAppMessages.getString(ClientAppConstants.key_ui_btn_stopdownload));
		//stopdbtn.addActionListener(new StopDownLoadAction());
		//JButton launchbtn = new JButton(ClientAppMessages.getString(ClientAppConstants.key_ui_btn_launchfoss));
		//launchbtn.addActionListener(new LaunchFossGuiAction());
		//JButton logbtn = new JButton(ClientAppMessages.getString(ClientAppConstants.key_ui_btn_showlog));
		//logbtn.addActionListener(new ShowClientAppLogAction());
		JButton closebtn = new JButton(ClientAppMessages.getString(ClientAppConstants.key_ui_btn_close));
		closebtn.addActionListener(new CloseAppAction());
		JButton setupbtn = new JButton(ClientAppMessages.getString(ClientAppConstants.key_ui_btn_setup));
		setupbtn.addActionListener(new SetupClientAppAction());
		
		controlBtnPanel.add(olrecoverbtn);
		//controlBtnPanel.add(stopdbtn);
		//controlBtnPanel.add(launchbtn);
		//controlBtnPanel.add(logbtn);
		controlBtnPanel.add(closebtn);
		controlBtnPanel.add(setupbtn);
				
		setLayout(new BorderLayout());
		getContentPane().add(logoPanel, BorderLayout.NORTH);
		getContentPane().add(consolePanel, BorderLayout.CENTER);
		getContentPane().add(controlBtnPanel, BorderLayout.SOUTH);
	}
	
	public void setProgressPercent(int pos){
		if(progressBar!=null){
			progressBar.setValue(pos);
		}
	}
	
	public void setProgressLabel(String text){
		if(progressLabel!=null){
			progressLabel.setText("<html><div style='margin:10px;font-size:13pt;font-family:微软雅黑;'>"+text+"</div></html>");
		}
	}
	
	public void setOlRecoverbtnEnable(boolean enable){
		if(olrecoverbtn!=null){
			this.olrecoverbtn.setEnabled(enable);
		}
	}
	
	public void startClient() throws Exception {
		setSize(new Dimension(width,height));
		double width_ = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height_ = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width_ - width) / 2,
				(int) (height_ - height) / 2);
		
		//this.setModal(true);
		setResizable(true);
		this.setVisible(true);
	}
	
	public void hideClientUI(){
		this.setVisible(false);
		this.dispose();
	}
	
	public boolean doOnlineRecover(String pdownloadserver,
			String pdownloadtype, String pdownloadftpport,
			String pdownloadftpmode, String pdownloadftpusername,
			String pdownloadftppassword) {
		try{
			
			if(mainDownLoadThread!=null && mainDownLoadThread.getStatus()==1){
				int result = JOptionPane.showConfirmDialog(getClientAppFrame(),
						ClientAppMessages.getString("ui.confirm.msg.000001"), "", JOptionPane.YES_NO_OPTION);

				if (JOptionPane.NO_OPTION == result) {
					return false;
				}
				else {
					mDownThreadStatus = 0;
				}
			}
			
			if(pdownloadserver!=null ){
				downloadserver = pdownloadserver;
			}
			if(pdownloadtype!=null){
				downloadtype = pdownloadtype;
			}
			if(ClientAppConstants.key_download_type_ftp.equals(downloadtype)){
				downloadftpport = pdownloadftpport;
			}
			if(pdownloadftpmode!=null){
				downloadftpmode = pdownloadftpmode;
			}
			if(pdownloadftpusername!=null){
				downloadftpusername = pdownloadftpusername;
			}
			if(pdownloadftppassword!=null){
				downloadftppassword = pdownloadftppassword;
			}
			
			Thread th = new Thread() {
				public void run() {
					try{
						setOlRecoverbtnEnable(false);
						Thread.sleep(NUM_2000);
						Map<String,String> remoteMap = loadRemoteAppFileMD5Hex();
						List<String> alllist = new ArrayList<String>();
						alllist.addAll(remoteMap.keySet());
						updateFossGuiAppHome(alllist);
					}catch (Exception exp) {
						setOlRecoverbtnEnable(true);
						log.error("Online Recover Action",exp);
						ClientAppAlertUtil util = ClientAppAlertUtil.getInstance(getClientAppFrame());
						util.showError(ClientAppMessages.getString("ui.error.msg.000002"), ClientAppMessages.getString("ui.error.msg.000003"));
					}
				}
			};
			th.start();
			
		}catch (Exception exp) {
			setOlRecoverbtnEnable(true);
			log.error("Online Recover Action",exp);
		}
		return true;
	}
	
	class OnlineRecoverAction implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			doOnlineRecover(null,null,null,null,null,null);
		}
	}
	
	class StopDownLoadAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			
		}
	}
	
	class LaunchFossGuiAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			
		}	
	}
	
	class ShowClientAppLogAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			
		}
	}
	
	class CloseAppAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			doSystemExit();
		}
	}
	
	class SetupClientAppAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			try{
				ClientAppSetupDialog setupDialog = ClientAppSetupDialog.getInstance(getClientAppFrame());
				if(ClientAppConstants.key_download_type_ftp.equals(downloadtype)){
					setupDialog.resetDefaultSetup(downloadserver,downloadtype,downloadftpport,downloadftpmode,downloadftpusername,downloadftppassword);
				}else{
					setupDialog.resetDefaultSetup(httpDownloadserver,downloadtype,downloadftpport,downloadftpmode,downloadftpusername,downloadftppassword);
				}
				setupDialog.pop();
			}catch (Exception exp) {
				exp.printStackTrace();
				log.error("启动参数配置界面错误",exp);
			}
		}
	}
	
	private void doSystemExit(){
		int result = JOptionPane.showConfirmDialog(this, ClientAppMessages
				.getString(ClientAppConstants.key_ui_message_confirm_exit), "",
				JOptionPane.YES_NO_OPTION);
		if (result == JOptionPane.YES_OPTION) {
			this.dispose();
			System.exit(0);
		}
	}
	
	public ClientApp getClientAppFrame(){
		return this;
	}
	
	public boolean isFTPConnectionOK(){
		
		return false;
	}
	
	class DownloadFinishHandler {
		public void doSomething(int result) throws Exception {
			try {
				if (result == 1) {
					hideClientUI();
					launchFossGui();
				}
			} catch (Exception e) {
				log.error("客户端更新完成，启动Foss客户端出错",e);
				throw new Exception("客户端更新完成，启动Foss客户端出错："+e.toString());
			} finally{
				if(result==1){
					System.exit(0);
				}
			}
		}
	}
	
	public static int mDownThreadStatus = 0; // 0=stop, 1=running 
	public static DownloadThread mainDownLoadThread = null;
	class DownloadThread extends Thread {
		
		List<String> updateList = null;
		public DownloadThread(){
		}
		
		public void setUpdateList(List<String> pUpdateList){
			this.updateList = pUpdateList;
		}
		
		private DownloadFinishHandler handler = null;
		private static final long NUM_1000 = 1000 ;
		private static final int NUM_100 = 100 ;
		public void setDownloadFinishHandler(DownloadFinishHandler phandler){
			this.handler = phandler;
		}
		
		@Override
		public synchronized void start() {
			if(mDownThreadStatus==0){
				mDownThreadStatus=1;
			}
			
			if(!this.isAlive()){
				super.start();
			}
		}

		
		//public void do_break(){
			//mDownThreadStatus = 0;
		//}
		
		public int getStatus(){
			return mDownThreadStatus;
		}
		
		@Override
		public void run() {
			while(true){
				try{
					Thread.sleep(NUM_1000 );
					log.info("下载监控主线程，1s监控一次.");
				}catch (Exception e) {
					//to do nothing
				}
				if(mDownThreadStatus==1){
					AutoUpdateProcessMoniter moniter = null;
					try{
						deleteAll(new File(getFossGuiAppHomeTmpAbsPath()));
						
						(new File(getFossGuiAppHomeTmpAbsPath())).mkdirs();
						
						boolean docontinue = false;
						if(updateList!=null && !updateList.isEmpty()){
							IClientAppDownload util = ClientAppDownload.getAppDownload(downloadtype);
							util.resetConnectionConfig();
							if(ClientAppConstants.key_download_type_http.equals(downloadtype)){
								util.updateDownloadServer(httpDownloadserver,httpDownloadPort,null,downloadftpusername,downloadftppassword);
							}else{
								util.updateDownloadServer(downloadserver,downloadftpport,downloadftpmode,downloadftpusername,downloadftppassword);
							}
							try{
								setProgressLabel(ClientAppMessages.getString("ui.launch.msg.000005"));
								util.connect();
								int count = 0;
								for(String path : updateList){
									try{
										if(mDownThreadStatus==0){
											docontinue = true;
											break;
										}else {
											//if(ClientAppConstants.key_download_type_http.equals(downloadtype)){
												int pctg = (count++)*NUM_100/updateList.size();									
												String pmsg2 = ClientAppMessages.getString("ui.launch.msg.000002")+"("+pctg+"%)&nbsp;&nbsp;&nbsp;"+path;
												log.info(pmsg2);
												setProgressLabel(pmsg2);
												setProgressPercent(pctg);									
												String localPath = getFossGuiAppHomeTmpAbsPath()+File.separator+path;
												util.download(util.getRemoteAppHomeRootPath(downloadpath)+path, localPath);
//											}else{
//												throw new RuntimeException("http 下载出现异常，自动切换为ftp下载模式");
//											}
										}
									}catch(Exception ex){
										//如果抛出异常 就使用http下载
										throw ex;
//										if( ClientAppConstants.key_download_type_ftp.equals(downloadtype)){
//										 try {
//											 	util.disconnect();
//												//log.error("ftp 下载出现异常，自动切换为http下载模式", ex);
//												log.error("http 下载出现异常，自动切换为ftp下载模式", ex);
//												downloadtype = ClientAppConstants.key_download_type_ftp;
//												//downloadftpport = "80";
//												util = ClientAppDownload.getAppDownload(downloadtype);
//												util.resetConnectionConfig();
//												util.updateDownloadServer(downloadserver,downloadftpport,downloadftpmode,downloadftpusername,downloadftppassword);
//												int pctg = (count++)*100/updateList.size();									
//												String p_msg_2 = ClientAppMessages.getString("ui.launch.msg.000002")+"("+pctg+"%)&nbsp;&nbsp;&nbsp;"+path;
//												log.info(p_msg_2);
//												setProgressLabel(p_msg_2);
//												setProgressPercent(pctg);									
//												String local_path = getFossGuiAppHomeTmpAbsPath()+File.separator+path;
//												util.download(util.getRemoteAppHomeRootPath(downloadpath)+path, local_path);
//										} catch (Exception e) {
//											throw ex;
//										}	
										
//										}else{
//											log.error("http 下载出现异常情况 退出下载", ex);
//											throw ex;
//										}
									}
								}
							}finally{
								util.disconnect();
							}
							//检查数据库fossdb.zip文件是否有更新
							File dbFile = new File(getFossGuiAppHomeTmpAbsPath()+File.separator+dbFileName);
							if(dbFile.exists()){
								//解压数据文件
								unZipIt(dbFile, getFossGuiAppHomeTmpAbsPath()+File.separator+dbOutputFloder);
							}
						}
						
						if(docontinue){
							setProgressLabel("");
							setProgressPercent(0);
							continue;
						}
						
						String pMsg = ClientAppMessages.getString("ui.launch.msg.000003");
						moniter = new AutoUpdateProcessMoniter(pMsg);
						moniter.start();
						log.info(pMsg);
						setProgressLabel(pMsg);
						setProgressPercent(NUM_100);
						
						// copy bak file to apphome force overwrite
						File bakdir = new File(getFossGuiAppHomeTmpAbsPath());
						File targetdir = new File(getFossGuiAppHomeAbsPath());
						copyFile(bakdir,targetdir);
						deleteAll(bakdir);
						moniter.end();
						mDownThreadStatus = 0;
						if(this.handler!=null){
							handler.doSomething(1);
						}
					}catch(Exception e){
						String errstr = ClientAppMessages.getString("ui.error.msg.000002");
						log.error(errstr,e);
						ClientAppAlertUtil util = ClientAppAlertUtil.getInstance(getClientAppFrame());
						util.showError(errstr, ClientAppMessages.getString("ui.error.msg.000003"));
						setOlRecoverbtnEnable(true);
					}finally{
						if(moniter!=null) {
							moniter.end();
						}
					}	
				}
			}
		}
	}
	
	public void unZipIt(File zipFile, String outputFolder){
        byte[] buffer = new byte[NUM_1024];
        
        try{
            log.info("Extract starting.");
            //create output directory is not exists
            File folder = new File(outputFolder);
            if(!folder.exists()){
                folder.mkdir();
            }
            //get the zip file content
            ZipInputStream zis = null;
            try{
	            zis= new ZipInputStream(new FileInputStream(zipFile));
	            //get the zipped file list entry
	            ZipEntry ze = zis.getNextEntry();
	            
	            while(ze!=null){
	                String fileName = ze.getName();
	                File newFile = new File(outputFolder + File.separator +fileName);
	                log.info("file unzip: "+ newFile.getAbsoluteFile());
	                
	
	            	if(ze.isDirectory()){
	            		newFile.mkdirs();
	            	}else{
	            		//create all non exists folders
	                    //else you will hit FileNotFoundException for compressed folder
	            		if(!newFile.getParentFile().exists()){
	            			newFile.getParentFile().mkdirs();
	            		}
	                    FileOutputStream fos =null;
	                    try{
	                    	fos = new FileOutputStream(newFile);
		                    int len;
		                    while((len = zis.read(buffer)) > 0){
		                        fos.write(buffer,0,len);
		                    }
		                    fos.flush();
	                    }finally{
	                    	if(fos!=null){
	                    		fos.close();
	                    	}
	                    }
	            	}
	               
	                ze = zis.getNextEntry();
	            }
	            
	           
            }finally{
            	if(zis!=null){
            		zis.closeEntry();
            		zis.close();
            	}
            }
            log.info("Extract completed.");
        }catch(IOException e){
        	log.error("解压缩文件出错",e);
        }
    }
	
	public String getFossGuiAppHomeAbsPath() throws Exception {
		return currWorkDir.getAbsolutePath()+File.separator+"appHome";
	}
	
	public String getFossGuiAppHomeTmpAbsPath() throws Exception {
		return currWorkDir.getAbsolutePath()+File.separator+"appHome_tmp";
	}
	
	public List<String> diffToIncrementList() throws Exception {
		
		AutoUpdateProcessMoniter moniter = new AutoUpdateProcessMoniter(ClientAppMessages.getString("ui.launch.msg.000006"));
		moniter.start();
		Map<String,String> remoteMap = null;
		try{
			remoteMap = loadRemoteAppFileMD5Hex();
		}catch(Exception e){
			log.error("load remote md5 file exception",e);
			return new ArrayList<String>();
			//下载出现异常 直接返回一个空的list什么也不下载直接进入appHome启动
		}
		moniter.end();
		
		moniter = new AutoUpdateProcessMoniter(ClientAppMessages.getString("ui.launch.msg.000004"));
		moniter.start();
		Map<String,String> localMap = scanLocalAppFileToMD5Hex(remoteMap);
		Map<String, String> localDateMap  = scanLocalAppFileToDate(remoteMap);
		moniter.end();
		
		List<String> incrementList = new ArrayList<String>();
		Set<String> keyset = remoteMap.keySet();
		for(String key : keyset){
			if(	
				(localMap.containsKey(key)||localDateMap.containsKey(key) )
				&& remoteMap.get(key)!=null && !"F".equals(remoteMap.get(key)) 
				){//远程和本地都有文件
				String remoteKey =(String) remoteMap.get(key);
				if("N".equals(remoteKey)){
					continue;
				}else if (remoteKey.startsWith("D")  &&   !"NA".equals(localDateMap.get(key)) ){//by date
					String remoteTime = remoteKey.substring(1);
					String localTime = localDateMap.get(key);
					
					try{
						long remoteTimelong = Long.parseLong(remoteTime);
						long localTimelong =  Long.parseLong(localTime);
						
						if(localTimelong<=remoteTimelong){
							incrementList.add(key);
						}
							
					}catch(Exception e){
						log.error("parse date",e);
					}
					
				}else if(!"NA".equals(localMap.get(key))){//md5
					String rmd5 = remoteMap.get(key);
					String lmd5 = localMap.get(key);
					if(!rmd5.equals(lmd5)){
						incrementList.add(key);
					}
				}
			
			}else {
				incrementList.add(key);
			}
		}
		
		log.info("获取远程版本与本机 Foss Gui差异程序包清单,差异文件："+incrementList.size()+"个");
		if(log.isDebugEnabled()){
			for(String f : incrementList){
				log.info("差异文件："+f);		
			}
		}		
		return incrementList;
	}
	
	private Map<String, String> loadRemoteAppFileMD5Hex() throws Exception {
		Map<String, String> m = new HashMap<String, String>();

		IClientAppDownload util = ClientAppDownload.getAppDownload(downloadtype);
		util.resetConnectionConfig();
		if(ClientAppConstants.key_download_type_http.equals(downloadtype)){
			util.updateDownloadServer(httpDownloadserver,httpDownloadPort,downloadftpmode,downloadftpusername,downloadftppassword);
		}else{
			util.updateDownloadServer(downloadserver,downloadftpport,downloadftpmode,downloadftpusername,downloadftppassword);
		}
		String local = System.getProperty("user.home") + File.separator + "md5hex.dat";
		try{
			util.connect();
			util.download(util.getRemoteAppHomeRootPath(downloadpath)+"md5hex.dat", local);
		}catch(Exception e){

//			if( ClientAppConstants.key_download_type_ftp.equals(downloadtype)){
//				util.disconnect();
//				log.error("ftp 下载md5hex出现异常，自动切换为http下载模式", e);
//				downloadtype = ClientAppConstants.key_download_type_http;
//				downloadftpport = "80";
//				util = ClientAppDownload.getAppDownload(downloadtype);
//				util.resetConnectionConfig();
//				util.updateDownloadServer(httpDownloadserver,downloadftpport,downloadftpmode,downloadftpusername,downloadftppassword);
//				util.connect();
//				util.download(util.getRemoteAppHomeRootPath(downloadpath)+"md5hex.dat", local);
//			}else{
			if(!"test".equals(env)){
				util.disconnect();
				log.error("http 下载md5hex 出现异常情况 自动切换为ftp下载模式", e);
				downloadtype = ClientAppConstants.key_download_type_ftp;
//				downloadftpport = "2121";
				util = ClientAppDownload.getAppDownload(downloadtype);
				util.resetConnectionConfig();
				util.updateDownloadServer(downloadserver,downloadftpport,downloadftpmode,downloadftpusername,downloadftppassword);
				util.connect();
				util.download(util.getRemoteAppHomeRootPath(downloadpath)+"md5hex.dat", local);
			}
				
//			}
			
			
		}finally{
			util.disconnect();
		}
		BufferedReader reader = new BufferedReader(new FileReader(local));
		String string = reader.readLine();
		while (string != null) {
			if (string.indexOf("=") != -1) {
				String key = string.split("=")[0];
				String value = string.split("=")[1];
				System.out.println("key="+key +" value="+value);
				m.put(key, value);
			}
			string = reader.readLine();
		}
		reader.close();

		log.debug("查询服务端远程appHome目录，共有需要根据MD5下载文件："+m.size()+"个");
		return m;
	}
	
	
	private Map<String, String> scanLocalAppFileToDate(Map<String,String> remoteMap) throws Exception {
		Map<String, String> dateMap = new HashMap<String, String>();
		File apphome = new File(getFossGuiAppHomeAbsPath());
		if(apphome.exists()){
			scanLocalAppFileToDateExp(apphome,dateMap,"",null ,remoteMap);
		}
		log.debug("扫描本机appHome目录，共有需要根据日期下载文件："+dateMap.size()+"个");
		return dateMap;
	}
	
	
	private void scanLocalAppFileToDateExp(File pf,Map<String, String> dateMap,String parentfolder,String excludeItems,
			Map<String,String> remoteMap) throws Exception {
		File[] files = pf.listFiles();
		for(File file : files){
			String fname = file.getName();
			String key = parentfolder+File.separator+fname;
			
			if(parentfolder.length()==0){
				key = fname;
			}	
			
			if(key.startsWith("\\")){
				key = key.substring(1);
			}
			key = key.replace("\\",  "/");
			
			if(excludeItems!=null && excludeItems.indexOf(","+fname+",")!=-1){
				continue;
			}else if(file.isFile() && ( remoteMap.get(key)==null || !remoteMap.get(key).startsWith("D")  ) ){
				dateMap.put(key,"NA");
			}else {
				if(file.isFile()){
				
				
					long time = 0;
					boolean success = false;
					
					try{
						time = parseFileDate(file, time, "yyyy-MM-dd  HH:mm");
						success = true;
					}catch(Exception e){
						//log.error("parseDate format exception yyyy-MM-dd  HH:mm", e);
					}
					
					if(!success){
						
						try{
							time = parseFileDate(file, time, "yyyy/MM/dd  HH:mm");	
						}catch(Exception e){
							log.error("parseDate format exception yyyy/MM/dd  HH:mm", e);
						}
					}
					
					
					dateMap.put(key,time+"");
				}else if(file.isDirectory()){
					scanLocalAppFileToDateExp(file, dateMap, parentfolder+File.separator+fname ,excludeItems, remoteMap);
				}	
			}
		}
	}

	/**
	 * @param file
	 * @param time
	 * @return
	 */
	private long parseFileDate(File file, long time, String format) throws Exception {
		String value = parseDate(file);//2013-02-21  18:28
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date date = sdf.parse(value);
		time = date.getTime();
		return time;
	}	

	
	private String parseDate(File file) {
		String dateStr = "";
		try{
			Process lsProc = Runtime.getRuntime().exec(  
					"cmd.exe /c dir " + file.getAbsolutePath() + " /tc");// 通过DOS获得的创建时间  
			
			InputStream is = lsProc.getInputStream();  
			BufferedReader br = new BufferedReader(new InputStreamReader(is));  
			String str;  
			int i = 0;  
			while ((str = br.readLine()) != null) {  
				i++;  
				if (i == NUM_6) {  
					dateStr = str.substring(0, NUM_17);
					log.warn( "str" + str);
					log.warn( "dateStr" + dateStr);
					log.debug("Create time:" + str.substring(0, NUM_17));
					
				}  
			}  
		}catch(Exception e){
			log.error("parseDate exception", e);
		}
		return dateStr;
		
	}

	private Map<String, String> scanLocalAppFileToMD5Hex(Map<String,String> remoteMap) throws Exception {
		Map<String, String> md5HexMap = new HashMap<String, String>();
		File apphome = new File(getFossGuiAppHomeAbsPath());
		if(apphome.exists()){
			scanLocalAppFileToMD5HexExp(apphome,md5HexMap,"",null,remoteMap);
		}
		log.debug("扫描本机appHome目录，共文件："+md5HexMap.size()+"个");
		return md5HexMap;
	}
	
	private void scanLocalAppFileToMD5HexExp(File pf,Map<String, String> md5HexMap,String parentfolder,String excludeItems,Map<String,String> remoteMap) throws Exception {
		File[] files = pf.listFiles();
		for(File file : files){
			String fname = file.getName();
			String key = parentfolder+File.separator+fname;
			if(parentfolder.length()==0){
				key = fname;
			}
			
			if(key.startsWith("\\")){
				key = key.substring(1);
			}
			key = key.replace("\\",  "/");
			
			if(excludeItems!=null && excludeItems.indexOf(","+fname+",")!=-1){
				continue;
			}else if(file.isFile() && (remoteMap.get(key)==null || (remoteMap.get(key)).startsWith("D")
				||"F".equals(remoteMap.get(key))||"N".equals(remoteMap.get(key)))
			){ //这些文件不用扫描md5
				md5HexMap.put(key,"NA");
			}else {
				if(file.isFile()){
				
					String value = parseMd5Hex(new FileInputStream(file));
					
					md5HexMap.put(key,value);
				}else if(file.isDirectory()){
					scanLocalAppFileToMD5HexExp(file, md5HexMap, parentfolder+File.separator+fname ,excludeItems, remoteMap);
				}	
			}
		}
	}	
	
	private String parseMd5Hex(FileInputStream fin ) throws IOException {
		Md5 md5 = new Md5 (fin);
		byte b[]= md5.getDigest();
		return md5.parseToValue(b);
	}
	
	class AutoUpdateProcessMoniter extends Thread {
		
		String msg = "";
		public AutoUpdateProcessMoniter(String msg){
			this.msg = msg;
		}
		
		private boolean end = false;
		private static final long NUM_500 = 500 ;
		public void end(){
			this.end = true;
			setProgressLabel("");
		}
		@Override
		public void run() {
			try{
				int points = NUM_5;
				while(true){
					StringBuilder strpoints = new StringBuilder();
					int i=0;
					while(i<NUM_6-points){
						strpoints.append("&nbsp;●");
						i++;
					}
					points++;
					if(points==NUM_6){
						points = 1;
					}
					setProgressLabel(this.msg+strpoints.toString());
					Thread.sleep(NUM_500);
					if(this.end){
						break;
					}
				}
			}catch(Exception e){
				//to do nothing
			}
		}
	}
	
	public void forceToAutoUpdate() throws Exception {
		try{
			log.info("自动运行，强制检查Foss Gui最新版本");	
			setOlRecoverbtnEnable(false);
			List<String> incrementList = diffToIncrementList();
			setProgressLabel("");
			updateFossGuiAppHome(incrementList);
		}catch(Exception e){
			throw e;
		}finally{
			setOlRecoverbtnEnable(true);
		}
		
	}
	
	public void updateFossGuiAppHome(List<String> updateList) throws Exception {
		if(mainDownLoadThread==null){
			mainDownLoadThread = new DownloadThread();
		}
		
		Collections.sort(updateList);
		mainDownLoadThread.setUpdateList(updateList);
		mainDownLoadThread.setDownloadFinishHandler(new DownloadFinishHandler());
		mainDownLoadThread.start();
	}
	
	public boolean backupFossGui(){
		// backup foss gui for recover
		
		return true;
	}
	
	public void launchFossGui() throws Exception {
		String path = currWorkDir.getAbsolutePath() + File.separator + "appHome" + File.separator + "foss.exe";
		Process process = Runtime.getRuntime().exec("cmd /c " + path);
		
		/*
		CommandLine cl = new CommandLine(path);
		DefaultExecutor executor = new DefaultExecutor();
		try {
			executor.execute(cl);
		} catch (Exception e) {
			throw e;
		}*/
	}
	
	public boolean deleteAll(File file) {
		if (file != null && file.exists()) {
			if (file.isDirectory()) {
				// 如果是目录, 先删除其子目录
				File[] children = file.listFiles();
				if (children != null && children.length > 0){
					for (int i = 0; i < children.length; i++){
						deleteAll(children[i]); // 递归删除
					}
				}
			}
			if (!file.delete()) {
				return false; // 删除文件
			}
		}
		return true;

	}
	
	public void copyFile(File fromDir, File toDir) throws Exception {
		if(fromDir.isDirectory()){
			File[] files = fromDir.listFiles();
			for(File file : files){
				if(file.isDirectory()){
					copyFile(file,toDir);
				}else if(file.isFile()){
					copyFile(file.getAbsolutePath(), file.getAbsolutePath().replace("appHome_tmp", "appHome"));
				}
			}
		}
		else if(fromDir.isFile()){
			copyFile(fromDir.getAbsolutePath(), fromDir.getAbsolutePath().replace("appHome_tmp", "appHome"));
		}
	}
	
	public void copyFile(String fromFileName, String toFileName)
			throws IOException {
		String frmpath = fromFileName;
		String topath = toFileName;
		File fromFile = new File(frmpath);
		File toFile = new File(topath);

		if (!fromFile.exists())
			throw new IOException("FileCopy: " + "no such source file: "
					+ fromFileName);
		if (!fromFile.isFile())
			throw new IOException("FileCopy: " + "can't copy directory: "
					+ fromFileName);
		if (!fromFile.canRead())
			throw new IOException("FileCopy: " + "source file is unreadable: "
					+ fromFileName);

		if (toFile.isDirectory())
			toFile = new File(toFile, fromFile.getName());

		if (toFile.exists()) {
			if (!toFile.canWrite())
				throw new IOException("FileCopy: "
						+ "destination file is unwriteable: " + toFileName);
			toFile.delete();
		} else {
			File dir = toFile.getParentFile();
			if (!dir.exists())
				dir.mkdirs();
		}
		InputStream from = null;
		OutputStream to = null;
		try {
			from = new BufferedInputStream(new FileInputStream(fromFile), NUM_8096);
			to = new BufferedOutputStream(new FileOutputStream(toFile), NUM_8096);
			byte[] buffer = new byte[NUM_8096];
			int bytesRead = -1;

			while ((bytesRead = from.read(buffer)) != -1)
				to.write(buffer, 0, bytesRead); // write
		}catch(Exception e){
			log.error("copy file exception", e);
			
		} finally {
			if (from != null)
				try {
					from.close();
				} catch (IOException e) {
					//to do nothing
				}
			if (to != null)
				try {
					to.close();
				} catch (IOException e) {
					//to do nothing
				}
		}
	}
	
}
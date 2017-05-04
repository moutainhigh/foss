/**
 * 
 */
package com.deppon.foss.client;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 杀死除自己之外的java进程
 * @author shixiaowei
 * 
 */
public class ProcesserKiller {
	
	private static final Log log = LogFactory.getLog(ProcesserKiller.class);
	public static void main(String[] args){
		killOtherTasks(null);
		
	}
	
	/**
	 * 杀死除自己之外的java进程
	 */
	public static void killOtherTasks(JFrame jframe) {
		Process process = null;
		boolean needKillProcess = true;
		boolean decideKill = false;
		BufferedReader input = null;
		try {
			process = Runtime.getRuntime().exec("cmd.exe   /c   tasklist");
			input= new BufferedReader(new InputStreamReader(
					process.getInputStream(), "GBK"));
			String line = "";
			while ((line = input.readLine()) != null) {
				if (line.startsWith("fosslauncher.exe") || line.startsWith("foss.exe")) {
					String strs[] = line.split("\\s+");
					if (!strs[1].equals(getPid())) {
						if(needKillProcess){
							if(jframe!=null){
								Object[] possibilities = { ClientAppMessages.getString("ui.launch.msg.hint.yes"),ClientAppMessages.getString("ui.launch.msg.hint.no") }; //"是", "否"
								int k = JOptionPane.showOptionDialog(jframe, ClientAppMessages.getString("ui.launch.msg.restartconfirm"),//"已有foss.exe程序在运行，是否停止重新启动？", 
										ClientAppMessages.getString("ui.launch.msg.hint"),//"提示",  
										JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null,
										possibilities, possibilities[0]);
								if(JOptionPane.YES_OPTION==k){
									//kill old thread
									needKillProcess = false;
									decideKill = true;
									Runtime.getRuntime().exec("TASKKILL /F /PID  " + strs[1]);
									
								}else{
									System.exit(1);
									//ignore this operation
									needKillProcess = false;
								}
								
							}else{
								Runtime.getRuntime().exec("TASKKILL /F /PID  " + strs[1]);
							}
						}else{							
							if(decideKill){
								Runtime.getRuntime().exec("TASKKILL /F /PID  " + strs[1]);
							}
						}
					}
				}
			}
		} catch (IOException e) {
			log.error("kill process exception", e);
		}finally{
			try {
				if(input !=null){
					input.close();
				}
			} catch (IOException e) {
				log.error("kill process exception", e);
			}
		}
	} 

	/**
	 * @return 自己的进程id
	 */
	private static String getPid() {
		RuntimeMXBean runtime = ManagementFactory.getRuntimeMXBean();
		String name = runtime.getName(); // format: "pid@hostname"
		try {
			return name.substring(0, name.indexOf('@'));
		} catch (Exception e) {
			log.error("get pid exception", e);
			return "-1";
		}
	}
}

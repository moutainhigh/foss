package com.deppon.foss.module.mainframe.client.utills;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.client.component.remote.RemoteInfo;
import com.deppon.foss.framework.client.core.context.ApplicationContext;
import com.deppon.foss.framework.client.core.context.SessionContext;
import com.deppon.foss.framework.shared.util.file.IOUtils;

public class RemotingInfoUtils {

	// 远程连接配置文件名称
	private static final String HESSIAN_CONFIG = "hessianConfig.ini";
	//日志工程
	private static final Logger LOGGER = LoggerFactory.getLogger(RemotingInfoUtils.class);

	public static RemoteInfo getRemotingInfo() {
		BufferedReader reader = null;
		InputStream in = null;
		String info = null;

		RemoteInfo rInfo = null;
		in = ApplicationContext.class.getClassLoader().getResourceAsStream(HESSIAN_CONFIG);

		if(SessionContext.get(HESSIAN_CONFIG)!=null)
		{
			return (RemoteInfo)SessionContext.get(HESSIAN_CONFIG);
		}else{
			
			try {
				reader = new BufferedReader(new InputStreamReader(in, Charset.defaultCharset().name()));
				//根据sonar(操作数中的分配)修改
				info = reader.readLine();
				while (info != null) {
					info = info.trim();
					if (info.length() < 1) {
						continue;
					}
					if (info.startsWith("[") && info.endsWith("]")) {
						rInfo = new RemoteInfo();
						rInfo.setName(info);

					}
					if(rInfo != null){
						if (info.startsWith("server-port")) {
							info = info.replaceAll(".*\\:", "");
							rInfo.setPort(Integer.valueOf(info));
						}
						if (info.startsWith("service-path")) {
							info = info.replaceAll(".*\\:", "");
							rInfo.setHessianPrefix(info);
						}
						if (info.startsWith("server-host")) {
							info = info.replaceAll(".*\\:", "");
							rInfo.setHostName(info);
						}
						if (info.startsWith("service-waittimeout")) {
							info = info.replaceAll(".*\\:", "");
							rInfo.setWaitTimeout(Integer.valueOf(info) * NumberConstants.NUMBER_1000 );
						}
						if (info.startsWith("connection-waittimeout")) {
							info = info.replaceAll(".*\\:", "");
							rInfo.setConnectionTimeout(Integer.valueOf(info) * NumberConstants.NUMBER_1000);
						}
					}
					info = reader.readLine();
				}
			} catch (FileNotFoundException e) {				
				//e.printStackTrace();
				//根据sonar(不要使用printStackTrace)修改
				LOGGER.error(e.getMessage(), e);
			} catch (NumberFormatException e) {
				//e.printStackTrace();
				//根据sonar(不要使用printStackTrace)修改
				LOGGER.error(e.getMessage(), e);
			} catch (IOException e) {
				//e.printStackTrace();
				//根据sonar(不要使用printStackTrace)修改
				LOGGER.error(e.getMessage(), e);
			} finally {
				IOUtils.close(reader);
				IOUtils.close(in);
			}

			SessionContext.set(HESSIAN_CONFIG, rInfo);
			return rInfo;
		}

	}
	
	
	public static RemoteInfo getRemotingWebInfo(String remoteName) {
		BufferedReader reader = null;
		InputStream in = null;
		String info = null;

		RemoteInfo rInfo = null;
		if(StringUtils.isEmpty(remoteName)){
			return null;
		}
		in = ApplicationContext.class.getClassLoader().getResourceAsStream(HESSIAN_CONFIG);
		
		if(SessionContext.get(HESSIAN_CONFIG)!=null)
		{
			return (RemoteInfo)SessionContext.get(HESSIAN_CONFIG);
		}else{
			
			try {
				reader = new BufferedReader(new InputStreamReader(in, Charset.defaultCharset().name()));
				//根据sonar(操作数中的分配)修改
				info = reader.readLine();
				while (info != null) {
					info = info.trim();
					if (info.length() < 1) {
						continue;
					}
					if (info.startsWith("[") && info.endsWith("]")) {
						if(rInfo != null){
							if(remoteName.equals(rInfo.getName())){
								break;
							}
						}
						
						rInfo = new RemoteInfo();
						rInfo.setName(info);
 
					}
				
					if(rInfo != null){
						if (info.startsWith("web-port")) {
							info = info.replaceAll(".*\\:", "");
							
							rInfo.setPort(Integer.valueOf(info));
							
						}
					
					
						if (info.startsWith("service-path")) {
							info = info.replaceAll(".*\\:", "");
							rInfo.setHessianPrefix(info);
						}
						
						
						if (info.startsWith("web-host")) {
							info = info.replaceAll(".*\\:", "");
							rInfo.setHostName(info);
							
						}
						
						
						if (info.startsWith("service-waittimeout")) {
							info = info.replaceAll(".*\\:", "");
							rInfo.setWaitTimeout(Integer.valueOf(info) * NumberConstants.NUMBER_1000);
						}
						if (info.startsWith("connection-waittimeout")) {
							info = info.replaceAll(".*\\:", "");
							rInfo.setConnectionTimeout(Integer.valueOf(info) * NumberConstants.NUMBER_1000);
						}
					}
					info = reader.readLine();
				}
			} catch (FileNotFoundException e) {				
				//e.printStackTrace();
				//根据sonar(不要使用printStackTrace)修改
				LOGGER.error(e.getMessage(), e);
			} catch (NumberFormatException e) {
				//e.printStackTrace();
				//根据sonar(不要使用printStackTrace)修改
				LOGGER.error(e.getMessage(), e);
			} catch (IOException e) {
				//e.printStackTrace();
				//根据sonar(不要使用printStackTrace)修改
				LOGGER.error(e.getMessage(), e);
			} finally {
				IOUtils.close(reader);
				IOUtils.close(in);
			}
			if(rInfo != null && !remoteName.equals(rInfo.getName())){
				return null;
			}
			SessionContext.set(HESSIAN_CONFIG, rInfo);
			return rInfo;
		}

	}

}

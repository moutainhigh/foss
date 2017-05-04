package com.deppon.foss.module.pickup.common.client.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.java.plugin.Plugin;
import org.java.plugin.PluginLifecycleException;
import org.java.plugin.registry.Extension;
import org.java.plugin.registry.ExtensionPoint;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.client.component.sync.ISyncDataSaver;
import com.deppon.foss.framework.entity.SyncDataRequest;
import com.deppon.foss.framework.entity.SyncDataResponse;
import com.deppon.foss.framework.service.ISyncDataRemoting;
import com.deppon.foss.util.define.FossConstants;

public class SaverUtils {

	

	//log
	private static final Log LOG = LogFactory.getLog(SaverUtils.class);
	
	public static   Map<Class<?>, ISyncDataSaver>  getDataSaver(Plugin plugin) {
		Plugin iniPlugin=null;
		try {
			iniPlugin = plugin.getManager().getPlugin("com.deppon.foss.module.init");
		} catch (PluginLifecycleException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Map<Class<?>, ISyncDataSaver> dataSavers = new HashMap<Class<?>, ISyncDataSaver>();
		
		if(iniPlugin != null){
			ExtensionPoint extensionPoint = iniPlugin.getDescriptor().getExtensionPoint("syncDataSaver");
			List<Extension> extensions = new ArrayList<Extension>(extensionPoint.getConnectedExtensions());
		
				for (Extension extension : extensions) {
					String clsString = extension.getParameter("saver-class").valueAsString();
					ClassLoader classLoader = plugin.getManager().getPluginClassLoader(
							extension.getDeclaringPluginDescriptor());
					try {
		
						Class cls = classLoader.loadClass(clsString);
						try {
							ISyncDataSaver dataSaver = (ISyncDataSaver) cls
									.newInstance();
							Class saveType = dataSaver.getSaveType();	
							dataSavers.put(saveType, dataSaver);
		
						} catch (InstantiationException e) {
							LOG.error("InstantiationException", e);
						} catch (IllegalAccessException e) {
							LOG.error("IllegalAccessException", e);
						}
		
					} catch (ClassNotFoundException e) {
						LOG.error("ClassNotFoundException", e);
					}
		
				}
		}
		return  dataSavers;
	}

	
	
	public static void syncData( ISyncDataRemoting service, Map<Class<?>, ISyncDataSaver> syncDataSaver,List<SyncDataRequest> requestsList) {
		
		for (SyncDataRequest request : requestsList) {
			Date date = null;
			String regionID = null;

			ISyncDataSaver dataSaver = syncDataSaver.get(request.getSyncKey());
			LOG.warn("现在在下载数据的数据对象："
					+request.getSyncKey().getName());
			
			SyncDataRequest currentRequest = request;
			SyncDataResponse response = null;
				
			response = service.processSyncData(currentRequest);
			List list = new ArrayList();
			
			if(FossConstants.YES.equals(request.getPagination())){//需要分页
				List subList =  response.getFromData();
				
				if(subList==null || subList.size()==0){
					LOG.info("没有下载到任何分页数据"+request.getSyncKey().getName() 
							+" 时间" + request.getFromDate()  );
				}
				
				while(subList!=null && subList.size()>0 ){
					LOG.info("下载数据"+request.getSyncKey().getName() 
							+" 时间" + request.getFromDate() + " total: "+ subList.size()  );
					date = dataSaver.saveData(subList);
					LOG.info("date" + date +" region id" + dataSaver.getRegionID());
					regionID = dataSaver.getRegionID();
					if(date!=null){
						// 同步数据请求
						SyncDataRequest saveRequest = new SyncDataRequest();
						saveRequest.setSyncKey(response.getSyncKey());
						
						saveRequest.setOrgCode(request.getOrgCode());
						saveRequest.setFromDate(date);
						saveRequest.setNeedOrgSearch(request.getNeedOrgSearch());
						saveRequest.setRegionID(regionID);
						saveRequest.setList(request.getList());
						syncDataSaver.get(request.getSyncKey()).updateBaseLine(
								saveRequest);
					}
					
					
					currentRequest.setSyncPage(currentRequest.getSyncPage()+1);
					response = service.processSyncData(currentRequest);
					subList =  response.getFromData();
				}
				
			}else{//不需要分页
				list= response.getFromData();

			}
			
		
			
			if(list!=null && list.size()>0){
				
				LOG.info("下载数据"+request.getSyncKey().getName() 
						+" 时间" + request.getFromDate() + " total: "+ list.size()  );
				if(dataSaver!=null){
					
					Object fistObject = list.get(0);
					String isOrgIncremet = null;
					if(fistObject instanceof Map ){
						
						Map map =(Map)fistObject;
						isOrgIncremet =(String) map.get("isOrgIncremet");
					}
					
					if(FossConstants.YES.equals(isOrgIncremet)){
						Map map =(Map)fistObject;
						List list2 =(List)map.get("list");
						dataSaver.saveData(list2);
						
						List versionList =(List) map.get("verionList");
						
						for (Iterator iterator = versionList.iterator(); iterator.hasNext();) {
							Map version = (Map) iterator.next();
							String orgCode = (String)version.get("orgCode");
							Date date2 = (Date) version.get("version");
							SyncDataRequest saveRequest = new SyncDataRequest();
							saveRequest.setSyncKey(response.getSyncKey());
							
							saveRequest.setOrgCode(orgCode);
							saveRequest.setFromDate(date2);
							saveRequest.setNeedOrgSearch(request.getNeedOrgSearch());
							saveRequest.setRegionID(regionID);
							saveRequest.setList(request.getList());
							syncDataSaver.get(request.getSyncKey()).updateBaseLineByOrgCode(
									saveRequest);
						}
					}else{
						if(list!=null && list.size() > NumberConstants.NUMBER_1000){
							List list2 = new ArrayList();
							int k=0;
							for(; k<list.size(); k++){
								
								Object object = list.get(k);
								list2.add(object);
								if(k % NumberConstants.NUMBER_1000 == 0 && k > 0){//每1000条commmit一次
									//normal channel
									date = dataSaver.saveData(list2);
									LOG.info("date" + date +" region id" + dataSaver.getRegionID());
									regionID = dataSaver.getRegionID();
									if(date!=null){
										// 同步数据请求
										SyncDataRequest saveRequest = new SyncDataRequest();
										saveRequest.setSyncKey(response.getSyncKey());
										
										saveRequest.setOrgCode(request.getOrgCode());
										saveRequest.setFromDate(date);
										saveRequest.setNeedOrgSearch(request.getNeedOrgSearch());
										saveRequest.setRegionID(regionID);
										saveRequest.setList(request.getList());
										syncDataSaver.get(request.getSyncKey()).updateBaseLine(
												saveRequest);
									}
									list2 = new ArrayList();
								}
							}
							
							date = dataSaver.saveData(list2);
							LOG.info("date" + date +" region id" + dataSaver.getRegionID());
							regionID = dataSaver.getRegionID();

							if(date!=null){
								// 同步数据请求
								SyncDataRequest saveRequest = new SyncDataRequest();
								saveRequest.setSyncKey(response.getSyncKey());
								
								saveRequest.setOrgCode(request.getOrgCode());
								saveRequest.setFromDate(date);
								saveRequest.setNeedOrgSearch(request.getNeedOrgSearch());
								saveRequest.setRegionID(regionID);
								saveRequest.setList(request.getList());
								syncDataSaver.get(request.getSyncKey()).updateBaseLine(
										saveRequest);
							}
						}else{
							//normal channel
							date = dataSaver.saveData(list);
							LOG.info("date" + date +" region id" + dataSaver.getRegionID());
							regionID = dataSaver.getRegionID();
							if(date!=null){
								// 同步数据请求
								SyncDataRequest saveRequest = new SyncDataRequest();
								saveRequest.setSyncKey(response.getSyncKey());
								
								saveRequest.setOrgCode(request.getOrgCode());
								saveRequest.setFromDate(date);
								saveRequest.setNeedOrgSearch(request.getNeedOrgSearch());
								saveRequest.setRegionID(regionID);
								saveRequest.setList(request.getList());
								syncDataSaver.get(request.getSyncKey()).updateBaseLine(
										saveRequest);
							}
						}
					}
				}
			}else{
				
				if(!FossConstants.YES.equals(request.getPagination())){
					LOG.info("没有下载到任何数据"+request.getSyncKey().getName() 
						+" 时间" + request.getFromDate()  );
				}
			}	
			
			
			
		}
	}

}

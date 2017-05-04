/**
 * @Title: LogMonitorDao.java
 * @Package com.deppon.foss.module.base.baseinfo.server.dao.impl
 * @Description: TODO
 * Copyright: Copyright (c) 2013 
 * Company:德邦物流
 * 
 * @author Comsys-129903-阮正华
 * @date 2013-3-23 上午9:44:09
 * @version V1.0
 */

package com.deppon.foss.module.base.baseinfo.server.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.server.components.dataaccess.mongodb.MongoDaoSupport;
import com.deppon.foss.framework.server.components.logger.entity.LogInfo;
import com.deppon.foss.module.base.baseinfo.api.server.dao.ILogsMonitorDao;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

/**
 * @ClassName: LogMonitorDao
 * @Description: 日志监控Dao层
 * @author Comsys-129903-阮正华
 * @date 2013-3-23 上午9:44:09
 *
 */

public class LogMonitorDao extends MongoDaoSupport implements ILogsMonitorDao {
	
	private DBCollection collection;
	
	@Override
	public List<LogInfo> queryLogsMonitor(LogInfo logMonitorEntity, int limit, int start) {
		init();
		List<LogInfo> logInfoList = new ArrayList<LogInfo>();
		String date = logMonitorEntity.getDate().substring(0, NumberConstants.NUMBER_10);
		BasicDBObject query1 = new BasicDBObject();
		query1.put("$gte",date+" 00:00:00.000");
		query1.put("$lte",date+" 23:59:59.999");
		BasicDBObject query = new BasicDBObject();
		query.put("date", query1);//设置日期条件
		setCondtion(query,logMonitorEntity);//设置其他条件
		DBCursor cursor = collection.find(query).limit(limit).skip(start);
		while(cursor.hasNext()){
			DBObject object = cursor.next();
			LogInfo logInfo = new LogInfo();
			logInfo.setDate(object.get("date").toString());
			logInfo.setRequestId(object.get("requestId")==null?"":object.get("requestId").toString());
			logInfo.setAppName(object.get("appName")==null?"":object.get("appName").toString());
			logInfo.setModuleName(object.get("moduleName")==null?"":object.get("moduleName").toString());
			logInfo.setUserName(object.get("userName")==null?"":object.get("userName").toString());
			logInfo.setUrl(object.get("url")==null?"":object.get("url").toString());
			logInfo.setType(object.get("type")==null?"":object.get("type").toString());
			logInfo.setClazz(object.get("clazz")==null?"":object.get("clazz").toString());
			logInfo.setMethod(object.get("method")==null?"":object.get("method").toString());
			logInfo.setIp(object.get("ip")==null?"":object.get("ip").toString());
			logInfo.setVersion(object.get("version")==null?"":object.get("version").toString());
			logInfo.setMacAddress(object.get("macAddress")==null?"":object.get("macAddress").toString());
			logInfo.setMessage(object.get("message")==null?"":object.get("message").toString());
			logInfo.setAction(object.get("action")==null?"":object.get("action").toString());
			logInfo.setArgs(object.get("args")==null?"":object.get("args").toString());
			logInfo.setResult(object.get("result")==null?"":object.get("result").toString());
			logInfoList.add(logInfo);
		}
		return logInfoList;
	}
	/**
	 * 
	  * setCondtion(设置条件)
	  *
	  * @Title: setCondtion
	  * @Description: TODO
	  * @param @param query
	  * @param @param logMonitorEntity    
	  * @return void    返回类型
	  * @throws
	 */
	private void setCondtion(BasicDBObject query,LogInfo logMonitorEntity){
		//属性值添加“/”表示模糊匹配
		//313353 sonar优化
		this.sonarSplit(query, logMonitorEntity);
		
		if(logMonitorEntity.getMethod()!=null && !logMonitorEntity.getMethod().equals("")){
			query.put("method", getLikeStr(logMonitorEntity.getMethod()));
		}
		if(logMonitorEntity.getIp()!=null && !logMonitorEntity.getIp().equals("")){
			query.put("ip", getLikeStr(logMonitorEntity.getIp()));
		}
		if(logMonitorEntity.getVersion()!=null && !logMonitorEntity.getVersion().equals("")){
			query.put("version", getLikeStr(logMonitorEntity.getVersion()));
		}
		if(logMonitorEntity.getMacAddress()!=null && !logMonitorEntity.getMacAddress().equals("")){
			query.put("macAddress", getLikeStr(logMonitorEntity.getMacAddress()));
		}
		if(logMonitorEntity.getMessage()!=null && !logMonitorEntity.getMessage().equals("")){
			query.put("message", getLikeStr(logMonitorEntity.getMessage()));
		}
		if(logMonitorEntity.getAction()!=null && !logMonitorEntity.getAction().equals("")){
			query.put("action", getLikeStr(logMonitorEntity.getAction()));
		}
	}
	
	/**
	 * sonar优化拆分
	 * 
	 * @author 313353
	 */
	private void sonarSplit(BasicDBObject query,LogInfo logMonitorEntity) {
		if(logMonitorEntity.getRequestId()!=null && !logMonitorEntity.getRequestId().equals("")){
			query.put("requestId", getLikeStr(logMonitorEntity.getRequestId()));
		}
		if(logMonitorEntity.getAppName()!=null && !logMonitorEntity.getAppName().equals("")){
			query.put("appName", getLikeStr(logMonitorEntity.getAppName()));
		}
		if(logMonitorEntity.getModuleName()!=null && !logMonitorEntity.getModuleName().equals("")){
			query.put("moduleName", getLikeStr(logMonitorEntity.getModuleName()));
		}
		if(logMonitorEntity.getUserName()!=null && !logMonitorEntity.getUserName().equals("")){
			query.put("userName", getLikeStr(logMonitorEntity.getUserName()));
		}
		if(logMonitorEntity.getUrl()!=null && !logMonitorEntity.getUrl().equals("")){
			query.put("url", getLikeStr(logMonitorEntity.getUrl()));
		}
		if(logMonitorEntity.getType()!=null && !logMonitorEntity.getType().equals("")){
			query.put("type", getLikeStr(logMonitorEntity.getType()));
		}
		if(logMonitorEntity.getClazz()!=null && !logMonitorEntity.getClazz().equals("")){
			query.put("clazz", getLikeStr(logMonitorEntity.getClazz()));
		}
	}
	
	/**
	 * 
	  * getLikeStr(模糊匹配)
	  *
	  * @Title: getLikeStr
	  * @Description: TODO
	  * @param @param findStr
	  * @param @return    
	  * @return BasicDBObject    返回类型
	  * @throws
	 */
	private BasicDBObject getLikeStr(String findStr) {
		Pattern pattern = Pattern.compile("^.*" + findStr + ".*$", Pattern.MULTILINE);
		return new BasicDBObject("$regex", pattern);
	}
	/**
	  * @Title: init
	  * @Description: 获取集合(表)
	  * @return void    返回类型
	  * @throws
	 */
	public void init(){
		collection = this.getDbCollection(LogInfo.class.getSimpleName());
	}
	
	@Override
	public Long queryRecordCount(LogInfo logMonitorEntity) {
		String date = logMonitorEntity.getDate().substring(0, NumberConstants.NUMBER_10);
		BasicDBObject query1 = new BasicDBObject();
		query1.put("$gte",date+" 00:00:00.000");
		query1.put("$lte",date+" 23:59:59.999");
		BasicDBObject query = new BasicDBObject();
		query.put("date", query1);
		setCondtion(query,logMonitorEntity);//设置其他条件
		return collection.count(query);
	}
	
}

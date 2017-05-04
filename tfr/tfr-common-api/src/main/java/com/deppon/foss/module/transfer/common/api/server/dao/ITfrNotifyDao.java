package com.deppon.foss.module.transfer.common.api.server.dao;
import java.util.List;
import java.util.Map;

import com.deppon.foss.module.transfer.common.api.shared.domain.TfrNotifyConfig;
import com.deppon.foss.module.transfer.common.api.shared.domain.TfrNotifyEntity;


/**
* @description foss同步数据到快递系统异步通知任务dao
* @version 1.0
* @author 283250-foss-liuyi
* @update 2016年4月26日 上午9:13:13
*/
public interface ITfrNotifyDao {
	
	
	
	/**
	* @description 增加一个初始的通知任务
	* @param e
	* @return
	* @version 1.0
	* @author 283250-foss-liuyi
	* @update 2016年4月26日 上午9:11:31
	*/
	int addTfrNotifyEntity(TfrNotifyEntity e);
	
	
	
	
	
	/**
	* @description 增加一个已经开始的通知任务
	* @param e
	* @return
	* @version 1.0
	* @author 283250-foss-liuyi
	* @update 2016年4月26日 上午10:15:58
	*/
	int addTfrNotifyBeginEntity(TfrNotifyEntity e);
	
	/**
	* @description 批量查询待通知任务
	* @param e
	* @return
	* @version 1.0
	* @author 283250-foss-liuyi
	* @update 2016年4月26日 上午9:11:34
	*/
	List<TfrNotifyEntity> selectTfrNotifyList(Map<String,Object> map);
	
	
	
	/**
	* @description 更新处理中的通知任务
	* @param id
	* @return
	* @version 1.0
	* @author 283250-foss-liuyi
	* @update 2016年4月26日 上午9:11:36
	*/
	int updateTfrNotifyIng(List<String> ids);
	
	
	
	/**
	* @description 通知成功更新
	* @param id
	* @return
	* @version 1.0
	* @author 283250-foss-liuyi
	* @update 2016年4月26日 上午9:12:49
	*/
	int updateTfrNotifySuccess(String id);
	
	
	/**
	* @description 通知失败更新
	* @param id
	* @return
	* @version 1.0
	* @author 283250-foss-liuyi
	* @update 2016年4月26日 上午9:12:49
	*/
	int updateTfrNotifyFail(TfrNotifyEntity e);

	TfrNotifyConfig queryNotifyConfig(String className);
	
	int updateTfrNotifyListJobId(Map<String, Object> map);
}

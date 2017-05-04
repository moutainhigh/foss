package com.deppon.foss.module.transfer.common.api.server.service;

import java.util.List;
import java.util.Map;

import com.deppon.foss.module.transfer.common.api.shared.domain.TfrNotifyConfig;
import com.deppon.foss.module.transfer.common.api.shared.domain.TfrNotifyEntity;


/**
* @description 异步方式同步悟空数据任务接口
* @version 1.0
* @author 283250-foss-liuyi
* @update 2016年4月27日 下午7:43:26
*/
public interface ITfrNotifyService {
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

	
	/**
	* @description 查询异步通知任务配置信息
	* @param className
	* @return
	* @version 1.0
	* @author 283250-foss-liuyi
	* @update 2016年5月6日 下午3:21:12
	*/
	TfrNotifyConfig queryNotifyConfig(String className);
	
	/**
	* @description 更新处理中的JobId
	* @param id
	* @return
	* @version 1.0
	* @author 283250-foss-liuyi
	* @update 2016年4月26日 上午9:11:36
	*/
	public int updateTfrNotifyListJobId(Map<String, Object> param);
}

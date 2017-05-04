package com.deppon.foss.module.settlement.common.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.shared.domain.OperatingLogEntity;


/**
 * 操作日志
 * @author 000123-foss-huangxiaobo
 * @date 2012-11-8 上午10:50:53
 */
public interface IOperatingLogService  extends IService {
	
	/**
	 * 新加操作日志
	 * @param item 日志内容
	 *  只需填入 operateBillNo，operateBillType，operateType 三个数据类型
	 */
	void addOperatingLog(OperatingLogEntity item,CurrentInfo currentInfo);
	
	
	/**
	 * 根据操作单号进行查询
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-11-8 上午11:23:20
	 */
	List<OperatingLogEntity> queryByOperateBillNo(String billNo);
	
	/**
	 *  新增多条操作日志信息
	 *  
	 * @author 099995-foss-wujiangtao
	 * @date 2013-1-25 下午2:27:28
	 * @param list
	 * @return
	 */
	void addBatchOperatingLog(List<OperatingLogEntity> list);
	
	

}

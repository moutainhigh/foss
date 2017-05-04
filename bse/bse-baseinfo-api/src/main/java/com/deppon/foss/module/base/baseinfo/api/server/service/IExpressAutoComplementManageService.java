package com.deppon.foss.module.base.baseinfo.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressAutoComplementManageEntity;

/**
 * 
 * @author 218392 zhangyongxue
 * @date 2015-05-19 11:02:38
 *
 */
public interface IExpressAutoComplementManageService extends IService{
	/**
	 *  <p> 新增自动补码管理信息 </p>
	 */
	int addExpressAutoComplementManage(ExpressAutoComplementManageEntity expressAutoComplementManageEntity);
	
	/**
	 *  修改快递自动补码管理信息
	 */
	int updateExpressAutoComplementManage(ExpressAutoComplementManageEntity entity);
	
	
	/**
	 * <p>根据传入的对象查询自动补码管理信息</p>
	 */
	List<ExpressAutoComplementManageEntity> queryExpressAutoComplementManage(
			ExpressAutoComplementManageEntity entity,int limit, int start);
	
	/**
	 * <p>统计总记录数</p>
	 */
	Long queryCount(ExpressAutoComplementManageEntity entity);
	
	/**
	 * <p>统计总记录数：根据城市编码查询数据库中是否有该条记录，供前台插入城市判别城市唯一的方法</p>
	 */
	Long queryCountbyCityCode(ExpressAutoComplementManageEntity entity);
	
	/**
	 * <p> 开启快递自动补码管理信息 </p>
	 */
	int openExpressAutoComplementManage(List<String> idList);
	
	/**
	 * <p> 关闭快递自动补码管理信息 </p>
	 */
	int closeExpressAutoComplementManage(List<String> idList);

	/**
	 * 静默操作 
	 * @author 232607 
	 * @date 2015-7-23 下午7:34:31
	 * @param idList
	 * @return
	 * @see
	 */
	int silentExpressAutoComplementManage(List<String> idList);

	/**
	 * 给中转的接口，传入城市编码，返回城市状态，1为关闭，2为静默，3位开启，如果没有该城市记录、或者该城市状态为空，返回1。
	 * @author 232607 
	 * @date 2015-7-23 下午8:17:51
	 * @param cityCode
	 * @return
	 * @see
	 */
	String queryListCityCodeByCodeStatus(String cityCode);

}

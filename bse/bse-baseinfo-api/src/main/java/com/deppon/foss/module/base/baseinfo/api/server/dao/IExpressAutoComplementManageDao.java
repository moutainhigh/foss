package com.deppon.foss.module.base.baseinfo.api.server.dao;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressAutoComplementManageEntity;

/**
 * 
 * @author 218392 zhangyongxue
 * @date 2015-05-18
 * 快递自动补码Dao
 *
 */
public interface IExpressAutoComplementManageDao{
	/**
	 *  <p> 新增快递自动补码管理信息 </p>
	 */
	int addExpressAutoComplementManageDao(ExpressAutoComplementManageEntity expressAutoComplementManageEntity);
	
	/**
	 * 修改快递自动补码管理信息
	 */
	int updateExpressAutoComplementManage(ExpressAutoComplementManageEntity entity);
	
	/**
	 * <p>查询快递自动补码管理信息</P>
	 */
	List<ExpressAutoComplementManageEntity> queryAllExpressAutoComplementManage(
			ExpressAutoComplementManageEntity entity,int limit, int start);
	
	/**
	 * 根据城市Code和开启关闭状态，查询城市Code
	 * @param entity
	 * @return
	 */
	String queryListCityCodeByCodeStatus(ExpressAutoComplementManageEntity entity);
	
	/**
	 * <p>统计总记录数</p>
	 */
	Long queryCount(ExpressAutoComplementManageEntity entity);
	
	/**
	 * 开启快递自动补码管理
	 */
	int openExpressAutoComplementManage(List<String> idList);
	
	/**
	 * 关闭快递自动补码管理
	 */
	int closeExpressAutoComplementManage(List<String> idList);
	
	/**
	 * <p>统计总记录数根据城市code</p>
	 */
	Long queryCountByCityCode(ExpressAutoComplementManageEntity entity);

	/**
	 * 静默操作
	 * @author 232607 
	 * @date 2015-7-23 下午7:44:41
	 * @param idList
	 * @return
	 * @see
	 */
	int silentExpressAutoComplementManage(List<String> idList);
	
	
	
}

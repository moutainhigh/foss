/**
 * 
 */
package com.deppon.foss.module.transfer.platform.api.server.dao;

import java.util.Date;
import java.util.List;

import com.deppon.foss.module.transfer.platform.api.shared.domain.PDAOnlineUsingEntity;
import com.deppon.foss.module.transfer.platform.api.shared.dto.PDAOnlineUsingDto;
import com.deppon.foss.module.transfer.platform.api.shared.dto.TransferCenterDto;

/**
 *@author 105795
 *@see 统计PDA在线情况
 *@date 2015-01-26
 */
public interface IQueryPDAonLineDao  {
	
	/**
	 *com.deppon.foss.module.transfer.platform.api.server.dao  
	 *@desc 统计-pda某天的登入退出情况于 及 查询（理货员、电叉司机、电叉司机组长）所列岗位所属部门、工号、姓名、岗位
	 *@param date
	 *@return int
	 *@author 105795
	 *@date 2015年1月26日下午3:44:17 
	 */
	void countPDAEmpToPro(Date date);
	
	/**
	 *com.deppon.foss.module.transfer.platform.api.server.dao  
	 *@desc 统计在线PDA日高峰，月高峰
	 *@param operateOrigCode 经营本部code
	 *@param OperateOrigName 经营本部名称
	 *@param outfiledCode 外场code
	 *@param outfiledName 外场名称
	 *@param date需要统计PDA在线情况的日期
	 *@return int
	 *@author 105795
	 *@date 2015年1月26日下午3:52:27 
	 */
	void calculatePDAOnline(String operateOrigCode,String operateOrigName,String outfiledCode,String outfiledName,Date date);
	
	/**
	 *com.deppon.foss.module.transfer.platform.api.server.dao  
	 *@desc 查询全国所有外场剔除掉分部
	 *@param 
	 *@return List<TransferCenterDto>
	 *@author 105795
	 *@date 2015年1月28日下午5:06:39 
	 */
	List<TransferCenterDto> queryAllTransferCenter();

	/**
	 *com.deppon.foss.module.transfer.platform.api.server.dao  
	 *@desc 根据转入条件查询pda的使用情况
	 *@param pdaOnlineUsingEntity 其中统计日期不能为空
	 *@return List<PDAOnlineUsingEntity>
	 *@author 105795
	 *@date 2015年1月31日下午4:14:03 
	 */
	List<PDAOnlineUsingEntity> queryPDAOnlineUsing(PDAOnlineUsingEntity pdaOnlineUsingEntity);
	
	
	/**
	 *com.deppon.foss.module.transfer.platform.api.server.dao  
	 *@desc 查询理货员 叉车司机所有人员使用PDA的情况  
	 *@param pdaOnlineUsingEntity 
	 *@return List<PDAOnlineUsingDto>
	 *@author 105795
	 *@date 2015年2月2日下午2:40:54 
	 */
	List<PDAOnlineUsingDto> queryAllCategoryPDAUsing(PDAOnlineUsingEntity pdaOnlineUsingEntity);
	
	
	/**
	 *com.deppon.foss.module.transfer.platform.api.server.dao  
	 *@desc 查询理货员 叉车司机所有人员使用PDA的情况  分页
	 *@param pdaOnlineUsingEntity 
	 *@return List<PDAOnlineUsingDto>
	 *@author 105795
	 *@date 2015年2月2日下午2:40:54 
	 */
	List<PDAOnlineUsingDto> queryAllCategoryPDAUsing(PDAOnlineUsingEntity pdaOnlineUsingEntity,int start, int limit);
	

	/**
	 *com.deppon.foss.module.transfer.platform.api.server.dao  
	 *@desc 查询某一个外场下面的从月初到查询日期的所有pda使用情况
	 *@param map
	 *@return List<PDAOnlineUsingDto>
	 *@author 105795
	 *@date 2015年2月3日上午9:51:01 
	 */
	List<PDAOnlineUsingDto> queryAllCategoryPDAUsingDetail(String transferCenterCode,Date startStaDate,Date endStaDate,int start, int limit);

	/**
	 *com.deppon.foss.module.transfer.platform.api.server.dao  
	 *@desc 根据部门code查询部门信息 
	 *@param code
	 *@return TransferCenterDto
	 *@author 105795
	 *@date 2015年2月4日上午11:18:33 
	 */
	TransferCenterDto queryOrgByOrgCode(String code);
	
	
}

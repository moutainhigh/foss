/**
 * 
 */
package com.deppon.foss.module.transfer.platform.server.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.platform.api.server.dao.IQueryPDAonLineDao;
import com.deppon.foss.module.transfer.platform.api.shared.domain.PDAOnlineUsingEntity;
import com.deppon.foss.module.transfer.platform.api.shared.dto.PDAOnlineUsingDto;
import com.deppon.foss.module.transfer.platform.api.shared.dto.TransferCenterDto;
import com.deppon.foss.util.CollectionUtils;

/**
 * @author 105795
 * @see 统计PDA在线情况
 * @date 2015-01-26
 */
public class QueryPDAonLineDao extends iBatis3DaoImpl implements
		IQueryPDAonLineDao {

	private static final String NAMESPACE = "pda-online.";

	/**
	 * com.deppon.foss.module.transfer.platform.api.server.dao
	 * 
	 * @desc 统计-pda某天的登入退出情况于 及 查询（理货员、电叉司机、电叉司机组长）所列岗位所属部门、工号、姓名、岗位
	 * @param date
	 * @return int
	 * @author 105795
	 * @date 2015年1月26日下午3:44:17
	 */
	public void countPDAEmpToPro(Date date) {
		Map<String, Date> dataMap = new HashMap<String, Date>();
		dataMap.put("date", date);
		this.getSqlSession().selectOne(NAMESPACE + "countPDAEmpToPro",dataMap);
	}

	/**
	 * com.deppon.foss.module.transfer.platform.api.server.dao
	 * 
	 * @param operateOrigCode
	 *            经营本部code
	 * @param OperateOrigName
	 *            经营本部名称
	 * @param outfiledCode
	 *            外场code
	 * @param outfiledName
	 *            外场名称
	 * @param date需要统计PDA在线情况的日期
	 * @return int
	 * @author 105795
	 * @date 2015年1月26日下午3:52:27
	 */
	public void calculatePDAOnline(String operateOrigCode,
			String operateOrigName, String outfiledCode, String outfiledName,
			Date date) {
		Map<String, Object> paramsMap = new HashMap<String, Object>();
			paramsMap.put("operateOrigCode", operateOrigCode);
			paramsMap.put("OperateOrigName", operateOrigName);
			paramsMap.put("outfiledCode", outfiledCode);
			paramsMap.put("outfiledName", outfiledName);
			paramsMap.put("date", date);
		this.getSqlSession().selectOne(NAMESPACE + "calculatePDAOnline",paramsMap);
		
	}

	/**
	 * com.deppon.foss.module.transfer.platform.api.server.dao
	 * 
	 * @desc 查询全国所有外场剔除掉分部
	 * @param
	 * @return List<TransferCenterDto>
	 * @author 105795
	 * @date 2015年1月28日下午5:06:39
	 */
	@SuppressWarnings("unchecked")
	public List<TransferCenterDto> queryAllTransferCenter() {
		return this.getSqlSession().selectList(
				NAMESPACE + "queryAllTransferCenter");
	}
	
	/**
	 *com.deppon.foss.module.transfer.platform.api.server.dao  
	 *@desc 根据转入条件查询pda的使用情况
	 *@param pdaOnlineUsingEntity 其中统计日期不能为空
	 *@return List<PDAOnlineUsingEntity>
	 *@author 105795
	 *@date 2015年1月31日下午4:14:03 
	 */
	@SuppressWarnings("unchecked")
	public List<PDAOnlineUsingEntity> queryPDAOnlineUsing(PDAOnlineUsingEntity pdaOnlineUsingEntity)
	{
		
		return this.getSqlSession().selectList(NAMESPACE+"queryPDAOnlineUsing", pdaOnlineUsingEntity);
		
		
	}
	
	/**
	 *com.deppon.foss.module.transfer.platform.api.server.dao  
	 *@desc 查询理货员 叉车司机所有人员使用PDA的情况  
	 *@param pdaOnlineUsingEntity 
	 *@return List<PDAOnlineUsingDto>
	 *@author 105795
	 *@date 2015年2月2日下午2:40:54 
	 */
	@SuppressWarnings("unchecked")
	public List<PDAOnlineUsingDto> queryAllCategoryPDAUsing(PDAOnlineUsingEntity pdaOnlineUsingEntity)
	{
		return this.getSqlSession().selectList(NAMESPACE+"queryAllCategoryPDAUsing", pdaOnlineUsingEntity);
	}
	
	/**
	 *com.deppon.foss.module.transfer.platform.api.server.dao  
	 *@desc 查询理货员 叉车司机所有人员使用PDA的情况  
	 *@param pdaOnlineUsingEntity 
	 *@return List<PDAOnlineUsingDto>
	 *@author 105795
	 *@date 2015年2月2日下午2:40:54 
	 */
	@SuppressWarnings("unchecked")
	public List<PDAOnlineUsingDto> queryAllCategoryPDAUsing(PDAOnlineUsingEntity pdaOnlineUsingEntity,int start, int limit)
	{
		RowBounds rowBounds = new RowBounds(start, limit);
		return this.getSqlSession().selectList(NAMESPACE+"queryAllCategoryPDAUsing", pdaOnlineUsingEntity,rowBounds);
	}
	
	/**
	 *com.deppon.foss.module.transfer.platform.api.server.dao  
	 *@desc 查询某一个外场下面的从月初到查询日期的所有pda使用情况
	 *@param map
	 *@return List<PDAOnlineUsingDto>
	 *@author 105795
	 *@date 2015年2月3日上午9:51:01 
	 */
	@SuppressWarnings("unchecked")
	public List<PDAOnlineUsingDto> queryAllCategoryPDAUsingDetail(String transferCenterCode,Date startStaDate,Date endStaDate,int start, int limit)
	{
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("transferCenterCode", transferCenterCode);
		paramsMap.put("staDateBegin", startStaDate);
		paramsMap.put("staDateEnd", endStaDate);
        if(start==0&&limit==0)
        {
        	//不走分页
        	return this.getSqlSession().selectList(NAMESPACE+"queryAllCategoryPDAUsingDetail", paramsMap);
        	
        }else{
        	//走分页
        	RowBounds rowBounds = new RowBounds(start, limit);
    		return this.getSqlSession().selectList(NAMESPACE+"queryAllCategoryPDAUsingDetail", paramsMap,rowBounds);
        	
        } 
		  
	}
	
	/**
	 *com.deppon.foss.module.transfer.platform.api.server.dao  
	 *@desc 根据部门code查询部门信息 
	 *@param code
	 *@return TransferCenterDto
	 *@author 105795
	 *@date 2015年2月4日上午11:18:33 
	 */
	public TransferCenterDto queryOrgByOrgCode(String code)
	{
		@SuppressWarnings("unchecked")
		List<TransferCenterDto> resultList=this.getSqlSession().selectList(NAMESPACE+"queryOrgByOrgCode", code);
		if(CollectionUtils.isNotEmpty(resultList)&&resultList.size()>0)
		{
			return resultList.get(0);
		}else{
			return null;
			
		}
		
	}
	
}

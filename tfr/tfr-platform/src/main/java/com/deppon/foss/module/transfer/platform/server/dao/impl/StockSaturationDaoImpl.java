package com.deppon.foss.module.transfer.platform.server.dao.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.platform.api.server.dao.IStockSaturationDao;
import com.deppon.foss.module.transfer.platform.api.shared.domain.PersonForTransferEntity;
import com.deppon.foss.module.transfer.platform.api.shared.domain.StockSaturationEntity;


/**
* @description 仓库饱和度的Dao
* @version 1.0
* @author 14022-foss-songjie
* @update 2014年3月22日 下午2:21:16
*/
public class StockSaturationDaoImpl extends iBatis3DaoImpl implements IStockSaturationDao {
	/** mapper 文件命名空间*/
	public static final String NAME_SPACE = "foss.platform.stock.saturation.";
	/**
	* @description 查询仓库饱和度
	* @param map
	* @param start
	* @param limit
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月21日 下午3:31:53
	*/
	@SuppressWarnings("unchecked")
	@Override
	public List<StockSaturationEntity> queryStockSaturationList(
			Map<String, String> map, int start, int limit) {
		if(start<=0 && limit <=0){
			return this.getSqlSession().selectList(NAME_SPACE+"queryStockSaturationList",map);
		}else{
			RowBounds rowBounds = new RowBounds(start, limit);
			return this.getSqlSession().selectList(NAME_SPACE+"queryStockSaturationList", map, rowBounds);
		}
	}
	
	/**
	* @description 查询仓库饱和度的总记录数
	* @param map
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月21日 下午3:33:12
	*/
	@Override
	public int queryStockSaturationListCount(Map<String, String> map) {
		return (Integer) this.getSqlSession().selectOne(NAME_SPACE+"queryStockSaturationListCount", map);
	}
	
	/**
	* @description 日饱和度
	* @param map
	* @param start
	* @param limit
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月22日 下午2:33:08
	*/
	@SuppressWarnings("unchecked")
	@Override
	public List<StockSaturationEntity> queryStockSaturationDayList(
			Map<String, String> map) {
		return this.getSqlSession().selectList(NAME_SPACE+"queryStockSaturationDayList",map);
	}


	/**
	* @description 月仓库饱和度
	* @param map
	* @param start
	* @param limit
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年4月3日 上午11:19:47
	*/
	@SuppressWarnings("unchecked")
	@Override
	public List<StockSaturationEntity> queryStockSaturationMonthList(
			Map<String, String> map) {
		return this.getSqlSession().selectList(NAME_SPACE+"queryStockSaturationMonthList",map);
	}
	
	
	/**
	* @description 根据部门code和理论统计时间查询
	* @param map
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月27日 下午3:48:49
	*/
	@Override
	public StockSaturationEntity queryStockSaturationByOrgCodeAndTime(
			Map<String, String> map) {
		return (StockSaturationEntity) this.getSqlSession().selectOne(NAME_SPACE+"queryStockSaturationByOrgCodeAndTime", map);
	}

	/**
	* @description 根据主键更新
	* @param stockSaturationEntity
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月27日 下午3:36:25
	*/
	@Override
	public int updateByPrimaryKey(StockSaturationEntity stockSaturationEntity) {
		return this.getSqlSession().update(NAME_SPACE+"updateByPrimaryKey", stockSaturationEntity);
	}

	/**
	* @description 单条插入
	* @param stockSaturationEntity
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年5月6日 下午4:27:10
	*/
	@Override
	public void insertStockSaturation(
			StockSaturationEntity stockSaturationEntity) {
		this.getSqlSession().insert(NAME_SPACE+"insertStockSaturation", stockSaturationEntity);
	}

	/**
	* @description 将统计数据批量插入
	* @param list
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月22日 下午2:12:33
	*/
	@Override
	public void batchInsertStockSaturation(List<StockSaturationEntity> list) {
		this.getSqlSession().insert(NAME_SPACE+"batchInsertStockSaturation", list);
	}
	
	/**
	* @description 定时任务的查询语句
	* @param map  key:orgCode;queryDateA;queryDateB
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月22日 下午2:20:57
	*/
	@SuppressWarnings("unchecked")
	@Override
	public List<StockSaturationEntity> stockSaturationJobQuery(
			Map<String, String> map) {
		return this.getSqlSession().selectList(NAME_SPACE+"stockSaturationJobQuery", map);
	}

	
	/**
	* @description 外场对应的负责人
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.platform.api.server.dao.IStockSaturationDao#queryPersonForTranferCenter()
	* @author 14022-foss-songjie
	* @update 2014年4月12日 上午9:38:16
	* @version V1.0
	*/
	@SuppressWarnings("unchecked")
	@Override
	public List<PersonForTransferEntity> queryPersonForTranferCenter() {
		return this.getSqlSession().selectList(NAME_SPACE+"queryPersonForTranferCenter");
	}

	/**
	* @description 获取对应外场的月操作货量
	* @param map queryDateA queryDateB orgCode
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年7月3日 下午4:01:10
	*/
	@Override
	public BigDecimal queryOperateMeasureMonth(Map<String, String> map) {
		BigDecimal bakcValue = BigDecimal.ZERO;
		bakcValue = (BigDecimal) this.getSqlSession().selectOne(NAME_SPACE+"queryOperateMeasureMonth", map);
		if(bakcValue==null){
			bakcValue= BigDecimal.ZERO;
		}
		return bakcValue;
	}

	/**
	 *@desc 统计当月预警天数与当月危险预警天数
	 *@param orgCode、startTime、endTime
	 *@return List<StockSaturationEntity>
	 *@author 105795
	 *@date 2015年3月12日下午3:36:36 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<StockSaturationEntity> calculateWarningAndDangerMothDays(
			List<String> orgCodeList, String startTime, String endTime) {
		Map<Object,Object> dataMap=new HashMap<Object,Object>();
		dataMap.put("orgCodeList", orgCodeList);
		dataMap.put("startTime", startTime);
		dataMap.put("endTime", endTime);
		return this.getSqlSession().selectList(NAME_SPACE+"calculateWarningAndDangerMothDays", dataMap);
	}
	
	
}

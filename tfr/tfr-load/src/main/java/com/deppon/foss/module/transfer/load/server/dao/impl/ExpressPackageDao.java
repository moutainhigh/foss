package com.deppon.foss.module.transfer.load.server.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.load.api.server.dao.IExpressPackageDao;
import com.deppon.foss.module.transfer.load.api.shared.domain.ExpressPackageDetailEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.ExpressPackageEntity;
import com.deppon.foss.module.transfer.load.api.shared.dto.ExpressPackageQueryDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.ExpressPackageSerialNoStockDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.ExpressPackageWayBillDetailDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.QuerySerialNoListForWaybillConditionDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.QueryWaybillForExpressPackageConditionDto;
import com.deppon.foss.util.define.FossConstants;

/** 
 * @className: ExpressPackageDao
 * @author: ShiWei shiwei@outlook.com
 * @description: 快递包Service
 * @date: 2013-7-17 下午4:53:04
 * 
 */
public class ExpressPackageDao extends iBatis3DaoImpl implements
		IExpressPackageDao {

	private static final String NAMESPACE = "foss.load.express.package.";
	
	protected final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	/**
	 * 查询包信息
	 * @author 045923-foss-shiwei
	 * @date 2013-7-17 下午4:58:28
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IExpressPackageDao#queryExpressPackageList(com.deppon.foss.module.transfer.load.api.shared.domain.ExpressPackageEntity, int, int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ExpressPackageEntity> queryExpressPackageList(
			ExpressPackageQueryDto entity,int start,int limit) {
		RowBounds rb = new RowBounds(start, limit);
		return this.getSqlSession().selectList(NAMESPACE + "queryExpressPackageList", entity, rb);
	}

	/**
	 * 查询包总个数，用于分页
	 * @author 045923-foss-shiwei
	 * @date 2013-7-17 下午5:17:24
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IExpressPackageDao#queryExpressPackageCount(com.deppon.foss.module.transfer.load.api.shared.domain.ExpressPackageEntity)
	 */
	@Override
	public Long queryExpressPackageCount(ExpressPackageQueryDto entity) {
		return (Long)this.getSqlSession().selectOne(NAMESPACE + "queryExpressPackageCount", entity);
	}

	/**
	 * 更新包状态
	 * @author 045923-foss-shiwei
	 * @date 2013-7-23 上午8:50:28
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IExpressPackageDao#updateExpressPackageState(java.lang.String, java.lang.String)
	 */
	@Override
	public int updateExpressPackageState(String packageNo, String targetState) {
		Map<String,String> map = new HashMap<String,String>();
		map.put("packageNo", packageNo);
		map.put("targetState", targetState);
		this.getSqlSession().update(NAMESPACE + "updateExpressPackageState",map);
		return FossConstants.SUCCESS;
	}
	
	/**
	 * 根据包号获取包信息
	 * @author 045923-foss-shiwei
	 * @date 2013-7-29 下午2:48:32
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ExpressPackageEntity queryExpressPackageByPackageNo(String packageNo){
		List<ExpressPackageEntity> packageList = this.getSqlSession().selectList(NAMESPACE + "queryExpressPackageByPackageNo", packageNo);
		if(CollectionUtils.isEmpty(packageList)){
			return null;
		}
		return packageList.get(0);
	}

	@Override
	public int updatePackageInfo(ExpressPackageEntity updateEntity) {
		
		return this.getSqlSession().update(NAMESPACE+"updatePackageInfo",updateEntity);
	}

	/**
	 * 新增包界面  根据运单号快速添加运单
	 * @Author: 200978  xiaobingcheng
	 * 2014-8-20
	 * @param queyWaybillForExpressPackageDto
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ExpressPackageWayBillDetailDto> queryWaybillStockByWaybillNo(
			QueryWaybillForExpressPackageConditionDto queyWaybillForExpressPackageDto) {
		
		return this.getSqlSession().selectList(NAMESPACE + "queryWaybillStockByWaybillNo", queyWaybillForExpressPackageDto);
	}

	/**
	 * 根据运单号获取流水号库存信息
	 * @Author: 200978  xiaobingcheng
	 * 2014-8-21
	 * @param querySerialNoListForWaybillConditionDto
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ExpressPackageSerialNoStockDto> querySerialNoStockList(
			QuerySerialNoListForWaybillConditionDto querySerialNoListForWaybillConditionDto) {
		
		return this.getSqlSession().selectList(NAMESPACE + "querySerialNoStockList", querySerialNoListForWaybillConditionDto);
	}
	
	/**
	 * 根据出发部门和到达部门查询线路条数
	 * @Author: 200978  xiaobingcheng
	 * 2014-8-22
	 * @param queryWaybillForExpressPackageDto
	 * @return
	 */
	public long queryExpressSourceLineByOrgCount(QueryWaybillForExpressPackageConditionDto queryWaybillForExpressPackageDto){
		
		return (Long) this.getSqlSession().selectOne(NAMESPACE + "queryExpressSourceLineByOrgCount", queryWaybillForExpressPackageDto);
	}

	/**
	 * 分页查询库存运单
	 * @Author: 200978  xiaobingcheng
	 * 2014-8-22
	 * @param queryWaybillForExpressPackageDto
	 * @param start
	 * @param limit
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ExpressPackageWayBillDetailDto> queryWaybillStockListForPackage(QueryWaybillForExpressPackageConditionDto queryWaybillForExpressPackageDto,int start,int limit){
		RowBounds rowBounds = new RowBounds(start,limit);
		return this.getSqlSession().selectList(NAMESPACE + "queryWaybillStockListForPackage", queryWaybillForExpressPackageDto, rowBounds);
	}
	
	/**
	 * 查询库存运单总数
	 * @Author: 200978  xiaobingcheng
	 * 2014-8-22
	 * @param queryWaybillForExpressPackageDto
	 * @return
	 */
	public Long queryWaybillStockListForPackageCount(QueryWaybillForExpressPackageConditionDto queryWaybillForExpressPackageDto){
		return (Long) this.getSqlSession().selectOne(NAMESPACE + "queryWaybillStockListForPackageCount", queryWaybillForExpressPackageDto);
	}
	
	/**
	 * 新增快递包明细
	 * @Author: 200978  xiaobingcheng
	 * 2014-8-25
	 * @param goods
	 */
	public void insertExpressPackageDetail(ExpressPackageDetailEntity goods){
		this.getSqlSession().insert(NAMESPACE + "insertExpressPackageDetailByPc", goods);
	}
	
	/**
	 * 新增快递包
	 * @Author: 200978  xiaobingcheng
	 * 2014-8-25
	 * @param expressPackage
	 */
	public void insertExpressPackage(ExpressPackageEntity expressPackage){
		this.getSqlSession().insert(NAMESPACE + "insertExpressPackageByPc", expressPackage);
	}
	
	/**
	 * 根据运单号列表，查询包明细中等于这些运单号的包明细
	 * @Author: 200978  xiaobingcheng
	 * 2014-9-23
	 * @param waybillNoList
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ExpressPackageDetailEntity> queryPackageDetailByWaybills(List<String> waybillNoList){
		
		if(CollectionUtils.isEmpty(waybillNoList)){
			return new ArrayList<ExpressPackageDetailEntity>();
		}
		
		HashMap<String,List<String>> map = new HashMap<String, List<String>>();
		map.put("waybillNoList", waybillNoList);
		return this.getSqlSession().selectList(NAMESPACE + "queryPackageDetailByWaybills", map);
	}
	
}

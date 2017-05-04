/*
 * PROJECT NAME: tfr-load
 * PACKAGE NAME: com.deppon.foss.module.transfer.load.server.dao.impl
 * FILE    NAME: PDAExpressPackageDao.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.load.server.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.load.api.server.dao.IPDAExpressPackageDao;
import com.deppon.foss.module.transfer.load.api.shared.define.LoadConstants;
import com.deppon.foss.module.transfer.load.api.shared.domain.ExpressPackageDetailEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.ExpressPackageEntity;
import com.deppon.foss.module.transfer.load.api.shared.dto.ExpressPackageGoodsDto;
import com.deppon.foss.module.transfer.pda.api.shared.dto.ExpressPackageDto;

/**
 * PDAExpressPackageDao
 * @author dp-duyi
 * @date 2013-7-22 上午10:25:52
 */
@SuppressWarnings("unchecked")
public class PDAExpressPackageDao  extends iBatis3DaoImpl implements IPDAExpressPackageDao{
	private static final String NAMESPACE = "foss.load.express.package.";
	/** 
	 * 根据理货员查询包
	 * @author dp-duyi
	 * @date 2013-7-22 上午10:26:15
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IPDAExpressPackageDao#queryPackage(java.lang.String, java.util.Date, java.util.Date, java.util.List)
	 */
	@Override
	public List<ExpressPackageDto> queryPackage(String userCode,
			Date queryStartTime, Date queryEndTime, List<String> states) {
		//根据理货员查询包
		Map<String,Object> condition = new HashMap<String,Object>();
		condition.put("userCode", userCode);
		condition.put("queryStartTime", queryStartTime);
		condition.put("queryEndTime", queryEndTime);
		condition.put("states", states);
		return this.getSqlSession().selectList(NAMESPACE+"queryPackageByUser", condition);
	}

	/** 
	 * 根据包号查询包
	 * @author dp-duyi
	 * @date 2013-7-22 上午10:38:32
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IPDAExpressPackageDao#queryPackageByNo(java.lang.String)
	 */
	@Override
	public ExpressPackageEntity queryPackageByNo(String packageNo) {
		List<ExpressPackageEntity> list = this.getSqlSession().selectList(NAMESPACE+"queryPackageByPackageNo", packageNo);
		if(CollectionUtils.isNotEmpty(list)){
			return list.get(0);
		}else{
			return null;
		}
	}
	
	/** 
	 * 根据包号查询包条数
	 * @author dp-duyi
	 * @date 2013-7-22 上午10:38:32
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IPDAExpressPackageDao#queryPackageByNo(java.lang.String)
	 */
	@Override
	public int queryPackageCountByNo(String packageNo) {
		return (Integer)this.getSqlSession().selectOne(NAMESPACE+"queryPackageCountByNo", packageNo);
	}
	/** 
	 * 插入快递包
	 * @author dp-duyi
	 * @date 2013-7-22 上午10:43:41
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IPDAExpressPackageDao#insertExpressPackage(com.deppon.foss.module.transfer.load.api.shared.domain.ExpressPackageEntity)
	 */
	@Override
	public int insertExpressPackage(ExpressPackageEntity packageEntity) {
		return this.getSqlSession().insert(NAMESPACE+"insertExpressPackage", packageEntity);
	}

	/** 
	 * 更新快递包
	 * @author dp-duyi
	 * @date 2013-7-22 上午11:40:23
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IPDAExpressPackageDao#updateExpressPackage(com.deppon.foss.module.transfer.load.api.shared.domain.ExpressPackageEntity)
	 */
	@Override
	public int updateExpressPackage(ExpressPackageEntity packageEntity) {
		return this.getSqlSession().update(NAMESPACE+"updateExpressPackage", packageEntity);
	}

	/** 
	 * 插入快递明细
	 * @author dp-duyi
	 * @date 2013-7-22 上午11:40:23
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IPDAExpressPackageDao#insertExpressPackageDetail(com.deppon.foss.module.transfer.load.api.shared.domain.ExpressPackageDetailEntity)
	 */
	@Override
	public int insertExpressPackageDetail(
			ExpressPackageDetailEntity packageDetailEntity) {
		return this.getSqlSession().insert(NAMESPACE+"insertExpressPackageDetail", packageDetailEntity);
	}

	/** 
	 * 刷新
	 * @author dp-duyi
	 * @date 2013-7-22 下午4:38:28
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IPDAExpressPackageDao#queryPackageGoods(java.lang.String)
	 */
	@Override
	public List<ExpressPackageGoodsDto> queryStockPackageGoods(String packageNo) {
		Map<String,Object> condition = new HashMap<String,Object>();
		condition.put("packageNo", packageNo);
		List<String> list = new ArrayList<String>();
		list.add(LoadConstants.PRODUCT_CODE_PACKAGE);
		list.add(LoadConstants.PRODUCT_CODE_RCP);
		list.add(LoadConstants.PRODUCT_CODE_EPEP);
 		condition.put("transportTypeCode", list);
		return this.getSqlSession().selectList(NAMESPACE+"queryStockPackageGoods", condition);
	}
	/** 
	 * 查询已扫货物
	 * @author dp-duyi
	 * @date 2013-7-30 下午3:37:03
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IPDAExpressPackageDao#queryScanPackageGoods(java.lang.String)
	 */
	@Override
	public List<ExpressPackageGoodsDto> queryScanPackageGoods(String packageNo) {
		return this.getSqlSession().selectList(NAMESPACE+"queryScanPackageGoods", packageNo);
	}
	/** 
	 * 查询操作件数
	 * @author dp-duyi
	 * @date 2013-7-25 下午3:36:40
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IPDAExpressPackageDao#queryPackageQty(java.lang.String)
	 */
	@Override
	public int queryPackageQty(String packageNo) {
		return (Integer) this.getSqlSession().selectOne(NAMESPACE+"queryPackageQty", packageNo);
	}

	/** 
	 * 计算总重量、总体积、总件数、总票数
	 * @author dp-duyi
	 * @date 2013-7-26 上午9:03:56
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IPDAExpressPackageDao#statisticalData(java.lang.String)
	 */
	@Override
	public ExpressPackageEntity statisticalData(String packageNo) {
		return (ExpressPackageEntity) this.getSqlSession().selectOne(NAMESPACE+"statisticalData", packageNo);
	}

	/** 
	 * 查询扫描明细
	 * @author dp-duyi
	 * @date 2013-7-30 上午10:48:32
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IPDAExpressPackageDao#queryExpressPackageDetail(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public ExpressPackageDetailEntity queryExpressPackageDetail(String packNo,
			String wayBillNo, String serialNo) {
		Map<String,String> condition = new HashMap<String,String>();
		condition.put("packNo", packNo);
		condition.put("wayBillNo", wayBillNo);
		condition.put("serialNo", serialNo);
		return (ExpressPackageDetailEntity) this.getSqlSession().selectOne(NAMESPACE+"queryExpressPackageDetail", condition);
	}

	/** 
	 * 更新包扫描明细
	 * @author dp-duyi
	 * @date 2013-7-30 上午10:48:32
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IPDAExpressPackageDao#updateExpressPackageDetail(com.deppon.foss.module.transfer.load.api.shared.domain.ExpressPackageDetailEntity)
	 */
	@Override
	public int updateExpressPackageDetail(
			ExpressPackageDetailEntity packageDetailEntity) {
		return this.getSqlSession().update(NAMESPACE+"updateExpressPackageDetail", packageDetailEntity);
	}

	/** 
	 * 更新库存是否包扫描
	 * @author dp-duyi
	 * @date 2013-8-6 上午8:55:41
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IPDAExpressPackageDao#updateStockIsPackage(java.lang.String, java.lang.String)
	 */
	@Override
	public int updateStockIsPackage(String wayBillNo, String serialNo) {
		Map<String,String> condition = new HashMap<String,String>();
		condition.put("wayBillNo", wayBillNo);
		condition.put("serialNo", serialNo);
		return this.getSqlSession().update(NAMESPACE+"updateStockIsPackage", condition);
	}

	/** 
	 * 查询已扫描货物明细
	 * @author dp-duyi
	 * @date 2013-8-6 上午8:55:41
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IPDAExpressPackageDao#queryScanPackageDetails(java.lang.String, java.lang.String)
	 */
	@Override
	public List<ExpressPackageDetailEntity> queryScanPackageDetails(
			String packageNo) {
		return this.getSqlSession().selectList(NAMESPACE+"queryScanPackageDetails", packageNo);
	}
	/** 
	 * 查询已扫描货物明细
	 * @author dp-duyi
	 * @date 2013-10-24 上午11:25:41
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IPDAExpressPackageDao#queryScanPackageDetails(java.lang.String, java.lang.String)
	 */
	@Override
	public int deletePackageDetail(String packageNo) {
		return this.getSqlSession().delete(NAMESPACE+"deletePackageDetail", packageNo);
	}
	
	/**
	 * @desc 下来直达包中的运单
	 * @author wqh
	 * @param packageEntity
	 * @date 2014-10-15
	 * 
	 * */
	public List<ExpressPackageGoodsDto> queryStockThroughPackageDetail(ExpressPackageEntity packageEntity){
		Map<String,Object> condition = new HashMap<String,Object>();
		condition.put("packageNo", packageEntity.getPackageNo());
		List<String> list = new ArrayList<String>();
		list.add(LoadConstants.PRODUCT_CODE_PACKAGE);
		list.add(LoadConstants.PRODUCT_CODE_RCP);
		list.add(LoadConstants.PRODUCT_CODE_EPEP);
 		condition.put("transportTypeCode", list);
 		condition.put("departOrgCode", packageEntity.getDepartOrgCode());
 		condition.put("arriveOrgCode", packageEntity.getArriveOrgCode());
		return this.getSqlSession().selectList(NAMESPACE+"queryStockThroughPackageDetail", condition);
		
	}
	
	/**
	 * @desc 下拉空运直达包中的运单
	 * @author rc
	 * @param packageEntity
	 * @date 2015-1-23
	 * 
	 * */
	@Override
	public List<ExpressPackageGoodsDto> queryStockAirThroughPackageDetail(ExpressPackageEntity packageEntity){
		Map<String,Object> condition = new HashMap<String,Object>();
		condition.put("packageNo", packageEntity.getPackageNo());
		List<String> list = new ArrayList<String>();
		list.add(LoadConstants.PRODUCT_CODE_DEAP);
 		condition.put("transportTypeCode", list);
 		condition.put("departOrgCode", packageEntity.getDepartOrgCode());
 		condition.put("arriveOrgCode", packageEntity.getArriveOrgCode());
		return this.getSqlSession().selectList(NAMESPACE+"queryStockAirThroughPackageDetail", condition);
		
	}
	/**
	 * 
	 * <p>刷新接驳点下运单明细</p> 
	 * @author alfred
	 * @date 2015-9-6 下午7:10:59
	 * @param packageNo
	 * @return 
	 */
	@Override
	public List<ExpressPackageGoodsDto> querySCStockPackageGoods(String packageNo) {
		Map<String,Object> condition = new HashMap<String,Object>();
		condition.put("packageNo", packageNo);
		List<String> list = new ArrayList<String>();
		list.add(LoadConstants.PRODUCT_CODE_PACKAGE);
		list.add(LoadConstants.PRODUCT_CODE_RCP);
		list.add(LoadConstants.PRODUCT_CODE_EPEP);
 		condition.put("transportTypeCode", list);
		return this.getSqlSession().selectList(NAMESPACE+"querySCStockPackageGoods", condition);
	}	/**
 * @com.deppon.foss.module.transfer.load.server.dao.impl
 * @desc: 下拉当前库存中的运输类型为快递空运的运单
 * @param:
 * @wqh
 * @2015年8月11日下午3:24:21
 */
public List<ExpressPackageGoodsDto> refrushAirPackageDetail(String packageNo,String orgCode,String destOrgCode ,List<String> transportTypeCode){
	  
	  Map<String,Object> condition = new HashMap<String,Object>();
		condition.put("packageNo", packageNo);
		condition.put("transportTypeCode", transportTypeCode);
		condition.put("orgCode", orgCode);
		condition.put("destOrgCode",destOrgCode);
		return this.getSqlSession().selectList(NAMESPACE+"refrushAirPackageDetail", condition);
  }

	@Override
	public int deleteExpressPackage(String packageNo) {
		return this.getSqlSession().delete(NAMESPACE+"deleteExpressPackage",packageNo);
	}
}

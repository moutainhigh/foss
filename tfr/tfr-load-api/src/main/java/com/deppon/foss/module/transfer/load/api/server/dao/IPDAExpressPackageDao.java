/*
 * PROJECT NAME: tfr-load-api
 * PACKAGE NAME: com.deppon.foss.module.transfer.load.api.server.dao
 * FILE    NAME: IPDAExpressPackageDao.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.load.api.server.dao;

import java.util.Date;
import java.util.List;

import com.deppon.foss.module.transfer.load.api.shared.domain.ExpressPackageDetailEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.ExpressPackageEntity;
import com.deppon.foss.module.transfer.load.api.shared.dto.ExpressPackageGoodsDto;
import com.deppon.foss.module.transfer.pda.api.shared.dto.ExpressPackageDto;

/**
 * IExpressPackageDao
 * @author dp-duyi
 * @date 2013-7-22 上午10:22:41
 */
public interface IPDAExpressPackageDao {
	public List<ExpressPackageDto> queryPackage(String userCode,Date queryStartTime,Date queryEndTime,List<String> states);
	public ExpressPackageEntity queryPackageByNo(String packageNo);
	public int queryPackageCountByNo(String packageNo);
	public int insertExpressPackage(ExpressPackageEntity packageEntity);
	public int updateExpressPackage(ExpressPackageEntity packageEntity);
	public int insertExpressPackageDetail(ExpressPackageDetailEntity packageDetailEntity);
	public ExpressPackageDetailEntity queryExpressPackageDetail(String packNo,String wayBillNo,String serialNo);
	public int updateExpressPackageDetail(ExpressPackageDetailEntity packageDetailEntity);
	public List<ExpressPackageGoodsDto> queryStockPackageGoods(String packageNo);
	public List<ExpressPackageGoodsDto> queryScanPackageGoods(String packageNo);
	public int queryPackageQty(String packageNo);
	public ExpressPackageEntity statisticalData(String packageNo);
	public int updateStockIsPackage(String wayBillNo,String serialNo);
	public List<ExpressPackageDetailEntity> queryScanPackageDetails(String packageNo);
	public int deletePackageDetail(String packageNo);
	public int deleteExpressPackage(String packageNo);
	
	/**-------------快递直达包--------------**/
	//查询库存中的运单
	public List<ExpressPackageGoodsDto> queryStockThroughPackageDetail(ExpressPackageEntity packageEntity);
	//查询库存中的空运运单列表
	public List<ExpressPackageGoodsDto> queryStockAirThroughPackageDetail(ExpressPackageEntity packageEntity);
	//刷新接驳点下运单明细
	public List<ExpressPackageGoodsDto> querySCStockPackageGoods(String packageNo);   //下拉当前库存中的运输类型为快递空运的运单
	public List<ExpressPackageGoodsDto> refrushAirPackageDetail(String packageNo ,String orgCode,String destOrgCode ,List<String> transportTypeCode);
}

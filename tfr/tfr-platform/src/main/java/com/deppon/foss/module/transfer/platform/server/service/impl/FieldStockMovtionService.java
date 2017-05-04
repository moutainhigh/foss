/**
 * 
 */
package com.deppon.foss.module.transfer.platform.server.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.base.util.define.BizTypeConstants;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.platform.api.server.dao.IFieldStockMovtionDao;
import com.deppon.foss.module.transfer.platform.api.server.service.IFieldStockMovtionService;
import com.deppon.foss.module.transfer.platform.api.shared.domain.FieldStockMovtionDetailEntity;
import com.deppon.foss.module.transfer.platform.api.shared.domain.FieldStockMovtionEntity;
import com.deppon.foss.module.transfer.platform.api.shared.dto.QueryConditionStockMovtionDto;
import com.deppon.foss.util.CollectionUtils;

/**
 * @author 105795
 * @desc 库区库存流动
 * @date 2015-03-03
 */

public class FieldStockMovtionService implements IFieldStockMovtionService {

	/**
	 * dao
	 * */
	private IFieldStockMovtionDao fieldStockMovtionDao;
	
	/**
	 * logger
	 */
	private final Logger logger = LoggerFactory.getLogger(QueryPDAonLineService.class);
	
	/**
	 * orgAdministrativeInfoComplexService
	 * */
	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;
	
	//包装货区
	private final String packageGoodsAreaType="BSE_GOODSAREA_TYPE_PACKING";
	
	//派送货区
	private final String pickupGoodsAreaType="BSE_GOODSAREA_TYPE_STATION";

	//快递库区
	private final String rcpGoodsAreaType="BSE_GOODSAREA_TYPE_EXPRESS";

	//快递派送库区
	private final String rcpPickupGoodsAreaType="BSE_GOODSAREA_TYPE_EXPRESS_STATION";
	
	//偏线库区
	private final String other="BSE_GOODSAREA_TYPE_OTHER";
	
	//空运货区
	private final String airGoodsAreaType="BSE_GOODSAREA_TYPE_AIRFREIGHT";
	
	
	/**
	 *@desc 根据货区类型查询货区编码
	 *@param origOrgCode,goodsTypeList
	 *@return List<String>
	 *@author 105795
	 *@date 2015年3月3日下午2:21:05 
	 */
	
	
	public List<String> queryGoodsCodeByGoodsType(String origOrgCode,List<String> goodsTypeList)
	{
		if(StringUtil.isEmpty(origOrgCode))
		{
			throw new TfrBusinessException("部门编码不能为空");
		}
		if(CollectionUtils.isEmpty(goodsTypeList))
		{
			throw new TfrBusinessException("货区类型不能为空");

		}
		return fieldStockMovtionDao.queryGoodsCodeByGoodsType(origOrgCode, goodsTypeList);
	}
	
	/**
	 *com.deppon.foss.module.transfer.platform.api.server.service  
	 *@desc 查询场内库存流动所有情况
	 *@param 
	 *@return List<FieldStockMovtionEntity>
	 *@author 105795
	 *@date 2015年3月3日下午3:39:49 
	 */
	public FieldStockMovtionEntity queryFieldStockMovtion(String origOrgCode)
	{
		Date date=new Date();
		logger.info("查询场内库存流动所有情况 开始时间..."+date );
		FieldStockMovtionEntity fieldStockMovtionEntity=new FieldStockMovtionEntity();
		//List<FieldStockMovtionDetailEntity> fieldStockMovtionDetailList=null;
		if(StringUtil.isEmpty(origOrgCode))
		{
			throw new TfrBusinessException("外场编码编码不能为空");
		}
		//找顶级外场
		Map<String, String> orgMap=queryParentTfrCtrCode(origOrgCode);
		if(orgMap==null){
			throw new TfrBusinessException("查询顶级外场失败！");
		}
		//初始化外场信息
		fieldStockMovtionEntity.setTransferCenterCode(orgMap.get("code"));
		fieldStockMovtionEntity.setTransferCenterName(orgMap.get("name")==null?"":orgMap.get("name"));

		//初始化查询条件
		QueryConditionStockMovtionDto queryCondition=new QueryConditionStockMovtionDto();
		//放入顶级外场作为查询条件
		queryCondition.setOrigOrgCode(orgMap.get("code"));
		
		/**
		 * 查询到达未卸车
		 * */
		/**
		 * @desc 优化代码，将计算放在oracle中完成
		 * @author 105795 
		 * 
		 * */
		FieldStockMovtionEntity mvArrEntity=fieldStockMovtionDao.queryArrivedUnloadTotalVote(queryCondition);
		if(mvArrEntity!=null){
			//总票数
			fieldStockMovtionEntity.setArrivedVotes(mvArrEntity.getArrivedVotes());
			//总体积
			fieldStockMovtionEntity.setArrivedVolume(mvArrEntity.getArrivedVolume()==null?BigDecimal.ZERO:mvArrEntity.getArrivedVolume());
			//总重量
			fieldStockMovtionEntity.setArrivedWeight(mvArrEntity.getArrivedWeight()==null?BigDecimal.ZERO:mvArrEntity.getArrivedWeight());
			
		}else{
			//总票数
			fieldStockMovtionEntity.setArrivedVotes(0);
			//总体积
			fieldStockMovtionEntity.setArrivedVolume(BigDecimal.ZERO);
			//总重量
			fieldStockMovtionEntity.setArrivedWeight(BigDecimal.ZERO);
		}
		
		
		/**
		 * 卸车中单据
		 * 
		 * */
		FieldStockMovtionEntity mvUnloadingEntity=fieldStockMovtionDao.queryUnLoadingTotalVote(queryCondition);
		if(mvUnloadingEntity!=null){
			//总票数
			fieldStockMovtionEntity.setUnloadingVotes(mvUnloadingEntity.getUnloadingVotes());
			//总体积
			fieldStockMovtionEntity.setUnloadingVolume(mvUnloadingEntity.getUnloadingVolume()==null?BigDecimal.ZERO:mvUnloadingEntity.getUnloadingVolume());
			//总重量
			fieldStockMovtionEntity.setUnloadingWeight(mvUnloadingEntity.getUnloadingWeight()==null?BigDecimal.ZERO:mvUnloadingEntity.getUnloadingWeight());
			
		}else{
			//总票数
			fieldStockMovtionEntity.setUnloadingVotes(0);
			//总体积
			fieldStockMovtionEntity.setUnloadingVolume(BigDecimal.ZERO);
			//总重量
			fieldStockMovtionEntity.setUnloadingWeight(BigDecimal.ZERO);
		}
		
		
		/**
		 * 待叉区
		 * */
		FieldStockMovtionEntity mvtrayEntity=fieldStockMovtionDao.queryTrayTotalVote(queryCondition);
		if(mvtrayEntity!=null){
			//总票数
			fieldStockMovtionEntity.setTrayVotes(mvtrayEntity.getTrayVotes());
			//总体积
			fieldStockMovtionEntity.setTrayVolume(mvtrayEntity.getTrayVolume()==null?BigDecimal.ZERO:mvtrayEntity.getTrayVolume());
			//总重量
			fieldStockMovtionEntity.setTrayWeight(mvtrayEntity.getTrayWeight()==null?BigDecimal.ZERO:mvtrayEntity.getTrayWeight());
			
		}else{
			//总票数
			fieldStockMovtionEntity.setTrayVotes(0);
			//总体积
			fieldStockMovtionEntity.setTrayVolume(BigDecimal.ZERO);
			//总重量
			fieldStockMovtionEntity.setTrayWeight(BigDecimal.ZERO);
		}
		
		/**
		 * 包装库区
		 * */
		//查询包装货区编码
		List<String> goodsAreaTypesPacking=new ArrayList<String>();
		goodsAreaTypesPacking.add(packageGoodsAreaType);
		List<String> goodsAreaCodesPacking=queryGoodsCodeByGoodsType(queryCondition.getOrigOrgCode(),goodsAreaTypesPacking);
		FieldStockMovtionEntity mvPackingEntity=null;
		if(CollectionUtils.isNotEmpty(goodsAreaCodesPacking)&&goodsAreaCodesPacking.size()>0){
			 queryCondition.setGoodsAreaCode(goodsAreaCodesPacking.get(0));
			 mvPackingEntity=fieldStockMovtionDao.queryPackagingTotalVote(queryCondition);
		}
		if(mvPackingEntity!=null){
			//总票数
			fieldStockMovtionEntity.setPackagingVotes(mvPackingEntity.getPackagingVotes());
			//总体积
			fieldStockMovtionEntity.setPackagingVolume(mvPackingEntity.getPackagingVolume()==null?BigDecimal.ZERO:mvPackingEntity.getPackagingVolume());
			//总重量
			fieldStockMovtionEntity.setPackagingWeight(mvPackingEntity.getPackagingWeight()==null?BigDecimal.ZERO:mvPackingEntity.getPackagingWeight());
			
		}else{
			//总票数
			fieldStockMovtionEntity.setPackagingVotes(0);
			//总体积
			fieldStockMovtionEntity.setPackagingVolume(BigDecimal.ZERO);
			//总重量
			fieldStockMovtionEntity.setPackagingWeight(BigDecimal.ZERO);
		}
		
		 
		/**
		 * 零担中转库区:非包装，非派送，非偏线，非空运库区
		 * */
		//查询货区编码
		List<String> tfrAreaTypes=new ArrayList<String>();
		tfrAreaTypes.add(packageGoodsAreaType);
		tfrAreaTypes.add(pickupGoodsAreaType);
		tfrAreaTypes.add(other);
		tfrAreaTypes.add(airGoodsAreaType);
		List<String> goodsAreaCodesTfr=queryGoodsCodeByGoodsType(queryCondition.getOrigOrgCode(),tfrAreaTypes);
		queryCondition.setGoodsAreaCodeList(goodsAreaCodesTfr);
		FieldStockMovtionEntity mvTfrStockEntity=null;
		if(CollectionUtils.isNotEmpty(goodsAreaCodesTfr)&&goodsAreaCodesTfr.size()>0){
			mvTfrStockEntity=fieldStockMovtionDao.queryTfrStockTotalVote(queryCondition);
		}
		
		if(mvTfrStockEntity!=null){
			//总票数
			fieldStockMovtionEntity.setStockVotes(mvTfrStockEntity.getStockVotes());
			//总体积
			fieldStockMovtionEntity.setStockVolume(mvTfrStockEntity.getStockVolume()==null?BigDecimal.ZERO:mvTfrStockEntity.getStockVolume());
			//总重量
			fieldStockMovtionEntity.setStockWeight(mvTfrStockEntity.getStockWeight()==null?BigDecimal.ZERO:mvTfrStockEntity.getStockWeight());
			
		}else{
			//总票数
			fieldStockMovtionEntity.setStockVotes(0);
			//总体积
			fieldStockMovtionEntity.setStockVolume(BigDecimal.ZERO);
			//总重量
			fieldStockMovtionEntity.setStockWeight(BigDecimal.ZERO);
		}
		
		
		/**
		 * 零担派送库区
		 * */
		List<String> goodsAreaTypesTfrPick=new ArrayList<String>();
		goodsAreaTypesTfrPick.add(pickupGoodsAreaType);
		FieldStockMovtionEntity mvTfrPickEntity=null;
		List<String> goodsAreaCodesTfrPick=queryGoodsCodeByGoodsType(queryCondition.getOrigOrgCode(),goodsAreaTypesTfrPick);
		if(CollectionUtils.isNotEmpty(goodsAreaCodesTfrPick)&&goodsAreaCodesTfrPick.size()>0){
			 queryCondition.setGoodsAreaCode(goodsAreaCodesTfrPick.get(0));
			 mvTfrPickEntity=fieldStockMovtionDao.queryTfrPickupStockTotalVote(queryCondition);
		}
		if(mvTfrPickEntity!=null){
			//总票数
			fieldStockMovtionEntity.setDeliverStockVotes(mvTfrPickEntity.getDeliverStockVotes());
			//总体积
			fieldStockMovtionEntity.setDeliverStockVolume(mvTfrPickEntity.getDeliverStockVolume()==null?BigDecimal.ZERO:mvTfrPickEntity.getDeliverStockVolume());
			//总重量
			fieldStockMovtionEntity.setDeliverStockWeight(mvTfrPickEntity.getDeliverStockWeight()==null?BigDecimal.ZERO:mvTfrPickEntity.getDeliverStockWeight());
			
		}else{
			//总票数
			fieldStockMovtionEntity.setDeliverStockVotes(0);
			//总体积
			fieldStockMovtionEntity.setDeliverStockVolume(BigDecimal.ZERO);
			//总重量
			fieldStockMovtionEntity.setDeliverStockWeight(BigDecimal.ZERO);
		}
		
		/**
		 * 快递中转库区库存
		 * */
		List<String> goodsAreaTypesExpressStock=new ArrayList<String>();
		goodsAreaTypesExpressStock.add(rcpGoodsAreaType);
		FieldStockMovtionEntity mvExpressStockEntity=null;
		List<String> goodsAreaCodesExpressStock=queryGoodsCodeByGoodsType(queryCondition.getOrigOrgCode(),goodsAreaTypesExpressStock);
		if(CollectionUtils.isNotEmpty(goodsAreaCodesExpressStock)&&goodsAreaCodesExpressStock.size()>0){
			 queryCondition.setGoodsAreaCode(goodsAreaCodesExpressStock.get(0));
			 mvExpressStockEntity=fieldStockMovtionDao.queryRcpStockTotalVote(queryCondition);
		}
		if(mvExpressStockEntity!=null){
			//总票数
			fieldStockMovtionEntity.setExpressStockVotes(mvExpressStockEntity.getExpressStockVotes());
			//总体积
			fieldStockMovtionEntity.setExpressStockVolume(mvExpressStockEntity.getExpressStockVolume()==null?BigDecimal.ZERO:mvExpressStockEntity.getExpressStockVolume());
			//总重量
			fieldStockMovtionEntity.setExpressStockWeight(mvExpressStockEntity.getExpressStockWeight()==null?BigDecimal.ZERO:mvExpressStockEntity.getExpressStockWeight());
			
		}else{
			//总票数
			fieldStockMovtionEntity.setExpressStockVotes(0);
			//总体积
			fieldStockMovtionEntity.setExpressStockVolume(BigDecimal.ZERO);
			//总重量
			fieldStockMovtionEntity.setExpressStockWeight(BigDecimal.ZERO);
		}
		
		/**
		 * 快递派送库区库存
		 * */
		List<String> goodsAreaTypesRcpPick=new ArrayList<String>();
		goodsAreaTypesRcpPick.add(rcpPickupGoodsAreaType);
		FieldStockMovtionEntity mvRcpPickEntity=null;
		List<String> goodsAreaCodesRcpPick=queryGoodsCodeByGoodsType(queryCondition.getOrigOrgCode(),goodsAreaTypesRcpPick);
		if(CollectionUtils.isNotEmpty(goodsAreaCodesRcpPick)&&goodsAreaCodesRcpPick.size()>0){
			 queryCondition.setGoodsAreaCode(goodsAreaCodesRcpPick.get(0));
			 mvRcpPickEntity=fieldStockMovtionDao.queryRcpPickupStockTotalVote(queryCondition);
		}
		if(mvRcpPickEntity!=null){
			//总票数
			fieldStockMovtionEntity.setExpressDeliverStockVotes(mvRcpPickEntity.getExpressDeliverStockVotes());
			//总体积
			fieldStockMovtionEntity.setExpressDeliverStockVolume(mvRcpPickEntity.getExpressDeliverStockVolume()==null?BigDecimal.ZERO:mvRcpPickEntity.getExpressDeliverStockVolume());
			//总重量
			fieldStockMovtionEntity.setExpressDeliverStockWeight(mvRcpPickEntity.getExpressDeliverStockWeight()==null?BigDecimal.ZERO:mvRcpPickEntity.getExpressDeliverStockWeight());
			
		}else{
			//总票数
			fieldStockMovtionEntity.setExpressDeliverStockVotes(0);
			//总体积
			fieldStockMovtionEntity.setExpressDeliverStockVolume(BigDecimal.ZERO);
			//总重量
			fieldStockMovtionEntity.setExpressDeliverStockWeight(BigDecimal.ZERO);
		}
		
		/**
		 * 正在装车 
		 * */
		FieldStockMovtionEntity mvLoadedEntity=fieldStockMovtionDao.queryLoadingTotalVote(queryCondition);
		if(mvLoadedEntity!=null){
			//总票数
			fieldStockMovtionEntity.setLoadedVotes(mvLoadedEntity.getStockVotes());
			//总体积
			fieldStockMovtionEntity.setLoadedVolume(mvLoadedEntity.getStockVolume()==null?BigDecimal.ZERO:mvLoadedEntity.getStockVolume());
			//总重量
			fieldStockMovtionEntity.setLoadedWeight(mvLoadedEntity.getStockWeight()==null?BigDecimal.ZERO:mvLoadedEntity.getStockWeight());
			
		}else{
			//总票数
			fieldStockMovtionEntity.setLoadedVotes(0);
			//总体积
			fieldStockMovtionEntity.setLoadedVolume(BigDecimal.ZERO);
			//总重量
			fieldStockMovtionEntity.setLoadedWeight(BigDecimal.ZERO);
		}

		
		logger.info("查询场内库存流动所有情况 结束时间..."+new Date());
		logger.info("查询场内库存流动所有情况 耗时 ----"+(new Date().getTime()-date.getTime()));

		return fieldStockMovtionEntity;
	}
	
	/**
	 *@desc 到达未卸车(包括长途短途) 
	 *@param queryCondition
	 *@return List<FieldStockMovtionDetailEntity>
	 *@author 105795
	 *@date 2015年3月3日上午11:13:21 
	 */
	public List<FieldStockMovtionDetailEntity> queryArrivedUnload(QueryConditionStockMovtionDto queryCondition,int start, int limit)
	{
		List<FieldStockMovtionDetailEntity> fieldStockMovtionDetailList=null;
		Date date=new Date();
		logger.info("查询场内库存流动 [到达未卸车(包括长途短途)] 开始时间..."+date );
		//参数校验
		checkoutCondition(queryCondition);
		fieldStockMovtionDetailList= fieldStockMovtionDao.queryArrivedUnload(queryCondition, start, limit);
		
		logger.info("查询场内库存[到达未卸车(包括长途短途)] 结束时间..."+new Date());
		logger.info("查询场内库存[到达未卸车(包括长途短途)] 耗时 ----"+(new Date().getTime()-date.getTime()));
		return fieldStockMovtionDetailList;
		
	}
	
	/**
	 *@desc 卸车 包含长途、短途、集中接货卸车
	 *@param queryCondition
	 *@return List<FieldStockMovtionDetailEntity>
	 *@author 105795
	 *@date 2015年3月3日下午2:16:47 
	 */
	public List<FieldStockMovtionDetailEntity> queryUnLoading(QueryConditionStockMovtionDto queryCondition,int start, int limit)
	{
		List<FieldStockMovtionDetailEntity> fieldStockMovtionDetailList=null;
		Date date=new Date();
		logger.info("查询场内库存流动 [卸车] 开始时间..."+date );
		//参数校验
		checkoutCondition(queryCondition);
		fieldStockMovtionDetailList= fieldStockMovtionDao.queryUnLoading(queryCondition, start, limit);
		
		logger.info("查询场内库存[卸车] 结束时间..."+new Date());
		logger.info("查询场内库存[卸车] 耗时 ----"+(new Date().getTime()-date.getTime()));
		return fieldStockMovtionDetailList;
	}
	

	/**
	 *@desc 待叉区：托盘绑定未叉车扫描，且在库存中、非正在装车 
	 *@param queryCondition
	 *@return List<FieldStockMovtionDetailEntity>
	 *@author 105795
	 *@date 2015年3月3日下午2:18:57 
	 */
	public List<FieldStockMovtionDetailEntity> queryTray(QueryConditionStockMovtionDto queryCondition,int start, int limit)
	{
		List<FieldStockMovtionDetailEntity> fieldStockMovtionDetailList=null;
		Date date=new Date();
		logger.info("查询场内库存流动 [待叉区] 开始时间..."+date );
		//参数校验
		checkoutCondition(queryCondition);
		fieldStockMovtionDetailList= fieldStockMovtionDao.queryTray(queryCondition, start, limit);
		
		logger.info("查询场内库存[待叉区] 结束时间..."+new Date());
		logger.info("查询场内库存[待叉区] 耗时 ----"+(new Date().getTime()-date.getTime()));
		return fieldStockMovtionDetailList;
	}
	
	/**
	 *@desc 包装库区：包装库区、非正在装车  
	 *@param queryCondition
	 *@return List<FieldStockMovtionDetailEntity>
	 *@author 105795
	 *@date 2015年3月3日下午2:18:57 
	 */
	public List<FieldStockMovtionDetailEntity> queryPackaging(QueryConditionStockMovtionDto queryCondition,int start, int limit)
	{
		List<FieldStockMovtionDetailEntity> fieldStockMovtionDetailList=null;
		Date date=new Date();
		logger.info("查询场内库存流动 [包装库区] 开始时间..."+date );
		//参数校验
		checkoutCondition(queryCondition);
		//查询包装货区编码
		List<String> goodsAreaTypes=new ArrayList<String>();
		goodsAreaTypes.add(packageGoodsAreaType);
		List<String> goodsAreaCodes=queryGoodsCodeByGoodsType(queryCondition.getOrigOrgCode(),goodsAreaTypes);
		if(goodsAreaCodes!=null&&goodsAreaCodes.size()>0){
			queryCondition.setGoodsAreaCode(goodsAreaCodes.get(0));
			fieldStockMovtionDetailList= fieldStockMovtionDao.queryPackaging(queryCondition, start, limit);
		}
		
		logger.info("查询场内库存[包装库区] 结束时间..."+new Date());
		logger.info("查询场内库存[包装库区] 耗时 ----"+(new Date().getTime()-date.getTime()));
		return fieldStockMovtionDetailList;
	}
	
	/**
	 *@desc 零担中转库区：库存中,非快递货、非包装库区，非派送库区、非偏线库区、非空运库库区、非待叉区,非正在装车
	 *@param queryCondition
	 *@return List<FieldStockMovtionDetailEntity>
	 *@author 105795
	 *@date 2015年3月3日下午2:18:57 
	 */
	public List<FieldStockMovtionDetailEntity> queryTfrStock(QueryConditionStockMovtionDto queryCondition,int start, int limit)
	{
		List<FieldStockMovtionDetailEntity> fieldStockMovtionDetailList=null;
		Date date=new Date();
		logger.info("查询场内库存流动 [零担中转库区] 开始时间..."+date );
		//参数校验
		checkoutCondition(queryCondition);
		/**
		 * 零担中转库区:非包装，非派送，非偏线，非空运库区
		 * */
		//查询包装货区编码
		List<String> tfrAreaTypes=new ArrayList<String>();
		tfrAreaTypes.add(packageGoodsAreaType);
		tfrAreaTypes.add(pickupGoodsAreaType);
		tfrAreaTypes.add(other);
		tfrAreaTypes.add(airGoodsAreaType);
		List<String> goodsAreaCodesTfr=queryGoodsCodeByGoodsType(queryCondition.getOrigOrgCode(),tfrAreaTypes);
		if(CollectionUtils.isNotEmpty(goodsAreaCodesTfr)&&goodsAreaCodesTfr.size()>0){
			queryCondition.setGoodsAreaCodeList(goodsAreaCodesTfr);
		}
		fieldStockMovtionDetailList= fieldStockMovtionDao.queryTfrStock(queryCondition, start, limit);
		
		logger.info("查询场内库存[零担中转库区] 结束时间..."+new Date());
		logger.info("查询场内库存[零担中转库区] 耗时 ----"+(new Date().getTime()-date.getTime()));
		return fieldStockMovtionDetailList;
	}
	
	/**
	 *@desc 零担派送库区：派送库区库存中、非待叉区,非正在装车
	 *@param queryCondition
	 *@return List<FieldStockMovtionDetailEntity>
	 *@author 105795
	 *@date 2015年3月3日下午2:18:57 
	 */
	public List<FieldStockMovtionDetailEntity> queryTfrPickupStock(QueryConditionStockMovtionDto queryCondition,int start, int limit)
	{
		List<FieldStockMovtionDetailEntity> fieldStockMovtionDetailList=null;
		Date date=new Date();
		logger.info("查询场内库存流动 [零担派送库区] 开始时间..."+date );
		//参数校验
		checkoutCondition(queryCondition);
		//查询派送货区编码
		List<String> goodsAreaTypes=new ArrayList<String>();
		goodsAreaTypes.add(pickupGoodsAreaType);
		List<String> goodsAreaCodes=queryGoodsCodeByGoodsType(queryCondition.getOrigOrgCode(),goodsAreaTypes);
		if(goodsAreaCodes!=null&&goodsAreaCodes.size()>0){
			queryCondition.setGoodsAreaCode(goodsAreaCodes.get(0));
			fieldStockMovtionDetailList= fieldStockMovtionDao.queryTfrPickupStock(queryCondition, start, limit);
		}
		
		logger.info("查询场内库存[零担派送库区] 结束时间..."+new Date());
		logger.info("查询场内库存[零担派送库区] 耗时 ----"+(new Date().getTime()-date.getTime()));
		return fieldStockMovtionDetailList;
	}
	
	/**
	 *@desc 快递中转库区库存
	 *@param queryCondition
	 *@return List<FieldStockMovtionDetailEntity>
	 *@author 105795
	 *@date 2015年3月3日下午2:18:57 
	 */
	public List<FieldStockMovtionDetailEntity> queryRcpStock(QueryConditionStockMovtionDto queryCondition,int start, int limit)
	{
		List<FieldStockMovtionDetailEntity> fieldStockMovtionDetailList=null;
		Date date=new Date();
		logger.info("查询场内库存流动 [快递中转库区库存] 开始时间..."+date );
		//参数校验
		checkoutCondition(queryCondition);
		//查询派送货区编码
		List<String> goodsAreaTypes=new ArrayList<String>();
		goodsAreaTypes.add(rcpGoodsAreaType);
		List<String> goodsAreaCodes=queryGoodsCodeByGoodsType(queryCondition.getOrigOrgCode(),goodsAreaTypes);
		if(goodsAreaCodes!=null&&goodsAreaCodes.size()>0){
			queryCondition.setGoodsAreaCode(goodsAreaCodes.get(0));
		}
		fieldStockMovtionDetailList= fieldStockMovtionDao.queryRcpStock(queryCondition, start, limit);
		
		logger.info("查询场内库存[快递中转库区库存] 结束时间..."+new Date());
		logger.info("查询场内库存[快递中转库区库存] 耗时 ----"+(new Date().getTime()-date.getTime()));
		return fieldStockMovtionDetailList;
	}
	
	/**
	 *@desc 快递派送库区库存 
	 *@param queryCondition
	 *@return List<FieldStockMovtionDetailEntity>
	 *@author 105795
	 *@date 2015年3月3日下午2:18:57 
	 */
	public List<FieldStockMovtionDetailEntity> queryRcpPickupStock(QueryConditionStockMovtionDto queryCondition,int start, int limit)
	{
		List<FieldStockMovtionDetailEntity> fieldStockMovtionDetailList=null;
		Date date=new Date();
		logger.info("查询场内库存流动 [快递派送库区库存 ] 开始时间..."+date );
		//参数校验
		checkoutCondition(queryCondition);
		//查询快递派送货区编码
		List<String> goodsAreaTypes=new ArrayList<String>();
		goodsAreaTypes.add(rcpPickupGoodsAreaType);
		List<String> goodsAreaCodes=queryGoodsCodeByGoodsType(queryCondition.getOrigOrgCode(),goodsAreaTypes);
		if(goodsAreaCodes!=null&&goodsAreaCodes.size()>0){
			queryCondition.setGoodsAreaCode(goodsAreaCodes.get(0));
			fieldStockMovtionDetailList= fieldStockMovtionDao.queryRcpPickupStock(queryCondition, start, limit);
		}
		
		logger.info("查询场内库存[快递派送库区库存 ] 结束时间..."+new Date());
		logger.info("查询场内库存[快递派送库区库存 ] 耗时 ----"+(new Date().getTime()-date.getTime()));
		return fieldStockMovtionDetailList;
	}
	
	/**
	 *@desc 正在装车 
	 *@param queryCondition
	 *@return List<FieldStockMovtionDetailEntity>
	 *@author 105795
	 *@date 2015年3月3日下午2:18:57 
	 */
	public List<FieldStockMovtionDetailEntity> queryLoading(QueryConditionStockMovtionDto queryCondition,int start, int limit)
	{
		List<FieldStockMovtionDetailEntity> fieldStockMovtionDetailList=null;
		Date date=new Date();
		logger.info("查询场内库存流动 [正在装车  ] 开始时间..."+date );
		//参数校验
		checkoutCondition(queryCondition);
		fieldStockMovtionDetailList= fieldStockMovtionDao.queryLoading(queryCondition, start, limit);
		
		logger.info("查询场内库存[正在装车 ] 结束时间..."+new Date());
		logger.info("查询场内库存[正在装车 ] 耗时 ----"+(new Date().getTime()-date.getTime()));
		return fieldStockMovtionDetailList;
	}

	
	/**
	 * 参数校验
	 * */
	private void checkoutCondition(QueryConditionStockMovtionDto queryCondition){
		
		if(queryCondition==null)
		{
			throw new TfrBusinessException("货区类型不能为空");
		}
		if(StringUtil.isEmpty(queryCondition.getOrigOrgCode()))
		{
			throw new TfrBusinessException("查询外场编码不能为空");

		}
		
		
	}
	
	/*
	 *com.deppon.foss.module.transfer.platform.api.server.service  
	 *@desc 根据部门编码查询顶级外场
	 *@param 
	 *@return Map<String,String>
	 *@author 105795
	 *@date 2015年3月4日下午2:55:03 
	 */
	public Map<String, String> queryParentTfrCtrCode(String code) {

		Map<String, String> result = null;

		// 调用综合接口判断当前部门是否外场或外场子部门
		List<String> bizTypesList = new ArrayList<String>();

		bizTypesList.add(BizTypeConstants.ORG_TRANSFER_CENTER);

		OrgAdministrativeInfoEntity orgEntity = orgAdministrativeInfoComplexService
				.queryOrgAdministrativeInfoIncludeSelfByCode(code, bizTypesList);
		if (orgEntity != null) {
			result = new HashMap<String, String>();
			result.put("code", orgEntity.getCode());
			result.put("name", orgEntity.getName());
		}

		return result;

	}
	
	
	//set
	/**
	 * @param fieldStockMovtionDao the fieldStockMovtionDao to set
	 */
	public void setFieldStockMovtionDao(IFieldStockMovtionDao fieldStockMovtionDao) {
		this.fieldStockMovtionDao = fieldStockMovtionDao;
	}

	/**
	 * @param orgAdministrativeInfoComplexService the orgAdministrativeInfoComplexService to set
	 */
	public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}
	
	
	
	
}

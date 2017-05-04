/*
 * PROJECT NAME: tfr-load
 * PACKAGE NAME: com.deppon.foss.module.transfer.load.server.service.impl
 * FILE    NAME: PDAExpressPackageService.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.load.server.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.IEmployeeService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IFreightRouteService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.FreightRouteLineDto;
import com.deppon.foss.module.pickup.pricing.dubbo.api.server.service.IProductService;
import com.deppon.foss.module.pickup.pricing.dubbo.api.shared.domain.ProductEntity;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.transfer.common.api.shared.define.TransferConstants;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.load.api.server.dao.IExpressThroughPackagePathDao;
import com.deppon.foss.module.transfer.load.api.server.dao.IPDAExpressPackageDao;
import com.deppon.foss.module.transfer.load.api.server.dao.IPDALoadDao;
import com.deppon.foss.module.transfer.load.api.server.service.IExpressThroughPackagePathService;
import com.deppon.foss.module.transfer.load.api.server.service.IPDACommonService;
import com.deppon.foss.module.transfer.load.api.shared.define.LoadConstants;
import com.deppon.foss.module.transfer.load.api.shared.domain.ExpressPackageDetailEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.ExpressPackageEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.PDATaskEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.PathdetailExtensionEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.ThroughPackageScanWaybillEntity;
import com.deppon.foss.module.transfer.load.api.shared.dto.ExpressPackageGoodsDto;
import com.deppon.foss.module.transfer.pda.api.server.service.IPDAExpressPackageService;
import com.deppon.foss.module.transfer.pda.api.shared.define.TransferPDADictConstants;
import com.deppon.foss.module.transfer.pda.api.shared.dto.ExpressPackageDetailDto;
import com.deppon.foss.module.transfer.pda.api.shared.dto.ExpressPackageDto;
import com.deppon.foss.module.transfer.pda.api.shared.dto.ExpressPackageScanDetailDto;
import com.deppon.foss.module.transfer.scheduling.api.server.dao.ITransportationPathDao;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.TransportPathEntity;
import com.deppon.foss.module.transfer.stock.api.define.StockConstants;
import com.deppon.foss.module.transfer.stock.api.server.service.IStockService;
import com.deppon.foss.module.transfer.stock.api.shared.domain.InOutStockEntity;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 快递包PDA接口
 * @author dp-duyi
 * @date 2013-7-22 上午10:01:42
 */
/**
 * @author wqh
 *
 */
public class PDAExpressPackageService implements IPDAExpressPackageService{
	static final Logger LOGGER = LoggerFactory.getLogger(PDAExpressPackageService.class);
	private IPDAExpressPackageDao pdaExpressPackageDao;
	private IEmployeeService employeeService;
	private IPDALoadDao pdaLoadDao;
	private IFreightRouteService freightRouteService;
	private IWaybillManagerService waybillManagerService;
	@Resource
	private IProductService productService4Dubbo;
	private IStockService stockService;
	private IPDACommonService pdaCommonService;
	//快递直达包
	private IExpressThroughPackagePathService expressThroughPackagePathService;
	private ITransportationPathDao transportationPathDao;//走货路径dao

	
	private IExpressThroughPackagePathDao expressThroughPackagePathDao;
	
	private double air_totalWeight_max=32.0;
	
	public void setFreightRouteService(IFreightRouteService freightRouteService) {
		this.freightRouteService = freightRouteService;
	}
	public void setPdaExpressPackageDao(IPDAExpressPackageDao pdaExpressPackageDao) {
		this.pdaExpressPackageDao = pdaExpressPackageDao;
	}
	public void setEmployeeService(IEmployeeService employeeService) {
		this.employeeService = employeeService;
	}
	public void setPdaLoadDao(IPDALoadDao pdaLoadDao) {
		this.pdaLoadDao = pdaLoadDao;
	}
	public void setWaybillManagerService(
			IWaybillManagerService waybillManagerService) {
		this.waybillManagerService = waybillManagerService;
	}
/*	public void setProductService(IProductService productService) {
		this.productService = productService;
	}*/
	public void setStockService(IStockService stockService) {
		this.stockService = stockService;
	}
	public void setPdaCommonService(IPDACommonService pdaCommonService) {
		this.pdaCommonService = pdaCommonService;
	}
	/** 
	 * 下拉未完成包任务
	 * @author dp-duyi
	 * @date 2013-7-22 上午10:04:30
	 * @see com.deppon.foss.module.transfer.pda.api.server.service.IPDAExpressPackageService#queryUnFinishedPackage(java.lang.String, java.util.Date, java.util.Date)
	 */
	@Override
	public List<ExpressPackageDto> queryUnFinishedPackage(String userCode,
			Date queryStartTime, Date queryEndTime) {
		LOGGER.error("获取快递包指令开始"+userCode);
		List<String> states = new ArrayList<String>();
		states.add(TransferPDADictConstants.EXPRESS_PACKAGE_STATE_PROGRESS);
		states.add(TransferPDADictConstants.EXPRESS_PACKAGE_STATE_UNSTART);
		List<ExpressPackageDto> result = pdaExpressPackageDao.queryPackage(userCode, queryStartTime, queryEndTime, states);
		if(CollectionUtils.isNotEmpty(result)){
			for(ExpressPackageDto l : result){
				LOGGER.error("获取快递包指令"+l.getUserCode()+l.getPackageNo()+l.getStatus()+l.getArriveOrgCode());
			}
		}
		LOGGER.error("获取快递包指令结束"+userCode);
		List<ExpressPackageDto> returnResult=new ArrayList<ExpressPackageDto>();
		//判断是否直达包
		for(ExpressPackageDto dto:result)
		{
			if(dto.getExpressPackageType()!=null&&TransferPDADictConstants.EXPRESS_PACKAGE_TYPE_THROUGH_ARRIVE.equalsIgnoreCase(dto.getExpressPackageType()))
			{
				dto.setIsThrough(FossConstants.YES);
			}else{
				dto.setIsThrough(FossConstants.NO);
			}
			returnResult.add(dto);
		}
		
		
		return returnResult;
	}

	/** 
	 * 创建包任务
	 * @author dp-duyi
	 * @date 2013-7-22 上午10:04:30
	 * @see com.deppon.foss.module.transfer.pda.api.server.service.IPDAExpressPackageService#createPackage(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.util.Date)
	 */
	@Override
	public String createPackage(String packageNo, String origOrgCode,
			String arriveOrgCode, String userCode, String deviceNo,
			Date createTime,String expressPackageType) {
		LOGGER.error("新建快递包开始"+packageNo);
		
		ExpressPackageEntity expressPackage = pdaExpressPackageDao.queryPackageByNo(packageNo);
		if(expressPackage != null){
			throw new TfrBusinessException("该包号已存在!");
		}
		//增加类型校验
		if(StringUtil.isEmpty(expressPackageType))
		{
			throw new TfrBusinessException("包类型为空");
		}
		
		expressPackage = new ExpressPackageEntity();
		OrgAdministrativeInfoEntity arriveOrg = new OrgAdministrativeInfoEntity();
		
		expressPackage.setExpressPackageType(expressPackageType);
		
		if(StringUtils.isBlank(arriveOrgCode)){
			throw new TfrBusinessException("到达部门不能为空");
		}
		//增加判定建包是否为接驳点建包
		if(StringUtils.equals(expressPackageType, "SECONDCAR_ARRIVE")){
			String arriveOrgName = pdaCommonService.queryAccessPointName(arriveOrgCode);
			if(StringUtils.isBlank(arriveOrgName)){
				throw new TfrBusinessException("到达部门不存在!");
			}else{
				arriveOrg.setName(arriveOrgName);
				arriveOrg.setCode(arriveOrgCode);
			}
		}else{
			arriveOrg = pdaCommonService.getTopCurrentOutfieldOrSalesDept(arriveOrgCode);
		}
		
		if(arriveOrg == null){
			throw new TfrBusinessException("到达部门不存在!");
		}else{
			expressPackage.setArriveOrgCode(arriveOrg.getCode());
			expressPackage.setArriveOrgName(arriveOrg.getName());
		}
		OrgAdministrativeInfoEntity origOrg = pdaCommonService.getTopCurrentOutfieldOrSalesDept(origOrgCode);
		if(origOrg == null){
			throw new TfrBusinessException("出发部门不存在!");
		}else{
			expressPackage.setDepartOrgCode(origOrg.getCode());
			expressPackage.setDepartOrgName(origOrg.getName());
		}
		if(arriveOrg.getCode().equals(origOrg.getCode())){
			throw new TfrBusinessException("出发部门和到达部门不能为同一部门");
		}
		EmployeeEntity emp = employeeService.queryEmployeeByEmpCode(userCode);
		expressPackage.setCreateUserCode(userCode);
		if(emp == null){
			throw new TfrBusinessException("创建人不存在!");
		}else{
			expressPackage.setCreateUserName(emp.getEmpName());
		}
		expressPackage.setId(UUIDUtils.getUUID());
		expressPackage.setPackageNo(packageNo);
		expressPackage.setCreateTime(new Date());
		expressPackage.setStatus(TransferPDADictConstants.EXPRESS_PACKAGE_STATE_UNSTART);
		PDATaskEntity pdaEntity = new PDATaskEntity();
		pdaEntity.setTaskNo(packageNo);
		pdaEntity.setDeviceNo(deviceNo);
		pdaEntity.setId(UUIDUtils.getUUID());
		pdaEntity.setJoinTime(createTime);
		pdaEntity.setTaskType(LoadConstants.LOADER_PARTICIPATION_EXPRESS_PACKAGE);
		pdaEntity.setBeCreator(FossConstants.YES);
		LOGGER.error("插入快递包PDA"+expressPackage.getPackageNo());
		pdaLoadDao.insertPDATask(pdaEntity);//插入装车任务表tfr.t_opt_load_task
		LOGGER.error("插入快递包"+expressPackage.getPackageNo());
		int pcount = pdaExpressPackageDao.queryPackageCountByNo(expressPackage.getPackageNo());
		if(pcount >= 1){
			throw new TfrBusinessException("该包号已存在!");
		}
		pdaExpressPackageDao.insertExpressPackage(expressPackage);
		LOGGER.error("新建快递包结束"+expressPackage.getPackageNo());
		return null;
	}

	/** 
	 * 刷新
	 * @author dp-duyi
	 * @date 2013-7-22 上午10:04:30
	 * @see com.deppon.foss.module.transfer.pda.api.server.service.IPDAExpressPackageService#refrushPackageDetail(java.lang.String)
	 */
	@Override
	public List<ExpressPackageDetailDto> refrushPackageDetail(String packageNo) {
		LOGGER.error("快递包刷新开始"+packageNo);
		List<ExpressPackageGoodsDto> packSerialGoods = new ArrayList<ExpressPackageGoodsDto>();
		List<ExpressPackageGoodsDto> packStockSerialGoods = new ArrayList<ExpressPackageGoodsDto>();
		ExpressPackageEntity expressPackage = pdaExpressPackageDao.queryPackageByNo(packageNo);
		//查询库存货物  alfred 2015-09-06
		if(StringUtils.equals(expressPackage.getExpressPackageType(), "SECONDCAR_ARRIVE")){
			//二程接驳建包刷新明细
			packStockSerialGoods = pdaExpressPackageDao.querySCStockPackageGoods(packageNo);
		}else{
			//普通包刷新明细
			packStockSerialGoods = pdaExpressPackageDao.queryStockPackageGoods(packageNo);
		}
		//查询已扫货物
		List<ExpressPackageGoodsDto> packScanSerialGoods = pdaExpressPackageDao.queryScanPackageGoods(packageNo);
		if(CollectionUtils.isNotEmpty(packStockSerialGoods)){
			packSerialGoods.addAll(packStockSerialGoods);
		}
		if(CollectionUtils.isNotEmpty(packScanSerialGoods)){
			packSerialGoods.addAll(packScanSerialGoods);
		}
		//ExpressPackageGoodsDto转换为   ExpressPackageDetailDto
		List<ExpressPackageDetailDto> wayBillGoodsList = new ArrayList<ExpressPackageDetailDto>();
		wayBillGoodsList = refrushUtil(packSerialGoods);
		if(CollectionUtils.isNotEmpty(wayBillGoodsList)&&wayBillGoodsList.size()>0)
		{
			LOGGER.debug("快递包"+packageNo+"刷新"+wayBillGoodsList.size()+"票");
			LOGGER.error("快递包刷新结束"+packageNo);
		}else{
			throw new TfrBusinessException("当前部门 没有刷新到运单!");
		}
		
		return wayBillGoodsList;
	}

	/** 
	 * 多货校验(建包的时候强扫)
	 * @author dp-duyi
	 * @date 2013-7-22 上午10:04:30
	 * @see com.deppon.foss.module.transfer.pda.api.server.service.IPDAExpressPackageService#moreGoodsVerify(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public ExpressPackageDetailDto moreGoodsVerify(String packageNo,
			String wayBillNo, String serialNo) {
		LOGGER.error("快递包多货校验开始"+packageNo+wayBillNo+serialNo);
		ExpressPackageEntity expressPackage = pdaExpressPackageDao.queryPackageByNo(packageNo);
		if(expressPackage != null && (TransferPDADictConstants.EXPRESS_PACKAGE_STATE_UNSTART.equals(expressPackage.getStatus())||TransferPDADictConstants.EXPRESS_PACKAGE_STATE_PROGRESS.equals(expressPackage.getStatus()))){
			
			WaybillEntity wayBill = waybillManagerService.queryWaybillBasicByNo(wayBillNo);
			if(wayBill != null){
				/**
				 * @desc 如果为快递空运包，则强扫必须为商务专递的运单
				 * by wqh
				 * */	
					String expressPackageType=expressPackage.getExpressPackageType();
					String productCode=wayBill.getProductCode();
					
					if(StringUtil.isNotEmpty(expressPackageType)
							&&(expressPackageType.equals(TransferPDADictConstants.EXPRESS_PACKAGE_TYPE_KD_AIR)||
							expressPackageType.equals(TransferPDADictConstants.EXPRESS_PACKAGE_TYPE_AIR_THROUGH_ARRIVE))
							&&StringUtil.isNotEmpty(productCode)
							&&!productCode.equals(TransferConstants.PRODUCT_EXPRESS_AIR)){
						
						LOGGER.error("运单：【"+wayBillNo+"】 不是特准快件,不能强扫！");
						throw new TfrBusinessException("运单：【"+wayBillNo+"】 不是特准快件,不能强扫到快递空运包或空运直达包中！");
					}else if(StringUtil.isNotEmpty(expressPackageType)
							&&(!expressPackageType.equals(TransferPDADictConstants.EXPRESS_PACKAGE_TYPE_KD_AIR)&&
							!expressPackageType.equals(TransferPDADictConstants.EXPRESS_PACKAGE_TYPE_AIR_THROUGH_ARRIVE))
							&&StringUtil.isNotEmpty(productCode)
							&&productCode.equals(TransferConstants.PRODUCT_EXPRESS_AIR)){

						LOGGER.error("运单：【"+wayBillNo+"】 是特准快件,不能强扫到非快递空运包！");
						throw new TfrBusinessException("运单：【"+wayBillNo+"】 是特准快件,不能强扫到非快递空运包！");
					}
				//查询标签是否有效
				Map<String,String> condition = new HashMap<String,String>();
				//运单号
				condition.put("wayBillNo", wayBill.getWaybillNo());
				//流水号
				condition.put("serialNo", serialNo);
				//有效状态
				condition.put("active", FossConstants.ACTIVE);
				//查询货签是否有效
				int valideLabeledCount = pdaLoadDao.queryValidLabeledCount(condition);
				//查询货物是否已签收
				int signedQty = pdaLoadDao.queryWayBillSignedState(condition);
				//货签无效或已签收，则货物状态为无效
				if(valideLabeledCount <= 0 || signedQty > 0 ){
					//无效
					LOGGER.error("快递包多货校验结束"+packageNo+wayBillNo+serialNo);
					throw new TfrBusinessException("无效标签!");
				}
				List<FreightRouteLineDto> freightRouteLineDtoList = freightRouteService.queryFreightRouteBySourceTarget(expressPackage.getDepartOrgCode(), wayBill.getCustomerPickupOrgCode(),
						 wayBill.getProductCode(), new Date());
				if(CollectionUtils.isEmpty(freightRouteLineDtoList)){
					LOGGER.error("快递包多货校验结束"+packageNo+wayBillNo+serialNo);
					throw new TfrBusinessException("走货路径不存在!");
				}else{
					boolean beExtra = true;
					for(FreightRouteLineDto rout : freightRouteLineDtoList){
						if(expressPackage.getArriveOrgCode().equals(rout.getTargetCode())){
							beExtra = false;
						}
					}
					if(!beExtra){
						ExpressPackageDetailDto goods = new ExpressPackageDetailDto();
						goods.setGoodsName(wayBill.getGoodsName());
						goods.setNotes(wayBill.getInnerNotes());
						goods.setPack(wayBill.getGoodsPackage());
						goods.setPackageNo(packageNo);
						goods.setReachOrgCode(wayBill.getCustomerPickupOrgCode());
						goods.setReachOrgName(wayBill.getCustomerPickupOrgName());
						goods.setReceiveOrgCode(wayBill.getReceiveOrgCode());
						goods.setReceiveOrgName(wayBill.getReceiveOrgName());
						List<String> serialNos = new ArrayList<String>();
						serialNos.add(serialNo);
						goods.setSerialNos(serialNos);
						goods.setTransportTypeCode(wayBill.getProductCode());
						ProductEntity product = productService4Dubbo.getProductByCache(wayBill.getProductCode(), null);
						if(product != null){
							goods.setTransportTypeName(product.getName());
						}else{
							LOGGER.error("快递包多货校验结束"+packageNo+wayBillNo+serialNo);
							throw new TfrBusinessException("该运输性质不存在!");
						}
						goods.setVolume(wayBill.getGoodsVolumeTotal().doubleValue());
						goods.setWayBillNo(wayBill.getWaybillNo());
						goods.setWayBillQty(wayBill.getGoodsQtyTotal());
						goods.setWeight(wayBill.getGoodsWeightTotal().doubleValue());
						
						LOGGER.error("快递包多货入库"+packageNo+wayBillNo+serialNo);
						InOutStockEntity inOutStockEntity = new InOutStockEntity();
						inOutStockEntity.setWaybillNO(wayBillNo);
						inOutStockEntity.setSerialNO(serialNo);
						inOutStockEntity.setOrgCode(expressPackage.getDepartOrgCode());
						inOutStockEntity.setInOutStockType(StockConstants.LOAD_MORE_GOODS_IN_STOCK_TYPE);
						inOutStockEntity.setOperatorCode(expressPackage.getCreateUserCode());
						inOutStockEntity.setOperatorName(expressPackage.getCreateUserName());
						stockService.inStockPC(inOutStockEntity);
						LOGGER.error("快递包多货入库"+packageNo+wayBillNo+serialNo);
						
						LOGGER.error("快递包多货校验结束"+packageNo+wayBillNo+serialNo);
						return goods;
					}else{
						LOGGER.error("快递包多货校验结束"+packageNo+wayBillNo+serialNo);
						throw new TfrBusinessException("货物信息目的站不符合，无法扫描!");
					}
				}
				
			
				
			}else{
				LOGGER.error("快递包多货校验结束"+packageNo+wayBillNo+serialNo);
				throw new TfrBusinessException("运单不存在!");
			}
		}else{
			LOGGER.error("快递包多货校验结束"+packageNo+wayBillNo+serialNo);
			throw new TfrBusinessException("无效包");	
		}
	}

	/** 
	 * 包扫描
	 * @author dp-duyi
	 * @date 2013-7-22 上午10:04:30
	 * @see com.deppon.foss.module.transfer.pda.api.server.service.IPDAExpressPackageService#scan(com.deppon.foss.module.transfer.pda.api.shared.dto.ExpressPackageScanDetailDto)
	 */
	@Override
	public void scan(ExpressPackageScanDetailDto scanDto) {
		LOGGER.error("快递包扫描开始"+scanDto.getTaskNo()+scanDto.getWayBillNo()+scanDto.getSerialNo()+scanDto.getType());
		ExpressPackageEntity expressPackage = pdaExpressPackageDao.queryPackageByNo(scanDto.getTaskNo());
		if(expressPackage != null && (TransferPDADictConstants.EXPRESS_PACKAGE_STATE_UNSTART.equals(expressPackage.getStatus())||TransferPDADictConstants.EXPRESS_PACKAGE_STATE_PROGRESS.equals(expressPackage.getStatus()))){
			ExpressPackageDetailEntity goods;
			goods = pdaExpressPackageDao.queryExpressPackageDetail(scanDto.getTaskNo(), scanDto.getWayBillNo(), scanDto.getSerialNo());
			boolean beupdate = true;
			if(goods == null){
				goods = new ExpressPackageDetailEntity();
				goods.setCreateTime(new Date());
				goods.setDeviceNo(scanDto.getDeviceNo());
				goods.setGoodsName(scanDto.getGoodsName());
				goods.setGoodsQty(scanDto.getGoodsQty());
				goods.setGoodsState(scanDto.getType());
				goods.setId(UUIDUtils.getUUID());
				goods.setNotes(scanDto.getNotes());
				goods.setPackageNo(scanDto.getTaskNo());
				goods.setPack(scanDto.getPack());
				goods.setReachOrgName(scanDto.getReachOrgName());
				goods.setRecieveOrgName(scanDto.getReceiveOrgName());
				goods.setScanState(scanDto.getScanState());
				goods.setScanTime(scanDto.getScanTime());
				goods.setSerialNo(scanDto.getSerialNo());
				goods.setTransportTypeCode(scanDto.getTransportTypeCode());
				goods.setTransportTypeName(scanDto.getTransportType());
				goods.setVolume(scanDto.getVolume());
				goods.setWayBillNo(scanDto.getWayBillNo());
				goods.setWeight(scanDto.getWeight());
				try{
					pdaExpressPackageDao.insertExpressPackageDetail(goods);
				}catch(org.springframework.dao.DuplicateKeyException e){
					int updateCount = pdaExpressPackageDao.updateExpressPackageDetail(goods);
					if(updateCount <= 0){
						beupdate = false;
					}
				}
			}else{
				
				goods.setDeviceNo(scanDto.getDeviceNo());
				goods.setScanState(scanDto.getScanState());
				goods.setScanTime(scanDto.getScanTime());
				goods.setGoodsState(scanDto.getType());
				int updateCount = pdaExpressPackageDao.updateExpressPackageDetail(goods);
				if(updateCount <= 0){
					beupdate = false;
				}
			}
			if(beupdate){
				int packageQty = pdaExpressPackageDao.queryPackageQty(scanDto.getTaskNo());
				if(packageQty>0){
					if(!TransferPDADictConstants.EXPRESS_PACKAGE_STATE_PROGRESS.equals(expressPackage.getStatus())){
						expressPackage.setStatus(TransferPDADictConstants.EXPRESS_PACKAGE_STATE_PROGRESS);
						pdaExpressPackageDao.updateExpressPackage(expressPackage);
					}
				}else{
					if(!TransferPDADictConstants.EXPRESS_PACKAGE_STATE_UNSTART.equals(expressPackage.getStatus())){
						expressPackage.setStatus(TransferPDADictConstants.EXPRESS_PACKAGE_STATE_UNSTART);
						pdaExpressPackageDao.updateExpressPackage(expressPackage);
					}
				}
			}
			LOGGER.error("快递包扫描结束"+scanDto.getTaskNo()+scanDto.getWayBillNo()+scanDto.getSerialNo()+scanDto.getType());
		}else{
			LOGGER.error("PDAExpressPackageService.scan(ExpressPackageScanDetailDto) package invaild packageNo="+scanDto.getTaskNo());
			//throw new TfrBusinessException("无效包");
		}
	}

	/** 
	 * 取消快递包
	 * @author dp-duyi
	 * @date 2013-7-22 上午10:04:30
	 * @see com.deppon.foss.module.transfer.pda.api.server.service.IPDAExpressPackageService#cancelPackage(java.lang.String, java.lang.String, java.lang.String, java.util.Date)
	 */
	@Override
	public void cancelPackage(String packageNo, String deviceNo,
			String operatorCode, Date cancelTime) {
		LOGGER.error("取消快递包开始"+packageNo);
		ExpressPackageEntity expressPackage = pdaExpressPackageDao.queryPackageByNo(packageNo);
		if(expressPackage == null){
			throw new TfrBusinessException("该包号不存在!");
		}else if(!TransferPDADictConstants.EXPRESS_PACKAGE_STATE_UNSTART.equals(expressPackage.getStatus())){
			throw new TfrBusinessException("该包状态不为未执行，不能取消!");
		}
		expressPackage.setStatus(TransferPDADictConstants.EXPRESS_PACKAGE_STATE_CANCELED);
		expressPackage.setEndTime(new Date());
		pdaExpressPackageDao.updateExpressPackage(expressPackage);
		//删除包中扫描记录——因为有唯一索引
		pdaExpressPackageDao.deletePackageDetail(packageNo);
		LOGGER.error("取消快递包结束"+packageNo);
	}

	/** 
	 * 提交快递包
	 * @author dp-duyi
	 * @date 2013-7-22 上午10:04:30
	 * @see com.deppon.foss.module.transfer.pda.api.server.service.IPDAExpressPackageService#submitPackage(java.lang.String, java.lang.String, java.lang.String, java.util.Date)
	 */
	@Override
	public void submitPackage(String packageNo, String deviceNo,
			String operatorCode, Date cancelTime) {
		LOGGER.error("提交快递包开始"+packageNo);
		ExpressPackageEntity expressPackage = pdaExpressPackageDao.queryPackageByNo(packageNo);
		if(expressPackage == null){
			throw new TfrBusinessException("该包号不存在!");
		}
		
		//更新库存状态
		List<ExpressPackageDetailEntity> scanDetails= pdaExpressPackageDao.queryScanPackageDetails(packageNo);
		
		if(CollectionUtils.isNotEmpty(scanDetails)){
			for(ExpressPackageDetailEntity scanGoods : scanDetails){//更新库存表tfr.t_opt_stock中的is_package
				LOGGER.error("更新库存是否包扫描"+packageNo+scanGoods.getWayBillNo()+scanGoods.getSerialNo());
				pdaExpressPackageDao.updateStockIsPackage(scanGoods.getWayBillNo(), scanGoods.getSerialNo());
			}
		}
		expressPackage.setStatus(TransferPDADictConstants.EXPRESS_PACKAGE_STATE_FINISHED);
		expressPackage.setEndTime(new Date());
		//统计总件数、总重量、总体积
		ExpressPackageEntity statisticalDate = pdaExpressPackageDao.statisticalData(packageNo);
		if(statisticalDate != null){
			expressPackage.setWeight(statisticalDate.getWeight());
			expressPackage.setVolume(statisticalDate.getVolume());
			expressPackage.setGoodsQty(statisticalDate.getGoodsQty());
			expressPackage.setWaybillQty(statisticalDate.getWaybillQty());
		}
		pdaExpressPackageDao.updateExpressPackage(expressPackage);
		
		// 灰度期间，防止包明细全是foss或ecs导致ecs或foss没有包明细，灰度过后，此代码删除
		// TODO
		if (CollectionUtils.isEmpty(scanDetails)) {
			return;
		}
		
		/**
		 * 如果是快递直达包，则生成直达包走货路由
		 * */
		//判断是否直达包
		if (TransferPDADictConstants.EXPRESS_PACKAGE_TYPE_THROUGH_ARRIVE.equals(expressPackage.getExpressPackageType())) 
		{
			
			expressThroughPackagePathService.createThroughPackagePath(packageNo);
		}
		//如果是空运直达包则生成和建直达包一样的走货路由
		if(TransferPDADictConstants.EXPRESS_PACKAGE_TYPE_AIR_THROUGH_ARRIVE.equals(expressPackage.getExpressPackageType())){
			expressThroughPackagePathService.createThroughPackagePath(packageNo);
		}
		//如果是快递空运包，则重量不能大于32kg
		if(TransferPDADictConstants.EXPRESS_PACKAGE_TYPE_KD_AIR.equals(expressPackage.getExpressPackageType())){
			BigDecimal totalWeight= expressPackage.getWeight();
			BigDecimal maxTotalWeight=new BigDecimal(air_totalWeight_max);
			if(totalWeight!=null&&totalWeight.compareTo(maxTotalWeight)>0){
				throw new TfrBusinessException("建立的快递空运包总重量超过 :"+air_totalWeight_max+" kg!");
			}
			
		}
		LOGGER.error("提交快递包结束"+packageNo);
		
	}
	
	
	/**
	 * @desc 根据运单号下拉所有的到达部门，返回当前部门以后的所有部门给PDA并按路由号排序
	 * @param waybillNo运单号
	 * @param currentOrgCode当前部门code
	 * @return list<Stirng> 
	 * @date 2014-10-13 
	 * @author foss-wqh-105795
	 * */
	@Transactional
	public List<String> obtainThroughPackArriveOrgCode(String packageNo,String waybillNo,String serialNo,String currentOrgCode,String packageType){
		//到达部门集合
		List<String> arriveOrigList=new ArrayList<String>();
		//参数校验
		if(StringUtil.isEmpty(packageNo))
		{
			LOGGER.error("包号为空");
			throw new TfrBusinessException("包号为空");
		}
		ExpressPackageEntity expressPackage = pdaExpressPackageDao.queryPackageByNo(packageNo);
		if(expressPackage != null){
			throw new TfrBusinessException("该包号已存在!");
		}
		if(StringUtil.isEmpty(waybillNo))
		{
			LOGGER.error("运单号为空");
			throw new TfrBusinessException("运单号为空");
		}
		if(StringUtil.isEmpty(currentOrgCode))
		{
			LOGGER.error("当前部门为空");
			throw new TfrBusinessException("当前部门为空");
		}
		
		WaybillEntity wayBillEntity = waybillManagerService.queryWaybillBasicByNo(waybillNo);
		if(wayBillEntity==null){
			LOGGER.error("运单号：{"+waybillNo+"} 不存在");
			throw new TfrBusinessException("运单号：{"+waybillNo+"} 不存在");
		}
		//判断是否为快递货
		if(!FossConstants.YES.equalsIgnoreCase(wayBillEntity.getIsExpress()))
		{
			LOGGER.error("运单号：{"+waybillNo+"} 不是快递货！");
			throw new TfrBusinessException("运单号：{"+waybillNo+"} 不是快递货！");
		}
		//如果是建空运直达包就判断传进来的运单号是不是空运单号
		if("KYZD".equals(packageType.trim())){
			if(wayBillEntity.getProductCode()!=null
					&&!wayBillEntity.getProductCode().equals(TransferConstants.PRODUCT_EXPRESS_AIR)){
				LOGGER.error("你在建空运直达包,运单号:{"+waybillNo+"}不是空运货");
				throw new TfrBusinessException("运单号:{"+waybillNo+"}不是空运货");
			}
		}
		/**
		 * desc 快递空运产品不能建直达包(如果是建空运直达包则可以)
		 * 2015-08-11
		 * by wqh
		 * */
		if(StringUtil.isNotBlank(packageType)&&!"KYZD".equals(packageType.trim())){
			if(wayBillEntity.getProductCode()!=null
					&&wayBillEntity.getProductCode().equals(TransferConstants.PRODUCT_EXPRESS_AIR)){
				LOGGER.error("运单号：{"+waybillNo+"} 是快递空运,不能建立直达包！");
				throw new TfrBusinessException("运单号：{"+waybillNo+"} 是快递空运,不能建立直达包！");
			}
		}
		
		//获取当前部门，如果是外场下面的部门,需要获取
		OrgAdministrativeInfoEntity origOrg = pdaCommonService.getTopCurrentOutfieldOrSalesDept(currentOrgCode);
        if(origOrg==null){
        	LOGGER.error("当前部门不存在");
			throw new TfrBusinessException("当前部门不存在");
        }
        //查询明细中的某一个运单 ，根据运单查询走货路径主表
        TransportPathEntity transportPathEntity=new TransportPathEntity();
        
        transportPathEntity.setWaybillNo(waybillNo);
        List<TransportPathEntity> transportPathList=transportationPathDao.queryTransportPathList(transportPathEntity);
        if(CollectionUtils.isEmpty(transportPathList)){
       	 LOGGER.error(waybillNo+"  ：走货路径不存在");
 			throw new TfrBusinessException(waybillNo+"  ：走货路径不存在");
        }  
		//如果存在多条 
        if(transportPathList.size()>1){
       	 LOGGER.error(waybillNo+"  ：存在多条走货路径");
  			throw new TfrBusinessException(waybillNo+"  ：存在多条走货路径");
        }
      //是否为分批配载
       boolean isOptBatch=false;
        transportPathEntity=transportPathList.get(0);
        if("Y".equals(transportPathEntity.getIfPartialStowage()))
        {
       	 isOptBatch=true;
        }
        PathdetailExtensionEntity pathdetailExtensionEntity=new PathdetailExtensionEntity();
        pathdetailExtensionEntity.setWaybillNo(waybillNo);
        //流水号
        //PathdetailExtensionEntity.setGoodsNo(serialNo);
        //到达部门
        pathdetailExtensionEntity.setOrigOrgCode(origOrg.getCode());
        
        //判断是否分批配载，分批配载取0001的出发部门部门的最大路段
        if(isOptBatch){
        	pathdetailExtensionEntity.setGoodsNo(serialNo);
        }
        PathdetailExtensionEntity maxPathDetailEntity=expressThroughPackagePathDao.queryDepartMinRoutePathDetail(pathdetailExtensionEntity);
        if(maxPathDetailEntity==null)
        {
        	    LOGGER.error(waybillNo+"  ：不存在始发部门为："+origOrg.getName()+"段的走货路径明细");
    			throw new TfrBusinessException(waybillNo+"  ：不存在始发部门为："+origOrg.getName()+"段的走货路径明细");
        }
        
		//查询大于当前路段所有走货路径明细
        List<PathdetailExtensionEntity> pathDetailList=expressThroughPackagePathService.queryAllArriveOrigCodeByWaybillNo(maxPathDetailEntity);
        if(pathDetailList==null||pathDetailList.size()<=0){
        	 LOGGER.error(waybillNo+"  ：不存在走货路径明细");
   			throw new TfrBusinessException(waybillNo+"  ：不存在走货路径明细");
        }
        //判断是否能创建直达包，如果到达部门小于2个，不能建直达包
        if(pathDetailList.size()<2){
        	
        	LOGGER.error(waybillNo+"  ：间隔部门小于2个, 不能建立直达包");
   			throw new TfrBusinessException(waybillNo+"  ：间隔部门小于2个, 不能建立直达包");
        }
        //如果到达部门等于2个，需判断最后一个部门是否为营业部，如果为营业部，则不能建立直达包
        /**
         * 判断运单的流水号在当前部门当前包中是否扫描过，如果第一次扫描记录下来，如果已经扫描过，则更新运单扫描记录
         * */
        
        ThroughPackageScanWaybillEntity throughPackageScanWaybillEntity=new ThroughPackageScanWaybillEntity();
        throughPackageScanWaybillEntity.setPackageNo(packageNo);
        
        List<ThroughPackageScanWaybillEntity> throughPackageScanWaybillList=expressThroughPackagePathDao.queryThroughPackageScanWaybil(throughPackageScanWaybillEntity);
        throughPackageScanWaybillEntity.setId(UUIDUtils.getUUID());
        throughPackageScanWaybillEntity.setScanTime(new Date());
        throughPackageScanWaybillEntity.setWaybillNo(waybillNo);
        throughPackageScanWaybillEntity.setSerialNo(serialNo);
        throughPackageScanWaybillEntity.setOrigCode(origOrg.getCode());
       //是否已经扫描过
        boolean isScaned=true;
        if(throughPackageScanWaybillList==null||throughPackageScanWaybillList.size()<1)
        {
        	isScaned=false;
        	//新增扫描记录
        	expressThroughPackagePathDao.addThroughPackageScanWaybill(throughPackageScanWaybillEntity);
        }
        if(isScaned)
        {
        	//已经扫描过更新
        	throughPackageScanWaybillEntity.setId(throughPackageScanWaybillList.get(0).getId());
        	
        	expressThroughPackagePathDao.updateThroughPackageScanWaybill(throughPackageScanWaybillEntity);
        }
        
        
        
        //将所有的到达部门提供给PDA
        for(int i=0;i<pathDetailList.size();i++){
        	/**
        	 * 这里将当前部门剔除掉
        	 * */
        	//将当前部门放入返回结果中
        	/*if(i==0)
        	{
        		arriveOrigList.add(currentOrgCode);
        	}*/
        	arriveOrigList.add(pathDetailList.get(i).getObjectiveOrgCode());
        }
        
		return arriveOrigList;
	}
	
	
	
	/**
	 * @desc 根据包号下拉直达包明细
	 * @param packageNo包号
	 * @return list<Stirng> 
	 * @date 2014-10-14 
	 * @author foss-wqh-105795
	 * */
	public List<ExpressPackageDetailDto> refrushThroughPackageDetail(String packageNo){
		LOGGER.error("快递直达包刷新明细开始==============="+packageNo);
		long startTime=new Date().getTime();
		LOGGER.error("快递直达包刷新明细开始时间：  "+new Date());
		List<ExpressPackageDetailDto> expressPackageDetailList=new ArrayList<ExpressPackageDetailDto>();
		//校验参数
		ExpressPackageEntity expressPackage = pdaExpressPackageDao.queryPackageByNo(packageNo);
		if(expressPackage == null){
			 LOGGER.error("包 :"+packageNo+" 不存在!");
			throw new TfrBusinessException("包 :"+packageNo+" 不存在!");
		}
		//判断是否直达包
		if (!TransferPDADictConstants.EXPRESS_PACKAGE_TYPE_THROUGH_ARRIVE.equals(expressPackage.getExpressPackageType())) 
		{

			LOGGER.error("不是直达包");
			throw new TfrBusinessException("不是直达包");
			
		}
		/**
		 * 查询库存中且建包没完成的运单
		 * 走货路径如 ：A-B—C-D-E-F，如果建包部门是B，直达包的到达部门是E，则需要将 经过 B-C-D-E 的运单查询出来，这些运单满足 还未扫描提交、库存中
		 * 
		 * */
		LOGGER.error("查询库存快递单开始时间：  "+new Date());
		List<ExpressPackageGoodsDto> packStockGoodsDetail=pdaExpressPackageDao.queryStockThroughPackageDetail(expressPackage);
		LOGGER.error("查询库存快递单结束时间：  "+new Date());
		List<ExpressPackageGoodsDto> packStockGoodsDetailRestltList=new ArrayList<ExpressPackageGoodsDto>();
		
		if(packStockGoodsDetail==null){
			LOGGER.error("直达包: "+packageNo+"  下拉明细失败！当前库存没有经过："+expressPackage.getDepartOrgName()+"-> "
		                  +expressPackage.getArriveOrgName()+"的运单");
			throw new TfrBusinessException("直达包: "+packageNo+"  下拉明细失败！当前库存没有经过："+expressPackage.getDepartOrgName()+"-> "
	                  +expressPackage.getArriveOrgName()+"的运单");
		}
		//通过包号查询扫描过的运单
        ThroughPackageScanWaybillEntity throughPackageScanWaybillEntity=new ThroughPackageScanWaybillEntity();
        throughPackageScanWaybillEntity.setPackageNo(packageNo);
        //根据 包号查找有没有扫描过tfr.t_opt_package_scan_waybill
        List<ThroughPackageScanWaybillEntity> throughPackageScanWaybillList=expressThroughPackagePathDao.queryThroughPackageScanWaybil(throughPackageScanWaybillEntity);
   
		if(throughPackageScanWaybillList==null||throughPackageScanWaybillList.size()<1)
		{
			LOGGER.error("包："+packageNo+" 不存在扫描过的运单");
			throw new TfrBusinessException("包："+packageNo+" 不存在扫描过的运单");
		}
        if(throughPackageScanWaybillList.size()>1)
        {
        	LOGGER.error("存在重复包号："+packageNo);
			throw new TfrBusinessException("存在重复包号："+packageNo);
        }
        //获取包packageNo中的运单
        String waybillNo=throughPackageScanWaybillList.get(0).getWaybillNo();
        //查询扫描运单路径明细
        LOGGER.error("处理运单【 "+waybillNo+" 】走货路径开始时间：  "+new Date());
        List<PathdetailExtensionEntity> pathDetailSourceList=queryPathDetail(expressPackage,waybillNo,throughPackageScanWaybillEntity.getSerialNo());
        LOGGER.error("处理运单【 "+waybillNo+" 】走货路径结束时间：  "+new Date());
        if(pathDetailSourceList==null||pathDetailSourceList.size()==0)
        {
        	LOGGER.error("运单："+waybillNo+" 路径明细为空");
			throw new TfrBusinessException("运单："+waybillNo+" 路径明细为空");
        }
        //出发部门set
        Set<String> departOrgSourSet=new HashSet<String>();
        //到达部门set
        Set<String> arriveOrgSourSet=new HashSet<String>();
        for(int i=0;i<pathDetailSourceList.size();i++)
        {
        	departOrgSourSet.add(pathDetailSourceList.get(i).getOrigOrgCode());
        	arriveOrgSourSet.add(pathDetailSourceList.get(i).getObjectiveOrgCode());

        }
        //最先查出的可以建包的运单号放在waybillNoList中
        List<String> waybillNoList=new ArrayList<String>();
        for(int i=0;i<packStockGoodsDetail.size();i++)
        {
        	String waybillNos=packStockGoodsDetail.get(i).getWayBillNo();
        	if(!waybillNoList.contains(waybillNos))
        	{
        		waybillNoList.add(waybillNos);
        	}
        }
        
        //比较运单集合中的路径明细
        LOGGER.error("查询运单集合【waybillNoList】走货路径开始时间：  "+new Date());
        List<PathdetailExtensionEntity> pathDetailTargetList=
        expressThroughPackagePathService.queryDepartAndArriveOrigCodeByWaybillNos(waybillNoList, expressPackage.getDepartOrgCode(), expressPackage.getArriveOrgCode());
        LOGGER.error("查询运单集合【waybillNoList】走货路径结束时间：  "+new Date());
        if(pathDetailTargetList==null||pathDetailTargetList.size()==0)
        {

			LOGGER.error("当前部门没有满足条件的运单");
		    throw new TfrBusinessException("当前部门没有满足条件的运单");
		
        }
        //先将运单中不存在走货路径的剔除掉
        List<String> waybillNos=new ArrayList<String>();
        for(int i=0;i<waybillNoList.size();i++)
        {
            for(int j=0;j<pathDetailTargetList.size();j++)
            {
            	if(waybillNoList.get(i).equals(pathDetailTargetList.get(j).getWaybillNo())&&!waybillNos.contains(waybillNoList.get(i)))
            	{
            		/*if(waybillNos.contains(waybillNoList.get(i)))
            		{
            			continue;
            		}*/
            		waybillNos.add(waybillNoList.get(i));
            	}else{
            		continue;
            	}
            }
        }
       
        //经过扫描运单的集合
        List<String> resultWaybillList=new ArrayList<String>();
        /**
         * 比较出发部门与到达部门，如果运单集合中存在某个运单与 被比较运单的出发部门、到达部门都一致，则可以判定该运单与被比较运单的在A-B-C-D的走货路径一致
         * 为了不影响性能，目前选择这么做
         * 
         * */
        //出发部门set
        Set<String> departOrgTarSet;
        //到达部门set
        Set<String> arriveOrgTarSet;
       
        //下面程序能保证waybillNos中每个运单在pathDetailTargetList都存在
        for(int i=0;i<waybillNos.size();i++)
        {
        	String waybill=waybillNos.get(i);
        	departOrgTarSet=new HashSet<String>();
        	arriveOrgTarSet=new HashSet<String>();
        	//找出比较运单的所有到达部门、出发部门
            for(int j=0;j<pathDetailTargetList.size();j++)
            {
            	
            	if(waybill.equals(pathDetailTargetList.get(j).getWaybillNo()))
            	{
            		departOrgTarSet.add(pathDetailTargetList.get(j).getOrigOrgCode());
            		arriveOrgTarSet.add(pathDetailTargetList.get(j).getObjectiveOrgCode());
            	}
            }
            //判断waybill是都经过 扫描运单的部门
           if(departOrgTarSet.containsAll(departOrgSourSet)&&arriveOrgTarSet.containsAll(arriveOrgSourSet))
           {
        	   resultWaybillList.add(waybill);
           }
        }
        
        //根据resultWaybillList剔除掉packStockGoodsDetailRestltList的明细
        
        for(int i=0;i<packStockGoodsDetail.size();i++)
        {
        	
        	for(int j=0;j<resultWaybillList.size();j++)
        	{
        		if(packStockGoodsDetail.get(i).getWayBillNo().equals(resultWaybillList.get(j)))
        		{
        			packStockGoodsDetailRestltList.add(packStockGoodsDetail.get(i));
        		}
        	}
        	
        }
		if(packStockGoodsDetailRestltList.size()==0)
		{
			LOGGER.error("直达包: "+packageNo+"  下拉明细失败！没有经过 {"+expressPackage.getDepartOrgName()+"}-> {"+expressPackage.getArriveOrgName()+" }运单");
		    throw new TfrBusinessException("直达包: "+packageNo+"  下拉明细失败！没有经过 {"+expressPackage.getDepartOrgName()+"}-> {"+expressPackage.getArriveOrgName()+"} 运单");
		}
      
		//将 ExpressPackageDetailDto 转换为 ExpressPackageDetailDto
		expressPackageDetailList=refrushUtil(packStockGoodsDetailRestltList);
		LOGGER.error("快递直达包刷新明细结束=======================你刷新了【  "+expressPackageDetailList.size()+" 】条运单");
		LOGGER.error("快递直达包刷新明细结束时间：  "+new Date());
		long endTime=new Date().getTime();
		
		LOGGER.error("本次刷新快递直达包刷新明细总动耗时：  "+(endTime-startTime));

		return expressPackageDetailList;
		
	}

	
	/**查询经过A-B-C-D 运单所有的走货路径明细，其中A为直达包出发部门,D为直达包到达部门*/
   private List<PathdetailExtensionEntity> queryPathDetail(ExpressPackageEntity expressPackage,String waybillNo,String serialNo)
   {
	   
	   //查询明细中的某一个运单 ，根据运单查询走货路径主表
       TransportPathEntity transportPathEntity=new TransportPathEntity();
       
       transportPathEntity.setWaybillNo(waybillNo);
       List<TransportPathEntity> transportPathList=transportationPathDao.queryTransportPathList(transportPathEntity);
       if(transportPathList==null){
      	 LOGGER.error(waybillNo+"  ：走货路径不存在");
			throw new TfrBusinessException(waybillNo+"  ：走货路径不存在");
       }  
		//如果存在多条 
       if(transportPathList.size()>1){
      	 LOGGER.error(waybillNo+"  ：存在多条走货路径");
 			throw new TfrBusinessException(waybillNo+"  ：存在多条走货路径");
       }
       //是否为分批配载
       boolean isOptBatch=false;
       transportPathEntity=transportPathList.get(0);
       if("Y".equals(transportPathEntity.getIfPartialStowage()))
       {
      	 isOptBatch=true;
       }
       PathdetailExtensionEntity pathdetailExtensionEntity=new PathdetailExtensionEntity();
       pathdetailExtensionEntity.setWaybillNo(waybillNo);
       
       //如果是分批配载 ，传入流水号
       if(isOptBatch){
      	
    	   pathdetailExtensionEntity.setGoodsNo(serialNo);
       }
       //当前部门
       pathdetailExtensionEntity.setOrigOrgCode(expressPackage.getDepartOrgCode());
       //查询直达包出发部门为当走货路径的最大路段
       PathdetailExtensionEntity minPathDetailEntity=expressThroughPackagePathDao.queryDepartMinRoutePathDetail(pathdetailExtensionEntity);
       
       if(minPathDetailEntity==null){
      	 LOGGER.error(waybillNo+" 实际走货路径没有经过部门+ "+expressPackage.getDepartOrgName());
 			throw new TfrBusinessException(waybillNo+" 实际走货路径没有经过部门+ "+expressPackage.getDepartOrgName());
      	 
       }
       
       int minRouteNo=Integer.parseInt(minPathDetailEntity.getRouteNo());
       //最终到达部门
       pathdetailExtensionEntity.setObjectiveOrgCode(expressPackage.getArriveOrgCode());
       
       //查询直达包到达部门为走货路径到达部门的最大路段
       PathdetailExtensionEntity maxPathDetailEntity=expressThroughPackagePathDao.queryArriveMaxRoutePathDetail(pathdetailExtensionEntity);
       if(maxPathDetailEntity==null){
      	 LOGGER.error(waybillNo+"  ：实际走货路径没有经过部门："+expressPackage.getArriveOrgName()+" ---");
  			throw new TfrBusinessException(waybillNo+"  ：实际走货路径没有经过部门："+expressPackage.getArriveOrgName()+" ---");
       }
       
       int maxRouteNo=Integer.parseInt(maxPathDetailEntity.getRouteNo());
       
       //存在 出发部门为当前部门的最小路段，直达包到达部门为走货路径到达部门的最大路段 ，查询出两者之间的所有走货路径明细段
       
       String goodsNo=null;
       if(pathdetailExtensionEntity.getGoodsNo()!=null){
      	 goodsNo=pathdetailExtensionEntity.getGoodsNo();
       }
       
       List<PathdetailExtensionEntity> allPathDetailList=expressThroughPackagePathDao.queryAllPathDetailList(waybillNo, goodsNo, minRouteNo, maxRouteNo);
       if(allPathDetailList==null||allPathDetailList.size()<1){
      	 LOGGER.error(waybillNo+"  ：走货路径 { "+minRouteNo+" } 至 "+maxRouteNo+" 路径不存在！");
			throw new TfrBusinessException(waybillNo+"  ：走货路径 { "+minRouteNo+" } 至 "+maxRouteNo+" 路径不存在 ！");
       }
       
       return allPathDetailList;
       
   }	

	
	
	/**
	 * 刷新公用方法
	 * */
	private List<ExpressPackageDetailDto> refrushUtil(List<ExpressPackageGoodsDto> packSerialGoods)
	{
		//定义返回集合
		List<ExpressPackageDetailDto> wayBillGoodsList = null;
		if(CollectionUtils.isNotEmpty(packSerialGoods)){
			wayBillGoodsList = new ArrayList<ExpressPackageDetailDto>();
			for(ExpressPackageGoodsDto serial : packSerialGoods)
			{
				boolean beExists = false;
				if(CollectionUtils.isNotEmpty(wayBillGoodsList))
				{
					for(ExpressPackageDetailDto wayBill : wayBillGoodsList){
						if(serial.getWayBillNo().equals(wayBill.getWayBillNo())){
							beExists = true;
							boolean beExistsSerialNo = false;
							for(String serialNo : wayBill.getSerialNos())
							{
								if(serial.getSeiralNo().equals(serialNo))
								{
									beExistsSerialNo = true;
								}
							}
							if(!beExistsSerialNo)
							{
								wayBill.getSerialNos().add(serial.getSeiralNo());
								wayBill.setStockQty(wayBill.getStockQty()+serial.getStockQty());
								wayBill.setOperateQty(wayBill.getOperateQty()+serial.getOperateQty());
							}
						}
					}
				}
				if(!beExists)
				{
					ExpressPackageDetailDto goods = new ExpressPackageDetailDto();
					goods.setGoodsName(serial.getGoodsName());
					goods.setNotes(serial.getNotes());
					goods.setOperateQty(serial.getOperateQty());
					goods.setPack(serial.getPack());
					goods.setPackageNo(serial.getPackageNo());
					goods.setReachOrgCode(serial.getReachOrgCode());
					goods.setReachOrgName(serial.getReachOrgName());
					goods.setReceiveOrgCode(serial.getReceiveOrgCode());
					goods.setReceiveOrgName(serial.getReceiveOrgName()); 
					goods.setStockQty(serial.getStockQty());
					goods.setTransportTypeCode(serial.getTransportTypeCode());
					goods.setTransportTypeName(serial.getTransportTypeName());
					goods.setVolume(serial.getVolume());
					goods.setWayBillNo(serial.getWayBillNo());
					goods.setWayBillQty(serial.getWayBillQty());
					goods.setWeight(serial.getWeight());
					List<String> serialNos = new ArrayList<String>();
					serialNos.add(serial.getSeiralNo());
					goods.setSerialNos(serialNos);
					goods.setStockQty(serial.getStockQty());
					wayBillGoodsList.add(goods);
				}
			}
			if(CollectionUtils.isNotEmpty(wayBillGoodsList))
			{
				for(ExpressPackageDetailDto g : wayBillGoodsList)
				{
					for(String s : g.getSerialNos())
					{
						LOGGER.debug(g.getPackageNo()+g.getWayBillNo()+s);
					}
				}
			}
		}
		return wayBillGoodsList;
	}
	

	/**
	 * @com.deppon.foss.module.transfer.load.server.service.impl
	 * @desc: 快递空运刷新包明细
	 * @param:packageNo
	 * @wqh
	 * @2015年8月11日上午10:59:58
	 */
	public List<ExpressPackageDetailDto> refrushAirPackageDetail(String packageNo){
		
		LOGGER.error("快递空运刷新明细开始==============="+packageNo);
		long startTime=new Date().getTime();
		LOGGER.error("快递空运刷新明细开始刷新明细开始时间：  "+new Date());
		//校验参数
		ExpressPackageEntity expressPackage = pdaExpressPackageDao.queryPackageByNo(packageNo);
		if(expressPackage == null){
			 LOGGER.error("包 :"+packageNo+" 不存在!");
			throw new TfrBusinessException("包 :"+packageNo+" 不存在!");
		}
		//判断是否空运包
		if (!TransferPDADictConstants.EXPRESS_PACKAGE_TYPE_KD_AIR.equals(expressPackage.getExpressPackageType())) 
		{

			throw new TfrBusinessException("不是快递空运包");
			
		}
		//产品为商务专递
		List<String> transportTypeCodes = new ArrayList<String>();
		transportTypeCodes.add(TransferConstants.PRODUCT_EXPRESS_AIR);
		//访问数据库
		List<ExpressPackageGoodsDto> packStockGoodsDetail=pdaExpressPackageDao.refrushAirPackageDetail(expressPackage.getPackageNo(),expressPackage.getDepartOrgCode(),expressPackage.getArriveOrgCode(),transportTypeCodes);

		if(CollectionUtils.isEmpty(packStockGoodsDetail)){
			 LOGGER.error("包 :"+packageNo+" 下来明细失败");
		}
		
		List<ExpressPackageDetailDto> resultList= refrushUtil(packStockGoodsDetail);
		
	   long endTime=new Date().getTime();
		
		LOGGER.error("本次刷新快递空运包刷新明细总动耗时：  "+(endTime-startTime));
		
		return resultList;
	}
	
	
	/**
	 * 建空运直达包刷新包明细
	 * com.deppon.foss.module.transfer.pda.api.server.service
	 * @param packageNo
	 * rc
	 * 2016年1月23日
	 * @return 包明细ExpressPackageDetailDto
	 */
	@Override
	public List<ExpressPackageDetailDto> resreshAirThroughPackageDetail(
			String packageNo) {
		LOGGER.error("快递空运刷新明细开始===============" + packageNo);
		long startTime = new Date().getTime();
		LOGGER.error("快递空运刷新明细开始刷新明细开始时间：  " + new Date());
		// 校验参数
		ExpressPackageEntity expressPackage = pdaExpressPackageDao
				.queryPackageByNo(packageNo);
		if (expressPackage == null) {
			LOGGER.error("包 :" + packageNo + " 不存在!");
			throw new TfrBusinessException("包 :" + packageNo + " 不存在!");
		}
		// 判断是否空运直达包
		if (!TransferPDADictConstants.EXPRESS_PACKAGE_TYPE_AIR_THROUGH_ARRIVE
				.equals(expressPackage.getExpressPackageType())) {

			throw new TfrBusinessException("不是快递空运直达包");

		}
		List<ExpressPackageDetailDto> expressPackageDetailList = new ArrayList<ExpressPackageDetailDto>();
		/**
		 * 查询库存中且建包没完成的运单 走货路径如 ：A-B—C-D-E-F，如果建包部门是B，直达包的到达部门是E，则需要将 经过 B-C-D-E
		 * 的运单查询出来，这些运单满足 还未扫描提交、库存中
		 * 
		 * */
		LOGGER.error("查询库存快递单开始时间：  " + new Date());
		List<ExpressPackageGoodsDto> packStockGoodsDetail = pdaExpressPackageDao
				.queryStockAirThroughPackageDetail(expressPackage);
		LOGGER.error("查询库存快递单结束时间：  " + new Date());
		List<ExpressPackageGoodsDto> packStockGoodsDetailRestltList = new ArrayList<ExpressPackageGoodsDto>();

		if (packStockGoodsDetail == null) {
			LOGGER.error("直达包: " + packageNo + "  下拉明细失败！当前库存没有经过："
					+ expressPackage.getDepartOrgName() + "-> "
					+ expressPackage.getArriveOrgName() + "的运单");
			throw new TfrBusinessException("直达包: " + packageNo
					+ "  下拉明细失败！当前库存没有经过：" + expressPackage.getDepartOrgName()
					+ "-> " + expressPackage.getArriveOrgName() + "的运单");
		}
		// 通过包号查询扫描过的运单
		ThroughPackageScanWaybillEntity throughPackageScanWaybillEntity = new ThroughPackageScanWaybillEntity();
		throughPackageScanWaybillEntity.setPackageNo(packageNo);

		List<ThroughPackageScanWaybillEntity> throughPackageScanWaybillList = expressThroughPackagePathDao
				.queryThroughPackageScanWaybil(throughPackageScanWaybillEntity);

		if (throughPackageScanWaybillList == null
				|| throughPackageScanWaybillList.size() < 1) {
			LOGGER.error("包：" + packageNo + " 不存在扫描过的运单");
			throw new TfrBusinessException("包：" + packageNo + " 不存在扫描过的运单");
		}
		if (throughPackageScanWaybillList.size() > 1) {
			LOGGER.error("存在重复包号：" + packageNo);
			throw new TfrBusinessException("存在重复包号：" + packageNo);
		}

		// 获取包packageNo中的运单
		String waybillNo = throughPackageScanWaybillList.get(0).getWaybillNo();
		// 查询扫描运单路径明细
		LOGGER.error("处理运单【 " + waybillNo + " 】走货路径开始时间：  " + new Date());
		List<PathdetailExtensionEntity> pathDetailSourceList = queryPathDetail(
				expressPackage, waybillNo,
				throughPackageScanWaybillEntity.getSerialNo());
		LOGGER.error("处理运单【 " + waybillNo + " 】走货路径结束时间：  " + new Date());
		if (pathDetailSourceList == null || pathDetailSourceList.size() == 0) {
			LOGGER.error("运单：" + waybillNo + " 路径明细为空");
			throw new TfrBusinessException("运单：" + waybillNo + " 路径明细为空");
		}

		// 出发部门set
		Set<String> departOrgSourSet = new HashSet<String>();
		// 到达部门set
		Set<String> arriveOrgSourSet = new HashSet<String>();
		for (int i = 0; i < pathDetailSourceList.size(); i++) {
			departOrgSourSet.add(pathDetailSourceList.get(i).getOrigOrgCode());
			arriveOrgSourSet.add(pathDetailSourceList.get(i)
					.getObjectiveOrgCode());

		}

		List<String> waybillNoList = new ArrayList<String>();
		for (int i = 0; i < packStockGoodsDetail.size(); i++) {
			String waybillNos = packStockGoodsDetail.get(i).getWayBillNo();
			if (!waybillNoList.contains(waybillNos)) {
				waybillNoList.add(waybillNos);
			}

		}

		// 比较运单集合中的路径明细
		LOGGER.error("查询运单集合【waybillNoList】走货路径开始时间：  " + new Date());
		List<PathdetailExtensionEntity> pathDetailTargetList = expressThroughPackagePathService
				.queryDepartAndArriveOrigCodeByWaybillNos(waybillNoList,
						expressPackage.getDepartOrgCode(),
						expressPackage.getArriveOrgCode());
		LOGGER.error("查询运单集合【waybillNoList】走货路径结束时间：  " + new Date());
		if (pathDetailTargetList == null || pathDetailTargetList.size() == 0) {

			LOGGER.error("当前部门没有满足条件的运单");
			throw new TfrBusinessException("当前部门没有满足条件的运单");

		}
		// 先将运单中不存在走货路径的剔除掉
		List<String> waybillNos = new ArrayList<String>();
		for (int i = 0; i < waybillNoList.size(); i++) {
			for (int j = 0; j < pathDetailTargetList.size(); j++) {
				if (waybillNoList.get(i).equals(
						pathDetailTargetList.get(j).getWaybillNo())
						&& !waybillNos.contains(waybillNoList.get(i))) {
					/*
					 * if(waybillNos.contains(waybillNoList.get(i))) { continue;
					 * }
					 */
					waybillNos.add(waybillNoList.get(i));
				} else {
					continue;
				}

			}
		}

		// 经过扫描运单的集合
		List<String> resultWaybillList = new ArrayList<String>();

		/**
		 * 比较出发部门与到达部门，如果运单集合中存在某个运单与
		 * 被比较运单的出发部门、到达部门都一致，则可以判定该运单与被比较运单的在A-B-C-D的走货路径一致 为了不影响性能，目前选择这么做
		 * 
		 * */
		// 出发部门set
		Set<String> departOrgTarSet;
		// 到达部门set
		Set<String> arriveOrgTarSet;

		// 下面程序能保证waybillNos中每个运单在pathDetailTargetList都存在
		for (int i = 0; i < waybillNos.size(); i++) {
			String waybill = waybillNos.get(i);
			departOrgTarSet = new HashSet<String>();
			arriveOrgTarSet = new HashSet<String>();
			// 找出比较运单的所有到达部门、出发部门
			for (int j = 0; j < pathDetailTargetList.size(); j++) {

				if (waybill.equals(pathDetailTargetList.get(j).getWaybillNo())) {
					departOrgTarSet.add(pathDetailTargetList.get(j)
							.getOrigOrgCode());
					arriveOrgTarSet.add(pathDetailTargetList.get(j)
							.getObjectiveOrgCode());
				}

			}
			// 判断waybill是都经过 扫描运单的部门
			if (departOrgTarSet.containsAll(departOrgSourSet)
					&& arriveOrgTarSet.containsAll(arriveOrgSourSet)) {
				resultWaybillList.add(waybill);
			}

		}

		// 根据resultWaybillList剔除掉packStockGoodsDetailRestltList的明细

		for (int i = 0; i < packStockGoodsDetail.size(); i++) {

			for (int j = 0; j < resultWaybillList.size(); j++) {
				if (packStockGoodsDetail.get(i).getWayBillNo()
						.equals(resultWaybillList.get(j))) {
					packStockGoodsDetailRestltList.add(packStockGoodsDetail
							.get(i));
				}
			}

		}
		if (packStockGoodsDetailRestltList.size() == 0) {
			LOGGER.error("直达包: " + packageNo + "  下拉明细失败！没有经过 {"
					+ expressPackage.getDepartOrgName() + "}-> {"
					+ expressPackage.getArriveOrgName() + " }运单");
			throw new TfrBusinessException("直达包: " + packageNo
					+ "  下拉明细失败！没有经过 {" + expressPackage.getDepartOrgName()
					+ "}-> {" + expressPackage.getArriveOrgName() + "} 运单");
		}

		// 将 ExpressPackageDetailDto 转换为 ExpressPackageDetailDto
		expressPackageDetailList = refrushUtil(packStockGoodsDetailRestltList);
		LOGGER.error("快递直达包刷新明细结束=======================你刷新了【  "
				+ expressPackageDetailList.size() + " 】条运单");
		LOGGER.error("快递直达包刷新明细结束时间：  " + new Date());
		long endTime = new Date().getTime();

		LOGGER.error("本次刷新快递直达包刷新明细总动耗时：  " + (endTime - startTime));

		return expressPackageDetailList;
	}
	
	
	
	
	
	
	
	
	
	//set and get
	public void setExpressThroughPackagePathService(
			IExpressThroughPackagePathService expressThroughPackagePathService) {
		this.expressThroughPackagePathService = expressThroughPackagePathService;
	}
	public void setTransportationPathDao(
			ITransportationPathDao transportationPathDao) {
		this.transportationPathDao = transportationPathDao;
	}
	/*public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}*/
	public void setExpressThroughPackagePathDao(
			IExpressThroughPackagePathDao expressThroughPackagePathDao) {
		this.expressThroughPackagePathDao = expressThroughPackagePathDao;
	}
	
	
	
}

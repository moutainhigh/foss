package com.deppon.foss.module.transfer.scheduling.server.service.impl;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.base.util.define.BizTypeConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.components.export.excel.ExcelExporter;
import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.framework.server.components.export.excel.ExportSetting;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILineService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IMotorcadeService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISaleDepartmentService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.GoodsAreaEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.LineEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.MotorcadeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.base.dict.api.server.service.IDataDictionaryValueService;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryValueEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.pickup.predeliver.api.shared.define.ArriveSheetConstants;
import com.deppon.foss.module.pickup.sign.api.server.service.ISignDetailService;
import com.deppon.foss.module.pickup.sign.api.shared.dto.StockDto;
import com.deppon.foss.module.pickup.waybill.api.server.service.ILabeledGoodService;
import com.deppon.foss.module.pickup.waybill.shared.domain.LabeledGoodEntity;
import com.deppon.foss.module.transfer.common.api.server.service.ITfrCommonService;
import com.deppon.foss.module.transfer.common.api.shared.define.TfrJobBusinessTypeEnum;
import com.deppon.foss.module.transfer.common.api.shared.domain.TfrJobProcessLogEntity;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.departure.api.server.dao.ITrackingDao;
import com.deppon.foss.module.transfer.departure.api.shared.domain.StockTrackingEntity;
import com.deppon.foss.module.transfer.load.api.server.service.IHandOverBillService;
import com.deppon.foss.module.transfer.load.api.server.service.ITruckTaskService;
import com.deppon.foss.module.transfer.load.api.shared.domain.HandOverBillEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.TruckTaskDetailEntity;
import com.deppon.foss.module.transfer.scheduling.api.define.ForecastConstants;
import com.deppon.foss.module.transfer.scheduling.api.define.TransportPathConstants;
import com.deppon.foss.module.transfer.scheduling.api.server.dao.IExhibitForecastDao;
import com.deppon.foss.module.transfer.scheduling.api.server.service.ICalculateTransportPathService;
import com.deppon.foss.module.transfer.scheduling.api.server.service.IExhibitForecastService;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.ExhibitForecastEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.PathDetailEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.ExhibitForecastDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.ExhibitForecastForWorldDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.ExhibitWaybillDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.util.ConstantsNumberSonar;
import com.deppon.foss.module.transfer.stock.api.shared.domain.InOutStockEntity;
import com.deppon.foss.module.transfer.stock.api.shared.exception.StockException;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;


/**
 * 展会货货量统计
 * @author 200978  xiaobingcheng
 * 2014-11-26
 */
public class ExhibitForecastService implements IExhibitForecastService {
	
	private static final Logger LOG = LoggerFactory.getLogger(ExhibitForecastService.class);
	
	private static final String EXHIBIT_COMPANY_CAR = "Company";
//	private static final String  EXHIBIT_LEASED_CAR = "Leased";
	private static final String EXHIBIT_ONTHEWAY = "ONTHEWAY";
	
	private static final int three = 3;
	
	private IExhibitForecastDao exhibitForecastDao;
	private ITrackingDao trackingDao;
	private ILabeledGoodService labeledGoodService;
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	private ICalculateTransportPathService calculateTransportPathService;
	private ISignDetailService signDetailService;
	private IHandOverBillService handOverBillService;
	private ITruckTaskService truckTaskService;
	private ILineService lineService;
	private ITfrCommonService tfrCommonService;
	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;
	private IMotorcadeService motorcadeService;
	private ISaleDepartmentService saleDepartmentService;
	private IDataDictionaryValueService dataDictionaryValueService;

	
	public void setDataDictionaryValueService(
			IDataDictionaryValueService dataDictionaryValueService) {
		this.dataDictionaryValueService = dataDictionaryValueService;
	}
	public void setSaleDepartmentService(
			ISaleDepartmentService saleDepartmentService) {
		this.saleDepartmentService = saleDepartmentService;
	}
	public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}
	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}
	public void setMotorcadeService(IMotorcadeService motorcadeService) {
		this.motorcadeService = motorcadeService;
	}
	public void setTfrCommonService(ITfrCommonService tfrCommonService) {
		this.tfrCommonService = tfrCommonService;
	}
	public void setLineService(ILineService lineService) {
		this.lineService = lineService;
	}
	public void setTruckTaskService(ITruckTaskService truckTaskService) {
		this.truckTaskService = truckTaskService;
	}
	public void setHandOverBillService(IHandOverBillService handOverBillService) {
		this.handOverBillService = handOverBillService;
	}
	public void setSignDetailService(ISignDetailService signDetailService) {
		this.signDetailService = signDetailService;
	}
	public void setExhibitForecastDao(IExhibitForecastDao exhibitForecastDao) {
		this.exhibitForecastDao = exhibitForecastDao;
	}
	public void setLabeledGoodService(ILabeledGoodService labeledGoodService) {
		this.labeledGoodService = labeledGoodService;
	}
	public void setTrackingDao(ITrackingDao trackingDao) {
		this.trackingDao = trackingDao;
	}
	public void setCalculateTransportPathService(
			ICalculateTransportPathService calculateTransportPathService) {
		this.calculateTransportPathService = calculateTransportPathService;
	}
	
	
	/**
	 * 展会货货量统计 job调用
	 * @Author: 200978  xiaobingcheng
	 * 2014-11-26
	 * @param day    取前day天的展会货
	 * @param scheduledFireTime    job开始执行的时间（当前时间）
	 */
	public void forecastExhibitCargo(int day,Date scheduledFireTime) throws TfrBusinessException{
		LOG.info("展会货统计开始。。。");
		if(day == 0){
			day = ConstantsNumberSonar.SONAR_NUMBER_30;
		}
		if(scheduledFireTime == null){
			scheduledFireTime = new Date();
		}
		//查询以scheduledFireTime开始前day天的没有全部签收，并且提货网点是驻地营业部的展会货运单
		List<ExhibitWaybillDto> waybillList =  exhibitForecastDao.queryExhibitWaybillOfNotSignAndStation(day, scheduledFireTime);
		if(CollectionUtils.isEmpty(waybillList)){
			LOG.error("没有有效的运单");
			return;
		}
		
		/**
		 * 先判断展会货统计中是否存在已经全部签收并且active还是Y的数据，如果有，则将记录设置为无效
		 */
		//查询展会货中所有有效并且已经签收的、正在派送中的、发更改提货网点改为非驻地部门的记录，并设置成无效
		List<String> exhibitWaybillNoList = exhibitForecastDao.querySignedAndActiveExhibit();
		for (String waybillNo : exhibitWaybillNoList) {
			exhibitForecastDao.cancleOldExhibitCargoByWaybillNo(waybillNo);
		}
		
		for (ExhibitWaybillDto waybillEntity : waybillList) {
			try{
				LOG.info("开始展会货统计，运单号："+waybillEntity.getWaybillNo());
				this.forecastExhibitCargoOfWaybill(waybillEntity);
				LOG.info("展会货统计结束，运单号："+waybillEntity.getWaybillNo());
			}catch(BusinessException e){
				LOG.error(e.getStackTrace().toString());
				TfrJobProcessLogEntity jobProcessLogEntity = new TfrJobProcessLogEntity();
				jobProcessLogEntity
						.setBizName(TfrJobBusinessTypeEnum.FORECAST_EXHIBIT_CARGO
								.getBizName());
				jobProcessLogEntity
						.setBizCode(TfrJobBusinessTypeEnum.FORECAST_EXHIBIT_CARGO
								.getBizCode());
				jobProcessLogEntity.setExecBizId("展会货统计失败,运单号："+waybillEntity.getWaybillNo());
				jobProcessLogEntity
						.setRemark("展会货统计失败,运单号："+waybillEntity.getWaybillNo());
				jobProcessLogEntity.setExceptionInfo(ExceptionUtils.getFullStackTrace(e));
				jobProcessLogEntity.setCreateTime(Calendar.getInstance()
						.getTime());
				tfrCommonService.addJobProcessLog(jobProcessLogEntity);
			}catch(Exception e){
				LOG.error(e.getStackTrace().toString());
				TfrJobProcessLogEntity jobProcessLogEntity = new TfrJobProcessLogEntity();
				jobProcessLogEntity
						.setBizName(TfrJobBusinessTypeEnum.FORECAST_EXHIBIT_CARGO
								.getBizName());
				jobProcessLogEntity
						.setBizCode(TfrJobBusinessTypeEnum.FORECAST_EXHIBIT_CARGO
								.getBizCode());
				jobProcessLogEntity.setExecBizId("展会货统计失败,运单号："+waybillEntity.getWaybillNo());
				jobProcessLogEntity
						.setRemark("展会货统计失败,运单号："+waybillEntity.getWaybillNo());
				jobProcessLogEntity.setExceptionInfo(ExceptionUtils.getFullStackTrace(e));
				jobProcessLogEntity.setCreateTime(Calendar.getInstance()
						.getTime());
				tfrCommonService.addJobProcessLog(jobProcessLogEntity);
			}
		}
	}
	
	/**
	 * 根据运单统计展会货
	 * 开单未交接：再开单的营业部库存 属于开单未交接
	 * 在途：未到达最后的驻地外场，也不在开单营业部，则属于在途
	 * 在库：已经到达最终的驻地外场 属于在库
	 * 一个运单可能有多条记录
	 * @Author: 200978  xiaobingcheng
	 * 2014-11-28
	 * @param waybill
	 * @return
	 */
	private void forecastExhibitCargoOfWaybill(ExhibitWaybillDto waybill){
		//先判断原有统计中是否含有这个运单的统计信息，并且为Y，如果有，则设置为无效
		this.checkOldExhibitCargoByWaybillNo(waybill.getWaybillNo());
		
		// 用来存储开单的list，库存的list，交接单的list,签收的list
		List<String> labelSerials = new ArrayList<String>();
		List<String> stockSerials = new ArrayList<String>();
		List<String> signSerials = new ArrayList<String>();
		// 查询开单时的所有的信息
		List<LabeledGoodEntity> labeledGoodList = labeledGoodService
				.queryAllSerialByWaybillNo(waybill.getWaybillNo());
		if (labeledGoodList != null) {
			for (LabeledGoodEntity LabeledGoodEntity : labeledGoodList) {
				if (!labelSerials.contains(LabeledGoodEntity.getSerialNo())) {
					labelSerials.add(LabeledGoodEntity.getSerialNo());
				}
			}
		}
		 
		// 查询所有的库存信息
		InOutStockEntity inOutStockEntity = new InOutStockEntity();
		inOutStockEntity.setWaybillNO(waybill.getWaybillNo());
		
		List<StockTrackingEntity> stocklist = trackingDao
				.queryUniqueStock(inOutStockEntity);
		
		// 查询库存中的部门，groupby 当前部门，下一部门
		List<StockTrackingEntity> stockgrouplist = trackingDao
				.queryUniqueStockNext(inOutStockEntity);
		
		if (stockgrouplist != null && stocklist != null) {
			for (StockTrackingEntity stockTrackingEntity : stockgrouplist) {
			//	int count = 0;
				for (StockTrackingEntity stockEntity : stocklist) {
					if (StringUtils.equals(stockTrackingEntity.getOrgCode(),
							stockEntity.getOrgCode())
							&& StringUtils.equals(
									stockTrackingEntity.getNextOrgCode(),
									stockEntity.getNextOrgCode())) {
						if (!stockSerials.contains(stockEntity.getSerialNO())) {
							stockSerials.add(stockEntity.getSerialNO());
						//	count++;
						}
					}
				}
				if (stockSerials != null) {
					ExhibitForecastEntity exhibitForecastEntity = this.calculateStatusAndPlanArriveTime(stockTrackingEntity,waybill);
					exhibitForecastEntity.setId(UUIDUtils.getUUID());
					exhibitForecastEntity.setWaybillNo(waybill.getWaybillNo());
					exhibitForecastEntity.setGoodsQtyTotal(waybill.getGoodsQtyTotal());
					exhibitForecastEntity.setBillTime(waybill.getBillTime());
					exhibitForecastEntity.setProductCode(waybill.getProductCode());
					exhibitForecastEntity.setProductName(waybill.getProductName());
					exhibitForecastEntity.setReceiveMethod(waybill.getReceiveMethod());
					double waybillVolume =  waybill.getGoodsVolumeTotal().doubleValue() / waybill.getGoodsQtyTotal();
					double waybillWeight = waybill.getGoodsWeightTotal().doubleValue() / waybill.getGoodsQtyTotal();
					exhibitForecastEntity.setWeight(BigDecimal.valueOf(waybillWeight * stockTrackingEntity.getGoodsCount()));
					exhibitForecastEntity.setVolume(BigDecimal.valueOf(waybillVolume * stockTrackingEntity.getGoodsCount()));
					exhibitForecastEntity.setGoodsName(waybill.getGoodsName());
					exhibitForecastEntity.setPacking(waybill.getGoodsPackage());
					exhibitForecastEntity.setCreateTime(new Date());
					exhibitForecastEntity.setActive(FossConstants.YES);
					if(stockTrackingEntity.getSerialNoss()!=null){
						String tempStr = stockTrackingEntity.getSerialNoss();
						//如果太长截取其中一部分
						if(tempStr.length()>ConstantsNumberSonar.SONAR_NUMBER_600){
							tempStr = tempStr.substring(0, ConstantsNumberSonar.SONAR_NUMBER_600) + "...";
						}
						//此时存储的是不同展会货记录的所有流水号，比如分批走A（0001,0002）B（0003），AB不同库存，A的流水号就是0001,0002；此时不是返回的在最终外场的库存
						exhibitForecastEntity.setGoodsNos(tempStr);
					}
					
					LOG.info("插入一条从库存中的记录，流水号："+exhibitForecastEntity.getGoodsNos());
					
					exhibitForecastDao.saveExhibitForecastEntity(exhibitForecastEntity);
				}
			}
		}
		// 如果开单件数等于库存件数，直接返回所有的库存
		if (labelSerials.size() != stockSerials.size()){
			//流水号集合  ---查询专线签收的流水号集合
			StockDto dto =new StockDto();
			dto.setWaybillNo(waybill.getWaybillNo()); //运单号
			dto.setActive(FossConstants.YES); //有效
			dto.setDestroyed(FossConstants.NO); //未作废
			dto.setStatus(ArriveSheetConstants.STATUS_SIGN);//签收
			List<String> temps = new ArrayList<String>();
			temps = signDetailService.querySerialNoByWaybillNo(dto); 
			if(CollectionUtils.isNotEmpty(temps)){
				signSerials.addAll(temps);
			}
			
			// 如果开单件数等于库存件数加签收件数，直接返回
			if (labelSerials.size() != (stockSerials.size() + signSerials
					.size())) {
				
				LOG.info("开单件数不等于库存件数加签收件数！");
				
				// 找到既不在库存中，也不在签收信息中，但是开过单的流水
				labelSerials.removeAll(stockSerials);
				labelSerials.removeAll(signSerials);
				
				
				// orderby出发部门，到达部门查找交接明细
				if (labelSerials.size() > 0) {
					StockTrackingEntity stockTrackingEntity = new StockTrackingEntity();
					stockTrackingEntity.setWaybillNO(waybill.getWaybillNo());
					//已生成交接单德流水号
					Set<String> serialSet = new HashSet<String>();
					//设置已生成交接单德流水号
					for(int i =0; i<labelSerials.size(); i++){
						serialSet.add(labelSerials.get(i));
					}
					//根据运单号，流水号查询交接单
					List<StockTrackingEntity> handovers = new ArrayList<StockTrackingEntity>();//去重的交接单
					List<StockTrackingEntity> handoverlist = new ArrayList<StockTrackingEntity>();
					for (String serialNo : serialSet) {
						List<String> tempList = new ArrayList<String>();
						tempList.add(serialNo);
						stockTrackingEntity.setSerialNos(tempList);
						//查询交接单，因为set进去的流水号只有一个，所以只返回包含一个对象的list
						handoverlist = trackingDao.queryNewHandover(stockTrackingEntity);
						//合并相同交接单号的交接单
						for(StockTrackingEntity entity1:handoverlist)
						{
							boolean flag = true;
							for(StockTrackingEntity entity2:handovers)
							{
								if(StringUtils.equals(entity1.getHandoverNo(),entity2.getHandoverNo()))
								{
									entity2.setGoodsCount(entity2.getGoodsCount()+entity1.getGoodsCount());
									flag = false;
									break;
								}
							}
							if(!flag)
								break;
							handovers.add(entity1);
						}
					}
					
					
					for (StockTrackingEntity StockTrackingEntity : handovers) {
						// 通过交接单号，运单号查找交接单信息
						HandOverBillEntity handOverBillEntity = handOverBillService
								.queryHandOverBillByNo(StockTrackingEntity
										.getHandoverNo());
						if (handOverBillEntity != null) {
							stockTrackingEntity.setHandoverNo(StockTrackingEntity
									.getHandoverNo());
							stockTrackingEntity.setSerialNos(null);
							//根据交接单运单 查询交接流水号信息
							List<String> list = trackingDao
									.querySerialNobyHandoverBill(stockTrackingEntity);
							
							ExhibitForecastEntity exhibitForecastEntity = this
									.calculateStatusAndPlanArriveTimeByHandOverBill(waybill, handOverBillEntity, list);
							exhibitForecastEntity.setId(UUIDUtils.getUUID());
							exhibitForecastEntity.setWaybillNo(waybill.getWaybillNo());
							exhibitForecastEntity.setGoodsQtyTotal(waybill.getGoodsQtyTotal());
							exhibitForecastEntity.setBillTime(waybill.getBillTime());
							exhibitForecastEntity.setProductCode(waybill.getProductCode());
							exhibitForecastEntity.setProductName(waybill.getProductName());
							exhibitForecastEntity.setReceiveMethod(waybill.getReceiveMethod());
							double waybillVolume =  waybill.getGoodsVolumeTotal().doubleValue() / waybill.getGoodsQtyTotal();
							double waybillWeight = waybill.getGoodsWeightTotal().doubleValue() / waybill.getGoodsQtyTotal();
							exhibitForecastEntity.setWeight(BigDecimal.valueOf(waybillWeight * list.size()));
							exhibitForecastEntity.setVolume(BigDecimal.valueOf(waybillVolume * list.size()));
							exhibitForecastEntity.setGoodsName(waybill.getGoodsName());
							exhibitForecastEntity.setPacking(waybill.getGoodsPackage());
							exhibitForecastEntity.setCreateTime(new Date());
							exhibitForecastEntity.setActive(FossConstants.YES);
							//构造流水号序列
							if(!CollectionUtils.isEmpty(list)){
								StringBuilder sb = new StringBuilder();
								for (int i =0;i<list.size();i++) {
									if(i>ConstantsNumberSonar.SONAR_NUMBER_350){
										sb.append("...");
										break;
									}
									sb.append(list.get(i));
									if(i!=list.size()-1){
										sb.append(",");
									}
								}
								exhibitForecastEntity.setGoodsNos(sb.toString());
							}
							
							LOG.info("插入再交接单中的展会货信息，流水号："+exhibitForecastEntity.getGoodsNos());
							
							//插入 到统计表
							exhibitForecastDao.saveExhibitForecastEntity(exhibitForecastEntity);
		 				}
					}
				}
			}
		}
	}

	/**
	 * 计算展会货部分信息    货物状态（开单未交接，在途，在库）,库存件数，入库时间，预计到达时间，最终到达部门
	 * @Author: 200978  xiaobingcheng
	 * 2014-11-30
	 * @param stockTrackingEntity  库存
	 * @param waybill  运单信息
	 * @return
	 */
	private ExhibitForecastEntity calculateStatusAndPlanArriveTime(StockTrackingEntity stockTrackingEntity,ExhibitWaybillDto waybill){
		//返回对象
		ExhibitForecastEntity result = new ExhibitForecastEntity();
		if(StringUtils.isEmpty(stockTrackingEntity.getSerialNoss())){
			throw new TfrBusinessException("库存没有流水号信息："+waybill.getWaybillNo());
		}
		//取库存中的一个流水号
		String []serialNos = stockTrackingEntity.getSerialNoss().split(",");
		String serialNo = serialNos[0];
		//查询走货路径
		List<PathDetailEntity> pathDetails = calculateTransportPathService.queryTotalPath(waybill.getWaybillNo(), serialNo);
		if(CollectionUtils.isEmpty(pathDetails)){
			throw new TfrBusinessException("没有走货路径,运单号："+waybill.getWaybillNo());
		}
		//走货路径条数
		int pathLen = pathDetails.size();
		//取第一条路径
		PathDetailEntity firstPathDetail = pathDetails.get(0);
		//最后一条走货路径
		PathDetailEntity lastPathDetail = pathDetails.get(pathLen-1);
		//倒数第二条走货路径
	//	PathDetailEntity lastSecondPathDetail = pathDetails.get(pathLen-2);
		//设置最终到达部门
		result.setReachOrgCode(lastPathDetail.getObjectiveOrgCode());
		//获取营业部信息
		SaleDepartmentEntity saleDepartment = saleDepartmentService.querySaleDepartmentByCode(lastPathDetail.getObjectiveOrgCode());
		if(saleDepartment == null){
			throw new TfrBusinessException("走货路径不正确,运单号："+waybill.getWaybillNo());
		}
		result.setReachOrgName(saleDepartment.getName());
		//设置最终驻地外场
		result.setTransferCenterCode(saleDepartment.getTransferCenter());
		//取得当前库存部门
		String nowSockOrgCode = stockTrackingEntity.getOrgCode();
		//如果库存在出发营业部
		if(StringUtils.equals(nowSockOrgCode, firstPathDetail.getOrigOrgCode())){
			result.setStockGoodsQty(0);//设置库存件数，在最终外场时，才算在库
			result.setStatus(ForecastConstants.FORECAST_NO_TRANSFER_BILLING);//设置状态为开单未交接
			result.setPlanArriveTime(lastPathDetail.getPlanArriveTime());
		}else if(StringUtils.equals(nowSockOrgCode, lastPathDetail.getOrigOrgCode())){//如果是最终到达外场
			result.setStockGoodsQty(serialNos.length);//设置库存件数，在最终外场时，才算在库
			result.setStatus(ForecastConstants.FORECAST_IN_LIBRARY);//设置状态为在库
			result.setInStockTime(stockTrackingEntity.getInStockTime());
		}else{
			result.setStockGoodsQty(0);//设置库存件数，在最终外场时，才算在库
			result.setStatus(ForecastConstants.FORECAST_IN_TRANSIT);//设置状态为在途
			result.setPlanArriveTime(lastPathDetail.getPlanArriveTime());
		}
		return result;
	}
	
	/**
	 * 计算展会货部分信息    货物状态（开单未交接，在途，在库）  此时只会是在途
	 * 只返回   货物状态，计划到达时间，到达部门编码和名称
	 * @Author: 200978  xiaobingcheng
	 * 2014-12-2
	 * @param waybill
	 * @param handOverBillEntity
	 * @param serialNos   该运单的流水号
	 * @return
	 */
	@SuppressWarnings("static-access")
	private ExhibitForecastEntity calculateStatusAndPlanArriveTimeByHandOverBill(ExhibitWaybillDto waybill,HandOverBillEntity handOverBillEntity,List<String> serialNos){
		//返回对象
		ExhibitForecastEntity result = new ExhibitForecastEntity();
		//只可能是在途的状态
		result.setStatus(ForecastConstants.FORECAST_IN_TRANSIT);
		if(CollectionUtils.isEmpty(serialNos)){
			throw new TfrBusinessException("库存没有流水号信息："+waybill.getWaybillNo()+"\t交接单信息："+handOverBillEntity.getHandOverBillNo());
		}
		//取库存中的一条流水号
		String serialNo = serialNos.get(0);
		//查询走货路径
		List<PathDetailEntity> pathDetails = calculateTransportPathService.queryTotalPath(waybill.getWaybillNo(), serialNo);
		if(CollectionUtils.isEmpty(pathDetails)){
			throw new TfrBusinessException("没有走货路径,运单号："+waybill.getWaybillNo());
		}
		//走货路径条数
		int pathLen = pathDetails.size();
		if(pathLen<2){
			throw new TfrBusinessException("走货路径不全,运单号："+waybill.getWaybillNo());
		}
		//最后一条走货路径
		PathDetailEntity lastPathDetail = pathDetails.get(pathLen-1);
		//倒数第二条走货路径
		PathDetailEntity lastSecondPathDetail = pathDetails.get(pathLen-2);
		//设置最终到达部门
		result.setReachOrgCode(lastPathDetail.getObjectiveOrgCode());
		//获取营业部信息
		SaleDepartmentEntity saleDepartment = saleDepartmentService.querySaleDepartmentByCode(lastPathDetail.getObjectiveOrgCode());
		if(saleDepartment == null){
			throw new TfrBusinessException("走货路径不正确,运单号："+waybill.getWaybillNo());
		}
		result.setReachOrgName(saleDepartment.getName());
		//设置最终驻地外场
		result.setTransferCenterCode(saleDepartment.getTransferCenter());
		//取得交接单的出发部门
		String nowSockOrgCode = handOverBillEntity.getDepartDeptCode();
		result.setPlanArriveTime(lastPathDetail.getPlanArriveTime());
		//如果交接单出发部门是倒数第二个部门(要的是倒数第二个外场)
		if(StringUtils.equals(nowSockOrgCode, lastSecondPathDetail.getOrigOrgCode())){
			// 根据部门编码，获取组织信息
			OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = orgAdministrativeInfoService
					.queryOrgAdministrativeInfoByCodeClean(lastSecondPathDetail.getOrigOrgCode());
			//如果是外场 就继续，否则不执行下面的代码
			if(StringUtils.equals(orgAdministrativeInfoEntity.getTransferCenter(), FossConstants.YES)){
				//如果倒数第二个部门是外场，则如果用户点击了发车确认，则预计到达时间计算方式为    发车时间+运行失效
				//根据单据号查询车辆任务明细
				List<TruckTaskDetailEntity> truckTaskDetailList = truckTaskService.queryTruckTaskDetail(handOverBillEntity.getHandOverBillNo());
				if(truckTaskDetailList.size()>0){
					TruckTaskDetailEntity truckTaskDetailEntity= truckTaskDetailList.get(0);
					//点了发车确认
					if(StringUtils.equals(this.EXHIBIT_ONTHEWAY, truckTaskDetailEntity.getState())){
						Date departTime = null;
						//实际发车时间没有就拿计划发车时间，计划发车时间都没有，那就报错
						if(truckTaskDetailEntity.getActualDepartTime() != null){
							departTime = truckTaskDetailEntity.getActualDepartTime();
						}else if(truckTaskDetailEntity.getPlanDepartTime() != null){
							departTime = truckTaskDetailEntity.getPlanDepartTime();
						}else{
							LOG.error("车辆任务明细没有发车时间,交接单号："+handOverBillEntity.getHandOverBillNo());
						}
						//有发车时间  发车时间 + 运行时效
						if(departTime!=null){
							//查询线路拿到运行时效
							LineEntity line = new LineEntity();
							line.setOrginalOrganizationCode(nowSockOrgCode);
							line.setDestinationOrganizationCode(handOverBillEntity.getArriveDeptCode());
							line.setActive(FossConstants.YES);
							line.setValid(FossConstants.YES);
							line.setIsDefault(FossConstants.YES);
							List<LineEntity> lineList = lineService.queryLineListByCondition(line);
							if(!CollectionUtils.isEmpty(lineList)){
								LineEntity nowLine = lineList.get(0);
								//运行时效变量
								Long aging = 0L;
								//是公司车 就拿卡航时效
								if(StringUtils.equals(truckTaskDetailEntity.getVehicleOwnerType(), this.EXHIBIT_COMPANY_CAR)){
									aging = nowLine.getFastAging();
								}else{
									aging = nowLine.getCommonAging();
								}
								Long planArriveTime = departTime.getTime() + aging*ConstantsNumberSonar.SONAR_NUMBER_3600;
								//设置计划到达时间
								result.setPlanArriveTime(new Date(planArriveTime));
							}
						}
					}
				}else{
					LOG.error("根据单据号没有查询到车辆任务明细,单据号："+handOverBillEntity.getHandOverBillNo());
				}
			}
		}
		return result;
	}
	
	/**
	 * 根据运单号查询原有的有效统计信息，如果存在，则设置为无效
	 * @Author: 200978  xiaobingcheng
	 * 2014-12-3
	 * @param waybill
	 */
	private void checkOldExhibitCargoByWaybillNo(String waybillNo){
		//根据运单号查询统计信息   该运单统计信息的条数
		int result = exhibitForecastDao.queryOldExhibitCargoByWaybillNo(waybillNo);     
		if(result>0){
			//设置原有的有效统计信息为无效
			exhibitForecastDao.cancleOldExhibitCargoByWaybillNo(waybillNo);
		}
	}
	
	
	/**
	 * 根据当前的员工的CODE查找所对应的外场或者营业部
	 * @param org
	 * @return
	 */
	public String findTransforCenter(String org){
		List<String> bizTypes = new ArrayList<String>();
		bizTypes.add(BizTypeConstants.ORG_TRANSFER_CENTER);
//		bizTypes.add(BizTypeConstants.ORG_SALES_DEPARTMENT);
		OrgAdministrativeInfoEntity parentOrg=null;
		
		//查找当前员工的部门实体信息
		OrgAdministrativeInfoEntity orgs = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeClean(org);
		//如果当前员工属于调度组
		if(null != orgs && StringUtils.equals("Y", orgs.getDispatchTeam())){
			//根据当前员工所在部门CODE查找顶级车队
			parentOrg = orgAdministrativeInfoComplexService.getTopFleetByCode(orgs.getCode());
			if(null != parentOrg && StringUtils.isNotEmpty(parentOrg.getCode())){
				//根据顶级车队CODE查找所对应的外场
				MotorcadeEntity motorcade = motorcadeService.queryMotorcadeByCode(parentOrg.getCode());
				if(null != motorcade && StringUtils.isNotEmpty(motorcade.getTransferCenter()))
				//返回外场CODE
				return motorcade.getTransferCenter();
			}
				throw new TfrBusinessException("未找到此部门：" + org + " 所对应的的上级 外场");
		}
		//当前员工不属于调度组的的时候
		parentOrg = orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoIncludeSelfByCode(org, bizTypes);
		if(null == parentOrg || StringUtils.isEmpty(parentOrg.getCode())){
			throw new TfrBusinessException("未找到此部门：" + org + " 所对应的的上级 驻地外场 ");
		}else{
			return parentOrg.getCode();
		}
	}
	
	/**
	 * 根据当前部门和货物状态 查询所有的展会货货量统计
	 * @Author: 200978  xiaobingcheng
	 * 2014-12-5
	 * @param dto    当前部门和货物状态
	 * @return
	 */
	public ExhibitForecastDto queryForecastExhibitList(ExhibitForecastDto dto,int start,int limit){
		ExhibitForecastDto result = new ExhibitForecastDto();
		//设置有效的外场code
		String trancenterCode = null;
		OrgAdministrativeInfoEntity org = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeClean(FossUserContext.getCurrentDept().getCode());
		if(StringUtils.equals(org.getTransferCenter(), FossConstants.YES)){
			trancenterCode = FossUserContext.getCurrentDept().getCode();
		}else{
			trancenterCode = this.findTransforCenter(FossUserContext.getCurrentDept().getCode());
		}
		dto.setTransferCenterCode(trancenterCode);
		//总票数、总重量和总体积初始化
		Integer totalWaybill = new Integer(0);
		BigDecimal weight = BigDecimal.ZERO.setScale(three, RoundingMode.HALF_DOWN);
		BigDecimal volume = BigDecimal.ZERO.setScale(three, RoundingMode.HALF_DOWN);
		//预计两天要到达的和在库的体积总数
		BigDecimal forecastVolume = BigDecimal.ZERO.setScale(three, RoundingMode.HALF_DOWN);
		//库区体积
		BigDecimal goodsAreaVolume = BigDecimal.ZERO.setScale(three, RoundingMode.HALF_DOWN);
		//库存占比
		double ratio = 0.0;
		//临时运单list  去除重复的运单
		List<String> tempWaybillList = new ArrayList<String>();
		//查询所有的展会货信息
		List<ExhibitForecastEntity> list = this.exhibitForecastDao.queryForecastExhibitList(dto,start,limit);
		
		//库存占比开始时间   取当前时间  不要时分秒
		Date startDate = DateUtils.getStartDatetime(new Date());
		//向后加两天, 并以23：:59：:59 结束
		Date endDate = DateUtils.getEndDatetime(startDate, 2);
		for (ExhibitForecastEntity exhibitForecastEntity : list) {
			weight = weight.add(exhibitForecastEntity.getWeight());
			volume = volume.add(exhibitForecastEntity.getVolume());
			if(!tempWaybillList.contains(exhibitForecastEntity.getWaybillNo())){
				totalWaybill += 1;//累加总票数
				tempWaybillList.add(exhibitForecastEntity.getWaybillNo());
			}
			//计算库存占比
			//如果在库
			if(StringUtils.equals(exhibitForecastEntity.getStatus(), ForecastConstants.FORECAST_IN_LIBRARY)){
				forecastVolume = forecastVolume.add(exhibitForecastEntity.getVolume());
			}else{
				Date planArriveTime = exhibitForecastEntity.getPlanArriveTime();
				//如果两天之内到达
				if(planArriveTime.after(startDate) && planArriveTime.before(endDate)){
					forecastVolume = forecastVolume.add(exhibitForecastEntity.getVolume());
				}
			}
		}
		
		//将展会货提货方式和货物状态换成中文
		for (ExhibitForecastEntity exhibit : list) {
			String status = exhibit.getStatus();
			if(StringUtils.equals(status, ForecastConstants.FORECAST_IN_LIBRARY)){
				exhibit.setStatus(ForecastConstants.FORECAST_IN_LIBRARY_NAME);
			}else if(StringUtils.equals(status, ForecastConstants.FORECAST_NO_TRANSFER_BILLING)){
				exhibit.setStatus(ForecastConstants.FORECAST_NO_TRANSFER_BILLING_NAME);
			}else{
				exhibit.setStatus(ForecastConstants.FORECAST_IN_TRANSIT_NAME);
			}
			String receiveMethod = "";
			DataDictionaryValueEntity entity = new DataDictionaryValueEntity();
			entity.setTermsCode("PICKUPGOODSHIGHWAYS");//查询提货网点实体
			entity.setValueCode(exhibit.getReceiveMethod());
			List<DataDictionaryValueEntity>  data = dataDictionaryValueService.queryDataDictionaryValueExactByEntity(entity, 0, Integer.MAX_VALUE);
			if(!CollectionUtils.isEmpty(data)){
				receiveMethod = data.get(0).getValueName();
			}
			exhibit.setReceiveMethod(receiveMethod);
		}
		result.setExhibitList(list);
		
		//查询该外场的派送货区
		List<GoodsAreaEntity> goodsAreas = exhibitForecastDao.queryGoodsAreaByOrgCode(trancenterCode,DictionaryValueConstants.BSE_GOODSAREA_TYPE_STATION);
		if(CollectionUtils.isEmpty(goodsAreas)){
			throw new TfrBusinessException("当前部门查询失败，该部门货所属外场没有派送货区，外场编码："+trancenterCode);
		}
		boolean flag = false;
		for (GoodsAreaEntity goodsAreaEntity : goodsAreas) {
			//货区体积不为空
			if(!StringUtils.equals(goodsAreaEntity.getVolume(), null)&&!StringUtils.equals(goodsAreaEntity.getVolume(), "")){
				goodsAreaVolume = new BigDecimal(goodsAreaEntity.getVolume());
				flag = true;
				break;//拿到体积就返回
			}
		}
		if(!flag){
			throw new TfrBusinessException("当前部门或所属外场对应的派送库区体积为空，无法计算库存占比，请输入库区体积再查询！外场编码："+trancenterCode);
		}
		//计算库存占比
		BigDecimal bigRatio = forecastVolume.divide(goodsAreaVolume,ConstantsNumberSonar.SONAR_NUMBER_5).multiply(new BigDecimal(ConstantsNumberSonar.SONAR_NUMBER_100));
		ratio = bigRatio.doubleValue();
		result.setStockRatio(ratio);
		result.setTotalWaybillQty(totalWaybill);
		result.setTotalWeight(weight);
		result.setTotalVolume(volume);
		return result;
	}
	
	/**
	 * 根据当前部门和货物状态 查询所有的展会货货量统计 de 总记录数
	 * @Author: 200978  xiaobingcheng
	 * 2014-12-5
	 * @param dto    当前部门和货物状态
	 * @return
	 */
	public Long queryForecastExhibitListCount(ExhibitForecastDto dto){
		return this.exhibitForecastDao.queryForecastExhibitListCount(dto);
	}
	
	/**
	 * 生成导出文件名称
	 * @Author: 200978  xiaobingcheng
	 * 2014-12-6
	 * @param fileName
	 * @return
	 * @throws TfrBusinessException
	 */
	public String encodeFileName(String fileName) throws TfrBusinessException {
		try {
			String returnStr;
			String agent = (String) ServletActionContext.getRequest().getHeader("USER-AGENT");
			if (agent != null && agent.indexOf("MSIE") == -1) {
				returnStr = new String(fileName.getBytes("UTF-8"), "iso-8859-1");
			} else {
				returnStr = URLEncoder.encode(fileName, "UTF-8");
			}
			return returnStr;
		} catch (UnsupportedEncodingException e) {
			LOG.error("转换文件名编码失败", e);
			throw new TfrBusinessException(StockException.EXPORT_FILE_ERROR_CODE, "");
		}
	}
	
	/**
	 * 导出展会货统计货量查询到Excel
	 * @Author: 200978  xiaobingcheng
	 * 2014-12-6
	 * @param forecastVO
	 * @return
	 * @throws TfrBusinessException
	 */
	@Transactional
	public InputStream queryStatisticalInquiriesExcelStream(ExhibitForecastDto exhibitForecastDto) throws TfrBusinessException {
		InputStream excelStream = null;
		// 获取导出信息
		ExhibitForecastDto exhibitListDto = this.queryForecastExhibitList(exhibitForecastDto, 0, Integer.MAX_VALUE);
		List<ExhibitForecastEntity> exhibitList = exhibitListDto.getExhibitList();
		// 行List
		List<List<String>> rowexhibitList = new ArrayList<List<String>>();
		for (ExhibitForecastEntity exhibitForecastEntity : exhibitList) {
			// 每行的列List
			List<String> columnList = new ArrayList<String>();
			
			//"运单号",
			if (null != exhibitForecastEntity.getWaybillNo()) {
				columnList.add(exhibitForecastEntity.getWaybillNo());
			} else {
				columnList.add(null);
			}
			//"货物重量(公斤)",
			if (null != exhibitForecastEntity.getWeight()) {
				columnList.add(exhibitForecastEntity.getWeight().toString());
			} else {
				columnList.add(null);
			}
			//"货物体积(方)",
			if (null != exhibitForecastEntity.getVolume()) {
				columnList.add(exhibitForecastEntity.getVolume().toString());
			} else {
				columnList.add(null);
			}
			//"运输性质",
			if (null != exhibitForecastEntity.getProductName()) {
				columnList.add(exhibitForecastEntity.getProductName());
			} else {
				columnList.add(null);
			}
			//"货物名称",
			if (null != exhibitForecastEntity.getGoodsName()) {
				columnList.add(exhibitForecastEntity.getGoodsName());
			} else {
				columnList.add(null);
			}
			//"包装",
			if (null != exhibitForecastEntity.getPacking()) {
				columnList.add(exhibitForecastEntity.getPacking());
			} else {
				columnList.add(null);
			}
			//"库存件数",
			if (null != exhibitForecastEntity.getStockGoodsQty()) {
				columnList.add(exhibitForecastEntity.getStockGoodsQty().toString());
			} else {
				columnList.add(null);
			}
			//"开单件数",
			if (null != exhibitForecastEntity.getGoodsQtyTotal()) {
				columnList.add(exhibitForecastEntity.getGoodsQtyTotal().toString());
			} else {
				columnList.add(null);
			}
			//"开单时间",
			if (null != exhibitForecastEntity.getBillTime()) {
				columnList.add(DateUtils.convert(exhibitForecastEntity.getBillTime(), DateUtils.DATE_TIME_FORMAT));
			} else {
				columnList.add(null);
			}
			//"入库时间",
			if (null != exhibitForecastEntity.getInStockTime()) {
				columnList.add(DateUtils.convert(exhibitForecastEntity.getInStockTime(), DateUtils.DATE_TIME_FORMAT));
			} else {
				columnList.add(null);
			}
			//" 预计到达时间",
			if (null != exhibitForecastEntity.getPlanArriveTime()) {
				columnList.add(DateUtils.convert(exhibitForecastEntity.getPlanArriveTime(), DateUtils.DATE_TIME_FORMAT));
			} else {
				columnList.add(null);
			}
			//" 提货方式",
			if (null != exhibitForecastEntity.getReceiveMethod()) {
				columnList.add(exhibitForecastEntity.getReceiveMethod());
			} else {
				columnList.add(null);
			}
			//" 货物状态",
			if (null != exhibitForecastEntity.getStatus()) {
					columnList.add(exhibitForecastEntity.getStatus());
			} else {
				columnList.add(null);
			}
			//" 到达部门",
			if (null != exhibitForecastEntity.getReachOrgName()) {
				columnList.add(exhibitForecastEntity.getReachOrgName());
			} else {
				columnList.add(null);
			}
			
			rowexhibitList.add(columnList);
		}
		ExportResource exhibit = new ExportResource();
		exhibit.setHeads(TransportPathConstants.FORECASTEXHIBIT_ROW_HEADS);
		if(CollectionUtils.isEmpty(rowexhibitList)){
			rowexhibitList.add(new ArrayList<String>(0));
		}
		exhibit.setRowList(rowexhibitList);
		ArrayList<ExportResource> exportResources = new ArrayList<ExportResource>();
		exportResources.add(exhibit);
		ExportSetting exportSetting = new ExportSetting();
		exportSetting.setSize(TransportPathConstants.SHEET_SIZE);
		ExcelExporter objExcelExportor = new ExcelExporter();
		excelStream = objExcelExportor.exportBySheet(exportResources, exportSetting, new String[] {TransportPathConstants.EXHIBIT_SHEET_NAME});
		return excelStream;
	}
	
	/**
	 * 查询全国外场展会货货量统计信息
	 * @Author: 200978  xiaobingcheng
	 * 2014-12-8
	 * @param transferCenterCode
	 * @return
	 */
	public List<ExhibitForecastForWorldDto> queryForecastExhibitForWorldList(String transferCenterCode){
		List<ExhibitForecastForWorldDto> exhibitForecastForWorldList = this.exhibitForecastDao.queryForecastExhibitForWorldList(transferCenterCode);
		if(CollectionUtils.isEmpty(exhibitForecastForWorldList)){
			return null;
		}
		/**
		 * 此处过滤掉没有驻地派送库区的外场，或者其驻地派送库区体积为空，声明新的list，放置有效数据
		 * shiwei 2015-04-12 PM 18:37
		 */
		List<ExhibitForecastForWorldDto> validDataList = new ArrayList<ExhibitForecastForWorldDto>();
		for (ExhibitForecastForWorldDto exhibitForecastForWorldDto : exhibitForecastForWorldList) {
			//预计两天要到达的和在库的体积总数
			BigDecimal forecastVolume = BigDecimal.ZERO.setScale(three, RoundingMode.HALF_DOWN);
			//库区体积
			BigDecimal goodsAreaVolume = BigDecimal.ZERO.setScale(three, RoundingMode.HALF_DOWN);
			//库存占比
			double ratio = 0.0;
			//库存占比开始时间   取当前时间  不要时分秒
			Date startDate = DateUtils.getStartDatetime(new Date());
			//向后加两天, 并以23：:59：:59 结束
			Date endDate = DateUtils.getEndDatetime(startDate, 2);
			//构造条件
			ExhibitForecastDto dto = new ExhibitForecastDto();
			dto.setActive(FossConstants.YES);
			dto.setTransferCenterCode(exhibitForecastForWorldDto.getTransferCenterCode());
			//查询所有的展会货信息
			List<ExhibitForecastEntity> list = this.exhibitForecastDao.queryForecastExhibitList(dto,0,Integer.MAX_VALUE);
			for (ExhibitForecastEntity exhibitForecastEntity : list) {
				//计算库存占比
				//如果在库
				if(StringUtils.equals(exhibitForecastEntity.getStatus(), ForecastConstants.FORECAST_IN_LIBRARY)){
					forecastVolume = forecastVolume.add(exhibitForecastEntity.getVolume());
				}else{
					Date planArriveTime = exhibitForecastEntity.getPlanArriveTime();
					//如果两天之内到达
					if(planArriveTime.after(startDate) && planArriveTime.before(endDate)){
						forecastVolume = forecastVolume.add(exhibitForecastEntity.getVolume());
					}
				}
			}
			
			List<GoodsAreaEntity> goodsAreas = exhibitForecastDao.queryGoodsAreaByOrgCode(exhibitForecastForWorldDto.getTransferCenterCode(),DictionaryValueConstants.BSE_GOODSAREA_TYPE_STATION);
			if(CollectionUtils.isEmpty(goodsAreas)){
				//throw new TfrBusinessException("当前部门查询失败，该部门货所属外场没有派送货区，外场编码："+exhibitForecastForWorldDto.getTransferCenterCode());
				continue;//跳过无效外场，不中断；
			}
			boolean flag = false;
			for (GoodsAreaEntity goodsAreaEntity : goodsAreas) {
				//货区体积不为空
				if(!StringUtils.equals(goodsAreaEntity.getVolume(), null)&&!StringUtils.equals(goodsAreaEntity.getVolume(), "")){
					goodsAreaVolume = new BigDecimal(goodsAreaEntity.getVolume());
					flag = true;
					break;//拿到体积就返回
				}
			}
			if(!flag){
				//throw new TfrBusinessException("当前部门或所属外场对应的派送库区体积为空，无法计算库存占比，请输入库区体积再查询！外场编码："+exhibitForecastForWorldDto.getTransferCenterCode());
				continue;//跳过无效外场，不中断；
			}
			//计算库存占比
			BigDecimal bigRatio = forecastVolume.divide(goodsAreaVolume,ConstantsNumberSonar.SONAR_NUMBER_5).multiply(new BigDecimal(ConstantsNumberSonar.SONAR_NUMBER_100));
			ratio = bigRatio.doubleValue();
			//设置库存占比
			exhibitForecastForWorldDto.setStockRatio(ratio);
			validDataList.add(exhibitForecastForWorldDto);
		}
		
		//按库存占比进行排序
		List<ExhibitForecastForWorldDto> result = new ArrayList<ExhibitForecastForWorldDto>();
		int len = validDataList.size();
		for(int i=0;i<len;i++){
			double max = -1;
			int temp = 0;
			for(int j=0;j<validDataList.size();j++){
				if(max < validDataList.get(j).getStockRatio()){
					max = validDataList.get(j).getStockRatio();
					temp = j;
				}
			}
			result.add(validDataList.get(temp));
			validDataList.remove(temp);
		}
		
		return result;
	}
	
	/**
	 * 导出全国展会货统计货量查询到Excel
	 * @Author: 200978  xiaobingcheng
	 * 2014-12-6
	 * @param forecastVO
	 * @return
	 * @throws TfrBusinessException
	 */
	@Transactional
	public InputStream queryExhibitForWorldExcelStream(String transferCenterCode) throws TfrBusinessException {
		InputStream excelStream = null;
		// 获取导出信息
		List<ExhibitForecastForWorldDto> exhibitForecastForWorldList = this.queryForecastExhibitForWorldList(transferCenterCode);
		// 行List
		List<List<String>> rowexhibitList = new ArrayList<List<String>>();
		for (ExhibitForecastForWorldDto exhibitForecastForWorldDto : exhibitForecastForWorldList) {
			// 每行的列List
			List<String> columnList = new ArrayList<String>();
			
			//"外场",
			if (null != exhibitForecastForWorldDto.getTransferCenterName()) {
				columnList.add(exhibitForecastForWorldDto.getTransferCenterName());
			} else {
				columnList.add(null);
			}
			//"总票数",
			if (null != exhibitForecastForWorldDto.getTotalWaybillQty()) {
				columnList.add(exhibitForecastForWorldDto.getTotalWaybillQty().toString());
			} else {
				columnList.add(null);
			}
			//"总重量",
			if (null != exhibitForecastForWorldDto.getTotalWeight()) {
				columnList.add(exhibitForecastForWorldDto.getTotalWeight().toString());
			} else {
				columnList.add(null);
			}
			//"总体积",
			if (null != exhibitForecastForWorldDto.getTotalVolume()) {
				columnList.add(exhibitForecastForWorldDto.getTotalVolume().toString());
			} else {
				columnList.add(null);
			}
			//"开单未交接重量",
			if (null != exhibitForecastForWorldDto.getNoBillWeight()) {
				columnList.add(exhibitForecastForWorldDto.getNoBillWeight().toString());
			} else {
				columnList.add(null);
			}
			//"开单未交接体积",
			if (null != exhibitForecastForWorldDto.getNoBillVolume()) {
				columnList.add(exhibitForecastForWorldDto.getNoBillVolume().toString());
			} else {
				columnList.add(null);
			}
			//"开单未交接票数",
			if (null != exhibitForecastForWorldDto.getNoBillWaybillQty()) {
				columnList.add(exhibitForecastForWorldDto.getNoBillWaybillQty().toString());
			} else {
				columnList.add(null);
			}
			//"库存余货重量",
			if (null != exhibitForecastForWorldDto.getStockWeight()) {
				columnList.add(exhibitForecastForWorldDto.getStockWeight().toString());
			} else {
				columnList.add(null);
			}
			//"库存余货体积",
			if (null != exhibitForecastForWorldDto.getStockVolume()) {
				columnList.add(exhibitForecastForWorldDto.getStockVolume().toString());
			} else {
				columnList.add(null);
			}
			//"库存余货票数",
			if (null != exhibitForecastForWorldDto.getStockWaybillQty()) {
				columnList.add(exhibitForecastForWorldDto.getStockWaybillQty().toString());
			} else {
				columnList.add(null);
			}
			//" 在途重量",
			if (null != exhibitForecastForWorldDto.getRunningWeight()) {
				columnList.add(exhibitForecastForWorldDto.getRunningWeight().toString());
			} else {
				columnList.add(null);
			}
			//" 在途体积",
			if (null != exhibitForecastForWorldDto.getRunningVolume()) {
				columnList.add(exhibitForecastForWorldDto.getRunningVolume().toString());
			} else {
				columnList.add(null);
			}
			//" 在途票数",
			if (null != exhibitForecastForWorldDto.getRunningWaybillQty()) {
					columnList.add(exhibitForecastForWorldDto.getRunningWaybillQty().toString());
			} else {
				columnList.add(null);
			}
			//" 库存占比",
			if (0 != exhibitForecastForWorldDto.getStockRatio()) {
				columnList.add(String.valueOf(exhibitForecastForWorldDto.getStockRatio())+"%");
			} else {
				columnList.add("0");
			}
			
			rowexhibitList.add(columnList);
		}
		ExportResource exhibit = new ExportResource();
		exhibit.setHeads(TransportPathConstants.FORECASTEXHIBITFORWORLD_ROW_HEADS);
		if(CollectionUtils.isEmpty(rowexhibitList)){
			rowexhibitList.add(new ArrayList<String>(0));
		}
		exhibit.setRowList(rowexhibitList);
		ArrayList<ExportResource> exportResources = new ArrayList<ExportResource>();
		exportResources.add(exhibit);
		ExportSetting exportSetting = new ExportSetting();
		exportSetting.setSize(TransportPathConstants.SHEET_SIZE);
		ExcelExporter objExcelExportor = new ExcelExporter();
		excelStream = objExcelExportor.exportBySheet(exportResources, exportSetting, new String[] {TransportPathConstants.EXHIBITFORWORLD_SHEET_NAME});
		return excelStream;
	}
	
}

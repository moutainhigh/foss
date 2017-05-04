package com.deppon.foss.module.transfer.stock.server.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.base.util.define.BizTypeConstants;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOutfieldService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OutfieldEntity;
import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.base.dict.api.server.service.IDataDictionaryValueService;
import com.deppon.foss.module.base.dict.api.shared.define.ConfigurationParamsConstants;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;
import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryValueEntity;
import com.deppon.foss.module.pickup.pricing.dubbo.api.server.service.IProductService;
import com.deppon.foss.module.pickup.pricing.dubbo.api.shared.domain.ProductEntity;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.transfer.common.api.server.service.IFOSSToOAService;
import com.deppon.foss.module.transfer.common.api.server.service.ITfrCommonService;
import com.deppon.foss.module.transfer.common.api.shared.define.TfrJobBusinessTypeEnum;
import com.deppon.foss.module.transfer.common.api.shared.define.TransferConstants;
import com.deppon.foss.module.transfer.common.api.shared.domain.TfrJobProcessLogEntity;
import com.deppon.foss.module.transfer.common.api.shared.dto.OaReportClearless;
import com.deppon.foss.module.transfer.common.api.shared.dto.ResponseDto;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.scheduling.api.shared.util.ConstantsNumberSonar;
import com.deppon.foss.module.transfer.stock.api.define.StockConstants;
import com.deppon.foss.module.transfer.stock.api.server.dao.IErrorLoseStartingDao;
import com.deppon.foss.module.transfer.stock.api.server.service.IErrorLoseStartingService;
import com.deppon.foss.module.transfer.stock.api.server.service.IStockService;
import com.deppon.foss.module.transfer.stock.api.shared.domain.InOutStockEntity;
import com.deppon.foss.module.transfer.stock.api.shared.domain.StockEntity;
import com.deppon.foss.module.transfer.stock.api.shared.domain.WaybillStockEntity;
import com.deppon.foss.module.transfer.stock.api.shared.dto.ErrorLoseStartingDto;
import com.deppon.foss.module.transfer.unload.api.server.service.IUnloadService;
import com.deppon.foss.module.transfer.unload.api.shared.define.UnloadConstants;
import com.deppon.foss.module.transfer.unload.api.shared.domain.LackGoodsFoundEntity;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;


/**
* @description 出发丢货上报OA的Service;发货部门在库时长超过3天
* @version 1.0
* @author 14022-foss-songjie
* @update 2014年12月22日 下午2:00:41
*/
public class ErrorLoseStartingService implements IErrorLoseStartingService {
	private static final Logger LOGGER = LoggerFactory.getLogger(ErrorLoseStartingService.class);
	
	/**
	 * 出发丢货上报OA的Dao
	* @fields errorLoseStartingDao
	* @author 14022-foss-songjie
	* @update 2015年1月4日 下午3:08:01
	* @version V1.0
	*/
	private IErrorLoseStartingDao errorLoseStartingDao;
	private IStockService stockService;
	
	private IFOSSToOAService fossToOAService;
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	private IConfigurationParamsService configurationParamsService;
	//调用卸车接口，用于其他部门少货找到处理
	private IUnloadService unloadService;
	@Resource
	private IProductService productService4Dubbo;
	private IProductService productService;
	private ITfrCommonService tfrCommonService;
	/** 综合管理 组织信息 Service*/
	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;
	
	private IWaybillManagerService waybillManagerService;
	private IOutfieldService outfieldService;
	private IDataDictionaryValueService dataDictionaryValueService;

	
	
	



	public void setErrorLoseStartingDao(IErrorLoseStartingDao errorLoseStartingDao) {
		this.errorLoseStartingDao = errorLoseStartingDao;
	}

	public void setStockService(IStockService stockService) {
		this.stockService = stockService;
	}

	public void setFossToOAService(IFOSSToOAService fossToOAService) {
		this.fossToOAService = fossToOAService;
	}

	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

	public void setConfigurationParamsService(
			IConfigurationParamsService configurationParamsService) {
		this.configurationParamsService = configurationParamsService;
	}

	public void setUnloadService(IUnloadService unloadService) {
		this.unloadService = unloadService;
	}
//
//	public void setProductService(IProductService productService) {
//		this.productService = productService;
//	}

	public void setTfrCommonService(ITfrCommonService tfrCommonService) {
		this.tfrCommonService = tfrCommonService;
	}

	public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}

	public void setWaybillManagerService(
			IWaybillManagerService waybillManagerService) {
		this.waybillManagerService = waybillManagerService;
	}
	
	

	public void setOutfieldService(IOutfieldService outfieldService) {
		this.outfieldService = outfieldService;
	}
	
	

	public void setDataDictionaryValueService(
			IDataDictionaryValueService dataDictionaryValueService) {
		this.dataDictionaryValueService = dataDictionaryValueService;
	}

	/**
	* @description 查找出发部门在库时间超过3的运单；上报oa丢货
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.stock.api.server.service.IErrorLoseStartingService#queryStockOvertime(java.util.Date, int, int)
	* @author 14022-foss-songjie
	* @update 2014年12月22日 下午2:32:42
	* @version V1.0
	*/
	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public void execStockOvertime(Date doTime, int threadNo,
			int threadCount) {
		//1:查询在库时间超过3天，并且库存所在部门和运单的收货部门部门一致
		//2:上报OA 出发丢货
		//3:记录oa上报的差错编号，运单号等，方便少货找到
		//4:进行入特殊库处理
		//Date findTime = new Date();
		List<ErrorLoseStartingDto> loseList = errorLoseStartingDao.findStockOverTime(DateUtils.addDayToDate(doTime, -3));
		List<ErrorLoseStartingDto> lackWaybillNoList = new ArrayList<ErrorLoseStartingDto>(); 
		Map<String,List<StockEntity>> stockSerialNOList = new HashMap<String, List<StockEntity>>();
		Map<String,String> oaErrorCodes = new HashMap<String,String>();
		for (ErrorLoseStartingDto errorLoseStartingDto : loseList) {
			try{
				//获取运单信息
				WaybillEntity waybillEntity = waybillManagerService.queryWaybillBasicByNo(errorLoseStartingDto.getWaybillNO());
				WaybillStockEntity waybillStockEntity = new WaybillStockEntity();
				waybillStockEntity.setWaybillNO(errorLoseStartingDto.getWaybillNO());
				waybillStockEntity.setOrgCode(errorLoseStartingDto.getOrgCode());
				//运单明细
				List<StockEntity> stockDetailList = stockService.queryStock(waybillStockEntity);
				if(waybillEntity != null){
					//上报少货
					ResponseDto responseDto = this.reportOALessGoods(waybillEntity,errorLoseStartingDto,stockDetailList);
					//进行入特殊库处理
					if(StringUtils.isNotBlank(responseDto.getErrorsNo())){
						lackWaybillNoList.add(errorLoseStartingDto);
						stockSerialNOList.put(errorLoseStartingDto.getWaybillNO(), stockDetailList);
						//oa差错编号
						oaErrorCodes.put(errorLoseStartingDto.getWaybillNO(), responseDto.getErrorsNo());
						LOGGER.info("---出发丢货上报; 单号为"+ errorLoseStartingDto.getWaybillNO() + "OA差错编号为："+ responseDto.getErrorsNo()+ "更新全部完成---");
					}else {
						//若上报失败，更新异常信息为备注信息，且记录日志
						String message = responseDto.getMessage();
						if(StringUtils.isNotEmpty(message) && message.length() > ConstantsNumberSonar.SONAR_NUMBER_300) {
							message = message.substring(0, ConstantsNumberSonar.SONAR_NUMBER_300);
						}
						//记录上报失败日志
						unloadService.addReportOaErrorLog("出发丢货",errorLoseStartingDto.getWaybillNO(),TransferConstants.REPORT_TYPE_LS,TransferConstants.LOSE_STARTING,message);
					}
				}

				
			}catch(Exception e){
				LOGGER.error(ExceptionUtils.getFullStackTrace(e));
				
				TfrJobProcessLogEntity jobProcessLogEntity = new TfrJobProcessLogEntity();
				jobProcessLogEntity.setBizName(TfrJobBusinessTypeEnum.LOSE_STARTING_OA_ERROR.getBizName());
				jobProcessLogEntity.setBizCode(TfrJobBusinessTypeEnum.LOSE_STARTING_OA_ERROR.getBizCode());
				jobProcessLogEntity.setRemark("调用OA接口出错！,运单编号为：" + errorLoseStartingDto.getWaybillNO());
				jobProcessLogEntity.setExceptionInfo(ExceptionUtils.getFullStackTrace(e));
				jobProcessLogEntity.setCreateTime(Calendar.getInstance().getTime());
				
				tfrCommonService.addJobProcessLog(jobProcessLogEntity);
			}

			
			//对于已成功上报的OA差错单对应的货件，"少货"性质的需要逐件进行入特殊库处理
			for(ErrorLoseStartingDto dto: lackWaybillNoList){
				
				List<StockEntity> detailList = stockSerialNOList.get(dto.getWaybillNO());
				
				for (StockEntity stockEntity : detailList) {
					try{
						InOutStockEntity stockDto = new InOutStockEntity();
						stockDto.setWaybillNO(dto.getWaybillNO());
						stockDto.setSerialNO(stockEntity.getSerialNO());
						stockDto.setOrgCode(dto.getOrgCode());
						stockDto.setOperatorName("出发丢货");
						stockDto.setOperatorCode("Lose_Starting");
						stockDto.setInOutStockType(StockConstants.LOSE_GOODS_IN_STOCK_TYPE);
						stockDto.setInOutStockBillNO("出发丢货");
						stockService.inStockRequiresNewTransactional(stockDto);
					}catch(Exception e){
						LOGGER.error(ExceptionUtils.getFullStackTrace(e));
						
						TfrJobProcessLogEntity jobProcessLogEntity = new TfrJobProcessLogEntity();
						jobProcessLogEntity.setBizName(TfrJobBusinessTypeEnum.LOSE_STARTING_OA_ERROR.getBizName());
						jobProcessLogEntity.setBizCode(TfrJobBusinessTypeEnum.LOSE_STARTING_OA_ERROR.getBizCode());
						jobProcessLogEntity.setRemark("出发丢货上报OA差异后入库出错！deptCode: " + dto.getOrgCode() + "waybillNo:" + dto.getWaybillNO() + ", serialNo:" + stockEntity.getSerialNO());
						jobProcessLogEntity.setExceptionInfo(ExceptionUtils.getFullStackTrace(e));
						jobProcessLogEntity.setCreateTime(Calendar.getInstance().getTime());
						
						tfrCommonService.addJobProcessLog(jobProcessLogEntity);
					}
				}
				
				
			}
			
			//插入数据到临时表，用于其他部门少货找到
			for(ErrorLoseStartingDto dto: lackWaybillNoList){
				List<StockEntity> detailList = stockSerialNOList.get(dto.getWaybillNO());
				
				for (StockEntity stockEntity : detailList) {
					try{
						//上报OA少货差错后，新增数据到临时表
						LackGoodsFoundEntity foundEntity = new LackGoodsFoundEntity();
						foundEntity.setId(UUIDUtils.getUUID());
						foundEntity.setWaybillNo(dto.getWaybillNO());
						foundEntity.setSerialNo(stockEntity.getSerialNO());
						foundEntity.setOaErrorNo(oaErrorCodes.get(dto.getWaybillNO()));
						foundEntity.setLackGoodsOrgCode(dto.getOrgCode());
						foundEntity.setReportType(TransferConstants.REPORT_TYPE_LS);
						foundEntity.setReportId("Lose_Starting");
						foundEntity.setReportOATime(new Date());
						unloadService.addLackGoodsFoundInfo(foundEntity);
						LOGGER.info("---出发丢货新增临时表数据成功,单号为："+ dto.getWaybillNO() + ",流水号为："+ stockEntity.getSerialNO() +",OA差错编号为：" + oaErrorCodes.get(dto.getWaybillNO()));
					}catch(Exception e){
						LOGGER.error(ExceptionUtils.getFullStackTrace(e));
						TfrJobProcessLogEntity jobProcessLogEntity = new TfrJobProcessLogEntity();
						jobProcessLogEntity.setBizName(TfrJobBusinessTypeEnum.LOSE_STARTING_OA_ERROR.getBizName());
						jobProcessLogEntity.setBizCode(TfrJobBusinessTypeEnum.LOSE_STARTING_OA_ERROR.getBizCode());
						jobProcessLogEntity.setRemark("出发丢货上报OA差异后新增临时表出错！deptCode: " + dto.getOrgCode() + "waybillNo:" + dto.getWaybillNO() + ", serialNo:" + stockEntity.getSerialNO());
						jobProcessLogEntity.setExceptionInfo(ExceptionUtils.getFullStackTrace(e));
						jobProcessLogEntity.setCreateTime(Calendar.getInstance().getTime());
						tfrCommonService.addJobProcessLog(jobProcessLogEntity);
					}
				}
			}
			
		}
	}
	
	/**
	* @description 上报oa
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2015年1月5日 上午10:19:35
	*/
	private ResponseDto reportOALessGoods(WaybillEntity waybillEntity,ErrorLoseStartingDto errorLoseStartingDto,List<StockEntity> stockDetailList){
		OaReportClearless lessDto = new OaReportClearless();
		lessDto.setWayBillId(waybillEntity.getWaybillNo());
		lessDto.setReportTime(Calendar.getInstance().getTime());
		//运单transport_type可为空
		if(StringUtils.isEmpty(waybillEntity.getTransportType())) {
			//运输类型为空时，根据运输性质查询对应的一级产品
			ProductEntity entity = productService4Dubbo.getProductLele(waybillEntity.getProductCode(),null,1);
			if(entity != null) {
				lessDto.setTransportType(entity.getName());
			}
		}else {
			lessDto.setTransportType(productService4Dubbo.getProductByCache(waybillEntity.getTransportType(), null).getName());		//运输类型
		}
		lessDto.setReturnBillType(tfrCommonService.queryDictNameByCode(DictionaryConstants.RETURN_BILL_TYPE, waybillEntity.getReturnBillType()));
		lessDto.setShipper(waybillEntity.getDeliveryCustomerContact());
		lessDto.setTransportProduct(productService4Dubbo.getProductByCache(waybillEntity.getProductCode(), null).getName());	//产品类型
		lessDto.setStowageType("");			//配载类型
		if(StringUtils.isNotEmpty(waybillEntity.getReceiveCustomerPhone())){
			lessDto.setReceiverTel(waybillEntity.getReceiveCustomerPhone());
		}else{
			lessDto.setReceiverTel(waybillEntity.getReceiveCustomerMobilephone());
		}
		lessDto.setGroupSendFlag(tfrCommonService.queryDictNameByCode(DictionaryConstants.PICKUP_GOODS, waybillEntity.getReceiveMethod()));		//提货方式
		lessDto.setRemark(waybillEntity.getTransportationRemark());
		if(null != waybillEntity.getGoodsWeightTotal()) {
			lessDto.setWeight(waybillEntity.getGoodsWeightTotal().doubleValue());
		} else {
			lessDto.setWeight(Double.valueOf(0));
		}
		if(null != waybillEntity.getGoodsVolumeTotal()) {
			lessDto.setVloume(waybillEntity.getGoodsVolumeTotal().doubleValue());
		} else {
			lessDto.setVloume(Double.valueOf(0));
		}
		lessDto.setGoods(waybillEntity.getGoodsName());
		//lessDto.setSendTime(com.deppon.foss.util.DateUtils.convert(waybillEntity.getPreDepartureTime(), "yyyy-MM-dd HH:mm:ss"));
		//发货时间取运单的开单时间
		lessDto.setSendTime(com.deppon.foss.util.DateUtils.convert(waybillEntity.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
		lessDto.setDestination(waybillEntity.getTargetOrgCode());
		lessDto.setReceiver(waybillEntity.getReceiveCustomerName());
		OrgAdministrativeInfoEntity recieveEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(waybillEntity.getReceiveOrgCode());
		if(recieveEntity != null){
			lessDto.setReceivingDeptID(recieveEntity.getUnifiedCode());
			lessDto.setReceivingDept(recieveEntity.getName());
		}
		lessDto.setPayType(tfrCommonService.queryDictNameByCode(DictionaryConstants.SETTLEMENT__PAYMENT_TYPE, waybillEntity.getPaidMethod()));				//付款方式
		if(waybillEntity.getInsuranceFee() == null){
			lessDto.setInsuranceMoney(new BigDecimal(0));
		}else{
			lessDto.setInsuranceMoney(waybillEntity.getInsuranceFee());
		}
		lessDto.setGoodsPacking(waybillEntity.getGoodsPackage());
		lessDto.setTotal(waybillEntity.getTotalFee());
		int detailSize =0;
		String detailSerialNO ="";
		if(stockDetailList!=null && stockDetailList.size()>0){
			StringBuffer s = new StringBuffer(ConstantsNumberSonar.SONAR_NUMBER_100);
			for(StockEntity entity: stockDetailList){
				s.append(entity.getSerialNO()).append(",");
			}
			detailSerialNO= s.toString();
			detailSize= stockDetailList.size();
		}
		lessDto.setNogoodscount(detailSize);
		//OA表设计中长度2000，事件经过最多放置650，故此处做处理
		String event = "出发丢货上报,包含件：" + detailSerialNO;
		if(event.length() > ConstantsNumberSonar.SONAR_NUMBER_650) {
			event = event.substring(0,ConstantsNumberSonar.SONAR_NUMBER_650) + "....";
		}
		lessDto.setEventReport(event);
		lessDto.setGoodsCount(waybillEntity.getGoodsQtyTotal());
		lessDto.setLostGoodsType("出发丢货");
		//责任部门
		lessDto.setResponsibleDept(errorLoseStartingDto.getOrgName());
		OrgAdministrativeInfoEntity currentOrg = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(errorLoseStartingDto.getOrgCode());
		lessDto.setResponsibleDeptId(currentOrg.getUnifiedCode());//当前外场部门编号
		//lessDto.setResponsibleDeptId(errorLoseStartingDto.getOrgCode());
		//责任事业部标杆编码
		//设置查询参数
		List<String> bizTypesList = new ArrayList<String>();
		//事业部类型
		bizTypesList.add(BizTypeConstants.ORG_DIVISION);
		//查询上级事业部门
		OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = orgAdministrativeInfoComplexService.
				queryOrgAdministrativeInfoByCode(errorLoseStartingDto.getOrgCode(), bizTypesList);
		if(orgAdministrativeInfoEntity != null){
			lessDto.setFinalSysCode(orgAdministrativeInfoEntity.getName());
		}else{
			lessDto.setFinalSysCode("");
			LOGGER.warn("清仓少货上报OA：查询责任部门【" + errorLoseStartingDto.getOrgCode() + "】的责任事业部失败！");
		}
		
		OrgAdministrativeInfoEntity createEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(waybillEntity.getCreateOrgCode());
		if(createEntity != null){
			lessDto.setSheetDeptId(createEntity.getUnifiedCode());
			lessDto.setSheetDept(createEntity.getName());
		}
		lessDto.setReceiver(waybillEntity.getReceiveCustomerContact()); //收货人
		lessDto.setUserId("Lose_Starting");
		lessDto.setReplayBill("出发丢货");
		//发货客户编码
		lessDto.setDeliveryCustomerCode(waybillEntity.getDeliveryCustomerCode());
		//收货客户编码
		lessDto.setReceiveCustomerCode(waybillEntity.getReceiveCustomerCode());
		//差异流水号
		String lackStr = detailSerialNO;
		//是否驻地
		lessDto.setStation(errorLoseStartingDto.getStation());
		//是否上门接货
		lessDto.setPickupToDoor(errorLoseStartingDto.getPickupToDoor());

		//业务渠道
		String transferChannel = UnloadConstants.TRANSFERCENTER_BUSINESS_CHANNEL;
		if(transferChannel.equals(errorLoseStartingDto.getChannelBusiness())){
			OutfieldEntity outfieldEntity = outfieldService
					.queryOutfieldByOrgCode(errorLoseStartingDto.getOrgCode());

			if (null != outfieldEntity
					&& StringUtils.isNotEmpty(outfieldEntity
							.getTransferServiceChannel())) {

				DataDictionaryValueEntity dictEntity = dataDictionaryValueService
						.queryDataDictionaryValueByTermsCodeValueCode(
								DictionaryConstants.TRANSFER_SERVICE_CHANNEL,
								outfieldEntity.getTransferServiceChannel());

				if (null != dictEntity
						&& StringUtils.isNotEmpty(dictEntity.getValueName())) {

					transferChannel = dictEntity.getValueName();
				}
			}
		}else{
			transferChannel = errorLoseStartingDto.getChannelBusiness();
		}
		lessDto.setBusinessChannel(transferChannel);
		
		
		if(StringUtils.isNotEmpty(lackStr)) {
			lessDto.setSerialNoList(lackStr.substring(0,lackStr.length() - 1 ));
		}

		ResponseDto responseDto = fossToOAService.reportLessGoods(lessDto);
		
		return responseDto;
	}
	
	/**
	 * ====================================
	 * 
	 * 
	 * 
	 * ====================================
	 */
	
	/**
	* @description 用一句话描述该文件做什么
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.stock.api.server.service.IErrorLoseStartingService#execFindErrorLoseStarting(java.util.Date, int, int)
	* @author 14022-foss-songjie
	* @update 2015年1月6日 下午2:04:07
	* @version V1.0
	 */
	@Override
	public void execFindErrorLoseStarting(Date doTime, int threadNo,
			int threadCount) {
		//reportLoseStartingGoodsFoundToOA();
	}
	
	
	/**
	* @description 自动上报出发丢货找到差错至oa，供job调用
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2015年1月5日 上午10:02:53
	*/
	private int reportLoseStartingGoodsFoundToOA(){
		//获取配置参数
		ConfigurationParamsEntity defaultBizHourSlice = configurationParamsService.queryConfigurationParamsByOrgCode(
				DictionaryConstants.SYSTEM_CONFIG_PARM__TFR , 
				ConfigurationParamsConstants.TFR_PARM_REPORT_ST_LACK_GOODS_FOUND_LATEST_REPORT_TIME, 
				FossConstants.ROOT_ORG_CODE);
		Date bizDate = null;
		if (defaultBizHourSlice == null
				|| StringUtils.isEmpty(defaultBizHourSlice.getConfValue())) {
			LOGGER.error("###########################读取配置参数中的oa清仓少货差错上报最晚时间为空！##########################");
			throw new TfrBusinessException("读取配置参数中的oa清仓少货差错上报最晚时间为空！");
		} else {
			bizDate = com.deppon.foss.util.DateUtils.strToDate(defaultBizHourSlice.getConfValue());
		}
		//查询出所有未少货找到的oa差错单编号
		List<String> errorNoList = unloadService.queryNoFoundLackGoodsOAErrorNo(TransferConstants.REPORT_TYPE_LS,bizDate);
		String lackOrgCode = null;
		//遍历这些少货差错
		for(String errorNo : errorNoList){
			//获取差错单下所有未找到的流水号
			List<LackGoodsFoundEntity> lackSerialNoList = unloadService.queryNoFoundLackGoodsDetailByOAErrorNo(errorNo);
			lackOrgCode = lackSerialNoList.get(0).getLackGoodsOrgCode();
			//bool变量，用来标记差错单下流水号是否已全部找到
			boolean beFoundAll = true;
			InOutStockEntity discoverer = null;
			//循环流水号，查询入库记录
			for(int i = 0;i < lackSerialNoList.size();i++){
				LackGoodsFoundEntity lackSerialNo = lackSerialNoList.get(i);
				String waybillNo = lackSerialNo.getWaybillNo(),
						serialNo = lackSerialNo.getSerialNo();
				Date reportTime = lackSerialNo.getReportOATime();
				List<InOutStockEntity> inStockList = stockService.queryInStockInfo(waybillNo, serialNo, null, reportTime);
				if(CollectionUtils.isEmpty(inStockList)){
					beFoundAll = false;
					continue;
				}else{
					//取出第一条入库记录
					InOutStockEntity firstInStock = inStockList.get(0);
					//更新少货差错单表
					lackSerialNo.setBeFound(FossConstants.YES);
					lackSerialNo.setDiscovererCode(firstInStock.getOperatorCode());
					lackSerialNo.setDiscovererName(firstInStock.getOperatorName());
					lackSerialNo.setDiscovererOrgCode(firstInStock.getOrgCode());
					lackSerialNo.setFoundTime(firstInStock.getInOutStockTime());
					lackSerialNo.setInStockId(firstInStock.getId());
					unloadService.updateUnloadLackGoodsInfo(lackSerialNo);
					
				}
				//取出最后一个流水号的入库记录
				if(beFoundAll){
					if(i + 1 == lackSerialNoList.size()){
						discoverer = inStockList.get(0);
					}
				}
			}
			//如果货物全部找到，则上报少货找到差错单
			if(beFoundAll){
				String lastOrgUnifiedCode = null;
				OrgAdministrativeInfoEntity orgEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(lackOrgCode);
				if(orgEntity != null){
					lastOrgUnifiedCode = orgEntity.getUnifiedCode();
				}
				/*
				 * 上报少货找到
				 */
				//sonar-352203
				ResponseDto responseDto = null;
				if(discoverer != null){
					/*ResponseDto */responseDto = fossToOAService.reportLessGoodsFound(errorNo, discoverer.getOperatorCode(), discoverer.getOperatorName(), lastOrgUnifiedCode);
				}
				//如果oa没处理成功，则回滚事务
				if(responseDto != null && !responseDto.getIsSuccess()){
					//记录日志
					LOGGER.error("######################上报OA少货已找到差错单失败，失败信息：" + responseDto.getFailureReason());
					//查询下一个少货差错单
					continue;
				}
			}
		}
		return FossConstants.SUCCESS;
	}

}

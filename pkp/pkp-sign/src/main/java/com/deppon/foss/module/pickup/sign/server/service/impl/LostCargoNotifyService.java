package com.deppon.foss.module.pickup.sign.server.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.base.util.define.BizTypeConstants;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISaleDepartmentService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.base.dict.api.server.service.IDataDictionaryValueService;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryValueEntity;
import com.deppon.foss.module.pickup.predeliver.api.server.dao.IShareJobDao;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.JobExceptionLogEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.JobSuccessLogEntity;
import com.deppon.foss.module.pickup.pricing.api.server.service.IProductService;
import com.deppon.foss.module.pickup.sign.api.server.dao.ILostCargoNotifyDao;
import com.deppon.foss.module.pickup.sign.api.server.service.ILostCargoNotifyService;
import com.deppon.foss.module.pickup.sign.api.shared.define.ReportConstants;
import com.deppon.foss.module.pickup.sign.api.shared.define.SignConstants;
import com.deppon.foss.module.pickup.sign.api.shared.dto.LostCargoReportDto;
import com.deppon.foss.module.pickup.waybill.api.server.service.ILabeledGoodService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IOAErrorService;
import com.deppon.foss.module.pickup.waybill.api.shared.define.SettlementReportNumber;
import com.deppon.foss.module.pickup.waybill.shared.domain.LabeledGoodEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.OaReportClearLose;
import com.deppon.foss.module.transfer.common.api.shared.dto.ResponseDto;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.define.FossConstants;

/***
 * @clasaName:com.deppon.foss.module.pickup.sign.server.service.impl.LostCargoNotifyService
 * @author: foss-yuting
 * @description: 丢货差错自动上报job
 * @date:2014年12月3日 下午2:27:21
 */
public class LostCargoNotifyService implements ILostCargoNotifyService {

	/**
	 * 营业部 Service实现
	 */
	private ISaleDepartmentService saleDepartmentService;
	
	/***
	 * 记录日志
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(LostCargoNotifyService.class);

	/***
	 * 丢货上报的dao
	 */
	private ILostCargoNotifyDao lostCargoNotifyDao;
	

	/***
	 * 货签服务，通过运单号查询丢货件数
	 */
	private ILabeledGoodService labeledGoodService;
	
	/**
	 * 组织接口
	 */
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	
	/**
	 * 数据字典接口
	 */
	private IDataDictionaryValueService dataDictionaryValueService;

	/**
	 * 产品接口
	 */
	private IProductService productService;
	

	/**
	 * 常量，当传给oa的数据字典缺失时，传该常量
	 */
	private static final String UNKNOWN_TYPE = "未知类型";
	
	/**
	 * 综合管理 组织信息 Service
	 */
	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;
	
	/**
	 * 上报OA接口
	 */
	private IOAErrorService oAErrorService;
	
	/***
	 * 上报成功或失败添加日志接口
	 */
	private IShareJobDao shareJobDao;
	
	
	/**
	 * 丢货差错自动上报 (次日上午8:00发送)
	 * @date 2014-12-3 下午3:58:10
	 * @param oaReportClearless
	 * @return  
	 * @see com.deppon.foss.module.pickup.sign.server.service.impl.LostCargoNotifyService#processNotifyLastCargo()
	 */
	@Transactional
	@Override
	public void processNotifyLastCargo() {
		//上报oa实体数据
		List<LostCargoReportDto> lostCargoReportList = lostCargoNotifyDao.queryReportOAList();
		//去除重复的流水号数据
		Map<String,LostCargoReportDto> map = new HashMap<String,LostCargoReportDto>();
		if(CollectionUtils.isNotEmpty(lostCargoReportList)){
			for (LostCargoReportDto lostCargoReportDto : lostCargoReportList) {
				String key = lostCargoReportDto.getWaybillNo()+lostCargoReportDto.getSerialNo();
				map.put(key, lostCargoReportDto);
			}
		}
		Collection<LostCargoReportDto> values = map.values();
		for (LostCargoReportDto lostCargoReportDto : values) {
			OaReportClearLose oaReportClearLose = copyInfo2OaReportClearLose(lostCargoReportDto);
			ResponseDto responseDto = oAErrorService.reportLostGoods(oaReportClearLose);
			processReturnInfo(responseDto,lostCargoReportDto);
		}
	}
	
	/**
	 * 处理oa上报结果 </br>
	 * @date 2014-12-23 下午3:58:10 
	 * @param 
	 * @see com.deppon.foss.module.pickup.sign.server.service.impl.MultThreadReportOALostService.processReturnInfo()
	 */
	public void processReturnInfo(ResponseDto responseDto,LostCargoReportDto lostCargoReportDto) {
		
		LOGGER.info("############处理OA返回结果 start ############");
		if (responseDto.getIsException()) {
			// 异常
			String msg = returnReportOAMsg(responseDto);
			int i = addReportOaLog(lostCargoReportDto, responseDto, msg, ReportConstants.REPORT_OA_ERROR);
			if (i == 1) {
				LOGGER.info("#####保存上报异常日志成功！######");
			} else {
				LOGGER.info("#####保存上报异常日志失败！######");
			}
		} else {
			// 成功,上报成功OA返回查错编号
			if (StringUtils.isNotBlank(responseDto.getErrorsNo())) {
				String msg = returnReportOAMsg(responseDto);
				int i = addReportOaLog(lostCargoReportDto, responseDto, msg, ReportConstants.REPORT_OA_SUCCESS);
				if (i == 1) {
					LOGGER.info("#####保存上报OA成功日志成功！######");
				} else {
					LOGGER.info("#####保存上报OA成功日志失败！######");
				}
			}
			else {
				String msg = returnReportOAMsg(responseDto);
				int i = addReportOaLog(lostCargoReportDto, responseDto, msg, ReportConstants.REPORT_OA_ERROR);
				if (i == 1) {
					LOGGER.info("#####保存上报OA失败日志成功！######");
				} else {
					LOGGER.info("#####保存上报OA失败日志失败！######");
				}
			}
			LOGGER.info("############处理OA返回结果 end ############");
		}
	}

	/**
	 * oa上报结果信息 </br>
	 * @date 2014-12-23 下午3:58:10
	 * @param 
	 * @see com.deppon.foss.module.pickup.sign.server.service.impl.MultThreadReportOALostService.returnReportOAMsg()
	 */
	private String returnReportOAMsg(ResponseDto responseDto) {
		String msg = responseDto.getMessage();
		if (StringUtils.isNotEmpty(msg) && msg.length() > SettlementReportNumber.TWO_THOUSAND) {
			msg = msg.substring(0, SettlementReportNumber.TWO_THOUSAND);
		}
		return msg;
	}
	
	
	/**
	 * 保存上报日志
	 * @date 2014-12-19 下午3:58:10
	 * @param lostCargoReportDto
	 * @author foss-yuting
	 * @return  
	 * @see com.deppon.foss.module.pickup.sign.server.service.impl.MultThreadReportOALostService#addReportOaLog(com.deppon.foss.module.pickup.sign.api.shared.dto.LostCargoReportDto)
	 */
	private int addReportOaLog(LostCargoReportDto lostCargoReportDto, ResponseDto responseDto, String msg,String isSuccess){
		if(StringUtils.isNotEmpty(isSuccess)&&isSuccess.equals(ReportConstants.REPORT_OA_SUCCESS)){
			JobSuccessLogEntity jobSuccessLogEntity = new JobSuccessLogEntity();
			jobSuccessLogEntity.setJobName(SignConstants.LOST_CARGO_NOTIFY_PROCESS_JOB);// job名称
			jobSuccessLogEntity.setCreateTime(new Date());
			jobSuccessLogEntity.setSuccessMsg("差错编号:" + responseDto.getErrorsNo() + ";运单号:" + lostCargoReportDto.getWaybillNo() + ";" + msg); // 调用OA接口成功返回差错编号
			jobSuccessLogEntity.setSuccessId(lostCargoReportDto.getId());
			return shareJobDao.addJobSuccessLogEntity(jobSuccessLogEntity);
		}else{
			JobExceptionLogEntity jobExceptionLogEntity = new JobExceptionLogEntity();
			jobExceptionLogEntity.setJobName(SignConstants.LOST_CARGO_NOTIFY_PROCESS_JOB);// job名称
			jobExceptionLogEntity.setErrorMsg(msg + ";运单号:" + lostCargoReportDto.getWaybillNo());
			jobExceptionLogEntity.setErrorId(lostCargoReportDto.getId());
			jobExceptionLogEntity.setCreateTime(new Date());
			return shareJobDao.addJobExceptionLogEntity(jobExceptionLogEntity);
		}
	}

	/**
	 * 拼装上报oa数据接口
	 * @date 2014-12-17 下午3:58:10
	 * @param oaReportClearless
	 * @return  
	 * @see com.deppon.foss.module.pickup.sign.server.service.impl.LostCargoNotifyService#addReportOaReturnLog(com.deppon.foss.module.transfer.common.api.shared.dto.OaReportClearless)
	 */
	private OaReportClearLose copyInfo2OaReportClearLose(LostCargoReportDto lostCargoReportDto) {
		
		OaReportClearLose oaReportClearLose = new OaReportClearLose();
		// 运单号
		oaReportClearLose.setWayBillId(lostCargoReportDto.getWaybillNo());
		// 交接单号
		oaReportClearLose.setReplayBill(null);
		// 到达交接人工号
		oaReportClearLose.setUserId(null);
		// 车牌号
		oaReportClearLose.setCarNumber(null);
		OrgAdministrativeInfoEntity responsibleOrg = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(lostCargoReportDto.getOperateOrgCode());
		if (responsibleOrg != null) {
			// 责任部门标杆编码
			oaReportClearLose.setResponsibleDeptId(responsibleOrg.getUnifiedCode());
			// 责任部门名称
			oaReportClearLose.setResponsibleDept(responsibleOrg.getName());
		}

		List<String> bizTypesList = new ArrayList<String>();
		// 事业部类型
		bizTypesList.add(BizTypeConstants.ORG_DIVISION);
		// 责任事业部编码
		OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoByCode(responsibleOrg.getCode(), bizTypesList);
		if (orgAdministrativeInfoEntity != null) {
			oaReportClearLose.setFinalSysCode(orgAdministrativeInfoEntity.getName());
		} 
		// 托运人
		oaReportClearLose.setShipper(lostCargoReportDto.getDeliveryCustomerContact());
		// 收货人
		oaReportClearLose.setReceiver(lostCargoReportDto.getReceiveCustomerContact());
		// 收货人电话
		if (StringUtils.isBlank(lostCargoReportDto.getReceiveCustomerPhone())) {
			// 若收货人电话为空，则传入收货人手机
			oaReportClearLose.setReceiverTel(lostCargoReportDto.getReceiveCustomerMobilephone());
		} else {
			// 若收货人电话不为空，则传入收货人电话
			oaReportClearLose.setReceiverTel(lostCargoReportDto.getReceiveCustomerPhone());
		}
		// 保险金额
		oaReportClearLose.setInsuranceMoney(lostCargoReportDto.getInsuranceFee());
		// 开单部门
		OrgAdministrativeInfoEntity createEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(lostCargoReportDto.getCreateOrgCode());
		if (createEntity != null) {
			// 开单部门标杆编码
			oaReportClearLose.setSheetDeptId(createEntity.getUnifiedCode());
			// 开单部门名称
			oaReportClearLose.setSheetDept(createEntity.getName());
		}
		// 运输性质
		oaReportClearLose.setTransportProduct(productService.getProductByCache(lostCargoReportDto.getProductCode(), null).getName());
		/*
		 * 运输类型
		 */
		DataDictionaryValueEntity dictEntity1 = dataDictionaryValueService.queryDataDictionaryValueByTermsCodeValueCode(DictionaryConstants.BSE_TRANS_TYPE,
				lostCargoReportDto.getTransportType());
		if (dictEntity1 == null) {
			// 数据字段为空，则设置为常量
			oaReportClearLose.setTransportType(UNKNOWN_TYPE);
		} else {
			// 运输类型
			oaReportClearLose.setTransportType(dictEntity1.getValueName());
		}
		/*
		 * 提货方式
		 */
		DataDictionaryValueEntity dictEntity3 = dataDictionaryValueService.queryDataDictionaryValueByTermsCodeValueCode(DictionaryConstants.PICKUP_GOODS,
				lostCargoReportDto.getReceiveMethod());
		if (dictEntity3 == null) {
			// 数据字段为空，则设置为常量
			oaReportClearLose.setGroupSendFlag(UNKNOWN_TYPE);
		} else {
			// 提货方式
			oaReportClearLose.setGroupSendFlag(dictEntity3.getValueName());
		}

		// 发货时间
		oaReportClearLose.setSendTime(DateUtils.convert(lostCargoReportDto.getBillTime()));
		// 收货部门
		OrgAdministrativeInfoEntity recieveEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(lostCargoReportDto.getReceiveOrgCode());
		if (recieveEntity != null) {
			// 收货部门标杆编码
			oaReportClearLose.setReceivingDeptID(recieveEntity.getUnifiedCode());
			// 收货部门名称
			oaReportClearLose.setReceivingDept(recieveEntity.getName());
		}
		// 货物包装
		oaReportClearLose.setGoodsPacking(lostCargoReportDto.getGoodsPackage());
		/*
		 * 返单类别
		 */
		DataDictionaryValueEntity dictEntity = dataDictionaryValueService.queryDataDictionaryValueByTermsCodeValueCode(DictionaryConstants.RETURN_BILL_TYPE,
				lostCargoReportDto.getReturnBillType());
		if (dictEntity == null) {
			// 数据字段为空，则设置为常量
			oaReportClearLose.setReturnBillType(UNKNOWN_TYPE);
		} else {
			oaReportClearLose.setReturnBillType(dictEntity.getValueCode());
		}
		// 储运事项
		oaReportClearLose.setRemark(lostCargoReportDto.getTransportationRemark());
		// 件数
		oaReportClearLose.setGoodsCount(lostCargoReportDto.getGoodsQtyTotal());
		// 目的站
		oaReportClearLose.setDestination(lostCargoReportDto.getTargetOrgCode());
		/*
		 * 付款方式
		 */
		DataDictionaryValueEntity dictEntity4 = dataDictionaryValueService.queryDataDictionaryValueByTermsCodeValueCode(DictionaryConstants.SETTLEMENT__PAYMENT_TYPE,
				lostCargoReportDto.getPaidMethod());
		if (dictEntity4 == null) {
			// 数据字段为空，则设置为常量
			oaReportClearLose.setPayType(UNKNOWN_TYPE);
		} else {
			// 付款方式
			oaReportClearLose.setPayType(dictEntity4.getValueName());
		}
		// 运费总额
		oaReportClearLose.setTotal(lostCargoReportDto.getTotalFee());
	
		// 上报时间
		oaReportClearLose.setReportTime(new Date());
		// 丢货类别
		oaReportClearLose.setLostGoodsType("到达丢货");
		//业务渠道
		oaReportClearLose.setBusinessChannel("车队");
		// 重量
		if (null != lostCargoReportDto.getGoodsWeightTotal()) {
			oaReportClearLose.setWeight(lostCargoReportDto.getGoodsWeightTotal().doubleValue());
		} else {
			oaReportClearLose.setWeight(Double.valueOf(0));
		}

		// 体积
		if (null != lostCargoReportDto.getGoodsVolumeTotal()) {
			oaReportClearLose.setVloume(lostCargoReportDto.getGoodsVolumeTotal().doubleValue());
		} else {
			oaReportClearLose.setVloume(Double.valueOf(0));
		}

		// 发货客户编码
		oaReportClearLose.setDeliveryCustomerCode(lostCargoReportDto.getDeliveryCustomerCode());
		// 收货客户编码
		oaReportClearLose.setReceiveCustomerCode(lostCargoReportDto.getReceiveCustomerCode());
		// 货物名称
		oaReportClearLose.setGoods(lostCargoReportDto.getGoodsName());
		// 根据运单查询流水号信息
		List<LabeledGoodEntity> labeledGoodEntityList = labeledGoodService.queryAllSerialByWaybillNo(lostCargoReportDto.getWaybillNo());
		// 少货件数
		oaReportClearLose.setNogoodscount(labeledGoodEntityList.size());
		// 流水号
		StringBuffer serialNoSb = new StringBuffer();
		for (int i = 0; i < labeledGoodEntityList.size(); i++) {
			if (i + 1 == labeledGoodEntityList.size()) {
				serialNoSb.append(labeledGoodEntityList.get(i).getSerialNo());
				serialNoSb.append(".");
			} else {
				serialNoSb.append(labeledGoodEntityList.get(i).getSerialNo());
				serialNoSb.append("、");
			}
		}
		oaReportClearLose.setSerialNoList(serialNoSb.toString());
		//配载方式
		oaReportClearLose.setStowageType(lostCargoReportDto.getLoadMethod());
		//新增字段 是否集中接货
		boolean isPickupToDoor = FossConstants.YES.equals(lostCargoReportDto.getPickupCentralized());
		if(isPickupToDoor){
			oaReportClearLose.setPickupToDoor(FossConstants.YES);
		}else{
			oaReportClearLose.setPickupToDoor(FossConstants.NO);
		}
		
		//获取对应的组织信息
		OrgAdministrativeInfoEntity orgInfoEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(lostCargoReportDto.getCreateOrgCode());
		if(orgAdministrativeInfoEntity != null){
			if (FossConstants.NO.equals(lostCargoReportDto.getPickupCentralized())) {
				// 是否营业部
				if (FossConstants.YES.equals(orgInfoEntity.getSalesDepartment())){
					SaleDepartmentEntity saleDepartmentEntity = saleDepartmentService.querySaleDepartmentByCode(lostCargoReportDto.getCreateOrgCode());
					if (saleDepartmentEntity != null) {
						// 是否驻地部门
						if(FossConstants.YES.equals(saleDepartmentEntity.getStation())){
							// 驻地部门
							oaReportClearLose.setStation(FossConstants.YES);
						}else{
							//非驻地部门
							oaReportClearLose.setStation(FossConstants.NO);
						}
					}
				}
			} 
		}
		return oaReportClearLose;
	}

	public void setLostCargoNotifyDao(ILostCargoNotifyDao lostCargoNotifyDao) {
		this.lostCargoNotifyDao = lostCargoNotifyDao;
	}

	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

	public void setLabeledGoodService(ILabeledGoodService labeledGoodService) {
		this.labeledGoodService = labeledGoodService;
	}

	public void setDataDictionaryValueService(
			IDataDictionaryValueService dataDictionaryValueService) {
		this.dataDictionaryValueService = dataDictionaryValueService;
	}

	public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}

	public void setSaleDepartmentService(
			ISaleDepartmentService saleDepartmentService) {
		this.saleDepartmentService = saleDepartmentService;
	}

	public void setoAErrorService(IOAErrorService oAErrorService) {
		this.oAErrorService = oAErrorService;
	}

	public void setShareJobDao(IShareJobDao shareJobDao) {
		this.shareJobDao = shareJobDao;
	}

	public void setProductService(IProductService productService) {
		this.productService = productService;
	}
	
}

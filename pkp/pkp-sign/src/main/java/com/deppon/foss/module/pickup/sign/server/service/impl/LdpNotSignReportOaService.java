package com.deppon.foss.module.pickup.sign.server.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.base.util.define.BizTypeConstants;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.base.dict.api.server.service.IDataDictionaryValueService;
import com.deppon.foss.module.base.dict.api.shared.define.ConfigurationParamsConstants;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryValueEntity;
import com.deppon.foss.module.pickup.predeliver.api.server.dao.IShareJobDao;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.JobExceptionLogEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.JobSuccessLogEntity;
import com.deppon.foss.module.pickup.pricing.api.server.service.IProductService;
import com.deppon.foss.module.pickup.sign.api.server.dao.ILdpNotSignReportOaDao;
import com.deppon.foss.module.pickup.sign.api.server.service.ILdpNotSignReportOaService;
import com.deppon.foss.module.pickup.sign.api.shared.define.SignConstants;
import com.deppon.foss.module.pickup.sign.api.shared.dto.LdpNotSignReportOaDto;
import com.deppon.foss.module.pickup.sign.api.shared.exception.LdpNotSignReportOAException;
import com.deppon.foss.module.pickup.waybill.api.server.service.ILabeledGoodService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IOAErrorService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillExpressService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.pickup.waybill.api.shared.define.SettlementReportNumber;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.LabeledGoodEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillExpressEntity;
import com.deppon.foss.module.transfer.common.api.shared.dto.OaReportClearless;
import com.deppon.foss.module.transfer.common.api.shared.dto.ResponseDto;
import com.deppon.foss.module.transfer.partialline.api.server.service.ILdpExternalBillService;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 快递代理外发XX天未签收自动上报OA丢货Service实现类
 * 
 * @ClassName: LdpNotSignReportOaService
 * @author 200664-yangjinheng
 * @date 2014年9月3日 上午10:35:48
 */
public class LdpNotSignReportOaService implements ILdpNotSignReportOaService {

	/**
	 * 记录日志
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(LdpNotSignReportOaService.class);

	/**
	 * 常量，当传给oa的数据字典缺失时，传该常量
	 */
	private static final String UNKNOWN_TYPE = "未知类型";

	// 上报OA接口
	private IOAErrorService oAErrorService;
	// 配置参数接口
	private IConfigurationParamsService configurationParamsService;

	// 货签服务，通过运单号查询丢货件数
	private ILabeledGoodService labeledGoodService;

	// 综合管理 组织信息 Service
	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;

	// 快递代理Service
	private ILdpExternalBillService ldpExternalBillService;

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

	// 查询运单接口
	private IWaybillManagerService waybillManagerService;

	// 上报成功或失败添加日志接口
	private IShareJobDao shareJobDao;

	private ILdpNotSignReportOaDao ldpNotSignReportOaDao;
	private IWaybillExpressService waybillExpressService;

	public void setWaybillManagerService(IWaybillManagerService waybillManagerService) {
		this.waybillManagerService = waybillManagerService;
	}

	public void setProductService(IProductService productService) {
		this.productService = productService;
	}

	public void setDataDictionaryValueService(IDataDictionaryValueService dataDictionaryValueService) {
		this.dataDictionaryValueService = dataDictionaryValueService;
	}

	public void setOrgAdministrativeInfoService(IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

	public void setLdpExternalBillService(ILdpExternalBillService ldpExternalBillService) {
		this.ldpExternalBillService = ldpExternalBillService;
	}

	public void setLdpNotSignReportOaDao(ILdpNotSignReportOaDao ldpNotSignReportOaDao) {
		this.ldpNotSignReportOaDao = ldpNotSignReportOaDao;
	}

	public void setOrgAdministrativeInfoComplexService(IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}

	public void setShareJobDao(IShareJobDao shareJobDao) {
		this.shareJobDao = shareJobDao;
	}

	public void setLabeledGoodService(ILabeledGoodService labeledGoodService) {
		this.labeledGoodService = labeledGoodService;
	}

	public void setoAErrorService(IOAErrorService oAErrorService) {
		this.oAErrorService = oAErrorService;
	}

	public void setConfigurationParamsService(IConfigurationParamsService configurationParamsService) {
		this.configurationParamsService = configurationParamsService;
	}

	/**
	 * 根据超过上报时间 和 上线的历史数据开始时间 查询符合条件的运单
	 * 
	 * @Title: queryCountbyCondition
	 * @author 200664-yangjinheng
	 * @date 2014年9月3日 上午10:36:24
	 * @throws
	 */
	private List<LdpNotSignReportOaDto> querybyRegisterTimeCondition(String reportTime, String beginTime) {
		if (StringUtil.isBlank(reportTime)) {
			throw new LdpNotSignReportOAException("上报时间为空");
		}
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("reportTime", reportTime);
		paramsMap.put("beginTime", beginTime);
		return ldpNotSignReportOaDao.querybyRegisterTimeCondition(paramsMap);
	}

	/**
	 * 上报OA丢货
	 * 
	 * @Title: reportOALessGoods
	 * @author 200664-yangjinheng
	 * @date 2014年9月12日 下午7:21:33
	 * @throws
	 */
	@Transactional
	public void reportOALessGoods() {
		// 配置参数 快递代理外发上报OA差错时间
		String reportTime = configurationParamsService.queryConfValueByCode(ConfigurationParamsConstants.TFR_PARM_LDP_REPORT_OA_ERROR_TIME_SWITCH);
		if (StringUtil.isBlank(reportTime)) {
			// 天数默认值 15天
			reportTime = "15";
		}
		// 上线的历史数据开始时间
		String beginTime = SignConstants.NOT_SIGN_REPORTING_LOSTGOODS_BEGIN_TIME;
		List<LdpNotSignReportOaDto> ldpNotSignReportOaDtoList = this.querybyRegisterTimeCondition(reportTime, beginTime);
		if (ldpNotSignReportOaDtoList.size() <= 0) {
			// 无数据上报结束
			LOGGER.info("快递代理外发XX天未签收自动上报OA丢货 无数据     LdpNotSignReportOaJob End");
			return;// 跳出当前循环
		}
		for (LdpNotSignReportOaDto ldpNotSignReportOaDto : ldpNotSignReportOaDtoList) {

			// 根据运单号查询运单信息
			WaybillEntity waybillEntity = waybillManagerService.queryWaybillBasicByNo(ldpNotSignReportOaDto.getWayBillId());

			if (waybillEntity == null) {
				continue;
			}
			//调用原单接口  获取原单
			WaybillExpressEntity waybills=waybillExpressService.queryWaybillByOriginalWaybillNo(waybillEntity.getWaybillNo(), WaybillConstants.WAYBILL_EXPRESSTYPE_RETURN_CARGO);
			//判断是否是原单
			if(waybills!=null){//是
				// 此时原单不需要自动上报  菜鸟需求决定
				continue;
		     }
			// 将快递代理外发XX天未签收自动上报OA丢货 运单信息复制到 OaReportClearless
			OaReportClearless oaReportClearless = this.copyInfo2OaReportClearless(ldpNotSignReportOaDto, waybillEntity);

			// 事件经过
			oaReportClearless.setEventReport("外发货物超过" + reportTime + "天未签收");

			// 调用OA接口
			ResponseDto responseDto = oAErrorService.reportLessGoods(oaReportClearless);

			// 是否成功 false代码正常 true代表发生异常
			if (responseDto.getIsException()) {
				// 异常
				String msg = responseDto.getMessage();
				if (StringUtils.isNotEmpty(msg) && msg.length() > SettlementReportNumber.TWO_THOUSAND) {
					msg = msg.substring(0, SettlementReportNumber.TWO_THOUSAND);
				}
				int i = this.addReportOaErrorLog(ldpNotSignReportOaDto, responseDto, msg);
				if (i == 1) {
					LOGGER.info("保存【上报OA异常日志】成功！");
				} else {
					LOGGER.info("保存【上报OA异常日志】失败！");
				}
			} else {
				// 成功,上报成功OA返回查错编号
				if (StringUtils.isNotBlank(responseDto.getErrorsNo())) {
					String msg = responseDto.getMessage();
					if (StringUtils.isNotEmpty(msg) && msg.length() > SettlementReportNumber.TWO_THOUSAND) {
						msg = msg.substring(0, SettlementReportNumber.TWO_THOUSAND);
					}
					// 上报成功后要把TFR.T_OPT_LDP_EXTERNAL_BILL表的REPORT_OA ='N'
					// 状态改为'Y'
					ldpExternalBillService.updateReportOAByPrimaryKey(ldpNotSignReportOaDto.getId(), FossConstants.YES);
					int i = this.addReportOaSuccessLog(ldpNotSignReportOaDto, responseDto, msg);
					if (i == 1) {
						LOGGER.info("保存【上报OA成功日志】成功！");
					} else {
						LOGGER.info("保存【上报OA成功日志】失败！");
					}
				}
				// 失败，失败一般是通过其他方式已经上报OA，OA返回“OA已上报丢货，请在OA上报丢货找到”
				// 需要把FOSS的TFR.T_OPT_LDP_EXTERNAL_BILL表的REPORT_OA = 'N'，状态改为'Y'
				else {
					String msg = responseDto.getMessage();
					if (StringUtils.isNotEmpty(msg) && msg.length() > SettlementReportNumber.TWO_THOUSAND) {
						msg = msg.substring(0, SettlementReportNumber.TWO_THOUSAND);
					}
					ldpExternalBillService.updateReportOAByPrimaryKey(ldpNotSignReportOaDto.getId(), FossConstants.YES);
					int i = this.addReportOaErrorLog(ldpNotSignReportOaDto, responseDto, msg);
					if (i == 1) {
						LOGGER.info("保存【上报OA失败日志】成功！");
					} else {
						LOGGER.info("保存【上报OA失败日志】失败！");
					}

				}
			}
		}

	}

	/**
	 * 
	 * 保存上报失败日志
	 * 
	 * @Title: addReportOaErrorLog
	 * @author 200664-yangjinheng
	 * @date 2014年9月5日 下午2:17:00
	 * @throws
	 */
	private int addReportOaErrorLog(LdpNotSignReportOaDto ldpNotSignReportOaDto, ResponseDto responseDto, String msg) {
		JobExceptionLogEntity jobExceptionLogEntity = new JobExceptionLogEntity();
		jobExceptionLogEntity.setJobName(SignConstants.NOT_SIGN_REPORTING_LOSTGOODS_JOB);// job名称
		jobExceptionLogEntity.setErrorMsg(msg + ";运单号:" + ldpNotSignReportOaDto.getWayBillId());
		jobExceptionLogEntity.setErrorId(ldpNotSignReportOaDto.getId());
		jobExceptionLogEntity.setCreateTime(new Date());
		return shareJobDao.addJobExceptionLogEntity(jobExceptionLogEntity);
	}

	/**
	 * 保存上报成功日志
	 * 
	 * @Title: addReportOaSuccessLog
	 * @author 200664-yangjinheng
	 * @date 2014年9月5日 下午1:48:14
	 * @throws
	 */
	private int addReportOaSuccessLog(LdpNotSignReportOaDto ldpNotSignReportOaDto, ResponseDto responseDto, String msg) {
		JobSuccessLogEntity jobSuccessLogEntity = new JobSuccessLogEntity();
		jobSuccessLogEntity.setJobName(SignConstants.NOT_SIGN_REPORTING_LOSTGOODS_JOB);// job名称
		jobSuccessLogEntity.setSuccessMsg("差错编号:" + responseDto.getErrorsNo() + ";运单号:" + ldpNotSignReportOaDto.getWayBillId() + ";" + msg); // 调用OA接口成功返回差错编号
		jobSuccessLogEntity.setSuccessId(ldpNotSignReportOaDto.getId());
		jobSuccessLogEntity.setCreateTime(new Date());
		return shareJobDao.addJobSuccessLogEntity(jobSuccessLogEntity);
	}

	/**
	 * 将快递代理外发XX天未签收自动上报OA丢货 运单信息复制到 OaReportClearless
	 * 
	 * @Title: copyInfo2OaReportClearless
	 * @author 200664-yangjinheng
	 * @date 2014年9月5日 上午10:29:48
	 * @throws
	 */
	private OaReportClearless copyInfo2OaReportClearless(LdpNotSignReportOaDto ldpNotSignReportOaDto, WaybillEntity waybillEntity) {
		OaReportClearless oaReportClearless = new OaReportClearless();
		// 运单号
		oaReportClearless.setWayBillId(ldpNotSignReportOaDto.getWayBillId());
		// 交接单号
		oaReportClearless.setReplayBill(ldpNotSignReportOaDto.getReplayBill());

		OrgAdministrativeInfoEntity responsibleOrg = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(ldpNotSignReportOaDto.getExternalOrgCode());
		if (responsibleOrg != null) {
			// 责任部门标杆编码
			oaReportClearless.setResponsibleDeptId(responsibleOrg.getUnifiedCode());
			// 责任部门名称
			oaReportClearless.setResponsibleDept(responsibleOrg.getName());
		}

		List<String> bizTypesList = new ArrayList<String>();
		// 事业部类型
		bizTypesList.add(BizTypeConstants.ORG_DIVISION);
		// 责任事业部编码
		OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoByCode(responsibleOrg.getCode(), bizTypesList);
		if (orgAdministrativeInfoEntity != null) {
			oaReportClearless.setFinalSysCode(orgAdministrativeInfoEntity.getName());
		} else {
			LOGGER.warn("上报OA少货：查询部门【" + oaReportClearless.getResponsibleDeptId() + "】的上一级事业部失败！");
		}

		// 到达交接人工号
		oaReportClearless.setUserId(ldpNotSignReportOaDto.getExternalUserCode());
		// 托运人
		oaReportClearless.setShipper(waybillEntity.getDeliveryCustomerContact());
		// 收货人
		oaReportClearless.setReceiver(waybillEntity.getReceiveCustomerContact());
		// 收货人电话
		if (StringUtils.isBlank(waybillEntity.getReceiveCustomerPhone())) {
			// 若收货人电话为空，则传入收货人手机
			oaReportClearless.setReceiverTel(waybillEntity.getReceiveCustomerMobilephone());
		} else {
			// 若收货人电话不为空，则传入收货人电话
			oaReportClearless.setReceiverTel(waybillEntity.getReceiveCustomerPhone());
		}
		// 保险金额
		oaReportClearless.setInsuranceMoney(waybillEntity.getInsuranceFee());
		// 开单部门
		OrgAdministrativeInfoEntity createEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(waybillEntity.getCreateOrgCode());
		if (createEntity != null) {
			// 开单部门标杆编码
			oaReportClearless.setSheetDeptId(createEntity.getUnifiedCode());
			// 开单部门名称
			oaReportClearless.setSheetDept(createEntity.getName());
		}
		// 运输性质
		oaReportClearless.setTransportProduct(productService.getProductByCache(waybillEntity.getProductCode(), null).getName());
		/*
		 * 运输类型
		 */
		DataDictionaryValueEntity dictEntity1 = dataDictionaryValueService.queryDataDictionaryValueByTermsCodeValueCode(DictionaryConstants.BSE_TRANS_TYPE,
				waybillEntity.getTransportType());
		if (dictEntity1 == null) {
			// 数据字段为空，则设置为常量
			oaReportClearless.setTransportType(UNKNOWN_TYPE);
		} else {
			// 运输类型
			oaReportClearless.setTransportType(dictEntity1.getValueName());
		}

		/*
		 * 提货方式
		 */
		DataDictionaryValueEntity dictEntity3 = dataDictionaryValueService.queryDataDictionaryValueByTermsCodeValueCode(DictionaryConstants.PICKUP_GOODS,
				waybillEntity.getReceiveMethod());
		if (dictEntity3 == null) {
			// 数据字段为空，则设置为常量
			oaReportClearless.setGroupSendFlag(UNKNOWN_TYPE);
		} else {
			// 提货方式
			oaReportClearless.setGroupSendFlag(dictEntity3.getValueName());
		}

		// 发货时间
		oaReportClearless.setSendTime(DateUtils.convert(waybillEntity.getBillTime()));
		// 收货部门
		OrgAdministrativeInfoEntity recieveEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(waybillEntity.getReceiveOrgCode());
		if (recieveEntity != null) {
			// 收货部门标杆编码
			oaReportClearless.setReceivingDeptID(recieveEntity.getUnifiedCode());
			// 收货部门名称
			oaReportClearless.setReceivingDept(recieveEntity.getName());
		}
		// 货物包装
		oaReportClearless.setGoodsPacking(waybillEntity.getGoodsPackage());
		/*
		 * 返单类别
		 */
		DataDictionaryValueEntity dictEntity = dataDictionaryValueService.queryDataDictionaryValueByTermsCodeValueCode(DictionaryConstants.RETURN_BILL_TYPE,
				waybillEntity.getReturnBillType());
		if (dictEntity == null) {
			// 数据字段为空，则设置为常量
			oaReportClearless.setReturnBillType(UNKNOWN_TYPE);
		} else {
			oaReportClearless.setReturnBillType(dictEntity.getValueName());
		}
		// 储运事项
		oaReportClearless.setRemark(waybillEntity.getTransportationRemark());
		// 件数
		oaReportClearless.setGoodsCount(waybillEntity.getGoodsQtyTotal());
		// 目的站
		oaReportClearless.setDestination(waybillEntity.getTargetOrgCode());
		/*
		 * 付款方式
		 */
		DataDictionaryValueEntity dictEntity4 = dataDictionaryValueService.queryDataDictionaryValueByTermsCodeValueCode(DictionaryConstants.SETTLEMENT__PAYMENT_TYPE,
				waybillEntity.getPaidMethod());
		if (dictEntity4 == null) {
			// 数据字段为空，则设置为常量
			oaReportClearless.setPayType(UNKNOWN_TYPE);
		} else {
			// 付款方式
			oaReportClearless.setPayType(dictEntity4.getValueName());
		}
		// 运费总额
		oaReportClearless.setTotal(waybillEntity.getTotalFee());
		// 车牌号
		oaReportClearless.setCarNumber(ldpNotSignReportOaDto.getVehicleNo());
		// 上报时间
		oaReportClearless.setReportTime(new Date());
		// 丢货类别
		oaReportClearless.setLostGoodsType("外发少货");

		// 重量
		if (null != waybillEntity.getGoodsWeightTotal()) {
			oaReportClearless.setWeight(waybillEntity.getGoodsWeightTotal().doubleValue());
		} else {
			oaReportClearless.setWeight(Double.valueOf(0));
		}

		// 体积
		if (null != waybillEntity.getGoodsVolumeTotal()) {
			oaReportClearless.setVloume(waybillEntity.getGoodsVolumeTotal().doubleValue());
		} else {
			oaReportClearless.setVloume(Double.valueOf(0));
		}

		// 发货客户编码
		oaReportClearless.setDeliveryCustomerCode(waybillEntity.getDeliveryCustomerCode());
		// 收货客户编码
		oaReportClearless.setReceiveCustomerCode(waybillEntity.getReceiveCustomerCode());

		// 货物名称
		oaReportClearless.setGoods(waybillEntity.getGoodsName());

		// 根据运单查询流水号信息
		List<LabeledGoodEntity> labeledGoodEntityList = labeledGoodService.queryAllSerialByWaybillNo(ldpNotSignReportOaDto.getWayBillId());
		// 少货件数
		oaReportClearless.setNogoodscount(labeledGoodEntityList.size());
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
		oaReportClearless.setSerialNoList(serialNoSb.toString());

		return oaReportClearless;
	}

	public void setWaybillExpressService(
			IWaybillExpressService waybillExpressService) {
		this.waybillExpressService = waybillExpressService;
	}

}

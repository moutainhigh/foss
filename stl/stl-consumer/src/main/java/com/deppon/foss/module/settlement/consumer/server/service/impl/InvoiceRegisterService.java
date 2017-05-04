/*******************************************************************************
 * Copyright 2013 STL TEAM
 * limitations under the License.
 * PROJECT NAME	: stl-consumer
 * FILE PATH : src/main/java/com/deppon/foss/module/settlement/consumer/server/service/impl/InvoiceService.java
 * FILE NAME        	: InvoiceService.java
 * AUTHOR			: FOSS结算系统开发
 * HOME PAGE		: http://www.deppon.com
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 */

package com.deppon.foss.module.settlement.consumer.server.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.xml.ws.Holder;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.ListUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.collections.Transformer;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.esb.header.ESBHeader;
import com.deppon.fims.inter.foss.service.FossToFimsService;
import com.deppon.fims.inter.foss.service.QuotaMarkReq;
import com.deppon.fims.inter.foss.service.QuotaMarkRes;
import com.deppon.fims.inter.foss.service.QuotaReq;
import com.deppon.fims.inter.foss.service.QuotaRes;
import com.deppon.fims.inter.foss.service.Waybill;
import com.deppon.fin.module.claimpay.service.CommonException;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISaleDepartmentService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.ProductEntityConstants;
import com.deppon.foss.module.pickup.waybill.api.server.service.IActualFreightService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.ActualFreightEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.settlement.common.api.server.dao.IProductEntityDao;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementESBDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementReportNumber;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableEntity;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.common.server.service.impl.BillReceivableService;
import com.deppon.foss.module.settlement.common.server.util.SettlementUtil;
import com.deppon.foss.module.settlement.consumer.api.server.dao.IInvoiceRegisterDao;
import com.deppon.foss.module.settlement.consumer.api.server.dao.IOtherRevenueDao;
import com.deppon.foss.module.settlement.consumer.api.server.service.IInvoiceRegisterService;
import com.deppon.foss.module.settlement.consumer.api.server.service.IMergeWaybillService;
import com.deppon.foss.module.settlement.consumer.api.server.service.IWaybillApplyStatusService;
import com.deppon.foss.module.settlement.consumer.api.server.service.IWaybillDetailService;
import com.deppon.foss.module.settlement.consumer.api.shared.domain.OtherRevenueEntity;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.WaybillInvoiceDto;
import com.deppon.foss.module.settlement.consumer.api.shared.vo.InvoiceRegisterVo;
import com.deppon.foss.module.settlement.pay.api.server.dao.IDiscountRateDao;
import com.deppon.foss.module.settlement.pay.api.shared.domain.DiscountRateEntity;
import com.deppon.foss.module.settlement.pay.api.shared.dto.DiscountRateDto;
import com.deppon.foss.util.NumberUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 定额发票登记服务
 * 
 * @author 163576
 * @date 2014-07-10 10:10:23
 */
public class InvoiceRegisterService implements IInvoiceRegisterService {

	/** 日志. */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(InvoiceRegisterService.class);

	/**
	 * 运单（合并运单）详细信息服务
	 */
	private IWaybillDetailService waybillDetailService;
	/**
	 * 获取合并运单号信息服务
	 */
	private IMergeWaybillService mergeWaybillService;
	/**
	 * 运单发票申请状态信息服务
	 */
	private IWaybillApplyStatusService waybillApplyStatusService;

	/** 配合发票，查询运单及小票接口Dao. */
	private IInvoiceRegisterDao invoiceRegisterDao;

	/** 小票DAO. */
	private IOtherRevenueDao otherRevenueDao;

	/** 运单管理服务. */
	private IWaybillManagerService waybillManagerService;

	private IActualFreightService actualFreightService;

	/** 财务系统：发票服务. */
	private FossToFimsService fossToFimsService;

	/**
	 * 应收单服务
	 */
	private BillReceivableService billReceivableService;

	/**
	 * 组织信息服务
	 */
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;

	/**
     * 产品类型Dao
     */
    private IProductEntityDao productEntityDao;
    //蛇口单Dao
    IDiscountRateDao discountRateDao;
	private ISaleDepartmentService saleDepartmentService;

	/**
	 * 常量定义
	 */
	public static final int WAYBILLNOLENGTH = 10;
	public static final String WAYBILLNOSTART = "1";

	/**
	 * 运单关联小票
	 * 
	 * @return the string
	 * @author ddw
	 * @date 2015-04-09
	 */
	@Override
	public InvoiceRegisterVo queryOtherRevenueNosByWaybillNos(InvoiceRegisterVo vo) {
		if (CollectionUtils.isNotEmpty(vo.getWaybillNOs())) {
			List<String> list = 
					invoiceRegisterDao.queryOtherRevenueNosByWaybillNos(vo.getWaybillNOs());
			vo.setOtherRevenueNos(list);
		}
		return vo;
	}

	/**
	 * 验证InvoiceRegisterVo，运单及小票的有效性，并返回
	 * returnVo（success,totalAmount,WaybillEntityList,OtherRevenueEntitieList）
	 * 失败情况 返回 UnWaybillNOs UnOtherRevenueNos
	 * 
	 * @param vo
	 * @return
	 */
	private InvoiceRegisterVo validateInvoiceRegisterVo(InvoiceRegisterVo vo,
			CurrentInfo cInfo) {
		InvoiceRegisterVo returnVo = new InvoiceRegisterVo();

		if (null == vo) {
			throw new SettlementException("定额发票登记服务,非法参数InvoiceRegisterVo："
					+ vo);
		}

		if (CollectionUtils.isEmpty(vo.getWaybillNOs())
				&& CollectionUtils.isEmpty(vo.getOtherRevenueNos())) {
			throw new SettlementException("定额发票登记服务,运单号及小票号都不能空。");
		}

		// 运单集合判断
		List<WaybillEntity> waybillEntityList = null;
		if (CollectionUtils.isNotEmpty(vo.getWaybillNOs())) {
			// 验证输入不能超过150条
			if (vo.getWaybillNOs().size() > SettlementReportNumber.ONE_HUNDRED_AND_FIFTY) {
				throw new SettlementException("定额发票登记服务,参数运单号不能超过150条。");
			}
			// 验证运单有效性
			waybillEntityList = waybillManagerService
					.queryWaybillBasicByNoList(vo.getWaybillNOs());
			if (CollectionUtils.isEmpty(waybillEntityList)) {
				throw new SettlementException("定额发票登记服务，运单号列表都无效，没有查询到有效运单。");
			}

			// 先验证 已作废 已中止 状态 (PC待补录、PDA待补录）条件
			// 再验证 验证运单是出发或到达不是是登记部门

			// 已作废 已中止 状态 (PC待补录、PDA待补录） 过滤
			List<String> waybillStatusList = Arrays.asList(
					WaybillConstants.OBSOLETE, WaybillConstants.ABORTED);
			List<String> pendingTypeList = Arrays.asList(
					WaybillConstants.WAYBILL_STATUS_PDA_PENDING,
					WaybillConstants.WAYBILL_STATUS_PC_PENDING);

			Transformer tfm = new Transformer() {
				@Override
				public Object transform(Object input) {
					return ((WaybillEntity) input).getWaybillNo();
				}
			}; // 获得运单中的运单号

			for (Iterator<WaybillEntity> iterator = waybillEntityList
					.iterator(); iterator.hasNext();) {
				WaybillEntity e = iterator.next();
				// (PC待补录、PDA待补录） 过滤
				if (pendingTypeList.contains(e.getPendingType())) {
					iterator.remove();
				} else {
					// 已作废 已中止状态 过滤
					ActualFreightEntity a = actualFreightService
							.queryByWaybillNo(e.getWaybillNo());
					if (a != null && waybillStatusList.contains(a.getStatus())) {
						iterator.remove();
					}
				}
			}

			// 验证输入存在无效运单
			if (waybillEntityList.size() != vo.getWaybillNOs().size()) {

				if (CollectionUtils.isEmpty(waybillEntityList)) {
					throw new SettlementException(
							"定额发票登记服务，运单号列表都无效，运单已经终止或作废。");
				}
				@SuppressWarnings("unchecked")
				List<String> unWaybills = ListUtils.removeAll(
						vo.getWaybillNOs(),
						CollectionUtils.collect(waybillEntityList, tfm));
				returnVo.setUnWaybillNOs(unWaybills);
			}

			if (CollectionUtils.isEmpty(returnVo.getUnWaybillNOs())) { // 无效运单列表是空时验证通过时，再去验证是否出发或到达部门
				for (Iterator<WaybillEntity> iterator = waybillEntityList
						.iterator(); iterator.hasNext();) {
					WaybillEntity e = iterator.next();
					// 验证运单是出发或到达不是是登记部门 剔除
					if (!(StringUtils.equals(cInfo.getCurrentDeptCode(),
							e.getReceiveOrgCode()) || StringUtils.equals(
							cInfo.getCurrentDeptCode(), e.getLastLoadOrgCode()))) {
						iterator.remove();
					}
				}

				// 验证输入存在无效运单
				if (waybillEntityList.size() != vo.getWaybillNOs().size()) {

					if (CollectionUtils.isEmpty(waybillEntityList)) {
						throw new SettlementException(
								"定额发票登记服务，运单号列表都无效，当前人登录部门和运单出发或到达部门不一致。");
					}
					@SuppressWarnings("unchecked")
					List<String> unDeptWaybillNOs = ListUtils.removeAll(
							vo.getWaybillNOs(),
							CollectionUtils.collect(waybillEntityList, tfm));
					returnVo.setUnDeptWaybillNOs(unDeptWaybillNOs);
				}
			}

		}

		// 小票集合判断
		List<OtherRevenueEntity> otherRevenueEntitieList = null;
		if (CollectionUtils.isNotEmpty(vo.getOtherRevenueNos())) {
			if (vo.getOtherRevenueNos().size() > SettlementReportNumber.ONE_HUNDRED_AND_FIFTY) {
				throw new SettlementException("定额发票登记服务,参数小票号不能超过150条。");
			}
			otherRevenueEntitieList = otherRevenueDao
					.queryOtherRevenueByOtherRevenueNos(
							vo.getOtherRevenueNos(), FossConstants.ACTIVE);
			if (CollectionUtils.isEmpty(otherRevenueEntitieList)) {
				throw new SettlementException("定额发票登记服务，小票号列表都无效，没有查询到有效小票。");
			}

			Transformer tfm = new Transformer() {
				@Override
				public Object transform(Object input) {
					return ((OtherRevenueEntity) input).getOtherRevenueNo();
				}
			}; // 获得小票中的小票号

			if (otherRevenueEntitieList.size() != vo.getOtherRevenueNos()
					.size()) {
				@SuppressWarnings("unchecked")
				List<String> unOtherRevenueNOs = ListUtils.removeAll(
						vo.getOtherRevenueNos(),
						CollectionUtils.collect(otherRevenueEntitieList, tfm));
				returnVo.setUnOtherRevenueNos(unOtherRevenueNOs);
			}

			if (CollectionUtils.isEmpty(returnVo.getUnOtherRevenueNos())) { // 无效小票列表是空时验证通过时，再去验证是否收入部门
				for (Iterator<OtherRevenueEntity> iterator = otherRevenueEntitieList
						.iterator(); iterator.hasNext();) {
					OtherRevenueEntity e = iterator.next();
					// 验证小票收入部门是登记部门 剔除
					if (!StringUtils.equals(cInfo.getCurrentDeptCode(),
							e.getGeneratingOrgCode())) {
						iterator.remove();
					}
				}

				if (otherRevenueEntitieList.size() != vo.getOtherRevenueNos()
						.size()) {
					@SuppressWarnings("unchecked")
					List<String> unDeptOtherRevenueNos = ListUtils.removeAll(vo
							.getOtherRevenueNos(), CollectionUtils.collect(
							otherRevenueEntitieList, tfm));
					returnVo.setUnDeptOtherRevenueNos(unDeptOtherRevenueNos);
				}
			}

		}

		// 设置返回信息
		if (CollectionUtils.isNotEmpty(returnVo.getUnOtherRevenueNos())
				|| CollectionUtils.isNotEmpty(returnVo.getUnWaybillNOs())
				|| CollectionUtils.isNotEmpty(returnVo
						.getUnDeptOtherRevenueNos())
				|| CollectionUtils.isNotEmpty(returnVo.getUnDeptWaybillNOs())) {
			// 如果存在无效的运单号或者小票号，总金额设置为0
			returnVo.setCurPayTotalAmount(BigDecimal.ZERO);
			returnVo.setDestPayOpenAmount(BigDecimal.ZERO);
			returnVo.setNoteTotalAmount(BigDecimal.ZERO);
			returnVo.setSuccess(false); // 验证失败
		} else {
			BigDecimal curPayTotalAmount = BigDecimal.ZERO;
			BigDecimal destPayTotalAmount = BigDecimal.ZERO;
			BigDecimal noteTotalAmount = BigDecimal.ZERO;
			// 追加运单总金额
			if (CollectionUtils.isNotEmpty(waybillEntityList)) {
				for (WaybillEntity entity : waybillEntityList) {
					if (entity == null) {
						throw new SettlementException("定额发票登记服务,运单实体为空");
					}
					// 根据登记部门 与 出发到达部门 来判断 是否 累计 运单上现付 到付金额
					if (StringUtils.equals(cInfo.getCurrentDeptCode(),
							entity.getReceiveOrgCode())) { // 出发部门
						curPayTotalAmount = curPayTotalAmount.add(entity
								.getPrePayAmount());
					}
					if (StringUtils.equals(cInfo.getCurrentDeptCode(),
							entity.getLastLoadOrgCode())) { // 到达部门
						if (entity.getCodAmount() != null
								&& entity.getCodAmount().compareTo(
										BigDecimal.ZERO) > 0) {// 判断是否有代收金额
							destPayTotalAmount = destPayTotalAmount.add(entity
									.getToPayAmount().subtract(
											entity.getCodAmount()));
						} else {
							destPayTotalAmount = destPayTotalAmount.add(entity
									.getToPayAmount());
						}
					}
				}
			}
			// 追加小票总金额
			if (CollectionUtils.isNotEmpty(otherRevenueEntitieList)) {
				for (OtherRevenueEntity entity : otherRevenueEntitieList) {
					if (entity == null) {
						throw new SettlementException("定额发票登记服务,小票实体为空");
					}
					noteTotalAmount = noteTotalAmount.add(entity.getAmount());
				}
			}

			returnVo.setCurPayTotalAmount(curPayTotalAmount);
			returnVo.setDestPayTotalAmount(destPayTotalAmount);
			returnVo.setNoteTotalAmount(noteTotalAmount);
			returnVo.setSuccess(true);
			// 返回运单小票信息，是否加判断 只在保存验证时返回该信息
			returnVo.setWaybillEntityList(waybillEntityList);
			returnVo.setOtherRevenueEntitieList(otherRevenueEntitieList);
		}

		return returnVo;
	}

	/**
	 * 验证运单及小票，并获得运单及小票总金额，可开票金额
	 * 
	 * 查询运单小票总金额，已开票金额信息
	 * 
	 * 根据输入的运单号 小票号，查询运单及小票的总金额，及调用FIMS发票系统查询运单及小票已开票金额
	 * 
	 * 可开票金额 = 总金额 - 已开票金额
	 * 
	 * 返回 InvoiceRegisterVo(totleAmount,openAmount,isSuccess),isSuccess
	 * false标示验证不通过
	 * 
	 * @author 163576
	 * @date 2014-06-6 下午1:10:23
	 * @param vo
	 * @return
	 */
	@Override
	public InvoiceRegisterVo validateAndQueryInvoiceAmounts(
			InvoiceRegisterVo vo, CurrentInfo cInfo) {
		InvoiceRegisterVo returnVo = null;
		try {
			returnVo = this.validateInvoiceRegisterVo(vo, cInfo);

			// 验证成功后调用接口查询可开票金额
			if (returnVo.isSuccess()) {

				// 调用FIMS的webservice接口 返回已开票金额

				QuotaReq request = new QuotaReq();
				String businessId = "";
				if (CollectionUtils.isNotEmpty(vo.getWaybillNOs())) {
					request.getInvoiceTicket().addAll(vo.getWaybillNOs());
					businessId += 'w' + vo.getWaybillNOs().get(0);
				}
				if (CollectionUtils.isNotEmpty(vo.getOtherRevenueNos())) {
					request.getSmallTicket().addAll(vo.getOtherRevenueNos());
					businessId += 'p' + vo.getOtherRevenueNos().get(0);
				}
				// 实例化ESBHeader，并设置到Holder对象中
				ESBHeader header = new ESBHeader();
				header.setEsbServiceCode(SettlementESBDictionaryConstants.ESB_FOSS2ESB_FIMS_SYN_REMAIN);
				// 与业务相关的字段
				header.setBusinessId(businessId);
				header.setExchangePattern(SettlementESBDictionaryConstants.ESB_HEADER__EXCHANGE_PATTERN);
				header.setVersion(SettlementESBDictionaryConstants.ESB_HEADER__VERSION);
				// 消息格式
				header.setMessageFormat(SettlementESBDictionaryConstants.ESB_HEADER__MESSAGE_FORMAT);
				header.setRequestId(UUIDUtils.getUUID());
				// 请求系统
				header.setSourceSystem(SettlementESBDictionaryConstants.ESB_HEADER__SOURCE_SYSTEM);
				Holder<ESBHeader> holder = new Holder<ESBHeader>(header);

				QuotaRes response = fossToFimsService.invoice(request, holder);
				if (response.getRereceiveAmount() == null) {
					throw new CommonException("FIMS可开票余额同步接口异常，预付已开票金额为空！");
				}
				if (response.getDescAmount() == null) {
					throw new CommonException("FIMS可开票余额同步接口异常，到付已开票金额为空！");
				}
				if (response.getOtherAmount() == null) {
					throw new CommonException("FIMS可开票余额同步接口异常，小票已开票金额为空！");
				}
				// 可开票金额 = 总金额 - 已开票金额
				// 接口变更为 现付 到付 小票可开票金额
				returnVo.setCurPayOpenAmount(returnVo.getCurPayTotalAmount()
						.subtract(response.getRereceiveAmount()));
				returnVo.setDestPayOpenAmount(returnVo.getDestPayTotalAmount()
						.subtract(response.getDescAmount()));
				returnVo.setNoteOpenAmount(returnVo.getNoteTotalAmount()
						.subtract(response.getOtherAmount()));

			}

		} catch (BusinessException e) {
			LOGGER.error(e.getErrorCode(), e);
			throw new SettlementException(e.getErrorCode());
		} catch (CommonException e) {
			LOGGER.error(e.getMessage(), e);
			throw new SettlementException("FIMS可开票余额同步接口异常!" + e.getMessage());
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new SettlementException("FIMS可开票余额同步接口Exception异常!"
					+ e.getMessage());
		}

		return returnVo;
	}

	/**
	 * 保存定额发票信息-发送至发票系统.
	 * 
	 * 将本次开票金额，开票员，小票列表，运单列表(运单/小票包括出发营业部， 到达营业部，运单号，运单金额) 发送至FIMS发票系统
	 * 
	 * 返回InvoiceRegisterVo的isSuccess true 申请成功
	 * 
	 * @author 163576
	 * @date 2014-06-6 下午1:10:23
	 * @param vo
	 * @param cInfo
	 * @return
	 */
	@Override
	public InvoiceRegisterVo registerInvoice(InvoiceRegisterVo vo,
			CurrentInfo cInfo) {
		InvoiceRegisterVo returnVo = null;
		try {
			// 先重复验证运单及小票，并获得运单及小票总金额，可开票金额，及验证通过后返回运单集合和小票集合。
			returnVo = this.validateAndQueryInvoiceAmounts(vo, cInfo);

			if (returnVo.isSuccess()) {
				// 验证返回的总金额与可开票金额是否与Vo前台页面一致。
				if (returnVo.getCurPayTotalAmount().compareTo(
						vo.getCurPayTotalAmount()) != 0) {
					throw new SettlementException("提交失败！现付总金额:"
							+ vo.getCurPayTotalAmount() + "->"
							+ returnVo.getCurPayTotalAmount()
							+ ",已有变化，请重新确认定额发票信息");
				}
				if (returnVo.getDestPayTotalAmount().compareTo(
						vo.getDestPayTotalAmount()) != 0) {
					throw new SettlementException("提交失败！到付总金额:"
							+ vo.getDestPayTotalAmount() + "->"
							+ returnVo.getDestPayTotalAmount()
							+ ",已有变化，请重新确认定额发票信息");
				}
				if (returnVo.getNoteTotalAmount().compareTo(
						vo.getNoteTotalAmount()) != 0) {
					throw new SettlementException("提交失败！小票总金额:"
							+ vo.getNoteTotalAmount() + "->"
							+ returnVo.getNoteTotalAmount()
							+ ",已有变化，请重新确认定额发票信息");
				}

				BigDecimal allOpenAmount = NumberUtils.add(
						NumberUtils.add(vo.getCurPayOpenAmount(),
								vo.getDestPayOpenAmount()),
						vo.getNoteOpenAmount());
				if (allOpenAmount.compareTo(BigDecimal.ZERO) <= 0) {
					throw new SettlementException("提交失败！可开票余额必须大于0，不允许重开发票！");
				}
				if (returnVo.getCurPayOpenAmount().compareTo(
						vo.getCurPayOpenAmount()) != 0) {
					throw new SettlementException("提交失败！现付可开票余额:"
							+ vo.getCurPayOpenAmount() + "->"
							+ returnVo.getCurPayOpenAmount()
							+ ",已有变化，请重新确认定额发票信息");
				}
				if (returnVo.getDestPayOpenAmount().compareTo(
						vo.getDestPayOpenAmount()) != 0) {
					throw new SettlementException("提交失败！到付可开票余额:"
							+ vo.getDestPayOpenAmount() + "->"
							+ returnVo.getDestPayOpenAmount()
							+ ",已有变化，请重新确认定额发票信息");
				}
				if (returnVo.getNoteOpenAmount().compareTo(
						vo.getNoteOpenAmount()) != 0) {
					throw new SettlementException("提交失败！小票可开票余额:"
							+ vo.getNoteOpenAmount() + "->"
							+ returnVo.getNoteOpenAmount()
							+ ",已有变化，请重新确认定额发票信息");
				}
				// 验证本次开票金额不能大于(总可开票余额+100)
				BigDecimal openAmountAdd100 = allOpenAmount.add(BigDecimal
						.valueOf(100));
				if (vo.getThisAmount().compareTo(openAmountAdd100) > 0) {
					throw new SettlementException("提交失败！不能超额开票!");
				}

				/**
				 * 运单 wb.receive_Org_Code, --发货部门编码 wb.Customer_Pickup_Org_Code,
				 * --到达部门编码 小票收入部门 t.generating_org_code -- 发货部门编码
				 */

				// 调用FIMS的webservice接口 保存定额发票信息-发送至发票系统
				QuotaMarkReq request = new QuotaMarkReq();
				request.setEmpcode(cInfo.getEmpCode());
				request.setAmount(vo.getThisAmount().toString());
				request.setBillDept(this.queryOrgUnifiedCodeByOrgCode(cInfo
						.getCurrentDeptCode()));
				Waybill w = null;
				if (CollectionUtils.isNotEmpty(returnVo.getWaybillEntityList())) {
					for (WaybillEntity entity : returnVo.getWaybillEntityList()) {
						ActualFreightEntity actentity = actualFreightService
								.queryByWaybillNo(entity.getWaybillNo());

						w = new Waybill();
						w.setWayBillNo(entity.getWaybillNo());
						w.setBusinessType(Arrays
								.asList(ProductEntityConstants.PRICING_PRODUCT_EXPRESS_PACKAGE,
										ProductEntityConstants.PRICING_PRODUCT_ROUND_COUPON_PACKAGE)
								.indexOf(entity.getProductCode()) > 0 ? "01"
								: "02");
						w.setPrePayAmount(entity.getPrePayAmount());
						w.setToPayAmount((entity.getCodAmount() != null && entity
								.getCodAmount().compareTo(BigDecimal.ZERO) > 0) ? entity
								.getToPayAmount().subtract(
										entity.getCodAmount()) : entity
								.getToPayAmount());
						w.setRereceiveCustomerCode(entity
								.getReceiveCustomerCode());
						w.setDeliverCustomerCode(w.getDeliverCustomerCode());
						w.setReceiveOrgCode(this
								.queryOrgUnifiedCodeByOrgCode(entity
										.getReceiveOrgCode()));
						w.setDescOrgCode(this
								.queryOrgUnifiedCodeByOrgCode(entity
										.getCustomerPickupOrgCode()));
						w.setBillTime(SettlementUtil.dateToXmlDate(entity
								.getBillTime()));

						if (actentity != null) {
							w.setInvoiceMark(StringUtils.equals(
									actentity.getInvoice(),
									SettlementDictionaryConstants.SETTLEMENT_INVOICE_MARK_ONE) ? "01"
									: "02");
							w.setReceiveDunningDeptCode(actentity
									.getStartReminderOrgCode());
							w.setReceiveContractDeptCode(actentity
									.getStartContractOrgCode());
							w.setDescDunningDeptCode(actentity
									.getArriveReminderOrgCode());
							w.setDescContractDeptCode(actentity
									.getArriveContractOrgCode());
						}

						request.getWaybills().add(w);
					}
				}
				if (CollectionUtils.isNotEmpty(returnVo
						.getOtherRevenueEntitieList())) {
					@SuppressWarnings("unchecked")
					List<String> otherRevenueNos = (List<String>) CollectionUtils
							.collect(returnVo.getOtherRevenueEntitieList(),
									new Transformer() {
										@Override
										public Object transform(Object input) {
											return ((OtherRevenueEntity) input)
													.getOtherRevenueNo();
										}
									});

					// 查询应收单
					List<BillReceivableEntity> billReceives = billReceivableService
							.queryBySourceBillNOs(
									otherRevenueNos,
									SettlementDictionaryConstants.BILL_RECEIVABLE__SOURCE_BILL_TYPE__OTHER_REVENUE,
									FossConstants.ACTIVE);

					for (final OtherRevenueEntity entity : returnVo
							.getOtherRevenueEntitieList()) {
						w = new Waybill();
						w.setWayBillNo(entity.getOtherRevenueNo());
						w.setOtherRevenueNo(null);

						w.setBusinessType(Arrays
								.asList(ProductEntityConstants.PRICING_PRODUCT_EXPRESS_PACKAGE,
										ProductEntityConstants.PRICING_PRODUCT_ROUND_COUPON_PACKAGE)
								.indexOf(entity.getProductCode()) > 0 ? "01"
								: "02");
						w.setInvoiceMark(StringUtils.equals(
								entity.getInvoiceMark(),
								SettlementDictionaryConstants.SETTLEMENT_INVOICE_MARK_ONE) ? "01"
								: "02");
						w.setPrePayAmount(entity.getAmount());
						w.setRereceiveCustomerCode(StringUtils.equals(
								entity.getGeneratingComCode(),
								entity.getCreateOrgCode()) ? entity
								.getCustomerCode() : null);
						w.setCustomerName(entity.getCustomerName());
						w.setReceiveOrgCode(this
								.queryOrgUnifiedCodeByOrgCode(entity
										.getGeneratingOrgCode()));
						w.setBillTime(SettlementUtil.dateToXmlDate(entity
								.getBusinessDate()));
						BillReceivableEntity billReceivableEntity = (BillReceivableEntity) CollectionUtils
								.find(billReceives, new Predicate() {
									@Override
									public boolean evaluate(Object object) {
										if (((BillReceivableEntity) object)
												.getSourceBillNo()
												.equals(entity
														.getOtherRevenueNo())) {
											return true;
										}
										return false;
									}
								});
						if (billReceivableEntity != null) {
							w.setReceiveDunningDeptCode(this
									.queryOrgUnifiedCodeByOrgCode(billReceivableEntity
											.getDunningOrgCode()));
							w.setReceiveContractDeptCode(this
									.queryOrgUnifiedCodeByOrgCode(billReceivableEntity
											.getContractOrgCode()));
						}
						request.getSmallTickets().add(w);
					}
				}

				// 实例化ESBHeader，并设置到Holder对象中
				ESBHeader header = new ESBHeader();
				header.setEsbServiceCode(SettlementESBDictionaryConstants.ESB_FOSS2ESB_FIMS_SYN_REGISTER);
				// 与业务相关的字段
				header.setBusinessId(cInfo.getEmpCode());
				header.setExchangePattern(SettlementESBDictionaryConstants.ESB_HEADER__EXCHANGE_PATTERN);
				header.setVersion(SettlementESBDictionaryConstants.ESB_HEADER__VERSION);
				// 消息格式
				header.setMessageFormat(SettlementESBDictionaryConstants.ESB_HEADER__MESSAGE_FORMAT);
				header.setRequestId(UUIDUtils.getUUID());
				// 请求系统
				header.setSourceSystem(SettlementESBDictionaryConstants.ESB_HEADER__SOURCE_SYSTEM);
				Holder<ESBHeader> holder = new Holder<ESBHeader>(header);

				QuotaMarkRes response = fossToFimsService.quotaRegist(request,
						holder);
				if (response == null) {
					throw new SettlementException(
							"FIMS定额发票登记信息同步接口请求异常，返回响应消息为空！");
				}

				returnVo.setSuccess(response.isIsSucess());
				if (!response.isIsSucess()) {
					throw new CommonException("FIMS定额发票登记信息同步接口请求异常!"
							+ response.getFailReason());
				}
			}

		} catch (BusinessException e) {
			LOGGER.error(e.getErrorCode(), e);
			throw new SettlementException("FOSS业务异常!" + e.getErrorCode());
		} catch (CommonException e) {
			LOGGER.error(e.getMessage(), e);
			throw new SettlementException("FIMS定额发票登记信息同步接口请求异常!"
					+ e.getMessage());
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new SettlementException("FIMS定额发票登记信息同步接口请求Exception异常!"
					+ e.getMessage());
		}
		// 默认返回失败
		return returnVo;
	}

	/**
	 * 根据部门编码查询获得标杆编码.查询不到返回部门编码
	 * 
	 * @param orgCode
	 * @return
	 */
	private String queryOrgUnifiedCodeByOrgCode(String orgCode) {
		// 通过综合接口获取部门
		OrgAdministrativeInfoEntity orgEntity = orgAdministrativeInfoService
				.queryOrgAdministrativeInfoByCodeClean(orgCode); // 部门
		return null == orgEntity ? orgCode : orgEntity.getUnifiedCode();

	}

	/**
	 * 根据运单号或者小票号集合返回运单及小票集合
	 * 
	 * 在发票管理系统中输入运单号（零担、快递代理）或小票单号，返回运单或小票的列表信息，用于发票开具。 如果运单中包括小票，则该小票也要返回。
	 * 
	 * @author 163576
	 * @date 2014-07-10 10:10:23
	 * @param waybillNos
	 * @param otherRevenues
	 * @return
	 * @throws com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException
	 */
	@Override
	public WaybillInvoiceDto queryWaybillReceiptInfoForFPList(
			List<String> waybillNos,List<String> mergeWaybillNos, List<String> otherRevenues,List<String> hhStatementNoList) {
		LOGGER.info("start,查询运单或小票的列表信息，用于发票开具。");
		WaybillInvoiceDto dto = new WaybillInvoiceDto();
		if (CollectionUtils.isEmpty(hhStatementNoList)) {
			if (CollectionUtils.isEmpty(waybillNos)&& CollectionUtils.isEmpty(mergeWaybillNos) && CollectionUtils.isEmpty(otherRevenues)) {
				LOGGER.error("运单号与小票号两者至少必录其一，也可两者同时录入，支持多个运单号及多个小票号！");
				throw new SettlementException("查询运单或小票的列表信息,运单号与小票号两者都为空！");
			} else {
				List<String> mergedWaybillNos = new ArrayList<String>();//已经合并的运单号
				List<String> unMergedWaybillNos = new ArrayList<String>();//未合并的运单号
				if (CollectionUtils.isNotEmpty(waybillNos)) { // 有运单时，返回运单信息
					/**
					 * 修改 322906 20161124
					 * 已经合并的运单通过字段mergedWaybillNoList返回
					 */
					//判断运单是否做过合并
					for(String waybillNo : waybillNos){
						String mergeWaybill = waybillDetailService.queryMergeWaybillNoByWaybillNo(waybillNo);
						if(StringUtils.isNotBlank(mergeWaybill)){
							mergedWaybillNos.add(waybillNo);
						}else{
							unMergedWaybillNos.add(waybillNo);
						}
					}
					//如果运单做过合并，将该运单返回
					if(mergedWaybillNos.size()>0){
						dto.setMergedWaybillNoList(mergedWaybillNos);
					}
					if(unMergedWaybillNos.size()>0){
						List<BillReceivableEntity> billReceivablelist = billReceivableService.queryReceivableByWaybillNOs(unMergedWaybillNos);
						if(CollectionUtils.isNotEmpty(billReceivablelist)){
							for (int i = 0; i < billReceivablelist.size(); i++) {
								BillReceivableEntity entity = billReceivablelist.get(i);
								/*if (StringUtil.isNotEmpty(entity.getIsDiscount())&& FossConstants.YES.equals(entity.getIsDiscount())) {
									if (StringUtil.isEmpty(entity.getStatementBillNo())|| SettlementConstants.DEFAULT_BILL_NO.equals(entity.getStatementBillNo())) {
										throw new SettlementException("优惠客户应收单没有做对账单不允许开发票！");
									}
								}*/
								//329757 判断付款方式是否是月结/临欠,并且开单类型是始发应收
								if(((SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CREDIT).equals(entity.getPaymentType()) || 
										(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__DEBT).equals(entity.getPaymentType())) && 
										(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__ORIGIN_RECEIVABLE).equals(entity.getBillType())){
									WaybillEntity waybillEntity = waybillManagerService.queryWaybillBasicByNo(entity.getWaybillNo());
									List<String> isDisCountBacks = new ArrayList<String>();
									//查询出目前所有快递的产品类型
									List<String> productCodes = productEntityDao.queryPackageProductCode();
									//{"RCP","EPEP","DEAP","GTSE","PACKAGE","ICES","GTEC","ICEC","ICSE"};
									//判断改单是零担还是快递
									if(productCodes.contains(waybillEntity.getProductCode())){
										//根据运单号查询客户是否是事后折客户
										//快递事后折
										isDisCountBacks = invoiceRegisterDao.queryIsDisCountByWaybill(entity.getWaybillNo());
										if(CollectionUtils.isNotEmpty(isDisCountBacks) && isDisCountBacks!=null){
										//判断运单是否是本月运单
										//开单月份是当前月份
										if(SettlementUtil.isInCurrentMonth(entity.getBusinessDate())){
											throw new SettlementException("事后折客户本月运单无法申请发票！");
										}else{
											//是否存在折扣单--快递
											int count=invoiceRegisterDao.queryDisCountExe(entity.getWaybillNo());
												if(count==0){
													//运单开单当月出发客户编码是否满足折扣率生成条件
													Date firstMonthDay  = SettlementUtil.getFirstDayOfMonth(entity.getBusinessDate());
													Date endMonthDay = SettlementUtil.addMonthToDate(firstMonthDay, 1);
													DiscountRateDto rateDto = new DiscountRateDto();
													rateDto.setCustomerCode(entity.getCustomerCode());
													rateDto.setEndDate(endMonthDay);
													rateDto.setActive(FossConstants.YES);
											        rateDto.setCreateType(SettlementDictionaryConstants.SETTLEMENT__CREATE_TYPE__MANUAL);
											        String[] billTypes = {SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__ORIGIN_RECEIVABLE,
											                SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_RECEIVABLE,
											                SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__AIR_AGENCY,
											                SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_PARTIAL_LINE,
											                SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__LAND_STOWAGE_AGENCY_COD
											        };
											        //付款方式为临时欠款、月结
											        String[] paymetnTypes = {SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CREDIT,
											                SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__DEBT};
											        //产品类型为快递
											        String[] productCode =productCodes.toArray(new String[0]);
											        rateDto.setPaymentTypes(paymetnTypes);
											        rateDto.setBillTypes(billTypes);
											        rateDto.setProductCodes(productCode);
											        DiscountRateEntity discountRate = discountRateDao.makeDiscountRate(rateDto);
											        if(discountRate!=null){
											        	if((discountRate.getCodCrmId() != null
											                &&discountRate.getInsuranceCrmId() != null
											                &&discountRate.getTransportCrmId() != null)){
											        		throw new SettlementException("事后折客户未做折扣单不允许申请发票！");
											        	}
											        }
												}
											}
										}
									}else{
										//零担事后折
										int isDisCountBack = invoiceRegisterDao.queryIsDisCountNoExp(entity.getWaybillNo());
										if(isDisCountBack != 0){
										//判断运单是否是本月运单
										//开单月份是当前月份
										if(SettlementUtil.isInCurrentMonth(entity.getBusinessDate())){
											throw new SettlementException("事后折客户本月运单无法申请发票！");
										}else{
											//是否存在折扣单--零担
											int count=invoiceRegisterDao.queryDisCountNoexe(entity.getWaybillNo());
												if(count==0){
													//运单开单当月出发客户编码是否满足折扣率生成条件
													 //当前月份的前一个月
													Date firstMonthDay  = SettlementUtil.getFirstDayOfMonth(entity.getBusinessDate());
													Date endMonthDay = SettlementUtil.addMonthToDate(firstMonthDay, 1);
													 DiscountRateDto rateDto = new DiscountRateDto();
													 rateDto.setEndDate(endMonthDay);
													 rateDto.setCustomerCode(entity.getCustomerCode());
													//零担生成折扣率条件
													 DiscountRateEntity discountRate = discountRateDao.makeDisCountRateNoExe(rateDto);
													 if(discountRate!=null){
														 throw new SettlementException("事后折客户未做折扣单不允许申请发票！");
													 }
												 }
											}
										}
									}
								}
							}	
						}
						dto.setWaybillDtoList(invoiceRegisterDao.queryWaybillInfoForInvoiceList(unMergedWaybillNos));
					}
				}
				//如果没有运单号，这里的unMergedWaybillNos也是为空的
				if(CollectionUtils.isNotEmpty(unMergedWaybillNos)||CollectionUtils.isNotEmpty(otherRevenues)){
					dto.setOtherRevenueDtoList(invoiceRegisterDao.queryOtherRevenueInfoForInvoiceList(unMergedWaybillNos,otherRevenues));
				}
				if(CollectionUtils.isNotEmpty(mergeWaybillNos)){//有合并运单时，返回合并运单信息
					List<WaybillInvoiceDto> waybillInvoiceDtos = mergeWaybillService.getMergeWaybillByMergeWaybillNo(mergeWaybillNos);
					dto.setMergeWaybillDtoList(waybillInvoiceDtos);
				}
				// 去除始发部门为合伙人的小票与运单号查询得到的数据
				dto = removeHhrData(dto);
			}
		} else {
			if (CollectionUtils.isNotEmpty(waybillNos)|| CollectionUtils.isNotEmpty(otherRevenues)) {
				LOGGER.error("当有合伙人对账单号时，运单号与小票号都为空");
				throw new SettlementException("当有合伙人对账单号时，运单号与小票号都为空！");
			} else {
				dto.setHhStatementDtoList(invoiceRegisterDao.queryHhStatementInfoForInvoiceList(hhStatementNoList));
			}
		}

		LOGGER.info("查询运单或小票的列表信息，用于发票开具。end");
		return dto;
	}

	// 去除始发部门为合伙人的数据 2016-6-12需求变更：开单部门为合伙人部门，且产品类型为整车的运单可以进行申请发票--340403
	private WaybillInvoiceDto removeHhrData(WaybillInvoiceDto dto) {
		String pro = "03";
		// TODO Auto-generated method stub
		List<WaybillInvoiceDto> temp = dto.getWaybillDtoList();
		if (temp != null && temp.size() != 0) {
			for (int i = 0; i < temp.size(); i++) {
				String receiveOrgCode = temp.get(i).getReceiveOrgCode();
				OrgAdministrativeInfoEntity orgEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByUnifiedCodeNoCache(receiveOrgCode);
				if (null != orgEntity && StringUtil.isNotBlank(orgEntity.getCode())) {
					SaleDepartmentEntity entity = saleDepartmentService .querySaleDepartmentInfoByCode(orgEntity.getCode());
					if (null != entity && FossConstants.YES.equals(entity.getIsLeagueSaleDept())) {
						if (!pro.equals(temp.get(i).getProduct())) {
							temp.remove(i);
						}
					}
				}
			}
		}
		dto.setWaybillDtoList(temp);
		return dto;
	}

	
	/**
	 * @param invoiceRegisterDao
	 *            the invoiceRegisterDao to set
	 */
	public void setInvoiceRegisterDao(IInvoiceRegisterDao invoiceRegisterDao) {
		this.invoiceRegisterDao = invoiceRegisterDao;
	}

	/**
	 * @param otherRevenueDao
	 *            the otherRevenueDao to set
	 */
	public void setOtherRevenueDao(IOtherRevenueDao otherRevenueDao) {
		this.otherRevenueDao = otherRevenueDao;
	}

	/**
	 * @param waybillManagerService
	 *            the waybillManagerService to set
	 */
	public void setWaybillManagerService(IWaybillManagerService waybillManagerService) {
		this.waybillManagerService = waybillManagerService;
	}

	/**
	 * @param fossToFimsService
	 *            the fossToFimsService to set
	 */
	public void setFossToFimsService(FossToFimsService fossToFimsService) {
		this.fossToFimsService = fossToFimsService;
	}

	/**
	 * @param orgAdministrativeInfoService
	 *            the orgAdministrativeInfoService to set
	 */
	public void setOrgAdministrativeInfoService(IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

	/**
	 * @param actualFreightService
	 *            the actualFreightService to set
	 */
	public void setActualFreightService(IActualFreightService actualFreightService) {
		this.actualFreightService = actualFreightService;
	}

	public void setBillReceivableService(BillReceivableService billReceivableService) {
		this.billReceivableService = billReceivableService;
	}

	public void setSaleDepartmentService(ISaleDepartmentService saleDepartmentService) {
		this.saleDepartmentService = saleDepartmentService;
	}

	public IWaybillDetailService getWaybillDetailService() {
		return waybillDetailService;
	}

	public void setWaybillDetailService(IWaybillDetailService waybillDetailService) {
		this.waybillDetailService = waybillDetailService;
	}

	public IMergeWaybillService getMergeWaybillService() {
		return mergeWaybillService;
	}

	public void setMergeWaybillService(IMergeWaybillService mergeWaybillService) {
		this.mergeWaybillService = mergeWaybillService;
	}

	public IWaybillApplyStatusService getWaybillApplyStatusService() {
		return waybillApplyStatusService;
	}

	public void setWaybillApplyStatusService(IWaybillApplyStatusService waybillApplyStatusService) {
		this.waybillApplyStatusService = waybillApplyStatusService;
	}

	public void setProductEntityDao(IProductEntityDao productEntityDao) {
		this.productEntityDao = productEntityDao;
	}

	public void setDiscountRateDao(IDiscountRateDao discountRateDao) {
		this.discountRateDao = discountRateDao;
	}
}

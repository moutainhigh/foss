package com.deppon.foss.module.settlement.consumer.server.action;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.consumer.api.server.service.IAirBillPaidCODService;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.AirBillPaidCODGridDto;
import com.deppon.foss.module.settlement.consumer.api.shared.vo.AirBillPaidCODConditionVo;
import com.deppon.foss.module.settlement.consumer.api.shared.vo.AirBillPaidCodVo;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.NumberUtils;

/**
 * 空运代收货款审核.
 *
 * @author 000123-foss-huangxiaobo
 * @date 2012-10-29 下午3:32:13
 */
public class AirBillPaidCODAction extends AbstractAction {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LogManager
			.getLogger(AirBillPaidCODAction.class);

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** 空运代收货款服务. */
	private IAirBillPaidCODService airBillPaidCodService;

	/** 查询条件VO. */
	private AirBillPaidCodVo vo;

	/**
	 * 查询.
	 *
	 * @return the string
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-11-8 下午5:34:56
	 */
	@JSON
	public String query() {

		// 获得查询条件
		AirBillPaidCODConditionVo conditionVo = vo.getQueryVo();
		// 如果是按运单查询
		if (conditionVo.getQueryType().equals("queryByWaybillNo")) {
			// 如果单号不为空,直接按单号进行查询
			if (!StringUtils.isEmpty(conditionVo.getWaybillNo())) {

				try {
					// 将字符转换成List
					List<String> waybillNos = new ArrayList<String>();
					String[] wayBillArray = conditionVo.getWaybillNo().split(
							",");
					// 将数组转换成List
					Collections.addAll(waybillNos, wayBillArray);
					// 设置查询条件
					conditionVo.setWaybillNoSet(waybillNos);

					// 按运单号进行查询
					vo.setGrid(this.airBillPaidCodService.queryByWaybillNos(
							FossUserContext.getCurrentInfo(),
							conditionVo.getWaybillNoSet()));
					// 结果集不会空时，设置为行数，否则设置为0
					if (!CollectionUtils.isEmpty(vo.getGrid())) {
						// 总行数
						this.totalCount = (long) vo.getGrid().size();
						// 合计空运代收货款的金额
						vo.setTotalAmount(totalCODAmount(vo.getGrid()));
					} else {
						this.totalCount = 0l;
						vo.setTotalAmount(BigDecimal.ZERO);
					}
				} catch (BusinessException ex) {

					LOGGER.error("空运代收货款审核之按运单查询出现异常：" + ex.getMessage(), ex);

					return this.returnError(ex);
				}
			} else {
				return this.returnError("按运单查询时，运单号不能为空！");
			}
		}
		// 后者按日期进行查询
		else {
			try {
				// 将字符串日期转换成日期格式
				this.vo.setQueryVo(this.parseVoJsonParams(vo.getQueryVo()));

				// 按签收日期查询
				if (this.vo.getQueryVo().getInceptSignDate() != null
						&& this.vo.getQueryVo().getEndSignDate() != null) {
					
					// 检查签收时间范围,是否在7天之类
					if (DateUtils.getTimeDiff(this.vo.getQueryVo()
							.getInceptSignDate(), this.vo.getQueryVo()
							.getEndSignDate()) > SettlementConstants.DATE_LIMIT_DAYS_WEEK) {
						
						return this.returnError("签收日期范围必须在"
								+ SettlementConstants.DATE_LIMIT_DAYS_WEEK+ "之内!");
					}
					
					//结束日期默认加1
					conditionVo.setEndSignDate(DateUtils.addDayToDate(conditionVo.getEndSignDate(), 1));

					// 总行数
					AirBillPaidCODGridDto airDto = this.airBillPaidCodService
							.queryTotalRowsBySignDate(
									FossUserContext.getCurrentInfo(), conditionVo);
					this.totalCount = airDto.getTotalCount();
					if (totalCount > 0) {
						// 获得结果结果集
						this.vo.setGrid(this.airBillPaidCodService
								.queryBySignDate(
										FossUserContext.getCurrentInfo(), conditionVo, this.start, limit));
						// 合计空运代收货款的金额
						vo.setTotalAmount(airDto.getTotalAmount());
					} else {
						// 没有查询结果时，清空查询结果
						this.vo.setGrid(null);
						vo.setTotalAmount(BigDecimal.ZERO);
					}
				}
				// 按审核日期查询
				else if (this.vo.getQueryVo().getInceptAuditDate() != null
						&& this.vo.getQueryVo().getEndAuditDate() != null) {
					
					// 检查审核时间范围,是否在7天之类
					if (DateUtils.getTimeDiff(this.vo.getQueryVo()
							.getInceptAuditDate(), this.vo.getQueryVo()
							.getEndAuditDate()) > SettlementConstants.DATE_LIMIT_DAYS_WEEK) {
						
						return this.returnError("审核日期范围必须在"
								+ SettlementConstants.DATE_LIMIT_DAYS_WEEK + "之内!");
					}
					
					//结束日期默认加1
					conditionVo.setEndAuditDate(DateUtils.addDayToDate(conditionVo.getEndAuditDate(), 1));
					
					// 总行数
					AirBillPaidCODGridDto airDto =  this.airBillPaidCodService
							.queryTotalRowsByAuditDate(
									FossUserContext.getCurrentInfo(), conditionVo);
					this.totalCount = airDto.getTotalCount();
					// 大于0时，查询结果集
					if (totalCount > 0) {
						// 获得结果结果集
						this.vo.setGrid(this.airBillPaidCodService
								.queryByAuditDate(
										FossUserContext.getCurrentInfo(), conditionVo, this.start, limit));
						// 合计空运代收货款的金额
						vo.setTotalAmount(airDto.getTotalAmount());
					} else {
						// 没有查询结果时，清空查询结果
						this.vo.setGrid(null);
						vo.setTotalAmount(BigDecimal.ZERO);
					}
				} else {
					return this.returnError("审核日期与签收日期至少要指定一组！");
				}
			} catch (BusinessException ex) {

				LOGGER.error("空运代收货款审核之按日期查询出现异常：" + ex.getMessage(), ex);

				return this.returnError(ex.getMessage());
			} catch (ParseException ex) {
				LOGGER.error("空运代收货款审核之按日期查询出现异常：" + ex.getMessage(), ex);

				return this.returnError(ex.getMessage());
			}
		}
		return this.returnSuccess();
	}

	/**
	 * 将查询条件的的字符串日期值转换成Date值.
	 *
	 * @param vo the vo
	 * @return the air bill paid cod condition vo
	 * @throws ParseException the parse exception
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-11-9 下午4:42:49
	 */
	private AirBillPaidCODConditionVo parseVoJsonParams(
			AirBillPaidCODConditionVo vo) throws ParseException {
		if (vo == null) {
			return null;
		} else {
			AirBillPaidCODConditionVo resultVo = vo;
			// 审核日期
			if (!StringUtils.isEmpty(vo.getInceptAuditDateStr())) {
				resultVo.setInceptAuditDate(DateUtils.convert(vo.getInceptAuditDateStr()));
			}
			if (!StringUtils.isEmpty(vo.getEndAuditDateStr())) {
				resultVo.setEndAuditDate(DateUtils.convert(vo.getEndAuditDateStr()));
			}
			// 签收日期
			if (!StringUtils.isEmpty(vo.getInceptSignDateStr())) {
				resultVo.setInceptSignDate(DateUtils.convert(vo.getInceptSignDateStr()));
			}
			//签收结束时间
			if (!StringUtils.isEmpty(vo.getEndSignDateStr())) {
				resultVo.setEndSignDate(DateUtils.convert(vo.getEndSignDateStr()));
			}
			return resultVo;
		}
	}

	/**
	 * 审核代收货款.
	 *
	 * @return the string
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-10-30 下午2:16:47
	 */
	@JSON
	public String auditAirCOD() {

		try {

			if (vo == null) {
				return returnError("代收货款VO信息为空");
			}

			// 判断代收货款信息ID不为空
			String[] entityIds = vo.getEntityIds();
			if (ArrayUtils.isEmpty(entityIds)) {
				return returnError("请选择需要审核的空运代收货款信息");
			}

			LOGGER.info("审核空运代收货款," + entityIds.length + "条代收货款记录");

			// 调用接口审核空运代收货款
			List<String> codIds = Arrays.asList(entityIds);
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			// 生效代收货款记录
			airBillPaidCodService.makeEffectiveBillCOD(codIds, currentInfo);

			LOGGER.info("审核空运代收货款结束");

		} catch (BusinessException e) {
			LOGGER.error("审核代收货款出现异常：" + e.getMessage(), e);
			return returnError(e);
		}

		return returnSuccess();
	}

	/**
	 * 反审核空运代收货款.
	 *
	 * @return the string
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-10-30 下午2:18:23
	 */
	public String reverseAuditAirCOD() {

		try {

			if (vo == null) {
				return returnError("代收货款VO信息为空");
			}

			// 判断代收货款信息ID不为空
			String[] entityIds = vo.getEntityIds();
			if (ArrayUtils.isEmpty(entityIds)) {
				return returnError("请选择需要审核的空运代收货款信息");
			}

			LOGGER.info("反审核空运代收货款," + entityIds.length + "条代收货款记录");

			// 调用接口反审核空运代收货款
			List<String> codIds = Arrays.asList(entityIds);
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			airBillPaidCodService.reverseEffectiveBillCOD(codIds, currentInfo);

			LOGGER.info("反审核空运代收货款结束");

		} catch (BusinessException e) {
			LOGGER.error("反审核空运代收货款异常：" + e.getMessage(), e);
			return returnError(e);
		}

		return returnSuccess();
	}
	
	/**
	 * 合计空运代收货款的金额.
	 *
	 * @param list the list
	 * @return the big decimal
	 * @author guxinhua
	 * @date 2013-2-2 上午10:29:41
	 */
	private BigDecimal totalCODAmount(List<AirBillPaidCODGridDto> list){
		BigDecimal totalCODAmount = BigDecimal.ZERO;
		// 合计空运代收货款的金额
		for (AirBillPaidCODGridDto airBillPaidCODGridDto : list) {
			totalCODAmount = NumberUtils.add(totalCODAmount, NumberUtils.createBigDecimal(airBillPaidCODGridDto.getCodAmount()));
		}
		return totalCODAmount;
	}

	/**
	 * Sets the air bill paid cod service.
	 *
	 * @param airBillPaidCodService the new air bill paid cod service
	 */
	public void setAirBillPaidCodService(
			IAirBillPaidCODService airBillPaidCodService) {
		this.airBillPaidCodService = airBillPaidCodService;
	}

	/**
	 * Gets the vo.
	 *
	 * @return vo
	 */
	public AirBillPaidCodVo getVo() {
		return vo;
	}

	/**
	 * Sets the vo.
	 *
	 * @param vo the new vo
	 */
	public void setVo(AirBillPaidCodVo vo) {
		this.vo = vo;
	}

}

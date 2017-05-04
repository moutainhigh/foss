package com.deppon.foss.module.settlement.consumer.server.action;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.consumer.api.server.service.IBillClaimService;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.BillClaimResultDto;
import com.deppon.foss.module.settlement.consumer.api.shared.vo.BillClaimVo;
import com.deppon.foss.util.DateUtils;

/**
 * 理赔action.
 *
 * @author foss-qiaolifeng
 * @date 2013-1-29 下午2:03:39
 */
public class BillClaimQueryAction extends AbstractAction {

	/** 理赔action序列号. */
	private static final long serialVersionUID = 3852171886471592876L;

	/** 声明日志对象. */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(BillClaimQueryAction.class);

	/** 理赔Vo. */
	private BillClaimVo billClaimVo;

	/** 注入理赔服务service. */
	private IBillClaimService billClaimService;

	/**
	 * 查询理赔单.
	 *
	 * @return the string
	 * @author foss-qiaolifeng
	 * @date 2013-1-29 下午2:10:57
	 */
	@JSON
	public String queryBillClaim() {

		try {
			// 设置查询条件
			setQueryConditionForParam();

			// 获取当前登录用户信息
			CurrentInfo cInfo = FossUserContext.getCurrentInfo();
			
			//设置查询类型为：理赔
			billClaimVo.getBillClaimQueryDto().setType(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__CLAIM);

			// 查询理赔列表
			BillClaimResultDto billClaimResultDto = billClaimService
					.queryBillClaimList(billClaimVo.getBillClaimQueryDto(),
							getStart(), getLimit(), cInfo);

			// 设置返回值
			this.setTotalCount(billClaimResultDto.getBillClaimTotalRows());
			billClaimVo.setBillClaimResultDto(billClaimResultDto);

		} catch (BusinessException e) {

			LOGGER.error("查询理赔信息：" + e.getMessage(), e);
			// 返回错误信息
			return returnError(e);
		}

		return returnSuccess();
	}
	
	/**
	 * 查询服务补救单.
	 *
	 * @return the string
	 * @author foss-qiaolifeng
	 * @date 2013-1-29 下午2:10:57
	 */
	@JSON
	public String queryBillClaimCP() {

		try {
			// 设置查询条件
			setQueryConditionForParam();

			// 获取当前登录用户信息
			CurrentInfo cInfo = FossUserContext.getCurrentInfo();
			
			// 查询理赔列表
			BillClaimResultDto billClaimResultDto = billClaimService
					.queryBillClaimList(billClaimVo.getBillClaimQueryDto(),
							getStart(), getLimit(), cInfo);

			// 设置返回值
			this.setTotalCount(billClaimResultDto.getBillClaimTotalRows());
			billClaimVo.setBillClaimResultDto(billClaimResultDto);

		} catch (BusinessException e) {

			LOGGER.error("查询理赔信息：" + e.getMessage(), e);
			// 返回错误信息
			return returnError(e);
		}

		return returnSuccess();
	}

	/**
	 * 退回理赔单.
	 *
	 * @return the string
	 * @author foss-qiaolifeng
	 * @date 2013-1-29 下午2:13:54
	 */
	@JSON
	public String returnBillClaim() {

		try {

			// 获取理赔信息的运单号
			String waybillNo = billClaimVo.getBillClaimQueryDto()
					.getWaybillNo();

			// 获取理赔退回原因
			String rtnReason = billClaimVo.getBillClaimQueryDto()
					.getRtnReason();

			// 获取当前登录用户信息
			CurrentInfo cInfo = FossUserContext.getCurrentInfo();

			// 退回理赔应付信息
			billClaimService.returnBillClainEntity(waybillNo, cInfo, rtnReason);

		} catch (BusinessException e) {

			LOGGER.error("退回理赔单信息：" + e.getMessage(), e);
			// 返回错误信息
			return returnError(e);
		}

		return returnSuccess();
	}

	/**
	 * 设置查询条件.
	 *
	 * @author foss-qiaolifeng
	 * @date 2012-11-19 下午2:04:11
	 */
	private void setQueryConditionForParam() {

		// 设置执行时使用小于结束日期+1天
		if (billClaimVo.getBillClaimQueryDto().getStartCreateTime() != null
				&& billClaimVo.getBillClaimQueryDto().getEndCreateTime() != null) {

			String dateEndTemp = DateUtils.addDay(billClaimVo
					.getBillClaimQueryDto().getEndCreateTime(), 1);
			Date dateEnd = DateUtils.convert(dateEndTemp);

			billClaimVo.getBillClaimQueryDto().setEndCreateTime(dateEnd);
		}

		// 处理云单号集合
		if (billClaimVo.getBillClaimQueryDto().getWayBillNosArray() != null) {

			// 将数组转化成list
			List<String> wayBillNos = Arrays.asList(billClaimVo
					.getBillClaimQueryDto().getWayBillNosArray());
			billClaimVo.getBillClaimQueryDto().setWayBillNos(wayBillNos);
		}
	}

	/**
	 * Gets the bill claim vo.
	 *
	 * @return the bill claim vo
	 */
	public BillClaimVo getBillClaimVo() {
		return billClaimVo;
	}

	/**
	 * Sets the bill claim vo.
	 *
	 * @param billClaimVo the new bill claim vo
	 */
	public void setBillClaimVo(BillClaimVo billClaimVo) {
		this.billClaimVo = billClaimVo;
	}

	/**
	 * Sets the bill claim service.
	 *
	 * @param billClaimService the new bill claim service
	 */
	public void setBillClaimService(IBillClaimService billClaimService) {
		this.billClaimService = billClaimService;
	}

}

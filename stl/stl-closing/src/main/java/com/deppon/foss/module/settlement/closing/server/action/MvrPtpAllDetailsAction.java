package com.deppon.foss.module.settlement.closing.server.action;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.settlement.closing.api.server.service.IMvrPtpAllDetailsService;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrPtpAllDetailResultDto;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrPtpAllDetailsDto;
import com.deppon.foss.module.settlement.closing.api.shared.vo.MvrPtpAllDetailsVo;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.util.DateUtils;

/**
 * 合伙人日明细action
 * @author 311396
 * @date 2016-3-22 下午3:49:31
 */

public class MvrPtpAllDetailsAction extends AbstractAction {

	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = -5562643414712686441L;

	/** 声明日志对象. */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(MvrPtpAllDetailsAction.class);

	/**
	 * 声明vo
	 */
	private MvrPtpAllDetailsVo vo;
	
	/**
	 * 注入查询合伙人日报表service
	 */
	private IMvrPtpAllDetailsService mvrPtpAllDetailsService;

	/**
	 * 查询报表
	 * @user 311396
	 * @date 2016-3-22 下午3:56:04
	 */
	@JSON
	public String queryMvrPtpAllDetails() {
		try {
			// 校验查询参数
			if (vo == null || vo.getDto() == null) {
				throw new SettlementException("查询参数没有正确注入到后台！");
			}

			MvrPtpAllDetailsDto dto = vo.getDto();

			// 校验dto参数
			if (dto.getReportType() == null) {
				throw new SettlementException("报表类型不能为空！");
			}
			
			
			if (dto.getStartDate() == null) {
				throw new SettlementException("开始日期不能为空！");
			}

			if (dto.getEndDate() == null) {
				throw new SettlementException("结束日期不能为空！");
			}

			// 结束日期加1天
			dto.setEndDate(DateUtils.convert(DateUtils.addDay(dto.getEndDate(),
					1)));
			dto.setStartPeriod(DateUtils.getDay(dto.getStartDate()));
			dto.setEndPeriod(DateUtils.getDay(dto.getEndDate()));

			dto.setUserCode(FossUserContext.getCurrentInfo().getEmpCode()); // 设置当前登录用户

			// 调用service查询合计项
			MvrPtpAllDetailResultDto resultDto = mvrPtpAllDetailsService
					.queryTotalCounts(vo.getDto());
//			 如果合计项，则表明有数据，再去查询明细
			if (resultDto != null && resultDto.getCount() > 0) {
				List<MvrPtpAllDetailResultDto> resultList = mvrPtpAllDetailsService
						.selectByConditions(vo.getDto(), this.start, this.limit);

				// 判空
				if (CollectionUtils.isNotEmpty(resultList)) {
					resultDto.setProductCode("合计");
					// 添加合计项
					resultList.add(resultDto);
				}
				// 设置结果集
				vo.setResultList(resultList);
				this.setTotalCount((long) resultDto.getCount());
			}

			return returnSuccess();

		} catch (BusinessException e) {
			// 记录错误
			LOGGER.error(e.getMessage(), e);
			return returnError(e);
		}
	}


	public void setVo(MvrPtpAllDetailsVo vo) {
		this.vo = vo;
	}
	public MvrPtpAllDetailsVo getVo() {
		return vo;
	}


	public void setMvrPtpAllDetailsService(
			IMvrPtpAllDetailsService mvrPtpAllDetailsService) {
		this.mvrPtpAllDetailsService = mvrPtpAllDetailsService;
	}
	
}

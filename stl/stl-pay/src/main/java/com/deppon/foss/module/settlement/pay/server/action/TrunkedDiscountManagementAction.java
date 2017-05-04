package com.deppon.foss.module.settlement.pay.server.action;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.settlement.pay.api.server.service.ITrunkedDiscountManagementService;
import com.deppon.foss.module.settlement.pay.api.shared.dto.TrunkedDiscountManagementDto;
import com.deppon.foss.module.settlement.pay.api.shared.vo.TrunkedDiscountManagementVo;
import com.deppon.foss.util.DateUtils;

/**
 * 零担事后折折扣管理
 * @ClassName: TrunkedDiscountManagementAction
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author &269052 |zhouyuan008@deppon.com
 * @date 2015-9-22 上午10:43:14
 * 
 */
public class TrunkedDiscountManagementAction extends AbstractAction {
	private static final long serialVersionUID = 1L;

	private static final Logger logger = LoggerFactory
			.getLogger(TrunkedDiscountManagementAction.class);

	/*
	 * 折扣单VO
	 */
	private TrunkedDiscountManagementVo trunkedDiscountManagementVo;
	/*
	 * 折扣单服务
	 */
	private ITrunkedDiscountManagementService trunkedDiscountManagementService;

	/**
	 * 
	 * @Title: queryTrunkedDiscountByCust
	 * @Description: (按客户去查询)
	 * @param @return
	 * @return String 返回类型
	 * @throws
	 * @author： 269052 |zhouyuan008@deppon.com
	 */
	@JSON
	public String queryTrunkedDiscountByCust() {
		try{
			// 获取前台参数
			TrunkedDiscountManagementDto trunkedDiscountManagementDto = trunkedDiscountManagementVo
					.getTrunkedDiscountManagementDto();
			// 判断用户是根据客户去查询还是单号去查询
			if (StringUtils.isNotBlank(trunkedDiscountManagementDto.getWaybillNo())) {
				// 按单号去查询
				queryTrunkedDiscountByNumber();
			} else {
				// 如果结束日期不为空，则需要对其进行+1操作
				if (trunkedDiscountManagementDto.getPeriodEndDate() != null) {
					// 结束日期加1天
					trunkedDiscountManagementDto
							.setPeriodEndDate(DateUtils.convert(DateUtils.addDay(
									trunkedDiscountManagementDto.getPeriodEndDate(),
									1)));
				}
				// 调用查询折扣单接口
				TrunkedDiscountManagementDto dto = trunkedDiscountManagementService
						.queryTrunkedDiscountByCust(trunkedDiscountManagementDto,
								this.getStart(), this.getLimit());

				// 返回查询结果到前台
				trunkedDiscountManagementVo.setTrunkedDiscountManagementDto(dto);
				// 分页查询返回总行数
				this.setTotalCount(dto.getCount());
			}
		}catch(BusinessException e){
			logger.info("queryTrunkedDiscountByCust异常");
			returnError("零担事后折折扣管理异常-按客户查询",e.getMessage());
		}
		return returnSuccess();
		
	}

	/**
	 * 
	 * @Title: queryTrunkedDiscountByNumber
	 * @Description: TODO(按照运单号去查询)
	 * @param @return
	 * @return String 返回类型
	 * @throws
	 * @author： 269052 |zhouyuan008@deppon.com
	 */
	@JSON
	public String queryTrunkedDiscountByNumber() {
		try{
			// 获取前台传递参数
			TrunkedDiscountManagementDto trunkedDiscountManagementDto = trunkedDiscountManagementVo
					.getTrunkedDiscountManagementDto();
			// 调用接口
			TrunkedDiscountManagementDto dto = trunkedDiscountManagementService
					.queryTrunkedDiscountByNumber(trunkedDiscountManagementDto,
							this.getStart(), this.getLimit());
			// 封装返回参数
			trunkedDiscountManagementVo.setTrunkedDiscountManagementDto(dto);
			// 分页查询总行数
			this.setTotalCount(dto.getCount());
		}catch(BusinessException e){
			logger.info("queryTrunkedDiscountByNumber异常");
			returnError("零担事后折折扣管理异常-按单号查询",e.getMessage());
		}
		return returnSuccess();
	}

	// ----getter-setter-----
	public void setTrunkedDiscountManagementService(
			ITrunkedDiscountManagementService trunkedDiscountManagementService) {
		this.trunkedDiscountManagementService = trunkedDiscountManagementService;
	}

	public TrunkedDiscountManagementVo getTrunkedDiscountManagementVo() {
		return trunkedDiscountManagementVo;
	}

	public void setTrunkedDiscountManagementVo(
			TrunkedDiscountManagementVo trunkedDiscountManagementVo) {
		this.trunkedDiscountManagementVo = trunkedDiscountManagementVo;
	}
}

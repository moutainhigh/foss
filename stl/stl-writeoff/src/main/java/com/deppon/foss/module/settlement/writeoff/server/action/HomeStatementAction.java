package com.deppon.foss.module.settlement.writeoff.server.action;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.writeoff.api.server.service.IHomeStatementService;
import com.deppon.foss.module.settlement.writeoff.api.shared.dto.HomeStatementDto;
import com.deppon.foss.module.settlement.writeoff.api.shared.vo.HomeStatementVo;
import com.deppon.foss.util.DateUtils;

/**
 * 家装对账单Action
 * @ClassName: HomeStatementAction
 * @Description: (这里用一句话描述这个类的作用)
 * @author &269052 |zhouyuan008@deppon.com
 * @date 2015-11-24 下午7:40:00
 * 
 */
public class HomeStatementAction extends AbstractAction {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory
			.getLogger(HomeStatementAction.class);
	
	/**
	 * 注入VO
	 */
	private HomeStatementVo homeStatementVo;
		
	/**
	 * 注入家装对账单Service
	 */
	private IHomeStatementService homeStatementService;

	/**
	 * 查询家装应收单应付单
	 * @Title: queryWoodenStatementD
	 * @Description: (查询家装应收单应付单)
	 * @param @return
	 * @return String 返回类型
	 * @throws
	 * @author： 269052 |zhouyuan008@deppon.com
	 */
	public String queryWoodenStatementD() {
		// 获取前台传递的参数
		HomeStatementDto homeStatementDto = homeStatementVo.getHomeStatementDto();
		try {
			// 判断是否按日期查询
			if (SettlementConstants.TAB_QUERY_BY_DATE.equals(homeStatementDto
					.getQueryTabType())) {
				// 设置结束日期加1
				if (homeStatementDto.getPeriodEndDate() != null) {
					homeStatementDto.setPeriodEndDate(DateUtils
							.convert(DateUtils.addDay(
									homeStatementDto.getPeriodEndDate(), 1)));
				}
			}
			// 调用Service返回DTO
			homeStatementDto = homeStatementService.queryHomeStatementD(
					homeStatementDto, this.getStart(), this.getLimit());
			// 设置家装对账单明细
			homeStatementVo.setHomeStatementDto(homeStatementDto);
			// 设置总行数
			this.setTotalCount(homeStatementDto.getCount());
			return returnSuccess();
		} catch (BusinessException e) {
			logger.error(e.getMessage(), e);
			return returnError(e);
		}
	}

	/**
	 * 生成家装对账单
	 * @Title: homeStatementSave
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param @return
	 * @return String 返回类型
	 * @throws
	 * @author： 269052 |zhouyuan008@deppon.com
	 */
	@JSON
	public String homeStatementSave() {
		logger.info("*****************生成家装对账单开始************");
		try{
			//获取前台传递参数
			HomeStatementDto homeStatementDto = homeStatementVo.getHomeStatementDto();
			//判断是否为按日期查询
			if(SettlementConstants.TAB_QUERY_BY_DATE.equals(homeStatementDto.getQueryTabType())){
				// 设置结束日期加1
				if (homeStatementDto.getPeriodEndDate() != null) {
					homeStatementDto.setPeriodEndDate(DateUtils
							.convert(DateUtils.addDay(
									homeStatementDto.getPeriodEndDate(), 1)));
				}
			}
			//调用接口
			homeStatementDto = homeStatementService.homeStatementSave(homeStatementDto);
			//设置VO 
			homeStatementVo.setHomeStatementDto(homeStatementDto);
			return returnSuccess();
		}catch(BusinessException e){
			logger.error(e.getErrorCode());
			return returnError(e.getErrorCode());
		}
	}

	/******* getter/setter **********/
	public HomeStatementVo getHomeStatementVo() {
		return homeStatementVo;
	}

	public void setHomeStatementVo(HomeStatementVo homeStatementVo) {
		this.homeStatementVo = homeStatementVo;
	}

	public void setHomeStatementService(
			IHomeStatementService homeStatementService) {
		this.homeStatementService = homeStatementService;
	}
}

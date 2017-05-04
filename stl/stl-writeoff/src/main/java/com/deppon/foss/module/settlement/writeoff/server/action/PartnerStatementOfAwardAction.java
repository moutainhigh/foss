package com.deppon.foss.module.settlement.writeoff.server.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.writeoff.api.server.service.IPartnerStatementOfAwardService;
import com.deppon.foss.module.settlement.writeoff.api.shared.dto.PartnerStatementOfAwardDto;
import com.deppon.foss.module.settlement.writeoff.api.shared.vo.PartnerStatementOfAwardVo;
import com.deppon.foss.util.DateUtils;

public class PartnerStatementOfAwardAction extends AbstractAction  {

	/**
	 * 序列号
	 */
	
	
	private static final long serialVersionUID = 1L;
	/**
	 * 记录日志 
	 */
	
	
	private static final Logger logger = LoggerFactory.getLogger(PartnerStatementOfAwardAction.class);
	/**
	 * 合伙人奖罚对账单Vo 
	 */
	
	private PartnerStatementOfAwardVo partnerStatementOfAwardVo;
	/**
	 * 合伙人奖罚对账单Service 
	 */

	private IPartnerStatementOfAwardService partnerStatementOfAwardService;
	
	/**
	 * 查询应收应付单
	 * @author 269044
	 * @date 2016-01-27 
	 */
	
	@JSON
	public String queryPartnerStatementOfAwardD(){
		//获取前台参数
		PartnerStatementOfAwardDto partnerStatementOfAwardDto = partnerStatementOfAwardVo.getPartnerStatementOfAwardDto();
		//判断查询页签，按客户查询，否则按应收应付单号来源编号查询
		if(SettlementConstants.TAB_QUERY_BY_DATE.equals(partnerStatementOfAwardDto.getQueryTabType())){
			//如果结束日期不为空，则需要对其进行+1操作
			if(partnerStatementOfAwardDto.getPeriodEndDate()!=null){
				//结束日期加1天
				partnerStatementOfAwardDto.setPeriodEndDate(DateUtils.convert(DateUtils.addDay(partnerStatementOfAwardDto.getPeriodEndDate(), 1)));
			}
		}
		
		//调用查询应收应付单接口
		PartnerStatementOfAwardDto dto = partnerStatementOfAwardService.queryPartnerStatementOfAwardD(partnerStatementOfAwardDto,this.getStart(),this.getLimit());
		//返回查询结果到前台
		partnerStatementOfAwardVo.setPartnerStatementOfAwardDto(dto);
		//分页查询返回总行数
		this.setTotalCount(dto.getCount());
		
		return returnSuccess();
	}
	
	/**
	 * 生成合伙人奖罚对账单
	 * @author 269044
	 * @date 2016-01-29
	 */
	@JSON
	public String partnerStatementOfAwardSave(){
		//获取前台参数
		PartnerStatementOfAwardDto partnerStatementOfAwardDto = partnerStatementOfAwardVo.getPartnerStatementOfAwardDto();
		try {
			//判断查询页签，按客户查询，否则按来源单号查询
			if(SettlementConstants.TAB_QUERY_BY_DATE.equals(partnerStatementOfAwardDto.getQueryTabType())){
				//如果结束日期不为空，则需要对其进行+1操作
				if(partnerStatementOfAwardDto.getPeriodEndDate()!=null){
					//结束日期加1天
					partnerStatementOfAwardDto.setPeriodEndDate(DateUtils.convert(DateUtils.addDay(partnerStatementOfAwardDto.getPeriodEndDate(), 1)));
				}
			}
			//调用对账单保存接口
			PartnerStatementOfAwardDto dto = partnerStatementOfAwardService.partnerStatementOfAwardSave(partnerStatementOfAwardDto);
			//保存成功返回对账单单号
			partnerStatementOfAwardVo.setPartnerStatementOfAwardDto(dto);
			
			return returnSuccess();
			
		} catch (BusinessException e) {
			
			logger.error(e.getMessage(), e);
			
			return returnError(e);
		}
	}
	
	
	
	public PartnerStatementOfAwardVo getPartnerStatementOfAwardVo() {
		return partnerStatementOfAwardVo;
	}
	public void setPartnerStatementOfAwardVo(
			PartnerStatementOfAwardVo partnerStatementOfAwardVo) {
		this.partnerStatementOfAwardVo = partnerStatementOfAwardVo;
	}
	public void setPartnerStatementOfAwardService(
			IPartnerStatementOfAwardService partnerStatementOfAwardService) {
		this.partnerStatementOfAwardService = partnerStatementOfAwardService;
	}
	
	
	
}

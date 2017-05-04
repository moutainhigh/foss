/*
 * 党章，是一个政党为保证全党在政治上，思想上的一致和组织上，行动上的统一所制定的章程。
 * 一个党的党章的主要内容应该包括该党的性质、指导思想、纲领任务、组织结构、组织制度，党员的条件、权利、义务和纪律等项。
 * 通常衡量一个政党是否成熟党章也是关键因素之一。党章是政党的宗旨和行为规范。
 * 中国共产党现行党章于中国共产党第十八次全国代表大会部分修改，于2012年11月14日通过。
 * 除总纲外共十一章五十三条。规定了党的纲领、组织机构、组织制度、党员的条件、党员的义务和权利、党的纪律等项.
 */
package com.deppon.foss.module.settlement.writeoff.server.action;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.writeoff.api.server.service.IPartnerPayStatementMakeService;
import com.deppon.foss.module.settlement.writeoff.api.shared.dto.PartnerPayStatementDto;
import com.deppon.foss.module.settlement.writeoff.api.shared.vo.PartnerPayStatementVo;
import com.deppon.foss.util.DateUtils;

/**
 * 合伙人付款对账单Action请求处理类
 * @author 黄乐为
 * @date 2016-2-16 上午11:48:21
 */
public class PartnerPayStatementMakeAction extends AbstractAction {

	private static final Logger logger = LoggerFactory.getLogger(PartnerPayStatementMakeAction.class);
	/**
	 * 类序列号
	 */
	private static final long serialVersionUID = -2335754753086836061L;

	/**
	 * 制作合伙人付款对账单查询结果（合伙人付款对账单及合伙人付款对账单明细单据）
	 */
	private PartnerPayStatementVo partnerPayStatementVo = new PartnerPayStatementVo();

	/**
	 * 制作合伙人付款对账单服务类接口
	 */
	private IPartnerPayStatementMakeService partnerPayStatementMakeService;


	/**
	 * 制作合伙人付款对账单时根据输入参数返回合伙人付款对账单及合伙人付款对账单明细单据
	 * @author 黄乐为
	 * @date 2016-1-27 下午6:34:06
	 * @return
	 */
	@JSON
	public String queryForPartnerPayStatementMake() {
		//记录日志
		logger.info("查询合伙人付款对账单action开始");
		try {
			// 获取查询DTO
			PartnerPayStatementDto queryDto = partnerPayStatementVo.getPartnerPayStatementDto();
			//判断查询页签，按客户查询，否则按应收应付单号查询
			if(SettlementConstants.TAB_QUERY_BY_DATE.equals(queryDto.getQueryTabType())){
				//初始化账期开始日期
				if(queryDto.getPeriodBeginDate() != null){
					queryDto.setPeriodBeginDate(DateUtils.getStartDatetime(queryDto.getPeriodBeginDate()));
				}
				//如果结束日期不为空，则需要对其进行+1操作
				if(queryDto.getPeriodEndDate()!=null){
					//结束日期加1天
					queryDto.setPeriodEndDate(DateUtils.getStartDatetime(queryDto.getPeriodEndDate(), 1));
				}
			}
			//调用查询应收应付单接口
			PartnerPayStatementDto dto = partnerPayStatementMakeService.queryPartnerPayStatementD(queryDto,this.getStart(),this.getLimit());
			//返回查询结果到前台
			partnerPayStatementVo.setPartnerPayStatementDto(dto);
			//分页查询返回总行数
			this.setTotalCount(dto.getCount());
			return returnSuccess();
		}catch (BusinessException e) {
			//记录日志
			logger.error(e.getMessage(), e);
			//异常返回
			return returnError(e);
		}
	}


	/**
	 * 制作合伙人付款对账单保存操作
	 * @author 黄乐为
	 * @date 2016-2-16 上午11:48:43
	 * @return
	 */
	@JSON
	public String addPartnerPayStatement() {
		//记录日志
		logger.info("制作合伙人付款对账单action开始");
		// 获取前台保存时传入的dto
		PartnerPayStatementDto queryDto = partnerPayStatementVo.getPartnerPayStatementDto();
		try {
			//判断查询页签，按客户查询，否则按应收应付单号查询
			/*if(SettlementConstants.TAB_QUERY_BY_DATE.equals(queryDto.getQueryTabType())){
				//如果结束日期不为空，则需要对其进行+1操作
				if(queryDto.getPeriodEndDate()!=null){
					//结束日期加1天
					queryDto.setPeriodEndDate(DateUtils.convert(DateUtils.addDay(queryDto.getPeriodEndDate(), 1)));
				}
			}*/
			//调用对账单保存接口
			PartnerPayStatementDto dto = partnerPayStatementMakeService.addStatement(queryDto);
			//保存成功返回对账单单号
			partnerPayStatementVo.setPartnerPayStatementDto(dto);
			//记录日志
			logger.info("制作合伙人付款对账单action结束");
			//正常返回
			return returnSuccess();
		}catch (BusinessException e) {
			//记录日志
			logger.error(e.getMessage(), e);
			//异常返回
			return returnError(e);
		}
	}

	public PartnerPayStatementVo getPartnerPayStatementVo() {
		return partnerPayStatementVo;
	}

	public void setPartnerPayStatementVo(
			PartnerPayStatementVo partnerPayStatementVo) {
		this.partnerPayStatementVo = partnerPayStatementVo;
	}

	public void setPartnerPayStatementMakeService(
			IPartnerPayStatementMakeService partnerPayStatementMakeService) {
		this.partnerPayStatementMakeService = partnerPayStatementMakeService;
	}

}

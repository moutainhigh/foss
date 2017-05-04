package com.deppon.foss.module.settlement.closing.server.action;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.closing.api.server.service.IMvrPartnerStockTfrService;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrPtpStDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;

/** 
 * 合伙人股份中转月报表
 * @author foss结算-306579-guoxinru 
 * @date 2016-3-18 下午1:44:10    
 */
public class MvrPartnerStockTfrAction extends AbstractAction{
	/**
	 * 序列号
	 */
	private static final Logger logger = LogManager.getLogger(MvrPartnerStockTfrAction.class);
	
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 注入合伙人股份中转月报表dto
	 */
	private MvrPtpStDto mvrPtpStDto;
	
	/**
	 * 注入合伙人股份中转月报表service
	 */
	private IMvrPartnerStockTfrService mvrPartnerStockTfrService;
	
	@JSON
	public String mvrPstQuery(){
		//参数非空判断
		if(null==mvrPtpStDto){
			return returnError("参数不能为空！");
		}
		//获取当前登录的用户信息
		CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
		// 设置用户数据查询权限
		mvrPtpStDto.setUserCode(currentInfo.getEmpCode());
		Long  total = 0L;
		//调用合伙人股份中转月报表service查询
		try {
			total = this.mvrPartnerStockTfrService.queryMvrPtpStCount(mvrPtpStDto);
			if(total>0){
				mvrPtpStDto = this.mvrPartnerStockTfrService.queryMvrPtpStEntityList(mvrPtpStDto,this.getStart(),this.getLimit());
			}
			this.setTotalCount(total);
		} catch (SettlementException e){
			logger.error(e.getMessage());
			return  returnError(e.getMessage());
		}
		return returnSuccess();
	}
	
	
	
	
	

	public MvrPtpStDto getMvrPtpStDto() {
		return mvrPtpStDto;
	}

	public void setMvrPtpStDto(MvrPtpStDto mvrPtpStDto) {
		this.mvrPtpStDto = mvrPtpStDto;
	}

	public void setMvrPartnerStockTfrService(
			IMvrPartnerStockTfrService mvrPartnerStockTfrService) {
		this.mvrPartnerStockTfrService = mvrPartnerStockTfrService;
	}
	
	
	
	
	
	
	
	
	
}

package com.deppon.foss.module.pickup.predeliver.server.action;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IQueryPredeliverService;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.SendHandoverInfoDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.SendInfoSearchDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.SendingWayBillInfoResponse;
import com.deppon.foss.module.pickup.predeliver.api.shared.exception.QueryGoodsException;
import com.deppon.foss.module.pickup.predeliver.api.shared.vo.SendInfoQueryVo;

public class QueryPredeliverAction extends AbstractAction{
	/** 序列化. */
	private static final long serialVersionUID = 1118011519934568369L;
	/** 派送信息查询. */
	private SendInfoQueryVo vo;
	/** 查询派送信息Service. */
	private IQueryPredeliverService queryPredeliverService;
	
	@JSON
	public String queryPredeliver(){
		try {
			//交接单查询
			if(StringUtils.equals(vo.getSendInfoSearchDto().getBillType(), "HANDOVER_NO")) {
				//查询出总条数，用于分页
				Long totalCount = queryPredeliverService.getPredeliverInfoCount(vo.getSendInfoSearchDto());
				if(totalCount != null && totalCount.intValue() > 0) {
					List<SendHandoverInfoDto> list = queryPredeliverService.queryPredeliver(vo.getSendInfoSearchDto(), this.getStart(), this.getLimit());
					vo.setSendHandoverInfoDtoList(list);
					SendInfoSearchDto sendInfoSearchDto = queryPredeliverService.queryPredeliverTotal(vo.getSendInfoSearchDto(), list);
					vo.setSendInfoSearchDto(sendInfoSearchDto);
				}else {
					vo.setSendHandoverInfoDtoList(null);
				}
				//设置总条数
				this.setTotalCount(totalCount);
			}
			else if (StringUtils.equals(vo.getSendInfoSearchDto().getBillType(), "EXPRESS_WAY_BILL")) {
				List<SendingWayBillInfoResponse> list = queryPredeliverService.queryPredeliverExpressWaybill(vo.getSendInfoSearchDto());
				vo.setBillInfoResponses(list);
				if (null!=list && list.size()>0) {
					SendInfoSearchDto sendInfoSearchDto = queryPredeliverService.queryPredeliverWaybillExpressTotal(vo.getSendInfoSearchDto(),list);
					vo.setSendInfoSearchDto(sendInfoSearchDto);
					this.setTotalCount(Long.valueOf(list.size()));
				}else{
					//设置总条数
					this.setTotalCount(0L);
				}
			}
			//运单查询
			else {
				//查询出总条数，用于分页
				Long totalCount = queryPredeliverService.getPredeliverInfoCountWaybill(vo.getSendInfoSearchDto());
				if(totalCount != null && totalCount.intValue() > 0) {
					List<SendHandoverInfoDto> list = queryPredeliverService.queryPredeliverWaybill(vo.getSendInfoSearchDto(), this.getStart(), this.getLimit());
					vo.setSendHandoverInfoDtoList(list);
					SendInfoSearchDto sendInfoSearchDto = queryPredeliverService.queryPredeliverWaybillTotal(vo.getSendInfoSearchDto(),list);
					vo.setSendInfoSearchDto(sendInfoSearchDto);
				}else {
					vo.setSendHandoverInfoDtoList(null);
				}
				//设置总条数
				this.setTotalCount(totalCount);
			}
		}
		//捕获异常
		catch (QueryGoodsException e) {
			//返回异常信息
			returnError(e);
		}catch (Exception e) {
			//返回异常信息
			returnError(e.getMessage());
		}
		//成功
		return returnSuccess();
	}
	public SendInfoQueryVo getVo() {
		return vo;
	}

	public void setVo(SendInfoQueryVo vo) {
		this.vo = vo;
	}

	public void setQueryPredeliverService(
			IQueryPredeliverService queryPredeliverService) {
		this.queryPredeliverService = queryPredeliverService;
	}
}

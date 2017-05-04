package com.deppon.foss.module.pickup.sign.server.action;

import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.pickup.sign.api.server.service.IDopSignService;
import com.deppon.foss.module.pickup.sign.api.shared.domain.DopSignEntity;
import com.deppon.foss.module.pickup.sign.api.shared.exception.SignException;
import com.deppon.foss.module.pickup.sign.api.shared.vo.SignVo;
import com.deppon.foss.module.pickup.waybill.api.server.service.IActualFreightService;

public class DopSignAction extends AbstractAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 *  运单状态服务接口
	 */
	private IActualFreightService actualFreightService;
	/*
	 * dop签收service
	 */
	private IDopSignService dopSignService;
	/**
	 * 注入 dop签收service
	 * @param dopSignService
	 */
	public void  setDopSignService(IDopSignService dopSignService){
		this.dopSignService=dopSignService;
	}
	
	/**
	 * 签收出库vo
	 */
	private SignVo signVo = new SignVo();
	
	public void setSignVo(SignVo signVo) {
		this.signVo = signVo;
	}

	public SignVo getSignVo() {
		return signVo;
	}
	/**
	 * dop签收实体
	 */
	private DopSignEntity dopSignEntity;
	/**
	 * 家装--查询DOP传过来的数据信息
	 * @author foss-zhuliangzhi
	 * @date 2015-10-18  下午7:01:12
	 * @return
	 */
	@JSON
	public String queryDopCacheInfo(){
		try {
			signVo.setDopEntity(dopSignService.queryDopCacheByParams(signVo.getSignDto()));
			signVo.setRfcFlag(dopSignService.queryDopSignRfc(signVo.getSignDto()));//设置反签收标志
		} catch (SignException e) {
			// 返回错误信息
			return returnError(e);
		}
		return returnSuccess();
	}
	public void setActualFreightService(IActualFreightService actualFreightService) {
		this.actualFreightService = actualFreightService;
	}

	public DopSignEntity getDopSignEntity() {
		return dopSignEntity;
	}

	public void setDopSignEntity(DopSignEntity dopSignEntity) {
		this.dopSignEntity = dopSignEntity;
	}

	

}

/**
 * Project Name:tfr-platform
 * File Name:TransferCenterLayoutAction.java
 * Package Name:com.deppon.foss.module.transfer.platform.server.action
 * Date:2014年12月30日上午11:25:42
 * Copyright (c) 2014, shiwei@outlook.com All Rights Reserved.
 *
*/

package com.deppon.foss.module.transfer.platform.server.action;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.module.transfer.platform.api.server.service.ITransferCenterLayoutService;
import com.deppon.foss.module.transfer.platform.api.shared.dto.TransferCenterUnitDto;
import com.deppon.foss.module.transfer.platform.api.shared.vo.TransferCenterLayoutVo;
import com.deppon.foss.util.define.FossConstants;

/**
 * ClassName:TransferCenterLayoutAction <br/>
 * Reason:	 场内布局图action. <br/>
 * Date:     2014年12月30日 上午11:25:42 <br/>
 * @author   045923
 * @version  
 * @since    JDK 1.6
 * @see 	 
 */
public class TransferCenterLayoutAction extends AbstractAction {

	/**
	 * serialVersionUID
	 * @since JDK 1.6
	 */
	private static final long serialVersionUID = 4734098298557500585L;
	
	/**
	 * service
	 */
	private ITransferCenterLayoutService transferCenterLayoutService;
	
	/**
	 * vo
	 */
	private TransferCenterLayoutVo transferCenterLayoutVo = new TransferCenterLayoutVo();

	public TransferCenterLayoutVo getTransferCenterLayoutVo() {
		return transferCenterLayoutVo;
	}

	public void setTransferCenterLayoutVo(
			TransferCenterLayoutVo transferCenterLayoutVo) {
		this.transferCenterLayoutVo = transferCenterLayoutVo;
	}

	public void setTransferCenterLayoutService(
			ITransferCenterLayoutService transferCenterLayoutService) {
		this.transferCenterLayoutService = transferCenterLayoutService;
	}
	
	/**
	 * 
	 * chooseDisplayPage:根据当前登录部门跳转页面，
	 * 转运场直接跳转图形显示页面，其他部门跳转查询页面。. <br/>
	 * Date:2014年12月30日上午11:33:14
	 * @author 045923
	 * @return
	 * @since JDK 1.6
	 */
	public String chooseDisplayPage(){
		String beTransferCenter = transferCenterLayoutService.beTransferCenter();
		if(StringUtils.equals(beTransferCenter, FossConstants.NO)){
			return FossConstants.NO;
		}
		transferCenterLayoutVo.setOrgCode(beTransferCenter);
		return FossConstants.YES;
		
	}
	
	/**
	 * displayShowPage:显示图形界面. <br/>
	 * Date:2014年12月30日下午8:06:12
	 * @author 045923
	 * @return
	 * @since JDK 1.6
	 */
	public String displayShowPage(){
		String beTransferCenter = transferCenterLayoutService.beTransferCenter();
		if(!StringUtils.equals(beTransferCenter, FossConstants.NO)){
			transferCenterLayoutVo.setOrgCode(beTransferCenter);
		}
		return SUCCESS;
	}
	
	/**
	 * queryUnits:根据转运场编号获取转运场库区、月台、待叉区. <br/>
	 * Date:2014年12月30日下午7:31:49
	 * @author 045923
	 * @return
	 * @since JDK 1.6
	 */
	public String queryTransferCenterUnits(){
		String orgCode = transferCenterLayoutVo.getOrgCode();
		List<TransferCenterUnitDto> gaList = transferCenterLayoutService.queryGoodsArea(orgCode);
		List<TransferCenterUnitDto> pfList = transferCenterLayoutService.queryPlatform(orgCode);
		List<TransferCenterUnitDto> taList = transferCenterLayoutService.queryTransferArea(orgCode);
		ArrayList<TransferCenterUnitDto> unitsList = new ArrayList<TransferCenterUnitDto>();
		if(!CollectionUtils.isEmpty(gaList)){
			unitsList.addAll(gaList);
		}
		if(!CollectionUtils.isEmpty(pfList)){
			unitsList.addAll(pfList);
		}
		if(!CollectionUtils.isEmpty(taList)){
			unitsList.addAll(taList);
		}
		transferCenterLayoutVo.setUnitsList(unitsList);
		return SUCCESS;
	}

}


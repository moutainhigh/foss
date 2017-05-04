/*******************************************************************************
 * Copyright 2013 BSE TEAM
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * PROJECT NAME	: bse-querying
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/querying/server/action/SalesstatementAction.java
 * 
 * FILE NAME        	: SalesstatementAction.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: bse-querying
 * PACKAGE NAME: com.deppon.foss.module.base.querying.server.action
 * FILE    NAME: IntegrativeQueryAction.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.base.baseinfo.server.action;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.shared.define.ElectronicTicketParamsConstants;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ElectronicTicketEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.ElectronicTicketProcVo;
import com.deppon.foss.module.pickup.pda.api.server.service.IPdaImageService;
import com.deppon.foss.module.pickup.waybill.shared.util.PropertiesUtil;
/**
 * 刷卡电子小票图片地址
 * @author 
 * @date
 * @since
 * @version
 */
public class ElectronicTicketAction extends AbstractAction {

    private static final long serialVersionUID = 1L;

    /**
     * 日志.
     */
//    private static final Logger LOGGER = LoggerFactory
//	    .getLogger(ElectronicTicketAction.class);
    public IPdaImageService pdaImageSerice;
    private String electricPictureUrl;
	
	
	public String getElectricPictureUrl() {
		return electricPictureUrl;
	}
	public void setElectricPictureUrl(String electricPictureUrl) {
		this.electricPictureUrl = electricPictureUrl;
	}
	public void setPdaImageSerice(IPdaImageService pdaImageSerice) {
		this.pdaImageSerice = pdaImageSerice;
	}
	private ElectronicTicketProcVo electronicTicketProcVo;
	public ElectronicTicketProcVo getElectronicTicketProcVo() {
		return electronicTicketProcVo;
	}
	public void setElectronicTicketProcVo(
			ElectronicTicketProcVo electronicTicketProcVo) {
		this.electronicTicketProcVo = electronicTicketProcVo;
	}
	
	@Override
	public String execute(){
		electricPictureUrl= PropertiesUtil.getKeyValue(ElectronicTicketParamsConstants.PICTURE_ELECTRONICTICKET_ADDRESS);
		return  returnSuccess();
	}
	/**
     *根据运单号查询
     * 
     * @author 
     * @date 
     */
    @JSON
	public String queryElectronicTicketListByWayBill() {
		ElectronicTicketEntity entityView = electronicTicketProcVo.getElectronicTicketDetail();
		String strwayBillNos = entityView.getWayBillNo();
		if(StringUtils.isNotBlank(strwayBillNos)){
			List<String> wayBillNos = getParms(strwayBillNos);
			List<ElectronicTicketEntity> electronicTicketEntitys= pdaImageSerice.queryElectronicTicketListByWaybillNumber(wayBillNos, start, limit);
			// 返回的结果显示在表格中：
			electronicTicketProcVo.setElectronicTicketEntitys(electronicTicketEntitys);
			totalCount = pdaImageSerice.queryCountByWaybn(wayBillNos);
			getTotalAmount(electronicTicketEntitys,electronicTicketProcVo);
			electronicTicketProcVo.setTotalCount(electronicTicketEntitys.size());
		}
		return returnSuccess();
		}
    /**
     *根据交易流水号查询
     * 
     * @author 
     * @date 
     */
    @JSON
 	public String queryElectronicTicketListBySerial() {
 		ElectronicTicketEntity entityView = electronicTicketProcVo.getElectronicTicketDetail();
 		String stserialNos = entityView.getSerialNo();
 		if(StringUtils.isNotBlank(stserialNos)){
 			List<String> serialNos = getParms(stserialNos);
 			List<ElectronicTicketEntity> electronicTicketEntitys= pdaImageSerice.queryElectronicTicketListBySerialNumber(serialNos, start, limit);
			// 返回的结果显示在表格中：
			electronicTicketProcVo.setElectronicTicketEntitys(electronicTicketEntitys);
			totalCount = pdaImageSerice.queryCountBySerialN(serialNos);
			getTotalAmount(electronicTicketEntitys,electronicTicketProcVo);
			electronicTicketProcVo.setTotalCount(electronicTicketEntitys.size());
 		}
 		return returnSuccess();
 		}
    
    /**
	 * 计算总金额
	 * @author
	 * @date 
	 */
	private void getTotalAmount(List<ElectronicTicketEntity> electronicList,ElectronicTicketProcVo electronicticketvo){
		// 循环计算核销总金额，并设置到返回对象中
		BigDecimal totalAmount = BigDecimal.ZERO;
		if (CollectionUtils.isNotEmpty(electronicList)) {
			for (ElectronicTicketEntity entity : electronicList) {
				totalAmount = totalAmount.add(entity.getCardMoney());
			}
			// 总金额
			electronicticketvo.setTotalAmount(totalAmount);
	}
	
	}
	
	/**
	 * 获取查询参数集
	 */
	private List<String> getParms(String str){
		String[] arr = str.split(",");
			List<String> parmsStr = new ArrayList<String>();
			for(int i = 0;i<arr.length;i++){
				parmsStr.add(arr[i].trim());
			}
		return parmsStr;
	}
}

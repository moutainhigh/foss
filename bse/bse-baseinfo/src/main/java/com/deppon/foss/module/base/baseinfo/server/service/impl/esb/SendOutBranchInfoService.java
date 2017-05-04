package com.deppon.foss.module.base.baseinfo.server.service.impl.esb;

import org.jgroups.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.deppon.esb.api.domain.AccessHeader;
import com.deppon.esb.core.exception.ESBException;
import com.deppon.esb.pojo.transformer.jaxb.OutBranchInfoRequestTrans;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.inteface.domain.usermanagements.OutBranchInfo;
import com.deppon.foss.inteface.domain.usermanagements.OutBranchInfoRequest;
import com.deppon.foss.module.base.baseinfo.api.server.service.esb.ISendOutBranchInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.esb.ISyncESBSendService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.SynchronousExternalSystemException;

public class SendOutBranchInfoService implements ISendOutBranchInfoService{
	//日志
	private static final Logger log = LoggerFactory.getLogger(SendOutBranchInfoService.class);
	
	//服务编码
	private static final String ESB_SERVICE_CODE_WDGH ="ESB_FOSS2ESB_OUTSIDE_SHOP";

	private ISyncESBSendService syncESBSendService;
	
	public void setSyncESBSendService(ISyncESBSendService syncESBSendService) {
			this.syncESBSendService = syncESBSendService;
		}
	/**
	 * 
	 *<p>同步代理网点信息方法</p>
	 *@author 269231 -qirongsheng
	 *@date 2016-3-29下午04:15:07
	 *@see
	 *@param dtos
	 *@param OperateType  操作类型
	 */
	@Override
	public void syncOutBranchInfoToWDGH(
			OuterBranchEntity dtos, String operateType) {

		//同步信息非空校验
		if(null != dtos){
			OutBranchInfoRequest request = new OutBranchInfoRequest();
			//服务编码
			AccessHeader accessHeader = new AccessHeader();
			accessHeader.setEsbServiceCode(ESB_SERVICE_CODE_WDGH);
			accessHeader.setBusinessId(UUID.randomUUID().toString());
			accessHeader.setBusinessDesc1("同步代理网点信息");
			accessHeader.setVersion("0.1");
			try {
				//转换对象
				request = transtoEsbObjects(dtos,operateType);
				log.info("开始调用 同步代理网点信息TO WDGH：\n"+new OutBranchInfoRequestTrans().fromMessage(request));
				syncESBSendService.createThreadToSendESB(syncESBSendService, accessHeader, request);
				log.info("结束调用 同步代理网点信息TO WDGH：\n"+new OutBranchInfoRequestTrans().fromMessage(request));
			} catch (ESBException e) {
				log.error(e.getMessage(), e);
				throw new SynchronousExternalSystemException(" 同步代理网点信息给网点规划"," 同步代理网点信息 发送数据到ESB错误");
			}
		}
		
	}
		

	/**
	 *<p>奖代理网点信息对象转换成esb请求对象</p>
	 *<p>For WDGH</p>
	 *@author 269231 -qirongsheng
	 *@date 2016-3-29下午04:17:41
	 *@param dtos
	 *@param OperateType  操作类型
	 *@return
	 */
	private OutBranchInfoRequest transtoEsbObjects(
			OuterBranchEntity dtos, String operateType) throws BusinessException{
		
		OutBranchInfoRequest request = new OutBranchInfoRequest();
		OutBranchInfo reqInfo =new OutBranchInfo();
		
		//非空校验
		if(null != dtos){
			reqInfo.setActive(dtos.getActive());
			reqInfo.setAddress(dtos.getAddress());
			reqInfo.setAgentCollectedUpperLimit(null);
			reqInfo.setAgentCompany(dtos.getAgentCompany());
			reqInfo.setAgentCompanySimpleCode(null);
			reqInfo.setAgentDeptCode(dtos.getAgentDeptCode());
			reqInfo.setAgentDeptName(dtos.getAgentDeptName());
			reqInfo.setAirWaybillName(dtos.getAirWaybillName());
			reqInfo.setAirWaybillTel(dtos.getAirWaybillTel());
			reqInfo.setArrive(dtos.getArrive());
			reqInfo.setArriveCharge(dtos.getArriveCharge());
			reqInfo.setBranchtype(dtos.getBranchtype());
			reqInfo.setCityCode(dtos.getCityCode());
			reqInfo.setContact(dtos.getContact());
			reqInfo.setContactPhone(dtos.getContactPhone());
			reqInfo.setCountryRegion(dtos.getCountryRegion());
			reqInfo.setCountryRegionName(dtos.getCountryRegionName());
			reqInfo.setCountyCode(dtos.getCountyCode());
			reqInfo.setCreateDate(dtos.getCreateDate());
			reqInfo.setCreateUser(dtos.getCreateUser());
			reqInfo.setDeliverArea(dtos.getDeliverArea());
			reqInfo.setDeliveryCoordinate(dtos.getDeliveryCoordinate());
			reqInfo.setDeptCoordinate(dtos.getDeptCoordinate());
			reqInfo.setHelpCharge(dtos.getHelpCharge());
			reqInfo.setId(dtos.getId());
			reqInfo.setInsured(null);
			reqInfo.setIsAirport(dtos.getIsAirport());
			reqInfo.setLeave(dtos.getLeave());
			reqInfo.setManagement(dtos.getManagement());
			reqInfo.setMobilePhone(dtos.getMobilePhone());
			reqInfo.setModifyDate(dtos.getModifyDate());
			reqInfo.setModifyUser(dtos.getModifyUser());
			reqInfo.setNotes(dtos.getNotes());
			reqInfo.setPickupArea(dtos.getPickupArea());
			reqInfo.setPickupSelf(dtos.getPickupSelf());
			reqInfo.setPickupToDoor(dtos.getPickupToDoor());
			reqInfo.setPinyin(dtos.getPinyin());
			reqInfo.setProvCode(dtos.getProvCode());
			reqInfo.setProvName(dtos.getProvName());
			reqInfo.setReturnBill(dtos.getReturnBill());
			reqInfo.setSimplename(dtos.getSimplename());
			reqInfo.setStationNumber(dtos.getStationNumber());
			reqInfo.setTransfer(dtos.getTransfer());
			if(null != dtos.getVersionNo())
				reqInfo.setVersionNo(dtos.getVersionNo());
			reqInfo.setVirtualCode(dtos.getVirtualCode());
		}
		
		//将数据添加到request对象中 				
		request.setOutBranchInfo(reqInfo);
		request.setOperate(operateType);
		return request;
	}
	
}

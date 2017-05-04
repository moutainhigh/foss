package com.deppon.foss.module.base.uumsitf.server.impl;

import java.util.Date;

import javax.xml.ws.Holder;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.esb.header.ESBHeader;
import com.deppon.esb.inteface.domain.finance.QueryBadDebtsArInfoRequest;
import com.deppon.esb.inteface.domain.finance.QueryBadDebtsArInfoResponse;
import com.deppon.esb.inteface.domain.finance.QueryDebtMoneyRequest;
import com.deppon.esb.inteface.domain.finance.QueryDebtMoneyResponse;
import com.deppon.esb.inteface.domain.finance.QueryExpressDeptRequest;
import com.deppon.esb.inteface.domain.finance.QueryExpressDeptResponse;
import com.deppon.esb.inteface.domain.finance.QueryInvoiceAmountRequestType;
import com.deppon.esb.inteface.domain.finance.QueryInvoiceAmountResponseType;
import com.deppon.esb.inteface.domain.finance.QueryReceiptMoneyRequest;
import com.deppon.esb.inteface.domain.finance.QueryReceiptMoneyResponse;
import com.deppon.esb.inteface.domain.finance.QueryStatementRequest;
import com.deppon.esb.inteface.domain.finance.QueryStatementResponse;
import com.deppon.foss.inteface.finmanager.CommonException;
import com.deppon.foss.inteface.finmanager.FossFinmanagerService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IExpressPartSalesDeptService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.ExpressPartSalesDeptResultDto;
import com.deppon.foss.module.base.baseinfo.server.service.impl.SyncLineInfosToGpsService;
import com.deppon.foss.util.UUIDUtils;

/**
 * 根据传营业部标杆编码返回其对应的快递点部的名称，编码，标杆编码
 * 
 * @author WangPeng
 * @Date   2013-8-8 下午2:56:48
 * @param  arg0
 * @param  arg1
 * @return
 * @throws CommonException
 *
 */
public class SyncExpAndDeptInfoToFinsService implements FossFinmanagerService {

	 /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(SyncLineInfosToGpsService.class);

    //注入营业部和快递点部关系的Service
    IExpressPartSalesDeptService expressPartSalesDeptService;
    
	public IExpressPartSalesDeptService getExpressPartSalesDeptService() {
		return expressPartSalesDeptService;
	}

	public void setExpressPartSalesDeptService(
			IExpressPartSalesDeptService expressPartSalesDeptService) {
		this.expressPartSalesDeptService = expressPartSalesDeptService;
	}
	
	//注入组织Service
	IOrgAdministrativeInfoService orgAdministrativeInfoService;
	
	
	public IOrgAdministrativeInfoService getOrgAdministrativeInfoService() {
		return orgAdministrativeInfoService;
	}

	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

	/**
	 * 根据传营业部标杆编码返回其对应的快递点部的名称，编码，标杆编码
	 * 
	 * @author WangPeng
	 * @Date   2013-8-8 下午2:56:48
	 * @param  arg0
	 * @param  arg1
	 * @return
	 * @throws CommonException
	 *
	 */
	@Override
	public QueryExpressDeptResponse queryExpressDept(Holder<ESBHeader> arg0,
			QueryExpressDeptRequest arg1) throws CommonException {
		
		LOGGER.info("manage start.............");
		//定义一个组织对象
		OrgAdministrativeInfoEntity orgEntity = null;
		//定义一个营业部和快递点部关系的对象
		ExpressPartSalesDeptResultDto  expAndDeptDto = null;
		//获取财务自助传递过来的营业部标杆编码
		String salesUnifyCode = arg1.getDeptStandartNumber().trim();
		if(StringUtils.isNotEmpty(salesUnifyCode)){
			orgEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByUnifiedCodeNoCache(salesUnifyCode);
			if(null != orgEntity){
				String deptCode = orgEntity.getCode();
				if(StringUtils.isNotEmpty(deptCode)){
					expAndDeptDto = expressPartSalesDeptService.queryExpressPartSalesDeptBySalesCodeAndTime(deptCode,new Date());
				}
			}
		}
		//定义一个响应类对象
		QueryExpressDeptResponse  response = new QueryExpressDeptResponse();
		
		//如果得到的快递点部的dto不为空，就说明得到查询结果了，可以返回结果
		if(null != expAndDeptDto){
			String expUnifyCode = expAndDeptDto.getUnifiedCode().trim();
			if(StringUtils.isNotEmpty(expUnifyCode)){
				response.setExDeptNumber(expAndDeptDto.getPartCode());
				response.setExDeptStandartName(expAndDeptDto.getPartName());
				response.setExDeptStandartNumber(expUnifyCode);
			}else{
				response.setExDeptNumber(null);
				response.setExDeptStandartName(null);
				response.setExDeptStandartNumber(null);
			}
		}else{
			response.setExDeptNumber(null);
			response.setExDeptStandartName(null);
			response.setExDeptStandartNumber(null);
		}
			
		arg0.value.setResponseId(UUIDUtils.getUUID());
		if(StringUtils.isNotEmpty(response.getExDeptStandartNumber())){
			
			arg0.value.setResultCode(1);
		}else{
			
			arg0.value.setResultCode(0);
		}
		LOGGER.info(response.toString());
		LOGGER.info("manage end.............");
		return response;
	}
	
	
	@Override
	public QueryInvoiceAmountResponseType queryInvoiceAmount(
			QueryInvoiceAmountRequestType arg0, Holder<ESBHeader> arg1)
			throws CommonException {
		return null;
	}

	@Override
	public QueryReceiptMoneyResponse queryReceiptMoney(Holder<ESBHeader> arg0,
			QueryReceiptMoneyRequest arg1) throws CommonException {
		return null;
	}

	@Override
	public QueryBadDebtsArInfoResponse queryBadDebtsArInfo(
			Holder<ESBHeader> arg0, QueryBadDebtsArInfoRequest arg1)
			throws CommonException {
		return null;
	}

	@Override
	public QueryDebtMoneyResponse queryDebtMoney(Holder<ESBHeader> arg0,
			QueryDebtMoneyRequest arg1) throws CommonException {
		return null;
	}

	@Override
	public QueryStatementResponse queryStatementInfo(
			Holder<ESBHeader> esbHeader, QueryStatementRequest parameters)
			throws CommonException {
		return null;
	}

}

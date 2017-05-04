package com.deppon.pda.bdm.module.core.server.service.impl.adapter;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.deppon.foss.base.util.define.BizTypeConstants;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.AdaterConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.domain.adapter.AdapterDeptVo;
import com.deppon.pda.bdm.module.core.shared.domain.adapter.ResponseDeptEntity;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.util.Argument;

/**
 * 该接口默认灰度使用
 * 
 * OrgAdministrativeInfoEntity 返回
 * orgCode 传入部门编码。
 * 
 * bizTypes请看BizTypeConstants类，目前包括：    
    ORG_TRANS_DEPARTMENT="TRANS_DEPARTMENT";
    ORG_TRANSFER_CENTER="TRANSFER_CENTER";
    ORG_AIR_DISPATCH="AIR_DISPATCH";
    ORG_DIVISION="DIVISION";
    ORG_BIG_REGION="BIG_REGION";
    ORG_SMALL_REGION="SMALL_REGION";
    ORG_IS_DELIVER_SCHEDULE="IS_DELIVER_SCHEDULE";
 * @version 1.0
 * @author 314587-ECS-LiuLiPeng
 * @update 2016年8月10日 下午12:08:15
 */
public class AdapterDeptService implements IBusinessService<ResponseDeptEntity, AdapterDeptVo>{
	private static final Logger logger = Logger.getLogger(AdapterDeptService.class);
	
	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;
	
	@Override
	public AdapterDeptVo parseBody(AsyncMsg asyncMsg) throws PdaBusiException {
		AdapterDeptVo resultBody = JSON.parseObject(asyncMsg.getContent(), AdapterDeptVo.class);
		return resultBody;
	}

	@Override
	public ResponseDeptEntity service(AsyncMsg asyncMsg, AdapterDeptVo param) throws PdaBusiException {
		logger.info("***************************查询部门归属开始***************************");
		
		Argument.hasText(param.getOrgCode(), "查询部门归属异常：param.getOrgCode()");
		/***************传入参数 List 默认属性赋值****************/
		List<String> list = new ArrayList<String>();
		//外场
		String wc = BizTypeConstants.ORG_TRANSFER_CENTER;
		//营业部
		String yyb = BizTypeConstants.ORG_SALES_DEPARTMENT;
		list.add(wc);
		list.add(yyb);
		param.setBizTypes(list);
		/***************传入参数 List 默认属性赋值****************/
		
		//返回结果
		ResponseDeptEntity result = new ResponseDeptEntity();
		
		OrgAdministrativeInfoEntity queryOrg = orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoIncludeSelfByCode(param.getOrgCode(), param.getBizTypes());
		if(null != queryOrg){
			//是否营业部
			if(queryOrg.getSalesDepartment().equals("Y"))
				result.setSalesDepartment(true);
			else
				 result.setSalesDepartment(false);
			//是否外场
			if(queryOrg.getTransferCenter().equals("Y"))
				result.setTransferCenter(true);
			else
				result.setTransferCenter(false);
		}
		logger.info("***************************查询部门归属结束***************************");
		return result;
	}

	@Override
	public String getOperType() {
		return AdaterConstant.ADAPTER_DEPT.VERSION;
	}

	@Override
	public boolean isAsync() {
		return false;
	}

	public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}
	
	
}

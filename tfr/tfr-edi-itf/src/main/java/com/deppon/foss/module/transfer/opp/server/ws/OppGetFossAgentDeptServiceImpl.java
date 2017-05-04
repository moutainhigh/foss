package com.deppon.foss.module.transfer.opp.server.ws;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.deppon.foss.module.base.baseinfo.api.server.service.IAirAgencyDeptService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IAirlinesAgentService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AirlinesAgentEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AirlinesAgentEntityTransEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchEntity;
import com.deppon.foss.module.transfer.common.api.server.util.JsonUtil;
import com.deppon.foss.module.transfer.edi.server.domain.FossAgentDeptResponse;
import com.deppon.foss.module.transfer.opp.server.inter.OppGetFossAgentDeptService;

public class OppGetFossAgentDeptServiceImpl implements
		OppGetFossAgentDeptService {
	private static final Logger logger = LoggerFactory.getLogger(OppGetFossAgentDeptServiceImpl.class);
	/**
	 * 空运代理网点接口 
	 */
	@Autowired
	private IAirAgencyDeptService airAgencyDeptService;
	/**
	 * 用来操作交互“航空公司代理人”的数据库对应数据访问Dao调用的Service接口 
	 */
	@Autowired
	private IAirlinesAgentService  airlinesAgentService;
	@Override
	public @ResponseBody String getFossAgentDept(@RequestBody String param,HttpServletResponse resp) throws Exception{
		logger.info("==================start=====================");
		logger.info("进入"+this.getClass().getName()+"类的方法");
		FossAgentDeptResponse agentDeptResponse=new FossAgentDeptResponse();
		try {
			//		IAirAgencyCompanyService.queryEntityByName
			OuterBranchEntity outerBranchEntity=new OuterBranchEntity();
			try {
				outerBranchEntity.setAgentDeptCode(param);
				List<OuterBranchEntity>  branchEntities=airAgencyDeptService.queryAgencyBranchByAgentDeptName(outerBranchEntity, 2, 0);//获取代理网点
				if (null!=branchEntities && 1==branchEntities.size()) {
					outerBranchEntity=branchEntities.get(0);
					outerBranchEntity.setDeliverArea("");
					outerBranchEntity.setPickupArea("");
				}else{
					outerBranchEntity=null;
				}
			} catch (Exception e) {
				outerBranchEntity=null;
				logger.info("获取代理网点出错："+e.getMessage());
				throw new Exception("获取代理网点出错："+e.getMessage());
			}	
			if(null==outerBranchEntity){
				AirlinesAgentEntityTransEntity agentEntityTransEntity=null;
				try {
					AirlinesAgentEntity airlinesAgent=new AirlinesAgentEntity();
					airlinesAgent.setAgentCode(param);
					List<AirlinesAgentEntityTransEntity> agentEntityTransEntities= airlinesAgentService.queryAirlinesAgentTransListBySelectiveCondition(airlinesAgent);//获取航空公司代理人
					if (null!=agentEntityTransEntities && agentEntityTransEntities.size()>0) {
						agentEntityTransEntity=agentEntityTransEntities.get(0);
					}
				} catch (Exception e) {
					agentEntityTransEntity=null;
					e.printStackTrace();
					logger.info("获取代理人出错："+e.getMessage());
					throw new Exception("获取代理人出错："+e.getMessage());
				}
				agentDeptResponse.setAgentEntity(agentEntityTransEntity);
			}else{
				agentDeptResponse.setOuterBranchEntity(outerBranchEntity);
			}
			agentDeptResponse.setSuccess(true);
			agentDeptResponse.setFailMsg("成功！");
			resp.setHeader("ESB-ResultCode", "1");
			logger.info("==================end=====================");
		} catch (Exception e) {
			
			agentDeptResponse.setSuccess(false);
			agentDeptResponse.setFailMsg(e.getMessage());	
			resp.setHeader("ESB-ResultCode", "0");
			logger.info("==================error=====================");
		}
		String str=JsonUtil.pojoToJson(agentDeptResponse);
		logger.info(str);
		char[] schar =str.toCharArray();
		StringBuilder builder = new StringBuilder();
		for (char c : schar) {
			builder.append(isChineseChar(c) ? gbEncoding(String
					.valueOf(c)) : c);
		}
		return builder.toString();
	}

	public void setAirAgencyDeptService(IAirAgencyDeptService airAgencyDeptService) {
		this.airAgencyDeptService = airAgencyDeptService;
	}
	
    public void setAirlinesAgentService(IAirlinesAgentService airlinesAgentService) {
		this.airlinesAgentService = airlinesAgentService;
	}

	private static boolean isChineseChar(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION) {
            return true;
        }
        return false;
    }
	public static String gbEncoding(final String gbString) {
		char[] utfBytes = gbString.toCharArray();
		String unicodeBytes = "";
		for (int byteIndex = 0; byteIndex < utfBytes.length; byteIndex++) {
			String hexB = Integer.toHexString(utfBytes[byteIndex]);
			if (hexB.length() <= 2) {
				hexB = "00" + hexB;
			}
			unicodeBytes = unicodeBytes + "\\u" + hexB;
		}
		return unicodeBytes;
	}
}

package com.deppon.foss.module.base.baseinfo.server.service.impl;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.module.base.baseinfo.api.server.dao.IOrgGisUrlDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgCodeShortUrlService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgGisUrlEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.OrgCodeShortUrlDto;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.OrgCodeShortUrlException;
import com.deppon.foss.util.define.ESBHeaderConstant;

public class OrgCodeShortUrlService implements IOrgCodeShortUrlService{

	/**
	 * 序列化
	 */
	private static final long serialVersionUID = 616329426L;
	
	/**
	 * 日志
	 */
	private static final Logger LOGGER=LoggerFactory.getLogger(OrgCodeShortUrlService.class);
	@Context
	HttpServletRequest request;
	@Context
	HttpServletResponse response;
	
	private IOrgGisUrlDao orgGisUrlDao;
	private IOrgAdministrativeInfoService  orgAdministrativeInfoService;
	
	public void setOrgGisUrlDao(IOrgGisUrlDao orgGisUrlDao) {
		this.orgGisUrlDao = orgGisUrlDao;
	}
	
	
	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}


	@Override
	public OrgCodeShortUrlDto queryShortUrlByOrgCode(String jsonString) {
		// TODO Auto-generated method stub
		
		// 设置相应header
				response.setHeader(ESBHeaderConstant.VERSION, request.getHeader(ESBHeaderConstant.VERSION));
				response.setHeader(ESBHeaderConstant.ESBSERVICECODE, "FOSS_ESB2FOSS_ORG_NAV_LINK_QUERY");
				response.setHeader(ESBHeaderConstant.REQUESTID, request.getHeader(ESBHeaderConstant.REQUESTID));
				response.setHeader(ESBHeaderConstant.BUSINESSID, request.getHeader(ESBHeaderConstant.BUSINESSID));
				response.setHeader(ESBHeaderConstant.MESSAGEFORMAT, request.getHeader(ESBHeaderConstant.MESSAGEFORMAT));
				response.setHeader(ESBHeaderConstant.EXCHANGEPATTERN, request.getHeader(ESBHeaderConstant.EXCHANGEPATTERN));
				response.setHeader(ESBHeaderConstant.BACKSERVICECODE, request.getHeader(ESBHeaderConstant.BACKSERVICECODE));
				response.setHeader(ESBHeaderConstant.RESPONSEID, UUID.randomUUID().toString());
				response.setHeader(ESBHeaderConstant.SOURCESYSTEM, "FOSS");
				//封装的实体
				OrgCodeShortUrlDto dto=new OrgCodeShortUrlDto();
				try {
					//解析客户端传过来的json
					JSONObject json=JSONObject.fromObject(jsonString);
					//从json里面拿出你想要的字段
					String unifiedCode=json.getString("unifiedCode");
					//必要的逻辑判断
					if(StringUtils.isBlank(unifiedCode)){
						dto.setState(false);
						dto.setMessage("部门编号为空");
						//需要的返回值，0表示失败，1表示成功
						response.setHeader(ESBHeaderConstant.RESULTCODE, "1");
						return dto;
					}
					//查询
					OrgAdministrativeInfoEntity orgEntity= orgAdministrativeInfoService.queryOrgAdministrativeInfoByUnifiedCodeNoCache(unifiedCode);
					if(orgEntity!=null){
						OrgGisUrlEntity entity=orgGisUrlDao.queryGisUrlInfoByOrgCode(orgEntity.getCode());
						if(entity!=null){
							dto.setShortUrl(entity.getShortUrl());
							dto.setState(true);
							dto.setMessage("已返回Gis短链接");
							response.setHeader(ESBHeaderConstant.RESULTCODE, "1");
							LOGGER.info("返回给CC的实体"+dto);
							return dto;
						}
					}
					dto.setState(false);
					dto.setMessage("查询不到Gis短链接");
					response.setHeader(ESBHeaderConstant.RESULTCODE, "1");
					LOGGER.info("返回给CC的实体"+dto);
					return dto;
				} catch (OrgCodeShortUrlException e) {
					// TODO: handle exception
					LOGGER.info("接口异常"+e.getMessage());
					e.printStackTrace();
					dto.setState(false);
					dto.setMessage(e.getMessage());
					response.setHeader(ESBHeaderConstant.RESULTCODE, "0");
					return dto;
				}
	}

}

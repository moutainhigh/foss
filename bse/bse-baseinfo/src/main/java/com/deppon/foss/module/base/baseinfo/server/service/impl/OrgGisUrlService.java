package com.deppon.foss.module.base.baseinfo.server.service.impl;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IOrgGisUrlDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgGisUrlService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgGisUrlEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.OrgGisUrlInfoDto;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.OrgGisUrlException;
import com.deppon.foss.util.define.ESBHeaderConstant;

/**
 * 同步给移动开发GIS长地址service实现类
 * 
 * @ClassName: OrgGisUrlService
 * @author 200664-yangjinheng
 * @date 2014年10月21日 上午10:01:08
 */
public class OrgGisUrlService implements IOrgGisUrlService {

	/**
	 * 日志
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(OrgGisUrlService.class);

	@Context
	HttpServletRequest request;
	@Context
	HttpServletResponse response;

	/**
	 * @Fields serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	private IOrgGisUrlDao orgGisUrlDao;

	public void setOrgGisUrlDao(IOrgGisUrlDao orgGisUrlDao) {
		this.orgGisUrlDao = orgGisUrlDao;
	}

	/**
	 * 根据移动端提供的短地址查询长地址
	 * 
	 * @Title: queryLongUrlByShortUrl
	 * @author 200664-yangjinheng
	 * @date 2014年10月21日 上午10:21:46
	 * @throws
	 */
	public OrgGisUrlInfoDto queryLongUrlByShortUrl(String jsonString) {


		// 设置相应header
		response.setHeader(ESBHeaderConstant.VERSION, request.getHeader(ESBHeaderConstant.VERSION));
		response.setHeader(ESBHeaderConstant.ESBSERVICECODE, "FOSS_ESB2FOSS_ORGGISURL");
		response.setHeader(ESBHeaderConstant.REQUESTID, request.getHeader(ESBHeaderConstant.REQUESTID));
		response.setHeader(ESBHeaderConstant.BUSINESSID, request.getHeader(ESBHeaderConstant.BUSINESSID));
		response.setHeader(ESBHeaderConstant.MESSAGEFORMAT, request.getHeader(ESBHeaderConstant.MESSAGEFORMAT));
		response.setHeader(ESBHeaderConstant.EXCHANGEPATTERN, request.getHeader(ESBHeaderConstant.EXCHANGEPATTERN));
		response.setHeader(ESBHeaderConstant.BACKSERVICECODE, request.getHeader(ESBHeaderConstant.BACKSERVICECODE));
		response.setHeader(ESBHeaderConstant.RESPONSEID, UUID.randomUUID().toString());
		response.setHeader(ESBHeaderConstant.SOURCESYSTEM, "FOSS");

		// ESBHeaderConstant.RESULTCODE 需要的返回值，0表示失败，1表示成功
		
		OrgGisUrlInfoDto dto = new OrgGisUrlInfoDto();

		try {
			// 解析客户端传过来的json数据
			JSONObject json = JSONObject.fromObject(jsonString);

			String shortUrl = json.getString("shortUrl");
			// 短地址为空，直接返回
			if (StringUtil.isBlank(shortUrl)) {
				dto.setState(false);
				dto.setMessage("传递的短地址为空");
				response.setHeader(ESBHeaderConstant.RESULTCODE, "1");
				return dto;
			}

			OrgGisUrlEntity entity = orgGisUrlDao.queryLongUrlByShortUrl(shortUrl);
			if (entity != null) {
				dto.setDepCoodinate(entity.getDepCoodinate());
				dto.setState(true);
				dto.setMessage("已返回组织地址坐标");
				response.setHeader(ESBHeaderConstant.RESULTCODE, "1");
				return dto;
			}

			// 查询不到，返回消息
			dto.setState(false);
			dto.setMessage("查询不到短地址匹配的组织地址坐标");
			response.setHeader(ESBHeaderConstant.RESULTCODE, "1");
			return dto;
		} catch (OrgGisUrlException e) {
			e.printStackTrace();
			dto.setMessage(e.getMessage());
			dto.setState(false);
			response.setHeader(ESBHeaderConstant.RESULTCODE, "0");
			return dto;
		}
	}

	/**
	 * 当行政组织变化时，更新GIS短地址信息
	 * 
	 * @Title: updateGisUrlInfo
	 * @author 200664-yangjinheng
	 * @date 2014年10月21日 上午10:22:00
	 * @throws
	 */
	public void updateGisUrlInfo(OrgAdministrativeInfoEntity entity) {

		// 查询
		OrgGisUrlEntity result = orgGisUrlDao.queryGisUrlInfoByOrgCode(entity.getCode());

		// 查询结果不为空且与旧的长地址不相等，则删除旧数据，增加新数据
		if (result != null) {
//			String depCoodinate = result.getDepCoodinate();
			String depCoordinate = entity.getDepCoordinate();
			if(StringUtils.isNotEmpty(depCoordinate)){
				if (!entity.getDepCoordinate().equals(result.getDepCoodinate())) {
					OrgGisUrlEntity deleteResult = orgGisUrlDao.deleteGisUrlInfo(entity);
					if (deleteResult == null) {
						String msg = "更新组织短链接时，作废旧数据失败";
						LOGGER.error(msg);
						throw new OrgGisUrlException(msg);
					}
					orgGisUrlDao.addGisUrlInfo(entity);
				}
			}
			
		}
		// 查询结果为空，直接增加数据
		else {
			orgGisUrlDao.addGisUrlInfo(entity);
		}
	}

}

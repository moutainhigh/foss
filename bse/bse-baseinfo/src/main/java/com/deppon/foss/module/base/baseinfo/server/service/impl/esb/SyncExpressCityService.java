package com.deppon.foss.module.base.baseinfo.server.service.impl.esb;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.esb.api.ESBJMSAccessor;
import com.deppon.esb.api.domain.AccessHeader;
import com.deppon.esb.core.exception.ESBException;
import com.deppon.esb.pojo.transformer.jaxb.SyncExpressCityRequestTrans;
import com.deppon.foss.base.util.define.SymbolConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.base.baseinfo.api.server.service.esb.ISyncExpressCityService;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.ExpressCityResultDto;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.define.FossConstants;
import com.deppon.ows.inteface.domain.CityInfo;
import com.deppon.ows.inteface.domain.SyncExpressCityRequest;

/**
 * 同步FOSS的试点城市和快递代理城市信息给官网、CRM、呼叫中心系统
 * 
 * @author 088933-foss-zhangjiheng
 * @date 2013-7-24 上午11:50:25
 */
public class SyncExpressCityService implements ISyncExpressCityService {
	/**
	 * 日志信息
	 */
	private static final Logger logger = LoggerFactory
			.getLogger(SyncExpressCityService.class);
	/**
	 * 服务编码
	 */
	private static final String ESB_SERVICE_CODE = "ESB_FOSS2ESB_SYNC_EXPRESS_CITY";

	/**
	 * 同步FOSS的试点城市和快递代理城市信息给官网、CRM、呼叫中心系统
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2013-7-24 上午11:50:25
	 */
	@Override
	public void syncExpressCityToOws(List<ExpressCityResultDto> list) {
		// 判断集合是否为空
		if (CollectionUtils.isNotEmpty(list)) {
			SyncExpressCityRequest expressCityRequest = new SyncExpressCityRequest();
			List<CityInfo> cityInfoList = new ArrayList<CityInfo>();

			StringBuffer versionNos = new StringBuffer();
			StringBuffer codes = new StringBuffer();
			for (ExpressCityResultDto entity : list) {
				// 判断实体是否为空
				if (entity == null) {
					continue;
				}
				// 调用私有方法对ESB实体赋值
				CityInfo cityInfo = this.transFossToEsb(entity);
				cityInfoList.add(cityInfo);
				versionNos.append(entity.getVersionNo());
				versionNos.append(SymbolConstants.EN_COMMA);
				//codes.append(entity.getDistrictCode());
				//codes.append(SymbolConstants.EN_COMMA);

			}
			codes.append(list.get(0).getDistrictCode());
			
			// 调用添加方法 addAll()
			expressCityRequest.getCitys().addAll(cityInfoList);
			// 创建服务头信息
			AccessHeader accessHeader = new AccessHeader();
			accessHeader.setEsbServiceCode(ESB_SERVICE_CODE);
			accessHeader.setBusinessId(codes.toString());
			accessHeader
					.setBusinessDesc1("send district info to other platform.同步试点、快递代理城市信息 到其它平台");
			accessHeader.setBusinessDesc2(versionNos.toString());
			accessHeader.setVersion("0.1");
			try {
				logger.info("start to send dictrct info to other platform 开始 同步试点、快递代理城市信息 到其它平台：\n"
						+ new SyncExpressCityRequestTrans()
								.fromMessage(expressCityRequest));
				ESBJMSAccessor.asynReqeust(accessHeader, expressCityRequest);
				logger.info("end to send dictrct info to other platform 结束 同步试点、快递代理城市信息 到其它平台：\n"
						+ new SyncExpressCityRequestTrans()
								.fromMessage(expressCityRequest));
			} catch (ESBException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * 将FOSS的试点城市信息set给ESB的实体信息
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2013-7-24 上午11:50:25
	 */
	private CityInfo transFossToEsb(ExpressCityResultDto entity) {
		if(null==entity){
			return null;
		}
		//校验关键字段信息不能为空
		if(StringUtils.isEmpty(entity.getId())){
		    throw new BusinessException("", "同步快递代理、试点城市信息时，ID为空！");
		}
		if(StringUtils.isEmpty(entity.getCityCode())){
			throw new BusinessException("", "同步快递代理、试点城市信息时，城市编码为空！");
		}
		if(StringUtils.isEmpty(entity.getType())){
			throw new BusinessException("", "同步快递代理、试点城市信息时，城市类别为空！");
		}
		CityInfo cityInfo = new CityInfo();
		//ID
		cityInfo.setId(entity.getId());
		//行政区域编码
		cityInfo.setCode(entity.getDistrictCode());
		//区域全称
		cityInfo.setName(entity.getCityName());
		
		//如果是试点城市
		if(entity.getType().equals(DictionaryValueConstants.EXPRESS_CITY_TYPE_PILOT)){
			cityInfo.setIsPilot(FossConstants.ACTIVE);
			cityInfo.setHasAgent(FossConstants.INACTIVE);
		}
		//如果是快递代理城市
		else if(entity.getType().equals(DictionaryValueConstants.EXPRESS_CITY_TYPE_LDP)){
			cityInfo.setIsPilot(FossConstants.INACTIVE);
			cityInfo.setHasAgent(FossConstants.ACTIVE);
		}
		//其他不识别类型
		else{
			throw new BusinessException("", "同步快递代理、试点城市信息时，城市类别既不是试点也不是快递代理类型！");
		}
		//创建时间
		cityInfo.setCreateTime(entity.getCreateTime());
		//更新时间
		cityInfo.setModifyTime(entity.getModifyTime());
		//是否启用
		cityInfo.setActive(entity.getActive());
		//创建人
		cityInfo.setCreateUserCode(entity.getCreateUserCode());
		//更新人
		cityInfo.setModifyUserCode(entity.getModifyUserCode());
		//版本号
		cityInfo.setVersionNo(entity.getVersionNo());
		return cityInfo;

	}

}

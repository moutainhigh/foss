package com.deppon.foss.module.base.baseinfo.server.action;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILtlServiceScopeService;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.LtlServiceScopeDto;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.LtlServiceScopeVo;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;

/**
 * 零担服务范围Action
 * 
 * @ClassName: LtlServiceScopeAction
 * @author 200664-yangjinheng
 * @date 2014年9月30日 下午12:32:31
 */
public class LtlServiceScopeAction extends AbstractAction {

	private static final long serialVersionUID = 1L;

	/**
	 * 日志打印对象声明
	 */
	private static final Logger LOGGER = Logger.getLogger(LtlServiceScopeAction.class);

	private LtlServiceScopeVo ltlServiceScopeVo;

	/**
	 * 声明零担服务范围service
	 */
	private ILtlServiceScopeService ltlServiceScopeService;

	public LtlServiceScopeVo getLtlServiceScopeVo() {
		return ltlServiceScopeVo;
	}

	public void setLtlServiceScopeVo(LtlServiceScopeVo ltlServiceScopeVo) {
		this.ltlServiceScopeVo = ltlServiceScopeVo;
	}

	public void setLtlServiceScopeService(ILtlServiceScopeService ltlServiceScopeService) {
		this.ltlServiceScopeService = ltlServiceScopeService;
	}

	/**
	 * 根据编码code查询行政区域
	 * 
	 * @Title: queryDistrictByCode
	 * @author 200664-yangjinheng
	 * @date 2014年9月30日 下午12:34:54
	 * @throws
	 */
	@JSON
	public String queryDistrictByCode() {
		// 校验前台传入的VO是否为空
		if (ltlServiceScopeVo == null
		// 校验前台传入的VO的行政区域明细信息是否为空
				|| ltlServiceScopeVo.getDistrictDto() == null//
				|| StringUtils.isEmpty(ltlServiceScopeVo//
						.getDistrictDto().getCode())) {
			// 校验不通过返回错误信息
			return returnSuccess();
		}
		try {
			// 根据CODE查询行政区域
			LtlServiceScopeDto dto = ltlServiceScopeService//
					.queryDistrictByCode(ltlServiceScopeVo.getDistrictDto().getCode());

			// 当行政区域级别为“县区”或者“市”时，查询该行政区域的营业网点信息和派送范围信息
			if (dto.getDegree().equals(DictionaryValueConstants.DISTRICT_COUNTY) || //
					dto.getDegree().equals(DictionaryValueConstants.DISTRICT_CITY)) {
				List<LtlServiceScopeDto> serviceScopeList = ltlServiceScopeService//
						.queryLtlServiceScopeInfo(dto);
				ltlServiceScopeVo.setServiceScopeList(serviceScopeList);
			}
			// 为VO设置查询结果
			ltlServiceScopeVo.setDistrictDto(dto);

			return returnSuccess();
		} catch (BusinessException e) {
			LOGGER.debug(e.getMessage(), e);
			return returnError(e);
		}
	}

	/**
	 * 根据name查询行政区域，迭代查询该行政区域的上级，直到上级为“中国”
	 * 
	 * @Title: queryDistrictByName
	 * @author 200664-yangjinheng
	 * @date 2014年9月30日 下午12:35:04
	 * @throws
	 */
	@JSON
	public String queryDistrictByName() {
		// 校验前台传入的VO是否为空
		if (ltlServiceScopeVo == null
		// 校验前台传入的VO的行政区域明细信息是否为空
				|| ltlServiceScopeVo.getDistrictDto() == null//
				|| StringUtils.isEmpty(ltlServiceScopeVo//
						.getDistrictDto().getName())) {
			// 校验不通过返回错误信息
			return returnSuccess();
		}

		// 根据Name查询
		LtlServiceScopeDto dto = ltlServiceScopeService//
				.queryDistrictByName(ltlServiceScopeVo.getDistrictDto().getName());
		if (dto == null) {
			return returnSuccess();
		}

		// 当行政区域级别为“县区”或者“市”时，查询该行政区域的营业网点信息和派送范围信息
		if (null != dto.getDegree() && (dto.getDegree().equals(DictionaryValueConstants.DISTRICT_COUNTY) ||
		//
				dto.getDegree().equals(DictionaryValueConstants.DISTRICT_CITY))) {
			List<LtlServiceScopeDto> serviceScopeList = ltlServiceScopeService//
					.queryLtlServiceScopeInfo(dto);
			ltlServiceScopeVo.setServiceScopeList(serviceScopeList);
		}

		// 为VO设置查询结果
		ltlServiceScopeVo.setDistrictDto(dto);

		return returnSuccess();
	}

}

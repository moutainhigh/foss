package com.deppon.foss.module.settlement.closing.server.service.impl;

import com.deppon.foss.module.pickup.pricing.api.server.service.IProductService;
import com.deppon.foss.module.settlement.closing.api.server.dao.IMvrPtpDetailDao;
import com.deppon.foss.module.settlement.closing.api.server.service.IBigSubTypeService;
import com.deppon.foss.module.settlement.closing.api.server.service.IMvrPtpAllDetailsService;
import com.deppon.foss.module.settlement.closing.api.shared.define.VDRptTypes;
import com.deppon.foss.module.settlement.closing.api.shared.domain.BigSubTypeEntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrPtpAllDetailResultDto;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrPtpAllDetailsDto;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import java.util.*;

public class MvrPtpAllDetailsService implements IMvrPtpAllDetailsService {

	/**
	 * 注入dao
	 */
	private IMvrPtpDetailDao mvrPtpDetailDao;

	/**
	 * 注入查询大类小类的dao
	 */
	private IBigSubTypeService bigSubTypeService;

	public void setBigSubTypeService(IBigSubTypeService bigSubTypeService) {
		this.bigSubTypeService = bigSubTypeService;
	}

	/**
	 * 根据产品CODE找对应的名称
	 */
	IProductService productService;

    private static Map<String, String> VIEW_MAP;

	@Override
	public List<MvrPtpAllDetailResultDto> selectByConditions(
			MvrPtpAllDetailsDto dto, int offset, int limit) {

		// 校验分页参数
		if (offset < 0 || limit < 0) {
			throw new SettlementException("分页参数有误！");
		}

		// 根据报表类型选择相应的明细
		if (StringUtils.isEmpty(dto.getReportType())) {
			throw new SettlementException("报表类型为空");
		} else {
			dto.setViewName(getViewName(dto.getReportType()));
		}

//		// 如果是以下几种类型，权限设置根据到达部门
//		if (StringUtils.equals(dto.getRptType(), VDRptTypes.NAFR) // 空运月报
//				|| StringUtils.equals(dto.getRptType(), VDRptTypes.NPLR) // 偏线月报
//				|| StringUtils.equals(dto.getRptType(), VDRptTypes.NRFDO)
//				|| StringUtils.equals(dto.getRptType(), VDRptTypes.NRFDT)) { // 专线到达
//			dto.setCodeStatus(SettlementConstants.VOUCHER_DETAILS_DESTORG_CODE_STATUS);
//		}
//		// 如果是始发月报，权限设置根据出发部门
//		if (StringUtils.equals(dto.getRptType(), VDRptTypes.NRFONO)
//				|| StringUtils.equals(dto.getRptType(), VDRptTypes.NRFONT)
//				|| StringUtils.equals(dto.getRptType(), VDRptTypes.NRFOSO)
//				|| StringUtils.equals(dto.getRptType(), VDRptTypes.NRFOST)) { // 始发月报
//			dto.setCodeStatus(SettlementConstants.VOUCHER_DETAILS_ORIGORG_CODE_STATUS);
//		}

		BigSubTypeEntity subType = new BigSubTypeEntity();

		if (StringUtils.isNotEmpty(dto.getTypeCode())
				&& StringUtils.isEmpty(dto.getSubTypeCode())) {

			if (StringUtils.equals(dto.getTypeCode(), "all")) {
				dto.setBusinessCaseList(null);
			} else {
				List<String> typeCodes = new ArrayList<String>();

				subType.setTableName(dto.getReportType());

				subType.setBigTypeCode(dto.getTypeCode());

				List<BigSubTypeEntity> typesList = bigSubTypeService
						.querySubType(subType);

				for (BigSubTypeEntity subtype : typesList) {
					typeCodes.add(subtype.getSubTypeCode());
				}
				dto.setBusinessCaseList(typeCodes);
			}

		} else if (!StringUtils.isEmpty(dto.getSubTypeCode())) {
			dto.setBusinessCaseList(Arrays.asList(new String[] { dto
					.getSubTypeCode() }));
		}

		// 调用dao进行查询
		List<MvrPtpAllDetailResultDto> list = mvrPtpDetailDao.selectByConditions(
				dto, offset, limit);

		// 将结果集里面的大类小类转换成中文
		BigSubTypeEntity bigSub = new BigSubTypeEntity();

		bigSub.setTableName(dto.getReportType());

		List<BigSubTypeEntity> typesList = bigSubTypeService
				.queryAllTypes(bigSub);

		// 如果列表不为空，则需要封装指标小类和指标大类
		if (CollectionUtils.isNotEmpty(list)) {

			// 获取所有业务场景对应的大类小类
			Map<String, String[]> map = bussinessCaseConvert(typesList);

			// 循环封装
			for (MvrPtpAllDetailResultDto resultDto : list) {
				// 获取大小类
				String[] businessCase = map.get(resultDto.getBusinessCase());
				// 判空
				if (businessCase != null && businessCase.length == 2) {
					// 设置指标转化
					resultDto.setSubTypeCode(businessCase[0]);
					resultDto.setTypeCode(businessCase[1]);
				}
			}
		}

		return list;
	}

	/**
	 * 将查询出来的结果转换成符合接口的Map
	 * 
	 * @param typesList
	 * @return
	 */
	private Map<String, String[]> bussinessCaseConvert(
			List<BigSubTypeEntity> typesList) {
		Map<String, String[]> typesMap = new HashMap<String, String[]>();

		if (typesList == null || typesList.size() == 0) {
			return null;
		}

		for (BigSubTypeEntity bigSubtype : typesList) {
			typesMap.put(
					bigSubtype.getSubTypeCode(),
					new String[] { bigSubtype.getSubTypeName(),
							bigSubtype.getBigTypeName() });
		}

		return typesMap;
	}


	/**
	 * 根据报表类型得到明细视图名称
	 * 
	 * @param rptType
	 * @return
	 */
	private String getViewName(String rptType) {
        if(VIEW_MAP == null){
            VIEW_MAP = new HashMap<String,String>();
        }
        if(VIEW_MAP.isEmpty()){
            VIEW_MAP.put(VDRptTypes.PTP_DQPA,"T_STL_PTP_NDVD_DQPA");
            VIEW_MAP.put(VDRptTypes.PTP_ST,"T_STL_PTP_NDVD_ST");
            VIEW_MAP.put(VDRptTypes.PTP_PSC,"T_STL_PTP_NDVD_PSC");
            VIEW_MAP.put(VDRptTypes.PTP_RP,"T_STL_PTP_NDVD_RP");
            VIEW_MAP.put(VDRptTypes.PTP_RPS,"T_STL_PTP_NDVD_RPS");
        }
        if(VIEW_MAP.containsKey(rptType)){
            return VIEW_MAP.get(rptType).toString();
        } else {
            throw new SettlementException("不支持的报表类型");
        }
	}
		
	@Override
	public MvrPtpAllDetailResultDto queryTotalCounts(MvrPtpAllDetailsDto dto) {
		// 根据报表类型选择相应的明细
		if (StringUtils.isEmpty(dto.getReportType())) {
			throw new SettlementException("报表类型为空");
		} else {
			dto.setViewName(getViewName(dto.getReportType()));
		}

		BigSubTypeEntity subType = new BigSubTypeEntity();

		if (StringUtils.isNotEmpty(dto.getTypeCode())
				&& StringUtils.isEmpty(dto.getSubTypeCode())) {

			if (StringUtils.equals(dto.getTypeCode(), "all")) {
				dto.setBusinessCaseList(null);
			} else {
				List<String> typeCodes = new ArrayList<String>();

				subType.setTableName(dto.getReportType());

				subType.setBigTypeCode(dto.getTypeCode());

				List<BigSubTypeEntity> typesList = bigSubTypeService
						.querySubType(subType);

				for (BigSubTypeEntity subtype : typesList) {
					typeCodes.add(subtype.getSubTypeCode());
				}
				dto.setBusinessCaseList(typeCodes);
			}
		} else if (!StringUtils.isEmpty(dto.getSubTypeCode())) {
			dto.setBusinessCaseList(Arrays.asList(new String[] { dto
					.getSubTypeCode() }));
		}

		return mvrPtpDetailDao.queryTotalCounts(dto);
	}

	public void setProductService(IProductService productService) {
		this.productService = productService;
	}

	public void setMvrPtpDetailDao(IMvrPtpDetailDao mvrPtpDetailDao) {
		this.mvrPtpDetailDao = mvrPtpDetailDao;
	}
}

package com.deppon.foss.module.settlement.closing.server.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.deppon.foss.module.pickup.pricing.api.server.service.IProductService;
import com.deppon.foss.module.settlement.closing.api.server.dao.IMvrDetailDao;
import com.deppon.foss.module.settlement.closing.api.server.service.IBigSubTypeService;
import com.deppon.foss.module.settlement.closing.api.server.service.IMvrAllDetailsService;
import com.deppon.foss.module.settlement.closing.api.shared.define.VDRptTypes;
import com.deppon.foss.module.settlement.closing.api.shared.domain.BigSubTypeEntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.VoucherDetailResultDto;
import com.deppon.foss.module.settlement.closing.api.shared.dto.VoucherDetailsDto;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;

public class MvrAllDetailsService implements IMvrAllDetailsService {

	/**
	 * 注入dao
	 */
	private IMvrDetailDao mvrDetailDao;

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

    private static Map VIEW_MAP;

	@Override
	public List<VoucherDetailResultDto> selectByConditions(
			VoucherDetailsDto dto, int offset, int limit) {

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

		// 如果是以下几种类型，权限设置根据到达部门
		if (StringUtils.equals(dto.getRptType(), VDRptTypes.NAFR) // 空运月报
				|| StringUtils.equals(dto.getRptType(), VDRptTypes.NPLR) // 偏线月报
				|| StringUtils.equals(dto.getRptType(), VDRptTypes.NRFDO)
				|| StringUtils.equals(dto.getRptType(), VDRptTypes.NRFDT)) { // 专线到达
			dto.setCodeStatus(SettlementConstants.VOUCHER_DETAILS_DESTORG_CODE_STATUS);
		}
		// 如果是始发月报，权限设置根据出发部门
		if (StringUtils.equals(dto.getRptType(), VDRptTypes.NRFONO)
				|| StringUtils.equals(dto.getRptType(), VDRptTypes.NRFONT)
				|| StringUtils.equals(dto.getRptType(), VDRptTypes.NRFOSO)
				|| StringUtils.equals(dto.getRptType(), VDRptTypes.NRFOST)) { // 始发月报
			dto.setCodeStatus(SettlementConstants.VOUCHER_DETAILS_ORIGORG_CODE_STATUS);
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

		// 调用dao进行查询
		List<VoucherDetailResultDto> list = mvrDetailDao.selectByConditions(
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
			for (VoucherDetailResultDto resultDto : list) {
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

	@Override
	public List<VoucherDetailResultDto> selectEgByConditions(
			VoucherDetailsDto dto, int offset, int limit) {
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

//		if (StringUtils.isEmpty(dto.getVoucherMark())) {
//			throw new SettlementException("发票标记为空");
//		}
		// 如果只选择大类没有选择小类，则查询该大类下所有的小类信息
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
			// 如果选择小类，则直接通过小类查找
			dto.setBusinessCaseList(Arrays.asList(new String[] { dto
					.getSubTypeCode() }));
		}

		// 调用dao进行查询
		List<VoucherDetailResultDto> list = mvrDetailDao.selectEgByConditions(
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
			for (VoucherDetailResultDto resultDto : list) {
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

	@Override
	public VoucherDetailResultDto queryEgTotalCounts(VoucherDetailsDto dto) {

		// 根据报表类型选择相应的明细
		if (StringUtils.isEmpty(dto.getReportType())) {
			throw new SettlementException("报表类型为空");
		} else {
			dto.setViewName(getViewName(dto.getReportType()));
		}

//		if (StringUtils.isEmpty(dto.getVoucherMark())) {
//			throw new SettlementException("发票标记为空");
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

		return mvrDetailDao.queryEgTotalCounts(dto);
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
            VIEW_MAP.put(VDRptTypes.NRFONO,"V_STL_DVD_NRFONO");
            VIEW_MAP.put(VDRptTypes.NRFOSO,"V_STL_DVD_NRFOSO");
            VIEW_MAP.put(VDRptTypes.NRFONT,"V_STL_DVD_NRFONT");
            VIEW_MAP.put(VDRptTypes.NRFOST,"V_STL_DVD_NRFOST");
            VIEW_MAP.put(VDRptTypes.NRFDO,"V_STL_DVD_NRFDO");
            VIEW_MAP.put(VDRptTypes.NRFDT,"V_STL_DVD_NRFDT");
            VIEW_MAP.put(VDRptTypes.SNRFDT,"V_STL_DVD_SNRFDT");
            VIEW_MAP.put(VDRptTypes.NPLR,"V_STL_DVD_NPLR");
            VIEW_MAP.put(VDRptTypes.NAFR,"V_STL_DVD_NAFR");
            VIEW_MAP.put(VDRptTypes.NRFI,"V_STL_DVD_NRFI");
            VIEW_MAP.put(VDRptTypes.SNRFI,"V_STL_DVD_SNRFI");
            VIEW_MAP.put(VDRptTypes.NPLI,"V_STL_DVD_NPLI");
            VIEW_MAP.put(VDRptTypes.NAFI,"V_STL_DVD_NAFI");
            VIEW_MAP.put(VDRptTypes.WOODEN,"V_STL_DVD_WOODEN");
            VIEW_MAP.put(VDRptTypes.ORCC,"V_STL_DVD_ORCC");
            VIEW_MAP.put(VDRptTypes.ORCCI,"V_STL_DVD_ORCCI");
            VIEW_MAP.put(VDRptTypes.NUSI,"V_STL_DVD_NUSI");
            VIEW_MAP.put(VDRptTypes.DCO,"V_STL_DVD_DCO");
            VIEW_MAP.put(VDRptTypes.DCD,"V_STL_DVD_DCD");
            VIEW_MAP.put(VDRptTypes.DCI,"V_STL_DVD_DCI");
            VIEW_MAP.put(VDRptTypes.HI,"V_STL_DVD_HI");
        }
        if(VIEW_MAP.containsKey(rptType)){
            return VIEW_MAP.get(rptType).toString();
        } else {
            throw new SettlementException("不支持的报表类型");
        }
	}

	@Override
	public VoucherDetailResultDto queryTotalCounts(VoucherDetailsDto dto) {
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

		return mvrDetailDao.queryTotalCounts(dto);
	}

	public void setProductService(IProductService productService) {
		this.productService = productService;
	}

	public void setMvrDetailDao(IMvrDetailDao mvrDetailDao) {
		this.mvrDetailDao = mvrDetailDao;
	}

}

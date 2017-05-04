/*
 * PROJECT NAME: stl-closing
 * PACKAGE NAME: com.deppon.foss.module.settlement.closing.server.service.impl
 * FILE    NAME: MvrRfiDetailService.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.settlement.closing.server.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.deppon.foss.module.pickup.pricing.api.server.service.IProductService;
import com.deppon.foss.module.settlement.closing.api.server.dao.IMvrDetailDao;
import com.deppon.foss.module.settlement.closing.api.server.service.IMvrDetailService;
import com.deppon.foss.module.settlement.closing.api.shared.define.VDRptTypes;
import com.deppon.foss.module.settlement.closing.api.shared.define.VDTypeMappingEnum;
import com.deppon.foss.module.settlement.closing.api.shared.dto.VoucherDetailResultDto;
import com.deppon.foss.module.settlement.closing.api.shared.dto.VoucherDetailsDto;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementReportNumber;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;

/**
 * 始发专线往来月报表明细
 * 
 * @author 045738-foss-maojianqiang
 * @date 2013-4-2 下午7:37:55
 */
public class MvrDetailService implements IMvrDetailService {

	/**
	 * 注入dao
	 */
	private IMvrDetailDao mvrDetailDao;
	/**
	 * 根据产品CODE找对应的名称
	 */
	IProductService productService;

	public void setMvrDetailDao(IMvrDetailDao mvrDetailDao) {
		this.mvrDetailDao = mvrDetailDao;
	}

	/**
	 * 查询明细列表
	 * 
	 * @author 045738-foss-maojianqiang
	 * @date 2013-4-2 下午7:37:55
	 * @param rb
	 * @param dto
	 * @return
	 * @see com.deppon.foss.module.settlement.closing.api.server.service.IMvrRfiDetailService#selectByConditions(org.apache.ibatis.session.RowBounds,
	 *      com.deppon.foss.module.settlement.closing.api.shared.dto.VoucherDetailsDto)
	 */
	@Override
	public List<VoucherDetailResultDto> selectByConditions(VoucherDetailsDto dto, int offset,
			int limit) {

		// 校验分页参数
		if (offset < 0 || limit < 0) {
			throw new SettlementException("分页参数有误！");
		}

		if (StringUtils.isEmpty(dto.getRptType())) {
			throw new SettlementException("报表类型为空");
		} else {
			dto.setViewName(getViewName(dto.getRptType()));
		}

		//如果是以下几种类型，权限设置根据到达部门
		if(StringUtils.equals(dto.getRptType(), VDRptTypes.TYPE_AFR) //空运月报
				|| StringUtils.equals(dto.getRptType(), VDRptTypes.TYPE_PLR) // 偏线月报
				|| StringUtils.equals(dto.getRptType(), VDRptTypes.TYPE_RFD)){ //专线到达
			dto.setCodeStatus(SettlementConstants.VOUCHER_DETAILS_DESTORG_CODE_STATUS);
		}
		//如果是始发月报，权限设置根据出发部门
		if(StringUtils.equals(dto.getRptType(), VDRptTypes.TYPE_RFO)){ //始发月报
			dto.setCodeStatus(SettlementConstants.VOUCHER_DETAILS_ORIGORG_CODE_STATUS);
		}
		
		// 获取业务场景转化--小类
		if (StringUtils.isNotBlank(dto.getSubTypeCode())) {

			dto.setBusinessCaseList(Arrays.asList(new String[] { dto.getSubTypeCode() }));

		}

		// 根据大类类获取业务场景
		else if (StringUtils.isNotBlank(dto.getTypeCode())) {
			List<String> list = VDTypeMappingEnum.getChildCodeByTypeCode(dto.getRptType(),
					dto.getTypeCode());
			dto.setBusinessCaseList(list);
		}

		this.resetBusinessCaseListByLand(dto);
		
		//如果是始发月报
		validaExtracted(dto);
		
		// 调用dao进行查询
		List<VoucherDetailResultDto> list = mvrDetailDao.selectByConditions(dto, offset, limit);

		// 如果列表不为空，则需要封装指标小类和指标大类
		if (CollectionUtils.isNotEmpty(list)) {

			// 获取所有业务场景对应的大类小类
			Map<String, String[]> map = VDTypeMappingEnum.getRenderMap(dto.getRptType());

			// 循环封装
			for (VoucherDetailResultDto resultDto : list) {
				//还款现金未签收
				if(resultDto.getBusinessCase().equals("LAND_UR_DEST_CH_NPOD")){
					resultDto.setSubTypeCode("还款现金未签收");
					resultDto.setTypeCode("还款运单总运费（到付）");
				//还款银行未签收
				}else if(resultDto.getBusinessCase().equals("LAND_UR_DEST_CD_NPOD")){
					resultDto.setSubTypeCode("还款银行未签收");
					resultDto.setTypeCode("还款运单总运费（到付）");
				//还款现金已签收
				}else if(resultDto.getBusinessCase().equals("LAND_UR_DEST_CH_POD")){
					resultDto.setSubTypeCode("还款现金已签收");
					resultDto.setTypeCode("还款运单总运费（到付）");
				//还款银行已签收
				}else if(resultDto.getBusinessCase().equals("LAND_UR_DEST_CD_POD")){
					resultDto.setSubTypeCode("还款银行已签收");
					resultDto.setTypeCode("还款运单总运费（到付）");
				}
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

	private void validaExtracted(VoucherDetailsDto dto) {
		if(StringUtils.equals(dto.getRptType(), VDRptTypes.TYPE_RFO)){ //始发月报
			// 获取业务场景转化--小类
			if (StringUtils.isNotBlank(dto.getSubTypeCode())) {
				//还款现金未签收
				if(dto.getSubTypeCode().equals("UR_DEST_CH_NPOD")){
					List<String> list = new ArrayList<String>();
					list.add("UR_DEST_CH_NPOD");
					list.add("LAND_UR_DEST_CH_NPOD");
					dto.setBusinessCaseList(list);
				//还款银行未签收
				}else if(dto.getSubTypeCode().equals("UR_DEST_CD_NPOD")){
					List<String> list = new ArrayList<String>();
					list.add("UR_DEST_CD_NPOD");
					list.add("LAND_UR_DEST_CD_NPOD");
					dto.setBusinessCaseList(list);
				//还款现金已签收
				}else if(dto.getSubTypeCode().equals("UR_DEST_CH_POD")){
					List<String> list = new ArrayList<String>();
					list.add("UR_DEST_CH_POD");
					list.add("LAND_UR_DEST_CH_POD");
					dto.setBusinessCaseList(list);
				//还款银行已签收
				}else if(dto.getSubTypeCode().equals("UR_DEST_CD_POD")){
					List<String> list = new ArrayList<String>();
					list.add("UR_DEST_CD_POD");
					list.add("LAND_UR_DEST_CD_POD");
					dto.setBusinessCaseList(list);
				}
			}
			// 根据大类类获取业务场景
			else if (StringUtils.isNotBlank(dto.getTypeCode())) {
				List<String> list = VDTypeMappingEnum.getChildCodeByTypeCode(dto.getRptType(),dto.getTypeCode());
				if(dto.getTypeCode().equals("HUAN_KUAN_YUN_DAN_ZONG_YUN_FEI__DAO_FU_")){
					list.add("LAND_UR_DEST_CH_NPOD");
					list.add("LAND_UR_DEST_CD_NPOD");
					list.add("LAND_UR_DEST_CH_POD");
					list.add("LAND_UR_DEST_CD_POD");
				}
				dto.setBusinessCaseList(list);
			}
		}
	}

	/**
	 * 查询合计项
	 * 
	 * @author 045738-foss-maojianqiang
	 * @date 2013-4-2 下午7:37:55
	 * @param dto
	 * @return
	 * @see com.deppon.foss.module.settlement.closing.api.server.service.IMvrRfiDetailService#queryTotalCounts(com.deppon.foss.module.settlement.closing.api.shared.dto.VoucherDetailsDto)
	 */
	@Override
	public VoucherDetailResultDto queryTotalCounts(VoucherDetailsDto dto) {

		if (StringUtils.isEmpty(dto.getRptType())) {
			throw new SettlementException("报表类型为空");
		} else {
			dto.setViewName(getViewName(dto.getRptType()));
		}

		//如果是以下几种类型，权限设置根据到达部门
		if(StringUtils.equals(dto.getRptType(), VDRptTypes.TYPE_AFR) //空运月报
				|| StringUtils.equals(dto.getRptType(), VDRptTypes.TYPE_PLR) // 偏线月报
				|| StringUtils.equals(dto.getRptType(), VDRptTypes.TYPE_RFD)){ //专线到达
			dto.setCodeStatus(SettlementConstants.VOUCHER_DETAILS_DESTORG_CODE_STATUS);
		}
		//如果是始发月报，权限设置根据出发部门
		if(StringUtils.equals(dto.getRptType(), VDRptTypes.TYPE_RFO)){ //始发月报
			dto.setCodeStatus(SettlementConstants.VOUCHER_DETAILS_ORIGORG_CODE_STATUS);
		}
		
		// 获取业务场景转化--小类
		if (StringUtils.isNotBlank(dto.getSubTypeCode())) {

			dto.setBusinessCaseList(Arrays.asList(new String[] { dto.getSubTypeCode() }));

		}

		// 根据大类类获取业务场景
		else if (StringUtils.isNotBlank(dto.getTypeCode())) {
			List<String> list = VDTypeMappingEnum.getChildCodeByTypeCode(dto.getRptType(),
					dto.getTypeCode());
			dto.setBusinessCaseList(list);
		}

		this.resetBusinessCaseListByLand(dto);
		
		validaExtracted(dto);
		
		return mvrDetailDao.queryTotalCounts(dto);
	}

	/**
	 * 根据小类编码，重新设置小类集合。
	 * 如果预收空运代理、空运应付冲应收 就新增 预收快递代理、快递代理应付冲应收 小类
	 * 
	 * @param rptType
	 * @return
	 */
	private void resetBusinessCaseListByLand(VoucherDetailsDto dto){
		
		List<String> list = dto.getBusinessCaseList(); // 获得小类
		if(CollectionUtils.isNotEmpty(list)){
			
			// 获得预收空运/快递代理 、 空运/快递代理应付冲应收 小类编码
			List<String> list1 = VDTypeMappingEnum.getChildCodeByTypeCode 
												(VDRptTypes.TYPE_AFR,"YU_SHOU_KONG_YUN_DAI_LI");//预收空运/快递代理
			List<String> list2 = VDTypeMappingEnum.getChildCodeByTypeCode
												(VDRptTypes.TYPE_RFO,"KONG_YUN_YING_FU_CHONG_YING_SHOU");//空运/快递代理应付冲应收
			
			CollectionUtils.addAll(list1, list2.iterator()); // 合并
			
			List<String> landList = new ArrayList<String>(SettlementReportNumber.TEN); // 快递代理指标
			for (String subType : list) {
				if(list1.contains(subType)){
					//AIR_ -> LAND_
					landList.add(StringUtils.replace(subType, "AIR_", "LAND_"));
				}
				
			}
			
			if(landList.size()>0){
				if(list.size() == 1){
					CollectionUtils.addAll(landList, list.iterator());
					dto.setBusinessCaseList(landList);
				}else{
					CollectionUtils.addAll(list, landList.iterator());
					dto.setBusinessCaseList(list);
				}
			}
		}
		
	}
	
	/**
	 * 根据报表类型得到明细视图名称
	 * 
	 * @param rptType
	 * @return
	 */
	private String getViewName(String rptType) {

		if (VDRptTypes.TYPE_RFO.equals(rptType)) {
			return "V_STL_DVD_RFO";
		}

		else if (VDRptTypes.TYPE_RFD.equals(rptType)) {
			return "V_STL_DVD_RFD";
		}

		else if (VDRptTypes.TYPE_PLR.equals(rptType)) {
			return "V_STL_DVD_PLR";
		}

		else if (VDRptTypes.TYPE_AFR.equals(rptType)) {
			return "V_STL_DVD_AFR";
		}

		else if (VDRptTypes.TYPE_RFI.equals(rptType)) {
			return "V_STL_DVD_RFI";
		}

		else if (VDRptTypes.TYPE_PLI.equals(rptType)) {
			return "V_STL_DVD_PLI";
		}

		else if (VDRptTypes.TYPE_AFI.equals(rptType)) {
			return "V_STL_DVD_AFI";
		}
		
		else if (VDRptTypes.TYPE_LDD.equals(rptType)) {
			return "V_STL_DVD_LDD";
		}
		
		else if (VDRptTypes.TYPE_LDI.equals(rptType)) {
			return "V_STL_DVD_LDI";
		}
		
		else if (VDRptTypes.TYPE_LWO.equals(rptType)) {
			return "V_STL_DVD_LWO";
		} else if(VDRptTypes.ORCCI.equals(rptType)){
			return "V_STL_DVD_ORCCI";
		} else if (VDRptTypes.ORCC.equals(rptType)){
			return "V_STL_DVD_ORCC";
		}else {
			throw new SettlementException("不支持的报表类型");
		}

	}

}

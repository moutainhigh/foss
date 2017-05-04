package com.deppon.foss.module.settlement.closing.server.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.framework.server.components.export.excel.HeaderRows;
import com.deppon.foss.framework.shared.util.classes.ReflectionUtils;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.closing.api.server.service.IMvrPliService;
import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrPliEntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrPliDto;
import com.deppon.foss.module.settlement.closing.server.dao.impl.MvrPliEntityDao;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementReportNumber;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.common.server.util.SettlementUtil;

/**
 * 始发偏线往来service.
 *
 * @author foss-pengzhen
 * @date 2013-3-6 上午11:19:07
 * @since
 * @version
 */
public class MvrPliService implements IMvrPliService {

	/** The mvr pli entity dao. */
	private MvrPliEntityDao mvrPliEntityDao;
	
	/**
	 * 根据多个参数查询始发偏线信息.
	 *
	 * @param mvrPliDto the mvr pli dto
	 * @param start the start
	 * @param limit the limit
	 * @return the mvr pli dto
	 * @author foss-pengzhen
	 * @date 2013-3-6 上午11:19:57
	 * @see com.deppon.foss.module.settlement.closing.api.server.service.IMvrAfiService#queryMvrAfiByConditions(com.deppon.foss.module.settlement.closing.api.shared.dto.MvrAfiDto)
	 */
	@Override
	public MvrPliDto queryMvrPliByConditions(MvrPliDto mvrPliDto,int start,int limit) {
		if(mvrPliDto == null){
			// 内部传参异常，请刷新界面后再尝试
			throw new SettlementException("内部传参异常，请刷新界面后再尝试！");
		}
		//根据多个参数查询始发偏线信息总数
		Long sum = mvrPliEntityDao.selectMvrPliByConditionsCount(mvrPliDto);
		//声明始发偏线往来对象集合
		List<MvrPliEntity> mvrPliEntities = null;
		//声明始发偏线往来对象
		MvrPliEntity mvrPliEntity = null;
		if(sum > 0){
			//调用始发、空运往返dao，根据多个参数查询始发偏线信息
			mvrPliEntities = mvrPliEntityDao.selectMvrPliByConditions(mvrPliDto,start,limit);
			//调用始发、空运往返dao，根据多个参数查询统计金额总和
			mvrPliEntity = mvrPliEntityDao.selectMvrPliByConditionsSum(mvrPliDto);
			mvrPliEntity.setPeriod("合计:");
			mvrPliEntity.setOrgType(sum.toString());
			mvrPliEntity.setOrgName("金额合计:");
			mvrPliEntities.add(mvrPliEntity);
		}
		
		// 设置始发偏线信息
		mvrPliDto.setMvrPliEntities(mvrPliEntities);
		mvrPliDto.setSum(sum);
		
		return mvrPliDto;
	}

	/**
	 * 导出始发偏线往来列表信息
	 * @author foss-pengzhen
	 * @date 2013-3-19 下午4:44:47
	 * @param billCashCashierConfirmDto
	 * @param currentInfo
	 * @return
	 * @see
	 */
	@SuppressWarnings("unused")
	@Override
	public ExportResource exportMvrPlis(MvrPliDto mvrPliDto,CurrentInfo currentInfo){
		//查询数据
		List<MvrPliEntity> mvrPliEntities = mvrPliEntityDao.selectMvrPliByConditions(mvrPliDto);
		//结果集为空，不能做导出操作！
		if(CollectionUtils.isEmpty(mvrPliEntities)){
			throw new SettlementException("结果集为空，不能做导出操作！");
		}
		
		//数据字典转换
		List<String> types = new ArrayList<String>();
		types.add(DictionaryConstants.MVRRFI_VOUCHER__ORG_TYPE);// 始发到达往来月报表出发/到达
		// 获取全部待转换缓存
		Map<String, Map<String, Object>> map = SettlementUtil
				.getDataDictionaryByTermsCodes(types);
		
		// 返回的结果集
		List<List<String>> resultList = new ArrayList<List<String>>();
		String[] columns = exportMvrPliEntity();
		if (CollectionUtils.isNotEmpty(mvrPliEntities)) {
			// 列数据
			List<String> colList = null;
			Object fieldValue = null;
			String cellValue = null;
			// 循环结果集
			for (MvrPliEntity entity : mvrPliEntities) {
				colList = new ArrayList<String>();
				// 循环列
				for (String column : columns) {
					// 获取对象的值，如果为空，则设置其为空字符串
					fieldValue = ReflectionUtils.getFieldValue(entity, column);
					cellValue = (fieldValue == null ? "" : fieldValue
							.toString());
					/*if(StringUtils.equals(column, "orgType")){
						cellValue = SettlementUtil.getDataDictionaryByTermsName(map,DictionaryConstants.MVRRFI_VOUCHER__ORG_TYPE,cellValue);
						}*/
					if(StringUtils.equals(column, "orgType")){
						cellValue = (SettlementDictionaryConstants.VOUCHER_ORG_TYPE_ORIG.equals(fieldValue)?"始发":"到达");
					}
					colList.add(cellValue);
				}
				resultList.add(colList);
			}
		}
		
		ExportResource sheet = new ExportResource();
		sheet.setHeads(mvrPliEntityHeads());
		
		sheet.setHeaderHeight(2);
		List<HeaderRows> headerList = new ArrayList<HeaderRows>();
		HeaderRows rowContent1 = new HeaderRows(SettlementReportNumber.ZERO,SettlementReportNumber.ZERO,SettlementReportNumber.ZERO,SettlementReportNumber.FIVE,"数据统计维度");
		HeaderRows rowContent2 = new HeaderRows(SettlementReportNumber.ZERO,SettlementReportNumber.ZERO,SettlementReportNumber.SIX,SettlementReportNumber.SEVEN,"偏线代理成本");
		HeaderRows rowContent3 = new HeaderRows(SettlementReportNumber.ZERO,SettlementReportNumber.ZERO, SettlementReportNumber.EIGHT, SettlementReportNumber.ELEVEN,"还款运单总运费（到付）");
		HeaderRows rowContent4 = new HeaderRows(SettlementReportNumber.ZERO,SettlementReportNumber.ZERO, SettlementReportNumber.TWELVE, SettlementReportNumber.THIRTEEN,"预收偏线代理");
	
		headerList.add(rowContent1);
		headerList.add(rowContent2);
		headerList.add(rowContent3);
		headerList.add(rowContent4);
		sheet.setHeaderList(headerList);
		sheet.setRowList(resultList);
		
		return sheet;
	}
	
	/**
	 * excel heads
	 * @author foss-pengzhen
	 * @date 2013-3-20 下午5:14:41
	 * @return
	 * @see
	 */
	public String[] mvrPliEntityHeads(){
		String[] heads = { "期间","客户编码", "客户名称",  "部门类型","部门编码", "部门名称", 
				"应付偏线代理成本冲应收到付运费已签收",
				"应付偏线代理成本冲应收到付运费未签收", 
				
				"还款现金未签收",
				"还款银行未签收", 
				"还款现金已签收", 
				"还款银行已签收",
				
				"预收偏线代理冲应收到付运费已签收", 
				"预收偏线代理冲应收到付运费未签收"};
		return heads;
	}
	
	/**
	 * 导出始发偏线往来列表信息
	 * @author foss-pengzhen
	 * @date 2013-3-19 下午4:44:47
	 * @param billCashCashierConfirmDto
	 * @param currentInfo
	 * @return
	 * @see
	 */
	public String[] exportMvrPliEntity(){
//		 "期间",  "始发/到达","客户编码", "客户名称", "部门编码", "部门名称", 
//				"应付偏线代理成本冲应收到付运费已签收",
//				"应付偏线代理成本冲应收到付运费未签收", 
//				"还款现金未签收",
//				"还款银行未签收", 
//				"还款现金已签收", 
//				"还款银行已签收",				
//				"预收偏线代理冲应收到付运费已签收", 
//				"预收偏线代理冲应收到付运费未签收""
		String[] columns = { "period","customerCode","customerName", "orgType", "orgCode","orgName",
				"plCostWoDestRcvPod", 
				"plCostWoDestRcvNpod", 
				
				"urDestChNpod",
				"urDestCdNpod",
				"urDestChPod", 
				"urDestCdPod",
				
				"plDrWoDestRcvPod", 
				"plDrWoDestRcvNpod"};
		return columns;
	}
	
	/**
	 * Gets the mvr pli entity dao.
	 *
	 * @return  the mvrPliEntityDao
	 */
	public MvrPliEntityDao getMvrPliEntityDao() {
		return mvrPliEntityDao;
	}

	
	/**
	 * Sets the mvr pli entity dao.
	 *
	 * @param mvrPliEntityDao the mvrPliEntityDao to set
	 */
	public void setMvrPliEntityDao(MvrPliEntityDao mvrPliEntityDao) {
		this.mvrPliEntityDao = mvrPliEntityDao;
	}

	
}

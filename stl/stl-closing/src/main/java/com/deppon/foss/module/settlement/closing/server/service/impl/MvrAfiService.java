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
import com.deppon.foss.module.settlement.closing.api.server.dao.IMvrAfiEntityDao;
import com.deppon.foss.module.settlement.closing.api.server.service.IMvrAfiService;
import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrAfiEntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrAfiDto;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementReportNumber;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.common.server.util.SettlementUtil;

/**
 * 始发、空运往来service.
 *
 * @author foss-pengzhen
 * @date 2013-3-6 上午11:19:07
 * @since
 * @version
 */
public class MvrAfiService implements IMvrAfiService {

	/** 注入始发空运往来dao. */
	private IMvrAfiEntityDao mvrAfiEntityDao;
	
	/**
	 * 根据多个参数查询始发空运信息.
	 *
	 * @param mvrAfiDto the mvr afi dto
	 * @param start the start
	 * @param limit the limit
	 * @return the mvr afi dto
	 * @author foss-pengzhen
	 * @date 2013-3-6 上午11:19:57
	 * @see com.deppon.foss.module.settlement.closing.api.server.service.IMvrAfiService#queryMvrAfiByConditions(com.deppon.foss.module.settlement.closing.api.shared.dto.MvrAfiDto)
	 */
	@Override
	public MvrAfiDto queryMvrAfiByConditions(MvrAfiDto mvrAfiDto,int start,int limit) {
		if(mvrAfiDto == null){
			throw new SettlementException("内部传参异常，请刷新界面后再尝试！");
		}
		//根据多个参数查询始发偏线信息
		Long sum = mvrAfiEntityDao.selectMvrAfiByConditionsCount(mvrAfiDto);
		List<MvrAfiEntity> mvrAfiEntities = null;
		MvrAfiEntity mvrAfiEntity = null;
		if(sum > 0){
			//调用始发、空运往返dao
			mvrAfiEntities = mvrAfiEntityDao.selectMvrAfiByConditions(mvrAfiDto,start,limit);
			
			//统计每列金额的总数
			mvrAfiEntity = mvrAfiEntityDao.selectMvrAfiByConditionsSum(mvrAfiDto);
			mvrAfiEntity.setCustomerCode("合计：");
			mvrAfiEntity.setCustomerName(sum.toString());
			mvrAfiEntity.setOrgName("金额合计:");
			mvrAfiEntities.add(mvrAfiEntity);
		}
		// 设置始发空运信息集合
		mvrAfiDto.setMvrAfiEntities(mvrAfiEntities);
		//总条数
		mvrAfiDto.setSum(sum);
		
		return mvrAfiDto;
	}
	
	/**
	 * 导出始发空运往来列表信息
	 * @author foss-pengzhen
	 * @date 2013-3-19 下午4:44:47
	 * @param billCashCashierConfirmDto
	 * @param currentInfo
	 * @return
	 * @see
	 */
	@Override
	public ExportResource exportMvrAfis(MvrAfiDto mvrAfiDto,CurrentInfo currentInfo){
		//查询数据
		List<MvrAfiEntity> mvrAfiEntities = mvrAfiEntityDao.selectMvrAfiByConditions(mvrAfiDto);
		//结果集为空，不能做导出操作！
		if(CollectionUtils.isEmpty(mvrAfiEntities)){
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
		String[] columns = exportMvrAfiEntity();
		if (CollectionUtils.isNotEmpty(mvrAfiEntities)) {
			// 列数据
			List<String> colList = null;
			Object fieldValue = null;
			String cellValue = null;
			// 循环结果集
			for (MvrAfiEntity entity : mvrAfiEntities) {
				colList = new ArrayList<String>();
				// 循环列
				for (String column : columns) {
					// 获取对象的值，如果为空，则设置其为空字符串
					fieldValue = ReflectionUtils.getFieldValue(entity, column);
					cellValue = (fieldValue == null ? "" : fieldValue.toString());
					if(StringUtils.equals(column, "orgType") && fieldValue!=null){
						cellValue = (fieldValue.equals(SettlementDictionaryConstants.VOUCHER_ORG_TYPE_ORIG)?"始发":"到达");
					}
					colList.add(cellValue);
				}
				resultList.add(colList);
			}
		}
		ExportResource sheet = new ExportResource();
		sheet.setHeads(mvrAfiEntityHeads());
		sheet.setHeaderHeight(2);
		List<HeaderRows> headerList = new ArrayList<HeaderRows>();
		HeaderRows rowContent1 = new HeaderRows(SettlementReportNumber.ZERO,SettlementReportNumber.ZERO,SettlementReportNumber.ZERO,SettlementReportNumber.FIVE,"数据统计维度");
		HeaderRows rowContent2 = new HeaderRows(SettlementReportNumber.ZERO,SettlementReportNumber.ZERO,SettlementReportNumber.SIX,SettlementReportNumber.NINE,"空运应付冲应收");
		HeaderRows rowContent3 = new HeaderRows(SettlementReportNumber.ZERO,SettlementReportNumber.ZERO,SettlementReportNumber.TEN, SettlementReportNumber.SEVENTEEN,"空运代收货款");
		HeaderRows rowContent4 = new HeaderRows(SettlementReportNumber.ZERO,SettlementReportNumber.ZERO,SettlementReportNumber.EIGHTEEN, SettlementReportNumber.TWENTY,"预收空运代理");
		HeaderRows rowContent5 = new HeaderRows(SettlementReportNumber.ZERO,SettlementReportNumber.ZERO, SettlementReportNumber.TWENTY_ONE, SettlementReportNumber.TWENTY_FOUR,"还款运单总运费（到付）");
		headerList.add(rowContent1);
		headerList.add(rowContent2);
		headerList.add(rowContent3);
		headerList.add(rowContent4);
		headerList.add(rowContent5);
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
	public String[] mvrAfiEntityHeads(){
		String[] heads = {"期间", "客户编码", "客户名称","部门类型", "部门编码", "部门名称", 
				
				"应付到达代理成本冲应收到付运费已签收",
				"应付到达代理成本冲应收到付运费未签收", 
				"其他应付冲应收到付运费已签收",
				"其他应付冲应收到付运费未签收", 
				
				"空运签收时未核销代收货款", 
				"空运反签收时未核销代收货款", 
				"空运还款代收货款现金已签收",
				"空运还款代收货款银行已签收", 
				"空运签收时已核销代收货款",
				"空运反签收时已核销代收货款",
				"空运到达代理应付冲应收代收货款已签收",
				"空运其他应付冲应收代收货款已签收", 
				
				"预收空运代理冲应收到付运费已签收",
				"预收空运代理冲应收到付运费未签收", 
				"预收空运代理冲应收代收货款已签收",
				
				"还款现金未签收",
				"还款银行未签收", 
				"还款现金已签收", 
				"还款银行已签收"};
		return heads;
	}
	
	/**
	 * 导出始发空运往来列表信息
	 * @author foss-pengzhen
	 * @date 2013-3-19 下午4:44:47
	 * @param billCashCashierConfirmDto
	 * @param currentInfo
	 * @return
	 * @see
	 */
	public String[] exportMvrAfiEntity(){
		String[] columns = { "period","customerCode","customerName",  "orgType", "orgCode", "orgName",
				"airPrAgencyWoDestRcvPod", 
				"airPrAgencyWoDestRcvNpod", 
				"airPrOtWoDestRcvPod", 
				"airPrOthWoDestRcvNPod",
				
				"airCodPodNwoCod", 
				"airCodNpodNwoCod", 
				"airCodChPod", 
				"airCodCdPod",
				"airCodPodWoCod", 
				"airCodNpodWoCod",
				"airCodWoAgencyPayPod", 
				"airCodWoOthPayCod",
				
				"airDrDestRcvPod", 
				"airDrDestRcvNpod",
				"airDrWoCodRcvPod", 
				
				"urDestChNpod",
				"urDestCdNpod", 
				"urDestChPod", 
				"urDestCdPod"};
		return columns;
	}
	
	/**
	 * Gets the mvr afi entity dao.
	 *
	 * @return  the mvrAfiEntityDao
	 */
	public IMvrAfiEntityDao getMvrAfiEntityDao() {
		return mvrAfiEntityDao;
	}

	
	/**
	 * Sets the mvr afi entity dao.
	 *
	 * @param mvrAfiEntityDao the mvrAfiEntityDao to set
	 */
	public void setMvrAfiEntityDao(IMvrAfiEntityDao mvrAfiEntityDao) {
		this.mvrAfiEntityDao = mvrAfiEntityDao;
	}

}

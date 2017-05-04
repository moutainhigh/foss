package com.deppon.foss.module.settlement.closing.server.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.framework.server.components.export.excel.HeaderRows;
import com.deppon.foss.framework.shared.util.classes.ReflectionUtils;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.closing.api.server.dao.IMvrLdiEntityDao;
import com.deppon.foss.module.settlement.closing.api.server.service.IMvrLdiService;
import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrLdiEntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrLdiDto;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementReportNumber;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.common.server.util.SettlementUtil;

/**
 * 快递代理往来service.
 *
 * @author foss-pengzhen
 * @date 2013-3-6 上午11:19:07
 * @since
 * @version
 */
public class MvrLdiService implements IMvrLdiService {

	/** 注入快递代理往来dao. */
	private IMvrLdiEntityDao MvrLdiEntityDao;
	
	/**
	 * 根据多个参数查询快递代理信息.
	 *
	 * @param MvrLdiDto the mvr afi dto
	 * @param start the start
	 * @param limit the limit
	 * @return the mvr afi dto
	 * @author foss-pengzhen
	 * @date 2013-3-6 上午11:19:57
	 * @see com.deppon.foss.module.settlement.closing.api.server.service.IMvrLdiService#queryMvrLdiByConditions(com.deppon.foss.module.settlement.closing.api.shared.dto.MvrLdiDto)
	 */
	@Override
	public List<MvrLdiEntity> queryMvrLdiByConditions(MvrLdiDto mvrLdiDto,int start,int limit) {
		if(mvrLdiDto == null){
			throw new SettlementException("内部传参异常，请刷新界面后再尝试！");
		}
		RowBounds rb = new RowBounds();
		List<MvrLdiEntity> mvrLdiDtoList = MvrLdiEntityDao.selectByConditions(rb,mvrLdiDto);
		return mvrLdiDtoList;
	}
	
	/**
	 * 查询总条数
	 * @author 045738-foss-maojianqiang
	 * @date 2013-8-22 下午4:15:41
	 * @param MvrLdiDto
	 * @return
	 * @see com.deppon.foss.module.settlement.closing.api.server.service.IMvrLdiService#queryTotalCounts(com.deppon.foss.module.settlement.closing.api.shared.dto.MvrLdiDto)
	 */
	public MvrLdiDto queryTotalCounts(MvrLdiDto mvrLdiDto) {
		if(mvrLdiDto == null){
			throw new SettlementException("内部传参异常，请刷新界面后再尝试！");
		}
		
		return MvrLdiEntityDao.queryTotalCounts(mvrLdiDto);
	}
	/**
	 * 导出快递代理往来列表信息
	 * @author foss-pengzhen
	 * @date 2013-3-19 下午4:44:47
	 * @param billCashCashierConfirmDto
	 * @param currentInfo
	 * @return
	 * @see
	 */
	@Override
	public ExportResource exportMvrLdis(MvrLdiDto MvrLdiDto,CurrentInfo currentInfo){
		RowBounds rb = new RowBounds(0,SettlementConstants.EXPORT_EXCEL_MAX_COUNTS);
		//查询数据
		List<MvrLdiEntity> MvrLdiDtoList = MvrLdiEntityDao.selectByConditions(rb,MvrLdiDto);
		//结果集为空，不能做导出操作！
		if(CollectionUtils.isEmpty(MvrLdiDtoList)){
			throw new SettlementException("结果集为空，不能做导出操作！");
		}
		
		//数据字典转换
		List<String> types = new ArrayList<String>();
		types.add(DictionaryConstants.MVRRFI_VOUCHER__ORG_TYPE);// 始发到达往来月报表出发/到达
		// 获取全部待转换缓存
		@SuppressWarnings("unused")
		Map<String, Map<String, Object>> map = SettlementUtil
				.getDataDictionaryByTermsCodes(types);
		
		// 返回的结果集
		List<List<String>> resultList = new ArrayList<List<String>>();
		String[] columns = exportMvrLdiEntity();
		if (CollectionUtils.isNotEmpty(MvrLdiDtoList)) {
			// 列数据
			List<String> colList = null;
			Object fieldValue = null;
			String cellValue = null;
			// 循环结果集
			for (MvrLdiEntity entity : MvrLdiDtoList) {
				colList = new ArrayList<String>();
				// 循环列
				for (String column : columns) {
					// 获取对象的值，如果为空，则设置其为空字符串
					fieldValue = ReflectionUtils.getFieldValue(entity, column);
					cellValue = (fieldValue == null ? "" : fieldValue.toString());
					if(StringUtils.equals(column, "orgType") && fieldValue != null){
						cellValue = (fieldValue.equals(SettlementDictionaryConstants.VOUCHER_ORG_TYPE_ORIG)?"始发":"到达");
					}
					colList.add(cellValue);
				}
				resultList.add(colList);
			}
		}
		ExportResource sheet = new ExportResource();
		sheet.setHeads(MvrLdiEntityHeads());
		sheet.setHeaderHeight(2);
		List<HeaderRows> headerList = new ArrayList<HeaderRows>();
		HeaderRows rowContent1 = new HeaderRows(SettlementReportNumber.ZERO,SettlementReportNumber.ZERO,SettlementReportNumber.ZERO,SettlementReportNumber.FIVE,"数据统计维度");
		HeaderRows rowContent2 = new HeaderRows(SettlementReportNumber.ZERO,SettlementReportNumber.ZERO,SettlementReportNumber.SIX,SettlementReportNumber.NINE,"快递代理应付冲应收");
		HeaderRows rowContent3 = new HeaderRows(SettlementReportNumber.ZERO,SettlementReportNumber.ZERO,SettlementReportNumber.TEN, SettlementReportNumber.SEVENTEEN,"快递代理代收货款");
		HeaderRows rowContent4 = new HeaderRows(SettlementReportNumber.ZERO,SettlementReportNumber.ZERO,SettlementReportNumber.EIGHTEEN, SettlementReportNumber.TWENTY,"预收快递代理");
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
	public String[] MvrLdiEntityHeads(){
		String[] heads = {"期间", "客户编码", "客户名称","部门类型", "部门编码", "部门名称", 
				"快递代理签收时未核销代收货款",
				"快递代理反签收时未核销代收货款", 
				"快递代理还款代收货款现金已签收",
				"快递代理还款代收货款银行已签收", 
				"快递代理签收时已核销代收货款", 
				"快递代理反签收时已核销代收货款", 
				"快递代理应付冲应收代收货款已签收",
				"快递代理其他应付冲应收代收货款已签收", 
				"还款现金未签收",
				"还款银行未签收",
				"还款现金已签收",
				"还款银行已签收", 
				"应付到达代理成本冲应收到付运费已签收",
				"应付到达代理成本冲应收到付运费未签收", 
				"其他应付冲应收到付运费已签收",
				"其他应付冲应收到付运费未签收",
				"预收快递代理冲应收到付运费已签收", 
				"预收快递代理冲应收到付运费未签收", 
				"预收快递代理冲应收代收货款已签收"};
		return heads;
	}
	
	/**
	 * 导出快递代理往来列表信息
	 * @author foss-pengzhen
	 * @date 2013-3-19 下午4:44:47
	 * @param billCashCashierConfirmDto
	 * @param currentInfo
	 * @return
	 * @see
	 */
	public String[] exportMvrLdiEntity(){
		String[] columns = { "period","customerCode","customerName",  "orgType", "orgCode", "orgName",
				"landCodPodNwoCod",
				"landCodNpodNwoCod",
				"landCodChPod",
				"landCodCdPod",
				"landCodPodWoCod",
				"landCodNpodWoCod",
				"landCodWoAgencyPayPod",
				"landCodWoOthPayCod",
				"landUrDestChNpod",
				"landUrDestCdNpod",
				"landUrDestChPod",
				"landUrDestCdPod",
				"landPrAgencyWoDestRcvPod",
				"landPrAgencyWoDestRcvNp",
				"landPrOtWoDestRcvPod",
				"landPrOthWoDestRcvNpod",
				"landDrDestRcvPod",
				"landDrDestRcvNpod",
				"landDrWoCodRcvPod"};
		return columns;
	}
	
	/**
	 * Gets the mvr afi entity dao.
	 *
	 * @return  the MvrLdiEntityDao
	 */
	public IMvrLdiEntityDao getMvrLdiEntityDao() {
		return MvrLdiEntityDao;
	}

	
	/**
	 * Sets the mvr afi entity dao.
	 *
	 * @param MvrLdiEntityDao the MvrLdiEntityDao to set
	 */
	public void setMvrLdiEntityDao(IMvrLdiEntityDao MvrLdiEntityDao) {
		this.MvrLdiEntityDao = MvrLdiEntityDao;
	}

	

}

package com.deppon.foss.module.settlement.pay.server.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.deppon.foss.framework.server.components.export.excel.ExcelExport;
import com.deppon.foss.framework.server.components.export.excel.SheetData;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.server.service.IBillDepositReceivedService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillDepositReceivedEntity;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.common.server.util.SettlementUtil;
import com.deppon.foss.module.settlement.pay.api.server.dao.IBillDepositReceivedUnverifyDao;
import com.deppon.foss.module.settlement.pay.api.server.service.IBillDepositReceivedUnverifyService;
import com.deppon.foss.module.settlement.pay.api.shared.dto.BillDepositReceivedPayDto;
import com.deppon.foss.util.DateUtils;

/**
 * 未核销预收账款报表service
 * @author foss-pengzhen
 * @date 2012-11-19 下午4:29:16
 * @since
 * @version
 */
public class BillDepositReceivedUnverifyService implements IBillDepositReceivedUnverifyService{

	/**
	 * 预收单接口
	 */
	private IBillDepositReceivedUnverifyDao billDepositReceivedUnverifyDao;

	/**
	 * 预收单公共接口
	 */
	private IBillDepositReceivedService billDepositReceivedService;
	
	/**
	 * 查询对应的预收单信息
	 * @author foss-pengzhen
	 * @date 2012-11-19 下午3:47:47
	 * @return
	 * @see
	 */
	public BillDepositReceivedPayDto queryDepositReceived(
			BillDepositReceivedPayDto billDepositReceivedPayDto,
			CurrentInfo currentInfo, int start, int limit) {
		//除会计外的用户只能查到本部门的预收单信息，会计可以查到管辖范围内部门的预收单信息
		List<BillDepositReceivedEntity> billDepositReceivedEntities = null;
		billDepositReceivedPayDto.setEmpCode(currentInfo.getEmpCode());
		String active = billDepositReceivedPayDto.getActive();
		BillDepositReceivedPayDto resultTotalDto = null;
		if(StringUtils.equals(billDepositReceivedPayDto.getQueryByTab(),SettlementConstants.TAB_QUERY_BY_DATE)){
			resultTotalDto = billDepositReceivedUnverifyDao.queryDepositReceivedUnverifyParamsCount(billDepositReceivedPayDto);
			if( resultTotalDto!=null&&resultTotalDto.getTotalNum() > 0 ){
    			billDepositReceivedEntities = billDepositReceivedUnverifyDao
    					.queryDepositReceivedUnverifyParams(billDepositReceivedPayDto, start, limit);
    			billDepositReceivedPayDto.setTotalNum(resultTotalDto.getTotalNum());
			}
		}
		List<String> depositReceivedNos = billDepositReceivedPayDto.getDepositReceivedNos();
		if(StringUtils.equals(billDepositReceivedPayDto.getQueryByTab(),SettlementConstants.TAB_QUERY_BY_DEPOIST_RECEIVED_NO)){
			resultTotalDto = billDepositReceivedUnverifyDao.queryDepositReceivedNosCount(depositReceivedNos,currentInfo,active);
			if( resultTotalDto!=null&&resultTotalDto.getTotalNum() > 0 ){
				billDepositReceivedEntities = billDepositReceivedUnverifyDao
					.queryDepositReceivedNos(depositReceivedNos, currentInfo ,active, start, limit);
				billDepositReceivedPayDto.setTotalNum(resultTotalDto.getTotalNum());
			}
		}
		//用于返回的DTO
		if (CollectionUtils.isNotEmpty(billDepositReceivedEntities) && (resultTotalDto!=null&&resultTotalDto.getTotalNum() > 0)) {
			// 设置返回dto
			billDepositReceivedPayDto.setBillDepositReceivedEntityList(billDepositReceivedEntities);
			billDepositReceivedPayDto.setTotalAmount(resultTotalDto.getTotalAmount());
			billDepositReceivedPayDto.setTotalVerifyAmount(resultTotalDto.getTotalVerifyAmount());
			billDepositReceivedPayDto.setTotalUnverifyAmount(resultTotalDto.getTotalUnverifyAmount());
		}else{
			billDepositReceivedPayDto.setTotalAmount(null);
			billDepositReceivedPayDto.setTotalVerifyAmount(null);
			billDepositReceivedPayDto.setTotalUnverifyAmount(null);
			billDepositReceivedPayDto.setBillDepositReceivedEntityList(null);
		}
		
		return billDepositReceivedPayDto;
	}

	/**
	 * 导出预收单报表
	 * @author foss-pengzhen
	 * @date 2012-12-11 下午4:16:38
	 * @param billDepositReceivedPayDto
	 * @param currentInfo
	 * @see
	 */
	public HSSFWorkbook depositReceivedUnverifyListExport(BillDepositReceivedPayDto billDepositReceivedPayDto
			,CurrentInfo currentInfo){
		HSSFWorkbook work = null;
		if(billDepositReceivedPayDto != null){
			List<BillDepositReceivedEntity> billDepositReceivedEntities = null;
			billDepositReceivedPayDto.setEmpCode(currentInfo.getEmpCode());
			if(billDepositReceivedPayDto.getStartBusinessDate()!= null){
				// 业务结束时间
				Date dateTemp = DateUtils.addDayToDate(billDepositReceivedPayDto.getEndBusinessDate(), 1);
				billDepositReceivedPayDto.setEndBusinessDate(dateTemp);
				
    	    	billDepositReceivedEntities = billDepositReceivedUnverifyDao
    	    				.queryDepositReceivedUnverifyParam(billDepositReceivedPayDto);
			}
			
			if(StringUtils.isNotEmpty(billDepositReceivedPayDto.getDepositReceivedNo())){
				String[] depositReceivedNo = billDepositReceivedPayDto.getDepositReceivedNo().split(",");
				List<String> depositReceivedNos = Arrays.asList(depositReceivedNo);
				billDepositReceivedEntities = billDepositReceivedService
    					.queryByDepositReceivedNOs(depositReceivedNos, "");
			}
			// 查询到的结果集不为空时，执行导出操作
			if (CollectionUtils.isNotEmpty(billDepositReceivedEntities)) {
				if(billDepositReceivedEntities.size()>SettlementConstants.EXPORT_EXCEL_MAX_COUNTS){
					throw new SettlementException("每次最多只能导出"+
							SettlementConstants.EXPORT_EXCEL_MAX_COUNTS+"条数据,请重新查询并导出");
				}
				ExcelExport export = new ExcelExport();
				billDepositReceivedPayDto.setBillDepositReceivedEntityList(billDepositReceivedEntities);
				// 设置每次最多导出10万条数据
				work = export.exportExcel(
						fillSheetDataByDepositReceived(billDepositReceivedPayDto), "sheet", SettlementConstants.EXPORT_MAX_COUNT);
			} else {
				throw new SettlementException("导出数据为空！");
			}
		}
		return work;
	}
	
	/**
	 * 生成预收单的excel
	 * @author foss-pengzhen
	 * @date 2012-12-6 上午11:13:38
	 */
	private SheetData fillSheetDataByDepositReceived(
			BillDepositReceivedPayDto  dto) {
		SheetData sheetData = new SheetData();
		sheetData.setRowHeads(new String[] { "预收编号", "预收部门", "预收部门编码",
				 "客户名称", "客户编码", "业务日期", "单据状态", "金额", "已核销金额", "未核销金额",
				 "付款方式" , "制单人", "制单人编号","记账日期","版本号", 
				"是否有效版本","是否红单", "备注" });

		List<BillDepositReceivedEntity> lists = dto.getBillDepositReceivedEntityList();
		if(CollectionUtils.isNotEmpty(lists)){
			List<List<String>> rowList = new ArrayList<List<String>>();
			
			//声明字典集合
			List<String> termCodes = new ArrayList<String>();
			termCodes.add(DictionaryConstants.BILL_DEPOSIT_RECEIVED__STATUS);
			termCodes.add(DictionaryConstants.SETTLEMENT__PAYMENT_TYPE);
			termCodes.add(DictionaryConstants.FOSS_ACTIVE);
			termCodes.add(DictionaryConstants.SETTLEMENT__IS_RED_BACK);
			//后台获取数据字典对应的数据
			Map<String,Map<String,Object>> map = SettlementUtil.getDataDictionaryByTermsCodes(termCodes);
			
			for (BillDepositReceivedEntity entity : lists) {
				List<String> cellList = new ArrayList<String>();
				cellList.add(entity.getDepositReceivedNo());// 预收编号
				cellList.add(entity.getGeneratingOrgName());// 预收部门
				cellList.add(entity.getGeneratingOrgCode());//预收部门编码
				cellList.add(entity.getCustomerName());//客户名称
				cellList.add(entity.getCustomerCode());//客户编码
				cellList.add(DateUtils.convert(entity.getAccountDate(),
						DateUtils.DATE_TIME_FORMAT));//业务日期
				cellList.add(SettlementUtil.getDataDictionaryByTermsName(map,
						DictionaryConstants.BILL_DEPOSIT_RECEIVED__STATUS, 
						entity.getStatus()));//单据状态
				cellList.add(entity.getAmount() != null ? entity.getAmount() + "" : "");//金额
				cellList.add(entity.getVerifyAmount() != null ? entity.getVerifyAmount() + "" : "");//已核销金额
				cellList.add(entity.getUnverifyAmount() != null ? entity.getUnverifyAmount() + "" : ""
						);//未核销金额
				cellList.add(SettlementUtil.getDataDictionaryByTermsName(map,
						DictionaryConstants.SETTLEMENT__PAYMENT_TYPE,
						entity.getPaymentType()));//收款方式
				cellList.add(entity.getCreateUserName());//制单人
				cellList.add(entity.getCreateUserCode());//制单人编号
				cellList.add(DateUtils.convert(entity.getAccountDate(),
						DateUtils.DATE_TIME_FORMAT));//记账日期
				cellList.add(entity.getVersionNo().toString());//版本号
				cellList.add(SettlementUtil.getDataDictionaryByTermsName(map,
						DictionaryConstants.FOSS_ACTIVE,
						entity.getActive()));//是否有效版本
				cellList.add(SettlementUtil.getDataDictionaryByTermsName(map,
						DictionaryConstants.SETTLEMENT__IS_RED_BACK,
						entity.getIsRedBack()));//是否红单
				cellList.add(entity.getNotes());
				rowList.add(cellList);
			}
			sheetData.setRowList(rowList);
		}
		return sheetData;
	}

	
	/**
	 * @return  the billDepositReceivedUnverifyDao
	 */
	public IBillDepositReceivedUnverifyDao getBillDepositReceivedUnverifyDao() {
		return billDepositReceivedUnverifyDao;
	}

	
	/**
	 * @param billDepositReceivedUnverifyDao the billDepositReceivedUnverifyDao to set
	 */
	public void setBillDepositReceivedUnverifyDao(
			IBillDepositReceivedUnverifyDao billDepositReceivedUnverifyDao) {
		this.billDepositReceivedUnverifyDao = billDepositReceivedUnverifyDao;
	}

	
	/**
	 * @return  the billDepositReceivedService
	 */
	public IBillDepositReceivedService getBillDepositReceivedService() {
		return billDepositReceivedService;
	}

	
	/**
	 * @param billDepositReceivedService the billDepositReceivedService to set
	 */
	public void setBillDepositReceivedService(
			IBillDepositReceivedService billDepositReceivedService) {
		this.billDepositReceivedService = billDepositReceivedService;
	}	
	
	
}

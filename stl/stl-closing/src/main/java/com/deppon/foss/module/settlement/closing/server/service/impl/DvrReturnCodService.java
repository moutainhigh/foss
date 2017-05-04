package com.deppon.foss.module.settlement.closing.server.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.framework.shared.util.classes.ReflectionUtils;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.closing.api.server.dao.IDvrReturnCodDao;
import com.deppon.foss.module.settlement.closing.api.server.service.IDvrReturnCodService;
import com.deppon.foss.module.settlement.closing.api.shared.domain.DvrReturnCodEntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.DvrReturnCodDto;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.common.server.util.SettlementUtil;
import com.deppon.foss.util.DateUtils;


public class DvrReturnCodService implements IDvrReturnCodService {

	private IDvrReturnCodDao dvrReturnCodDao;
	
	/**
	 * 根据多个参数查询退代收货款信息
	 * @author foss-pengzhen
	 * @date 2013-4-3 上午11:17:17
	 * @param dvrReturnCodDto
	 * @return
	 * @see
	 */
	@Override
	public DvrReturnCodDto queryDvrReturnCodByConditions(
			DvrReturnCodDto dvrReturnCodDto, int start, int limit) {
		//内部异常，传入参数为空！
		if(null == dvrReturnCodDto){
			throw new SettlementException("内部异常，传入参数为空！");
		}
		//内部异常，传入参数中的期间为空！
		if(StringUtils.isEmpty(dvrReturnCodDto.getPeriod())){
			throw new SettlementException("内部异常，传入参数中的期间为空！");
		}
		Long totalNum = dvrReturnCodDao.queryDvrReturnCodByConditionCount(dvrReturnCodDto);
		
		//设置返回的数据到dto集合中
		if(totalNum.longValue() > 0){
			List<DvrReturnCodEntity> dvrReturnCodEntities = dvrReturnCodDao.
    				queryDvrReturnCodByConditions(dvrReturnCodDto, start, limit);
			int codAmount = 0; 
			//如果返回的数据不为空
			if(CollectionUtils.isNotEmpty(dvrReturnCodEntities)){
				for(DvrReturnCodEntity dvrReturnCodEntity:dvrReturnCodEntities){
					codAmount += dvrReturnCodEntity.getCodAmount();
				}
			}
			//在list集合最后加上一行总计
			DvrReturnCodEntity dvrReturnCodEntity = new DvrReturnCodEntity();
			dvrReturnCodEntity.setPeriod("合计");
			dvrReturnCodEntity.setPayableOrgCode(totalNum.toString());
			dvrReturnCodEntity.setCodAmount((long) codAmount);
			dvrReturnCodEntities.add(dvrReturnCodEntity);
    		dvrReturnCodDto.setDvrReturnCodEntities(dvrReturnCodEntities);
    		dvrReturnCodDto.setSum(totalNum);
		}
		return dvrReturnCodDto;
	}

	/**
	 * 导出退代收货款列表信息
	 * @author foss-pengzhen
	 * @date 2013-4-2 上午11:02:59
	 * @param dvrReturnCodDto
	 * @param currentInfo
	 * @return
	 * @see
	 */
	@Override
	public ExportResource exportDvrReturnCods(DvrReturnCodDto dvrReturnCodDto,
			CurrentInfo currentInfo) {
		//内部错误，传入参数为空，不能做导出操作！
		if(null == dvrReturnCodDto){
			throw new SettlementException("内部错误，传入参数为空，不能做导出操作！");
		}
		//查询库中最新数据
		List<DvrReturnCodEntity> dvrReturnCodEntities = dvrReturnCodDao.queryDvrReturnCodByConditions(dvrReturnCodDto);
		//结果集为空，不能做导出操作！
		if(CollectionUtils.isEmpty(dvrReturnCodEntities)){
			throw new SettlementException("结果集为空，不能做导出操作！");
		}
		// 返回的结果集
		List<List<String>> resultList = new ArrayList<List<String>>();
		String[] columns = exportDvrReturnCodEntity();
		// 列数据
		List<String> colList = null;
		Object fieldValue = null;
		String cellValue = null;
		
		//数据字典转换
		List<String> types = new ArrayList<String>();
		types.add(DictionaryConstants.COD__REFUND_PATH);// 代收货款退款路径
		// 获取全部待转换缓存
		Map<String, Map<String, Object>> map = SettlementUtil
				.getDataDictionaryByTermsCodes(types);
		
		// 循环结果集
		for (DvrReturnCodEntity entity : dvrReturnCodEntities) {
			colList = new ArrayList<String>();
			// 循环列
			for (String column : columns) {
				// 获取对象的值，如果为空，则设置其为空字符串
				fieldValue = ReflectionUtils.getFieldValue(entity, column);
				cellValue = (fieldValue == null ? "" : fieldValue
						.toString());
				//如果是日期类型
				if(StringUtils.equals(column, "paymentDate")){
					cellValue = DateUtils.convert(entity.getPaymentDate(),
							DateUtils.DATE_FORMAT);
				}
				//如果是业务类型
				if(StringUtils.equals(column, "productCode") && fieldValue!=null){
					//转换业务类型为中文导出
					if(StringUtils.equals(fieldValue.toString(), SettlementDictionaryConstants.COD__COD_TYPE__RETURN_1_DAY)){
						cellValue = SettlementConstants.COD__COD_TYPE__RETURN_1_DAY_NAME;
					}
					if(StringUtils.equals(fieldValue.toString(), SettlementConstants.COD__COD_TYPE__RETURN_R3RA_DAY_CODE)){
						cellValue = SettlementConstants.COD__COD_TYPE__RETURN_3_A_DAY_NAME;
					}
				}
				//如果是付款类型
				if(StringUtils.equals(column, "refundPath")){
					cellValue = SettlementUtil.getDataDictionaryByTermsName(map,DictionaryConstants.COD__REFUND_PATH,cellValue);
				}
				colList.add(cellValue);
			}
			resultList.add(colList);
		}
		ExportResource sheet = new ExportResource();
		sheet.setHeads(dvrReturnCodEntityHeads());
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
	public String[] dvrReturnCodEntityHeads(){
		String[] heads = {
            "付款日期","付款所属期间","应付部门编码","应付部门名称",
            "应付子公司","退款部门（固定值）","退款子公司（固定值）",
            "业务类型","银行账户","付款途径","金额"
        };
		return heads;
	}
	
	/**
	 * 导出退代收货款列表信息
	 * @author foss-pengzhen
	 * @date 2013-3-19 下午4:44:47
	 * @return
	 * @see
	 */
	public String[] exportDvrReturnCodEntity(){
		String[] columns = {"paymentDate","period","payableOrgCode",
				"payableOrgName","payableComName","returnOrg",
				"returnComOrg","productCode","bankAccount","refundPath","codAmount"};
		return columns;
	}
	
	/**
	 * @return  the dvrReturnCodDao
	 */
	public IDvrReturnCodDao getDvrReturnCodDao() {
		return dvrReturnCodDao;
	}

	
	/**
	 * @param dvrReturnCodDao the dvrReturnCodDao to set
	 */
	public void setDvrReturnCodDao(IDvrReturnCodDao dvrReturnCodDao) {
		this.dvrReturnCodDao = dvrReturnCodDao;
	}

}

package com.deppon.foss.module.settlement.closing.server.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.framework.shared.util.classes.ReflectionUtils;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.pricing.api.server.service.IProductService;
import com.deppon.foss.module.settlement.closing.api.server.dao.IDvdReturnCodDao;
import com.deppon.foss.module.settlement.closing.api.server.service.IDvdReturnCodService;
import com.deppon.foss.module.settlement.closing.api.shared.domain.DvdReturnCodEntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.DvdReturnCodDto;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.common.server.util.SettlementUtil;
import com.deppon.foss.util.DateUtils;

/**
 * 退代收货款明细
 * @author foss-pengzhen
 * @date 2013-4-11 上午9:27:32
 * @since
 * @version
 */
public class DvdReturnCodService implements IDvdReturnCodService {

	private IDvdReturnCodDao dvdReturnCodDao;
	
	/**
	 * 根据产品CODE找对应的名称
	 */
	private IProductService  productService;
	
	/**
	 * 根据多个参数查询退代收货款明细信息
	 * @author foss-pengzhen
	 * @date 2013-4-3 上午11:17:17
	 * @param dvdReturnCodDto
	 * @return
	 * @see
	 */
	@Override
	public DvdReturnCodDto queryDvdReturnCodByConditions(
			DvdReturnCodDto dvdReturnCodDto, int start, int limit) {
		//内部异常，传入参数为空！
		if(null == dvdReturnCodDto){
			throw new SettlementException("内部异常，传入参数为空！");
		}
		if(StringUtils.isNotEmpty(dvdReturnCodDto.getCodType())){
			//如果类型为三日退和审核退的时候，添加三日退和审核退的信息到类型集合中
			if(StringUtils.equals(dvdReturnCodDto.getCodType(), 
					SettlementConstants.COD__COD_TYPE__RETURN_R3RA_DAY_CODE)){
				//声明类型集合
				List<String> codTypes = new ArrayList<String>();
				//添加类型到集合中
				codTypes.add(SettlementDictionaryConstants.COD__COD_TYPE__RETURN_3_DAY);
				codTypes.add(SettlementDictionaryConstants.COD__COD_TYPE__RETURN_APPROVE);
				dvdReturnCodDto.setCodTypes(codTypes);
			}
			//如果类型为即日退的时候，添加即日退的信息到类型集合中
			if(StringUtils.equals(dvdReturnCodDto.getCodType(), 
					SettlementDictionaryConstants.COD__COD_TYPE__RETURN_1_DAY)){
				//声明类型集合
				List<String> codTypes = new ArrayList<String>();
				//添加类型到集合中
				codTypes.add(SettlementDictionaryConstants.COD__COD_TYPE__RETURN_1_DAY);
				dvdReturnCodDto.setCodTypes(codTypes);
			}
		}
		Date dateTemp = DateUtils.addDayToDate(dvdReturnCodDto.getEndDate(), 1);
		dvdReturnCodDto.setEndDate(dateTemp);
		Long totalNum = dvdReturnCodDao.queryDvdReturnCodByConditionCount(dvdReturnCodDto);
		
		//设置返回的数据到dto集合中
		if(totalNum.longValue() > 0){
			//调用dao层，查询相关数据
			List<DvdReturnCodEntity> dvdReturnCodEntities = dvdReturnCodDao.
    				queryDvdReturnCodByConditions(dvdReturnCodDto, start, limit);
			int codAmount = 0; 
			//如果返回的数据不为空
			if(CollectionUtils.isNotEmpty(dvdReturnCodEntities)){
				//循环累积退款金额
				for(DvdReturnCodEntity dvdReturnCodEntity : dvdReturnCodEntities){
					codAmount += dvdReturnCodEntity.getCodAmount();
				}
			}
			//在list集合最后加上一行总计
    		DvdReturnCodEntity dvdReturnCodEntity = new DvdReturnCodEntity();
    		dvdReturnCodEntity.setPeriod("合计");
    		dvdReturnCodEntity.setWaybillNo(totalNum.toString());
    		dvdReturnCodEntity.setCodAmount((long) codAmount);
    		dvdReturnCodEntities.add(dvdReturnCodEntity);
    		dvdReturnCodDto.setDvdReturnCodEntities(dvdReturnCodEntities);
    		dvdReturnCodDto.setSum(totalNum);
		}
		return dvdReturnCodDto;
	}

	/**
	 * 导出退代收货款列表明细信息
	 * @author foss-pengzhen
	 * @date 2013-4-2 上午11:02:59
	 * @param dvdReturnCodDto
	 * @param currentInfo
	 * @return
	 * @see
	 */
	@Override
	public ExportResource exportDvdReturnCods(DvdReturnCodDto dvdReturnCodDto,
			CurrentInfo currentInfo) {
		//内部错误，传入参数为空，不能做导出操作！
		if(null == dvdReturnCodDto){
			throw new SettlementException("内部错误，传入参数为空，不能做导出操作！");
		}
		if(StringUtils.isNotEmpty(dvdReturnCodDto.getCodType())){
			//如果类型为三日退和审核退的时候，添加三日退和审核退的信息到类型集合中
			if(StringUtils.equals(dvdReturnCodDto.getCodType(), 
					SettlementConstants.COD__COD_TYPE__RETURN_R3RA_DAY_CODE)){
				//声明类型集合
				List<String> codTypes = new ArrayList<String>();
				//添加类型到集合中
				codTypes.add(SettlementDictionaryConstants.COD__COD_TYPE__RETURN_3_DAY);
				codTypes.add(SettlementDictionaryConstants.COD__COD_TYPE__RETURN_APPROVE);
				dvdReturnCodDto.setCodTypes(codTypes);
			}
			//如果类型为即日退的时候，添加即日退的信息到类型集合中
			if(StringUtils.equals(dvdReturnCodDto.getCodType(), 
					SettlementDictionaryConstants.COD__COD_TYPE__RETURN_1_DAY)){
				//声明类型集合
				List<String> codTypes = new ArrayList<String>();
				//添加类型到集合中
				codTypes.add(SettlementDictionaryConstants.COD__COD_TYPE__RETURN_1_DAY);
				dvdReturnCodDto.setCodTypes(codTypes);
			}
		}
		Date dateTemp = DateUtils.addDayToDate(dvdReturnCodDto.getEndDate(), 1);
		dvdReturnCodDto.setEndDate(dateTemp);
		//查询库中最新数据
		List<DvdReturnCodEntity> dvdReturnCodEntities = dvdReturnCodDao.queryDvdReturnCodByConditions(dvdReturnCodDto);
		//结果集为空，不能做导出操作！
		if(CollectionUtils.isEmpty(dvdReturnCodEntities)){
			throw new SettlementException("结果集为空，不能做导出操作！");
		}
		// 返回的结果集
		List<List<String>> resultList = new ArrayList<List<String>>();
		String[] columns = exportDvdReturnCodEntity();
		// 列数据
		List<String> colList = null;
		Object fieldValue = null;
		String cellValue = null;
		
		//数据字典转换
		List<String> types=new ArrayList<String>();
		types.add(DictionaryConstants.COD__COD_TYPE);// 代收货款类型
		types.add(DictionaryConstants.COD__REFUND_PATH);// 代收货款退款路径
		types.add(DictionaryConstants.SETTLEMENT__CUSTOMER_TYPE);// 客户类型
		//获取全部待转换缓存
		Map<String,Map<String,Object>> map = SettlementUtil.getDataDictionaryByTermsCodes(types);
		
		// 循环结果集
		for (DvdReturnCodEntity entity : dvdReturnCodEntities) {
			colList = new ArrayList<String>();
			// 循环列
			for (String column : columns) {
				// 获取对象的值，如果为空，则设置其为空字符串
				fieldValue = ReflectionUtils.getFieldValue(entity, column);
				cellValue = (fieldValue == null ? "" : fieldValue
						.toString());
				//如果是运输性质
				if(StringUtils.equals(column, "productCode")){
					cellValue = productService.getProductByCache(entity.getProductCode(),new Date()).getName(); 
				}
				//如果是记账日期
				if(StringUtils.equals(column, "accountDate")){
					cellValue = DateUtils.convert(entity.getAccountDate(),
							DateUtils.DATE_FORMAT);
				}
				//如果是业务日期
				if(StringUtils.equals(column, "businessDate")){
					cellValue = DateUtils.convert(entity.getBusinessDate(),
							DateUtils.DATE_FORMAT);
				}
				//如果是签收日期
				if(StringUtils.equals(column, "signDate")){
					cellValue = DateUtils.convert(entity.getSignDate(),
							DateUtils.DATE_FORMAT);
				}
				//如果是付款日期
				if(StringUtils.equals(column, "paymentDate")){
					cellValue = DateUtils.convert(entity.getPaymentDate(),
							DateUtils.DATE_FORMAT);
				}
				//如果是业务类型
				if(StringUtils.equals(column, "codType")){
					//转换业务类型为中文导出
					cellValue = SettlementUtil.getDataDictionaryByTermsName(map,DictionaryConstants.COD__COD_TYPE,cellValue);
				}
				//如果是付款类型
				if(StringUtils.equals(column, "refundPath")){
					//转换付款类型为中文导出
					cellValue = SettlementUtil.getDataDictionaryByTermsName(map,DictionaryConstants.COD__REFUND_PATH,cellValue);
				}//如果是客户类型
				if(StringUtils.equals(column, "customerType")){
					//转换客户类型为中文导出
					cellValue = SettlementUtil.getDataDictionaryByTermsName(map,DictionaryConstants.SETTLEMENT__CUSTOMER_TYPE,cellValue);
				}
				colList.add(cellValue);
			}
			resultList.add(colList);
		}
		ExportResource sheet = new ExportResource();
		sheet.setHeads(dvdReturnCodEntityHeads());
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
	public String[] dvdReturnCodEntityHeads(){
		String[] heads = {
            "付款所属期间","运单号","运输性质","应付部门编码","应付部门名称","应付子公司编码","应付子公司名称",
            "代收货款类型","发货客户编码","客户类型","发货客户名称/代理名称","收款人","收款人银行帐号",
            "开户行编码","开户行名称","记账日期","业务日期","签收日期","付款日期",
            "退款部门（固定值）","退款子公司（固定值）","付款途径","银行账户","退款金额"
        };
		return heads;
	}
	
	/**
	 * 导出始发空运往来列表信息
	 * @author foss-pengzhen
	 * @date 2013-3-19 下午4:44:47
	 * @return
	 * @see
	 */
	public String[] exportDvdReturnCodEntity(){
		String[] columns = {
				//付款所属期间
				"period",
				/*运单号*/
                "waybillNo",
                /*运输性质*/
                "productCode",
                /*应付部门编码*/
                "payableOrgCode",
                /*应付部门名称*/
                "payableOrgName",
                /*应付子公司编码*/
                "payableComCode",
                /*应付子公司名称*/
                "payableComName",
                /*代收货款类型*/
                "codType",
                /*发货客户编码*/
                "customerCode",
                /*客户类型*/
                "customerType",
                /*发货客户名称/代理名称*/
                "customerName",
                /*收款人*/
                "payeeName",
                /*收款人银行帐号*/
                "payeeAccount",
                /*开户行编码*/
                "bankHqCode",
                /*开户行名称*/
                "bankHqName",
                /*记账日期*/
                "accountDate",
                /*业务日期*/
                "businessDate",
                /*签收日期*/
                "signDate",
                /*付款日期*/
                "paymentDate",
                /*退款部门（固定值）*/
                "returnOrg",
                /*退款子公司（固定值）*/
                "returnComOrg",
                /*退款路径*/
                "refundPath",
                "comAccount",
                /*退款金额*/
                "codAmount"
                };
		return columns;
	}
	
	/**
	 * @return  the dvdReturnCodDao
	 */
	public IDvdReturnCodDao getDvdReturnCodDao() {
		return dvdReturnCodDao;
	}

	
	/**
	 * @param dvdReturnCodDao the dvdReturnCodDao to set
	 */
	public void setDvdReturnCodDao(IDvdReturnCodDao dvdReturnCodDao) {
		this.dvdReturnCodDao = dvdReturnCodDao;
	}

	/**
	 * @param productService
	 */
	public void setProductService(IProductService productService) {
		this.productService = productService;
	}

	
}

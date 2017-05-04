package com.deppon.foss.module.settlement.consumer.server.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.ProductEntityConstants;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.consumer.api.server.dao.IInvoiceRegisterDao;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.InvoiceManagementAddDto;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.WaybillInvoiceDto;
import com.deppon.foss.util.define.FossConstants;


/**
 * 配合发票，查询运单及小票接口实现.
 *
 * @author 163576
 * @date 2014-7-9 10:42:20
 */
public class InvoiceRegisterDao extends iBatis3DaoImpl implements IInvoiceRegisterDao {

	/** The Constant NAMESPACE. */
	public static final String NAMESPACE = "foss.stl.InvoiceRegisterDao.";
	
	/** 日志. */
	private static final Logger LOGGER = LoggerFactory.getLogger(InvoiceRegisterDao.class);

	/**
	 * 查询运单集合
	 * @author 163576
	 * @date 2014-7-9 10:42:20
	 * @param waybillNos
	 * @return
	 */
	@Override
	public List<WaybillInvoiceDto> queryWaybillInfoForInvoiceList(
			List<String> waybillNos) {
		if(CollectionUtils.isEmpty(waybillNos)){
			LOGGER.error("内部错误，参数为空！");
			throw new SettlementException("内部错误，参数为空！");
		}
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("waybillNos", waybillNos);	
		paramMap.put("package", ProductEntityConstants.PRICING_PRODUCT_EXPRESS_PACKAGE);
		paramMap.put("active", FossConstants.ACTIVE);
		paramMap.put("statusList", Arrays.asList(WaybillConstants.OBSOLETE,WaybillConstants.ABORTED));
		paramMap.put("pendingTypeList", Arrays.asList(WaybillConstants.WAYBILL_STATUS_PDA_PENDING,WaybillConstants.WAYBILL_STATUS_PC_PENDING));
		
		@SuppressWarnings("unchecked")
		List<WaybillInvoiceDto> list = this.getSqlSession().selectList(NAMESPACE + 
												"queryWaybillInfoForInvoiceList",paramMap);
		return list;
	}

	/**
	 * 查询小票集合及运单中包括小票集合
	 * @author 163576
	 * @date 2014-7-9 10:42:20
	 * @param waybillNos 与小票号两者至少必录其一
	 * @param otherRevenues 与运单号两者至少必录其一
	 * @return
	 */
	@Override
	public List<WaybillInvoiceDto> queryOtherRevenueInfoForInvoiceList(
			List<String> waybillNos, List<String> otherRevenues) {
		if(CollectionUtils.isEmpty(waybillNos) && CollectionUtils.isEmpty(otherRevenues)){
			LOGGER.error("内部错误，参数为空！");
			throw new SettlementException("内部错误，参数为空！");
		}
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		if(CollectionUtils.isNotEmpty(waybillNos)){
			paramMap.put("waybillNos", waybillNos);	
		}
		if(CollectionUtils.isNotEmpty(otherRevenues)){
			paramMap.put("otherRevenues", otherRevenues);
		}
		
		paramMap.put("active", FossConstants.ACTIVE);
		paramMap.put("disable", FossConstants.INACTIVE);
		@SuppressWarnings("unchecked")
		List<WaybillInvoiceDto> list = this.getSqlSession().selectList(NAMESPACE + 
														"queryOtherRevenueInfoForInvoiceList",paramMap);
		return list;
	}
	
	@Override
	public List<String> queryOtherRevenueNosByWaybillNos(List<String> waybillNos) {
		if(CollectionUtils.isEmpty(waybillNos)){
			LOGGER.error("内部错误，参数为空！");
			throw new SettlementException("内部错误，参数为空！");
		}
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("waybillNos", waybillNos);	
		
		@SuppressWarnings("unchecked")
		List<String> list = this.getSqlSession().selectList(NAMESPACE + "queryOtherRevenueNosByWaybillNos",paramMap);
		return list;
	}
	
	@Override
	public List<WaybillInvoiceDto> queryHhStatementInfoForInvoiceList(
			List<String> list) {
		// TODO Auto-generated method stub
		List<WaybillInvoiceDto> resultList=new ArrayList<WaybillInvoiceDto>();
		List<InvoiceManagementAddDto> tempList=this.getSqlSession().selectList("foss.stl.StatementCreatDao." + "queryHsByStatementNoForFims",list);
		if(list!=null&&list.size()!=0){
			for(InvoiceManagementAddDto temp:tempList){
				WaybillInvoiceDto dto=new WaybillInvoiceDto();
				//对账单号
				dto.setWaybillNo(temp.getStatementBillNo());
				//业务类型
				dto.setProduct(temp.getTransportProperties());
				//发票标记
				dto.setInvoiceMark(temp.getInvoiceMark());
				//预付金额
				dto.setPrePayAmount(new BigDecimal(temp.getTotleFee()));
				//到付金额
				dto.setToPayAmount(new BigDecimal(0));
				//发货方客户编码
				dto.setDeliverCustomerCode(temp.getCustomerCode());
				//发货部门编码
				dto.setReceiveOrgCode(temp.getSectorBenchmarkingEncoding());
				//开单日期
				dto.setBillTime(temp.getCreateTime());
				//是否一般纳税人
				if("Y".equals(temp.getIsGeneralTaxpayer())){
					dto.setIsGeneralTaxpayer("1");
				}else{
					dto.setIsGeneralTaxpayer("0");
				}
				//税务登记号
				dto.setTaxId(temp.getTaxId());
				//发票抬头
				dto.setInvoiceHeadCode(temp.getInvoiceHeadCode());
				//注册地址
				dto.setRegisteredAddress(temp.getRegisteredTelephone());
				//注册电话
				dto.setRegisteredTelephone(temp.getRegisteredTelephone());
				//开户行
				dto.setBank(temp.getBank());
				//开户银行帐号
				dto.setAccountBank(temp.getAccountBank());
				resultList.add(dto);
			}
		}
		return resultList;
	}
	/**
	 * add by 329757
	 * 根据运单号查询发货客户是否是事后折客户-快递
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<String> queryIsDisCountByWaybill(String waybillNo) {
		if(StringUtil.isBlank(waybillNo)){
			throw new SettlementException("运单号为空！");
		}
		return this.getSqlSession().selectList(NAMESPACE + "queryIsDisCountByWaybill",waybillNo);
	}

	/**
	 * add by 329757
	 * 根据运单号查询发货客户是否是事后折客户-零担
	 */
	@Override
	public int queryIsDisCountNoExp(String waybillNo) {
		if(StringUtil.isBlank(waybillNo)){
			throw new SettlementException("运单号为空！");
		}
		return (Integer) this.getSqlSession().selectOne(NAMESPACE + "queryIsDisCountNoExp",waybillNo);
	}

	/**
	 * 查询零担是否存在折扣应付单--零担
	 * add by 329757
	 */
	@Override
	public int queryDisCountNoexe(String waybillNo) {
		if(StringUtil.isBlank(waybillNo)){
			throw new SettlementException("运单号为空！");
		}
		return (Integer) this.getSqlSession().selectOne(NAMESPACE + "queryDisCountNoexe",waybillNo);
	}

	/**
	 * add by 329757
	 * 判断运客户编码是否存在折扣--快递
	 * @param waybillNo
	 * @return
	 */
	@Override
	public int queryDisCountExe(String waybillNo) {
		if(StringUtil.isBlank(waybillNo)){
			throw new SettlementException("运单号为空！");
		}
		return (Integer) this.getSqlSession().selectOne(NAMESPACE + "queryDisCountExe",waybillNo);
	}
}

package com.deppon.foss.module.settlement.pay.server.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.framework.shared.util.classes.ReflectionUtils;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.common.api.server.service.IBusinessLockService;
import com.deppon.foss.module.base.common.api.shared.define.MutexElementType;
import com.deppon.foss.module.base.common.api.shared.dto.MutexElement;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillDao;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.settlement.common.api.server.dao.IWaybillEntityForEcsDao;
import com.deppon.foss.module.settlement.common.api.server.service.IBillCashCollectionService;
import com.deppon.foss.module.settlement.common.api.server.service.IBillDepositReceivedService;
import com.deppon.foss.module.settlement.common.api.server.service.IBillPayableService;
import com.deppon.foss.module.settlement.common.api.server.service.IBillRepaymentService;
import com.deppon.foss.module.settlement.common.api.server.service.IQueryIsVehicleassembleForEcs;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillCashCollectionEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillDepositReceivedEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillRepaymentEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillCashCollectionDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillDepositReceivedEntityDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillPayableDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillRepaymentConditionDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillRepaymentDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.common.server.util.ListComparator;
import com.deppon.foss.module.settlement.common.server.util.SettlementUtil;
import com.deppon.foss.module.settlement.consumer.api.server.service.ICodAuditLogService;
import com.deppon.foss.module.settlement.consumer.api.server.service.ICodAuditService;
import com.deppon.foss.module.settlement.consumer.api.shared.domain.CodAuditEntity;
import com.deppon.foss.module.settlement.consumer.api.shared.domain.CodAuditLogEntity;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.CodAuditDto;
import com.deppon.foss.module.settlement.consumer.server.service.impl.LineSignService;
import com.deppon.foss.module.settlement.pay.api.server.dao.IBillCashCashierConfirmPayDao;
import com.deppon.foss.module.settlement.pay.api.server.service.IBillCashCashierConfirmPayService;
import com.deppon.foss.module.settlement.pay.api.shared.dto.BillCashCashierConfirmDto;
import com.deppon.foss.module.transfer.load.api.server.service.IVehicleAssembleBillService;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;
/**
 * 确认收银service
 * @author foss-pengzhen
 * @date 2012-12-13 下午2:50:05
 * @since
 * @version
 */
public class BillCashCashierConfirmPayService implements IBillCashCashierConfirmPayService{
	
	/** The Constant LOGGER. */
	private static final Logger LOGGER = LogManager.getLogger(LineSignService.class);

	public IBusinessLockService getBusinessLockService() {
		return businessLockService;
	}

	public void setBusinessLockService(IBusinessLockService businessLockService) {
		this.businessLockService = businessLockService;
	}

	/**
	 * 注入确认收银查询DAO
	 */
	private IBillCashCashierConfirmPayDao billCashCashierConfirmPayDao;
	
	/**
	 * 注入确认收银提交公共接口
	 */
	private IBillCashCollectionService billCashCollectionService;
	
	/**
	 * 注入还款单公共接口
	 */
	private IBillRepaymentService billRepaymentService;
	
	private IBillPayableService billPayableService;
	
	/**
	 * 注入预收单公共接口
	 */
	private IBillDepositReceivedService billDepositReceivedService;
	
	/**
	 * 注入部门是否存在的接口
	 */
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;

	/**
	 * 互斥锁接口
	 */
	private IBusinessLockService businessLockService;
     
	/**
	 * PDA 签收生效应付单锁
	 * */
	private static final String PDASIGN_ENABLEPAYABLE = "PDA签收生效应付单";
	
    /**
     * 查询第一次开单时候的代收金额
     * @return
     */
    private IWaybillDao waybillDao;
    
	/** 查询配载单记录的 */
	private IVehicleAssembleBillService vehicleAssembleBillService;
	
	/** 插入代收货款审核表 */
	private ICodAuditService codAuditService;
	
	/**
	 * 注入插入审核日志的 codAuditLogService
	 */
	private ICodAuditLogService codAuditLogService;
	
	/**
	 * 注入查询配载单接口
	 */
	private IQueryIsVehicleassembleForEcs queryIsVehicleassembleForEcs;
	
	/**
	 * 注入运单dao
	 */
	private IWaybillEntityForEcsDao waybillEntityForEcsDao;
	
	/**
	 * 确认收银时间参数查询
	 * @author foss-pengzhen
	 * @date 2012-12-13 下午2:50:36
	 * @param billCashCashierConfirmDto 确认收银Dto
	 * @param currentInfo 用户信息
	 * @return
	 * @see
	 */
	@Override
	public List<BillCashCashierConfirmDto> queryCashCashierConfirmPags(
			BillCashCashierConfirmDto billCashCashierConfirmDto,CurrentInfo currentInfo){
		if(billCashCashierConfirmDto == null){
			throw new SettlementException("内部异常，传入参数为空，不能查询收银确认数据!");
		}
		//设置查询状态为未收银状态
		billCashCashierConfirmDto.setStatus(SettlementDictionaryConstants.BILL_CASH_COLLECTION__STATUS__SUBMIT);
		//设置默认制单部门名称
		billCashCashierConfirmDto.setCollectionOrgCode(currentInfo.getCurrentDeptCode());
		
		billCashCashierConfirmDto.setEmpCode(currentInfo.getEmpCode());
		
		List<BillCashCashierConfirmDto> billCashCashierConfirmDtos = new ArrayList<BillCashCashierConfirmDto>();
		//预收单集合
		List<BillCashCashierConfirmDto> billDepositReceivedDtos = null;
		//现金收款单集合
		List<BillCashCashierConfirmDto> billCashCollectionDtos = null;
		//还款单集合
		List<BillCashCashierConfirmDto> billRepaymentDtos = null;
		
		if(StringUtils.equals(billCashCashierConfirmDto.getQueryByTab(),SettlementConstants.TAB_QUERY_BY_DATE)){
			//Date dateTemp = DateUtils.addDayToDate(billCashCashierConfirmDto.getEndBusinessDate(), 1);
			//billCashCashierConfirmDto.setEndBusinessDate(dateTemp);
			//获取现金收款单中未确认收银的数据
			if(StringUtils.equals(SettlementDictionaryConstants.CASH_COLLECTION_RPT_D__SOURCE_BILL_TYPE__CASH_COLLECTION
					,billCashCashierConfirmDto.getBillType())){
				billCashCollectionDtos = billCashCashierConfirmPayDao.queryCashCollectionPayParams(billCashCashierConfirmDto);
			}
			//获取还款单中未确认收银的数据
			else if(StringUtils.equals(SettlementDictionaryConstants.CASH_COLLECTION_RPT_D__SOURCE_BILL_TYPE__REPAYMENT
					,billCashCashierConfirmDto.getBillType())){
				billRepaymentDtos = billCashCashierConfirmPayDao.queryRepaymentPayParams(billCashCashierConfirmDto);
			}
			//获取预收单中未确认收银的数据
			else if(StringUtils.equals(SettlementDictionaryConstants.CASH_COLLECTION_RPT_D__SOURCE_BILL_TYPE__DEPOSIT_RECEIVED
					,billCashCashierConfirmDto.getBillType())){
				billDepositReceivedDtos = billCashCashierConfirmPayDao.queryDepositReceivedParams(billCashCashierConfirmDto);
			}else{
				billCashCashierConfirmDtos= billCashCashierConfirmPayDao.querybillCashCashierConfirmParams(billCashCashierConfirmDto);
			}
		}else if(StringUtils.equals(billCashCashierConfirmDto.getQueryByTab(),SettlementConstants.TAB_QUERY_BY_DR_CC_RP_NO)){
			String[] billNos = billCashCashierConfirmDto.getBillNo().split(",|;");
			List<String> billNoList = Arrays.asList(billNos);
			//收集单号，放入单号集合
			billCashCashierConfirmDto.setBillNos(billNoList);
			//获取未确认收银的数据
			billCashCollectionDtos = billCashCashierConfirmPayDao.querybillCashCashierConfirmNos(billCashCashierConfirmDto);
		}else if(StringUtils.equals(billCashCashierConfirmDto.getQueryByTab(),SettlementConstants.TAB_QUERY_BY_WAYBILL_NO)){
			String[] waybillNos = billCashCashierConfirmDto.getWaybillNo().split(",|;");
			List<String> wayBillNoList = Arrays.asList(waybillNos);
			//收集单号，放入单号集合
			billCashCashierConfirmDto.setWaybillNos(wayBillNoList);
			//获取未确认收银的数据
			billCashCollectionDtos = billCashCashierConfirmPayDao.querybillCashCashierConfirmWayBillNos(billCashCashierConfirmDto);
		}else if(StringUtils.equals(billCashCashierConfirmDto.getQueryByTab(),SettlementConstants.TAB_QUERY_BY_LANDSTOWAGE_NO)){
			String[] batchNos = billCashCashierConfirmDto.getBatchNo().split(",|;");
			List<String> batchNoList = Arrays.asList(batchNos);
			//收集单号，放入单号集合
			billCashCashierConfirmDto.setBatchNos(batchNoList);
			//获取未确认收银的数据
			billCashCollectionDtos = billCashCashierConfirmPayDao.querybillCashCashierConfirmBatchNos(billCashCashierConfirmDto);
		}
		//添加现金收款单数据到list，返回到界面
		if(CollectionUtils.isNotEmpty(billCashCollectionDtos)){
			billCashCashierConfirmDtos.addAll(billCashCollectionDtos);
		}
		//添加还款单数据到list，返回到界面
		if(CollectionUtils.isNotEmpty(billRepaymentDtos)){
			billCashCashierConfirmDtos.addAll(billRepaymentDtos);
		}
		//添加预收单数据到list，返回到界面
		if(CollectionUtils.isNotEmpty(billDepositReceivedDtos)){
			billCashCashierConfirmDtos.addAll(billDepositReceivedDtos);
		}
		
		if(CollectionUtils.isNotEmpty(billCashCashierConfirmDtos)){
			//声明单号集合
			List<String> repaymentNos = new ArrayList<String>();
			//循环取billRepaymentDtos集合中的单号
			for(BillCashCashierConfirmDto cashCashierConfirmDto : billCashCashierConfirmDtos){
				String billNo = cashCashierConfirmDto.getBillNo().substring(0, 2);
				if(StringUtils.isEmpty(cashCashierConfirmDto.getWaybillNo())){
					if(StringUtils.equals(billNo, SettlementDictionaryConstants.CASH_COLLECTION_RPT_D__SOURCE_BILL_TYPE__REPAYMENT)){
    					//取billRepaymentDtos集合中运单号为空的还款单号放入单号集合中
    					repaymentNos.add(cashCashierConfirmDto.getBillNo());
					}
				}
				if(StringUtils.equals(billNo, SettlementDictionaryConstants.CASH_COLLECTION_RPT_D__SOURCE_BILL_TYPE__DEPOSIT_RECEIVED)){
					cashCashierConfirmDto.setWaybillNo("");
				}
			}
			if(CollectionUtils.isNotEmpty(repaymentNos)){
				List<BillCashCashierConfirmDto> cashCashierConfirmDtos = new ArrayList<BillCashCashierConfirmDto>();
				if(repaymentNos.size() > SettlementConstants.MAX_BILL_NO_SIZE){
					List<List<String>> repayListList = com.deppon.foss.util.CollectionUtils.splitListBySize(repaymentNos, SettlementConstants.MAX_BILL_NO_SIZE);
					
					BillCashCashierConfirmDto cashCashierConfirmDto = new BillCashCashierConfirmDto();
					cashCashierConfirmDto.setWriteoffType(SettlementDictionaryConstants.BILL_WRITEOFF__WRITEOFF_TYPE__REPAYMENT_RECEIVABLE);
					for (List<String> list : repayListList) {
						cashCashierConfirmDto.setBillNos(list);
						cashCashierConfirmDtos.addAll(billCashCashierConfirmPayDao.cashCashierConfirmDetailWaybillNos(cashCashierConfirmDto));
					}
					
				}else{
					BillCashCashierConfirmDto cashCashierConfirmDto = new BillCashCashierConfirmDto();
					cashCashierConfirmDto.setBillNos(repaymentNos);
					cashCashierConfirmDto.setWriteoffType(SettlementDictionaryConstants.BILL_WRITEOFF__WRITEOFF_TYPE__REPAYMENT_RECEIVABLE);
					
					cashCashierConfirmDtos = billCashCashierConfirmPayDao.cashCashierConfirmDetailWaybillNos(cashCashierConfirmDto);
					
				}
				//循环取之前界面条件查询的对象集合
				for(BillCashCashierConfirmDto confirmDto : billCashCashierConfirmDtos){
    				//循环取库中返回的所有运单集合
    				for(BillCashCashierConfirmDto cashierConfirmDto :cashCashierConfirmDtos){
						//cashCashierConfirmDtos运单集合中的单号对应的billRepaymentDtos对象集合单号，取cashCashierConfirmDtos集合中的运单
						if(StringUtils.equals(cashierConfirmDto.getBeginNo(), confirmDto.getBillNo())){
							if(StringUtils.isEmpty(confirmDto.getWaybillNo())){
								confirmDto.setWaybillNo(cashierConfirmDto.getEndWaybillNo());
							}
						}
					}
				}
			}
		}
		return billCashCashierConfirmDtos;
	}
	
	/**
	 * njs 将导出的数据与左边Grid一致
	 * @param billCashCashierConfirmDtos
	 * @return
	 */
	private List<BillCashCashierConfirmDto> convertToleftSide(List<BillCashCashierConfirmDto> billCashCashierConfirmDtos) {
		
		if(CollectionUtils.isNotEmpty(billCashCashierConfirmDtos)){
			//声明单号集合
			List<String> repaymentNos = new ArrayList<String>();
			//循环取billRepaymentDtos集合中的单号
			for(BillCashCashierConfirmDto cashCashierConfirmDto : billCashCashierConfirmDtos){
				String billNo = cashCashierConfirmDto.getBillNo().substring(0, 2);
				if(StringUtils.isEmpty(cashCashierConfirmDto.getWaybillNo())){
					if(StringUtils.equals(billNo, SettlementDictionaryConstants.CASH_COLLECTION_RPT_D__SOURCE_BILL_TYPE__REPAYMENT)){
    					//取billRepaymentDtos集合中运单号为空的还款单号放入单号集合中
    					repaymentNos.add(cashCashierConfirmDto.getBillNo());
					}
				}
				if(StringUtils.equals(billNo, SettlementDictionaryConstants.CASH_COLLECTION_RPT_D__SOURCE_BILL_TYPE__DEPOSIT_RECEIVED)){
					cashCashierConfirmDto.setWaybillNo("");
				}
			}
			if(CollectionUtils.isNotEmpty(repaymentNos)){
				List<BillCashCashierConfirmDto> cashCashierConfirmDtos = new ArrayList<BillCashCashierConfirmDto>();
				if(repaymentNos.size()>SettlementConstants.MAX_BILL_NO_SIZE){
					List<List<String>> repayListList = com.deppon.foss.util.CollectionUtils.splitListBySize(repaymentNos, SettlementConstants.MAX_BILL_NO_SIZE);
					
					BillCashCashierConfirmDto cashCashierConfirmDto = new BillCashCashierConfirmDto();
					cashCashierConfirmDto.setWriteoffType(SettlementDictionaryConstants.BILL_WRITEOFF__WRITEOFF_TYPE__REPAYMENT_RECEIVABLE);
					for (List<String> list : repayListList) {
						cashCashierConfirmDto.setBillNos(list);
						cashCashierConfirmDtos.addAll(billCashCashierConfirmPayDao.cashCashierConfirmDetailWaybillNos(cashCashierConfirmDto));
					}
					
				}else{
					BillCashCashierConfirmDto cashCashierConfirmDto = new BillCashCashierConfirmDto();
					cashCashierConfirmDto.setBillNos(repaymentNos);
					cashCashierConfirmDto.setWriteoffType(SettlementDictionaryConstants.BILL_WRITEOFF__WRITEOFF_TYPE__REPAYMENT_RECEIVABLE);
					
					cashCashierConfirmDtos = billCashCashierConfirmPayDao.cashCashierConfirmDetailWaybillNos(cashCashierConfirmDto);
					
				}
				//循环取之前界面条件查询的对象集合
				for(BillCashCashierConfirmDto confirmDto : billCashCashierConfirmDtos){
    				//循环取库中返回的所有运单集合
    				for(BillCashCashierConfirmDto cashierConfirmDto :cashCashierConfirmDtos){
						//cashCashierConfirmDtos运单集合中的单号对应的billRepaymentDtos对象集合单号，取cashCashierConfirmDtos集合中的运单
						if(StringUtils.equals(cashierConfirmDto.getBeginNo(), confirmDto.getBillNo())){
							if(StringUtils.isEmpty(confirmDto.getWaybillNo())){
								confirmDto.setWaybillNo(cashierConfirmDto.getEndWaybillNo());
								
							}
						}
					}
				}
			}
		}	
		return billCashCashierConfirmDtos;
	}
	
	/**
	 * 查询运单号
	 * @author foss-pengzhen
	 * @date 2013-6-28 上午10:54:33
	 * @return
	 * @see
	 */
	@Override
	public StringBuffer cashCashierConfirmDetailWaybillNo(String billNo){
		//核销的类型为还款冲应收
		String writeoffType = SettlementDictionaryConstants.BILL_WRITEOFF__WRITEOFF_TYPE__REPAYMENT_RECEIVABLE;
		//获取还款单上的运单信息
		StringBuffer waybillNo = new StringBuffer();
		List<BillCashCashierConfirmDto> billCashCashierConfirmDtos = billCashCashierConfirmPayDao.cashCashierConfirmDetailWaybillNo(billNo,writeoffType);
		for(BillCashCashierConfirmDto billCashCashierConfirmDto : billCashCashierConfirmDtos){
			if(null == billCashCashierConfirmDto){
				throw new SettlementException(billNo + "不存在对应的运单号！");
			}
			if(StringUtils.isEmpty(waybillNo.toString())){
				waybillNo.append(billCashCashierConfirmDto.getEndWaybillNo());
			}else{
				waybillNo.append(","+billCashCashierConfirmDto.getEndWaybillNo());
			}
		}
		return waybillNo;
	}
	
	/**
	 * 导出现金收银列表信息
	 * @author foss-pengzhen
	 * @date 2013-3-19 下午4:44:47
	 * @param billCashCashierConfirmDto
	 * @param currentInfo
	 * @return
	 * @see
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public ExportResource exportBillCashCashierConfirms(List<LinkedHashMap> billCashCashierConfirms,CurrentInfo currentInfo){
		//内部异常，传入参数为空，不能导出收银确认数据!
		if(CollectionUtils.isEmpty(billCashCashierConfirms)){
			throw new SettlementException("内部异常，传入参数为空，不能导出收银确认数据!");
		}
		List<String> billCashCashierConfirmIds = new ArrayList<String>();
		for(LinkedHashMap billCashCashierConfirm : billCashCashierConfirms){
			billCashCashierConfirmIds.add(billCashCashierConfirm.get("id").toString());
		}
		
		//内部异常，传入参数为空，不能导出收银确认数据!
		if(CollectionUtils.isEmpty(billCashCashierConfirmIds)){
			throw new SettlementException("内部异常，传入参数为空，不能导出收银确认数据!");
		}
		List<BillCashCashierConfirmDto> billCashCashierConfirmDtos= billCashCashierConfirmPayDao.querybillCashCashierConfirmIds(billCashCashierConfirmIds);
		
		//将导出的数据与左边Grid一致
		billCashCashierConfirmDtos=convertToleftSide(billCashCashierConfirmDtos);
				
		if(CollectionUtils.isEmpty(billCashCashierConfirmDtos)){
			throw new SettlementException("内部异常，加载库中最新数据为空，不能导出收银确认数据!");
		}
		// 返回的结果集
		// 列数据
		List<String> colList = null;
		Object fieldValue = null;
		String cellValue = null;
		List<List<String>> resultList = new ArrayList<List<String>>();
		String[] columns = exportBillCashCashierConfirms();
		
		//声明字典集合
		List<String> termCodes = new ArrayList<String>();
		//添加单据类型数据字典到集合中
		termCodes.add(DictionaryConstants.BILL_PARENT_TYPE);
		//添加收款方式数据字典到集合中
		termCodes.add(DictionaryConstants.SETTLEMENT__PAYMENT_TYPE);
		//添加状态数据字典到集合中
		termCodes.add(DictionaryConstants.BILL_DEPOSIT_RECEIVED__STATUS);
		//后台获取数据字典对应的数据
		Map<String,Map<String,Object>> map = SettlementUtil.getDataDictionaryByTermsCodes(termCodes);
		
		for(BillCashCashierConfirmDto cashierConfirmDto : billCashCashierConfirmDtos){
			colList = new ArrayList<String>();
			// 循环列
			for (String column : columns) {
				// 获取对象的值，如果为空，则设置其为空字符串
				fieldValue = ReflectionUtils.getFieldValue(cashierConfirmDto, column);
				cellValue = (fieldValue == null ? "" : fieldValue
						.toString());
				//如果是单据类型
				if(StringUtils.equals(column, "billType") && fieldValue != null){
					//转换单据类型为中文导出
					cellValue = SettlementUtil.getDataDictionaryByTermsName(map, 
							DictionaryConstants.BILL_PARENT_TYPE, fieldValue.toString());
				}
				if(StringUtils.equals(column, "paymentType")){
					//转换收款方式为中文导出
					cellValue = SettlementUtil.getDataDictionaryByTermsName(map, 
							DictionaryConstants.SETTLEMENT__PAYMENT_TYPE, fieldValue.toString());
				}
				if(StringUtils.equals(column, "status")){
					//转换状态为中文导出
					cellValue = SettlementUtil.getDataDictionaryByTermsName(map, 
							DictionaryConstants.BILL_DEPOSIT_RECEIVED__STATUS, fieldValue.toString());
				}
				if(StringUtils.equals(column, "settleApproach")){
					//转换状态为中文导出
                    if(fieldValue != null){
                        if( StringUtils.equals(fieldValue.toString(), SettlementDictionaryConstants.SETTLE_APPROACH_PC)){
                            cellValue = "PC端";
                        }
                        if( StringUtils.equals(fieldValue.toString(), SettlementDictionaryConstants.SETTLE_APPROACH_MOBILE)){
                            cellValue = "移动端";
                        }
                    }
				}
				colList.add(cellValue);
			}
			resultList.add(colList);
		}
		ExportResource sheet = new ExportResource();
		sheet.setHeads(billCashCashierConfirmHeads());
		sheet.setRowList(resultList);
		return sheet;
	}

    /**
     * 查询未收银确认的代收货款相关单据
     * @param billCashCashierConfirmDto
     * @return 单号list
     */
    @Override
    public List<String> queryUnconfirmedCodRelatedBill(BillCashCashierConfirmDto billCashCashierConfirmDto) {
        return billCashCashierConfirmPayDao.queryUnconfirmedCodRelatedBill(billCashCashierConfirmDto);
    }

    /**
	 * excel heads
	 * @author foss-pengzhen
	 * @date 2013-3-20 下午5:14:41
	 * @return
	 * @see
	 */
	public String[] billCashCashierConfirmHeads(){
		String[] heads = {"单号","运单号","单据类型","总金额","收款方式","结清方式",
				"客户编码","客户名称","收款部门编码","收款部门名称","制单人编码",
				"制单人名称","版本号","状态","收银部门名称","收银部门编码","POS串号","银联交易流水号"};
		return heads;
	}
	
	/**
	 * 导出现金收银列表信息
	 * @author foss-pengzhen
	 * @date 2013-3-19 下午4:44:47
	 * @param billCashCashierConfirmDto
	 * @param currentInfo
	 * @return
	 * @see
	 */
	public String[] exportBillCashCashierConfirms(){
		String[] result = {
		//单号
		"billNo",
		//运单号
		"waybillNo",
		//单据类型
		"billType",
		//总金额
		"amount",
		//收款方式
		"paymentType",
		//结清方式
		"settleApproach",
		//客户编码
		"customerCode",
		//客户名称
		"customerName",
		//收款部门编码
		"collectionOrgCode",
		//收款部门名称
		"collectionOrgName",
		//制单人编码
		"createUserCode",
		//制单人名称
		"createUserName",
		//版本号
		"versionNo",
		//状态
		"status",
		"collectionOrgName","collectionOrgCode","posSerialNum","batchNo"
		};
		return result;
	}
	
	/**
	 * 确认收银提交
	 * @author foss-pengzhen
	 * @date 2012-12-17 下午2:38:32
	 * @param billCashCashierConfirmDto
	 * @param currentInfo
	 * @see
	 */
	@SuppressWarnings("null")
	@Transactional
	public void cashCashierConfirmCommit(List<BillCashCashierConfirmDto> billCashCashierConfirmDtos,CurrentInfo currentInfo){
		if(CollectionUtils.isEmpty(billCashCashierConfirmDtos)){
			throw new SettlementException("内部异常，传入参数为空，不能收银确认!");
		}
		//声明锁机制
		List<MutexElement> elements_List = null;
		//预收单集合
		List<BillDepositReceivedEntity> billDepositReceivedEntities = null;
		List<String> depositReceivedIds = new ArrayList<String>();
		
		//现金收款单集合
		List<BillCashCollectionEntity> billCashCollectionEntities = null;
		List<String> cashCollectionIds = new ArrayList<String>();
		
		//还款单集合
		List<BillRepaymentEntity> billRepaymentEntities = null;
		List<String> repaymentIds = new ArrayList<String>();
		
		String active = "";
		for(BillCashCashierConfirmDto cashCashierConfirmDto : billCashCashierConfirmDtos){
			//如是现金收款单，添加到现金收款单集合
			if(SettlementDictionaryConstants.CASH_COLLECTION_RPT_D__SOURCE_BILL_TYPE__CASH_COLLECTION
					.equals(cashCashierConfirmDto.getBillType())){
				cashCollectionIds.add(cashCashierConfirmDto.getId());
			}
			//如是还款单，添加到还款单集合
			if(SettlementDictionaryConstants.CASH_COLLECTION_RPT_D__SOURCE_BILL_TYPE__REPAYMENT
					.equals(cashCashierConfirmDto.getBillType())){
				repaymentIds.add(cashCashierConfirmDto.getId());
			}
			//如是预收单，添加到预收单集合
			if(SettlementDictionaryConstants.CASH_COLLECTION_RPT_D__SOURCE_BILL_TYPE__DEPOSIT_RECEIVED
					.equals(cashCashierConfirmDto.getBillType())){
				depositReceivedIds.add(cashCashierConfirmDto.getId());
			}
		}
		//现金收款单集合不为空，执行现金收款单load库中最新数据
		if(CollectionUtils.isNotEmpty(cashCollectionIds)){
			billCashCollectionEntities = billCashCollectionService.queryByCashCollectionIds(cashCollectionIds, active);
		}
		//还款单集合不为空，执行还款单load库中最新数据
		if(CollectionUtils.isNotEmpty(repaymentIds)){
			billRepaymentEntities = billRepaymentService.queryByRepaymentIds(repaymentIds, active);
		}
		
		//预收单集合不为空，执行预收单load库中最新数据
		if(CollectionUtils.isNotEmpty(depositReceivedIds)){
			billDepositReceivedEntities = billDepositReceivedService.queryByDepositReceivedIds(depositReceivedIds, active);
		}

		//如果集合都为空，则抛出异常信息
		if(CollectionUtils.isEmpty(billCashCollectionEntities)
				&& CollectionUtils.isEmpty(billRepaymentEntities)
				&& CollectionUtils.isEmpty(billDepositReceivedEntities)){
			throw new SettlementException("库中数据为空，不能收银确认!");
		}
		
		//现金收款单集合不为空，则调用现金收款接口
		if(CollectionUtils.isNotEmpty(billCashCollectionEntities)){
    		//现金收款单DTO
    		BillCashCollectionDto billCashCollectionDto = new BillCashCollectionDto();
    		billCashCollectionDto.setBillCashCollections(billCashCollectionEntities);
    		//调用批量现金收款单接口
    		billCashCollectionService.confirmCashierBillCashCollection(billCashCollectionDto, currentInfo);
		}
		//还款单集合不为空，则调用还款单接口
		if(CollectionUtils.isNotEmpty(billRepaymentEntities)){
			BillRepaymentDto billRepaymentDto = new BillRepaymentDto();
			billRepaymentDto.setBillRepayments(billRepaymentEntities);
			//调用还款单接口
			billRepaymentService.confirmCashierBillRepayments(billRepaymentDto, currentInfo);
			//取出还款单的来源单据类型为：代收货款，
			List<String> waybillNos = new ArrayList<String>();
			for(BillRepaymentEntity billRepaymentEntity : billRepaymentEntities){
				if(StringUtils.equals(billRepaymentEntity.getSourceBillType(), 
						SettlementDictionaryConstants.BILL_REPAYMENT__SOURCE_BILL_TYPE__COD)){
					/**
					 * @author 218392 zhangyongxue 
					 * @date 2015-12-30 23:14:30
					 * 代收货款收银确认BUG:如果分两次对代收结清货款，但是只对一个还款单进行收银确认，那么久不应该让这个单号对应的代收应付单生效
					 */
					String waybillNo = billRepaymentEntity.getWaybillNo();
					BillRepaymentConditionDto dto = new BillRepaymentConditionDto();
					dto.setActive("Y");
					dto.setStatus("S");
					dto.setWaybillNo(waybillNo);
					String [] arr = {"COD"};
					dto.setSourceBillTypes(arr);
					List<BillRepaymentEntity> repaymentList = billRepaymentService.queryBillRepaymentByCondition(dto);
					if(repaymentList != null && repaymentList.size() > 0){
						LOGGER.info("未收银确认的条数："+repaymentList.size());
						continue;
					}else{
						waybillNos.add(billRepaymentEntity.getWaybillNo());	
						//BUG-52335添加运单锁				
						//添加对应的锁
						if(elements_List == null){
							elements_List = new ArrayList<MutexElement>();
						}				
						MutexElement element = new MutexElement(billRepaymentEntity.getWaybillNo(), PDASIGN_ENABLEPAYABLE,MutexElementType.PDA_SIGNAL_CASHCONFIRM);
						elements_List.add(element);
					}
				}							
			}
			//对相应的单据添加锁
			if(elements_List!= null){
				boolean canLock = businessLockService.lock(elements_List,SettlementConstants.BUSINESS_LOCK_BATCH);
				if(!canLock){
					throw new SettlementException("部分正在签收，请一分钟再后操作！");
				}			
			}
			List<BillPayableEntity> billPayableEntities = null;
			if(CollectionUtils.isNotEmpty(waybillNos)){
			//根据运单号集合和单据类型，查询已签收且未生效(并且运输性质不为空运)应付单信息
			String billType = SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__ACCOUNT_PAYABLE_COD;
				billPayableEntities = billPayableService.queryByWaybillNosByBillType(waybillNos,billType);
			}
			//批量更改支付状态
			//批量生效应付单BillPayableService中的： batchUpdateByTakeEffect
			if(CollectionUtils.isNotEmpty(billPayableEntities)){		
    			BillPayableDto billPayableDto = new BillPayableDto();
    			billPayableDto.setBillPayables(billPayableEntities);
    			billPayableService.batchUpdateByTakeEffect(billPayableDto, currentInfo);

    			/*
    			 * @author 218392 张永雪
    			 * @date 2015-12-15 09:37:20
    			 * 针对有效的代收数据，审批筛选条件如下（如下条件满足任意一个则为需审核数据）： 
    			 *	1、	单笔代收退款金额≥80000元
    			 *	2、	单笔代收货款金额更改额绝对值≥50000元的
    			 *	3、	出发部门=到达部门
    			 *	4、	签收时间-开单时间≤12小时
    			 *	5、	23：00-次日8：00之间签收的代收货款数据
    			 *	6、	存在代收款的运单，货物轨迹中无配载单记录的。
    			 *
    			 *筛选的数据必须同时满足以下条件：
    			 *	1、	此次改动，不涉及快递代理代收流程、空运代收流程。只针对专线单据。
    			 *	2、	单据类型必须为有代收货款的单据
    			 *	3、	单据状态必须为已经正常签收
    			 *	4、	单据状态必须为已经收银确认
    			 *		备注：单据为满足以上条件的零担、快递单据
    			 */
    			CodAuditDto audiDto = new CodAuditDto();
    			List<String> waybillListNo = new ArrayList<String>();
    			List<CodAuditLogEntity> logList = new ArrayList<CodAuditLogEntity>();
				String msg = "";
    			for(BillPayableEntity billPayableEntity : billPayableEntities){
    				/**  1、单笔代收退款金额≥80000元   
    				 * */
    				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    				BigDecimal codeAmount= billPayableEntity.getAmount();//单笔代收退款金额
    				BigDecimal amount1 = new BigDecimal(80000);//定义80000元
    				int result = codeAmount.compareTo(amount1);//1大于，-1小于，0是等于
    				LOGGER.info("(收银确认操作)单笔代收货款退款金额为：" + codeAmount + "...和80000比较的结果是：" + result);
    				
    				/**  2、单笔代收货款金额更改额绝对值≥50000元的  
    				 * */
    				String waybillNo = billPayableEntity.getWaybillNo();//运单号
    				if(!StringUtils.isNotEmpty(waybillNo)){
    					throw new SettlementException("运单号为空！");
    				}
    				WaybillEntity entity= waybillDao.queryFirstWaybillByWaybillNo(waybillNo);//通过运单编号查询第一次开单时运单信息
    				BigDecimal waybillCodAmount = entity.getCodAmount();//第一次开单时候的代收金额
    				/**
    				 * @author 231434
    				 * update 为快递是需要查找第一条代收退款应付单的amount
    				 * amountCompare（差值） = codeAmount - 最初的代收退款金额
    				 */
    				if(SettlementUtil.isPackageProductCode(entity.getProductCode())){
    					//TODO 查询第一条代收退款应付单的amount
    					waybillCodAmount = this.billPayableService.selectFirstPayableAmountByWaybillNo(waybillNo);
    				}
    				
    				LOGGER.info("(收银确认操作)通过运单编号查询第一次开单时的金额为：" + waybillCodAmount);
    				BigDecimal amountCompare = codeAmount.subtract(waybillCodAmount);//差值
    				BigDecimal amount2 = new BigDecimal(50000);//定义80000元
    				BigDecimal absAmount = amountCompare.abs();//绝对值
    				int num = absAmount.compareTo(amount2);//1大于，-1小于，0是等于
    				LOGGER.info("(收银确认操作)单笔代收货款金额更改额绝对值为：" + absAmount + "...和50000比较的结果是：" + num);
    				
    				/**  3、出发部门=到达部门
    				 *   */
    				String  origOrgcode = billPayableEntity.getOrigOrgCode();//出发部门(取自应付单)
    				String destOrgcode 	= billPayableEntity.getDestOrgCode();//到达部门(取自应付单)
    				LOGGER.info("(收银确认操作)出发部门编码为：" + origOrgcode +",到达部门编码为：" + destOrgcode);
    				Boolean isEquals = false;
    				if(StringUtils.isNotEmpty(origOrgcode) || StringUtils.isNotEmpty(destOrgcode)){
    					isEquals = origOrgcode.equals(destOrgcode);
    				}else{
    					throw new SettlementException("(收银确认操作)出发部门或到达部门为空！");
    				}
    				
    				/**  4、签收时间-开单时间≤12小时  
    				 * */
    				Date signTime = billPayableEntity.getSignDate();//签收时间
    				Date billTime = entity.getBillTime();//开单时间
    				LOGGER.info("(收银确认操作)签收的时间是：" + dateFormat.format(signTime));
    				LOGGER.info("(收银确认操作)开单的时间是：" + dateFormat.format(billTime));
    				double subTime = Math.abs(signTime.getTime() - billTime.getTime());
    				LOGGER.info("(收银确认操作)开单的时间差毫秒数为：" + subTime);
    				Boolean isOverTime = false;
    				if(subTime < (12*3600000)){//签收时间是否小于12个小时
    					isOverTime = true;
    				}
    				LOGGER.info("签收时间是否小于12个小时：" + isOverTime);
    				//签收开单时长单位小时，用于插入代收审核表中
    				int signBillDiffer = (int) (subTime/3600000.0) ;
    				LOGGER.info("(收银确认操作)开单的时长是：" + signBillDiffer);
    				
    				/**  
    				 * 5、线上BUG修复 2016-01-11 13:41:50 放到上线版本为0114
    				 * 签收时间在签收当天8:00之前, 23：00之后的代收货款数据
    				 */
    				Calendar cal = Calendar.getInstance();
    				cal.setTime(signTime);
    				cal.set(Calendar.HOUR_OF_DAY, 23);
    				cal.set(Calendar.MINUTE, 0);
    				cal.set(Calendar.SECOND, 0);
    				cal.set(Calendar.MILLISECOND, 000);
    				Date date23 = cal.getTime();//获取当天23:00
    				LOGGER.info("(收银确认操作)当天23点00分000毫秒的时间是：" + dateFormat.format(date23));
    				
    				final int numberOfEight = 8;
    				Calendar cal8 = Calendar.getInstance();
    				cal8.setTime(signTime);
    				cal8.set(Calendar.HOUR_OF_DAY, numberOfEight);
    				cal8.set(Calendar.MINUTE, 0);
    				cal8.set(Calendar.SECOND, 0);
    				cal8.set(Calendar.MILLISECOND, 000);
    				Date date8 = cal8.getTime();//获取次日08:00:00:000
    				LOGGER.info("(收银确认操作)当天8点00分00秒000毫秒的时间是：" + dateFormat.format(date8));
    				LOGGER.info("签收时间为：" + dateFormat.format(signTime));
    				
    				long signTimeLon = signTime.getTime();
    				long date23Lon 	 = date23.getTime();
    				long date8Lon	 = date8.getTime();
    				
    				Boolean timeIsBetween = false;
    				if(signTimeLon >= date23Lon || signTimeLon <= date8Lon){
    					timeIsBetween = true;
    				}
    				
    				/**  6、存在代收款的运单，货物轨迹中无配载单记录的  
    				 * */
    				String isY = "";
    				Boolean isExist = false;
    				/*isY = vehicleAssembleBillService.queryIsVehicleassembleByNo(waybillNo);
    				if(StringUtils.equals("N", isY)){
    					isExist = true;
    				}*/
    				/**
    				 * @author 325369
    				 * @date 2016-09-06
    				 * 根据运单号查询运单表，判断是否是悟空运单，是则请求悟空接口查询配载单数，否则调用本地接口
    				 */
    				if (waybillEntityForEcsDao.queryWaybillIsEcsByWaybillNo(waybillNo) > 0) {
    					isY = queryIsVehicleassembleForEcs.queryIsVehicleassembleForEcs(waybillNo);
    					isExist = StringUtils.equals("N", isY) == true ? true : false;
    				} else {
    					isY = vehicleAssembleBillService.queryIsVehicleassembleByNo(waybillNo);
    					isExist = StringUtils.equals("N", isY) == true ? true : false;
    				}
    				
    				BillRepaymentConditionDto repaymentConDto = new BillRepaymentConditionDto();
    				repaymentConDto.setWaybillNo(waybillNo);
    				repaymentConDto.setActive(FossConstants.YES);
    				// 来源单据类型：代收货款
    				repaymentConDto
    						.setSourceBillTypes(new String[] { SettlementDictionaryConstants.BILL_REPAYMENT__SOURCE_BILL_TYPE__COD });
    				List<BillRepaymentEntity> repayList = this.billRepaymentService
    						.queryBillRepaymentByCondition(repaymentConDto);
    				
    				
    				Date cashConfirmTime = null;//收银确认时间 
    				String paymentType = null;
    				// 还款单集合不为空
    				if (repayList != null && repayList.size() > 0) {
    					// 对查询到得还款单repayEntity进行排序，按照记账日期accountDate从前往后进行排序
    					ListComparator orderComparator = new ListComparator(
    							SettlementConstants.ACCOUNT_DATE);
    					Collections.sort(repayList, orderComparator);
    					// 获取最后一条还款单记录，判断是否已经收银确认
    					BillRepaymentEntity repayEntity = repayList.get((repayList.size() - 1));
    					// 还款单状态为已经收银确认
    					if (repayEntity != null
    							&& SettlementDictionaryConstants.BILL_REPAYMENT__STATUS__CONFIRM
    									.equals(repayEntity.getStatus())) {
    						cashConfirmTime = repayEntity.getCashConfirmTime();
    						paymentType = repayEntity.getPaymentType();
    					}
    					
    					
    				}
    				/**
					 * 新增的三个筛选条件1：单笔代收即日退≥30000，单笔代收三日退（审核退）≥50000
					 * */
    				//R1, 单笔代收即日退≥30000 billPayableEntity.getCodType()
    				boolean codconditionR1=false;
    				final int numberOfThirtyThousand = 30000;
    				if(SettlementDictionaryConstants.COD__COD_TYPE__RETURN_1_DAY.equals(billPayableEntity.getCodType())&&
    						codeAmount.compareTo(new BigDecimal(numberOfThirtyThousand))>=0){
    					codconditionR1=true;
    					LOGGER.info("单笔代收即日退退款金额为：" + codeAmount + "...和30000比较的结果是：" + codeAmount.compareTo(new BigDecimal(numberOfThirtyThousand)));
    				}
    				//R3,单笔代收三日退≥50000 SettlementDictionaryConstants.COD__COD_TYPE__RETURN_3_DAY
    				boolean codconditionR3=false;
    				final int numberOfFiftyThousand = 50000;
    				if(SettlementDictionaryConstants.COD__COD_TYPE__RETURN_3_DAY.equals(billPayableEntity.getCodType())&&
    						codeAmount.compareTo(new BigDecimal(numberOfFiftyThousand))>=0){
    					codconditionR3=true;
    					LOGGER.info("单笔代收三日退退款金额为：" + codeAmount + "...和50000比较的结果是：" + codeAmount.compareTo(new BigDecimal(numberOfFiftyThousand)));
    				}
    				//RA,单笔代收审核退≥50000  SettlementDictionaryConstants.COD__COD_TYPE__RETURN_APPROVE
    				boolean codconditionRA=false;
    				if(SettlementDictionaryConstants.COD__COD_TYPE__RETURN_APPROVE.equals(billPayableEntity.getCodType())&&
    						codeAmount.compareTo(new BigDecimal(numberOfFiftyThousand))>=0){
    					codconditionRA=true;
    					LOGGER.info("单笔代收审核退退款金额为：" + codeAmount + "...和50000比较的结果是：" + codeAmount.compareTo(new BigDecimal(numberOfFiftyThousand)));
    				}
    				
    				/**新增的三个筛选条件2:付款方式为“电汇”的所有单据
    				 * */
    				boolean codpayType = false;
    				//做判断，是否是电汇
    				/**
    				 * 这里的收款方式取的是客户最后一次对  应收单 进行还款时候的方式
    				 * paymentType，取自上述的最后一个还款单里的。
    				 * String paymentType = repayList.get((repayList.size() - 1))..getPaymentType()
    				 **/
    				if(paymentType.equals(SettlementDictionaryConstants.BILL_CLAIM__PAYMENT_CATEGORIES__TELEGRAPH_TRANSFER)){
    					codpayType = true;
    				}
    				
    				/**新增的三个筛选条件3.代收货款更改受理时间与签收时间之差小于1小时的
    				 * */
    				boolean isChangeOverTime = false;
    				final int numberOfThirtySix = 3600000;
    				//定义Dto存放单号
    				CodAuditDto codAuditDto = new CodAuditDto();
    				codAuditDto.setWaybillNo(waybillNo);
    				//获取更改时间
    				List<CodAuditEntity>  codAuditEntityList= codAuditService.queryCodChangeTime(codAuditDto);
    				if(CollectionUtils.isNotEmpty(codAuditEntityList)){
    					//排序,按照
    					ListComparator codChangeEntity = new ListComparator("changeTime");
    					Collections.sort(codAuditEntityList, codChangeEntity);
    					//获取最后一条更改记录
    					CodAuditEntity   codAuditEntity = codAuditEntityList.get((codAuditEntityList.size() - 1));
    					//签收跟更改时间差
    					double subChangeTime = Math.abs(signTime.getTime()-codAuditEntity.getChangeTime().getTime());
    					LOGGER.info("(收银确认操作)运单更改的时间差毫秒数为：" + subChangeTime);
    					if(subChangeTime<(1*numberOfThirtySix)){
    						isChangeOverTime = true;
    					}
    				}
    				
    				
    				/** 判断:满足任意一个则为需审核数据
    				 *  */
    				if((result>-1) || (num>-1) || (isEquals) || (isOverTime) || (timeIsBetween) || (isExist)){
    					//1.插入 stl.t_stl_cod_audit,状态为 复核会计待审核-RA
    					CodAuditEntity record = new CodAuditEntity();
    					record.setId(UUIDUtils.getUUID());
    					record.setWaybillNo(waybillNo);//单号
    					record.setActive("Y");
    					record.setCodAmount(codeAmount);//代收金额
    					record.setLockStatus(SettlementDictionaryConstants.SETTLE_CODAUDIT_FUNDAUDIT);//资金部待审核状态
    					record.setSigTime(signTime);//签收时间
    					record.setOrigOrgCode(origOrgcode);//出发部门编码
    					record.setOrigOrgName(billPayableEntity.getOrigOrgName());//出发部门名称
    					record.setDestOrgCode(destOrgcode);//到达部门编码
    					record.setDestOrgName(billPayableEntity.getDestOrgName());//到达部门名称
    					record.setHasTrack(isY);//是否有走货路径Y/N
    					record.setCodType(billPayableEntity.getCodType());//代收货款类型
    					record.setPaymentType(paymentType);//付款方式
    					record.setBillTime(billTime);//开单时间
    					if(cashConfirmTime != null){
    						record.setComfirmTime(cashConfirmTime);//收银确定时间
    					}else{
    						throw new SettlementException("收银时间为空！");
    					}
    					record.setAccountNo(billPayableEntity.getAccountNo());//银行行号
    					record.setCustomerCode(billPayableEntity.getCustomerCode());//客户编码
    					record.setCustomerName(billPayableEntity.getCustomerName());//客户名称
    					record.setMobileNo(billPayableEntity.getCustomerPhone());//客户手机号码
    					record.setCreateDate(new Date());//创建日期
    			        UserEntity user =  FossUserContext.getCurrentUser();
    					String userCode = user != null ?user.getUserName():"";
    					record.setCreateUser(userCode);//创建人
    					record.setBillSignDiffer(signBillDiffer);//签收开单时长
    					record.setChangeAmount(absAmount);//代收更改金额
						/**
						 * @author 218392 ZYX
						 * 2016-10-17 签收的时候判断代收支付审核表中是否有数据，如果有则不再插入
						 */
    					List<CodAuditEntity> codAuditEntities = codAuditService.queryCodDtoByWaybillNo(waybillNo);
    					if(CollectionUtils.isNotEmpty(codAuditEntities)){
    						LOGGER.info("单号：" + waybillNo +"在代收支付审核界面已存在，无需收银确认的时候再次插入");
    					}else{
        					codAuditService.addCodAudit(record);
        					//2.同时还要往stl.t_stl_cod_audit_log日志表中插入,记录时效的时候把这表里的是否有效改成N 
        					waybillListNo.add(waybillNo);
        					msg = "资金部待审核";
    					}

    			}else{	
    					if(codconditionR1||codconditionR3||codconditionRA||codpayType||isChangeOverTime){
    						//1.插入 stl.t_stl_cod_audit,状态为 复核会计待审核-RA
        					CodAuditEntity record = new CodAuditEntity();
        					record.setId(UUIDUtils.getUUID());
        					//单号
        					record.setWaybillNo(waybillNo);
        					record.setActive("Y");
        					//代收金额
        					record.setCodAmount(codeAmount);
        					//复核会计待审核
        					record.setLockStatus(SettlementDictionaryConstants.SETTLE_CODAUDIT_REVIEWAUDIT);
        					//签收时间
        					record.setSigTime(signTime);
        					//出发部门编码
        					record.setOrigOrgCode(origOrgcode);
        					//出发部门名称
        					record.setOrigOrgName(billPayableEntity.getOrigOrgName());
        					//到达部门编码
        					record.setDestOrgCode(destOrgcode);
        					//到达部门名称
        					record.setDestOrgName(billPayableEntity.getDestOrgName());
        					//是否有走货路径Y/N
        					record.setHasTrack(isY);
        					//代收货款类型
        					record.setCodType(billPayableEntity.getCodType());
        					//付款方式
        					record.setPaymentType(paymentType);
        					//开单时间
        					record.setBillTime(billTime);
        					if(cashConfirmTime != null){
        						//收银确定时间
        						record.setComfirmTime(cashConfirmTime);
        					}else{
        						throw new SettlementException("收银时间为空！");
        					}
        					//银行行号
        					record.setAccountNo(billPayableEntity.getAccountNo());
        					//客户编码
        					record.setCustomerCode(billPayableEntity.getCustomerCode());
        					//客户名称
        					record.setCustomerName(billPayableEntity.getCustomerName());
        					//客户手机号码
        					record.setMobileNo(billPayableEntity.getCustomerPhone());
        					//创建日期
        					record.setCreateDate(new Date());
        					UserEntity user =  FossUserContext.getCurrentUser();
        					String userCode = user != null ?user.getUserName():"";
        					//创建人
        					record.setCreateUser(userCode);
        					//签收开单时长
        					record.setBillSignDiffer(signBillDiffer);
        					//代收更改金额
        					record.setChangeAmount(absAmount);
    						/**
    						 * @author 218392 ZYX
    						 * 2016-10-17 签收的时候判断代收支付审核表中是否有数据，如果有则不再插入
    						 */
        					List<CodAuditEntity> codAuditEntities = codAuditService.queryCodDtoByWaybillNo(waybillNo);
        					/**
        					 * @author 218392 zhangyongxue 2016-10-21 22:41:00 BUG：满足代收货款支付审核的数据没有进入，因为这里的判断有问题，
        					 * 集合判断空得用下面的，不能CodAuditEntities ！= null
        					 */
        					if(CollectionUtils.isNotEmpty(codAuditEntities)){
        						LOGGER.info("单号：" + waybillNo +"在代收支付审核界面已存在，无需收银确认的时候再次插入");
        					}else{
            					codAuditService.addCodAudit(record);
            					//2.同时还要往stl.t_stl_cod_audit_log日志表中插入,记录时效的时候把这表里的是否有效改成N 
            					waybillListNo.add(waybillNo);
            					msg = "复核会计组待审核";
        					}
    					}
    			}
			}
    			if(StringUtils.isNotBlank(msg)){
        			audiDto.setWaybillNos(waybillListNo);
        			logList = buildCodAuditLogs(audiDto.getWaybillNos(),logList,msg);
        			if(logList != null && logList.size() > 0){
        				codAuditLogService.insertBath(logList);//还是调用的批量插入
        			}else{
        				LOGGER.info("插入日志的实体信息为空！");
        			}
    			}
		} 
		//@author zhangyongxue 218392 线上BUG 2016-01-18收银确认的时候，点击确定，收银确认不了
		//这是由于代收货款支付审核需求1月5号上线引起的，下面的括号，竟然扩到了下面 BUG-52335 解锁 if之后了，造成收银确认不了
	   }
			
		
			
		//预收单合集不为空，则调用预收接口
		if(CollectionUtils.isNotEmpty(billDepositReceivedEntities)){
			//预收单DTO
			BillDepositReceivedEntityDto billDepositReceivedEntityDto = new BillDepositReceivedEntityDto();
			billDepositReceivedEntityDto.setBillDepositReceivedEntityList(billDepositReceivedEntities);
			//调用预收单收银确认接口
			billDepositReceivedService.confirmCashierBillDepositReceived(billDepositReceivedEntityDto, currentInfo);
		}
		//BUG-52335 解锁
		if(elements_List!= null){
			businessLockService.unlock(elements_List);
		}
	
	}

    /**
     * 创建日志集合
     * @param waybillNos
     * @param logEntities
     * @param operatContext
     */
    public List<CodAuditLogEntity> buildCodAuditLogs(List<String> waybillNos ,
                                   List<CodAuditLogEntity> logEntities,
                                   String operatContext){
        UserEntity user =  FossUserContext.getCurrentUser();
        String userCode = user != null ?user.getUserName():"";
        String deptCode = FossUserContext.getCurrentDeptCode();
        String deptName = FossUserContext.getCurrentDeptName();
        //遍历运单号，进行封装日志信息
        if(CollectionUtils.isNotEmpty(waybillNos)){
            for(String waybillNo:waybillNos){
                CodAuditLogEntity entity = new CodAuditLogEntity();
                entity.setId(UUID.randomUUID().toString());
                entity.setWaybillNo(waybillNo);
                entity.setModifyUser(userCode);
                entity.setModifyDate(new Date());
                entity.setCreateUser(userCode);
                entity.setOperateContent(operatContext);
                entity.setOperateDeptcode(deptCode);
                entity.setOperateDeptname(deptName);
                entity.setOperateTime(new Date());
                entity.setCreateDate(new Date());
                logEntities.add(entity);
            }
        }
        return logEntities;
    }
	
	/**
	 * 收银确认判断部门
	 * @author foss-pengzhen
	 * @date 2012-12-21 下午4:39:37
	 * @return
	 * @see
	 */
	public String deptJudge(CurrentInfo currentInfo){
		String billingGroup = "N";
		//判空
		if(currentInfo != null){
			//查询部门信息
        	OrgAdministrativeInfoEntity orgEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(currentInfo.getCurrentDeptCode());
        	//判空
        	if(orgEntity!=null){
        		billingGroup = orgEntity.getBillingGroup();
        	}
        }
		return billingGroup;
	}

	/**
	 * @GET
	 * @return billCashCashierConfirmPayDao
	 */
	public IBillCashCashierConfirmPayDao getBillCashCashierConfirmPayDao() {
		/*
		 *@get
		 *@ return billCashCashierConfirmPayDao
		 */
		return billCashCashierConfirmPayDao;
	}

	/**
	 * @SET
	 * @param billCashCashierConfirmPayDao
	 */
	public void setBillCashCashierConfirmPayDao(
			IBillCashCashierConfirmPayDao billCashCashierConfirmPayDao) {
		/*
		 *@set
		 *@this.billCashCashierConfirmPayDao = billCashCashierConfirmPayDao
		 */
		this.billCashCashierConfirmPayDao = billCashCashierConfirmPayDao;
	}

	/**
	 * @GET
	 * @return billCashCollectionService
	 */
	public IBillCashCollectionService getBillCashCollectionService() {
		/*
		 *@get
		 *@ return billCashCollectionService
		 */
		return billCashCollectionService;
	}

	/**
	 * @SET
	 * @param billCashCollectionService
	 */
	public void setBillCashCollectionService(
			IBillCashCollectionService billCashCollectionService) {
		/*
		 *@set
		 *@this.billCashCollectionService = billCashCollectionService
		 */
		this.billCashCollectionService = billCashCollectionService;
	}

	/**
	 * @GET
	 * @return billRepaymentService
	 */
	public IBillRepaymentService getBillRepaymentService() {
		/*
		 *@get
		 *@ return billRepaymentService
		 */
		return billRepaymentService;
	}

	/**
	 * @SET
	 * @param billRepaymentService
	 */
	public void setBillRepaymentService(IBillRepaymentService billRepaymentService) {
		/*
		 *@set
		 *@this.billRepaymentService = billRepaymentService
		 */
		this.billRepaymentService = billRepaymentService;
	}

	/**
	 * @GET
	 * @return billPayableService
	 */
	public IBillPayableService getBillPayableService() {
		/*
		 *@get
		 *@ return billPayableService
		 */
		return billPayableService;
	}

	/**
	 * @SET
	 * @param billPayableService
	 */
	public void setBillPayableService(IBillPayableService billPayableService) {
		/*
		 *@set
		 *@this.billPayableService = billPayableService
		 */
		this.billPayableService = billPayableService;
	}

	/**
	 * @GET
	 * @return billDepositReceivedService
	 */
	public IBillDepositReceivedService getBillDepositReceivedService() {
		/*
		 *@get
		 *@ return billDepositReceivedService
		 */
		return billDepositReceivedService;
	}

	/**
	 * @SET
	 * @param billDepositReceivedService
	 */
	public void setBillDepositReceivedService(
			IBillDepositReceivedService billDepositReceivedService) {
		/*
		 *@set
		 *@this.billDepositReceivedService = billDepositReceivedService
		 */
		this.billDepositReceivedService = billDepositReceivedService;
	}

	/**
	 * @GET
	 * @return orgAdministrativeInfoService
	 */
	public IOrgAdministrativeInfoService getOrgAdministrativeInfoService() {
		/*
		 *@get
		 *@ return orgAdministrativeInfoService
		 */
		return orgAdministrativeInfoService;
	}

	/**
	 * @SET
	 * @param orgAdministrativeInfoService
	 */
	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		/*
		 *@set
		 *@this.orgAdministrativeInfoService = orgAdministrativeInfoService
		 */
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

	public void setWaybillDao(IWaybillDao waybillDao) {
		this.waybillDao = waybillDao;
	}

	public void setVehicleAssembleBillService(
			IVehicleAssembleBillService vehicleAssembleBillService) {
		this.vehicleAssembleBillService = vehicleAssembleBillService;
	}

	public void setCodAuditService(ICodAuditService codAuditService) {
		this.codAuditService = codAuditService;
	}

	public void setCodAuditLogService(ICodAuditLogService codAuditLogService) {
		this.codAuditLogService = codAuditLogService;
	}
	
	public void setQueryIsVehicleassembleForEcs(
			IQueryIsVehicleassembleForEcs queryIsVehicleassembleForEcs) {
		this.queryIsVehicleassembleForEcs = queryIsVehicleassembleForEcs;
	}

	public void setWaybillEntityForEcsDao(
			IWaybillEntityForEcsDao waybillEntityForEcsDao) {
		this.waybillEntityForEcsDao = waybillEntityForEcsDao;
	}
	
}

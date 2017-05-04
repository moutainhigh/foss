package com.deppon.foss.module.settlement.consumer.server.action;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.BankEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.consumer.api.server.service.ICodCommonService;
import com.deppon.foss.module.settlement.consumer.api.server.service.ISalesPayCODService;
import com.deppon.foss.module.settlement.consumer.api.shared.domain.CODEntity;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.CODDto;
import com.deppon.foss.module.settlement.consumer.api.shared.vo.CODVo;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 出纳帐号修改Action.
 *
 * @author ibm-guxinhua
 * @date 2012-10-30 上午9:36:10
 */
public class SalesPayCODAction extends AbstractAction {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -8801328974015310224L;

	/** 声明日志对象. */
	private static final Logger LOGGER = LogManager
			.getLogger(SalesPayCODAction.class);

	/** 前台页面查询条件值，查询所有. */
	private static final String QUERY_CONDITION_ALL = "all";

	/** 前台页面查询条件值，按运单号查询. */
	private static final String QUERY_CONDITION_WAYBILL = "waybill";
	
	/** 代收货款出发申请服务. */
	private ISalesPayCODService salesPayCODService;

	/** 代收货款记录管理服务. */
	private ICodCommonService codCommonService;
	

	/** 代收货款VO. */
	private CODVo salesPayCODVO;
	

	/**
	 * 营业部冻结.
	 *
	 * @return the string
	 * @author ibm-guxinhua
	 * @date 2012-10-30 上午9:36:10
	 */
	@JSON
	public String freezePayCOD() {
		try {
			if (null == salesPayCODVO) {
				// 查询参数无效
				throw new SettlementException("查询参数无效");
			}

			String[] entityIds = salesPayCODVO.getEntityIds();
			// 代收货款实体为空，不能进行冻结代收货款操作
			if (ArrayUtils.isEmpty(entityIds)) {
				throw new SettlementException("代收货款实体为空，不能进行冻结代收货款操作");
			}
			// 营业部冻结代收货款
			salesPayCODService.freezeBillPayCOD(entityIds,
					FossUserContext.getCurrentInfo());
			return returnSuccess();
		} catch (BusinessException e) {
			LOGGER.error(e.getMessage(), e);
			return returnError(e);
		}
	}

	/**
	 * 出纳审核前判断客户是否存在应收账款
	 * 获得客户存在应收账款的代收款客户信息
	 * @author guxinhua
	 * @date 2013-4-9 下午6:52:45
	 * @return
	 */
	@JSON
	public String queryCODDtoCheckReceivableUnAmount(){
		try {
			
			if(null == salesPayCODVO || CollectionUtils.isEmpty(salesPayCODVO.getCods())){
				// 查询参数无效
				throw new SettlementException("查询参数无效");
			}
			
			List<CODDto> list = salesPayCODService.queryCODDtoCheckReceivableUnAmount(salesPayCODVO.getCods());
			
			salesPayCODVO.setCods(list);
			return returnSuccess();
		} catch (BusinessException e) {
			LOGGER.error(e.getMessage(), e);
			return returnError(e);
		}
	}
	
	/**
	 * 出纳审核.
	 *
	 * @return the string
	 * @author ibm-guxinhua
	 * @date 2012-10-30 上午9:36:10
	 */
	@JSON
	public String cashierAuditAccounts() {
		try {
			// 查询参数无效
			if (null == salesPayCODVO) {
				throw new SettlementException("查询参数无效");
			}
			// 代收货款实体为空，不能进行审核代收货款操作
			String[] entityIds = salesPayCODVO.getEntityIds();
			if (ArrayUtils.isEmpty(entityIds)) {
				throw new SettlementException("代收货款实体为空，不能进行审核代收货款操作");
			}
			// 营业部审核代收货款
			salesPayCODService.auditBillPayCOD(Arrays.asList(entityIds),
					FossUserContext.getCurrentInfo());
			return returnSuccess();
		} catch (BusinessException e) {
			LOGGER.error(e.getMessage(), e);
			return returnError(e);
		}
	}

	/**
	 * 修改银行帐号.
	 *
	 * @return the string
	 * @author ibm-guxinhua
	 * @date 2012-10-30 上午9:36:10
	 */
	@JSON
	public String changeBankAccounts() {
		try {
			// 查询参数无效
			if (null == salesPayCODVO) {
				throw new SettlementException("查询参数无效");
			}
			
			//查询参数无效
			salesPayCODService.changeBillPayCODBankAccounts(
					// 通过salesPayCODVO创建CODEntity.
					createCODEntityBysalesPayCODVO(salesPayCODVO),
					FossUserContext.getCurrentInfo());
			return returnSuccess();
		} catch (BusinessException e) {
			LOGGER.error(e.getMessage(), e);
			return returnError(e);
		}
	}

	/**
	 * 判断所选账号的银行暂不支持即日退(根据银行编码查询是否支持即日退)
	 * 
	 * @author guxinhua
	 * @param 
	 * @date 2012-12-29 上午10:19:40
	 * @return
	 */
	@JSON
	public String queryBankIntraDayTypeByBankCode() {
		try {
			// 查询参数无效
			if (null == salesPayCODVO || StringUtils.isBlank(salesPayCODVO.getBankCode())) {
				throw new SettlementException("查询参数无效");
			}

			BankEntity entity = new BankEntity();
			// 银行编码
			entity.setCode(salesPayCODVO.getBankCode());
			// 根据银行编码查询是否支持即日退
			boolean bool = salesPayCODService.checkBankIntraDayTypeByBankEntity(entity);
			if (bool){
				// 支持即日退
				salesPayCODVO.setIntraDayType(FossConstants.YES);
			} else {
				// 不支持即日退
				salesPayCODVO.setIntraDayType(FossConstants.NO);
			}
			return returnSuccess();
		} catch (BusinessException e) {
			LOGGER.error(e.getMessage(), e);
			return returnError(e);
		}
	} 
	
	/**
	 * 经理审核同意.
	 *
	 * @return the string
	 * @author ibm-guxinhua
	 * @date 2012-10-30 上午9:36:10
	 */
	@JSON
	public String agreeChangeAccounts() {
		try {
			if (null == salesPayCODVO) {
				// 查询参数无效
				throw new SettlementException("查询参数无效");
			}

			String[] entityIds = salesPayCODVO.getEntityIds();
			// 代收货款实体为空，不能进行经理审核代收货款操作
			if (ArrayUtils.isEmpty(entityIds)) {
				throw new SettlementException("代收货款实体为空，不能进行经理审核代收货款操作");
			}

			String password = salesPayCODVO.getPassword();
			// 密码不能为空
			if (StringUtils.isBlank(password)) {
				throw new SettlementException("密码不能为空");
			}
			// 代收货款经理审核同意
			salesPayCODService.agreeChangeBankAccounts(
					Arrays.asList(entityIds), FossUserContext.getCurrentInfo(), password );

			return returnSuccess();
		} catch (BusinessException e) {
			LOGGER.error(e.getMessage(), e);
			return returnError(e);
		}
	}

	/**
	 * 经理审核退回.
	 *
	 * @return the string
	 * @author ibm-guxinhua
	 * @date 2012-10-30 上午9:36:10
	 */
	@JSON
	public String denyChageAccounts() {
		try {
			if (null == salesPayCODVO) {
				// 查询参数无效
				throw new SettlementException("查询参数无效");
			}

			String[] entityIds = salesPayCODVO.getEntityIds();
			if (ArrayUtils.isEmpty(entityIds)) {
				// 代收货款实体为空，不能进行经理审核代收货款操作
				throw new SettlementException("代收货款实体为空，不能进行经理审核代收货款操作");
			}

			String note = salesPayCODVO.getRefundNotes();
			if (StringUtils.isBlank(note)) {
				// 退回原因不能为空
				throw new SettlementException("退回原因不能为空");
			}
			// 代收货款经理审核拒绝
			salesPayCODService.denyChangeBankAccounts(Arrays.asList(entityIds),
					FossUserContext.getCurrentInfo(), note);

			return returnSuccess();
		} catch (BusinessException e) {
			LOGGER.error(e.getMessage(), e);
			return returnError(e);
		}
	}

	/**
	 * 代收货款信息——出发申请查询(账号管理).
	 *
	 * @return the string
	 * @author ibm-guxinhua
	 * @date 2012-10-30 上午9:36:10
	 */
	@JSON
	public String querySalesPayCOD() {
		try {
			if (null == salesPayCODVO) {
				// 查询参数无效
				throw new SettlementException("查询参数无效");
			}

			//运单号集合
			List<String> waybillNos = null;
			//代收货款状态集合
			List<String> statuses = new ArrayList<String>();
			//代收货款集合
			List<CODDto> salesPayCODVOList = null;
			//总条数
			int salesPayCODVOTotalRecords = 0;
			//运单号字符串
			String waybillNoStr = salesPayCODVO.getWaybillNos();
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			
			if (!StringUtils.isEmpty(waybillNoStr)) {// 输入单号进行查询，进行查询。
				// 增加单号
				waybillNos = new ArrayList<String>();
				//运单号字符串转运单号集合
				String[] waybillNoArray = StringUtils.split(waybillNoStr,',');
				for (String waybillNo : waybillNoArray) {
					waybillNos.add(waybillNo);
				}
				// AG待审核,RF退款失败,SF营业部冻结,NR未退款
				statuses.add(SettlementDictionaryConstants.COD__STATUS__APPROVING); // 添加默认状态
				statuses.add(SettlementDictionaryConstants.COD__STATUS__RETURN_FAILURE);
				statuses.add(SettlementDictionaryConstants.COD__STATUS__SHIPPER_FREEZE);
				statuses.add(SettlementDictionaryConstants.COD__STATUS__NOT_RETURN);

				// 按运单号、代收货款状态、应付部门查询代收货款
				salesPayCODVOList = codCommonService.queryBillCODByWaybillNo(
						waybillNos, statuses, currentInfo);
				salesPayCODVOTotalRecords = salesPayCODVOList.size();
			} else {
				
				/**
				 * @author 218392 zhangyongxue 2015-11-09 14:37:00
				 */
				String timeType = salesPayCODVO.getTimeType();//是否按业务日期查询 yes-是；no-否
				Date timeBegin 	= salesPayCODVO.getTimeBegin();//业务日期起始时间
				Date timeEnd	= salesPayCODVO.getTimeEnd();//业务日期结束时间
				//如果按业务日期类型查询
				if(StringUtils.equals("yes", timeType)){
					if(null == timeBegin){
						return returnError("开始时间不能为空！");
					}
					if(null == timeEnd){
						return returnError("结束时间不能为空！");
					}
					// 返回两个时间的相差天数
					long diffDays = DateUtils.getTimeDiff(timeBegin, timeEnd);
					// 查询日期不能大于
					// 31天
					if (diffDays > SettlementConstants.DATE_LIMIT_DAYS_MONTH) {
						return returnError("查询日期不能大于"
								+ SettlementConstants.DATE_LIMIT_DAYS_MONTH + "天!");
					}
					// 录入结束时间必须大于录入开始时间!
					if (diffDays < 0) {
						return returnError("结束时间必须大于开始时间!");//@218392 zhangyongxue 2015-11-09
					}
					// Check whether this category is
					// enabled for the DEBUG Level.
					if (LOGGER.isDebugEnabled()) {
						LOGGER.debug("startTime:" + timeBegin);
						LOGGER.debug("endTime:" + timeEnd);
					}
					// 返回该日期加1秒
					salesPayCODVO.setTimeEnd(DateUtils
							.getEndTimeAdd(timeEnd));
				}
				//@author 218392 zhangyongxue 2015-11-09
				if(StringUtils.equals("no", timeType)){
					salesPayCODVO.setTimeType("");
				}
				
				
				String statusStr = salesPayCODVO.getStatus();
				if (!StringUtils.isEmpty(statusStr)) {
					// 增加代收货款状态
					String[] statusArray = StringUtils.split(statusStr,',');
					for (String status : statusArray) {
						statuses.add(status);
					}
				} else {
					// 代收货款状态为空，不能进行出发申请查询
					throw new SettlementException("代收货款状态为空，不能进行出发申请查询");

				}
				// 出发申请查询代收货款大小
				salesPayCODVOTotalRecords = salesPayCODService
						.queryStartApplyBillCODSizeZyx(currentInfo, statuses,salesPayCODVO);
				if (salesPayCODVOTotalRecords > 0) { // 判断总条数>0，查询数据
					//出发申请查询代收货款
					//【应付部门、代收货款状态、分页号都不允许为空】
					salesPayCODVOList = salesPayCODService
							.queryStartApplyBillCODZyx(currentInfo, statuses,
									getStart(), getLimit(),salesPayCODVO);
				} else {
					// 这里不做处理
				}

			}
			salesPayCODVO.setCods(salesPayCODVOList);// 设置到VO对象中
			salesPayCODVO.setTotalRecords(salesPayCODVOTotalRecords);
			return returnSuccess();
		} catch (BusinessException e) {
			LOGGER.error(e.getMessage(), e);
			return returnError(e);
		}
	}

	/**
	 * 代收货款信息——审核支付申请查询(审核).
	 *
	 * @return the string
	 * @author ibm-guxinhua
	 * @date 2012-10-30 上午9:36:10
	 */
	@JSON
	public String queryManagerAuditCOD() {
		try {
			if (null == salesPayCODVO) {
				throw new SettlementException("查询参数无效");
			}

			List<CODDto> salesPayCODVOList = null;
			int salesPayCODVOTotalRecords = 0;

			List<String> statuses = new ArrayList<String>();
			String statusStr = SettlementDictionaryConstants.COD__STATUS__CASHIER_APPROVE;// 出纳收银员审核
			statuses.add(statusStr);// 增加代收货款状态

			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();

			if (StringUtils.equals(QUERY_CONDITION_ALL,salesPayCODVO.getQueryCondition())) {// 查询所有
				//出发申请查询代收货款大小
				//【应付部门、代收货款状态都不允许为空】
				salesPayCODVOTotalRecords = salesPayCODService
						.queryStartApplyBillCODSize(currentInfo, statuses);
				if (salesPayCODVOTotalRecords > 0) {
					//出发申请查询代收货款
					//【应付部门、代收货款状态、分页号都不允许为空】
					salesPayCODVOList = salesPayCODService
							.queryStartApplyBillCOD(currentInfo, statuses,
									getStart(), getLimit());
				} else {
					// 这里不做处理
				}
			} else if (StringUtils.equals(QUERY_CONDITION_WAYBILL,salesPayCODVO
					.getQueryCondition())) {// 按照单号查询

				// 增加单号
				String waybillNoArrStr = salesPayCODVO.getWaybillNos();
				if (StringUtils.isBlank(waybillNoArrStr)) {
					//运单号集合为空，不能按运单号查询
					throw new SettlementException("运单号集合为空，不能按运单号查询");
				}
				// 运单号字符串
				// 转化为运单号集合
				List<String> waybillNoList = new ArrayList<String>();
				String[] waybillNoArr = StringUtils.split(waybillNoArrStr,',');
				for (String waybillNo : waybillNoArr) {
					waybillNoList.add(waybillNo);
				}
				// 按运单号、代收货款状态、应付部门查询代收货款
				salesPayCODVOList = codCommonService.queryBillCODByWaybillNo(
						waybillNoList, statuses, currentInfo);
				salesPayCODVOTotalRecords = salesPayCODVOList.size();
			} else {
				throw new SettlementException("查询参数无效");
			}

			salesPayCODVO.setCods(salesPayCODVOList);// 设置到VO对象中
			salesPayCODVO.setTotalRecords(salesPayCODVOTotalRecords);
			return returnSuccess();
		} catch (BusinessException e) {
			LOGGER.error(e.getMessage(), e);
			return returnError(e);
		}
	}

	/**
	 * 通过salesPayCODVO创建CODEntity.
	 *
	 * @param salesPayCODVO the sales pay codvo
	 * @return the cOD entity
	 * @author ibm-guxinhua
	 * @date 2012-11-02 下午3:36:10
	 */
	private CODEntity createCODEntityBysalesPayCODVO(CODVo salesPayCODVO) {

		CODEntity entity = new CODEntity();
		// ID
		entity.setId(salesPayCODVO.getId());
		// 运单号
		entity.setWaybillNo(salesPayCODVO.getWaybillNo());
		// 账号
		entity.setAccountNo(salesPayCODVO.getPayeeAccount());
		//开户行编码
		entity.setBankHQCode(salesPayCODVO.getBankCode());
		// 开户行名称
		entity.setBankHQName(salesPayCODVO.getBank());
		// 对公对私标识
		entity.setPublicPrivateFlag(salesPayCODVO.getPublicPrivateFlag());
		// 省编码
		entity.setProvinceCode(salesPayCODVO.getProvinceCode());
		// 城市编码
		entity.setCityCode(salesPayCODVO.getCityCode());
		// 省名称
		entity.setProvinceName(salesPayCODVO.getProvince());
		// 城市名称
		entity.setCityName(salesPayCODVO.getCity());
		// 支行编码
		entity.setBankBranchCode(salesPayCODVO.getBankSubbranchCode());
		// 支行名称
		entity.setBankBranchName(salesPayCODVO.getBankSubbranch());
		// 收款人
		entity.setPayeeName(salesPayCODVO.getPayeeName());
		// 有效
		entity.setActive(FossConstants.ACTIVE);
		// 电话
		entity.setPayeePhone(salesPayCODVO.getPayeePhone());
		// 收款人与发货人关系
		entity.setPayeeRelationship(salesPayCODVO.getPayeeAndConsignor());
		return entity;
	}

	/**
	 * Sets the sales pay cod service.
	 *
	 * @param salesPayCODService the new sales pay cod service
	 */
	public void setSalesPayCODService(ISalesPayCODService salesPayCODService) {
		this.salesPayCODService = salesPayCODService;
	}

	/**
	 * Sets the cod common service.
	 *
	 * @param codCommonService the new cod common service
	 */
	public void setCodCommonService(ICodCommonService codCommonService) {
		this.codCommonService = codCommonService;
	}

	/**
	 * Gets the sales pay codvo.
	 *
	 * @return salesPayCODVO
	 */
	public CODVo getSalesPayCODVO() {
		return salesPayCODVO;
	}

	/**
	 * Sets the sales pay codvo.
	 *
	 * @param salesPayCODVO the new sales pay codvo
	 */
	public void setSalesPayCODVO(CODVo salesPayCODVO) {
		this.salesPayCODVO = salesPayCODVO;
	}

}

package com.deppon.foss.module.settlement.consumer.server.action;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.common.server.util.SettlementUtil;
import com.deppon.foss.module.settlement.consumer.api.server.service.IBillPayCODConfirmService;
import com.deppon.foss.module.settlement.consumer.api.server.service.ICodCommonService;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.CODDto;
import com.deppon.foss.module.settlement.consumer.api.shared.vo.CODPayConfirmVo;

/**
 * 代收货款汇款确认WEB层.
 *
 * @author 046644-foss-zengbinwen
 * @date 2012-11-6 上午10:39:58
 */
public class CODPayConfirmAction extends AbstractAction {

	/** 日志. */
	private static final Logger logger = LogManager
			.getLogger(CODPayConfirmAction.class);

	/** 序列号. */
	private static final long serialVersionUID = 540417242400743198L;

	/** 代收货款汇款确认VO. */
	private CODPayConfirmVo confirmVo;

	/** 代收货款汇款确认服务. */
	private IBillPayCODConfirmService billPayCODConfirmService;

	/** 代收货款服务. */
	private ICodCommonService codCommonService;
	
	/**
	 * 是否全选
	 */
	private String isAllSelect;

	/**
	 * 限制运单个数
	 */
	private static final int NUM_MAX = 50;

	/**
	 * 查询代收货款汇款确认数据.
	 *
	 * @return the string
	 * @author 046644-foss-zengbinwen
	 * @date 2012-11-6 上午10:43:33
	 */
	@JSON
	public String queryPayConfirm() {

		try {

			// 如果查询条件为空，则返回
			if (confirmVo == null) {
				return returnError("代收货款汇款确认查询条件为空");
			}

			// 如果选择的代收货款状态不为空，设置代收货款状态
			if (StringUtils.isNotEmpty(confirmVo.getSelectStatus())) {
				List<String> statuses = new ArrayList<String>();
				statuses.add(confirmVo.getSelectStatus());
				confirmVo.setStatuses(statuses);
			}

			// 设置解析后的开始时间
			if (StringUtils.isNotEmpty(confirmVo.getExportStartTimeString())) {
				confirmVo.setExportStartTime(SettlementUtil
						.parseDateTime(confirmVo.getExportStartTimeString()));
			}

			// 设置解析后的结束时间
			if (StringUtils.isNotEmpty(confirmVo.getExportEndTimeString())) {
				confirmVo.setExportEndTime(SettlementUtil
						.parseDateTime(confirmVo.getExportEndTimeString()));
			}

			// 判断运单号是否为空，如果不为空，则按运单号查询
			String waybillNoString = confirmVo.getWaybillNoString();
			if (StringUtils.isNotEmpty(waybillNoString)) {

				// 运单号不合法，则抛出异常
				if (!SettlementUtil.isValidateWaybillNo(waybillNoString,NUM_MAX)) {
					return returnError("输入的运单号不合法");
				}

				// 按运单号查询
				else {

					// 构造运单号List
					List<String> waybillNoList = new ArrayList<String>();
					String[] waybillNos = waybillNoString.split(",");
					for (String waybillNo : waybillNos) {
						waybillNoList.add(waybillNo);
					}

					// 代收货款状态
					List<String> statuses = new ArrayList<String>();
					statuses.add(SettlementDictionaryConstants.COD__STATUS__RETURNING);
					statuses.add(SettlementDictionaryConstants.COD__STATUS__RETURNED);

					// 按单号查询结果
					List<CODDto> confirmList = codCommonService
							.queryBillOfflineCODByWaybillNo(waybillNoList,
									statuses);

					// 如果不为空，设置结果集
					if (confirmList != null) {
						confirmVo.setCods(confirmList);
						setTotalCount(Long.valueOf(confirmList.size()));
						confirmVo.setTotalAmount(this.sumCodReturnAmount(confirmList));
					}
				}

			}

			// 运单号不为空，按其它条件查询
			else {

				// 查询结果大小，合计金额
				CODDto codDto = billPayCODConfirmService
						.queryPayConfirmSize(confirmVo);

				// 如果结果大小大于0，则查询具体列表
				if (codDto.getTotalCount().longValue() > 0) {
					List<CODDto> confirmList = billPayCODConfirmService
							.queryPayConfirm(confirmVo, getStart(), getLimit());
					confirmVo.setCods(confirmList);
					confirmVo.setTotalAmount(codDto.getTotalAmount());
				}

				setTotalCount(codDto.getTotalCount());
				
			}

		} catch (BusinessException e) {

			logger.error(e.getMessage(), e);
			String error = returnError(e);
			setSuccess(true);
			setException(true);
			// 异常结果返回
			return error;

		}

		return returnSuccess();
	}

	/**
	 * 确认汇款成功.
	 *
	 * @return the string
	 * @author 046644-foss-zengbinwen
	 * @date 2012-11-7 上午10:50:12
	 */
	@SuppressWarnings("unchecked")
	@JSON
	public String confirmSuccess() {

		try {

			// 如果查询条件为空，则返回
			if (confirmVo == null) {
				return returnError("代收货款汇款确认查询条件为空");
			}

			// 判断代收货款实体ID不能为空
			String[] codEntityIds = confirmVo.getCodEntityIds();
			
			
			if (ArrayUtils.isEmpty(codEntityIds)) {
				return returnError("代收货款实体ID为空");
			}

			List<String> entityIds = Arrays.asList(codEntityIds);
			
//			判断是否全选
			if(isAllSelect!=null && isAllSelect.equals("true")){
				List<CODDto> allCod=queryCodByCondition();
				
				
				entityIds = ((List<String>) CollectionUtils.collect(allCod, new Transformer() {
					
					@Override
					public Object transform(Object input) {
						return ((CODDto)input).getId();
					}
				}));
			}
			
			
			// 获取当前用户名
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();

			// 更新代收货款状态
			billPayCODConfirmService.updatePayCODOfflineSuccess(entityIds,
					currentInfo);

		} catch (BusinessException e) {
			logger.error(e.getMessage(), e);
			return returnError(e);
		}

		return returnSuccess();
	}

	/**
	 * 确认汇款失败.
	 *
	 * @return the string
	 * @author 046644-foss-zengbinwen
	 * @date 2012-11-7 上午10:51:44
	 */
	@SuppressWarnings("unchecked")
	@JSON
	public String confirmFailure() {

		try {

			// 如果查询条件为空，则返回
			if (confirmVo == null) {
				return returnError("代收货款汇款确认查询条件为空");
			}

			// 判断代收货款实体ID不能为空
			String[] codEntityIds = confirmVo.getCodEntityIds();
			if (ArrayUtils.isEmpty(codEntityIds)) {
				return returnError("代收货款实体ID为空");
			}

			List<String> entityIds = Arrays.asList(codEntityIds);
			
			
//			判断是否全选
			if(isAllSelect!=null && isAllSelect.equals("true")){
				List<CODDto> allCod=queryCodByCondition();
				
				
				entityIds = ((List<String>) CollectionUtils.collect(allCod, new Transformer() {
					
					@Override
					public Object transform(Object input) {
						return ((CODDto)input).getId();
					}
				}));
			}
			
			String failNotes = confirmVo.getFailNotes();
			// 获取当前用户
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();

			// 更新代收货款状态
			billPayCODConfirmService.updatePayCODOfflineFailure(entityIds,
					currentInfo, failNotes);

		} catch (BusinessException e) {
			logger.error(e.getMessage(), e);
			return returnError(e);
		}

		return returnSuccess();
	}

	/**
	 * 确认反汇款成功.
	 *
	 * @return the string
	 * @author 046644-foss-zengbinwen
	 * @date 2012-11-7 上午10:53:53
	 */
	@SuppressWarnings("unchecked")
	@JSON
	public String confirmAntiRemittance() {

		try {

			// 如果查询条件为空，则返回
			if (confirmVo == null) {
				return returnError("代收货款汇款确认查询条件为空");
			}

			// 判断代收货款实体ID不能为空
			String[] codEntityIds = confirmVo.getCodEntityIds();
			if (ArrayUtils.isEmpty(codEntityIds)) {
				return returnError("代收货款实体ID为空");
			}

			List<String> entityIds = Arrays.asList(codEntityIds);
			
			
//			判断是否全选
			if(isAllSelect!=null && isAllSelect.equals("true")){
				List<CODDto> allCod=queryCodByCondition();
				
				
				entityIds = ((List<String>) CollectionUtils.collect(allCod, new Transformer() {
					
					@Override
					public Object transform(Object input) {
						return ((CODDto)input).getId();
					}
				}));
			}
			
			
			String failNotes = confirmVo.getFailNotes();
			// 获取当前用户
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();

			// 更新代收货款状态
			billPayCODConfirmService.updatePayCODOfflineAntiRemittance(
					entityIds, currentInfo, failNotes);

		} catch (BusinessException e) {
			logger.error(e.getMessage(), e);
			return returnError(e);
		}

		return returnSuccess();
	}

	/**
	 * 
	 * 合计代收货款应退金额
	 * @author guxinhua
	 * @date 2013-3-26 下午1:05:38
	 * @param confirmList
	 * @return
	 */
	private BigDecimal sumCodReturnAmount(List<CODDto> confirmList){
		BigDecimal totalAmount = BigDecimal.ZERO;
		
		for (CODDto codDto : confirmList) {

			if(null == codDto.getReturnAmount() || codDto.getReturnAmount().compareTo(BigDecimal.ZERO) == 0){
				if(null == codDto.getVerifyAmount() || StringUtils.isBlank(String.valueOf(codDto.getVerifyAmount()))){
					totalAmount = totalAmount.add(codDto.getAmount());
				}else{
					// 金额
					totalAmount = totalAmount.add(codDto.getAmount().subtract(codDto.getVerifyAmount()));
				}
			}else{
				// 金额
				totalAmount = totalAmount.add(codDto.getReturnAmount());
			}
			
		}
		
		return totalAmount;
	}
	
	private List<CODDto> queryCodByCondition(){
		List<CODDto> confirmList=new ArrayList<CODDto>();
		// 如果查询条件为空，则返回
		if (confirmVo == null) {
			return null;
		}
		// 判断运单号是否为空，如果不为空，则按运单号查询
		String waybillNoString = confirmVo.getWaybillNoString();
		//判读单号是否为空
		if (StringUtils.isNotEmpty(waybillNoString)) {
			// 运单号不合法，则抛出异常
			if (!SettlementUtil.isValidateWaybillNo(waybillNoString,NUM_MAX)) {
				throw new SettlementException("输入的运单号不合法");
			}

			// 按运单号查询
			else {

				// 构造运单号List
				List<String> waybillNoList = new ArrayList<String>();
				String[] waybillNos = waybillNoString.split(",");
				for (String waybillNo : waybillNos) {
					waybillNoList.add(waybillNo);
				}

				// 代收货款状态
				List<String> statuses = new ArrayList<String>();
				statuses.add(SettlementDictionaryConstants.COD__STATUS__RETURNING);
				statuses.add(SettlementDictionaryConstants.COD__STATUS__RETURNED);

				// 按单号查询结果
				confirmList = codCommonService.queryBillOfflineCODByWaybillNo(waybillNoList,statuses);
				// 如果不为空，设置结果集
				if (confirmList != null) {
					confirmVo.setCods(confirmList);
					setTotalCount(Long.valueOf(confirmList.size()));
					confirmVo.setTotalAmount(this.sumCodReturnAmount(confirmList));
				}
			}

		}
		else {
			// 如果选择的代收货款状态不为空，设置代收货款状态
			if (StringUtils.isNotEmpty(confirmVo.getSelectStatus())) {
				List<String> statuses = new ArrayList<String>();
				statuses.add(confirmVo.getSelectStatus());
				confirmVo.setStatuses(statuses);
			}
	
			// 设置解析后的开始时间
			if (StringUtils.isNotEmpty(confirmVo.getExportStartTimeString())) {
				confirmVo.setExportStartTime(SettlementUtil
						.parseDateTime(confirmVo.getExportStartTimeString()));
			}
	
			// 设置解析后的结束时间
			if (StringUtils.isNotEmpty(confirmVo.getExportEndTimeString())) {
				confirmVo.setExportEndTime(SettlementUtil
						.parseDateTime(confirmVo.getExportEndTimeString()));
			}
			// 查询结果大小，合计金额
			CODDto codDto = billPayCODConfirmService
					.queryPayConfirmSize(confirmVo);
	
			// 如果结果大小大于0，则查询具体列表
			if (codDto.getTotalCount().longValue() > 0) {
				 confirmList = billPayCODConfirmService
						.queryPayConfirm(confirmVo, 0, Integer.valueOf(codDto.getTotalCount().toString()));
			}
		}
		return confirmList;	
	}
	
	
	
	/**
	 * Gets the confirm vo.
	 *
	 * @return confirmVo
	 */
	public CODPayConfirmVo getConfirmVo() {
		return confirmVo;
	}

	/**
	 * Sets the confirm vo.
	 *
	 * @param confirmVo the new confirm vo
	 */
	public void setConfirmVo(CODPayConfirmVo confirmVo) {
		this.confirmVo = confirmVo;
	}

	/**
	 * Sets the bill pay cod confirm service.
	 *
	 * @param billPayCODConfirmService the new bill pay cod confirm service
	 */
	public void setBillPayCODConfirmService(
			IBillPayCODConfirmService billPayCODConfirmService) {
		this.billPayCODConfirmService = billPayCODConfirmService;
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
	 * @return  the isAllSelect
	 */
	public String getIsAllSelect() {
		return isAllSelect;
	}

	/**
	 * @param isAllSelect the isAllSelect to set
	 */
	public void setIsAllSelect(String isAllSelect) {
		this.isAllSelect = isAllSelect;
	}

}

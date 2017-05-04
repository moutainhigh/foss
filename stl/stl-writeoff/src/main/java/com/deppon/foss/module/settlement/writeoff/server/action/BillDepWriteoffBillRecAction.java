/*
 * 党章，是一个政党为保证全党在政治上，思想上的一致和组织上，行动上的统一所制定的章程。
 * 一个党的党章的主要内容应该包括该党的性质、指导思想、纲领任务、组织结构、组织制度，党员的条件、权利、义务和纪律等项。
 * 通常衡量一个政党是否成熟党章也是关键因素之一。党章是政党的宗旨和行为规范。
 * 中国共产党现行党章于中国共产党第十八次全国代表大会部分修改，于2012年11月14日通过。
 * 除总纲外共十一章五十三条。规定了党的纲领、组织机构、组织制度、党员的条件、党员的义务和权利、党的纪律等项.
 */
package com.deppon.foss.module.settlement.writeoff.server.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillDepositReceivedEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableEntity;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.writeoff.api.server.service.IBillDepWriteoffBillRecService;
import com.deppon.foss.module.settlement.writeoff.api.shared.define.SettlementWriteoffConstants;
import com.deppon.foss.module.settlement.writeoff.api.shared.vo.BillDepWriteoffBillRecVo;

/**
 * 预收冲应收Action
 * 
 * @author foss-qiaolifeng
 * @date 2012-10-23 上午9:21:43
 */
public class BillDepWriteoffBillRecAction extends AbstractAction {

	/**
	 * Logger
	 */
	private static final Logger logger = LogManager.getLogger(BillDepWriteoffBillRecAction.class);

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 3003559619424852872L;

	/**
	 * 预收冲应收参数vo
	 */
	private BillDepWriteoffBillRecVo billDepWriteoffBillRecVo;

	/**
	 * 预收冲应收参数service
	 */
	private IBillDepWriteoffBillRecService billDepWriteoffBillRecService;

	/**
	 * 查询预收单
	 * 
	 * @author foss-qiaolifeng
	 * @date 2012-11-22 上午11:42:23
	 */
	@JSON
	public String queryBillDep() {

		logger.debug("查询预收单开始...");

		try {
			// 设置参数查询条件
			setQueryConditionForByParams();
			// 设置单号查询条件
			setQueryConditionForByNos();

			// 获取当前登录用户信息
			CurrentInfo cInfo = FossUserContext.getCurrentInfo();
			//设置登录用户员工编码
			billDepWriteoffBillRecVo.getBillDepositReceivedDto().setEmpCode(cInfo.getEmpCode());
			
			// 根据应收单号、预收单号获取用于可用于核销的应收、预收单信息
			billDepWriteoffBillRecVo.setBillDepWriteoffBillRecDto(billDepWriteoffBillRecService.queryBillDep(billDepWriteoffBillRecVo.getBillDepositReceivedDto()));
			//正常返回
			return returnSuccess();
		//异常处理	
		} catch (BusinessException e) {
			//记录日志
			logger.error(e.getMessage(), e);
			//异常返回
			return returnError(e);
		}
	}

	/**
	 * 查询应收单
	 * 
	 * @author foss-qiaolifeng
	 * @date 2012-11-22 下午1:21:07
	 */
	@JSON
	public String queryBillRec() {

		logger.debug("查询应收单开始...");
		try {
			// 设置参数查询条件
			setQueryConditionForByParams();
			// 设置单号查询条件
			setQueryConditionForByNos();

			// 获取当前登录用户信息
			CurrentInfo cInfo = FossUserContext.getCurrentInfo();
			//设置登录用户员工编码
			billDepWriteoffBillRecVo.getBillReceivableDto().setEmpCode(cInfo.getEmpCode());
			
			// 根据应收单号、预收单号获取用于可用于核销的应收、预收单信息
			billDepWriteoffBillRecVo.setBillDepWriteoffBillRecDto(billDepWriteoffBillRecService.queryBillRec(billDepWriteoffBillRecVo.getBillReceivableDto()));

			//正常返回
			return returnSuccess();
		//异常处理	
		} catch (BusinessException e) {
			//记录日志
			logger.error(e.getMessage(), e);
			//异常返回
			return returnError(e);
		}
	}

	/**
	 * 会计核销 预收冲应收
	 * 
	 * @author foss-qiaolifeng
	 * @date 2012-10-18 下午2:07:07
	 */
	@JSON
	public String writeoffBillDepAndBillRecByAccounting() {

		try {
			logger.debug("会计核销预收冲应收开始...");

			// 设置参数查询条件
			setQueryConditionForByParams();
			// 设置单号查询条件
			setQueryConditionForByNos();

			// 获取界面选择的待核销预收单
			List<BillDepositReceivedEntity> billDepositReceivedEntityListFrom = billDepWriteoffBillRecVo.getBillDepositreceivedEntityList();
			// 获取界面选择的待核销应收单
			List<BillReceivableEntity> billReceivableEntityListFrom = billDepWriteoffBillRecVo.getBillReceivableEntityList();

			// 根据条件重新Load待核销预收单
			List<BillDepositReceivedEntity> billDepositReceivedEntityListAll = null;
			// 根据条件重新Load待核销应收单
			List<BillReceivableEntity> billReceivableEntityListAll = null;
			
			/* BUG-44001
			 * 核销时添加按运单号查询应收单的功能
			 * 
			 * 杨书硕 2013-7-15 下午9:34:28
			 */

			//应收单号是否为空
			boolean isReceivableNosNotEmpty = CollectionUtils.isNotEmpty(billDepWriteoffBillRecVo.getBillReceivableDto().getReceivableNos());
			
			//应收单对应运单号是否为空
			boolean isReceivableWaybillNosNotEmpty = CollectionUtils.isNotEmpty(billDepWriteoffBillRecVo.getBillReceivableDto().getWaybillNoList());
			
			//预收单号是否为空
			boolean isPrecollectedNosNotEmpty = CollectionUtils.isNotEmpty(billDepWriteoffBillRecVo.getBillDepositReceivedDto().getPrecollectedNos());
			
			//预收单号不为空，应收单号和应收单对应运单号至少存在一个则按单号查询
			if ((isReceivableNosNotEmpty||isReceivableWaybillNosNotEmpty)&&isPrecollectedNosNotEmpty) {

				//按单号查询预收单
				billDepositReceivedEntityListAll = billDepWriteoffBillRecService.queryByDepositReceivedNOs(billDepWriteoffBillRecVo.getBillDepositReceivedDto());
				
				//按单号查询应收单
				billReceivableEntityListAll = billDepWriteoffBillRecService.queryByReceivableNOs(billDepWriteoffBillRecVo.getBillReceivableDto());

				// 如果输入的预收、应收单号为空，按参数查询
			} else {
				//按参数查询预收单
				billDepositReceivedEntityListAll = billDepWriteoffBillRecService.queryByDepositReceivedParams(billDepWriteoffBillRecVo.getBillDepositReceivedDto());
				//按参数查询预收单
				billReceivableEntityListAll = billDepWriteoffBillRecService.queryByReceivableParams(billDepWriteoffBillRecVo.getBillReceivableDto());
			}

			// 当查询结果不为空时执行核销service
			if (CollectionUtils.isNotEmpty(billDepositReceivedEntityListAll)&& CollectionUtils.isNotEmpty(billReceivableEntityListAll)) {

				// 获取当前登录用户信息
				CurrentInfo cInfo = FossUserContext.getCurrentInfo();

				//执行预收冲应收，并设置返回值
				billDepWriteoffBillRecVo.setBillDepWriteoffBillRecDto(billDepWriteoffBillRecService.writeoffBillDepAndBillRec(billDepositReceivedEntityListFrom,billReceivableEntityListFrom,billDepositReceivedEntityListAll,billReceivableEntityListAll, cInfo));
				//预收单为空提示
			}else if(CollectionUtils.isEmpty(billDepositReceivedEntityListAll)){
				//提示待核销预收单已被其他用户核销或确认对账单
				throw new SettlementException("待核销预收单已被其他用户核销或确认对账单");
			//应收单为空提示	
			}else if(CollectionUtils.isEmpty(billReceivableEntityListAll)){
				//提示待核销应收单已被其他用户核销、确认对账单或存在更改单
				throw new SettlementException("待核销应收单已被其他用户核销、确认对账单或存在更改单");
			}
			
			//正常返回
			return returnSuccess();
		//异常处理	
		} catch (BusinessException e) {
			//记录日志
			logger.error(e.getMessage(), e);
			//异常返回
			return returnError(e);
		}
	}

	/**
	 * 收银员核销 预收冲应收
	 * 
	 * @author foss-qiaolifeng
	 * @date 2012-10-18 下午2:07:07
	 */
	@JSON
	public String writeoffBillDepAndBillRecByCashier() {

		try {
			logger.debug("收银员核销预收冲应收开始...");

			// 设置参数查询条件
			setQueryConditionForByParams();
			// 设置单号查询条件
			setQueryConditionForByNos();

			// 获取界面选择的待核销预收单
			List<BillDepositReceivedEntity> billDepositReceivedEntityListFrom = billDepWriteoffBillRecVo.getBillDepositreceivedEntityList();
			// 获取界面选择的待核销应收单
			List<BillReceivableEntity> billReceivableEntityListFrom = billDepWriteoffBillRecVo.getBillReceivableEntityList();

			// 收银员核销数据时，预收、应收单业务时间不能超过两年
			validateWriteoffDate(billDepositReceivedEntityListFrom,billReceivableEntityListFrom);

			// 根据条件重新Load待核销预收单
			List<BillDepositReceivedEntity> billDepositReceivedEntityListAll = null;
			// 根据条件重新Load待核销应收单
			List<BillReceivableEntity> billReceivableEntityListAll = null;
			
			/* BUG-44001
			 * 核销时添加按运单号查询应收单的功能
			 * 
			 * 杨书硕 2013-7-15 下午9:34:28
			 */

			//应收单号是否为空
			boolean isReceivableNosNotEmpty = CollectionUtils.isNotEmpty(billDepWriteoffBillRecVo.getBillReceivableDto().getReceivableNos());
			
			//应收单对应运单号是否为空
			boolean isReceivableWaybillNosNotEmpty = CollectionUtils.isNotEmpty(billDepWriteoffBillRecVo.getBillReceivableDto().getWaybillNoList());
			
			//预收单号是否为空
			boolean isPrecollectedNosNotEmpty = CollectionUtils.isNotEmpty(billDepWriteoffBillRecVo.getBillDepositReceivedDto().getPrecollectedNos());
			
			//预收单号不为空，应收单号和应收单对应运单号至少存在一个则按单号查询
			if ((isReceivableNosNotEmpty||isReceivableWaybillNosNotEmpty)&&isPrecollectedNosNotEmpty) {
				//按单号查询预收单
				billDepositReceivedEntityListAll = billDepWriteoffBillRecService.queryByDepositReceivedNOs(billDepWriteoffBillRecVo.getBillDepositReceivedDto());
				//按单号查询应收单
				billReceivableEntityListAll = billDepWriteoffBillRecService.queryByReceivableNOs(billDepWriteoffBillRecVo.getBillReceivableDto());

				// 如果输入的预收、应收单号为空，按参数查询
			} else {
				//按参数查询预收单
				billDepositReceivedEntityListAll = billDepWriteoffBillRecService.queryByDepositReceivedParams(billDepWriteoffBillRecVo.getBillDepositReceivedDto());
				//按参数查询应收单
				billReceivableEntityListAll = billDepWriteoffBillRecService.queryByReceivableParams(billDepWriteoffBillRecVo.getBillReceivableDto());
			}

			// 当查询结果不为空时执行核销service
			if (CollectionUtils.isNotEmpty(billDepositReceivedEntityListAll)&& CollectionUtils.isNotEmpty(billReceivableEntityListAll)) {

				// 获取当前登录用户信息
				CurrentInfo cInfo = FossUserContext.getCurrentInfo();
				//执行预收冲应收，并设置返回值
				billDepWriteoffBillRecVo.setBillDepWriteoffBillRecDto(billDepWriteoffBillRecService.writeoffBillDepAndBillRec(billDepositReceivedEntityListFrom,billReceivableEntityListFrom,billDepositReceivedEntityListAll,billReceivableEntityListAll, cInfo));
			//预收单为空提示
			}else if(CollectionUtils.isEmpty(billDepositReceivedEntityListAll)){
				//提示待核销预收单已被其他用户核销或确认对账单
				throw new SettlementException("待核销预收单已被其他用户核销或确认对账单");
			//应收单为空提示	
			}else if(CollectionUtils.isEmpty(billReceivableEntityListAll)){
				//提示待核销应收单已被其他用户核销、确认对账单或存在更改单
				throw new SettlementException("待核销应收单已被其他用户核销、确认对账单或存在更改单");
			}

			//正常返回
			return returnSuccess();
		//异常处理	
		} catch (BusinessException e) {
			//记录日志
			logger.error(e.getMessage(), e);
			//异常返回
			return returnError(e);
		}
	}

	/**
	 * 为按参数查询设置查询条件
	 * 
	 * @author foss-qiaolifeng
	 * @date 2012-10-30 上午8:47:17
	 */
	private void setQueryConditionForByParams() {

		// 业务结束时间设置，使执行时结束日期等于结束日期+1天
		if (billDepWriteoffBillRecVo.getBillDepositReceivedDto().getEndBusinessDate() != null) {

			//实现结束日期加1天
			Date dateTemp = DateUtils.addDays(billDepWriteoffBillRecVo.getBillDepositReceivedDto().getEndBusinessDate(), 1);

			//设置预收参数Dto的结束时间参数
			billDepWriteoffBillRecVo.getBillDepositReceivedDto().setEndBusinessDate(dateTemp);

		}
		
		// 业务结束时间设置，使执行时结束日期等于结束日期+1天
		if (billDepWriteoffBillRecVo.getBillReceivableDto().getEndBusinessDate() != null) {

			//实现结束日期加1天
			Date dateTemp = DateUtils.addDays(billDepWriteoffBillRecVo.getBillReceivableDto().getEndBusinessDate(), 1);

			//设置应收参数Dto的结束时间参数
			billDepWriteoffBillRecVo.getBillReceivableDto().setEndBusinessDate(dateTemp);
		}

		// 记账结束时间设置，使执行时结束日期等于结束日期+1天
		if (billDepWriteoffBillRecVo.getBillDepositReceivedDto()
				.getEndAccountDate() != null) {

			//实现结束日期加1天
			Date dateTemp = DateUtils.addDays(billDepWriteoffBillRecVo.getBillDepositReceivedDto().getEndAccountDate(), 1);
			//设置预收参数Dto的结束时间参数
			billDepWriteoffBillRecVo.getBillDepositReceivedDto().setEndAccountDate(dateTemp);
		}
		
		// 记账结束时间设置，使执行时结束日期等于结束日期+1天
		if (billDepWriteoffBillRecVo.getBillReceivableDto().getEndAccountDate() != null) {

			//实现结束日期加1天
			Date dateTemp = DateUtils.addDays(billDepWriteoffBillRecVo.getBillReceivableDto().getEndAccountDate(), 1);

			//设置应收参数Dto的结束时间参数
			billDepWriteoffBillRecVo.getBillReceivableDto().setEndAccountDate(dateTemp);
		}

		// 待查数据部门必须等于登录用户操作部门
		String deptCode = FossUserContext.getCurrentInfo().getCurrentDeptCode();
		//设置预收参数Dto的当前部门参数
		billDepWriteoffBillRecVo.getBillDepositReceivedDto().setCollectionOrgCode(deptCode);
		//设置应收参数Dto的当前部门参数
		billDepWriteoffBillRecVo.getBillReceivableDto().setGeneratingOrgCode(deptCode);
	}

	/**
	 * 为按单号查询设置查询条件
	 * 
	 * @author foss-qiaolifeng
	 * @date 2012-10-30 上午8:47:17
	 */
	private void setQueryConditionForByNos() {

		// 获取应收单号并分割处理
		if (StringUtils.isNotEmpty(billDepWriteoffBillRecVo.getBillReceivableDto().getReceivableNumbers())) {
			
			/* BUG-40786 添加按照运单号查询应收单的功能
			 * 
			 * 杨书硕 2013-7-8 上午8:53:35
			 */
			
			//分割单号字符串
			String[] numbers = billDepWriteoffBillRecVo.getBillReceivableDto().getReceivableNumbers().split("[\\s|.|\\;|\\,|，|。|；]+");
			
			//应收单号列表
			List<String> recNoList = new ArrayList<String>();
			
			//运单号列表
			List<String> wayNoList = new ArrayList<String>();
			
			//分开应收单号和运单号到两个List中
			for(String n:numbers){
				if(n.matches("YS\\d{6,}")){
					recNoList.add(n);
				} else if(n.matches("\\d{6,}")){
					wayNoList.add(n);
				}
			}
			
			//设置应收单Dto的应收单号
			billDepWriteoffBillRecVo.getBillReceivableDto().setReceivableNos(recNoList);
			
			//设置应收单Dto的运单号
			billDepWriteoffBillRecVo.getBillReceivableDto().setWaybillNoList(wayNoList);
			
			//如果应收单号不为空，根据,或;分割应收单号
			//String[] recNumbers = billDepWriteoffBillRecVo.getBillReceivableDto().getReceivableNumbers().split(",|;");
			//将应收单号数据转为存入list
//			List<String> receivableNoList = new ArrayList<String>();
//			for (int i = 0; i < recNumbers.length; i++) {
//				receivableNoList.add(recNumbers[i].trim());
//			}
			//设置应收参数dto的应收单号
//			billDepWriteoffBillRecVo.getBillReceivableDto().setReceivableNos(receivableNoList);

		}

		// 获取预收单号并分割处理
		if (StringUtils.isNotEmpty(billDepWriteoffBillRecVo.getBillDepositReceivedDto().getPrecollectedNumbers())) {
			//如果预收单号不为空，根据,或;分割预收单号
			String[] numbers = billDepWriteoffBillRecVo.getBillDepositReceivedDto().getPrecollectedNumbers().split(",|;");
			//将预收单号数据转为存入list
			List<String> precollectedNoList = new ArrayList<String>();
			for (int i = 0; i < numbers.length; i++) {
				precollectedNoList.add(numbers[i].trim());
			}
			//设置预收参数dto的预收单号
			billDepWriteoffBillRecVo.getBillDepositReceivedDto().setPrecollectedNos(precollectedNoList);

		}

		// 待查数据部门必须等于登录用户操作部门
		String deptCode = FossUserContext.getCurrentInfo().getCurrentDeptCode();
		//设置预收参数Dto的当前部门参数
		billDepWriteoffBillRecVo.getBillDepositReceivedDto().setCollectionOrgCode(deptCode);
		//设置应收参数Dto的当前部门参数
		billDepWriteoffBillRecVo.getBillReceivableDto().setGeneratingOrgCode(deptCode);
	}

	/**
	 * 收银员核销数据时，预收、应收单业务时间不能超过两年
	 * 
	 * @author foss-qiaolifeng
	 * @date 2012-11-14 上午10:24:13
	 */
	private void validateWriteoffDate(List<BillDepositReceivedEntity> billDepositReceivedEntityListFrom,List<BillReceivableEntity> billReceivableEntityListFrom) {

		// 收银员核销数据时，预收、应收单业务时间不能超过两年
		Date nowDate = new Date();
		//设置限制时间点
		Date beforeTwoYearDate = DateUtils.addYears(nowDate,-SettlementWriteoffConstants.WRITEOFF_LIMIT_DATE_CASHIER);
		
		//循环预收单
		for (BillDepositReceivedEntity entity : billDepositReceivedEntityListFrom) {
			//如果预收单业务时间早于设置的限制时间点
			if (entity.getBusinessDate().before(beforeTwoYearDate)) {
				//提示收银员不能核销业务时间超过两年的预收单
				throw new SettlementException("收银员不能核销业务时间超过两年的预收单!");
			}
		}
		//循环应收单
		for (BillReceivableEntity entity : billReceivableEntityListFrom) {
			//如果应收单业务时间早于设置的限制时间点
			if (entity.getBusinessDate().before(beforeTwoYearDate)) {
				//提示收银员不能核销业务时间超过两年的应收单
				throw new SettlementException("收银员不能核销业务时间超过两年的应收单!");
			}
		}
	}

	
	/**
	 * @get
	 * @return billDepWriteoffBillRecVo
	 */
	public BillDepWriteoffBillRecVo getBillDepWriteoffBillRecVo() {
		/*
		 * @get
		 * @return billDepWriteoffBillRecVo
		 */
		return billDepWriteoffBillRecVo;
	}

	
	/**
	 * @set
	 * @param billDepWriteoffBillRecVo
	 */
	public void setBillDepWriteoffBillRecVo(
			BillDepWriteoffBillRecVo billDepWriteoffBillRecVo) {
		/*
		 *@set
		 *@this.billDepWriteoffBillRecVo = billDepWriteoffBillRecVo
		 */
		this.billDepWriteoffBillRecVo = billDepWriteoffBillRecVo;
	}

	
	/**
	 * @set
	 * @param billDepWriteoffBillRecService
	 */
	public void setBillDepWriteoffBillRecService(
			IBillDepWriteoffBillRecService billDepWriteoffBillRecService) {
		/*
		 *@set
		 *@this.billDepWriteoffBillRecService = billDepWriteoffBillRecService
		 */
		this.billDepWriteoffBillRecService = billDepWriteoffBillRecService;
	}



}

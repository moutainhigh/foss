package com.deppon.foss.module.settlement.common.server.service.impl;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;

import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.base.dict.api.shared.define.ConfigurationParamsConstants;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.server.dao.IBillCashCollectionEntityDao;
import com.deppon.foss.module.settlement.common.api.server.service.IBillCashCollectionService;
import com.deppon.foss.module.settlement.common.api.server.service.IWaybillChangeMsgService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementReportNumber;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillCashCollectionEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.WaybillChangeMsgEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillCashCollectionDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.util.NumberUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 
 * 现金收款单公共服务实现类
 * 
 * @author ibm-zhuwei
 * @date 2012-10-10 下午2:51:31
 * @since
 * @version
 */
public class BillCashCollectionService implements IBillCashCollectionService {

	/**
	 * 日志 logger
	 */
	private static final Logger logger = LogManager.getLogger(BillCashCollectionService.class);
	
	/**
	 * 现金收款单Dao实现
	 */
	private IBillCashCollectionEntityDao billCashCollectionEntityDao;

	/**
	 * 系统配置参数服务
	 */
	private IConfigurationParamsService configurationParamsService;
	
	/**
	 * 财务变更消息实体Service
	 */
	private IWaybillChangeMsgService waybillChangeMsgService;

	// -------------------- getter/setter --------------------

	/**
	 * set dao
	 * @author 099995-foss-wujiangtao
	 * @date 2013-3-14 上午11:24:10
	 * @param billCashCollectionEntityDao
	 */
	public void setBillCashCollectionEntityDao(
			IBillCashCollectionEntityDao billCashCollectionEntityDao) {
		this.billCashCollectionEntityDao = billCashCollectionEntityDao;
	}

	/**
	 * @param configurationParamsService
	 */
	public void setConfigurationParamsService(
			IConfigurationParamsService configurationParamsService) {
		this.configurationParamsService = configurationParamsService;
	}

	// -------------------- write methods --------------------

	/**
	 * 生成现金收款单
	 * @author foss-wangxuemin
	 * @date Apr 19, 2013 4:13:19 PM
	 */
	private void add(BillCashCollectionEntity entity){
		// FOSS生成的所有单据的初始化状态都为"N"
		entity.setIsInit(FossConstants.NO);
		//调用dao层的保存现金收款单方法
		int i = billCashCollectionEntityDao.add(entity);
		//不等于1抛出异常，说明数据库添加失败
		if (i != 1) {
			throw new SettlementException("生成现金收款单出错");
		}
	}
	/**
	 * 新增现金收款单
	 * @author ibm-zhuwei
	 * @date 2012-10-17 下午1:25:14
	 * @param entity
	 *      现金收款单实体
	 * @param currentInfo
	 *      当前操作者
	 */
	@Override
	public void addBillCashCollection(BillCashCollectionEntity entity, CurrentInfo currentInfo) {
		
		// 输入参数校验
		if (entity == null || StringUtils.isEmpty(entity.getSourceBillNo())) {
			throw new SettlementException("新增现金收款单出错");
		}
		logger.info("调用新增现金收款单实体参数信息："+ReflectionToStringBuilder.toString(entity));
		logger.info("调用新增现金收款单实体参数信息："+ReflectionToStringBuilder.toString(currentInfo));
		logger.info("entering service, sourceBillNo: " + entity.getSourceBillNo());
		
		// 获取付款方式
		String paymentType = entity.getPaymentType();
		
		//付款方式不为现金或者银行卡时，抛出异常信息
		if ( !SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CASH.equals(paymentType)
				&& !SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CARD.equals(paymentType)) {
			throw new SettlementException("运单和小票的付款方式不正确，付款方式必须为现金或银行卡，不能进行新增现金收款单操作！");
		}
		
		// 金额不能为空
		if (entity.getAmount() == null) {
			throw new SettlementException("应付单金额为空，不能进行新增现金收款单操作！");
		}
		
		//金额必须大于0
		if (entity.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
			throw new SettlementException("应收金额小于等于0，不能进行新增现金收款单操作");
		}
		
		//来源单据类型为运单的，需要对费用信息进行校验
		if (SettlementDictionaryConstants.BILL_CASH_COLLECTION__SOURCE_BILL_TYPE__WAYBILL
				.equals(entity.getSourceBillType())) {	
			validaCollection(entity);
		} 
		
		//new一个时间
		validaBillCash(entity, currentInfo);
		
		logger.info("successfully exit service, sourceBillNo: " + entity.getSourceBillNo());
	}

	private void validaCollection(BillCashCollectionEntity entity) {
		// 运单

// 明细费用之和等于增值业务费
BigDecimal valueAddFee = NumberUtils.sum(entity.getPickupFee(),
			entity.getDeliveryGoodsFee(), entity.getPackagingFee(),
			entity.getCodFee(), entity.getInsuranceFee(),
			entity.getOtherFee());

//判断valueAddFee 是否相等，不等抛出异常信息
if (valueAddFee.compareTo(entity.getValueAddFee()) != 0) {
		throw new SettlementException("明细之和不等于增值业务费，不能进行新增现金收款单操作");
}

// 明细费用之和等于总运费
BigDecimal sum = entity.getTransportFee().add(valueAddFee);
if (sum.compareTo(entity.getAmount()) != 0) {
		throw new SettlementException("明细之和不等于总运费，不能进行新增现金收款单操作");
}

// 产品类型不能为空
if (StringUtils.isEmpty(entity.getProductCode())) {
		throw new SettlementException("产品类型为空，不能进行新增现金收款单操作");
}
	}

	private void validaBillCash(BillCashCollectionEntity entity,
			CurrentInfo currentInfo) {
		Date date = new Date();
		
		//创建时间
		entity.setCreateTime(date);
		
		//操作者编码
		entity.setCreateUserCode(currentInfo.getEmpCode());
		
		//操作者名称
		entity.setCreateUserName(currentInfo.getEmpName());
		
		//创建部门编码
		entity.setCreateOrgCode(currentInfo.getCurrentDeptCode());
		
		//创建部门名称
		entity.setCreateOrgName(currentInfo.getCurrentDeptName());
		
		//修改时间
		entity.setModifyTime(date);
		
	   //新增现金收款单，默认把修改者信息设置为创建者信息
		entity.setModifyUserCode(currentInfo.getEmpCode());
		
		//修改人编码
		entity.setModifyUserName(currentInfo.getEmpName());
		
		//业务日期和记账日期格式化到秒
		//业务日期在记账日期之后 ，提示不能保存
		Date bussinessDate = entity.getBusinessDate();
		Date accountDate = entity.getAccountDate();
		//modify by 354658-duyijun  将业务日期 秒 置为0，解决开单跨系统的时间差问题。
		Calendar cal = Calendar.getInstance();
		cal.setTime(bussinessDate);
		cal.set(Calendar.SECOND, SettlementReportNumber.ZERO);//将秒 置为0
        cal.set(Calendar.MILLISECOND, 000);//将毫秒 置为0
        bussinessDate=cal.getTime();
		//业务日期不能晚于记账日期
		if(bussinessDate.after(accountDate)){
			throw new SettlementException("记账日期小于业务日期，不能进行新增现金收款单操作！");
		}
		
		this.add(entity);
		
		// 单据的运单号和来源单号相同,新增应付单时插入财务变更消息表 +1
		if (StringUtils.isNotEmpty(entity.getWaybillNo())
				&& SettlementDictionaryConstants.BILL_CASH_COLLECTION__SOURCE_BILL_TYPE__WAYBILL
						.equals(entity.getSourceBillType())){
			// 添加财务变更消息方法
			this.addChangeMsg(
					entity.getWaybillNo(),
					entity.getCashCollectionNo(),
					SettlementDictionaryConstants.WAYBILL_CHANGE_MSG__MSG_ACTION__PROCESSING, // 消息类型：新增
					date);
			
			this.addChangeMsg(
					entity.getWaybillNo(),
					entity.getCashCollectionNo(),
					SettlementDictionaryConstants.WAYBILL_CHANGE_MSG__MSG_ACTION__PROCESS_DONE, // 消息类型：处理完成
					date);
		}
	}

	/**
	 * 红冲现金收款单
	 * @author ibm-zhuwei
	 * @date 2012-10-17 下午1:25:27
	 * @param entity
	 * @param currentInfo
	 */
	@Override
	public void writeBackBillCashCollection(BillCashCollectionEntity entity,CurrentInfo currentInfo) {

		// 输入参数校验
		if (entity == null || StringUtils.isEmpty(entity.getSourceBillNo())) {
			throw new SettlementException("红冲现金收款单出错");
		}
		
		logger.info("entering service, sourceBillNo: " + entity.getSourceBillNo());

		//对记账日期进行格式化到秒
		Date now = Calendar.getInstance().getTime();
		
		//调用综合管理接口，查询结算业务最大红冲天数
		String span = configurationParamsService.queryConfValueByCode(ConfigurationParamsConstants.STL_CASH_COLLECTION_WRITEBACK_DAY);
		
		//根据财务的记账日期和配置参数相加
		Date d = DateUtils.addDays(entity.getAccountDate(), Integer.valueOf(span));
		
		// 如果时间差超过30天
		if (now.after(d)) {
			throw new SettlementException("超出结算业务红冲的最大时间范围，不允许进行红冲现金收款单操作");
		}
		BillCashCollectionEntity updateEntity = new BillCashCollectionEntity();
		
		// 作废旧单据// ID
		updateEntity.setId(entity.getId());	
		
		// 分区键
		updateEntity.setAccountDate(entity.getAccountDate());
		
		// 版本号
		updateEntity.setVersionNo(entity.getVersionNo());
		
		//是否有效
		updateEntity.setActive(FossConstants.INACTIVE);
		
		//修改时间
		updateEntity.setModifyTime(now);
		
		// 操作者编码
		updateEntity.setModifyUserCode(currentInfo.getEmpCode());
		
		//操作者名称
		updateEntity.setModifyUserName(currentInfo.getEmpName());
		
		int i = billCashCollectionEntityDao.updateByWriteBack(updateEntity);
		if (i != 1) {
			throw new SettlementException("红冲现金收款单出错");
		}
		
		// 生成红冲单
		BillCashCollectionEntity newEntity = new BillCashCollectionEntity();
		
		//历史单据进行拷贝
		BeanUtils.copyProperties(entity, newEntity);
		
		//设置ID
		newEntity.setId(UUIDUtils.getUUID());
		
		//是否有效-设置为无效
		newEntity.setActive(FossConstants.INACTIVE);
		
		//设置版本号
		newEntity.setVersionNo(FossConstants.INIT_VERSION_NO);
		
		//是否红单：红单
		newEntity.setIsRedBack(SettlementDictionaryConstants.SETTLEMENT__IS_RED_BACK__YES);
		
		//记账日期
		newEntity.setAccountDate(now);
		
		//创建时间
		newEntity.setCreateTime(now);
		
		//修改时间
		newEntity.setModifyTime(now);

		//设置金额为负数
		newEntity.setAmount(NumberUtils.multiply(newEntity.getAmount(), -1));
		
		//在原来基础上乘以-1
		newEntity.setTransportFee(NumberUtils.multiply(newEntity.getTransportFee(), -1));
		
		//在原来基础上乘以-1
		newEntity.setDeliveryGoodsFee(NumberUtils.multiply(newEntity.getDeliveryGoodsFee(), -1));
		
		//接货费
		newEntity.setPickupFee(NumberUtils.multiply(newEntity.getPickupFee(), -1));
		
		//在原来基础上乘以-1
		newEntity.setPackagingFee(NumberUtils.multiply(newEntity.getPackagingFee(), -1));
		
		//在原来基础上乘以-1
		newEntity.setCodFee(NumberUtils.multiply(newEntity.getCodFee(), -1));
		
		//在原来基础上乘以-1
		newEntity.setInsuranceFee(NumberUtils.multiply(newEntity.getInsuranceFee(), -1));
		
		//在原来基础上乘以-1
		newEntity.setOtherFee(NumberUtils.multiply(newEntity.getOtherFee(), -1));
		
		//操作者信息
		newEntity.setModifyUserCode(currentInfo.getEmpCode());
		
		//修改人名称
		newEntity.setModifyUserName(currentInfo.getEmpName());
		
		//收银状态
		newEntity.setStatus(SettlementDictionaryConstants.BILL_CASH_COLLECTION__STATUS__SUBMIT);
		
		newEntity.setCashConfirmTime(null);
		newEntity.setCashConfirmUserCode(null);
		newEntity.setCashConfirmUserName(null);
		
		this.add(newEntity);
		
		logger.info("successfully exit service, sourceBillNo: " + entity.getSourceBillNo());
		
	}

	/**
	 * 运单签收时，调用此方法，确认现金收款单收入
	 * 
	 * 更新现金收款单的收入日期
	 * @author dp-wujiangtao
	 * @date 2012-10-22 下午12:35:19
	 * @param entity
	 * @param currentInfo
	 * @return 
	 */
	@Override
	public void confirmIncomeBillCashCollection(BillCashCollectionEntity entity,CurrentInfo currentInfo) {

		// 输入参数校验
		if (entity == null || StringUtils.isEmpty(entity.getId()) || entity.getConrevenDate() == null
			|| entity.getAccountDate() == null || entity.getVersionNo() == null
			) {
			throw new SettlementException("确认现金收款单收入失败");
		}
		
		logger.info("entering service, id: " + entity.getId());

		Date now = Calendar.getInstance().getTime();
		
		//设置修改时间
		entity.setModifyTime(now);
		
		// 操作者信息
		entity.setModifyUserCode(currentInfo.getEmpCode());
		
		//修改人名称
		entity.setModifyUserName(currentInfo.getEmpName());
		int i = billCashCollectionEntityDao.updateByConfirmIncome(entity);
		
		//不等于1，说明数据库更新失败，抛出异常信息
		if ( i != 1 ) {
			throw new SettlementException("确认现金收款单收入失败");
		}
		
		logger.info("successfully exit service, id: " + entity.getId());
	}

	/**
	 * 反签收时，置空确认收入日期
	 * 
	 * @author dp-wujiangtao
	 * @date 2012-10-22 下午12:40:59
	 * @param entity       现金收款单实体
	 * @param currentInfo  当前操作者
	 * @return
	 */
	@Override
	public void reverseConfirmIncomeBillCashCollection(BillCashCollectionEntity entity,CurrentInfo currentInfo) {
		
		// 输入参数校验
		//试题ID和记账日期+版本号作为更新条件
		if (entity == null || StringUtils.isEmpty(entity.getId())
			|| entity.getAccountDate() == null || entity.getVersionNo() == null
			) {
			throw new SettlementException("反确认现金收款单收入失败");
		}
		
		logger.info("entering service, id: " + entity.getId());

		Date now = Calendar.getInstance().getTime();
		
		//设置修改时间
		entity.setModifyTime(now);
		
		//确认收入日期设置为空
		entity.setConrevenDate(null);
		
		// 操作者信息
		entity.setModifyUserCode(currentInfo.getEmpCode());
		
		//设置修改人名称
		entity.setModifyUserName(currentInfo.getEmpName());
		
		int i = billCashCollectionEntityDao.updateByConfirmIncome(entity);
		
		if ( i != 1 ) {
			throw new SettlementException("反确认现金收款单收入失败");
		}
		
		logger.info("successfully exit service, id: " + entity.getId());
	}

	/**
	 * 批量确认收银
	 * @author ibm-zhuwei
	 * @date 2012-12-17 上午11:17:46
	 * @param dto
	 *        
	 * @param currentInfo
	 */
	@Override
	public void confirmCashierBillCashCollection(BillCashCollectionDto dto, CurrentInfo currentInfo) {
		// 输入参数校验
		if (dto == null || CollectionUtils.isEmpty(dto.getBillCashCollections())) {
			throw new SettlementException("确认收银，现金收款单参数不能为空！");
		}
		
		//大于1000条记录，抛出异常信息
		if (dto.getBillCashCollections().size() > SettlementConstants.MAX_LIST_SIZE) {
			throw new SettlementException("确认收银，现金收款单数量大于"
					+ SettlementConstants.MAX_LIST_SIZE + "不能进行操作。");
		}
		
		logger.info("entering service, ids: " + dto.getBillCashCollections());
		
		//获取一个
		Date now = new Date();

		// 操作者信息
		dto.setModifyTime(now);
		
		//修改人编码
		dto.setModifyUserCode(currentInfo.getEmpCode());
		
		//修改人名称
		dto.setModifyUserName(currentInfo.getEmpName());

		// 现金收款单状态
		dto.setStatus(SettlementDictionaryConstants.BILL_CASH_COLLECTION__STATUS__CONFIRM);
		
		//收银确认时间
		dto.setCashConfirmTime(now);
		
		//确认人编码
		dto.setCashConfirmUserCode(currentInfo.getEmpCode());
		
		//确认人名称
		dto.setCashConfirmUserName(currentInfo.getEmpName());
		
		int i = this.billCashCollectionEntityDao.updateByConfirmCashier(dto);
		
		if (i != dto.getBillCashCollections().size()) {
			throw new SettlementException("确认收银现金收款单失败");
		}
		
		logger.info("successfully exit service, ids: " + dto.getBillCashCollections());
	}
	
	// -------------------- validate methods --------------------
	
	/**
	 * 验证同一个运单是否存在多条现金收款单
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-1 下午7:31:49
	 * @param list
	 *       根据运单号查询出来的现金收款单记录
	 */
	@Override
	public void validateWaybillForBillCashCollection(
			List<BillCashCollectionEntity> list) {
		
		//传入的list集合不为空，进行查询
		if(CollectionUtils.isNotEmpty(list)){
			
			//list大小不能大于1
			if(list.size()>1){
				throw new SettlementException("同一个运单存在多条现金收款单");
			}
		}
	}
	
	// -------------------- read methods --------------------
	
	/**
	 * 根据传入的一到多个现金收款单Id号，获取一到多条现金收款单信息
	 * @author foss-pengzhen
	 * @date 2013-3-26 上午9:26:46
	 * @param cashCollectionIds  现金收款单Id
	 * @return
	 * @see
	 */
	@Override
	public List<BillCashCollectionEntity> queryByCashCollectionIds(
			List<String> cashCollectionIds, String active) {
		// 输入参数校验
		if (CollectionUtils.isEmpty(cashCollectionIds)) {
			throw new SettlementException("查询现金收款单，单据Id不能为空");
		}
		
		logger.debug("entering service, cashCollectionIds: " + cashCollectionIds);
		
		return this.billCashCollectionEntityDao.queryByCashCollectionIds(
				cashCollectionIds, active);
	}
	/**
	 * 
	 * 根据传入的一到多个现金收款单号，获取一到多条现金收款单信息
	 * 
	 * @author dp-wujiangtao
	 * @date 2012-10-12 下午6:44:25
	 * @param cashCollectionNos
	 *            现金收款单号集合
	 * @param active
	 *            是否有效
	 * @return
	 */
	@Override
	public List<BillCashCollectionEntity> queryByCashCollectionNOs(
			List<String> cashCollectionNos, String active) {
		// 输入参数校验
		if (CollectionUtils.isEmpty(cashCollectionNos)) {
			throw new SettlementException("查询现金收款单，输入的单据编号不能为空");
		}
		
		logger.debug("entering service, cashCollectionNos: " + cashCollectionNos);
		
		return this.billCashCollectionEntityDao.queryByCashCollectionNOs(
				cashCollectionNos, active);
	}

	/**
	 * 
	 * 根据传入的一个现金收款单号，获取一条现金收款单信息
	 * 
	 * @author dp-wujiangtao
	 * @date 2012-10-12 下午6:44:33
	 * @param cashCollectionNo
	 *            现金收款单号
	 * @param active
	 *            是否有效
	 * @return
	 */
	@Override
	public BillCashCollectionEntity queryByCashCollectionNO(
			String cashCollectionNo, String active) {
		// 输入参数校验
		if (StringUtils.isEmpty(cashCollectionNo)) {
			throw new SettlementException("查询现金收款单，输入的单号信息不能为空");
		}

		logger.debug("entering service, cashCollectionNo: " + cashCollectionNo);
		
		//调用Dao方法进行查询
		List<BillCashCollectionEntity> list = this.billCashCollectionEntityDao
				.queryByCashCollectionNOs(Arrays.asList(cashCollectionNo), active);
		
		//list不为空，返回第一条记录，同一个现金收款单号，有且只有一条有效的现金收款单数据
		if (CollectionUtils.isNotEmpty(list)) {
			return   (BillCashCollectionEntity) list.get(0);
		}
		
		logger.debug("exit service, cashCollectionNo: " + cashCollectionNo);
		
		return null;
	}

	/**
	 * 根据传入的一到多个来源单号和来源单据类型，获取一到多条现金收款单信息
	 * @author 099995-foss-wujiangtao
	 * @date 2012-10-23 上午8:32:05
	 * @param sourceBillNos
	 *       来源单号
	 * @param sourceBillType
	 *        来源单据类型
	 * @param active
	 *        是否有效
	 */
	@Override
	public List<BillCashCollectionEntity> queryBySourceBillNOs(
			List<String> sourceBillNos, String sourceBillType, String active) {
		// 输入参数校验
		if(CollectionUtils.isEmpty(sourceBillNos)){
			throw new SettlementException("查询现金收款单，输入的来源单号信息不能为空");
		}

		logger.debug("entering service, sourceBillNos: " + sourceBillNos);
		
		return this.billCashCollectionEntityDao.queryBySourceBillNOs(sourceBillNos, sourceBillType, active);
	}

	/**
	 * 根据来源单号集合和部门编码集合，查询现金收款单信息
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-1-22 上午9:27:38
	 * @param sourceBillNos  必填
	 * @param sourceBillType 可为空
	 * @param orgCodes  可为空
	 * @param active    可为空
	 * @param currentInfo 当前操作者信息
	 * @return
	 */
	@Override
	public List<BillCashCollectionEntity> queryBySourceBillNOsAndOrgCodes(
			List<String> sourceBillNos, String sourceBillType,
			List<String> orgCodes, String active, CurrentInfo currentInfo) {
		
		//来源单号不能为空，为空抛出异常信息
		if(CollectionUtils.isEmpty(sourceBillNos)){
			throw new SettlementException("按照来源单号集合和部门编码集合，查询现金收款单信息参数不能为空！");
		}
		return this.billCashCollectionEntityDao.queryBySourceBillNOsAndOrgCodes
				(sourceBillNos, sourceBillType, orgCodes, active,currentInfo);
	}

	
	/**
	 * 新增财务变更消息
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-12-5 上午10:46:37
	 * @param waybillNo
	 * @param payableNo //应付单号
	 * @param msgAction
	 * @param date
	 */
	private void addChangeMsg(String waybillNo,String payableNo,String msgAction,Date date){
		WaybillChangeMsgEntity entity=new WaybillChangeMsgEntity();
		
		//ID
		entity.setId(UUIDUtils.getUUID());
		
		//运单号
		entity.setWaybillNo(waybillNo);
		
		//消息发生日期
		entity.setMsgDate(date);
		
		//消息动作
		entity.setMsgAction(msgAction);
		
		//来源单据类型-应付单
		entity.setSourceBillType(SettlementDictionaryConstants.
				WAYBILL_CHANGE_MSG__SOURCE_BILL_TYPE_CASHCOLLECTION);
		
		//来源单号-应付单号
		entity.setSourceBillNo(payableNo);
		
		this.waybillChangeMsgService.addChangeMsg(entity);
	}
	
	public void setWaybillChangeMsgService(
			IWaybillChangeMsgService waybillChangeMsgService){
		this.waybillChangeMsgService = waybillChangeMsgService;
	}

	/**
     * 根据银联交易流水号，查询现金收款单信息
     * @author 045738-foss-maojianqiang
     * @date 2013-7-22 上午9:18:52
     * @param sourceBillNos
     * @param sourceBillType
     * @param orgCodes
     * @param active
     * @param currentInfo
     * @return
     */
	public List<BillCashCollectionEntity> queryByBatchNos(
			List<String> batchNos, String active, CurrentInfo currentInfo) {
		//校验传入参数
		if(CollectionUtils.isEmpty(batchNos)){
			throw new SettlementException("根据银联交易流水号查询，传入的流水号不能为空！");
		}
		return this.billCashCollectionEntityDao.queryByBatchNos(batchNos, active, currentInfo);
	}
	
}

/*
 * 党章，是一个政党为保证全党在政治上，思想上的一致和组织上，行动上的统一所制定的章程。
 * 一个党的党章的主要内容应该包括该党的性质、指导思想、纲领任务、组织结构、组织制度，党员的条件、权利、义务和纪律等项。
 * 通常衡量一个政党是否成熟党章也是关键因素之一。党章是政党的宗旨和行为规范。
 * 中国共产党现行党章于中国共产党第十八次全国代表大会部分修改，于2012年11月14日通过。
 * 除总纲外共十一章五十三条。规定了党的纲领、组织机构、组织制度、党员的条件、党员的义务和权利、党的纪律等项.
 */
package com.deppon.foss.module.settlement.writeoff.server.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.server.service.ISettlementCommonService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementNoRuleEnum;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.writeoff.api.server.dao.IWoodenStatementDao;
import com.deppon.foss.module.settlement.writeoff.api.server.service.IWoodenStatementService;
import com.deppon.foss.module.settlement.writeoff.api.shared.domain.WoodenStatementDEntity;
import com.deppon.foss.module.settlement.writeoff.api.shared.domain.WoodenStatementEntity;
import com.deppon.foss.module.settlement.writeoff.api.shared.dto.WoodenStatementDto;
import com.deppon.foss.util.CollectionUtils;

public class WoodenStatementService implements IWoodenStatementService {

	/*
	 * 代打木架对账单DAO
	 */
	private IWoodenStatementDao woodenStatementDao;
	/*
	 * 获取结算单据编号服务
	 */
	private ISettlementCommonService settlementCommonService;
	/**
	 * 代打木架对账单新增查询应收应付单
	 * @author ddw
	 * @date 2014-06-16
	 */
	@Override
	public WoodenStatementDto queryWoodenStatementD(WoodenStatementDto dto,int start,int limit) {
		//对账单明细列表
		List<WoodenStatementDEntity> woodenStatementDList = new ArrayList<WoodenStatementDEntity>();
		//对账单DTO
		WoodenStatementDto woodenStatementDto = new WoodenStatementDto();
		//获取当前用户
		CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
		//获取当前用户所在部门
		dto.setOrgCode(currentInfo.getCurrentDeptCode());
		//设置总行数
		int count = 0;
		//判断查询页签，按客户查询，否则按应收应付单号查询
		if(SettlementConstants.TAB_QUERY_BY_DATE.equals(dto.getQueryTabType())){
			//根据时间客户查询应收应付单据
			woodenStatementDList = woodenStatementDao.queryWoodenStatementDByCustomer(dto,start,limit);
			//根据时间客户查询应收应付总行数
			count = woodenStatementDao.countWoodenStatementDByCustomer(dto);
		}else{
			//按单号查询不需要分页，设置总行数为单号个数
			count = dto.getNumbers().size();
			//按应收应付单号查询应收应付单
			woodenStatementDList = woodenStatementDao.queryWoodenStatementDByNumber(dto);
		}
		//设置对账单明细返回列表
		woodenStatementDto.setWoodenStatementDList(woodenStatementDList);
		//设置对账单明细返回总行数
		woodenStatementDto.setCount(count);
		//返回参数
		return woodenStatementDto;
	}
	/**
	 * 查询代打木架对账单
	 * @author ddw
	 * @date 2014-06-16
	 */
	@Override
	public WoodenStatementDto queryWoodenStatement(WoodenStatementDto dto,int start,int limit) {
		//对账单列表
		List<WoodenStatementEntity> woodenStatementList = new ArrayList<WoodenStatementEntity>();
		//对账单DTO
		WoodenStatementDto woodenStatementDto = new WoodenStatementDto();
		//获取当前用户
		CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
		//获取当前用户所在部门
		dto.setOrgCode(currentInfo.getCurrentDeptCode());
		//设置总行数
		int count = 0;
		//判断查询页签，按客户查询，否则按对账单单号查询
		if(SettlementConstants.TAB_QUERY_BY_DATE.equals(dto.getQueryTabType())){
			//根据时间客户查询对账单
			woodenStatementList = woodenStatementDao.queryWoodenStatementByCustomer(dto,start,limit);
			//根据时间客户查询对账单总行数
			count = woodenStatementDao.countWoodenStatementByCustomer(dto);
		}else{
			//按单号查询不需要分页，设置总行数为单号个数
			count = dto.getNumbers().size();
			//按对账单单号查询对账单
			woodenStatementList = woodenStatementDao.queryWoodenStatementByNumber(dto);
		}
		//设置对账单返回列表
		woodenStatementDto.setWoodenStatementList(woodenStatementList);
		//设置对账单返回总行数
		woodenStatementDto.setCount(count);
		//返回参数
		return woodenStatementDto;
	}
	/**
	 * 双击对账单记录查看明细，按对账单单号查询
	 * @author ddw
	 * @date 2014-06-16
	 */
	@Override
	public WoodenStatementDto queryWoodenDByStatementBillNo(WoodenStatementDto dto, int start, int limit) {
		//对账单DTO
		WoodenStatementDto woodenStatementDto = new WoodenStatementDto();
		//按对账单单号分页查询对账单明细
		List<WoodenStatementDEntity> woodenStatementDList = woodenStatementDao.queryWoodenDByStatementBillNo(dto,start,limit);
		//按对账单单号查询对账单明细总行数
		int count = woodenStatementDao.countWoodenDByStatementBillNo(dto);
		//按对账单单号查询所有对账单明细
		List<WoodenStatementDEntity> list = woodenStatementDao.queryWoodenDByStatementBillNo(dto);
		//设置对账单金额
		sumRecAndPayAmount(woodenStatementDto,list);
		//设置对账单明细返回列表
		woodenStatementDto.setWoodenStatementDList(woodenStatementDList);
		//设置对账单明细返回总行数
		woodenStatementDto.setCount(count);
		//返回参数
		return woodenStatementDto;
	}
	/**
	 * 生成代打木架对账单
	 * @author ddw
	 * @date 2014-06-16
	 */
	@Transactional
	@Override
	public WoodenStatementDto woodenStatementSave(WoodenStatementDto woodenStatementDto) {
		//生成对账单单号
		String statementNo = settlementCommonService.getSettlementNoRule(SettlementNoRuleEnum.DZ);
		//设置对账单单号
		woodenStatementDto.setStatementBillNo(statementNo);
		//获取当前用户
		CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
		//获取当前用户工号
		woodenStatementDto.setEmpCode(currentInfo.getEmpCode());
		//获取当前用户姓名
		woodenStatementDto.setEmpName(currentInfo.getEmpName());
		//获取当前用户所在部门
		woodenStatementDto.setOrgCode(currentInfo.getCurrentDeptCode());
		//插入行数
		int insertDRows = 0;
		
		if(SettlementConstants.TAB_QUERY_BY_DATE.equals(woodenStatementDto.getQueryTabType())){
			//判断客户编码是否为空
			if(null == woodenStatementDto.getCustomerCode() || "".equals(woodenStatementDto.getCustomerCode())){
				throw new SettlementException("客户编码为空不可以生成对账单！");
			}
			//按时间客户生成对账单明细
			insertDRows = woodenStatementDao.woodenStatementDSaveByCustomer(woodenStatementDto);
		}else{
			//按单号生成对账单明细
			insertDRows = woodenStatementDao.woodenStatementDSaveByNumber(woodenStatementDto);
		}
		//获取所有生成的对账单明细
		List<WoodenStatementDEntity> list = woodenStatementDao.queryWoodenDByStatementBillNo(woodenStatementDto);
		//设置对账单金额
		sumRecAndPayAmount(woodenStatementDto,list);
		//生成对账单
		int insertRows = woodenStatementDao.woodenStatementSaveByStatementBillNo(woodenStatementDto);
		//判断是否生成一条对账单
		if(insertRows != 1){
			throw new SettlementException("生成对账单失败，插入条数大于1，请确认应收单应付单客户是否一致");
		}
		//更新应付单
		int updatePayRows = woodenStatementDao.woodenPayUpdateByStatementBillNo(woodenStatementDto);
		//更新应收单
		int updateRecRows = woodenStatementDao.woodenRecUpdateByStatementBillNo(woodenStatementDto);
		//判断更新的应收应付单据总条数是否和生成的对账单明细条数是否相等
		if((updatePayRows + updateRecRows) != insertDRows){
			throw new SettlementException("对账单明细生成条数和应付单应收单生成条数不一致，请重新查询！");
		}
		//返回参数
		return woodenStatementDto;
	}
	/**
	 * 设置对账单金额，单据类型
	 * @author ddw
	 * @date 2014-06-16
	 */
	private void sumRecAndPayAmount(WoodenStatementDto woodenStatementDto,List<WoodenStatementDEntity> list){
		// 本期发生金额
		BigDecimal periodAmount = BigDecimal.ZERO; 
		// 本期应收金额
		BigDecimal periodRecAmount = BigDecimal.ZERO; 
		// 本期应付金额
		BigDecimal periodPayAmount = BigDecimal.ZERO; 
		//循环对账单明细计算应收应付金额
		for(WoodenStatementDEntity dEntity : list){
			//计算应付金额
			if(SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT_D__BILL_PARENT_TYPE__PAYABLE.equals(dEntity.getBillParentType())){
				periodPayAmount = periodPayAmount.add(dEntity.getUnverifyAmount());
			}
			//计算应收金额
			if(SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT_D__BILL_PARENT_TYPE__RECEIVABLE.equals(dEntity.getBillParentType())){
				periodRecAmount = periodRecAmount.add(dEntity.getUnverifyAmount());
			}
		}
		//判断应收应付金额大小
		if(periodPayAmount.compareTo(periodRecAmount) > 0){
			//计算本期发生金额，应付金额减去应收金额
			periodAmount = periodPayAmount.subtract(periodRecAmount);
			//应付大于应收，对账单为应付对账单
			woodenStatementDto.setBillType(SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT__BILL_TYPE__YF_WOODEN_ACCOUNT);
		}else{
			//计算本期发生金额，应收金额减去应付金额
			periodAmount = periodRecAmount.subtract(periodPayAmount);
			//应收大于应付，对账单为应收对账单
			woodenStatementDto.setBillType(SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT__BILL_TYPE__YS_WOODEN_ACCOUNT);
		}
		//设置对账单应付金额
		woodenStatementDto.setPeriodPayAmount(periodPayAmount);
		//设置对账单应收金额
		woodenStatementDto.setPeriodRecAmount(periodRecAmount);
		//设置发生金额
		woodenStatementDto.setPeriodAmount(periodAmount);
		//设置未核销金额
		woodenStatementDto.setUnpaidAmount(periodAmount);
	}
	/**
	 * 确认代打木架对账单
	 * @author ddw
	 * @date 2014-06-16
	 */
	@Transactional
	@Override
	public void confirmWoodenStatement(WoodenStatementDto woodenStatementDto) {
		//获取当前用户
		CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
		//获取当前用户工号
		woodenStatementDto.setEmpCode(currentInfo.getEmpCode());
		//获取当前用户姓名
		woodenStatementDto.setEmpName(currentInfo.getEmpName());
		
		woodenStatementDto.setOrgCode(currentInfo.getCurrentDeptCode());
		//修改行数
		int updateRows = 0;
		//定义list列表
		List<String> list = new ArrayList<String>();
		//获取对账单单号
		list.add(woodenStatementDto.getStatementBillNo());
		//设置单号
		woodenStatementDto.setNumbers(list);
		//按单号集合查询对账单
		List<WoodenStatementEntity> woodenStatementList = woodenStatementDao.queryWoodenStatementByNumber(woodenStatementDto);
		if(null != woodenStatementList && woodenStatementList.size() > 0){
			//获取对账单
			WoodenStatementEntity woodenStatementEntity = woodenStatementList.get(0);
			if(woodenStatementEntity.getPeriodAmount().compareTo(woodenStatementEntity.getUnpaidAmount()) != 0){
				throw new SettlementException("对账单已经核销不可以进行确认或反确认操作！");
			}
			//判断对账单是否存在有效的核销单
			int writeoffBillCount = woodenStatementDao.queryWriteoffBillByStatementBillNo(woodenStatementEntity.getStatementBillNo());
			if(writeoffBillCount > 0){
				throw new SettlementException("对账单已经核销不可以进行确认或反确认操作！");
			}
			//判断确认状态
			if(SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT__CONFIRM_STATUS__CONFIRM.equals(
					woodenStatementDto.getConfirmStatus())){
				//判断对账单是否为确认状态
				if(SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT__CONFIRM_STATUS__CONFIRM.equals(
						woodenStatementEntity.getConfirmStatus())){
					throw new SettlementException("对账单已经为确认状态不可以再次确认！");
				}
				//更新对账单为确认状态
				updateRows = woodenStatementDao.confirmWoodenStatement(woodenStatementDto);
			}else if(SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT__CONFIRM_STATUS__NOT_CONFIRM.equals(
					woodenStatementDto.getConfirmStatus())){
				//判断对账单是否为未确认状态
				if(SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT__CONFIRM_STATUS__NOT_CONFIRM.equals(
						woodenStatementEntity.getConfirmStatus())){
					throw new SettlementException("对账单已经为未确认状态不可以再次未确认！");
				}
				//更新对账单为未确认状态
				updateRows = woodenStatementDao.unConfirmWoodenStatement(woodenStatementDto);
			}else{
				throw new SettlementException("对账单确认状态错误！");
			}
		}else{
			throw new SettlementException("没有找到对账单数据，请确认对账单是否存在！");
		}
		//判断更新行数
		if(updateRows == 0){
			throw new SettlementException("对账单不存在，请重新查询对账单！");
		}
	}
	/**
	 * 代打木架对账单添加明细
	 * @author ddw
	 * @date 2014-06-16
	 */
	@Transactional
	@Override
	public WoodenStatementDto addWoodenStatementD(WoodenStatementDto woodenStatementDto) {
		//获取当前用户
		CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
		//获取当前用户工号
		woodenStatementDto.setEmpCode(currentInfo.getEmpCode());
		//获取当前用户姓名
		woodenStatementDto.setEmpName(currentInfo.getEmpName());
		//获取当前用户所在部门
		woodenStatementDto.setOrgCode(currentInfo.getCurrentDeptCode());
		//插入行数
		int insertRows = 0;
		//更新行数
		int updateRows = 0;
		//按单号查询对账单明细
		List<WoodenStatementDEntity> woodenStatementDList = woodenStatementDao.queryDelWoodenStatementD(woodenStatementDto);
		//判断对账单是否存在同一个对账单
		if(CollectionUtils.isNotEmpty(woodenStatementDList)){
			throw new SettlementException("应收应付单据已经在此对账单，不可以重复新增！");
		}
		//判断插入页签
		if(SettlementConstants.TAB_QUERY_BY_DATE.equals(woodenStatementDto.getQueryTabType())){
			//按时间客户添加对账单明细
			insertRows = woodenStatementDao.addWoodenStatementDByCustomer(woodenStatementDto);
		}else{
			//按应收应付单号添加对账单明细
			insertRows = woodenStatementDao.addWoodenStatementDByNumber(woodenStatementDto);
		}
		//判断插入行数
		if(insertRows == 0){
			throw new SettlementException("对账单明细插入失败，新增条数为零！");
		}
		//查询最新对账单明细
		List<WoodenStatementDEntity> list = woodenStatementDao.queryWoodenDByStatementBillNo(woodenStatementDto);
		//设置对账单金额
		sumRecAndPayAmount(woodenStatementDto,list);
		//按对账单单号更新对账单金额、单据类型
		updateRows = woodenStatementDao.woodenStatementUpdateByStatementBillNo(woodenStatementDto);
		//判断更新行数
		if(updateRows != 1){
			throw new SettlementException("对账单单号存在重复！");
		}
		//更新应付单
		int updatePayRows = woodenStatementDao.woodenPayUpdateByStatementBillNo(woodenStatementDto);
		//更新应收单
		int updateRecRows = woodenStatementDao.woodenRecUpdateByStatementBillNo(woodenStatementDto);
		//判断更新行数
		if(updatePayRows == 0 && updateRecRows == 0){
			throw new SettlementException("对账单明细插入失败，应收应付单据更新条数同时为零！");
		}
		//返回参数
		return woodenStatementDto;
	}
	/**
	 * 代打木架对账单删除明细
	 * @author ddw
	 * @date 2014-06-16
	 */
	@Transactional
	@Override
	public WoodenStatementDto delWoodenStatementD(WoodenStatementDto woodenStatementDto) {
		//获取当前用户
		CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
		//获取当前用户工号
		woodenStatementDto.setEmpCode(currentInfo.getEmpCode());
		//获取当前用户姓名
		woodenStatementDto.setEmpName(currentInfo.getEmpName());
		//获取当前用户所在部门
		woodenStatementDto.setOrgCode(currentInfo.getCurrentDeptCode());
		//按单号删除对账单明细
		int updateRows = woodenStatementDao.delWoodenStatementD(woodenStatementDto);
		//查询最新对账单明细
		List<WoodenStatementDEntity> list = woodenStatementDao.queryWoodenDByStatementBillNo(woodenStatementDto);
		//设置对账单金额
		sumRecAndPayAmount(woodenStatementDto,list);
		//按对账单单号更新对账单金额、单据类型
		updateRows = woodenStatementDao.woodenStatementUpdateByStatementBillNo(woodenStatementDto);
		//判断更新行数
		if(updateRows != 1){
			throw new SettlementException("对账单单号存在重复！");
		}
		//更新应付单
		int updatePayRows = woodenStatementDao.updatePayStatementBillNo(woodenStatementDto);
		//更新应收单
		int updateRecRows = woodenStatementDao.updateRecStatementBillNo(woodenStatementDto);
		//判断更新行数
		if(updatePayRows == 0 && updateRecRows == 0){
			throw new SettlementException("对账单明细删除失败，应收应付单据更新条数同时为零！");
		}
		//返回参数
		return woodenStatementDto;
	}
	/**
	 * 代打木架对账单添加明细查询应收应付单
	 * @author ddw
	 * @date 2014-06-16
	 */
	@Override
	public WoodenStatementDto queryAddWoodenStatementD(WoodenStatementDto dto, int start, int limit) {
		//对账单DTO
		WoodenStatementDto woodenStatementDto = new WoodenStatementDto();
		CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
		//获取当前用户所在部门
		dto.setOrgCode(currentInfo.getCurrentDeptCode());
		//对账单明细列表
		List<WoodenStatementDEntity> woodenStatementDList = woodenStatementDao.queryAddWoodenStatementD(dto);
		//设置对账单明细返回列表
		woodenStatementDto.setWoodenStatementDList(woodenStatementDList);
		//返回参数
		return woodenStatementDto;
	}
	/**
	 * 代打木架对账单删除明细查询应收应付明细
	 * @author ddw
	 * @date 2014-06-16
	 */
	@Override
	public WoodenStatementDto queryDelWoodenStatementD(WoodenStatementDto dto){
		//对账单DTO
		WoodenStatementDto woodenStatementDto = new WoodenStatementDto();
		//按单号查询对账单明细
		List<WoodenStatementDEntity> woodenStatementDList = woodenStatementDao.queryDelWoodenStatementD(dto);
		//设置对账单明细返回列表
		woodenStatementDto.setWoodenStatementDList(woodenStatementDList);
		//返回参数
		return woodenStatementDto;
	}
	/**
	 * 更新代打木架对账单
	 * @author ddw
	 * @date 2014-06-16
	 */
	@Override
	public void updateStatementForWriteOff(WoodenStatementEntity entity,CurrentInfo cInfo) {
		//对账单DTO
		WoodenStatementDto woodenStatementDto = new WoodenStatementDto();
		//发生金额
		woodenStatementDto.setPeriodAmount(entity.getPeriodAmount());
		//未核销金额
		woodenStatementDto.setUnpaidAmount(entity.getUnpaidAmount());
		//单据类型
		woodenStatementDto.setBillType(entity.getBillType());
		//当前用户工号
		woodenStatementDto.setEmpCode(cInfo.getEmpCode());
		//当前用户姓名
		woodenStatementDto.setEmpName(cInfo.getEmpName());
		//对账单单号
		woodenStatementDto.setStatementBillNo(entity.getStatementBillNo());
		//更新对账单
		woodenStatementDao.woodenStatementUpdateByStatementBillNo(woodenStatementDto);
	}
	/**
	 * 按对账单单号查询对账单信息
	 * @author ddw
	 * @date 2014-06-16
	 */
	@Override
	public WoodenStatementEntity queryByStatementNo(String statementNo) {
		WoodenStatementEntity woodenStatementEntity = null;
		//对账单DTO
		WoodenStatementDto woodenStatementDto = new WoodenStatementDto();
		//获取当前用户
		CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
		//获取当前用户所在部门
		woodenStatementDto.setOrgCode(currentInfo.getCurrentDeptCode());
		//单号列表
		List<String> numbers = new ArrayList<String>();
		//添加对账单单号
		numbers.add(statementNo);
		//设置对账单单号
		woodenStatementDto.setNumbers(numbers);
		//按对账单单号查询对账单
		List<WoodenStatementEntity> list = woodenStatementDao.queryWoodenStatementByNumber(woodenStatementDto);
		
		if(CollectionUtils.isNotEmpty(list)){
			woodenStatementEntity = list.get(0);
		}
		
		//返回对账单实体
		return woodenStatementEntity;
	}
	/**
	 * 按对账单单号查询对账单明细信息
	 * @author ddw
	 * @date 2014-06-16
	 */
	@Override
	public List<WoodenStatementDEntity> queryByStatementBillNo(String statementNo) {
		//对账单DTO
		WoodenStatementDto woodenStatementDto = new WoodenStatementDto();
		//设置对账单单号
		woodenStatementDto.setStatementBillNo(statementNo);
		//按对账单单号查询对账单明细
		List<WoodenStatementDEntity> list = woodenStatementDao.queryWoodenDByStatementBillNo(woodenStatementDto);
		//返回对账单明细列表
		return list;
	}
	
	/**
	 * 按对账单单号查询对账单信息 给 付款接口使用
	 * @author ddw
	 * @date 2014-06-16
	 */
	@Override
	public WoodenStatementEntity queryByStateBillNoForInterface(String statementNo) {
		if(StringUtils.isEmpty(statementNo)){
			throw new SettlementException("对账单号不能为空！");
		}
		WoodenStatementEntity woodenStatementEntity = null;
		//对账单DTO
		WoodenStatementDto woodenStatementDto = new WoodenStatementDto();
		//单号列表
		List<String> numbers = new ArrayList<String>();
		//添加对账单单号
		numbers.add(statementNo);
		//设置对账单单号
		woodenStatementDto.setNumbers(numbers);
		//按对账单单号查询对账单
		List<WoodenStatementEntity> list = woodenStatementDao.queryWoodenStatementByNumber(woodenStatementDto);
		if(CollectionUtils.isNotEmpty(list)){
			woodenStatementEntity = list.get(0);
		}
		//返回对账单实体
		return woodenStatementEntity;
	}
	
	/**
	 * 按对账单单号查询对账单信息 
	 * @author ddw
	 * @date 2014-06-16
	 */
	@Override
	public List<WoodenStatementEntity> queryWoodenStatementByNumber(List<String> statementNoList) {
		//对账单DTO
		WoodenStatementDto woodenStatementDto = new WoodenStatementDto();
		//设置对账单单号
		woodenStatementDto.setNumbers(statementNoList);
		//按对账单单号查询对账单
		List<WoodenStatementEntity> list = woodenStatementDao.queryWoodenStatementByNumber(woodenStatementDto);
		//返回对账单列表
		return list;
	}
	
	public void setSettlementCommonService(ISettlementCommonService settlementCommonService) {
		this.settlementCommonService = settlementCommonService;
	}

	public void setWoodenStatementDao(IWoodenStatementDao woodenStatementDao) {
		this.woodenStatementDao = woodenStatementDao;
	}

}

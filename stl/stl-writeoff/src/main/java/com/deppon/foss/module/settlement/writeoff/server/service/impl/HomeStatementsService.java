package com.deppon.foss.module.settlement.writeoff.server.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.writeoff.api.server.dao.IHomeStatementsDao;
import com.deppon.foss.module.settlement.writeoff.api.server.service.IHomeStatementsService;
import com.deppon.foss.module.settlement.writeoff.api.shared.domain.HomeStatementDEntity;
import com.deppon.foss.module.settlement.writeoff.api.shared.domain.HomeStatementEntity;
import com.deppon.foss.module.settlement.writeoff.api.shared.dto.HomeItemDto;
import com.deppon.foss.util.CollectionUtils;

public class HomeStatementsService implements IHomeStatementsService {
	private IHomeStatementsDao homeStatementsDao;
	/**
	 * 查询符合条件的对账单
	 */
	@Override
	public HomeItemDto queryHome(HomeItemDto dto, int start,
			int limit) {
		HomeItemDto homeItemDto =new HomeItemDto();
		//获取当前用户
		CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
		//获取当前用户所在部门
		dto.setOrgCode(currentInfo.getCurrentDeptCode());
		//设置总行数
		long count = 0;
		//对账单列表
		List<HomeStatementEntity> homestatementdtoList=new ArrayList<HomeStatementEntity>();
		//判断查询页签，按客户查询，否则按对账单单号查询
		if(SettlementConstants.TAB_QUERY_BY_DATE.equals(dto.getQueryTabType())){
			//根据时间客户查询对账单
			homestatementdtoList=homeStatementsDao.queryHome(dto, start, limit);
			//总行数
			count=homeStatementsDao.queryHomeTotalCount(dto);
		}else{
			//按单号查询不需要分页，设置总行数为单号个数
			count = dto.getNumbers().size();
			//根据单号查询对账单
			homestatementdtoList=homeStatementsDao.queryHomeByNumber(dto);
		}
		//设置对账单返回列表
		homeItemDto.setHomeList(homestatementdtoList);
		//设置对账单返回总行数
		homeItemDto.setCount(count);
		return homeItemDto;
	}
	/**
	 * 双击对账单记录查看明细，按对账单单号查询
	 */
	@Override
	public HomeItemDto queryHomeStatementBillNo(HomeItemDto dto, int start,
			int limit) {
		HomeItemDto homeItemDto =new HomeItemDto();
		//按对账单单号分页查询对账单明细
		List<HomeStatementDEntity> homeStatementDList = homeStatementsDao.queryHomeStatementDBillNo(dto, start, limit);
		//按对账单单号查询对账单明细总行数
		int count=homeStatementsDao.countHomeStatementBillNo(dto);
		//按对账单单号查询所有对账单明细
		List<HomeStatementDEntity> homeList=homeStatementsDao.queryHomeStatementDBillNo(dto);
		//设置对账单金额
		sumRecAndPayAmount(homeItemDto,homeList);
		//设置对账单明细返回列表
		homeItemDto.setHomeStatementDList(homeStatementDList);
		//设置对账单明细返回总行数
		homeItemDto.setCount(count);
		return homeItemDto;
	}
	
	/**
	 * 设置对账单金额，单据类型
	 * @author 268217
	 */
	private void sumRecAndPayAmount(HomeItemDto dto,List<HomeStatementDEntity> list){
		// 本期发生金额
		BigDecimal periodAmount = BigDecimal.ZERO; 
		// 本期应收金额
		BigDecimal periodRecAmount = BigDecimal.ZERO; 
		// 本期应付金额
		BigDecimal periodPayAmount = BigDecimal.ZERO;
		//循环对账单明细计算应收应付金额
		for(HomeStatementDEntity dEntity : list){
			//计算应付金额 
			if(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__HOME_IMPROVEMENT.equals(dEntity.getBillType())){
				periodPayAmount = periodPayAmount.add(dEntity.getUnverifyAmount());
			}
			//计算应收金额
			if(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__HOME_IMPROVEMENT.equals(dEntity.getBillType())){
				periodRecAmount = periodRecAmount.add(dEntity.getUnverifyAmount());
			}
		}
		//判断应收应付金额大小
		if(periodPayAmount.compareTo(periodRecAmount) > 0){
			//计算本期发生金额，应付金额减去应收金额
			periodAmount = periodPayAmount.subtract(periodRecAmount);
			//应付大于应收，对账单为应付对账单
			dto.setBillType(SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT__BILL_TYPE__YF_HOME_ACCOUNT);
		}else{
			//计算本期发生金额，应收金额减去应付金额
			periodAmount = periodRecAmount.subtract(periodPayAmount);
			//应收大于应付，对账单为应收对账单
			dto.setBillType(SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT__BILL_TYPE__YS_HOME_ACCOUNT);
		}
		//设置对账单应付金额
		dto.setPeriodPayAmount(periodPayAmount);
		//设置对账单应收金额
		dto.setPeriodRecAmount(periodRecAmount);
		//设置发生金额
		dto.setPeriodAmount(periodAmount);
		//设置未核销金额
		dto.setUnpaidAmount(periodAmount);
	}
	/**
	 * 家装对账单添加明细查询应收应付单
	 * @author 268217
	 */
	@Override
	public HomeItemDto queryAddHomeByNo(HomeItemDto dto, int start,int limit){
		HomeItemDto homeItemDto =new HomeItemDto();
		//获取当前用户
		CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
		//获取当前用户所在部门
		dto.setOrgCode(currentInfo.getCurrentDeptCode());
		//对账单明细列表
		List<HomeStatementDEntity> homeStatementDList=homeStatementsDao.queryAddHomeByNo(dto);
		//设置对账单明细返回列表
		homeItemDto.setHomeStatementDList(homeStatementDList);
		return homeItemDto;
	}
	/**
	 * 家装对账单添加明细
	 * @author 268217
	 */
	@Transactional
	@Override
	public HomeItemDto addHomeStatementD(HomeItemDto homeItemDto){
		//获取当前用户
		CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
		//获取当前用户工号
		homeItemDto.setEmpCode(currentInfo.getEmpCode());
		//获取当前用户姓名
		homeItemDto.setEmpName(currentInfo.getEmpName());
		//获取当前用户所在部门
		homeItemDto.setOrgCode(currentInfo.getCurrentDeptCode());
		//插入行数
		int insertRows = 0;
		//更新行数
		int updateRows = 0;
		//按单号查询对账单明细
		List<HomeStatementDEntity> homeList=homeStatementsDao.queryDelHomeBillNo(homeItemDto);
		//判断对账单是否存在同一个对账单
		if(CollectionUtils.isNotEmpty(homeList)){
			throw new SettlementException("应收应付单据已经在此对账单，不可以重复新增！");
		}
		//按应收应付单号添加对账单明细
		insertRows = homeStatementsDao.addHomeStatementDByNumber(homeItemDto);
		//判断插入行数
		if(insertRows == 0){
			throw new SettlementException("对账单明细插入失败，新增条数为零！");
		}
		//查询最新对账单明细
		List<HomeStatementDEntity> list=homeStatementsDao.queryHomeStatementDBillNo(homeItemDto);
		//设置对账单金额
		sumRecAndPayAmount(homeItemDto,list);
		//按对账单单号更新对账单金额、单据类型
		updateRows = homeStatementsDao.UpdateHomeByStatementBillNo(homeItemDto);
		//判断更新行数
		if(updateRows != 1){
			throw new SettlementException("对账单单号存在重复！");
		}
		//更新应付单
		int updatePayRows = homeStatementsDao.UpdatePayRowsBillNo(homeItemDto);
		//更新应收单
		int updateRecRows = homeStatementsDao.UpdateRecRowsBillNo(homeItemDto);
		//判断更新行数
		if(updatePayRows == 0 && updateRecRows == 0){
			throw new SettlementException("对账单明细插入失败，应收应付单据更新条数同时为零！");
		}
		return homeItemDto;
	}
	/**
	 * 家装对账单删除明细查询应收应付单
	 * @author 268217
	 */
	@Override
	public HomeItemDto queryDelHomeByNo(HomeItemDto dto, int start,int limit){
		HomeItemDto homeItemDto =new HomeItemDto();
		//获取当前用户
		CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
		//获取当前用户所在部门
		dto.setOrgCode(currentInfo.getCurrentDeptCode());
		//对账单明细列表
		List<HomeStatementDEntity> homeStatementDList=homeStatementsDao.queryDelHomeByNo(dto);
		//设置对账单明细返回列表
		homeItemDto.setHomeStatementDList(homeStatementDList);
		return homeItemDto;
	}
	/**
	 * 家装对账单删除明细
	 * @author 268217
	 */
	@Transactional
	@Override
	public HomeItemDto delHomeStatement(HomeItemDto homeItemDto){
		//获取当前用户
		CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
		//获取当前用户工号
		homeItemDto.setEmpCode(currentInfo.getEmpCode());
		//获取当前用户姓名
		homeItemDto.setEmpName(currentInfo.getEmpName());
		//获取当前用户所在部门
		homeItemDto.setOrgCode(currentInfo.getCurrentDeptCode());
		//按单号删除对账单明细
		int updateRows = homeStatementsDao.delHomeStatement(homeItemDto);
		//查询最新对账单明细
		List<HomeStatementDEntity> list=homeStatementsDao.queryHomeStatementDBillNo(homeItemDto);
		//设置对账单金额
		sumRecAndPayAmount(homeItemDto,list);
		//按对账单单号更新对账单金额、单据类型
		updateRows = homeStatementsDao.UpdateHomeByStatementBillNo(homeItemDto);
		//判断更新行数
		if(updateRows != 1){
			throw new SettlementException("对账单单号存在重复！");
		}
		//更新应付单
		int updatePayRows = homeStatementsDao.updatePayable(homeItemDto);
		//更新应收单
		int updateRecRows = homeStatementsDao.updateReceivable(homeItemDto);
		//判断更新行数
		if(updatePayRows == 0 && updateRecRows == 0){
			throw new SettlementException("对账单明细删除失败，应收应付单据更新条数同时为零！");
		}
		return homeItemDto;
	}
	/**
	 * 确认家装对账单
	 * @author 268217
	 */
	@Transactional
	@Override
	public void confirmWoodenStatement(HomeItemDto homeItemDto) {
		//获取当前用户
		CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
		//获取当前用户工号
		homeItemDto.setEmpCode(currentInfo.getEmpCode());
		//获取当前用户姓名
		homeItemDto.setEmpName(currentInfo.getEmpName());
		//获取当前用户所在部门
		homeItemDto.setOrgCode(currentInfo.getCurrentDeptCode());
		//修改行数
		int updateRows = 0;
		//定义list列表
		List<String> list = new ArrayList<String>();
		//获取对账单单号
		list.add(homeItemDto.getStatementBillNo());
		//设置单号
		homeItemDto.setNumbers(list);
		//按单号集合查询对账单
		List<HomeStatementEntity> homeStatementsDtoList=homeStatementsDao.queryHomeByNumber(homeItemDto);
		if(homeStatementsDtoList != null && homeStatementsDtoList.size()>0){
			//获取对账单
			HomeStatementEntity homeStatementsDto=homeStatementsDtoList.get(0);
			if(homeStatementsDto.getPeriodAmount().compareTo(homeStatementsDto.getUnverifyAmount()) != 0){
				throw new SettlementException("对账单已经核销不可以进行确认或反确认操作！");
			}
			//判断对账单是否存在有效的核销单
			int writeoffBillCount = homeStatementsDao.queryHomeBillByStatementBillNo(homeStatementsDto.getStatementBillNo());
			if(writeoffBillCount > 0){
				throw new SettlementException("对账单已经核销不可以进行确认或反确认操作！");
			}
			//判断确认状态
			if(SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT__CONFIRM_STATUS__CONFIRM.equals(homeItemDto.getConfirmStatus())){
				//判断对账单是否为确认状态
				if(SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT__CONFIRM_STATUS__CONFIRM.equals(homeStatementsDto.getConfirmStatus())){
					throw new SettlementException("对账单已经为确认状态不可以再次确认！");
				}
				//更新对账单为确认状态
				updateRows = homeStatementsDao.confirmHomeStatement(homeItemDto);
			}else if(SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT__CONFIRM_STATUS__NOT_CONFIRM.equals(homeItemDto.getConfirmStatus())){
				//判断对账单是否为未确认状态
				if(SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT__CONFIRM_STATUS__NOT_CONFIRM.equals(homeStatementsDto.getConfirmStatus())){
					throw new SettlementException("对账单已经为未确认状态不可以再次未确认！");
				}
				//更新对账单为未确认状态
				updateRows = homeStatementsDao.unconfirmHomeStatement(homeItemDto);
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
	 * 导出用
	 * 按对账单单号查询对账单明细信息
	 * @author 268217
	 */
	@Override
	public List<HomeStatementDEntity> queryByStatementBillNo(String statementNo){
		//对账单DTO
		HomeItemDto homeItemDto = new HomeItemDto();
		//设置对账单单号
		homeItemDto.setStatementBillNo(statementNo);
		//按对账单单号查询对账单明细
		List<HomeStatementDEntity> homeStatementDEntityList=homeStatementsDao.queryHomeStatementDBillNo(homeItemDto);
		return homeStatementDEntityList;
	}
	
	/**
	 * 按对账单单号查询付款单明细信息
	 * @author 268217
	 */
	/*@Override
	public HomeItemDto queryHomePayment(HomeItemDto dto){
		//对账单DTO
		HomeItemDto homeItemDto = new HomeItemDto();
		// 获取单据编号 （此处单号是手动生成付款单）
		String paymentNo=settlementCommonService.getSettlementNoRule(SettlementNoRuleEnum.FK1);
		billPaymentDService.addBillPaymentD(dto)
		
		return homeItemDto;
	}*/
	
	
	
	
	
	
	
	public IHomeStatementsDao getHomeStatementsDao() {
		return homeStatementsDao;
	}
	public void setHomeStatementsDao(IHomeStatementsDao homeStatementsDao) {
		this.homeStatementsDao = homeStatementsDao;
	}
}

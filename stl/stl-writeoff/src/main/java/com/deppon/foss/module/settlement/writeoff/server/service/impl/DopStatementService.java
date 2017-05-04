package com.deppon.foss.module.settlement.writeoff.server.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.server.service.ISettlementCommonService;
import com.deppon.foss.module.settlement.writeoff.api.server.dao.IDopStatementDao;
import com.deppon.foss.module.settlement.writeoff.api.server.service.IDopStatementService;
import com.deppon.foss.module.settlement.writeoff.api.shared.domain.HomeStatementDEntity;
import com.deppon.foss.module.settlement.writeoff.api.shared.domain.HomeStatementEntity;
import com.deppon.foss.module.settlement.writeoff.api.shared.dto.HomeStatementDto;
import com.deppon.foss.util.CollectionUtils;

/**
 * 家装
 * @ClassName: DopStatementService
 * @author &269052 |zhouyuan008@deppon.com
 * @date 2015-12-11 下午5:28:40
 */
public class DopStatementService implements IDopStatementService {

	/**
	 * 家装对账单DAO
	 */
	private IDopStatementDao dopStatementDao;
	/*
	 * 获取结算单据编号服务
	 */
	private ISettlementCommonService settlementCommonService;

	/**
	 * 更新家装对账单
	 * 
	 * @author zya
	 * @date 2015-12-14
	 */
	@Override
	public void updateStatementForWriteOff(HomeStatementEntity entity,
			CurrentInfo cInfo) {
		// 对账单DTO
		HomeStatementDto homeStatementDto = new HomeStatementDto();
		// 发生金额
		homeStatementDto.setPeriodAmount(entity.getPeriodAmount());
		// 未核销金额
		homeStatementDto.setUnpaidAmount(entity.getUnverifyAmount());
		// 单据类型
		homeStatementDto.setBillType(entity.getBillType());
		// 当前用户工号
		homeStatementDto.setEmpCode(cInfo.getEmpCode());
		// 当前用户姓名
		homeStatementDto.setEmpName(cInfo.getEmpName());
		// 对账单单号
		homeStatementDto.setStatementBillNo(entity.getStatementBillNo());
		// 更新对账单
		dopStatementDao.homeStatementUpdateByStatementBillNo(homeStatementDto);
	}

	/**
	 * 按对账单单号查询对账单信息
	 * 
	 * @author zya
	 * @date 2015-12-14
	 */
	@Override
	public HomeStatementEntity queryByStatementNo(String statementNo) {
		HomeStatementEntity homeStatementEntity = null;
		// 对账单DTO
		HomeStatementDto homeStatementDto = new HomeStatementDto();
		// 获取当前用户
		CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
		// 获取当前用户所在部门
		homeStatementDto.setOrgCode(currentInfo.getCurrentDeptCode());
		// 单号列表
		List<String> numbers = new ArrayList<String>();
		// 添加对账单单号
		numbers.add(statementNo);
		// 设置对账单单号
		homeStatementDto.setNumbers(numbers);
		// 按对账单单号查询对账单
		List<HomeStatementEntity> list = dopStatementDao
				.queryHomeStatementByNumber(homeStatementDto);

		if (CollectionUtils.isNotEmpty(list)) {
			homeStatementEntity = list.get(0);
		}

		// 返回对账单实体
		return homeStatementEntity;
	}

	/**
	 * 按对账单单号查询对账单明细信息
	 * 
	 * @author zya
	 * @date 2015-12-14
	 */
	@Override
	public List<HomeStatementDEntity> queryByStatementBillNo(String statementNo,String orgCode) {
		// 对账单DTO
		HomeStatementDto homeStatementDto = new HomeStatementDto();
		// 设置对账单单号
		homeStatementDto.setStatementBillNo(statementNo);
		homeStatementDto.setOrgCode(orgCode);
		// 按对账单单号查询对账单明细
		List<HomeStatementDEntity> list = dopStatementDao
				.queryHomeDByStatementBillNo(homeStatementDto);
		// 返回对账单明细列表
		return list;
	}
		
	/**
	 * 根据对账单号去查询部门
	 */
	@Override
	public List<HomeStatementDEntity> getOrgNameByStatementBillNo(
			String statementNo) {
		// 对账单DTO
		HomeStatementDto homeStatementDto = new HomeStatementDto();
		// 设置对账单单号
		homeStatementDto.setStatementBillNo(statementNo);
		// 按对账单单号查询对账单明细
		List<HomeStatementDEntity> list = dopStatementDao
				.getOrgNameByStatementBillNo(homeStatementDto);
		// 返回对账单明细列表
		return list;
	}
	
	
	/************* getter/setter **************/
	public IDopStatementDao getDopStatementDao() {
		return dopStatementDao;
	}

	public void setDopStatementDao(IDopStatementDao dopStatementDao) {
		this.dopStatementDao = dopStatementDao;
	}

	public ISettlementCommonService getSettlementCommonService() {
		return settlementCommonService;
	}

	public void setSettlementCommonService(
			ISettlementCommonService settlementCommonService) {
		this.settlementCommonService = settlementCommonService;
	}

	
}

package com.deppon.foss.module.settlement.pay.server.service.impl;

//import java.sql.Connection;
//import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.waybill.shared.domain.ActualFreightEntity;
import com.deppon.foss.module.settlement.common.api.server.service.IBillPayableService;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableEntity;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.common.server.service.impl.BillPayableService;
import com.deppon.foss.module.settlement.pay.api.server.dao.IDiscountManagementDao;
import com.deppon.foss.module.settlement.pay.api.server.service.IDiscountManagementService;
import com.deppon.foss.module.settlement.pay.api.shared.domain.DiscountManagementDEntity;
import com.deppon.foss.module.settlement.pay.api.shared.domain.DiscountManagementEntity;
import com.deppon.foss.module.settlement.pay.api.shared.dto.DiscountAddDto;
import com.deppon.foss.module.settlement.pay.api.shared.dto.DiscountManagementDto;
import com.deppon.foss.util.define.FossConstants;

public class DiscountManagementService implements IDiscountManagementService{
	/**
	 * 折扣单Dao
	 */
	private IDiscountManagementDao discountManagementDao;
	/**
	 * 红冲应付单
	 */
	private IBillPayableService billPayableService;
	/**
	 * 按客户查询折扣单
	 * @author wy
	 * @date 2015-02-04
	 */
	@Override
	public DiscountManagementDto queryDiscountByCust(DiscountManagementDto dto, int start, int limit) {
		//折扣单列表
		List<DiscountManagementEntity> discountManagementList = new ArrayList<DiscountManagementEntity>();
//		DiscountManagementEntity dm=new DiscountManagementEntity();
		//折扣单DTO
		DiscountManagementDto DiscountManagementDto = new DiscountManagementDto();
		//获取当前用户
		CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
		//获取当前用户所在部门
		dto.setOrgCode(currentInfo.getCurrentDeptCode());
		//设置总行数
		int count = 0;
		//返回给前台的数据对总金额进行计算 总金额：纯运费折扣+代收手续费折扣+保价手续费折扣
		discountManagementList = discountManagementDao.queryDiscountByCust(dto,start,limit);
		//遍历返回的结果
//		for(int i=0;i<DiscountManagementList.size();i++){
//			dm=DiscountManagementList.get(i);
//			dm.setCodDiscountRate(dm.getCodDiscountRate()+"%");
//		}
		//根据时间客户查询折扣单总行数
		count = discountManagementDao.queryCountDiscountByCust(dto);
		//设置折扣单返回列表
		DiscountManagementDto.setDiscountManagementList(discountManagementList);
		//设置折扣单返回总行数
		DiscountManagementDto.setCount(count);
		//返回参数
		return DiscountManagementDto;
	}
	/**
	 * 按单号查询折扣单
	 * @author wy
	 * @date 2015-02-04
	 */
	@Override
	public DiscountManagementDto queryDiscountByNumber(DiscountManagementDto dto, int start, int limit) {
		//折扣单列表
		List<DiscountManagementEntity> discountManagementList = new ArrayList<DiscountManagementEntity>();
//		DiscountManagementEntity dm=new DiscountManagementEntity();
		//折扣单DTO
		DiscountManagementDto discountManagementDto = new DiscountManagementDto();
		//获取当前用户
		CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
		//获取当前用户所在部门
		dto.setOrgCode(currentInfo.getCurrentDeptCode());
		//设置总行数
		int count = 0;
		//处理前台传入的折扣单号进行截取封装
		List<String> numlist=new ArrayList<String>();
		String[] nums=dto.getDiscountNo().split(", ");
		
		for(int i=0;i<nums.length;i++){
//			nums[i].trim();
			numlist.add(nums[i]);
		}
		dto.setNumbers(numlist);
		
		
		discountManagementList = discountManagementDao.queryDiscountByNumber(dto,start,limit);
		//根据单号查询折扣单总行数
		count = discountManagementDao.queryCountDiscountByNumber(dto);
		//设置折扣单返回列表
		discountManagementDto.setDiscountManagementList(discountManagementList);
		//设置折扣单返回总行数
		discountManagementDto.setCount(count);
		//返回参数
		return discountManagementDto;
	}
	
	/**
	 * 双击折扣单查询折扣单明细
	 * @author wy
	 * @date 2015-02-04
	 */
	@Override
	public List<DiscountManagementDEntity> queryDiscountDEntity(
			String discountNo,int start, int limit) {
		//折扣单明细列表
		List<DiscountManagementDEntity> discountManagementDList = new ArrayList<DiscountManagementDEntity>();
		//根据折扣单号查询折扣单明细
		discountManagementDList = discountManagementDao.queryDiscountDEntity(discountNo,start,limit);
		//根据折扣单号查询折扣单明细总数
		return discountManagementDList;
	}
	/**
	 * 导出查询折扣单明细
	 * @author wy
	 * @date 2015-02-04
	 */
	@Override
	public List<DiscountManagementDEntity> queryDiscountDEntityExport(String discountNo) {
		//折扣单明细列表
		List<DiscountManagementDEntity> discountManagementDList = new ArrayList<DiscountManagementDEntity>();
		//根据折扣单号查询折扣单明细
		discountManagementDList = discountManagementDao.queryDiscountDEntityExport(discountNo);
		return discountManagementDList;
	}

	/**
	 * 按运单单号查询折扣单信息 
	 * @author ddw
	 * @date 2015-02-06
	 */
	@Override
	public List<DiscountManagementEntity> queryDiscountPayable(List<String> waybillNos) {
		List<DiscountManagementEntity> discountList = discountManagementDao.queryDiscountPayable(waybillNos);
		return discountList;
	}


	public void setDiscountManagementDao(
			IDiscountManagementDao discountManagementDao) {
		this.discountManagementDao = discountManagementDao;
	}
	
	public void setBillPayableService(BillPayableService billPayableService) {
		this.billPayableService = billPayableService;
	}
	
	
	/**
	 * 确认折扣单
	 * @author wy
	 * @date 2015-02-06
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void confirmDiscount(DiscountManagementDto discountManagementDto) {
		//获取当前用户
		CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
		//获取当前用户编号
		discountManagementDto.setEmpCode(currentInfo.getEmpCode());
		//获取当前用户编号
		discountManagementDto.setEmpName(currentInfo.getEmpName());
		//校验折扣单状态
		if (discountManagementDao.queryStatus(discountManagementDto)>0) {
			throw new SettlementException("该单已作废，不允许进行确认折扣单操作");
		}else if(discountManagementDao.queryStatusC(discountManagementDto)>0){
			throw new SettlementException("该单已确认，不允许进行确认折扣单操作");
        }else if(discountManagementDao.queryCountDiscountByCustS(discountManagementDto) > 1){   //add by 302307 增加新的校验
            throw new SettlementException("同一个客户同一个月只存在一条折扣单时才可以确认，请作废多余折扣单");
		}
		
		//确认折扣单更新应付单状态，生效应付单ACTIVE更新为‘Y’
		discountManagementDao.confirmDiscount(discountManagementDto);
		//确认折扣单更新折扣单状态
		discountManagementDao.confirmDiscountStatus(discountManagementDto);
	}
	/**
	 * 作废折扣单
	 * @author wy
	 * @date 2015-02-06
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void discountDelete(DiscountManagementDto discountManagementDto) {
		//获取当前用户
		CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
		//获取当前用户编号
		discountManagementDto.setEmpCode(currentInfo.getEmpCode());
		//获取当前用户名称
		discountManagementDto.setEmpName(currentInfo.getEmpName());
		//校验折扣单号状态
		if (discountManagementDao.queryDeleteStatus(discountManagementDto)>0) {
			throw new SettlementException("该单已确认，不允许进行作废折扣单操作");
		}
		else if(discountManagementDao.queryDeleteStatusD(discountManagementDto)>0){
			throw new SettlementException("该单已作废，不允许进行作废折扣单操作");
		}
        //判断是否有并发重复数据，如果没有，则更新应收单是否折扣标示
        int isDiscount = discountManagementDao.queryCountDiscountByCustS(discountManagementDto);
		//作废折扣单时更新折扣单状态字段：STATUS为'D'
		discountManagementDao.discountDelete(discountManagementDto);
		
		//作废折扣单时更新应收单字段：IS_DISCOUNT 为'N'
//		discountManagementDao.discountDeleteReceivable(discountManagementDto);
		DiscountAddDto dto = new DiscountAddDto();
		dto.setDiscountNo(discountManagementDto.getDiscountNo());
		dto.setIsDiscount(FossConstants.NO);
		dto.setCurrentUser(currentInfo);

        if(isDiscount == 0){
		    discountManagementDao.updateReceivableDiscountStatus(dto);
        }
		
		// 红冲应付单
//		BillPayableEntity entity =new BillPayableEntity();
		List<String> sourceBillNosList = new ArrayList<String>();
		sourceBillNosList.add(discountManagementDto.getDiscountNo());
		//根据来源单号查询应付单
		List<BillPayableEntity> entityList= billPayableService.queryBySourceBillNOs(sourceBillNosList,null,FossConstants.YES);
//		entity.setWaybillNo(discountManagementDto.getDiscountNo());
		
//		entity.setSourceBillNo(discountManagementDto.getDiscountNo());
		for(BillPayableEntity entity:entityList){
			billPayableService.writeBackBillPayable(entity, currentInfo);
		}
		//更新实际承运信息
		List<DiscountManagementDto> discountManagementDaoList = new ArrayList<DiscountManagementDto>();
		//根据折扣单号查询运单号
		discountManagementDaoList=discountManagementDao.selectWaybillNo(discountManagementDto);
		List<ActualFreightEntity> entitysList = new ArrayList<ActualFreightEntity>();
		for(int i=0;i<discountManagementDaoList.size();i++){
			ActualFreightEntity entitys = new ActualFreightEntity();
			entitys.setWaybillNo(discountManagementDaoList.get(i).getWaybillNo());
//			entitys.setToPayAmountDiscount(BigDecimal.ZERO);
//			entitys.setPrePayAmountDiscount(BigDecimal.ZERO);
			entitysList.add(entitys);
		}
		
//		actualFreightService.updateByActualFreightEntitys(entitysList);
	}
	/**
	 * 查询折扣单明细总行数
	 * @author wy
	 * @date 2015-02-04
	 */
	@Override
	public int queryCountByDiscount(String discountNo) {
		int count = discountManagementDao.queryCountDiscountBydiscountNo(discountNo);
		return count;
	}
}

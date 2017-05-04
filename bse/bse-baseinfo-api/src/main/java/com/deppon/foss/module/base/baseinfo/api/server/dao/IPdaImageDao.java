package com.deppon.foss.module.base.baseinfo.api.server.dao;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.ElectronicTicketEntity;
public interface IPdaImageDao {
	/**
	 * 新增刷卡电子小票图片地址
	 * 
	 * 
	 * @author
	 * @date
	 */
   public int pdaAddImage(List<ElectronicTicketEntity> entitys);
   /**
	 * 作废刷卡电子小票图片地址
	 * 
	 * 
	 * @author
	 * @date
	 */
   public int deleteElectronicTicket(ElectronicTicketEntity entity);
   /**
	 * 根据交易流水号查询刷卡电子小票图片地址
	 * 
	 * 
	 * @author
	 * @date
	 */
   public List<ElectronicTicketEntity> queryElectronicTicketListBySerialNumber(List<String> serialNos,int start, int limit);
   /**
	 * 根据运单号查询刷卡电子小票图片地址
	 * 
	 * 
	 * @author
	 * @date
	 */
   public List<ElectronicTicketEntity> queryElectronicTicketListByWaybillNumber(List<String> wayBillNos,int start, int limit);

   /**
	 * 根据运单号查询返回的记录总数,用于分页
	 */
	long queryCountByWaybn(List<String> wayBillNos);
	/**
	 * 根据交易流水号查询返回的记录总数
	 * @param serialNos
	 * @return
	 */
	long queryCountBySerialN(List<String> serialNos);
	public List<ElectronicTicketEntity> queryElectronicTicketBySerialNo(ElectronicTicketEntity entity);
	
}
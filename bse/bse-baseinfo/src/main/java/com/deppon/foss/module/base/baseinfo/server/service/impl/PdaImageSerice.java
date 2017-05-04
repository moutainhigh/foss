package com.deppon.foss.module.base.baseinfo.server.service.impl;

import java.util.Date;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IPdaImageDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ElectronicTicketEntity;
import com.deppon.foss.module.pickup.pda.api.server.service.IPdaImageService;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;
import com.opensymphony.xwork2.inject.Inject;

public class PdaImageSerice implements IPdaImageService {
	/**
	 * 日志.
	 */
//	private static final Logger LOGGER = LoggerFactory
//			.getLogger(PdaImageSerice.class);

	@Inject
	private IPdaImageDao pdaImageDao;

	

	public void setPdaImageDao(IPdaImageDao pdaImageDao) {
		this.pdaImageDao = pdaImageDao;
	}

	/**
	 * 新增
	 */
	@Override
	@Transactional
	public int pdaAddImage(List<ElectronicTicketEntity> entitys) {
		if (entitys.size() == 0) {
			return FossConstants.FAILURE;
		}
		//根据交易流水号判断去重
		for(ElectronicTicketEntity electronicTicket : entitys){
			List<ElectronicTicketEntity> listEntitys = this.queryElectronicTicketBySerialNo(electronicTicket);
			if(listEntitys.size()!=0){
				this.deleteElectronicTicket(electronicTicket);
			}
		}
		for (ElectronicTicketEntity entity : entitys) {
			entity.setId(UUIDUtils.getUUID());
			entity.setActive(FossConstants.ACTIVE);
			entity.setCreateTime(new Date());
			entity.setModifyUser(entity.getCreateUser());
		}
		pdaImageDao.pdaAddImage(entitys);
		return FossConstants.SUCCESS;
	}
	@Override
	public List<ElectronicTicketEntity> queryElectronicTicketListBySerialNumber(
			List<String> serialNos, int start, int limit) {

		List<ElectronicTicketEntity> entites = pdaImageDao.queryElectronicTicketListBySerialNumber(serialNos, start, limit);
		return entites;
	}
	@Override
	public List<ElectronicTicketEntity> queryElectronicTicketListByWaybillNumber(
			List<String> wayBillNos, int start, int limit) {
		List<ElectronicTicketEntity> entites = pdaImageDao.queryElectronicTicketListByWaybillNumber(wayBillNos, start, limit);
		return entites;
	}
	
	/**
	 * 根据运单号查询返回的记录总数,用于分页
	 */
	@Override
	public long queryCountByWaybn(List<String> wayBillNos) {
		return pdaImageDao.queryCountByWaybn(wayBillNos);
	}

	/**
	 * 根据交易流水号查询返回的记录总数
	 * 
	 * @param serialNos
	 * @return
	 */
	@Override
	public long queryCountBySerialN(List<String> serialNos) {
		return pdaImageDao.queryCountBySerialN(serialNos);
	}

	/**
	 * 删除
	 * 
	 * @return
	 */
	@Override
	@Transactional
	public int deleteElectronicTicket(ElectronicTicketEntity entity) {
		entity.setActive(FossConstants.INACTIVE);

		pdaImageDao.deleteElectronicTicket(entity);
		return FossConstants.SUCCESS;
	}
	/**
	 * 根据交易流水号精确查询
	 * @param code
	 * @return
	 */
	@Override
	public List<ElectronicTicketEntity> queryElectronicTicketBySerialNo(ElectronicTicketEntity entity) {
		return pdaImageDao.queryElectronicTicketBySerialNo(entity);
	}
}
package com.deppon.foss.module.settlement.consumer.server.dao.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.consumer.api.server.dao.IInvoiceDao;
import com.deppon.foss.module.settlement.consumer.api.shared.domain.InvoiceEntity;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.InvoiceDto;


/**
 * 开发票Dao实现.
 *
 * @author ibm-guxinhua
 * @date 2012-11-7 下午3:31:35
 */
public class InvoiceDao extends iBatis3DaoImpl implements IInvoiceDao {

	/** The Constant NAMESPACE. */
	public static final String NAMESPACE = "foss.stl.InvoiceDao.";
	
	/** 日志. */
	private static final Logger LOGGER = LoggerFactory.getLogger(InvoiceDao.class);
	
	/**
	 * 新加开发票记录.
	 *
	 * @param entity the entity
	 * @return the int
	 * @author ibm-guxinhua
	 * @date 2012-11-7 下午3:31:35
	 */
	@Override
	public int addInvoice(InvoiceEntity entity) {
		if(null == entity){
			LOGGER.error("内部错误，参数为空！");
			throw new SettlementException("内部错误，参数为空！");
		}
		return this.getSqlSession().insert(NAMESPACE + "addInvoice", entity);
	}

	/**
	 * 更新开发票记录.
	 *
	 * @param entity the entity
	 * @return the int
	 * @author ibm-guxinhua
	 * @date 2012-11-7 下午3:31:35
	 */
	@Override
	public int updateInvoice(InvoiceEntity entity) {
		if(null == entity || StringUtils.isBlank(entity.getSourceBillNo())){
			LOGGER.error("内部错误，参数为空！");
			throw new SettlementException("内部错误，参数为空！");
		}
		return this.getSqlSession().update(NAMESPACE + "updateInvoice",entity);
	}

	/**
	 * 判断开发票记录是否存在,如存在则返回发票记录实体.
	 *
	 * @param entity the entity
	 * @return the invoice entity
	 * @author ibm-guxinhua
	 * @date 2012-11-7 下午3:31:35
	 */
	@Override
	public InvoiceEntity existsInvoice(InvoiceEntity entity) {
		if(null == entity || StringUtils.isBlank(entity.getSourceBillNo()) || StringUtils.isBlank(entity.getOrgCode())){
			LOGGER.error("内部错误，参数为空！");
			throw new SettlementException("内部错误，参数为空！");
		}
		return (InvoiceEntity) this.getSqlSession().selectOne(NAMESPACE + "existsInvoice", entity);
	}

	/**
	 * 查询开发票记录.
	 *
	 * @param dto the dto
	 * @return the list
	 * @author ibm-guxinhua
	 * @date 2012-11-7 下午3:31:35
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<InvoiceEntity> queryInvoice(InvoiceDto dto) {
		if(null == dto || CollectionUtils.isEmpty(dto.getSourceBillNoList())){
			LOGGER.error("内部错误，参数为空！");
			throw new SettlementException("内部错误，参数为空！");
		}
		return this.getSqlSession().selectList(NAMESPACE + "queryInvoice", dto);
	}

}

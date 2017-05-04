package com.deppon.foss.module.settlement.consumer.api.server.dao;

import java.util.List;

import com.deppon.foss.module.settlement.consumer.api.shared.domain.InvoiceEntity;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.InvoiceDto;

/**
 * 开发票Dao
 * @author ibm-guxinhua
 * @date 2012-11-6 下午4:20:17
 */
public interface IInvoiceDao {

	/**
	 * 新加开发票记录
	 * @author ibm-guxinhua
	 * @date 2012-11-6 下午4:21:54
	 */
	int addInvoice(InvoiceEntity entity);
	   
	/**
	 * 更新开发票记录
	 * @author ibm-guxinhua
	 * @date 2012-11-6 下午4:22:10
	 */
	int updateInvoice(InvoiceEntity entity);
	   
	/**
	 * 判断开发票记录是否存在,如存在则返回发票记录实体 
	 * @author ibm-guxinhua
	 * @date 2012-11-6 下午4:22:20
	 */
	InvoiceEntity existsInvoice(InvoiceEntity entity);
	   
	/**
	 * 查询开发票记录
	 * @author ibm-guxinhua
	 * @date 2012-11-6 下午4:22:34
	 */
	List<InvoiceEntity> queryInvoice(InvoiceDto dto);
}

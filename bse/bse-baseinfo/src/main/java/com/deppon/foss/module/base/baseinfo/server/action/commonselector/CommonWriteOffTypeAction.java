package com.deppon.foss.module.base.baseinfo.server.action.commonselector;

import java.util.List;

import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.module.base.baseinfo.api.server.action.IQueryAction;
import com.deppon.foss.module.base.dict.api.server.service.IDataDictionaryValueService;
import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryValueEntity;
/**
 * 
 *<p>Title: CommonWriteOffTypeAction</p>
 * <p>Description: 核销标准公共选择器</p>
 * <p>Company: Deppon</p>
 * @author    130566-ZengJunfan
 * @date       2014-7-24
 */
public class CommonWriteOffTypeAction extends AbstractAction implements
		IQueryAction {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -1373311694343139347L;
	// service
	/** The data dictionary value service. */
	private IDataDictionaryValueService dataDictionaryValueService;
	// search condition
	/** The data dictionary value entity. */
	private DataDictionaryValueEntity dataDictionaryValueEntity;
	// result
	/** The data dictionary value entities. */
	private List<DataDictionaryValueEntity> dataDictionaryValueEntities;
	/**
	 * 
	 *<p>Title: query</p>
	 *<p>查询</p>
	 *@author 130566-ZengJunfan
	 *@date 2014-7-24下午4:45:35
	 * @see com.deppon.foss.module.base.baseinfo.api.server.action.IQueryAction#query()
	 * @return
	 */
	@Override
	public String query() {
		dataDictionaryValueEntities = dataDictionaryValueService
				.queryDataDictionaryValueByEntity(dataDictionaryValueEntity,
						start, limit);
		setTotalCount(dataDictionaryValueService
				.queryDataDictionaryValueByEntityCount(dataDictionaryValueEntity));
		return returnSuccess();
	}
	
	
	public void setDataDictionaryValueService(
			IDataDictionaryValueService dataDictionaryValueService) {
		this.dataDictionaryValueService = dataDictionaryValueService;
	}
	public DataDictionaryValueEntity getDataDictionaryValueEntity() {
		return dataDictionaryValueEntity;
	}
	public void setDataDictionaryValueEntity(
			DataDictionaryValueEntity dataDictionaryValueEntity) {
		this.dataDictionaryValueEntity = dataDictionaryValueEntity;
	}
	public List<DataDictionaryValueEntity> getDataDictionaryValueEntities() {
		return dataDictionaryValueEntities;
	}
	public void setDataDictionaryValueEntities(
			List<DataDictionaryValueEntity> dataDictionaryValueEntities) {
		this.dataDictionaryValueEntities = dataDictionaryValueEntities;
	}
	
}

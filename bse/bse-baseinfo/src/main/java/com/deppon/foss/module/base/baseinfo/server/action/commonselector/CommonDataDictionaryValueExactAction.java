package com.deppon.foss.module.base.baseinfo.server.action.commonselector;

import java.util.List;

import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.server.action.IQueryAction;
import com.deppon.foss.module.base.dict.api.server.service.IDataDictionaryValueService;
import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryValueEntity;
import com.deppon.foss.module.base.dict.api.shared.vo.DataDictionaryVo;

public class CommonDataDictionaryValueExactAction extends AbstractAction implements IQueryAction {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private IDataDictionaryValueService dataDictionaryValueService;
	private DataDictionaryVo dataDictionaryVo;
	/**
	 * 人员公共组件查询方法.
	 *
	 * @return the string
	 * @author 130346
	 * @date 2014-03-07 上午11:36:29
	 * @see com.deppon.foss.module.base.baseinfo.api.server.action.IQueryAction#query()
	 */
	@JSON
	@Override
	public String query() {
		long totalCount=dataDictionaryValueService.queryDataDictionaryValueByEntityCount(dataDictionaryVo.getDataDictionaryValueEntity());
		if(totalCount > 0){
			List<DataDictionaryValueEntity> dataDictionaryValueEntityList=dataDictionaryValueService.queryDataDictionaryValueByEntity(dataDictionaryVo.getDataDictionaryValueEntity(), start, limit);
			dataDictionaryVo.setDataDictionaryValueEntityList(dataDictionaryValueEntityList);
		} 
		setTotalCount(totalCount);
		
		return returnSuccess();
	}
	public DataDictionaryVo getDataDictionaryVo() {
		return dataDictionaryVo;
	}
	public void setDataDictionaryVo(DataDictionaryVo dataDictionaryVo) {
		this.dataDictionaryVo = dataDictionaryVo;
	}
	public void setDataDictionaryValueService(
			IDataDictionaryValueService dataDictionaryValueService) {
		this.dataDictionaryValueService = dataDictionaryValueService;
	}

}

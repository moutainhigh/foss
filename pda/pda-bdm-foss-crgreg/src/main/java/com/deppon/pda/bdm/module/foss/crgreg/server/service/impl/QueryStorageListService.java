package com.deppon.pda.bdm.module.foss.crgreg.server.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.transfer.common.api.shared.dto.BaseDataDictDto;
import com.deppon.foss.module.transfer.pda.api.server.service.IPDAStockService;
import com.deppon.pda.bdm.module.core.server.cache.DeptCache;
import com.deppon.pda.bdm.module.core.server.cache.UserCache;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.CrgregConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.domain.UserEntity;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.foss.crgreg.shared.domain.StockPostionEntity;

/**
 * 
 * TODO(获取库位列表)
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:Administrator,date:2012-12-1 下午2:43:52,content:TODO
 * </p>
 * 
 * @author Administrator
 * @date 2013-07-15 上午10:56:52
 * @since
 * @version
 */
public class QueryStorageListService implements
		IBusinessService<List<StockPostionEntity>, Void> {

	private static final Log LOG = LogFactory.getLog(QueryStorageListService.class);

	private IPDAStockService ipdaStockService;
	
	private DeptCache deptCache;
	
	private UserCache userCache;
	
	@Override
	public Void parseBody(AsyncMsg asyncMsg) throws PdaBusiException {
		return null;
	}

	@Override
	public List<StockPostionEntity> service(AsyncMsg asyncMsg, Void param)
			throws PdaBusiException {
		LOG.info("-----获取库位列表-----");
		List<StockPostionEntity> postionVos = new ArrayList<StockPostionEntity>();
		// 外场编码
		String orgCode = getOrgCode(asyncMsg.getUserCode());
		List<BaseDataDictDto> baseDataDicts = null;
		try {
			//获取派送货区
			baseDataDicts = ipdaStockService.areaByOrgcodeList(orgCode);
			//获取派送货区库位
			if (baseDataDicts != null && baseDataDicts.size() != 0) {
				for (BaseDataDictDto dictDto : baseDataDicts) {
					List<BaseDataDictDto> dtos = ipdaStockService.queryPositionList(orgCode, dictDto.getValueCode());
					addPostions(postionVos,dictDto.getValueCode(),dtos);
				}
			}
		} catch (BusinessException e) {
			throw new FossInterfaceException(e.getCause(), e.getErrorCode());
		}
		if(postionVos.size() == 0){
			//LOG.error("获取派送库位为空 ， 外场编码为:"+orgCode+"  派送获取编码为："+baseDataDicts.get(0).getValueCode());
		}
		return postionVos;
	}


	private void addPostions(List<StockPostionEntity> postionVos,
			String valueCode, List<BaseDataDictDto> dtos) {
		StockPostionEntity inOutStockPostionEntity = null;
		for(BaseDataDictDto dto: dtos){
			inOutStockPostionEntity = new StockPostionEntity();
			inOutStockPostionEntity.setAreaCode(valueCode);
			inOutStockPostionEntity.setValueCode(dto.getValueCode());
			inOutStockPostionEntity.setValueName(dto.getValueName());
			postionVos.add(inOutStockPostionEntity);
		}
	}

	private String getOrgCode(String userCode) {
		 UserEntity userEntity = userCache.get(userCode);
		 return deptCache.get(userEntity.getDeptId()).getDeptCode();
	}

	
	@Override
	public String getOperType() {
		return CrgregConstant.OPER_TYPE_REG_STOCK_POSTION_LIST.VERSION;
	}

	@Override
	public boolean isAsync() {
		return false;
	}
	
	public void setIpdaStockService(IPDAStockService ipdaStockService) {
		this.ipdaStockService = ipdaStockService;
	}

	public void setDeptCache(DeptCache deptCache) {
		this.deptCache = deptCache;
	}

	public void setUserCache(UserCache userCache) {
		this.userCache = userCache;
	}
	
}

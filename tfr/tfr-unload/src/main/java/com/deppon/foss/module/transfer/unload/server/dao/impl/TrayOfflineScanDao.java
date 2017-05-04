package com.deppon.foss.module.transfer.unload.server.dao.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.common.api.server.service.IFOSSToWkService;
import com.deppon.foss.module.transfer.common.api.shared.domain.RequestParameterEntity;
import com.deppon.foss.module.transfer.unload.api.server.dao.ITrayOfflineScanDao;
import com.deppon.foss.module.transfer.unload.api.shared.domain.TrayOfflineScanEntity;
import com.deppon.foss.module.transfer.unload.api.shared.domain.TrayOfflineScanExpressEntity;
import com.deppon.foss.module.transfer.unload.api.shared.dto.QueryTrayOfflineScanConditionDto;

@SuppressWarnings("unchecked")
public class TrayOfflineScanDao extends iBatis3DaoImpl implements ITrayOfflineScanDao{
	
	private static final String NAMESPACE="tfr-nuload-trayscan.";
	
	private IFOSSToWkService fossToWkService;
	

	public void setFossToWkService(IFOSSToWkService fossToWkService) {
		this.fossToWkService = fossToWkService;
	}

	/** 
	 * 查询叉车离线扫描信息 零担
	 * @author foss-heyongdong
	 * @date 2014年1月6日 16:01:22
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.ITrayOfflineScanDao#querytrayOfflineScanInfo(QueryTrayOfflineScanConditionDto,limit,start)
	 */
	@Override
	public List<TrayOfflineScanEntity> querytrayOfflineScanInfo(
			QueryTrayOfflineScanConditionDto queryTrayOfflineScanConditionDto,int limit,int start) {
		//分页
		RowBounds rowBounds = new RowBounds(start, limit);
		//返回查询结果
		return this.getSqlSession().selectList(NAMESPACE+"querytrayOfflineScanInfo", queryTrayOfflineScanConditionDto,rowBounds);
		 
	}

	/** 
	 * 查询叉车离线扫描信息 快递
	 * @author foss-heyongdong
	 * @date 2014年1月6日 16:01:22
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.ITrayOfflineScanDao#querytrayOfflineScanInfo(QueryTrayOfflineScanConditionDto,limit,start)
	 */
	@Override
	public String querytrayOfflineScanInfoExpress(
			QueryTrayOfflineScanConditionDto queryTrayOfflineScanConditionDto,int limit,int start) {
		
		try {
			//分页
			queryTrayOfflineScanConditionDto.setCurrentPageNo(start);
			queryTrayOfflineScanConditionDto.setPageSize(limit);
			RequestParameterEntity parameter=new RequestParameterEntity();
			parameter.setRequestEntity(queryTrayOfflineScanConditionDto);
			//查询返回的数据
			String result = fossToWkService.queryTrayOfflineScanListExpress(parameter);
			return result;
		} catch (Exception e) {
			logger.error("查询数据异常");
			e.printStackTrace();
		}
		
		return "";
	}

	
	/**
	 * 查询叉车离线扫描信息总条数  零担
	 * @author foss-heyongdong
	 * @Date 2014年1月8日 09:56:58
	 * @param queryTrayOfflineScanConditionDto
	 * @return List
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.ITrayOfflineScanDao#querytrayOfflineScanTotal(QueryTrayOfflineScanConditionDto)
	 */
	@Override
	public List<TrayOfflineScanEntity>  querytrayOfflineScanInfoNoPage(QueryTrayOfflineScanConditionDto queryTrayOfflineScanConditionDto) {
		
		return this.getSqlSession().selectList(NAMESPACE+"querytrayOfflineScanInfo", queryTrayOfflineScanConditionDto);
	}
	
	/**
	 * 查询叉车离线扫描信息总条数  快递
	 * @author foss-heyongdong
	 * @Date 2014年1月8日 09:56:58
	 * @param queryTrayOfflineScanConditionDto
	 * @return List
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.ITrayOfflineScanDao#querytrayOfflineScanTotal(QueryTrayOfflineScanConditionDto)
	 */
	@Override
	public List<TrayOfflineScanExpressEntity>  querytrayOfflineScanInfoNoPageExpress(QueryTrayOfflineScanConditionDto queryTrayOfflineScanConditionDto) {
		
		return this.getSqlSession().selectList(NAMESPACE+"querytrayOfflineScanInfoExpress", queryTrayOfflineScanConditionDto);
	}
	
	

}

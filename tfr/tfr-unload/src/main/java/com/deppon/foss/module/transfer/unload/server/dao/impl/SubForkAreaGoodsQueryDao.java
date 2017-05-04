package com.deppon.foss.module.transfer.unload.server.dao.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.common.api.server.service.IFOSSToWkService;
import com.deppon.foss.module.transfer.common.api.shared.domain.RequestParameterEntity;
import com.deppon.foss.module.transfer.unload.api.server.dao.ISubForkAreaGoodsQueryDao;
import com.deppon.foss.module.transfer.unload.api.shared.domain.SubForkAreaGoodsEntity;
import com.deppon.foss.module.transfer.unload.api.shared.vo.SubForkAreaGoodsVo;

public class SubForkAreaGoodsQueryDao extends iBatis3DaoImpl implements ISubForkAreaGoodsQueryDao{
	
	/**命名空间常量*/
	private static final String NAMESPACE = "foss.unload.querySubForkAreaGoods.";
	
	private IFOSSToWkService fossToWkService;
	
	
	public void setFossToWkService(IFOSSToWkService fossToWkService) {
		this.fossToWkService = fossToWkService;
	}

	/**
	 * @author zenghaibin  零担
	 * @date 2014-07-03 上午9:35:34
	 * @return 返回一个参数列别List<BaseDataDictDto>
	 */
	@Override
	public List<SubForkAreaGoodsEntity> querySubForkAreaGoods(SubForkAreaGoodsVo subForkAreaGoodsVo,int limit,int start){
		//分页
		RowBounds rowBounds = new RowBounds(start, limit);
				
		return this.getSqlSession().selectList(NAMESPACE+"querySubForkAreaGoods", subForkAreaGoodsVo, rowBounds);
		
	}
	
	/**
	 * @author zenghaibin  快递
	 * @date 2014-07-03 上午9:35:34
	 * @return 返回一个参数列别List<BaseDataDictDto>
	 */
	@Override
	public String querySubForkAreaGoodsExpress(SubForkAreaGoodsVo subForkAreaGoodsVo,int limit,int start){
		//分页
		//RowBounds rowBounds = new RowBounds(start, limit);
		try {
			subForkAreaGoodsVo.setCurrentPageNo(start);
			subForkAreaGoodsVo.setPageSize(limit);
			RequestParameterEntity resultParameter=new RequestParameterEntity();
			resultParameter.setRequestEntity(subForkAreaGoodsVo);
			//获取数据
			String result=fossToWkService.querySubForkAreaGoodsExpress(resultParameter);
			return result;
		} catch (Exception e) {
			logger.error("查询数据异常！");
		}
		return "";
		//return this.getSqlSession().selectList(NAMESPACE+"querySubForkAreaGoodsExpress", subForkAreaGoodsVo, rowBounds);
		
	}

	/**
	 *分页  零担
	 *@author zenghaibin
	 *@param subForkAreaGoodsVo 查询参数vo
	 *@return 数据量
	 **/
	@Override
	public Long querySubForkAreaGoodsCount(SubForkAreaGoodsVo subForkAreaGoodsVo) {
		
		return (Long)this.getSqlSession().selectOne(NAMESPACE+"querySubForkAreaGoodsCount", subForkAreaGoodsVo);
	}
	
	/**
	 *分页  快递
	 *@author zenghaibin
	 *@param subForkAreaGoodsVo 查询参数vo
	 *@return 数据量
	 **/
	@Override
	public Long querySubForkAreaGoodsCountExpress(SubForkAreaGoodsVo subForkAreaGoodsVo) {
		
		return (Long)this.getSqlSession().selectOne(NAMESPACE+"querySubForkAreaGoodsCountExpress", subForkAreaGoodsVo);
	}
	
	

}

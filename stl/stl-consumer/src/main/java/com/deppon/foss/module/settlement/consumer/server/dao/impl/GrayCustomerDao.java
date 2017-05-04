package com.deppon.foss.module.settlement.consumer.server.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.settlement.consumer.api.server.dao.IGrayCustomerDao;
import com.deppon.foss.module.settlement.consumer.api.shared.domain.GrayCustomerEntity;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.GrayCustomerDto;

/**
 * 更新灰名单
 * 
 *  @author 269044-zhurongrong
 *  @date 2016-04-12
 */
public class GrayCustomerDao extends iBatis3DaoImpl implements IGrayCustomerDao {
	
	/**
	 * 定义命名空间
	 */
	public static final String NAMESPACE = "foss.stl.GrayCustomerDao.";
	
	/**
	 * 查询灰名单数据
	 * 
	 * @author 269044-zhurongrong
	 * @date 2016-04-15
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<GrayCustomerEntity> queryGrayCustomerList(int start, int limit) {
		RowBounds rb = new RowBounds(start, limit);
		return this.getSqlSession().selectList(NAMESPACE + "queryGrayCustomerList",null,rb);
	}

	/**
	 * 根据客户编码查询客户是否为灰名单客户
	 * 
	 * @author 269044-zhurongrong
	 * @date 2016-04-18
	 */
	@Override
	@SuppressWarnings("unchecked")
	public GrayCustomerEntity queryGrayCustomerByCustomerCode(String customer) {
		List<GrayCustomerEntity> list = this.getSqlSession().selectList(NAMESPACE + "queryGrayCustomerByCustomerCode",customer);
		GrayCustomerEntity grayCustomerEntity = null ;
		if(list!= null && list.size() > 0) {
			grayCustomerEntity = list.get(0);
		}
		return grayCustomerEntity;
	}

	/**
	 * 根据客户编码集合查询灰名单信息
	 * 
	 * @author 269044-zhurongrong
	 * @date 2016-04-20
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<GrayCustomerEntity> queryGrayCustomerListByCustomerCodes(
			List<String> customerCodes) {	
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("customerCodes", customerCodes);
		List<GrayCustomerEntity> list = this.getSqlSession().selectList(NAMESPACE + "queryListByCustomerCodes",map);
		return list;
	}
	
	/**
	 * 根据客户编码删除灰名单
	 *
	 * @author 269044-zhurongrong
	 * @date 2016-04-18
	 */
	@Override
	public int deleteGrayCustomerByCustomerCode(GrayCustomerDto grayCustomerDto) {
		int i = this.getSqlSession().update(NAMESPACE + "deleteGrayCustomerByCustomerCode",grayCustomerDto);
		return i;
	}

	/**
	 * 添加灰名单信息
	 * 
	 * @author 269044-zhurongrong
	 * @date 2016-04-18
	 */
	@Override
	public int addGrayCustomer(GrayCustomerEntity grayCustomerEntity) {
		int i = this.getSqlSession().insert(NAMESPACE + "addGrayCustomer",grayCustomerEntity);
		return i;
	}
	
	/**
	 * 查询灰名单信息总条数
	 * 
	 * @author 269044-zhurongrong
	 * @dare 2016-04-28
	 */
	@Override
	public int countQueryGrayCustomer() {
		int i = (Integer) this.getSqlSession().selectOne(NAMESPACE + "countQueryGrayCustomer");
		return i;
	}

}

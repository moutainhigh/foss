package com.deppon.foss.module.base.baseinfo.server.dao.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.ISpecialdiscountDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SpecialdiscountEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.SpecialAddressException;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 
 * 特惠活动客户打折扣dao接口
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:CSS,date:2015-7-28 下午2:37:25,
 * </p>
 * 
 * @author 261997
 * @date 2015-7-28 下午2:37:25
 * @since
 * @version
 */
public class SpecialdiscountDao extends SqlSessionDaoSupport implements
		ISpecialdiscountDao {

	private static final String NAMESPACE = "foss.bse.bse-baseinfo.specialdiscount.";

	/**
	 * 插入实体信息
	 * 
	 * @param entity
	 * @return
	 * @author css-261997
	 * @date 2015-07-28 上午10:36:32
	 */
	@Override
	public int insert(SpecialdiscountEntity entity) {
		if (entity == null) {
			return FossConstants.FAILURE;
		}
		entity.setId(UUIDUtils.getUUID());// 生成主键ID

		int result = getSqlSession().insert(
				NAMESPACE + "insertSpecialdiscount", entity);

		return 0 < result ? FossConstants.SUCCESS : FossConstants.FAILURE;
	}

	/**
	 * 修改实体信息
	 * 
	 * @param entity
	 * @return
	 * @author css-261997
	 * @date 2015-07-28 上午10:36:32
	 */
	@Override
	public int updateBySpecialdiscount(SpecialdiscountEntity entity) {
		if (entity == null) {
			return FossConstants.FAILURE;
		}

		int result = getSqlSession().insert(
				NAMESPACE + "updateSpecialdiscount", entity);

		return 0 < result ? FossConstants.SUCCESS : FossConstants.FAILURE;
	}

	/**
	 * 根据传入的主键查询符合条件的实体信息
	 * 
	 * @param entity
	 * @return
	 * @author css-261997
	 * @date 2015-07-28 上午10:36:32
	 */
	@Override
	public SpecialdiscountEntity selectByPrimaryKey(SpecialdiscountEntity specialdiscount) {
		String id = specialdiscount.getId();
		BigDecimal crmid = specialdiscount.getCrmId();
		if (StringUtils.isBlank(id)&&StringUtils.isBlank(crmid.toString())) {
			throw new SpecialAddressException("ID和Crmid都为空");
		}
		@SuppressWarnings("unchecked")
		List<SpecialdiscountEntity> entitys = this.getSqlSession().selectList(
				NAMESPACE + "querySpecialdiscount", specialdiscount);
		return CollectionUtils.isEmpty(entitys) ? null : entitys.get(0);
	}
	
	/**
	 * 根据传入的客户编码，与开单时间查询符合条件的实体信息
	 * 
	 * @param entity
	 * @return
	 * @author css-261997
	 * @date 2015-08-14 上午15:36:32
	 */
	@Override
	public SpecialdiscountEntity selectBySpecialdiscountYX(String code) {		
		if (StringUtils.isBlank(code)) {
			throw new SpecialAddressException("客户编码为空！");
		}
		SpecialdiscountEntity specialdiscountEntity = new SpecialdiscountEntity();
		specialdiscountEntity.setCode(code);
		
		@SuppressWarnings("unchecked")
		List<SpecialdiscountEntity> entitys = this.getSqlSession().selectList(
				NAMESPACE + "querySpecialdiscountByYX", specialdiscountEntity);
		return CollectionUtils.isEmpty(entitys) ? null : entitys.get(0);
	}
	
	@Override
	public SpecialdiscountEntity selectBySpecialdiscountTime(String code,Date createtime) {		
		if (StringUtils.isBlank(code)) {
			throw new SpecialAddressException("客户编码为空！");
		}
		Map map = new HashMap();
		map.put("code", code);
		map.put("createtime", createtime);

		@SuppressWarnings("unchecked")
		List<SpecialdiscountEntity> entitys = this.getSqlSession().selectList(
				NAMESPACE + "querySpecialdiscountByTime", map);
		return CollectionUtils.isEmpty(entitys) ? null : entitys.get(0);
	}
	
	
	@Override
	public boolean selectBySpecialdiscountKHFQ(String code,Date createtime) {		
		if (StringUtils.isBlank(code)) {
			throw new SpecialAddressException("客户编码为空！");
		}
		boolean bool = false;
		
		Map map = new HashMap();
		map.put("code", code);
		map.put("createtime", createtime);

		int i = (Integer) this.getSqlSession().selectOne(NAMESPACE + "querySpecialdiscountKHFQ",map);
		if(i>0){
			bool = true;
		}
		return bool;
	}
	

}

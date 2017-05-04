package com.deppon.foss.module.pickup.pricing.server.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IToAddPartnerProgramDao;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ToAddPartnerProgramEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.ToAddPartnerProgramCondtionDto;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.define.FossConstants;

public class ToAddPartnerProgramDao extends SqlSessionDaoSupport implements IToAddPartnerProgramDao{
	
	public static final String NAMESPACE = "com.deppon.foss.module.pickup.pricing.api.server.dao.ToAddPartnerProgramEntityMapper.";

	/**
	 * @功能 根据条件查询合伙人到达加收方案总数
	 * @author 265475
	 * @date 2016-9-30 9:37:54
	 * @return
	 */
	@Override
	public Long queryToAddPartnerProgramVoBatchInfoCount(
			ToAddPartnerProgramCondtionDto toAddPartnerProgramCondtionDto) {
		return (Long) this.getSqlSession().selectOne(NAMESPACE+"queryToAddPartnerProgramVoBatchInfoCount", toAddPartnerProgramCondtionDto);
	}

	/**
	 * <p>
	 * Description:根据NAME查询偏线价格方案<br />
	 * </p>
	 * @author 265475
	 * @date 2016-09-05 下午2:04:08
	 * @return
	 * @version V1.0
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ToAddPartnerProgramEntity> queryToAddPartnerProgramByName(String planname) {
		String sql = NAMESPACE + "queryToAddPartnerProgramByName";	
		return getSqlSession().selectList(sql, planname);
	}

	/**
	 * .
	 * <p>
	 * 新增合伙人到达加收方案<br/>
	 * 方法名：addToAddPartnerProgram
	 * </p>
	 * 
	 * @author 265475
	 * 
	 * @时间 2016-09-05
	 * 
	 * @since JDK1.6
	 */
	@Override
	public int insertSelective(ToAddPartnerProgramEntity record) {
		String sql = NAMESPACE + "insertSelective";		
		return getSqlSession().insert(sql, record);
	}

	/**
	 * @功能 根据条件查询合伙人到达加收方案
	 * @author 265475
	 * @date 2016-9-30 9:37:54
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ToAddPartnerProgramEntity> querytoAddPartnerProgramVoBatchInfo(
			ToAddPartnerProgramCondtionDto toAddPartnerProgramCondtionDto, int start, int limit) {
		if (start == 0 && limit == 0) {
			return this.getSqlSession().selectList(NAMESPACE+"queryToAddPartnerProgramVoBatchInfo", toAddPartnerProgramCondtionDto);
		} else {
			RowBounds rowBounds = new RowBounds(start, limit);
			return this.getSqlSession().selectList(NAMESPACE+"queryToAddPartnerProgramVoBatchInfo", toAddPartnerProgramCondtionDto, rowBounds);
		}
	}
	
	/**
     * 
     * <p>(
     * 根据方案ID查询合伙到达加收方案明细信息
     * )<br/>
     * </p> 
     * 
     * @author 265475
     * @date 2012-12-20 下午3:29:14
     * @return
     * @see
     */
	@Override
	public ToAddPartnerProgramEntity selectById(String toAddPartnerProgramid) {
		String sql = NAMESPACE + "selectById";		
		return (ToAddPartnerProgramEntity)getSqlSession().selectOne(sql, toAddPartnerProgramid);	
	}
	
	/**
	 * .
	 * <p>
	 * 修改合伙人到达加收方案<br/>
	 * 方法名：updateToAddPartnerProgram
	 * </p>
	 * 
	 * @author zoushengli
	 * 
	 * @时间 2016-09-05
	 * 
	 * @since JDK1.6
	 */
	@Override
	public int updateByPrimaryKeySelective(ToAddPartnerProgramEntity record) {
		String sqlAddress = NAMESPACE + "updateByToAddPartnerProgramPrimaryKey";		
		return getSqlSession().update(sqlAddress, record);				
	}
	
	/**
	 * .
	 * <p>
	 * 删除合伙人到达加收方案<br/>
	 * 方法名：deletePricingDiscount
	 * </p>
	 * 
	 * @author 邹胜利
	 * 
	 * @时间 2016-09-05
	 * 
	 * @since JDK1.6
	 */
	@Override
	public int deleteByPrimaryKey(List<String> ids) {
		Map<String, Object> paraMap = new HashMap<String,Object>();
		paraMap.put("ids", ids);
		return getSqlSession().delete(NAMESPACE + "deleteByPrimaryKey", paraMap);
	}
	
	/**
	 * 查询ID是否有效
	 * @author 265475
     * @date 2012-12-20 下午3:29:14
     * @return
	 */
	@Override
	public ToAddPartnerProgramEntity selectByPrimaryKey(String toAddPartnerProgramid) {
		String sql = NAMESPACE + "selectByPrimaryKey";		
		return (ToAddPartnerProgramEntity)getSqlSession().selectOne(sql, toAddPartnerProgramid);	
	}
	
	/**
	 * 更新时间
	 * @author 265475
     * @date 2016-09-05 下午3:29:14
     * @return
	 */
	@Override
	public int updateToAddPartnerProgramEndTime(ToAddPartnerProgramEntity record) {
		String sqlAddress = NAMESPACE + "updateToAddPartnerProgramEndTime";		
		return getSqlSession().update(sqlAddress, record);
	}

	@SuppressWarnings("unchecked")
	@Override
	public ToAddPartnerProgramEntity searchToAddPartnerProgramByArgument(String orgCode, String transportFlag, Date billTime) {
		@SuppressWarnings("rawtypes")
		Map paramenterMap = new HashMap();
		paramenterMap.put("active", FossConstants.ACTIVE);
		paramenterMap.put("orgCode", orgCode);
		paramenterMap.put("transportFlag", PricingConstants.ProductEntityConstants.PRICING_PRODUCT_C1_C20001);
		paramenterMap.put("receiveDate", billTime);
		List<ToAddPartnerProgramEntity> list = getSqlSession().selectList(NAMESPACE + "searchToAddPartnerProgramByArgument",paramenterMap);
		if(CollectionUtils.isNotEmpty(list)){
			return list.get(0);
		}else{
			return null;
		}
	}
	
	/**
	 * <p>
	 * Description:根据主键Id批量激活合伙人到达加收方案<br />
	 * </p>
	 * @author zoushengli
	 * @version 0.1 2016-09-05
	 * @param id
	 * @return
	 * int
	 */
	@Override
	public int updateToAddPartnerProgramActiveById(List<String> ids, String yesOrNo) {
		Map<String, Object> paraMap = new HashMap<String,Object>();
		paraMap.put("active", yesOrNo);
		paraMap.put("ids", ids);
		paraMap.put("versionNo", new Date().getTime());
		return getSqlSession().update(NAMESPACE + "updateToAddPartnerProgramActiveById", paraMap);
	}
	
	

	/**
	 * 根据查询条件查询加收方案信息
	 * @author Foss-351326-xingjun 
	 * @date 2016-8-31 下午7:16:33
	 * @param toAddPartnerProgramCondtionDto
	 * @return 
	 * @see com.deppon.foss.module.pickup.pricing.api.server.dao.IToAddPartnerProgramDao#addedFeePlanCaculateQuery(com.deppon.foss.module.pickup.pricing.api.shared.dto.ToAddPartnerProgramCondtionDto)
	 */
	@Override
	public ToAddPartnerProgramEntity addedFeePlanCaculateQuery(
			ToAddPartnerProgramCondtionDto toAddPartnerProgramCondtionDto) {
		return (ToAddPartnerProgramEntity) this.getSqlSession().selectOne(NAMESPACE+"queryAddedFeePlanCaculate", toAddPartnerProgramCondtionDto);

	}

	/**
	 * <p>TODO(合伙人到达加收方案：目的站网点名称)</p> 
	 * @author 352676 
	 * @date 2016年9月1日 下午4:09:32
	 * @param saleDepartmentEntity
	 * @param limit
	 * @param start
	 * @return
	 * @see
	 */
	@SuppressWarnings("unchecked")
	public List<SaleDepartmentEntity> queryTwolevelAgencyDeptsByCondition(
			SaleDepartmentEntity saleDepartmentEntity, int limit, int start){
		RowBounds bounds=new RowBounds(start, limit);
		String sql = "foss.pkp.SaleDepartmentEntityMapper.";
		return this.getSqlSession().selectList(sql+"queryTwolevelVehAgencyDept", saleDepartmentEntity,bounds);
	}
	/**
	 * 合伙人到达加收方案：目的站网点名称  
	 * @author 352676 
	 * @date 2016年9月1日 下午4:13:32
	 * @param saleDepartmentEntity
	 * @return
	 * @see
	 */
	public Long queryTwolevelAgencyDeptsCount(SaleDepartmentEntity saleDepartmentEntity){
		
		String sql = "foss.pkp.SaleDepartmentEntityMapper.";
		Long count=(Long) this.getSqlSession().selectOne(sql+"queryTwolevelVehAgencyDeptCount", saleDepartmentEntity);
		return count ;
	}

}

package com.deppon.foss.module.pickup.pricing.api.server.dao;

import java.util.Date;
import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ToAddPartnerProgramEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.ToAddPartnerProgramCondtionDto;


public interface IToAddPartnerProgramDao {

	/**
	 * @功能 根据条件查询合伙人到达加收方案总数
	 * @author 265475
	 * @date 2016-9-30 9:37:54
	 * @return
	 */
	Long queryToAddPartnerProgramVoBatchInfoCount(ToAddPartnerProgramCondtionDto toAddPartnerProgramCondtionDto);

	/**
	 * @功能 根据条件查询合伙人到达加收方案
	 * @author 265475
	 * @date 2016-9-30 9:37:54
	 * @return
	 */
	public List<ToAddPartnerProgramEntity> querytoAddPartnerProgramVoBatchInfo(
			ToAddPartnerProgramCondtionDto toAddPartnerProgramCondtionDto, int start, int limit);
	
	/**
     * 
     * <p>(
     * 根据方案ID查询合伙到达加收方案明细信息
     * )<br/>
     * </p> 
     * @author 265475
	 * @date 2016-09-05 下午2:04:08
	 * @return
	 * @version V1.0
     */
	public ToAddPartnerProgramEntity selectById(String toAddPartnerProgramid);
	
	
	/**
	 * <p>
	 * Description:根据NAME查询偏线价格方案<br />
	 * </p>
	 * @author zoushengli
	 * @version 0.1 2016-09-05
	 * @param name
	 * @return
	 * List<MarketingEventDto>
	 */
	public List<ToAddPartnerProgramEntity> queryToAddPartnerProgramByName(String name);
	

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
	public int insertSelective(ToAddPartnerProgramEntity record);
	
	/**
     * <p>
     * Description:根据主键更新内容不为空的字段<br />
     * </p>
     * @author zoushengli
     * @version 0.1 2016-09-05
     * @param record
     * @return
     * int
     */
	public int updateByPrimaryKeySelective(ToAddPartnerProgramEntity record);


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
	public int deleteByPrimaryKey(List<String> ids);

	/**
	 * 查询ID是否有效
	 * @author 265475
     * @date 2012-12-20 下午3:29:14
     * @return
	 */
	public ToAddPartnerProgramEntity selectByPrimaryKey(String toAddPartnerProgramid);
	

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
	public int updateToAddPartnerProgramActiveById(List<String> ids, String yesOrNo);
	

	ToAddPartnerProgramEntity searchToAddPartnerProgramByArgument(String orgCode, String transportFlag, Date billTime);


	/**
     * <p>
     * Description: 修改截止时间<br />
     * </p>
     * @author zoushengli
     * @version 0.1 2016-09-05
     * @param record
     * @return
     * int
     */
	public int updateToAddPartnerProgramEndTime(ToAddPartnerProgramEntity record);
	

	
	/**
	 * 根据查询条件查询加收方案信息
	 * @author Foss-351326-xingjun 
	 * @date 2016-8-31 下午7:15:16
	 * @param toAddPartnerProgramCondtionDto
	 * @return
	 * @see
	 */
	public ToAddPartnerProgramEntity addedFeePlanCaculateQuery(
			ToAddPartnerProgramCondtionDto toAddPartnerProgramCondtionDto);

	/**
	 * <p>(伙人到达加收方案：目的站网点名称)</p> 
	 * @author 352676 
	 * @date 2016年9月1日 下午4:09:32
	 * @param saleDepartmentEntity
	 * @param limit
	 * @param start
	 * @return
	 * @see
	 */
	public List<SaleDepartmentEntity> queryTwolevelAgencyDeptsByCondition(
			SaleDepartmentEntity saleDepartmentEntity, int limit, int start);
	/**
	 * 合伙人到达加收方案：目的站网点名称  
	 * @author 352676 
	 * @date 2016年9月1日 下午4:13:32
	 * @param saleDepartmentEntity
	 * @return
	 * @see
	 */
	public Long queryTwolevelAgencyDeptsCount(SaleDepartmentEntity saleDepartmentEntity);

	

}

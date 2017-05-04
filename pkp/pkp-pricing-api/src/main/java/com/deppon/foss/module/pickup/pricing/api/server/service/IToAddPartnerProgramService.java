package com.deppon.foss.module.pickup.pricing.api.server.service;

import java.util.Date;
import java.util.List;

import org.apache.poi.ss.usermodel.Workbook;

import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ToAddPartnerProgramEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.ToAddPartnerProgramCondtionDto;
import com.deppon.foss.module.pickup.pricing.api.shared.vo.ToAddPartnerProgramVo;

public interface IToAddPartnerProgramService {

	Long queryToAddPartnerProgramVoBatchInfoCount(
			ToAddPartnerProgramCondtionDto toAddPartnerProgramCondtionDto);

	public List<ToAddPartnerProgramEntity> querytoAddPartnerProgramVoBatchInfo(
			ToAddPartnerProgramCondtionDto toAddPartnerProgramCondtionDto, int start, int limit);

	void immediatelyActiveToAddPartnerProgram(String toAddPartnerProgramid, String yesOrNo, Date effectiveTime);
	
	ToAddPartnerProgramVo toAddPartnerProgramPrice(ToAddPartnerProgramEntity toAddPartnerProgramEntity);
	
	/**
	 * <p>修改合伙人到达加收方案</p> 
	 * 
	 * @author zoushengli
	 * 
	 * @date 2016-09-05 下午3:04:15
	 * 
	 * @param marketingEventEntity
	 * 
	 * @see
	 */
	public void updateToAddPartnerProgram(ToAddPartnerProgramEntity record);
	
	
	/**
	 * <p>删除合伙人到达加收方案</p> 
	 * 
	 * @author sz
	 * 
	 * @date 2016-09-05 下午3:02:48
	 * 
	 * @param marketingEventId
	 * 
	 * @see
	 */
	public void deleteByPrimaryKey(List<String> ids);
	

	
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
	public void updateToAddPartnerProgramActiveById(List<ToAddPartnerProgramEntity> opps, String yesOrNo);
	
	/**
	 * <p>
	 * Description:导出合伙人到达加收方案<br />
	 * </p>
	 * @author zoushengli
	 * @version 0.1 2016-09-05
	 * @param id
	 * @return ExportResource
	 * int
	 */
	public ExportResource exportToAddPartnerProgram(ToAddPartnerProgramCondtionDto dto);

	/**
     * <p>
     * Description:根据ID查询记录<br />
     * </p>
     * @author zoushengli
     * @version 0.1 2016-09-05
     * @param id
     * @return
     * ToAddPartnerProgramVo
     */
	public ToAddPartnerProgramVo selectById(String id);


	/**
	 * @author zoushengli
	 * @date 2016-09-05 下午2:04:08
	 * @return
	 * @version V1.0
	 */
	public void importToAddPartnerProgram(Workbook book);

	/**
	 * 立即合伙人到达加收方案
	 * @author 265475
	 * @date 2016-09-05 下午2:04:08
	 * @return
	 * @version V1.0
	 * @param toAddPartnerProgramid	中止方案id
	 * @param effectiveTime 中止时间
	 */
	public void immediatelyStopToAddPartnerProgram(String toAddPartnerProgramid, Date effectiveTime);
	
	/**
	 * 根据查询条件查询加收方案信息
	 * @author Foss-351326-xingjun 
	 * @date 2016-8-31 上午10:30:57
	 * @param toAddPartnerProgramCondtionDto
	 * @return
	 * @see
	 */
	public ToAddPartnerProgramEntity addedFeePlanCaculateQuery(ToAddPartnerProgramCondtionDto toAddPartnerProgramCondtionDto);

	/**
	 * <p>TODO(合伙人到达加收方案：目的站网点名称)</p> 
	 * @author 352676 
	 * @date 2016年9月1日 下午3:55:05
	 * @param saleDepartmentEntity
	 * @param limit
	 * @param start
	 * @return
	 * @see
	 */
	public List<SaleDepartmentEntity> queryTwolevelAgencyDeptsByCondition(
			SaleDepartmentEntity saleDepartmentEntity, int limit, int start);
	/**
	 * <p>TODO(合伙人到达加收方案：目的站网点名称 数量)</p> 
	 * @author 352676 
	 * @date 2016年9月1日 下午3:56:37
	 * @param saleDepartmentEntity
	 * @return
	 * @see
	 */
	public Long queryTwolevelAgencyDeptsCount(SaleDepartmentEntity saleDepartmentEntity);


}

package com.deppon.foss.module.base.baseinfo.api.server.dao;

import java.util.Date;
import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressPartSalesDeptEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.ExpressPartSalesDeptQueryDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.ExpressPartSalesDeptResultDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.ExpressSaleDepartmentResultDto;

/**
 * 快递点部营业部映射关系Dao
 * 
 * @author foss-qiaolifeng
 * @date 2013-7-25 上午10:13:11
 */
public interface IExpressPartSalesDeptDao {

	
	/**
	 * 根据快递点部名称查询快递点部营业部映射关系总条数
	 * @author foss-qiaolifeng
	 * @date 2013-8-27 下午2:32:24
	 * @param expressPartSalesDeptQueryDto
	 * @return
	 */
	long queryTotalByCondition(ExpressPartSalesDeptQueryDto expressPartSalesDeptQueryDto);
	/**
	 * 根据快递点部名称查询快递点部营业部映射关系
	 * 
	 * @author foss-qiaolifeng
	 * @date 2013-7-25 上午10:06:34
	 * @param expressPartSalesDeptQueryDto
	 * @return
	 */
	List<ExpressPartSalesDeptResultDto> queryExpressPartSalesDeptByCondition(
			ExpressPartSalesDeptQueryDto expressPartSalesDeptQueryDto, int start, int limit);

	/**
	 * 根据营业部编码查询快递点部信息
	 * 
	 * @author foss-zhangjiheng
	 * @date 2013-7-25 上午10:06:34
	 * @param expressPartSalesDeptQueryDto
	 * @return
	 */
	ExpressPartSalesDeptResultDto queryExpressPartSalesDeptBySalesCode(
			String salesDeptCode);

	/**
	 * 根据营业部编码和创建时间查询快递点部信息
	 * 
	 * @author foss-zhangjiheng
	 * @date 2013-7-25 上午10:06:34
	 * @param expressPartSalesDeptQueryDto
	 * @return
	 */
	List<ExpressPartSalesDeptResultDto> queryExpressPartSalesDeptBySalesCodeAndTime(
			String salesDeptCode,Date createTime);

	/**
	 * 根据快递点部编码查询快递点部营业部映射关系
	 * 
	 * @author foss-qiaolifeng
	 * @date 2013-7-26 上午11:14:09
	 * @param expressPartSalesDeptQueryDto
	 * @return
	 */
	List<ExpressPartSalesDeptResultDto> queryExpressPartSalesDeptByExpressPartCode(
			ExpressPartSalesDeptQueryDto expressPartSalesDeptQueryDto);

	/**
	 * 根据营业部编码列表查询营业部扩展dto
	 * 
	 * @author foss-qiaolifeng
	 * @date 2013-7-26 下午1:49:21
	 * @param expressPartSalesDeptQueryDto
	 * @return
	 */
	List<ExpressSaleDepartmentResultDto> querySaleDepartmentResultDtoBySalesDeptCodeList(
			ExpressPartSalesDeptQueryDto expressPartSalesDeptQueryDto);

	/**
	 * 根据营业部条件查部营业部信息,多条件模糊查询
	 * 
	 * @author foss-qiaolifeng
	 * @date 2013-7-26 下午5:17:17
	 * @param expressPartSalesDeptQueryDto
	 * @return
	 */
	List<ExpressSaleDepartmentResultDto> querySaleDepartmentResultDtoByCondition(
			ExpressPartSalesDeptQueryDto expressPartSalesDeptQueryDto);

	/**
	 * 根据快递点部编码、营业部编码查询有效的映射信息
	 * 
	 * @author foss-qiaolifeng
	 * @date 2013-7-29 下午2:08:47
	 * @param expressPartCode
	 * @param salesDeptCode
	 * @param active
	 * @return
	 */
	List<ExpressPartSalesDeptResultDto> queryResultDtoByExpressPartCodeAndSalesDeptCode(
			String expressPartCode, String salesDeptCode, String active);

	/**
	 * 新增快递点部营业部映射信息
	 * 
	 * @author foss-qiaolifeng
	 * @date 2013-7-29 下午2:23:20
	 * @param entity
	 */
	int addExpressPartSalesDept(ExpressPartSalesDeptEntity entity);

	
	/**
	 * 根据id查询快递点部营业部映射信息
	 * @author foss-qiaolifeng
	 * @date 2013-8-28 下午2:49:33
	 * @param ids
	 * @return
	 */
	List<ExpressPartSalesDeptEntity> queryInfosByIds(List<String> ids);
	
	/**
	 * 根据id删除快递点部营业部映射信息
	 * @author foss-qiaolifeng
	 * @date 2013-8-28 下午2:50:01
	 * @param ids
	 * @return
	 */
	int deleteInfosByIds(List<String> ids);
	
	/**
	 * 修改快递点部营业部映射信息
	 * 
	 * @author foss-qiaolifeng
	 * @date 2013-7-29 下午2:44:27
	 * @param entity
	 * @return
	 */
	int updateExpressPartSalesDeptByPartCode(ExpressPartSalesDeptEntity entity);
	
	/**
	 * 根据营业部编码\激活状态查询营业部快递点部映射关系
	 * @author foss-qiaolifeng
	 * @date 2013-8-29 上午9:41:05
	 * @param salesDeptCode
	 * @param active
	 * @return
	 */
	List<ExpressPartSalesDeptEntity> queryInfosBySalesCode(String salesDeptCode,String active);
	
}

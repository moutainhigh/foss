package com.deppon.foss.module.base.baseinfo.api.server.service;

import java.util.Date;
import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressPartSalesDeptEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.ExpressPartSalesDeptQueryDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.ExpressPartSalesDeptResultDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.ExpressSaleDepartmentResultDto;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;

/**
 * 快递点部营业部映射关系Service
 * 
 * @author foss-qiaolifeng
 * @date 2013-7-25 上午10:06:13
 */
public interface IExpressPartSalesDeptService extends IService {

	
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
	 * 根据营业部编码和创建时间查询快递点部信息
	 * 
	 * @author foss-zhangjiheng
	 * @date 2013-7-25 上午10:06:34
	 * @param expressPartSalesDeptQueryDto
	 * @return
	 */
	ExpressPartSalesDeptResultDto queryExpressPartSalesDeptBySalesCodeAndTime(
			String salesDeptCode, Date createTime);

	/**
	 * 根据快递点部编码查询快递点部营业部映射关系
	 * 
	 * @author foss-qiaolifeng
	 * @date 2013-7-26 上午11:01:30
	 * @param expressPartSalesDeptQueryDto
	 * @return
	 */
	List<ExpressPartSalesDeptResultDto> queryExpressPartSalesDeptByExpressPartCode(
			ExpressPartSalesDeptQueryDto expressPartSalesDeptQueryDto);

	/**
	 * 根据营业部编码列表查询营业部扩展dto
	 * 
	 * @author foss-qiaolifeng
	 * @date 2013-7-26 上午11:01:32
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
	 * 新增快递点部营业部映射信息
	 * 
	 * @author foss-qiaolifeng
	 * @date 2013-7-29 下午1:39:26
	 * @param expressPartSalesDeptQueryDto
	 */
	void updateExpressPartSalesDept(
			ExpressPartSalesDeptQueryDto expressPartSalesDeptQueryDto,
			CurrentInfo currentInfo);

	/**
	 * 修改快递点部营业部映射信息
	 * 
	 * @author foss-qiaolifeng
	 * @date 2013-7-29 下午1:39:26
	 * @param expressPartSalesDeptQueryDto
	 */
	void addExpressPartSalesDept(
			ExpressPartSalesDeptQueryDto expressPartSalesDeptQueryDto,
			CurrentInfo currentInfo);

	/**
	 * 删除快递点部营业部映射信息
	 * 
	 * @author foss-qiaolifeng
	 * @date 2013-7-29 下午1:39:53
	 * @param expressPartSalesDeptQueryDto
	 */
	void deleteExpressPartSalesDept(
			ExpressPartSalesDeptQueryDto expressPartSalesDeptQueryDto,
			CurrentInfo currentInfo);

	/**
	 * 根据快递点部编码获取快递大区信息
	 * 
	 * @author foss-qiaolifeng
	 * @date 2013-7-30 上午10:29:32
	 * @param expressPartSalesDeptQueryDto
	 * @return
	 */
	OrgAdministrativeInfoEntity getExpressPartBigRegionByPartCode(
			ExpressPartSalesDeptQueryDto expressPartSalesDeptQueryDto);
	
	/**
	 * 立即激活
	 * @author foss-qiaolifeng
	 * @date 2013-8-28 下午4:36:39
	 * @param expressPartSalesDeptQueryDto
	 * @param currentInfo
	 */
	void activeImmediatelyExpressPartSalesDept(
			ExpressPartSalesDeptQueryDto expressPartSalesDeptQueryDto,
			CurrentInfo currentInfo);

	/**
	 * 立即中止
	 * @author foss-qiaolifeng
	 * @date 2013-8-28 下午4:36:41
	 * @param expressPartSalesDeptQueryDto
	 * @param currentInfo
	 */
	void inActiveImmediatelyExpressPartSalesDept(
			ExpressPartSalesDeptQueryDto expressPartSalesDeptQueryDto,
			CurrentInfo currentInfo);
	
	/**
	 * 升级
	 * @author foss-qiaolifeng
	 * @date 2013-8-28 下午4:36:41
	 * @param expressPartSalesDeptQueryDto
	 * @param currentInfo
	 */
	void upgradeExpressPartSalesDept(
			ExpressPartSalesDeptQueryDto expressPartSalesDeptQueryDto,
			CurrentInfo currentInfo);
	/**
	 * 新增同步营业部点部数据
	 * @param expressPartSalesDeptEntity
	 */
	void sysAddExpressPartSalesDept(
			ExpressPartSalesDeptEntity expressPartSalesDeptEntity);
	/**
	 * 修改同步营业部点部数据
	 * @param expressPartSalesDeptEntity
	 */
	void sysUpdateExpressPartSalesDept(ExpressPartSalesDeptEntity expressPartSalesDeptEntity);
	/**
	 * 作废同步营业部点部数据
	 * @param expressPartSalesDeptEntity
	 */
	void sysDeleteExpressPartSalesDept(ExpressPartSalesDeptEntity expressPartSalesDeptEntity);
	
	
}

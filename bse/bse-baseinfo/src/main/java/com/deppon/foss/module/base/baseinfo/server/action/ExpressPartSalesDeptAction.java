package com.deppon.foss.module.base.baseinfo.server.action;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.server.service.IExpressPartSalesDeptService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.ExpressPartSalesDeptResultDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.ExpressSaleDepartmentResultDto;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.ExpressPartSalesDeptVo;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;

/**
 * 快递点部营业部映射关系action
 * @author foss-qiaolifeng
 * @date 2013-7-25 上午11:08:18
 */
public class ExpressPartSalesDeptAction extends AbstractAction {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -7073219920446793339L;

	/**
	 * 日志信息
	 */
	private static final Logger LOGGER = LoggerFactory
				.getLogger(ExpressPartSalesDeptAction.class);
	
	/**
	 * 快递点部营业部映射关系service
	 */
	private IExpressPartSalesDeptService expressPartSalesDeptService;

	/**
	 * 快递点部营业部映射关系Vo
	 */
	private ExpressPartSalesDeptVo expressPartSalesDeptVo;
	
	public ExpressPartSalesDeptVo getExpressPartSalesDeptVo() {
		return expressPartSalesDeptVo;
	}

	public void setExpressPartSalesDeptVo(
			ExpressPartSalesDeptVo expressPartSalesDeptVo) {
		this.expressPartSalesDeptVo = expressPartSalesDeptVo;
	}

	public void setExpressPartSalesDeptService(
			IExpressPartSalesDeptService expressPartSalesDeptService) {
		this.expressPartSalesDeptService = expressPartSalesDeptService;
	}
	
	/**
	 * 查询快递点部营业部映射关系信息
	 * @author foss-qiaolifeng
	 * @date 2013-7-25 上午11:14:07
	 * @return
	 */
	@JSON
	public String queryExpressPartSalesDept(){
		
		try{
			
			//验证参数
			if(expressPartSalesDeptVo==null
					||expressPartSalesDeptVo.getExpressPartSalesDeptQueryDto()==null){
				throw new BusinessException("查询快递点部营业部映射关系失败,快递点部名称参数异常");
			}
			
			//先查询总条数
			long total = expressPartSalesDeptService.queryTotalByCondition(expressPartSalesDeptVo.getExpressPartSalesDeptQueryDto());
			//如果总数大于0
			if(total>0){
				//查询快递点部营业部映射关系信息
				List<ExpressPartSalesDeptResultDto> returnDto = expressPartSalesDeptService.
						queryExpressPartSalesDeptByCondition(expressPartSalesDeptVo.getExpressPartSalesDeptQueryDto(), this.getStart(), this.getLimit());
				//设置返回值
				expressPartSalesDeptVo.setExpressPartSalesDeptResultDtoList(returnDto);
				this.setTotalCount(total);
			}
			
		}catch(BusinessException e){
			LOGGER.error(e.getMessage(), e);
			return returnError(e.getMessage(), e);
		}
		return returnSuccess();
	}
	
	/**
	 * 根据快递点部code获取快递点部配置的快递点部营业部映射信息
	 * @author foss-qiaolifeng
	 * @date 2013-7-26 上午10:28:43
	 * @return
	 */
	@JSON
	public String showDetialExpressPartSalesDept(){
		
		try{
			//验证参数
			if(expressPartSalesDeptVo==null
					||expressPartSalesDeptVo.getExpressPartSalesDeptQueryDto()==null
					||StringUtils.isEmpty(expressPartSalesDeptVo.getExpressPartSalesDeptQueryDto().getExpressPartCode())){
				throw new BusinessException("查看快递点部营业部映射信息失败,快递点部编码参数异常");
			}
			
			//根据快递点部编码查询快递点部营业部映射关系
			List<ExpressPartSalesDeptResultDto> expressPartSalesDeptResultDtoList = expressPartSalesDeptService.queryExpressPartSalesDeptByExpressPartCode(
					expressPartSalesDeptVo.getExpressPartSalesDeptQueryDto());
			if(CollectionUtils.isNotEmpty(expressPartSalesDeptResultDtoList)){
				//生成营业部编码集合
				List<String> salesDeptCodeList = new ArrayList<String>();
				for(ExpressPartSalesDeptResultDto dto:expressPartSalesDeptResultDtoList){
					//循环将营业部code加入到营业部code列表
					salesDeptCodeList.add(dto.getSalesDeptCode());
				}
				if(CollectionUtils.isEmpty(salesDeptCodeList)){
					throw new BusinessException("查看快递点部营业部映射信息失败,该快递点部没有配置的营业部信息");
				}
				//设置查询条件:营业部编码列表
				expressPartSalesDeptVo.getExpressPartSalesDeptQueryDto().setSelectedCodeList(salesDeptCodeList);
				//根据营业部编码列表查询营业部扩展dto
				List<ExpressSaleDepartmentResultDto> expressSaleDepartmentResultDtoList = expressPartSalesDeptService.querySaleDepartmentResultDtoBySalesDeptCodeList(
						expressPartSalesDeptVo.getExpressPartSalesDeptQueryDto());
				//设置返回值
				expressPartSalesDeptVo.setExpressPartSalesDeptResultDto(expressPartSalesDeptResultDtoList.get(0));
				expressPartSalesDeptVo.setSaleDepartmentResultDtoList(expressSaleDepartmentResultDtoList);
			}
			
			
		}catch(BusinessException e){
			LOGGER.error(e.getMessage(), e);
			return returnError(e.getMessage(), e);
		}
		return returnSuccess();
	}
	
	/**
	 * 查询营业部信息
	 * @author foss-qiaolifeng
	 * @date 2013-7-26 下午5:01:04
	 * @return
	 */
	@JSON
	public String querySalesDeptResultDtoList(){
		
		try{
			//验证参数
			if(expressPartSalesDeptVo==null
					||expressPartSalesDeptVo.getExpressPartSalesDeptQueryDto()==null){
				throw new BusinessException("查询营业部信息失败,参数异常");
			}
			
			//根据营业部条件查部营业部信息,多条件模糊查询
			List<ExpressSaleDepartmentResultDto> expressSaleDepartmentResultDtoList = expressPartSalesDeptService
					.querySaleDepartmentResultDtoByCondition(expressPartSalesDeptVo.getExpressPartSalesDeptQueryDto());
			
			//设置返回值
			expressPartSalesDeptVo.setSaleDepartmentResultDtoList(expressSaleDepartmentResultDtoList);
		}catch(BusinessException e){
			LOGGER.error(e.getMessage(), e);
			return returnError(e.getMessage(), e);
		}
		return returnSuccess();
	}
	
	/**
	 * 新增
	 * @author foss-qiaolifeng
	 * @date 2013-7-29 上午11:23:28
	 * @return
	 */
	@JSON
	public String addExpressPartSalesDept(){
		
		try{
			
			//验证参数
			if(expressPartSalesDeptVo==null
					||expressPartSalesDeptVo.getExpressPartSalesDeptQueryDto()==null){
				throw new BusinessException("新增快递点部营业部映射信息失败,参数异常");
			}
			
			// 获取当前登录用户信息
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			//新增
			expressPartSalesDeptService.addExpressPartSalesDept(expressPartSalesDeptVo.getExpressPartSalesDeptQueryDto(), currentInfo);
		}catch(BusinessException e){
			LOGGER.error(e.getMessage(), e);
			return returnError(e.getMessage(), e);
		}
		return returnSuccess();
	}
	
	/**
	 * 修改
	 * @author foss-qiaolifeng
	 * @date 2013-7-29 上午11:23:40
	 * @return
	 */
	@JSON
	public String updateExpressPartSalesDept(){
		
		try{
			
			//验证参数
			if(expressPartSalesDeptVo==null
					||expressPartSalesDeptVo.getExpressPartSalesDeptQueryDto()==null){
				throw new BusinessException("修改快递点部营业部映射信息失败,参数异常");
			}
			
			// 获取当前登录用户信息
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			//修改
			expressPartSalesDeptService.updateExpressPartSalesDept(expressPartSalesDeptVo.getExpressPartSalesDeptQueryDto(), currentInfo);
		}catch(BusinessException e){
			LOGGER.error(e.getMessage(), e);
			return returnError(e.getMessage(), e);
		}
		return returnSuccess();
	}
	
	/**
	 * 删除
	 * @author foss-qiaolifeng
	 * @date 2013-7-29 上午11:23:40
	 * @return
	 */
	@JSON
	public String deleteExpressPartSalesDept(){
		
		try{
			
			//验证参数
			if(expressPartSalesDeptVo==null
					||expressPartSalesDeptVo.getExpressPartSalesDeptQueryDto()==null){
				throw new BusinessException("作废快递点部营业部映射信息失败,参数异常");
			}
			
			// 获取当前登录用户信息
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			//删除
			expressPartSalesDeptService.deleteExpressPartSalesDept(expressPartSalesDeptVo.getExpressPartSalesDeptQueryDto(), currentInfo);
			
		}catch(BusinessException e){
			LOGGER.error(e.getMessage(), e);
			return returnError(e.getMessage(), e);
		}
		return returnSuccess();
	}
	
	/**
	 * 根据快递点部编码获取所属快递大区
	 * @author foss-qiaolifeng
	 * @date 2013-7-30 上午10:49:43
	 * @return
	 */
	@JSON
	public String getExpressPartBigRegionByPartCode(){
		
		try{
			//验证参数
			if(expressPartSalesDeptVo==null
					||expressPartSalesDeptVo.getExpressPartSalesDeptQueryDto()==null){
				return null;
			}
			
			//查询并设置返回值
			OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = expressPartSalesDeptService.getExpressPartBigRegionByPartCode(expressPartSalesDeptVo.getExpressPartSalesDeptQueryDto());
			expressPartSalesDeptVo.setOrgAdministrativeInfoEntity(orgAdministrativeInfoEntity);
			
		}catch(BusinessException e){
			LOGGER.error(e.getMessage(), e);
			return returnError(e.getMessage(), e);
		}
		return returnSuccess();
	}
	
	/**
	 * 立即激活
	 * @author foss-qiaolifeng
	 * @date 2013-8-28 下午4:18:48
	 * @return
	 */
	@JSON
	public String activeImmediatelyExpressPartSalesDept(){
	
		try{
			
			//验证参数
			if(expressPartSalesDeptVo==null
					||expressPartSalesDeptVo.getExpressPartSalesDeptQueryDto()==null){
				throw new BusinessException("激活快递点部营业部映射信息失败,参数异常");
			}
			
			// 获取当前登录用户信息
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			//激活
			expressPartSalesDeptService.activeImmediatelyExpressPartSalesDept(expressPartSalesDeptVo.getExpressPartSalesDeptQueryDto(), currentInfo);
			
		}catch(BusinessException e){
			LOGGER.error(e.getMessage(), e);
			return returnError(e.getMessage(), e);
		}
		return returnSuccess();
	}
	
	/**
	 * 立即中止
	 * @author foss-qiaolifeng
	 * @date 2013-8-28 下午4:19:02
	 * @return
	 */
	@JSON
	public String inActiveImmediatelyExpressPartSalesDept(){
	
		try{
			
			//验证参数
			if(expressPartSalesDeptVo==null
					||expressPartSalesDeptVo.getExpressPartSalesDeptQueryDto()==null){
				throw new BusinessException("中止快递点部营业部映射信息失败,参数异常");
			}
			
			// 获取当前登录用户信息
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			//中止
			expressPartSalesDeptService.inActiveImmediatelyExpressPartSalesDept(expressPartSalesDeptVo.getExpressPartSalesDeptQueryDto(), currentInfo);
			
		}catch(BusinessException e){
			LOGGER.error(e.getMessage(), e);
			return returnError(e.getMessage(), e);
		}
		return returnSuccess();
	}
	
	/**
	 * 升级
	 * @author foss-qiaolifeng
	 * @date 2013-9-2 下午2:18:17
	 * @return
	 */
	@JSON
	public String upgradeExpressPartSalesDept(){
		
		try{
			//验证参数
			if(expressPartSalesDeptVo==null
					||expressPartSalesDeptVo.getExpressPartSalesDeptQueryDto()==null){
				throw new BusinessException("升级快递点部营业部映射信息失败,参数异常");
			}
			
			// 获取当前登录用户信息
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			//升级
			expressPartSalesDeptService.upgradeExpressPartSalesDept(expressPartSalesDeptVo.getExpressPartSalesDeptQueryDto(), currentInfo);
			
		}catch(BusinessException e){
			LOGGER.error(e.getMessage(), e);
			return returnError(e.getMessage(), e);
		}
		return returnSuccess();
	}
	
}

package com.deppon.foss.module.base.baseinfo.server.action;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISalesExpenseMappingService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SalesExpenseMappingEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.SalesExpenseMappingVo;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
/**
 * 营业部与外请车费用承担部门映射信息
 * @author 307196
 *
 */
public class SalesExpenseMappingAction extends AbstractAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * 日志.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(SalesExpenseMappingAction.class);
    private ISalesExpenseMappingService salesExpenseMappingService;
    

	private SalesExpenseMappingVo salesExpenseMappingVo = new SalesExpenseMappingVo();


	/**
	 * 查询营业部与外请车费用承担部门映射
	 * @return
	 */
	@JSON
	public String querySalesExpenseMapping(){
		
		try{
			
			//验证参数
			if(salesExpenseMappingVo.getSalesExpenseMappingQueryDto()==null){
				throw new BusinessException("查询营业部与外请车费用承担部门映射失败,参数异常");
			}
			
			//先查询总条数
			long total = salesExpenseMappingService.queryTotalByCondition(salesExpenseMappingVo.getSalesExpenseMappingQueryDto());
			//如果总数大于0
			if(total>0){
				//查询营业部与外请车费用承担部门映射基础信息
				List<SalesExpenseMappingEntity> returnDto = salesExpenseMappingService.querySalesExpenseMappingByCondition(salesExpenseMappingVo.getSalesExpenseMappingQueryDto(), this.getStart(), this.getLimit());
				//设置返回值
				salesExpenseMappingVo.setSalesExpenseMappingEntityList(returnDto);
				this.setTotalCount(total);
			}
			
		}catch(BusinessException e){
			LOGGER.error(e.getMessage(), e);
			return returnError(e.getMessage(), e);
		}
		return returnSuccess();
	}
	
	/**
	 * 新增营业部与外请车费用承担部门映射
	 * @return
	 */
	@JSON
	public String addSalesExpenseMapping(){
		
		try{
			
			//验证参数
			if(CollectionUtils.isEmpty(salesExpenseMappingVo.getSalesExpenseMappingEntityList())){
				throw new BusinessException("新增营业部与外请车费用承担部门映射失败,参数异常");
			}
			
			// 获取当前登录用户信息
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			//新增
			salesExpenseMappingService.addSalesExpenseMapping(salesExpenseMappingVo.getSalesExpenseMappingEntityList(), currentInfo);
		}catch(BusinessException e){
			LOGGER.error(e.getMessage(), e);
			return returnError(e.getMessage(), e);
		}
		return returnSuccess();
	}
	
	/**
	 * 修改营业部与外请车费用承担部门映射
	 * @return
	 */
	@JSON
	public String updateSalesExpenseMapping(){
		
		try{
			
			//验证参数
			if(salesExpenseMappingVo.getSalesExpenseMappingQueryDto()==null){
				throw new BusinessException("修改营业部与外请车费用承担部门映射失败,参数异常");
			}
			
			// 获取当前登录用户信息
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			//修改
			salesExpenseMappingService.updateSalesExpenseMapping(salesExpenseMappingVo.getSalesExpenseMappingQueryDto(), currentInfo);
		}catch(BusinessException e){
			LOGGER.error(e.getMessage(), e);
			return returnError(e.getMessage(), e);
		}
		return returnSuccess();
	}
	
	/**
	 * 作废营业部与外请车费用承担部门映射
	 * @return
	 */
	@JSON
	public String deleteSalesExpenseMapping(){
		
		try{
			
			//验证参数
			if(salesExpenseMappingVo.getSalesExpenseMappingEntity()==null){
				throw new BusinessException("删除营业部与外请车费用承担部门映射失败,参数异常");
			}
			
			// 获取当前登录用户信息
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			//作废
			salesExpenseMappingService.deleteSalesExpenseMapping(salesExpenseMappingVo.getSalesExpenseMappingEntity(), currentInfo);
			
		}catch(BusinessException e){
			LOGGER.error(e.getMessage(), e);
			return returnError(e.getMessage(), e);
		}
		return returnSuccess();
	}

	public void setSalesExpenseMappingService(
			ISalesExpenseMappingService salesExpenseMappingService) {
		this.salesExpenseMappingService = salesExpenseMappingService;
	}

	public SalesExpenseMappingVo getSalesExpenseMappingVo() {
		return salesExpenseMappingVo;
	}

	public void setSalesExpenseMappingVo(SalesExpenseMappingVo salesExpenseMappingVo) {
		this.salesExpenseMappingVo = salesExpenseMappingVo;
	}
	

		
}

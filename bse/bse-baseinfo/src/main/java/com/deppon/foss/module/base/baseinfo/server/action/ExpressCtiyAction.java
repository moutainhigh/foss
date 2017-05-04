package com.deppon.foss.module.base.baseinfo.server.action;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.server.service.IExpressCityService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressCityEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.ExpressCityResultDto;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.ExpressCityVo;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;

/**
 * 试点城市action
 * @author foss-qiaolifeng
 * @date 2013-7-17 下午3:57:42
 */
public class ExpressCtiyAction extends AbstractAction {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 日志信息
	 */
	private static final Logger LOGGER = LoggerFactory
				.getLogger(ExpressCtiyAction.class);

	/**
	 * 试点城市service
	 */
	private IExpressCityService expressCityService;
	
	/**
	 * 试点城市Vo
	 */
	private ExpressCityVo expressCityVo;

	public void setExpressCityService(IExpressCityService expressCityService) {
		this.expressCityService = expressCityService;
	}
	
	public ExpressCityVo getExpressCityVo() {
		return expressCityVo;
	}

	public void setExpressCityVo(ExpressCityVo expressCityVo) {
		this.expressCityVo = expressCityVo;
	}

	/**
	 * 查询快递代理/试点城市信息
	 * @author foss-qiaolifeng
	 * @date 2013-7-18 上午9:34:52
	 * @return
	 */
	@JSON
	public String queryExpressCity(){
		
		try{
			//判断查询参数
			if(expressCityVo==null||expressCityVo.getExpressCityQueryDto()==null){
				throw new BusinessException("查询快递代理/试点城市失败，查询参数为空");
			}
			
			//查询总条数
			long countSum = expressCityService.queryExpressCityCountByCondition(expressCityVo.getExpressCityQueryDto());
			//如果条数大于0
			if(countSum>0){
				//查询快递代理/试点城市列表
				List<ExpressCityResultDto> expressCityResultDtoList = expressCityService.
						queryExpressCityListByCondition(expressCityVo.getExpressCityQueryDto(), this.getStart(), this.getLimit());
				//将查询结果设置到Vo里
				expressCityVo.setExpressCityResultDtoList(expressCityResultDtoList);
				this.setTotalCount(countSum);
			}
			
		}catch(BusinessException e){
			LOGGER.error(e.getMessage(), e);
			return returnError(e.getMessage(), e);
		}
		return returnSuccess();
	}
	
	/**
	 * 查看快递代理/试点城市
	 * @author foss-qiaolifeng
	 * @date 2013-7-18 下午4:07:21
	 * @return
	 */
	@JSON
	public String showExpressCity(){
		
		try{
			
			//判断查询参数
			if(expressCityVo==null||expressCityVo.getExpressCityQueryDto()==null){
				throw new BusinessException("查看快递代理/试点城市失败，查询参数为空");
			}
			
			//查询一条数据
			ExpressCityResultDto expressCityResultDto = expressCityService.queryOneExpressCityByCondition(expressCityVo.getExpressCityQueryDto());
			//设置返回数据
			expressCityVo.setExpressCityResultDto(expressCityResultDto);
			
			
		}catch(BusinessException e){
			LOGGER.error(e.getMessage(), e);
			return returnError(e.getMessage(), e);
		}
		return returnSuccess();
	}
	
	
	/**
	 * 新增快递代理/试点城市
	 * @author foss-qiaolifeng
	 * @date 2013-7-18 下午3:58:29
	 * @return
	 */
	@JSON
	public String addExpressCity(){
		
		try{
			//判断查询参数
			if(expressCityVo==null
					||CollectionUtils.isEmpty(expressCityVo.getExpressCityQueryDto().getNewExpressCityEntityList())){
				throw new BusinessException("新增快递代理/试点城市失败，参数异常");
			}
			
			//获取全部新增的数据
			List<ExpressCityEntity> entityList = expressCityVo.getExpressCityQueryDto().getNewExpressCityEntityList();
			
			// 获取当前登录用户信息
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			
			//批量新增
			expressCityService.addExpressCityList(entityList,currentInfo);
			
		}catch(BusinessException e){
			LOGGER.error(e.getMessage(), e);
			return returnError(e.getMessage(), e);
		}
		
		return returnSuccess();
	}
	
	
	/**
	 * 作废快递代理/试点城市
	 * @author foss-qiaolifeng
	 * @date 2013-7-22 上午9:56:28
	 * @return
	 */
	@JSON 
	public String disableExpressCity(){
		
		try{
			
			//判断查询参数
			if(expressCityVo==null
					||expressCityVo.getExpressCityQueryDto()==null
					||StringUtils.isEmpty(expressCityVo.getExpressCityQueryDto().getSelectedIds())){
				throw new BusinessException("作废快递代理/试点城市失败，参数异常");
			}
			
			// 获取当前登录用户信息
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			
			//作废
			expressCityService.disabledExpressCity(expressCityVo.getExpressCityQueryDto(), currentInfo);
			
			
		}catch(BusinessException e){
			LOGGER.error(e.getMessage(), e);
			return returnError(e.getMessage(), e);
		}
		
		return returnSuccess();
	}
	
	/**
	 * 修改快递代理/试点城市
	 * @author foss-qiaolifeng
	 * @date 2013-7-22 下午2:28:26
	 * @return
	 */
	@JSON 
	public String updateExpressCity(){
		
		try{
			//判断查询参数
			if(expressCityVo==null
					||expressCityVo.getExpressCityQueryDto()==null
					||expressCityVo.getExpressCityQueryDto().getExpressCityEntity()==null
					||CollectionUtils.isEmpty(expressCityVo.getExpressCityQueryDto().getNewExpressCityEntityList())){
				throw new BusinessException("作废快递代理/试点城市失败，参数异常");
			}
			
			
			// 获取当前登录用户信息
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
						
			//修改
			expressCityService.updateExpressCity(expressCityVo.getExpressCityQueryDto().getExpressCityEntity(),
					expressCityVo.getExpressCityQueryDto().getNewExpressCityEntityList(), currentInfo);
			
		}catch(BusinessException e){
			LOGGER.error(e.getMessage(), e);
			return returnError(e.getMessage(), e);
		}
		
		return returnSuccess();
	}
}

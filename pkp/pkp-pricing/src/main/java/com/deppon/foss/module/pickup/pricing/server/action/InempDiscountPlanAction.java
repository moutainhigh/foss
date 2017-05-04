package com.deppon.foss.module.pickup.pricing.server.action;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.pickup.pricing.api.server.service.IInempDiscountPlanService;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.InempDiscountPlanEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.exception.MessageType;
import com.deppon.foss.module.pickup.pricing.api.shared.vo.InempDiscountPlanVo;


public class InempDiscountPlanAction extends AbstractAction {

	/**
	 *序列化ID
	 */
	private static final long serialVersionUID = 1L;
    /**
     * 日志处理.
     */
    private static final Logger LOGGER = LoggerFactory
	    .getLogger(InempDiscountPlanAction.class);
    
	/**
	 * 内部员工折扣方案service
	 */
	private IInempDiscountPlanService inempDiscountPlanService;
	/**
	 * 数据交互vo
	 */
	private InempDiscountPlanVo  inempDiscountPlanVo = new InempDiscountPlanVo();
	
    
    
	public InempDiscountPlanVo getInempDiscountPlanVo() {
		return inempDiscountPlanVo;
	}

	public void setInempDiscountPlanVo(InempDiscountPlanVo inempDiscountPlanVo) {
		this.inempDiscountPlanVo = inempDiscountPlanVo;
	}

	public void setInempDiscountPlanService(
			IInempDiscountPlanService inempDiscountPlanService) {
		this.inempDiscountPlanService = inempDiscountPlanService;
	}
	/**
	 * 新增内部员工折扣方案
	 * dp-foss-dongjialing
	 * 225131
	 * @return
	 */
	@JSON
	public String addInempDiscountPlan(){
		try{
			inempDiscountPlanService.insertSelective(inempDiscountPlanVo.getInempDiscountPlanEntity());
			return returnSuccess(MessageType.SAVE_SUCCESS);
		} catch (BusinessException e) {
		    LOGGER.error("新增快递折扣方案信息出现异常: " + e.getMessage());
		    return returnError(e);
		}
	}
	/**
	 * 修改内部员工折扣方案
	 * dp-foss-dongjialing
	 * 225131
	 * @return
	 */
	@JSON
    public String updateInempDiscountPlan(){
    	try{
    		inempDiscountPlanService.updateByIdSelective(inempDiscountPlanVo.getInempDiscountPlanEntity());
    		return returnSuccess(MessageType.UPDATE_SUCCESS);
    	}catch(BusinessException e){
		    LOGGER.error("修改快递折扣方案信息出现异常: " + e.getMessage());
  		    return returnError(e);
    	}
    }
	/**
	 * 根据内部员工折扣方案Id删除方案
	 * dp-foss-dongjialing
	 * 225131
	 * @return
	 */
    @JSON
    public String deleteInempDsicountPlan(){
    	try{
    		inempDiscountPlanService.deleteByIds(inempDiscountPlanVo.getInempDiscountPlanIds());
    		return returnSuccess(MessageType.DELETE_SUCCESS);
    	}catch(BusinessException e){
    		LOGGER.error("删除快递折扣方案信息出现异常: " + e.getMessage());
  		    return returnError(e);
    	}
    	
    }
    /**
     * 根据查询条件分页查询内部员工折扣方案集合
     * dp-foss-dongjialing
     * 225131
     * @return
     */
    @JSON
    public String queryInempDiscountPlanList(){
    	try{
    		List<InempDiscountPlanEntity>  discountEntityList =inempDiscountPlanService.queryInempDiscountPlanList(inempDiscountPlanVo.getInempDiscountPlanEntity(), getStart(), getLimit());
    		inempDiscountPlanVo.setInempDiscountPlanEntityList(discountEntityList);
    		Long totalCount = inempDiscountPlanService.queryInempDiscountPlanListCount(inempDiscountPlanVo.getInempDiscountPlanEntity());
    		this.setTotalCount(totalCount);
    		return returnSuccess(MessageType.SAVE_SUCCESS);
    	}catch(BusinessException e){
    		LOGGER.error("查询快递折扣方案信息出现异常: " + e.getMessage());
  		    return returnError(e);
    	}
    }
    /**
     * 根据Id查询员工内部折扣方案
     * dp-foss-dongjialing
     * 225131
     * @return
     */
    @JSON
    public String queryInempDiscountPlanById(){
    	try{
    		InempDiscountPlanEntity  discountEntity =inempDiscountPlanService.queryInempDiscountPlanById(inempDiscountPlanVo.getInempDiscountPlanEntity());
    		inempDiscountPlanVo.setInempDiscountPlanEntity(discountEntity);
    			
    		return returnSuccess(MessageType.SAVE_SUCCESS);
    	}catch(BusinessException e){
    		LOGGER.error("查询快递折扣方案信息出现异常: " + e.getMessage());
  		    return returnError(e);
    	}
		
    }
    /**
     * 激活员工内部折扣方案
     * dp-foss-dongjialing
     * 225131
     * @return
     */
    @JSON
    public String activeInempDiscountPlan(){
    	try{
    		Date nowDate= new Date();
		    if(null!=inempDiscountPlanVo.getInempDiscountPlanEntity().getBeginTime() && nowDate.compareTo(inempDiscountPlanVo.getInempDiscountPlanEntity().getBeginTime())>0)
		    {
		    	return returnError(MessageType.SHOW_FAILURE_MESSAGE);
		    }
		    inempDiscountPlanService.activeInempDiscountPlan(inempDiscountPlanVo.getInempDiscountPlanEntity());
    		return returnSuccess(MessageType.ACTIVE_PRICEDISCOUNT_SUCCESS);
    	}catch(BusinessException e){
    		LOGGER.error("激活快递折扣方案出现异常: " + e.getMessage());
  		    return returnError(e);
    	}
    }
    /**
     * 中止员工内部折扣方案
     * dp-foss-dongjialing
     * 225131
     * @return
     */
    @JSON
    public String stopInempDiscountPlan(){
    	try{
    		inempDiscountPlanService.stopInempDiscountPlan(inempDiscountPlanVo.getInempDiscountPlanEntity());
    		return returnSuccess(MessageType.STOP_SUCCESS);
    	}catch(BusinessException e){
    		LOGGER.error("中止快递折扣方案出现异常: " + e.getMessage());
  		    return returnError(e);
    	}
    }
    
    /**
     * 升级内部员工折扣方案
     * dp-foss-dongjialing
     * 225131
     * @return
     */
    public String  upgradeInempDiscountPlan(){
    	try{
    		inempDiscountPlanService.upgradeInempDiscountPlan(inempDiscountPlanVo.getInempDiscountPlanEntity());
    		return returnSuccess(MessageType.COPY_PRICEDISCOUNT_SUCCESS);
    	}catch(BusinessException e){
    		LOGGER.error("在升级折扣方案时出现异常："+e.getMessage());
    		return returnError(e);
    	}
    }
       
}

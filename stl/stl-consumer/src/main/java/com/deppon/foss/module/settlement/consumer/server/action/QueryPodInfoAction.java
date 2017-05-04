package com.deppon.foss.module.settlement.consumer.server.action;

import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.consumer.api.server.service.IQueryPodInfoService;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.PodDto;
import com.deppon.foss.module.settlement.consumer.api.shared.vo.PodInfoVo;
import com.deppon.foss.util.DateUtils;

/**
 * 签收类型查询的action
 * @author 198704 weitao
 * @date 2014-8-23
 */
public class QueryPodInfoAction extends AbstractAction{
	/**
	 * 日志
	 */
	private static final Logger logger = LogManager
			.getLogger(QueryPodInfoAction.class);

	/**
	 * 生成序列号
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * podVo
	 */
	private PodInfoVo vo;
	
	/**
	 *  定义一个service变量
	 */
	private IQueryPodInfoService queryPodInfoService;
	

	/**
	 * 查询签收记录信息
	 * @return String
	 */
    @JSON
	public String queryPodInfo(){
    	//日志调试
    	logger.info("查询签收记录开始...");   	
    	try{
    		//验证前台传来的dto是否为空   
    		if(null==vo.getDto()){
        		//为空则抛出异常
        		throw new SettlementException("DTO不能为空");
        	}
    		else{
    			if(vo.getDto().getQueryType().equals(SettlementConstants.TAB_QUERY_BY_DATE)){
        			vo.getDto().setPodEndDate(DateUtils.convert(DateUtils.addDay(vo.getDto().getPodEndDate(), 1)));
    			}
    			//查询出数据的总条数
    			int count = queryPodInfoService.queryPodInfoCount(vo.getDto());
    			if(count>0){
    				 //分页查询签收记录的list集合
	       			 List<PodDto> list = queryPodInfoService.queryPodInfo(vo.getDto(), start, limit);
	       			 //设置dtoList的值
	       			 vo.setDtoList(list);  
	       			 //显示总条数
	       			 vo.getDto().setTotalCount(count);
	       			 // 设置查询出数据的总条数
	    			 this.setTotalCount((long) queryPodInfoService.queryPodInfoCount(vo.getDto()));
    			}
    		}
        	return returnSuccess();
    	}catch(BusinessException e){
    		//日志记录错误信息
    		logger.error("查询签收记录错误!"+e.getMessage(),e);
    		return returnError(e);
    	}
    }
	
	/**
	 * @return podVo
	 */
    public PodInfoVo getVo() {
		return vo;
	}

    /**
     * @param vo
     */
	public void setVo(PodInfoVo vo) {
		this.vo = vo;
	}

	/**
	 * @param queryPodInfoService
	 */
	public void setQueryPodInfoService(IQueryPodInfoService queryPodInfoService) {
		this.queryPodInfoService = queryPodInfoService;
	}

}

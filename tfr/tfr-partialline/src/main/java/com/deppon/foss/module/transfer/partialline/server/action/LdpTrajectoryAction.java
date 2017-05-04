package com.deppon.foss.module.transfer.partialline.server.action;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.module.transfer.partialline.api.server.service.ILdpTrajectoryService;
import com.deppon.foss.module.transfer.partialline.api.shared.domain.ExpressOpreateRecordEntity;
import com.deppon.foss.module.transfer.partialline.api.shared.domain.QueryLdpTrajectoryEntity;
import com.deppon.foss.module.transfer.partialline.api.shared.exception.ExternalBillException;
import com.deppon.foss.module.transfer.partialline.api.shared.vo.QueryLdpTrajectoryVo;

public class LdpTrajectoryAction extends AbstractAction {

	/**
	 * 
	 * @author hwy 218427
	 * @date 2015年3月6日 上午10:02:31
	 */
	private static final long serialVersionUID = 1L;
	
	private static final Logger LOGGER=LoggerFactory.getLogger(LdpTrajectoryAction.class);

    private ILdpTrajectoryService ldpTrajectoryService;
	
	private QueryLdpTrajectoryVo queryLdpTrajectoryVo;
	
	public String queryLdpTrajectory(){
		try {
			LOGGER.info("查询录入外发快递轨迹");
			List<QueryLdpTrajectoryEntity> list=ldpTrajectoryService.queryLdpTrajectory(queryLdpTrajectoryVo, this.getStart(), this.getLimit());
			queryLdpTrajectoryVo.setLdpTrajectoryEntitylist(list);
			Long totalCount = ldpTrajectoryService.queryLdpTrajectoryCount(queryLdpTrajectoryVo);
			this.setTotalCount(totalCount);
		    return returnSuccess();
		} catch (ExternalBillException e) {
			return returnError(e);
		}
		
	}
	
	public String insertLdpTrajectory(){
		try {
			LOGGER.info("录入外发快递轨迹");
			ldpTrajectoryService.insertLdpTrajectory(queryLdpTrajectoryVo);
		    return returnSuccess();
		} catch (ExternalBillException e) {
			return returnError(e);
		}catch(Exception e){
			return returnError(e.toString());
		}
		
	}
	
	public String queryOperationRecords(){
		try {
			LOGGER.info("查询操作记录");
			if(queryLdpTrajectoryVo==null){
				throw new ExternalBillException("参数为空");
			}			
			String wayBillNo= queryLdpTrajectoryVo.getWayBillNo();
			 List<ExpressOpreateRecordEntity> expressOpreateRecordEntityList=ldpTrajectoryService.queryOperationRecords(wayBillNo, start, limit);
			queryLdpTrajectoryVo.setExpressOpreateRecordEntityList(expressOpreateRecordEntityList);
			Long totalCount = ldpTrajectoryService.queryOperationRecordsCount(wayBillNo);
			this.setTotalCount(totalCount);
			return returnSuccess();
		} catch (ExternalBillException e) {
			return returnError(e);
		}
	}
	
	//删除操作记录
	public String deleteValue() {
		try {
			LOGGER.info("删除操作记录");
			if(queryLdpTrajectoryVo==null){
				throw new ExternalBillException("参数为空");
			}
			if(queryLdpTrajectoryVo.getExpressOpreateRecordEntity() != null) {
				ldpTrajectoryService.deleteValue(queryLdpTrajectoryVo.getExpressOpreateRecordEntity().getId());
			}
			return returnSuccess("删除操作记录成功");
		} catch (ExternalBillException e) {
			return returnError(e);
		}
	}

	public QueryLdpTrajectoryVo getQueryLdpTrajectoryVo() {
		return queryLdpTrajectoryVo;
	}

	public void setQueryLdpTrajectoryVo(QueryLdpTrajectoryVo queryLdpTrajectoryVo) {
		this.queryLdpTrajectoryVo = queryLdpTrajectoryVo;
	}

	public void setLdpTrajectoryService(ILdpTrajectoryService ldpTrajectoryService) {
		this.ldpTrajectoryService = ldpTrajectoryService;
	}
	
	
}

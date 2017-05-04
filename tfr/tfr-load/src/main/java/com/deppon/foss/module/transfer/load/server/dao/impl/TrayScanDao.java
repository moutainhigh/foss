package com.deppon.foss.module.transfer.load.server.dao.impl;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.transfer.common.api.server.service.IFOSSToWkService;
import com.deppon.foss.module.transfer.common.api.shared.domain.RequestParameterEntity;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.load.api.server.dao.ITrayScanDao;
import com.deppon.foss.module.transfer.load.api.shared.domain.TrayScanDetaiEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.TrayScanExpressEntity;
import com.deppon.foss.module.transfer.load.api.shared.dto.QueryTrayScanConditionDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.TrayScanDto;



/** 
 * @className: TrayScanDao
 * @author: 105869-foss-heyongdong
 * @description: 托盘扫描任务dao实现类
 * @date: 2013-8-1 9:54:43
 * 
 */
public class TrayScanDao extends iBatis3DaoImpl implements ITrayScanDao{
	
	/**命名空间常量*/
	private static final String NAMESPACE = "foss.load.trayscan.";
	
	/**记录日志*/
	protected final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	private IFOSSToWkService fossToWkService;
	

	public void setFossToWkService(IFOSSToWkService fossToWkService) {
		this.fossToWkService = fossToWkService;
	}

	/**
	 * 获取托盘扫描任务  零担
	 * @author 105869-foss-heyongdong
	 * @date 2013-8-1 9:49:12
	 * @param queryTrayScanConditiondto查询条件dto
	 * @param limit
	 * @param start
	 * @return List<TrayScanDto>
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.ITrayScanDao#queryTrayScanList(com.deppon.foss.module.transfer.load.api.shared.dto.QueryTrayScanConditionDto)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<TrayScanDto> queryTrayScanList(
			QueryTrayScanConditionDto queryTrayScanConditiondto, int limit,
			int start) {
		//分页
		RowBounds rowBounds = new RowBounds(start, limit);
		//返回查询结果
		return this.getSqlSession().selectList(NAMESPACE + "queryTrayScanList",queryTrayScanConditiondto,rowBounds);
		
	}
	
	/**
	 * 获取托盘扫描任务  快递
	 * @author 105869-foss-heyongdong
	 * @date 2013-8-1 9:49:12
	 * @param queryTrayScanConditiondto查询条件dto
	 * @param limit
	 * @param start
	 * @return List<TrayScanDto>
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.ITrayScanDao#queryTrayScanList(com.deppon.foss.module.transfer.load.api.shared.dto.QueryTrayScanConditionDto)
	 */
	@Override
	public String queryTrayScanListExpress(
			QueryTrayScanConditionDto queryTrayScanConditiondto, int limit,
			int start) {
		try {
			//设置分页
			queryTrayScanConditiondto.setCurrentPageNo(start);
			queryTrayScanConditiondto.setPageSize(limit);
			RequestParameterEntity requestParameter=new RequestParameterEntity();
			requestParameter.setRequestEntity(queryTrayScanConditiondto);
			String result=fossToWkService.queryTrayScanListExpress(requestParameter);
			//String result="{\"data\":[{\"bindingDeptName\": \"www.deppon.com\",\"bingingName\": \"罗盼\",\"taskBindTime\": \"2016-05-12 12:00:00\",\"taskBinderDeptName\": null,\"taskBinderDeptNo\": null,\"bindingDept\": \"上海德邦\",\"optionOrgCode\": null,\"outfiledCode\": null,\"currentPageNo\": 0,\"trayBindingTaskNo\": \"2016001\",\"forkliftDriverName\": \"张小飞\",\"forkliftScanTime\": \"2016-05-15 12:00:00\",\"forkliftTicketsCount\": 200,\"forkLiftDept\": \"110110\",\"bindingCode\": \"1150\",\"trayScanStatus\": \"SCANNED\",\"beginTrayScanTime\": null,\"endTrayScanTime\": null,\"forkliftDriverDeptName\": \"FBI\",\"pageSize\": 0,\"billNo\": null,\"forkLiftDriverCode\": null,\"id\": 1007,\"trayBindingTaskDetailsOrgEntity\": null,\"unloadTaskNo\": null,\"forkliftDriverNo\": \"110\",\"outFieldCode\": null,\"manageStatus\": null,\"forkliftDriverDeptNo\": null,\"statisticStatus\": null,\"operatorDeptNo\": null,\"operatorDeptName\": null,\"operationOrgCode\": null,\"operationTime\": null,\"operatorName\": null},{\"bindingDeptName\": \"www.deppon.com\",\"bingingName\": \"罗盼\",\"taskBindTime\": \"2016-05-12 12:00:00\",\"taskBinderDeptName\": null,\"taskBinderDeptNo\": null,\"bindingDept\": \"上海德邦\",\"optionOrgCode\": null,\"outfiledCode\": null,\"currentPageNo\": 0,\"trayBindingTaskNo\": \"2016002\",\"forkliftDriverName\": \"张小飞\",\"forkliftScanTime\": \"2016-05-15 12:00:00\",\"forkliftTicketsCount\": 200,\"forkLiftDept\": \"110110\",\"bindingCode\": \"1150\",\"trayScanStatus\": \"SCANNED\",\"beginTrayScanTime\": null,\"endTrayScanTime\": null,\"forkliftDriverDeptName\": \"FBI\",\"pageSize\": 0,\"billNo\": null,\"forkLiftDriverCode\": null,\"id\": 1008,\"trayBindingTaskDetailsOrgEntity\": null,\"unloadTaskNo\": null,\"forkliftDriverNo\": \"110\",\"outFieldCode\": null,\"manageStatus\": null,\"forkliftDriverDeptNo\": null,\"statisticStatus\": null,\"operatorDeptNo\": null,\"operatorDeptName\": null,\"operationOrgCode\": null,\"operationTime\": null,\"operatorName\": null}],\"totalRows\":1}";
			return result;
		} catch (Exception e) {
			logger.error("查询数据异常");
			throw new TfrBusinessException("查询数据异常！"); 
		}
		
	}
	
	/**
	 * 获取托盘扫描任务的总条数    零担
	 * @author 105869-foss-heyongdong
	 * @date 2013-8-1 9:49:40
	 * @param queryTrayScanConditiondto查询条件dto
	 * @return Long
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.ITrayScanDao#queryTrayScanList(com.deppon.foss.module.transfer.load.api.shared.dto.QueryTrayScanConditionDto)
	 */
	@Override
	public Long getTrayScanListCount(
			QueryTrayScanConditionDto queryTrayScanConditiondto) {
		//返回查询结果
		return (Long)this.getSqlSession().selectOne(NAMESPACE + "getTrayScanListCount",queryTrayScanConditiondto);
		
	}
	
	/**
	 * 获取托盘扫描任务的总条数   快递
	 * @author 105869-foss-heyongdong
	 * @date 2013-8-1 9:49:40
	 * @param queryTrayScanConditiondto查询条件dto
	 * @return Long
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.ITrayScanDao#queryTrayScanList(com.deppon.foss.module.transfer.load.api.shared.dto.QueryTrayScanConditionDto)
	 */
	@Override
	public Long getTrayScanListCountExpress(
			QueryTrayScanConditionDto queryTrayScanConditiondto) {
		//返回查询结果
		return (Long)this.getSqlSession().selectOne(NAMESPACE + "getTrayScanListCountExpress",queryTrayScanConditiondto);
		
	}
	
	/**  零担
	 * 获取托盘扫描任务的总条数
	 * @author 105869-foss-heyongdong
	 * @date 2013-8-1 9:49:40
	 * @param queryTrayScanConditiondto查询条件dto
	 * @return List<TrayScanDetaiEntity>
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.ITrayScanDao#queryWaybillByTaskNo(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<TrayScanDetaiEntity> queryWaybillByTaskNo(String traytaskCode) {
		return this.getSqlSession().selectList(NAMESPACE+"queryWaybillByTaskNo",traytaskCode);
	}
	
	/**  快递
	 * 获取托盘扫描任务的总条数
	 * @author 105869-foss-heyongdong
	 * @date 2013-8-1 9:49:40
	 * @param queryTrayScanConditiondto查询条件dto
	 * @return List<TrayScanExpressEntity>
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.ITrayScanDao#queryWaybillByTaskNo(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String queryWaybillByTaskNoExpress(String traytaskCode) {
		
		try {
			HashMap<String,String> map=new HashMap<String,String>();
			map.put("traytaskCode", traytaskCode);
			map.put("optionOrgCode", FossUserContext.getCurrentDeptCode());
			RequestParameterEntity result=new RequestParameterEntity();
			result.setRequestEntity(map);
			String resultString=fossToWkService.queryWaybillByTaskNoExpress(result);
			return resultString;
		} catch (Exception e) {
			logger.error("查询数据出错");
			throw new TfrBusinessException("查询数据异常！"); 
		}
		//return this.getSqlSession().selectList(NAMESPACE+"queryWaybillByTaskNoExpress",map);
	}
	
	/**
	 * 零担
	 * 导出托盘扫描任务不用分页查询
	 * @author 105869-foss-heyongdong
	 * @date 2013-8-1 9:49:12
	 * @param queryTrayScanConditiondto查询条件dto
	 * @return List<TrayScanDto>
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.ITrayScanDao#queryTrayScanListNoPage(com.deppon.foss.module.transfer.load.api.shared.dto.QueryTrayScanConditionDto)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<TrayScanDto> queryTrayScanListNoPage(
			QueryTrayScanConditionDto queryTrayScanConditiondto) {
		
		//返回查询结果
		return this.getSqlSession().selectList(NAMESPACE + "queryTrayScanList",queryTrayScanConditiondto);
	}
	
	/**
	 *  快递
	 * 导出托盘扫描任务不用分页查询
	 * @author 105869-foss-heyongdong
	 * @date 2013-8-1 9:49:12
	 * @param queryTrayScanConditiondto查询条件dto
	 * @return List<TrayScanDto>
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.ITrayScanDao#queryTrayScanListNoPage(com.deppon.foss.module.transfer.load.api.shared.dto.QueryTrayScanConditionDto)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<TrayScanExpressEntity> queryTrayScanListNoPageExpress(
			QueryTrayScanConditionDto queryTrayScanConditiondto) {
		
		//返回查询结果
		return this.getSqlSession().selectList(NAMESPACE + "queryTrayScanListExpress",queryTrayScanConditiondto);
	}
	
	/**  零担
	 * 导出托盘扫描任务不用分页查询
	 * @author 105869-foss-heyongdong
	 * @date 2013-8-1 9:49:12
	 * @param queryTrayScanConditiondto查询条件dto
	 * @return List<TrayScanDto>
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.ITrayScanDao#queryTrayScanListNoPage(com.deppon.foss.module.transfer.load.api.shared.dto.QueryTrayScanConditionDto)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<TrayScanDto> queryTrayScanSummary(
			QueryTrayScanConditionDto queryTrayScanConditiondto) {
		//返回叉车总票数
		return this.getSqlSession().selectList(NAMESPACE+"queryTrayScanSummary",queryTrayScanConditiondto);
	}
	
	/**  快递
	 * 导出托盘扫描任务不用分页查询
	 * @author 105869-foss-heyongdong
	 * @date 2013-8-1 9:49:12
	 * @param queryTrayScanConditiondto查询条件dto
	 * @return List<TrayScanDto>
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.ITrayScanDao#queryTrayScanListNoPage(com.deppon.foss.module.transfer.load.api.shared.dto.QueryTrayScanConditionDto)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<TrayScanDto> queryTrayScanSummaryExpress(
			QueryTrayScanConditionDto queryTrayScanConditiondto) {
		//返回叉车总票数
		return this.getSqlSession().selectList(NAMESPACE+"queryTrayScanSummaryExpress",queryTrayScanConditiondto);
	}
	
	/**
	 *提供装车接口，按运单，流水，外场编码，判断是否已绑定托盘兵扫描
	 *@author 045923 205109-foss-zenghaibin
	 *@date 2014/11/29 15:55
	 *@param wayBillNo seriano outFieldCode
	 ***/
	@Override
	public Long queryTrayScanTaskDtailCount(HashMap map){
		
		return (Long)this.getSqlSession().selectOne(NAMESPACE+"queryTrayScanTaskDtailCount",map);
	}

	/**
	 *提供装车接口，按运单，流水，外场编码，判断是否已绑定托盘兵扫描
	 *@author 205109-foss-zenghaibin
	 *@date 2014/12/08 15:55
	 *@param wayBillNo seriano outFieldCode
	 ***/
	@Override
	public Long queryOfflienTrayScanTaskDtailCount(HashMap map){
		
		return (Long)this.getSqlSession().selectOne(NAMESPACE+"queryOfflienTrayScanTaskDtailCount",map);
	}
}

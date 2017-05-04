package com.deppon.foss.module.base.baseinfo.server.service.impl.esb;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.jws.WebService;
import javax.xml.ws.Holder;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.esb.header.ESBHeader;
import com.deppon.esb.inteface.domain.foss.ExpressTrainLineScheduleRequest;
import com.deppon.esb.inteface.domain.foss.ExpressTrainLineScheduleResponse;
import com.deppon.esb.inteface.domain.foss.LineScheduleInfo;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.esb.util.ESBHeaderUtil;
import com.deppon.foss.inteface.expresstrainlinescheduleservice.CommonException;
import com.deppon.foss.inteface.expresstrainlinescheduleservice.IExpressTrainLineScheduleService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IExpressLineScheduleService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IExpressTrainProgramService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressLineScheduleEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressTrainProgramEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.ExpressTrainScheduleException;
import com.deppon.foss.module.base.common.api.server.service.IBusinessLockService;
import com.deppon.foss.module.base.common.api.shared.define.MutexElementType;
import com.deppon.foss.module.base.common.api.shared.dto.MutexElement;
/**
 * 
 *<p>Title: SyncExpressLineScheduleService</p>
 * <p>Description:同步GIS 快递班车线路信息给FOSS，webService 服务端 </p>
 * <p>Company: Deppon</p>
 * @author    130566-ZengJunfan
 * @date       2014-5-14
 */
@WebService
public class SyncExpressLineScheduleService implements
		IExpressTrainLineScheduleService {
	/**
	 * 日志
	 */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(SyncExpressLineScheduleService.class);
	// 用于set给创建人，修改人的值常量
	private static final String GIS = "GIS";
	/**
	 * 业务互斥锁服务.
	 */
	private IBusinessLockService businessLockService;
	/**
	 * 快递班车方案
	 */
	private IExpressTrainProgramService expressTrainProgramService;
	
	/**
	 * 快递线路时刻表
	 */
	private IExpressLineScheduleService  expressLineScheduleService;
	
	
	public void setExpressLineScheduleService(
			IExpressLineScheduleService expressLineScheduleService) {
		this.expressLineScheduleService = expressLineScheduleService;
	}

	public void setExpressTrainProgramService(
			IExpressTrainProgramService expressTrainProgramService) {
		this.expressTrainProgramService = expressTrainProgramService;
	}
	
	public void setBusinessLockService(IBusinessLockService businessLockService) {
		this.businessLockService = businessLockService;
	}
	/**
	 *<p>Title: expressTrainLineSchedule</p>
	 *<p>同步信息的主方法</p>
	 *@author 130566-ZengJunfan
	 *@date 2014-5-14下午3:06:38
	 * @param esbHeader
	 * @param expressTrainLineScheduleRequest
	 * @return
	 * @throws CommonException
	 */
	@Override
	public ExpressTrainLineScheduleResponse expressTrainLineSchedule(
			Holder<ESBHeader> esbHeader,
			ExpressTrainLineScheduleRequest expressTrainLineScheduleRequest)
			throws CommonException {
		// 消息头
		ESBHeaderUtil.processEsbHeaderForResponse(esbHeader);
		// 响应对象
		ExpressTrainLineScheduleResponse response = new ExpressTrainLineScheduleResponse();
		//非空校验
		if(null ==expressTrainLineScheduleRequest ||StringUtils.isBlank(expressTrainLineScheduleRequest.getProgramName())){
			LOGGER.info("请求对象不存在或请求的programName 为空！.......");
			response.setResultReason("请求对象不存在或请求的programName 为空！.......");
			response.setResultCode("0");
			return response;
		}else{
			// 业务锁
			MutexElement mutex = new MutexElement(String.valueOf(expressTrainLineScheduleRequest.getProgramName()), "GIS_PROGRAM_NAME",
								MutexElementType.GIS_PROGRAM_NAME);
			LOGGER.info("开始加锁：" + mutex.getBusinessNo());
			boolean result = businessLockService.lock(mutex,
					NumberConstants.ZERO);
			if (result) {
				LOGGER.info("成功加锁：" + mutex.getBusinessNo());
			} else {
				LOGGER.info("失败加锁：" + mutex.getBusinessNo());
				response.setProgramName(expressTrainLineScheduleRequest.getProgramName());
				response.setResultReason("失败加锁");
				response.setResultCode("0");
				return response;
			}
			//处理GIS 同步的快递班车线路的信息
			response =this.syncLineScheduleInfos(expressTrainLineScheduleRequest);
			LOGGER.info("开始解锁：" + mutex.getBusinessNo());
			// 解业务锁
			businessLockService.unlock(mutex);
			LOGGER.info("完成解锁：" + mutex.getBusinessNo());
		}
		return response;
	}
	/**
	 * 
	 *<p>Title: syncLineScheduleInfos</p>
	 *<p>处理GIS同步快递班车线路时刻表信息</p>
	 *@author 130566-ZengJunfan
	 *@date 2014-5-14下午3:20:51
	 * @param expressTrainLineScheduleRequest
	 * @return
	 * @throws CommonException 
	 */
	private ExpressTrainLineScheduleResponse syncLineScheduleInfos(
			ExpressTrainLineScheduleRequest syncRequest) throws CommonException {
		LOGGER.info("同步GIS的数据信息开始...............");
		//响应对象
		ExpressTrainLineScheduleResponse response =new ExpressTrainLineScheduleResponse();
		if(null !=syncRequest ){
			//获取方案信息
			ExpressTrainProgramEntity programEntity=expressTrainProgramService.queryExpressTrainProgramByProgramName(syncRequest.getProgramName());
			if(null ==programEntity){
				response.setProgramName(syncRequest.getProgramName());
				response.setResultReason("数据库中不存在 方案名称是"+ syncRequest.getProgramName() + "的数据");
				response.setResultCode("0");
				return response;
			}else{
				try {
					//将esb 对象转换成foss 对象
					programEntity =convertProgramEntity(programEntity,syncRequest);
					//更新方案
					int result =expressTrainProgramService.updateExpressTrainProgram(programEntity);
					
					//将esb 对象转换成foss 对象集合
					List<ExpressLineScheduleEntity> lineScheduleList =convertLineScheduleList(syncRequest.getLineScheduleList(),syncRequest.getProgramName());
					//新增线路时刻信息
					int lineResult =expressLineScheduleService.addExpressLineScheduleMore(lineScheduleList);
					if(result>0&&lineResult>0){
						LOGGER.info("*****信息同步成功******");
					}else{
						LOGGER.info("*****信息同步失败******");
						response.setProgramName(syncRequest.getProgramName());
						response.setResultReason("方案更新失败");
						response.setResultCode("0");
						return response;
					}
				} catch (ExpressTrainScheduleException e) {
					LOGGER.info(e.getMessage(), e);
					throw new CommonException(e.getMessage(), e);
				}
				LOGGER.info("同步GIS的数据信息开始...............");
				response.setProgramName(syncRequest.getProgramName());
				response.setResultReason("同步方案名称是" + syncRequest.getProgramName()
						+ "的线路数据成功");
				response.setResultCode("1");
				return response;
			}
		}else{
			response.setResultCode("请求对象不存在");
			response.setResultCode("0");
		}
		return response;
	}
	/**
	 * 
	 *<p>Title: convertLineScheduleList</p>
	 *<p>将Esb 对象集合转换为Foss对象集合</p>
	 *@author 130566-ZengJunfan
	 *@date 2014-5-14下午4:25:12
	 * @param lineScheduleList
	 * @return
	 */
	private List<ExpressLineScheduleEntity> convertLineScheduleList(
			List<LineScheduleInfo> lineScheduleList ,String programName) {
		List<ExpressLineScheduleEntity> list =new ArrayList<ExpressLineScheduleEntity>();
		for (LineScheduleInfo lineScheduleInfo : lineScheduleList) {
			ExpressLineScheduleEntity lineSchedule =convertLineSchedule(lineScheduleInfo,programName);
			list.add(lineSchedule);
		}
		return list;
	}
	/**
	 * 转换成FOSS线路时刻表对象
	 *<p>Title: convertLineSchedule</p>
	 *<p></p>
	 *@author 130566-ZengJunfan
	 *@date 2014-5-14下午4:31:06
	 * @param lineScheduleInfo
	 * @param programName
	 * @return
	 */
	private ExpressLineScheduleEntity convertLineSchedule(
			LineScheduleInfo lineScheduleInfo, String programName) {
			ExpressLineScheduleEntity lineSchedule =new ExpressLineScheduleEntity();
			//设置线路名称
			lineSchedule.setLineName(lineScheduleInfo.getLineName());
			//设置方案名称
			lineSchedule.setProgramName(programName);
			//设置出发部门的GIS坐标编码
			lineSchedule.setOriginDeptGisId(lineScheduleInfo.getOriginDeptGisId());
			//设置出发部门编码
			lineSchedule.setOriginDeptCode(lineScheduleInfo.getOriginDeptCode());
			//设置到达部门Gis 坐标编码
			lineSchedule.setArriveDeptGisId(lineScheduleInfo.getArriveDeptGisId());
			//设置到达部门编码
			lineSchedule.setArriveDeptCode(lineScheduleInfo.getArriveDeptCode());
			//设置行驶距离
			lineSchedule.setTravelDistance(lineScheduleInfo.getTravelDistance());
			//设置行驶时效
			lineSchedule.setTravelTime(lineScheduleInfo.getTravelTime());
			//设置出发、到达时间
			Date departTime =lineScheduleInfo.getDepartTime().toGregorianCalendar().getTime();
			Date arriveTime =lineScheduleInfo.getArriveTime().toGregorianCalendar().getTime();
			//设置出发时间
			lineSchedule.setDepartTime(departTime);
			//设置到达时间
			lineSchedule.setArriveTime(arriveTime);
			//设置线段顺序
			lineSchedule.setLineOrderCode(lineScheduleInfo.getLineOrderCode());
			//设置创建人
			lineSchedule.setCreateUser(GIS);
		return lineSchedule;
	}

	/**
	 * 将Esb 对象转换为Foss对象
	 *<p>Title: convertProgramEntity</p>
	 *<p></p>
	 *@author 130566-ZengJunfan
	 *@date 2014-5-14下午3:36:51
	 * @param programEntity
	 * @param syncRequest
	 * @return
	 */
	private ExpressTrainProgramEntity convertProgramEntity(
			ExpressTrainProgramEntity programEntity,
			ExpressTrainLineScheduleRequest syncRequest) {
		//线路条数
		programEntity.setLineCount(syncRequest.getLineCount().intValue());
		//车辆数
		programEntity.setVehicleCount(syncRequest.getVehicleCount().intValue());
		//
		programEntity.setModifyUser(GIS);
		return programEntity;
	}

}

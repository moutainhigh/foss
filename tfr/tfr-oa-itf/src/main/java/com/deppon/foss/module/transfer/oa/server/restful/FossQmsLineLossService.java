package com.deppon.foss.module.transfer.oa.server.restful;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillDao;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillQueryService;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillInfoByWaybillNoReultDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillInfoDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillQueryInfoDto;
import com.deppon.foss.module.transfer.oa.server.shared.response.LineLossWaybillInfRequest;
import com.deppon.foss.module.transfer.oa.server.shared.response.LineLossWaybillInfResponse;
import com.deppon.foss.module.transfer.scheduling.api.server.service.ICalculateTransportPathService;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.PathDetailEntity;
import com.deppon.foss.module.transfer.unload.api.server.service.IUnloadService;
import com.deppon.foss.module.transfer.unload.api.shared.dto.LineLossWaybillInfDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.UnloadSerialDetaiDto;
import com.deppon.foss.util.define.FossConstants;
import com.deppon.foss.workflowservice.CommonException;
@Controller
public class FossQmsLineLossService{

	private static Logger LOGGER = Logger
			.getLogger(FossQmsLineLossService.class);
	
	
	/**
	 * 运单Dao
	 */
	private IWaybillDao waybillDao;
	/**
	 * 运单查询服务
	 */
	@Resource
	private IWaybillQueryService waybillQueryService;
	
	
	/**
	 * 卸车服务
	 */
	private IUnloadService unloadService;
	
	/**走货路径*/
	@Resource
	private ICalculateTransportPathService calculateTransportPathService;

	/**
	 * 
	 * 线路货损查询运单
	 * 
	 */
	@RequestMapping(value = "/queryLineLossWaybillInfo", method = RequestMethod.POST)
	public @ResponseBody LineLossWaybillInfResponse queryWaybillInfo(@RequestBody LineLossWaybillInfRequest request){
		String waybillNo = request.getWaybillNum();
		LOGGER.info("线路货损查询运单信息和卸车扫描时间开始" + new Date() + " ==== " + waybillNo);
		LineLossWaybillInfResponse response = new LineLossWaybillInfResponse();

		List<String> waybillList = new ArrayList<String>();
		waybillList.add(waybillNo);
		WaybillQueryInfoDto waybillQueryInfoDto = new WaybillQueryInfoDto();//创建对象
		waybillQueryInfoDto.setState(FossConstants.ACTIVE);
		waybillQueryInfoDto.setWaybillList(waybillList);
		LineLossWaybillInfDto lineLossWaybillInfDto = new LineLossWaybillInfDto();
		try {
			if(StringUtils.isBlank(waybillNo)){
				throw new CommonException("运单不存在");
			}
			//查询运单信息
			WaybillInfoByWaybillNoReultDto waybillInfoByWaybillNoReultDto = waybillQueryService.queryWaybillInfoByWaybillNo(waybillNo);
			if(waybillInfoByWaybillNoReultDto == null){
				throw new CommonException("查询运单信息失败");
			}
			BeanUtils.copyProperties(lineLossWaybillInfDto, waybillInfoByWaybillNoReultDto);
			//查询运单信息
			List<WaybillInfoDto> waybillInfoDtoList = waybillDao.queryWaybillInfo(waybillQueryInfoDto);
			//运输类型
			String trasptype = "";
			String transportType = "";
			if(CollectionUtils.isNotEmpty(waybillInfoDtoList)){
				//开单部门
				lineLossWaybillInfDto.setCreateOrgName(waybillInfoDtoList.get(0).getCreateOrgName());
				trasptype = waybillInfoDtoList.get(0).getTransportType();
				if("TRANS_VEHICLE".equals(trasptype)){
					transportType = "汽运";
				}else if("TRANS_AIRCRAFT".equals(trasptype)){
					transportType = "空运";
				}else if("TRANS_EXPRESS".equals(trasptype)){
					transportType = "经济快递";
				}
			}
		
			lineLossWaybillInfDto.setTransportType(transportType);
			lineLossWaybillInfDto.setBillTimeStr();
			//查询卸车流水信息
			List<UnloadSerialDetaiDto> unloadSerials = unloadService.queryUnloadSerialDetailByWaybillNo(waybillNo);
			for (UnloadSerialDetaiDto unloadSerialDetaiDto : unloadSerials) {
				unloadSerialDetaiDto.setUnloadTimeStr();
			}
			//查询走货路径
			List<PathDetailEntity> pathDetails = calculateTransportPathService.queryPathDetailByNos(waybillNo, null);
			StringBuffer pathDetailUnion = new StringBuffer();
			//循环拼接走货路径
			for (PathDetailEntity pathDetailEntity : pathDetails) {
				pathDetailUnion.append(pathDetailEntity.getOrigOrgCode()).append("-");
			}
			//获取最后一个路径
			if(CollectionUtils.isNotEmpty(pathDetails)){
				pathDetailUnion.append(pathDetails.get(pathDetails.size() - 1).getObjectiveOrgCode());
			}
			//设置拼接走货路径
			response.setPathDeatailUnion(pathDetailUnion.toString());
			//设置走货路径集合
			response.setPathDetails(pathDetails);
			//设置卸车扫描集
			response.setUnloadScanList(unloadSerials);
		} catch (Exception e) {
			response.setReturnCode("1");
			response.setReturnMsg(StringUtils.substring(e.getMessage(), 0, 100));
			return response;
		}
		LOGGER.info("线路货损查询运单信息和卸车扫描时间结束" + new Date() + " ==== " + waybillNo);
		response.setReturnCode("0");
		response.setLineLossWaybillInfDto(lineLossWaybillInfDto);
		return response;
	}

	public void setWaybillQueryService(IWaybillQueryService waybillQueryService) {
		this.waybillQueryService = waybillQueryService;
	}

	public void setWaybillDao(IWaybillDao waybillDao) {
		this.waybillDao = waybillDao;
	}

	public void setUnloadService(IUnloadService unloadService) {
		this.unloadService = unloadService;
	}

	public void setCalculateTransportPathService(
			ICalculateTransportPathService calculateTransportPathService) {
		this.calculateTransportPathService = calculateTransportPathService;
	}
	
	
}

package com.deppon.foss.module.transfer.load.server.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.load.api.server.service.IExpressPackageService;
import com.deppon.foss.module.transfer.load.api.shared.domain.ExpressPackageEntity;
import com.deppon.foss.module.transfer.load.api.shared.dto.CancelPackageResultDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.ExpressPackageQueryDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.ExpressPackageSaveConditionDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.ExpressPackageSerialNoStockDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.ExpressPackageWayBillDetailDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.QueryWaybillForExpressPackageConditionDto;
import com.deppon.foss.module.transfer.load.api.shared.vo.ExpressPackageVo;
import com.deppon.foss.util.define.FossConstants;

/** 
 * @className: ExpressPackageAction
 * @author: ShiWei shiwei@outlook.com
 * @description: 快递包action
 * @date: 2013-7-17 下午5:28:05
 * 
 */
public class ExpressPackageAction extends AbstractAction {
	
	private static final long serialVersionUID = -941920636643364755L;
	
	/**
	 * 记录日志
	 */
	protected final Logger log = LoggerFactory.getLogger(this.getClass()); 
	
	/**
	 * 快递包service
	 */
	private IExpressPackageService expressPackageService;
	
	/**
	 * 本模块Vo
	 */
	private ExpressPackageVo packageVo = new ExpressPackageVo();

	public ExpressPackageVo getPackageVo() {
		return packageVo;
	}

	public void setPackageVo(ExpressPackageVo packageVo) {
		this.packageVo = packageVo;
	}

	public void setExpressPackageService(IExpressPackageService expressPackageService) {
		this.expressPackageService = expressPackageService;
	}
	
	/**
	 * 包信息管理页面跳转
	 * @author 045923-foss-shiwei
	 * @date 2013-7-18 下午5:06:22
	 */
	public String expressPackageQueryIndex(){
		return returnSuccess();
	}
	
	/**
	 * 查询包
	 * @author 045923-foss-shiwei
	 * @date 2013-7-18 下午3:18:09
	 */
	@JSON
	public String queryExpressPackageList(){
		//接收查询条件
		ExpressPackageQueryDto queryDto = packageVo.getQueryPackageDto();
		//获取查询的包列表
		List<ExpressPackageEntity> entityList = expressPackageService.queryExpressPackageList(queryDto, this.getStart(), this.getLimit());
		packageVo.setPackageList(entityList);
		Long totalCount = expressPackageService.queryExpressPackageCount(queryDto);
		this.setTotalCount(totalCount);
		return returnSuccess();
		
	}
	
	/**
	 * 包查询界面，撤销包
	 * @author 045923-foss-shiwei
	 * @date 2013-7-25 下午1:58:45
	 */
	@JSON
	public String cancelExpressPackage(){
		List<String> packageNoList = packageVo.getPackageNoList();
		if(CollectionUtils.isNotEmpty(packageNoList)){
			//定义返回的处理结果list
			List<CancelPackageResultDto> dtoList = new ArrayList<CancelPackageResultDto>();
			for(String packageNo : packageNoList){
				CancelPackageResultDto dto = new CancelPackageResultDto();
				dto.setPackageNo(packageNo);
				try{
					expressPackageService.cancelExpressPackage(packageNo);
					dto.setBeSuccess(FossConstants.YES);
				}catch(Exception e){
					dto.setBeSuccess(FossConstants.NO);
					dto.setMessage(e.getMessage());
				}finally{
					dtoList.add(dto);
				}
			}
			packageVo.setResultList(dtoList);
		}
		
		return returnSuccess();
	}
	/**
	 * 修改包信息
	 * @author 105869-heyongdong
	 * @2014年7月7日 11:32:23
	 * @return
	 */
	@JSON
	public String modifyPackageInfo(){
		String packageNo = packageVo.getPackageNo();
		String destOrgName = packageVo.getDestOrgName();
		String destOrgCode = packageVo.getDestOrgCode();
		try{
			expressPackageService.modifyPackageInfo(packageNo,destOrgName,destOrgCode);
		}catch(TfrBusinessException e){
			return returnError(e);
		}catch(Exception e){
			return returnError(e.toString());
		}
		return returnSuccess();
	}

	/**
	 * 通过运单号快速添加，获取运单库存和其下流水号库存
	 * @Author: 200978  xiaobingcheng
	 * 2014-8-20
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@JSON
	public String queryWaybillStockByWaybillNo(){
		//获取查询条件dto
		QueryWaybillForExpressPackageConditionDto queryWaybillForExpressPackageDto = packageVo.getQueryWaybillForExpressPackageDto();
		try{
			Map<String,Object> map = expressPackageService.queryWaybillStockByWaybillNo(queryWaybillForExpressPackageDto);
			//返回运单库存
			packageVo.setWaybillStock((ExpressPackageWayBillDetailDto)map.get("waybillStock"));
			//返回流水号库存列表
			packageVo.setSerialNoStockList((List<ExpressPackageSerialNoStockDto>)map.get("serialNoList"));
		}catch(BusinessException e){
			return returnError(e);
		}
		return returnSuccess();
	}
	
	/**
	 * 查询当前部门到到达部门是否存在线路
	 * @Author: 200978  xiaobingcheng
	 * 2014-8-22
	 * @return
	 */
	@JSON
	public String queryExpressLineIsExist(){
		boolean isExistLine = false;
		//获取查询条件dto
		QueryWaybillForExpressPackageConditionDto queryWaybillForExpressPackageDto = packageVo.getQueryWaybillForExpressPackageDto();
		try {
			isExistLine = expressPackageService.queryExpressLineIsExist(queryWaybillForExpressPackageDto);
			queryWaybillForExpressPackageDto.setIsExistPackageLine(isExistLine);
	//	    queryWaybillForExpressPackageDto.setIsExistPackageLine(true);
		} catch (BusinessException e) {
			return returnError(e);
		}
		return returnSuccess();
	}
	
	/**
	 * 查询库存运单,并查询每个运单下的所有流水号
	 * @Author: 200978  xiaobingcheng
	 * 2014-8-22
	 * @return
	 */
	@JSON
	public String queryWaybillStockListForPackage(){
		//获取查询条件dto
		QueryWaybillForExpressPackageConditionDto queryWaybillForExpressPackageDto = packageVo.getQueryWaybillForExpressPackageDto();
		try {
			List<ExpressPackageWayBillDetailDto> waybillStockList = expressPackageService.queryWaybillStockListForPackage(queryWaybillForExpressPackageDto,this.getStart(),this.getLimit());
			packageVo.setWaybillStockList(waybillStockList);
			Long totalCount = expressPackageService.queryWaybillStockListForPackageCount(queryWaybillForExpressPackageDto);
			this.setTotalCount(totalCount);
		} catch (BusinessException e) {
			return returnError(e);
		}
		return returnSuccess();
	}
	
	/**
	 * 新增快递包  PC端
	 * @Author: 200978  xiaobingcheng
	 * 2014-8-25
	 * @return
	 */
	@JSON
	public String saveExpressPackage(){
		ExpressPackageSaveConditionDto expressPackageSaveConditionDto = packageVo.getExpressPackageSaveConditionDto();
		if (expressPackageSaveConditionDto == null 
				|| expressPackageSaveConditionDto.getPackageEntity() == null 
				|| expressPackageSaveConditionDto.getPackageEntity().getPackageNo() == null) {
			return null;
		}
		String packageNo = expressPackageSaveConditionDto.getPackageEntity().getPackageNo();
		try {
			expressPackageService.saveExpressPackage(expressPackageSaveConditionDto);//pC端保存快递包
			packageVo.setPackageNo(packageNo);
			log.error("包号：" + packageNo + "生成完毕，action返回处理成功");
		} catch (BusinessException e) {
			return returnError(e);
		}
		return returnSuccess();
	}
	
	/**
	 * 查询是否包号已经存在
	 * @Author: 200978  xiaobingcheng
	 * 2014-8-25
	 * @return
	 */
	@JSON
	public String queryIsExistPackageNo(){
		String packageNo = packageVo.getExpressPackageSaveConditionDto().getPackageEntity().getPackageNo();
		try {
			boolean isExistPackageNo = expressPackageService.queryIsExistPackageNo(packageNo);
			packageVo.getExpressPackageSaveConditionDto().setIsExistPackageNo(isExistPackageNo);
		} catch (BusinessException e) {
			return returnError(e);
		}
		return returnSuccess();
	}
}

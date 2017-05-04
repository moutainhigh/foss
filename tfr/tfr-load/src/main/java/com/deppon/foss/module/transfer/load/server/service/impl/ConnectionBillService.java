
package com.deppon.foss.module.transfer.load.server.service.impl;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.base.util.define.BizTypeConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.framework.server.components.export.excel.ExportSetting;
import com.deppon.foss.framework.server.components.export.excel.ExporterExecutor;
import com.deppon.foss.module.base.baseinfo.api.server.service.IAcceptPointSalesDeptService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IMotorcadeService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AcceptPointSalesChildrenDeptEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.MotorcadeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.transfer.common.api.server.service.ITfrCommonService;
import com.deppon.foss.module.transfer.common.api.shared.define.TfrSerialNumberRuleEnum;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.load.api.server.dao.IConnectionBillDao;
import com.deppon.foss.module.transfer.load.api.server.service.IConnectionBillService;
import com.deppon.foss.module.transfer.load.api.shared.define.LoadConstants;
import com.deppon.foss.module.transfer.load.api.shared.domain.ConnectionBillDetailEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.ConnectionBillEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.HandOverBillSerialNoDetailEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.SerialNoStockEntity;
import com.deppon.foss.module.transfer.load.api.shared.dto.HandOverBillDetailDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.NewConnectionBillDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.QueryArrivalConnectionBillConditionDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.QueryConnectionBillConditionDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.QueryHandOverBillNoForUnloadTaskLackGoodsDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.QuerySerialNoListForWaybillConditionDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.QueryWayBillForConnectionBillDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.UpdateConnectionBillDto;
import com.deppon.foss.module.transfer.load.api.shared.vo.ArrivalConnectionBillVo;
import com.deppon.foss.module.transfer.load.api.shared.vo.ConnectionBillVo;
import com.deppon.foss.module.transfer.stock.api.define.StockConstants;
import com.deppon.foss.module.transfer.stock.api.server.service.IStockService;
import com.deppon.foss.module.transfer.stock.api.shared.domain.InOutStockEntity;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;


/** 
 * @className: IConnectionBillService
 * @author:zenghaibin-foos-205109
 * @description: 接驳交接单模块service接口
 * @date: 2015-04-1=09 上午10:21:31
 */
public class ConnectionBillService implements IConnectionBillService {


	/**
	 * 记录日志
	 */
	protected final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	/**
     * 接驳点对应营业部映射的service
     */
    private IAcceptPointSalesDeptService acceptPointSalesDeptService;
	
	//查询接驳交接单相关信息的dao
	private IConnectionBillDao connectionBillDao;
	
	//查询组织部门信息service
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	
	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;
	
	private IMotorcadeService motorcadeService;
	
	//生成交接单号
	private  ITfrCommonService tfrCommonService;
	
	//库存
	private IStockService stockService;

	/**
	 *查询当前部门编码
	 * 
	 ***/
	public String [] queryOrgCode(){
		
		String []  info=new String[LoadConstants.SONAR_NUMBER_3];
		String orgCode = FossUserContext.getCurrentInfo().getCurrentDeptCode();
		
		OrgAdministrativeInfoEntity org =this.querySuperOrgByOrgCode(orgCode);
		if(org != null){
			
			info[0]=org.getCode();
			info[1]=org.getName();
			info[2]=org.getTransferCenter();
		}
		return info;
	}
	
	
	/**
	 *查询顶级外场
	 *@author 205109-foss-zenghaibin
	 * @date 2015-08-07
	 ****/
	private  OrgAdministrativeInfoEntity querySuperOrgByOrgCode(String orgCode){
		//设置查询参数
		List<String> bizTypesList = new ArrayList<String>();
		//外场类型
		bizTypesList.add(BizTypeConstants.ORG_TRANSFER_CENTER);
		//查询上级部门
		OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = orgAdministrativeInfoComplexService.
				queryOrgAdministrativeInfoIncludeSelfByCode(orgCode, bizTypesList);
		if(orgAdministrativeInfoEntity != null){
			//返回部门
			return orgAdministrativeInfoEntity;
		}else{
			OrgAdministrativeInfoEntity fleet = orgAdministrativeInfoComplexService.getTopFleetByCode(orgCode);
			if(fleet!=null){
				MotorcadeEntity motorcadeEntity= motorcadeService.queryMotorcadeByCodeClean(fleet.getCode());
				//若查询出的上级车队是顶级车队
				if(motorcadeEntity !=null&&FossConstants.YES.equals(motorcadeEntity.getIsTopFleet())
						&& StringUtils.isNotEmpty(motorcadeEntity.getTransferCenter())){
					
					OrgAdministrativeInfoEntity orgEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(motorcadeEntity.getTransferCenter());
					if(orgEntity!=null&&StringUtils.equals(orgEntity.getTransferCenter(), FossConstants.YES)){
						return orgEntity;
					}else{
						//获取上级部门失败
						LOGGER.error("################查询组织（code：" + orgCode + "）所属的上级部门失败(包括营业部、派送部、外场、总调)！##########");
						return null;
					}
				}else{
					//获取上级部门失败
					LOGGER.error("################查询组织（code：" + orgCode + "）所属的上级部门失败(包括营业部、派送部、外场、总调)！##########");
					return null;
				}
				
			}else{
				//获取上级部门失败
				LOGGER.error("################查询组织（code：" + orgCode + "）所属的上级部门失败(包括营业部、派送部、外场、总调)！##########");
				return null;	
			}
			
		}
	}
	/**
	 *用于查询接驳交接单信息
	 *@author 205109-foss-zenghaibin
	 * @date 2015-04-09
	 * @param connectionBillVo 页面传递的查询条件参数（交接单号，部门信息，时间等）
	 * @param limit 分页参数
	 * @param start 分页参数
	 * @return List<ConnectionBillEntity>  返回交接单基本信息实体List
	 ****/
	@Override
	public List<ConnectionBillEntity> queryConnectionBillList(ConnectionBillVo connectionBillVo,int limit,int start){
		
		
		if(connectionBillVo==null){
			LOGGER.info("TFR LOAD 接驳交接单查询参数connectionBillVo为空");
			throw new TfrBusinessException("请求参数为空");
		}
		QueryConnectionBillConditionDto dto = connectionBillVo.getQueryConnectionBillConditionDto();
		if(dto==null){
			LOGGER.info("TFR LOAD 接驳交接单查询参数queryConnectionBillConditionDto为空");
			throw new TfrBusinessException("页面参数为空");
		}
		if(dto.getBeginHandOverTime()==null||dto.getEndHandOverTime()==null){
			throw new TfrBusinessException("交接时间为空");
		}
		if(StringUtils.isBlank(dto.getDepartDeptCode())){
			throw new TfrBusinessException("出发部门为空");
		}
		
		List<ConnectionBillEntity> list=new ArrayList<ConnectionBillEntity>();
		 list= connectionBillDao.queryConnectionBillList(dto, limit, start);
		 return list;
	}
	
	/**
	 *用于查询接驳交接单信息
	 * @author 218427-foss-hongwy
	 * @date 2015-10-29
	 * @param arrivalConnectionBillVo 页面传递的查询条件参数（交接单号，部门信息，时间等）
	 * @param limit 分页参数
	 * @param start 分页参数
	 * @return List<ConnectionBillEntity>  返回交接单基本信息实体List
	 ****/
	@Override
	public List<ConnectionBillEntity> queryArrivalConnectionBillList(ArrivalConnectionBillVo arrivalConnectionBillVo,int limit,int start){
		if(arrivalConnectionBillVo==null){
			LOGGER.info("TFR LOAD 接驳交接单(到达)查询参数arrivalConnectionBillVo为空");
			throw new TfrBusinessException("请求参数为空");
		}
		QueryArrivalConnectionBillConditionDto dto = arrivalConnectionBillVo.getQueryArrivalConnectionBillConditionDto();
		if(dto==null){
			LOGGER.info("TFR LOAD 接驳交接单(到达)查询参数queryArrivalConnectionBillConditionDto为空");
			throw new TfrBusinessException("页面参数为空");
		}
		if(dto.getDepartTime()==null||dto.getArriveTime()==null){
			throw new TfrBusinessException("出发时间为空");
		}
		if(dto.getArriveDeptCode()==null){
			throw new TfrBusinessException("到达部门为空");
		}
		
		List<ConnectionBillEntity> list = new ArrayList<ConnectionBillEntity>();
		list = connectionBillDao.queryArrivalConnectionBillList(dto, limit, start);
		return list;
		
	}

	/**
	 *用于查询接驳交接单信息总条数
	 *@author 205109-foss-zenghaibin
	 * @date 2015-04-09
	 * @param connectionBillVo 页面传递的查询条件参数（交接单号，部门信息，时间等）
	 ****/
	@Override
	public Long queryConnectionBillListCount(ConnectionBillVo connectionBillVo){
		
		QueryConnectionBillConditionDto dto = connectionBillVo.getQueryConnectionBillConditionDto();

		return connectionBillDao.queryConnectionBillListCount(dto);
	}
    
	/**
	 * 用于查询接驳交接单信息总条数
	 * @author 218427-foss-hongwy
	 * @date 2015-10-30
	 * @param arrivalConnectionBillVo 页面传递的查询条件参数（交接单号，部门信息，时间等）
	 ****/
	@Override
	public Long queryArrivalConnectionBillListCount(ArrivalConnectionBillVo arrivalConnectionBillVo){
		QueryArrivalConnectionBillConditionDto dto = arrivalConnectionBillVo.getQueryArrivalConnectionBillConditionDto();
		
		return connectionBillDao.queryArrivalConnectionBillListConut(dto);
	}

	
	/**
	 *用于查询库存运单
	 *查询的运单必须在当天当前外场库存并且到达部门为接驳点所辐射营业部
	 *@author 205109-foss-zenghaibin
	 * @date 2015-04-09
	 * @param connectionBillVo 页面传递的查询条件参数（交接单号，部门信息，时间等）
	 * @return List<ConnectionBillDetailEntity> 交接单明细list
	 ****/
	@Override
	public   Map<String,Object>  queryWaybillStockList(ConnectionBillVo connectionBillVo,int limit,int start){
		Map<String,Object> hashMap=new HashMap<String,Object>();
		List<ConnectionBillDetailEntity>  waybillStockList=new ArrayList<ConnectionBillDetailEntity>();
		Long totalCount=0L;
		if(connectionBillVo==null){
			
			throw new TfrBusinessException("参数为空");
		}else if(connectionBillVo.getQueryWayBillForConnectionBillDto()==null){
			throw new TfrBusinessException("查询条件不能为空");
		}else if(StringUtils.isBlank(connectionBillVo.getQueryWayBillForConnectionBillDto().getArriveDeptCode())){
			throw new TfrBusinessException("到达接驳点不能为空");
		}
		QueryWayBillForConnectionBillDto wayBillDto=new QueryWayBillForConnectionBillDto();//定义查询参数
		wayBillDto=connectionBillVo.getQueryWayBillForConnectionBillDto();
		String arriveDeptCode=wayBillDto.getArriveDeptCode();
		String currentOrgCode = wayBillDto.getOrgCode();//当前外场编码
		wayBillDto.setOrgCode(currentOrgCode);
		if(wayBillDto.getBeginInStorageTime()==null||wayBillDto.getEndInStorageTime()==null){
			throw new TfrBusinessException("入库开始和结束时间不能为空");
		}
		//定义到达接驳点查询辐射营业部的查询条件
		List<String> acceptPointCodes=new ArrayList<String>();
		//定义接收所辐射的营业部
		List<AcceptPointSalesChildrenDeptEntity> acceptPointList;
		 acceptPointCodes.add(arriveDeptCode);
		try{
			acceptPointList=acceptPointSalesDeptService.queryAcceptPointSaleDeptsByAcceptPointCode(acceptPointCodes);
		}catch(Exception e){
			
			throw new BusinessException("调综合接查询接驳点所辐射营业部异常："+arriveDeptCode);
		}
		if(acceptPointList!=null){
			wayBillDto.setAcceptPointSalesDepts(new ArrayList<String>());
			for(int i=0;i<acceptPointList.size();i++){
				wayBillDto.getAcceptPointSalesDepts().add(acceptPointList.get(i).getSalesDepartmentCode());
				
			}
			if(wayBillDto.getAcceptPointSalesDepts()==null||wayBillDto.getAcceptPointSalesDepts().size()==0){
				throw new TfrBusinessException("接驳点所辐射的营业部编码为空");
			}
			waybillStockList=connectionBillDao.queryWaybillStockList(wayBillDto,limit,start);
			totalCount=this.queryWaybillStockCount(wayBillDto);
			
		}else{
			
			throw new TfrBusinessException("无该接驳点所辐射的营业部,请配置");
		}
		
		//构造流水号查询条件
		QuerySerialNoListForWaybillConditionDto queryDto = new QuerySerialNoListForWaybillConditionDto();
		queryDto.setCurrentDeptCode(currentOrgCode);
		queryDto.setNextDeptCodeList(wayBillDto.getAcceptPointSalesDepts());
		
		for(ConnectionBillDetailEntity waybillStock : waybillStockList){
			queryDto.setWaybillNo(waybillStock.getWaybillNo());
			//查询流水明细
			List<SerialNoStockEntity> serialNoStockList = this.querySerialNoStockListByWaybillNo(queryDto);
			waybillStock.setSerialNoStockList(serialNoStockList);
		}
		
		hashMap.put("totalCount", totalCount);
		hashMap.put("waybillStockList", waybillStockList);
		//返回结果
		return hashMap;
	}
	
	/**
	 * 内部调用，根据运单号获取流水号库存
	 * @author 205109-foss-zenghaibin
	 * @date 2015-05-08 上午11:33:22
	 */
	private List<SerialNoStockEntity> querySerialNoStockListByWaybillNo(QuerySerialNoListForWaybillConditionDto querySerialNoListForWaybillConditionDto) {	
		
		return connectionBillDao.querySerialNoStockList(querySerialNoListForWaybillConditionDto);
	}
	/**查询库存运单数量 分页用
	 *@author 205109-foss-zenghaibin
	 * @date 2015-04-09
	 * @param connectionBillVo （运单号，入库时间，运输性质等）
	 * @return Long
	 ***/
	@Override
	public  Long queryWaybillStockCount(QueryWayBillForConnectionBillDto wayBillDto){
		
		Long count=connectionBillDao.queryWaybillStockCount(wayBillDto);
		return count;
	}
	
	/**
	 *保存接驳交接单，并返回交接单号
	 *@author 205109-foss-zenghaibin
	 * @date 2015-05-04
	 * @param newConnectionBillDto 
	 * @return String 
	 ***/
	@Transactional
	@Override
	public String saveConnectionBill(NewConnectionBillDto newConnectionBillDto){
		
		//校验交接单数据
		this.validate(newConnectionBillDto);
		//交接单基本信息
		 ConnectionBillEntity connectionBillEntity=newConnectionBillDto.getConnectionBillEntity();
		 List<ConnectionBillDetailEntity> waybillList=newConnectionBillDto.getWaybillStockList();
		 List<ConnectionBillDetailEntity>  unSavedWaybillStockList=new ArrayList<ConnectionBillDetailEntity>();//待保存的交接单明细
		 List<HandOverBillSerialNoDetailEntity> serialNoList=newConnectionBillDto.getSerialNoStockList();
		 List<HandOverBillSerialNoDetailEntity> unsavedSerialNoStockList=new ArrayList<HandOverBillSerialNoDetailEntity>();//待保存的运单流水明细

		
		 
		 String connectionBillNo = tfrCommonService.generateSerialNumber(TfrSerialNumberRuleEnum.JBJJD);
		//获取当前用户工号、姓名
		UserEntity user = FossUserContext.getCurrentUser();
		String userCode = user.getEmployee().getEmpCode();
		//用户name
		String userName = user.getEmployee().getEmpName();
		String currentOrgCode = connectionBillEntity.getDepartDeptCode();
		 Date nowDate=new Date();
		 LOGGER.info("接驳交接单基本信息实体保存star..............");
		 connectionBillEntity.setId(UUIDUtils.getUUID());//id主键
		 connectionBillEntity.setConnectionBillNo(connectionBillNo);//交接单号
		 connectionBillEntity.setHandOverTime(nowDate);//交接时间
		 connectionBillEntity.setCreateUserName(userName);//创建人姓名
		 connectionBillEntity.setCreateUserCode(userCode);//创建人工号
		 connectionBillEntity.setModifyUserName(userName);//修改人姓名
		 connectionBillEntity.setModifyUserCode(userCode);//修改人工号
		 connectionBillEntity.setModifyDate(nowDate);//修改时间
		 connectionBillEntity.setIsPda(FossConstants.NO);//是否pda
		 
		 
		  for (int i = 0; i < waybillList.size(); i++) {
			  ConnectionBillDetailEntity waybillEntity=waybillList.get(i);
			  waybillEntity.setId(UUIDUtils.getUUID());//id主键
			  waybillEntity.setConnectionBillNo(connectionBillNo);//交接单号
			  waybillEntity.setOrigOrgCode(connectionBillEntity.getDepartDeptCode());//出发部门编码
			  waybillEntity.setHandOverType(connectionBillEntity.getHandOverType());//交接类型
			  waybillEntity.setCreateDate(connectionBillEntity.getHandOverTime());//创建时间
			  waybillEntity.setModifyDate(connectionBillEntity.getModifyDate());//修改时间
			//保险价值，乘以100
			  waybillEntity.setInsuranceValue(waybillEntity.getInsuranceValue().multiply(new BigDecimal(LoadConstants.SONAR_NUMBER_100)));
			  unSavedWaybillStockList.add(waybillEntity);
			  
		  }
		  
		 List<InOutStockEntity> outStockList = new ArrayList<InOutStockEntity>(); 
		 for(int i=0;i<serialNoList.size();i++){
			 HandOverBillSerialNoDetailEntity seriaNoEntity=serialNoList.get(i);
			 
			 
			 InOutStockEntity entity = new InOutStockEntity();
				//运单号
				entity.setWaybillNO(seriaNoEntity.getWaybillNo());
				//流水号
				entity.setSerialNO(seriaNoEntity.getSerialNo());
				//部门code
				entity.setOrgCode(currentOrgCode);
				//出库类型，手动交接出库
				entity.setInOutStockType(StockConstants.HANDMADE_HANDOVER_BILL_OUT_STOCK_TYPE);
				//操作人code
				entity.setOperatorCode(userCode);
				//操作人name
				entity.setOperatorName(userName);
				//交接单号
				entity.setInOutStockBillNO(connectionBillNo);
				outStockList.add(entity);
			// 设置流水号之交接单编号
			seriaNoEntity.setHandOverBillNo(connectionBillEntity.getConnectionBillNo());
			// 设置流水号之交接时间
			seriaNoEntity.setHandOverTime(connectionBillEntity.getHandOverTime());
			// 设置流水号之出发部门编码
			seriaNoEntity.setOrigOrgCode(connectionBillEntity.getDepartDeptCode());
			// 流水号生成主键
			seriaNoEntity.setId(UUIDUtils.getUUID());
			unsavedSerialNoStockList.add(seriaNoEntity);
			
		 }
		 
		 if(!CollectionUtils.isEmpty(outStockList)){
				//记录批量出库日志
				LOGGER.error("交接单号：" + connectionBillNo + "流水号批量出库开始");
				//批量出库
				stockService.outStockBatchPC(outStockList);
				LOGGER.error("交接单号：" + connectionBillNo + "流水号批量出库结束");
			}
		 
		 connectionBillEntity.setStatu(LoadConstants.HANDOVERBILL_STATE_FORMAL_HANDOVER);
		 connectionBillDao.saveConnectionBill(connectionBillEntity,unSavedWaybillStockList,unsavedSerialNoStockList);
		return connectionBillNo;
	}
	
	/**
	 *保存接驳交接单，并返回交接单号
	 *@author 205109-foss-zenghaibin
	 * @date 2015-05-04
	 * @param newConnectionBillDto 
	 * @return String 
	 ***/
	private void validate(NewConnectionBillDto newConnectionBillDto){
		
		if(newConnectionBillDto==null){
			throw new TfrBusinessException("参数为空");
		}
		//交接单基本信息实体
		 ConnectionBillEntity connectionBillEntity=newConnectionBillDto.getConnectionBillEntity();
		 //运单信息
		 List<ConnectionBillDetailEntity> waybillList=newConnectionBillDto.getWaybillStockList();
		 //流水信息
		 List<HandOverBillSerialNoDetailEntity> serialNoList=newConnectionBillDto.getSerialNoStockList();
		if(connectionBillEntity==null){
			throw new TfrBusinessException("交接单基本信息为空");
		}else if(StringUtils.isBlank(connectionBillEntity.getDepartDeptCode())){
			throw new TfrBusinessException("到达接驳点信息不能为空");
		}else if(StringUtils.isBlank(connectionBillEntity.getVehicleNo())){
			throw new TfrBusinessException("车牌号不能为空");
		}else if(StringUtils.isBlank(connectionBillEntity.getDriverCode())){
			throw new TfrBusinessException("接驳司机工号不能为空");
		}
		
		if(waybillList==null||waybillList.size()==0){
			throw new TfrBusinessException("运单不能为空");
		}
		if(serialNoList==null || serialNoList.size()==0){
			throw new TfrBusinessException("运单流水不能为空");
		}
		
		
	}
	
	
	/**
	 *通过交接单号查询交接单实体
	 * @author 205109-foss-zenghaibin
	 * @date 2015-04-09 10:09:30
	 * @param connctionBillNo 接驳交接单号
	 ***/
	@Override
	public ConnectionBillEntity queryConnectionBillByNo(String connctionBillNo){
		
		ConnectionBillEntity entity =new ConnectionBillEntity();
		 entity = connectionBillDao.queryConnectionBillByNo(connctionBillNo);
		return entity;
	}
	
	/**
	 *通过交接单号查询交接单实体
	 * @author 218427-foss-hongwy
	 * @date 2015-11-01 14:55:46
	 * @param connctionBillNo 接驳交接单号
	 ***/
	@Override
	public ConnectionBillEntity queryArrivalConnectionBillByNo(String arrivalConnectionBillNo){
		ConnectionBillEntity entity = new ConnectionBillEntity();
		entity = connectionBillDao.queryArrivalConnectionBillByNo(arrivalConnectionBillNo);
		return entity;
	}
	
	/**
	 *通过交接单号查询交接单实体
	 * @author 205109-foss-zenghaibin
	 * @date 2015-04-09 10:09:30
	 * @param connctionBillNo 接驳交接单号
	 ***/
	@Override
	public List<ConnectionBillDetailEntity>  queryConnectionBillDetailByNo(String connectionBillNo){
		
		List<ConnectionBillDetailEntity>  waybillStockList=connectionBillDao.queryConnectionBillDetailByNo(connectionBillNo,null);
		
		return waybillStockList;
	}
	
	
	/**
	 *通过交接单号查询交接单实体
	 * @author 218427-foss-hongwy
	 * @date 2015-10-31 15:46:30
	 * @param arrivalConnctionBillNo 接驳交接单号
	 ***/
	@Override
	public List<ConnectionBillDetailEntity> queryArrivalConnectionBillDetailByNo(String arrivalConnectionBillNo){
		List<ConnectionBillDetailEntity> waybillStockList =connectionBillDao.queryArrivalConnectionBillDetailByNo(arrivalConnectionBillNo);
		
		return waybillStockList;
		
	}
	/**
	 *通过交接单号,运单号查流水明细
	 * @author 205109-foss-zenghaibin
	 * @date 2015-04-09 10:09:30
	 ***/
	@Override
	public List<HandOverBillSerialNoDetailEntity>  queryWaybillDetailByNos(String connctionBillNo,String waybillNo){
		List<HandOverBillSerialNoDetailEntity> list= new ArrayList<HandOverBillSerialNoDetailEntity> ();
		
		list=connectionBillDao.queryWaybillDetailByNos( connctionBillNo, waybillNo);
		
		return  list;
	}
	
	/**
	 *通过交接单号,运单号查流水明细
	 * @author 218427-foss-hongwy
	 * @date 2015-17-09 10:09:30
	 ***/
	@Override
	public List<HandOverBillSerialNoDetailEntity> queryArrivalWaybillDetailByNos(String connectionBillNo,String waybillNo){
		List<HandOverBillSerialNoDetailEntity> list = new ArrayList<HandOverBillSerialNoDetailEntity>();
		list=connectionBillDao.queryArrivalWaybillDetailByNos(connectionBillNo,waybillNo);
	    return list;
	}
	
	
	
	@Transactional
	@Override
	public void updateConnectionBill(UpdateConnectionBillDto updateConnectionBillDto){

		//获取当前用户名、工号、部门code
		String userName = FossUserContext.getCurrentInfo().getEmpName();
		//当前用户code
		String userCode = FossUserContext.getCurrentInfo().getEmpCode();
		//获取修改后的交接单基本信息实体
		ConnectionBillEntity nowConnectionBillEntity = updateConnectionBillDto.getConnectionBillEntity();
		//获取交接单号
		String connectionBillNo = nowConnectionBillEntity.getConnectionBillNo();
		//为修改后的交接单补充其他属性
		addFieldsValueForConnectionBill(nowConnectionBillEntity,updateConnectionBillDto);
		//获取删除的运单Map
		Map<String,ConnectionBillDetailEntity> deletedWaybillMap = updateConnectionBillDto.getDeletedWaybillMap();
		//获取被修改的运单Map
		Map<String,ConnectionBillDetailEntity> updatedWaybillMap = updateConnectionBillDto.getUpdatedWaybillMap();
		//获取被删除的流水号list
		List<HandOverBillSerialNoDetailEntity> deletedSerialNoList = updateConnectionBillDto.getDeletedSerialNoList();
		//获取原来的交接单基本信息实体
		ConnectionBillEntity oldConnectionBillEntity = connectionBillDao.queryConnectionBillByNo(connectionBillNo);
		//当前登录部门code
		String deptCode = oldConnectionBillEntity.getDepartDeptCode();
		nowConnectionBillEntity.setDepartDeptCode(oldConnectionBillEntity.getDepartDeptCode());
		if(oldConnectionBillEntity.getStatu()!=LoadConstants.HANDOVERBILL_STATE_FORMAL_HANDOVER){
			 throw new BusinessException("该交接单状态不是已交接，不可修改"); 
		}
		//将待删除的运单map转化为list
		List<ConnectionBillDetailEntity> deletedWaybillList = convertMap2List(nowConnectionBillEntity,deletedWaybillMap);
		//将待更新的运单map转化为list
		List<ConnectionBillDetailEntity> updatedWaybillList = convertMap2List(nowConnectionBillEntity,updatedWaybillMap);
		//获取修改后的所有运单List
	
		
		//需要重新入库删除的流水号  deletedSerialNoList
		for(HandOverBillSerialNoDetailEntity serialNo : deletedSerialNoList){
			InOutStockEntity inStockEntity = new InOutStockEntity();
			//运单号
			inStockEntity.setWaybillNO(serialNo.getWaybillNo());
			//流水号
			inStockEntity.setSerialNO(serialNo.getSerialNo());
			//入库类型
			inStockEntity.setInOutStockType(StockConstants.MODIFY_HANDOVERBILL_DELETE_SERIALNO);
			//操作人code
			inStockEntity.setOperatorCode(userCode);
			//操作人name
			inStockEntity.setOperatorName(userName);
			//部门code
			inStockEntity.setOrgCode(deptCode);
			//调用库存服务，重新在本部门入库
			stockService.inStockPC(inStockEntity);
		}
		
		connectionBillDao.updateConnectionBill(nowConnectionBillEntity, 
				deletedWaybillList,
				updatedWaybillList, 
				deletedSerialNoList);
	}
	
	

	/**
	 * 私有方法，为更新后的交接单基本信息补充属性
	 * @author 205109-foss-zenghaibin
	 * @date 2012-11-1 下午10:09:54
	 * @param nowConnectionBillEntity
	 * @param updateConnectionBillDto
	 * @return void
	 * @exception 无
	 */
	private void addFieldsValueForConnectionBill(ConnectionBillEntity nowConnectionBillEntity,UpdateConnectionBillDto updateConnectionBillDto){
		//为nowHandOverBillEntity设置属性值，重量体积等，修改日期 TODO 转换快递体积
		CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
		//修改日期
		nowConnectionBillEntity.setModifyDate(new Date());
		//修改人
		nowConnectionBillEntity.setModifyUser(currentInfo.getEmpCode());
		//修改人code
		nowConnectionBillEntity.setModifyUserCode(currentInfo.getEmpCode());
		//修改人name
		nowConnectionBillEntity.setModifyUserName(currentInfo.getEmpName());
		//总重量
		nowConnectionBillEntity.setWeightTotal(updateConnectionBillDto.getTotalWeight());
		//总体积
		nowConnectionBillEntity.setVolumeTotal(updateConnectionBillDto.getTotalCubage());
		//总票数
		nowConnectionBillEntity.setWaybillQtyTotal(updateConnectionBillDto.getTotalCount());
		//总件数
		nowConnectionBillEntity.setGoodsQtyTotal(updateConnectionBillDto.getTotalPieces());
	}
	
	/**
	 * 私有方法，将运单信息Map转化为List，同时补充字段信息
	 * @author 205109-foss-zenghaibin
	 * @param nowConnectionBillEntity
	 * @param map
	 * @return List<ConnectionBillDetailEntity>
	 * @exception 无
	 * @date 2015-05-13 上午11:04:54
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List<ConnectionBillDetailEntity> convertMap2List(ConnectionBillEntity nowConnectionBillEntity,Map<String,ConnectionBillDetailEntity> map){
		//获取交接单号
		String connectionBillNo = nowConnectionBillEntity.getConnectionBillNo();
		List<ConnectionBillDetailEntity> list = new ArrayList<ConnectionBillDetailEntity>();
		if(map != null && map.size() != 0){
			Set entrySet = map.entrySet();
			Iterator iterator = entrySet.iterator();
			//遍历map，补充属性值，转化为list
			while(iterator.hasNext()) {
				Map.Entry<String,ConnectionBillDetailEntity> entry = (Map.Entry<String,ConnectionBillDetailEntity>)iterator.next();//key
				ConnectionBillDetailEntity value = entry.getValue();//value
				//交接单号
				value.setConnectionBillNo(connectionBillNo);
				//设置出发部门编码
				value.setOrigOrgCode(nowConnectionBillEntity.getDepartDeptCode());
				//设置ID
				value.setId(UUIDUtils.getUUID());
				//设置交接时间
				value.setCreateDate(nowConnectionBillEntity.getHandOverTime());
				//设置修改时间
				value.setModifyDate(new Date());
				//币种
				value.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
				//交接类型
				value.setHandOverType(nowConnectionBillEntity.getHandOverType());
				list.add(value);
			} 
		}
		return list;
	}
	
  /**
	 *作废交接单
	 * @author 205109-foss-zenghaibin
	 * @date 2015-04-09 10:09:30
	 ***/
	@Transactional
	@Override
	public void cancelConnectionBillbyNo(String connectionBillNo){
		
		/**
		 *1.非pda 
		 * 2.出发部门是本部门
		 * 3.状态为已交接
		 * 满足以上条件才能作废
		 ***/
	
		LOGGER.error("开始作废交接单：" + connectionBillNo);
		//获取当前部门code，用户姓名、code
//		String deptCode = FossUserContext.getCurrentInfo().getCurrentDeptCode();
		String userName = FossUserContext.getCurrentInfo().getEmpName();
		String userCode = FossUserContext.getCurrentInfo().getEmpCode();
				
		//获取要作废的交接单实体
		ConnectionBillEntity billEntity = this.queryConnectionBillByNo(connectionBillNo);
		//获取交接单下所有的流水号
		List<HandOverBillSerialNoDetailEntity> serialNoList = this.queryWaybillDetailByNos( connectionBillNo, null);
		//流水号空判断
		if(CollectionUtils.isEmpty(serialNoList)){
			throw new TfrBusinessException("交接单流水号为空，无法作废！");
		}
		
		if(billEntity.getStatu()!=LoadConstants.HANDOVERBILL_STATE_FORMAL_HANDOVER){
			throw new TfrBusinessException("该交接单状态不是已交接，无法作废！");
		}else if(StringUtils.equals(billEntity.getIsPda(), "Y")){
			throw new TfrBusinessException("该交接单为pda创建，无法作废！");
		}
		LOGGER.error("作废交接单（" + connectionBillNo + "）时获取交接单的流水号个数为：" + serialNoList.size());
		
		//更新交接单状态为已作废
		connectionBillDao.updateConnectionBillStateByNo(connectionBillNo, LoadConstants.HANDOVERBILL_STATE_ALREADY_CANCEL);
		//入库
		for(HandOverBillSerialNoDetailEntity serialNo : serialNoList){
				//在本部门入库所有流水号
				InOutStockEntity inStockEntity = new InOutStockEntity();
				//运单号
				inStockEntity.setWaybillNO(serialNo.getWaybillNo());
				//流水号
				inStockEntity.setSerialNO(serialNo.getSerialNo());
				//部门code
				inStockEntity.setOrgCode(billEntity.getDepartDeptCode());
				//操作人code
				inStockEntity.setOperatorCode(userCode);
				//操作人name
				inStockEntity.setOperatorName(userName);
				//交接单作废入库
				inStockEntity.setInOutStockType(StockConstants.CANCEL_HANDOVERBILL);
				//入库
				LOGGER.error("作废交接单：" + connectionBillNo + "入库开始，运单号：" + serialNo.getWaybillNo() + "，流水号：" + serialNo.getSerialNo());
				stockService.inStockPC(inStockEntity);
				LOGGER.error("作废交接单：" + connectionBillNo + "入库结束，运单号：" + serialNo.getWaybillNo() + "，流水号：" + serialNo.getSerialNo());
			}
		/**
		 * PC端新增包，出现的问题，需要在作废包交接单的时候，将包的状态变成未交接
		 */
		
		LOGGER.error("作废交接单完毕：" + connectionBillNo);
	
	}
	
	/**
	 * 将文件名转成UTF-8编码以防止乱码
	 * @param 文件名
	 * @author 205109-foss-zenghaibin
	 * @date 2015-01-23 上午8:59:05
	 */
	@Override
	public String encodeFileName(String fileName) {
		try {
			return URLEncoder.encode(fileName, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			LOGGER.error("将文件名转成UTF-8编码时出错");
			throw new TfrBusinessException("将文件名转成UTF-8编码时出错","");
		}
	}
	

	/**
	 * 接驳交接单导出
	 * @param connectionBillVo
	 * @author 205109-foss-zenghaibin
	 * @date 2015-05-14 上午8:59:05
	 */
	public InputStream exportConnectionBillExcel(ConnectionBillVo connectionBillVo){
		

		
		InputStream excelStream = null;
		if(null==connectionBillVo){
			throw new TfrBusinessException("参数为空");
		}
		List<ConnectionBillEntity> connectionBillEntityList =this.queryConnectionBillList(connectionBillVo,Integer.MAX_VALUE,0);
		//行List
		List<List<String>> rowList = new ArrayList<List<String>>();
		for(ConnectionBillEntity connectionBillEntity : connectionBillEntityList){
			//每行的列List
			List<String> columnList = new ArrayList<String>();
			//交接单编号
			columnList.add(connectionBillEntity.getConnectionBillNo());
			//状态
			//交接日期
			if(connectionBillEntity.getStatu()==LoadConstants.HANDOVERBILL_STATE_FORMAL_HANDOVER){
				columnList.add("已交接");
			}else if(connectionBillEntity.getStatu()==LoadConstants.HANDOVERBILL_STATE_ALREADY_ARRIVE){
				columnList.add("已到达");
			}else if(connectionBillEntity.getStatu()==LoadConstants.HANDOVERBILL_STATE_ALREADY_INSTOCK){
				columnList.add("已入库");
			}
			
			//交接类型                                                                                                                                                                      EXPRESS_CONNECTION_HANDOVER
			if(connectionBillEntity.getHandOverType()==null){
				columnList.add("");
			}else if(StringUtils.equals(connectionBillEntity.getHandOverType(), "EXPRESS_CONNECTION_HANDOVER")){
				columnList.add("接驳交接单");
			}else{
				columnList.add(connectionBillEntity.getHandOverType());
			}
			//交接日期
			if(connectionBillEntity.getHandOverTime()!=null){
				columnList.add(DateUtils.convert(connectionBillEntity.getHandOverTime()));
			}else{
				columnList.add("");
			}
			//车牌号
			if(connectionBillEntity.getVehicleNo()!=null){
				columnList.add(connectionBillEntity.getVehicleNo());
			}else{
				columnList.add("");
			}
			//出发部门
			if(connectionBillEntity.getDepartDeptName()!=null){
				columnList.add(connectionBillEntity.getDepartDeptName());
			}else{
				columnList.add("");
			}
			//到达接驳点
			if(connectionBillEntity.getArriveDeptName()!=null){
				columnList.add(connectionBillEntity.getArriveDeptName());
			}else{
				columnList.add("");
			}
			//到达时间
			if(connectionBillEntity.getArriveTime()!=null){
				columnList.add(DateUtils.convert(connectionBillEntity.getArriveTime()));
			}else{
				columnList.add("");
			}
			//总票数
			if(connectionBillEntity.getWaybillQtyTotal()!=null){
				columnList.add(connectionBillEntity.getWaybillQtyTotal()+"");
			}else{
				columnList.add("");
			}
			//总件数
			if(connectionBillEntity.getGoodsQtyTotal()!=null){
				columnList.add(connectionBillEntity.getGoodsQtyTotal()+"");
			}else{
				columnList.add("");
			}
			//总重量
			if(connectionBillEntity.getWeightTotal()!=null){
				columnList.add(connectionBillEntity.getWeightTotal()+"");
			}else{
				columnList.add("");
			}
			//总体积
			if(connectionBillEntity.getVolumeTotal()!=null){
				columnList.add(connectionBillEntity.getVolumeTotal()+"");
			}else{
				columnList.add("");
			}
			//制单人
			if(connectionBillEntity.getCreateUserName()!=null){
				columnList.add(connectionBillEntity.getCreateUserName()+"");
			}else{
				columnList.add("");
			}
			//司机
			if(connectionBillEntity.getDriverName()!=null){
				columnList.add(connectionBillEntity.getDriverName()+"");
			}else{
				columnList.add("");
			}
			
			rowList.add(columnList);
		}
		/**
		 *交接单编号，状态，交接类型，交接日期，车牌号，出发部门，出发时间 ，到达接驳点，到达时间，、总票数、总件数、总重量、总体积、制单人、司机 
		 * **/
		String[] rowHeads = {"交接单编号","状态","交接类型","交接日期","车牌号","出发部门","到达接驳点","到达时间","总票数",
				"总件数","总重量","总体积","制单人","司机"};//定义表头
		
		ExportSetting exportSetting = new ExportSetting();
		exportSetting.setSheetName("接驳交接单");
		exportSetting.setSize(LoadConstants.SONAR_NUMBER_5000);
		ExportResource exportResource  = new ExportResource();
		exportResource.setHeads(rowHeads);
		exportResource.setRowList(rowList);
		ExporterExecutor objExporterExecutor = new ExporterExecutor();
		excelStream = objExporterExecutor.exportSync(exportResource, exportSetting);
		
		return excelStream;
	
	}
	
	/**
	 * 接驳交接单导出
	 * @param 
	 * @author 205109-foss-zenghaibin
	 * @date 2015-05-14 上午8:59:05
	 */
	public InputStream exportConnectionDetailExcel(String  connectionBillNo){
		
		InputStream excelStream = null;
		if(StringUtils.isBlank(connectionBillNo)){
			throw new TfrBusinessException("交接单号为空");
		}
		List<ConnectionBillDetailEntity> entityList= this.queryConnectionBillDetailByNo(connectionBillNo);
		List<List<String>> rowList = new ArrayList<List<String>>();
		for(ConnectionBillDetailEntity connectionBillDetailEntity : entityList){
			//每行的列List
			List<String> columnList = new ArrayList<String>();
			//运单号
			columnList.add(connectionBillDetailEntity.getWaybillNo());
			//运输性质
			if(StringUtils.isNotBlank(connectionBillDetailEntity.getTransProperty())){
				columnList.add(connectionBillDetailEntity.getTransProperty());
			}else{
				columnList.add("");
			}
			//已配件数
			if(connectionBillDetailEntity.getPieces()!=null){
				columnList.add(connectionBillDetailEntity.getPieces()+"");
			}else{
				columnList.add("");
			}
			//已配重量
			if(connectionBillDetailEntity.getWeight()!=null){
				columnList.add(connectionBillDetailEntity.getWeight()+"");
			}else{
				columnList.add("");
			}
			//已配体积
			if(connectionBillDetailEntity.getCubage()!=null){
				columnList.add(connectionBillDetailEntity.getCubage()+"");
			}else{
				columnList.add("");
			}
			//备注
			if(connectionBillDetailEntity.getNote()!=null){
				columnList.add(connectionBillDetailEntity.getNote()+"");
			}else{
				columnList.add("");
			}
			//货物名称
			if(connectionBillDetailEntity.getGoodsName()!=null){
				columnList.add(connectionBillDetailEntity.getGoodsName()+"");
			}else{
				columnList.add("");
			}
			//货物名称
			if(connectionBillDetailEntity.getPacking()!=null){
				columnList.add(connectionBillDetailEntity.getPacking()+"");
			}else{
				columnList.add("");
			}
			//运单备注
			if(connectionBillDetailEntity.getWaybillNote()!=null){
				columnList.add(connectionBillDetailEntity.getWaybillNote()+"");
			}else{
				columnList.add("");
			}
			rowList.add(columnList);
		}
		String[] rowHeads = {"运单号","运输性质","已配件数","已配重量","已配体积","备注","货物名称","包装","运单备注"};//定义表头
		
		ExportSetting exportSetting = new ExportSetting();
		exportSetting.setSheetName("接驳交接单明细");
		exportSetting.setSize(LoadConstants.SONAR_NUMBER_5000);
		ExportResource exportResource  = new ExportResource();
		exportResource.setHeads(rowHeads);
		exportResource.setRowList(rowList);
		ExporterExecutor objExporterExecutor = new ExporterExecutor();
		excelStream = objExporterExecutor.exportSync(exportResource, exportSetting);
		
		
		return excelStream;
		
	}
	
	/**
	 * 根据接驳交接单号查询出打印清单中需要的数据 
	 * @author zenghaibin-foss-205109
	 * @param connectionBillNo
	 * @return List<connectionBillNo>
	 * @exception 无
	 * @date 2015-05-29 上午9:33:17
	 */
	@Override
	public List<HandOverBillDetailDto> queryPrintConnectionBillDataByNo(String connectionBillNo) {
		/**
		 * 调用dao，返回查询结果
		 */
		return connectionBillDao.queryPrintConnectionBillDataByNo(connectionBillNo);
	}

	/**
	 * 根据接驳交接单号查询出打印清单中需要的数据 
	 * @author zenghaibin-foss-205109
	 * @param connectionBillNo
	 * @return String
	 * @exception 无
	 * @date 2015-05-29 上午9:33:17
	 */
	@Override
	public List<String> queryConnectionBillNoForUnloadTaskLackGoods(QueryHandOverBillNoForUnloadTaskLackGoodsDto queryDto){
		
		return  connectionBillDao.queryConnectionBillNoForUnloadTaskLackGoods(queryDto);
	}

	
	
	public void setConnectionBillDao(IConnectionBillDao connectionBillDao) {
		this.connectionBillDao = connectionBillDao;
	}

	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

	public void setTfrCommonService(ITfrCommonService tfrCommonService) {
		this.tfrCommonService = tfrCommonService;
	}

	public void setStockService(IStockService stockService) {
		this.stockService = stockService;
	}

	public void setAcceptPointSalesDeptService(
			IAcceptPointSalesDeptService acceptPointSalesDeptService) {
		this.acceptPointSalesDeptService = acceptPointSalesDeptService;
	}

	public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}

	public void setMotorcadeService(IMotorcadeService motorcadeService) {
		this.motorcadeService = motorcadeService;
	}


	
}
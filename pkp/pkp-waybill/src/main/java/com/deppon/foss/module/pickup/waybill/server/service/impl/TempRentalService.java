package com.deppon.foss.module.pickup.waybill.server.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.module.base.baseinfo.api.server.service.IMotorcadeService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISaleDepartmentService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISalesMotorcadeService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.MotorcadeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SalesMotorcadeEntity;
import com.deppon.foss.module.pickup.waybill.api.server.dao.ITempRentalDao;
import com.deppon.foss.module.pickup.waybill.api.server.service.ITempRentalService;
import com.deppon.foss.module.pickup.waybill.shared.dto.TempRentalQueryDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.RentalMarkEntity;
import com.deppon.foss.util.define.FossConstants;
/**
 * 
 * 临时租车查询
 * 
 * @author HeHaiSu
 * @date 2014-07-24 上午9:51:00
 */
public class TempRentalService implements ITempRentalService {

	/**
	 * 日志
	 */
	protected final static Logger logger = LoggerFactory.getLogger(TempRentalService.class.getName());

	private final static String YES = "Y";
	/**
	 * 查询角色type 1：营业部 2：驻地营业部 3：车队 4:外场
	 */
	private final static int ONE = 1;
	private final static int TWO = 2;
	private final static int THREE = 3;
	private final static int FOUR = 4;
	/**
	 * 单据类型： 1运单 2派送单
	 */
	private final static String BILL_TYPE_WAYBILL = "WAYBILL";
	private final static String BILL_TYPE_DELIVERBILL = "DELIVERBILL";
	/**
	 * 查询类型:QBD：按时间段查询 QBB：按单号查询
	 */
	private final static String QUERY_TYPE_QBD = "QBD";
	private final static String QUERY_TYPE_QBB = "QBB";
	/**
	 * 不作分页时参数
	 */
	private final static int NO_ROW_OFFSET = 0;
	private final static int NO_ROW_LIMIT = Integer.MAX_VALUE;
	/**
	 * 组织机构查询
	 */
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	/**
	 * 营业部查询
	 */
	private ISaleDepartmentService saleDepartmentService;
	/**
	 * 组织机构复杂查询
	 */
	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;
	/**
	 * 营业部车队关系 Service接口
	 */
	private ISalesMotorcadeService salesMotorcadeService;
	/**
	 * 车队查询
	 */
	private IMotorcadeService motorcadeService;
	/**
	 * 临时租车查询Dao
	 */
	private ITempRentalDao tempRentalDao;
	
	
	/**
	 * 查询快递运单号信息
	 * 运单、派送单
	 * @param billType  WAYBILL:按运单号查  
	 * @return
	 */
	public List<RentalMarkEntity>  searchTWaybill(TempRentalQueryDto tempRentalQueryDto, String billType){
/*		try {
		Map<Object, Object> maps = new HashMap<Object, Object>();
		String[] str1 = arrvRegionIdSet.split(","); 
		List<String> idLongs = new ArrayList<String>(); 
		for(String list : str1){  
            String waybillno = list;  
             
        
		WaybillDto waybill = waybillManagerService.queryWaybillByNo(waybillno);
		
		// 运单信息表中未找到
		if (waybill == null || waybill.getWaybillEntity() == null) {
			// 检查是未补录
			WaybillPendingEntity pendingEntity = waybillPendingDao.queryByWaybillNumber(waybillno);
			if (pendingEntity == null) {
				*//**
				 * 运单号不存在！
				 *//*
				return null;
				//throw new WaybillImportException(WaybillImportException.WAYBILL_NUMBER_NOT_EXIST);
			} else {
				*//**
				 * 运单未补录！
				 *//*
				return null;
				//throw new WaybillImportException("");
			}
		}
		if (WaybillConstants.WAYBILL_STATUS_PDA_PENDING.equals(waybill.getWaybillEntity().getPendingType())) {
			*//**
			 * 运单未补录！
			 *//*
			return null;
			//throw new WaybillImportException("");
		}
		if (waybill.getActualFreightEntity().getStatus()=="OBSOLETE") {
			*//**
			 * 运单已作废！
			 *//*
			return null;
		//	throw new WaybillImportException("");
		}
		
		List<String> arrvRegionIdList = new ArrayList<String>();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		Date createTime = df.parse("2004-03-26 13:31:40");
		Date nowTime=df.parse(df.format(new Date()));
		long diff = nowTime.getTime() - createTime.getTime();
		long days = diff / (1000 * 60 * 60 * 24);
		if((diff-days*(1000 * 60 * 60 * 24))/(1000* 60 * 60)>0){
			days=days+1;
		}
		if(days<=20){
		int type = (Integer) maps.get("type");
		if(type==1|type==2|type==3|type==4){
			List<RentalMarkEntity> publishPriceDtos = tempRentalDao.querySaleBillByBill(waybillno);
		}
		}}
//		DateTime b = Convert.ToDateTime("1984-12-17 08:03:35");  
//		DateTime c = Convert.ToDateTime("1984-12-17 08:05:54");
//		TimeSpan ts=b-c;
//		ts.TotalDays
//		Ts.Days
//		ts.Hours
		return null;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/

		Map<Object, Object> maps = new HashMap<Object, Object>();
		String orgCode = tempRentalQueryDto.getOrgCode();
		int start = tempRentalQueryDto.getStart();
		int limit = tempRentalQueryDto.getLimit();
		if (limit <= 0)
			tempRentalQueryDto.setLimit(NumberConstants.NUMBER_50);
		
		if (StringUtils.isBlank(orgCode))
			return null;
		
		if (BILL_TYPE_WAYBILL.equals(billType)) {
			
			getTypeWaybill(maps, tempRentalQueryDto, billType);
			
			if (maps.isEmpty())
				return null;
			int type = (Integer) maps.get("type");
		 if (QUERY_TYPE_QBB.equals(tempRentalQueryDto.getQueryType()) //按单号查询运单
					&& !tempRentalQueryDto.getExpressWaybillNos().isEmpty() 
					&& tempRentalQueryDto.getExpressWaybillNos().size() > 0) {
				if (type == 1 || type == 2 || type == THREE || type == FOUR) {//营业部
					return tempRentalDao.querySaleBillByBill(maps, start, limit);
				}else {
					return null;
				}
			}
		} else if (BILL_TYPE_DELIVERBILL.equals(billType)) {
			
			getType(maps, tempRentalQueryDto, billType);
			
			if (maps.isEmpty())
				return null;
			int dtype = (Integer) maps.get("type");
			//按时间段查询派送单
			if (QUERY_TYPE_QBD.equals(tempRentalQueryDto.getQueryType())) {
				if (dtype == 1 || dtype == 2) {//营业部或驻地营业部
					return tempRentalDao.querySaleDepartTempRentalDeliverBillByDate(maps, start, limit);
				} else if (dtype == THREE || dtype == FOUR) {//车队或上级部门为车队
					return tempRentalDao.queryMotorcadeTempRentalDeliverBillByDate(maps, start, limit);
				} else {
					return null;
				}
			} else if (QUERY_TYPE_QBB.equals(tempRentalQueryDto.getQueryType())
						&& !tempRentalQueryDto.getDeliverbillNos().isEmpty() 
						&& tempRentalQueryDto.getDeliverbillNos().size() > 0) {//按单号查询派送单
				if (dtype == 1 || dtype == 2) {//营业部或驻地营业部
					return tempRentalDao.querySaleDepartTempRentalDeliverBillByBillNos(maps, start, limit);
				} else if (dtype == THREE || dtype == FOUR) {//车队或上级部门为车队
					return tempRentalDao.queryMotorcadeTempRentalDeliverBillByBillNos(maps, start, limit);
				} else {
					return null;
				}
			} else {
				return null;
			}
		}
		return null;
		
	}
	
	
	
	/**
	 * 查询临时租车信息
	 * 运单、派送单
	 * @param TempRentalQueryDto
	 * @param billType  WAYBILL:按运单号查     DELIVERBILL:按派送单查
	 * @return
	 */
	public List<RentalMarkEntity> searchTempRental (
			TempRentalQueryDto tempRentalQueryDto, String billType) {
		
		Map<Object, Object> maps = new HashMap<Object, Object>();
		String orgCode = tempRentalQueryDto.getOrgCode();
		int start = tempRentalQueryDto.getStart();
		int limit = tempRentalQueryDto.getLimit();
		if (limit <= 0)
			tempRentalQueryDto.setLimit(NumberConstants.NUMBER_50);
		
		if (StringUtils.isBlank(orgCode))
			return null;
		
		if (BILL_TYPE_WAYBILL.equals(billType)) {
			
			getType(maps, tempRentalQueryDto, billType);
			
			if (maps.isEmpty())
				return null;
			int type = (Integer) maps.get("type");
			//按时间段查询运单
			if (QUERY_TYPE_QBD.equals(tempRentalQueryDto.getQueryType())) {
				if (type == 1) {
					return tempRentalDao.querySaleDepartTempRentalWayBillByDate(maps, start, limit);
				} else if (type == 2) {
					return tempRentalDao.queryStationSaleDepartTempRentalWayBillByDate(maps, start, limit);
				} else if (type == THREE || type == FOUR) {
					return tempRentalDao.queryMotorcadeTempRentalWayBillByDate(maps, start, limit);
				} else {
					return null;
				}
			} else if (QUERY_TYPE_QBB.equals(tempRentalQueryDto.getQueryType()) //按单号查询派送单
					&& !tempRentalQueryDto.getWaybillNos().isEmpty() 
					&& tempRentalQueryDto.getWaybillNos().size() > 0) {
				
				if (type == 1) {//营业部
					return tempRentalDao.querySaleDepartTempRentalWayBillByBillNos(maps, start, limit);
				} else if (type == 2) {//驻地部门
					return tempRentalDao.queryStationSaleDepartTempRentalWayBillByBillNos(maps, start, limit);
				} else if (type == THREE || type == FOUR) {//车队或上级部门为车队
					return tempRentalDao.queryMotorcadeTempRentalWayBillByBillNos(maps, start, limit);
				} else {
					return null;
				}
			}
		} else if (BILL_TYPE_DELIVERBILL.equals(billType)) {
			
			getType(maps, tempRentalQueryDto, billType);
			
			if (maps.isEmpty())
				return null;
			int dtype = (Integer) maps.get("type");
			//按时间段查询派送单
			if (QUERY_TYPE_QBD.equals(tempRentalQueryDto.getQueryType())) {
				if (dtype == 1 || dtype == 2) {//营业部或驻地营业部
					return tempRentalDao.querySaleDepartTempRentalDeliverBillByDate(maps, start, limit);
				} else if (dtype == THREE|| dtype == FOUR) {//车队或上级部门为车队
					return tempRentalDao.queryMotorcadeTempRentalDeliverBillByDate(maps, start, limit);
				} else {
					return null;
				}
			} else if (QUERY_TYPE_QBB.equals(tempRentalQueryDto.getQueryType())
						&& !tempRentalQueryDto.getDeliverbillNos().isEmpty() 
						&& tempRentalQueryDto.getDeliverbillNos().size() > 0) {//按单号查询派送单
				if (dtype == 1 || dtype == 2) {//营业部或驻地营业部
					return tempRentalDao.querySaleDepartTempRentalDeliverBillByBillNos(maps, start, limit);
				} else if (dtype == THREE || dtype == FOUR) {//车队或上级部门为车队
					return tempRentalDao.queryMotorcadeTempRentalDeliverBillByBillNos(maps, start, limit);
				} else {
					return null;
				}
			} else {
				return null;
			}
		}
		return null;
		
	}
	
	/**
	 * 获取操作者部门的数据权限类别，即：营业部、驻地营业部、车队（及下属部门）、外场（及下属部门）所以查询的数据不一样
	 * @param maps
	 * @param tempRentalQueryDto
	 * @param billType
	 */
	private void getTypeWaybill (Map<Object, Object> maps, TempRentalQueryDto tempRentalQueryDto, String billType) {
		String orgCode = tempRentalQueryDto.getOrgCode();
		Date now = Calendar.getInstance().getTime();
		int limit = tempRentalQueryDto.getLimit();
		if (limit <= 0)
			tempRentalQueryDto.setLimit(NumberConstants.NUMBER_50);
		
		maps.put("currentOrg", orgCode);
		
		if (QUERY_TYPE_QBD.equals(tempRentalQueryDto.getQueryType())) {
			if (tempRentalQueryDto.getStartTime() != null) {
				maps.put("startTime", tempRentalQueryDto.getStartTime());
			} else {
				maps.put("startTime", getNextDay(now));//为空则置为系统日期的前一天
			}
				
			if (tempRentalQueryDto.getEndTime() != null) {
				maps.put("endTime", tempRentalQueryDto.getEndTime());
			} else {
				maps.put("endTime", now);//为空则置为系统日期
			}
			
			if (StringUtils.isNotBlank(tempRentalQueryDto.getTemprentalMarkNo())) {
				maps.put("TemprentalMarkNo", tempRentalQueryDto.getTemprentalMarkNo());
			}
				
			if (StringUtils.isNotBlank(tempRentalQueryDto.getVehicleNo()))
				maps.put("vehicleNo", tempRentalQueryDto.getVehicleNo());
			
			if (StringUtils.isNotBlank(tempRentalQueryDto.getCreateOrgCode()))
				maps.put("createOrgCode", tempRentalQueryDto.getCreateOrgCode());
		}
		
		if (BILL_TYPE_WAYBILL.equals(billType)) {
			
			deal(tempRentalQueryDto,maps,orgCode);
			
			if (maps.isEmpty())
				return;
			
			//运单
			maps.put("billList", tempRentalQueryDto.getExpressWaybillNos());
			
		}else {
			maps.clear();
		}
	}
	
	
	
	
	/**
	 * 获取操作者部门的数据权限类别，即：营业部、驻地营业部、车队（及下属部门）、外场（及下属部门）所以查询的数据权限不一样
	 * @param maps
	 * @param tempRentalQueryDto
	 * @param billType
	 */
	private void getType (Map<Object, Object> maps, TempRentalQueryDto tempRentalQueryDto, String billType) {
		String orgCode = tempRentalQueryDto.getOrgCode();
		Date now = Calendar.getInstance().getTime();
		int limit = tempRentalQueryDto.getLimit();
		if (limit <= 0)
			tempRentalQueryDto.setLimit(NumberConstants.NUMBER_50);
		
		maps.put("currentOrg", orgCode);
		
		if (QUERY_TYPE_QBD.equals(tempRentalQueryDto.getQueryType())) {
			if (tempRentalQueryDto.getStartTime() != null) {
				maps.put("startTime", tempRentalQueryDto.getStartTime());
			} else {
				maps.put("startTime", getNextDay(now));//为空则置为系统日期的前一天
			}
				
			if (tempRentalQueryDto.getEndTime() != null) {
				maps.put("endTime", tempRentalQueryDto.getEndTime());
			} else {
				maps.put("endTime", now);//为空则置为系统日期
			}
			
			if (StringUtils.isNotBlank(tempRentalQueryDto.getTemprentalMarkNo())) {
				maps.put("TemprentalMarkNo", tempRentalQueryDto.getTemprentalMarkNo());
			}
				
			if (StringUtils.isNotBlank(tempRentalQueryDto.getVehicleNo()))
				maps.put("vehicleNo", tempRentalQueryDto.getVehicleNo());
			
			if (StringUtils.isNotBlank(tempRentalQueryDto.getCreateOrgCode()))
				maps.put("createOrgCode", tempRentalQueryDto.getCreateOrgCode());
		}
		
		if (BILL_TYPE_WAYBILL.equals(billType)) {
			
			deal(tempRentalQueryDto,maps,orgCode);
			
			if (maps.isEmpty())
				return;
			
			//运单
			maps.put("billList", tempRentalQueryDto.getWaybillNos());
			
		} else if (BILL_TYPE_DELIVERBILL.equals(billType)) {
			
			deal(tempRentalQueryDto,maps,orgCode);
			
			if (maps.isEmpty())
				return;
			//派送单
			maps.put("billList", tempRentalQueryDto.getDeliverbillNos());
		} else {
			maps.clear();
		}
	}
	
	
	
	private void deal (TempRentalQueryDto tempRentalQueryDto, Map<Object, Object> maps, String orgCode) {
		List<String> orgs = new ArrayList<String>();
		
		OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = 
				orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(orgCode);
		if (orgAdministrativeInfoEntity == null) {
			maps.clear();
			return;
		}
		maps.put("entity", orgAdministrativeInfoEntity);//不做数据库查询参数用
		//登陆部门为营业部
		if (orgAdministrativeInfoEntity.checkSaleDepartment()) {
			maps.put("type", ONE);
			//是否驻地营业部
			SaleDepartmentEntity saleDepartmentEntity = 
					saleDepartmentService.querySimpleSaleDepartmentByCode(orgCode);
			
			if (saleDepartmentEntity != null && YES.equals(saleDepartmentEntity.getStation()) 
					&& YES.equals(saleDepartmentEntity.getActive())) {//登陆部门为驻地营业部
				maps.put("type", TWO);
			}
		} else if (orgAdministrativeInfoEntity.checkTransDepartment() 
					|| orgAdministrativeInfoEntity.checkTransferCenter()) {//登陆部门为车队或者外场
			this.dealMotorcadeAndTransDepart(orgAdministrativeInfoEntity, maps, orgs);
		} else {//登陆部门为车队或外场的下属部门，如果都不是则不进行查询
			
			List<String> bizTypes =new ArrayList<String>();
            bizTypes.add("TRANS_DEPARTMENT");
            //查询所属上级部门是否是个车队
            OrgAdministrativeInfoEntity superOrg =orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoIncludeSelfByCode(orgCode, bizTypes);
			if (superOrg == null) {
				bizTypes.clear();
				bizTypes.add("TRANSFER_CENTER");
				superOrg =orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoIncludeSelfByCode(orgCode, bizTypes);
				if (superOrg == null) {
					maps.clear();
					return;
				}
			}
			maps.put("parentEntity", superOrg);//不做数据库查询参数用
			
			if (superOrg.checkTransDepartment() 
					|| superOrg.checkTransferCenter()) {
				this.dealMotorcadeAndTransDepart(superOrg, maps, orgs);
			} else {
				//该登陆部门无权限操作
				maps.clear();
			}
		}
	}
	
	/**
	 * 处理车队与外场
	 * @param orgEntity
	 * @param maps
	 * @param orgs
	 */
	private void dealMotorcadeAndTransDepart (OrgAdministrativeInfoEntity orgEntity,Map<Object, Object> maps, List<String> orgs) {
		if (orgEntity.checkTransDepartment()) {//车队
			
			//1.查询车队的顶级车队所管辖的营业部（包括其本身）
			//首先查询本身是否顶级车队
			MotorcadeEntity motorcade = motorcadeService.queryMotorcadeByCode(orgEntity.getCode());
			OrgAdministrativeInfoEntity topFleet = new OrgAdministrativeInfoEntity();
			if (motorcade != null) {
				//如果本身就是顶级车队
				if (StringUtils.equals(motorcade.getIsTopFleet(), FossConstants.YES)) {
					topFleet.setCode(motorcade.getCode());
				} else {
					//--查询顶级车队
					topFleet = orgAdministrativeInfoComplexService.getTopFleetByCode(orgEntity.getCode());
					if (topFleet == null) {
						maps.clear();
						return;
					}
				}
			} else {
				//--查询顶级车队
				topFleet = orgAdministrativeInfoComplexService.getTopFleetByCode(orgEntity.getCode());
				if (topFleet == null) {
					maps.clear();
					return;
				}
			}
			
			//--查询顶级车队所管辖的营业部（包括其本身）
			SalesMotorcadeEntity topEntity = new SalesMotorcadeEntity();
			topEntity.setMotorcadeCode(topFleet.getCode());
			//查询顶级车队所管辖的营业部
			List<SalesMotorcadeEntity> salesDeparts = salesMotorcadeService.querySalesMotorcadeExactByEntity(topEntity, NO_ROW_OFFSET, NO_ROW_LIMIT);
			if (salesDeparts.isEmpty() || salesDeparts.size() == 0) {
				maps.clear();
				return;
			}
			//--所有营业部
			orgs = getSalesDepartCodes(salesDeparts);
			//--把顶级车队加进去
			orgs.add(topFleet.getCode());
			
			//2.查询当前车队所有子部门的数据
			List<OrgAdministrativeInfoEntity> allSubDeparts = orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoEntityAllSubByCode (topFleet.getCode());
			
			if (allSubDeparts != null && allSubDeparts.size() > 0) {
				List<String> allSubOrgIds = getOrgAdministrativeInfoCodes(allSubDeparts);
				orgs.addAll(allSubOrgIds);
			}
			
			if (orgs.isEmpty() || orgs.size() == 0) {
				maps.clear();
				return;
			}
			maps.put("type", THREE);//登陆部门为车队
			maps.put("orgs", orgs);
		} else {//外场
			//查询当前外场所有子部门的数据
			List<OrgAdministrativeInfoEntity> allSubDeparts = orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoEntityAllSubByCode (orgEntity.getCode());
			
			if (allSubDeparts != null && allSubDeparts.size() > 0) {
				List<String> allSubOrgIds = getOrgAdministrativeInfoCodes(allSubDeparts);
				orgs.addAll(allSubOrgIds);
			}
			if (orgs.isEmpty() || orgs.size() == 0) {
				maps.clear();
				return;
			}
			maps.put("type", FOUR);//登陆部门为外场
			maps.put("orgs", orgs);
		}
	}
	
	/**
	 * 根据条件查临时租车运单总条数
	 * @param tempRentalQueryDto
	 * @return
	 */
	public Long countByWayBillParams(TempRentalQueryDto tempRentalQueryDto) {
		Map<Object, Object> args = new HashMap<Object, Object>();
		String orgCode = tempRentalQueryDto.getOrgCode();
		if (StringUtils.isBlank(orgCode))
			return 0l;
		
		getType(args, tempRentalQueryDto, BILL_TYPE_WAYBILL);
		
		if (args.isEmpty())
			return 0l;
		
		if (QUERY_TYPE_QBD.equals(tempRentalQueryDto.getQueryType())) {
			return tempRentalDao.countTempRentalWayBillByDate(args);
		} else if (QUERY_TYPE_QBB.equals(tempRentalQueryDto.getQueryType()) 
				&& !tempRentalQueryDto.getWaybillNos().isEmpty() 
				&& tempRentalQueryDto.getWaybillNos().size() > 0) {
			return tempRentalDao.countTempRentalWayBillByBillNos(args);
		} else {
			return 0L;
		}
	}
	
	/**
	 * 根据条件查临时租车运单总条数
	 * @param tempRentalQueryDto
	 * @return
	 */
	public Long countByWayBill(TempRentalQueryDto tempRentalQueryDto) {
		Map<Object, Object> args = new HashMap<Object, Object>();
		String orgCode = tempRentalQueryDto.getOrgCode();
		if (StringUtils.isBlank(orgCode))
			return 0l;
		
		getTypeWaybill(args, tempRentalQueryDto, BILL_TYPE_WAYBILL);
		
		if (args.isEmpty())
			return 0l;
		
		if (QUERY_TYPE_QBB.equals(tempRentalQueryDto.getQueryType()) 
				&& !tempRentalQueryDto.getExpressWaybillNos().isEmpty() 
				&& tempRentalQueryDto.getExpressWaybillNos().size() > 0) {
			return tempRentalDao.countWayBillByBillNos(args);
		} else {
			return 0L;
		}
	}

	/**
	 * 根据条件查临时租车派送单总条数
	 * @param tempRentalQueryDto
	 * @return
	 */
	public Long countByDeliverBillParams(TempRentalQueryDto tempRentalQueryDto) {
		Map<Object, Object> args = new HashMap<Object, Object>();
		String orgCode = tempRentalQueryDto.getOrgCode();
		if (StringUtils.isBlank(orgCode))
			return 0l;
		
		getType(args, tempRentalQueryDto, BILL_TYPE_DELIVERBILL);
		
		if (args.isEmpty())
			return 0l;
		
		if (QUERY_TYPE_QBD.equals(tempRentalQueryDto.getQueryType())) {
			return tempRentalDao.countTempRentalDeliverBillByDate(args);
		} else if (QUERY_TYPE_QBB.equals(tempRentalQueryDto.getQueryType())
				&& !tempRentalQueryDto.getDeliverbillNos().isEmpty() 
				&& tempRentalQueryDto.getDeliverbillNos().size() > 0) {
			return tempRentalDao.countTempRentalDeliverBillByBillNos(args);
		} else {
			return 0L;
		}
	}
	
	private List<String> getSalesDepartCodes(List<SalesMotorcadeEntity> entities) {
		List<String> ids = new ArrayList<String>();
		for (SalesMotorcadeEntity entity : entities) {
			if (StringUtils.isNotBlank(entity.getSalesdeptCode()))
				ids.add(entity.getSalesdeptCode());
		}
		return ids;
	}
	
	//OrgAdministrativeInfoEntity
	private List<String> getOrgAdministrativeInfoCodes(List<OrgAdministrativeInfoEntity> entities) {
		List<String> ids = new ArrayList<String>();
		for (OrgAdministrativeInfoEntity entity : entities) {
			if (StringUtils.isNotBlank(entity.getCode()))
				ids.add(entity.getCode());
		}
		return ids;
	}
	
	
	/**
	 * 获取指定日期的前一天
	 * @param date
	 * @return
	 */
	public Date getNextDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		date = calendar.getTime();
		return date;
	}
	
	
	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}


	public void setSaleDepartmentService(
			ISaleDepartmentService saleDepartmentService) {
		this.saleDepartmentService = saleDepartmentService;
	}


	public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}


	public void setSalesMotorcadeService(
			ISalesMotorcadeService salesMotorcadeService) {
		this.salesMotorcadeService = salesMotorcadeService;
	}


	public void setMotorcadeService(IMotorcadeService motorcadeService) {
		this.motorcadeService = motorcadeService;
	}


	public void setTempRentalDao(ITempRentalDao tempRentalDao) {
		this.tempRentalDao = tempRentalDao;
	}

}
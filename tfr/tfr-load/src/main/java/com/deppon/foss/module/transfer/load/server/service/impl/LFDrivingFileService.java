package com.deppon.foss.module.transfer.load.server.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IVehicleDrivingService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IVehicleService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.VehicleDrivingEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.VehicleAssociationDto;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.load.api.server.dao.ILFDrivingFileDao;
import com.deppon.foss.module.transfer.load.api.server.service.ILFDrivingFileService;
import com.deppon.foss.module.transfer.load.api.shared.define.LoadConstants;
import com.deppon.foss.module.transfer.load.api.shared.domain.LFDrivingFileDetailEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.LFDrivingFileEntity;
import com.deppon.foss.module.transfer.load.api.shared.exception.LFDrivingFileException;
import com.deppon.foss.module.transfer.load.api.shared.vo.LFDrivingFileVo;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;


/**
 * 
* @ClassName: LFDrivingFileService
* @Description:长途车辆行驶档案Service
* @author ZX_189284
* @date 2016-5-23 下午6:19:26
*
 */
public class LFDrivingFileService implements ILFDrivingFileService {
	public static final Logger LOGGER = LoggerFactory.getLogger(LFDrivingFileService.class);
	
	/**
	 * 长途车辆行驶档案Dao
	 */
   private ILFDrivingFileDao  lfDrivingFileDao;
   /**
    * 车辆信息Service
    */
   private IVehicleService vehicleService;
   /**
    * 综合长途车队信息  基础资料
    */
   private IVehicleDrivingService vehicleDrivingService;
	/**
	 * 综合长途车队信息  基础资料
	 * @param vehicleDrivingService 要设置的 vehicleDrivingService
	 */
	public void setVehicleDrivingService(
			IVehicleDrivingService vehicleDrivingService) {
		this.vehicleDrivingService = vehicleDrivingService;
	}
	/**
	 * 组织service···
	 */
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	
	/**
	 * @param orgAdministrativeInfoService 要设置的 orgAdministrativeInfoService
	 */
	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}
	/**
	 * 车辆信息Service
	 * @param vehicleService 要设置的 vehicleService
	 */
	public void setVehicleService(IVehicleService vehicleService) {
		this.vehicleService = vehicleService;
	}
	/**
	 * 长途车辆行驶档案Dao
	 * @param lfDrivingFileDao 要设置的 lfDrivingFileDao
	 */
	public void setLfDrivingFileDao(ILFDrivingFileDao lfDrivingFileDao) {
		this.lfDrivingFileDao = lfDrivingFileDao;
	}
	/**
	 * 
	* @Title: saveLFDrivingFile
	* @Description: 界面新增  保存 长途车行驶档案信息
	* @author ZX_189284
	* @param @param lfDrivingFileEntity
	* @param @param lfDrivingFileDetails    设定文件
	* @return void    返回类型
	* @throws
	 */
	@Override
	@Transactional
	public String saveLFDrivingFile(LFDrivingFileEntity lfDrivingFileEntity,List<LFDrivingFileDetailEntity> lfDrivingFileDetails){
		String drivingNo=getDrivingNo(lfDrivingFileEntity.getOrgIdCode());
		lfDrivingFileEntity.setDrivingDate(DateUtils.convert(new Date(),"yyyyMM"));
		lfDrivingFileEntity.setDrivingNo(drivingNo);
		lfDrivingFileEntity.setId(UUIDUtils.getUUID());
		lfDrivingFileEntity.setCreateTime(new Date());
		lfDrivingFileEntity.setActive(FossConstants.YES);
		lfDrivingFileEntity.setCreateUserCode(FossUserContext.getCurrentInfo().getEmpCode());
		lfDrivingFileEntity.setCreateUserName(FossUserContext.getCurrentInfo().getEmpName());
		/**
		 * 用来生成线路Code 
		 */
		ArrayList<LFDrivingFileDetailEntity> lfDrivingFileDetaillist=new ArrayList<LFDrivingFileDetailEntity>();
		for (LFDrivingFileDetailEntity lfDrivingFileDetailEntity : lfDrivingFileDetails) {
			lfDrivingFileDetailEntity.setDrivingNo(drivingNo);
			lfDrivingFileDetailEntity.setId(UUIDUtils.getUUID());
			checkLFDrivingFileDetail(lfDrivingFileDetailEntity);
			addlfDrivingFileDetail(lfDrivingFileDetailEntity);
			LFDrivingFileDetailEntity departDetail=new LFDrivingFileDetailEntity();
			departDetail.setDepartTime(lfDrivingFileDetailEntity.getDepartTime());
			departDetail.setOrigOrgCode(lfDrivingFileDetailEntity.getOrigOrgCode());
			departDetail.setOrigOrgName(lfDrivingFileDetailEntity.getOrigOrgName());
			LFDrivingFileDetailEntity arriveDetail=new LFDrivingFileDetailEntity();
			arriveDetail.setArriveTime(lfDrivingFileDetailEntity.getArriveTime());
			arriveDetail.setDestOrgCode(lfDrivingFileDetailEntity.getDestOrgCode());
			arriveDetail.setDestOrgName(lfDrivingFileDetailEntity.getDestOrgName());
			lfDrivingFileDetaillist.add(departDetail);
			lfDrivingFileDetaillist.add(arriveDetail);
		}
		
		lfDrivingFileEntity=encapsulationLine(lfDrivingFileEntity,lfDrivingFileDetaillist);
		addLFDrivingFile(lfDrivingFileEntity);
		return drivingNo;
		
	}
	/**
	 * 
	* @Title: encapsulationLine
	* @Description: 根据要排序的list 生成线路
	* @author ZX_189284
	* @param @param lineCode 线路code
	* @param @param lineName 线路名称
	* @param @param lfDrivingFileDetaillist    设定文件
	* @return LFDrivingFileEntity   
	* 将该条行车数据明细中所有配载单的“配载部门”及配载时间、
    * “到达部门”及到达时间按照时间顺序进行排序，将“配载部门”和“到达部门”用“-”按顺序连接，
    * 相邻部门为同一部门时只保留其一。
	* 例如，该行车记录详情内有两个配载单——
	* 配载单一：配载部门为上海转运场，配载时间为20160301 20:00:00，
	* 到达部门为南京转运场，到达时间为20160302 01:30:00；
	* 配载单二：配载部门为南京转运场，配载时间为20160302 20:00:00，
	* 到达部门为上海转运场，到达时间为20160303 01:30:00；
	* 则按时间排序并用“-”连接后得到“上海转运场-南京转运场-南京转运场-上海转运场”，
	* 因“南京转运场”相邻且重复，故得到最终所需线路‘“上海转运场-南京转运场-上海转运场”’
	* @throws
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private LFDrivingFileEntity encapsulationLine( LFDrivingFileEntity lfDrivingFileEntity,List<LFDrivingFileDetailEntity>  lfDrivingFileDetaillist){
		StringBuffer lineCode=new StringBuffer();
		StringBuffer lineName=new StringBuffer();
		//按要求 根据时间排序
		Collections.sort(lfDrivingFileDetaillist, new Comparator(){
	       public int compare(Object arg0,Object arg1){
	        	 LFDrivingFileDetailEntity org0=(LFDrivingFileDetailEntity)arg0;
	        	 LFDrivingFileDetailEntity org1=(LFDrivingFileDetailEntity)arg1;
	        	 if(null==org0.getDepartTime()){
	        		  if(null==org1.getArriveTime()){
	        			 return  DateUtils.getSecondDiff(org0.getArriveTime(),org1.getDepartTime())<0?1:-1;
	        		  }else{
	        			 return  DateUtils.getSecondDiff(org0.getArriveTime(),org1.getArriveTime())<0?1:-1; 
	        		  }
	        	  }else{
	        		  if(null==org1.getArriveTime()){
		        		 return  DateUtils.getSecondDiff(org0.getDepartTime(),org1.getDepartTime())<0?1:-1;
		        	  }else{
		        		 return  DateUtils.getSecondDiff(org0.getDepartTime(),org1.getArriveTime())<0?1:-1; 
		        	  }
	        	  }
		  } 	
		});
		for (int i = 0; i < lfDrivingFileDetaillist.size(); i++) {
		  if(i==0){
				if(null==lfDrivingFileDetaillist.get(i).getDepartTime()){
					lineCode.append(lfDrivingFileDetaillist.get(i).getDestOrgCode());
					lineName.append(lfDrivingFileDetaillist.get(i).getDestOrgName());
				}else{
					lineCode.append(lfDrivingFileDetaillist.get(i).getOrigOrgCode());
					lineName.append(lfDrivingFileDetaillist.get(i).getOrigOrgName());
				}
			}else{
				if(null==lfDrivingFileDetaillist.get(i).getDepartTime()){
				   if(!StringUtils.equals(lfDrivingFileDetaillist.get(i).getDestOrgCode(),lfDrivingFileDetaillist.get(i-1).getDestOrgCode())&&
					 !StringUtils.equals( lfDrivingFileDetaillist.get(i).getDestOrgCode(),lfDrivingFileDetaillist.get(i-1).getOrigOrgCode())){
						lineCode.append(",").append(lfDrivingFileDetaillist.get(i).getDestOrgCode());
						lineName.append("-").append(lfDrivingFileDetaillist.get(i).getDestOrgName());
					}
				}else{
					if(!StringUtils.equals(lfDrivingFileDetaillist.get(i).getOrigOrgCode(),lfDrivingFileDetaillist.get(i-1).getDestOrgCode())&&
							!StringUtils.equals(lfDrivingFileDetaillist.get(i).getOrigOrgCode(),lfDrivingFileDetaillist.get(i-1).getOrigOrgCode())){
						lineCode.append(",").append(lfDrivingFileDetaillist.get(i).getOrigOrgCode());
						lineName.append("-").append(lfDrivingFileDetaillist.get(i).getOrigOrgName());
					}
				}
			}
		}
		lfDrivingFileEntity.setLineCode(lineCode.toString());
		lfDrivingFileEntity.setLineName(lineName.toString());
		return lfDrivingFileEntity;
	}
	/**
	 * 
	* @Title: addLFDrivingFile
	* @Description: 新增 :长途车辆行驶档案  基础信息
	* @author ZX_189284
	* @param @param lfDrivingFileEntity
	* @param @return    设定文件
	* @return int    返回类型
	* @throws
	 */
	@Override
	public int addLFDrivingFile(LFDrivingFileEntity lfDrivingFileEntity){
		checkLFDrivingFile(lfDrivingFileEntity);
		return lfDrivingFileDao.addLFDrivingFile(lfDrivingFileEntity);	
	}
	/**
	 * 
	* @Title: checkLFDrivingFile
	* @Description: 检查 行驶档案基础数据
	* @author ZX_189284
	* @param @param lfDrivingFileEntity    设定文件
	* @return void    返回类型
	* @throws
	 */
	public void checkLFDrivingFile(LFDrivingFileEntity lfDrivingFileEntity){
		if(StringUtils.isEmpty(lfDrivingFileEntity.getDrivingDate())){
			throw new  LFDrivingFileException("期间为空");
		}
		if(StringUtils.isEmpty(lfDrivingFileEntity.getOrgIdCode())){
			throw new  LFDrivingFileException("所属车队为空");
		}
		if(StringUtils.isEmpty(lfDrivingFileEntity.getLineCode())){
			throw new  LFDrivingFileException("线路途经外场为空");
		}
		if(lfDrivingFileEntity.getDepartDistance()==null){
			throw new  LFDrivingFileException("出发公里数为空");
		}
		if(lfDrivingFileEntity.getArriveDistance()==null){
			throw new  LFDrivingFileException("到达公里数为空");
		}
		if(lfDrivingFileEntity.getConsumeFuelFeeTotal()==null){
			throw new  LFDrivingFileException("总油费为空");
		}
		if(lfDrivingFileEntity.getToolFeeTotal()==null){
			throw new  LFDrivingFileException("路桥费为空");
		}
		if(lfDrivingFileEntity.getConsumeFuelTotal()==null){
			throw new  LFDrivingFileException("总有升数为空");
		}
		if(StringUtils.isEmpty(lfDrivingFileEntity.getVehicleNo())){
			throw new  LFDrivingFileException("车牌号为空");
		}
		if(StringUtils.isEmpty(lfDrivingFileEntity.getDriverCodeOne())){
			throw new  LFDrivingFileException("司机1为空");
		}
		if(StringUtils.isEmpty(lfDrivingFileEntity.getDriverCodeTwo())){
			throw new  LFDrivingFileException("司机2为空");
		}	
	}
	
	/**
	 * 
	* @Title: addlfDrivingFileDetail
	* @Description: 新增 :长途车辆行驶档案  明细信息（配载信息）
	* @author ZX_189284
	* @param  lfDrivingFileDetailEntity 明细实体（配载单信息）
	* @param @return    设定文件
	* @return int    返回类型
	* @throws
	 */
	@Override
	public int addlfDrivingFileDetail(LFDrivingFileDetailEntity lfDrivingFileDetailEntity){
		checkLFDrivingFileDetail(lfDrivingFileDetailEntity);
		return lfDrivingFileDao.addlfDrivingFileDetail(lfDrivingFileDetailEntity);
	}
	public void checkLFDrivingFileDetail(LFDrivingFileDetailEntity lfDrivingFileDetailEntity){
		if(StringUtils.isEmpty(lfDrivingFileDetailEntity.getDrivingNo())){
			throw new  LFDrivingFileException("行车编码为空");
		}	
		if(StringUtils.isEmpty(lfDrivingFileDetailEntity.getDestOrgCode())){
			throw new LFDrivingFileException("到达部门为空");	
		}
		if(StringUtils.isEmpty(lfDrivingFileDetailEntity.getOrigOrgCode())){
			throw new LFDrivingFileException("配载部门为空");	
		}
		if(StringUtils.isEmpty(lfDrivingFileDetailEntity.getVehicleassembleNo())){
			throw new LFDrivingFileException("配载车次号为空");	
		}
		if(lfDrivingFileDetailEntity.getVolumeTotal()==null){
			throw new LFDrivingFileException("总体积为空");	
		}
		if(lfDrivingFileDetailEntity.getWeightTotal()==null){
			throw new LFDrivingFileException("总重量为空");	
		}
	}
	/**
	 * 
	* @Title: selectLFDrivingFile
	* @Description: 查询：长途车辆行驶档案  基础信息（分页）
	* @author ZX_189284
	* @param @param lfDrivingFileEntity
	* lfDrivingFileEntity.drivingDate 期间
	* lfDrivingFileEntity.drivingNo 行驶编码
	* lfDrivingFileEntity.orgIdCode 所属车队
	* @param @param start
	* @param @param limit
	* @return List<LFDrivingFileEntity>    返回类型
	* @throws
	 */
	@Override
	public List<LFDrivingFileEntity> selectLFDrivingFile (LFDrivingFileEntity lfDrivingFileEntity, int start, int limit){
		return lfDrivingFileDao.selectLFDrivingFile(lfDrivingFileEntity, start, limit);
	}
	/**
	 * 
	* @Title: selectLFDrivingFileCount
	* @Description: 查询：长途车辆行驶档案  基础信息总数
	* @author ZX_189284
	* @param @param lfDrivingFileEntity
	* @return Int    返回类型
	* @throws
	 */
	@Override
	public Long  selectLFDrivingFileCount(LFDrivingFileEntity lfDrivingFileEntity){
		return lfDrivingFileDao.selectLFDrivingFileCount(lfDrivingFileEntity);
	}

	/**
	 * 
	* @Title: qureyOtherBillInfo
	* @Description: 根据配载单号 查询对应的车辆任务下的配载信息 和快递交接单信息
	* @author ZX_189284
	* @param @param qureyOtherBillInfo 配载单号
	* @return list<LFDrivingFileDetailEntity>    返回类型
	* @throws
	 */
	@Override
	public List<LFDrivingFileDetailEntity> qureyOtherBillInfo(String vehicleassembleNo){
		if(StringUtils.isEmpty(vehicleassembleNo)){
			throw new LFDrivingFileException("输入的配载单号为空");
		}
		//根据 配载单 查询出的 车辆任务ID为
		String truckTaskId=lfDrivingFileDao.qureyTaskIdByNo(vehicleassembleNo);
		if(StringUtils.isNotBlank(truckTaskId)){
			List<LFDrivingFileDetailEntity> lfDrivingFileDetails =new ArrayList<LFDrivingFileDetailEntity>();
			 List<LFDrivingFileDetailEntity> lfDrivingFileDetailLD=lfDrivingFileDao.qureyOtherBillInfo(truckTaskId);
			 if(CollectionUtils.isNotEmpty(lfDrivingFileDetailLD)&&lfDrivingFileDetailLD.size()>0){
				 lfDrivingFileDetails.addAll(lfDrivingFileDetailLD);
		    	}
			 List<LFDrivingFileDetailEntity> lfDrivingFileDetailWK= lfDrivingFileDao.qureyOtherBillInfoWk(truckTaskId);
			 if(CollectionUtils.isNotEmpty(lfDrivingFileDetailWK)&&lfDrivingFileDetailWK.size()>0){
				 lfDrivingFileDetails.addAll(lfDrivingFileDetailWK);
		    	}
			return lfDrivingFileDetails;
		}
		return null;
	}
	/**
	 * 
	* @Title: getDrivingNo
	* @Description: 根据所属车队生成 行驶编码
	* @author ZX_189284
	* @param @param origOrgCode
	* @param @return    设定文件
	* @return String    返回类型
	* @throws
	 */
	@Override
	public String getDrivingNo(String orgIdCode){
		String newCode = "";
		long newSeq = 0;
		// 截取出发日期年月日
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		//发车日期字符串，yyyyMMdd
		String leaveDate = dateFormat.format(new Date()).toString();
		//库存占比开始时间   取当前时间  不要时分秒
		Date startDate = DateUtils.getStartDatetime(new Date());
		//向后加两天, 并以23：:59：:59 结束
	    Date endDate = DateUtils.getEndDatetime(startDate);
		//按照传入参数，查询对应配载车次记录的最后单号
		String oldSn = lfDrivingFileDao.queryLatestDrivingNo(orgIdCode,startDate,endDate);
		//若未返回记录，则单号为01
		if(StringUtils.isBlank(oldSn)){
			newSeq = 1;
		}else{
		//若返回了1条记录，则在已有单号基础上+1
			String interceptedNo = StringUtils.right(oldSn, LoadConstants.SONAR_NUMBER_3);
			try{
				newSeq = Long.valueOf(interceptedNo).longValue() + 1;
			}catch(NumberFormatException e){
				throw new TfrBusinessException(TfrBusinessException.ILLEGAL_ARGUMENT_EXCEPTION, e.getMessage(), e);
			}
		}
		String newSn = String.format("%0"+ LoadConstants.SONAR_NUMBER_3 +"d", newSeq);
//		String orgIdTopOutField=null;
//		OrgAdministrativeInfoEntity orgInfo = orgAdministrativeInfoComplexService.getTopFleetByCode(orgIdCode);
//		if(orgInfo != null){
//			MotorcadeEntity motorcade = motorcadeService.queryMotorcadeByCode(orgInfo.getCode());
//			if(motorcade != null){
//				orgIdTopOutField=  motorcade.getTransferCenter();
//				LOGGER.error("驻地外场Code"+orgIdCode);
//			}
//		}
//		/**根据行政组织编码查询外场**/
//		OutfieldEntity startOrg = outfieldService.queryOutfieldByOrgCode(orgIdTopOutField);
//		if(null == startOrg ){
//			throw new TfrBusinessException("未找到对应车队的顶级外场");
//		}
		List<VehicleDrivingEntity>  vehicleDrivingEntitys=vehicleDrivingService.queryVehicleDrivingByLHF(orgIdCode);
		if(CollectionUtils.isEmpty(vehicleDrivingEntitys)){
			throw new LFDrivingFileException("查询编码失败，请在综合长度车辆行驶档案添加对应长途车队");
		}
		newCode = vehicleDrivingEntitys.get(0).getTrafficCode() + leaveDate + newSn;
		///保证编码不重复
		LFDrivingFileEntity 	lfDrivingFileEntity=lfDrivingFileDao.queryLfDrivingFileByDNo(newCode);
		if(lfDrivingFileEntity!=null){
			 newSn = String.format("%0"+ LoadConstants.SONAR_NUMBER_3 +"d", newSeq+1);
			newCode = vehicleDrivingEntitys.get(0).getTrafficCode()  + leaveDate + newSn;
		}
		return newCode;
	}
	@Override
	public VehicleAssociationDto lfDrivingFileVehicle(String vehicleNo) {
		return vehicleService.queryVehicleByVehicleNoIncludeTractors(vehicleNo);
	}
	/**
	 * 
	* @Title: queryLfDrivingFileByDNo
	* @Description: 根据行驶编码  查询行驶档案基础信息(包括 配载信息)
	* @author ZX_189284
	* @param @param drivingNo
	* @param @return    设定文件
	* @return LFDrivingFileEntity    返回类型
	* @throws
	 */
	@Override
	public  LFDrivingFileEntity queryLfDrivingFile(String drivingNo){
		LFDrivingFileEntity lfDrivingFileEntity=null;
		if(StringUtils.isNotBlank(drivingNo)){
			lfDrivingFileEntity=lfDrivingFileDao.queryLfDrivingFileByDNo(drivingNo);
			List<LFDrivingFileDetailEntity> list =lfDrivingFileDao.selectLFDrivingFileDetail(drivingNo);
			if(StringUtils.isNotBlank(lfDrivingFileEntity.getLineTransferCode())){
				String[] codes=lfDrivingFileEntity.getLineTransferCode().split(",");
				List<OrgAdministrativeInfoEntity> lists=orgAdministrativeInfoService.queryOrgAdministrativeInfoBatchByCode(codes);
				List<String> codelist=Arrays.asList(codes);
				lfDrivingFileEntity.setLineTransferCodeList(codelist);
				lfDrivingFileEntity.setLineTransferEntitys(lists);
			}
			lfDrivingFileEntity.setLfDrivingFileDetailList(list);
		}else{
			throw new LFDrivingFileException("行驶编码为空！");
		}
		return lfDrivingFileEntity;
	}
	@Override
	@Transactional
	public String updateLFDrivingFile(LFDrivingFileVo lfDrivingFileVo) {
		LFDrivingFileEntity	lfDrivingFileEntity=lfDrivingFileVo.getLfDrivingFile();
		List<LFDrivingFileDetailEntity> lfDrivingFileDetails=lfDrivingFileVo.getLfDrivingFileDetails();
		/**
		 * 用来生成线路Code 
		 */
		List<LFDrivingFileDetailEntity> lfDrivingFileDetaillist=new ArrayList<LFDrivingFileDetailEntity>();
		for (LFDrivingFileDetailEntity lfDrivingFileDetailEntity : lfDrivingFileDetails) {
			if(StringUtils.equals(FossConstants.YES, lfDrivingFileVo.getIsChange())){
				lfDrivingFileDao.detelelfDFDetailsByNo(lfDrivingFileEntity.getDrivingNo());
				lfDrivingFileDetailEntity.setDrivingNo(lfDrivingFileEntity.getDrivingNo());
				lfDrivingFileDetailEntity.setId(UUIDUtils.getUUID());
				checkLFDrivingFileDetail(lfDrivingFileDetailEntity);
				addlfDrivingFileDetail(lfDrivingFileDetailEntity);
			}else{
			 if(StringUtils.isEmpty(lfDrivingFileDetailEntity.getId())){
				lfDrivingFileDetailEntity.setDrivingNo(lfDrivingFileEntity.getDrivingNo());
				lfDrivingFileDetailEntity.setId(UUIDUtils.getUUID());
				checkLFDrivingFileDetail(lfDrivingFileDetailEntity);
				addlfDrivingFileDetail(lfDrivingFileDetailEntity);
			 }
			}
			LFDrivingFileDetailEntity departDetail=new LFDrivingFileDetailEntity();
			departDetail.setDepartTime(lfDrivingFileDetailEntity.getDepartTime());
			departDetail.setOrigOrgCode(lfDrivingFileDetailEntity.getOrigOrgCode());
			departDetail.setOrigOrgName(lfDrivingFileDetailEntity.getOrigOrgName());
			LFDrivingFileDetailEntity arriveDetail=new LFDrivingFileDetailEntity();
			arriveDetail.setArriveTime(lfDrivingFileDetailEntity.getArriveTime());
			arriveDetail.setDestOrgCode(lfDrivingFileDetailEntity.getDestOrgCode());
			arriveDetail.setDestOrgName(lfDrivingFileDetailEntity.getDestOrgName());
			lfDrivingFileDetaillist.add(departDetail);
			lfDrivingFileDetaillist.add(arriveDetail);
		}
		if(!StringUtils.equals(FossConstants.YES, lfDrivingFileVo.getIsChange())){
		if(lfDrivingFileVo.getUpdatelfDrivingFileDetails()!=null){
			updatelfDrivingFileDetails(lfDrivingFileVo.getUpdatelfDrivingFileDetails());
		}
		if(lfDrivingFileVo.getIds()!=null&&lfDrivingFileVo.getIds().size()>0){
			lfDrivingFileDao.detelelfDrivingFileDetails(lfDrivingFileVo.getIds());
		}
		}
		lfDrivingFileEntity=encapsulationLine(lfDrivingFileEntity,lfDrivingFileDetaillist);
		lfDrivingFileEntity.setModifyTime(new Date());
		lfDrivingFileEntity.setModifyUserCode(FossUserContext.getCurrentInfo().getEmpCode());
		lfDrivingFileEntity.setModifyUserName(FossUserContext.getCurrentInfo().getEmpName());
		updateLFDrivingFile(lfDrivingFileEntity);
		return lfDrivingFileEntity.getDrivingNo();
	}
	public void updatelfDrivingFileDetails(List<LFDrivingFileDetailEntity> updatelfDrivingFileDetails){
		for (LFDrivingFileDetailEntity lfDrivingFileDetailEntity : updatelfDrivingFileDetails) {
			checkLFDrivingFileDetail(lfDrivingFileDetailEntity);
			if(StringUtils.isEmpty(lfDrivingFileDetailEntity.getId())){
				throw new LFDrivingFileException("明细id为空 不能修改！");
			}
			lfDrivingFileDao.updatelfDrivingFileDetail(lfDrivingFileDetailEntity);
		}
	}
	public void updateLFDrivingFile(LFDrivingFileEntity lfDrivingFileEntity){
		lfDrivingFileEntity.setDrivingDate("过滤修改时候的校验  期间为空");
		checkLFDrivingFile(lfDrivingFileEntity);
		if(StringUtils.isEmpty(lfDrivingFileEntity.getId())){
			throw new LFDrivingFileException("id为空 不能修改！");
		}
		lfDrivingFileDao.updateLFDrivingFile(lfDrivingFileEntity);
	}
	/**
	 *根据登陆人部门信息查询所属车队
	 */
	@Override
	public LFDrivingFileEntity queryOrgIdCode(String orgIdCode) {
		List<VehicleDrivingEntity> vehicleDrivingEntitys=vehicleDrivingService.queryVehicleDrivingByDEP(orgIdCode);
		if(vehicleDrivingEntitys.size()>0){
			LFDrivingFileEntity lFDrivingFileEntity=new LFDrivingFileEntity();
			lFDrivingFileEntity.setOrgIdCode(vehicleDrivingEntitys.get(0).getLongHaulFleetCode());
			lFDrivingFileEntity.setOrgIdName(vehicleDrivingEntitys.get(0).getLongHaulFleetName());
			return lFDrivingFileEntity;
		}else{
			throw new LFDrivingFileException("根据登陆部门获取所属车队失败！请在综合的长途车辆行驶档案信息的的车队中维护对应车队");
		}
		
	}
}

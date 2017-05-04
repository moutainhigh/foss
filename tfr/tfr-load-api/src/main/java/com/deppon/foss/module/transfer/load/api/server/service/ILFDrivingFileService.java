package com.deppon.foss.module.transfer.load.api.server.service;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.dto.VehicleAssociationDto;
import com.deppon.foss.module.transfer.load.api.shared.domain.LFDrivingFileDetailEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.LFDrivingFileEntity;
import com.deppon.foss.module.transfer.load.api.shared.vo.LFDrivingFileVo;


public interface ILFDrivingFileService {
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
	int addLFDrivingFile(LFDrivingFileEntity lfDrivingFileEntity);
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
	int addlfDrivingFileDetail(LFDrivingFileDetailEntity lfDrivingFileDetailEntity);
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
	List<LFDrivingFileEntity> selectLFDrivingFile(
			LFDrivingFileEntity lfDrivingFileEntity, int start, int limit);
	/**
	 * 
	* @Title: selectLFDrivingFileCount
	* @Description: 查询：长途车辆行驶档案  基础信息总数
	* @author ZX_189284
	* @param @param lfDrivingFileEntity
	* @return Int    返回类型
	* @throws
	 */
	Long selectLFDrivingFileCount(LFDrivingFileEntity lfDrivingFileEntity);
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
	String saveLFDrivingFile(LFDrivingFileEntity lfDrivingFileEntity,
			List<LFDrivingFileDetailEntity> lfDrivingFileDetails);
	/**
	 * 
	* @Title: qureyOtherBillInfo
	* @Description: 根据配载单号 查询对应的车辆任务下的配载信息 和快递交接单信息
	* @author ZX_189284
	* @param @param qureyOtherBillInfo 配载单号
	* @return list<LFDrivingFileDetailEntity>    返回类型
	* @throws
	 */
	List<LFDrivingFileDetailEntity> qureyOtherBillInfo(String vehicleassembleNo);
	/**
	 * 
	* @Title: queryLatestDrivingNo
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @author ZX_189284
	* @param @param orgIdCode    设定文件
	* @return void    返回类型
	* @throws
	 */
	 String getDrivingNo(String orgIdCode);
	 /**
	  * 
	 * @Title: getVehicle
	 * @Description: 根据车牌号 查询出来的车辆信息
	 * @author ZX_189284
	 * @param @param vehicleNo 车牌
	 * @param @return    设定文件
	 * @return VehicleAssociationDto    返回类型
	 * @throws
	  */
	VehicleAssociationDto lfDrivingFileVehicle(String vehicleNo);
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
	LFDrivingFileEntity queryLfDrivingFile(String drivingNo);
	/**
	 * 
	* @Title: updateLFDrivingFile
	* @Description:修改 行驶档案 包括配载信息
	* @author ZX_189284
	* @param @param lfDrivingFileVo
	* @param @return    设定文件
	* @return String    返回类型
	* @throws
	 */
	String updateLFDrivingFile(LFDrivingFileVo lfDrivingFileVo);

	/**
	 * 
	* @Title: queryOrgIdCode
	* @Description:根据登陆人部门信息查询所属车队
	* @author ZX_189284
	* @param @param orgIdCode
	* @param @return    设定文件
	* @return LFDrivingFileEntity    返回类型
	* @throws
	 */
	LFDrivingFileEntity queryOrgIdCode(String orgIdCode);

}

package com.deppon.foss.module.transfer.load.api.server.dao;

import java.util.Date;
import java.util.List;

import com.deppon.foss.module.transfer.load.api.shared.domain.LFDrivingFileDetailEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.LFDrivingFileEntity;
/**
 * 
* @ClassName: ILFDrivingFileDao
* @Description: 长途车辆行驶档案 Dao接口
* @author ZX_189284
* @date 2016-5-23 下午4:47:28
*
 */
public interface ILFDrivingFileDao {
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
	* @Title: selectLFDrivingFileDetail
	* @Description: 根据行车编号 查询车使 明细
	* @author ZX_189284
	* @param @param drivingNo 行车编码
	* @return List<LFDrivingFileDetailEntity>    返回类型
	* @throws
	 */
	List<LFDrivingFileDetailEntity> selectLFDrivingFileDetail(String drivingNo);

	/**
	 * 
	* @Title: qureyOtherBillInfo
	* @Description: 根据任务id查询  对应的配载单
	* @author ZX_189284
	* @param @param qureyOtherBillInfo 配载单号
	* @return list<LFDrivingFileDetailEntity>    返回类型
	* @throws
	 */
	List<LFDrivingFileDetailEntity> qureyOtherBillInfo(String truckTaskId);
	/**
	 * 
	* @Title: qureyOtherBillInfo
	* @Description: 根据任务id查询  对应的悟空交接单
	* @author ZX_189284
	* @param @param truckTaskId 配载单号
	* @return list<LFDrivingFileDetailEntity>    返回类型
	* @throws
	 */
	List<LFDrivingFileDetailEntity> qureyOtherBillInfoWk(String truckTaskId );
	/**
	 * 
	* @Title: queryLatestDrivingNo
	* @Description: 获取对应车队 当天的最大行驶号，用于生成 行驶档案 行驶号时使用
	* @author ZX_189284
	* @param @param origOrgCode 所属车队
	* @param @param startDate 当天开始时间 
	* @param @param endDate  当天结束时间 
	* @param @return    设定文件
	* @return String    返回类型
	* @throws
	 */
	String queryLatestDrivingNo(String origOrgCode, Date startDate, Date endDate);
	/**
	 * 
	* @Title: queryLfDrivingFileByDNo
	* @Description: 根据行驶编码  查询行驶档案基础信息
	* @author ZX_189284
	* @param @param drivingNo
	* @param @return    设定文件
	* @return LFDrivingFileEntity    返回类型
	* @throws
	 */
	LFDrivingFileEntity queryLfDrivingFileByDNo(String drivingNo);
	/**
	 * 
	* @Title: detelelfDrivingFileDetails
	* @Description:  根据 id 批量作废 档案配载信息
	* @author ZX_189284
	* @param @param ids    设定文件
	* @return void    返回类型
	* @throws
	 */
	void detelelfDrivingFileDetails(List<String> ids);
	/**
	 * 
	* @Title: updatelfDrivingFileDetail
	* @Description:根据 id修改 档案配载明细信息
	* @author ZX_189284
	* @param @param lfDrivingFileDetailEntity    设定文件
	* @return void    返回类型
	* @throws
	 */
	void updatelfDrivingFileDetail(
			LFDrivingFileDetailEntity lfDrivingFileDetailEntity);
	/**
	 * 
	* @Title: updateLFDrivingFile
	* @Description:根据id 修改 行驶档案 基础信息
	* @author ZX_189284
	* @param @param lfDrivingFileEntity    设定文件
	* @return void    返回类型
	* @throws
	 */
	void updateLFDrivingFile(LFDrivingFileEntity lfDrivingFileEntity);
	/**
	 * 
	* @Title: detelelfDFDetailsByNo
	* @Description: 根据 行驶编码  批量作废 档案配载信息
	* @author ZX_189284
	* @param @param drivingNo    设定文件
	* @return void    返回类型
	* @throws
	 */
	void detelelfDFDetailsByNo(String drivingNo);
	/**
	 * 
	* @Title: qureyTaskIdByNo
	* @Description:根据配载单号 或者 交接单号  查询对应的车辆任务ID
	* @author ZX_189284
	* @param @param vehicleassembleNo 配载单号
	* @return String 任务id
	* @throws
	 */
	String qureyTaskIdByNo(String vehicleassembleNo);
}

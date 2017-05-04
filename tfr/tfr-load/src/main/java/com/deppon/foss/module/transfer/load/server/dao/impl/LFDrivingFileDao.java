package com.deppon.foss.module.transfer.load.server.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.load.api.server.dao.ILFDrivingFileDao;
import com.deppon.foss.module.transfer.load.api.shared.domain.LFDrivingFileDetailEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.LFDrivingFileEntity;
/**
 * 
* @ClassName: LFDrivingFileDao
* @Description:长途车辆行驶档案Dao
* @author ZX_189284
* @date 2016-5-23 下午2:22:07
*
 */
public class LFDrivingFileDao extends iBatis3DaoImpl implements ILFDrivingFileDao{
	private static final String nameSpace="foss.load.lfDrivingFile.";
	/**
	 * 
	* @Title: addLFDrivingFile
	* @Description:新增 :长途车辆行驶档案  基础信息
	* @author ZX_189284
	* @param LFDrivingFileEntity 基础信息实体
	* @return int    返回类型
	* @throws
	 */
	@Override
	public int addLFDrivingFile(LFDrivingFileEntity lfDrivingFileEntity){
		return this.getSqlSession().insert(nameSpace+"addLFDrivingFile", lfDrivingFileEntity);
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
		return this.getSqlSession().insert(nameSpace+"addlfDrivingFileDetail", lfDrivingFileDetailEntity);		
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
	@SuppressWarnings("unchecked")
	public List<LFDrivingFileEntity> selectLFDrivingFile(LFDrivingFileEntity lfDrivingFileEntity, int start, int limit){
		RowBounds rowBounds = new RowBounds(start, limit);
		return  this.getSqlSession().selectList(nameSpace+"selectLFDrivingFile", lfDrivingFileEntity,rowBounds);
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
	public Long selectLFDrivingFileCount(LFDrivingFileEntity lfDrivingFileEntity){
		return  (Long) this.getSqlSession().selectOne(nameSpace+"selectLFDrivingFileCount", lfDrivingFileEntity);
	}
	/**
	 * 
	* @Title: selectLFDrivingFileDetail
	* @Description: 根据行车编号 查询车使 明细
	* @author ZX_189284
	* @param @param drivingNo 行车编码
	* @return List<LFDrivingFileDetailEntity>    返回类型
	* @throws
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<LFDrivingFileDetailEntity> selectLFDrivingFileDetail(String drivingNo){
		return this.getSqlSession().selectList(nameSpace+"selectLFDrivingFileDetail", drivingNo);
	}

	/**
	 * 
	* @Title: qureyOtherBillInfo
	* @Description: 根据任务id查询  对应的配载单
	* @author ZX_189284
	* @param @param truckTaskId 配载单号
	* @return list<LFDrivingFileDetailEntity>    返回类型
	* @throws
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<LFDrivingFileDetailEntity> qureyOtherBillInfo(String truckTaskId ){
		return  this.getSqlSession().selectList(nameSpace+"qureyOtherBillInfo", truckTaskId);
	}
	/**
	 * 
	* @Title: qureyOtherBillInfo
	* @Description: 根据任务id查询  对应的悟空交接单
	* @author ZX_189284
	* @param @param truckTaskId 配载单号
	* @return list<LFDrivingFileDetailEntity>    返回类型
	* @throws
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<LFDrivingFileDetailEntity> qureyOtherBillInfoWk(String truckTaskId ){
		return  this.getSqlSession().selectList(nameSpace+"qureyOtherBillInfoWk", truckTaskId);
	}
	/**
	 * 
	* @Title: qureyTaskIdByNo
	* @Description:根据配载单号 或者 交接单号  查询对应的车辆任务ID
	* @author ZX_189284
	* @param @param vehicleassembleNo 配载单号
	* @return String 任务id
	* @throws
	 */
	@Override
	public String  qureyTaskIdByNo(String vehicleassembleNo ){
		return  (String) this.getSqlSession().selectOne(nameSpace+"qureyTaskIdByNo", vehicleassembleNo);
	}
	
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
	@Override
	public String queryLatestDrivingNo(String orgIdCode, Date startDate,
			Date endDate) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("orgIdCode", orgIdCode);
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		return (String) this.getSqlSession().selectOne(nameSpace+"queryLatestDrivingNo", map);
	}
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
	@Override
	public LFDrivingFileEntity queryLfDrivingFileByDNo(String drivingNo){
		return (LFDrivingFileEntity) this.getSqlSession().selectOne(nameSpace+"queryLfDrivingFileByDNo", drivingNo);
	}
	/**
	 * 
	* @Title: detelelfDrivingFileDetails
	* @Description:  根据 id 批量作废 档案配载信息
	* @author ZX_189284
	* @param @param ids    设定文件
	* @return void    返回类型
	* @throws
	 */
	@Override
	public void detelelfDrivingFileDetails(List<String> ids) {
		this.getSqlSession().delete(nameSpace+"detelelfDrivingFileDetails", ids);
	}
	/**
	 * 
	* @Title: detelelfDFDetailsByNo
	* @Description: 根据 行驶编码  批量作废 档案配载信息
	* @author ZX_189284
	* @param @param drivingNo    设定文件
	* @return void    返回类型
	* @throws
	 */
	@Override
	public void detelelfDFDetailsByNo(String drivingNo) {
		this.getSqlSession().delete(nameSpace+"detelelfDFDetailsByNo", drivingNo);
	}
	
	/**
	 * 
	* @Title: updatelfDrivingFileDetail
	* @Description:根据 id修改 档案配载明细信息
	* @author ZX_189284
	* @param @param lfDrivingFileDetailEntity    设定文件
	* @return void    返回类型
	* @throws
	 */
	@Override
	public void updatelfDrivingFileDetail(
			LFDrivingFileDetailEntity lfDrivingFileDetailEntity) {
		this.getSqlSession().update(nameSpace+"updatelfDrivingFileDetail", lfDrivingFileDetailEntity);
	}
	/**
	 * 
	* @Title: updateLFDrivingFile
	* @Description:根据id 修改 行驶档案 基础信息
	* @author ZX_189284
	* @param @param lfDrivingFileEntity    设定文件
	* @return void    返回类型
	* @throws
	 */
	@Override
	public void updateLFDrivingFile(LFDrivingFileEntity lfDrivingFileEntity) {
		this.getSqlSession().update(nameSpace+"updateLFDrivingFile", lfDrivingFileEntity);
	}
	
}

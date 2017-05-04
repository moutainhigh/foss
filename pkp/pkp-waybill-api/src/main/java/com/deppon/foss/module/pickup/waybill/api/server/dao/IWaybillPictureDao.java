package com.deppon.foss.module.pickup.waybill.api.server.dao;

import java.util.List;
import java.util.Map;

import com.deppon.foss.module.pickup.waybill.api.shared.dto.WaybillPicturePushLogDto;
import com.deppon.foss.module.pickup.waybill.shared.domain.PDAWoodenRequireEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillPictureEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillPicturePushLogEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillPicturePushMessageEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillPictureSendSmsEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillPushMessageEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.CenterBillOrgDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.OrgCodeTreeByCodeAndCondition;
import com.deppon.foss.module.pickup.waybill.shared.dto.SearchPictureWaybillCondiction;
import com.deppon.foss.module.pickup.waybill.shared.dto.SubCenterBillOrgDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillPictureDto;
import com.deppon.foss.module.pickup.waybill.shared.vo.SearchPictureVo;
import com.deppon.foss.module.pickup.waybill.shared.vo.WaybillPicturePushVo;

/**
 * 图片开单Dao
 * ClassName: IWaybillPictureDao <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * date: 2014-10-10 下午2:23:40 <br/>
 *
 * @author 157229-zxy
 * @version 
 * @since JDK 1.6
 */
public interface IWaybillPictureDao {
	
	/**
	 * 新增图片开单
	 * insertWaybillPicture: <br/>
	 * 
	 * Date:2014-10-10下午2:23:55
	 * @author 157229-zxy
	 * @param waybillPictureEntity
	 * @return
	 * @since JDK 1.6
	 */
	int insertWaybillPicture(WaybillPictureEntity waybillPictureEntity);
	
	/**
	 * 修改图片开单信息
	 * updateWaybillPictureByEntity: <br/>
	 * 
	 * Date:2014-10-10下午2:24:01
	 * @author 157229-zxy
	 * @param waybillPictureEntity
	 * @return
	 * @since JDK 1.6
	 */
	int updateWaybillPictureByEntity(WaybillPictureEntity waybillPictureEntity);
	
	/**
	 * 根据运单号 删除图片开单信息
	 * deleteWaybillPictureByWaybillNo: <br/>
	 * 
	 * Date:2014-10-10下午2:24:17
	 * @author 157229-zxy
	 * @param waybillNo
	 * @return
	 * @since JDK 1.6
	 */
	int deleteWaybillPictureByWaybillNo(String waybillNo);
	
	/**
	 * 根据主键ID删除运单信息
	 * deleteWaybillPictureById: <br/>
	 * 
	 * Date:2014-10-10下午2:24:27
	 * @author 157229-zxy
	 * @param id
	 * @return
	 * @since JDK 1.6
	 */
	int deleteWaybillPictureById(String id);
	
	/**
	 * 查询运单信息
	 * queryWaybillPictureByEntity: <br/>
	 * 
	 * Date:2014-10-10下午2:25:00
	 * @author 157229-zxy
	 * @param waybillPictureEntity
	 * @return
	 * @since JDK 1.6
	 */
	List<WaybillPictureEntity> queryWaybillPictureByEntity(WaybillPictureEntity waybillPictureEntity);
	
	/**
	 * 根据运单号查询图片开单信息
	 * queryWaybillPictureByWaybillNo: <br/>
	 * 
	 * Date:2014-10-10下午2:36:34
	 * @author 157229-zxy
	 * @param waybillNo
	 * @return
	 * @since JDK 1.6
	 */
	WaybillPictureEntity queryWaybillPictureByWaybillNo(String waybillNo);
	
	/**
	 * 分页查询运单信息 
	 * queryNoPrintWaybillPictureByDriverCode: <br/>
	 * 
	 * Date:2014-10-10下午7:03:57
	 * @author 157229-zxy
	 * @param driverCode
	 * @return
	 * @since JDK 1.6
	 */
	List<WaybillPictureEntity> queryWaybillPictureByDtoPage(WaybillPictureDto waybillPictureDto);
	/**
	 * 更改运单审批状态
	 * */
	void  updateWaybillPictureById(WaybillPictureDto waybillPictureDto);
	/**
	 * 通过条件查询图片开单
	 * @param condiction
	 * @return
	 */
	List<SearchPictureVo> searchPictureWaybillByCondiction (SearchPictureWaybillCondiction condiction);
	//查询图片运单
	WaybillPictureEntity findWaybillPictureOne(WaybillPictureEntity wpe);
	//更具条件查询图片运单的总数
	int findWaybillPictureCount(WaybillPictureEntity entity);
	
	/**
	 * 根据waybillId查询运单信息
	 * queryWaybillPictureById: <br/>
	 * 
	 * Date:2014-10-18下午4:20:00
	 * @author 157229-zxy
	 * @param waybillId
	 * @return
	 * @since JDK 1.6
	 */
	WaybillPictureEntity queryWaybillPictureById(String waybillId);

	int updateWaybillPicture(WaybillPictureEntity wpe);

	int updatePictureWaybillByWaybillno(
			WaybillPictureEntity waybillPictureEntity);

	void insertWaybillPDAWoodenRequireEntity(
			PDAWoodenRequireEntity requireEntity);
	int saveWaybillPushMessage(WaybillPushMessageEntity e);
	int delWaybillPushMessage(WaybillPushMessageEntity e);
	List<WaybillPushMessageEntity> queryWaybillPushMessageEntity(int min);
	
	/**
	 * 通过jobid查询百度云要推送的数据
	 * @param jobId
	 * @return
	 */
	List<WaybillPicturePushMessageEntity> queryWaybillPicturePushMessageByJobId(String jobId);
	/**
	 * 通过jobid查询要发送短信的数据
	 * @param jobId
	 * @return
	 */
	List<WaybillPictureSendSmsEntity> queryWaybillPictureSendSmsByJobId(String jobId);
	/**
	 * 插入推送消息表
	 * @param entity
	 */
	void insertWaybillPicturePushMessage(WaybillPicturePushMessageEntity entity);
	/**
	 * 插入短信发送表
	 * @param entity
	 */
	void insertWaybillPictureSendSms(WaybillPictureSendSmsEntity entity);
	/**
	 * 删除推送消息数据
	 * @param id
	 */
	void deleteWaybillPicturePushMessageById(String id);
	/**
	 * 删除短信发送数据
	 * @param id
	 */
	void deleteWaybillPictureSendSmsById(String id);
	/**
	 * 根据行数更新推送消息jobid
	 * @param vo
	 */
	int updatePushMessageByRowNum(WaybillPicturePushVo vo);
	/**
	 * 根据行数更新短信发送jobid
	 * @param vo
	 */
	int updateSendSmsByRowNum(WaybillPicturePushVo vo);
	/**
	 * 插入日志
	 */
	void insertWaybillPicturePushLog(WaybillPicturePushLogEntity entity);
	
	List<WaybillPicturePushLogDto> queryOuterWaybillPicturePushLog(List<String> waybillNos);
	/**
	 * 查询图片开单总数
	 * */
	Long queryWaybillPictureByDtoPageCounts(WaybillPictureDto waybillPictureDto);
	/**
	 * 查询图片开单列表
	 * */
	List<WaybillPictureDto> queryWaybillPictureDtoPage(WaybillPictureDto waybillPictureDto);
	/**
	 * 
	* @Description: 查询开单组未开单量
	* @author hbhk 
	* @date 2015-6-17 上午8:40:10 
	  @param params
	  @return
	 */
	List<CenterBillOrgDto> queryPicBillOrgAndNotCount(Map<String, Object> params);
	
	/**
	 * Describe：递归查询机构集合
	 *
	 * @author Foss-278328-hujinyang
	 * @Date:2015-9-6  下午4:36:27 <br>
	 * @param treeByCodeAndCondition
	 * @return 
	 * @return List<String>
	 */
	List<String> iteratorQueryDepartmentCodeLists(OrgCodeTreeByCodeAndCondition treeByCodeAndCondition);
	
	/**
	 * 根据sql筛选图片分配规则  完全由sql实现业务分配
	 * @author Foss-278328-hujinyang
	 * @date 2016-1-29
	 * @return
	 */
	public List<SubCenterBillOrgDto> queryDistributeInfoBySql(Map<String, Object> params);
	/**
	 * 图片开单查询本地未补录订单
	 * @author Foss-352676-YUANHB 
	 * @date 2017-3-9 下午9:57:46
	 * @param entity
	 * @return
	 * @see
	 */
	int getPictureWaybillLocalCount(WaybillPictureEntity entity);
	/**
	 * 图片开单查询全国待补录订单
	 * @author Foss-352676-YUANHB 
	 * @date 2017-3-9 下午9:57:51
	 * @return
	 * @see
	 */
	int getPictureWaybillAllCount(WaybillPictureEntity entity);
}

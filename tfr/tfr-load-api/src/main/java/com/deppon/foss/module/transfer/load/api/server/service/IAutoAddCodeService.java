package com.deppon.foss.module.transfer.load.api.server.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.transfer.load.api.shared.domain.AutoAddCodeEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.AutoAddGisEntity;

/**
 * @title: IAutoAddCodeService 
 * @description: 业务接口.
 * @author: 140022-foss-songjie
 * @Date: 2015-05-13 09:22:38
 */
public interface IAutoAddCodeService extends IService {

	/**
	 * <pre>
	 * 	   保存操作.
	 * </pre>
	 * @param entity 自动补码Service层
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	void save(AutoAddCodeEntity entity) throws IllegalArgumentException, IllegalAccessException;
	
	/**
	 * <pre>
	 * 	   移除操作.
	 * </pre>
	 * @param entity 自动补码Service层
	 * @throws SQLException
	 */
	void remove(AutoAddCodeEntity entity);
	
	/**
	 * <pre>
	 * 	   更新操作.
	 * </pre>
	 * @param entity 自动补码Service层
	 * @throws SQLException
	 */
	void update(AutoAddCodeEntity entity) throws SQLException;
	
	/**
	 * <pre>
	 * 	   查询操作.
	 * </pre>
	 * @param entity 自动补码Service层
	 * @return AutoAddCodeEntiy
	 * @throws SQLException
	 */
	AutoAddCodeEntity find(AutoAddCodeEntity entity) throws SQLException;
	
	/**
	 * outerAutoAddCode:虚拟营业部匹配到落地配外发网点后直接补码. <br/>  
	 *  Date:2015年6月16日下午4:31:58  
	 * @author shiwei-045923 shiwei@outlook.com  
	 * @param entity
	 * @param gisEntity 
	 * @return  
	 * @since JDK 1.6
	 */
	int outerAutoAddCode(AutoAddCodeEntity entity,AutoAddGisEntity gisEntity);
	
	
	/**
	* @description 判断是否外发单
	* @param entity
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2015年10月28日 下午5:48:26
	*/
	String outerAutoAddCodeFlag(AutoAddCodeEntity entity);
	
	
	/**
	* @description 通过esb请求gis服务获取运单对应的提货网点
	* @param entityList
	* @return
	* @throws Exception
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2015年10月28日 上午9:23:21
	*/
	Map<String,Object> requestGisByPostList(List<AutoAddCodeEntity> entityList) throws Exception;
	
	
	/**
	* @description 自动补码
	* @param entity
	* @param requestGisStartTime
	* @param requestGisEndTime
	* @param wResDto
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2015年10月28日 上午9:30:04
	*/
	void autoComplement_TheadComm(AutoAddCodeEntity entity,AutoAddGisEntity gisEntity);
	
	
	/**
	* @description 人工补码
	* @param entity
	* @param requestGisStartTime
	* @param requestGisEndTime
	* @param errorMsg
	* @param resutType
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2015年10月28日 上午11:17:18
	*/
	void addHand(AutoAddCodeEntity entity,AutoAddGisEntity gisEntity,String resutType);
	
	/**
	 * testGisBeNormal:检测GIS地址匹配服务是否正常. <br/>  
	 *  
	 *  Date:2015年6月14日下午5:07:36  
	 * @author shiwei-045923 shiwei@outlook.com  
	 * @return  
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonGenerationException 
	 * @since JDK 1.6
	 */
	int testGisBeNormal() throws JsonGenerationException, JsonMappingException, IOException;
	
	/**
	* @description 重置
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2015年9月24日 上午09:55:05
	*/
	void restNaAJobId();
	
	/**
	* @description 根据jobId重置补码
	* @param jobId
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2015年10月29日 上午8:39:57
	*/
	void restNaAJobId(String jobId) throws Exception;
	
	/**
	 * updateAndGetJobId:更新并范围jobID. <br/>  
	 *  Date:2015年6月15日下午4:48:57  
	 * @author shiwei-045923 shiwei@outlook.com  
	 * @return  
	 * @since JDK 1.6
	 */
	String updateAndGetJobId();
	
	
	/**
	* @description 更新jobid并返回对应的jobid
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2015年11月4日 上午9:51:19
	*/
	String GisUpdateGetJobId();
	
	/**
	 * queryAutoAddCodeEntityByJodId:根据生成的jobId更新待补码运单. <br/>  
	 *  Date:2015年6月15日下午8:46:46  
	 * @author shiwei-045923 shiwei@outlook.com  
	 * @param jobId
	 * @return  
	 * @since JDK 1.6
	 */
	List<AutoAddCodeEntity> queryAutoAddCodeEntityByJodId(String jobId);
	
	
	/**
	* @description 据生成的jobId获取 List<AutoAddGisEntity> 
	* @param jobId
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2015年11月4日 上午10:25:19
	*/
	List<AutoAddGisEntity> queryGisAutoAddCodeEntityByJodId(String jobId);
	
	
	/**
	* @description gis交互返回或未处理掉的运单保存
	* @param entity
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2015年11月4日 上午10:32:32
	*/
	int insertAutoAddGisEntity (AutoAddGisEntity entity);

	/**  
	 *  Date:2015年6月15日下午9:03:45  
	 * @author shiwei-045923 shiwei@outlook.com  
	 * @param obj  
	 * @since JDK 1.6  
	 */
	void ThreadsPool(Object obj);
	
	/**
	 * resetData:重置异常数据. <br/>  
	 *  Date:2015年6月15日下午9:11:24  
	 * @author shiwei-045923 shiwei@outlook.com    
	 * @since JDK 1.6
	 */
	void resetData();
	
	
	/**
	* @description 自动补码总开关
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2015年7月24日 下午2:55:05
	*/
	public String readAutoAddCodePower();
	
	
	/**
	* @description 自动补码job的线程数量
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2015年7月25日 下午4:23:45
	*/
	public int readAutoAddCodeThreadNum();
	
	/**
	* @description 自动补码job的线程执行间隔时间
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2015年7月25日 下午4:23:58
	*/
	public int readAutoAddCodeThreadExeTime();
	
	/**
	* @description 自动补码job的一次处理总量
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2015年7月25日 下午4:24:14
	*/
	public int readAutoAddCodeExeNum();
	
	/**
	* @description 自动补码gis的线程数量
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2015年7月25日 下午4:24:14
	*/
	public int readAutoGisThreadNum();
	
	
	/**
	* @description 自动补码gis的一次处理总量
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2015年7月25日 下午4:24:14
	*/
	public int readAutoGisExeNum();
}
package com.deppon.pda.bdm.module.foss.monitor.server.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.sql.DataSource;

import com.deppon.pda.bdm.module.core.shared.constants.Constant;
import com.deppon.pda.bdm.module.core.shared.util.UUIDUtils;
import com.deppon.pda.bdm.module.core.shared.vos.MessgeVo;
import com.deppon.pda.bdm.module.foss.monitor.server.dao.IMonitorDao;
/**
 * 
  * @ClassName MonitorDaoImpl 
  * @Description TODO 监控dao的实现
  * @author mt hyssmt@vip.qq.com
  * @date 2013-10-1 上午11:09:09
 */
public class MonitorDaoImpl implements IMonitorDao{
	private Object dataSource;
	public void saveMonitorInfo(Map<String,MessgeVo> msgMap) throws Exception{
		Connection conn = ((DataSource)dataSource).getConnection();
		
		Set<Entry<String,MessgeVo>> entrySet = msgMap.entrySet();
		Iterator<Entry<String,MessgeVo>> it = entrySet.iterator();
		PreparedStatement ps = null;
		MessgeVo msgVo = null;
		try{
			msgVo = msgMap.get(Constant.MONITOR.MONITOR_MSGTIME);
			conn.setAutoCommit(false);
			String sql = "INSERT INTO PDA.T_PDA_MONITOR(ID,OPERTYPE,TOTALNUM,SUCCESSNUM,FAILNUM,MONITORTIME) " +
					"VALUES(?,?,?,?,?,?)";
			ps = conn.prepareStatement(sql);
			
			while(it.hasNext()){
				 Entry<String,MessgeVo> entry = it.next();
				 //时间操作类不需要插入
				 if(entry.getKey().equals(Constant.MONITOR.MONITOR_MSGTIME)){
					 continue;
				 }
				 MessgeVo msg = entry.getValue();
				 //保存监控消息
				 ps.setString(1, UUIDUtils.getUUID());
				 ps.setString(2, msg.getOperType());
				 ps.setInt(3, msg.getNormalTotal() + msg.getErrTotal());
				 ps.setInt(4, msg.getNormalTotal());
				 ps.setInt(5, msg.getErrTotal());
				 ps.setString(6, msgVo.getMsgTime());
				 ps.executeUpdate();
			}
			conn.commit();
		}catch(Exception ex){
			throw ex;
		}finally{
			
			if(ps != null){
				ps.close();
				ps = null;
			}
			if(conn != null && !conn.isClosed()){
				conn.close();
				conn = null;
			}
		}
	}

	public void delMonitorInfo() throws Exception{
		Connection conn = ((DataSource)dataSource).getConnection();
		PreparedStatement ps = null;
		try{
			String sql = "delete from PDA.T_PDA_MONITOR where createdate < trunc(sysdate)";
			ps = conn.prepareStatement(sql);
			ps.executeUpdate();
		}catch(Exception ex){
			throw ex;
		}finally{
			
			if(ps != null){
				ps.close();
				ps = null;
			}
			if(conn != null && !conn.isClosed()){
				conn.close();
				conn = null;
			}
		}
	}

	public void setDataSource(Object dataSource) {
		this.dataSource = dataSource;
	}
}

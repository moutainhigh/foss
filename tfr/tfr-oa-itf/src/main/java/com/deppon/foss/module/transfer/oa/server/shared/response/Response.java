package com.deppon.foss.module.transfer.oa.server.shared.response;

/**
 * 查询货物轨迹返回CUBC实体，
 * 参数问waybillNo
 * @author 310248
 *
 */
public class Response {
    
	//当有值时为Y，没有时N；异常时为空；
	private String hasTrack;
	
	//响应信息
	private String errmsg;
	
	public String getHasTrack() {
		return hasTrack;
	}
	public void setHasTrack(String hasTrack) {
		this.hasTrack = hasTrack;
	}
	public String getErrmsg() {
		return errmsg;
	}
	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}

}

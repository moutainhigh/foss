package com.deppon.foss.module.transfer.common.api.server.service;

public interface IBillNumService {

	String generateHandoverbillNo();

	String generateLoadTaskNo(String orgCode);
	
	String generateDeliverTaskNo(String orgCode);

	String generateUnLoadTaskNo(String orgCode);

	String generateStTaskNo(String orgCode);

}

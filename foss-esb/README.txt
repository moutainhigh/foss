目前通过ESB的webservice接口分为两类：
1. 和OA进行交互的接口；
2. 其他系统的webservice接口；

对于OA的webservice接口，WSDL生成的接口里不包含ESBHeader的参数，主要在cxf的interceptor进行配置；
测试代码请参考：com.deppon.foss.esb.oa.OAServiceTest
相应的spring配置文件在：/src/test/resources/com/deppon/foss/esb/oa/

而对于其他系统的webservice，WSDL生成的接口中包含了ESBHeader的参数；
测试代码请参考：com.deppon.foss.esb.common.CommonServiceTest 
相应的spring配置文件在：/src/test/resources/com/deppon/foss/esb/common/
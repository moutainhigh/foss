注意：
在本地开发调试的时候需要把spring-mqc.xml文件中
<bean id="esbJmsTransactionManager1" class="org.springframework.transaction.jta.JtaTransactionManager" />
<bean id="esbJmsTransactionManager2" class="org.springframework.transaction.jta.JtaTransactionManager" />
注释掉。
把
	<bean id="esbJmsTransactionManager1"
	    class="org.springframework.jms.connection.JmsTransactionManager">
	    <property name="connectionFactory" ref="esbJmsConnectionFactory1" />
	</bean>
	<bean id="esbJmsTransactionManager2"
	    class="org.springframework.jms.connection.JmsTransactionManager">
	    <property name="connectionFactory" ref="esbJmsConnectionFactory2" />
	</bean>
	放开。
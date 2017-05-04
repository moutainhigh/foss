package com.deppon.foss.util.typehandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import net.sf.log4jdbc.PreparedStatementSpy;
import oracle.jdbc.OraclePreparedStatement;
import oracle.sql.DATE;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.springframework.jdbc.support.nativejdbc.CommonsDbcpNativeJdbcExtractor;

public class OracleDateTimeTypeHandler extends BaseTypeHandler {

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i,
			Object parameter, JdbcType jdbcType) throws SQLException {
		CommonsDbcpNativeJdbcExtractor extractor = new CommonsDbcpNativeJdbcExtractor();
		
		if ( ps instanceof PreparedStatementSpy ) {
			ps = ((PreparedStatementSpy)ps).getRealPreparedStatement();
		}
		ps = extractor.getNativePreparedStatement(ps);
		
		OraclePreparedStatement oraps = ps.unwrap(OraclePreparedStatement.class);
		
		((OraclePreparedStatement)oraps).setDATE(i, new DATE(new java.sql.Timestamp(((Date)parameter).getTime())));
	}

	@Override
	public Object getNullableResult(ResultSet rs, String columnName)
			throws SQLException {
	    return rs.getTimestamp(columnName);
	}

	@Override
	public Object getNullableResult(CallableStatement cs, int columnIndex)
			throws SQLException {
	    return cs.getTimestamp(columnIndex);
	}

}

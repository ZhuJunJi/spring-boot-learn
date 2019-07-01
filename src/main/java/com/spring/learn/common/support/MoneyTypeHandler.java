package com.spring.learn.common.support;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author J.zhu
 * @date 2019/7/1
 */
public class MoneyTypeHandler extends BaseTypeHandler<Money> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Money parameter, JdbcType jdbcType) throws SQLException {
        ps.setObject(i, parameter);
    }

    @Override
    public Money getNullableResult(ResultSet rs, String columnName) throws SQLException {
        double amount = rs.getDouble(columnName);
        return Money.of(CurrencyUnit.of("CNY"), amount/100);
    }

    @Override
    public Money getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        double amount = rs.getDouble(columnIndex);
        return Money.of(CurrencyUnit.of("CNY"), amount/100);
    }

    @Override
    public Money getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        double amount = cs.getDouble(columnIndex);
        return Money.of(CurrencyUnit.of("CNY"), amount/100);
    }
}

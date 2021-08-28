package com.example.testebsintegration.config;

import org.hibernate.tool.schema.extract.internal.SequenceInformationExtractorOracleDatabaseImpl;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class AppSequenceInformationExtractor extends SequenceInformationExtractorOracleDatabaseImpl
{
    /**
     * Singleton access
     */
    public static final AppSequenceInformationExtractor INSTANCE = new AppSequenceInformationExtractor();

    @Override
    protected Long resultSetMinValue(ResultSet resultSet) throws SQLException {
        return resultSet.getBigDecimal("min_value").longValue();
    }
}

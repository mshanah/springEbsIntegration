package com.example.testebsintegration.config;

import org.hibernate.dialect.Oracle12cDialect;
import org.hibernate.tool.schema.extract.spi.SequenceInformationExtractor;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppOracleDialect extends Oracle12cDialect
{
    @Override
    public SequenceInformationExtractor getSequenceInformationExtractor() {
        return AppSequenceInformationExtractor.INSTANCE;
    }

    @Override
    public String getQuerySequencesString() {
        return "select * from user_sequences";
    }
}

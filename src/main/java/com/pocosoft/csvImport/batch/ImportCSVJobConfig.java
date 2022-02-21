package com.pocosoft.csvImport.batch;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.pocosoft.csvImport.entity.EmployeeDetail;


@Configuration
public class ImportCSVJobConfig<JobCompletionNotificationListener> {
	
	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Autowired
	private DataSource dataSource;
	
	private JdbcTemplate jdbcTemplate;
	
	

	
	@Bean(name = "myReader")
	@StepScope
    public FlatFileItemReader<EmployeeDetail> importReader(@Value("#{jobParameters[fullPathFileName]}") String pathToFile) {
        FlatFileItemReader<EmployeeDetail> reader = new FlatFileItemReader<>();
        reader.setResource(new FileSystemResource(pathToFile));
        reader.setLinesToSkip(1);
        reader.setLineMapper(new DefaultLineMapper<EmployeeDetail>() {{
            setLineTokenizer(new DelimitedLineTokenizer() {{
                setNames(new String[]{"name", "email", "address", "telephone"});
            }});
            setFieldSetMapper(new BeanWrapperFieldSetMapper<EmployeeDetail>() {{
                setTargetType(EmployeeDetail.class);
            }});
        }});
        return reader;
    }
	
	
	@Bean
    public JdbcBatchItemWriter<EmployeeDetail> writer() {
        JdbcBatchItemWriter<EmployeeDetail> writer = new JdbcBatchItemWriter<>();
        writer.setItemSqlParameterSourceProvider(
                new BeanPropertyItemSqlParameterSourceProvider<EmployeeDetail>());
        writer.setSql("INSERT INTO employee_details_tab(fullName, email, address, telephone)"
				+ " VALUES(:name, :email, :address, :telephone)");
        writer.setDataSource(dataSource);
        return writer;
    }
	
	
	  @Bean
	    public Job importCSVFileJob(ItemReader<EmployeeDetail> importReader) {
	    	return jobBuilderFactory.get("importCSVFileJob")
					.incrementer(new RunIdIncrementer())
					.flow(step1(importReader))
					.end()
					.build();
	    }
	
	
	@Bean
    public CSVFileItemProcessor processor()
    {
		return new CSVFileItemProcessor();
    }
	
	@Bean
    public Step step1(ItemReader<EmployeeDetail> importReader) {
        return stepBuilderFactory.get("step1").<EmployeeDetail, EmployeeDetail>chunk(1000).reader(importReader)
                .processor(processor()).writer(writer()).build();
    }

}

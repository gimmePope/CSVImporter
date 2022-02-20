package com.pocosoft.csvImport.batch;

import org.springframework.batch.item.ItemProcessor;

import com.pocosoft.csvImport.entity.EmployeeDetail;

public class CSVFileItemProcessor implements ItemProcessor<EmployeeDetail, EmployeeDetail> {

	@Override
	public EmployeeDetail process(EmployeeDetail item) throws Exception {
		// TODO Auto-generated method stub
		return item;
	}

}

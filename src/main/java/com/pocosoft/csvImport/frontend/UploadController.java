package com.pocosoft.csvImport.frontend;

import java.io.IOException;

import javax.batch.operations.JobRestartException;

import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class UploadController {
	
	
	@GetMapping("/")
	public String indexPage()
	{
		//System.out.println("I JUST WANT TO LET U KNOW I HEAR YOU!!!!!!");
		
		return "index";
	}
	
	@GetMapping("/upload")
	public String displayUpload()
	{
		//System.out.println("I JUST WANT TO LET U KNOW I HEAR YOU FROM UPLOAD !!!!!!");
		return "index";
	}
	
	@PostMapping("/process/csv/upload")
	public String  processUploadedCSV(@RequestParam("csv_file") MultipartFile multipartFile)throws IOException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException
	{
		return "";
	}

}

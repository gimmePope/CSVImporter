package com.pocosoft.csvImport.frontend;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.batch.operations.JobRestartException;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class UploadController {
	
	
		@Value("${file.upload.path}")
		String uploadPath;
		
	 	@Autowired 
	    private JobLauncher jobLauncher;
	    
	  	@Autowired 
	    private Job importCSVFileJob;
	
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
	public ResponseEntity<String>  processUploadedCSV(@RequestParam("csv_file") MultipartFile file)throws IOException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException, org.springframework.batch.core.repository.JobRestartException
	{
		if(file.isEmpty())
			return new ResponseEntity<String>("Please select a CSV file for processing",HttpStatus.OK);
		
		String fileName = file.getOriginalFilename();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		if(!fileName.endsWith(".csv"))
			return new ResponseEntity<String>("Please select a CSV file for processing",HttpStatus.OK);
			System.out.println("File Name is " + fileName);
			//Save upload file
			File fileToImport = new File(uploadPath + formatter.format(new Date()) + fileName);
			
			//delete if file already exist in upload directory
			
			if(fileToImport.exists())
			  fileToImport.delete();
			
			boolean fileCreated = false;
			try {
				fileCreated = fileToImport.createNewFile();
				if(fileCreated)
				{
					
					FileOutputStream fout = new FileOutputStream(fileToImport);
					fout.write(file.getBytes());
					fout.close();	
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			//Launch the Batch Job
			if(fileCreated)
			{
	        JobExecution jobExecution = jobLauncher.run(importCSVFileJob, new JobParametersBuilder()
	                .addString("fullPathFileName", fileToImport.getAbsolutePath())
	                .toJobParameters()); 
	        
			}
			else
			{
				return new ResponseEntity<String>("File Upload Failed",HttpStatus.OK);
			}
			
			
		return new ResponseEntity<String>("Loaded Succesfully",HttpStatus.OK);
	}

}

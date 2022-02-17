package com.pocosoft.csvImport.frontend;

import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

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
	public String  processUploadedCSV()
	{
		return "";
	}

}

package com.application.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;


import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;



@Service
public class FileConvertUtil {

	
	public String convertCSVToJson(MultipartFile multipartFile) throws IOException
	{		
        File input=multipartToFile(multipartFile);        
        List<Map<?, ?>> data = readObjectsFromCsv(input);
        return writeAsJson(data);
	}
	
	private  File multipartToFile(MultipartFile multipart) throws IllegalStateException, IOException 
	{
		File convFile = new File("c:\\" + multipart.getOriginalFilename());
		convFile.createNewFile();
		FileOutputStream fos = new FileOutputStream(convFile);
		fos.write(multipart.getBytes());
		fos.close();
		return convFile;
	    
	}
	private List<Map<?, ?>> readObjectsFromCsv(File file) throws IOException {
        CsvSchema bootstrap = CsvSchema.emptySchema().withHeader();
        CsvMapper csvMapper = new CsvMapper();
        MappingIterator<Map<?, ?>> mappingIterator = csvMapper.reader(Map.class).with(bootstrap).readValues(new InputStreamReader(new FileInputStream(file),"ISO-8859-1" ));
        return mappingIterator.readAll();
    }

	private String writeAsJson(List<Map<?, ?>> data) throws IOException {
       ObjectMapper mapper = new ObjectMapper();
       return mapper.writeValueAsString(data);       
    }
}

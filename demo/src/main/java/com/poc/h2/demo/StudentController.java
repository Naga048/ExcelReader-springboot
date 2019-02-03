package com.poc.h2.demo;

import static java.util.stream.Collectors.toMap;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.WorkbookUtil;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

@RestController
@RequestMapping("/api")
public class StudentController {
	
	@Autowired
	private StudentRepository studentRepo;
	
	@Autowired
	private PersonRepository personRepo;
	
	@Autowired
	private NodeRepository noderepo;
	
	@Autowired
	private KeyValueRepository keyrepo;
	
	@RequestMapping(value="/hello",method=RequestMethod.GET)
	  String index(){
	    return "Hello spring";
	  }
	
	@RequestMapping("/students")
	@ResponseBody
	public List<Student> getList()
	
	{
		System.out.println("test.......");
		List<Student> list = new ArrayList<Student>();
		
		for (Student student : list) {
			new ByteArrayInputStream(student.getData());
		}
		
		
		return 
				(List<Student>) //list;
		studentRepo.findAll();
	}

	@PostMapping("/upload") // //new annotation since 4.3
    public List<Map<String,Object>> singleFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes) {
			System.out.println("enter method.........");
			List<Map<String, Object>> mapList=null;
        try {
        	InputStream inputStream = file.getInputStream();
        	 /*//mapList =getxlsData( inputStream);
        	List<List<ExcelProperties>> data =  getxlsData1(file.getInputStream());
        	
        	for (List<ExcelProperties> list : data) {
				for (ExcelProperties object : list) {
					//System.out.println("Row id...."+object.getRowId()+"......object value.........."+object.getCellValue()+".....cell Id........"+object.getCellId());
				}
			}
        	List<ExcelProperties> headers = data.stream().findFirst().get();
        	List<List<ExcelProperties>> data1 = data.stream().skip(1).collect(Collectors.toList());
        	System.out.println("data1........."+data1.size());
        	List<Person> persons = new ArrayList<>();
        	for (List<ExcelProperties> list : data1) {
        		List<ExcelProperties> data2 = list.stream().filter(p->headers.contains(p.getCellId())).collect(Collectors.toList());
        		for (ExcelProperties excelProperties : data2) {
        			System.out.println("Row id...."+excelProperties.getRowId()+"......object value.........."+excelProperties.getCellValue()+".....cell Id........"+excelProperties.getCellId());
				}
				
			}*/
        	
        	getObjectFromXls(file.getInputStream());
        	
            /*Student student = new Student();
            //student.setId(1L);
            student.setName("Naga");
            student.setPassportNumber("111111");
            student.setData(IOUtils.toByteArray(inputStream));
            studentRepo.save(student);*/
        	 /*for (Map<String, Object> map : mapList) {
        		 
        		 Set<Map.Entry<String,Object>> set =  map.entrySet();
        		for (Entry<String, Object> entry : set) {
        			Excel_Data data1 = new Excel_Data();
        		 data1.setColumnName(entry.getKey());
           		 data1.setColumnValue(entry.getValue().toString());
           		 personRepo.save(data1);
           		 
				}
        		 
        		 
			}*/
        	
        } catch (IOException e) {
            e.printStackTrace();
        }

        return mapList;
    }
	
	private List<Map<String, Object>> getxlsData(InputStream inputStream) {
		
		List<Map<String,Object>> mapList = new ArrayList<>();
		
		try {
			XSSFWorkbook workbook =  new XSSFWorkbook(inputStream);
			XSSFSheet  sheet= workbook.getSheetAt(0);
			UploadUtil uploadUtil = new UploadUtil();
			Supplier<Stream<Row>> rowStreamSupplier = uploadUtil.getRowStreamSupplier(sheet);
			
			Row headerRow = rowStreamSupplier.get().findFirst().get();
			List<String> headerList = uploadUtil.getStream(headerRow).map(Cell::getStringCellValue).
					map(String::valueOf).collect(Collectors.toList());
			System.out.println("header size..."+headerList.size());
			
			int colCount = headerList.size();
			Stream<Row> dataRows = rowStreamSupplier.get().skip(1);
			Iterator<Row> it = dataRows.iterator();
			
			Map<String,String> rowMap = null;
			/*while(it.hasNext())
			{
				rowMap = new HashMap<>();
				Stream<Cell> stream = uploadUtil.getStream(it.next());
				List<String> values = stream.map(Cell::getStringCellValue).collect(Collectors.toList());
				
				System.out.println("values size..."+values.size());
				for (int i = 0; i < colCount; i++) {
					rowMap.put(headerList.get(i), values.get(i));
					mapList.add(rowMap);
					
				}
				
				
			}*/
			
			return rowStreamSupplier.get().skip(1).map(row->{
				
				List<Object> cellList = uploadUtil.getStream(row)
						.map( 
								cell->{
									Object val="";
									if(cell.CELL_TYPE_STRING==cell.getCellType())
									{
									 val = cell.getStringCellValue();
									
									}
									if(cell.CELL_TYPE_NUMERIC==cell.getCellType())
									{
										val = cell.getNumericCellValue();
									}
									return val;
									}
								
								)
						.collect(Collectors.toList());
				return uploadUtil.cellIteratorSupplier(colCount)
						 .get()
						 .collect(toMap(headerList::get, cellList::get));
			}).collect(Collectors.toList());
			
			
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return mapList;
		
	}
	
	public List<List<ExcelProperties>> getxlsData1(InputStream inputStream) {
		//Create Workbook instance holding reference to .xlsx file
        XSSFWorkbook workbook;
        List<List<ExcelProperties>>  data = new ArrayList<List<ExcelProperties>>();
		try {
			workbook = new XSSFWorkbook(inputStream);
			//Get first/desired sheet from the workbook
	        XSSFSheet sheet = workbook.getSheetAt(0);
	        Iterator<Row> rowIterator = sheet.iterator();
	        while (rowIterator.hasNext()) 
	        {
	        	Row row = rowIterator.next();
	        	System.out.println("row.getRowNum()......."+row.getRowNum());
	        	Iterator<Cell> cellIterator = row.cellIterator();
	        	List<ExcelProperties> objects = new ArrayList<>();
	        	while (cellIterator.hasNext()) 
	            {
	        		   Cell cell = cellIterator.next();
	        		   ExcelProperties excelProperties = new ExcelProperties();
	        		   Object val="";
	        		   if(cell.CELL_TYPE_STRING==cell.getCellType());
	        		   {
	        		   	val=cell.getStringCellValue();
	        		   	excelProperties.setCellValue(val);
	        		   	excelProperties.setRowId(row.getRowNum());
	        		   	excelProperties.setCellId(cell.getColumnIndex());
	        		   }
	        		   	/*if(cell.CELL_TYPE_NUMERIC==cell.getCellType());
	        		   	{
	        		   	val=cell.getNumericCellValue();
	        		   	}*/
	        		   	objects.add(excelProperties) ;
	        			   
	            }
	        	data.add(objects);
	        }
			
		 		
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        
		
		return data;
		
		
		
	}
	
	
	public void getObjectFromXls(InputStream in)
	{
		//Create Workbook instance holding reference to .xlsx file
        XSSFWorkbook workbook;
        
        try {
			workbook = new XSSFWorkbook(in);
			//Get first/desired sheet from the workbook
	        XSSFSheet sheet = workbook.getSheetAt(0);
	        Node node = new Node();
	        node.setNodeName(sheet.getSheetName());
	        noderepo.save(node);
	        Iterator<Row> rowIterator = sheet.iterator();
	        List<List<KeyValue>> list = new ArrayList<List<KeyValue>>();
	        while (rowIterator.hasNext()) {
				Row row = (Row) rowIterator.next();
				Iterator<Cell> cellIterator = row.cellIterator();
				String key = "";
				if(cellIterator.hasNext()) {
					key = cellIterator.next().toString();
					System.out.println("cell first value..."+key);
				}
				List<KeyValue> keyValues = new ArrayList<>();
				while (cellIterator.hasNext()) {
					Cell cell = (Cell) cellIterator.next();
					KeyValue keyValue = new KeyValue();
					keyValue.setKey(key);
					keyValue.setValue(cell.getNumericCellValue());
					keyValue.setNode(node);
					keyrepo.save(keyValue);
					keyValues.add(keyValue);
				}
				System.out.println("keyValues.size...."+keyValues.size());
				list.add(keyValues);
				
			}
	        
	        
	       System.out.println("list size...."+list.size());
	       List<KeyValue> list1 = keyrepo.findByNodeId(1l);
	       List<List<KeyValue>> list3 = new ArrayList<List<KeyValue>>();
	       list3.add(list1);
	       JsonArray jsonArray = new JsonArray();
	       JsonObject jsonObject1 = new JsonObject();
	       for (List<KeyValue> list2 : list3) {
	    	   JsonObject jsonObject = new JsonObject();
	    	   int i=1;
	    	   Object key = "";
	    	   JsonObject jsonObject2 =null;
	    	    for (KeyValue keyValue : list2) {
	    	    	key = keyValue.getKey();
	    	    	System.out.println("keyValue.getValue()...."+keyValue.getValue());
	    	    	 jsonObject2 = new JsonObject();
	    	    	jsonObject2.addProperty("id", keyValue.getId());
	    	    	jsonObject2.addProperty("value", keyValue.getValue());
	    	    	jsonObject.addProperty("key_"+i, jsonObject2.toString());
	    			i++;
				}
	    	    jsonObject2 = new JsonObject();
	    	    jsonObject2.addProperty("id", 0);
    	    	jsonObject2.addProperty("value", key.toString());
	    	    jsonObject.addProperty("key_0", jsonObject2.toString());
	    	   
	    	    jsonArray.add(jsonObject);
			
		}
	       //System.out.println("jsonArray...."+jsonArray.toString());    
	       jsonObject1.add("tab1", jsonArray);  
	       System.out.println("jsonObject1...."+jsonObject1.toString());
	       /*
	       JsonObject jsonObject = jsonObject1;
	       JsonArray array = jsonObject.getAsJsonArray("tab1");
	       System.out.println("array...."+array.toString());
	       List<List<KeyValue>> list4 = new ArrayList<List<KeyValue>>();
	       List<KeyValue> keyValues = new ArrayList<KeyValue>();
	       for (int i = 0; i < array.size(); i++) {
	    	   JsonObject objects = (JsonObject) array.get(i);
	    	   Set<String> set = objects.keySet();
	    	   String key = objects.get("key_0").toString();
	    	   for (String string : set) {
	    		   KeyValue keyValue = new KeyValue();
	    		   if(!string.equalsIgnoreCase("key_0"))
	    		   {
		    	   keyValue.setKey(key);
		    	   keyValue.setValue(objects.get(string).getAsDouble());
	    		   }
	    		   keyValues.add(keyValue);
			}
	    	   
	    	   list4.add(keyValues);
		}
	       System.out.println("list1 size...."+list1.size());   
	       //FileInputStream  fileInputStream = new FileInputStream("Writesheet.xlsx");
	       
	       Workbook workbook1 = new XSSFWorkbook();
	       XSSFSheet spreadsheet = (XSSFSheet) workbook1.createSheet( "Employee Info");
	       
	       XSSFRow row;
	       JsonArray array1 = jsonObject.getAsJsonArray("tab1");
	       for (int i = 0; i < array1.size(); i++) {
	    	   row = spreadsheet.createRow(i);
	    	   JsonObject objects = (JsonObject) array.get(i);
	    	   Set<String> set= objects.keySet();
	    	   TreeSet<String> tSet = new TreeSet<>(set);
	    	   int cellId=0;
	    	   for (String string : tSet) {
	    		   Cell cell = row.createCell(cellId++);
	               cell.setCellValue(objects.get(string).toString().replaceAll("^\"|\"$", ""));
			}
	    	   
		}
	       FileOutputStream out = new FileOutputStream("Writesheet.xlsx");
	    	      
	       workbook1.write(out);
	       workbook1.close();
	    	      
	    	      out.flush();
	    	      out.close();
	        
	      */ 
	       
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		
		
	}
	
	
}

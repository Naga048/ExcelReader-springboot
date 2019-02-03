package com.poc.h2.demo;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import org.apache.commons.io.IOUtils;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

@RestController
@RequestMapping("/api")
public class FileController {
	
	@Autowired
	NodeRepository nodeRepository;
	@Autowired
	ScenarioReporistory sceRepo;
	@Autowired
	ScopeRepository scopeRepo;
	@Autowired
	ParameterRepository paramRepo;
	@Autowired
	NodeEntriesRepository nodeEntryRepo;
	@Autowired
	NodeEntryXrefRepository xrefRepo;
	
	
	
	
	
	@PostMapping("/uploadFile") // //new annotation since 4.3
    public Parameter_Nodes singleFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes) {
			System.out.println("enter method.........");
			List<KRIList> mapList=null;
			Parameter_Nodes parameter_Nodes=null;
        try {
        	
        	Scenario scenario = new Scenario();
        	scenario.setSceName("Application_Sce");
        	sceRepo.save(scenario);
        	
         	ScopeEntity scopeEntity = new ScopeEntity();
        	scopeEntity.setDivId(1l);
        	scopeEntity.setSceId(scenario);
        	scopeRepo.save(scopeEntity);
        	ScopeEntity scopeEntity1 = new ScopeEntity();
        	scopeEntity1.setDivId(2l);
        	scopeEntity1.setSceId(scenario);
        	scopeRepo.save(scopeEntity1);
        	
        	
        	List<ScopeEntity> list =  scopeRepo.findBySceId(scenario);
        	System.out.println("list size..."+list.size());
        	
        	for (ScopeEntity scopeEntity2 : list) {
        		ParameterSet parameterSet = new ParameterSet();
            	parameterSet.setSceId(scenario);
            	parameterSet.setScopeId(scopeEntity2);
            	parameterSet.setData(IOUtils.toByteArray(file.getInputStream()));
            	parameterSet.setFileName(file.getOriginalFilename());
            	paramRepo.save(parameterSet);
            	
			}
        	
        	List<ParameterSet> parameterSets = paramRepo.findBySceId(scenario);
        	 parameter_Nodes = new Parameter_Nodes();
        	
        	for (ParameterSet parameterSet : parameterSets) {
        		mapList = ExcelUtils.getKriNames(new ByteArrayInputStream(parameterSet.getData()));
        		for (KRIList kriList : mapList) {
            		Node node = new Node();
            		node.setNodeName(kriList.getKriName());
            		node.setHeaderCount((kriList.getSheetNo()));
            		node.setParameterSet(parameterSet);
            		nodeRepository.save(node);
 				
    			}
       		
			}
        	
        	parameter_Nodes.setScenario_id(scenario.getSceId().toString());
        	parameter_Nodes.setParametersList(mapList);
        	
        	
        }
	 catch (IOException e) {
        e.printStackTrace();
    }

    return parameter_Nodes;
	}
	
	
	@PostMapping("/saveFile") // //new annotation since 4.3
	
    public void saveFile(@RequestBody Parameter_Nodes parameter_Nodes) throws IOException {
	  
		Scenario scenario = new Scenario();
		scenario.setSceId(Long.parseLong(parameter_Nodes.getScenario_id()));
		List<ParameterSet> parameterSet = paramRepo.findBySceId(scenario);
		List<KRIList> list = parameter_Nodes.getParametersList();
		
		for (ParameterSet paramSet : parameterSet) {
			
			List<Node> nodeList =   nodeRepository.findByParamId(paramSet.getpId());
			
			for (Node node : nodeList) {
				for (KRIList kri : list) {
					
					if(node.getNodeName().equals(kri.getKriName()))
					{
						Node node2 = new Node();
						node2.setId(node.getId());
						node2.setNodeName(node.getNodeName());
						node2.setHeaderCount(kri.getSheetNo());
						node2.setNodeType(kri.getKriType());
						node2.setParameterSet(paramSet);
						nodeRepository.save(node2);
					}
					
				}
			}
		}
			//logic to parse excel
			//System.out.println("parameterSet  size....."+parameterSet.size());
		List<List<ParameterSet>> list3 = SplitList.split(parameterSet, 2);
		System.out.println("List<List<ParameterSet>>..."+list3.size());
			for (ParameterSet paramSet1 : parameterSet) {
				//System.out.println("paramSet1 id....."+paramSet1.getpId());
				List<Node> nodeList1 =   nodeRepository.findByParamId(paramSet1.getpId());
				InputStream inputStream = new ByteArrayInputStream(paramSet1.getData());
				//System.out.println("nodeList1 size....."+nodeList1.size());
				XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
				for (Node node : nodeList1) {
					
					
					//System.out.println("node name......"+node.getNodeName());
					XSSFSheet sheet = workbook.getSheet(node.getNodeName().trim());
					Iterator<Row> rowIterator = sheet.iterator();
					Row headerOne =null;
					if(node.getHeaderCount().equals("1.0"))
					{
						if(rowIterator.hasNext()) {
							 headerOne = (Row) rowIterator.next();
						}
							
						
					}
					if(node.getHeaderCount().equals("2.0"))
					{
						if(rowIterator.hasNext()) rowIterator.next();
						if(rowIterator.hasNext()) rowIterator.next();
					}
					while (rowIterator.hasNext()) {
						
						Row row = (Row) rowIterator.next();
						//System.out.println("row id....."+row.getRowNum());
						Iterator<Cell> cellIterator = row.cellIterator();
						String key = "";
						if(!node.getNodeType().equals("INTERVAL"))
						{
							if(cellIterator.hasNext()) {
								key = cellIterator.next().toString();
								
							}
						}
						else
						{
							String lowerLimt="";
							String upperLimt="";
							if(cellIterator.hasNext()) lowerLimt = cellIterator.next().toString();
							if(cellIterator.hasNext()) upperLimt = cellIterator.next().toString();
							key = "["+lowerLimt+","+upperLimt+"]";
							
						}
						while (cellIterator.hasNext()) {
							
							Cell cell = (Cell) cellIterator.next();
							//System.out.println("cell  value..."+cell.toString());
							NodeEntries nodeEntries = new NodeEntries();
							nodeEntries.setNodeEntryName(key);
							nodeEntries.setEntryValue(cell.getNumericCellValue());
							Node node1= new Node();
							node1.setId(node.getId());
							nodeEntries.setNodeId(node1);
							nodeEntryRepo.save(nodeEntries);
							
								if(headerOne!=null && headerOne.getCell(cell.getColumnIndex())!=null)
								{
								
							    List<NodeEntries> entries= nodeEntryRepo.findByNodeName(headerOne.getCell(cell.getColumnIndex()).toString());
								if(entries.size()>0) {
							    NodeEntries headerNodeEntry = Collections.max(entries,new NodeEntries());
								
								NodeEntryXref entryXref = new NodeEntryXref();
								entryXref.setChildNodeId(nodeEntries);
								entryXref.setParentNodeId(headerNodeEntry.getNodeEntryId());
								xrefRepo.save(entryXref);
								}
								
								}
								
							
						}
						
					}
					
					
					
				
				
			}
			
			
			
		}
	
	}
	
	
	@PostMapping("/writeFile/{sceId}/{scopeId}/{pId}") 
	public String writeDataToExcel(@PathVariable Long sceId,@PathVariable Long scopeId,@PathVariable Long pId) throws IOException {
		
		
		
		Optional<ParameterSet> parameterSet = paramRepo.findById(pId);
		InputStream inputStream = new ByteArrayInputStream(parameterSet.get().getData());
		XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
		List<Node> nodes =  nodeRepository.findByParamId(pId);
		XSSFWorkbook workbook1 = new XSSFWorkbook();
		XSSFSheet kriSheet = (XSSFSheet) workbook1.createSheet("KRIList");
		int rowCount = 0;
		Map<Integer, Object[]> data = new TreeMap<Integer, Object[]>();
		int id=0;
		for (Node node : nodes) {
			data.put(id, new Object[] {node.getNodeName(),node.getNodeType(),node.getNodeName(),node.getHeaderCount()});
			id++;
			
		}
		Set<Integer> keyset = data.keySet();
		int rowNum=0;
		for (Integer key : keyset)
        {
            Row row = kriSheet.createRow(rowNum++);
            Object [] objArr = data.get(key);
            int cellnum = 0;
            for (Object obj : objArr)
            {
               Cell cell = row.createCell(cellnum++);
               if(RegExUtil.isString(obj.toString()))
            	   cell.setCellValue(obj.toString().replaceAll("^\"|\"$", ""));
               
               if(RegExUtil.isDouble(obj.toString()))
            	   cell.setCellValue(Double.parseDouble(obj.toString().replaceAll("^\"|\"$", "")));
              
            }
        }
		JsonObject nodesObject = new JsonObject();
		JsonArray nodesJsonAr = new JsonArray();
		for (Node node : nodes) {
			
			XSSFSheet spreadsheet = (XSSFSheet) workbook1.createSheet(node.getNodeName());
			List<NodeEntries> entries =  nodeEntryRepo.findByNid(node.getId());
			XSSFSheet sheet = workbook.getSheet(node.getNodeName().trim());
			XSSFCellStyle  newCellStyle = workbook1.createCellStyle();
			int maxRowNum=0;
			if(node.getHeaderCount().equals("1.0") ||node.getHeaderCount().equals("2.0") )
				maxRowNum = (sheet.getLastRowNum()-(int)Double.parseDouble(node.getHeaderCount()));
			else
				maxRowNum = sheet.getLastRowNum();
			maxRowNum = maxRowNum+1;
			
			List<List<NodeEntries>> NodeEntriesList = SplitList.split(entries, maxRowNum);
			JsonArray jsonArray = new JsonArray();
			JsonObject nodeObj = new JsonObject();
			for (List<NodeEntries> list : NodeEntriesList) {
				JsonObject rowObject = new JsonObject();
				int i=1;
		    	Object key = "";
		    	JsonObject nodeEntry =null;
		    	for (NodeEntries nodeEn : list) {
	    	    	key = nodeEn.getNodeEntryName();
	    	    	
	    	    	nodeEntry = new JsonObject();
	    	    	nodeEntry.addProperty("id", nodeEn.getNodeEntryId());
	    	    	nodeEntry.addProperty("value", nodeEn.getEntryValue());
	    	    	rowObject.add("key_"+i, nodeEntry);
	    			i++;
				}
		    	nodeEntry = new JsonObject();
		    	nodeEntry.addProperty("id", 0);
		    	nodeEntry.addProperty("value", key.toString());
		    	rowObject.add("key_0", nodeEntry);
	    	   
	    	    jsonArray.add(rowObject);
	    	     	
				
			}
			nodeObj.add(node.getNodeName(), jsonArray);  
			nodesJsonAr.add(nodeObj);
			nodesObject.add("nodesData", nodesJsonAr);
			/*
			 * 
			 * Writing data to Excel
			 * 
			 * 
			 * 
			 * 
			 */
		    Map<Integer, Object[]> excelData = new TreeMap<Integer, Object[]>();
		    JsonArray jsonArray1 = (JsonArray)nodeObj.get(node.getNodeName());
		    int mapKey=0;
		    for(int j=0;j<jsonArray1.size();j++)
		    {
     	    	JsonObject jsonObj = (JsonObject)jsonArray1.get(j);
		    	Set<String> ketSet = new TreeSet<String>(jsonObj.keySet());
		    	Object[] obj = new Object[ketSet.size()] ;
		    	for (String str : ketSet) {
     	    		JsonObject valObj = (JsonObject)jsonObj.get(str);
		    		int value = Integer.parseInt(str.replaceAll("[^0-9]", ""));
		    		obj[value] = valObj.get("value");
				}
		    	excelData.put(mapKey, obj);mapKey++;
		    }
		    
		    Set<Integer> mapSet = excelData.keySet();
    	    if(node.getHeaderCount().equals("1.0"))
    	    {
    	    	Row newRow = spreadsheet.createRow(0);
    	    	Row sourceRow = sheet.getRow(0);
    	    	ExcelUtils.workSheetHeaders(newRow,sourceRow,newCellStyle);
    	    
    	    }
    	    else if(node.getHeaderCount().equals("2.0"))
    	    {
    	    	Row newFirstRow = spreadsheet.createRow(0);
    	    	Row sourceFirstRow = sheet.getRow(0);
    	    	ExcelUtils.workSheetHeaders(newFirstRow,sourceFirstRow,newCellStyle);
    	    	Row newSecondRow = spreadsheet.createRow(1);
    	    	Row sourceSecondRow = sheet.getRow(1);
    	    	ExcelUtils.workSheetHeaders(newSecondRow,sourceSecondRow,newCellStyle);
    	    }
    	    
    	    int outputExcelRowNum=0;
		    if(node.getHeaderCount().equals("1.0"))
		     outputExcelRowNum=1;
		    if(node.getHeaderCount().equals("2.0"))
			 outputExcelRowNum=2;
		    for (Integer key : mapSet)
	        {
		    	Row row = spreadsheet.createRow(outputExcelRowNum++);
		    	Object [] objArr = excelData.get(key);
		    	int cellnum = 0;
		    	for (Object obj : objArr)
	            {
		    		 XSSFCellStyle style = workbook1.createCellStyle();
		    		if(cellnum==0)
		    			ExcelUtils.customCellStyle(style);
	    	    	
		    		Cell cell = row.createCell(cellnum++);
	                if(RegExUtil.isString(obj.toString()))
	            	   cell.setCellValue(obj.toString().replaceAll("^\"|\"$", ""));
	               
	               if(RegExUtil.isDouble(obj.toString()))
	            	   cell.setCellValue(Double.parseDouble(obj.toString().replaceAll("^\"|\"$", "")));
	               cell.setCellStyle(style);
	               
	               
	            }
	        }
			
		}
		FileOutputStream out = new FileOutputStream(parameterSet.get().getFileName());
		workbook1.write(out);
	    workbook1.close();
	    out.flush();
	    out.close();
		
		return nodesObject.toString();
	}
	
	
	
	
}

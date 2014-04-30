package frame.retrieval.engine.index.create.impl.file.parse;

import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.commons.logging.Log;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import frame.base.core.util.StringClass;
import frame.retrieval.engine.common.RetrievalUtil;
import frame.retrieval.engine.index.doc.file.internal.RFileDocument;

/**
 * Excel文件内容解析器
 * @author 
 *
 */
public class ExcelFileContentParser extends AbstractFileContentParser {

	private Log log=RetrievalUtil.getLog(this.getClass());
	
	public String getContent(RFileDocument document, String charsetName) {

		InputStream fileInputStream = null;
		StringBuffer content = new StringBuffer();

		try {
			fileInputStream = new FileInputStream(document.getFile());
			HSSFWorkbook workbook = new HSSFWorkbook(fileInputStream);// 创建对Excel工作簿文件的引用
			
			if(workbook!=null){
				int numSheetsNumber=workbook.getNumberOfSheets();
				for (int numSheets = 0; numSheets < numSheetsNumber; numSheets++) {
					HSSFSheet aSheet = workbook.getSheetAt(numSheets);// 获得一个sheet
					if(aSheet!=null){
						int lastRowNum=aSheet.getLastRowNum();
						for (int rowNumOfSheet = 0; rowNumOfSheet <=lastRowNum ; rowNumOfSheet++) {
							HSSFRow aRow = aSheet.getRow(rowNumOfSheet); // 获得一个行
							if(aRow!=null){
								int lastCellNum=aRow.getLastCellNum();
								for (int cellNumOfRow = 0; cellNumOfRow <=lastCellNum ; cellNumOfRow++) {
									HSSFCell aCell = aRow.getCell(cellNumOfRow);// 获得列值
									if(aCell!=null){
										int cellType=aCell.getCellType();
										if(cellType==HSSFCell.CELL_TYPE_STRING){
											String value=StringClass.getString(aCell.getStringCellValue());
											content.append(value);
										}
									}
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			RetrievalUtil.errorLog(log,document.getFile().getAbsolutePath(), e);
		} finally {
			if (fileInputStream != null) {
				try {
					fileInputStream.close();
				} catch (Exception e) {

				}
			}
		}

		return content.toString();

	}

}

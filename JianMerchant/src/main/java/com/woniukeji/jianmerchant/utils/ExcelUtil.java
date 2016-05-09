package com.woniukeji.jianmerchant.utils;

import java.io.File;
import java.io.IOException;
import java.util.List;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;

import com.woniukeji.jianmerchant.entity.EnrolledUser;
import com.woniukeji.jianmerchant.entity.PublishUser;


/**
 * @author 冯景润
 * @version 创建时间：2015-12-5 下午1:32:57
 **/
public class ExcelUtil {

	WritableWorkbook writableWorkbook;
	WritableSheet sheet;
	
	String filename;
	
	List<PublishUser.ListTUserInfoEntity> users;
	File file;
	
	public ExcelUtil(String filename,List<PublishUser.ListTUserInfoEntity> users) throws IOException {
		this.filename = filename;
		this.users = users;
		init();
	}

	private void init() throws IOException {
		file = new File(Environment.getExternalStorageDirectory() + "/"+filename+".xls");
		writableWorkbook = Workbook.createWorkbook(file);
		sheet = writableWorkbook.createSheet("sheet1", 0);
	}

	public File create() throws WriteException, IOException {
		Label label1, label2, label3, label4;
		WritableCellFormat format = new WritableCellFormat();
		format.setAlignment(Alignment.CENTRE);
		format.setVerticalAlignment(VerticalAlignment.CENTRE);
		sheet.mergeCells(0, 0, 3, 0);
		sheet.addCell(new Label(0, 0, filename, format));
		PublishUser.ListTUserInfoEntity user;
		label1 = new Label(0, 1, "姓名", format);
		label2 = new Label(1, 1, "电话", format);
		label3 = new Label(2, 1, "性别", format);
		label4 = new Label(3, 1, "学校", format);
		sheet.setColumnView(1, 15);
		sheet.addCell(label1);
		sheet.addCell(label2);
		sheet.addCell(label3);
		sheet.addCell(label4);
		for (int i = 0; i < users.size(); i++) {
			sheet.setColumnView(i+2, 15);
			user = users.get(i);
			label1 = new Label(0, i+2, user.getName(), format);
			label2 = new Label(1, i+2, user.getTel(), format);
			label3 = new Label(2, i+2, user.getSex_resume() == 0 ? "女" : "男", format);
			label4 = new Label(3, i+2, user.getSchool(), format);
			sheet.addCell(label1);
			sheet.addCell(label2);
			sheet.addCell(label3);
			sheet.addCell(label4);
		}
		writableWorkbook.write();
		writableWorkbook.close();
		return file;
	}
	
	/** 
	 * 打开文件 
	 * @param file 
	 */  
	public static void openFileByOtherApp(Context context,File file){  
	      
	    Intent intent = new Intent();  
	    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);  
	    //设置intent的Action属性  
	    intent.setAction(Intent.ACTION_VIEW);  
	    //获取文件file的MIME类型  
	    String type = "application/vnd.ms-excel";
	    //设置intent的data和Type属性。  
	    intent.setDataAndType(/*uri*/Uri.fromFile(file), type);  
	    //跳转  
	    context.startActivity(intent);    
	      
	}  
	  
	
}

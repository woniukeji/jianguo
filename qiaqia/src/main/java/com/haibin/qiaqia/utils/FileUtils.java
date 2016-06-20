package com.haibin.qiaqia.utils;

import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

@SuppressLint("NewApi")
public class FileUtils {
	/**
	 * Get a file path from a Uri. This will get the the path for Storage Access
	 * Framework Documents, as well as the _data field for the MediaStore and
	 * other file-based ContentProviders.
	 * 
	 * @param context
	 *            The context.
	 * @param uri
	 *            The Uri to query.
	 * @author paulburke
	 */
	public static String getPath(final Context context, final Uri uri){
		
		final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
		
		//
		if(isKitKat && DocumentsContract.isDocumentUri(context, uri)){
			// ExternalStorageProvider
			if(isExternalStorageDocument(uri)){
				final String docId = DocumentsContract.getDocumentId(uri);
				final String[] split = docId.split(":");
				final String type = split[0];
				
				if("primary".equalsIgnoreCase(type)){ return Environment
						.getExternalStorageDirectory() + "/" + split[1]; }
				
				// TODO handle non-primary volumes
			}
			// DownloadsProvider
			else if(isDownloadsDocument(uri)){
				final String id = DocumentsContract.getDocumentId(uri);
				final Uri contentUri = ContentUris.withAppendedId(
						Uri.parse("content://downloads/public_downloads"),
						Long.valueOf(id));
				return getDataColumn(context, contentUri, null, null);
			}
			// MediaProvider
			else if(isMediaDocument(uri)){
				final String docId = DocumentsContract.getDocumentId(uri);
				final String[] split = docId.split(":");
				final String type = split[0];
				
				Uri contentUri = null;
				if("image".equals(type)){
					contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
				}else if("video".equals(type)){
					contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
				}else if("audio".equals(type)){
					contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
				}
				
				final String selection = "_id=?";
				final String[] selectionArgs = new String[] { split[1]};
				
				return getDataColumn(context, contentUri, selection,
						selectionArgs);
			}
		}
		// MediaStore (and general)
		else if("content".equalsIgnoreCase(uri.getScheme())){
			// Return the remote address
			if(isGooglePhotosUri(uri)) return uri.getLastPathSegment();
			return getDataColumn(context, uri, null, null);
		}
		else if("file".equalsIgnoreCase(uri.getScheme())){ return uri.getPath(); }
		return null;
	}
	
	/**
	 * Get the value of the data column for this Uri. This is useful for
	 * MediaStore Uris, and other file-based ContentProviders.
	 * 
	 * @param context
	 *            The context.
	 * @param uri
	 *            The Uri to query.
	 * @param selection
	 *            (Optional) Filter used in the query.
	 * @param selectionArgs
	 *            (Optional) Selection arguments used in the query.
	 * @return The value of the _data column, which is typically a file path.
	 */
	public static String getDataColumn(Context context, Uri uri,
			String selection, String[] selectionArgs){
		
		Cursor cursor = null;
		final String column = "_data";
		final String[] projection = { column};
		
		try{
			cursor = context.getContentResolver().query(uri, projection,
					selection, selectionArgs, null);
			if(cursor != null && cursor.moveToFirst()){
				final int index = cursor.getColumnIndexOrThrow(column);
				return cursor.getString(index);
			}
		}finally{
			if(cursor != null) cursor.close();
		}
		return null;
	}
	
	/**
	 * @param uri
	 *            The Uri to check.
	 * @return Whether the Uri authority is ExternalStorageProvider.
	 */
	public static boolean isExternalStorageDocument(Uri uri){
		return "com.android.externalstorage.documents".equals(uri
				.getAuthority());
	}
	
	/**
	 * @param uri
	 *            The Uri to check.
	 * @return Whether the Uri authority is DownloadsProvider.
	 */
	public static boolean isDownloadsDocument(Uri uri){
		return "com.android.providers.downloads.documents".equals(uri
				.getAuthority());
	}
	
	/**
	 * @param uri
	 *            The Uri to check.
	 * @return Whether the Uri authority is MediaProvider.
	 */
	public static boolean isMediaDocument(Uri uri){
		return "com.android.providers.media.documents".equals(uri
				.getAuthority());
	}
	
	/**
	 * @param uri
	 *            The Uri to check.
	 * @return Whether the Uri authority is Google Photos.
	 */
	public static boolean isGooglePhotosUri(Uri uri){
		return "com.google.android.apps.photos.content".equals(uri
				.getAuthority());
	}
	/**
	 *  新建目录
	 *  @param  folderPath  String  如  c:/fqf
	 *  @return  boolean
	 */
	public static void  newFolder(String  folderPath)  {
		try  {
			String  filePath  =  folderPath;
			filePath  =  filePath.toString();
			File  myFilePath  =  new  File(filePath);
			if  (!myFilePath.exists())  {
				myFilePath.mkdir();
			}
		}
		catch  (Exception  e)  {
			System.out.println("新建目录操作出错");
			e.printStackTrace();
		}
	}

	/**
	 *  新建文件
	 *  @param
	 *  @param  filePathAndName  String  文件路径及名称  如c:/fqf.txt
	 *  @return  boolean
	 */
	public static File newFile(String filePathAndName)  {
		File myFilePath = null;
		try  {
			String  filePath  =  filePathAndName;
			filePath  =  filePath.toString();  //取的路径及文件名
			myFilePath  =  new  File(filePath);
			/**如果文件不存在就建一个新文件*/
			if  (!myFilePath.exists())  {
				myFilePath.createNewFile();
			}
//			FileWriter  resultFile  =  new FileWriter(myFilePath);  //用来写入字符文件的便捷类, 在给出 File 对象的情况下构造一个 FileWriter 对象
//			PrintWriter myFile  =  new  PrintWriter(resultFile);  //向文本输出流打印对象的格式化表示形式,使用指定文件创建不具有自动行刷新的新 PrintWriter。
//			String  strContent  =  fileContent;
//			myFile.println(strContent);
//			resultFile.close();

		}
		catch  (Exception  e)  {
			Log.e("FileUtils", "新建文件操作出错");
			e.printStackTrace();

		}

		return myFilePath;
	}

		/**
		 *
		 * @param fromFile 被复制的文件
		 * @param toFile 复制的目录文件
		 * @param rewrite 是否重新创建文件
		 *
		 * <p>文件的复制操作方法
		 */
		public  static void copyfile(File fromFile, File toFile,Boolean rewrite ){

			if(!fromFile.exists()){
				return;
			}

			if(!fromFile.isFile()){
				return;
			}
			if(!fromFile.canRead()){
				return;
			}
			if(!toFile.getParentFile().exists()){
				toFile.getParentFile().mkdirs();
			}
			if(toFile.exists() && rewrite){
				toFile.delete();
			}


			try {
				FileInputStream fosfrom = new FileInputStream(fromFile);
				FileOutputStream fosto = new FileOutputStream(toFile);

				byte[] bt = new byte[1024];
				int c;
				while((c=fosfrom.read(bt)) > 0){
					fosto.write(bt,0,c);
				}
				//关闭输入、输出流
				fosfrom.close();
				fosto.close();


			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

}

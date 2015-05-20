package app.preview;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Base64;

public class SaveBitMap {  
	  
    private final static String CACHE = "/soosokan";  
  
//    /** 
//     * 保存图片的方法 保存到sdcard 
//     *  
//     * @throws Exception 
//     *  
//     */  
//    public static void saveImage(Bitmap bitmap, String imageName)  
//            throws Exception {  
//        String filePath = isExistsFilePath();  
//        FileOutputStream fos = null;  
//        File file = new File(filePath, imageName);  
//        try {  
//            fos = new FileOutputStream(file);  
//            if (null != fos) {  
//                bitmap.compress(Bitmap.CompressFormat.PNG, 90, fos);  
//                fos.flush();  
//                fos.close();  
//            }  
//        } catch (Exception e) {   
//        }  
//    }  
  
    /** 
     * 获取sd卡的缓存路径， 一般在卡中sdCard就是这个目录 
     *  
     * @return SDPath 
     */  
    public static String getSDPath() {  
        File sdDir = null;  
//        boolean sdCardExist = Environment.getExternalStorageState().equals(  
//                android.os.Environment.MEDIA_MOUNTED); // 判断sd卡是否存在  
//        if (sdCardExist) {  
            sdDir = Environment.getExternalStorageDirectory();// 获取根目录  
//        } else {  
////            Log.e("ERROR", "没有内存卡");  
//        }  
        return sdDir.toString();  
    }  
  
    /** 
     * 获取缓存文件夹目录 如果不存在创建 否则则创建文件夹 
     *  
     * @return filePath 
     */  
    public static String isExistsFilePath() {  
        String filePath = getSDPath() + CACHE;  
        File file = new File(filePath);  
        if (!file.exists()) {  
            file.mkdirs();  
        }  
        return filePath;  
    }  
    /** 
     * 获取SDCard文件 
     *  
     * @return Bitmap 
     */  
    public static Bitmap getImageFromSDCard(String imageName) {  
        String filepath = getSDPath() + CACHE  + "/" + imageName+".jpg";  System.out.println("get"+filepath);
        File file = new File(filepath);  
        if (file.exists()) {  
            Bitmap bm = BitmapFactory.decodeFile(filepath);  
            return bm;  
        }  
        return null;  
    }  
    
    public static String BitMapToString(Bitmap bitmap){
        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,1, baos);
        //bitmap.compress(Bitmap.CompressFormat.JPEG,1, baos);
        byte [] b=baos.toByteArray();
        String temp=Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
  }
    /**
     * @param encodedString
     * @return bitmap (from given string)
     */
    public static Bitmap StringToBitMap(String encodedString){
   try{
     byte [] encodeByte=Base64.decode(encodedString,Base64.DEFAULT);
     Bitmap bitmap=BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
     return bitmap;
   }catch(Exception e){
     e.getMessage();
     return null;
   }
    }
    
    
    /** 
       * 删除SD卡或者手机的缓存图片和目录 
       */  
      public static void deleteFile(String filename) {  

          File dirFile = new File(Environment.getExternalStorageDirectory()  +"/soosokan"+filename+".jpg");  
         if(! dirFile.exists()){  
             return;  
          }  
          if (dirFile.isDirectory()) {  
              String[] children = dirFile.list();  
              for (int i = 0; i < children.length; i++) {  
                  new File(dirFile, children[i]).delete();  
              }  
          }  
           
          dirFile.delete();  
     }
      
      public static String FileToString(String imageName){
    	//对文件的操作  
    	  FileInputStream in;
    	  String result = null;
		try {
			in = new FileInputStream(getSDPath() + CACHE  + "/" + imageName+".jpg");
			byte buffer[] = StreamUtil.read(in);//把图片文件流转成byte数组  
	    	  byte[] encod = Base64.encode(buffer,Base64.DEFAULT);//使用base64编码
	    	   result  = new String(encod); 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
    	  

		return result;
      }
}  

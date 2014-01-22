package framework.base.snoic.base.util.file;

import java.io.File;
import java.io.FileInputStream;
import java.text.DecimalFormat;

public class FileSizeHelper {
	
	public final static long FORMAT_SIZE_K = 1024;
	public final static long FORMAT_SIZE_M = 1048576;
	public final static long FORMAT_SIZE_G = 1073741824;
	
	private FileHelper fileHelper = new FileHelper();
	/**
	 * 取得文件大小
	 * @param f
	 * @return
	 * @throws Exception
	 */
	public long getFileSizes(File f) throws Exception{
        long s=0;
        FileInputStream fis = null;
         try {
			if (f.exists()) {
			     fis = new FileInputStream(f);
			    s= fis.available();
			 } else {
			     f.createNewFile();
			     System.out.println("文件不存在");
			 }
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
       	 if(fis!=null)
    		 fis.close();
		}
         return s;
     }
     /**
      * 取得文件夹大小 --递归
      * @param f
      * @return
      * @throws Exception
      */
    public long getFileSize(File f)throws Exception
    {
         long size = 0;
         File flist[] = f.listFiles();
         for (int i = 0; i < flist.length; i++)
         {
             if (flist[i].isDirectory())
             {
                 size = size + getFileSize(flist[i]);
             } else
             {
                 size = size + flist[i].length();
             }
         }
         return size;
     }

    /**
     * 转换文件大小
     * @param fileS
     * @return
     */
    public String FormetFileSize(long fileS) {
        DecimalFormat df = new DecimalFormat("#.00");
         String fileSizeString = "";
         if (fileS < 1024) {
             fileSizeString = df.format((double) fileS) + "B";
         } else if (fileS < 1048576) {
             fileSizeString = df.format((double) fileS / FORMAT_SIZE_K) + "K";
         } else if (fileS < 1073741824) {
             fileSizeString = df.format((double) fileS / FORMAT_SIZE_M) + "M";
         } else {
             fileSizeString = df.format((double) fileS / FORMAT_SIZE_G) + "G";
         }
         return fileSizeString;
     }
    
    /**
     * 递归求取目录文件个数
     * @param f
     * @return
     */
     public long getlist(File f){
        long size = 0;
         File flist[] = f.listFiles();
         size=flist.length;
         for (int i = 0; i < flist.length; i++) {
             if (flist[i].isDirectory()) {
                 size = size + getlist(flist[i]);
                 size--;
             }
         }
         return size;
    }
     
     /**
      * 得到文件夾或者是文件的大小
      * @return
      */
     public long getFilesSizes(String path){
    	 if(fileHelper.isExists(path))
    		 return getFilesSizes(new File(path));
    	 else
    		 return 0;
     }
     
     /**
      * 得到文件夾或者是文件的大小
      * @return
      */
     public long getFilesSizes(File file){
    	 long l = 0;
    	 try {
			if (file.isDirectory()) { //如果路径是文件夹的时候
			      l = getFileSize(file);
			  } else {
			      l = getFileSizes(file);
			  }
		} catch (Exception e) {
			e.printStackTrace();
		}
    	 return l;
     }
     
     /**
      * 得到轉化過的文件夾大小
      * @return
      */
     public String getFormatFilesSizes(String path){
    	 return FormetFileSize(getFilesSizes(path));
     }
     
     /**
      * 得到轉化過的文件夾大小
      * @return
      */
     public String getFormatFilesSizes(File file){
    	 return FormetFileSize(getFilesSizes(file));
     }
     
     
     public static void main(String args[])
     {
    	 FileSizeHelper g = new FileSizeHelper();
         long startTime = System.currentTimeMillis();
         try
         {
             long l = 0;
             String path = "D:\\apache-tomcat-6.0.21\\index\\DB\\B\\VIEW_THREAD_CASE_2";
             File ff = new File(path);
             if (ff.isDirectory()) { //如果路径是文件夹的时候
                System.out.println("文件个数           " + g.getlist(ff));
                 System.out.println("目录");
                 l = g.getFileSize(ff);
                 System.out.println(path + "目录的大小为：" + g.FormetFileSize(l));
             } else {
                 System.out.println("文件个数           1");
                 System.out.println("文件");
                 l = g.getFileSizes(ff);
                 System.out.println(path + "文件的大小为：" + g.FormetFileSize(l));
             }
            
         } catch (Exception e)
         {
             e.printStackTrace();
         }
         long endTime = System.currentTimeMillis();
         System.out.println("总共花费时间为：" + (endTime - startTime) + "毫秒...");
     }

}

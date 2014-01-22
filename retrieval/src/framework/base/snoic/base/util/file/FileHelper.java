/**
 * Copyright 2010 
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package framework.base.snoic.base.util.file;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import framework.base.snoic.base.util.RandomSeed;
import framework.base.snoic.base.util.StringClass;
/**
 * SnoicsFile 文件操作
 * @author 
 *
 */
public class FileHelper implements Serializable {
	private static final long serialVersionUID = -5477672686625234287L;

	

    /**
     * 取得当前路径
     * 
     * @return String
     */

    public String getCurrentPath() {
        File directory = new File(".");
        String nowPath = "";
        try {
            nowPath = directory.getCanonicalFile().toString();
        } catch (IOException e) {
        	
        }
        return nowPath;
    }
    
    /**
     * 创建文件
     * 
     * @param filename
     *            文件名称
     * @return boolean
     */
    public boolean createFile(String filename) {
    	filename=StringClass.getFormatPath(filename);
        try {
            String newfilepath = StringClass.getPreString(filename, "/");
            if (!isExists(newfilepath)) {
                File directory = new File(newfilepath);
                directory.mkdirs();
            }
            File newFile = new File(filename);
            return newFile.createNewFile();
        } catch (Exception e) {
        	
        }

        return false;
    }

    /**
     * 创建文件夹
     * @param dirName 文件夹名称
     * 
     * @return boolean
     */
    public boolean createDir(String dirName){
    	dirName=StringClass.getFormatPath(dirName);
        boolean dirflag=isDir(dirName);
        if(!dirflag){
            File dir=new File(dirName);
            return dir.mkdirs();
        }else{
        	return false;
        }
    }
    /**
     * 文件是否存在
     * 
     * @param filename1
     *            文件名称
     * @return boolean
     */

    public boolean isExists(String filename1) {
    	filename1=StringClass.getFormatPath(filename1);
        File file = new File(filename1);
        return file.exists();
    }

    /**
     * 文件是否存在
     * 
     * @param filename1
     *            文件名称
     * @return boolean
     */
    public boolean isFile(String filename1) {
    	filename1=StringClass.getFormatPath(filename1);
        File file = new File(filename1);
        if (isExists(filename1)) {
        	return file.isFile();
        } else{
            return (false);
        }
    }

    /**
     * 文件夹是否存在
     * 
     * @param filename1
     *            文件夹名称
     * @return boolean
     */

    public boolean isDir(String filename1) {
    	filename1=StringClass.getFormatPath(filename1);
        File file = new File(filename1);
        if (isExists(filename1)) {
        	return file.isDirectory();
        } else{
            return (false);
        }
    }
    
    /**
     * 获取配置文件最后修改时间
     * @param fileName
     * @return long
     */
    public long getLastModifyTime(String fileName){
    	if(!isFile(fileName)){
    		return -1;
    	}
    	fileName=StringClass.getFormatPath(fileName);
        File file = new File(fileName);
        return file.lastModified();
    }
    
    /**
     * 取得文件大小
     * @param fileName
     * @return long  if(return==0) 文件不存在
     */
    public long getFileSize(String fileName){
    	if(!isFile(fileName)){
    		return 0;
    	}
    	fileName=StringClass.getFormatPath(fileName);
        long filesize=0;
        FileInputStream in=null;
        try{
          if(isFile(fileName)){
              File checkfile=new File(fileName);
              in=new FileInputStream(checkfile);
              filesize=in.available();
          }
        }catch(Exception e){
            filesize=0;
        }finally{
            try{
                in.close();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        return filesize;
    }

    /**
     * 取得文件大小
     * @param fileName
     * @return long  if(return==0) 文件不存在
     */
    public long getFileSize(File fileName){
    	if(!fileName.isFile()){
    		return 0;
    	}
        long filesize=0;
        FileInputStream in=null;
        try{
          if(fileName.isFile()){
              in=new FileInputStream(fileName);
              filesize=in.available();
          }
        }catch(Exception e){
            
        }finally{
            try{
                in.close();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        return filesize;
    }
    
    /**
     * 文件改名
     * @param fileName
     * @param toFileName
     * @return boolean 
     */
    public boolean renameFile(String fileName,String toFileName){
    	if(!isFile(fileName)){
    		return false;
    	}
    	fileName=StringClass.getFormatPath(fileName);
    	toFileName=StringClass.getFormatPath(toFileName);
        File file=new File(fileName);
        File fileto=new File(toFileName);
        return file.renameTo(fileto);
    }

    /**
     * 文件改名
     * @param fileName
     * @param toFileName
     * @return boolean 
     */
    public boolean renameFile(File fileName,File toFileName){
    	if(!fileName.isFile()){
    		return false;
    	}
        return fileName.renameTo(toFileName);
    }
    
    /**
     * 复制文件fromfilename到文件tofilename
     * 
     * @param fromfilename
     * @param tofilename
     * @return boolean
     */
    public boolean copyFile(String fromfilename, String tofilename) {
    	fromfilename=StringClass.getFormatPath(fromfilename);
    	tofilename=StringClass.getFormatPath(tofilename);
        boolean fileflag = false;
        BufferedInputStream fin = null;
        BufferedOutputStream fout =null;
        boolean flag=false;
        try {
            File file = new File(fromfilename); //判断被复复制文件是否存在
            fileflag = file.isFile(); //如果被复复制文件不存在则返回
            if (fileflag == false) {
            	flag=(false);
            } else if (fileflag) { //如果被复复制文件存在
                String prestring = "";
                prestring = StringClass.getPreString(tofilename, "/");
                File filedir = new File(prestring);
                boolean fsflag = filedir.exists(); //判断新文件路径是否存在，如果不存在则创建
                if (fsflag == false) {
                    filedir.mkdirs();
                }
                //复制fromfilename到tofilename
                fin = new BufferedInputStream(new FileInputStream(fromfilename));
                fout = new BufferedOutputStream(new FileOutputStream(tofilename));
                byte[] buffer = new byte[1024 * 5];
                int bytesum = 0;
                int byteread = 0;
                while ((byteread = fin.read(buffer)) != -1) {
                    bytesum += byteread;
                    fout.write(buffer, 0, byteread);
                }
                flag= (true);
            }
        } catch (IOException e) {
            flag=false;
        }finally{
        	if(fout!=null) {
                try {
                    fout.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
        	}
        	if(fin!=null) {
                try {
                    fin.close();
                } catch (IOException e2) {
                    e2.printStackTrace();
                }         
        	}   
        }
        return (flag);
    }

    /**
     * 移动文件夹
     * @param frompath
     * @param topath
     * @return boolean
     */
    public boolean moveFolder(String frompath,String topath){
    	frompath=StringClass.getFormatPath(frompath);
    	topath=StringClass.getFormatPath(topath);
    	
    	if(!isDir(frompath)){
    		return false;
    	}
    	boolean flag=copyFolder(frompath,topath);
    	if(!flag){
    		return false;
    	}
    	delFolder(frompath);
    	return true;
    }
    
    /**
     * 剪切文件fromfilename到文件tofilename
     * 
     * @param fromfilename
     * @param tofilename
     * @return boolean
     */
    public boolean moveFile(String fromfilename, String tofilename) {
    	fromfilename=StringClass.getFormatPath(fromfilename);
    	tofilename=StringClass.getFormatPath(tofilename);
        boolean fileflag = false;
        BufferedInputStream fin = null;
        BufferedOutputStream fout =null;
        File file = null;
        try {
            file = new File(fromfilename); //判断被移动文件是否存在
            fileflag = file.isFile(); //如果文件不存在则返回
            if (fileflag == false){
                return (false);
            }else if (fileflag) { //如果文件存在
                String prestring = "";
                prestring = StringClass.getPreString(tofilename, "/");
                File filedir = new File(prestring); //判断新文件路径是否存在，不存在则创建
                boolean fsflag = filedir.exists();
                if (fsflag == false) {
                    filedir.mkdirs();
                }
                //复制filename1到filename2
                fin = new BufferedInputStream(
                        new FileInputStream(fromfilename));
                fout = new BufferedOutputStream(
                        new FileOutputStream(tofilename));
                byte[] buffer = new byte[1024 * 5];
                int bytesum = 0;
                int byteread = 0;
                while ((byteread = fin.read(buffer)) != -1) {
                    bytesum += byteread;
                    fout.write(buffer, 0, byteread);
                }
                fileflag=true;
            }
            if(fileflag){
                deleteFile(fromfilename); //删除filename1
            }
            
        } catch (IOException e) {
            fileflag=false;
        }finally{
        	if(fout!=null) {
                try {
                    fout.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
        	}
        	if(fin!=null) {
                try {
                    fin.close();
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
        	}
        }
        
        return (fileflag);
    }

    /**
     * 删除文件
     * 
     * @param filename
     * @return boolean
     */

    public boolean deleteFile(String filename) {
    	filename=StringClass.getFormatPath(filename);
        File file = new File(filename);
        boolean fileflag = file.isFile();
        if (fileflag == false){
            return (false);
        }else{
            file.delete();
            return (true);
        }
    }

    /**
     * 删除文件夹
     * 
     * @param folderPath
     *            String 文件夹路径及名称 如c:/fqf
     * @return boolean
     */

    public boolean delFolder(String folderPath) {
    	folderPath=StringClass.getFormatPath(folderPath);
    	boolean flag=true;
        try {
            delAllFile(folderPath); //删除完里面所有内容
            File filePath = new File(folderPath);
            filePath.delete(); //删除空文件夹
            flag=true;
        } catch (Exception e) {
        	flag=false;
        }
        return flag;
    }

    /**
     * 删除文件夹里面的所有文件
     * 
     * @param path
     *            String 文件夹路径 如 c:/fqf
     * @return boolean
     */

    public boolean delAllFile(String path) {
    	path=StringClass.getFormatPath(path);
        File file = new File(path);
        boolean flag=true;
        if (!isDir(path)) {
            return false;
        }
        String[] tempList = file.list();
        File temp = null;
        for (int i = 0; i < tempList.length; i++) {
            if (path.endsWith(File.separator)) {
                temp = new File(path + tempList[i]);
            } else {
                temp = new File(path + File.separator + tempList[i]);
            }
            if (temp.isFile()) {
                temp.delete();
            }
            if (temp.isDirectory()) {
                delAllFile(path + "/" + tempList[i]);//先删除文件夹里面的文件
                delFolder(path + "/" + tempList[i]);//再删除空文件夹
            }
        }
        return flag;
    }

    /**
     * 复制整个文件夹内容
     * 
     * @param oldPath
     *            String 原文件路径 如：c:/fqf
     * @param newPath
     *            String 复制后路径 如：f:/fqf/ff
     * @return boolean
     */
    public boolean copyFolder(String oldPath, String newPath) {
    	oldPath=StringClass.getFormatPath(oldPath);
    	if(!isDir(oldPath)){
    		return false;
    	}
    	boolean flag=true;
    	newPath=StringClass.getFormatPath(newPath);
        FileInputStream input = null;
        FileOutputStream output = null;
        try {
            (new File(newPath)).mkdirs(); //如果文件夹不存在 则建立新文件夹
            File a = new File(oldPath);
            String[] file = a.list();
            File temp = null;
            for (int i = 0; i < file.length; i++) {
                try {
					if (oldPath.endsWith(File.separator)) {
					    temp = new File(oldPath + file[i]);
					} else {
					    temp = new File(oldPath + File.separator + file[i]);
					}

					if (temp.isFile()) {
					    input = new FileInputStream(temp);
					    output = new FileOutputStream(newPath
					            + "/" + (temp.getName()).toString());
					    byte[] b = new byte[1024 * 5];
					    int len;
					    while ((len = input.read(b)) != -1) {
					        output.write(b, 0, len);
					    }
					    output.flush();
					}
					if (temp.isDirectory()) {//如果是子文件夹
					    copyFolder(oldPath + "/" + file[i], newPath + "/" + file[i]);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}finally{
					if(output!=null) {
		                try {
		                    output.close();
		                    output=null;
		                } catch (IOException e1) {
		                    e1.printStackTrace();
		                }
		        	}
		        	if(input!=null) {
		                try {
		                    input.close();
		                    input=null;
		                } catch (IOException e2) {
		                    e2.printStackTrace();
		                }
		        	}
				}
            }
        } catch (Exception e) {
        	flag=false;
        }finally{
        	if(output!=null) {
                try {
                    output.close();
                    output=null;
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
        	}
        	if(input!=null) {
                try {
                    input.close();
                    input=null;
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
        	}
        }
        return flag;
    }

    /**
     * 将文件内容读到字符串中
     * @param filename 文件名
     * @return String
     */
    public String fileToString(String filename){
    	filename=StringClass.getFormatPath(filename);
    	StringBuffer filestring=new StringBuffer("");
    	if(!isFile(filename)){
    		return "";
    	}
    	BufferedReader bf = null;
    	try{
            bf = new BufferedReader(new FileReader(filename));
            filestring=new StringBuffer();
            String temp="";
            while ((temp=bf.readLine()) != null) {
            	filestring.append(temp);
            	filestring.append("\n");
            }
    	}catch(Exception e){
    		filestring=new StringBuffer();
    	}finally {
    		if(bf!=null) {
    			try {
    				bf.close();
    			}catch(Exception e) {
    				e.printStackTrace();
    			}
    		}
    	}
    	return filestring.toString();
    }

    /**
     * 将文件内容读到字符串中
     * @param filename 文件名
     * @param charsetName 字符集
     * @return String
     */
    public String fileToString(String filename,String charsetName){

		charsetName=StringClass.getString(charsetName);

		if(charsetName.equals("")){
			return fileToString(filename);
		}
		
    	filename=StringClass.getFormatPath(filename);
    	StringBuffer filestring=new StringBuffer("");
    	if(!isFile(filename)){
    		return "";
    	}
    	InputStreamReader bf=null;
    	try{
    		bf=new InputStreamReader(new FileInputStream(filename),charsetName); 
            filestring=new StringBuffer();
            int c;
            while((c=bf.read())>-1){
                filestring.append((char)c);
            }
    	}catch(Exception e){
    		filestring=new StringBuffer();
    	}finally {
    		if(bf!=null) {
    			try {
    				bf.close();
    			}catch(Exception e) {
    				e.printStackTrace();
    			}
    		}
    	}
    	return filestring.toString();
    }
    
    /**
     * 从BufferedReader中读取固定的行数到字符串中
     * @param bf BufferedReader
     * @param fromline 从第几行开始
     * @param toline 到第几行结束
     * @return String
     */
    public String getLineToString(BufferedReader bf,int fromline,int toline){
    	StringBuffer linetostring=null;
    	if(bf!=null){
    		int count=0;
    		linetostring=new StringBuffer();
    		String temp ="";
    	    try{
                while (((temp = bf.readLine()) != null)&&(count<=toline)) {
                    if(count>=fromline){
                    	linetostring.append(temp);
                    	linetostring.append("\n");
                    }
                    count++;
                }
    	    }catch(Exception e){
    		
    	    }
    	    return linetostring.toString();
    	}else{
    		return "";
    	}
    }
    
    /**
     * 将filelist中的所有文件的内容按顺序写入newfile中
     * 
     * @param filelist
     * @param tofilename
     * @return boolean
     */
    public boolean filesToFile(ArrayList filelist, String tofilename) {
    	tofilename=StringClass.getFormatPath(tofilename);
        BufferedReader bf = null;
        PrintWriter out1 = null;
        boolean flag=true;
        try {
            File file;
            String newfilepath = StringClass.getPreString(tofilename, "/"); //判断目标路径是否存在，不存在则创建
            boolean fileflagnewfile = isExists(newfilepath);
            if (!fileflagnewfile) {
                file = new File(newfilepath);
                file.mkdirs();
            }
            fileflagnewfile = isExists(tofilename); //判断目标文件是否存在，如果不存在则创建
            if (!fileflagnewfile) {
                file = new File(tofilename);
                file.createNewFile();
            }
            out1 = new PrintWriter(new BufferedWriter(new FileWriter(tofilename)));

            Iterator it = filelist.iterator(); //将所有新文件的内容都读入字符串中，如果其中的文件不存在则跳过该文件
            while (it.hasNext()) {
                String filename = (String) it.next();
                filename=StringClass.getFormatPath(filename);
                boolean fileflag = isExists(filename);
                if (fileflag) {
                    try {
						bf = new BufferedReader(new FileReader(
						        filename));
						String temp = new String();
						while ((temp = bf.readLine()) != null) {
//                    	out1.println(temp + "\n");
							out1.println(temp);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}finally{
						if(bf!=null) {
			                try {
			                    bf.close();
			                } catch (IOException e1) {
			                    e1.printStackTrace();
			                }
			        	}
					}
                } else {
                    System.out.println("Can't found " + filename + "\n");
                }
            }
        } catch (Exception e) {
        	flag=false;
        }finally{
        	if(bf!=null) {
                try {
                    bf.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
        	}
        	if(out1!=null) {
        		try {
                    out1.close();
        		}catch(Exception e) {
        			e.printStackTrace();
        		}
        	}
        }
        return flag;
    }

    /**
     * 将filelist中的所有文件的内容按顺序写入newfile中已有内容的后面
     * 
     * @param filelist
     * @param newfile
     * @return boolean
     */
    public boolean appendFilesToFile(LinkedList filelist, String newfile) {
		newfile = StringClass.getFormatPath(newfile);

		BufferedReader bf = null;
		PrintWriter out = null;
		
		String tempfilename="";
		boolean flag=true;
		try {
			File file;
			String newfilepath = StringClass.getPreString(newfile, "/"); // 判断目标路径是否存在，不存在则创建
		    tempfilename=newfilepath+"/"+RandomSeed.getRandomString(RandomSeed.NUMBER_TYPE);
			copyFile(newfile,tempfilename);
			filelist.addFirst(tempfilename);
			
			boolean fileflagnewfile = isExists(newfilepath);
			if (!fileflagnewfile) {
				file = new File(newfilepath);
				file.mkdirs();
			}
			fileflagnewfile = isExists(newfile); // 判断目标文件是否存在，如果不存在则创建
			if (!fileflagnewfile) {
				file = new File(newfile);
				file.createNewFile();
			}
			out = new PrintWriter(new BufferedWriter(new FileWriter(newfile)));

			Iterator it = filelist.iterator(); // 将所有新文件的内容都读入字符串中，如果其中的文件不存在则跳过该文件
			while (it.hasNext()) {
				String filename = (String) it.next();
				filename = StringClass.getFormatPath(filename);
				boolean fileflag = isExists(filename);
				if (fileflag) {
					bf = new BufferedReader(new FileReader(filename));
					String temp1 = new String();
					while ((temp1 = bf.readLine()) != null) {
//						out.println(temp1 + "\n");
						out.println(temp1);
					}
				} else {
					System.out.println("Can't found " + filename + "\n");
				}
			}
		} catch (Exception e) {
			flag=false;
		} finally {
			if(bf!=null) {
				try {
					bf.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			if(out!=null) {
				try {
					out.close();
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
			deleteFile(tempfilename);
		}
		return flag;
	}

    /**
	 * 将filelist中的所有字符串按顺序写入newfile中
	 * 
	 * @param string
	 * @param tofilename
     * @return boolean
	 */
    public boolean stringToFile(String string, String tofilename) {
    	tofilename=StringClass.getFormatPath(tofilename);
    	PrintWriter out1 = null;
        boolean flag=true;
        try {
            File file;
            String newfilepath = StringClass.getPreString(tofilename, "/");
            boolean fileflag = isExists(newfilepath); //判断目标文件夹是否存在，如果不存在则创建
            if (!fileflag) {
                file = new File(newfilepath);
                file.mkdirs();
            }
            fileflag = isExists(tofilename); //判断目标文件是否存在，如果不存在则创建
            if (!fileflag) {
                file = new File(tofilename);
                file.createNewFile();
            }
            
            //将内容写入目标文件
            out1 = new PrintWriter(new BufferedWriter(new FileWriter(tofilename)));
            out1.println(string);
        } catch (Exception e) {
        	flag=false;
        }finally{
        	if(out1!=null) {
        		try {
                    out1.close();
        		}catch(Exception e) {
        			e.printStackTrace();
        		}
        	}
        }
        return flag;
    }
    
    /**
	 * 将filelist中的所有字符串按顺序写入newfile中
	 * 
	 * @param filelist
	 * @param tofilename
     * @return boolean
	 */
    public boolean stringToFile(ArrayList filelist, String tofilename) {
    	tofilename=StringClass.getFormatPath(tofilename);
        PrintWriter out1 = null;
        boolean flag=true;
        try {
            File file;
            String newfilepath = StringClass.getPreString(tofilename, "/");
            boolean fileflag = isExists(newfilepath); //判断目标文件夹是否存在，如果不存在则创建
            if (!fileflag) {
                file = new File(newfilepath);
                file.mkdirs();
            }
            fileflag = isExists(tofilename); //判断目标文件是否存在，如果不存在则创建
            if (!fileflag) {
                file = new File(tofilename);
                file.createNewFile();
            }
            
            //将内容写入目标文件
            out1 = new PrintWriter(new BufferedWriter(new FileWriter(tofilename)));
            Iterator it = filelist.iterator(); // 将要写入的新的内容读入到字符串中
            while (it.hasNext()) {
                String newstring = (String) it.next();
//              out1.println(newstring + "\n");
                out1.println(newstring);
            }
        } catch (Exception e) {
        	flag=false;
        }finally{
        	if(out1!=null) {
        		try {
                    out1.close();
        		}catch(Exception e) {
        			e.printStackTrace();
        		}
        	}
        }
        return flag;
    }

    /**
     * 将filelist中的所有字符串按顺序写入filename中已有内容的后面
     * 
     * @param filelist
     * @param filename
     * @return boolean
     */
    public boolean appendStringToFile(ArrayList filelist, String filename) {
    	filename=StringClass.getFormatPath(filename);
        String tempfilename1="";
        String tempfilename2="";
        boolean flag=true;
        try {
            boolean fileflag = isExists(filename);
            //写入文件
            File file;
            String newfilepath = StringClass.getPreString(filename, "/");
            fileflag = isExists(newfilepath);
            if (!fileflag) {
                file = new File(newfilepath);
                file.mkdirs();
            }
            fileflag = isExists(filename);
            if (!fileflag) {
                file = new File(filename);
                file.createNewFile();
            }
            
		    tempfilename1=newfilepath+"/"+RandomSeed.getRandomString(RandomSeed.NUMBER_TYPE);
			copyFile(filename,tempfilename1);
			
			tempfilename2=newfilepath+"/"+RandomSeed.getRandomString(RandomSeed.NUMBER_TYPE);
			stringToFile(filelist,tempfilename2);
			
			ArrayList arraylist=new ArrayList();
			arraylist.add(tempfilename1);
			arraylist.add(tempfilename2);
			filesToFile(arraylist,filename);
        } catch (Exception e) {
        	flag=false;
        }finally{
        	deleteFile(tempfilename1);
        	deleteFile(tempfilename2);
        }
        return flag;
    }

    /**
     * 将字符串写入文件中
     * 
     * @param string
     *            字符串
     * @param filename
     *            要写入的文件
     * @return boolean
     */

    public boolean appendStringToFile(String string, String filename) {
    	ArrayList arraylist=new ArrayList();
    	arraylist.add(string);
    	boolean flag=appendStringToFile(arraylist,filename);
    	return flag;
    }
    
    /**
     * 把文件读到BufferedReader中
     * @param filename
     * @return BufferedReader
     */
    public BufferedReader fileToBufferedReader(String filename){
    	filename=StringClass.getFormatPath(filename);
    	if(!isFile(filename)){
    		return null;
    	}else{
    		try{
        		BufferedReader bf = new BufferedReader(new FileReader(filename));
        		return bf;
    		}catch(Exception e){
    			
    		}
    	}
    	return null;
    }
    
    /**
     * 把BufferedReader写入文件
     * @param bf
     */
    public boolean bufferedReaderToFile(BufferedReader bf,String tofilename){
    	tofilename=StringClass.getFormatPath(tofilename);
    	if(!isFile(tofilename)){
    		createFile(tofilename);
    	}
    	boolean flag=true;
    	PrintWriter out = null;
    	try{
    		out = new PrintWriter(new BufferedWriter(new FileWriter(tofilename)));
    	}catch(Exception e){
    		flag=false;
    	}
        String temp = new String();
        try{
            while ((temp = bf.readLine()) != null) {
//            	out.println(temp + "\n");
            	out.println(temp);
            }
        }catch(Exception e){
        	flag=false;
        }finally {
        	if(bf!=null) {
        		try {
        			bf.close();
        		}catch(Exception e) {
        			e.printStackTrace();
        		}
        	}
        }
        
        if(out!=null) {
        	try {
                out.close();
        	}catch(Exception e) {
        		e.printStackTrace();
        	}
        }
        return flag;
    }
    
    /**
     * 把文件读到OutputStream中
     * @param filename
     * @return OutputStream
     */
    public OutputStream fileToOutputStream(String filename){
    	filename=StringClass.getFormatPath(filename);
    	if(!isFile(filename)){
    		return null;
    	}else{
    		try{
    			FileOutputStream output = new FileOutputStream(filename); 
        		return output;
    		}catch(Exception e){
    			
    		}
    	}
    	return null;
    }
    
    /**
     * 把文件读到InputStream中
     * @param filename
     * @return InputStream
     */
    public InputStream fileToInputStream(String filename){
    	filename=StringClass.getFormatPath(filename);
    	if(!isFile(filename)){
    		return null;
    	}else{
    		try{
    	        BufferedInputStream bufferedInputStream=new BufferedInputStream(new FileInputStream(filename));
        		return bufferedInputStream;
    		}catch(Exception e){
    			
    		}
    	}
    	return null;
    }
    
    /**
     * 把InputStream读到文件中
     * @param inputStream
     * @param filename
     * @return boolean
     */
    public boolean inputStreamToFile(InputStream inputStream,String filename){
    	BufferedInputStream  bufferedInputStream=new BufferedInputStream(inputStream);
    	filename=StringClass.getFormatPath(filename);
    	if(!isFile(filename)){
    		createFile(filename);
    	}
    	boolean flag=true;
    	BufferedOutputStream bufferedOutputStream = null;
        try{
            bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(filename));
            int content;
            while((content=bufferedInputStream.read()) != -1){
            	bufferedOutputStream.write(content);
            }
        	bufferedOutputStream.flush();
        }catch(Exception exception){
            flag=false;
        }finally{
        	if(bufferedOutputStream!=null) {
            	try{
            		bufferedOutputStream.close();
            	}catch(Exception e){
            		e.printStackTrace();
            	}
        	}
        	if(bufferedInputStream!=null) {
            	try{
            		bufferedInputStream.close();
            	}catch(Exception e){
            		e.printStackTrace();
            	}
        	}
        	if(inputStream!=null) {
            	try{
            		inputStream.close();
            	}catch(Exception e){
            		e.printStackTrace();
            	}
        	}
        }
        return flag;
    }    
    
    /**
     * 把文件读到DataInputStream中
     * @param filename
     * @return DataInputStream
     */
    public DataInputStream fileToDataInputStream(String filename){
    	filename=StringClass.getFormatPath(filename);
    	if(!isFile(filename)){
    		return null;
    	}else{
    		try{
    			DataInputStream input = new DataInputStream(new BufferedInputStream(new FileInputStream(filename)));
        		return input;
    		}catch(Exception e){
    			
    		}
    	}
    	return null;
    }
    
    /**
     * 把DataInputStream写入文件
     * @param datainputstream
     * @param tofilename
     * @return boolean
     */
    public boolean dataInputStreamToFile(DataInputStream datainputstream,String tofilename){
    	tofilename=StringClass.getFormatPath(tofilename);
    	if(!isFile(tofilename)){
    		createFile(tofilename);
    	}
    	boolean flag=true;
    	DataOutputStream dataoutputstream = null;
    	try{
    	    dataoutputstream = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(tofilename)));
			byte b[] = new byte[1024*10];
			int len = 0;
			while ((len = datainputstream.read(b, 0, 1024)) != -1) {
				dataoutputstream.write(b, 0, len);
			}

			dataoutputstream.flush();
    	}catch(Exception e){
    		flag=false;
    	}finally {
    		if(datainputstream!=null) {
    			try {
    				datainputstream.close();
    			}catch(Exception e) {
    				e.printStackTrace();
    			}
    		}
    		if(dataoutputstream!=null) {
    			try {
    				dataoutputstream.close();
    			}catch(Exception e) {
    				e.printStackTrace();
    			}
    		}
    	}
    	return flag;
    }
    
	/**
	 * 返回路径path下所有文件列表,包括子文件夹
	 * @param path 文件路径
	 * @return ArrayList
	 */
	public ArrayList getAllFileList(String path){
		path=StringClass.getFormatPath(path);
		path=path+"/";
		File filePath=new File(path);
		if(!isDir(path)){
			return null;
		}
		String[] filelist=filePath.list();
		ArrayList filelistFilter=new ArrayList();

		for(int i=0;i<filelist.length;i++){
			String tempfilename=StringClass.getFormatPath(path+filelist[i]);
			File filterFile=new File(tempfilename);
			if(filterFile.isFile()){
			  filelistFilter.add(tempfilename);	
			}else if(filterFile.isDirectory()){
				filelistFilter.addAll(getAllFileList(tempfilename));
			}
		}
				
		return filelistFilter;
	}
	
	/**
	 * 获取文件夹下的所以文件列表
	 * @param path
	 * @return String[]
	 */
	public String[] getList(String path) {
		path=StringClass.getFormatPath(path);
		if(!isDir(path)) {
			return null;
		}
		File file=new File(path);
		String[] list=file.list();
		return list;
	}
	
	/**
	 * 返回路径path下所有文件列表,不包括子文件夹
	 * @param path 文件路径
	 * @return ArrayList
	 */
	public ArrayList getFileList(String path){
		path=StringClass.getFormatPath(path);
		path=path+"/";
		File filePath=new File(path);
		if(!isDir(path)){
			return null;
		}
		String[] filelist=filePath.list();
		ArrayList filelistFilter=new ArrayList();

		for(int i=0;i<filelist.length;i++){
			String tempfilename=StringClass.getFormatPath(path+filelist[i]);
			File filterFile=new File(tempfilename);
			if(filterFile.isFile()){
			  filelistFilter.add(tempfilename);	
			}
		}
				
		return filelistFilter;
	}

	/**
	 * 返回路径path下所有后缀名为extend的文件列表,包括子文件夹
	 * @param path 文件路径
	 * @param extend 后缀名,如果需要返回多种类型的文件，请用','隔开
	 * @return ArrayList
	 */
	public ArrayList getAllFileList(String path,String extend){
		
		ArrayList extendlist=StringClass.getInterString(",",extend);
		
		path=StringClass.getFormatPath(path);
		path=path+"/";
		File filePath=new File(path);
		if(!isDir(path)){
			return null;
		}
		String[] filelist=filePath.list();
		ArrayList filelistFilter=new ArrayList();

		for(int i=0;i<filelist.length;i++){
			int extendIndex=0;
			extendIndex=filelist[i].lastIndexOf(".");
			if(extendIndex<0){
				extendIndex=0;
			}
			String theExtend="";
			if(extendIndex==0){
				theExtend="";
			}else{
		        theExtend=filelist[i].substring(extendIndex+1,filelist[i].length());
			}
			if(!extend.equals("")){
				String tempfilename=StringClass.getFormatPath(path+filelist[i]);
			    File filterFile=new File(tempfilename);
			    if(filterFile.isFile()){
			      if(extendlist.contains(theExtend)){
			        filelistFilter.add(tempfilename);
			      }
			    }else if(filterFile.isDirectory()){
			    	filelistFilter.addAll(getAllFileList(tempfilename,extend));
			    }
		    }else{
				  String tempfilename=StringClass.getFormatPath(path+filelist[i]);
			      File filterFile=new File(tempfilename);
			      if(filterFile.isFile()){
			    	  if(extendIndex==0){
			             filelistFilter.add(tempfilename);
			    	  }
			      }else if(filterFile.isDirectory()){
				    	filelistFilter.addAll(getAllFileList(tempfilename,extend));
				  }
			}

		}
		return filelistFilter;
	}
	
	/**
	 * 返回路径path下所有后缀名为extend的文件列表,不包括子文件夹
	 * @param path 文件路径
	 * @param extend 如果需要返回多种类型的文件，请用','隔开
	 * @return ArrayList
	 */
	public ArrayList getFileList(String path,String extend){
		ArrayList extendlist=StringClass.getInterString(",",extend);
		path=StringClass.getFormatPath(path);
		path=path+"/";
		File filePath=new File(path);
		if(!isDir(path)){
			return null;
		}
		String[] filelist=filePath.list();
		ArrayList filelistFilter=new ArrayList();

		for(int i=0;i<filelist.length;i++){
			int extendIndex=0;
			extendIndex=filelist[i].lastIndexOf(".");
			if(extendIndex<0){
				extendIndex=0;
			}
			String theExtend="";
			if(extendIndex==0){
				theExtend="";
			}else{
		        theExtend=filelist[i].substring(extendIndex+1,filelist[i].length());
			}
			if(!extend.equals("")){
				String tempfilename=StringClass.getFormatPath(path+filelist[i]);
			    File filterFile=new File(tempfilename);
			    if(filterFile.isFile()){
			    	if(extendlist.contains(theExtend)){
			          filelistFilter.add(tempfilename);
			    	}
			    }
			}else{
				  String tempfilename=StringClass.getFormatPath(path+filelist[i]);
			      File filterFile=new File(tempfilename);
			      if(filterFile.isFile()){
			    	  if(extendIndex==0){
			            filelistFilter.add(tempfilename);
			    	  }
			      }
			}

		}
		return filelistFilter;
	}
	
	/**
	 * 返回路径path下所有包含字符串includestring的文件列表,包括子文件夹
	 * @param path 文件路径
	 * @param includestring 文件中包含的字符串,如果需要多个字符串，请用','隔开
	 * @return ArrayList
	 */
	public ArrayList getAllFileListIncludeString(String path,String includestring){
		ArrayList filelistFilter=new ArrayList();
		includestring=StringClass.getString(includestring,"");
		if(includestring.equals("")){
			return null;
		}
		
		ArrayList includestringlist=StringClass.getInterString(",",includestring);
		int includestringlength=includestringlist.size();
		
		path=StringClass.getFormatPath(path);
		path=path+"/";
		File filePath=new File(path);
		
		if(!isDir(path)){
			return null;
		}
		
		String[] filelist=filePath.list();

		for (int i = 0; i < filelist.length; i++) {
			String tempfilename = StringClass.getFormatPath(path + filelist[i]);
			if (isFile(tempfilename)) {
				String filestring = fileToString(tempfilename);
				for (int j = 0; j < includestringlength; j++) {
					if (filestring.indexOf((String) includestringlist.get(j)) > -1) {
						filelistFilter.add(tempfilename);
					}
				}
			} else if (isDir(tempfilename)) {
				filelistFilter.addAll(getAllFileListIncludeString(tempfilename,includestring));
			}
		}
		return filelistFilter;
	}
	
	/**
	 * 返回路径path下所有包含字符串includestring的文件列表,不包括子文件夹
	 * @param path 文件路径
	 * @param includestring 文件中包含的字符串,如果需要多个字符串，请用','隔开
	 * @return ArrayList
	 */
	public ArrayList getFileListIncludeString(String path,String includestring){
		
		includestring=StringClass.getString(includestring,"");
		if(includestring.equals("")){
			return null;
		}
		
		ArrayList includestringlist=StringClass.getInterString(",",includestring);
		int includestringlength=includestringlist.size();
		
		path=StringClass.getFormatPath(path);
		path=path+"/";
		File filePath=new File(path);
		
		if(!isDir(path)){
			return null;
		}
		
		String[] filelist=filePath.list();
		ArrayList filelistFilter=new ArrayList();

		for (int i = 0; i < filelist.length; i++) {
			String tempfilename = StringClass.getFormatPath(path + filelist[i]);
			if (isFile(tempfilename)) {
				String filestring = fileToString(tempfilename);
				for (int j = 0; j < includestringlength; j++) {
					if (filestring.indexOf((String) includestringlist.get(j)) > -1) {
						filelistFilter.add(tempfilename);
					}
				}
			}
		}
		return filelistFilter;
	}
	
	/**
	 * 返回路径path下所有包含字符串includestring的文件列表,包括子文件夹
	 * @param path 文件路径
	 * @param includestring 文件中包含的字符串,如果需要多个字符串，请用','隔开
	 * @param extend 如果需要返回多种类型的文件，请用','隔开
	 * @return ArrayList
	 */
	public ArrayList getAllFileListIncludeString(String path,String includestring,String extend){
		ArrayList filelistFilter=new ArrayList();
		includestring=StringClass.getString(includestring,"");
		if(includestring.equals("")){
			return null;
		}
		
		path=StringClass.getFormatPath(path);
		path=path+"/";
		if(!isDir(path)){
			return null;
		}
		
		ArrayList includestringlist=StringClass.getInterString(",",includestring);

		ArrayList extendfilelist=getAllFileList(path,extend);
		
		int includestringlength=includestringlist.size();
		
		int filelistlength=0;
		try{
			filelistlength=extendfilelist.size();
		}catch(Exception e){
			
		}
		for (int i = 0; i < filelistlength; i++) {
			String tempfilename = StringClass.getFormatPath((String)extendfilelist.get(i));
			String filestring = fileToString(tempfilename);
			for (int j = 0; j < includestringlength; j++) {
				if (filestring.indexOf((String) includestringlist.get(j)) > -1) {
					filelistFilter.add(tempfilename);
				}
			}
		}
		return filelistFilter;
	}
	
	/**
	 * 返回路径path下所有包含字符串includestring的文件列表,不包括子文件夹
	 * @param path 文件路径
	 * @param includestring 文件中包含的字符串,如果需要多个字符串，请用','隔开
	 * @param extend 如果需要返回多种类型的文件，请用','隔开
	 * @return ArrayList
	 */
	public ArrayList getFileListIncludeString(String path,String includestring,String extend){
		ArrayList filelistFilter=new ArrayList();
		includestring=StringClass.getString(includestring,"");
		if(includestring.equals("")){
			return null;
		}
		
		path=StringClass.getFormatPath(path);
		path=path+"/";
		if(!isDir(path)){
			return null;
		}
		
		ArrayList includestringlist=StringClass.getInterString(",",includestring);

		ArrayList extendfilelist=getFileList(path,extend);
		
		int includestringlength=includestringlist.size();
		
		int filelistlength=0;
		try{
			filelistlength=extendfilelist.size();
		}catch(Exception e){
			
		}
		for (int i = 0; i < filelistlength; i++) {
			String tempfilename = StringClass.getFormatPath((String)extendfilelist.get(i));
			String filestring = fileToString(tempfilename);
			for (int j = 0; j < includestringlength; j++) {
				if (filestring.indexOf((String) includestringlist.get(j)) > -1) {
					filelistFilter.add(tempfilename);
				}
			}
		}
		return filelistFilter;
	}
	
	/**
	 * 获取文件后缀名
	 * @param filename
	 * @return
	 */
	public String getFileType(String filename){
		String fileType="";
		filename=StringClass.getFormatPath(filename);
		if(filename.indexOf(".")<0){
			return "";
		}else{
			fileType=filename.substring(filename.lastIndexOf(".")+1,filename.length());
		}
		
		return fileType;
	}
	
	/**
	 * 获取文件后缀名
	 * @param file
	 * @return
	 */
	public String getFileType(File file){
		String fileType="";
		fileType=getFileType(file.getAbsolutePath());
		return fileType;
	}
	
	/**
	 * 将byte[]转为文件
	 * @param theBytes
	 * @param tofilename
	 */
	public void byteToFile(byte[] theBytes,String tofilename){
		
		InputStream in = new ByteArrayInputStream(theBytes);
		try{
			DataInputStream dataInputStream = new DataInputStream(in);
			dataInputStreamToFile(dataInputStream, tofilename);
		}finally{
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	/**
	 * 保存对象到文件中
	 * @param object 对象
	 * @param filename 文件名
     * @return boolean
	 */
	public boolean saveObject(Object object,String filename){
		filename=StringClass.getFormatPath(filename);
		if(!isFile(filename)){
			createFile(filename);
		}
		boolean flag=true;
		ObjectOutputStream objectOutputStream = null;
		try{
			objectOutputStream = new ObjectOutputStream(new FileOutputStream(filename));
			objectOutputStream.writeObject(object);
			objectOutputStream.flush();
		}catch(Exception e){
		    flag=false;
		}finally {
			if(objectOutputStream!=null) {
				try{
					objectOutputStream.close();
				}catch(Exception e1){
					e1.printStackTrace();
				}
			}
		}
		return flag;
	}
	
	/**
	 * 把对象从文件中读取出来
	 * @param filename
	 * @return Object
	 */
	public Object readObject(String filename){
		Object object=null;
		filename=StringClass.getFormatPath(filename);
		if(!isFile(filename)){
			return null;
		}
	    ObjectInputStream objectInputStream = null;
	    try{
	    	objectInputStream=new ObjectInputStream(new FileInputStream(filename));
	    	object=objectInputStream.readObject() ;
	    	objectInputStream.close();
	    }catch(Exception e){
	    	object=null;
	    }finally {
			if(objectInputStream!=null) {
				try{
					objectInputStream.close();
				}catch(Exception e1){
					e1.printStackTrace();
				}
			}
	    }
		return object;
	}
	
	/**
	 * 将byte转换 成file
	 * @param bytes
	 * @param file
	 */
	public void byte2File(byte[] bytes ,File file){
		InputStream in = null;
		try {
			in = new ByteArrayInputStream(bytes);
			inputStream2Flie(in,file);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				if(in!=null)
					in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 将inputStream装成file文件
	 * @param in
	 * @param file
	 */
	public void inputStream2Flie(InputStream in , File file){
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(file);
			int size=0;
			byte[] Buffer = new byte[4096*5];
			while((size=in.read(Buffer))!=-1)
			{
				out.write(Buffer,0,size);
			}
			out.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				if(in!=null)
					in.close();
				if(out!=null)
					out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
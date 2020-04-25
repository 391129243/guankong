package com.learn.all_electric.utils;

import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 文件中的工具类
 */
public class AndroidFileUtils {
    /**
     *
     * @param string
     * @param filePath
     * @param fileName
     */
    public static void writeStringToFile(String string, String filePath, String fileName){
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        File file;
        try{
            file = new File(filePath,fileName);
            if(file.exists()){
                boolean isdelete = file.delete();
                LogUtil.i("删除文件",isdelete +"");
            }
            File dir = new File(filePath);
            if(!dir.exists() &&!dir.isDirectory()){
                boolean is_mkdir = dir.mkdirs();
                LogUtil.i("创建文件夹",is_mkdir+"");
            }

            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(string.getBytes());
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(null != bos){
                try {
                    bos.close();;
                }catch (IOException e1){
                    e1.printStackTrace();
                }
            }

            if(null != fos){
                try{
                    fos.close();
                }catch (IOException e1){
                    e1.printStackTrace();
                }
            }
        }

    }

    /**
     * 删除文件方法
     */
    public static void deleteFile(File file){
        if(file.exists()){
           boolean is_delete =  file.delete();
            LogUtil.i("删除文件",is_delete +"");
        }
    }

    public static String readJsonData(String filePath) throws IOException {
        // 读取文件数据
        //System.out.println("读取文件数据util");

        StringBuffer strbuffer = new StringBuffer();
        File myFile = new File(filePath);//"D:"+File.separatorChar+"DStores.json"
        LogUtil.i("1111",myFile.exists()+"");
        if (!myFile.exists()) {
            System.err.println("Can't Find " + filePath);
        }
        try {
            FileInputStream fis = new FileInputStream(filePath);
            InputStreamReader inputStreamReader = new InputStreamReader(fis, "UTF-8");
            BufferedReader in  = new BufferedReader(inputStreamReader);

            String str;
            while ((str = in.readLine()) != null) {
                strbuffer.append(str);  //new String(str,"UTF-8")
            }
            in.close();
        } catch (IOException e) {
            e.getStackTrace();
        }
        //System.out.println("读取文件结束util");
        return strbuffer.toString();
    }

}

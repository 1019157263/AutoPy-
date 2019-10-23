package whstywh.com.accessibilityservicedemo;

import android.accessibilityservice.AccessibilityService;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import java.util.List;
import android.accessibilityservice.*;
import android.graphics.*;
import java.io.*;

import java.io.File;  
import java.io.InputStreamReader;  
import java.io.BufferedReader;  
import java.io.BufferedWriter;  
import java.io.FileInputStream;  
import java.io.FileWriter;
import org.apache.http.util.*;



public class MyAccessibilityService extends AccessibilityService
{

    @Override
	int n=0;
    public void onAccessibilityEvent(AccessibilityEvent event)
	{



		String fileName = "/sdcard/AutoPy.config";				// assets下文件
		//	String fileName = "/sdcard/test/my_sdcard_test.txt";	// sdcard下子目录文件
		String ret = "";

		try
		{
			FileInputStream fis = new FileInputStream(fileName);

			int len = fis.available();
			byte []buffer = new byte[len];

			fis.read(buffer);
			ret = EncodingUtils.getString(buffer, "UTF-8");

			fis.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		String [] stringArr= ret.split(",");
		//System.out.println(stringArr);
		String x=stringArr[0];

		for (int i=0;i < stringArr.length;i++)
		{

			System.out.println(stringArr[i]);
			System.out.println(",");
		}
		if (stringArr[0].equals("0"))
		{
			System.out.println("是零模式");
			//  Integer.parseInt(stringArr[1]);

			GestureDescription.Builder builder = new GestureDescription.Builder();
			Path p = new Path();
			p.moveTo(Integer.parseInt(stringArr[1]), Integer.parseInt(stringArr[2]));//坐标
			//p.lineTo(200,200);
			builder.addStroke(new GestureDescription.StrokeDescription(p, 0, 100));
			dispatchGesture(builder.build(), null, null);
			writeTxt("/sdcard/AutoPy.config","执行成功");
			


		}
		if (stringArr[0].equals("1"))
		{
			System.out.println("是一模式");
			//  Integer.parseInt(stringArr[1]);
			GestureDescription.Builder builder = new GestureDescription.Builder();
			Path p = new Path();
			p.moveTo(Integer.parseInt(stringArr[1]), Integer.parseInt(stringArr[2]));//坐标
			p.lineTo(Integer.parseInt(stringArr[3]), Integer.parseInt(stringArr[4]));
			builder.addStroke(new GestureDescription.StrokeDescription(p, Integer.parseInt(stringArr[5]), Integer.parseInt(stringArr[5])));
			dispatchGesture(builder.build(), null, null);
			//File file = new File("/sdcard/AutoPy.config");
			writeTxt("/sdcard/AutoPy.config","执行成功");
			


		}



    }
    @Override
    public void onInterrupt()
	{

    }
	
	public static void writeTxt(String txtPath,String content){    
		FileOutputStream fileOutputStream = null;
		File file = new File(txtPath);
		try {
			if(file.exists()){
				//判断文件是否存在，如果不存在就新建一个txt
				file.createNewFile();
			}
			fileOutputStream = new FileOutputStream(file);
			fileOutputStream.write(content.getBytes());
			fileOutputStream.flush();
			fileOutputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
	
	
}

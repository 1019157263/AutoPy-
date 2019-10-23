package whstywh.com.accessibilityservicedemo;

import android.accessibilityservice.*;
import android.app.*;
import android.content.*;
import android.graphics.*;
import android.os.*;
import android.provider.*;
import android.support.v7.app.*;
import android.view.*;
import android.view.accessibility.*;
import java.io.*;
import java.util.*;

import android.app.AlertDialog;
import android.widget.*;

public class MainActivity extends AppCompatActivity
{


    private AlertDialog mAlertDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (!isServiceEnabled())
		{
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("请开启无障碍服务");
            builder.setNegativeButton("取消", null);
            builder.setPositiveButton("开启", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which)
					{
						//打开系统无障碍设置界面
						Intent accessibleIntent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
						startActivity(accessibleIntent);
					}
				});

            mAlertDialog = builder.create();
            mAlertDialog.show();
        }
    }


    public void install(View view)
	{

		String xx="def dian(x,y):\n" +
			"    f=open('/sdcard/AutoPy.config','w+')\n" +
			"    f.write('0,'+str(x)+','+str(y))\n" +
			"    f.close()\n" +
			"def tuo(x1,y1,x2,y2,t=1000):\n" +
			"    f=open('/sdcard/AutoPy.config','w+')\n" +
			"    f.write('1,'+str(x1)+','+str(y1)+','+str(x2)+','+str(y2)+','+str(t))\n" +
			"    f.close()\n" +
			"#作者:sunny开始学坏";

		writeTxt("/sdcard/qpython/Dian.py", xx);
		
		Toast.makeText(this, "写入成功!", Toast.LENGTH_SHORT).show();


    }


	public static void writeTxt(String txtPath, String content)
	{    
		FileOutputStream fileOutputStream = null;
		File file = new File(txtPath);
		try
		{
			if (file.exists())
			{
				//判断文件是否存在，如果不存在就新建一个txt
				file.createNewFile();
			}
			fileOutputStream = new FileOutputStream(file);
			fileOutputStream.write(content.getBytes());
			fileOutputStream.flush();
			fileOutputStream.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
    }


	public class MyAccessibilityService extends AccessibilityService
	{

		@Override
		public void onAccessibilityEvent(AccessibilityEvent p1)
		{
			// TODO: Implement this method


			GestureDescription.Builder builder = new GestureDescription.Builder();
			Path p = new Path();
			p.moveTo(100, 100);
			p.lineTo(200, 200);
			builder.addStroke(new GestureDescription.StrokeDescription(p, 1000, 1));
			dispatchGesture(builder.build(), null, null);


		}

		@Override
		public void onInterrupt()
		{
			// TODO: Implement this method
		}


		public void dispatchGestureView001(int startTime, int x, int y)
		{
			//Point position = new Point(x, y);
			GestureDescription.Builder builder = new GestureDescription.Builder();
			Path p = new Path();
			p.moveTo(x, y);
			p.lineTo(200, 200);
			builder.addStroke(new GestureDescription.StrokeDescription(p, startTime, 1));
			dispatchGesture(builder.build(), null, null);
		}



		private void dispatchGestureView(int x, int y)
		{
			Point position = new Point(x, y);
			GestureDescription.Builder builder = new GestureDescription.Builder();
			Path p = new Path();
			p.moveTo(position.x, position.y);
			builder.addStroke(new GestureDescription.StrokeDescription(p, 0L, 500L));
			GestureDescription gesture = builder.build();
			dispatchGesture(gesture, new GestureResultCallback() {
					@Override
					public void onCompleted(GestureDescription gestureDescription)
					{
						super.onCompleted(gestureDescription);
						//Log.d( "onCompleted: 完成..........");
					}

					@Override
					public void onCancelled(GestureDescription gestureDescription)
					{
						super.onCancelled(gestureDescription);
						//Log.d(TAG, "onCompleted: 取消..........");
					}
				}, null);
		}



	}



	public void Dian(View view)
	{

		//dispatchGestureView001(1000,100,100);
		//MyAccessibilityService
		System.out.println("运行到这了");
		MyAccessibilityService xx= new MyAccessibilityService();
		xx.dispatchGestureView001(1000, 100, 100);
		xx.dispatchGestureView(100, 100);
		//xx.onAccessibilityEvent();
		System.out.println("运行结束");

	}

    /**
     * 检查无障碍服务是否开启
     */
    private boolean isServiceEnabled()
	{
        AccessibilityManager accessibilityManager = (AccessibilityManager) getSystemService(Context.ACCESSIBILITY_SERVICE);

        List<AccessibilityServiceInfo> accessibilityServices =
			accessibilityManager.getEnabledAccessibilityServiceList(
			AccessibilityServiceInfo.FEEDBACK_ALL_MASK);

        for (AccessibilityServiceInfo info : accessibilityServices)
		{
            if (info.getId().contains("whstywh.com.accessibilityservicedemo"))
			{
                return true;
            }
        }
        return false;
    }
}

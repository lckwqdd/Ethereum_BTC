package com.mirko.androidutil.utils.android;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Log工具类，具备打印规则控制
 */
public class LogUtils
{

    private static  int SDCARD_LOG_FILE_SAVE_DAYS = 3; //日志最多保存天数
    static String PROJECT_LOG = "ProjectLog" ;

    static boolean V_DEBUG = true;
    static boolean I_DEBUG = false;
    static boolean E_DEBUG = false;
    static boolean W_DEBUG = false;

    /**
     * 初始化程序log打印规则
     * @param v_log : true 使能 v 等级log ; false 关闭 v 等级log
     * @param i_log : true 使能 i 等级log ; false 关闭 i 等级log
     * @param e_log : true 使能 e 等级log ; false 关闭 e 等级log
     */
    public static void InitLogUtils(boolean w_log ,boolean v_log, boolean i_log, boolean e_log) {
        V_DEBUG = v_log ;
        I_DEBUG = i_log ;
        E_DEBUG = e_log ;
        W_DEBUG = w_log ;
    }

    public static void v(String tag, String msg) {
        if (V_DEBUG) {
            android.util.Log.v(tag, msg);
        }

    }

    public static void v(String tag, String msg, Throwable t) {
        if (V_DEBUG) {
            android.util.Log.v(tag, msg, t);
        }

    }

    public static void w(String tag, String msg) {
        if (V_DEBUG) {
            android.util.Log.w(tag, msg);
        }

    }


    public static void w(String tag, String msg, Throwable t) {
        if (V_DEBUG) {
            android.util.Log.w(tag, msg, t);
        }

    }

    public static void i(String tag, String msg) {
        if (V_DEBUG) {
            android.util.Log.i(tag, msg);
        }

    }

    public static void i(String tag, String msg, Throwable t) {
        if (V_DEBUG) {
            android.util.Log.i(tag, msg, t);
        }

    }

    public static void e(String tag, String msg) {
        if (V_DEBUG) {
            android.util.Log.e(tag, msg);
        }

    }

    public static void e(String tag, String msg, Throwable t) {
        if (V_DEBUG) {
            android.util.Log.e(tag, msg, t);
        }

    }

    public static void d(String tag, String msg) {
        if (V_DEBUG) {
            android.util.Log.d(tag, msg);
//			appendLog("[debug]" + msg);
        }

    }


    public static void d(String tag, String msg, Throwable t) {
        if (V_DEBUG) {
            android.util.Log.d(tag, msg, t);
        }

    }

    private static final Object locker = new Object();
    private static void appendLog(String text) {
        synchronized (locker) {
            try {
                long tMills = System.currentTimeMillis();
                String date = new SimpleDateFormat("yyyy-MM-dd").format(tMills);
                String time = new SimpleDateFormat("HH:mm:ss").format(tMills);
                String fileName = date + ".log";
//				String path = StorageUtil.getDirectoryByDirType(StorageType.TYPE_LOG);
//				String path = Environment.getExternalStorageDirectory().getPath()  + "/" + getPackageName() + "/cache"+"/log"+"/";
                String path = "";
                // 创建目录
                File dir = new File(path);
                if (!dir.exists())
                    dir.mkdir();
                // 打开文件
                File file = new File(path + File.separator + fileName);
                FileWriter filerWriter = new FileWriter(file, true);// 后面这个参数代表是不是要接上文件中原来的数据，不进行覆盖
                BufferedWriter bufWriter = new BufferedWriter(filerWriter);
                bufWriter.write(time + "\t" + text + "\n");
                bufWriter.newLine();
                bufWriter.close();
                filerWriter.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 得到现在时间前的几天日期，用来得到需要删除的日志文件
     * */
    private static Date getDateBefore() {
        Date nowtime = new Date();
        Calendar now = Calendar.getInstance();
        now.setTime(nowtime);
        now.set(Calendar.DATE, now.get(Calendar.DATE) - SDCARD_LOG_FILE_SAVE_DAYS);
        return now.getTime();
    }

    private static String getMethodClassName(Exception ex, int lev) {
        try {
            StackTraceElement methodStack = ex.getStackTrace()[lev];
            String result = methodStack.getClassName();
            int lastIndex = result.lastIndexOf(".");
            result = result.substring(lastIndex + 1);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}

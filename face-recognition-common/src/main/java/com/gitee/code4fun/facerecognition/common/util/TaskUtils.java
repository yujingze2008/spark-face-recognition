package com.gitee.code4fun.facerecognition.common.util;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @author yujingze
 * @data 2018/9/11
 */
public class TaskUtils {

    private static final long DEFAULT_DELAY = 3 * 1000;

    private static final long DEFAULT_PERIOD = 5 * 1000;


    public static void run(TimerTask task) {
        run(task, DEFAULT_DELAY, DEFAULT_PERIOD);
    }

    public static void run(TimerTask task, long delay, long period) {
        new Timer().schedule(task, delay, period);
    }

}

package com.bootdo.train.tasks;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;

@Component
public class ClearTemporaryFilesTask {

    @Scheduled(cron = "0 0 0 1/1 * ?")
    public void task() {
        System.out.println("清除临时文件定时任务开启+++++++++");
        try {
            String path = ResourceUtils.getURL("classpath:").getPath() + "image/doc/";
            traverseFolder(path);
        } catch (FileNotFoundException e) {
            System.out.println("清除临时文件定时任务出错==错误信息：{}");
        }
        System.out.println("清除临时文件定时任务结束---------");
    }

    /*
        找到 文件下的所有文件并且干掉
     */
    public static void traverseFolder(String path) {
        File file = new File(path);
        if (file.exists()) {
            File[] files = file.listFiles();
            if (null == files || files.length == 0) {
                System.out.println("文件夹是空的!");
                return;
            } else {
                for (File file2 : files) {
                    if (file2.isDirectory()) {
                        traverseFolder(file2.getAbsolutePath());
                    } else {
                        /*
                            移除
                         */
                        file2.delete();
                        System.out.println("移除文件成功:" + file2.getAbsolutePath());
                    }
                }
            }
        } else {
            System.out.println("文件不存在!");
            return;
        }
    }


}

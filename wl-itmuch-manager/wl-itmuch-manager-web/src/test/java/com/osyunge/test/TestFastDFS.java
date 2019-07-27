package com.osyunge.test;

import com.osyunge.utils.FastDFSClient;
import org.csource.common.MyException;
import org.csource.fastdfs.*;
import org.junit.Test;

import java.io.IOException;

public class TestFastDFS {
    //F:\WebProjectFile_JAVA10\wl-itmuch-manager\wl-itmuch-manager-web\src\main\resources\properties
    @Test
    public void testUploadFile() throws IOException, MyException {
        ClientGlobal.init("F:\\WebProjectFile_JAVA10\\wl-itmuch-manager\\wl-itmuch-manager-web\\src\\main\\resources\\properties\\client.conf");
        TrackerClient trackerClient = new TrackerClient();
        TrackerServer trackerServer = trackerClient.getConnection();
        StorageServer storageServer = null;
        StorageClient storageClient = new StorageClient(trackerServer,storageServer);
        String[] strings = storageClient.upload_file("F:\\1.jpg", "jpg", null);
        for (int i=0;i<strings.length;i++){
            System.out.println(strings[i]);
        }
    }

    @Test
    public void testUploadFileUtil() throws Exception {
        FastDFSClient client = new FastDFSClient("F:\\WebProjectFile_JAVA10\\wl-itmuch-manager\\wl-itmuch-manager-web\\src\\main\\resources\\properties\\client.conf");
        String jpg = client.uploadFile("F:\\1.jpg", "jpg");
        System.out.println(jpg);
    }
}

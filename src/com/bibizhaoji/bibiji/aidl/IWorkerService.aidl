package com.bibizhaoji.bibiji.aidl;
import com.bibizhaoji.bibiji.aidl.IPPClient;

interface IWorkerService{
    boolean register(in IPPClient client);
    boolean start();
    boolean stop();
    boolean shutdown();
}
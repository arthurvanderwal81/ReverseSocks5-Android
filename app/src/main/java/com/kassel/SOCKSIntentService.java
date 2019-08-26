package com.kassel;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import java.io.IOException;

import sockslib.common.methods.NoAuthenticationRequiredMethod;
import sockslib.server.SocksProxyServer;
import sockslib.server.SocksServerBuilder;
import sockslib.server.listener.LoggingListener;

public class SOCKSIntentService extends IntentService {
    public SOCKSIntentService() {
        super("SOCKSIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        try {
            SocksServerBuilder builder = SocksServerBuilder.newSocks5ServerBuilder();

            builder.setBindPort(9999);
            builder.setSocksMethods(new NoAuthenticationRequiredMethod());

            final SocksProxyServer server = builder.build();

            Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
                @Override
                public void run() {
                    server.shutdown();
                    Log.i("", "SOCKS5 server shutdown");
                }
            }));

            server.getSessionManager().addSessionListener("logger", new LoggingListener());
            server.start();
        } catch (IOException ioException) {
            Log.e("Exception", ioException.getMessage());
        }
    }
}

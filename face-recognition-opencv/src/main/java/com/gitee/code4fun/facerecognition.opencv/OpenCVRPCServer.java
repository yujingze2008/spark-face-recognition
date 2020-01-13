package com.gitee.code4fun.facerecognition.opencv;

import com.gitee.code4fun.facerecognition.opencv.service.OpenCVServiceGrpcImpl;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.opencv.core.Core;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @author yujingze
 * @data 18/7/30
 */
public class OpenCVRPCServer {

    private static final Logger logger = LoggerFactory.getLogger(OpenCVRPCServer.class);

    private int port;
    private Server server;

    private void start() throws IOException {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        server = ServerBuilder.forPort(5666)
                .addService(new OpenCVServiceGrpcImpl())
                .build()
                .start();

        logger.info("Server started, listening on " + port);
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                // Use stderr here since the logger may have been reset by its JVM shutdown hook.
                logger.error("*** shutting down gRPC server since JVM is shutting down");
                OpenCVRPCServer.this.stop();
                logger.error("*** server shut down");
            }
        });
    }

    private void stop() {
        if (server != null) {
            server.shutdown();
        }
    }

    /**
     * Await termination on the main thread since the grpc library uses daemon threads.
     */
    private void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    public static void main(String[] args) throws Exception {
        final OpenCVRPCServer server = new OpenCVRPCServer();
        server.start();
        server.blockUntilShutdown();
    }

}

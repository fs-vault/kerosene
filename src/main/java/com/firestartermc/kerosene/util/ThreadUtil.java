package com.firestartermc.kerosene.util;

import net.minecraft.server.v1_16_R2.MinecraftServer;

public class ThreadUtil {

    private ThreadUtil() {
    }

    public static boolean isMainThread() {
        return Thread.currentThread() == MinecraftServer.getServer().serverThread;
    }

}

package com.firestartermc.kerosene.util;

import com.firestartermc.kerosene.Kerosene;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitScheduler;
import org.jetbrains.annotations.NotNull;

import javax.annotation.concurrent.ThreadSafe;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

/**
 * Miscellaneous concurrency utility methods.
 * <p>
 * This class provides methods for writing concurrent and thread
 * safe code. It also provides easy-to-use, thread-safe utilities
 * for interacting with Minecraft NMS and the Bukkit API.
 *
 * @author Firestarter Minecraft Servers
 * @since 5.0
 */
@ThreadSafe
public final class ConcurrentUtils {

    private ConcurrentUtils() {
    }

    /**
     * Ensures that a given {@link Runnable} is ran on the primary server
     * thread. If this method is called off-main, a {@link BukkitScheduler}
     * task is created to execute the runnable on main.
     * <p>
     * This is useful for creating thread-safe utility methods where parts
     * need to interact with Minecraft NMS or non-thread-safe Bukkit API
     * methods.
     *
     * @param runnable the runnable to execute on main
     * @since 5.0
     */
    public static void ensureMain(@NotNull Runnable runnable) {
        if (!Bukkit.isPrimaryThread()) {
            Bukkit.getScheduler().runTask(Kerosene.getKerosene(), runnable);
        } else {
            runnable.run();
        }
    }

    /**
     * Executes a {@link Callable<Void>} asynchronously as a future, returning a
     * {@link CompletableFuture<Void>} that completes once the given callable has
     * finished executing. If the callable throws an exception, the future
     * completes exceptionally with the exception that was thrown.
     *
     * @param callable callable to execute
     * @return future which completes once callable execution succeeds
     * @since 5.0
     */
    @Deprecated(forRemoval = true)
    public static CompletableFuture<Void> callAsync(@NotNull Callable<Void> callable) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                callable.call();
                return null;
            } catch (Exception e) {
                throw new CompletionException(e);
            }
        });
    }

    @NotNull
    public static CompletableFuture<Void> callAsync(@NotNull CheckedConsumer consumer) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                consumer.accept();
                return null;
            } catch (Exception e) {
                throw new CompletionException(e);
            }
        });
    }

    @FunctionalInterface
    public interface CheckedConsumer {
        void accept() throws Exception;
    }
}

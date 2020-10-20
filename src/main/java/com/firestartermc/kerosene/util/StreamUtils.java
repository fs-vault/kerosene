package com.firestartermc.kerosene.util;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.concurrent.ThreadSafe;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Miscellaneous {@link InputStream} utility methods.
 * <p>
 * This class provides methods for interacting with streams. It also
 * provides thread-safe utilities for reading and converting streams
 * into other types.
 *
 * @author Firestarter Minecraft Servers
 * @see Player
 * @since 4.0
 */
@ThreadSafe
public final class StreamUtils {

    private StreamUtils() {
    }

    /**
     * Reads an {@link InputStream} and converts its data into a
     * {@link String}. This is useful when reading a file and converting
     * it into a readable string. If an {@link Exception} is thrown while
     * reading the stream, a null string is returned.
     *
     * @param stream the stream to convert
     * @return the string result of the stream's contents
     * @since 4.0
     */
    @Nullable
    public static String streamToString(@NotNull InputStream stream) {
        try {
            var reader = new InputStreamReader(stream);
            var bufferedReader = new BufferedReader(reader);
            var stringBuilder = new StringBuilder();
            var separator = System.getProperty("line.separator");

            String string;
            while ((string = bufferedReader.readLine()) != null) {
                stringBuilder.append(string).append(separator);
            }

            return stringBuilder.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

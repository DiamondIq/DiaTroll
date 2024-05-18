package me.diamond.trollplugin.utils;

import org.bukkit.Bukkit;

import java.util.regex.Pattern;

public class ServerVersionUtils {
    private static final Pattern VERSION_PATTERN = Pattern.compile("\\d+(\\.\\d+)*");

    private ServerVersionUtils() {
        // Private constructor to prevent instantiation
    }

    public static boolean isVersionGreaterThan(String version) {
        String serverVersion = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
        return compareVersions(serverVersion, version) > 0;
    }

    public static boolean isVersionGreaterThanOrEqualTo(String version) {
        String serverVersion = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
        return compareVersions(serverVersion, version) >= 0;
    }


    public static int compareVersions(String version1, String version2) {
        version1 = version1.replaceAll("[v_]", "").replaceAll("[^\\d.]", "");
        version2 = version2.replaceAll("[v_]", "").replaceAll("[^\\d.]", "");

        String[] v1Array = version1.split("\\.");
        String[] v2Array = version2.split("\\.");

        int length = Math.max(v1Array.length, v2Array.length);

        for (int i = 0; i < length; i++) {
            int v1 = i < v1Array.length ? Integer.parseInt(v1Array[i]) : 0;
            int v2 = i < v2Array.length ? Integer.parseInt(v2Array[i]) : 0;

            if (v1 < v2) {
                return -1;
            } else if (v1 > v2) {
                return 1;
            }
        }

        // Handle the case when all parts are equal
        return 0;
    }

}

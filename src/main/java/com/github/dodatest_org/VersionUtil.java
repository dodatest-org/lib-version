package com.github.dodatest_org;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public final class VersionUtil {

    private static final String VERSION_RESOURCE = "/version.txt";
    private static volatile String cachedVersion;

    private VersionUtil() {
        // utility class
    }


    // Retrieve library version
    public static String getVersion() {
        String v = cachedVersion;
        if (v == null) {
            synchronized (VersionUtil.class) {
                if (cachedVersion == null) {
                    cachedVersion = loadVersion();
                }
                v = cachedVersion;
            }
        }
        return v;
    }

    private static String loadVersion() {
        // Read from JAR manifest metadata
        Package pkg = VersionUtil.class.getPackage();
        if (pkg != null) {
            String impl = pkg.getImplementationVersion();
            if (impl != null && !impl.isEmpty()) {
                return impl;
            }
        }

        // Read from resource file 
        try (InputStream in = VersionUtil.class.getResourceAsStream(VERSION_RESOURCE)) {
            if (in == null) {
                return "UNKNOWN";
            }
            String content = new String(in.readAllBytes(), StandardCharsets.UTF_8).trim();
            return content.isEmpty() ? "UNKNOWN" : content;
        } catch (Exception e) {
            return "UNKNOWN";
        }
    }
}

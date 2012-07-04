package grimbo.portlet.test;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.pluto.util.assemble.io.WebXmlStreamingAssembly;

/**
 * Simple main class that rewrites a web.xml to contain the PlutoInvoker servlet
 * entries. This should really be a Maven plugin!
 * 
 * @author grimbo
 */
public class RewriteWebXml {
    /**
     * Main method that takes the build output directory as the first and only
     * argument.
     * 
     * @param args
     * 
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        File target = new File(args[0]);

        File[] dirs = target.listFiles(new FileFilter() {
            public boolean accept(File pathname) {
                return pathname.isDirectory();
            }
        });

        for (int i = 0; i < dirs.length; i++) {
            // For each folder, check if there is a portlet.xml.
            // If there is, assume it's an expanded portlet webapp.
            File portletXml = new File(dirs[i], "WEB-INF/portlet.xml");
            if (portletXml.exists()) {
                rewriteWebXml(portletXml);
            }
        }
    }

    /**
     * For the portletXml File, make a backup of its sibling web.xml file, and
     * rewrite web.xml to include a PlutoInvoker servlet entry per portlet.
     * 
     * @param portletXml
     * 
     * @throws IOException
     */
    private static void rewriteWebXml(File portletXml) throws IOException {
        OutputStream assembledWebXmlOut = null;
        InputStream portletXmlIn = null;
        InputStream webXmlIn = null;

        try {
            portletXmlIn = new FileInputStream(portletXml);

            File webXml = new File(portletXml.getParentFile(), "web.xml");
            File webXmlBackup = new File(webXml.getParentFile(), "web.xml.backup");
            FileUtils.copyFile(webXml, webXmlBackup);

            webXmlIn = new FileInputStream(webXmlBackup);

            assembledWebXmlOut = new FileOutputStream(webXml);

            // Use the default dispatch class
            String dispatchServletClass = null;
            WebXmlStreamingAssembly.assembleStream(webXmlIn, portletXmlIn, assembledWebXmlOut, dispatchServletClass);
        } finally {
            IOUtils.closeQuietly(portletXmlIn);
            IOUtils.closeQuietly(webXmlIn);
            IOUtils.closeQuietly(assembledWebXmlOut);
        }
    }
}

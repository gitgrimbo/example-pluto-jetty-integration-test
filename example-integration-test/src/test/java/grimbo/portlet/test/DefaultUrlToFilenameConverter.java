package grimbo.portlet.test;

public class DefaultUrlToFilenameConverter implements UrlToFilenameConverter {

    public String convert(String url) {
        String filename = url;
        filename = filename.replaceAll("/", "__");
        filename = filename.replaceAll("\\?", "_Q_");
        filename = filename.replaceAll("\\&", "_A_");
        filename = filename.replaceAll("#", "_H_");
        filename = filename.replaceAll("=", "_E_");
        filename = filename.replaceAll("[^\\p{L}\\p{N}_\\-]", "");
        return filename;
    }

}

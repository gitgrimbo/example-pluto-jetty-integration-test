package grimbo.portlet.test;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestDefaultUrlToFilenameConverter {
    UrlToFilenameConverter u2f = new DefaultUrlToFilenameConverter();

    @Test
    public void testWhenSlashTestReturn__Test() {
        assertEquals("", "__test", u2f.convert("/test"));
    }

    @Test
    public void testWhenSlashReturn() {
        assertEquals("", "__", u2f.convert("/"));
    }

    @Test
    public void testWhenMultiplePathItems() {
        assertEquals("", "__multiple__path__items", u2f.convert("/multiple/path/items"));
    }

    @Test
    public void testWhenUrlWithQueryParamReplaceWithQAndEAndA() {
        assertEquals("", "__multiple_Q_name1_E_value1_A_name2_E_value2", u2f.convert("/multiple?name1=value1&name2=value2"));
    }

    @Test
    public void testWhenUrlWithHashReplaceWithH() {
        assertEquals("", "__path_H_name", u2f.convert("/path#name"));
    }
}

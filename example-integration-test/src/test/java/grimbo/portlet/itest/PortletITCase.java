package grimbo.portlet.itest;

import static net.sourceforge.jwebunit.junit.JWebUnit.*;
import static org.junit.Assert.*;
import grimbo.portlet.test.DefaultUrlToFilenameConverter;
import grimbo.portlet.test.UrlToFilenameConverter;

import java.io.File;
import java.io.IOException;

import net.sourceforge.jwebunit.api.IElement;
import net.sourceforge.jwebunit.junit.WebTester;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;

public class PortletITCase {
    String username = "pluto";
    String password = "pluto";
    WebTester tester;
    UrlToFilenameConverter urlToFilenameConverter = new DefaultUrlToFilenameConverter();
    boolean save = true;

    @Before
    public void before() {
        tester = getTester();
        login(username, password);
    }

    private void login(String username, String password) {
        // Example Pluto login form
        //@formatter:off
		/*
<form method="POST" action="j_security_check">
  <fieldset>
    <legend>Login to Pluto</legend>
    <div>
      <label for="j_username">User Name</label>
      <input type="text" name="j_username" id="j_username"/>
    </div>
    <div>
      <label for="j_password">Password</label>
      <input type="password" name="j_password" id="j_password"/>
    </div>
    <div>
      <label for="j_login"></label>
      <input type="submit" value="Login" name="login" id="j_login"/>
    </div>
  </fieldset>
</form>
		*/
		//@formatter:on
        beginAt("/pluto/portal");

        // write(new File("login.html"), tester.getPageSource());

        assertFormElementPresent("j_username");
        assertFormElementPresent("j_password");

        setTextField("j_username", username);
        setTextField("j_password", password);

        submit();
    }

    @Test
    public void whenViewingTest1PagePortlet1IsPresent() {
        gotoPageAndSave("/pluto/portal/Test1");
        String toFind = "this-is-test-portlet-1";
        assertTextPresent(toFind);
    }

    @Test
    public void whenViewingTest2PagePortlet2IsPresent() {
        gotoPageAndSave("/pluto/portal/Test2");
        String toFind = "this-is-test-portlet-2";
        assertTextPresent(toFind);
    }

    @Test
    public void whenViewingTest1BlankThemePagePortlet1IsPresent() {
        gotoPageAndSave("/pluto/portal/Test1-blank-theme");
        String toFind = "this-is-test-portlet-1";
        assertEquals("Only source on the page is portlet source", toFind, getPageSource());
    }

    @Test
    public void whenViewingTest2BlankThemePagePortlet2IsPresent() {
        gotoPageAndSave("/pluto/portal/Test2-blank-theme");
        String toFind = "this-is-test-portlet-2";
        assertEquals("Only source on the page is portlet source", toFind, getPageSource());
    }

    @Test
    public void test3() {
        // There is no Test3 page, so we expect an error
        gotoPageAndSave("/pluto/portal/Test3");
        String src = tester.getPageSource();
        // write(new File("test3.html"), src);
        String toFind = "this-is-test-portlet-1";
        assertTrue(toFind + " should not be found", -1 == src.indexOf(toFind));
    }

    private void gotoPageAndSave(String url) {
        gotoPage(url);

        if (!save) {
            return;
        }

        try {
            String filename = urlToFilenameConverter.convert(url);
            String src = getPageSource();
            write(new File(".", filename), src);
        } catch (Exception e) {
            // Don't let save stop the tests.
            e.printStackTrace();
        }
    }

    private void write(File f, String s) {
        try {
            FileUtils.write(f, s);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private IElement xpath(String xpath) {
        try {
            return getElementByXPath(xpath);
        } catch (AssertionError e) {
            e.printStackTrace();
            return null;
        }
    }

    private void delay(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}

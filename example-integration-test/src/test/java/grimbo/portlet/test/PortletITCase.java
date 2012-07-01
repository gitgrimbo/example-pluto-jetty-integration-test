package grimbo.portlet.test;

import static net.sourceforge.jwebunit.junit.JWebUnit.*;
import static org.junit.Assert.*;

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
    public void test1() {
        gotoPage("/pluto/portal/Test1");
        // write(new File("test1.html"), tester.getPageSource());
        String toFind = "this-is-test-portlet-1";
        assertTextPresent(toFind);
    }

    @Test
    public void test2() {
        gotoPage("/pluto/portal/Test2");
        // write(new File("test2.html"), tester.getPageSource());
        String toFind = "this-is-test-portlet-2";
        assertTextPresent(toFind);
    }

    @Test
    public void test3() {
        // There is no Test3 page, so we expect an error
        gotoPage("/pluto/portal/Test3");
        String src = tester.getPageSource();
        // write(new File("test3.html"), src);
        String toFind = "this-is-test-portlet-1";
        assertTrue(toFind + " should not be found", -1 == src.indexOf(toFind));
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

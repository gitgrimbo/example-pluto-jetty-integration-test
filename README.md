What is in this repo?
===

This repo attempts to demonstrate a simple integration test of portlets using [Jetty] and [Pluto].

test-portlet-1 and test-portlet-2
---

These are *very* simple portlet web applications. They contain basically the exact same portlet, but under two different classnames for easier identification.

```
App             |  Portlet class                     |  Portlet name
------------------------------------------------------------------
test-portlet-1  |  grimbo.portlet.test1.TestPortlet  |  TestPortlet1
test-portlet-2  |  grimbo.portlet.test2.TestPortlet  |  TestPortlet2
```

pluto-jetty-test-parent
---

This project is the parent pom for an integration test. It contains common dependencies, plugin config, etc.

The basic idea is that an integration test will have these steps:

1. Copy the Pluto webapp into "target". This will give us a copy to play with. I.e. change the `pluto-portal-driver-config.xml` file.

1. Copy a project-local copy of `pluto-portal-driver-config.xml` into the copied Pluto webapp. This lets us create our own pages, and place the right portlets on them for the integration test.

1. Use the `jetty-maven-plugin` plugin to start Jetty in the `pre-integration-test` phase.

1. Jetty will be pointed to a project-local copy of `jetty.xml` and `realm.properties`, which will allow us to aggregate together the webapps that we need for the integration tests.
So the copy of the Pluto webapp will be declared here, along with any other webapps that are required for the integration tests. `realm.properties` is required to define the "pluto" user/password, and also the "pluto" security realm that Pluto uses to authenticate requests.

1. The Maven Jetty plugin will be started with the same Pluto server dependencies that are required in the [Pluto-Tomcat][ExamplePlutoDownloadUrl] bundle ([Pluto download page][PlutoDownload]). I.e. for Tomcat, these jars are located in `%PLUTO_HOME%\lib\`.

1. Use the `maven-failsafe-plugin` plugin to run the integration tests.

1. The provided integration test is called `grimbo.portlet.test.PortletITCase`. This adheres to the Failsafe integration test naming convention, whereby classes called xxxITCase are treated as integration tests.

1. [JWebUnit] is used to code the integration tests.

1. Use the `jetty-maven-plugin` plugin to stop Jetty in the `post-integration-test` phase.

Current versions used by the project are:

    <jetty.version>7.6.4.v20120524</jetty.version>
    <pluto.version>2.0.3</pluto.version>

dummy-pluto-plugin
---

Provides a simple `RewriteWebXml` class to add PlutoInvoker servlet entries for each portlet defined in portlet.xml.

example-integration-test
---

This project uses the `pluto-jetty-test-parent` pom and demonstrates a simple integration test.

Extra steps are:

1. Use the `maven-dependency-plugin` to unpack the webapps required for testing into "target".

1. Using the `org.codehaus.mojo.maven-exec-plugin` plugin, run the `RewriteWebXml` class (provided by the dummy-pluto-plugin project).
This will add the PlutoInvoker servlet entries into web.xml, which are required for integration with the Pluto portlet container.

[Jetty]: http://jetty.codehaus.org/jetty/
[Pluto]: http://portals.apache.org/pluto/v20/getting-started.html
[PlutoDownload]: http://portals.apache.org/pluto/download.html
[ExamplePlutoDownloadUrl]: http://www.us.apache.org/dist/portals/pluto/pluto-2.0.3-bundle.zip
[JWebUnit]: http://jwebunit.sourceforge.net/

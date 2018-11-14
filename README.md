# hibersap-hybris
Hibersap integration for Hybris platform

Simple [Hibersap](http://hibersap.org/index.html) integration for [SAP Hybris platform (TM)](https://www.hybris.com)
========

Use as any other standard Hybris extension. It's designed to be used as a singleton
* add `hibersap-core-x.x.x.jar` to `hibersap/lib` folder
* add hibersap to `config/localextensions.xml`
* add hibersap to your calling module's `extensioninfo.xml`
* inject Spring reference to Hibersap service in your calling module

```java
@Resource(name = "hibersapService")
private HibersapService hibersapService;
```
* refer your RFC destination in `hibersap/resources/hibersap-spring.xml`
```xml
<property name="rfcDestinationName" value="ECC_TST_CONNECTION" />
```

TODO
========
* retrieve RFC destination from current storefront (no more need for *rfcDestinationName* Spring property)
* extend and refine error handling

What is [Hibersap](https://github.com/hibersap/hibersap)
========

Hibersap helps developers of Java applications to call business logic in SAP backends. It defines a set of Java annotations to map SAP function modules to Java classes as well as a small, clean API to execute these function modules and handle transaction and security aspects.

Hibersap's programming model is quite similar to those of modern O/R mappers, significantly speeding up the development of SAP interfaces and making it much more fun to write the integration code.

Under the hood, Hibersap either uses the SAP Java Connector (JCo) or a JCA compatible resource adapter to communicate with the SAP backend. While retaining the benefits of JCo and JCA like transactions, security, connection pooling, etc., developers can focus on writing business logic because the need for boilerplate code is largely reduced.

For more information please visit the Hibersap home page: http://hibersap.org

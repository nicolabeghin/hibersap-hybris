# hibersap-hybris
Simple [Hibersap](http://hibersap.org/) integration for [SAP Hybris Commerce platform (TM)](https://www.sap.com/products/crm/e-commerce-platforms.html)

## What is Hibersap
[Hibersap](https://hibersap.org/) helps developers of Java applications to call business logic in SAP backends. It defines a set of Java annotations to map SAP function modules to Java classes as well as a small, clean API to execute these function modules and handle transaction and security aspects.

Hibersap's programming model is quite similar to those of modern O/R mappers, significantly speeding up the development of SAP interfaces and making it much more fun to write the integration code.

![hibersap](https://hibersap.org/img/HibersapMapping.png)

## How to use
Use as any other standard Hybris extension. It's designed to be used as a singleton
* clone project to `hybris/bin/custom/hibersap`
* add `hibersap-core-x.x.x.jar` to `hybris/bin/custom/hibersap/lib` folder
* enable `hibersap` extension in `hybris/config/localextensions.xml`
```java
<extension name='hibersap' />
```

* add `hibersap` to your calling module's `extensioninfo.xml`
* inject Spring reference to `HibersapService` service in your calling module

```java
@Resource(name = "hibersapService")
private HibersapService hibersapService;
```

## How it works
The extensions picks up the SAP backend settings from the active `SAPConfiguration` (ref. [DefaultHibersapService.java](https://github.com/nicolabeghin/hibersap-hybris/blob/master/src/de/hybris/hibersap/services/impl/DefaultHibersapService.java#L70)).


## TODO
Extend and refine error handling.
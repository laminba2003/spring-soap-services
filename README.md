# Spring SOAP

SOAP is a messaging protocol. Messages (requests and responses) are XML documents over HTTP. The XML contract is defined by the WSDL (Web Services Description Language). It provides a set of rules to define the messages, bindings, operations, and location of the service.
There are two possible approaches when creating a web service: Contract-Last and Contract-First. When we use a contract-last approach, we start with the Java code, and generate the web service contract (WSDL) from the classes. When using contract-first, we start with the WSDL contract, from which we generate the Java classes.
Spring-WS only supports the contract-first development style.



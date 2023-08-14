# Spring Security: method security

* @Secured
* @DeclareRoles
* @RolesAllowed
* @PreAuthorize
* @PostAuthorize

* @PreFilter
* @PostFilter

The documentation here seems to be incorrect: @PreFilter is supported for Collections only. OK for @PostFilter
https://github.com/spring-projects/spring-security/blob/main/docs/modules/ROOT/pages/servlet/authorization/method-security.adoc#use-postfilter


Same-class invocation for the annotated methods: IDEA should check that the @PreFilter, @PostFilter, @PreAuthorize-annotated methods 
are called not only from the same class. See `com.mytests.spring.security.methodsecuritytest.callsFromTheSameClassTests` package

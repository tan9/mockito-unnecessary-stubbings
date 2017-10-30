When only run on partial of test cases in a test class, no UnnecessaryStubbingException will be thrown, for example:

```bash
# only run test `a`, `b` among all three cases, the test will pass
./mvnw -Dtest=FooTest#a+b test
```

This behavior making developers hard to focus on **unit** of tests to fix.

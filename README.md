Chisel ScalaCheck
=================

This is a test-bed to explore the ways to integrate [ScalaCheck](https://www.scalacheck.org/) with [Chisel](https://chisel.eecs.berkeley.edu/).

There are 2 options to use ScalaCheck in Chisel.

1. Run `chisel.iotesters.Driver` in `scalacheck.Properties`.
2. Use `scalacheck.Gen` in `chisel.iotesters.Driver`.

### Run `chisel.iotesters.Driver` in `scalacheck.Properties`.

See `src/test/scala/problems/Mux2Specification.scala`.

This option has some disadvantages such as excessive calls to `Driver.execute` and too verbose log output.

    $ sbt test

### Use `scalacheck.Gen` in `chisel.iotesters.Driver`.

See `src/test/scala/problems/Mux2Tests`.

This is the more preferred option.

We need to implement some utility APIs to make test cases terse.

    $ sbt
    > test:runMain problems.Mux2Tests


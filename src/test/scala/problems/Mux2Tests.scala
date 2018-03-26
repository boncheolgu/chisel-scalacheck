// See LICENSE.txt for license details.
package problems

import chisel3._
import chisel3.util._
import chisel3.iotesters.{Driver, TesterOptionsManager, PeekPokeTester}

import org.scalacheck.Properties
import org.scalacheck.Prop.forAll
import org.scalacheck.Gen

class Mux2Tests(c: Mux2, s: Int, i0: Int, i1: Int) extends PeekPokeTester(c) {
  poke(c.io.sel, s)
  poke(c.io.in1, i1)
  poke(c.io.in0, i0)
  step(1)
  expect(c.io.out, if (s == 1) i1 else i0)
}

object Mux2Specification extends Properties("Mux2") {
  val uints : Gen[Int] = Gen.oneOf(0, 1)

  val manager = new TesterOptionsManager()

  property("mux2") = forAll(uints, uints, uints) { (s: Int , i0: Int, i1: Int) =>
    Driver.execute(() => new Mux2, manager) {
      (c) => new Mux2Tests(c, s, i0, i1)
    }
  }
}

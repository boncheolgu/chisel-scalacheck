// See LICENSE.txt for license details.
package problems

import chisel3.iotesters.{Driver, PeekPokeTester}

import org.scalacheck.Gen
import org.scalacheck.Prop.forAll

import org.scalatest.FreeSpec

class Mux2Tester(c: Mux2) extends PeekPokeTester(c) {
  val sel_gen: Gen[Int] = Gen.choose(0, 1)
  val in_gen: Gen[Int] = Gen.choose(0, 1)

  forAll(sel_gen, in_gen, in_gen) {
    (s: Int, i0: Int, i1: Int) =>
    poke(c.io.sel, s)
    poke(c.io.in1, i1)
    poke(c.io.in0, i0)
    step(1)
    expect(c.io.out, if (s == 1) i1 else i0)
  }.check
}

class Mux2Tests extends FreeSpec {
  "Mux2Spec" in {
    Driver.execute(Array(), () => new Mux2) (new Mux2Tester(_))
    // Driver.execute(Array("--backend-name", "verilator"), () => new Mux2)(new Mux2Tester(_))
  }
}

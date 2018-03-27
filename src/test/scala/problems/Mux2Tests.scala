// See LICENSE.txt for license details.
package problems

import chisel3.iotesters.{Driver, PeekPokeTester}

import org.scalacheck.Gen
import org.scalacheck.Prop.{forAll, BooleanOperators}

import org.scalatest.{FreeSpec, Matchers}

class Mux2Tests extends FreeSpec with Matchers {
  val sel_gen: Gen[Int] = Gen.choose(0, 1)
  val in_gen: Gen[Int] = Gen.choose(0, 1)
  val options: Array[String] = Array()

  "Mux2Spec" in {
    Driver.execute(options, () => new Mux2) (c => new PeekPokeTester(c) {
      forAll(sel_gen, in_gen, in_gen) {
        (s: Int, i0: Int, i1: Int) =>
        poke(c.io.sel, s)
        poke(c.io.in1, i1)
        poke(c.io.in0, i0)
        step(1)
        expect(c.io.out, if (s == 1) i1 else i1) :| "labeling option #1"
      }.check

      forAll(sel_gen, in_gen, in_gen) {
        (s: Int, i0: Int, i1: Int) =>
        poke(c.io.sel, s)
        poke(c.io.in1, i1)
        poke(c.io.in0, i0)
        step(1)
        expect(c.io.out, if (s == 1) i1 else i1, "labeling option #2")
      }.check
    })
  }
}

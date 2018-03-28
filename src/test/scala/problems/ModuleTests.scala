// See LICENSE.txt for license details.
package problems

import chisel3._
import chisel3.iotesters.{Driver, PeekPokeTester, TesterOptionsManager}

import org.scalacheck.Gen
import org.scalacheck.Prop.{forAll, BooleanOperators}

import org.scalatest.PropSpec

trait ChiselIOSpec extends PropSpec {
  def runTest[T <: Module](description: String, dutGen: () => T)(
      testerGen: T => PeekPokeTester[T]): Unit = {
    val optionsManager = new TesterOptionsManager

    property(description) {
      val result = Driver.execute(dutGen, optionsManager)(testerGen)
      assert(result)
    }
  }
}

class ModuleTests extends ChiselIOSpec {
  val sel_gen: Gen[Int] = Gen.choose(0, 1)
  val in_gen: Gen[Int] = Gen.choose(0, 1)

  runTest("Mux2Spec", () => new Mux2)(c =>
    new PeekPokeTester(c) {
      forAll(sel_gen, in_gen, in_gen) { (s: Int, i0: Int, i1: Int) =>
        poke(c.io.sel, s)
        poke(c.io.in1, i1)
        poke(c.io.in0, i0)
        step(1)
        expect(c.io.out, if (s == 1) i1 else i0) :| "labeling option #1"
      }.check

      forAll(sel_gen, in_gen, in_gen) { (s: Int, i0: Int, i1: Int) =>
        poke(c.io.sel, s)
        poke(c.io.in1, i1)
        poke(c.io.in0, i0)
        step(1)
        expect(c.io.out, if (s == 1) i1 else i0, "labeling option #2")
      }.check
  })
}

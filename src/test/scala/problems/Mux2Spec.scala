// See LICENSE.txt for license details.
package problems

import chisel3._
import chisel3.testers.BasicTester
import chisel3.util._

import org.scalacheck.Gen

import org.scalatest._
import org.scalatest.prop._

class Mux2Tester(sel: Int, in0: Int, in1: Int) extends BasicTester {
  val mux2 = Module(new Mux2)
  mux2.io.sel := sel.asUInt
  mux2.io.in0 := in0.asUInt
  mux2.io.in1 := in1.asUInt
  assert(mux2.io.out === (if (sel == 1) in1 else in0).asUInt)
  stop()
}

class Mux2Spec extends ChiselPropSpec {
  val sel_gen: Gen[Int] = Gen.choose(0, 1)
  val in_gen: Gen[Int] = Gen.choose(0, 1)

  property("Mux2 should work correctly") {
    forAll(sel_gen, in_gen, in_gen) { (s: Int, i0: Int, i1: Int) =>
      assertTesterPasses { new Mux2Tester(s, i0, i1) }
    }
  }
}

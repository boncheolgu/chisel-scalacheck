// See LICENSE.txt for license details.
package problems

import chisel3._
import chisel3.util._
import chisel3.iotesters.{Driver, TesterOptionsManager, PeekPokeTester}

import org.scalacheck.Arbitrary.arbitrary
import org.scalacheck.Gen
import org.scalacheck.Properties
import org.scalacheck.Prop.forAll
import org.scalacheck.Test

object Mux2Specification extends Properties("Mux2") {
  val manager = new TesterOptionsManager {
    // https://github.com/freechipsproject/chisel-testers/blob/master/src/main/scala/chisel3/iotesters/TesterOptions.scala#L13
    testerOptions = testerOptions.copy(isVerbose=false)

    interpreterOptions = interpreterOptions.copy(setVerbose = false)

    // https://github.com/freechipsproject/firrtl/blob/master/src/main/scala/firrtl/ExecutionOptionsManager.scala#L173
    firrtlOptions = firrtlOptions.copy()

    // https://github.com/freechipsproject/chisel3/blob/master/src/main/scala/chisel3/ChiselExecutionOptions.scala#L15
    chiselOptions = chiselOptions.copy()
  }

  val sel_gen : Gen[Int] = Gen.choose(0, 1)
  val in_gen : Gen[Int] = Gen.choose(0, 1)

  property("mux2") = forAll(sel_gen, in_gen, in_gen) { (s: Int , i0: Int, i1: Int) =>
    Driver.execute(() => new Mux2, manager) {
      (c) => new PeekPokeTester(c) {
        poke(c.io.sel, s)
        poke(c.io.in1, i1)
        poke(c.io.in0, i0)
        step(1)
        expect(c.io.out, if (s == 1) i1 else i0)
      }
    }
  }
}

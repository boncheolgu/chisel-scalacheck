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

object Mux2Tests {
  def main(args: Array[String]): Unit = {
    val optionsManager = new TesterOptionsManager()
    optionsManager.doNotExitOnHelp()
    optionsManager.parse(args)

    val sel_gen : Gen[Int] = Gen.choose(0, 1)
    val in_gen : Gen[Int] = Gen.choose(0, 1)

    Driver.execute(() => new Mux2, optionsManager) {
      (c) => new PeekPokeTester(c) {
        val prop = forAll(sel_gen, in_gen, in_gen) ( (s: Int , i0: Int, i1: Int) => {
          poke(c.io.sel, s)
          poke(c.io.in1, i1)
          poke(c.io.in0, i0)
          step(1)
          expect(c.io.out, if (s == 1) i1 else i0)
        })

        prop.check
      }
    }
  }
}

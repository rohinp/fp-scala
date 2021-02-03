package day4

import scala.concurrent.ExecutionContext

package object executor {
  def execute(body: =>Unit) = ExecutionContext.global.execute(new Runnable {
    def run() = body
  })
}

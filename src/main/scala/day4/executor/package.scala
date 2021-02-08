package day4

import scala.concurrent.ExecutionContext

package object executor {
  def execute(body: =>Unit): Unit = ExecutionContext.global.execute(() => body)
}

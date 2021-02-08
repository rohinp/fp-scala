import cats.implicits._
def smashIt_curried[A:cats.Monoid]:List[A] => List[A]  => List[A] =
  la => lb =>
    for {
      a <- la
      b <- lb
    }
    yield a |+| b


package day2

object Optics {
  object Lenses {
    case class Lens[O, I](
        get: O => I,
        set: (O, I) => O
    )

    def compose[Outer, Inner, Value](
        outer: Lens[Outer, Inner],
        inner: Lens[Inner, Value]
    ): Lens[Outer, Value] =
      Lens[Outer, Value](
        get = outer.get.andThen(inner.get),
        set = (obj, value) => outer.set(obj, inner.set(outer.get(obj), value))
      )

    implicit class LensSyntax[Outer, Inner](val outer: Lens[Outer, Inner])
        extends AnyVal {
      @inline def ->[Value](inner: Lens[Inner, Value]): Lens[Outer, Value] =
        compose(outer, inner)
    }
  }
  object Prisms {
    case class Prism[S, A](
        getOption: S => Option[A],
        reverseGet: A => S => S
    )
  }

}

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
        reverseGet: A => S
    ) {
      def modifyOption(f: A => A): S => Option[S] =
        s => getOption(s).map(f).map(reverseGet)
    }
    object Prism {
      def apply[S, A](pf: PartialFunction[S, A])(f: A => S): Prism[S, A] =
        Prism(s => Option(s).collect(pf), f)

      def compose[S, A, B](
                            outer: Prism[S, A],
                            inner: Prism[A, B]
      ): Prism[S, B] =
        Prism(
          s => outer.getOption(s).flatMap(inner.getOption),
          a => inner.reverseGet.andThen(outer.reverseGet)(a)
        )
    }


    //in between lens and prism for better composition
    case class Optional[S, A](
                            getOption: S => Option[A],
                            modify: (A => A) => S => S
                          ) {
      def set(a: A): S => S = modify(_ => a)
    }

    object Optional {
      /*def combine[B](optional: Optional[A, B]): Optional[S, B] =
        new Optional[S, B] {
          def modify(f: B => B): S => S =
            (optional.modify _).andThen(self.modify _)(f)
          def getOption(s: S): Option[B] =
            self.getOption(s).flatMap(optional.getOption)
        }

      def combine[B](lens: Lens[A, B]): Optional[S, B] =
        new Optional[S, B] {
          def modify(f: B => B): S => S =
            (lens.modify _).andThen(self.modify _)(f)
          def getOption(s: S): Option[B] =
            self.getOption(s).map(lens.get)
        }

      def combine[B](prism: Prism[A, B]): Optional[S, B] =
        new Optional[S, B] {
          def modify(f: B => B): S => S =
            self.modify { a =>
              prism
                .getOption(a)
                .map(f)
                .map(prism.reverseGet)
                .getOrElse(a)
            }
          def getOption(s: S): Option[B] =
            self.getOption(s).flatMap(prism.getOption)
        }*/
    }

  }

}

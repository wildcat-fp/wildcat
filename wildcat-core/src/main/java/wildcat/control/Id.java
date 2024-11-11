package wildcat.control;

import static wildcat.utils.Types.genericCast;

import java.util.function.Function;
import org.checkerframework.checker.nullness.qual.NonNull;
import wildcat.hkt.Kind;
import wildcat.typeclasses.core.Applicative;
import wildcat.typeclasses.core.Apply;
import wildcat.typeclasses.core.Functor;
import wildcat.typeclasses.core.Monad;

public record Id<T extends @NonNull Object>(T value) implements Kind<Id.k, T> {
  
  public static <T extends @NonNull Object> Id<T> of(final T value) {
    return new Id<>(value);
  }
  
  public static Functor<Id.k> functor() {
    return id_functor.functor_instance();
  }
  
  public static Apply<Id.k> apply() {
    return id_apply.apply_instance();
  }
  
  public static Applicative<Id.k> applicative() {
    return id_applicative.applicative_instance();
  }
  
  public static Monad<Id.k> monad() {
    return id_monad.monad_instance();
  }
  
  public <U extends @NonNull Object> Id<U> map(
      final Function<? super T, ? extends U> f
  ) {
    return new Id<>(f.apply(value()));
  }
  
  public <U extends @NonNull Object> Id<U> flatMap(
      final Function<? super T, ? extends @NonNull Id<? extends U>> f
  ) {
    return genericCast(f.apply(value()));
  }
  
  public <B extends @NonNull Object> Id<B> ap(final Id<? extends @NonNull Function<? super T, ? extends B>> f) {
    return f.map(fn -> fn.apply(value()));
  }
  
  interface k extends Monad.k {}
}

class id_functor implements Functor<Id.k> {
  private static final id_functor instance = new id_functor();
  
  id_functor() {}
  
  static id_functor functor_instance() {
    return instance;
  }
  
  @Override
  public final <A extends @NonNull Object, B extends @NonNull Object> Id<B> map(
      final Kind<Id.k, A> fa,
      final Function<? super A, ? extends B> f
  ) {
    final Id<A> id = fa.fix();
    return id.map(f);
  }
}

class id_apply extends id_functor implements Apply<Id.k> {
  private static final id_apply instance = new id_apply();
  
  id_apply() {}
  
  static id_apply apply_instance() {
    return instance;
  }
  
  @Override
  public final <A extends @NonNull Object, B extends @NonNull Object> Id<? extends B> ap(
      final Kind<Id.k, ? extends A> fa,
      final Kind<Id.k, ? extends @NonNull Function<? super A, ? extends B>> f
  ) {
    final Id<A> id = genericCast(fa.fix());
    final Id<@NonNull Function<? super A, ? extends B>> idF = genericCast(f.fix());
    return id.ap(idF);
  }
}

class id_applicative extends id_apply implements Applicative<Id.k> {
  private static final id_applicative instance = new id_applicative();
  
  id_applicative() {}
  
  static id_applicative applicative_instance() {
    return instance;
  }
  
  @Override
  public final <T extends @NonNull Object> Id<T> pure(final T value) {
    return new Id<>(value);
  }
}

class id_monad extends id_applicative implements Monad<Id.k> {
  private static final id_monad instance = new id_monad();
  
  id_monad() {}
  
  static id_monad monad_instance() {
    return instance;
  }
  
  @Override
  public final <A extends @NonNull Object, B extends @NonNull Object> Id<? extends B> flatMap(
      final Kind<Id.k, A> fa,
      final Function<? super A, ? extends @NonNull Kind<Id.k, ? extends B>> f
  ) {
    final Id<A> id = fa.fix();
    final Function<? super A, ? extends Id<? extends B>> fixedF = t -> {
      final Kind<Id.k, ? extends B> applied = f.apply(t);
      return genericCast(applied.fix());
    };
    
    return id.flatMap(fixedF);
  }
}
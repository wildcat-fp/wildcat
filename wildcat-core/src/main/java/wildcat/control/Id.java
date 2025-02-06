package wildcat.control;

import static wildcat.utils.Types.genericCast;

import org.checkerframework.checker.nullness.qual.NonNull;
import wildcat.fns.nonnull.NonNullFunction;
import wildcat.hkt.Kind;
import wildcat.typeclasses.core.Applicative;
import wildcat.typeclasses.core.Apply;
import wildcat.typeclasses.core.FlatMap;
import wildcat.typeclasses.core.Functor;
import wildcat.typeclasses.core.Monad;
import wildcat.typeclasses.equivalence.Eq;
import wildcat.typeclasses.equivalence.EqK;

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
  
  public static FlatMap<Id.k> flatmap() {
    return id_flatmap.flatmap_instance();
  }
  
  public static Monad<Id.k> monad() {
    return id_monad.monad_instance();
  }
  
  public static EqK<Id.k> eqk() {
    return id_eqk.eqk_instance();
  }
  
  public <U extends @NonNull Object> Id<U> map(
      final NonNullFunction<? super T, ? extends U> f
  ) {
    return new Id<>(f.apply(value()));
  }
  
  public <U extends @NonNull Object> Id<U> flatMap(
      final NonNullFunction<? super T, ? extends @NonNull Id<? extends U>> f
  ) {
    return genericCast(f.apply(value()));
  }
  
  public <B extends @NonNull Object> Id<B> ap(final Id<@NonNull NonNullFunction<? super T, ? extends B>> f) {
    return f.map(fn -> fn.apply(value()));
  }
  
  interface k extends Monad.k, EqK.k {}
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
      final NonNullFunction<? super A, ? extends B> f
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
  public final <A extends @NonNull Object, B extends @NonNull Object> Id<B> ap(
      final Kind<Id.k, A> fa,
      final Kind<Id.k, @NonNull NonNullFunction<? super A, ? extends B>> f
  ) {
    final Id<A> id = genericCast(fa.fix());
    final Id<@NonNull NonNullFunction<? super A, ? extends B>> idF = f.fix();
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

class id_flatmap extends id_apply implements FlatMap<Id.k> {
  private static final id_flatmap instance = new id_flatmap();
  
  id_flatmap() {}
  
  static id_flatmap flatmap_instance() {
    return instance;
  }
  
  @Override
  public final <A extends @NonNull Object, B extends @NonNull Object> Id<B> flatMap(
      final Kind<Id.k, A> fa,
      final NonNullFunction<? super A, ? extends @NonNull Kind<Id.k, B>> f
  ) {
    final Id<A> id = fa.fix();
    final NonNullFunction<? super A, ? extends Id<B>> fixedF = t -> f.apply(t).fix();
    
    return id.flatMap(fixedF);
  }
  
}

class id_monad extends id_flatmap implements Monad<Id.k> {
  private static final id_monad instance = new id_monad();
  
  id_monad() {}
  
  static id_monad monad_instance() {
    return instance;
  }
  
  @Override
  public <T extends @NonNull Object> Kind<Id.k, T> pure(T value) {
    return id_applicative.applicative_instance().pure(value);
  }
}

class id_eqk implements EqK<Id.k> {
  private static final id_eqk instance = new id_eqk();
  
  id_eqk() {}
  
  static id_eqk eqk_instance() {
    return instance;
  }
  
  @Override
  public <A extends @NonNull Object> boolean eqK(
      final Kind<Id.k, A> a,
      final Kind<Id.k, A> b,
      final Eq<A> eq
  ) {
    final Id<A> idA = a.fix();
    final Id<A> idB = b.fix();
    
    return eq.eqv(idA.value(), idB.value());
  }
}
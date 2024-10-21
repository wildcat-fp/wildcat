package wildcat.hkt;

public interface Kind2<F extends Kind2.k, A, B> {
  
  
  @SuppressWarnings(
    {
      "unchecked",
      "TypeParameterUnusedInFormals"
  }
  )
  default <O extends Kind2<F, A, B>> O fix() {
    return (O) this;
  }
  
  interface k {}
}

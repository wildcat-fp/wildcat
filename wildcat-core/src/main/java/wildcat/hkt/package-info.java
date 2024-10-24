/**
 * Provides core interfaces for simulating higher-kinded types (HKTs) in Wildcat.
 *
 * <p>
 * This package contains the fundamental building blocks for representing and working with
 * higher-kinded types in Java, a language that doesn't natively support them.
 * The primary interfaces in this package are:
 * </p>
 *
 * <ul>
 * <li>{@link wildcat.hkt.Kind}: Represents a higher-kinded type with one type parameter.</li>
 * <li>{@link wildcat.hkt.Kinded}: A marker interface for types that can be lifted into a {@code Kind} context.</li>
 * <li>{@link wildcat.hkt.Kind2}: Represents a higher-kinded type with two type parameters.</li>
 * <li>{@link wildcat.hkt.Kinded2}: A marker interface for types that can be lifted into a {@code Kind2} context.</li>
 * </ul>
 *
 * <p>
 * These interfaces, along with the "witness type" technique, enable the implementation of type classes
 * and higher-order functions in Wildcat, bringing the power of functional programming to Java.
 * </p>
 *
 * <h3>Higher-Kinded Types (HKTs)</h3>
 *
 * <p>
 * In functional programming, higher-kinded types are types that can take other types as parameters.
 * For example, {@code List} is a higher-kinded type because it can be parameterized with a type like {@code String}
 * to create {@code List<String>}. Java doesn't directly support HKTs, so this package provides a way to
 * simulate them using interfaces and type parameters.
 * </p>
 *
 * <h3>Witness Types</h3>
 *
 * <p>
 * The "witness type" technique involves using empty interfaces (like {@link wildcat.hkt.Kind.k})
 * as unique tags for type constructors. These witness types are used as type parameters in the
 * {@link wildcat.hkt.Kind} and {@link wildcat.hkt.Kind2} interfaces to represent the type constructor
 * being applied.
 * </p>
 *
 * <h3>Example</h3>
 *
 * <p>
 * To represent the type {@code Option<String>} as a higher-kinded type, you would use
 * {@code Kind<Option.k, String>}.
 * Here, {@code Option.k} is the witness type for the {@code Option} type constructor, and {@code String} is the type parameter
 * applied to the constructor.
 * </p>
 *
 * <h3>Usage</h3>
 *
 * <p>
 * The interfaces in this package are primarily used internally by Wildcat's type class system.
 * However, understanding their purpose and how they simulate HKTs is essential for working with
 * type classes and higher-order functions in Wildcat.
 * </p>
 *
 * @apiNote This package provides the core abstractions for simulating higher-kinded types in Wildcat,
 *   enabling the use of type classes and higher-order functions in a Java environment.
 */
package wildcat.hkt;

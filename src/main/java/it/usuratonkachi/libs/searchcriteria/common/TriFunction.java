package it.usuratonkachi.libs.searchcriteria.common;

@FunctionalInterface
public interface TriFunction<F, S, T, R> {

    /**
     * Applies this function to the given arguments.
     *
     * @param first the first function argument
     * @param second the second function argument
     * @param third the second function argument
     * @return the function result
     */
    R apply(F first, S second, T third);

}

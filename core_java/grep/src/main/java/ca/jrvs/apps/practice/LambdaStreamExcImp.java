package ca.jrvs.apps.practice;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class LambdaStreamExcImp implements LambdaStreamExc{

    @Override
    public Stream<String> createStrStream(String... strings) {
        return Arrays.asList(strings).stream();
    }

    @Override
    public Stream<String> toUpperCase(String... strings) {
        for(String string : strings){
            string.toUpperCase();
        }
        return Arrays.asList(strings).stream();
    }

    @Override
    public Stream<String> filter(Stream<String> stringStream, String pattern) {
        return stringStream.filter(p -> p.matches(pattern));
    }

    @Override
    public IntStream createIntStream(int[] arr) {
        return IntStream.of(arr);
    }

    @Override
    public <E> List<E> toList(Stream<E> stream) {
        return stream.collect(Collectors.toList());
    }

    @Override
    public List<Integer> toList(IntStream intStream) {
        List<Integer> newList = new ArrayList<>();
        for( Integer a : intStream.toArray()){
            newList.add(a);
        }
        return newList;
    }

    @Override
    public IntStream createIntStream(int start, int end) {
        return IntStream.range(start, end);
    }

    @Override
    public DoubleStream squareRootIntStream(IntStream intStream) {
        return DoubleStream.of(squareRootIntStream(intStream).toArray());
    }

    @Override
    public IntStream getOdd(IntStream intStream) {
        return intStream.filter(i -> i % 2 != 0);
    }

    @Override
    public Consumer<String> getLambdaPrinter(String prefix, String suffix) {
        return null;
    }

    @Override
    public void printMessages(String[] messages, Consumer<String> printer) {

    }

    @Override
    public void printOdd(IntStream intStream, Consumer<String> printer) {

    }

    @Override
    public Stream<Integer> flatNestedInt(Stream<List<Integer>> ints) {
        return null;
    }
}

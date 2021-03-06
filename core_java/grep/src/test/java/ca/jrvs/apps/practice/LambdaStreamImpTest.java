package ca.jrvs.apps.practice;

import java.util.stream.Stream;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class LambdaStreamImpTest {
  private LambdaStreamImp lse;
  @Before
  public void setUp() throws Exception {
    lse = new LambdaStreamImp();
  }

  @Test
  public void toUpperCase() {
    List<String> expected = new ArrayList<String>();
    expected.add("BOB");
    expected.add("SAM");
    expected.add("DOUG");

    List<String> result = lse.toUpperCase("bOb", "Sam", "DoUg").collect(Collectors.toList());
    Assert.assertEquals(expected, result);
  }

  @Test
  public void filter() {
    List<String> expected = new ArrayList<String>();
    expected.add("dog");

    List<String> result = lse.filter(Stream.of("abc", "dog", "bcbc"), "bc").collect(Collectors.toList());
    Assert.assertEquals(expected, result);
  }

  @Test
  public void squareRootIntStream() {
    List<Double> expected = new ArrayList<Double>();
    expected.add(Math.sqrt(1));
    expected.add(Math.sqrt(2));
    expected.add(Math.sqrt(4));

    List<Double> result = lse.squareRootIntStream(IntStream.of(1, 2, 4)).boxed().collect(Collectors.toList());
    Assert.assertEquals(expected, result);
  }

  @Test
  public void getOdd() {
    List<Integer> expected = new ArrayList<Integer>();
    expected.add(1);
    expected.add(3);
    IntStream inputStream = IntStream.of(1, 2, 3, 4);
    List<Integer> result = lse.toList(lse.getOdd(inputStream));

    Assert.assertEquals(expected, result);
  }

/*
  @Test
  public void printMessages() {
    String[] messages = {"a", "b", "c"};
    System.out.println("printMessages():");
    lse.printMessages(messages, lse.getLambdaPrinter("msg:", "!"));
  }

  @Test
  public void printOdd() {
    System.out.println("printOdd():");
    lse.printOdd(lse.createIntStream(0, 5), lse.getLambdaPrinter("odd number:", "!"));
  }
*/
}

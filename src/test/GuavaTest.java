package test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hamcrest.Matcher;
import org.hamcrest.MatcherAssert;
import org.hamcrest.core.IsEqual;
import org.junit.Assert;
import org.junit.Test;

import com.google.common.base.CharMatcher;
import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.google.common.base.Throwables;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.common.collect.Sets.SetView;
import com.google.common.io.Files;
import com.google.common.primitives.Ints;

public class GuavaTest {
	
	public void test() {
		ImmutableList<String> of = ImmutableList.of("a", "b", "c", "d");
		System.out.println(of.toString());
		// ImmutableMap<String,String> map = ImmutableMap.of("key1", "value1",
		// "key2", "value2");
	}

//	@Test
	public void test2() {
		File file = new File(getClass().getResource("/xml").getFile());
		List<String> lines = null;
		try {
			lines = Files.readLines(file, Charset.forName("GBK"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(lines);
	}
	
//	@Test
	public void test3(){
		int a=3,b=5;
		int compare = Ints.compare(a, b);
		Assert.assertEquals(-1, compare);
	}
	
//	@Test
	public void test4(){
		Assert.assertEquals("89983", CharMatcher.DIGIT.retainFrom("some text 89983 and more"));
		Assert.assertEquals("some text  and more", CharMatcher.DIGIT.removeFrom("some text 89983 and more"));
	}
	
	//@Test
	public void test5(){
		int[] numbers = { 1, 2, 3, 4, 5 };
		String numbersAsString = Joiner.on(";").join(Ints.asList(numbers));
		System.out.println(numbersAsString);
		String numbersAsStringDirectly = Ints.join(";", numbers);
//		Iterable split = Splitter.on(",").split(numbersAsStringDirectly);
		String testString = "foo , what,,,more,";
		Iterable<String> split = Splitter.on(",").omitEmptyStrings().trimResults().split(testString);
		Iterator<String> it = split.iterator();
		while (it.hasNext()) {
			String string = (String) it.next();
			System.out.println(string);
		}
	}
	
	//@Test
	public void test6(){
		int[] array = { 1, 2, 3, 4, 5 };
		int[] array2 = {4,6};
		int a = 4;
		boolean contains = Ints.contains(array, a);
		Assert.assertTrue(contains);
		int indexOf = Ints.indexOf(array, a);
		int max = Ints.max(array);
		int min = Ints.min(array);
		int[] concat = Ints.concat(array, array2);
		System.out.println(Ints.join(",", concat));
	}
	
	//@Test
	public void test8(){
		HashSet<Integer> setA = Sets.newHashSet(1, 2, 3, 4, 5);
		HashSet<Integer> setB = Sets.newHashSet(4, 5, 6, 7, 8);
		SetView<Integer> union = Sets.union(setA, setB);
		System.out.println("union:");
		for (Integer integer : union)
		    System.out.println(integer);       
		 
		SetView<Integer> difference = Sets.difference(setA, setB);
		System.out.println("difference:");
		for (Integer integer : difference)
		    System.out.println(integer);      
		 
		SetView<Integer> intersection = Sets.intersection(setA, setB);
		System.out.println("intersection:");
		for (Integer integer : intersection)
		    System.out.println(integer);
	}
//	@Test
	public void test9(){
		int count = 0;
//		Preconditions.checkArgument(count > 0, "must be positive: %s", count);
		Preconditions.checkNotNull(null);
		
	}
	
//	@Test
	public void test10(){
		Map<String, Double> map = Maps.newHashMap();
		map.put("key1", 10d);
		map.put("key2", 11d);
//		ImmutableMap<String, Double> map = ImmutableMap.of("key1", 10d,"key2", 11d);
		Map map2 = Maps.transformValues(map, new Function<Double,Double>() {
			double eurToUsd = 1.4888;
			@Override
			public Double apply(Double input) {
				// TODO Auto-generated method stub
				return input*eurToUsd;
			}
			
		});
		System.out.println(map2.toString());
		Assert.assertThat(6, IsEqual.equalTo(5));
	}
	
	    
//	    @Test
	    public void testOptional() throws Exception { 
	        Optional<Integer> possible=Optional.of(6);
	        Optional<Integer> absentOpt=Optional.absent();
	        Optional<Integer> NullableOpt=Optional.fromNullable(null);
	        Optional<Integer> NoNullableOpt=Optional.fromNullable(10);
	        if(possible.isPresent()){
	            System.out.println("possible isPresent:"+possible.isPresent());
	            System.out.println("possible value:"+possible.get());
	        }
	        if(absentOpt.isPresent()){
	            System.out.println("absentOpt isPresent:"+absentOpt.isPresent()); ;
	        }
	        if(NullableOpt.isPresent()){
	            System.out.println("fromNullableOpt isPresent:"+NullableOpt.isPresent()); ;
	        }
	        if(NoNullableOpt.isPresent()){
	            System.out.println("NoNullableOpt isPresent:"+NoNullableOpt.isPresent()); ;
	        }
	    } 
	    
	    
	        @Test
	        public void testThrowables(){
	            try {
	                throw new Exception();
	            } catch (Throwable t) {
	                String ss = Throwables.getStackTraceAsString(t);
	                System.out.println("ss:"+ss);
	                Throwables.propagate(t);
	            }
	        }
	        
	        @Test
	        public void call() throws IOException {
	            try {
	                throw new IOException();
	            } catch (Throwable t) {
	                Throwables.propagateIfInstanceOf(t, IOException.class);
	                throw Throwables.propagate(t);
	            }
	        }
	        
	        public Void testPropagateIfPossible() throws Exception {
	              try {
	                  throw new Exception();
	              } catch (Throwable t) {
	                Throwables.propagateIfPossible(t, Exception.class);
	                Throwables.propagate(t);
	              }

	              return null;
	        }
	
}

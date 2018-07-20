package javaReflection;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

import static org.testng.Assert.assertTrue;

import java.lang.reflect.*;

import org.testng.annotations.Test;

public class JavaReflectionTest {
  @Test
  public void givenObject_WhenGetFieldNameAtRuntime_ThenCorrect() {
	  Object person = new Person();
	  Field[] fields = person.getClass().getDeclaredFields();
	  List<String> actuallyFieldNames = getFieldNames(fields);
	  assertTrue(Arrays.asList("name","age").containsAll(actuallyFieldNames));
  }
  
  private static List<String> getFieldNames(Field[] fields) {
	  List<String> fieldNames = new ArrayList<String>();
	  for (Field field : fields) {
		  fieldNames.add(field.getName());
	  }
	  return fieldNames;
  }
}

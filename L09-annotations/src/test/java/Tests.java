import com.google.common.math.IntMath;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

public class Tests {

  @Test
  @AfterEach
  public void test() {
    System.out.println(IntMath.mod(100, 1));
  }
}

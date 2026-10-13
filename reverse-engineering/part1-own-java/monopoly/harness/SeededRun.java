import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Random;

// Runs a program's Main with a fixed stdin and every static java.util.Random field replaced by
// one seeded generator, so two builds of the same game roll the same dice. Fields are found by
// type, not by name, so the original and the obfuscated/reconstructed builds are treated alike.
// Usage: SeededRun <classpath> <seed> <stdin file>
public class SeededRun {
    public static void main(String[] args) throws Exception {
        URL url = Path.of(args[0]).toUri().toURL();
        long seed = Long.parseLong(args[1]);
        byte[] stdin = Files.readAllBytes(Path.of(args[2]));

        System.setIn(new ByteArrayInputStream(stdin)); // before Main's static Scanner is created
        try (URLClassLoader loader = new URLClassLoader(new URL[]{url}, null)) {
            Class<?> main = Class.forName("Main", true, loader);
            int replaced = 0;
            for (Field field : main.getDeclaredFields()) {
                if (Modifier.isStatic(field.getModifiers()) && field.getType() == Random.class) {
                    field.setAccessible(true);
                    field.set(null, new Random(seed));
                    replaced++;
                }
            }
            if (replaced != 1) {
                throw new IllegalStateException("expected one static Random field, found " + replaced);
            }
            try {
                main.getMethod("main", String[].class).invoke(null, (Object) new String[0]);
            } catch (java.lang.reflect.InvocationTargetException e) {
                System.out.flush();
                System.out.println("\n<<EXCEPTION " + e.getCause().getClass().getName() + ">>");
            }
        }
    }
}

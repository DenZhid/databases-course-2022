package generators;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

abstract class Generator {

    protected final Random random;

    protected Generator(Random random) {
        this.random = random;
    }

    protected abstract Object generate();

    public List<Object> generateMultiple(int number) {
        List<Object> result = new ArrayList<>();
        for (int i = 0; i < number; i++) {
            result.add(generate());
        }
        return result;
    }

    protected String createTemplate(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            if (random.nextBoolean()) {
                sb.append('?');
                continue;
            }
            sb.append('#');
        }
        return sb.toString();
    }

    protected int getLength(int maxLength, int qualifier) {
        int length = random.nextInt(maxLength);
        if (length < qualifier) {
            length += maxLength;
        }
        return length;
    }
}

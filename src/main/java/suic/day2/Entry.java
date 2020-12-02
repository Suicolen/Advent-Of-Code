package suic.day2;

import com.svetylkovo.rojo.annotations.Group;
import com.svetylkovo.rojo.annotations.Mapper;
import com.svetylkovo.rojo.annotations.Regex;
import lombok.Data;

@Regex("(\\d+)-(\\d+) ([\\w]): ([\\w]+)")
@Data
public class Entry {
    @Group(1)
    int min;

    @Group(2)
    int max;

    @Group(3)
    @Mapper(CharWrapper.class)
    char character;

    @Group(4)
    String password;
}

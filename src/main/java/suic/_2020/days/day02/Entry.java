package suic._2020.days.day02;

import com.svetylkovo.rojo.annotations.Group;
import com.svetylkovo.rojo.annotations.Mapper;
import com.svetylkovo.rojo.annotations.Regex;
import lombok.Data;

@Regex("(\\d+)-(\\d+) ([\\w]): ([\\w]+)")
@Data
public class Entry {
    @Group(1)
    int lowerBound;

    @Group(2)
    int upperBound;

    @Group(3)
    @Mapper(CharWrapper.class)
    char character;

    @Group(4)
    String password;
}

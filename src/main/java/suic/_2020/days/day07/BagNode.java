package suic._2020.days.day07;

import lombok.EqualsAndHashCode;
import lombok.Value;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Value
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class BagNode {
    @EqualsAndHashCode.Include
    String name;
    Map<BagNode, Integer> children = new HashMap<>();
    Set<BagNode> parents = new HashSet<>();


}

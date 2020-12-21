package tjoker.server.core;

import java.util.HashSet;
import java.util.Set;

/**
 * @program: server
 * @description:
 * @author: 十字街头的守候
 * @create: 2020-12-16 21:14
 **/
public class Mapping {
    private String name;
    private Set<String> pattern;

    public Mapping() {
        pattern=new HashSet<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<String> getPattern() {
        return pattern;
    }

    public void setPattern(Set<String> pattern) {
        this.pattern = pattern;
    }
    public void addPattern(String pattern){
        this.pattern.add(pattern);
    }
}

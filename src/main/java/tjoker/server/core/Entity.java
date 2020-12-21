package tjoker.server.core;

/**
 * @program: server
 * @description: 封装servlet信息
 * @author: 十字街头的守候
 * @create: 2020-12-16 21:20
 **/
public class Entity {
    private String name;
    private String clz;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClz() {
        return clz;
    }

    public void setClz(String clz) {
        this.clz = clz;
    }
}

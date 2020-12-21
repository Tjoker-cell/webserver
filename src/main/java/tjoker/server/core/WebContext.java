package tjoker.server.core;



import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: server
 * @description: 将实体类转换成map，获取class值
 * @author: 十字街头的守候
 * @create: 2020-12-19 08:48
 **/
public class WebContext {

    private List<Entity> entityList = null;
    private List<Mapping> mappingList = null;

    //key->servlet-name v->servlet-class
    private Map<String, String> entityMap = new HashMap<>();
    //key->url-pattern  v->servlet-name
    private Map<String, String> mappingMap = new HashMap<>();

    public WebContext(List<Entity> entityList, List<Mapping> mappingList) {
        this.entityList = entityList;
        this.mappingList = mappingList;
        //将entity的list转换成了对应的map
        for (Entity entity : entityList) {
            entityMap.put(entity.getName(), entity.getClz());
        }
        //将mapping的list转换成了对应的map
        for (Mapping mapping : mappingList) {
            for (String pattern : mapping.getPattern()) {
                mappingMap.put(pattern, mapping.getName());
            }

        }
    }
        public String getClz (String pattern){
            String s = mappingMap.get(pattern);
            String clz = entityMap.get(s);
            return clz;
        }

}
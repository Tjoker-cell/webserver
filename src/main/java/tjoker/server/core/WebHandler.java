package tjoker.server.core;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import tjoker.server.core.Entity;
import tjoker.server.core.Mapping;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: server
 * @description: 解析web.xml文件
 * @author: 十字街头的守候
 * @create: 2020-12-16 19:48
 **/
public class WebHandler extends DefaultHandler {
    private List<Entity> entityList;
    private List<Mapping> mappingList;
    private tjoker.server.core.Entity entity;
    private tjoker.server.core.Mapping mapping;
    private boolean isMapping;
    private String tag;//存储操作表示符

    @Override
    public void startDocument() throws SAXException {
        //文档解析开始初始化
        entityList=new ArrayList<>();
        mappingList=new ArrayList<>();
    }

    @Override
    public void endDocument() throws SAXException {
        //文档结束

    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if(qName!=null){
            tag=qName;
            if(qName.equals("servlet")){
                isMapping=false;
                entity = new Entity();
            }else if(qName.equals("servlet-mapping")){
                isMapping=true;
                mapping = new Mapping();
            }
        }
    }
    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {

        if(tag!=null){
                        String str =new String(ch,start,length).trim();
                        if(isMapping==false){
                            if(tag.equals("servlet-name")){
                                entity.setName(str);
                            }else if(tag.equals("servlet-class")){
                                entity.setClz(str);
                }
            }else {
                if(tag.equals("servlet-name")){
                    mapping.setName(str);
                }else if(tag.equals("url-pattern")){
                    mapping.addPattern(str);
                }
            }
        }
    }
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if(qName!=null){
            if(qName.equals("servlet")){
                entityList.add(entity);
            }else if(qName.equals("servlet-mapping")){
                mappingList.add(mapping);
            }

        }
        tag=null;
        }



    public List<Entity> getEntityList() {
        return entityList;
    }

    public List<Mapping> getMappingList() {
        return mappingList;
    }

    public Entity getEntity() {
        return entity;
    }

    public Mapping getMapping() {
        return mapping;
    }

}

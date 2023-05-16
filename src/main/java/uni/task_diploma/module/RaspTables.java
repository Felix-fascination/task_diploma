package uni.task_diploma.module;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class RaspTables {

     Map<String, RaspTable> tables = new HashMap<String, RaspTable>();

     public RaspTable putTable(String groupName, RaspTable table) {
         tables.put(groupName,table);
         return table;
     }

     public RaspTable getTable(String groupName){
         return tables.get(groupName);
     }
}

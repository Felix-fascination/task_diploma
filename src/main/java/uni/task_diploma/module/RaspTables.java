package uni.task_diploma.module;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class RaspTables {

     Map<String, RaspTable> tables = new HashMap<String, RaspTable>();

     public RaspTable putTable(String groupName, Boolean odd, RaspTable table) {
         tables.put(groupName + odd.toString(),table);
         return table;
     }

     public RaspTable getTable(String groupName, Boolean odd){
         return tables.get(groupName + odd.toString());
     }

     public void addComment(String groupName, Boolean odd, Comment comment) {
         tables.get(groupName + odd.toString()).addComment(comment);
     }

     public Boolean isPresent(String groupName, Boolean odd){
         return tables.containsKey(groupName + odd.toString());
     }
}

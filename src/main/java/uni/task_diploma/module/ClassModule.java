package uni.task_diploma.module;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@AllArgsConstructor
@Builder
@Setter
public class ClassModule {

    public String className;

    public String type;

    public String room;

    public List<String> lector;

    public String comment;

    public String commentator;


    public ClassModule(){
        this.className = "";
        this.room = "";
        this.lector = new ArrayList<>();
        this.type = "";
    }
}

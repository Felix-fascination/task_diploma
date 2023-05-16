package uni.task_diploma.module;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@AllArgsConstructor
@Builder
public class ClassModule {
    // Possibly need to change these two to outer and inner keys for Maps
    /*private final String dayOfWeek;

    private final String time;*/

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

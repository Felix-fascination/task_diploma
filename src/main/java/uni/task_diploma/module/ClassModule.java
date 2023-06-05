package uni.task_diploma.module;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;


@AllArgsConstructor
@Builder
@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ClassModule {

    Integer id;

    String className;

    String type;

    String room;

    int dayNumber;

    int timeNumber;

    String time;

    String lector;

    String comment;

    String commentator;


    public ClassModule(){
        this.className = "";
        this.room = "";
        this.lector = "";
        this.type = "";
        this.comment = "";
        this.commentator = "";
    }
}

package uni.task_diploma.module;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Comment {

    private final String day;

    private final String time;

    private final String comment;

    private String commentatorName;


}

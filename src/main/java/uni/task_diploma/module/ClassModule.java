package uni.task_diploma.module;

import lombok.Builder;
import lombok.RequiredArgsConstructor;

import java.util.List;


@RequiredArgsConstructor
@Builder
public class ClassModule {
    // Possibly need to change these two to outer and inner keys for Maps
    /*private final String dayOfWeek;

    private final String time;*/

    private final String className;

    // remake to enumerate
    private final String type;

    private final String room;

    private final List<String> lector;
}

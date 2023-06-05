package uni.task_diploma.module;


import lombok.*;
import lombok.experimental.FieldDefaults;

@RequiredArgsConstructor
@Getter
@EqualsAndHashCode
@Builder
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class TimeValue {
    @EqualsAndHashCode.Exclude
    String time;
    int timeIndex;


}

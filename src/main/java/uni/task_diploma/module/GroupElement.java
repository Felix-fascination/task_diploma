package uni.task_diploma.module;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
@Builder
@EqualsAndHashCode
public class GroupElement implements Comparable{

    private final String groupName;

    private final String groupHref;

    private final String facultyName;

    private final String course;

    @Override
    public String toString() {
        return "course: " + course + "\nFaculty: " + facultyName + " \n group name:" + groupName + "\n href: " + groupHref;
    }


    @Override
    public int compareTo(Object o) {
        GroupElement groupElement = (GroupElement) o;
        int facultyDiff = groupElement.getFacultyName().compareTo(this.facultyName)*1000;
        int courseDiff = groupElement.getCourse().compareTo(this.course) * 10000;
        // Some collisions are possible
        String groupNumber1 = groupElement.getGroupName().split("-")[1];
        String groupNumber2 = this.groupName.split("-")[1];


        return courseDiff + facultyDiff + groupNumber1.compareTo(groupNumber2) * 10 +
                + groupElement.getGroupHref().compareTo(this.groupHref);

    }
}

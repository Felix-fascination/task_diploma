package uni.task_diploma.module;

import lombok.*;

@AllArgsConstructor
@Getter
@Builder
@EqualsAndHashCode
public class GroupElement{

    private String groupName;

    private String groupHref;


    /*private final String facultyName;

    private final String course;*/

    /*@Override
    public String toString() {
        return "course: " + course + "\nFaculty: " + facultyName + " \n group name:" + groupName + "\n href: " + groupHref;
    }*/


    /*@Override
    public int compareTo(Object o) {
        GroupElement groupElement = (GroupElement) o;
        int facultyDiff = this.facultyName.compareTo(groupElement.getFacultyName())*1000;
        int courseDiff = this.course.compareTo(groupElement.getCourse()) * 100000;
        // Some collisions are possible
        String groupNumber1 = groupElement.getGroupName().split("-")[1];
        String groupNumber2 = this.groupName.split("-")[1];


        return courseDiff + facultyDiff + groupNumber2.compareTo(groupNumber1) * 10 +
                + this.groupHref.compareTo(groupElement.getGroupHref());

    }*/
}

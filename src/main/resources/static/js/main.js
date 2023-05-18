function showCourse(courseName) {
    // Hide all course containers
    var containers = document.getElementsByClassName('course-container');
    for (var i = 0; i < containers.length; i++) {
        containers[i].style.display = 'none';
    }

    // Show the selected course container
    var container = document.getElementById(courseName);
    if (container) {
        container.style.display = 'block';
    }
}

// Show the first course container by default
window.onload = function() {
    var firstCourseButton = document.querySelector('.course-button');
    if (firstCourseButton) {
        var firstCourseName = firstCourseButton.getAttribute('data-course');
        showCourse(firstCourseName);
    }
};

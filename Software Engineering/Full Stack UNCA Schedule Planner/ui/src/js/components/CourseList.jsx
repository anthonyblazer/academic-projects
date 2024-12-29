import React from "react";
import Course from "./Course.jsx";

export default function CourseList({ courseList, addToSchedule }) {
    return (
        <div className="grid gap-6 lg:grid-cols-2">
            {courseList.map((course) => (
                <Course
                    key={course.crn}
                    course={course}
                    addToSchedule={addToSchedule}
                />
            ))}
        </div>
    );
}

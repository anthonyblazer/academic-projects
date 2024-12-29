import { React } from "react";
import { CloseCircleFilled, CheckCircleFilled } from "@ant-design/icons";

export default function Course({
    course,
    removeCourse = null,
    addToSchedule = null,
}) {
    function formatTime(timeStr) {
        let t = new Date(timeStr);
        return t.toLocaleTimeString("en-US", {
            hour: "2-digit",
            minute: "2-digit",
            timeZone: "America/New_York",
        });
    }
    const startTime = formatTime(course.start_time);
    const endTime = formatTime(course.end_time);

    function getExtras() {
        const extras = [];
        if (course.diversity_intensive) {
            extras.push("DIVERSITY INTENSIVE");
        }
        if (course.diversity_intensive_r) {
            extras.push("DIVERSITY INTENSIVE - RACE");
        }
        if (course.arts) {
            extras.push("Arts");
        }
        if (course.first_year_seminar) {
            extras.push("FIRST YEAR SEMINAR");
        }
        if (course.honors) {
            extras.push("HONORS");
        }
        if (course.service_learning) {
            extras.push("SERVICE LEARNING");
        }

        return extras;
    }
    return (
        <div className="text-sm border border-gray-200 bg-white rounded-md p-4 gap-2 grid grid-cols-[auto_75px] items-start">
            <div>
                <h3 className="font-semibold">
                    {course.code}. {course.title}
                </h3>
                <div>
                    <span>
                        {course.open ? (
                            <CheckCircleFilled className="text-green-700 text-lg align-text-top" />
                        ) : (
                            <CloseCircleFilled className="text-red-700 text-lg align-text-top" />
                        )}
                    </span>
                    <span> {course.crn} &bull; </span>
                    <span>
                        Seats Available:{" "}
                        {course.enrollment_max - course.enrollment_current}
                    </span>
                </div>
                <div>
                    {course.days ? course.days + " • " : ""}
                    {startTime ? startTime + "-" : ""}
                    {endTime ? endTime + " • " : ""}
                    {course.location
                        ? course.location.full_location + " • "
                        : ""}
                    {course.hours} Credit Hour(s)
                </div>
                <div>
                    {course.instructors.map((inst, idx) => {
                        return (
                            <span key={`inst_${idx}`}>
                                {inst.full_name}
                                {idx < course.instructors.length - 1 && " • "}
                            </span>
                        );
                    })}
                </div>
                <div className="flex gap-2">
                    {getExtras().map((str) => (
                        <span
                            key={str}
                            className="bg-gray-100 mt-1 rounded-md px-2 py-1 text-xs font-semibold uppercase"
                        >
                            {str}
                        </span>
                    ))}
                </div>
            </div>
            {removeCourse ? (
                <button
                    onClick={function () {
                        removeCourse(course.crn);
                    }}
                    className="self-center font-bold py-2 px-2 text-sm rounded bg-gray-200 text-gray-900 hover:bg-gray-300"
                >
                    Remove
                </button>
            ) : (
                <button
                    onClick={function () {
                        addToSchedule(course.crn);
                    }}
                    className="self-center font-bold py-2 px-2 text-sm rounded bg-gray-200 text-gray-900 hover:bg-gray-300"
                >
                    Add
                </button>
            )}
        </div>
    );
}

import { React, useState, useEffect } from "react";
import Form from "./components/CourseSearchForm.jsx";
import Header from "./components/Header.jsx";
import CourseList from "./components/CourseList.jsx";
import Schedule from "./components/Schedule.jsx";
import {
    fetchUser,
    fetchSchedule,
    fetchCourses,
    deleteCourseFromSchedule,
    addCourseToSchedule,
} from "./services/api.jsx";

export default function App() {
    const [courseList, setCourseList] = useState([]);
    const [user, setUser] = useState(null);
    const [schedule, setSchedule] = useState(null);
    const username = "svanwart";

    async function filterCourses(options) {
        const data = await fetchCourses(options);
        setCourseList(data);
    }

    async function removeCourse(crn) {
        const scheduleNew = await deleteCourseFromSchedule(schedule, crn);
        setSchedule(scheduleNew);
    }

    async function addCourse(crn) {
        const scheduleNew = await addCourseToSchedule(schedule, crn);
        if (scheduleNew && scheduleNew.id) {
            setSchedule(scheduleNew);
        } else if (scheduleNew.detail) {
            console.error(scheduleNew.detail);
        } else {
            console.error("Not added - unknown error, check logs");
        }
    }

    useEffect(() => {
        async function initializeData() {
            const userData = await fetchUser(username);
            setUser(userData);

            const scheduleData = await fetchSchedule(userData.username);
            setSchedule(scheduleData);
        }
        initializeData();
    }, []);

    return (
        <>
            <Header user={user} />
            <Schedule schedule={schedule} removeCourse={removeCourse} />

            <main className="bg-slate-50 w-auto max-w-screen-xl m-auto lg:p-10">
                <Form fetchCourses={filterCourses} />
                <CourseList courseList={courseList} addToSchedule={addCourse} />
            </main>
        </>
    );
}
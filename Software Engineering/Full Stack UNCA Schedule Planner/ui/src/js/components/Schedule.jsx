import { React, useState } from "react";
import { Button, Drawer } from "antd";
import Course from "./Course.jsx";

export default function Schedule({ schedule, removeCourse }) {
    const [open, setOpen] = useState(false);

    function showSchedule() {
        setOpen(true);
    }
    function onClose() {
        setOpen(false);
    }

    return (
        <>
            <Button onClick={showSchedule} className="absolute top-10 right-4">
                View Schedule
            </Button>
            {schedule && (
                <Drawer
                    title={schedule.name}
                    onClose={onClose}
                    open={open}
                    width={640}
                    placement="right"
                >
                    <div className="grid gap-6">
                        {schedule.courses && schedule.courses.length > 0
                            ? schedule.courses.map((course) => (
                                  <Course
                                      key={course.crn}
                                      course={course}
                                      removeCourse={removeCourse}
                                  />
                              ))
                            : "No courses currently added to your schedule."}
                    </div>
                </Drawer>
            )}
        </>
    );
}

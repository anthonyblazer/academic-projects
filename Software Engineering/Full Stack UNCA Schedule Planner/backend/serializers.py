from datetime import datetime
from typing import List, Optional

from pydantic import BaseModel


class Instructor(BaseModel):
    username: str
    full_name: str
    first_name: str
    last_name: str

    class Config:
        orm_mode = True


class Location(BaseModel):
    full_location: str
    building: Optional[str] = None
    room: Optional[str] = None

    class Config:
        orm_mode = True


class Course(BaseModel):
    crn: int
    code: str
    department: str
    title: str
    hours: Optional[int] = None
    days: Optional[str] = ""
    start_time: Optional[datetime] = None
    end_time: Optional[datetime] = None
    enrollment_current: int
    enrollment_max: int
    waitlist_max: int
    waitlist_available: int
    term_part: str
    start_date: Optional[datetime] = None
    end_date: Optional[datetime] = None
    instructional_method: str
    async_class: bool
    instructors: List[Instructor] = []
    location: Optional[Location] = None  # type: ignore
    diversity_intensive: bool
    diversity_intensive_r: bool
    distance_learning: bool
    first_year_seminar: bool
    graduate: bool
    honors: bool
    arts: bool
    service_learning: bool
    open: bool

    class Config:
        orm_mode = True


class ScheduleCreate(BaseModel):
    name: str
    course_ids: List[int]  # List of course IDs to add to the schedule


class Schedule(BaseModel):
    id: int
    name: str
    user_id: int
    courses: List[Course] = []  # Courses in this schedule

    class Config:
        orm_mode = True


class SchedulCourseCreate(BaseModel):
    crn: int


# Base User schemas (same as before)
class User(BaseModel):
    id: int
    username: str
    email: str
    first_name: str
    last_name: str

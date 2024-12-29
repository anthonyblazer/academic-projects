import csv
import random

import requests
from faker import Faker
from sqlalchemy import select
from sqlalchemy.orm import Session

from db import Base
from db import engine_sync as engine
from models import (
    Course,
    CourseInstructor,
    Instructor,
    Location,
    Schedule,
    ScheduleCourse,
    User,
)

fake = Faker()


def create_location(db, course_data):
    location_entry = course_data["Location"]
    location = db.execute(
        select(Location).filter(
            Location.full_location == location_entry["FullLocation"]
        )
    ).scalar()
    if location:
        return location
    if location_entry["FullLocation"]:
        location = Location(
            full_location=location_entry["FullLocation"],
            building=location_entry["Building"],
            room=location_entry["Room"],
        )
        db.add(location)
        db.commit()
        db.refresh(location)
        return location
    else:
        return None


def create_and_associate_instructors(db, course_data, course):
    # Insert Instructors
    instructors = []
    for instructor_data in course_data["Instructors"]:
        instructor = db.execute(
            select(Instructor).filter(
                Instructor.full_name == instructor_data.get("Name")
            )
        ).scalar()

        # create new instructor entry if doesn't exist
        if not instructor:
            name_tokens = instructor_data.get("Name").split(",")
            last_name = ""
            first_name = ""
            if len(name_tokens) == 2:
                last_name = name_tokens[0].strip()
                first_name = name_tokens[1].strip()

            instructor = Instructor(
                username=instructor_data.get("Username"),
                full_name=instructor_data.get("Name"),
                last_name=last_name,
                first_name=first_name,
            )
            db.add(instructor)
            db.commit()
            db.refresh(instructor)
        instructors.append(instructor)
    db.commit()

    # Add instructor-course relationships:
    if instructors:
        for instructor in instructors:
            course_instructor = CourseInstructor(
                course_id=course.crn, instructor_id=instructor.id
            )
            db.add(course_instructor)
        db.commit()


def create_course(db, course_data, location):

    classification_data = course_data["Classification"]
    course = Course(
        crn=course_data["CRN"],
        code=course_data["Code"],
        department=course_data["Department"],
        title=course_data["Title"],
        hours=course_data["Hours"],
        days=course_data["Days"],
        start_time=course_data["StartTime"],
        end_time=course_data["EndTime"],
        enrollment_current=course_data["EnrollmentCurrent"],
        enrollment_max=course_data["EnrollmentMax"],
        waitlist_max=course_data["WaitlistMax"],
        waitlist_available=course_data["WaitlistAvailable"],
        term_part=course_data["TermPart"],
        start_date=course_data["StartDate"],
        end_date=course_data["EndDate"],
        instructional_method=course_data["InstructionalMethod"],
        async_class=course_data["Async"],
        location_id=location.id if location else None,
        diversity_intensive=classification_data.get("DiversityIntensive"),
        diversity_intensive_r=classification_data.get("DiversityIntensiveR"),
        distance_learning=classification_data.get("DistanceLearning"),
        first_year_seminar=classification_data.get("FirstYearSeminar"),
        graduate=classification_data.get("Graduate"),
        honors=classification_data.get("Honors"),
        arts=classification_data.get("Arts"),
        service_learning=classification_data.get("ServiceLearning"),
        open=classification_data.get("Open"),
    )

    db.add(course)
    db.commit()
    db.refresh(course)  # Make sure the CRN is available after commit
    return course


def create_users_from_csv(db: Session):
    users = []
    with open("users.csv", "r") as file:
        reader = csv.reader(file)
        for row in reader:
            last_name = row[0]
            first_name = row[1]
            email = row[2]
            username = email.split("@")[0]

            # Check if user already exists
            user = db.execute(select(User).filter(User.username == username)).scalar()
            if user:
                print(f"User {username} already exists in the database.")
            else:
                user = User(
                    username=username,
                    email=email,
                    first_name=first_name,
                    last_name=last_name,
                )
                db.add(user)
            users.append(user)
        db.commit()
    return users


def fetch_course_data(year: str, term: str) -> list:
    url_base = "https://meteor.unca.edu/registrar/class-schedules/api/v1/courses/"
    response = requests.get(f"{url_base}{year}/{term}/")
    return response.json()


def load_course_data(db: Session):

    courses = []
    course_data_list = fetch_course_data(year="2025", term="spring")

    for course_data in course_data_list:
        # Check if course already exists by its unique field (e.g., CRN)
        existing_course = db.execute(
            select(Course).filter(Course.crn == course_data["CRN"])
        ).scalar()
        # print(existing_course)
        if existing_course:
            print(f"Course with CRN {course_data['CRN']} already exists, skipping.")
            continue  # Skip adding the course if it exists

        # Insert Location
        location = create_location(db, course_data)

        # Insert Course
        course = create_course(db, course_data, location)
        courses.append(course)

        # Insert Instructors
        create_and_associate_instructors(db, course_data, course)

    print("Database populated successfully with fake data!")
    return courses


def create_fake_schedules(db: Session, users: list[User]):
    print("Create fake schedule...")
    result = db.execute(select(Course).order_by(Course.department, Course.code))
    courses = result.scalars().all()
    for user in users:
        schedule = db.execute(
            select(Schedule).filter(Schedule.user_id == user.id)
        ).scalar()
        if schedule:
            print(f"{user.first_name} {user.last_name} already has a schedule")
        else:
            schedule = Schedule(
                name=f"{user.first_name} {user.last_name}'s Schedule", user_id=user.id
            )
            db.add(schedule)
            db.commit()
            db.refresh(schedule)

        # Add some courses to the schedule
        print("Creating", schedule.name)
        limit = 5
        if len(schedule.courses) < limit:
            count = limit - len(schedule.courses)
            for _ in range(count):
                course = random.choice(courses)

                # check that course isn't already in the user's schedule:
                course_schedule_result = db.execute(
                    select(ScheduleCourse).where(
                        ScheduleCourse.schedule_id == schedule.id,
                        ScheduleCourse.course_crn == course.crn,
                    )
                )
                course_schedule = course_schedule_result.scalar_one_or_none()
                if course_schedule:
                    print(course, "already in the user's schedule")
                    continue

                # add the course
                course_schedule = ScheduleCourse(
                    course_crn=course.crn, schedule_id=schedule.id
                )
                db.add(course_schedule)
            db.commit()


def main():
    # Initialize the database tables if they don't exist
    Base.metadata.create_all(bind=engine)

    # Creates a synchronous session (but server endpoints use an async session)
    with Session(bind=engine) as db:
        users = create_users_from_csv(db)
        load_course_data(db)
        create_fake_schedules(db, users)


if __name__ == "__main__":
    main()

import asyncio

from sqlalchemy import select, distinct  # , or_
from sqlalchemy.orm import joinedload, selectinload

from db import AsyncSessionLocal
from models import Course, Schedule, User

"""
Documentation:
https://docs.sqlalchemy.org/en/20/orm/queryguide/select.html#selecting-orm-entities-and-attributes
"""


async def show_courses(db: AsyncSessionLocal):
    # create query:
    query = select(Course).order_by(Course.department)

    # execute the query:
    result = await db.execute(query)

    # convert the query results to a list:
    courses = result.scalars().all()

    # print select information for each course:
    for course in courses:
        print(f"{course.crn} ({course.department}) - {course.title}")


async def show_courses_with_table_joins(db: AsyncSessionLocal):

    # this is how joins work in SQLAlchemy:
    query = (
        select(Course)
        .options(
            selectinload(
                Course.instructors
            ),  # joins courses table to instructors table
            selectinload(Course.location),  # joins courses table to locations table
        )
        .order_by(Course.department)
    )

    # execute the query:
    result = await db.execute(query)

    # convert the query results to a list:
    courses = result.scalars().all()

    # print select information for each course:
    for course in courses:

        # print stuff from courses tables
        print(f"{course.crn} ({course.department}) - {course.title}")

        # because we joined on the instructors table, we  have access to its data:
        instructor_names = [instructor.full_name for instructor in course.instructors]
        print("Intructor(s):", ", ".join(instructor_names))

        # because we joined on the locations table, we can output the location:
        if course.location:
            print("Location:", course.location.full_location)
        print("-" * 70)


async def print_schedules(db: AsyncSessionLocal):
    # Fetch the courses from the DB:
    query = select(Schedule).options(
        # joins the schedule and courses table together
        selectinload(Schedule.courses).options(
            # and within each course, it also joins with the
            # corresponding location and instructors:
            joinedload(Course.location),
            selectinload(Course.instructors),
        )
    )
    result = await db.execute(query)

    schedules = result.scalars().all()

    for schedule in schedules:
        print()
        print("-" * 70)
        print(schedule.name)
        print("-" * 70)
        for course in schedule.courses:
            print(f"* {course.crn} ({course.department}) - {course.title}")  # noqa
            instructor_names = [
                instructor.full_name for instructor in course.instructors
            ]
            print("     * Intructor(s):", ", ".join(instructor_names))
            if course.location:
                print("     * Location:", course.location.full_location)
            print()


# ++++++++++++++++++++++++
#   TEST 2 ANSWERS
# ++++++++++++++++++++++++

"""
Print all usernames in the system
"""
async def show_usernames(db: AsyncSessionLocal):
    try:
        # Query all usernames from the User model
        query = select(User.username).order_by(User.username)
        result = await db.execute(query)
        
        # Extract and print usernames
        usernames = result.scalars().all()
        print("Usernames in the system:")
        for username in usernames:
            print(f"- {username}")
    except Exception as e:
        print(f"Error querying usernames: {e}")


"""
Prints a distinct list of departments
"""
async def show_distinct_deparments(db: AsyncSessionLocal):
    try:
        # Query distinct departments from the Course model
        query = select(distinct(Course.department)).order_by(Course.department)
        result = await db.execute(query)
        
        # Extract and print unique departments
        departments = result.scalars().all()
        print("Unique departments:")
        for department in departments:
            print(f"- {department}")
    except Exception as e:
        print(f"Error querying departments: {e}")


"""
Prints the CRN and title of courses in the CSCI department that are currently open (not full).
"""
async def show_open_cs_courses(db: AsyncSessionLocal):
    try:
        # Query open CSCI courses from the Course model
        query = (
            select(Course.crn, Course.title)
            .where(Course.department == "CSCI", Course.enrollment_current < Course.enrollment_max)
            .order_by(Course.crn)
        )
        result = await db.execute(query)
        
        # Extract and print open courses
        open_courses = result.all()
        print("Open CSCI courses:")
        for crn, title in open_courses:
            print(f"- CRN: {crn}, Title: {title}")
    except Exception as e:
        print(f"Error querying open CSCI courses: {e}")





async def main():
    # create a DB session
    db = AsyncSessionLocal()

    # async function invocations go here:
    await show_courses(db)
    await show_courses_with_table_joins(db)
    await print_schedules(db)
    # New functions
    await show_usernames(db)
    await show_distinct_deparments(db)
    await show_open_cs_courses(db)

    await db.close()


if __name__ == "__main__":
    asyncio.run(main())

# ###################
# # SELECT PRACTICE #
# ###################
# # https://docs.sqlalchemy.org/en/14/orm/tutorial.html#querying

# # Query all of the users:
# query = select(User).order_by(User.id)
# # users = session.execute(query)
# users = session.scalars(query)

# # Output all of the users using regular Python:
# print(query)  # prints the SQL
# for user in users:
#     print(user.username)

# # Query all of the tasks:
# query = select(Task).order_by(Task.id)
# print(query)
# tasks = session.scalars(query)

# # Print them:
# for task in tasks:
#     print(task.name, task.user.username)

# # Query all of the tasks owned by Keith Taylor:
# query = (
#     select(Task).join(User).filter(User.username == "keith_taylor").order_by(Task.id)
# )
# print(query)
# tasks = session.scalars(query)

# for task in tasks:
#     print(task.name, task.user.username)

# # Query all of the tasks owned by Keith Taylor or Misty Baker:
# query = (
#     select(Task)
#     .join(User)
#     .filter(or_(User.username == "keith_taylor", User.username == "misty_baker"))
#     .order_by(Task.id)
# )
# print(query)
# tasks = session.scalars(query)
# for task in tasks:
#     print(task.name, task.user.username)

# ###################
# # INSERT PRACTICE #
# ###################

# # create a user:
# user = User(
#     username="walter_jones",
#     first_name="Walter",
#     last_name="Jones",
#     email="walter_jones@gmail.com",
# )

# # save it:
# session.add(user)
# session.commit()

# # verify that it worked:
# query = select(User).order_by(User.id)
# users = session.scalars(query)
# for user in users:
#     print(user.to_dict())


# # create a task:
# task = Task(name="Gym", description="Lift weights", done=False, user=user)

# # save it:
# session.add(task)
# session.commit()

# # verify that it worked:
# query = select(Task).order_by(Task.id)
# tasks = session.scalars(query)
# for task in tasks:
#     print(task.to_dict())


# ###################
# # UPDATE PRACTICE #
# ###################

# # get task #5 from the database:
# query = select(Task).where(Task.id == 5)
# result = session.execute(query).fetchone()
# print(result)  # returns as a tuple, so get the first task from the tuple
# task = None
# if result is not None:
#     task = result[0]
#     print(task.to_dict())  # before
#     task.done = True
#     session.commit()
#     print(task.to_dict())  # after


# ###################
# # DELETE PRACTICE #
# ###################

# # delete task #5
# if task is not None:
#     session.delete(task)
#     session.commit()


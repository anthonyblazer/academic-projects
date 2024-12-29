const rootURL = "http://localhost:8000";

// React Task 1:
export async function fetchUser(username) {
    const url = `${rootURL}/api/users/${username}`;
    try {
        const response = await fetch(url);
        if (!response.ok) {
            throw new Error(`Failed to fetch user: ${response.statusText}`);
        }
        const user = await response.json();
        return user;
    } catch (error) {
        console.error("Error fetching user:", error);
        throw error; // Re-throwing the error for higher-level handling
    }
}

// React Task 3:
export async function fetchCourses(options = {}) {
    let baseURL = `${rootURL}/api/courses/?`;

    // Add existing parameters
    if (options.title) {
        baseURL += `title=${options.title}&`;
    }
    if (options.instructor) {
        baseURL += `instructor=${options.instructor}&`;
    }
    if (options.department) {
        baseURL += `department=${options.department}&`;
    }

    if (options.hours) {
        baseURL += `hours=${options.hours}&`;
    }


    // Add new parameters
    if (options.classification) {
        // Append multiple designations, if provided
        options.classification.forEach((category) => {
            baseURL += `${category}=true&`;
        });
    }
    if (options.days) {
        // Join days into a comma-separated string
        baseURL += `days=${options.days.join(",")}&`;
    }
    if (options.openOnly !== undefined) {
        // Add boolean for open courses
        baseURL += `open_courses=${options.openOnly}&`;
    }

    // Remove trailing '&' if it exists
    baseURL = baseURL.endsWith("&") ? baseURL.slice(0, -1) : baseURL;

    console.log(baseURL);
    const response = await fetch(baseURL);
    const courses = await response.json();
    console.log(courses);
    return courses;
}

export async function fetchSchedule(username) {
    const response = await fetch(`${rootURL}/api/schedules/${username}`);
    return await response.json();
}

export async function deleteCourseFromSchedule(schedule, crn) {
    const url = `${rootURL}/api/schedules/${schedule.id}/courses/${crn}`;
    const response = await fetch(url, {
        method: "DELETE",
    });
    const data = await response.json();
    console.log(data);
    return data;
}

export async function addCourseToSchedule(schedule, crn) {
    console.log(crn);
    const url = `${rootURL}/api/schedules/${schedule.id}/courses`;

    const response = await fetch(url, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify({
            crn: crn,
        }),
    });
    const data = await response.json();
    console.log(data);
    return data;
}
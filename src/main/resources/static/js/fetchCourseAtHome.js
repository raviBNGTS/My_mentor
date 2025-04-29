// Function to fetch courses from API
async function fetchCourses() {
    try {
        const response = await fetch('/api/courses'); // Change this to your actual API endpoint
        const courses = await response.json();
        const scroller = document.getElementById('courses-scroller');

        // Create a card for each course
        courses.slice(0, 10).forEach(course => {
            const courseCard = document.createElement('div');
            courseCard.classList.add('course-card');
            courseCard.innerHTML = `
                <img src="${course.imageUrl}" alt="${course.title}">
                <h3>${course.title}</h3>
                <p>${course.description}</p>
            `;
            scroller.appendChild(courseCard);
        });
    } catch (error) {
        console.error('Error fetching courses:', error);
    }
}

// Load courses when the DOM is ready
document.addEventListener('DOMContentLoaded', fetchCourses);

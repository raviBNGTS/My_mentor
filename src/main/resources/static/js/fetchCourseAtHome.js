
document.addEventListener("DOMContentLoaded", function () {
    fetch("http://localhost:8080/courses")
        .then(response => response.json())
        .then(data => {
            const container = document.getElementById("courses-scroller");
            container.innerHTML = ""; // Clear placeholder

            data.forEach(course => {
                const courseDiv = document.createElement("div");
                courseDiv.classList.add("course");

                courseDiv.innerHTML = `
                    <h3>${course.title}</h3>
                    <p>${course.description}</p>
                    <img src="${course.imageUrl}" alt="${course.title}" width="200">
                `;
                let container.appendChild(courseDiv);
            });
        })
        .catch(error => {
            console.error("Error fetching courses:", error);
        });
});


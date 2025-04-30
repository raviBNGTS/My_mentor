document.addEventListener("DOMContentLoaded", () => {
    fetch("/featured-courses")
        .then(response => response.json())
        .then(data => {
            const container = document.getElementById("courses-scroller");
            if (!container) return;

            if (data.length === 0) {
                container.innerHTML = "<p>No featured courses found.</p>";
                return;
            }

            data.forEach(course => {
                const div = document.createElement("div");
                div.classList.add("course-card");
                div.innerHTML = `
                    <img src="/images/${course.imageName}" alt="${course.title}" />
                    <h3>${course.title}</h3>
                    <p>${course.price}</p>
                    <p>${course.language}</p>
                `;
                container.appendChild(div);
            });
        })
        .catch(error => console.error("Error loading featured courses:", error));
});

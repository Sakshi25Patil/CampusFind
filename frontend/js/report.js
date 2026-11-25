
const reportForm = document.getElementById("reportForm");
const formMessage = document.getElementById("formMessage");

reportForm.addEventListener("submit", async function (event) {
    event.preventDefault();

    const reportType = window.location.pathname.endsWith("report-found.html")
        ? "Found"
        : "Lost";

    const newItem = {
        itemName: document.getElementById("itemName").value.trim(),
        category: document.getElementById("category").value,
        reportType: reportType,
        description: document.getElementById("description").value.trim(),
        location: document.getElementById("location").value.trim(),
        itemDate: document.getElementById("itemDate").value,
        studentName: document.getElementById("studentName").value.trim(),
        contact: document.getElementById("contact").value.trim()
    };

    try {
        const response = await fetch("http://localhost:8080/api/items", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(newItem)
        });

        const result = await response.json();

        if (!response.ok) {
            throw new Error(result.message || "Unable to submit report.");
        }

        formMessage.textContent =
            `${reportType} item report submitted successfully!`;

        formMessage.style.color = "green";

        reportForm.reset();

    } catch (error) {
        formMessage.textContent =
            error.message || "Unable to connect to the Java server.";

        formMessage.style.color = "red";
    }
});

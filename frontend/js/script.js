
const API_URL = "http://localhost:8080/api/items";

async function loadItems() {

    const itemsContainer = document.getElementById("itemsContainer");

    try {

        const response = await fetch(API_URL);

        if (!response.ok) {
            throw new Error("Unable to fetch items");
        }

        const items = await response.json();

        itemsContainer.innerHTML = "";

        if (items.length === 0) {
            itemsContainer.innerHTML = "<p>No items reported yet.</p>";
            return;
        }

        items.forEach(item => {

            const card = document.createElement("div");

            card.className = "item-card";

            const heading = document.createElement("h3");
            heading.textContent = item.itemName;

            const details = [
                ["Category", item.category],
                ["Type", item.reportType],
                ["Description", item.description || "Not provided"],
                ["Location", item.location],
                ["Date", item.itemDate],
                ["Reported By", item.studentName]
            ];

            card.appendChild(heading);

            details.forEach(([label, value]) => {
                const paragraph = document.createElement("p");
                paragraph.textContent = `${label}: ${value}`;
                card.appendChild(paragraph);
            });

            const status = document.createElement("span");
            status.className = "status";
            status.textContent = item.status;

            card.appendChild(status);
            itemsContainer.appendChild(card);

        });

    } catch (error) {

        console.error("Error:", error);

        itemsContainer.innerHTML =
            "<p>Unable to load items. Please check whether the Java server is running.</p>";
    }
}

// Load items when the webpage opens
document.addEventListener("DOMContentLoaded", loadItems);

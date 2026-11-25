
const lostContainer = document.getElementById("lostItemsContainer");
const foundContainer = document.getElementById("foundItemsContainer");
const searchInput = document.getElementById("searchInput");

let allItems = [];

function escapeHTML(value) {
    return String(value ?? "").replace(/[&<>"']/g, character => ({
        "&": "&amp;",
        "<": "&lt;",
        ">": "&gt;",
        '"': "&quot;",
        "'": "&#39;"
    })[character]);
}

function createItemCard(item) {
    return `
        <div class="item-card">
            <h3>${escapeHTML(item.itemName)}</h3>
            <p><strong>Category:</strong> ${escapeHTML(item.category)}</p>
            <p><strong>Description:</strong> ${escapeHTML(item.description)}</p>
            <p><strong>Location:</strong> ${escapeHTML(item.location)}</p>
            <p><strong>Date:</strong> ${escapeHTML(item.itemDate)}</p>
            <span class="item-status">${escapeHTML(item.status)}</span>
        </div>
    `;
}

function displayItems(searchText = "") {
    const search = searchText.toLowerCase();

    const filteredItems = allItems.filter(item =>
        `${item.itemName} ${item.category} ${item.description} ${item.location}`
            .toLowerCase()
            .includes(search)
    );

    const lostItems = filteredItems.filter(
        item => item.reportType === "Lost"
    );

    const foundItems = filteredItems.filter(
        item => item.reportType === "Found"
    );

    lostContainer.innerHTML = lostItems.length
        ? lostItems.map(createItemCard).join("")
        : "<p>No matching lost items found.</p>";

    foundContainer.innerHTML = foundItems.length
        ? foundItems.map(createItemCard).join("")
        : "<p>No matching found items found.</p>";
}

async function loadItems() {
    try {
        const response = await fetch("http://localhost:8080/api/items");

        if (!response.ok) {
            throw new Error("Unable to load items.");
        }

        allItems = await response.json();

        displayItems(searchInput.value);

    } catch (error) {
        lostContainer.innerHTML =
            "<p>Unable to load lost items. Please check whether the Java server is running.</p>";

        foundContainer.innerHTML =
            "<p>Unable to load found items. Please check whether the Java server is running.</p>";
    }
}

searchInput.addEventListener("input", () => {
    displayItems(searchInput.value);
});

loadItems();

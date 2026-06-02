document.getElementById("loginForm").addEventListener("submit", function (event) {
    event.preventDefault();

    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;
    const errorMessage = document.getElementById("errorMessage");

    if (email === "admin@worknest.com" && password === "admin123") {
        localStorage.setItem("userEmail", email);
        localStorage.setItem("userRole", "ADMIN");

        window.location.href = "/dashboard";
    } else {
        errorMessage.classList.remove("d-none");
    }
});